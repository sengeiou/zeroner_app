package coms.mediatek.wearableProfiles;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import java.util.UUID;

class b {
    private BluetoothGatt a = null;
    private BluetoothGattCharacteristic b = null;
    private BluetoothGattDescriptor c = null;
    private int d = 0;
    private int e = 0;
    private int f = 0;

    b() {
    }

    public BluetoothGatt a() {
        return this.a;
    }

    public void a(int i) {
        this.d = i;
    }

    public void a(BluetoothGatt bluetoothGatt) {
        this.a = bluetoothGatt;
    }

    public void a(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.b = bluetoothGattCharacteristic;
    }

    public void a(BluetoothGattDescriptor bluetoothGattDescriptor) {
        this.c = bluetoothGattDescriptor;
    }

    public BluetoothGattCharacteristic b() {
        return this.b;
    }

    public void b(int i) {
        this.e = i;
    }

    public BluetoothGattDescriptor c() {
        return this.c;
    }

    public void c(int i) {
        this.f = i;
    }

    public int d() {
        return this.d;
    }

    public int e() {
        return this.e;
    }

    public int f() {
        return this.f;
    }

    public String toString() {
        UUID uuid = null;
        StringBuilder append = new StringBuilder().append("BluetoothGatt=").append(this.a).append("\nCharacteristic=").append(this.b == null ? null : this.b.getUuid()).append("\nDescriptor=");
        if (this.c != null) {
            uuid = this.c.getUuid();
        }
        return append.append(uuid).append("\nStatus=").append(this.d).append(", NewState=").append(this.e).append(", Rssi=").append(this.f).toString();
    }
}
