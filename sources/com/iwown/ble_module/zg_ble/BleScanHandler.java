package com.iwown.ble_module.zg_ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public class BleScanHandler {
    private static BleScanHandler bleScanHandler = null;
    private BluetoothLeScanner bluetoothLeScanner;
    private BluetoothAdapter mBluetoothAdapter;
    private final LeScanCallback mLeScanCallback;
    private boolean mScanning;
    private final ScanCallback scanCallback;

    private BleScanHandler(Context context, LeScanCallback mLeScanCallback2, ScanCallback scanCallback2) {
        this.mLeScanCallback = mLeScanCallback2;
        this.scanCallback = scanCallback2;
        try {
            this.mBluetoothAdapter = ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter();
            if (VERSION.SDK_INT >= 21) {
                this.bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static BleScanHandler init(Context context, LeScanCallback mLeScanCallback2, ScanCallback scanCallback2) {
        if (bleScanHandler == null) {
            bleScanHandler = new BleScanHandler(context, mLeScanCallback2, scanCallback2);
        }
        return bleScanHandler;
    }

    public static BleScanHandler getInstance() {
        if (bleScanHandler != null) {
            return bleScanHandler;
        }
        throw new RuntimeException("BleScanHandler init is null");
    }

    public void scanBleDevice() {
        if (this.mScanning) {
            Log.d("AAA", "scan ble ing");
            return;
        }
        stopScanBleDevice();
        this.mScanning = true;
        if (VERSION.SDK_INT >= 21) {
            this.bluetoothLeScanner.startScan(this.scanCallback);
        } else {
            this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
        }
    }

    public BluetoothAdapter getmBluetoothAdapter() {
        return this.mBluetoothAdapter;
    }

    public void stopScanBleDevice() {
        this.mScanning = false;
        if (VERSION.SDK_INT >= 21) {
            this.bluetoothLeScanner.stopScan(this.scanCallback);
        }
        this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
    }

    public boolean isBlueEnable() {
        return this.mBluetoothAdapter != null && this.mBluetoothAdapter.isEnabled();
    }
}
