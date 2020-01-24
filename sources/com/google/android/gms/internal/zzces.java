package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.location.zzag;

final class zzces extends zzcet {
    private /* synthetic */ zzag zzill;

    zzces(zzceq zzceq, GoogleApiClient googleApiClient, zzag zzag) {
        this.zzill = zzag;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zza(this.zzill, (zzn<Status>) this);
    }
}
