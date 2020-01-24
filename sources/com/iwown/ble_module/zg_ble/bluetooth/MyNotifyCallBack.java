package com.iwown.ble_module.zg_ble.bluetooth;

import com.iwown.ble_module.zg_ble.callback.BleNotifyCallback;
import com.iwown.ble_module.zg_ble.exception.BleException;

public class MyNotifyCallBack extends BleNotifyCallback {
    public void onNotifySuccess() {
    }

    public void onNotifyFailure(BleException exception) {
    }

    public void onCharacteristicChanged(byte[] data) {
    }
}
