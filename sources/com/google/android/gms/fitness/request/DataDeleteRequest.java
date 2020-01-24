package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataDeleteRequest extends zzbfm {
    public static final Creator<DataDeleteRequest> CREATOR = new zzj();
    private final long zzdvq;
    private final int zzeck;
    private final List<DataType> zzgzy;
    private final long zzgzz;
    private final zzbyf zzhgc;
    private final List<DataSource> zzhgf;
    private final List<Session> zzhgg;
    private final boolean zzhgh;
    private final boolean zzhgi;

    public static class Builder {
        /* access modifiers changed from: private */
        public long zzdvq;
        /* access modifiers changed from: private */
        public List<DataType> zzgzy = new ArrayList();
        /* access modifiers changed from: private */
        public long zzgzz;
        /* access modifiers changed from: private */
        public List<DataSource> zzhgf = new ArrayList();
        /* access modifiers changed from: private */
        public List<Session> zzhgg = new ArrayList();
        /* access modifiers changed from: private */
        public boolean zzhgh = false;
        /* access modifiers changed from: private */
        public boolean zzhgi = false;

        public Builder addDataSource(DataSource dataSource) {
            boolean z = true;
            zzbq.checkArgument(!this.zzhgh, "All data is already marked for deletion.  addDataSource() cannot be combined with deleteAllData()");
            if (dataSource == null) {
                z = false;
            }
            zzbq.checkArgument(z, "Must specify a valid data source");
            if (!this.zzhgf.contains(dataSource)) {
                this.zzhgf.add(dataSource);
            }
            return this;
        }

        public Builder addDataType(DataType dataType) {
            boolean z = true;
            zzbq.checkArgument(!this.zzhgh, "All data is already marked for deletion.  addDataType() cannot be combined with deleteAllData()");
            if (dataType == null) {
                z = false;
            }
            zzbq.checkArgument(z, "Must specify a valid data type");
            if (!this.zzgzy.contains(dataType)) {
                this.zzgzy.add(dataType);
            }
            return this;
        }

        public Builder addSession(Session session) {
            boolean z = true;
            zzbq.checkArgument(!this.zzhgi, "All sessions already marked for deletion.  addSession() cannot be combined with deleteAllSessions()");
            zzbq.checkArgument(session != null, "Must specify a valid session");
            if (session.getEndTime(TimeUnit.MILLISECONDS) <= 0) {
                z = false;
            }
            zzbq.checkArgument(z, "Cannot delete an ongoing session. Please stop the session prior to deleting it");
            this.zzhgg.add(session);
            return this;
        }

        public DataDeleteRequest build() {
            zzbq.zza(this.zzdvq > 0 && this.zzgzz > this.zzdvq, (Object) "Must specify a valid time interval");
            zzbq.zza((this.zzhgh || !this.zzhgf.isEmpty() || !this.zzgzy.isEmpty()) || (this.zzhgi || !this.zzhgg.isEmpty()), (Object) "No data or session marked for deletion");
            if (!this.zzhgg.isEmpty()) {
                for (Session session : this.zzhgg) {
                    zzbq.zza(session.getStartTime(TimeUnit.MILLISECONDS) >= this.zzdvq && session.getEndTime(TimeUnit.MILLISECONDS) <= this.zzgzz, "Session %s is outside the time interval [%d, %d]", session, Long.valueOf(this.zzdvq), Long.valueOf(this.zzgzz));
                }
            }
            return new DataDeleteRequest(this);
        }

        public Builder deleteAllData() {
            zzbq.checkArgument(this.zzgzy.isEmpty(), "Specific data type already added for deletion. deleteAllData() will delete all data types and cannot be combined with addDataType()");
            zzbq.checkArgument(this.zzhgf.isEmpty(), "Specific data source already added for deletion. deleteAllData() will delete all data sources and cannot be combined with addDataSource()");
            this.zzhgh = true;
            return this;
        }

        public Builder deleteAllSessions() {
            zzbq.checkArgument(this.zzhgg.isEmpty(), "Specific session already added for deletion. deleteAllData() will delete all sessions and cannot be combined with addSession()");
            this.zzhgi = true;
            return this;
        }

        public Builder setTimeInterval(long j, long j2, TimeUnit timeUnit) {
            zzbq.zzb(j > 0, "Invalid start time :%d", Long.valueOf(j));
            zzbq.zzb(j2 > j, "Invalid end time :%d", Long.valueOf(j2));
            this.zzdvq = timeUnit.toMillis(j);
            this.zzgzz = timeUnit.toMillis(j2);
            return this;
        }
    }

    DataDeleteRequest(int i, long j, long j2, List<DataSource> list, List<DataType> list2, List<Session> list3, boolean z, boolean z2, IBinder iBinder) {
        this.zzeck = i;
        this.zzdvq = j;
        this.zzgzz = j2;
        this.zzhgf = Collections.unmodifiableList(list);
        this.zzgzy = Collections.unmodifiableList(list2);
        this.zzhgg = list3;
        this.zzhgh = z;
        this.zzhgi = z2;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    private DataDeleteRequest(long j, long j2, List<DataSource> list, List<DataType> list2, List<Session> list3, boolean z, boolean z2, zzbyf zzbyf) {
        this.zzeck = 3;
        this.zzdvq = j;
        this.zzgzz = j2;
        this.zzhgf = Collections.unmodifiableList(list);
        this.zzgzy = Collections.unmodifiableList(list2);
        this.zzhgg = list3;
        this.zzhgh = z;
        this.zzhgi = z2;
        this.zzhgc = zzbyf;
    }

    private DataDeleteRequest(Builder builder) {
        this(builder.zzdvq, builder.zzgzz, builder.zzhgf, builder.zzgzy, builder.zzhgg, builder.zzhgh, builder.zzhgi, null);
    }

    public DataDeleteRequest(DataDeleteRequest dataDeleteRequest, zzbyf zzbyf) {
        this(dataDeleteRequest.zzdvq, dataDeleteRequest.zzgzz, dataDeleteRequest.zzhgf, dataDeleteRequest.zzgzy, dataDeleteRequest.zzhgg, dataDeleteRequest.zzhgh, dataDeleteRequest.zzhgi, zzbyf);
    }

    public boolean deleteAllData() {
        return this.zzhgh;
    }

    public boolean deleteAllSessions() {
        return this.zzhgi;
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof DataDeleteRequest)) {
                return false;
            }
            DataDeleteRequest dataDeleteRequest = (DataDeleteRequest) obj;
            if (!(this.zzdvq == dataDeleteRequest.zzdvq && this.zzgzz == dataDeleteRequest.zzgzz && zzbg.equal(this.zzhgf, dataDeleteRequest.zzhgf) && zzbg.equal(this.zzgzy, dataDeleteRequest.zzgzy) && zzbg.equal(this.zzhgg, dataDeleteRequest.zzhgg) && this.zzhgh == dataDeleteRequest.zzhgh && this.zzhgi == dataDeleteRequest.zzhgi)) {
                return false;
            }
        }
        return true;
    }

    public List<DataSource> getDataSources() {
        return this.zzhgf;
    }

    public List<DataType> getDataTypes() {
        return this.zzgzy;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzgzz, TimeUnit.MILLISECONDS);
    }

    public List<Session> getSessions() {
        return this.zzhgg;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzdvq, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzdvq), Long.valueOf(this.zzgzz)});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("startTimeMillis", Long.valueOf(this.zzdvq)).zzg("endTimeMillis", Long.valueOf(this.zzgzz)).zzg("dataSources", this.zzhgf).zzg("dateTypes", this.zzgzy).zzg("sessions", this.zzhgg).zzg("deleteAllData", Boolean.valueOf(this.zzhgh)).zzg("deleteAllSessions", Boolean.valueOf(this.zzhgi)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzdvq);
        zzbfp.zza(parcel, 2, this.zzgzz);
        zzbfp.zzc(parcel, 3, getDataSources(), false);
        zzbfp.zzc(parcel, 4, getDataTypes(), false);
        zzbfp.zzc(parcel, 5, getSessions(), false);
        zzbfp.zza(parcel, 6, this.zzhgh);
        zzbfp.zza(parcel, 7, this.zzhgi);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zza(parcel, 8, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zzai(parcel, zze);
    }
}
