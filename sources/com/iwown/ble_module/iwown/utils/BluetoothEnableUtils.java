package com.iwown.ble_module.iwown.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build.VERSION;

public class BluetoothEnableUtils {
    public static boolean isEnabledBluetooth(Context context) {
        BluetoothAdapter adapter = ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter();
        if (!context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            return false;
        }
        if (adapter == null) {
            return false;
        }
        return adapter.isEnabled();
    }

    public static boolean hasLollipop() {
        return VERSION.SDK_INT >= 21;
    }
}
