package no.nordicsemi.android.dfu;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.SystemClock;
import com.socks.library.KLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;
import java.util.zip.CRC32;
import no.nordicsemi.android.dfu.internal.ArchiveInputStream;
import no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException;
import no.nordicsemi.android.dfu.internal.exception.DfuException;
import no.nordicsemi.android.dfu.internal.exception.RemoteDfuException;
import no.nordicsemi.android.dfu.internal.exception.RemoteDfuExtendedErrorException;
import no.nordicsemi.android.dfu.internal.exception.UnknownResponseException;
import no.nordicsemi.android.dfu.internal.exception.UploadAbortedException;
import no.nordicsemi.android.error.SecureDfuError;

class SecureDfuImpl extends BaseCustomDfuImpl {
    protected static final UUID DEFAULT_DFU_CONTROL_POINT_UUID = new UUID(-8157989241631715488L, -6937650605005804976L);
    protected static final UUID DEFAULT_DFU_PACKET_UUID = new UUID(-8157989237336748192L, -6937650605005804976L);
    protected static final UUID DEFAULT_DFU_SERVICE_UUID = new UUID(279658205548544L, -9223371485494954757L);
    protected static UUID DFU_CONTROL_POINT_UUID = DEFAULT_DFU_CONTROL_POINT_UUID;
    protected static UUID DFU_PACKET_UUID = DEFAULT_DFU_PACKET_UUID;
    protected static UUID DFU_SERVICE_UUID = DEFAULT_DFU_SERVICE_UUID;
    private static final int DFU_STATUS_SUCCESS = 1;
    private static final int MAX_ATTEMPTS = 3;
    private static final int OBJECT_COMMAND = 1;
    private static final int OBJECT_DATA = 2;
    private static final byte[] OP_CODE_CALCULATE_CHECKSUM = {3};
    private static final int OP_CODE_CALCULATE_CHECKSUM_KEY = 3;
    private static final byte[] OP_CODE_CREATE_COMMAND = {1, 1, 0, 0, 0, 0};
    private static final byte[] OP_CODE_CREATE_DATA = {1, 2, 0, 0, 0, 0};
    private static final int OP_CODE_CREATE_KEY = 1;
    private static final byte[] OP_CODE_EXECUTE = {4};
    private static final int OP_CODE_EXECUTE_KEY = 4;
    private static final byte[] OP_CODE_PACKET_RECEIPT_NOTIF_REQ = {2, 0, 0};
    private static final int OP_CODE_PACKET_RECEIPT_NOTIF_REQ_KEY = 2;
    private static final int OP_CODE_RESPONSE_CODE_KEY = 96;
    private static final byte[] OP_CODE_SELECT_OBJECT = {6, 0};
    private static final int OP_CODE_SELECT_OBJECT_KEY = 6;
    private final SecureBluetoothCallback mBluetoothCallback = new SecureBluetoothCallback();
    private BluetoothGattCharacteristic mControlPointCharacteristic;
    private BluetoothGattCharacteristic mPacketCharacteristic;

    private class ObjectChecksum {
        protected int CRC32;
        protected int offset;

        private ObjectChecksum() {
        }
    }

    private class ObjectInfo extends ObjectChecksum {
        protected int maxSize;

        private ObjectInfo() {
            super();
        }
    }

