package com.google.android.gms.fitness.request;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.fitness.data.BleDevice;

final class zzb implements zzcl<BleScanCallback> {
    private /* synthetic */ BleDevice zzhfx;

    zzb(zza zza, BleDevice bleDevice) {
        this.zzhfx = bleDevice;
    }

    public final void zzahz() {
    }

    public final /* synthetic */ void zzu(Object obj) {
        ((BleScanCallback) obj).onDeviceFound(this.zzhfx);
    }
}
