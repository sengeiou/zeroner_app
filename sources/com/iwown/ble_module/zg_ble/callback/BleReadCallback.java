package com.iwown.ble_module.zg_ble.callback;

import com.iwown.ble_module.zg_ble.exception.BleException;

public abstract class BleReadCallback extends BleBaseCallback {
    public abstract void onReadFailure(BleException bleException);

    public abstract void onReadSuccess(byte[] bArr);
}
