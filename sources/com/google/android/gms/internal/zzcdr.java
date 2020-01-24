package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcdr extends zzcds {
    private /* synthetic */ PendingIntent zzikr;

    zzcdr(zzcdp zzcdp, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        this.zzikr = pendingIntent;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zzc(this.zzikr);
        setResult(Status.zzfni);
    }
}
