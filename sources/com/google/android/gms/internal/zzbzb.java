package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.DataUpdateRequest;

final class zzbzb extends zzbwa {
    private /* synthetic */ DataUpdateRequest zzhez;

    zzbzb(zzbyy zzbyy, GoogleApiClient googleApiClient, DataUpdateRequest dataUpdateRequest) {
        this.zzhez = dataUpdateRequest;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxo) ((zzbvv) zzb).zzakn()).zza(new DataUpdateRequest(this.zzhez, (IBinder) new zzcac(this)));
    }
}