    protected class SecureBluetoothCallback extends BaseCustomBluetoothCallback {
        protected SecureBluetoothCallback() {
            super();
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (characteristic.getValue() == null || characteristic.getValue().length < 3) {
                SecureDfuImpl.this.loge("Empty response: " + parse(characteristic));
                SecureDfuImpl.this.mError = DfuBaseService.ERROR_INVALID_RESPONSE;
                SecureDfuImpl.this.notifyLock();
                return;
            }
            if (characteristic.getIntValue(17, 0).intValue() == 96) {
                switch (characteristic.getIntValue(17, 1).intValue()) {
                    case 3:
                        int offset = characteristic.getIntValue(20, 3).intValue();
                        if (((int) (((ArchiveInputStream) SecureDfuImpl.this.mFirmwareStream).getCrc32() & 4294967295L)) == characteristic.getIntValue(20, 7).intValue()) {
                            SecureDfuImpl.this.mProgressInfo.setBytesReceived(offset);
                        } else if (SecureDfuImpl.this.mFirmwareUploadInProgress) {
                            SecureDfuImpl.this.mFirmwareUploadInProgress = false;
                            SecureDfuImpl.this.notifyLock();
                            return;
                        }
                        handlePacketReceiptNotification(gatt, characteristic);
                        break;
                    default:
                        if (!SecureDfuImpl.this.mRemoteErrorOccurred) {
                            if (characteristic.getIntValue(17, 2).intValue() != 1) {
                                SecureDfuImpl.this.mRemoteErrorOccurred = true;
                            }
                            handleNotification(gatt, characteristic);
                            break;
                        }
                        break;
                }
            } else {
                SecureDfuImpl.this.loge("Invalid response: " + parse(characteristic));
                SecureDfuImpl.this.mError = DfuBaseService.ERROR_INVALID_RESPONSE;
            }
            SecureDfuImpl.this.notifyLock();
        }
    }

    SecureDfuImpl(Intent intent, DfuBaseService service) {
        super(intent, service);
    }

    public boolean isClientCompatible(Intent intent, BluetoothGatt gatt) {
        BluetoothGattService dfuService = gatt.getService(DFU_SERVICE_UUID);
        if (dfuService == null) {
            return false;
        }
        this.mControlPointCharacteristic = dfuService.getCharacteristic(DFU_CONTROL_POINT_UUID);
        this.mPacketCharacteristic = dfuService.getCharacteristic(DFU_PACKET_UUID);
        if (this.mControlPointCharacteristic == null || this.mPacketCharacteristic == null) {
            return false;
        }
        return true;
    }

    public boolean initialize(Intent intent, BluetoothGatt gatt, int fileType, InputStream firmwareStream, InputStream initPacketStream) throws DfuException, DeviceDisconnectedException, UploadAbortedException {
        KLog.e(DfuBaseService.NOTIFICATION_CHANNEL_DFU, "-------initialize-------" + initPacketStream);
        if (initPacketStream != null) {
            return super.initialize(intent, gatt, fileType, firmwareStream, initPacketStream);
        }
        this.mService.sendLogBroadcast(20, "The Init packet is required by this version DFU Bootloader");
        this.mService.terminateConnection(gatt, DfuBaseService.ERROR_INIT_PACKET_REQUIRED);
        return false;
    }

    public BaseBluetoothGattCallback getGattCallback() {
        return this.mBluetoothCallback;
    }

    /* access modifiers changed from: protected */
    public UUID getControlPointCharacteristicUUID() {
        return DFU_CONTROL_POINT_UUID;
    }

    /* access modifiers changed from: protected */
    public UUID getPacketCharacteristicUUID() {
        return DFU_PACKET_UUID;
    }

    /* access modifiers changed from: protected */
    public UUID getDfuServiceUUID() {
        return DFU_SERVICE_UUID;
    }

