package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.internal.zzau;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.internal.zzp;

final class zzc extends zzau {
    private /* synthetic */ OnMarkerDragListener zzirc;

    zzc(GoogleMap googleMap, OnMarkerDragListener onMarkerDragListener) {
        this.zzirc = onMarkerDragListener;
    }

    public final void zzb(zzp zzp) {
        this.zzirc.onMarkerDragStart(new Marker(zzp));
    }

    public final void zzc(zzp zzp) {
        this.zzirc.onMarkerDragEnd(new Marker(zzp));
    }

    public final void zzd(zzp zzp) {
        this.zzirc.onMarkerDrag(new Marker(zzp));
    }
}
