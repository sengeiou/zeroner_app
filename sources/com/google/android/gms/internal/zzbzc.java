package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest;

final class zzbzc extends zzbwa {
    private /* synthetic */ DataUpdateListenerRegistrationRequest zzhfa;

    zzbzc(zzbyy zzbyy, GoogleApiClient googleApiClient, DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest) {
        this.zzhfa = dataUpdateListenerRegistrationRequest;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxo) ((zzbvv) zzb).zzakn()).zza(new DataUpdateListenerRegistrationRequest(this.zzhfa, (IBinder) new zzcac(this)));
    }
}
