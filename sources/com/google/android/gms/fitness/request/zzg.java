package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbwt;
import com.google.android.gms.internal.zzbwu;

public final class zzg extends zzbfm {
    public static final Creator<zzg> CREATOR = new zzh();
    private final int versionCode;
    private DataType zzgzg;
    private final zzbwt zzhgd;
    private final boolean zzhge;

    zzg(int i, IBinder iBinder, DataType dataType, boolean z) {
        this.versionCode = i;
        this.zzhgd = zzbwu.zzas(iBinder);
        this.zzgzg = dataType;
        this.zzhge = z;
    }

    public zzg(zzbwt zzbwt, DataType dataType, boolean z) {
        this.versionCode = 3;
        this.zzhgd = zzbwt;
        this.zzgzg = dataType;
        this.zzhge = z;
    }

    public final String toString() {
        String str = "DailyTotalRequest{%s}";
        Object[] objArr = new Object[1];
        objArr[0] = this.zzgzg == null ? "null" : this.zzgzg.zzaqp();
        return String.format(str, objArr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhgd.asBinder(), false);
        zzbfp.zza(parcel, 2, (Parcelable) this.zzgzg, i, false);
        zzbfp.zza(parcel, 4, this.zzhge);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, zze);
    }
}
