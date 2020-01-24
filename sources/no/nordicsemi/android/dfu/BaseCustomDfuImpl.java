package no.nordicsemi.android.dfu;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.CRC32;
import no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException;
import no.nordicsemi.android.dfu.internal.exception.DfuException;
import no.nordicsemi.android.dfu.internal.exception.HexFileValidationException;
import no.nordicsemi.android.dfu.internal.exception.UploadAbortedException;

abstract class BaseCustomDfuImpl extends BaseDfuImpl {
    protected boolean mFirmwareUploadInProgress;
    /* access modifiers changed from: private */
    public boolean mInitPacketInProgress;
    protected int mPacketsBeforeNotification;
    protected int mPacketsSentSinceNotification;
    protected boolean mRemoteErrorOccurred;

    protected class BaseCustomBluetoothCallback extends BaseBluetoothGattCallback {
        protected BaseCustomBluetoothCallback() {
            super();
        }

        /* access modifiers changed from: protected */
        public void onPacketCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            boolean notificationExpected = true;
            if (status == 0) {
                if (!characteristic.getUuid().equals(BaseCustomDfuImpl.this.getPacketCharacteristicUUID())) {
                    BaseCustomDfuImpl.this.mService.sendLogBroadcast(5, "Data written to " + characteristic.getUuid() + ", value (0x): " + parse(characteristic));
                    BaseCustomDfuImpl.this.mRequestCompleted = true;
                } else if (BaseCustomDfuImpl.this.mInitPacketInProgress) {
                    BaseCustomDfuImpl.this.mService.sendLogBroadcast(5, "Data written to " + characteristic.getUuid() + ", value (0x): " + parse(characteristic));
                    BaseCustomDfuImpl.this.mInitPacketInProgress = false;
                } else if (BaseCustomDfuImpl.this.mFirmwareUploadInProgress) {
                    BaseCustomDfuImpl.this.mProgressInfo.addBytesSent(characteristic.getValue().length);
                    BaseCustomDfuImpl.this.mPacketsSentSinceNotification++;
                    if (BaseCustomDfuImpl.this.mPacketsBeforeNotification <= 0 || BaseCustomDfuImpl.this.mPacketsSentSinceNotification < BaseCustomDfuImpl.this.mPacketsBeforeNotification) {
                        notificationExpected = false;
                    }
                    boolean lastPacketTransferred = BaseCustomDfuImpl.this.mProgressInfo.isComplete();
                    boolean lastObjectPacketTransferred = BaseCustomDfuImpl.this.mProgressInfo.isObjectComplete();
                    if (notificationExpected) {
                        return;
                    }
                    if (lastPacketTransferred || lastObjectPacketTransferred) {
                        BaseCustomDfuImpl.this.mFirmwareUploadInProgress = false;
                        BaseCustomDfuImpl.this.notifyLock();
                        return;
                    }
                    try {
                        BaseCustomDfuImpl.this.waitIfPaused();
                        if (BaseCustomDfuImpl.this.mAborted || BaseCustomDfuImpl.this.mError != 0 || BaseCustomDfuImpl.this.mRemoteErrorOccurred || BaseCustomDfuImpl.this.mResetRequestSent) {
                            BaseCustomDfuImpl.this.mFirmwareUploadInProgress = false;
                            BaseCustomDfuImpl.this.mService.sendLogBroadcast(15, "Upload terminated");
                            BaseCustomDfuImpl.this.notifyLock();
                            return;
                        }
                        int available = BaseCustomDfuImpl.this.mProgressInfo.getAvailableObjectSizeIsBytes();
                        byte[] buffer = BaseCustomDfuImpl.this.mBuffer;
                        if (available < buffer.length) {
                            buffer = new byte[available];
                        }
                        BaseCustomDfuImpl.this.writePacket(gatt, characteristic, buffer, BaseCustomDfuImpl.this.mFirmwareStream.read(buffer));
                        return;
                    } catch (HexFileValidationException e) {
                        BaseCustomDfuImpl.this.loge("Invalid HEX file");
                        BaseCustomDfuImpl.this.mError = 4099;
                    } catch (IOException e2) {
                        BaseCustomDfuImpl.this.loge("Error while reading the input stream", e2);
                        BaseCustomDfuImpl.this.mError = 4100;
                    }
                } else {
                    onPacketCharacteristicWrite(gatt, characteristic, status);
                }
            } else if (BaseCustomDfuImpl.this.mResetRequestSent) {
                BaseCustomDfuImpl.this.mRequestCompleted = true;
            } else {
                BaseCustomDfuImpl.this.loge("Characteristic write error: " + status);
                BaseCustomDfuImpl.this.mError = status | 16384;
            }
            BaseCustomDfuImpl.this.notifyLock();
        }