    public void performDfu(Intent intent) throws DfuException, DeviceDisconnectedException, UploadAbortedException {
        logw("Secure DFU bootloader found");
        this.mProgressInfo.setProgress(-2);
        this.mService.waitFor(1000);
        BluetoothGatt gatt = this.mGatt;
        if (VERSION.SDK_INT >= 21) {
            logi("Requesting MTU = 517");
            requestMtu(517);
        }
        try {
            enableCCCD(this.mControlPointCharacteristic, 1);
            this.mService.sendLogBroadcast(10, "Notifications enabled");
            this.mService.waitFor(1000);
            sendInitPacket(gatt);
            sendFirmware(gatt);
            this.mProgressInfo.setProgress(-5);
            this.mService.waitUntilDisconnected();
            this.mService.sendLogBroadcast(5, "Disconnected by the remote device");
            finalize(intent, false);
        } catch (UploadAbortedException e) {
            throw e;
        } catch (UnknownResponseException e2) {
            loge(e2.getMessage());
            this.mService.sendLogBroadcast(20, e2.getMessage());
            this.mService.terminateConnection(gatt, DfuBaseService.ERROR_INVALID_RESPONSE);
        } catch (RemoteDfuException e3) {
            int error = e3.getErrorNumber() | 512;
            loge(e3.getMessage() + ": " + SecureDfuError.parse(error));
            this.mService.sendLogBroadcast(20, String.format("Remote DFU error: %s", new Object[]{SecureDfuError.parse(error)}));
            if (e3 instanceof RemoteDfuExtendedErrorException) {
                RemoteDfuExtendedErrorException ee = (RemoteDfuExtendedErrorException) e3;
                int extendedError = ee.getExtendedErrorNumber() | 1024;
                loge("Extended Error details: " + SecureDfuError.parseExtendedError(extendedError));
                this.mService.sendLogBroadcast(20, "Details: " + SecureDfuError.parseExtendedError(extendedError) + " (Code = " + ee.getExtendedErrorNumber() + ")");
                this.mService.terminateConnection(gatt, extendedError | 8192);
                return;
            }
            this.mService.terminateConnection(gatt, error | 8192);
        }
    }

    private void sendInitPacket(BluetoothGatt gatt) throws RemoteDfuException, DeviceDisconnectedException, DfuException, UploadAbortedException, UnknownResponseException {
        CRC32 crc32 = new CRC32();
        logi("Setting object to Command (Op Code = 6, Type = 1)");
        ObjectInfo info = selectObject(1);
        logi(String.format(Locale.US, "Command object info received (Max size = %d, Offset = %d, CRC = %08X)", new Object[]{Integer.valueOf(info.maxSize), Integer.valueOf(info.offset), Integer.valueOf(info.CRC32)}));
        this.mService.sendLogBroadcast(10, String.format(Locale.US, "Command object info received (Max size = %d, Offset = %d, CRC = %08X)", new Object[]{Integer.valueOf(info.maxSize), Integer.valueOf(info.offset), Integer.valueOf(info.CRC32)}));
        if (this.mInitPacketSizeInBytes > info.maxSize) {
        }
        boolean skipSendingInitPacket = false;
        boolean resumeSendingInitPacket = false;
        if (info.offset > 0 && info.offset <= this.mInitPacketSizeInBytes) {
            try {
                byte[] buffer = new byte[info.offset];
                this.mInitPacketStream.read(buffer);
                crc32.update(buffer);
                if (info.CRC32 == ((int) (crc32.getValue() & 4294967295L))) {
                    logi("Init packet CRC is the same");
                    if (info.offset == this.mInitPacketSizeInBytes) {
                        logi("-> Whole Init packet was sent before");
                        skipSendingInitPacket = true;
                        this.mService.sendLogBroadcast(10, "Received CRC match Init packet");
                    } else {
                        logi("-> " + info.offset + " bytes of Init packet were sent before");
                        resumeSendingInitPacket = true;
                        this.mService.sendLogBroadcast(10, "Resuming sending Init packet...");
                    }
                } else {
                    this.mInitPacketStream.reset();
                    crc32.reset();
                    info.offset = 0;
                }
            } catch (IOException e) {
                loge("Error while reading " + info.offset + " bytes from the init packet stream", e);
                try {
                    this.mInitPacketStream.reset();
                    crc32.reset();
                    info.offset = 0;
                } catch (IOException e1) {
                    loge("Error while resetting the init packet stream", e1);
                    this.mService.terminateConnection(gatt, 4100);
                    return;
                }
            }
        }
        if (!skipSendingInitPacket) {
            setPacketReceiptNotifications(0);
            this.mService.sendLogBroadcast(10, "Packet Receipt Notif disabled (Op Code = 2, Value = 0)");
            int attempt = 1;
            while (attempt <= 3) {
                if (!resumeSendingInitPacket) {
                    logi("Creating Init packet object (Op Code = 1, Type = 1, Size = " + this.mInitPacketSizeInBytes + ")");
                    writeCreateRequest(1, this.mInitPacketSizeInBytes);
                    this.mService.sendLogBroadcast(10, "Command object created");
                }
                logi("Sending " + (this.mInitPacketSizeInBytes - info.offset) + " bytes of init packet...");
                writeInitData(this.mPacketCharacteristic, crc32);
                int crc = (int) (crc32.getValue() & 4294967295L);
                this.mService.sendLogBroadcast(10, String.format(Locale.US, "Command object sent (CRC = %08X)", new Object[]{Integer.valueOf(crc)}));
                logi("Sending Calculate Checksum command (Op Code = 3)");
                ObjectChecksum checksum = readChecksum();
                this.mService.sendLogBroadcast(10, String.format(Locale.US, "Checksum received (Offset = %d, CRC = %08X)", new Object[]{Integer.valueOf(checksum.offset), Integer.valueOf(checksum.CRC32)}));
                logi(String.format(Locale.US, "Checksum received (Offset = %d, CRC = %08X)", new Object[]{Integer.valueOf(checksum.offset), Integer.valueOf(checksum.CRC32)}));
                if (crc == checksum.CRC32) {
                    break;
                } else if (attempt < 3) {
                    attempt++;
                    logi("CRC does not match! Retrying...(" + attempt + "/" + 3 + ")");
                    this.mService.sendLogBroadcast(15, "CRC does not match! Retrying...(" + attempt + "/" + 3 + ")");
                    resumeSendingInitPacket = false;
                    try {
                        info.offset = 0;
                        info.CRC32 = 0;
                        this.mInitPacketStream.reset();
                        crc32.reset();
                    } catch (IOException e2) {
                        loge("Error while resetting the init packet stream", e2);
                        this.mService.terminateConnection(gatt, 4100);
                        return;
                    }
                } else {
                    loge("CRC does not match!");
                    this.mService.sendLogBroadcast(20, "CRC does not match!");
                    this.mService.terminateConnection(gatt, DfuBaseService.ERROR_CRC_ERROR);
                    return;
                }
            }
        }
        logi("Executing init packet (Op Code = 4)");
        writeExecute();
        this.mService.sendLogBroadcast(10, "Command object executed");
    }

