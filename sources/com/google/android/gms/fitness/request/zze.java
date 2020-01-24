package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;

public final class zze extends zzbfm {
    public static final Creator<zze> CREATOR = new zzf();
    private final int zzeck;
    private final String zzhga;
    private final BleDevice zzhgb;
    private final zzbyf zzhgc;

    zze(int i, String str, BleDevice bleDevice, IBinder iBinder) {
        this.zzeck = i;
        this.zzhga = str;
        this.zzhgb = bleDevice;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    public zze(String str, BleDevice bleDevice, zzbyf zzbyf) {
        this.zzeck = 4;
        this.zzhga = str;
        this.zzhgb = bleDevice;
        this.zzhgc = zzbyf;
    }

    public final String toString() {
        return String.format("ClaimBleDeviceRequest{%s %s}", new Object[]{this.zzhga, this.zzhgb});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhga, false);
        zzbfp.zza(parcel, 2, (Parcelable) this.zzhgb, i, false);
        zzbfp.zza(parcel, 3, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
