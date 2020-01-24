package com.iwown.ble_module.zg_ble.callback;

import com.iwown.ble_module.zg_ble.exception.BleException;

public abstract class BleMtuChangedCallback extends BleBaseCallback {
    public abstract void onMtuChanged(int i);

    public abstract void onSetMTUFailure(BleException bleException);
}
