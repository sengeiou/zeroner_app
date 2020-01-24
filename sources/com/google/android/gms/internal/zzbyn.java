package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.zzbl;

final class zzbyn extends zzbvj {
    private /* synthetic */ String zzheq;

    zzbyn(zzbyi zzbyi, GoogleApiClient googleApiClient, String str) {
        this.zzheq = str;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxi) ((zzbve) zzb).zzakn()).zza(new zzbl(this.zzheq, new zzcac(this)));
    }
}