    private void sendFirmware(BluetoothGatt gatt) throws RemoteDfuException, DeviceDisconnectedException, DfuException, UploadAbortedException, UnknownResponseException {
        int numberOfPacketsBeforeNotification = this.mPacketsBeforeNotification;
        if (numberOfPacketsBeforeNotification > 0) {
            setPacketReceiptNotifications(numberOfPacketsBeforeNotification);
            this.mService.sendLogBroadcast(10, "Packet Receipt Notif Req (Op Code = 2) sent (Value = " + numberOfPacketsBeforeNotification + ")");
        }
        logi("Setting object to Data (Op Code = 6, Type = 2)");
        ObjectInfo info = selectObject(2);
        logi(String.format(Locale.US, "Data object info received (Max size = %d, Offset = %d, CRC = %08X)", new Object[]{Integer.valueOf(info.maxSize), Integer.valueOf(info.offset), Integer.valueOf(info.CRC32)}));
        this.mService.sendLogBroadcast(10, String.format(Locale.US, "Data object info received (Max size = %d, Offset = %d, CRC = %08X)", new Object[]{Integer.valueOf(info.maxSize), Integer.valueOf(info.offset), Integer.valueOf(info.CRC32)}));
        this.mProgressInfo.setMaxObjectSizeInBytes(info.maxSize);
        int chunkCount = ((this.mImageSizeInBytes + info.maxSize) - 1) / info.maxSize;
        int currentChunk = 0;
        boolean resumeSendingData = false;
        if (info.offset > 0) {
            try {
                currentChunk = info.offset / info.maxSize;
                int bytesSentAndExecuted = info.maxSize * currentChunk;
                int bytesSentNotExecuted = info.offset - bytesSentAndExecuted;
                if (bytesSentNotExecuted == 0) {
                    bytesSentAndExecuted -= info.maxSize;
                    bytesSentNotExecuted = info.maxSize;
                }
                if (bytesSentAndExecuted > 0) {
                    this.mFirmwareStream.read(new byte[bytesSentAndExecuted]);
                    this.mFirmwareStream.mark(info.maxSize);
                }
                this.mFirmwareStream.read(new byte[bytesSentNotExecuted]);
                if (((int) (((ArchiveInputStream) this.mFirmwareStream).getCrc32() & 4294967295L)) == info.CRC32) {
                    logi(info.offset + " bytes of data sent before, CRC match");
                    this.mService.sendLogBroadcast(10, info.offset + " bytes of data sent before, CRC match");
                    this.mProgressInfo.setBytesSent(info.offset);
                    this.mProgressInfo.setBytesReceived(info.offset);
                    if (bytesSentNotExecuted != info.maxSize || info.offset >= this.mImageSizeInBytes) {
                        resumeSendingData = true;
                    } else {
                        logi("Executing data object (Op Code = 4)");
                        writeExecute();
                        this.mService.sendLogBroadcast(10, "Data object executed");
                    }
                } else {
                    logi(info.offset + " bytes sent before, CRC does not match");
                    this.mService.sendLogBroadcast(15, info.offset + " bytes sent before, CRC does not match");
                    this.mProgressInfo.setBytesSent(bytesSentAndExecuted);
                    this.mProgressInfo.setBytesReceived(bytesSentAndExecuted);
                    info.offset -= bytesSentNotExecuted;
                    info.CRC32 = 0;
                    this.mFirmwareStream.reset();
                    logi("Resuming from byte " + info.offset + "...");
                    this.mService.sendLogBroadcast(10, "Resuming from byte " + info.offset + "...");
                }
            } catch (IOException e) {
                loge("Error while reading firmware stream", e);
                this.mService.terminateConnection(gatt, 4100);
                return;
            }
        } else {
            this.mProgressInfo.setBytesSent(0);
        }
        long startTime = SystemClock.elapsedRealtime();
        if (info.offset < this.mImageSizeInBytes) {
            int attempt = 1;
            while (this.mProgressInfo.getAvailableObjectSizeIsBytes() > 0) {
                if (!resumeSendingData) {
                    int availableObjectSizeInBytes = this.mProgressInfo.getAvailableObjectSizeIsBytes();
                    logi("Creating Data object (Op Code = 1, Type = 2, Size = " + availableObjectSizeInBytes + ") (" + (currentChunk + 1) + "/" + chunkCount + ")");
                    writeCreateRequest(2, availableObjectSizeInBytes);
                    this.mService.sendLogBroadcast(10, "Data object (" + (currentChunk + 1) + "/" + chunkCount + ") created");
                    this.mService.sendLogBroadcast(10, "Uploading firmware...");
                } else {
                    this.mService.sendLogBroadcast(10, "Resuming uploading firmware...");
                    resumeSendingData = false;
                }
                try {
                    logi("Uploading firmware...");
                    uploadFirmwareImage(this.mPacketCharacteristic);
                    logi("Sending Calculate Checksum command (Op Code = 3)");
                    ObjectChecksum checksum = readChecksum();
                    logi(String.format(Locale.US, "Checksum received (Offset = %d, CRC = %08X)", new Object[]{Integer.valueOf(checksum.offset), Integer.valueOf(checksum.CRC32)}));
                    this.mService.sendLogBroadcast(10, String.format(Locale.US, "Checksum received (Offset = %d, CRC = %08X)", new Object[]{Integer.valueOf(checksum.offset), Integer.valueOf(checksum.CRC32)}));
                    int bytesLost = this.mProgressInfo.getBytesSent() - checksum.offset;
                    if (bytesLost > 0) {
                        logw(bytesLost + " bytes were lost!");
                        this.mService.sendLogBroadcast(15, bytesLost + " bytes were lost");
                        try {
                            this.mFirmwareStream.reset();
                            this.mFirmwareStream.read(new byte[(info.maxSize - bytesLost)]);
                            this.mProgressInfo.setBytesSent(checksum.offset);
                            this.mPacketsBeforeNotification = 1;
                            setPacketReceiptNotifications(1);
                            this.mService.sendLogBroadcast(10, "Packet Receipt Notif Req (Op Code = 2) sent (Value = " + 1 + ")");
                        } catch (IOException e2) {
                            loge("Error while reading firmware stream", e2);
                            this.mService.terminateConnection(gatt, 4100);
                            return;
                        }
                    }
                    int crc = (int) (((ArchiveInputStream) this.mFirmwareStream).getCrc32() & 4294967295L);
                    if (crc != checksum.CRC32) {
                        String crcFailMessage = String.format(Locale.US, "CRC does not match! Expected %08X but found %08X.", new Object[]{Integer.valueOf(crc), Integer.valueOf(checksum.CRC32)});
                        if (attempt < 3) {
                            attempt++;
                            String crcFailMessage2 = crcFailMessage + String.format(Locale.US, " Retrying...(%d/%d)", new Object[]{Integer.valueOf(attempt), Integer.valueOf(3)});
                            logi(crcFailMessage2);
                            this.mService.sendLogBroadcast(15, crcFailMessage2);
                            try {
                                this.mFirmwareStream.reset();
                                this.mProgressInfo.setBytesSent(((ArchiveInputStream) this.mFirmwareStream).getBytesRead());
                            } catch (IOException e3) {
                                loge("Error while resetting the firmware stream", e3);
                                this.mService.terminateConnection(gatt, 4100);
                                return;
                            }
                        } else {
                            loge(crcFailMessage);
                            this.mService.sendLogBroadcast(20, crcFailMessage);
                            this.mService.terminateConnection(gatt, DfuBaseService.ERROR_CRC_ERROR);
                            return;
                        }
                    } else if (bytesLost > 0) {
                        resumeSendingData = true;
                    } else {
                        logi("Executing data object (Op Code = 4)");
                        writeExecute();
                        this.mService.sendLogBroadcast(10, "Data object executed");
                        currentChunk++;
                        attempt = 1;
                        this.mFirmwareStream.mark(0);
                    }
                } catch (DeviceDisconnectedException e4) {
                    loge("Disconnected while sending data");
                    throw e4;
                }
            }
        } else {
            logi("Executing data object (Op Code = 4)");
            writeExecute();
            this.mService.sendLogBroadcast(10, "Data object executed");
        }
        long endTime = SystemClock.elapsedRealtime();
        logi("Transfer of " + (this.mProgressInfo.getBytesSent() - info.offset) + " bytes has taken " + (endTime - startTime) + " ms");
        this.mService.sendLogBroadcast(10, "Upload completed in " + (endTime - startTime) + " ms");
    }

