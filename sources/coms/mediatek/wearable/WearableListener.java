package coms.mediatek.wearable;

import android.bluetooth.BluetoothDevice;

public interface WearableListener {
    void onConnectChange(int i, int i2);

    void onDeviceChange(BluetoothDevice bluetoothDevice);

    void onDeviceScan(BluetoothDevice bluetoothDevice);

    void onModeSwitch(int i);
}
