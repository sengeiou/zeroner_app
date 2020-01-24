package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.StartBleScanRequest;

final class zzbyj extends zzbvj {
    private /* synthetic */ StartBleScanRequest zzheo;

    zzbyj(zzbyi zzbyi, GoogleApiClient googleApiClient, StartBleScanRequest startBleScanRequest) {
        this.zzheo = startBleScanRequest;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxi) ((zzbve) zzb).zzakn()).zza(new StartBleScanRequest(this.zzheo, (zzbyf) new zzcac(this)));
    }
}
