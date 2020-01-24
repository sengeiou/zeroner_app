package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;

public final class zzaa extends zzbfm {
    public static final Creator<zzaa> CREATOR = new zzab();
    private final int zzeck;
    private final zzbyf zzhgc;

    zzaa(int i, IBinder iBinder) {
        this.zzeck = i;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    public zzaa(zzbyf zzbyf) {
        this.zzeck = 2;
        this.zzhgc = zzbyf;
    }

    public final String toString() {
        return String.format("DisableFitRequest", new Object[0]);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
