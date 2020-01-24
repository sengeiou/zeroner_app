package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.request.zzbj;

final class zzbzl extends zzbwg {
    private /* synthetic */ Subscription zzhfi;

    zzbzl(zzbzi zzbzi, GoogleApiClient googleApiClient, Subscription subscription) {
        this.zzhfi = subscription;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxq) ((zzbwb) zzb).zzakn()).zza(new zzbj(this.zzhfi, false, new zzcac(this)));
    }
}
