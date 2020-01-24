package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

final class zzcej extends zzcem {
    private /* synthetic */ PendingIntent zzikr;
    private /* synthetic */ LocationRequest zzild;

    zzcej(zzceb zzceb, GoogleApiClient googleApiClient, LocationRequest locationRequest, PendingIntent pendingIntent) {
        this.zzild = locationRequest;
        this.zzikr = pendingIntent;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zza(this.zzild, this.zzikr, (zzceu) new zzcen(this));
    }
}
