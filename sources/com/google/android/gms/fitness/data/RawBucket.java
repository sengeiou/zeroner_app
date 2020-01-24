package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@KeepName
public final class RawBucket extends zzbfm {
    public static final Creator<RawBucket> CREATOR = new zzy();
    public final long zzdvq;
    private int zzeck;
    public final Session zzgzp;
    public final long zzgzz;
    public final List<RawDataSet> zzhab;
    public final int zzhac;
    public final boolean zzhad;
    public final int zzhdk;

    public RawBucket(int i, long j, long j2, Session session, int i2, List<RawDataSet> list, int i3, boolean z) {
        this.zzeck = i;
        this.zzdvq = j;
        this.zzgzz = j2;
        this.zzgzp = session;
        this.zzhdk = i2;
        this.zzhab = list;
        this.zzhac = i3;
        this.zzhad = z;
    }

    public RawBucket(Bucket bucket, List<DataSource> list, List<DataType> list2) {
        this.zzeck = 2;
        this.zzdvq = bucket.getStartTime(TimeUnit.MILLISECONDS);
        this.zzgzz = bucket.getEndTime(TimeUnit.MILLISECONDS);
        this.zzgzp = bucket.getSession();
        this.zzhdk = bucket.zzaqc();
        this.zzhac = bucket.getBucketType();
        this.zzhad = bucket.zzaqd();
        List<DataSet> dataSets = bucket.getDataSets();
        this.zzhab = new ArrayList(dataSets.size());
        for (DataSet rawDataSet : dataSets) {
            this.zzhab.add(new RawDataSet(rawDataSet, list, list2));
        }
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof RawBucket)) {
                return false;
            }
            RawBucket rawBucket = (RawBucket) obj;
            if (!(this.zzdvq == rawBucket.zzdvq && this.zzgzz == rawBucket.zzgzz && this.zzhdk == rawBucket.zzhdk && zzbg.equal(this.zzhab, rawBucket.zzhab) && this.zzhac == rawBucket.zzhac && this.zzhad == rawBucket.zzhad)) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzdvq), Long.valueOf(this.zzgzz), Integer.valueOf(this.zzhac)});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("startTime", Long.valueOf(this.zzdvq)).zzg("endTime", Long.valueOf(this.zzgzz)).zzg(EnvConsts.ACTIVITY_MANAGER_SRVNAME, Integer.valueOf(this.zzhdk)).zzg("dataSets", this.zzhab).zzg("bucketType", Integer.valueOf(this.zzhac)).zzg("serverHasMoreData", Boolean.valueOf(this.zzhad)).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzdvq);
        zzbfp.zza(parcel, 2, this.zzgzz);
        zzbfp.zza(parcel, 3, (Parcelable) this.zzgzp, i, false);
        zzbfp.zzc(parcel, 4, this.zzhdk);
        zzbfp.zzc(parcel, 5, this.zzhab, false);
        zzbfp.zzc(parcel, 6, this.zzhac);
        zzbfp.zza(parcel, 7, this.zzhad);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
