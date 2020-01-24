package com.inuker.bluetooth.library.connect.listener;

import android.bluetooth.BluetoothGattDescriptor;

public interface WriteDescriptorListener extends GattResponseListener {
    void onDescriptorWrite(BluetoothGattDescriptor bluetoothGattDescriptor, int i);
}
