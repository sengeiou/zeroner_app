package com.google.android.gms.maps;

import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.internal.zzah;
import com.google.android.gms.maps.model.RuntimeRemoteException;

final class zzm implements OnLocationChangedListener {
    private /* synthetic */ zzah zzirm;

    zzm(zzl zzl, zzah zzah) {
        this.zzirm = zzah;
    }

    public final void onLocationChanged(Location location) {
        try {
            this.zzirm.zzd(location);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
