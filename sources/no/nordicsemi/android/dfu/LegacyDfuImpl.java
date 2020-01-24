package no.nordicsemi.android.dfu;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import java.util.UUID;
import no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException;
import no.nordicsemi.android.dfu.internal.exception.DfuException;
import no.nordicsemi.android.dfu.internal.exception.UnknownResponseException;
import no.nordicsemi.android.dfu.internal.exception.UploadAbortedException;

class LegacyDfuImpl extends BaseCustomDfuImpl {
    protected static final UUID DEFAULT_DFU_CONTROL_POINT_UUID = new UUID(23300500811742L, 1523193452336828707L);
    protected static final UUID DEFAULT_DFU_PACKET_UUID = new UUID(23304795779038L, 1523193452336828707L);
    protected static final UUID DEFAULT_DFU_SERVICE_UUID = new UUID(23296205844446L, 1523193452336828707L);
    protected static final UUID DEFAULT_DFU_VERSION_UUID = new UUID(23313385713630L, 1523193452336828707L);
    protected static UUID DFU_CONTROL_POINT_UUID = DEFAULT_DFU_CONTROL_POINT_UUID;
    protected static UUID DFU_PACKET_UUID = DEFAULT_DFU_PACKET_UUID;
    protected static UUID DFU_SERVICE_UUID = DEFAULT_DFU_SERVICE_UUID;
    private static final int DFU_STATUS_SUCCESS = 1;
    protected static UUID DFU_VERSION_UUID = DEFAULT_DFU_VERSION_UUID;
    private static final byte[] OP_CODE_ACTIVATE_AND_RESET = {5};
    private static final int OP_CODE_ACTIVATE_AND_RESET_KEY = 5;
    private static final byte[] OP_CODE_INIT_DFU_PARAMS = {2};
    private static final byte[] OP_CODE_INIT_DFU_PARAMS_COMPLETE = {2, 1};
    private static final int OP_CODE_INIT_DFU_PARAMS_KEY = 2;
    private static final byte[] OP_CODE_INIT_DFU_PARAMS_START = {2, 0};
    private static final int OP_CODE_PACKET_RECEIPT_NOTIF_KEY = 17;
    private static final byte[] OP_CODE_PACKET_RECEIPT_NOTIF_REQ = {8, 0, 0};
    private static final int OP_CODE_PACKET_RECEIPT_NOTIF_REQ_KEY = 8;
    private static final byte[] OP_CODE_RECEIVE_FIRMWARE_IMAGE = {3};
    private static final int OP_CODE_RECEIVE_FIRMWARE_IMAGE_KEY = 3;
    private static final byte[] OP_CODE_RESET = {6};
    private static final int OP_CODE_RESET_KEY = 6;
    private static final int OP_CODE_RESPONSE_CODE_KEY = 16;
    private static final byte[] OP_CODE_START_DFU = {1, 0};
    private static final int OP_CODE_START_DFU_KEY = 1;
    private static final byte[] OP_CODE_START_DFU_V1 = {1};
    private static final byte[] OP_CODE_VALIDATE = {4};
    private static final int OP_CODE_VALIDATE_KEY = 4;
    private final LegacyBluetoothCallback mBluetoothCallback = new LegacyBluetoothCallback();
    private BluetoothGattCharacteristic mControlPointCharacteristic;
    /* access modifiers changed from: private */
    public boolean mImageSizeInProgress;
    private BluetoothGattCharacteristic mPacketCharacteristic;

    protected class LegacyBluetoothCallback extends BaseCustomBluetoothCallback {
        protected LegacyBluetoothCallback() {
            super();
        }

        /* access modifiers changed from: protected */
        public void onPacketCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (LegacyDfuImpl.this.mImageSizeInProgress) {
                LegacyDfuImpl.this.mService.sendLogBroadcast(5, "Data written to " + characteristic.getUuid() + ", value (0x): " + parse(characteristic));
                LegacyDfuImpl.this.mImageSizeInProgress = false;
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            switch (characteristic.getIntValue(17, 0).intValue()) {
                case 17:
                    LegacyDfuImpl.this.mProgressInfo.setBytesReceived(characteristic.getIntValue(20, 1).intValue());
                    handlePacketReceiptNotification(gatt, characteristic);
                    break;
                default:
                    if (!LegacyDfuImpl.this.mRemoteErrorOccurred) {
                        if (characteristic.getIntValue(17, 2).intValue() != 1) {
                            LegacyDfuImpl.this.mRemoteErrorOccurred = true;
                        }
                        handleNotification(gatt, characteristic);
                        break;
                    }
                    break;
            }
            LegacyDfuImpl.this.notifyLock();
        }
    }

    LegacyDfuImpl(Intent intent, DfuBaseService service) {
        super(intent, service);
    }

