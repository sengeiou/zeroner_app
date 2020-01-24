package com.google.android.gms.internal;

import android.location.Location;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.zzt;

final class zzcfi extends zzt {
    private final zzci<LocationListener> zzfus;

    zzcfi(zzci<LocationListener> zzci) {
        this.zzfus = zzci;
    }

    public final synchronized void onLocationChanged(Location location) {
        this.zzfus.zza(new zzcfj(this, location));
    }

    public final synchronized void release() {
        this.zzfus.clear();
    }
}
