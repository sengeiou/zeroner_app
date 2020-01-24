package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.location.GeofencingRequest;

final class zzcer extends zzcet {
    private /* synthetic */ PendingIntent zzhfb;
    private /* synthetic */ GeofencingRequest zzilk;

    zzcer(zzceq zzceq, GoogleApiClient googleApiClient, GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        this.zzilk = geofencingRequest;
        this.zzhfb = pendingIntent;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zza(this.zzilk, this.zzhfb, (zzn<Status>) this);
    }
}
