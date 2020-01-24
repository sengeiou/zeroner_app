package com.iwown.ble_module.proto.ble;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Handler;
import android.os.Looper;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.WristBand;
import com.iwown.ble_module.scan.Scanner;
import com.iwown.ble_module.utils.BluetoothUtils;
import com.socks.library.KLog;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AbsBle implements IBle {
    protected static final long CONNECT_DELAY_MILLIS = 5000;
    protected static final int GATT_CLOSE_DELAY_MILLIS = 1000;
    protected static final long SCAN_PERIOD = 10000;
    protected static final Object mLock = new Object();
    protected MyScanCallback callback;
    public List<BluetoothGattCharacteristic> characteristics = new ArrayList();
    protected Map<String, BluetoothGatt> mBluetoothGatts;
    BluetoothLeScanner mBluetoothLeScanner;
    protected BluetoothAdapter mBtAdapter;
    public Handler mHandler = new Handler(Looper.getMainLooper());
    protected boolean mIsConnected;
    protected boolean mIsConnecting;
    protected boolean mIsScanning;
    protected long mLastScanTime;
    protected LeScanCallback mLeScanCallback = new LeScanCallback() {
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            AbsBle.this.scanCount++;
            if (System.currentTimeMillis() - AbsBle.this.mLastScanTime <= 15000) {
                AbsBle.this.mService.bleDeviceFound(device, rssi, scanRecord, 0);
            } else if (AbsBle.this.mStopCount <= 10) {
                AbsBle.this.mStopCount++;
                AbsBle.this.stopScan();
            }
        }
    };
    /* access modifiers changed from: private */
    public BleService mService;
    protected int mStopCount = 0;
    Runnable mStopScanRunnable = new Runnable() {
        public void run() {
            AbsBle.this.stopScan();
        }
    };
    public WristBand mWristBand;
    protected long scanCount = 0;

    @TargetApi(21)
    private class MyScanCallback extends ScanCallback {
        private MyScanCallback() {
        }

        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            AbsBle.this.scanCount++;
            if (System.currentTimeMillis() - AbsBle.this.mLastScanTime <= 15000) {
                AbsBle.this.mService.bleDeviceFound(result.getDevice(), result.getRssi(), result.getScanRecord().getBytes(), 0);
            } else if (AbsBle.this.mStopCount <= 10) {
                AbsBle.this.mStopCount++;
                AbsBle.this.stopScan();
            }
        }

        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    }

    public WristBand getWristBand() {
        return this.mWristBand;
    }

    public void setWristBand(WristBand wristBand) {
        this.mWristBand = wristBand;
    }

    public AbsBle(BleService service) {
        this.mService = service;
        if (!this.mService.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            this.mService.bleNotSupported();
            return;
        }
        this.mBtAdapter = ((BluetoothManager) this.mService.getSystemService("bluetooth")).getAdapter();
        if (this.mBtAdapter == null) {
            this.mService.bleNoBtAdapter();
        }
        if (BluetoothUtils.hasLollipop()) {
            this.callback = new MyScanCallback();
        }
    }

    public void startScan(boolean filter) {
        scan(filter);
    }

    public void stopScan() {
        Scanner.getInstance(this.mService).stopScan();
    }

    public boolean adapterEnabled() {
        return false;
    }

    public boolean connect(String address) {
        return false;
    }

    public boolean connect() {
        return false;
    }

    public void setNeedReconnect(boolean needReconnect) {
    }

    public void unbindDevice() {
    }

    public void disconnect(String address, boolean needCloseGatt) {
    }

    public boolean discoverServices(String address) {
        return false;
    }

    public boolean isScanning() {
        return false;
    }

    public boolean isConnected() {
        try {
            if (this.mWristBand == null || ((BluetoothGatt) this.mBluetoothGatts.get(this.mWristBand.getDev_addr())) == null || this.characteristics == null || this.characteristics.size() == 0) {
                return false;
            }
            return this.mIsConnected;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        }
    }

    private void scan(boolean filter) {
        Scanner.getInstance(this.mService).startScan(new UUID[0]);
    }

    public boolean isConnecting() {
        return this.mIsConnecting;
    }

    /* access modifiers changed from: protected */
    public boolean refreshDeviceCache(BluetoothGatt gatt) {
        try {
            Method refresh = BluetoothGatt.class.getMethod("refresh", new Class[0]);
            if (refresh != null) {
                boolean success = ((Boolean) refresh.invoke(gatt, new Object[0])).booleanValue();
                KLog.e("Refreshing result: " + success);
                return success;
            }
        } catch (Exception e) {
            KLog.e("An exception occured while refreshing device " + e.toString());
        }
        return false;
    }

    public BluetoothGattCharacteristic getCharacteristic(UUID uuid) {
        try {
            for (BluetoothGattCharacteristic characteristic : this.characteristics) {
                if (characteristic.getUuid().equals(uuid)) {
                    return characteristic;
                }
            }
            return null;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public BluetoothGattCharacteristic getWriteCharacter() {
        return null;
    }

    public BluetoothGattCharacteristic getNotifyCharacter() {
        return null;
    }

    public void sendCmd(byte[] data) {
        if (isConnected()) {
            BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.get(this.mWristBand.getDev_addr());
            BluetoothGattCharacteristic writeCharacter = getWriteCharacter();
            if (gatt != null && writeCharacter != null) {
                writeCharacter.setValue(data);
                gatt.writeCharacteristic(writeCharacter);
            }
        }
    }
}
