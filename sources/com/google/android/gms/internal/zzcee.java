package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcee extends zzcem {
    private /* synthetic */ boolean zzilg;

    zzcee(zzceb zzceb, GoogleApiClient googleApiClient, boolean z) {
        this.zzilg = z;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zzbj(this.zzilg);
        setResult(Status.zzfni);
    }
}
