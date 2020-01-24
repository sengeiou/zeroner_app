package com.google.android.gms.internal;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

final class zzcff implements zzcl<LocationCallback> {
    private /* synthetic */ LocationResult zzilr;

    zzcff(zzcfe zzcfe, LocationResult locationResult) {
        this.zzilr = locationResult;
    }

    public final void zzahz() {
    }

    public final /* synthetic */ void zzu(Object obj) {
        ((LocationCallback) obj).onLocationResult(this.zzilr);
    }
}
