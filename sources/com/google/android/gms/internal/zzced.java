package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.location.LocationCallback;

final class zzced extends zzcem {
    private /* synthetic */ LocationCallback zzilf;

    zzced(zzceb zzceb, GoogleApiClient googleApiClient, LocationCallback locationCallback) {
        this.zzilf = locationCallback;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zzb(zzcm.zzb(this.zzilf, LocationCallback.class.getSimpleName()), new zzcen(this));
    }
}
