package com.google.android.gms.internal;

import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcef extends zzcem {
    private /* synthetic */ Location zzilh;

    zzcef(zzceb zzceb, GoogleApiClient googleApiClient, Location location) {
        this.zzilh = location;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zzc(this.zzilh);
        setResult(Status.zzfni);
    }
}
