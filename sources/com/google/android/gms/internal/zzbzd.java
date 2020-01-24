package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.zzw;

final class zzbzd extends zzbwa {
    private /* synthetic */ PendingIntent zzhfb;

    zzbzd(zzbyy zzbyy, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        this.zzhfb = pendingIntent;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxo) ((zzbvv) zzb).zzakn()).zza(new zzw(this.zzhfb, new zzcac(this)));
    }
}
