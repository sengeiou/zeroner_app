package coms.mediatek.wearableProfiles;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.util.Log;

class a {
    private int a;
    private BluetoothGatt b;
    private BluetoothGattCharacteristic c;
    private BluetoothGattDescriptor d;

    public a(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        this.a = i;
        this.b = bluetoothGatt;
        this.c = bluetoothGattCharacteristic;
    }

    public a(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
        this.a = i;
        this.b = bluetoothGatt;
        this.d = bluetoothGattDescriptor;
    }

    public void a() {
        Log.d("[wearable]GattRequest", "excuteRequest, action=" + this.a);
        if (this.c != null) {
            if (this.a == 1) {
                this.b.readCharacteristic(this.c);
            } else if (this.a == 2) {
                this.b.writeCharacteristic(this.c);
            }
        } else if (this.d == null) {
        } else {
            if (this.a == 1) {
                this.b.readDescriptor(this.d);
            } else if (this.a == 2) {
                this.b.writeDescriptor(this.d);
            }
        }
    }
}
