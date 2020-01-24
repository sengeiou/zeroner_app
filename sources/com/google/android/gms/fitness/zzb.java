package com.google.android.gms.fitness;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.result.BleDevicesResult;

final /* synthetic */ class zzb implements zzbo {
    static final zzbo zzgnw = new zzb();

    private zzb() {
    }

    public final Object zzb(Result result) {
        return ((BleDevicesResult) result).getClaimedBleDevices();
    }
}
