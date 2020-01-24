package no.nordicsemi.android.dfu.internal.scanner;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings.Builder;

@TargetApi(21)
public class BootloaderScannerLollipop extends ScanCallback implements BootloaderScanner {
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
                if (!BootloaderScannerLollipop.this.mFound) {
                    BootloaderScannerLollipop.this.mBootloaderAddress = null;
                    BootloaderScannerLollipop.this.mFound = true;
                    synchronized (BootloaderScannerLollipop.this.mLock) {
                        BootloaderScannerLollipop.this.mLock.notifyAll();
                    }
                }
            }
        }, "Scanner timer").start();
        BluetoothLeScanner scanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        scanner.startScan(null, new Builder().setScanMode(2).build(), this);
        try {
            synchronized (this.mLock) {
                while (!this.mFound) {
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
        }
        scanner.stopScan(this);
        return this.mBootloaderAddress;
    }

    public void onScanResult(int callbackType, ScanResult result) {
        String address = result.getDevice().getAddress();
        if (this.mDeviceAddress.equals(address) || this.mDeviceAddressIncremented.equals(address)) {
            this.mBootloaderAddress = address;
            this.mFound = true;
            synchronized (this.mLock) {
                this.mLock.notifyAll();
            }
        }
    }
}
