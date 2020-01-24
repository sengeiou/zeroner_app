package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.zzt;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.zzao;

final class zzbzr extends zzbwm {
    private /* synthetic */ SensorRequest zzgzo;
    private /* synthetic */ zzt zzhfl;
    private /* synthetic */ PendingIntent zzhfm;

    zzbzr(zzbzp zzbzp, GoogleApiClient googleApiClient, SensorRequest sensorRequest, zzt zzt, PendingIntent pendingIntent) {
        this.zzgzo = sensorRequest;
        this.zzhfl = zzt;
        this.zzhfm = pendingIntent;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxs) ((zzbwh) zzb).zzakn()).zza(new zzao(this.zzgzo, this.zzhfl, this.zzhfm, new zzcac(this)));
    }

    /* access modifiers changed from: protected */
    public final Status zzab(Status status) {
        return status;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return zzb(status);
    }
}
