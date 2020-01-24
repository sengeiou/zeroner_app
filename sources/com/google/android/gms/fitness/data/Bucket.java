package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.zza;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Bucket extends zzbfm implements ReflectedParcelable {
    public static final Creator<Bucket> CREATOR = new zze();
    public static final int TYPE_ACTIVITY_SEGMENT = 4;
    public static final int TYPE_ACTIVITY_TYPE = 3;
    public static final int TYPE_SESSION = 2;
    public static final int TYPE_TIME = 1;
    private final long zzdvq;
    private final int zzeck;
    private final Session zzgzp;
    private final long zzgzz;
    private final int zzhaa;
    private final List<DataSet> zzhab;
    private final int zzhac;
    private boolean zzhad;

    Bucket(int i, long j, long j2, Session session, int i2, List<DataSet> list, int i3, boolean z) {
        this.zzhad = false;
        this.zzeck = i;
        this.zzdvq = j;
        this.zzgzz = j2;
        this.zzgzp = session;
        this.zzhaa = i2;
        this.zzhab = list;
        this.zzhac = i3;
        this.zzhad = z;
    }

    public Bucket(RawBucket rawBucket, List<DataSource> list) {
        this(2, rawBucket.zzdvq, rawBucket.zzgzz, rawBucket.zzgzp, rawBucket.zzhdk, zza(rawBucket.zzhab, list), rawBucket.zzhac, rawBucket.zzhad);
    }

    private static List<DataSet> zza(Collection<RawDataSet> collection, List<DataSource> list) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (RawDataSet dataSet : collection) {
            arrayList.add(new DataSet(dataSet, list));
        }
        return arrayList;
    }

    public static String zzda(int i) {
        switch (i) {
            case 0:
                return "unknown";
            case 1:
                return "time";
            case 2:
                return "session";
            case 3:
                return "type";
            case 4:
                return "segment";
            default:
                return "bug";
        }
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof Bucket)) {
                return false;
            }
            Bucket bucket = (Bucket) obj;
            if (!(this.zzdvq == bucket.zzdvq && this.zzgzz == bucket.zzgzz && this.zzhaa == bucket.zzhaa && zzbg.equal(this.zzhab, bucket.zzhab) && this.zzhac == bucket.zzhac && this.zzhad == bucket.zzhad)) {
                return false;
            }
        }
        return true;
    }

    public String getActivity() {
        return zza.getName(this.zzhaa);
    }

    public int getBucketType() {
        return this.zzhac;
    }

    public DataSet getDataSet(DataType dataType) {
        for (DataSet dataSet : this.zzhab) {
            if (dataSet.getDataType().equals(dataType)) {
                return dataSet;
            }
        }
        return null;
    }

    public List<DataSet> getDataSets() {
        return this.zzhab;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzgzz, TimeUnit.MILLISECONDS);
    }

    public Session getSession() {
        return this.zzgzp;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzdvq, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzdvq), Long.valueOf(this.zzgzz), Integer.valueOf(this.zzhaa), Integer.valueOf(this.zzhac)});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("startTime", Long.valueOf(this.zzdvq)).zzg("endTime", Long.valueOf(this.zzgzz)).zzg(EnvConsts.ACTIVITY_MANAGER_SRVNAME, Integer.valueOf(this.zzhaa)).zzg("dataSets", this.zzhab).zzg("bucketType", zzda(this.zzhac)).zzg("serverHasMoreData", Boolean.valueOf(this.zzhad)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzdvq);
        zzbfp.zza(parcel, 2, this.zzgzz);
        zzbfp.zza(parcel, 3, (Parcelable) getSession(), i, false);
        zzbfp.zzc(parcel, 4, this.zzhaa);
        zzbfp.zzc(parcel, 5, getDataSets(), false);
        zzbfp.zzc(parcel, 6, getBucketType());
        zzbfp.zza(parcel, 7, zzaqd());
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }

    public final boolean zza(Bucket bucket) {
        return this.zzdvq == bucket.zzdvq && this.zzgzz == bucket.zzgzz && this.zzhaa == bucket.zzhaa && this.zzhac == bucket.zzhac;
    }

    public final int zzaqc() {
        return this.zzhaa;
    }

    public final boolean zzaqd() {
        if (this.zzhad) {
            return true;
        }
        for (DataSet zzaqd : this.zzhab) {
            if (zzaqd.zzaqd()) {
                return true;
            }
        }
        return false;
    }
}
