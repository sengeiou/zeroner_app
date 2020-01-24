package no.nordicsemi.android.dfu;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.preference.PreferenceManager;
import com.alibaba.android.arouter.utils.Consts;
import java.util.UUID;
import no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException;
import no.nordicsemi.android.dfu.internal.exception.DfuException;
import no.nordicsemi.android.dfu.internal.exception.UploadAbortedException;

class LegacyButtonlessDfuImpl extends BaseButtonlessDfuImpl {
    protected static UUID DFU_CONTROL_POINT_UUID = LegacyDfuImpl.DEFAULT_DFU_CONTROL_POINT_UUID;
    protected static UUID DFU_SERVICE_UUID = LegacyDfuImpl.DEFAULT_DFU_SERVICE_UUID;
    protected static UUID DFU_VERSION_UUID = LegacyDfuImpl.DEFAULT_DFU_VERSION_UUID;
    private static final byte[] OP_CODE_ENTER_BOOTLOADER = {1, 4};
    private BluetoothGattCharacteristic mControlPointCharacteristic;
    private int mVersion;

    LegacyButtonlessDfuImpl(Intent intent, DfuBaseService service) {
        super(intent, service);
    }

    public boolean isClientCompatible(Intent intent, BluetoothGatt gatt) throws DfuException, DeviceDisconnectedException, UploadAbortedException {
        BluetoothGattService dfuService = gatt.getService(DFU_SERVICE_UUID);
        if (dfuService == null) {
            return false;
        }
        this.mControlPointCharacteristic = dfuService.getCharacteristic(DFU_CONTROL_POINT_UUID);
        if (this.mControlPointCharacteristic == null) {
            return false;
        }
        this.mProgressInfo.setProgress(-2);
        this.mService.waitFor(1000);
        int version = 0;
        BluetoothGattCharacteristic versionCharacteristic = dfuService.getCharacteristic(DFU_VERSION_UUID);
        if (versionCharacteristic != null) {
            version = readVersion(gatt, versionCharacteristic);
            this.mVersion = version;
            int minor = version & 15;
            int major = version >> 8;
            logi("Version number read: " + major + Consts.DOT + minor + " -> " + getVersionFeatures(version));
            this.mService.sendLogBroadcast(10, "Version number read: " + major + Consts.DOT + minor);
        } else {
            logi("No DFU Version characteristic found -> " + getVersionFeatures(0));
            this.mService.sendLogBroadcast(10, "DFU Version characteristic not found");
        }
        boolean assumeDfuMode = PreferenceManager.getDefaultSharedPreferences(this.mService).getBoolean(DfuSettingsConstants.SETTINGS_ASSUME_DFU_NODE, false);
        if (intent.hasExtra(DfuBaseService.EXTRA_FORCE_DFU)) {
            assumeDfuMode = intent.getBooleanExtra(DfuBaseService.EXTRA_FORCE_DFU, false);
        }
        boolean moreServicesFound = gatt.getServices().size() > 3;
        if (version == 0 && moreServicesFound) {
            logi("Additional services found -> Bootloader from SDK 6.1. Updating SD and BL supported, extended init packet not supported");
        }
        if (version == 1 || (!assumeDfuMode && version == 0 && moreServicesFound)) {
            return true;
        }
        return false;
    }

    public void performDfu(Intent intent) throws DfuException, DeviceDisconnectedException, UploadAbortedException {
        boolean hasServiceChanged;
        boolean z;
        boolean z2 = true;
        logw("Application with legacy buttonless update found");
        this.mService.sendLogBroadcast(15, "Application with buttonless update found");
        this.mService.sendLogBroadcast(1, "Jumping to the DFU Bootloader...");
        enableCCCD(this.mControlPointCharacteristic, 1);
        this.mService.sendLogBroadcast(10, "Notifications enabled");
        this.mService.waitFor(1000);
        this.mProgressInfo.setProgress(-3);
        logi("Sending Start DFU command (Op Code = 1, Upload Mode = 4)");
        writeOpCode(this.mControlPointCharacteristic, OP_CODE_ENTER_BOOTLOADER, true);
        this.mService.sendLogBroadcast(10, "Jump to bootloader sent (Op Code = 1, Upload Mode = 4)");
        this.mService.waitUntilDisconnected();
        this.mService.sendLogBroadcast(5, "Disconnected by the remote device");
        BluetoothGatt gatt = this.mGatt;
        BluetoothGattService gas = gatt.getService(GENERIC_ATTRIBUTE_SERVICE_UUID);
        if (gas == null || gas.getCharacteristic(SERVICE_CHANGED_UUID) == null) {
            hasServiceChanged = false;
        } else {
            hasServiceChanged = true;
        }
        DfuBaseService dfuBaseService = this.mService;
        if (!hasServiceChanged) {
            z = true;
        } else {
            z = false;
        }
        dfuBaseService.refreshDeviceCache(gatt, z);
        this.mService.close(gatt);
        logi("Starting service that will connect to the DFU bootloader");
        Intent newIntent = new Intent();
        newIntent.fillIn(intent, 24);
        if (this.mVersion != 0) {
            z2 = false;
        }
        restartService(newIntent, z2);
    }

    private int readVersion(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        if (!this.mConnected) {
            throw new DeviceDisconnectedException("Unable to read version number: device disconnected");
        } else if (this.mAborted) {
            throw new UploadAbortedException();
        } else if (characteristic == null) {
            return 0;
        } else {
            this.mReceivedData = null;
            this.mError = 0;
            logi("Reading DFU version number...");
            this.mService.sendLogBroadcast(1, "Reading DFU version number...");
            characteristic.setValue(null);
            this.mService.sendLogBroadcast(0, "gatt.readCharacteristic(" + characteristic.getUuid() + ")");
            gatt.readCharacteristic(characteristic);
            try {
                synchronized (this.mLock) {
                    while (true) {
                        if (((this.mRequestCompleted && characteristic.getValue() != null) || !this.mConnected || this.mError != 0 || this.mAborted) && !this.mPaused) {
                            break;
                        }
                        this.mRequestCompleted = false;
                        this.mLock.wait();
                    }
                }
            } catch (InterruptedException e) {
                loge("Sleeping interrupted", e);
            }
            if (this.mError != 0) {
                throw new DfuException("Unable to read version number", this.mError);
            } else if (this.mConnected) {
                return characteristic.getIntValue(18, 0).intValue();
            } else {
                throw new DeviceDisconnectedException("Unable to read version number: device disconnected");
            }
        }
    }

    private String getVersionFeatures(int version) {
        switch (version) {
            case 0:
                return "Bootloader from SDK 6.1 or older";
            case 1:
                return "Application with Legacy buttonless update from SDK 7.0 or newer";
            case 5:
                return "Bootloader from SDK 7.0 or newer. No bond sharing";
            case 6:
                return "Bootloader from SDK 8.0 or newer. Bond sharing supported";
            case 7:
                return "Bootloader from SDK 8.0 or newer. SHA-256 used instead of CRC-16 in the Init Packet";
            case 8:
                return "Bootloader from SDK 9.0 or newer. Signature supported";
            default:
                return "Unknown version";
        }
    }
}
