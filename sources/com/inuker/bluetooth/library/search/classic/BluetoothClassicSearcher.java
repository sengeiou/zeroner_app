package com.inuker.bluetooth.library.search.classic;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.inuker.bluetooth.library.search.BluetoothSearcher;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.BluetoothSearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import kotlin.jvm.internal.ShortCompanionObject;

public class BluetoothClassicSearcher extends BluetoothSearcher {
    private BluetoothSearchReceiver mReceiver;

    private static class BluetoothClassicSearcherHolder {
        /* access modifiers changed from: private */
        public static BluetoothClassicSearcher instance = new BluetoothClassicSearcher();

        private BluetoothClassicSearcherHolder() {
        }
    }

    private class BluetoothSearchReceiver extends BroadcastReceiver {
        private BluetoothSearchReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.device.action.FOUND")) {
                BluetoothClassicSearcher.this.notifyDeviceFounded(new SearchResult((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"), intent.getShortExtra("android.bluetooth.device.extra.RSSI", ShortCompanionObject.MIN_VALUE), null));
            }
        }
    }

    private BluetoothClassicSearcher() {
        this.mBluetoothAdapter = BluetoothUtils.getBluetoothAdapter();
    }

    public static BluetoothClassicSearcher getInstance() {
        return BluetoothClassicSearcherHolder.instance;
    }

    public void startScanBluetooth(BluetoothSearchResponse callback) {
        super.startScanBluetooth(callback);
        registerReceiver();
        if (this.mBluetoothAdapter.isDiscovering()) {
            this.mBluetoothAdapter.cancelDiscovery();
        }
        this.mBluetoothAdapter.startDiscovery();
    }

    public void stopScanBluetooth() {
        unregisterReceiver();
        if (this.mBluetoothAdapter.isDiscovering()) {
            this.mBluetoothAdapter.cancelDiscovery();
        }
        super.stopScanBluetooth();
    }

    /* access modifiers changed from: protected */
    public void cancelScanBluetooth() {
        unregisterReceiver();
        if (this.mBluetoothAdapter.isDiscovering()) {
            this.mBluetoothAdapter.cancelDiscovery();
        }
        super.cancelScanBluetooth();
    }

    private void registerReceiver() {
        if (this.mReceiver == null) {
            this.mReceiver = new BluetoothSearchReceiver();
            BluetoothUtils.registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.device.action.FOUND"));
        }
    }

    private void unregisterReceiver() {
        if (this.mReceiver != null) {
            BluetoothUtils.unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }
}
