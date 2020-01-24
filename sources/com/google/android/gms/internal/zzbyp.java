package com.google.android.gms.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.fitness.result.BleDevicesResult;

final class zzbyp extends zzcah {
    private final zzn<BleDevicesResult> zzgbw;

    private zzbyp(zzn<BleDevicesResult> zzn) {
        this.zzgbw = zzn;
    }

    /* synthetic */ zzbyp(zzn zzn, zzbyj zzbyj) {
        this(zzn);
    }

    public final void zza(BleDevicesResult bleDevicesResult) {
        this.zzgbw.setResult(bleDevicesResult);
    }
}