    public boolean isClientCompatible(Intent intent, BluetoothGatt gatt) {
        BluetoothGattService dfuService = gatt.getService(DFU_SERVICE_UUID);
        if (dfuService == null) {
            return false;
        }
        BluetoothGattCharacteristic characteristic = dfuService.getCharacteristic(DFU_CONTROL_POINT_UUID);
        if (characteristic == null || characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG) == null) {
            return false;
        }
        this.mControlPointCharacteristic = characteristic;
        this.mPacketCharacteristic = dfuService.getCharacteristic(DFU_PACKET_UUID);
        if (this.mPacketCharacteristic != null) {
            return true;
        }
        return false;
    }

    public BaseCustomBluetoothCallback getGattCallback() {
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

    /* JADX WARNING: Code restructure failed: missing block: B:121:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x013d, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x013e, code lost:
        logi("Sending Reset command (Op Code = 6)");
        r30.mAborted = false;
        writeOpCode(r30.mControlPointCharacteristic, OP_CODE_RESET);
        r30.mService.sendLogBroadcast(10, "Reset request sent");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x016f, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x02d8, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x02e3, code lost:
        if (r6.getErrorNumber() != 3) goto L_0x02e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x02e5, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x02e6, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x02f1, code lost:
        if (r7.getErrorNumber() != 3) goto L_0x02f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x02f3, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x02f4, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x02f5, code lost:
        loge(r6.getMessage());
        r30.mService.sendLogBroadcast(20, r6.getMessage());
        logi("Sending Reset command (Op Code = 6)");
        writeOpCode(r30.mControlPointCharacteristic, OP_CODE_RESET);
        r30.mService.sendLogBroadcast(10, "Reset request sent");
        r30.mService.terminateConnection(r13, no.nordicsemi.android.dfu.DfuBaseService.ERROR_INVALID_RESPONSE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0364, code lost:
        if ((r12 & 4) <= 0) goto L_0x051f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        r30.mRemoteErrorOccurred = false;
        logw("DFU target does not support (SD/BL)+App update");
        r30.mService.sendLogBroadcast(15, "DFU target does not support (SD/BL)+App update");
        r12 = r12 & -5;
        r30.mFileType = r12;
        OP_CODE_START_DFU[1] = (byte) r12;
        r30.mProgressInfo.setTotalPart(2);
        ((no.nordicsemi.android.dfu.internal.ArchiveInputStream) r30.mFirmwareStream).setContentType(r12);
        r30.mService.sendLogBroadcast(1, "Sending only SD/BL");
        logi("Resending Start DFU command (Op Code = 1, Upload Mode = " + r12 + ")");
        writeOpCode(r30.mControlPointCharacteristic, OP_CODE_START_DFU);
        r30.mService.sendLogBroadcast(10, "DFU Start sent (Op Code = 1, Upload Mode = " + r12 + ")");
        logi("Sending image size array to DFU Packet: [" + r16 + "b, " + r5 + "b, " + 0 + "b]");
        writeImageSize(r30.mPacketCharacteristic, r16, r5, 0);
        r30.mService.sendLogBroadcast(10, "Firmware image size sent [" + r16 + "b, " + r5 + "b, " + 0 + "b]");
        r15 = readNotificationResponse();
        r17 = getStatusCode(r15, 1);
        r30.mService.sendLogBroadcast(10, "Response received (Op Code = " + r15[1] + " Status = " + r17 + ")");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x04fd, code lost:
        if (r17 == 2) goto L_0x04ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x04ff, code lost:
        resetAndRestart(r13, r31);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x050e, code lost:
        if (r17 != 1) goto L_0x0510;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0510, code lost:
        r0 = new no.nordicsemi.android.dfu.internal.exception.RemoteDfuException("Starting DFU failed", r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x051e, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x051f, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0524, code lost:
        if (r12 == 4) goto L_0x0526;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        r30.mRemoteErrorOccurred = false;
        r11 = false;
        logw("DFU target does not support DFU v.2");
        r30.mService.sendLogBroadcast(15, "DFU target does not support DFU v.2");
        r30.mService.sendLogBroadcast(1, "Switching to DFU v.1");
        logi("Resending Start DFU command (Op Code = 1)");
        writeOpCode(r30.mControlPointCharacteristic, OP_CODE_START_DFU_V1);
        r30.mService.sendLogBroadcast(10, "DFU Start sent (Op Code = 1)");
        logi("Sending application image size to DFU Packet: " + r30.mImageSizeInBytes + " bytes");
        writeImageSize(r30.mPacketCharacteristic, r30.mImageSizeInBytes);
        r30.mService.sendLogBroadcast(10, "Firmware image size sent (" + r30.mImageSizeInBytes + " bytes)");
        r15 = readNotificationResponse();
        r17 = getStatusCode(r15, 1);
        r30.mService.sendLogBroadcast(10, "Response received (Op Code = " + r15[1] + ", Status = " + r17 + ")");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0634, code lost:
        if (r17 == 2) goto L_0x0636;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0636, code lost:
        resetAndRestart(r13, r31);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x06d5, code lost:
        if (r17 != 1) goto L_0x06d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        r0 = new no.nordicsemi.android.dfu.internal.exception.RemoteDfuException("Starting DFU failed", r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x06e5, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x06e6, code lost:
        throw r7;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x013d A[ExcHandler: UploadAbortedException (r6v2 'e' no.nordicsemi.android.dfu.internal.exception.UploadAbortedException A[CUSTOM_DECLARE]), Splitter:B:6:0x0089] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x02f4 A[ExcHandler: UnknownResponseException (r6v1 'e' no.nordicsemi.android.dfu.internal.exception.UnknownResponseException A[CUSTOM_DECLARE]), Splitter:B:6:0x0089] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void performDfu(android.content.Intent r31) throws no.nordicsemi.android.dfu.internal.exception.DfuException, no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException, no.nordicsemi.android.dfu.internal.exception.UploadAbortedException {
        /*
            r30 = this;
            java.lang.String r23 = "Legacy DFU bootloader found"
            r0 = r30
            r1 = r23
            r0.logw(r1)
            r0 = r30
            no.nordicsemi.android.dfu.DfuProgressInfo r0 = r0.mProgressInfo
            r23 = r0
            r24 = -2
            r23.setProgress(r24)
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService
            r23 = r0
            r24 = 1000(0x3e8, float:1.401E-42)
            r23.waitFor(r24)
            r0 = r30
            android.bluetooth.BluetoothGatt r13 = r0.mGatt
            java.util.UUID r23 = DFU_SERVICE_UUID
            r0 = r23
            android.bluetooth.BluetoothGattService r23 = r13.getService(r0)
            java.util.UUID r24 = DFU_VERSION_UUID
            android.bluetooth.BluetoothGattCharacteristic r21 = r23.getCharacteristic(r24)
            r0 = r30
            r1 = r21
            int r20 = r0.readVersion(r1)
            r23 = 5
            r0 = r20
            r1 = r23
            if (r0 < r1) goto L_0x0087
            r0 = r30
            java.io.InputStream r0 = r0.mInitPacketStream
            r23 = r0
            if (r23 != 0) goto L_0x0087
            java.lang.StringBuilder r23 = new java.lang.StringBuilder
            r23.<init>()
            java.lang.String r24 = "Init packet not set for the DFU Bootloader version "
            java.lang.StringBuilder r23 = r23.append(r24)
            r0 = r23
            r1 = r20
            java.lang.StringBuilder r23 = r0.append(r1)
            java.lang.String r23 = r23.toString()
            r0 = r30
            r1 = r23
            r0.logw(r1)
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService
            r23 = r0
            r24 = 20
            java.lang.String r25 = "The Init packet is required by this version DFU Bootloader"
            r23.sendLogBroadcast(r24, r25)
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService
            r23 = r0
            r24 = 4107(0x100b, float:5.755E-42)
            r0 = r23
            r1 = r24
            r0.terminateConnection(r13, r1)
        L_0x0086:
            return
        L_0x0087:
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 1
            r0 = r30
            r1 = r23
            r2 = r24
            r0.enableCCCD(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.String r25 = "Notifications enabled"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 1000(0x3e8, float:1.401E-42)
            r23.waitFor(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            int r12 = r0.mFileType     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r12 & 1
            if (r23 <= 0) goto L_0x0170
            r0 = r30
            int r0 = r0.mImageSizeInBytes     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r16 = r0
        L_0x00bf:
            r23 = r12 & 2
            if (r23 <= 0) goto L_0x0174
            r0 = r30
            int r5 = r0.mImageSizeInBytes     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x00c7:
            r23 = r12 & 4
            if (r23 <= 0) goto L_0x0177
            r0 = r30
            int r4 = r0.mImageSizeInBytes     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x00cf:
            r0 = r30
            java.io.InputStream r0 = r0.mFirmwareStream     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r0 = r23
            boolean r0 = r0 instanceof no.nordicsemi.android.dfu.internal.ArchiveInputStream     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            if (r23 == 0) goto L_0x0186
            r0 = r30
            java.io.InputStream r0 = r0.mFirmwareStream     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r22 = r0
            no.nordicsemi.android.dfu.internal.ArchiveInputStream r22 = (no.nordicsemi.android.dfu.internal.ArchiveInputStream) r22     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            boolean r23 = r22.isSecureDfuRequired()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            if (r23 == 0) goto L_0x017a
            java.lang.String r23 = "Secure DFU is required to upload selected firmware"
            r0 = r30
            r1 = r23
            r0.loge(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 20
            java.lang.String r25 = "The device does not support given firmware."
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = "Sending Reset command (Op Code = 6)"
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            byte[] r24 = OP_CODE_RESET     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.String r25 = "Reset request sent"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 4099(0x1003, float:5.744E-42)
            r0 = r23
            r1 = r24
            r0.terminateConnection(r13, r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            goto L_0x0086
        L_0x013d:
            r6 = move-exception
            java.lang.String r23 = "Sending Reset command (Op Code = 6)"
            r0 = r30
            r1 = r23
            r0.logi(r1)
            r23 = 0
            r0 = r23
            r1 = r30
            r1.mAborted = r0
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic
            r23 = r0
            byte[] r24 = OP_CODE_RESET
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService
            r23 = r0
            r24 = 10
            java.lang.String r25 = "Reset request sent"
            r23.sendLogBroadcast(r24, r25)
            throw r6
        L_0x0170:
            r16 = 0
            goto L_0x00bf
        L_0x0174:
            r5 = 0
            goto L_0x00c7
        L_0x0177:
            r4 = 0
            goto L_0x00cf
        L_0x017a:
            int r16 = r22.softDeviceImageSize()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            int r5 = r22.bootloaderImageSize()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            int r4 = r22.applicationImageSize()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x0186:
            r11 = 1
            byte[] r23 = OP_CODE_START_DFU     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r24 = 1
            byte r0 = (byte) r12     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r25 = r0
            r23[r24] = r25     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23.<init>()     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "Sending Start DFU command (Op Code = 1, Upload Mode = "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r12)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = ")"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r23 = r23.toString()     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            byte[] r24 = OP_CODE_START_DFU     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r25.<init>()     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "DFU Start sent (Op Code = 1, Upload Mode = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r25
            java.lang.StringBuilder r25 = r0.append(r12)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = ")"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r25 = r25.toString()     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23.<init>()     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "Sending image size array to DFU Packet ("
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r23
            r1 = r16
            java.lang.StringBuilder r23 = r0.append(r1)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "b, "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r5)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "b, "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r4)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "b)"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r23 = r23.toString()     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mPacketCharacteristic     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            r0 = r30
            r1 = r23
            r2 = r16
            r0.writeImageSize(r1, r2, r5, r4)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r25.<init>()     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "Firmware image size sent ("
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r25
            r1 = r16
            java.lang.StringBuilder r25 = r0.append(r1)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "b, "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r25
            java.lang.StringBuilder r25 = r0.append(r5)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "b, "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r25
            java.lang.StringBuilder r25 = r0.append(r4)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "b)"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r25 = r25.toString()     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            byte[] r15 = r30.readNotificationResponse()     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = 1
            r0 = r30
            r1 = r23
            int r17 = r0.getStatusCode(r15, r1)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r25.<init>()     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "Response received (Op Code = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r26 = 1
            byte r26 = r15[r26]     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = " Status = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r25
            r1 = r17
            java.lang.StringBuilder r25 = r0.append(r1)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = ")"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r25 = r25.toString()     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = 2
            r0 = r17
            r1 = r23
            if (r0 != r1) goto L_0x034b
            r0 = r30
            r1 = r31
            r0.resetAndRestart(r13, r1)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            goto L_0x0086
        L_0x02d8:
            r6 = move-exception
            int r23 = r6.getErrorNumber()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r24 = 3
            r0 = r23
            r1 = r24
            if (r0 == r1) goto L_0x0362
            throw r6     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
        L_0x02e6:
            r7 = move-exception
            int r23 = r7.getErrorNumber()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = 3
            r0 = r23
            r1 = r24
            if (r0 == r1) goto L_0x0520
            throw r7     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x02f4:
            r6 = move-exception
            r10 = 4104(0x1008, float:5.751E-42)
            java.lang.String r23 = r6.getMessage()
            r0 = r30
            r1 = r23
            r0.loge(r1)
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService
            r23 = r0
            r24 = 20
            java.lang.String r25 = r6.getMessage()
            r23.sendLogBroadcast(r24, r25)
            java.lang.String r23 = "Sending Reset command (Op Code = 6)"
            r0 = r30
            r1 = r23
            r0.logi(r1)
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic
            r23 = r0
            byte[] r24 = OP_CODE_RESET
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService
            r23 = r0
            r24 = 10
            java.lang.String r25 = "Reset request sent"
            r23.sendLogBroadcast(r24, r25)
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService
            r23 = r0
            r24 = 4104(0x1008, float:5.751E-42)
            r0 = r23
            r1 = r24
            r0.terminateConnection(r13, r1)
            goto L_0x0086
        L_0x034b:
            r23 = 1
            r0 = r17
            r1 = r23
            if (r0 == r1) goto L_0x06e7
            no.nordicsemi.android.dfu.internal.exception.RemoteDfuException r23 = new no.nordicsemi.android.dfu.internal.exception.RemoteDfuException     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "Starting DFU failed"
            r0 = r23
            r1 = r24
            r2 = r17
            r0.<init>(r1, r2)     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            throw r23     // Catch:{ RemoteDfuException -> 0x02d8, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
        L_0x0362:
            r23 = r12 & 4
            if (r23 <= 0) goto L_0x051f
            r23 = r12 & 3
            if (r23 <= 0) goto L_0x051f
            r23 = 0
            r0 = r23
            r1 = r30
            r1.mRemoteErrorOccurred = r0     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r23 = "DFU target does not support (SD/BL)+App update"
            r0 = r30
            r1 = r23
            r0.logw(r1)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            r24 = 15
            java.lang.String r25 = "DFU target does not support (SD/BL)+App update"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r12 = r12 & -5
            r0 = r30
            r0.mFileType = r12     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            byte[] r23 = OP_CODE_START_DFU     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r24 = 1
            byte r0 = (byte) r12     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r25 = r0
            r23[r24] = r25     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            no.nordicsemi.android.dfu.DfuProgressInfo r0 = r0.mProgressInfo     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            r24 = 2
            r23.setTotalPart(r24)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            java.io.InputStream r0 = r0.mFirmwareStream     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r22 = r0
            no.nordicsemi.android.dfu.internal.ArchiveInputStream r22 = (no.nordicsemi.android.dfu.internal.ArchiveInputStream) r22     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r22
            r0.setContentType(r12)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r4 = 0
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            r24 = 1
            java.lang.String r25 = "Sending only SD/BL"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23.<init>()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "Resending Start DFU command (Op Code = 1, Upload Mode = "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r12)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = ")"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r23 = r23.toString()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            byte[] r24 = OP_CODE_START_DFU     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r25.<init>()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "DFU Start sent (Op Code = 1, Upload Mode = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r25
            java.lang.StringBuilder r25 = r0.append(r12)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = ")"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r25 = r25.toString()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23.<init>()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "Sending image size array to DFU Packet: ["
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r23
            r1 = r16
            java.lang.StringBuilder r23 = r0.append(r1)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "b, "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r5)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "b, "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r4)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "b]"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r23 = r23.toString()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mPacketCharacteristic     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            r0 = r30
            r1 = r23
            r2 = r16
            r0.writeImageSize(r1, r2, r5, r4)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r25.<init>()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "Firmware image size sent ["
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r25
            r1 = r16
            java.lang.StringBuilder r25 = r0.append(r1)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "b, "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r25
            java.lang.StringBuilder r25 = r0.append(r5)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "b, "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r25
            java.lang.StringBuilder r25 = r0.append(r4)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "b]"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r25 = r25.toString()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            byte[] r15 = r30.readNotificationResponse()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = 1
            r0 = r30
            r1 = r23
            int r17 = r0.getStatusCode(r15, r1)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r25.<init>()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = "Response received (Op Code = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r26 = 1
            byte r26 = r15[r26]     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = " Status = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r0 = r25
            r1 = r17
            java.lang.StringBuilder r25 = r0.append(r1)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r26 = ")"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r25 = r25.toString()     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            r23 = 2
            r0 = r17
            r1 = r23
            if (r0 != r1) goto L_0x0508
            r0 = r30
            r1 = r31
            r0.resetAndRestart(r13, r1)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            goto L_0x0086
        L_0x0508:
            r23 = 1
            r0 = r17
            r1 = r23
            if (r0 == r1) goto L_0x06e7
            no.nordicsemi.android.dfu.internal.exception.RemoteDfuException r23 = new no.nordicsemi.android.dfu.internal.exception.RemoteDfuException     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            java.lang.String r24 = "Starting DFU failed"
            r0 = r23
            r1 = r24
            r2 = r17
            r0.<init>(r1, r2)     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
            throw r23     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
        L_0x051f:
            throw r6     // Catch:{ RemoteDfuException -> 0x02e6, UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4 }
        L_0x0520:
            r23 = 4
            r0 = r23
            if (r12 != r0) goto L_0x06e6
            r23 = 0
            r0 = r23
            r1 = r30
            r1.mRemoteErrorOccurred = r0     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r11 = 0
            java.lang.String r23 = "DFU target does not support DFU v.2"
            r0 = r30
            r1 = r23
            r0.logw(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 15
            java.lang.String r25 = "DFU target does not support DFU v.2"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 1
            java.lang.String r25 = "Switching to DFU v.1"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = "Resending Start DFU command (Op Code = 1)"
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            byte[] r24 = OP_CODE_START_DFU_V1     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.String r25 = "DFU Start sent (Op Code = 1)"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = "Sending application image size to DFU Packet: "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            int r0 = r0.mImageSizeInBytes     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = r0
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = " bytes"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = r23.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mPacketCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r0 = r30
            int r0 = r0.mImageSizeInBytes     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = r0
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeImageSize(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r25.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = "Firmware image size sent ("
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            int r0 = r0.mImageSizeInBytes     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r26 = r0
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = " bytes)"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r25 = r25.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            byte[] r15 = r30.readNotificationResponse()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = 1
            r0 = r30
            r1 = r23
            int r17 = r0.getStatusCode(r15, r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r25.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = "Response received (Op Code = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r26 = 1
            byte r26 = r15[r26]     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = ", Status = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r25
            r1 = r17
            java.lang.StringBuilder r25 = r0.append(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = ")"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r25 = r25.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = 2
            r0 = r17
            r1 = r23
            if (r0 != r1) goto L_0x06cf
            r0 = r30
            r1 = r31
            r0.resetAndRestart(r13, r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            goto L_0x0086
        L_0x063f:
            r6 = move-exception
            int r23 = r6.getErrorNumber()
            r0 = r23
            r10 = r0 | 256(0x100, float:3.59E-43)
            java.lang.StringBuilder r23 = new java.lang.StringBuilder
            r23.<init>()
            java.lang.String r24 = r6.getMessage()
            java.lang.StringBuilder r23 = r23.append(r24)
            java.lang.String r24 = ": "
            java.lang.StringBuilder r23 = r23.append(r24)
            java.lang.String r24 = no.nordicsemi.android.error.LegacyDfuError.parse(r10)
            java.lang.StringBuilder r23 = r23.append(r24)
            java.lang.String r23 = r23.toString()
            r0 = r30
            r1 = r23
            r0.loge(r1)
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService
            r23 = r0
            r24 = 20
            java.util.Locale r25 = java.util.Locale.US
            java.lang.String r26 = "Remote DFU error: %s"
            r27 = 1
            r0 = r27
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r27 = r0
            r28 = 0
            java.lang.String r29 = no.nordicsemi.android.error.LegacyDfuError.parse(r10)
            r27[r28] = r29
            java.lang.String r25 = java.lang.String.format(r25, r26, r27)
            r23.sendLogBroadcast(r24, r25)
            java.lang.String r23 = "Sending Reset command (Op Code = 6)"
            r0 = r30
            r1 = r23
            r0.logi(r1)
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic
            r23 = r0
            byte[] r24 = OP_CODE_RESET
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService
            r23 = r0
            r24 = 10
            java.lang.String r25 = "Reset request sent"
            r23.sendLogBroadcast(r24, r25)
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService
            r23 = r0
            r0 = r10 | 8192(0x2000, float:1.14794E-41)
            r24 = r0
            r0 = r23
            r1 = r24
            r0.terminateConnection(r13, r1)
            goto L_0x0086
        L_0x06cf:
            r23 = 1
            r0 = r17
            r1 = r23
            if (r0 == r1) goto L_0x06e7
            no.nordicsemi.android.dfu.internal.exception.RemoteDfuException r23 = new no.nordicsemi.android.dfu.internal.exception.RemoteDfuException     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = "Starting DFU failed"
            r0 = r23
            r1 = r24
            r2 = r17
            r0.<init>(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            throw r23     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x06e6:
            throw r7     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x06e7:
            r0 = r30
            java.io.InputStream r0 = r0.mInitPacketStream     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            if (r23 == 0) goto L_0x0830
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.String r25 = "Writing Initialize DFU Parameters..."
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            if (r11 == 0) goto L_0x07da
            java.lang.String r23 = "Sending the Initialize DFU Parameters START (Op Code = 2, Value = 0)"
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            byte[] r24 = OP_CODE_INIT_DFU_PARAMS_START     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = "Sending "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            int r0 = r0.mInitPacketSizeInBytes     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = r0
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = " bytes of init packet"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = r23.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mPacketCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 0
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeInitData(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = "Sending the Initialize DFU Parameters COMPLETE (Op Code = 2, Value = 1)"
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            byte[] r24 = OP_CODE_INIT_DFU_PARAMS_COMPLETE     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.String r25 = "Initialize DFU Parameters completed"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x077c:
            byte[] r15 = r30.readNotificationResponse()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = 2
            r0 = r30
            r1 = r23
            int r17 = r0.getStatusCode(r15, r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r25.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = "Response received (Op Code = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r26 = 1
            byte r26 = r15[r26]     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = ", Status = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r25
            r1 = r17
            java.lang.StringBuilder r25 = r0.append(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = ")"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r25 = r25.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = 1
            r0 = r17
            r1 = r23
            if (r0 == r1) goto L_0x0830
            no.nordicsemi.android.dfu.internal.exception.RemoteDfuException r23 = new no.nordicsemi.android.dfu.internal.exception.RemoteDfuException     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = "Device returned error after sending init packet"
            r0 = r23
            r1 = r24
            r2 = r17
            r0.<init>(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            throw r23     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x07da:
            java.lang.String r23 = "Sending the Initialize DFU Parameters (Op Code = 2)"
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            byte[] r24 = OP_CODE_INIT_DFU_PARAMS     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = "Sending "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            int r0 = r0.mInitPacketSizeInBytes     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = r0
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = " bytes of init packet"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = r23.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mPacketCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 0
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeInitData(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            goto L_0x077c
        L_0x0830:
            if (r11 != 0) goto L_0x0848
            r0 = r30
            int r0 = r0.mPacketsBeforeNotification     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            if (r23 <= 0) goto L_0x09bb
            r0 = r30
            int r0 = r0.mPacketsBeforeNotification     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            r0 = r23
            r1 = r24
            if (r0 > r1) goto L_0x09bb
        L_0x0848:
            r0 = r30
            int r14 = r0.mPacketsBeforeNotification     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x084c:
            if (r14 <= 0) goto L_0x08b8
            r0 = r30
            r0.mPacketsBeforeNotification = r14     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = "Sending the number of packets before notifications (Op Code = 8, Value = "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r14)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = ")"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = r23.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            byte[] r23 = OP_CODE_PACKET_RECEIPT_NOTIF_REQ     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r0.setNumberOfPackets(r1, r14)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            byte[] r24 = OP_CODE_PACKET_RECEIPT_NOTIF_REQ     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r25.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = "Packet Receipt Notif Req (Op Code = 8) sent (Value = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r25
            java.lang.StringBuilder r25 = r0.append(r14)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = ")"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r25 = r25.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x08b8:
            java.lang.String r23 = "Sending Receive Firmware Image request (Op Code = 3)"
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            byte[] r24 = OP_CODE_RECEIVE_FIRMWARE_IMAGE     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.String r25 = "Receive Firmware Image request sent"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            long r18 = android.os.SystemClock.elapsedRealtime()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuProgressInfo r0 = r0.mProgressInfo     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 0
            r23.setBytesSent(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = "Uploading firmware..."
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ DeviceDisconnectedException -> 0x09bf }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ DeviceDisconnectedException -> 0x09bf }
            r23 = r0
            r24 = 10
            java.lang.String r25 = "Uploading firmware..."
            r23.sendLogBroadcast(r24, r25)     // Catch:{ DeviceDisconnectedException -> 0x09bf }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mPacketCharacteristic     // Catch:{ DeviceDisconnectedException -> 0x09bf }
            r23 = r0
            r0 = r30
            r1 = r23
            r0.uploadFirmwareImage(r1)     // Catch:{ DeviceDisconnectedException -> 0x09bf }
            long r8 = android.os.SystemClock.elapsedRealtime()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            byte[] r15 = r30.readNotificationResponse()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = 3
            r0 = r30
            r1 = r23
            int r17 = r0.getStatusCode(r15, r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = "Response received (Op Code = "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = 0
            byte r24 = r15[r24]     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = ", Req Op Code = "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = 1
            byte r24 = r15[r24]     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = ", Status = "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = 2
            byte r24 = r15[r24]     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = ")"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = r23.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r25.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = "Response received (Op Code = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r26 = 1
            byte r26 = r15[r26]     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = ", Status = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r25
            r1 = r17
            java.lang.StringBuilder r25 = r0.append(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = ")"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r25 = r25.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = 1
            r0 = r17
            r1 = r23
            if (r0 == r1) goto L_0x09cb
            no.nordicsemi.android.dfu.internal.exception.RemoteDfuException r23 = new no.nordicsemi.android.dfu.internal.exception.RemoteDfuException     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = "Device returned error after sending file"
            r0 = r23
            r1 = r24
            r2 = r17
            r0.<init>(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            throw r23     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x09bb:
            r14 = 10
            goto L_0x084c
        L_0x09bf:
            r6 = move-exception
            java.lang.String r23 = "Disconnected while sending data"
            r0 = r30
            r1 = r23
            r0.loge(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            throw r6     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x09cb:
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = "Transfer of "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuProgressInfo r0 = r0.mProgressInfo     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = r0
            int r24 = r24.getBytesSent()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = " bytes has taken "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            long r24 = r8 - r18
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = " ms"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = r23.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r25.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = "Upload completed in "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            long r26 = r8 - r18
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = " ms"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r25 = r25.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = "Sending Validate request (Op Code = 4)"
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            byte[] r24 = OP_CODE_VALIDATE     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.String r25 = "Validate request sent"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            byte[] r15 = r30.readNotificationResponse()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = 4
            r0 = r30
            r1 = r23
            int r17 = r0.getStatusCode(r15, r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = "Response received (Op Code = "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = 0
            byte r24 = r15[r24]     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = ", Req Op Code = "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = 1
            byte r24 = r15[r24]     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = ", Status = "
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r24 = 2
            byte r24 = r15[r24]     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = ")"
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = r23.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r25.<init>()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = "Response received (Op Code = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r26 = 1
            byte r26 = r15[r26]     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = ", Status = "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r25
            r1 = r17
            java.lang.StringBuilder r25 = r0.append(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r26 = ")"
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r25 = r25.toString()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = 1
            r0 = r17
            r1 = r23
            if (r0 == r1) goto L_0x0af7
            no.nordicsemi.android.dfu.internal.exception.RemoteDfuException r23 = new no.nordicsemi.android.dfu.internal.exception.RemoteDfuException     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r24 = "Device returned validation error"
            r0 = r23
            r1 = r24
            r2 = r17
            r0.<init>(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            throw r23     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
        L_0x0af7:
            r0 = r30
            no.nordicsemi.android.dfu.DfuProgressInfo r0 = r0.mProgressInfo     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = -5
            r23.setProgress(r24)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            java.lang.String r23 = "Sending Activate and Reset request (Op Code = 5)"
            r0 = r30
            r1 = r23
            r0.logi(r1)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            android.bluetooth.BluetoothGattCharacteristic r0 = r0.mControlPointCharacteristic     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            byte[] r24 = OP_CODE_ACTIVATE_AND_RESET     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            r1 = r23
            r2 = r24
            r0.writeOpCode(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 10
            java.lang.String r25 = "Activate and Reset request sent"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r23.waitUntilDisconnected()     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r0 = r30
            no.nordicsemi.android.dfu.DfuBaseService r0 = r0.mService     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = r0
            r24 = 5
            java.lang.String r25 = "Disconnected by the remote device"
            r23.sendLogBroadcast(r24, r25)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            r23 = 5
            r0 = r20
            r1 = r23
            if (r0 != r1) goto L_0x0b57
            r23 = 1
        L_0x0b4c:
            r0 = r30
            r1 = r31
            r2 = r23
            r0.finalize(r1, r2)     // Catch:{ UploadAbortedException -> 0x013d, UnknownResponseException -> 0x02f4, RemoteDfuException -> 0x063f }
            goto L_0x0086
        L_0x0b57:
            r23 = 0
            goto L_0x0b4c
        */
        throw new UnsupportedOperationException("Method not decompiled: no.nordicsemi.android.dfu.LegacyDfuImpl.performDfu(android.content.Intent):void");
    }

    private void setNumberOfPackets(byte[] data, int value) {
        data[1] = (byte) (value & 255);
        data[2] = (byte) ((value >> 8) & 255);
    }

    private int getStatusCode(byte[] response, int request) throws UnknownResponseException {
        if (response != null && response.length == 3 && response[0] == 16 && response[1] == request && response[2] >= 1 && response[2] <= 6) {
            return response[2];
        }
        throw new UnknownResponseException("Invalid response received", response, 16, request);
    }

    private int readVersion(BluetoothGattCharacteristic characteristic) {
        if (characteristic != null) {
            return characteristic.getIntValue(18, 0).intValue();
        }
        return 0;
    }

    private void writeOpCode(BluetoothGattCharacteristic characteristic, byte[] value) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        boolean reset = false;
        if (value[0] == 6 || value[0] == 5) {
            reset = true;
        }
        writeOpCode(characteristic, value, reset);
    }

    private void writeImageSize(BluetoothGattCharacteristic characteristic, int imageSize) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        this.mReceivedData = null;
        this.mError = 0;
        this.mImageSizeInProgress = true;
        characteristic.setWriteType(1);
        characteristic.setValue(new byte[4]);
        characteristic.setValue(imageSize, 20, 0);
        this.mService.sendLogBroadcast(1, "Writing to characteristic " + characteristic.getUuid());
        this.mService.sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + ")");
        this.mGatt.writeCharacteristic(characteristic);
        try {
            synchronized (this.mLock) {
                while (true) {
                    if ((!this.mImageSizeInProgress || !this.mConnected || this.mError != 0 || this.mAborted) && !this.mPaused) {
                        break;
                    }
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
        if (this.mAborted) {
            throw new UploadAbortedException();
        } else if (this.mError != 0) {
            throw new DfuException("Unable to write Image Size", this.mError);
        } else if (!this.mConnected) {
            throw new DeviceDisconnectedException("Unable to write Image Size: device disconnected");
        }
    }

    private void writeImageSize(BluetoothGattCharacteristic characteristic, int softDeviceImageSize, int bootloaderImageSize, int appImageSize) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        this.mReceivedData = null;
        this.mError = 0;
        this.mImageSizeInProgress = true;
        characteristic.setWriteType(1);
        characteristic.setValue(new byte[12]);
        characteristic.setValue(softDeviceImageSize, 20, 0);
        characteristic.setValue(bootloaderImageSize, 20, 4);
        characteristic.setValue(appImageSize, 20, 8);
        this.mService.sendLogBroadcast(1, "Writing to characteristic " + characteristic.getUuid());
        this.mService.sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + ")");
        this.mGatt.writeCharacteristic(characteristic);
        try {
            synchronized (this.mLock) {
                while (true) {
                    if ((!this.mImageSizeInProgress || !this.mConnected || this.mError != 0 || this.mAborted) && !this.mPaused) {
                        break;
                    }
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
        if (this.mAborted) {
            throw new UploadAbortedException();
        } else if (this.mError != 0) {
            throw new DfuException("Unable to write Image Sizes", this.mError);
        } else if (!this.mConnected) {
            throw new DeviceDisconnectedException("Unable to write Image Sizes: device disconnected");
        }
    }

    private void resetAndRestart(BluetoothGatt gatt, Intent intent) throws DfuException, DeviceDisconnectedException, UploadAbortedException {
        boolean hasServiceChanged;
        boolean z = true;
        this.mService.sendLogBroadcast(15, "Last upload interrupted. Restarting device...");
        this.mProgressInfo.setProgress(-5);
        logi("Sending Reset command (Op Code = 6)");
        writeOpCode(this.mControlPointCharacteristic, OP_CODE_RESET);
        this.mService.sendLogBroadcast(10, "Reset request sent");
        this.mService.waitUntilDisconnected();
        this.mService.sendLogBroadcast(5, "Disconnected by the remote device");
        BluetoothGattService gas = gatt.getService(GENERIC_ATTRIBUTE_SERVICE_UUID);
        if (gas == null || gas.getCharacteristic(SERVICE_CHANGED_UUID) == null) {
            hasServiceChanged = false;
        } else {
            hasServiceChanged = true;
        }
        DfuBaseService dfuBaseService = this.mService;
        if (hasServiceChanged) {
            z = false;
        }
        dfuBaseService.refreshDeviceCache(gatt, z);
        this.mService.close(gatt);
        logi("Restarting the service");
        Intent newIntent = new Intent();
        newIntent.fillIn(intent, 24);
        restartService(newIntent, false);
    }
}
