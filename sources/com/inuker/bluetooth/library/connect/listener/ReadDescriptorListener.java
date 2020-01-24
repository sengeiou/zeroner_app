package com.inuker.bluetooth.library.connect.listener;

import android.bluetooth.BluetoothGattDescriptor;

public interface ReadDescriptorListener extends GattResponseListener {
    void onDescriptorRead(BluetoothGattDescriptor bluetoothGattDescriptor, int i, byte[] bArr);
}
