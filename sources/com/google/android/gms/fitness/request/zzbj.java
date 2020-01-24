package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;

public final class zzbj extends zzbfm {
    public static final Creator<zzbj> CREATOR = new zzbk();
    private final int zzeck;
    private final zzbyf zzhgc;
    private Subscription zzhif;
    private final boolean zzhig;

    zzbj(int i, Subscription subscription, boolean z, IBinder iBinder) {
        this.zzeck = i;
        this.zzhif = subscription;
        this.zzhig = z;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    public zzbj(Subscription subscription, boolean z, zzbyf zzbyf) {
        this.zzeck = 3;
        this.zzhif = subscription;
        this.zzhig = false;
        this.zzhgc = zzbyf;
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("subscription", this.zzhif).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzhif, i, false);
        zzbfp.zza(parcel, 2, this.zzhig);
        zzbfp.zza(parcel, 3, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
