package no.nordicsemi.android.dfu;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.UUID;
import no.nordicsemi.android.dfu.DfuCallback.DfuGattCallback;
import no.nordicsemi.android.dfu.internal.ArchiveInputStream;
import no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException;
import no.nordicsemi.android.dfu.internal.exception.DfuException;
import no.nordicsemi.android.dfu.internal.exception.UploadAbortedException;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScannerFactory;

abstract class BaseDfuImpl implements DfuService {
    protected static final UUID CLIENT_CHARACTERISTIC_CONFIG = new UUID(45088566677504L, -9223371485494954757L);
    protected static final UUID GENERIC_ATTRIBUTE_SERVICE_UUID = new UUID(26392574038016L, -9223371485494954757L);
    protected static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    protected static final int INDICATIONS = 2;
    protected static final int MAX_PACKET_SIZE_DEFAULT = 20;
    protected static final int NOTIFICATIONS = 1;
    protected static final UUID SERVICE_CHANGED_UUID = new UUID(46200963207168L, -9223371485494954757L);
    private static final String TAG = "DfuImpl";
    protected boolean mAborted;
    protected byte[] mBuffer = new byte[20];
    protected boolean mConnected;
    protected int mError;
    protected int mFileType;
    protected InputStream mFirmwareStream;
    protected BluetoothGatt mGatt;
    protected int mImageSizeInBytes;
    protected int mInitPacketSizeInBytes;
    protected InputStream mInitPacketStream;
    protected final Object mLock = new Object();
    protected boolean mPaused;
    protected DfuProgressInfo mProgressInfo;
    protected byte[] mReceivedData = null;
    protected boolean mRequestCompleted;
    protected boolean mResetRequestSent;
    protected DfuBaseService mService;

    protected class BaseBluetoothGattCallback extends DfuGattCallback {
        protected BaseBluetoothGattCallback() {
        }

