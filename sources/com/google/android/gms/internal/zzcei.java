package com.google.android.gms.internal;

import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

final class zzcei extends zzcem {
    private /* synthetic */ LocationRequest zzild;
    private /* synthetic */ LocationCallback zzilf;
    private /* synthetic */ Looper zzili;

    zzcei(zzceb zzceb, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationCallback locationCallback, Looper looper) {
        this.zzild = locationRequest;
        this.zzilf = locationCallback;
        this.zzili = looper;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zza(zzcfo.zza(this.zzild), zzcm.zzb(this.zzilf, zzcgc.zzb(this.zzili), LocationCallback.class.getSimpleName()), (zzceu) new zzcen(this));
    }
}
