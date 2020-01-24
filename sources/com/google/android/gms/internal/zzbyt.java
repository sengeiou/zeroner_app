package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.zzaa;

final class zzbyt extends zzbvp {
    zzbyt(zzbyq zzbyq, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxk) ((zzbvk) zzb).zzakn()).zza(new zzaa(new zzcac(this)));
    }
}
