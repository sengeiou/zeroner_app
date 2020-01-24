package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.location.LocationListener;

final class zzcek extends zzcem {
    private /* synthetic */ LocationListener zzile;

    zzcek(zzceb zzceb, GoogleApiClient googleApiClient, LocationListener locationListener) {
        this.zzile = locationListener;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zza(zzcm.zzb(this.zzile, LocationListener.class.getSimpleName()), (zzceu) new zzcen(this));
    }
}
