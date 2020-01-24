package com.iwown.ble_module.zg_ble.data;

import android.bluetooth.BluetoothGatt;
import com.iwown.ble_module.zg_ble.bluetooth.BleDevice;
import com.iwown.ble_module.zg_ble.callback.BleGattCallback;

public class BleConnectStateParameter {
    private BleDevice bleDevice;
    private BleGattCallback callback;
    private BluetoothGatt gatt;
    private boolean isAcitive;
    private int status;

    public BleConnectStateParameter(BleGattCallback callback2, BluetoothGatt gatt2, int status2) {
        this.callback = callback2;
        this.gatt = gatt2;
        this.status = status2;
    }

    public BleGattCallback getCallback() {
        return this.callback;
    }

    public void setCallback(BleGattCallback callback2) {
        this.callback = callback2;
    }

    public BluetoothGatt getGatt() {
        return this.gatt;
    }

    public void setGatt(BluetoothGatt gatt2) {
        this.gatt = gatt2;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }

    public boolean isAcitive() {
        return this.isAcitive;
    }

    public void setAcitive(boolean acitive) {
        this.isAcitive = acitive;
    }

    public BleDevice getBleDevice() {
        return this.bleDevice;
    }

    public void setBleDevice(BleDevice bleDevice2) {
        this.bleDevice = bleDevice2;
    }
}
