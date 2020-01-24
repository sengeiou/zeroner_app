package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzcel extends zzcem {
    private /* synthetic */ PendingIntent zzikr;

    zzcel(zzceb zzceb, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        this.zzikr = pendingIntent;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zza(this.zzikr, (zzceu) new zzcen(this));
    }
}
