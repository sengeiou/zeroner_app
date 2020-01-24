package com.inuker.bluetooth.library.connect.listener;

import android.bluetooth.BluetoothGattCharacteristic;

public interface ReadCharacterListener extends GattResponseListener {
    void onCharacteristicRead(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i, byte[] bArr);
}
