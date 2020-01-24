package no.nordicsemi.android.dfu.internal.scanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;

public class BootloaderScannerJB implements BootloaderScanner, LeScanCallback {
    /* access modifiers changed from: private */
    public String mBootloaderAddress;
    private String mDeviceAddress;
    private String mDeviceAddressIncremented;
    /* access modifiers changed from: private */
    public boolean mFound;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();

    public String searchFor(String deviceAddress) {
        String firstBytes = deviceAddress.substring(0, 15);
        String lastByteIncremented = String.format("%02X", new Object[]{Integer.valueOf((Integer.valueOf(deviceAddress.substring(15), 16).intValue() + 1) & 255)});
        this.mDeviceAddress = deviceAddress;
        this.mDeviceAddressIncremented = firstBytes + lastByteIncremented;
        this.mBootloaderAddress = null;
        this.mFound = false;
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(BootloaderScanner.TIMEOUT);
                } catch (InterruptedException e) {
                }
                if (!BootloaderScannerJB.this.mFound) {
                    BootloaderScannerJB.this.mBootloaderAddress = null;
                    BootloaderScannerJB.this.mFound = true;
                    synchronized (BootloaderScannerJB.this.mLock) {
                        BootloaderScannerJB.this.mLock.notifyAll();
                    }
                }
            }
        }, "Scanner timer").start();
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.startLeScan(this);
        try {
            synchronized (this.mLock) {
                while (!this.mFound) {
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
        }
        adapter.stopLeScan(this);
        return this.mBootloaderAddress;
    }

    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        String address = device.getAddress();
        if (this.mDeviceAddress.equals(address) || this.mDeviceAddressIncremented.equals(address)) {
            this.mBootloaderAddress = address;
            this.mFound = true;
            synchronized (this.mLock) {
                this.mLock.notifyAll();
            }
        }
    }
}
