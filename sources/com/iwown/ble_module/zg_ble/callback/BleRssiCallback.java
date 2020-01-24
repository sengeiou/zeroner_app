package com.iwown.ble_module.zg_ble.callback;

import com.iwown.ble_module.zg_ble.exception.BleException;

public abstract class BleRssiCallback extends BleBaseCallback {
    public abstract void onRssiFailure(BleException bleException);

    public abstract void onRssiSuccess(int i);
}