    private int getStatusCode(byte[] response, int request) throws UnknownResponseException {
        if (response != null && response.length >= 3 && response[0] == 96 && response[1] == request && (response[2] == 1 || response[2] == 2 || response[2] == 3 || response[2] == 4 || response[2] == 5 || response[2] == 7 || response[2] == 8 || response[2] == 10 || response[2] == 11)) {
            return response[2];
        }
        throw new UnknownResponseException("Invalid response received", response, 96, request);
    }

    private void setNumberOfPackets(byte[] data, int value) {
        data[1] = (byte) (value & 255);
        data[2] = (byte) ((value >> 8) & 255);
    }

    private void setObjectSize(byte[] data, int value) {
        data[2] = (byte) (value & 255);
        data[3] = (byte) ((value >> 8) & 255);
        data[4] = (byte) ((value >> 16) & 255);
        data[5] = (byte) ((value >> 24) & 255);
    }

    private void setPacketReceiptNotifications(int number) throws DfuException, DeviceDisconnectedException, UploadAbortedException, UnknownResponseException, RemoteDfuException {
        if (!this.mConnected) {
            throw new DeviceDisconnectedException("Unable to read Checksum: device disconnected");
        }
        logi("Sending the number of packets before notifications (Op Code = 2, Value = " + number + ")");
        setNumberOfPackets(OP_CODE_PACKET_RECEIPT_NOTIF_REQ, number);
        writeOpCode(this.mControlPointCharacteristic, OP_CODE_PACKET_RECEIPT_NOTIF_REQ);
        byte[] response = readNotificationResponse();
        int status = getStatusCode(response, 2);
        if (status == 11) {
            throw new RemoteDfuExtendedErrorException("Sending the number of packets failed", response[3]);
        } else if (status != 1) {
            throw new RemoteDfuException("Sending the number of packets failed", status);
        }
    }

