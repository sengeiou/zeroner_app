package com.iwown.ble_module.zg_ble.callback;

import com.iwown.ble_module.zg_ble.exception.BleException;

public abstract class BleWriteCallback extends BleBaseCallback {
    public abstract void onWriteFailure(BleException bleException);

    public abstract void onWriteSuccess();
}
