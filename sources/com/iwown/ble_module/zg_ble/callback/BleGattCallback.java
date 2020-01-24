package com.iwown.ble_module.zg_ble.callback;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import com.iwown.ble_module.zg_ble.bluetooth.BleDevice;
import com.iwown.ble_module.zg_ble.exception.BleException;

@TargetApi(18)
public abstract class BleGattCallback extends BluetoothGattCallback {
    public abstract void onConnectFail(BleException bleException);

    public abstract void onConnectSuccess(BleDevice bleDevice, BluetoothGatt bluetoothGatt, int i);

    public abstract void onDisConnected(boolean z, BleDevice bleDevice, BluetoothGatt bluetoothGatt, int i);

    public abstract void onStartConnect();
}
