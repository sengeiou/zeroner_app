package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.DataDeleteRequest;

final class zzbza extends zzbwa {
    private /* synthetic */ DataDeleteRequest zzhey;

    zzbza(zzbyy zzbyy, GoogleApiClient googleApiClient, DataDeleteRequest dataDeleteRequest) {
        this.zzhey = dataDeleteRequest;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxo) ((zzbvv) zzb).zzakn()).zza(new DataDeleteRequest(this.zzhey, (zzbyf) new zzcac(this)));
    }
}
