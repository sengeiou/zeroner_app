package com.iwown.ble_module.zg_ble.callback;

import com.iwown.ble_module.zg_ble.exception.BleException;

public abstract class BleNotifyCallback extends BleBaseCallback {
    public abstract void onCharacteristicChanged(byte[] bArr);

    public abstract void onNotifyFailure(BleException bleException);

    public abstract void onNotifySuccess();
}
