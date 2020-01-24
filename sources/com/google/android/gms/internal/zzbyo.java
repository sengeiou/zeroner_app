package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.zzah;
import com.google.android.gms.fitness.result.BleDevicesResult;

final class zzbyo extends zzbvh<BleDevicesResult> {
    zzbyo(zzbyi zzbyi, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxi) ((zzbve) zzb).zzakn()).zza(new zzah(new zzbyp(this, null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return BleDevicesResult.zzac(status);
    }
}
