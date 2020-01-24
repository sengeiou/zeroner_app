package com.iwown.ble_module.zg_ble.callback;

import com.iwown.ble_module.zg_ble.exception.BleException;

public abstract class BleIndicateCallback extends BleBaseCallback {
    public abstract void onCharacteristicChanged(byte[] bArr);

    public abstract void onIndicateFailure(BleException bleException);

    public abstract void onIndicateSuccess();
}
