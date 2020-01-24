package no.nordicsemi.android.dfu;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.R;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Locale;
import no.nordicsemi.android.dfu.internal.ArchiveInputStream;
import no.nordicsemi.android.dfu.internal.HexInputStream;

public abstract class DfuBaseService extends IntentService implements ProgressListener {
    public static final int ACTION_ABORT = 2;
    public static final int ACTION_PAUSE = 0;
    public static final int ACTION_RESUME = 1;
    public static final String BROADCAST_ACTION = "no.nordicsemi.android.dfu.broadcast.BROADCAST_ACTION";
    public static final String BROADCAST_ERROR = "no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR";
    public static final String BROADCAST_LOG = "no.nordicsemi.android.dfu.broadcast.BROADCAST_LOG";
    public static final String BROADCAST_PROGRESS = "no.nordicsemi.android.dfu.broadcast.BROADCAST_PROGRESS";
    static boolean DEBUG = true;
    public static final int ERROR_BLUETOOTH_DISABLED = 4106;
    public static final int ERROR_CONNECTION_MASK = 16384;
    public static final int ERROR_CONNECTION_STATE_MASK = 32768;
    public static final int ERROR_CRC_ERROR = 4109;
    public static final int ERROR_DEVICE_DISCONNECTED = 4096;
    public static final int ERROR_DEVICE_NOT_BONDED = 4110;
    public static final int ERROR_FILE_ERROR = 4098;
    public static final int ERROR_FILE_INVALID = 4099;
    public static final int ERROR_FILE_IO_EXCEPTION = 4100;
    public static final int ERROR_FILE_NOT_FOUND = 4097;
    public static final int ERROR_FILE_SIZE_INVALID = 4108;
    public static final int ERROR_FILE_TYPE_UNSUPPORTED = 4105;
    public static final int ERROR_INIT_PACKET_REQUIRED = 4107;
    public static final int ERROR_INVALID_RESPONSE = 4104;
    public static final int ERROR_MASK = 4096;
    public static final int ERROR_REMOTE_MASK = 8192;
    public static final int ERROR_REMOTE_TYPE_LEGACY = 256;
    public static final int ERROR_REMOTE_TYPE_SECURE = 512;
    public static final int ERROR_REMOTE_TYPE_SECURE_BUTTONLESS = 2048;
    public static final int ERROR_REMOTE_TYPE_SECURE_EXTENDED = 1024;
    public static final int ERROR_SERVICE_DISCOVERY_NOT_STARTED = 4101;
    public static final int ERROR_SERVICE_NOT_FOUND = 4102;
    public static final int ERROR_TYPE_COMMUNICATION = 2;
    public static final int ERROR_TYPE_COMMUNICATION_STATE = 1;
    public static final int ERROR_TYPE_DFU_REMOTE = 3;
    public static final int ERROR_TYPE_OTHER = 0;
    public static final String EXTRA_ACTION = "no.nordicsemi.android.dfu.extra.EXTRA_ACTION";
    private static final String EXTRA_ATTEMPT = "no.nordicsemi.android.dfu.extra.EXTRA_ATTEMPT";
    public static final String EXTRA_AVG_SPEED_B_PER_MS = "no.nordicsemi.android.dfu.extra.EXTRA_AVG_SPEED_B_PER_MS";
    public static final String EXTRA_CUSTOM_UUIDS_FOR_BUTTONLESS_DFU_WITHOUT_BOND_SHARING = "no.nordicsemi.android.dfu.extra.EXTRA_CUSTOM_UUIDS_FOR_BUTTONLESS_DFU_WITHOUT_BOND_SHARING";
    public static final String EXTRA_CUSTOM_UUIDS_FOR_BUTTONLESS_DFU_WITH_BOND_SHARING = "no.nordicsemi.android.dfu.extra.EXTRA_CUSTOM_UUIDS_FOR_BUTTONLESS_DFU_WITH_BOND_SHARING";
    public static final String EXTRA_CUSTOM_UUIDS_FOR_EXPERIMENTAL_BUTTONLESS_DFU = "no.nordicsemi.android.dfu.extra.EXTRA_CUSTOM_UUIDS_FOR_EXPERIMENTAL_BUTTONLESS_DFU";
    public static final String EXTRA_CUSTOM_UUIDS_FOR_LEGACY_DFU = "no.nordicsemi.android.dfu.extra.EXTRA_CUSTOM_UUIDS_FOR_LEGACY_DFU";
    public static final String EXTRA_CUSTOM_UUIDS_FOR_SECURE_DFU = "no.nordicsemi.android.dfu.extra.EXTRA_CUSTOM_UUIDS_FOR_SECURE_DFU";
    public static final String EXTRA_DATA = "no.nordicsemi.android.dfu.extra.EXTRA_DATA";
    public static final String EXTRA_DEVICE_ADDRESS = "no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS";
    public static final String EXTRA_DEVICE_NAME = "no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME";
    public static final String EXTRA_DISABLE_NOTIFICATION = "no.nordicsemi.android.dfu.extra.EXTRA_DISABLE_NOTIFICATION";
    public static final String EXTRA_ERROR_TYPE = "no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE";
    public static final String EXTRA_FILE_MIME_TYPE = "no.nordicsemi.android.dfu.extra.EXTRA_MIME_TYPE";
    public static final String EXTRA_FILE_PATH = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH";
    public static final String EXTRA_FILE_RES_ID = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_RES_ID";
    public static final String EXTRA_FILE_TYPE = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE";
    public static final String EXTRA_FILE_URI = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_URI";
    public static final String EXTRA_FORCE_DFU = "no.nordicsemi.android.dfu.extra.EXTRA_FORCE_DFU";
    public static final String EXTRA_INIT_FILE_PATH = "no.nordicsemi.android.dfu.extra.EXTRA_INIT_FILE_PATH";
    public static final String EXTRA_INIT_FILE_RES_ID = "no.nordicsemi.android.dfu.extra.EXTRA_INIT_FILE_RES_ID";
    public static final String EXTRA_INIT_FILE_URI = "no.nordicsemi.android.dfu.extra.EXTRA_INIT_FILE_URI";
    public static final String EXTRA_KEEP_BOND = "no.nordicsemi.android.dfu.extra.EXTRA_KEEP_BOND";
    public static final String EXTRA_LOG_LEVEL = "no.nordicsemi.android.dfu.extra.EXTRA_LOG_LEVEL";
    public static final String EXTRA_LOG_MESSAGE = "no.nordicsemi.android.dfu.extra.EXTRA_LOG_INFO";
    public static final String EXTRA_PACKET_RECEIPT_NOTIFICATIONS_ENABLED = "no.nordicsemi.android.dfu.extra.EXTRA_PRN_ENABLED";
    public static final String EXTRA_PACKET_RECEIPT_NOTIFICATIONS_VALUE = "no.nordicsemi.android.dfu.extra.EXTRA_PRN_VALUE";
    public static final String EXTRA_PARTS_TOTAL = "no.nordicsemi.android.dfu.extra.EXTRA_PARTS_TOTAL";
    public static final String EXTRA_PART_CURRENT = "no.nordicsemi.android.dfu.extra.EXTRA_PART_CURRENT";
    public static final String EXTRA_PROGRESS = "no.nordicsemi.android.dfu.extra.EXTRA_PROGRESS";
    public static final String EXTRA_RESTORE_BOND = "no.nordicsemi.android.dfu.extra.EXTRA_RESTORE_BOND";
    public static final String EXTRA_SPEED_B_PER_MS = "no.nordicsemi.android.dfu.extra.EXTRA_SPEED_B_PER_MS";
    public static final String EXTRA_UNSAFE_EXPERIMENTAL_BUTTONLESS_DFU = "no.nordicsemi.android.dfu.extra.EXTRA_UNSAFE_EXPERIMENTAL_BUTTONLESS_DFU";
    public static final int LOG_LEVEL_APPLICATION = 10;
    public static final int LOG_LEVEL_DEBUG = 0;
    public static final int LOG_LEVEL_ERROR = 20;
    public static final int LOG_LEVEL_INFO = 5;
    public static final int LOG_LEVEL_VERBOSE = 1;
    public static final int LOG_LEVEL_WARNING = 15;
    public static final String MIME_TYPE_OCTET_STREAM = "application/octet-stream";
    public static final String MIME_TYPE_ZIP = "application/zip";
    public static final String NOTIFICATION_CHANNEL_DFU = "dfu";
    public static final int NOTIFICATION_ID = 283;
    public static final int PROGRESS_ABORTED = -7;
    public static final int PROGRESS_COMPLETED = -6;
    public static final int PROGRESS_CONNECTING = -1;
    public static final int PROGRESS_DISCONNECTING = -5;
    public static final int PROGRESS_ENABLING_DFU_MODE = -3;
    public static final int PROGRESS_STARTING = -2;
    public static final int PROGRESS_VALIDATING = -4;
    protected static final int STATE_CLOSED = -5;
    protected static final int STATE_CONNECTED = -2;
    protected static final int STATE_CONNECTED_AND_READY = -3;
    protected static final int STATE_CONNECTING = -1;
    protected static final int STATE_DISCONNECTED = 0;
    protected static final int STATE_DISCONNECTING = -4;
    private static final String TAG = "DfuBaseService";
    public static final int TYPE_APPLICATION = 4;
    public static final int TYPE_AUTO = 0;
    public static final int TYPE_BOOTLOADER = 2;
    public static final int TYPE_SOFT_DEVICE = 1;
    /* access modifiers changed from: private */
    public boolean mAborted;
    private BluetoothAdapter mBluetoothAdapter;
    private final BroadcastReceiver mBondStateBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")).getAddress().equals(DfuBaseService.this.mDeviceAddress)) {
                int bondState = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", -1);
                if (bondState != 11 && DfuBaseService.this.mDfuServiceImpl != null) {
                    DfuBaseService.this.mDfuServiceImpl.onBondStateChanged(bondState);
                }
            }
        }
    };
    protected int mConnectionState;
    private final BroadcastReceiver mConnectionStateBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")).getAddress().equals(DfuBaseService.this.mDeviceAddress)) {
                String action = intent.getAction();
                DfuBaseService.this.logi("Action received: " + action);
                DfuBaseService.this.sendLogBroadcast(0, "[Broadcast] Action received: " + action);
            }
        }
    };
    /* access modifiers changed from: private */
    public String mDeviceAddress;
    private String mDeviceName;
    private final BroadcastReceiver mDfuActionReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int action = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_ACTION", 0);
            DfuBaseService.this.logi("User action received: " + action);
            switch (action) {
                case 0:
                    DfuBaseService.this.sendLogBroadcast(15, "[Broadcast] Pause action received");
                    if (DfuBaseService.this.mDfuServiceImpl != null) {
                        DfuBaseService.this.mDfuServiceImpl.pause();
                        return;
                    }
                    return;
                case 1:
                    DfuBaseService.this.sendLogBroadcast(15, "[Broadcast] Resume action received");
                    if (DfuBaseService.this.mDfuServiceImpl != null) {
                        DfuBaseService.this.mDfuServiceImpl.resume();
                        return;
                    }
                    return;
                case 2:
                    DfuBaseService.this.sendLogBroadcast(15, "[Broadcast] Abort action received");
                    DfuBaseService.this.mAborted = true;
                    if (DfuBaseService.this.mDfuServiceImpl != null) {
                        DfuBaseService.this.mDfuServiceImpl.abort();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public DfuCallback mDfuServiceImpl;
    private boolean mDisableNotification;
    /* access modifiers changed from: private */
    public int mError;
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (status != 0) {
                DfuBaseService.this.loge("Connection state change error: " + status + " newState: " + newState);
                if (newState == 0) {
                    DfuBaseService.this.mConnectionState = 0;
                    if (DfuBaseService.this.mDfuServiceImpl != null) {
                        DfuBaseService.this.mDfuServiceImpl.getGattCallback().onDisconnected();
                    }
                }
                DfuBaseService.this.mError = 32768 | status;
            } else if (newState == 2) {
                DfuBaseService.this.logi("Connected to GATT server");
                DfuBaseService.this.sendLogBroadcast(5, "Connected to " + DfuBaseService.this.mDeviceAddress);
                DfuBaseService.this.mConnectionState = -2;
                if (gatt.getDevice().getBondState() == 12) {
                    DfuBaseService.this.logi("Waiting 1600 ms for a possible Service Changed indication...");
                    DfuBaseService.this.waitFor(1600);
                }
                DfuBaseService.this.sendLogBroadcast(1, "Discovering services...");
                DfuBaseService.this.sendLogBroadcast(0, "gatt.discoverServices()");
                boolean success = gatt.discoverServices();
                DfuBaseService.this.logi("Attempting to start service discovery... " + (success ? "succeed" : "failed"));
                if (!success) {
                    DfuBaseService.this.mError = 4101;
                } else {
                    return;
                }
            } else if (newState == 0) {
                DfuBaseService.this.logi("Disconnected from GATT server");
                DfuBaseService.this.mConnectionState = 0;
                if (DfuBaseService.this.mDfuServiceImpl != null) {
                    DfuBaseService.this.mDfuServiceImpl.getGattCallback().onDisconnected();
                }
            }
            synchronized (DfuBaseService.this.mLock) {
                DfuBaseService.this.mLock.notifyAll();
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == 0) {
                DfuBaseService.this.logi("Services discovered");
                DfuBaseService.this.mConnectionState = -3;
            } else {
                DfuBaseService.this.loge("Service discovery error: " + status);
                DfuBaseService.this.mError = status | 16384;
            }
            synchronized (DfuBaseService.this.mLock) {
                DfuBaseService.this.mLock.notifyAll();
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (DfuBaseService.this.mDfuServiceImpl != null) {
                DfuBaseService.this.mDfuServiceImpl.getGattCallback().onCharacteristicWrite(gatt, characteristic, status);
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (DfuBaseService.this.mDfuServiceImpl != null) {
                DfuBaseService.this.mDfuServiceImpl.getGattCallback().onCharacteristicRead(gatt, characteristic, status);
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (DfuBaseService.this.mDfuServiceImpl != null) {
                DfuBaseService.this.mDfuServiceImpl.getGattCallback().onCharacteristicChanged(gatt, characteristic);
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (DfuBaseService.this.mDfuServiceImpl != null) {
                DfuBaseService.this.mDfuServiceImpl.getGattCallback().onDescriptorWrite(gatt, descriptor, status);
            }
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (DfuBaseService.this.mDfuServiceImpl != null) {
                DfuBaseService.this.mDfuServiceImpl.getGattCallback().onDescriptorRead(gatt, descriptor, status);
            }
        }

        @SuppressLint({"NewApi"})
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            if (DfuBaseService.this.mDfuServiceImpl != null) {
                DfuBaseService.this.mDfuServiceImpl.getGattCallback().onMtuChanged(gatt, mtu, status);
            }
        }
    };
    private long mLastNotificationTime;
    private int mLastProgress = -1;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    DfuProgressInfo mProgressInfo;

    /* access modifiers changed from: protected */
    public abstract Class<? extends Activity> getNotificationTarget();

    public DfuBaseService() {
        super(TAG);
    }

    private static IntentFilter makeDfuActionIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("no.nordicsemi.android.dfu.broadcast.BROADCAST_ACTION");
        return intentFilter;
    }

    @TargetApi(26)
    public void onCreate() {
        super.onCreate();
        DEBUG = isDebug();
        initialize();
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        IntentFilter actionFilter = makeDfuActionIntentFilter();
        manager.registerReceiver(this.mDfuActionReceiver, actionFilter);
        registerReceiver(this.mDfuActionReceiver, actionFilter);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        filter.addAction("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED");
        filter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        registerReceiver(this.mConnectionStateBroadcastReceiver, filter);
        registerReceiver(this.mBondStateBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 4);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(channel);
            startForeground(1, new Builder(getApplicationContext(), "11111").build());
            return;
        }
        startForeground(1, new Notification());
    }

    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        ((NotificationManager) getSystemService("notification")).cancel(283);
        stopSelf();
    }

    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mDfuActionReceiver);
        unregisterReceiver(this.mDfuActionReceiver);
        unregisterReceiver(this.mConnectionStateBroadcastReceiver);
        unregisterReceiver(this.mBondStateBroadcastReceiver);
    }

    /* JADX WARNING: type inference failed for: r9v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r22v3 */
    /* JADX WARNING: type inference failed for: r9v2 */
    /* JADX WARNING: type inference failed for: r9v3 */
    /* JADX WARNING: type inference failed for: r9v4 */
    /* JADX WARNING: type inference failed for: r9v5 */
    /* JADX WARNING: type inference failed for: r9v6 */
    /* JADX WARNING: type inference failed for: r9v7 */
    /* JADX WARNING: type inference failed for: r9v8 */
    /* JADX WARNING: type inference failed for: r9v9, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r9v10, types: [java.io.ByteArrayInputStream] */
    /* JADX WARNING: type inference failed for: r9v11, types: [java.io.ByteArrayInputStream] */
    /* JADX WARNING: type inference failed for: r9v12, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r9v13 */
    /* JADX WARNING: type inference failed for: r9v14 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:185:0x0531, code lost:
        r5 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:?, code lost:
        logw("Upload aborted");
        sendLogBroadcast(15, "Upload aborted");
        terminateConnection(r6, 0);
        r34.mProgressInfo.setProgress(-7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x0637, code lost:
        if (r4 != null) goto L_0x0639;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:?, code lost:
        r4.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x063d, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:244:?, code lost:
        sendLogBroadcast(20, "Device has disconnected");
        loge(r13.getMessage());
        close(r6);
        report(4096);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x065f, code lost:
        if (r4 != null) goto L_0x0661;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:247:?, code lost:
        r4.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x0665, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:250:?, code lost:
        r14 = r13.getErrorNumber();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:251:0x066e, code lost:
        if ((32768 & r14) > 0) goto L_0x0670;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:252:0x0670, code lost:
        r14 = r14 & -32769;
        sendLogBroadcast(20, java.lang.String.format("Error (0x%02X): %s", new java.lang.Object[]{java.lang.Integer.valueOf(r14), no.nordicsemi.android.error.GattError.parseConnectionError(r14)}));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:253:0x069c, code lost:
        loge(r13.getMessage());
        terminateConnection(r6, r13.getErrorNumber());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:254:0x06ae, code lost:
        if (r4 != null) goto L_0x06b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:256:?, code lost:
        r4.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:257:0x06b5, code lost:
        r14 = r14 & -16385;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:259:?, code lost:
        sendLogBroadcast(20, java.lang.String.format("Error (0x%02X): %s", new java.lang.Object[]{java.lang.Integer.valueOf(r14), no.nordicsemi.android.error.GattError.parse(r14)}));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:260:0x06e0, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:261:0x06e1, code lost:
        if (r4 != null) goto L_0x06e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:263:?, code lost:
        r4.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:264:0x06e6, code lost:
        throw r5;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:58:0x01b7, B:211:0x05a7, B:236:0x0617] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0293 A[SYNTHETIC, Splitter:B:106:0x0293] */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x02f6 A[Catch:{ UploadAbortedException -> 0x0611, DeviceDisconnectedException -> 0x063d, DfuException -> 0x0665, all -> 0x06e0, all -> 0x0531 }] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x0366 A[SYNTHETIC, Splitter:B:136:0x0366] */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x03a4 A[SYNTHETIC, Splitter:B:144:0x03a4] */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x03af  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01cf A[SYNTHETIC, Splitter:B:61:0x01cf] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0251 A[SYNTHETIC, Splitter:B:90:0x0251] */
    /* JADX WARNING: Unknown variable types count: 11 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onHandleIntent(android.content.Intent r35) {
        /*
            r34 = this;
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS"
            r0 = r35
            java.lang.String r10 = r0.getStringExtra(r5)
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME"
            r0 = r35
            java.lang.String r11 = r0.getStringExtra(r5)
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_DISABLE_NOTIFICATION"
            r30 = 0
            r0 = r35
            r1 = r30
            boolean r12 = r0.getBooleanExtra(r5, r1)
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH"
            r0 = r35
            java.lang.String r15 = r0.getStringExtra(r5)
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_URI"
            r0 = r35
            android.os.Parcelable r17 = r0.getParcelableExtra(r5)
            android.net.Uri r17 = (android.net.Uri) r17
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_RES_ID"
            r30 = 0
            r0 = r35
            r1 = r30
            int r16 = r0.getIntExtra(r5, r1)
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_INIT_FILE_PATH"
            r0 = r35
            java.lang.String r19 = r0.getStringExtra(r5)
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_INIT_FILE_URI"
            r0 = r35
            android.os.Parcelable r21 = r0.getParcelableExtra(r5)
            android.net.Uri r21 = (android.net.Uri) r21
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_INIT_FILE_RES_ID"
            r30 = 0
            r0 = r35
            r1 = r30
            int r20 = r0.getIntExtra(r5, r1)
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE"
            r30 = 0
            r0 = r35
            r1 = r30
            int r7 = r0.getIntExtra(r5, r1)
            if (r15 == 0) goto L_0x0084
            if (r7 != 0) goto L_0x0084
            java.util.Locale r5 = java.util.Locale.US
            java.lang.String r5 = r15.toLowerCase(r5)
            java.lang.String r30 = "zip"
            r0 = r30
            boolean r5 = r5.endsWith(r0)
            if (r5 == 0) goto L_0x00c5
            r7 = 0
        L_0x0084:
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_MIME_TYPE"
            r0 = r35
            java.lang.String r24 = r0.getStringExtra(r5)
            if (r24 == 0) goto L_0x00c7
        L_0x008f:
            r5 = r7 & -8
            if (r5 > 0) goto L_0x00a9
            java.lang.String r5 = "application/zip"
            r0 = r24
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L_0x00d1
            java.lang.String r5 = "application/octet-stream"
            r0 = r24
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L_0x00d1
        L_0x00a9:
            java.lang.String r5 = "File type or file mime-type not supported"
            r0 = r34
            r0.logw(r5)
            r5 = 15
            java.lang.String r30 = "File type or file mime-type not supported"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)
            r5 = 4105(0x1009, float:5.752E-42)
            r0 = r34
            r0.report(r5)
        L_0x00c4:
            return
        L_0x00c5:
            r7 = 4
            goto L_0x0084
        L_0x00c7:
            if (r7 != 0) goto L_0x00cd
            java.lang.String r24 = "application/zip"
            goto L_0x008f
        L_0x00cd:
            java.lang.String r24 = "application/octet-stream"
            goto L_0x008f
        L_0x00d1:
            java.lang.String r5 = "application/octet-stream"
            r0 = r24
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0101
            r5 = 1
            if (r7 == r5) goto L_0x0101
            r5 = 2
            if (r7 == r5) goto L_0x0101
            r5 = 4
            if (r7 == r5) goto L_0x0101
            java.lang.String r5 = "Unable to determine file type"
            r0 = r34
            r0.logw(r5)
            r5 = 15
            java.lang.String r30 = "Unable to determine file type"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)
            r5 = 4105(0x1009, float:5.752E-42)
            r0 = r34
            r0.report(r5)
            goto L_0x00c4
        L_0x0101:
            no.nordicsemi.android.dfu.UuidHelper.assignCustomUuids(r35)
            r0 = r34
            r0.mDeviceAddress = r10
            r0 = r34
            r0.mDeviceName = r11
            r0 = r34
            r0.mDisableNotification = r12
            r5 = 0
            r0 = r34
            r0.mConnectionState = r5
            r5 = 0
            r0 = r34
            r0.mError = r5
            android.content.SharedPreferences r26 = android.preference.PreferenceManager.getDefaultSharedPreferences(r34)
            java.lang.String r5 = "settings_mbr_size"
            r30 = 4096(0x1000, float:5.74E-42)
            java.lang.String r30 = java.lang.String.valueOf(r30)
            r0 = r26
            r1 = r30
            java.lang.String r28 = r0.getString(r5, r1)
            int r23 = java.lang.Integer.parseInt(r28)     // Catch:{ NumberFormatException -> 0x01da }
            if (r23 >= 0) goto L_0x0137
            r23 = 0
        L_0x0137:
            r34.startForeground()
            r5 = 1
            java.lang.String r30 = "DFU service started"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)
            r8 = 0
            r9 = 0
            r5 = 1
            java.lang.String r30 = "Opening file..."
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            if (r17 == 0) goto L_0x01df
            r0 = r34
            r1 = r17
            r2 = r24
            r3 = r23
            java.io.InputStream r8 = r0.openInputStream(r1, r2, r3, r7)     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
        L_0x0160:
            if (r21 == 0) goto L_0x01fd
            android.content.ContentResolver r5 = r34.getContentResolver()     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            r0 = r21
            java.io.InputStream r9 = r5.openInputStream(r0)     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            r22 = r9
        L_0x016e:
            int r18 = r8.available()     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            if (r7 != 0) goto L_0x0188
            java.lang.String r5 = "application/zip"
            r0 = r24
            boolean r5 = r5.equals(r0)     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            if (r5 == 0) goto L_0x0188
            r0 = r8
            no.nordicsemi.android.dfu.internal.ArchiveInputStream r0 = (no.nordicsemi.android.dfu.internal.ArchiveInputStream) r0     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            r29 = r0
            int r7 = r29.getContentType()     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
        L_0x0188:
            java.lang.String r5 = "application/zip"
            r0 = r24
            boolean r5 = r5.equals(r0)     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            if (r5 == 0) goto L_0x0729
            r0 = r8
            no.nordicsemi.android.dfu.internal.ArchiveInputStream r0 = (no.nordicsemi.android.dfu.internal.ArchiveInputStream) r0     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            r29 = r0
            r5 = r7 & 4
            if (r5 <= 0) goto L_0x021a
            int r5 = r29.applicationImageSize()     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            int r5 = r5 % 4
            if (r5 == 0) goto L_0x021a
            no.nordicsemi.android.dfu.internal.exception.SizeValidationException r5 = new no.nordicsemi.android.dfu.internal.exception.SizeValidationException     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            java.lang.String r30 = "Application firmware is not word-aligned."
            r0 = r30
            r5.<init>(r0)     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            throw r5     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
        L_0x01af:
            r13 = move-exception
            r9 = r22
        L_0x01b2:
            java.lang.String r5 = "A security exception occurred while opening file"
            r0 = r34
            r0.loge(r5, r13)     // Catch:{ all -> 0x0531 }
            r5 = 20
            java.lang.String r30 = "Opening file failed: Permission required"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            r5 = 4097(0x1001, float:5.741E-42)
            r0 = r34
            r0.report(r5)     // Catch:{ all -> 0x0531 }
            if (r8 == 0) goto L_0x01d2
            r8.close()     // Catch:{ IOException -> 0x06e7 }
        L_0x01d2:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x01da:
            r13 = move-exception
            r23 = 4096(0x1000, float:5.74E-42)
            goto L_0x0137
        L_0x01df:
            if (r15 == 0) goto L_0x01ed
            r0 = r34
            r1 = r24
            r2 = r23
            java.io.InputStream r8 = r0.openInputStream(r15, r1, r2, r7)     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            goto L_0x0160
        L_0x01ed:
            if (r16 <= 0) goto L_0x0160
            r0 = r34
            r1 = r16
            r2 = r24
            r3 = r23
            java.io.InputStream r8 = r0.openInputStream(r1, r2, r3, r7)     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            goto L_0x0160
        L_0x01fd:
            if (r19 == 0) goto L_0x020a
            java.io.FileInputStream r22 = new java.io.FileInputStream     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            r0 = r22
            r1 = r19
            r0.<init>(r1)     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            goto L_0x016e
        L_0x020a:
            if (r20 <= 0) goto L_0x072d
            android.content.res.Resources r5 = r34.getResources()     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            r0 = r20
            java.io.InputStream r9 = r5.openRawResource(r0)     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            r22 = r9
            goto L_0x016e
        L_0x021a:
            r5 = r7 & 2
            if (r5 <= 0) goto L_0x025c
            int r5 = r29.bootloaderImageSize()     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            int r5 = r5 % 4
            if (r5 == 0) goto L_0x025c
            no.nordicsemi.android.dfu.internal.exception.SizeValidationException r5 = new no.nordicsemi.android.dfu.internal.exception.SizeValidationException     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            java.lang.String r30 = "Bootloader firmware is not word-aligned."
            r0 = r30
            r5.<init>(r0)     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            throw r5     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
        L_0x0231:
            r13 = move-exception
            r9 = r22
        L_0x0234:
            java.lang.String r5 = "An exception occurred while opening file"
            r0 = r34
            r0.loge(r5, r13)     // Catch:{ all -> 0x0531 }
            r5 = 20
            java.lang.String r30 = "Opening file failed: File not found"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            r5 = 4097(0x1001, float:5.741E-42)
            r0 = r34
            r0.report(r5)     // Catch:{ all -> 0x0531 }
            if (r8 == 0) goto L_0x0254
            r8.close()     // Catch:{ IOException -> 0x06ea }
        L_0x0254:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x025c:
            r5 = r7 & 1
            if (r5 <= 0) goto L_0x029e
            int r5 = r29.softDeviceImageSize()     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            int r5 = r5 % 4
            if (r5 == 0) goto L_0x029e
            no.nordicsemi.android.dfu.internal.exception.SizeValidationException r5 = new no.nordicsemi.android.dfu.internal.exception.SizeValidationException     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            java.lang.String r30 = "Soft Device firmware is not word-aligned."
            r0 = r30
            r5.<init>(r0)     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            throw r5     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
        L_0x0273:
            r13 = move-exception
            r9 = r22
        L_0x0276:
            java.lang.String r5 = "Firmware not word-aligned"
            r0 = r34
            r0.loge(r5, r13)     // Catch:{ all -> 0x0531 }
            r5 = 20
            java.lang.String r30 = "Opening file failed: Firmware size must be word-aligned"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            r5 = 4108(0x100c, float:5.757E-42)
            r0 = r34
            r0.report(r5)     // Catch:{ all -> 0x0531 }
            if (r8 == 0) goto L_0x0296
            r8.close()     // Catch:{ IOException -> 0x06ed }
        L_0x0296:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x029e:
            r5 = 4
            if (r7 != r5) goto L_0x0322
            byte[] r5 = r29.getApplicationInit()     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            if (r5 == 0) goto L_0x0729
            java.io.ByteArrayInputStream r9 = new java.io.ByteArrayInputStream     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            byte[] r5 = r29.getApplicationInit()     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            r9.<init>(r5)     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
        L_0x02b0:
            r5 = 5
            java.lang.StringBuilder r30 = new java.lang.StringBuilder     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            r30.<init>()     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            java.lang.String r31 = "Image file opened ("
            java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            r0 = r30
            r1 = r18
            java.lang.StringBuilder r30 = r0.append(r1)     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            java.lang.String r31 = " bytes in total)"
            java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            java.lang.String r30 = r30.toString()     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ SecurityException -> 0x0726, FileNotFoundException -> 0x0723, SizeValidationException -> 0x0720, IOException -> 0x0333, Exception -> 0x0371 }
            r5 = 1000(0x3e8, float:1.401E-42)
            r0 = r34
            r0.waitFor(r5)     // Catch:{ all -> 0x0531 }
            r5 = 1000(0x3e8, float:1.401E-42)
            r0 = r34
            r0.waitFor(r5)     // Catch:{ all -> 0x0531 }
            no.nordicsemi.android.dfu.DfuProgressInfo r5 = new no.nordicsemi.android.dfu.DfuProgressInfo     // Catch:{ all -> 0x0531 }
            r0 = r34
            r5.<init>(r0)     // Catch:{ all -> 0x0531 }
            r0 = r34
            r0.mProgressInfo = r5     // Catch:{ all -> 0x0531 }
            r0 = r34
            boolean r5 = r0.mAborted     // Catch:{ all -> 0x0531 }
            if (r5 == 0) goto L_0x03af
            java.lang.String r5 = "Upload aborted"
            r0 = r34
            r0.logw(r5)     // Catch:{ all -> 0x0531 }
            r5 = 15
            java.lang.String r30 = "Upload aborted"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            r0 = r34
            no.nordicsemi.android.dfu.DfuProgressInfo r5 = r0.mProgressInfo     // Catch:{ all -> 0x0531 }
            r30 = -7
            r0 = r30
            r5.setProgress(r0)     // Catch:{ all -> 0x0531 }
            if (r8 == 0) goto L_0x031a
            r8.close()     // Catch:{ IOException -> 0x06f6 }
        L_0x031a:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x0322:
            byte[] r5 = r29.getSystemInit()     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            if (r5 == 0) goto L_0x0729
            java.io.ByteArrayInputStream r9 = new java.io.ByteArrayInputStream     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            byte[] r5 = r29.getSystemInit()     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            r9.<init>(r5)     // Catch:{ SecurityException -> 0x01af, FileNotFoundException -> 0x0231, SizeValidationException -> 0x0273, IOException -> 0x071b, Exception -> 0x0716, all -> 0x0711 }
            goto L_0x02b0
        L_0x0333:
            r13 = move-exception
        L_0x0334:
            java.lang.String r5 = "An exception occurred while calculating file size"
            r0 = r34
            r0.loge(r5, r13)     // Catch:{ all -> 0x0531 }
            r5 = 20
            java.lang.StringBuilder r30 = new java.lang.StringBuilder     // Catch:{ all -> 0x0531 }
            r30.<init>()     // Catch:{ all -> 0x0531 }
            java.lang.String r31 = "Opening file failed: "
            java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ all -> 0x0531 }
            java.lang.String r31 = r13.getLocalizedMessage()     // Catch:{ all -> 0x0531 }
            java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ all -> 0x0531 }
            java.lang.String r30 = r30.toString()     // Catch:{ all -> 0x0531 }
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            r5 = 4098(0x1002, float:5.743E-42)
            r0 = r34
            r0.report(r5)     // Catch:{ all -> 0x0531 }
            if (r8 == 0) goto L_0x0369
            r8.close()     // Catch:{ IOException -> 0x06f0 }
        L_0x0369:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x0371:
            r13 = move-exception
        L_0x0372:
            java.lang.String r5 = "An exception occurred while opening files. Did you set the firmware file?"
            r0 = r34
            r0.loge(r5, r13)     // Catch:{ all -> 0x0531 }
            r5 = 20
            java.lang.StringBuilder r30 = new java.lang.StringBuilder     // Catch:{ all -> 0x0531 }
            r30.<init>()     // Catch:{ all -> 0x0531 }
            java.lang.String r31 = "Opening file failed: "
            java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ all -> 0x0531 }
            java.lang.String r31 = r13.getLocalizedMessage()     // Catch:{ all -> 0x0531 }
            java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ all -> 0x0531 }
            java.lang.String r30 = r30.toString()     // Catch:{ all -> 0x0531 }
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            r5 = 4098(0x1002, float:5.743E-42)
            r0 = r34
            r0.report(r5)     // Catch:{ all -> 0x0531 }
            if (r8 == 0) goto L_0x03a7
            r8.close()     // Catch:{ IOException -> 0x06f3 }
        L_0x03a7:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x03af:
            r5 = 1
            java.lang.String r30 = "Connecting to DFU target..."
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            r0 = r34
            no.nordicsemi.android.dfu.DfuProgressInfo r5 = r0.mProgressInfo     // Catch:{ all -> 0x0531 }
            r30 = -1
            r0 = r30
            r5.setProgress(r0)     // Catch:{ all -> 0x0531 }
            r0 = r34
            android.bluetooth.BluetoothGatt r6 = r0.connect(r10)     // Catch:{ all -> 0x0531 }
            if (r6 != 0) goto L_0x03f5
            java.lang.String r5 = "Bluetooth adapter disabled"
            r0 = r34
            r0.loge(r5)     // Catch:{ all -> 0x0531 }
            r5 = 20
            java.lang.String r30 = "Bluetooth adapter disabled"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            r5 = 4106(0x100a, float:5.754E-42)
            r0 = r34
            r0.report(r5)     // Catch:{ all -> 0x0531 }
            if (r8 == 0) goto L_0x03ed
            r8.close()     // Catch:{ IOException -> 0x06f9 }
        L_0x03ed:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x03f5:
            r0 = r34
            int r5 = r0.mConnectionState     // Catch:{ all -> 0x0531 }
            if (r5 != 0) goto L_0x0422
            java.lang.String r5 = "Device got disconnected before service discovery finished"
            r0 = r34
            r0.loge(r5)     // Catch:{ all -> 0x0531 }
            r5 = 5
            java.lang.String r30 = "Disconnected"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            r5 = 4096(0x1000, float:5.74E-42)
            r0 = r34
            r0.terminateConnection(r6, r5)     // Catch:{ all -> 0x0531 }
            if (r8 == 0) goto L_0x041a
            r8.close()     // Catch:{ IOException -> 0x06fc }
        L_0x041a:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x0422:
            r0 = r34
            int r5 = r0.mError     // Catch:{ all -> 0x0531 }
            if (r5 <= 0) goto L_0x0557
            r0 = r34
            int r5 = r0.mError     // Catch:{ all -> 0x0531 }
            r30 = 32768(0x8000, float:4.5918E-41)
            r5 = r5 & r30
            if (r5 <= 0) goto L_0x04e6
            r0 = r34
            int r5 = r0.mError     // Catch:{ all -> 0x0531 }
            r30 = -32769(0xffffffffffff7fff, float:NaN)
            r14 = r5 & r30
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0531 }
            r5.<init>()     // Catch:{ all -> 0x0531 }
            java.lang.String r30 = "An error occurred while connecting to the device:"
            r0 = r30
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ all -> 0x0531 }
            java.lang.StringBuilder r5 = r5.append(r14)     // Catch:{ all -> 0x0531 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0531 }
            r0 = r34
            r0.loge(r5)     // Catch:{ all -> 0x0531 }
            r5 = 20
            java.lang.String r30 = "Connection failed (0x%02X): %s"
            r31 = 2
            r0 = r31
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0531 }
            r31 = r0
            r32 = 0
            java.lang.Integer r33 = java.lang.Integer.valueOf(r14)     // Catch:{ all -> 0x0531 }
            r31[r32] = r33     // Catch:{ all -> 0x0531 }
            r32 = 1
            java.lang.String r33 = no.nordicsemi.android.error.GattError.parseConnectionError(r14)     // Catch:{ all -> 0x0531 }
            r31[r32] = r33     // Catch:{ all -> 0x0531 }
            java.lang.String r30 = java.lang.String.format(r30, r31)     // Catch:{ all -> 0x0531 }
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
        L_0x047f:
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_ATTEMPT"
            r30 = 0
            r0 = r35
            r1 = r30
            int r5 = r0.getIntExtra(r5, r1)     // Catch:{ all -> 0x0531 }
            if (r5 != 0) goto L_0x0541
            r5 = 15
            java.lang.String r30 = "Retrying..."
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            r0 = r34
            int r5 = r0.mConnectionState     // Catch:{ all -> 0x0531 }
            if (r5 == 0) goto L_0x04a5
            r0 = r34
            r0.disconnect(r6)     // Catch:{ all -> 0x0531 }
        L_0x04a5:
            r5 = 1
            r0 = r34
            r0.refreshDeviceCache(r6, r5)     // Catch:{ all -> 0x0531 }
            r0 = r34
            r0.close(r6)     // Catch:{ all -> 0x0531 }
            java.lang.String r5 = "Restarting the service"
            r0 = r34
            r0.logi(r5)     // Catch:{ all -> 0x0531 }
            android.content.Intent r25 = new android.content.Intent     // Catch:{ all -> 0x0531 }
            r25.<init>()     // Catch:{ all -> 0x0531 }
            r5 = 24
            r0 = r25
            r1 = r35
            r0.fillIn(r1, r5)     // Catch:{ all -> 0x0531 }
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_ATTEMPT"
            r30 = 1
            r0 = r25
            r1 = r30
            r0.putExtra(r5, r1)     // Catch:{ all -> 0x0531 }
            r0 = r34
            r1 = r25
            r0.startService(r1)     // Catch:{ all -> 0x0531 }
            if (r8 == 0) goto L_0x04de
            r8.close()     // Catch:{ IOException -> 0x06ff }
        L_0x04de:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x04e6:
            r0 = r34
            int r5 = r0.mError     // Catch:{ all -> 0x0531 }
            r14 = r5 & -16385(0xffffffffffffbfff, float:NaN)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0531 }
            r5.<init>()     // Catch:{ all -> 0x0531 }
            java.lang.String r30 = "An error occurred during discovering services:"
            r0 = r30
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ all -> 0x0531 }
            java.lang.StringBuilder r5 = r5.append(r14)     // Catch:{ all -> 0x0531 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0531 }
            r0 = r34
            r0.loge(r5)     // Catch:{ all -> 0x0531 }
            r5 = 20
            java.lang.String r30 = "Connection failed (0x%02X): %s"
            r31 = 2
            r0 = r31
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0531 }
            r31 = r0
            r32 = 0
            java.lang.Integer r33 = java.lang.Integer.valueOf(r14)     // Catch:{ all -> 0x0531 }
            r31[r32] = r33     // Catch:{ all -> 0x0531 }
            r32 = 1
            java.lang.String r33 = no.nordicsemi.android.error.GattError.parse(r14)     // Catch:{ all -> 0x0531 }
            r31[r32] = r33     // Catch:{ all -> 0x0531 }
            java.lang.String r30 = java.lang.String.format(r30, r31)     // Catch:{ all -> 0x0531 }
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            goto L_0x047f
        L_0x0531:
            r5 = move-exception
        L_0x0532:
            if (r8 == 0) goto L_0x0537
            r8.close()     // Catch:{ IOException -> 0x070e }
        L_0x0537:
            r30 = 0
            r0 = r34
            r1 = r30
            r0.stopForeground(r1)
            throw r5
        L_0x0541:
            r0 = r34
            int r5 = r0.mError     // Catch:{ all -> 0x0531 }
            r0 = r34
            r0.terminateConnection(r6, r5)     // Catch:{ all -> 0x0531 }
            if (r8 == 0) goto L_0x054f
            r8.close()     // Catch:{ IOException -> 0x0702 }
        L_0x054f:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x0557:
            r0 = r34
            boolean r5 = r0.mAborted     // Catch:{ all -> 0x0531 }
            if (r5 == 0) goto L_0x058f
            java.lang.String r5 = "Upload aborted"
            r0 = r34
            r0.logw(r5)     // Catch:{ all -> 0x0531 }
            r5 = 15
            java.lang.String r30 = "Upload aborted"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            r5 = 0
            r0 = r34
            r0.terminateConnection(r6, r5)     // Catch:{ all -> 0x0531 }
            r0 = r34
            no.nordicsemi.android.dfu.DfuProgressInfo r5 = r0.mProgressInfo     // Catch:{ all -> 0x0531 }
            r30 = -7
            r0 = r30
            r5.setProgress(r0)     // Catch:{ all -> 0x0531 }
            if (r8 == 0) goto L_0x0587
            r8.close()     // Catch:{ IOException -> 0x0705 }
        L_0x0587:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x058f:
            r5 = 5
            java.lang.String r30 = "Services discovered"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x0531 }
            java.lang.String r5 = "no.nordicsemi.android.dfu.extra.EXTRA_ATTEMPT"
            r30 = 0
            r0 = r35
            r1 = r30
            r0.putExtra(r5, r1)     // Catch:{ all -> 0x0531 }
            r4 = 0
            no.nordicsemi.android.dfu.DfuServiceProvider r27 = new no.nordicsemi.android.dfu.DfuServiceProvider     // Catch:{ UploadAbortedException -> 0x0611, DeviceDisconnectedException -> 0x063d, DfuException -> 0x0665 }
            r27.<init>()     // Catch:{ UploadAbortedException -> 0x0611, DeviceDisconnectedException -> 0x063d, DfuException -> 0x0665 }
            r0 = r27
            r1 = r34
            r1.mDfuServiceImpl = r0     // Catch:{ UploadAbortedException -> 0x0611, DeviceDisconnectedException -> 0x063d, DfuException -> 0x0665 }
            r0 = r27
            r1 = r35
            r2 = r34
            no.nordicsemi.android.dfu.DfuService r4 = r0.getServiceImpl(r1, r2, r6)     // Catch:{ UploadAbortedException -> 0x0611, DeviceDisconnectedException -> 0x063d, DfuException -> 0x0665 }
            r0 = r34
            r0.mDfuServiceImpl = r4     // Catch:{ UploadAbortedException -> 0x0611, DeviceDisconnectedException -> 0x063d, DfuException -> 0x0665 }
            if (r4 != 0) goto L_0x05f2
            java.lang.String r5 = "DfuBaseService"
            java.lang.String r30 = "DFU Service not found."
            r0 = r30
            android.util.Log.w(r5, r0)     // Catch:{ UploadAbortedException -> 0x0611, DeviceDisconnectedException -> 0x063d, DfuException -> 0x0665 }
            r5 = 15
            java.lang.String r30 = "DFU Service not found"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ UploadAbortedException -> 0x0611, DeviceDisconnectedException -> 0x063d, DfuException -> 0x0665 }
            r5 = 4102(0x1006, float:5.748E-42)
            r0 = r34
            r0.terminateConnection(r6, r5)     // Catch:{ UploadAbortedException -> 0x0611, DeviceDisconnectedException -> 0x063d, DfuException -> 0x0665 }
            if (r4 == 0) goto L_0x05e5
            r4.release()     // Catch:{ all -> 0x0531 }
        L_0x05e5:
            if (r8 == 0) goto L_0x05ea
            r8.close()     // Catch:{ IOException -> 0x0708 }
        L_0x05ea:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x05f2:
            r5 = r35
            boolean r5 = r4.initialize(r5, r6, r7, r8, r9)     // Catch:{ UploadAbortedException -> 0x0611, DeviceDisconnectedException -> 0x063d, DfuException -> 0x0665 }
            if (r5 == 0) goto L_0x05ff
            r0 = r35
            r4.performDfu(r0)     // Catch:{ UploadAbortedException -> 0x0611, DeviceDisconnectedException -> 0x063d, DfuException -> 0x0665 }
        L_0x05ff:
            if (r4 == 0) goto L_0x0604
            r4.release()     // Catch:{ all -> 0x0531 }
        L_0x0604:
            if (r8 == 0) goto L_0x0609
            r8.close()     // Catch:{ IOException -> 0x070b }
        L_0x0609:
            r5 = 0
            r0 = r34
            r0.stopForeground(r5)
            goto L_0x00c4
        L_0x0611:
            r13 = move-exception
            java.lang.String r5 = "Upload aborted"
            r0 = r34
            r0.logw(r5)     // Catch:{ all -> 0x06e0 }
            r5 = 15
            java.lang.String r30 = "Upload aborted"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x06e0 }
            r5 = 0
            r0 = r34
            r0.terminateConnection(r6, r5)     // Catch:{ all -> 0x06e0 }
            r0 = r34
            no.nordicsemi.android.dfu.DfuProgressInfo r5 = r0.mProgressInfo     // Catch:{ all -> 0x06e0 }
            r30 = -7
            r0 = r30
            r5.setProgress(r0)     // Catch:{ all -> 0x06e0 }
            if (r4 == 0) goto L_0x0604
            r4.release()     // Catch:{ all -> 0x0531 }
            goto L_0x0604
        L_0x063d:
            r13 = move-exception
            r5 = 20
            java.lang.String r30 = "Device has disconnected"
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x06e0 }
            java.lang.String r5 = r13.getMessage()     // Catch:{ all -> 0x06e0 }
            r0 = r34
            r0.loge(r5)     // Catch:{ all -> 0x06e0 }
            r0 = r34
            r0.close(r6)     // Catch:{ all -> 0x06e0 }
            r5 = 4096(0x1000, float:5.74E-42)
            r0 = r34
            r0.report(r5)     // Catch:{ all -> 0x06e0 }
            if (r4 == 0) goto L_0x0604
            r4.release()     // Catch:{ all -> 0x0531 }
            goto L_0x0604
        L_0x0665:
            r13 = move-exception
            int r14 = r13.getErrorNumber()     // Catch:{ all -> 0x06e0 }
            r5 = 32768(0x8000, float:4.5918E-41)
            r5 = r5 & r14
            if (r5 <= 0) goto L_0x06b5
            r5 = -32769(0xffffffffffff7fff, float:NaN)
            r14 = r14 & r5
            r5 = 20
            java.lang.String r30 = "Error (0x%02X): %s"
            r31 = 2
            r0 = r31
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x06e0 }
            r31 = r0
            r32 = 0
            java.lang.Integer r33 = java.lang.Integer.valueOf(r14)     // Catch:{ all -> 0x06e0 }
            r31[r32] = r33     // Catch:{ all -> 0x06e0 }
            r32 = 1
            java.lang.String r33 = no.nordicsemi.android.error.GattError.parseConnectionError(r14)     // Catch:{ all -> 0x06e0 }
            r31[r32] = r33     // Catch:{ all -> 0x06e0 }
            java.lang.String r30 = java.lang.String.format(r30, r31)     // Catch:{ all -> 0x06e0 }
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x06e0 }
        L_0x069c:
            java.lang.String r5 = r13.getMessage()     // Catch:{ all -> 0x06e0 }
            r0 = r34
            r0.loge(r5)     // Catch:{ all -> 0x06e0 }
            int r5 = r13.getErrorNumber()     // Catch:{ all -> 0x06e0 }
            r0 = r34
            r0.terminateConnection(r6, r5)     // Catch:{ all -> 0x06e0 }
            if (r4 == 0) goto L_0x0604
            r4.release()     // Catch:{ all -> 0x0531 }
            goto L_0x0604
        L_0x06b5:
            r14 = r14 & -16385(0xffffffffffffbfff, float:NaN)
            r5 = 20
            java.lang.String r30 = "Error (0x%02X): %s"
            r31 = 2
            r0 = r31
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x06e0 }
            r31 = r0
            r32 = 0
            java.lang.Integer r33 = java.lang.Integer.valueOf(r14)     // Catch:{ all -> 0x06e0 }
            r31[r32] = r33     // Catch:{ all -> 0x06e0 }
            r32 = 1
            java.lang.String r33 = no.nordicsemi.android.error.GattError.parse(r14)     // Catch:{ all -> 0x06e0 }
            r31[r32] = r33     // Catch:{ all -> 0x06e0 }
            java.lang.String r30 = java.lang.String.format(r30, r31)     // Catch:{ all -> 0x06e0 }
            r0 = r34
            r1 = r30
            r0.sendLogBroadcast(r5, r1)     // Catch:{ all -> 0x06e0 }
            goto L_0x069c
        L_0x06e0:
            r5 = move-exception
            if (r4 == 0) goto L_0x06e6
            r4.release()     // Catch:{ all -> 0x0531 }
        L_0x06e6:
            throw r5     // Catch:{ all -> 0x0531 }
        L_0x06e7:
            r5 = move-exception
            goto L_0x01d2
        L_0x06ea:
            r5 = move-exception
            goto L_0x0254
        L_0x06ed:
            r5 = move-exception
            goto L_0x0296
        L_0x06f0:
            r5 = move-exception
            goto L_0x0369
        L_0x06f3:
            r5 = move-exception
            goto L_0x03a7
        L_0x06f6:
            r5 = move-exception
            goto L_0x031a
        L_0x06f9:
            r5 = move-exception
            goto L_0x03ed
        L_0x06fc:
            r5 = move-exception
            goto L_0x041a
        L_0x06ff:
            r5 = move-exception
            goto L_0x04de
        L_0x0702:
            r5 = move-exception
            goto L_0x054f
        L_0x0705:
            r5 = move-exception
            goto L_0x0587
        L_0x0708:
            r5 = move-exception
            goto L_0x05ea
        L_0x070b:
            r5 = move-exception
            goto L_0x0609
        L_0x070e:
            r30 = move-exception
            goto L_0x0537
        L_0x0711:
            r5 = move-exception
            r9 = r22
            goto L_0x0532
        L_0x0716:
            r13 = move-exception
            r9 = r22
            goto L_0x0372
        L_0x071b:
            r13 = move-exception
            r9 = r22
            goto L_0x0334
        L_0x0720:
            r13 = move-exception
            goto L_0x0276
        L_0x0723:
            r13 = move-exception
            goto L_0x0234
        L_0x0726:
            r13 = move-exception
            goto L_0x01b2
        L_0x0729:
            r9 = r22
            goto L_0x02b0
        L_0x072d:
            r22 = r9
            goto L_0x016e
        */
        throw new UnsupportedOperationException("Method not decompiled: no.nordicsemi.android.dfu.DfuBaseService.onHandleIntent(android.content.Intent):void");
    }

    private InputStream openInputStream(String filePath, String mimeType, int mbrSize, int types) throws IOException {
        InputStream is = new FileInputStream(filePath);
        if (MIME_TYPE_ZIP.equals(mimeType)) {
            return new ArchiveInputStream(is, mbrSize, types);
        }
        if (filePath.toLowerCase(Locale.US).endsWith("hex")) {
            return new HexInputStream(is, mbrSize);
        }
        return is;
    }

    /* JADX INFO: finally extract failed */
    private InputStream openInputStream(Uri stream, String mimeType, int mbrSize, int types) throws IOException {
        InputStream is = getContentResolver().openInputStream(stream);
        if (MIME_TYPE_ZIP.equals(mimeType)) {
            return new ArchiveInputStream(is, mbrSize, types);
        }
        Cursor cursor = getContentResolver().query(stream, new String[]{"_display_name"}, null, null, null);
        try {
            if (!cursor.moveToNext() || !cursor.getString(0).toLowerCase(Locale.US).endsWith("hex")) {
                cursor.close();
                return is;
            }
            InputStream hexInputStream = new HexInputStream(is, mbrSize);
            cursor.close();
            return hexInputStream;
        } catch (Throwable th) {
            cursor.close();
            throw th;
        }
    }

    private InputStream openInputStream(int resId, String mimeType, int mbrSize, int types) throws IOException {
        InputStream is = getResources().openRawResource(resId);
        if (MIME_TYPE_ZIP.equals(mimeType)) {
            return new ArchiveInputStream(is, mbrSize, types);
        }
        is.mark(2);
        int firstByte = is.read();
        is.reset();
        if (firstByte == 58) {
            return new HexInputStream(is, mbrSize);
        }
        return is;
    }

    /* access modifiers changed from: protected */
    public BluetoothGatt connect(String address) {
        if (!this.mBluetoothAdapter.isEnabled()) {
            return null;
        }
        this.mConnectionState = -1;
        logi("Connecting to the device...");
        BluetoothDevice device = this.mBluetoothAdapter.getRemoteDevice(address);
        sendLogBroadcast(0, "gatt = device.connectGatt(autoConnect = false)");
        BluetoothGatt connectGatt = device.connectGatt(this, false, this.mGattCallback);
        try {
            synchronized (this.mLock) {
                while (true) {
                    if ((this.mConnectionState == -1 || this.mConnectionState == -2) && this.mError == 0) {
                        this.mLock.wait();
                    }
                }
            }
            return connectGatt;
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
            return connectGatt;
        }
    }

    /* access modifiers changed from: protected */
    public void terminateConnection(BluetoothGatt gatt, int error) {
        if (this.mConnectionState != 0) {
            disconnect(gatt);
        }
        try {
            refreshDeviceCache(gatt, false);
            waitFor(ServiceErrorCode.YOU_AND_ME_IS_FRIEND);
            close(gatt);
            waitFor(ServiceErrorCode.YOU_AND_ME_IS_FRIEND);
            if (error != 0) {
                report(error);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: protected */
    public void disconnect(BluetoothGatt gatt) {
        if (this.mConnectionState != 0) {
            sendLogBroadcast(1, "Disconnecting...");
            this.mProgressInfo.setProgress(-5);
            this.mConnectionState = -4;
            logi("Disconnecting from the device...");
            sendLogBroadcast(0, "gatt.disconnect()");
            gatt.disconnect();
            waitUntilDisconnected();
            sendLogBroadcast(5, "Disconnected");
        }
    }

    /* access modifiers changed from: protected */
    public void waitUntilDisconnected() {
        try {
            synchronized (this.mLock) {
                while (this.mConnectionState != 0 && this.mError == 0) {
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException e) {
            loge("Sleeping interrupted", e);
        }
    }

    /* access modifiers changed from: protected */
    public void waitFor(int millis) {
        synchronized (this.mLock) {
            try {
                sendLogBroadcast(0, "wait(" + millis + ")");
                this.mLock.wait((long) millis);
            } catch (InterruptedException e) {
                loge("Sleeping interrupted", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void close(BluetoothGatt gatt) {
        logi("Cleaning up...");
        sendLogBroadcast(0, "gatt.close()");
        gatt.close();
        this.mConnectionState = -5;
    }

    /* access modifiers changed from: protected */
    public void refreshDeviceCache(BluetoothGatt gatt, boolean force) {
        if (force || gatt.getDevice().getBondState() == 10) {
            sendLogBroadcast(0, "gatt.refresh() (hidden)");
            try {
                Method refresh = gatt.getClass().getMethod("refresh", new Class[0]);
                if (refresh != null) {
                    logi("Refreshing result: " + ((Boolean) refresh.invoke(gatt, new Object[0])).booleanValue());
                }
            } catch (Exception e) {
                loge("An exception occurred while refreshing device", e);
                sendLogBroadcast(15, "Refreshing failed");
            }
        }
    }

    public void updateProgressNotification() {
        String title;
        DfuProgressInfo info = this.mProgressInfo;
        int progress = info.getProgress();
        if (this.mLastProgress != progress) {
            this.mLastProgress = progress;
            sendProgressBroadcast(info);
            if (!this.mDisableNotification) {
                long now = SystemClock.elapsedRealtime();
                if (now - this.mLastNotificationTime >= 250 || -6 == progress || -7 == progress) {
                    this.mLastNotificationTime = now;
                    String deviceAddress = this.mDeviceAddress;
                    String deviceName = this.mDeviceName != null ? this.mDeviceName : getString(R.string.device_module_dfu_unknown_name);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                    NotificationCompat.Builder builder2 = builder.setSmallIcon(17301640).setOnlyAlertOnce(true);
                    builder2.setColor(-7829368);
                    switch (progress) {
                        case -7:
                            builder2.setOngoing(false).setContentTitle(getString(R.string.device_module_dfu_status_aborted)).setSmallIcon(17301641).setContentText(getString(R.string.device_module_dfu_status_aborted_msg)).setAutoCancel(true);
                            break;
                        case -6:
                            builder2.setOngoing(false).setContentTitle(getString(R.string.device_module_dfu_status_completed)).setSmallIcon(17301641).setContentText(getString(R.string.device_module_dfu_status_completed_msg)).setAutoCancel(true).setColor(-16730086);
                            break;
                        case -5:
                            builder2.setOngoing(true).setContentTitle(getString(R.string.device_module_dfu_status_disconnecting)).setContentText(getString(R.string.device_module_dfu_status_disconnecting_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                            break;
                        case -4:
                            builder2.setOngoing(true).setContentTitle(getString(R.string.device_module_dfu_status_validating)).setContentText(getString(R.string.device_module_dfu_status_validating_msg)).setProgress(100, 0, true);
                            break;
                        case -3:
                            builder2.setOngoing(true).setContentTitle(getString(R.string.device_module_dfu_status_switching_to_dfu)).setContentText(getString(R.string.device_module_dfu_status_switching_to_dfu_msg)).setProgress(100, 0, true);
                            break;
                        case -2:
                            builder2.setOngoing(true).setContentTitle(getString(R.string.device_module_dfu_status_starting)).setContentText(getString(R.string.device_module_dfu_status_starting_msg)).setProgress(100, 0, true);
                            break;
                        case -1:
                            builder2.setOngoing(true).setContentTitle(getString(R.string.device_module_dfu_status_connecting)).setContentText(getString(R.string.device_module_dfu_status_connecting_msg, new Object[]{deviceName})).setProgress(100, 0, true);
                            break;
                        default:
                            if (info.getTotalParts() == 1) {
                                title = getString(R.string.device_module_dfu_status_uploading);
                            } else {
                                title = getString(R.string.device_module_dfu_status_uploading_part, new Object[]{Integer.valueOf(info.getCurrentPart()), Integer.valueOf(info.getTotalParts())});
                            }
                            builder2.setOngoing(true).setContentTitle(title).setContentText(getString(R.string.device_module_dfu_status_uploading_msg, new Object[]{deviceName})).setProgress(100, progress, false);
                            break;
                    }
                    Intent intent = new Intent(this, getNotificationTarget());
                    intent.addFlags(268435456);
                    intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", deviceAddress);
                    intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", deviceName);
                    intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_PROGRESS", progress);
                    builder2.setContentIntent(PendingIntent.getActivity(this, 0, intent, 134217728));
                    if (!(progress == -7 || progress == -6)) {
                        Intent abortIntent = new Intent("no.nordicsemi.android.dfu.broadcast.BROADCAST_ACTION");
                        abortIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_ACTION", 2);
                        builder2.addAction(R.mipmap.ic_launcher, getString(R.string.device_module_dfu_action_abort), PendingIntent.getBroadcast(this, 1, abortIntent, 134217728));
                    }
                    NotificationManager manager = (NotificationManager) getSystemService("notification");
                    try {
                        if (VERSION.SDK_INT >= 26) {
                            NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 4);
                            channel.enableVibration(false);
                            channel.enableLights(false);
                            channel.setSound(null, null);
                            manager.createNotificationChannel(channel);
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                    manager.notify(283, builder2.build());
                }
            }
        }
    }

    private void report(int error) {
        String deviceName;
        sendErrorBroadcast(error);
        if (!this.mDisableNotification) {
            String deviceAddress = this.mDeviceAddress;
            if (this.mDeviceName != null) {
                deviceName = this.mDeviceName;
            } else {
                deviceName = getString(R.string.device_module_dfu_unknown_name);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(17301640).setOnlyAlertOnce(true).setColor(SupportMenu.CATEGORY_MASK).setOngoing(false).setContentTitle(getString(R.string.device_module_dfu_status_error)).setSmallIcon(17301641).setContentText(getString(R.string.device_module_dfu_status_error_msg)).setAutoCancel(true);
            Intent intent = new Intent(this, getNotificationTarget());
            intent.addFlags(268435456);
            intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", deviceAddress);
            intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", deviceName);
            intent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_PROGRESS", error);
            builder.setContentIntent(PendingIntent.getActivity(this, 0, intent, 134217728));
            NotificationManager manager = (NotificationManager) getSystemService("notification");
            try {
                if (VERSION.SDK_INT >= 26) {
                    NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 4);
                    channel.enableVibration(false);
                    channel.enableLights(false);
                    channel.setSound(null, null);
                    manager.createNotificationChannel(channel);
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
            manager.notify(283, builder.build());
        }
    }

    private void startForeground() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(17301640).setContentTitle(getString(R.string.device_module_dfu_status_initializing)).setContentText(getString(R.string.device_module_dfu_status_starting_msg)).setColor(-7829368).setOngoing(true);
        Intent targetIntent = new Intent(this, getNotificationTarget());
        targetIntent.addFlags(268435456);
        targetIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", this.mDeviceAddress);
        targetIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", this.mDeviceName);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, targetIntent, 134217728));
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 4);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(channel);
            startForeground(283, new Builder(getApplicationContext(), "11111").build());
            return;
        }
        startForeground(283, builder.build());
    }

    /* access modifiers changed from: protected */
    public boolean isDebug() {
        return true;
    }

    private void sendProgressBroadcast(DfuProgressInfo info) {
        Intent broadcast = new Intent("no.nordicsemi.android.dfu.broadcast.BROADCAST_PROGRESS");
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", info.getProgress());
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", this.mDeviceAddress);
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_PART_CURRENT", info.getCurrentPart());
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_PARTS_TOTAL", info.getTotalParts());
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_SPEED_B_PER_MS", info.getSpeed());
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_AVG_SPEED_B_PER_MS", info.getAverageSpeed());
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
    }

    private void sendErrorBroadcast(int error) {
        Intent broadcast = new Intent("no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR");
        if ((error & 16384) > 0) {
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", error & -16385);
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE", 2);
        } else if ((32768 & error) > 0) {
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", -32769 & error);
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE", 1);
        } else if ((error & 8192) > 0) {
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", error & -8193);
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE", 3);
        } else {
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", error);
            broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE", 0);
        }
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", this.mDeviceAddress);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
    }

    /* access modifiers changed from: 0000 */
    public void sendLogBroadcast(int level, String message) {
        String fullMessage = "[DFU] " + message;
        Intent broadcast = new Intent(BROADCAST_LOG);
        broadcast.putExtra(EXTRA_LOG_MESSAGE, fullMessage);
        broadcast.putExtra(EXTRA_LOG_LEVEL, level);
        broadcast.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", this.mDeviceAddress);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
    }

    private boolean initialize() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService("bluetooth");
        if (bluetoothManager == null) {
            loge("Unable to initialize BluetoothManager.");
            return false;
        }
        this.mBluetoothAdapter = bluetoothManager.getAdapter();
        if (this.mBluetoothAdapter != null) {
            return true;
        }
        loge("Unable to obtain a BluetoothAdapter.");
        return false;
    }

    /* access modifiers changed from: private */
    public void loge(String message) {
        Log.e(TAG, message);
    }

    private void loge(String message, Throwable e) {
        Log.e(TAG, message, e);
    }

    private void logw(String message) {
        if (DEBUG) {
            Log.w(TAG, message);
        }
    }

    /* access modifiers changed from: private */
    public void logi(String message) {
        if (DEBUG) {
            Log.i(TAG, message);
        }
    }
}
