package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbuu;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@KeepName
public final class RawDataPoint extends zzbfm {
    public static final Creator<RawDataPoint> CREATOR = new zzz();
    private int versionCode;
    public final long zzhdl;
    public final long zzhdm;
    public final Value[] zzhdn;
    public final int zzhdo;
    public final int zzhdp;
    public final long zzhdq;
    public final long zzhdr;

    public RawDataPoint(int i, long j, long j2, Value[] valueArr, int i2, int i3, long j3, long j4) {
        this.versionCode = i;
        this.zzhdl = j;
        this.zzhdm = j2;
        this.zzhdo = i2;
        this.zzhdp = i3;
        this.zzhdq = j3;
        this.zzhdr = j4;
        this.zzhdn = valueArr;
    }

    RawDataPoint(DataPoint dataPoint, List<DataSource> list) {
        this.versionCode = 4;
        this.zzhdl = dataPoint.getTimestamp(TimeUnit.NANOSECONDS);
        this.zzhdm = dataPoint.getStartTime(TimeUnit.NANOSECONDS);
        this.zzhdn = dataPoint.zzaqf();
        this.zzhdo = zzbuu.zza(dataPoint.getDataSource(), list);
        this.zzhdp = zzbuu.zza(dataPoint.zzaqg(), list);
        this.zzhdq = dataPoint.zzaqh();
        this.zzhdr = dataPoint.zzaqi();
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof RawDataPoint)) {
                return false;
            }
            RawDataPoint rawDataPoint = (RawDataPoint) obj;
            if (!(this.zzhdl == rawDataPoint.zzhdl && this.zzhdm == rawDataPoint.zzhdm && Arrays.equals(this.zzhdn, rawDataPoint.zzhdn) && this.zzhdo == rawDataPoint.zzhdo && this.zzhdp == rawDataPoint.zzhdp && this.zzhdq == rawDataPoint.zzhdq)) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzhdl), Long.valueOf(this.zzhdm)});
    }

    public final String toString() {
        return String.format("RawDataPoint{%s@[%s, %s](%d,%d)}", new Object[]{Arrays.toString(this.zzhdn), Long.valueOf(this.zzhdm), Long.valueOf(this.zzhdl), Integer.valueOf(this.zzhdo), Integer.valueOf(this.zzhdp)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhdl);
        zzbfp.zza(parcel, 2, this.zzhdm);
        zzbfp.zza(parcel, 3, (T[]) this.zzhdn, i, false);
        zzbfp.zzc(parcel, 4, this.zzhdo);
        zzbfp.zzc(parcel, 5, this.zzhdp);
        zzbfp.zza(parcel, 6, this.zzhdq);
        zzbfp.zza(parcel, 7, this.zzhdr);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, zze);
    }
}
