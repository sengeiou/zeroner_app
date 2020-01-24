package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.request.zze;

final class zzbym extends zzbvj {
    private /* synthetic */ BleDevice zzher;

    zzbym(zzbyi zzbyi, GoogleApiClient googleApiClient, BleDevice bleDevice) {
        this.zzher = bleDevice;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxi) ((zzbve) zzb).zzakn()).zza(new zze(this.zzher.getAddress(), this.zzher, new zzcac(this)));
    }
}
