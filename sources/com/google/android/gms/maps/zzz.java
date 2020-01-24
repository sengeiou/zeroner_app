package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.internal.zzao;
import com.google.android.gms.maps.model.LatLng;

final class zzz extends zzao {
    private /* synthetic */ OnMapLongClickListener zzirz;

    zzz(GoogleMap googleMap, OnMapLongClickListener onMapLongClickListener) {
        this.zzirz = onMapLongClickListener;
    }

    public final void onMapLongClick(LatLng latLng) {
        this.zzirz.onMapLongClick(latLng);
    }
}
