package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.zzbq;

final class zzah extends zzbq {
    private /* synthetic */ OnStreetViewPanoramaReadyCallback zzitl;

    zzah(zza zza, OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback) {
        this.zzitl = onStreetViewPanoramaReadyCallback;
    }

    public final void zza(IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate) throws RemoteException {
        this.zzitl.onStreetViewPanoramaReady(new StreetViewPanorama(iStreetViewPanoramaDelegate));
    }
}
