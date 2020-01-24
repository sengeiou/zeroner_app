package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SessionInsertRequest extends zzbfm {
    public static final Creator<SessionInsertRequest> CREATOR = new zzau();
    private final int versionCode;
    private final Session zzhhs;
    private final List<DataSet> zzhht;
    private final List<DataPoint> zzhhu;
    private final zzbyf zzhhv;

    public static class Builder {
        /* access modifiers changed from: private */
        public Session zzhhs;
        /* access modifiers changed from: private */
        public List<DataSet> zzhht = new ArrayList();
        /* access modifiers changed from: private */
        public List<DataPoint> zzhhu = new ArrayList();
        private List<DataSource> zzhhw = new ArrayList();

        private final void zzd(DataPoint dataPoint) {
            long startTime = this.zzhhs.getStartTime(TimeUnit.NANOSECONDS);
            long endTime = this.zzhhs.getEndTime(TimeUnit.NANOSECONDS);
            long timestamp = dataPoint.getTimestamp(TimeUnit.NANOSECONDS);
            if (timestamp != 0) {
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                long zza = (timestamp < startTime || timestamp > endTime) ? zzbut.zza(timestamp, TimeUnit.NANOSECONDS, timeUnit) : timestamp;
                zzbq.zza(zza >= startTime && zza <= endTime, "Data point %s has time stamp outside session interval [%d, %d]", dataPoint, Long.valueOf(startTime), Long.valueOf(endTime));
                if (dataPoint.getTimestamp(TimeUnit.NANOSECONDS) != zza) {
                    Log.w("Fitness", String.format("Data point timestamp [%d] is truncated to [%d] to match the precision [%s] of the session start and end time", new Object[]{Long.valueOf(dataPoint.getTimestamp(TimeUnit.NANOSECONDS)), Long.valueOf(zza), timeUnit}));
                    dataPoint.setTimestamp(zza, TimeUnit.NANOSECONDS);
                }
            }
            long startTime2 = this.zzhhs.getStartTime(TimeUnit.NANOSECONDS);
            long endTime2 = this.zzhhs.getEndTime(TimeUnit.NANOSECONDS);
            long startTime3 = dataPoint.getStartTime(TimeUnit.NANOSECONDS);
            long endTime3 = dataPoint.getEndTime(TimeUnit.NANOSECONDS);
            if (startTime3 != 0 && endTime3 != 0) {
                TimeUnit timeUnit2 = TimeUnit.MILLISECONDS;
                if (endTime3 > endTime2) {
                    endTime3 = zzbut.zza(endTime3, TimeUnit.NANOSECONDS, timeUnit2);
                }
                zzbq.zza(startTime3 >= startTime2 && endTime3 <= endTime2, "Data point %s has start and end times outside session interval [%d, %d]", dataPoint, Long.valueOf(startTime2), Long.valueOf(endTime2));
                if (endTime3 != dataPoint.getEndTime(TimeUnit.NANOSECONDS)) {
                    Log.w("Fitness", String.format("Data point end time [%d] is truncated to [%d] to match the precision [%s] of the session start and end time", new Object[]{Long.valueOf(dataPoint.getEndTime(TimeUnit.NANOSECONDS)), Long.valueOf(endTime3), timeUnit2}));
                    dataPoint.setTimeInterval(startTime3, endTime3, TimeUnit.NANOSECONDS);
                }
            }
        }

        public Builder addAggregateDataPoint(DataPoint dataPoint) {
            zzbq.checkArgument(dataPoint != null, "Must specify a valid aggregate data point.");
            DataSource dataSource = dataPoint.getDataSource();
            zzbq.zza(!this.zzhhw.contains(dataSource), "Data set/Aggregate data point for this data source %s is already added.", dataSource);
            DataSet.zzb(dataPoint);
            this.zzhhw.add(dataSource);
            this.zzhhu.add(dataPoint);
            return this;
        }

        public Builder addDataSet(DataSet dataSet) {
            boolean z = true;
            zzbq.checkArgument(dataSet != null, "Must specify a valid data set.");
            DataSource dataSource = dataSet.getDataSource();
            zzbq.zza(!this.zzhhw.contains(dataSource), "Data set for this data source %s is already added.", dataSource);
            if (dataSet.getDataPoints().isEmpty()) {
                z = false;
            }
            zzbq.checkArgument(z, "No data points specified in the input data set.");
            this.zzhhw.add(dataSource);
            this.zzhht.add(dataSet);
            return this;
        }

        public SessionInsertRequest build() {
            boolean z = true;
            zzbq.zza(this.zzhhs != null, (Object) "Must specify a valid session.");
            if (this.zzhhs.getEndTime(TimeUnit.MILLISECONDS) == 0) {
                z = false;
            }
            zzbq.zza(z, (Object) "Must specify a valid end time, cannot insert a continuing session.");
            for (DataSet dataPoints : this.zzhht) {
                for (DataPoint zzd : dataPoints.getDataPoints()) {
                    zzd(zzd);
                }
            }
            for (DataPoint zzd2 : this.zzhhu) {
                zzd(zzd2);
            }
            return new SessionInsertRequest(this);
        }

        public Builder setSession(Session session) {
            this.zzhhs = session;
            return this;
        }
    }

    SessionInsertRequest(int i, Session session, List<DataSet> list, List<DataPoint> list2, IBinder iBinder) {
        this.versionCode = i;
        this.zzhhs = session;
        this.zzhht = Collections.unmodifiableList(list);
        this.zzhhu = Collections.unmodifiableList(list2);
        this.zzhhv = zzbyg.zzba(iBinder);
    }

    private SessionInsertRequest(Session session, List<DataSet> list, List<DataPoint> list2, zzbyf zzbyf) {
        this.versionCode = 3;
        this.zzhhs = session;
        this.zzhht = Collections.unmodifiableList(list);
        this.zzhhu = Collections.unmodifiableList(list2);
        this.zzhhv = zzbyf;
    }

    private SessionInsertRequest(Builder builder) {
        this(builder.zzhhs, builder.zzhht, builder.zzhhu, null);
    }

    public SessionInsertRequest(SessionInsertRequest sessionInsertRequest, zzbyf zzbyf) {
        this(sessionInsertRequest.zzhhs, sessionInsertRequest.zzhht, sessionInsertRequest.zzhhu, zzbyf);
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof SessionInsertRequest)) {
                return false;
            }
            SessionInsertRequest sessionInsertRequest = (SessionInsertRequest) obj;
            if (!(zzbg.equal(this.zzhhs, sessionInsertRequest.zzhhs) && zzbg.equal(this.zzhht, sessionInsertRequest.zzhht) && zzbg.equal(this.zzhhu, sessionInsertRequest.zzhhu))) {
                return false;
            }
        }
        return true;
    }

    public List<DataPoint> getAggregateDataPoints() {
        return this.zzhhu;
    }

    public List<DataSet> getDataSets() {
        return this.zzhht;
    }

    public Session getSession() {
        return this.zzhhs;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhhs, this.zzhht, this.zzhhu});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("session", this.zzhhs).zzg("dataSets", this.zzhht).zzg("aggregateDataPoints", this.zzhhu).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) getSession(), i, false);
        zzbfp.zzc(parcel, 2, getDataSets(), false);
        zzbfp.zzc(parcel, 3, getAggregateDataPoints(), false);
        zzbfp.zza(parcel, 4, this.zzhhv == null ? null : this.zzhhv.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, zze);
    }
}
