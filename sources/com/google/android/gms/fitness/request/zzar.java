package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.zzt;
import com.google.android.gms.fitness.data.zzu;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;

public final class zzar extends zzbfm {
    public static final Creator<zzar> CREATOR = new zzas();
    private final int zzeck;
    private final PendingIntent zzeeo;
    private final zzbyf zzhgc;
    private final zzt zzhhj;

    zzar(int i, IBinder iBinder, PendingIntent pendingIntent, IBinder iBinder2) {
        this.zzeck = i;
        this.zzhhj = iBinder == null ? null : zzu.zzar(iBinder);
        this.zzeeo = pendingIntent;
        this.zzhgc = zzbyg.zzba(iBinder2);
    }

    public zzar(zzt zzt, PendingIntent pendingIntent, zzbyf zzbyf) {
        this.zzeck = 4;
        this.zzhhj = zzt;
        this.zzeeo = pendingIntent;
        this.zzhgc = zzbyf;
    }

    public final String toString() {
        return String.format("SensorUnregistrationRequest{%s}", new Object[]{this.zzhhj});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhhj == null ? null : this.zzhhj.asBinder(), false);
        zzbfp.zza(parcel, 2, (Parcelable) this.zzeeo, i, false);
        if (this.zzhgc != null) {
            iBinder = this.zzhgc.asBinder();
        }
        zzbfp.zza(parcel, 3, iBinder, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
