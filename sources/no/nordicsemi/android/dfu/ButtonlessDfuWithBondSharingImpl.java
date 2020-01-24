package no.nordicsemi.android.dfu;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import java.util.UUID;
import no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException;
import no.nordicsemi.android.dfu.internal.exception.DfuException;
import no.nordicsemi.android.dfu.internal.exception.UploadAbortedException;

class ButtonlessDfuWithBondSharingImpl extends ButtonlessDfuImpl {
    protected static UUID BUTTONLESS_DFU_SERVICE_UUID = DEFAULT_BUTTONLESS_DFU_SERVICE_UUID;
    protected static UUID BUTTONLESS_DFU_UUID = DEFAULT_BUTTONLESS_DFU_UUID;
    protected static final UUID DEFAULT_BUTTONLESS_DFU_SERVICE_UUID = SecureDfuImpl.DEFAULT_DFU_SERVICE_UUID;
    protected static final UUID DEFAULT_BUTTONLESS_DFU_UUID = new UUID(-8157989228746813600L, -6937650605005804976L);
    private BluetoothGattCharacteristic mButtonlessDfuCharacteristic;

    ButtonlessDfuWithBondSharingImpl(Intent intent, DfuBaseService service) {
        super(intent, service);
    }

    public boolean isClientCompatible(Intent intent, BluetoothGatt gatt) {
        BluetoothGattService dfuService = gatt.getService(BUTTONLESS_DFU_SERVICE_UUID);
        if (dfuService == null) {
            return false;
        }
        this.mButtonlessDfuCharacteristic = dfuService.getCharacteristic(BUTTONLESS_DFU_UUID);
        if (this.mButtonlessDfuCharacteristic != null) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public int getResponseType() {
        return 2;
    }

    /* access modifiers changed from: protected */
    public BluetoothGattCharacteristic getButtonlessDfuCharacteristic() {
        return this.mButtonlessDfuCharacteristic;
    }

    /* access modifiers changed from: protected */
    public boolean shouldScanForBootloader() {
        return false;
    }

    public void performDfu(Intent intent) throws DfuException, DeviceDisconnectedException, UploadAbortedException {
        logi("Buttonless service with bond sharing found -> SDK 14 or newer");
        if (!isBonded()) {
            logw("Device is not paired, cancelling DFU");
            this.mService.sendLogBroadcast(15, "Device is not bonded");
            this.mService.terminateConnection(this.mGatt, DfuBaseService.ERROR_DEVICE_NOT_BONDED);
            return;
        }
        super.performDfu(intent);
    }
}
