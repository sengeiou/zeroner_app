package com.google.android.gms.internal;

import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

final class zzceh extends zzcem {
    private /* synthetic */ LocationRequest zzild;
    private /* synthetic */ LocationListener zzile;
    private /* synthetic */ Looper zzili;

    zzceh(zzceb zzceb, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        this.zzild = locationRequest;
        this.zzile = locationListener;
        this.zzili = looper;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzcfk) zzb).zza(this.zzild, zzcm.zzb(this.zzile, zzcgc.zzb(this.zzili), LocationListener.class.getSimpleName()), (zzceu) new zzcen(this));
    }
}
