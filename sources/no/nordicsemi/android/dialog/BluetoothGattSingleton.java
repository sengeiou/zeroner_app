package no.nordicsemi.android.dialog;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

public class BluetoothGattSingleton {
    private static BluetoothGatt gatt = null;
    private static BluetoothGattCharacteristic spotaMemInfoCharacteristic = null;

    public static BluetoothGatt getGatt() {
        return gatt;
    }

    public static void setGatt(BluetoothGatt newGatt) {
        gatt = newGatt;
    }

    public static BluetoothGattCharacteristic getSpotaMemInfoCharacteristic() {
        return spotaMemInfoCharacteristic;
    }

    public static void setSpotaMemInfoCharacteristic(BluetoothGattCharacteristic spotaMemInfoCharacteristic2) {
        spotaMemInfoCharacteristic = spotaMemInfoCharacteristic2;
    }
}
