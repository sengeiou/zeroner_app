package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class DataUpdateRequest extends zzbfm {
    public static final Creator<DataUpdateRequest> CREATOR = new zzz();
    private final long zzdvq;
    private final int zzeck;
    private final long zzgzz;
    private final DataSet zzhdx;
    private final zzbyf zzhgc;

    public static class Builder {
        /* access modifiers changed from: private */
        public long zzdvq;
        /* access modifiers changed from: private */
        public long zzgzz;
        /* access modifiers changed from: private */
        public DataSet zzhdx;

        public DataUpdateRequest build() {
            zzbq.zza(this.zzdvq, (Object) "Must set a non-zero value for startTimeMillis/startTime");
            zzbq.zza(this.zzgzz, (Object) "Must set a non-zero value for endTimeMillis/endTime");
            zzbq.checkNotNull(this.zzhdx, "Must set the data set");
            for (DataPoint dataPoint : this.zzhdx.getDataPoints()) {
                long startTime = dataPoint.getStartTime(TimeUnit.MILLISECONDS);
                long endTime = dataPoint.getEndTime(TimeUnit.MILLISECONDS);
                zzbq.zza(!((startTime > endTime ? 1 : (startTime == endTime ? 0 : -1)) > 0 || (((startTime > 0 ? 1 : (startTime == 0 ? 0 : -1)) != 0 && (startTime > this.zzdvq ? 1 : (startTime == this.zzdvq ? 0 : -1)) < 0) || (((startTime > 0 ? 1 : (startTime == 0 ? 0 : -1)) != 0 && (startTime > this.zzgzz ? 1 : (startTime == this.zzgzz ? 0 : -1)) > 0) || (endTime > this.zzgzz ? 1 : (endTime == this.zzgzz ? 0 : -1)) > 0 || (endTime > this.zzdvq ? 1 : (endTime == this.zzdvq ? 0 : -1)) < 0))), "Data Point's startTimeMillis %d, endTimeMillis %d should lie between timeRange provided in the request. StartTimeMillis %d, EndTimeMillis: %d", Long.valueOf(startTime), Long.valueOf(endTime), Long.valueOf(this.zzdvq), Long.valueOf(this.zzgzz));
            }
            return new DataUpdateRequest(this);
        }

        public Builder setDataSet(DataSet dataSet) {
            zzbq.checkNotNull(dataSet, "Must set the data set");
            this.zzhdx = dataSet;
            return this;
        }

        public Builder setTimeInterval(long j, long j2, TimeUnit timeUnit) {
            zzbq.zzb(j > 0, "Invalid start time :%d", Long.valueOf(j));
            zzbq.zzb(j2 >= j, "Invalid end time :%d", Long.valueOf(j2));
            this.zzdvq = timeUnit.toMillis(j);
            this.zzgzz = timeUnit.toMillis(j2);
            return this;
        }
    }

    DataUpdateRequest(int i, long j, long j2, DataSet dataSet, IBinder iBinder) {
        this.zzeck = i;
        this.zzdvq = j;
        this.zzgzz = j2;
        this.zzhdx = dataSet;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    private DataUpdateRequest(long j, long j2, DataSet dataSet, IBinder iBinder) {
        this.zzeck = 1;
        this.zzdvq = j;
        this.zzgzz = j2;
        this.zzhdx = dataSet;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    private DataUpdateRequest(Builder builder) {
        this(builder.zzdvq, builder.zzgzz, builder.zzhdx, null);
    }

    public DataUpdateRequest(DataUpdateRequest dataUpdateRequest, IBinder iBinder) {
        this(dataUpdateRequest.zzdvq, dataUpdateRequest.zzgzz, dataUpdateRequest.getDataSet(), iBinder);
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof DataUpdateRequest)) {
                return false;
            }
            DataUpdateRequest dataUpdateRequest = (DataUpdateRequest) obj;
            if (!(this.zzdvq == dataUpdateRequest.zzdvq && this.zzgzz == dataUpdateRequest.zzgzz && zzbg.equal(this.zzhdx, dataUpdateRequest.zzhdx))) {
                return false;
            }
        }
        return true;
    }

    public IBinder getCallbackBinder() {
        if (this.zzhgc == null) {
            return null;
        }
        return this.zzhgc.asBinder();
    }

    public DataSet getDataSet() {
        return this.zzhdx;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzgzz, TimeUnit.MILLISECONDS);
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzdvq, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzdvq), Long.valueOf(this.zzgzz), this.zzhdx});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("startTimeMillis", Long.valueOf(this.zzdvq)).zzg("endTimeMillis", Long.valueOf(this.zzgzz)).zzg("dataSet", this.zzhdx).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzdvq);
        zzbfp.zza(parcel, 2, this.zzgzz);
        zzbfp.zza(parcel, 3, (Parcelable) getDataSet(), i, false);
        zzbfp.zza(parcel, 4, getCallbackBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }

    public final long zzaae() {
        return this.zzdvq;
    }

    public final long zzaqu() {
        return this.zzgzz;
    }
}