    private void writeOpCode(BluetoothGattCharacteristic characteristic, byte[] value) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        writeOpCode(characteristic, value, false);
    }

    private void writeCreateRequest(int type, int size) throws DeviceDisconnectedException, DfuException, UploadAbortedException, RemoteDfuException, UnknownResponseException {
        if (!this.mConnected) {
            throw new DeviceDisconnectedException("Unable to create object: device disconnected");
        }
        byte[] data = type == 1 ? OP_CODE_CREATE_COMMAND : OP_CODE_CREATE_DATA;
        setObjectSize(data, size);
        writeOpCode(this.mControlPointCharacteristic, data);
        byte[] response = readNotificationResponse();
        int status = getStatusCode(response, 1);
        if (status == 11) {
            throw new RemoteDfuExtendedErrorException("Creating Command object failed", response[3]);
        } else if (status != 1) {
            throw new RemoteDfuException("Creating Command object failed", status);
        }
    }

    private ObjectInfo selectObject(int type) throws DeviceDisconnectedException, DfuException, UploadAbortedException, RemoteDfuException, UnknownResponseException {
        if (!this.mConnected) {
            throw new DeviceDisconnectedException("Unable to read object info: device disconnected");
        }
        OP_CODE_SELECT_OBJECT[1] = (byte) type;
        writeOpCode(this.mControlPointCharacteristic, OP_CODE_SELECT_OBJECT);
        byte[] response = readNotificationResponse();
        int status = getStatusCode(response, 6);
        if (status == 11) {
            throw new RemoteDfuExtendedErrorException("Selecting object failed", response[3]);
        } else if (status != 1) {
            throw new RemoteDfuException("Selecting object failed", status);
        } else {
            ObjectInfo info = new ObjectInfo();
            info.maxSize = this.mControlPointCharacteristic.getIntValue(20, 3).intValue();
            info.offset = this.mControlPointCharacteristic.getIntValue(20, 7).intValue();
            info.CRC32 = this.mControlPointCharacteristic.getIntValue(20, 11).intValue();
            return info;
        }
    }

    private ObjectChecksum readChecksum() throws DeviceDisconnectedException, DfuException, UploadAbortedException, RemoteDfuException, UnknownResponseException {
        if (!this.mConnected) {
            throw new DeviceDisconnectedException("Unable to read Checksum: device disconnected");
        }
        writeOpCode(this.mControlPointCharacteristic, OP_CODE_CALCULATE_CHECKSUM);
        byte[] response = readNotificationResponse();
        int status = getStatusCode(response, 3);
        if (status == 11) {
            throw new RemoteDfuExtendedErrorException("Receiving Checksum failed", response[3]);
        } else if (status != 1) {
            throw new RemoteDfuException("Receiving Checksum failed", status);
        } else {
            ObjectChecksum checksum = new ObjectChecksum();
            checksum.offset = this.mControlPointCharacteristic.getIntValue(20, 3).intValue();
            checksum.CRC32 = this.mControlPointCharacteristic.getIntValue(20, 7).intValue();
            return checksum;
        }
    }

    private void writeExecute() throws DfuException, DeviceDisconnectedException, UploadAbortedException, UnknownResponseException, RemoteDfuException {
        if (!this.mConnected) {
            throw new DeviceDisconnectedException("Unable to read Checksum: device disconnected");
        }
        writeOpCode(this.mControlPointCharacteristic, OP_CODE_EXECUTE);
        byte[] response = readNotificationResponse();
        int status = getStatusCode(response, 4);
        if (status == 11) {
            throw new RemoteDfuExtendedErrorException("Executing object failed", response[3]);
        } else if (status != 1) {
            throw new RemoteDfuException("Executing object failed", status);
        }
    }
}
