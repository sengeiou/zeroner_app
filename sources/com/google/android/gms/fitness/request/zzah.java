package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzcag;
import com.google.android.gms.internal.zzcah;

public final class zzah extends zzbfm {
    public static final Creator<zzah> CREATOR = new zzai();
    private final int zzeck;
    private final zzcag zzhhe;

    zzah(int i, IBinder iBinder) {
        this.zzeck = i;
        this.zzhhe = zzcah.zzbb(iBinder);
    }

    public zzah(zzcag zzcag) {
        this.zzeck = 2;
        this.zzhhe = zzcag;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhhe.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
