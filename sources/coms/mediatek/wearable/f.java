package coms.mediatek.wearable;

import android.bluetooth.BluetoothDevice;

class f implements e {
    f() {
    }

    public void a(float f, String str) {
        WearableManager.getInstance().a(f, str);
    }

    public void a(int i) {
        WearableManager.getInstance().a(i);
    }

    public void a(int i, int i2) {
        WearableManager.getInstance().a(i, i2);
    }

    public void a(BluetoothDevice bluetoothDevice) {
        WearableManager.getInstance().a(bluetoothDevice);
    }

    public void a(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        WearableManager.getInstance().a(bluetoothDevice, i, bArr);
    }

    public void a(byte[] bArr, int i) {
        WearableManager.getInstance().a(bArr, i);
    }
}
