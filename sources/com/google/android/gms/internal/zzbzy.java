package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.zzax;

final class zzbzy extends zzbws {
    private /* synthetic */ PendingIntent zzhfm;
    private /* synthetic */ int zzhfs = 0;

    zzbzy(zzbzt zzbzt, GoogleApiClient googleApiClient, PendingIntent pendingIntent, int i) {
        this.zzhfm = pendingIntent;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxu) ((zzbwn) zzb).zzakn()).zza(new zzax(this.zzhfm, new zzcac(this), this.zzhfs));
    }
}