        /* access modifiers changed from: protected */
        public void handlePacketReceiptNotification(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (!BaseCustomDfuImpl.this.mFirmwareUploadInProgress) {
                handleNotification(gatt, characteristic);
                return;
            }
            BluetoothGattCharacteristic packetCharacteristic = gatt.getService(BaseCustomDfuImpl.this.getDfuServiceUUID()).getCharacteristic(BaseCustomDfuImpl.this.getPacketCharacteristicUUID());
            try {
                BaseCustomDfuImpl.this.mPacketsSentSinceNotification = 0;
                BaseCustomDfuImpl.this.waitIfPaused();
                if (BaseCustomDfuImpl.this.mAborted || BaseCustomDfuImpl.this.mError != 0 || BaseCustomDfuImpl.this.mRemoteErrorOccurred || BaseCustomDfuImpl.this.mResetRequestSent) {
                    BaseCustomDfuImpl.this.mFirmwareUploadInProgress = false;
                    BaseCustomDfuImpl.this.mService.sendLogBroadcast(15, "Upload terminated");
                    return;
                }
                boolean lastPacketTransferred = BaseCustomDfuImpl.this.mProgressInfo.isComplete();
                boolean lastObjectPacketTransferred = BaseCustomDfuImpl.this.mProgressInfo.isObjectComplete();
                if (lastPacketTransferred || lastObjectPacketTransferred) {
                    BaseCustomDfuImpl.this.mFirmwareUploadInProgress = false;
                    BaseCustomDfuImpl.this.notifyLock();
                    return;
                }
                int available = BaseCustomDfuImpl.this.mProgressInfo.getAvailableObjectSizeIsBytes();
                byte[] buffer = BaseCustomDfuImpl.this.mBuffer;
                if (available < buffer.length) {
                    buffer = new byte[available];
                }
                BaseCustomDfuImpl.this.writePacket(gatt, packetCharacteristic, buffer, BaseCustomDfuImpl.this.mFirmwareStream.read(buffer));
            } catch (HexFileValidationException e) {
                BaseCustomDfuImpl.this.loge("Invalid HEX file");
                BaseCustomDfuImpl.this.mError = 4099;
            } catch (IOException e2) {
                BaseCustomDfuImpl.this.loge("Error while reading the input stream", e2);
                BaseCustomDfuImpl.this.mError = 4100;
            }
        }

