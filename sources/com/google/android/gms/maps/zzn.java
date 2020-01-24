package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnGroundOverlayClickListener;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.internal.zzg;

final class zzn extends zzy {
    private /* synthetic */ OnGroundOverlayClickListener zzirn;

    zzn(GoogleMap googleMap, OnGroundOverlayClickListener onGroundOverlayClickListener) {
        this.zzirn = onGroundOverlayClickListener;
    }

    public final void zza(zzg zzg) {
        this.zzirn.onGroundOverlayClick(new GroundOverlay(zzg));
    }
}
