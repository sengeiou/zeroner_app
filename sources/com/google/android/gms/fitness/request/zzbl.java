package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;

public final class zzbl extends zzbfm {
    public static final Creator<zzbl> CREATOR = new zzbm();
    private final int zzeck;
    private final String zzhga;
    private final zzbyf zzhgc;

    zzbl(int i, String str, IBinder iBinder) {
        this.zzeck = i;
        this.zzhga = str;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    public zzbl(String str, zzbyf zzbyf) {
        this.zzeck = 5;
        this.zzhga = str;
        this.zzhgc = zzbyf;
    }

    public final String toString() {
        return String.format("UnclaimBleDeviceRequest{%s}", new Object[]{this.zzhga});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzhga, false);
        zzbfp.zza(parcel, 3, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