        public void onDisconnected() {
            BaseDfuImpl.this.mConnected = false;
            BaseDfuImpl.this.notifyLock();
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == 0) {
                BaseDfuImpl.this.mService.sendLogBroadcast(5, "Read Response received from " + characteristic.getUuid() + ", value (0x): " + parse(characteristic));
                BaseDfuImpl.this.mReceivedData = characteristic.getValue();
                BaseDfuImpl.this.mRequestCompleted = true;
            } else {
                BaseDfuImpl.this.loge("Characteristic read error: " + status);
                BaseDfuImpl.this.mError = status | 16384;
            }
            BaseDfuImpl.this.notifyLock();
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (status != 0) {
                BaseDfuImpl.this.loge("Descriptor read error: " + status);
                BaseDfuImpl.this.mError = status | 16384;
            } else if (BaseDfuImpl.CLIENT_CHARACTERISTIC_CONFIG.equals(descriptor.getUuid())) {
                BaseDfuImpl.this.mService.sendLogBroadcast(5, "Read Response received from descr." + descriptor.getCharacteristic().getUuid() + ", value (0x): " + parse(descriptor));
                if (BaseDfuImpl.SERVICE_CHANGED_UUID.equals(descriptor.getCharacteristic().getUuid())) {
                    BaseDfuImpl.this.mRequestCompleted = true;
                } else {
                    BaseDfuImpl.this.loge("Unknown descriptor read");
                }
            }
            BaseDfuImpl.this.notifyLock();
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (status != 0) {
                BaseDfuImpl.this.loge("Descriptor write error: " + status);
                BaseDfuImpl.this.mError = status | 16384;
            } else if (BaseDfuImpl.CLIENT_CHARACTERISTIC_CONFIG.equals(descriptor.getUuid())) {
                BaseDfuImpl.this.mService.sendLogBroadcast(5, "Data written to descr." + descriptor.getCharacteristic().getUuid() + ", value (0x): " + parse(descriptor));
                if (BaseDfuImpl.SERVICE_CHANGED_UUID.equals(descriptor.getCharacteristic().getUuid())) {
                    BaseDfuImpl.this.mService.sendLogBroadcast(1, "Indications enabled for " + descriptor.getCharacteristic().getUuid());
                } else {
                    BaseDfuImpl.this.mService.sendLogBroadcast(1, "Notifications enabled for " + descriptor.getCharacteristic().getUuid());
                }
            }
            BaseDfuImpl.this.notifyLock();
        }

        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            if (status == 0) {
                BaseDfuImpl.this.mService.sendLogBroadcast(5, "MTU changed to: " + mtu);
                if (mtu - 3 > BaseDfuImpl.this.mBuffer.length) {
                    BaseDfuImpl.this.mBuffer = new byte[(mtu - 3)];
                }
                BaseDfuImpl.this.logw("MTU changed to: " + mtu);
            } else {
                BaseDfuImpl.this.logw("Changing MTU failed: " + status + " (mtu: " + mtu + ")");
            }
            BaseDfuImpl.this.mRequestCompleted = true;
            BaseDfuImpl.this.notifyLock();
        }

        /* access modifiers changed from: protected */
        public String parse(BluetoothGattCharacteristic characteristic) {
            return parse(characteristic.getValue());
        }

        /* access modifiers changed from: protected */
        public String parse(BluetoothGattDescriptor descriptor) {
            return parse(descriptor.getValue());
        }

        private String parse(byte[] data) {
            if (data == null) {
                return "";
            }
            int length = data.length;
            if (length == 0) {
                return "";
            }
            char[] out = new char[((length * 3) - 1)];
            for (int j = 0; j < length; j++) {
                int v = data[j] & 255;
                out[j * 3] = BaseDfuImpl.HEX_ARRAY[v >>> 4];
                out[(j * 3) + 1] = BaseDfuImpl.HEX_ARRAY[v & 15];
                if (j != length - 1) {
                    out[(j * 3) + 2] = '-';
                }
            }
            return new String(out);
        }
    }

    BaseDfuImpl(Intent intent, DfuBaseService service) {
        this.mService = service;
        this.mProgressInfo = service.mProgressInfo;
        this.mConnected = true;
    }

    public void release() {
        this.mService = null;
    }

    public void pause() {
        this.mPaused = true;
    }

    public void resume() {
        this.mPaused = false;
        notifyLock();
    }

    public void abort() {
        this.mPaused = false;
        this.mAborted = true;
        notifyLock();
    }

    public void onBondStateChanged(int state) {
        this.mRequestCompleted = true;
        notifyLock();
    }

    public boolean initialize(Intent intent, BluetoothGatt gatt, int fileType, InputStream firmwareStream, InputStream initPacketStream) throws DfuException, DeviceDisconnectedException, UploadAbortedException {
        int size;
        int size2;
        this.mGatt = gatt;
        this.mFileType = fileType;
        this.mFirmwareStream = firmwareStream;
        this.mInitPacketStream = initPacketStream;
        int currentPart = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_PART_CURRENT", 1);
        int totalParts = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_PARTS_TOTAL", 1);
        if (fileType > 4) {
            logw("DFU target does not support (SD/BL)+App update, splitting into 2 parts");
            this.mService.sendLogBroadcast(15, "Sending system components");
            this.mFileType &= -5;
            totalParts = 2;
            ((ArchiveInputStream) this.mFirmwareStream).setContentType(this.mFileType);
        }
        if (currentPart == 2) {
            this.mService.sendLogBroadcast(15, "Sending application");
        }
        try {
            size = initPacketStream.available();
        } catch (Exception e) {
            size = 0;
        }
        this.mInitPacketSizeInBytes = size;
        try {
            size2 = firmwareStream.available();
        } catch (Exception e2) {
            size2 = 0;
        }
        this.mImageSizeInBytes = size2;
        this.mProgressInfo.init(size2, currentPart, totalParts);
        if (VERSION.SDK_INT < 23 && gatt.getDevice().getBondState() == 12) {
            BluetoothGattService genericAttributeService = gatt.getService(GENERIC_ATTRIBUTE_SERVICE_UUID);
            if (genericAttributeService != null) {
                BluetoothGattCharacteristic serviceChangedCharacteristic = genericAttributeService.getCharacteristic(SERVICE_CHANGED_UUID);
                if (serviceChangedCharacteristic != null) {
                    if (!isServiceChangedCCCDEnabled()) {
                        enableCCCD(serviceChangedCharacteristic, 2);
                    }
                    this.mService.sendLogBroadcast(10, "Service Changed indications enabled");
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void notifyLock() {
        synchronized (this.mLock) {
            this.mLock.notifyAll();
        }
    }

    /* access modifiers changed from: protected */
    public void waitIfPaused() {
        try {
            synchronized (this.mLock) {
                while (this.mPaused) {
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0113, code lost:
        if (r11.mError == 0) goto L_0x0119;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void enableCCCD(android.bluetooth.BluetoothGattCharacteristic r12, int r13) throws no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException, no.nordicsemi.android.dfu.internal.exception.DfuException, no.nordicsemi.android.dfu.internal.exception.UploadAbortedException {
        /*
            r11 = this;
            r10 = 2
            r7 = 0
            r6 = 1
            android.bluetooth.BluetoothGatt r4 = r11.mGatt
            if (r13 != r6) goto L_0x002f
            java.lang.String r1 = "notifications"
        L_0x000a:
            boolean r5 = r11.mConnected
            if (r5 != 0) goto L_0x0033
            no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException r5 = new no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unable to set "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = " state: device disconnected"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x002f:
            java.lang.String r1 = "indications"
            goto L_0x000a
        L_0x0033:
            boolean r5 = r11.mAborted
            if (r5 == 0) goto L_0x003d
            no.nordicsemi.android.dfu.internal.exception.UploadAbortedException r5 = new no.nordicsemi.android.dfu.internal.exception.UploadAbortedException
            r5.<init>()
            throw r5
        L_0x003d:
            r5 = 0
            r11.mReceivedData = r5
            r11.mError = r7
            java.util.UUID r5 = CLIENT_CHARACTERISTIC_CONFIG
            android.bluetooth.BluetoothGattDescriptor r2 = r12.getDescriptor(r5)
            byte[] r5 = r2.getValue()
            if (r5 == 0) goto L_0x0069
            byte[] r5 = r2.getValue()
            int r5 = r5.length
            if (r5 != r10) goto L_0x0069
            byte[] r5 = r2.getValue()
            byte r5 = r5[r7]
            if (r5 <= 0) goto L_0x0069
            byte[] r5 = r2.getValue()
            byte r5 = r5[r6]
            if (r5 != 0) goto L_0x0069
            r0 = r6
        L_0x0066:
            if (r0 == 0) goto L_0x006b
        L_0x0068:
            return
        L_0x0069:
            r0 = r7
            goto L_0x0066
        L_0x006b:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r8 = "Enabling "
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.String r8 = "..."
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            r11.logi(r5)
            no.nordicsemi.android.dfu.DfuBaseService r5 = r11.mService
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Enabling "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r1)
            java.lang.String r9 = " for "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.util.UUID r9 = r12.getUuid()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r5.sendLogBroadcast(r6, r8)
            no.nordicsemi.android.dfu.DfuBaseService r5 = r11.mService
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "gatt.setCharacteristicNotification("
            java.lang.StringBuilder r8 = r8.append(r9)
            java.util.UUID r9 = r12.getUuid()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = ", true)"
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r5.sendLogBroadcast(r7, r8)
            r4.setCharacteristicNotification(r12, r6)
            if (r13 != r6) goto L_0x013f
            byte[] r5 = android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
        L_0x00dc:
            r2.setValue(r5)
            no.nordicsemi.android.dfu.DfuBaseService r8 = r11.mService
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r9 = "gatt.writeDescriptor("
            java.lang.StringBuilder r5 = r5.append(r9)
            java.util.UUID r9 = r2.getUuid()
            java.lang.StringBuilder r9 = r5.append(r9)
            if (r13 != r6) goto L_0x0142
            java.lang.String r5 = ", value=0x01-00)"
        L_0x00fa:
            java.lang.StringBuilder r5 = r9.append(r5)
            java.lang.String r5 = r5.toString()
            r8.sendLogBroadcast(r7, r5)
            r4.writeDescriptor(r2)
            java.lang.Object r8 = r11.mLock     // Catch:{ InterruptedException -> 0x0173 }
            monitor-enter(r8)     // Catch:{ InterruptedException -> 0x0173 }
        L_0x010b:
            if (r0 != 0) goto L_0x0115
            boolean r5 = r11.mConnected     // Catch:{ all -> 0x0170 }
            if (r5 == 0) goto L_0x0115
            int r5 = r11.mError     // Catch:{ all -> 0x0170 }
            if (r5 == 0) goto L_0x0119
        L_0x0115:
            boolean r5 = r11.mPaused     // Catch:{ all -> 0x0170 }
            if (r5 == 0) goto L_0x0148
        L_0x0119:
            java.lang.Object r5 = r11.mLock     // Catch:{ all -> 0x0170 }
            r5.wait()     // Catch:{ all -> 0x0170 }
            byte[] r5 = r2.getValue()     // Catch:{ all -> 0x0170 }
            if (r5 == 0) goto L_0x0146
            byte[] r5 = r2.getValue()     // Catch:{ all -> 0x0170 }
            int r5 = r5.length     // Catch:{ all -> 0x0170 }
            if (r5 != r10) goto L_0x0146
            byte[] r5 = r2.getValue()     // Catch:{ all -> 0x0170 }
            r9 = 0
            byte r5 = r5[r9]     // Catch:{ all -> 0x0170 }
            if (r5 <= 0) goto L_0x0146
            byte[] r5 = r2.getValue()     // Catch:{ all -> 0x0170 }
            r9 = 1
            byte r5 = r5[r9]     // Catch:{ all -> 0x0170 }
            if (r5 != 0) goto L_0x0146
            r0 = r6
        L_0x013e:
            goto L_0x010b
        L_0x013f:
            byte[] r5 = android.bluetooth.BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
            goto L_0x00dc
        L_0x0142:
            java.lang.String r5 = ", value=0x02-00)"
            goto L_0x00fa
        L_0x0146:
            r0 = r7
            goto L_0x013e
        L_0x0148:
            monitor-exit(r8)     // Catch:{ all -> 0x0170 }
        L_0x0149:
            int r5 = r11.mError
            if (r5 == 0) goto L_0x017b
            no.nordicsemi.android.dfu.internal.exception.DfuException r5 = new no.nordicsemi.android.dfu.internal.exception.DfuException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unable to set "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = " state"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            int r7 = r11.mError
            r5.<init>(r6, r7)
            throw r5
        L_0x0170:
            r5 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0170 }
            throw r5     // Catch:{ InterruptedException -> 0x0173 }
        L_0x0173:
            r3 = move-exception
            java.lang.String r5 = "Sleeping interrupted"
            r11.loge(r5, r3)
            goto L_0x0149
        L_0x017b:
            boolean r5 = r11.mConnected
            if (r5 != 0) goto L_0x0068
            no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException r5 = new no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unable to set "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = " state: device disconnected"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: no.nordicsemi.android.dfu.BaseDfuImpl.enableCCCD(android.bluetooth.BluetoothGattCharacteristic, int):void");
    }

    private boolean isServiceChangedCCCDEnabled() throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        boolean z = true;
        if (!this.mConnected) {
            throw new DeviceDisconnectedException("Unable to read Service Changed CCCD: device disconnected");
        } else if (this.mAborted) {
            throw new UploadAbortedException();
        } else {
            BluetoothGatt gatt = this.mGatt;
            BluetoothGattService genericAttributeService = gatt.getService(GENERIC_ATTRIBUTE_SERVICE_UUID);
            if (genericAttributeService == null) {
                return false;
            }
            BluetoothGattCharacteristic serviceChangedCharacteristic = genericAttributeService.getCharacteristic(SERVICE_CHANGED_UUID);
            if (serviceChangedCharacteristic == null) {
                return false;
            }
            BluetoothGattDescriptor descriptor = serviceChangedCharacteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG);
            if (descriptor == null) {
                return false;
            }
            this.mRequestCompleted = false;
            this.mError = 0;
            logi("Reading Service Changed CCCD value...");
            this.mService.sendLogBroadcast(1, "Reading Service Changed CCCD value...");
            this.mService.sendLogBroadcast(0, "gatt.readDescriptor(" + descriptor.getUuid() + ")");
            gatt.readDescriptor(descriptor);
            try {
                synchronized (this.mLock) {
                    while (true) {
                        if ((this.mRequestCompleted || !this.mConnected || this.mError != 0) && !this.mPaused) {
                            break;
                        }
                        this.mLock.wait();
                    }
                }
            } catch (InterruptedException e) {
                loge("Sleeping interrupted", e);
            }
            if (this.mError != 0) {
                throw new DfuException("Unable to read Service Changed CCCD", this.mError);
            } else if (!this.mConnected) {
                throw new DeviceDisconnectedException("Unable to read Service Changed CCCD: device disconnected");
            } else {
                if (!(descriptor.getValue() != null && descriptor.getValue().length == 2 && descriptor.getValue()[0] == BluetoothGattDescriptor.ENABLE_INDICATION_VALUE[0] && descriptor.getValue()[1] == BluetoothGattDescriptor.ENABLE_INDICATION_VALUE[1])) {
                    z = false;
                }
                return z;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void writeOpCode(BluetoothGattCharacteristic characteristic, byte[] value, boolean reset) throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        if (this.mAborted) {
            throw new UploadAbortedException();
        }
        this.mReceivedData = null;
        this.mError = 0;
        this.mRequestCompleted = false;
        this.mResetRequestSent = reset;
        characteristic.setWriteType(2);
        characteristic.setValue(value);
        this.mService.sendLogBroadcast(1, "Writing to characteristic " + characteristic.getUuid());
        this.mService.sendLogBroadcast(0, "gatt.writeCharacteristic(" + characteristic.getUuid() + ")");
        this.mGatt.writeCharacteristic(characteristic);
        try {
            synchronized (this.mLock) {
                while (true) {
                    if ((this.mRequestCompleted || !this.mConnected || this.mError != 0) && !this.mPaused) {
                        break;
                    }
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
        if (!this.mResetRequestSent && this.mError != 0) {
            throw new DfuException("Unable to write Op Code " + value[0], this.mError);
        } else if (!this.mResetRequestSent && !this.mConnected) {
            throw new DeviceDisconnectedException("Unable to write Op Code " + value[0] + ": device disconnected");
        }
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"NewApi"})
    public boolean createBond() {
        boolean result = true;
        BluetoothDevice device = this.mGatt.getDevice();
        if (device.getBondState() != 12) {
            this.mRequestCompleted = false;
            this.mService.sendLogBroadcast(1, "Starting pairing...");
            if (VERSION.SDK_INT >= 19) {
                this.mService.sendLogBroadcast(0, "gatt.getDevice_name().createBond()");
                result = device.createBond();
            } else {
                result = createBondApi18(device);
            }
            try {
                synchronized (this.mLock) {
                    while (!this.mRequestCompleted && !this.mAborted) {
                        this.mLock.wait();
                    }
                }
            } catch (InterruptedException e) {
                loge("Sleeping interrupted", e);
            }
        }
        return result;
    }

    private boolean createBondApi18(BluetoothDevice device) {
        try {
            Method createBond = device.getClass().getMethod("createBond", new Class[0]);
            if (createBond != null) {
                this.mService.sendLogBroadcast(0, "gatt.getDevice_name().createBond() (hidden)");
                return ((Boolean) createBond.invoke(device, new Object[0])).booleanValue();
            }
        } catch (Exception e) {
            Log.w(TAG, "An exception occurred while creating bond", e);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean removeBond() {
        BluetoothDevice device = this.mGatt.getDevice();
        if (device.getBondState() == 10) {
            return true;
        }
        this.mService.sendLogBroadcast(1, "Removing bond information...");
        boolean z = false;
        try {
            Method removeBond = device.getClass().getMethod("removeBond", new Class[0]);
            if (removeBond != null) {
                this.mRequestCompleted = false;
                this.mService.sendLogBroadcast(0, "gatt.getDevice_name().removeBond() (hidden)");
                boolean result = ((Boolean) removeBond.invoke(device, new Object[0])).booleanValue();
                try {
                    synchronized (this.mLock) {
                        while (!this.mRequestCompleted && !this.mAborted) {
                            this.mLock.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    loge("Sleeping interrupted", e);
                }
            }
            return true;
        } catch (Exception e2) {
            Log.w(TAG, "An exception occurred while removing bond information", e2);
            return z;
        }
    }

    /* access modifiers changed from: protected */
    public boolean isBonded() {
        return this.mGatt.getDevice().getBondState() == 12;
    }

    /* access modifiers changed from: protected */
    @RequiresApi(api = 21)
    public void requestMtu(int mtu) throws DeviceDisconnectedException, UploadAbortedException {
        if (this.mAborted) {
            throw new UploadAbortedException();
        }
        this.mRequestCompleted = false;
        this.mService.sendLogBroadcast(1, "Requesting new MTU...");
        this.mService.sendLogBroadcast(0, "gatt.requestMtu(" + mtu + ")");
        if (this.mGatt.requestMtu(mtu)) {
            try {
                synchronized (this.mLock) {
                    while (true) {
                        if ((this.mRequestCompleted || !this.mConnected || this.mError != 0) && !this.mPaused) {
                            break;
                        }
                        this.mLock.wait();
                    }
                }
            } catch (InterruptedException e) {
                loge("Sleeping interrupted", e);
            }
            if (!this.mConnected) {
                throw new DeviceDisconnectedException("Unable to read Service Changed CCCD: device disconnected");
            }
        }
    }

    /* access modifiers changed from: protected */
    public byte[] readNotificationResponse() throws DeviceDisconnectedException, DfuException, UploadAbortedException {
        try {
            synchronized (this.mLock) {
                while (true) {
                    if ((this.mReceivedData != null || !this.mConnected || this.mError != 0 || this.mAborted) && !this.mPaused) {
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
            throw new DfuException("Unable to write Op Code", this.mError);
        } else if (this.mConnected) {
            return this.mReceivedData;
        } else {
            throw new DeviceDisconnectedException("Unable to write Op Code: device disconnected");
        }
    }

    /* access modifiers changed from: protected */
    public void restartService(Intent intent, boolean scanForBootloader) {
        String newAddress = null;
        if (scanForBootloader) {
            this.mService.sendLogBroadcast(1, "Scanning for the DFU Bootloader...");
            newAddress = BootloaderScannerFactory.getScanner().searchFor(this.mGatt.getDevice().getAddress());
            logi("Scanning for new address finished with: " + newAddress);
            if (newAddress != null) {
                this.mService.sendLogBroadcast(5, "DFU Bootloader found with address " + newAddress);
            } else {
                this.mService.sendLogBroadcast(5, "DFU Bootloader not found. Trying the same address...");
            }
        }
        if (newAddress != null) {
            intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", newAddress);
        }
        this.mService.startService(intent);
    }

    /* access modifiers changed from: protected */
    public String parse(byte[] data) {
        if (data == null) {
            return "";
        }
        int length = data.length;
        if (length == 0) {
            return "";
        }
        char[] out = new char[((length * 3) - 1)];
        for (int j = 0; j < length; j++) {
            int v = data[j] & 255;
            out[j * 3] = HEX_ARRAY[v >>> 4];
            out[(j * 3) + 1] = HEX_ARRAY[v & 15];
            if (j != length - 1) {
                out[(j * 3) + 2] = '-';
            }
        }
        return new String(out);
    }

    /* access modifiers changed from: 0000 */
    public void loge(String message) {
        Log.e(TAG, message);
    }

    /* access modifiers changed from: 0000 */
    public void loge(String message, Throwable e) {
        Log.e(TAG, message, e);
    }

    /* access modifiers changed from: 0000 */
    public void logw(String message) {
        if (DfuBaseService.DEBUG) {
            Log.w(TAG, message);
        }
    }

    /* access modifiers changed from: 0000 */
    public void logi(String message) {
        if (DfuBaseService.DEBUG) {
            Log.i(TAG, message);
        }
    }
}
