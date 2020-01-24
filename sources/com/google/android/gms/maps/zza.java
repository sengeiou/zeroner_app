package com.google.android.gms.maps;

import com.google.android.gms.maps.GoogleMap.OnIndoorStateChangeListener;
import com.google.android.gms.maps.internal.zzaa;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.internal.zzj;

final class zza extends zzaa {
    private /* synthetic */ OnIndoorStateChangeListener zzira;

    zza(GoogleMap googleMap, OnIndoorStateChangeListener onIndoorStateChangeListener) {
        this.zzira = onIndoorStateChangeListener;
    }

    public final void onIndoorBuildingFocused() {
        this.zzira.onIndoorBuildingFocused();
    }

    public final void zza(zzj zzj) {
        this.zzira.onIndoorLevelActivated(new IndoorBuilding(zzj));
    }
}
