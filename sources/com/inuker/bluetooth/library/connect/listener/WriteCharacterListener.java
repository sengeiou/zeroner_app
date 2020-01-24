package com.inuker.bluetooth.library.connect.listener;

import android.bluetooth.BluetoothGattCharacteristic;

public interface WriteCharacterListener extends GattResponseListener {
    void onCharacteristicWrite(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i, byte[] bArr);
}
