package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbxz;
import com.google.android.gms.internal.zzbya;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SessionReadRequest extends zzbfm {
    public static final Creator<SessionReadRequest> CREATOR = new zzaw();
    private final String mSessionId;
    private final long zzdvq;
    private final int zzeck;
    private final List<DataType> zzgzy;
    private final long zzgzz;
    private final List<DataSource> zzhgf;
    private final boolean zzhgq;
    private final String zzhhx;
    private boolean zzhhy;
    private final List<String> zzhhz;
    private final zzbxz zzhia;

    public static class Builder {
        /* access modifiers changed from: private */
        public String mSessionId;
        /* access modifiers changed from: private */
        public long zzdvq = 0;
        /* access modifiers changed from: private */
        public List<DataType> zzgzy = new ArrayList();
        /* access modifiers changed from: private */
        public long zzgzz = 0;
        /* access modifiers changed from: private */
        public List<DataSource> zzhgf = new ArrayList();
        /* access modifiers changed from: private */
        public boolean zzhgq = false;
        /* access modifiers changed from: private */
        public String zzhhx;
        /* access modifiers changed from: private */
        public boolean zzhhy = false;
        /* access modifiers changed from: private */
        public List<String> zzhhz = new ArrayList();

        public SessionReadRequest build() {
            zzbq.zzb(this.zzdvq > 0, "Invalid start time: %s", Long.valueOf(this.zzdvq));
            zzbq.zzb(this.zzgzz > 0 && this.zzgzz > this.zzdvq, "Invalid end time: %s", Long.valueOf(this.zzgzz));
            return new SessionReadRequest(this);
        }

        public Builder enableServerQueries() {
            this.zzhgq = true;
            return this;
        }

        public Builder excludePackage(String str) {
            zzbq.checkNotNull(str, "Attempting to use a null package name");
            if (!this.zzhhz.contains(str)) {
                this.zzhhz.add(str);
            }
            return this;
        }

        public Builder read(DataSource dataSource) {
            zzbq.checkNotNull(dataSource, "Attempting to add a null data source");
            if (!this.zzhgf.contains(dataSource)) {
                this.zzhgf.add(dataSource);
            }
            return this;
        }

        public Builder read(DataType dataType) {
            zzbq.checkNotNull(dataType, "Attempting to use a null data type");
            if (!this.zzgzy.contains(dataType)) {
                this.zzgzy.add(dataType);
            }
            return this;
        }

        public Builder readSessionsFromAllApps() {
            this.zzhhy = true;
            return this;
        }

        public Builder setSessionId(String str) {
            this.mSessionId = str;
            return this;
        }

        public Builder setSessionName(String str) {
            this.zzhhx = str;
            return this;
        }

        public Builder setTimeInterval(long j, long j2, TimeUnit timeUnit) {
            this.zzdvq = timeUnit.toMillis(j);
            this.zzgzz = timeUnit.toMillis(j2);
            return this;
        }
    }

    SessionReadRequest(int i, String str, String str2, long j, long j2, List<DataType> list, List<DataSource> list2, boolean z, boolean z2, List<String> list3, IBinder iBinder) {
        this.zzeck = i;
        this.zzhhx = str;
        this.mSessionId = str2;
        this.zzdvq = j;
        this.zzgzz = j2;
        this.zzgzy = list;
        this.zzhgf = list2;
        this.zzhhy = z;
        this.zzhgq = z2;
        this.zzhhz = list3;
        this.zzhia = zzbya.zzay(iBinder);
    }

    private SessionReadRequest(Builder builder) {
        this(builder.zzhhx, builder.mSessionId, builder.zzdvq, builder.zzgzz, builder.zzgzy, builder.zzhgf, builder.zzhhy, builder.zzhgq, builder.zzhhz, null);
    }

    public SessionReadRequest(SessionReadRequest sessionReadRequest, zzbxz zzbxz) {
        this(sessionReadRequest.zzhhx, sessionReadRequest.mSessionId, sessionReadRequest.zzdvq, sessionReadRequest.zzgzz, sessionReadRequest.zzgzy, sessionReadRequest.zzhgf, sessionReadRequest.zzhhy, sessionReadRequest.zzhgq, sessionReadRequest.zzhhz, zzbxz);
    }

    private SessionReadRequest(String str, String str2, long j, long j2, List<DataType> list, List<DataSource> list2, boolean z, boolean z2, List<String> list3, zzbxz zzbxz) {
        this(5, str, str2, j, j2, list, list2, z, z2, list3, zzbxz == null ? null : zzbxz.asBinder());
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof SessionReadRequest)) {
                return false;
            }
            SessionReadRequest sessionReadRequest = (SessionReadRequest) obj;
            if (!(zzbg.equal(this.zzhhx, sessionReadRequest.zzhhx) && this.mSessionId.equals(sessionReadRequest.mSessionId) && this.zzdvq == sessionReadRequest.zzdvq && this.zzgzz == sessionReadRequest.zzgzz && zzbg.equal(this.zzgzy, sessionReadRequest.zzgzy) && zzbg.equal(this.zzhgf, sessionReadRequest.zzhgf) && this.zzhhy == sessionReadRequest.zzhhy && this.zzhhz.equals(sessionReadRequest.zzhhz) && this.zzhgq == sessionReadRequest.zzhgq)) {
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

    public List<String> getExcludedPackages() {
        return this.zzhhz;
    }

    public String getSessionId() {
        return this.mSessionId;
    }

    public String getSessionName() {
        return this.zzhhx;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzdvq, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhhx, this.mSessionId, Long.valueOf(this.zzdvq), Long.valueOf(this.zzgzz)});
    }

    public boolean includeSessionsFromAllApps() {
        return this.zzhhy;
    }

    public String toString() {
        return zzbg.zzx(this).zzg("sessionName", this.zzhhx).zzg("sessionId", this.mSessionId).zzg("startTimeMillis", Long.valueOf(this.zzdvq)).zzg("endTimeMillis", Long.valueOf(this.zzgzz)).zzg("dataTypes", this.zzgzy).zzg("dataSources", this.zzhgf).zzg("sessionsFromAllApps", Boolean.valueOf(this.zzhhy)).zzg("excludedPackages", this.zzhhz).zzg("useServer", Boolean.valueOf(this.zzhgq)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, getSessionName(), false);
        zzbfp.zza(parcel, 2, getSessionId(), false);
        zzbfp.zza(parcel, 3, this.zzdvq);
        zzbfp.zza(parcel, 4, this.zzgzz);
        zzbfp.zzc(parcel, 5, getDataTypes(), false);
        zzbfp.zzc(parcel, 6, getDataSources(), false);
        zzbfp.zza(parcel, 7, this.zzhhy);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zza(parcel, 8, this.zzhgq);
        zzbfp.zzb(parcel, 9, getExcludedPackages(), false);
        zzbfp.zza(parcel, 10, this.zzhia == null ? null : this.zzhia.asBinder(), false);
        zzbfp.zzai(parcel, zze);
    }
}
