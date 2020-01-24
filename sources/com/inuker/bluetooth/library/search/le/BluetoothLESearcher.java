package com.inuker.bluetooth.library.search.le;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import com.inuker.bluetooth.library.search.BluetoothSearcher;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.BluetoothSearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.BluetoothUtils;

public class BluetoothLESearcher extends BluetoothSearcher {
    private final LeScanCallback mLeScanCallback;

    private static class BluetoothLESearcherHolder {
        /* access modifiers changed from: private */
        public static BluetoothLESearcher instance = new BluetoothLESearcher();

        private BluetoothLESearcherHolder() {
        }
    }

    private BluetoothLESearcher() {
        this.mLeScanCallback = new LeScanCallback() {
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                BluetoothLESearcher.this.notifyDeviceFounded(new SearchResult(device, rssi, scanRecord));
            }
        };
        this.mBluetoothAdapter = BluetoothUtils.getBluetoothAdapter();
    }

    public static BluetoothLESearcher getInstance() {
        return BluetoothLESearcherHolder.instance;
    }

    @TargetApi(18)
    public void startScanBluetooth(BluetoothSearchResponse response) {
        super.startScanBluetooth(response);
        this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
    }

    @TargetApi(18)
    public void stopScanBluetooth() {
        try {
            this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
        } catch (Exception e) {
            BluetoothLog.e((Throwable) e);
        }
        super.stopScanBluetooth();
    }

    /* access modifiers changed from: protected */
    @TargetApi(18)
    public void cancelScanBluetooth() {
        this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
        super.cancelScanBluetooth();
    }
}
