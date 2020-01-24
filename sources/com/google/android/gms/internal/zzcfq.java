package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.location.zzp;
import com.google.android.gms.location.zzq;
import com.google.android.gms.location.zzs;
import com.google.android.gms.location.zzt;

public final class zzcfq extends zzbfm {
    public static final Creator<zzcfq> CREATOR = new zzcfr();
    private PendingIntent zzeeo;
    private int zzikz;
    private zzceu zzilc;
    private zzcfo zzima;
    private zzs zzimb;
    private zzp zzimc;

    zzcfq(int i, zzcfo zzcfo, IBinder iBinder, PendingIntent pendingIntent, IBinder iBinder2, IBinder iBinder3) {
        zzceu zzceu = null;
        this.zzikz = i;
        this.zzima = zzcfo;
        this.zzimb = iBinder == null ? null : zzt.zzbe(iBinder);
        this.zzeeo = pendingIntent;
        this.zzimc = iBinder2 == null ? null : zzq.zzbd(iBinder2);
        if (!(iBinder3 == null || iBinder3 == null)) {
            IInterface queryLocalInterface = iBinder3.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
            zzceu = queryLocalInterface instanceof zzceu ? (zzceu) queryLocalInterface : new zzcew(iBinder3);
        }
        this.zzilc = zzceu;
    }

    public static zzcfq zza(zzp zzp, @Nullable zzceu zzceu) {
        return new zzcfq(2, null, null, null, zzp.asBinder(), zzceu != null ? zzceu.asBinder() : null);
    }

    public static zzcfq zza(zzs zzs, @Nullable zzceu zzceu) {
        return new zzcfq(2, null, zzs.asBinder(), null, null, zzceu != null ? zzceu.asBinder() : null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzikz);
        zzbfp.zza(parcel, 2, (Parcelable) this.zzima, i, false);
        zzbfp.zza(parcel, 3, this.zzimb == null ? null : this.zzimb.asBinder(), false);
        zzbfp.zza(parcel, 4, (Parcelable) this.zzeeo, i, false);
        zzbfp.zza(parcel, 5, this.zzimc == null ? null : this.zzimc.asBinder(), false);
        if (this.zzilc != null) {
            iBinder = this.zzilc.asBinder();
        }
        zzbfp.zza(parcel, 6, iBinder, false);
        zzbfp.zzai(parcel, zze);
    }
}
