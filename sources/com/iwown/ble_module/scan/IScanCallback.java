package com.iwown.ble_module.scan;

import android.bluetooth.BluetoothDevice;

public interface IScanCallback {
    void onError(int i);

    void onScanResult(BluetoothDevice bluetoothDevice, int i, byte[] bArr, String str);
}
