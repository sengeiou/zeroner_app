package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;

public final class zzbh extends zzbfm {
    public static final Creator<zzbh> CREATOR = new zzbi();
    private final int zzeck;
    private final zzbyf zzhgc;
    private final zzae zzhid;

    zzbh(int i, IBinder iBinder, IBinder iBinder2) {
        zzae zzag;
        this.zzeck = i;
        if (iBinder == null) {
            zzag = null;
        } else {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.request.IBleScanCallback");
            zzag = queryLocalInterface instanceof zzae ? (zzae) queryLocalInterface : new zzag(iBinder);
        }
        this.zzhid = zzag;
        this.zzhgc = zzbyg.zzba(iBinder2);
    }

    public zzbh(BleScanCallback bleScanCallback, zzbyf zzbyf) {
        this((zzae) zzd.zzaqt().zzb(bleScanCallback), zzbyf);
    }

    public zzbh(zzae zzae, zzbyf zzbyf) {
        this.zzeck = 3;
        this.zzhid = zzae;
        this.zzhgc = zzbyf;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhid.asBinder(), false);
        zzbfp.zza(parcel, 2, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
