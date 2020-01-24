package com.google.android.gms.maps;

import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.internal.zzi;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.internal.zzp;

final class zzg extends zzi {
    private /* synthetic */ InfoWindowAdapter zzirg;

    zzg(GoogleMap googleMap, InfoWindowAdapter infoWindowAdapter) {
        this.zzirg = infoWindowAdapter;
    }

    public final IObjectWrapper zzh(zzp zzp) {
        return zzn.zzz(this.zzirg.getInfoWindow(new Marker(zzp)));
    }

    public final IObjectWrapper zzi(zzp zzp) {
        return zzn.zzz(this.zzirg.getInfoContents(new Marker(zzp)));
    }
}
