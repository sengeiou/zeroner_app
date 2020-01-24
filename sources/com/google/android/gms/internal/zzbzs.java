package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.zzt;
import com.google.android.gms.fitness.request.zzar;

final class zzbzs extends zzbwm {
    private /* synthetic */ PendingIntent zzhfb;
    private /* synthetic */ zzt zzhfn;

    zzbzs(zzbzp zzbzp, GoogleApiClient googleApiClient, zzt zzt, PendingIntent pendingIntent) {
        this.zzhfn = zzt;
        this.zzhfb = pendingIntent;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxs) ((zzbwh) zzb).zzakn()).zza(new zzar(this.zzhfn, this.zzhfb, new zzcac(this)));
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
