package no.nordicsemi.android.dfu;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;

abstract class BaseButtonlessDfuImpl extends BaseDfuImpl {
    private final ButtonlessBluetoothCallback mBluetoothCallback = new ButtonlessBluetoothCallback();

    protected class ButtonlessBluetoothCallback extends BaseBluetoothGattCallback {
        protected ButtonlessBluetoothCallback() {
            super();
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            BaseButtonlessDfuImpl.this.mService.sendLogBroadcast(5, "Notification received from " + characteristic.getUuid() + ", value (0x): " + parse(characteristic));
            BaseButtonlessDfuImpl.this.mReceivedData = characteristic.getValue();
            BaseButtonlessDfuImpl.this.notifyLock();
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            BaseButtonlessDfuImpl.this.mRequestCompleted = true;
            BaseButtonlessDfuImpl.this.notifyLock();
        }
    }

    BaseButtonlessDfuImpl(Intent intent, DfuBaseService service) {
        super(intent, service);
    }

    public BaseBluetoothGattCallback getGattCallback() {
        return this.mBluetoothCallback;
    }

    /* access modifiers changed from: protected */
    public void finalize(Intent intent, boolean forceRefresh, boolean scanForBootloader) {
        boolean z;
        boolean keepBond = intent.getBooleanExtra(DfuBaseService.EXTRA_KEEP_BOND, false);
        DfuBaseService dfuBaseService = this.mService;
        BluetoothGatt bluetoothGatt = this.mGatt;
        if (forceRefresh || !keepBond) {
            z = true;
        } else {
            z = false;
        }
        dfuBaseService.refreshDeviceCache(bluetoothGatt, z);
        this.mService.close(this.mGatt);
        if (this.mGatt.getDevice().getBondState() == 12 && (intent.getBooleanExtra(DfuBaseService.EXTRA_RESTORE_BOND, false) || !keepBond)) {
            removeBond();
            this.mService.waitFor(2000);
        }
        logi("Restarting to bootloader mode");
        Intent newIntent = new Intent();
        newIntent.fillIn(intent, 24);
        restartService(newIntent, scanForBootloader);
    }
}
