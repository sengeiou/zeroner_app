package com.iwown.ble_module.scan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.bluetooth.le.ScanSettings.Builder;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.iwown.ble_module.utils.BluetoothUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Scanner {
    private static final int NULL_BT_ADAPTER = 200;
    private static final int PHONE_NOT_SUPPORT_BLE = 199;
    protected static final long SCAN_PERIOD = 12000;
    private static Scanner instance = null;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    protected MyScanCallback callback;
    private boolean isScanning = false;
    private BluetoothLeScanner mBluetoothLeScanner;
    private final BluetoothAdapter mBtAdapter;
    private Context mContext;
    /* access modifiers changed from: private */
    public IScanCallback mIScanCallback = null;
    private LeScanCallback mLeScanCallback;
    Runnable mStopScanRunnable = new Runnable() {
        public void run() {
            Scanner.this.stopScan();
        }
    };
    private long scanPeriodTime = SCAN_PERIOD;

    protected class MyLeScanCallback implements LeScanCallback {
        protected MyLeScanCallback() {
        }

        public void onLeScan(BluetoothDevice device, int i, byte[] bytes) {
            if (Scanner.this.mIScanCallback != null) {
                String str = "";
                if (!TextUtils.isEmpty(device.getName())) {
                    String dev_name = device.getName();
                    KLog.d("no2855-->扫描结果：" + device.getAddress() + " === " + device.getName());
                    Scanner.this.mIScanCallback.onScanResult(device, i, bytes, dev_name);
                }
            }
        }
    }

    @TargetApi(21)
    private class MyScanCallback extends ScanCallback {
        private MyScanCallback() {
        }

        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            String str = "Device-XXXX";
            if (Scanner.this.mIScanCallback != null && result != null && !TextUtils.isEmpty(result.getDevice().getName())) {
                String dev_name = result.getDevice().getName();
                KLog.d("no2855-->扫描结果11：" + result.getDevice().getAddress() + " === " + result.getDevice().getName());
                Scanner.this.mIScanCallback.onScanResult(result.getDevice(), result.getRssi(), result.getScanRecord().getBytes(), dev_name);
            }
        }

        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            if (Scanner.this.mIScanCallback != null) {
                Scanner.this.mIScanCallback.onError(errorCode);
            }
        }
    }

    public void setIScanCallback(IScanCallback IScanCallback) {
        this.mIScanCallback = IScanCallback;
    }

    public long getScanPeriodTime() {
        return this.scanPeriodTime;
    }

    public void setScanPeriodTime(long scanPeriodTime2) {
        this.scanPeriodTime = scanPeriodTime2;
    }

    public static Scanner getInstance(Context context) {
        if (instance == null) {
            synchronized (Scanner.class) {
                if (instance == null) {
                    instance = new Scanner(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private Scanner(Context context) {
        this.mContext = context;
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le") && this.mIScanCallback != null) {
            this.mIScanCallback.onError(199);
        }
        this.mBtAdapter = ((BluetoothManager) this.mContext.getSystemService("bluetooth")).getAdapter();
        if (this.mBtAdapter == null && this.mIScanCallback != null) {
            this.mIScanCallback.onError(200);
        }
        this.mLeScanCallback = new MyLeScanCallback();
        if (BluetoothUtils.hasLollipop()) {
            this.callback = new MyScanCallback();
        }
    }

    public boolean startScan(UUID... uuids) {
        if (this.isScanning) {
            KLog.e("licl", "上次扫描还没结束，不能开启这次扫描......");
            stopScan();
        }
        if (!BluetoothUtils.isEnabledBluetooth(this.mContext)) {
            KLog.e("licl", "蓝牙不可用");
            this.isScanning = false;
            return false;
        }
        this.isScanning = true;
        mHandler.removeCallbacks(this.mStopScanRunnable);
        mHandler.postDelayed(this.mStopScanRunnable, this.scanPeriodTime);
        if (uuids.length == 0) {
            return scan();
        }
        return scan(uuids);
    }

    public BluetoothAdapter getmBtAdapter() {
        return this.mBtAdapter;
    }

    private boolean scan(UUID... uuids) {
        if (BluetoothUtils.hasLollipop()) {
            this.mBluetoothLeScanner = this.mBtAdapter.getBluetoothLeScanner();
            ScanSettings settings = new Builder().setScanMode(1).build();
            List<ScanFilter> bleScanFilters = new ArrayList<>();
            if (uuids == null || uuids.length == 0) {
                return false;
            }
            for (UUID uuid : uuids) {
                bleScanFilters.add(new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString(uuid.toString())).build());
            }
            this.mBluetoothLeScanner.startScan(bleScanFilters, settings, this.callback);
            return true;
        }
        this.mBtAdapter.startLeScan(uuids, this.mLeScanCallback);
        return true;
    }

    private boolean scan() {
        if (BluetoothUtils.hasLollipop()) {
            this.mBluetoothLeScanner = this.mBtAdapter.getBluetoothLeScanner();
            this.mBluetoothLeScanner.startScan(null, new Builder().setScanMode(1).build(), this.callback);
        } else {
            this.mBtAdapter.startLeScan(this.mLeScanCallback);
        }
        return true;
    }

    public void stopScan() {
        mHandler.removeCallbacks(this.mStopScanRunnable);
        this.isScanning = false;
        if (BluetoothUtils.isEnabledBluetooth(this.mContext)) {
            KLog.e("no2855-->停止扫描");
            if (BluetoothUtils.hasLollipop()) {
                this.mBluetoothLeScanner = this.mBtAdapter.getBluetoothLeScanner();
                this.mBluetoothLeScanner.stopScan(this.callback);
                return;
            }
            this.mBtAdapter.stopLeScan(this.mLeScanCallback);
        }
    }

    public boolean isScanning() {
        return this.isScanning;
    }
}
