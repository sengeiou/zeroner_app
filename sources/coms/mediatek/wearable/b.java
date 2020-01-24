package coms.mediatek.wearable;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

class b {
    /* access modifiers changed from: private */
    public static Linker a;
    private static LeScanCallback b = new LeScanCallback() {
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            b.a.d(bluetoothDevice);
        }
    };

    public static void a() {
        Log.d("[wearable]ConnectReLEScan", "stopConnectReLEScann");
        BluetoothAdapter.getDefaultAdapter().stopLeScan(b);
    }

    public static boolean a(Linker linker) {
        Log.d("[wearable]ConnectReLEScan", "startConnectReLEScann");
        a = linker;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        defaultAdapter.stopLeScan(b);
        return defaultAdapter.startLeScan(b);
    }
}
