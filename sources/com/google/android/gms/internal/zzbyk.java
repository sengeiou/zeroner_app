package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.zzbh;

final class zzbyk extends zzbvj {
    private /* synthetic */ BleScanCallback zzhep;

    zzbyk(zzbyi zzbyi, GoogleApiClient googleApiClient, BleScanCallback bleScanCallback) {
        this.zzhep = bleScanCallback;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxi) ((zzbve) zzb).zzakn()).zza(new zzbh(this.zzhep, (zzbyf) new zzcac(this)));
    }
}
