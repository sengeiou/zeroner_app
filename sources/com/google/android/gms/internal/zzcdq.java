package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcdq extends zzcds {
    private /* synthetic */ long zzikq;
    private /* synthetic */ PendingIntent zzikr;

    zzcdq(zzcdp zzcdp, GoogleApiClient googleApiClient, long j, PendingIntent pendingIntent) {
        this.zzikq = j;
        this.zzikr = pendingIntent;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zza(this.zzikq, this.zzikr);
        setResult(Status.zzfni);
    }
}