        /* access modifiers changed from: protected */
        public void handleNotification(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            BaseCustomDfuImpl.this.mService.sendLogBroadcast(5, "Notification received from " + characteristic.getUuid() + ", value (0x): " + parse(characteristic));
            BaseCustomDfuImpl.this.mReceivedData = characteristic.getValue();
            BaseCustomDfuImpl.this.mFirmwareUploadInProgress = false;
        }
    }

    /* access modifiers changed from: protected */
    public abstract UUID getControlPointCharacteristicUUID();

    /* access modifiers changed from: protected */
    public abstract UUID getDfuServiceUUID();

    /* access modifiers changed from: protected */
    public abstract UUID getPacketCharacteristicUUID();

    BaseCustomDfuImpl(Intent intent, DfuBaseService service) {
        int numberOfPackets;
        boolean z = true;
        super(intent, service);
        if (intent.hasExtra(DfuBaseService.EXTRA_PACKET_RECEIPT_NOTIFICATIONS_ENABLED)) {
            String str = DfuBaseService.EXTRA_PACKET_RECEIPT_NOTIFICATIONS_ENABLED;
            if (VERSION.SDK_INT >= 23) {
                z = false;
            }
            boolean packetReceiptNotificationEnabled = intent.getBooleanExtra(str, z);
            int numberOfPackets2 = intent.getIntExtra(DfuBaseService.EXTRA_PACKET_RECEIPT_NOTIFICATIONS_VALUE, 8);
            if (numberOfPackets2 < 0 || numberOfPackets2 > 65535) {
                numberOfPackets2 = 8;
            }
            if (!packetReceiptNotificationEnabled) {
                numberOfPackets2 = 0;
            }
            this.mPacketsBeforeNotification = numberOfPackets2;
            return;
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(service);
        String str2 = DfuSettingsConstants.SETTINGS_PACKET_RECEIPT_NOTIFICATION_ENABLED;
        if (VERSION.SDK_INT >= 23) {
            z = false;
        }
        boolean packetReceiptNotificationEnabled2 = preferences.getBoolean(str2, z);
        try {
            numberOfPackets = Integer.parseInt(preferences.getString(DfuSettingsConstants.SETTINGS_NUMBER_OF_PACKETS, String.valueOf(8)));
            if (numberOfPackets < 0 || numberOfPackets > 65535) {
                numberOfPackets = 8;
            }
        } catch (NumberFormatException e) {
            numberOfPackets = 8;
        }
        if (!packetReceiptNotificationEnabled2) {
            numberOfPackets = 0;
        }
        this.mPacketsBeforeNotification = numberOfPackets;
    }

    /* access modifiers changed from: protected */
    public void writeInitData(BluetoothGattCharacteristic characteristic, CRC32 crc32) throws DfuException, DeviceDisconnectedException, UploadAbortedException {
        try {
            byte[] data = this.mBuffer;
            while (true) {
                int size = this.mInitPacketStream.read(data, 0, data.length);
                if (size != -1) {
                    writeInitPacket(characteristic, data, size);
                    if (crc32 != null) {
                        crc32.update(data, 0, size);
                    }
                } else {
                    return;
                }
            }
        } catch (IOException e) {
            loge("Error while reading Init packet file", e);
            throw new DfuException("Error while reading Init packet file", 4098);
        }
    }

    private void writeInitPacket(BluetoothGattCharacteristic characteristic, byte[] buffer, int size) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        if (this.mAborted) {
            throw new UploadAbortedException();
        }
        byte[] locBuffer = buffer;
        if (buffer.length != size) {
            locBuffer = new byte[size];
            System.arraycopy(buffer, 0, locBuffer, 0, size);
        }
        this.mReceivedData = null;
        this.mError = 0;
        this.mInitPacketInProgress = true;
        characteristic.setWriteType(1);
        characteristic.setValue(locBuffer);
        logi("Sending init packet (Value = " + parse(locBuffer) + ")");
        this.mService.sendLogBroadcast(1, "Writing to characteristic " + characteristic.getUuid());
        this.mService.sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + ")");
        this.mGatt.writeCharacteristic(characteristic);
        try {
            synchronized (this.mLock) {
                while (true) {
                    if ((!this.mInitPacketInProgress || !this.mConnected || this.mError != 0) && !this.mPaused) {
                        break;
                    }
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
        if (this.mError != 0) {
            throw new DfuException("Unable to write Init DFU Parameters", this.mError);
        } else if (!this.mConnected) {
            throw new DeviceDisconnectedException("Unable to write Init DFU Parameters: device disconnected");
        }
    }

    /* access modifiers changed from: protected */
    public void uploadFirmwareImage(BluetoothGattCharacteristic packetCharacteristic) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        if (this.mAborted) {
            throw new UploadAbortedException();
        }
        this.mReceivedData = null;
        this.mError = 0;
        this.mFirmwareUploadInProgress = true;
        this.mPacketsSentSinceNotification = 0;
        byte[] buffer = this.mBuffer;
        try {
            int size = this.mFirmwareStream.read(buffer);
            this.mService.sendLogBroadcast(1, "Sending firmware to characteristic " + packetCharacteristic.getUuid() + "...");
            writePacket(this.mGatt, packetCharacteristic, buffer, size);
            try {
                synchronized (this.mLock) {
                    while (true) {
                        if ((!this.mFirmwareUploadInProgress || this.mReceivedData != null || !this.mConnected || this.mError != 0) && !this.mPaused) {
                            break;
                        }
                        this.mLock.wait();
                    }
                }
            } catch (InterruptedException e) {
                loge("Sleeping interrupted", e);
            }
            if (this.mError != 0) {
                throw new DfuException("Uploading Firmware Image failed", this.mError);
            } else if (!this.mConnected) {
                throw new DeviceDisconnectedException("Uploading Firmware Image failed: device disconnected");
            }
        } catch (HexFileValidationException e2) {
            throw new DfuException("HEX file not valid", 4099);
        } catch (IOException e3) {
            throw new DfuException("Error while reading file", 4100);
        }
    }

    /* access modifiers changed from: private */
    public void writePacket(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] buffer, int size) {
        byte[] locBuffer = buffer;
        if (size > 0) {
            if (buffer.length != size) {
                locBuffer = new byte[size];
                System.arraycopy(buffer, 0, locBuffer, 0, size);
            }
            characteristic.setWriteType(1);
            characteristic.setValue(locBuffer);
            gatt.writeCharacteristic(characteristic);
        }
    }

    /* access modifiers changed from: protected */
    public void finalize(Intent intent, boolean forceRefresh) {
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
        boolean alreadyWaited = false;
        if (this.mGatt.getDevice().getBondState() == 12) {
            boolean restoreBond = intent.getBooleanExtra(DfuBaseService.EXTRA_RESTORE_BOND, false);
            if (restoreBond || !keepBond) {
                removeBond();
                this.mService.waitFor(2000);
                alreadyWaited = true;
            }
            if (restoreBond && (this.mFileType & 4) > 0) {
                createBond();
                alreadyWaited = false;
            }
        }
        if (this.mProgressInfo.isLastPart()) {
            if (!alreadyWaited) {
                this.mService.waitFor(1400);
            }
            this.mProgressInfo.setProgress(-6);
            return;
        }
        logi("Starting service that will upload application");
        Intent newIntent = new Intent();
        newIntent.fillIn(intent, 24);
        newIntent.putExtra(DfuBaseService.EXTRA_FILE_MIME_TYPE, DfuBaseService.MIME_TYPE_ZIP);
        newIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE", 4);
        newIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_PART_CURRENT", this.mProgressInfo.getCurrentPart() + 1);
        newIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_PARTS_TOTAL", this.mProgressInfo.getTotalParts());
        restartService(newIntent, true);
    }
}
