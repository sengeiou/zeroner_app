package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbuu;
import java.util.Arrays;
import java.util.List;

@KeepName
public final class RawDataSet extends zzbfm {
    public static final Creator<RawDataSet> CREATOR = new zzaa();
    private int zzeck;
    public final boolean zzhad;
    public final int zzhdo;
    private int zzhds;
    public final List<RawDataPoint> zzhdt;

    public RawDataSet(int i, int i2, int i3, List<RawDataPoint> list, boolean z) {
        this.zzeck = i;
        this.zzhdo = i2;
        this.zzhds = i3;
        this.zzhdt = list;
        this.zzhad = z;
    }

    public RawDataSet(DataSet dataSet, List<DataSource> list, List<DataType> list2) {
        this.zzeck = 3;
        this.zzhdt = dataSet.zzab(list);
        this.zzhad = dataSet.zzaqd();
        this.zzhdo = zzbuu.zza(dataSet.getDataSource(), list);
        this.zzhds = zzbuu.zza(dataSet.getDataType(), list2);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof RawDataSet)) {
                return false;
            }
            RawDataSet rawDataSet = (RawDataSet) obj;
            if (!(this.zzhdo == rawDataSet.zzhdo && this.zzhad == rawDataSet.zzhad && zzbg.equal(this.zzhdt, rawDataSet.zzhdt))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzhdo)});
    }

    public final String toString() {
        return String.format("RawDataSet{%s@[%s]}", new Object[]{Integer.valueOf(this.zzhdo), this.zzhdt});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzhdo);
        zzbfp.zzc(parcel, 2, this.zzhds);
        zzbfp.zzc(parcel, 3, this.zzhdt, false);
        zzbfp.zza(parcel, 4, this.zzhad);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
