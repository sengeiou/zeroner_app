package com.iwown.ble_module.mtk_ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.R;
import com.iwown.ble_module.WristBand;
import com.iwown.ble_module.iwown.utils.Constants;
import com.iwown.ble_module.iwown.utils.Constants.R1UUID;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.leprofiles.BleGattUuid.Char;
import com.iwown.ble_module.mtk_ble.leprofiles.BleGattUuid.Desc;
import com.iwown.ble_module.mtk_ble.leprofiles.BleGattUuid.Service;
import com.iwown.ble_module.mtk_ble.leprofiles.BleGattUuid.ZeronerUUID;
import com.iwown.ble_module.mtk_ble.leprofiles.LocalBluetoothLEManager;
import com.iwown.ble_module.mtk_ble.leprofiles.basclient.BatteryChangeListener;
import com.iwown.ble_module.mtk_ble.task.BleWriteDataTask;
import com.iwown.ble_module.mtk_ble.task.ITask;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.scan.IScanCallback;
import com.iwown.ble_module.scan.Scanner;
import com.iwown.ble_module.utils.BluetoothUtils;
import com.iwown.ble_module.utils.Util;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import coms.mediatek.ctrl.epo.EpoDownloadController;
import coms.mediatek.wearable.WearableManager;
import coms.mediatek.wearableProfiles.GattRequestManager;
import coms.mediatek.wearableProfiles.WearableClientProfile;
import coms.mediatek.wearableProfiles.WearableClientProfileRegister;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.cli.HelpFormatter;

public class MTKBle implements IScanCallback {
    private static final int RECNT_TIME_LIMIT = 3;
    public static int SDK_TYPE = 2;
    private static final String TAG = "iwown";
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    static Handler mHandler = new Handler(Looper.getMainLooper());
    protected static final Object mLock = new Object();
    /* access modifiers changed from: private */
    public static int reopen_notify_times = 0;
    private static MTKBle sInstance = null;
    private byte[] CLIENT_CHAR_VALUE = {1, 0};
    /* access modifiers changed from: private */
    public boolean DBG = true;
    List<BluetoothGattCharacteristic> characteristics = new ArrayList();
    /* access modifiers changed from: private */
    public Runnable findCharacteristciTimeoutRunnable = new Runnable() {
        public void run() {
            MTKBle.this.isConnecting = false;
            MTKBle.this.isConnected = false;
            MTKBle.this.disconnect();
            KLog.i(MTKBle.TAG, "没有获取到特征值");
            L.file("没有获取到特征值", 4);
            MTKBle.mHandler.postDelayed(MTKBle.this.reconnectRunnable, 1000);
        }
    };
    /* access modifiers changed from: private */
    public boolean isConnected = false;
    /* access modifiers changed from: private */
    public boolean isConnecting = false;
    private boolean isScanning = false;
    private BatteryChangeListener mBatteryChangeListener = null;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic mBatteryLevel = null;
    /* access modifiers changed from: private */
    public BluetoothGattDescriptor mClientCharConfig = null;
    /* access modifiers changed from: private */
    public Context mContext;
    private int mCurrentBatteryLevel = -1;
    private IDataReceiveHandler mDataReceiveHandler = null;
    /* access modifiers changed from: private */
    public Runnable mDiscntTimeoutRunnable = new Runnable() {
        public void run() {
            MTKBle.this.isConnecting = false;
            MTKBle.this.isConnected = false;
            KLog.i(MTKBle.TAG, "断连没有收到系统onConnectionStateChange回调");
            if (MTKBle.this.mGatt != null && MTKBle.this.manager != null) {
                KLog.i(MTKBle.TAG, "BluetoothManager状态：" + MTKBle.this.manager.getConnectionState(MTKBle.this.mGatt.getDevice(), 7));
            }
        }
    };
    /* access modifiers changed from: private */
    public BluetoothGatt mGatt = null;
    private GattCallbackImpl mGattCallbackImpl;
    protected BluetoothGattCharacteristic mHeartRateCharacter;
    private final MtkBaseReceiver mMtkBaseReceiver;
    protected BluetoothGattCharacteristic mNewHeartRateCharacter;
    /* access modifiers changed from: private */
    public Runnable mTimeoutRunnable = new Runnable() {
        public void run() {
            MTKBle.this.isConnecting = false;
            MTKBle.this.isConnected = false;
            MTKBle.this.disconnect();
            KLog.i(MTKBle.TAG, "没有收到系统onConnectionStateChange回调");
            L.file("没有收到系统onConnectionStateChange回调", 4);
            if (!(MTKBle.this.mGatt == null || MTKBle.this.manager == null)) {
                KLog.i(MTKBle.TAG, "BluetoothManager状态：" + MTKBle.this.manager.getConnectionState(MTKBle.this.mGatt.getDevice(), 7));
            }
            if (MTKBle.this.recnt_time_now <= 3) {
                MTKBle.mHandler.postDelayed(MTKBle.this.reconnectRunnable, 3000);
            } else {
                KLog.i(MTKBle.TAG, "重连次数超出了设定限制，不重连了");
                L.file("重连次数超出了设定限制，不重连了", 4);
                MTKBle.this.recnt_time_now = 0;
            }
            MtkBroadcastSender.getInstance(MTKBle.this.mContext).onNoCallback();
        }
    };
    private WristBand mWristBand = null;
    /* access modifiers changed from: private */
    public BluetoothManager manager = null;
    private boolean needReconnect = true;
    /* access modifiers changed from: private */
    public boolean newState = false;
    /* access modifiers changed from: private */
    public int recnt_time_now = 0;
    /* access modifiers changed from: private */
    public Runnable reconnectRunnable = new Runnable() {
        public void run() {
            KLog.i(MTKBle.TAG, "准备去重连");
            L.file("准备去重连", 4);
            MTKBle.this.reconnect();
        }
    };

    private class GattCallbackImpl extends WearableClientProfile {
        private static final String TAG = "BasGatt.CallbackImpl";
        /* access modifiers changed from: private */
        public Runnable closeNotifyRunnable;
        /* access modifiers changed from: private */
        public Runnable reCmdRunnable;

        private GattCallbackImpl() {
            this.reCmdRunnable = new Runnable() {
                public void run() {
                }
            };
            this.closeNotifyRunnable = new Runnable() {
                public void run() {
                    MTKBle.access$1008();
                    if (MTKBle.reopen_notify_times > 1) {
                        MTKBle.mHandler.removeCallbacks(this);
                        return;
                    }
                    for (final BluetoothGattCharacteristic characteristic : MTKBle.this.characteristics) {
                        if (characteristic.getUuid().equals(ZeronerUUID.BAND_CHARACTERISTIC_NEW_INDICATE)) {
                            if (GattCallbackImpl.this.setCharacteristicNotification(characteristic, false)) {
                                KLog.e(GattCallbackImpl.TAG, "mtk关notify成功");
                                MTKBle.mHandler.removeCallbacks(GattCallbackImpl.this.reCmdRunnable);
                                MTKBle.mHandler.removeCallbacks(GattCallbackImpl.this.closeNotifyRunnable);
                                MTKBle.mHandler.postDelayed(new Runnable() {
                                    public void run() {
                                        if (GattCallbackImpl.this.setCharacteristicNotification(characteristic, true)) {
                                            KLog.e(GattCallbackImpl.TAG, "mtk重开notify成功");
                                        }
                                        MTKBle.mHandler.postDelayed(GattCallbackImpl.this.reCmdRunnable, 1000);
                                    }
                                }, 200);
                            } else {
                                KLog.e(GattCallbackImpl.TAG, "mtk关notify失败");
                            }
                        }
                    }
                }
            };
        }

        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            KLog.i(TAG, "onConnectionStateChange, status = " + status + ", newState = " + newState + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + gatt.getDevice().getName());
            L.file("onConnectionStateChange, status = " + status + ", newState = " + newState + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + gatt.getDevice().getName(), 4);
            MTKBle.mHandler.removeCallbacks(MTKBle.this.mTimeoutRunnable);
            String address = gatt.getDevice().getAddress();
            MTKBle.this.isConnecting = false;
            if (2 == newState) {
                MTKBle.this.recnt_time_now = 0;
                MTKBle.this.mGatt = gatt;
                MTKBle.this.newState = true;
                MTKBle.this.isConnected = true;
                KLog.i(TAG, "connect success");
                MTKBle.mHandler.postDelayed(MTKBle.this.findCharacteristciTimeoutRunnable, 10000);
                KLog.e("---MtkBroadcastSender onConnectStateChangeWithStateCode");
                MtkBroadcastSender.getInstance(MTKBle.this.mContext).onConnectStateChangeWithStateCode(true, status, newState);
                return;
            }
            MTKBle.this.notifyMyAll();
            MTKBle.reopen_notify_times = 0;
            MTKBle.this.disconnect();
            MTKBle.mHandler.removeCallbacks(MTKBle.this.mDiscntTimeoutRunnable);
            MTKBle.mHandler.removeCallbacks(this.closeNotifyRunnable);
            MTKBle.this.clearCache();
            MTKBle.this.mGatt = null;
            MTKBle.this.notifyBatteryChanged(-1, false);
            MTKBle.this.newState = false;
            MTKBle.this.isConnected = false;
            MtkBroadcastSender.getInstance(MTKBle.this.mContext).onConnectStateChangeWithStateCode(false, status, newState);
            MTKBle.this.reconnect();
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (MTKBle.this.DBG) {
                KLog.e(TAG, "onServicesDiscovered--" + status);
                L.file("onServicesDiscovered--" + status, 4);
            }
            for (BluetoothGattService bluetoothGattService : gatt.getServices()) {
                if (bluetoothGattService.getUuid().equals(ZeronerUUID.BAND_SERVICE_MAIN) || bluetoothGattService.getUuid().equals(R1UUID.BAND_SERVICE_MAIN_R1)) {
                    KLog.i(TAG, "====走新协议===" + bluetoothGattService.getUuid());
                    L.file("====走新协议===" + bluetoothGattService.getUuid(), 4);
                    MtkBroadcastSender.getInstance(MTKBle.this.mContext).onServicesDiscovered(bluetoothGattService.getUuid().toString(), status);
                    MTKBle.this.characteristics = bluetoothGattService.getCharacteristics();
                    searchCharacteristicByUUid(MTKBle.this.characteristics);
                }
            }
            BluetoothGattService bas = gatt.getService(Service.BATTERY_SERVICE);
            if (bas != null) {
                MTKBle.this.mBatteryLevel = bas.getCharacteristic(Char.BATTERY_LEVEL);
                if (MTKBle.this.mBatteryLevel != null) {
                    KLog.d(TAG, "mBatteryLevel = " + MTKBle.this.mBatteryLevel.getUuid());
                    MTKBle.this.mClientCharConfig = MTKBle.this.mBatteryLevel.getDescriptor(Desc.CLIENT_CHARACTERISTIC_CONFIG);
                    List<BluetoothGattDescriptor> list = MTKBle.this.mBatteryLevel.getDescriptors();
                    if (list != null) {
                        KLog.d(TAG, "list = " + list.size());
                        for (BluetoothGattDescriptor desc : list) {
                            KLog.d(TAG, "desc = " + desc.getUuid());
                        }
                    }
                }
                if (MTKBle.this.mClientCharConfig != null) {
                    MTKBle.this.setNotifyEnabled();
                } else {
                    KLog.e(TAG, "mClientCharConfig = null");
                }
                if (MTKBle.this.mBatteryLevel != null) {
                    if (MTKBle.this.mGatt != null) {
                        KLog.d(TAG, "setCharacteristicNotification" + MTKBle.this.mBatteryLevel.getUuid());
                        MTKBle.this.mGatt.setCharacteristicNotification(MTKBle.this.mBatteryLevel, true);
                    }
                    MTKBle.this.readBatteryLevel();
                    return;
                }
                KLog.e(TAG, "mBatteryLevel = null");
                return;
            }
            KLog.e(TAG, "bas = null");
        }

        public void searchCharacteristicByUUid(List<BluetoothGattCharacteristic> characteristics) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                ThrowableExtension.printStackTrace(e);
            }
            for (BluetoothGattCharacteristic characteristic : characteristics) {
                if (ZeronerUUID.BAND_CHARACTERISTIC_NEW_INDICATE.equals(characteristic.getUuid())) {
                    MtkBroadcastSender.getInstance(MTKBle.this.mContext).onCharacteristicFound("0000ff23-0000-1000-8000-00805f9b34fb");
                    setCharacteristicNotification(characteristic, true);
                } else if (ZeronerUUID.BAND_CHARACTERISTIC_NEW_WRITE.equals(characteristic.getUuid())) {
                    KLog.i(TAG, "connect success（UUID:" + characteristic.getUuid().toString() + "）");
                    L.file("connect success（UUID:" + characteristic.getUuid().toString() + "）", 4);
                    MTKBle.mHandler.removeCallbacks(MTKBle.this.findCharacteristciTimeoutRunnable);
                    MtkBroadcastSender.getInstance(MTKBle.this.mContext).onCharacteristicFound("0000ff21-0000-1000-8000-00805f9b34fb");
                    MtkBroadcastSender.getInstance(MTKBle.this.mContext).onCanWriteDataToDev();
                    MTKBle.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            MtkBackgroundThreadManager.getInstance().wakeUp();
                        }
                    }, 1000);
                } else if (R1UUID.BAND_CHARACTERISTIC_NEW_NOTIFY.equals(characteristic.getUuid())) {
                    MTKBle.this.mHeartRateCharacter = characteristic;
                } else if (R1UUID.BAND_CHARACTERISTIC_CUSTOM_NOTIFY_HEART.equals(characteristic.getUuid())) {
                    KLog.i(String.format("get NewHeartRateCharacter", new Object[0]));
                    MTKBle.this.mNewHeartRateCharacter = characteristic;
                }
            }
        }

        public boolean setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
            if (MTKBle.this.mGatt == null) {
                KLog.i(TAG, "setCharacteristicNotification:mGatt==null");
                L.file("setCharacteristicNotification:mGatt==null", 4);
                return false;
            }
            boolean mgatt = MTKBle.this.mGatt.setCharacteristicNotification(characteristic, enabled);
            KLog.i(TAG, "mgattmgattmgattmgattmgatt" + mgatt);
            List<BluetoothGattDescriptor> descriptorList = characteristic.getDescriptors();
            if (descriptorList != null && descriptorList.size() > 0) {
                for (BluetoothGattDescriptor descriptor : descriptorList) {
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    GattRequestManager.getInstance().writeDescriptor(MTKBle.this.mGatt, descriptor);
                    KLog.i(TAG, characteristic.getUuid() + "=====================" + true);
                }
            }
            return true;
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            int format;
            int format2;
            if (R1UUID.BAND_CHARACTERISTIC_NEW_NOTIFY.equals(characteristic.getUuid())) {
                if ((characteristic.getProperties() & 1) != 0) {
                    format2 = 18;
                } else {
                    format2 = 17;
                }
                int heartRate = characteristic.getIntValue(format2, 1).intValue();
                KLog.e("no2855 receive heart rate data " + heartRate);
                MtkBroadcastSender.getInstance(MTKBle.this.mContext).onDataArrive(Constants.HEART_RATE_TYPE, String.valueOf(heartRate));
                return;
            }
            if (R1UUID.BAND_CHARACTERISTIC_CUSTOM_NOTIFY_HEART.equals(characteristic.getUuid())) {
                if ((characteristic.getProperties() & 1) != 0) {
                    format = 18;
                } else {
                    format = 17;
                }
                int i = 9;
                while (true) {
                    if (i < 0) {
                        break;
                    }
                    int validFlag = characteristic.getIntValue(format, i * 2).intValue();
                    int heartRate2 = characteristic.getIntValue(format, (i * 2) + 1).intValue();
                    KLog.e(String.format("---receive %d new heart rate data:%d,flag:%d", new Object[]{Integer.valueOf(i + 1), Integer.valueOf(heartRate2), Integer.valueOf(validFlag)}));
                    if (validFlag == 6 && heartRate2 > 0) {
                        MtkBroadcastSender.getInstance(MTKBle.this.mContext).onDataArrive(Constants.HEART_RATE_TYPE, String.valueOf(heartRate2));
                        break;
                    }
                    i--;
                }
            }
            byte[] data = characteristic.getValue();
            if (data[0] == 35 && data[1] == -1 && data[2] == 0) {
                KLog.i(TAG, "收到00数据, closeNotifyRunnable移除");
                MTKBle.mHandler.removeCallbacks(this.closeNotifyRunnable);
            }
            if (NotifyPackageContacter.getInstance().contactData(data)) {
                MTKBle.this.parseData(characteristic.getUuid(), NotifyPackageContacter.getInstance().getDatas());
            }
            L.file(Util.bytesToString(data), 2);
            if (characteristic != null) {
                MTKBle.this.onBatteryLevelChange(characteristic);
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (MTKBle.this.DBG) {
                KLog.d(TAG, "onCharacteristicRead: ");
            }
            if (characteristic != null) {
                MTKBle.this.onBatteryLevelChange(characteristic);
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (MTKBle.this.DBG) {
                KLog.d(TAG, "onCharacteristicWrite: " + Util.bytesToString(characteristic.getValue()));
            }
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            if (status == 0) {
                MtkBackgroundThreadManager.getInstance().removeTask();
                KLog.i(TAG, "将数据" + Util.bytesToString(characteristic.getValue()) + "写入特征（UUID:" + characteristic.getUuid().toString() + "）成功，等待回调响应...");
                L.file(Util.bytesToString(characteristic.getValue()), 1);
                MtkBroadcastSender.getInstance(MTKBle.this.mContext).onDataWrite(characteristic.getValue());
                return;
            }
            KLog.i(TAG, "将数据" + Util.bytesToString(characteristic.getValue()) + "写入特征（UUID:" + characteristic.getUuid().toString() + "）失败...");
            L.file("失败： " + Util.bytesToString(characteristic.getValue()), 1);
            ITask nowTask = MtkBackgroundThreadManager.getInstance().getNowTask();
            if (nowTask instanceof BleWriteDataTask) {
                BleWriteDataTask task = (BleWriteDataTask) nowTask;
                if (task.getRetryCount() < 2) {
                    task.setNeedRetry(true);
                    return;
                }
                task.setNeedRetry(false);
                MtkBroadcastSender.getInstance(MTKBle.this.mContext).onError(MTKBleError.BLE_WRITE_DATA_FAIL);
            }
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (MTKBle.this.DBG) {
                KLog.d(TAG, "onDescriptorRead:");
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (MTKBle.this.DBG) {
                KLog.d(TAG, "onDescriptorWrite: " + status);
            }
            if (status == 0) {
                KLog.i(TAG, "UUID:" + descriptor.getCharacteristic().getUuid().toString() + "）set notify success");
                L.file("UUID:" + descriptor.getCharacteristic().getUuid().toString() + "）set notify success", 4);
                return;
            }
            KLog.i(TAG, "UUID:" + descriptor.getCharacteristic().getUuid().toString() + "）set notify failure");
            L.file("UUID:" + descriptor.getCharacteristic().getUuid().toString() + "）set notify failure", 4);
        }

        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            if (MTKBle.this.DBG) {
                KLog.d(TAG, "onReadRemoteRssi: ");
            }
        }

        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            if (MTKBle.this.DBG) {
                KLog.d(TAG, "onReliableWriteCompleted: ");
            }
        }
    }

    static /* synthetic */ int access$1008() {
        int i = reopen_notify_times;
        reopen_notify_times = i + 1;
        return i;
    }

    public IDataReceiveHandler getDataReceiveHandler() {
        return this.mDataReceiveHandler;
    }

    public void setDataReceiveHandler(IDataReceiveHandler dataReceiveHandler) {
        this.mDataReceiveHandler = dataReceiveHandler;
    }

    public WristBand getWristBand() {
        return this.mWristBand;
    }

    public void setWristBand(WristBand wristBand) {
        this.mWristBand = wristBand;
    }

    public boolean isScanning() {
        return Scanner.getInstance(this.mContext).isScanning();
    }

    public boolean isConnecting() {
        return this.isConnecting;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public void setConnected(boolean connected) {
        this.isConnected = connected;
    }

    public boolean isNeedReconnect() {
        return this.needReconnect;
    }

    public static MTKBle getInstance(Context context) {
        if (sInstance == null) {
            synchronized (MTKBle.class) {
                if (sInstance == null) {
                    sInstance = new MTKBle(context);
                    LocalBluetoothLEManager.getInstance().init(context);
                }
            }
        }
        return sInstance;
    }

    public static MTKBle getInstance() {
        return sInstance;
    }

    private MTKBle(Context context) {
        boolean isSuccess = WearableManager.getInstance().init(Boolean.valueOf(true), context, null, R.xml.wearable_config);
        KLog.d(TAG, "WearableManager init " + isSuccess);
        L.file("WearableManager init " + isSuccess, 4);
        if (WearableManager.getInstance().getWorkingMode() != 1) {
            WearableManager.getInstance().switchMode();
        }
        this.mContext = context;
        this.mGattCallbackImpl = new GattCallbackImpl();
        TreeSet<UUID> uuidSet = new TreeSet<>();
        uuidSet.add(Char.BATTERY_LEVEL);
        uuidSet.add(Desc.CLIENT_CHARACTERISTIC_CONFIG);
        uuidSet.add(ZeronerUUID.BAND_CHARACTERISTIC_NEW_WRITE);
        uuidSet.add(ZeronerUUID.BAND_CHARACTERISTIC_NEW_INDICATE);
        uuidSet.add(R1UUID.BAND_CHARACTERISTIC_NEW_NOTIFY);
        uuidSet.add(R1UUID.BAND_SERVICE_MAIN_R1);
        uuidSet.add(R1UUID.BAND_CHARACTERISTIC_CUSTOM_NOTIFY_HEART);
        this.mGattCallbackImpl.addUuids(uuidSet);
        this.mGattCallbackImpl.setCallbackRunningInBluetoothThread(true);
        WearableClientProfileRegister.registerWearableClientProfile(this.mGattCallbackImpl, Looper.getMainLooper());
        WearableManager.getInstance().addController(EpoDownloadController.getInstance());
        MtkBroadcastSender.getInstance(context);
        MtkCmdAssembler.getInstance(context);
        this.mMtkBaseReceiver = new MtkBaseReceiver();
        LocalBroadcastManager.getInstance(this.mContext).registerReceiver(this.mMtkBaseReceiver, getIntentFilter());
        sInstance = this;
    }

    public void registerBatteryChangeListener(BatteryChangeListener listener) {
        this.mBatteryChangeListener = listener;
    }

    public void unregisterBatteryChangeListener() {
        this.mBatteryChangeListener = null;
    }

    public void onScanResult(BluetoothDevice device, int rssi, byte[] scanRecord, String nameFromScanRecord) {
    }

    public void onError(int code) {
    }

    /* access modifiers changed from: private */
    public void readBatteryLevel() {
        if (this.mGatt != null && this.mBatteryLevel != null) {
            GattRequestManager.getInstance().readCharacteristic(this.mGatt, this.mBatteryLevel);
        }
    }

    /* access modifiers changed from: private */
    public void setNotifyEnabled() {
        if (this.mGatt != null && this.mClientCharConfig != null) {
            this.mClientCharConfig.setValue(this.CLIENT_CHAR_VALUE);
            GattRequestManager.getInstance().writeDescriptor(this.mGatt, this.mClientCharConfig);
        }
    }

    /* access modifiers changed from: private */
    public void onBatteryLevelChange(BluetoothGattCharacteristic characteristic) {
        if (characteristic != null) {
            BluetoothGattService service = characteristic.getService();
            if (service != null && service.getUuid().equals(Service.BATTERY_SERVICE) && characteristic.getUuid().equals(Char.BATTERY_LEVEL)) {
                byte[] bytes = characteristic.getValue();
                if (bytes != null && bytes.length > 0) {
                    notifyBatteryChanged(bytes[0], true);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyBatteryChanged(int level, boolean needNotify) {
        if (this.mBatteryChangeListener != null) {
            if (this.mCurrentBatteryLevel == level) {
                needNotify = false;
            }
            this.mCurrentBatteryLevel = level;
            this.mBatteryChangeListener.onBatteryValueChanged(level, needNotify);
        }
    }

    public void writeCharacteristicNewAPI(UUID uuid, byte[] data) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        try {
            BluetoothGattCharacteristic characteristic = getCharacteristic(uuid);
            if (this.mGatt == null || characteristic == null) {
                String str = TAG;
                Object[] objArr = new Object[1];
                StringBuilder append = new StringBuilder().append("mGatt==null?:");
                if (this.mGatt == null) {
                    z = true;
                } else {
                    z = false;
                }
                objArr[0] = append.append(z).append("/characteristic==null?:").append(characteristic == null).toString();
                KLog.i(str, objArr);
                StringBuilder append2 = new StringBuilder().append("mGatt==null?:");
                if (this.mGatt == null) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                StringBuilder append3 = append2.append(z2).append("/characteristic==null?:");
                if (characteristic != null) {
                    z3 = false;
                }
                L.file(append3.append(z3).toString(), 4);
                return;
            }
            characteristic.setWriteType(1);
            characteristic.setValue(data);
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            GattRequestManager.getInstance().writeCharacteristic(this.mGatt, characteristic);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    public void notifyMyAll() {
        synchronized (mLock) {
            mLock.notifyAll();
        }
    }

    public BluetoothGattCharacteristic getCharacteristic(UUID uuid) {
        try {
            for (BluetoothGattCharacteristic characteristic : this.characteristics) {
                if (characteristic.getUuid().equals(uuid)) {
                    return characteristic;
                }
            }
            return null;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            KLog.e(TAG, "getCharacteristic-->Exception");
            return null;
        }
    }

    public void setNeedReconnect(boolean needReconnect2) {
        this.needReconnect = needReconnect2;
    }

    public synchronized boolean connect() {
        boolean connect;
        if (this.mWristBand == null) {
            connect = false;
        } else {
            connect = connect(this.mWristBand.getDev_addr());
        }
        return connect;
    }

    public synchronized boolean connect(String devAddress) {
        boolean z = true;
        boolean z2 = false;
        synchronized (this) {
            KLog.i("---MTKBle connect");
            if (this.isConnecting || this.mWristBand == null) {
                String str = TAG;
                Object[] objArr = new Object[1];
                objArr[0] = "有设备正在连接: " + this.isConnecting + "/mWristBand==null?:" + (this.mWristBand == null);
                KLog.i(str, objArr);
                StringBuilder append = new StringBuilder().append("有设备正在连接: ").append(this.isConnecting).append("/mWristBand==null?:");
                if (this.mWristBand != null) {
                    z = false;
                }
                L.file(append.append(z).toString(), 4);
            } else if (!BluetoothUtils.isEnabledBluetooth(this.mContext)) {
                KLog.i(TAG, "蓝牙不可用");
                L.file("蓝牙不可用", 4);
                this.isConnecting = false;
            } else {
                if (Util.getBrand().equalsIgnoreCase("oppo")) {
                    stopScan();
                }
                WearableManager.getInstance().setRemoteDevice(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(devAddress));
                MtkBroadcastSender.getInstance(this.mContext).onStartConnect();
                this.isConnecting = true;
                this.isConnected = false;
                mHandler.removeCallbacks(this.mTimeoutRunnable);
                mHandler.removeCallbacks(this.findCharacteristciTimeoutRunnable);
                mHandler.removeCallbacks(this.reconnectRunnable);
                mHandler.postDelayed(this.mTimeoutRunnable, 40000);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        WearableManager.getInstance().connect();
                    }
                }, 1000);
                z2 = true;
            }
        }
        return z2;
    }

    private synchronized boolean connect(BluetoothDevice bluetoothDevice) {
        boolean z = false;
        synchronized (this) {
            if (this.isConnecting) {
                KLog.i(TAG, "有设备正在连接......");
                L.file("有设备正在连接......", 4);
            } else if (!BluetoothUtils.isEnabledBluetooth(this.mContext)) {
                KLog.i(TAG, "蓝牙不可用");
                L.file("蓝牙不可用", 4);
                this.isConnecting = false;
            } else {
                if (bluetoothDevice == null) {
                    WearableManager.getInstance().setRemoteDevice(bluetoothDevice);
                }
                this.isConnecting = true;
                this.isConnected = false;
                mHandler.removeCallbacks(this.mTimeoutRunnable);
                mHandler.removeCallbacks(this.findCharacteristciTimeoutRunnable);
                mHandler.removeCallbacks(this.reconnectRunnable);
                mHandler.postDelayed(this.mTimeoutRunnable, 20000);
                WearableManager.getInstance().connect();
                z = true;
            }
        }
        return z;
    }

    public void unbindDevice() {
        mHandler.removeCallbacks(this.mTimeoutRunnable);
        mHandler.removeCallbacks(this.findCharacteristciTimeoutRunnable);
        mHandler.removeCallbacks(this.reconnectRunnable);
        if (isConnecting()) {
            disconnect();
        }
        this.isConnecting = false;
    }

    public void disconnect() {
        mHandler.removeCallbacks(this.mDiscntTimeoutRunnable);
        KLog.i(TAG, "mtk代码断连...");
        L.file("mtk代码断连...", 4);
        WearableManager.getInstance().disconnect();
        MtkBackgroundThreadManager.getInstance().needWait();
    }

    public void reconnect() {
        if (!this.needReconnect) {
            L.file("无需重连", 4);
            KLog.i(TAG, "无需重连");
        } else if (this.isConnecting) {
            KLog.i(TAG, "mtk重连，但有设备正在连接......");
            L.file("mtk重连，但有设备正在连接......", 4);
        } else {
            if (Util.getBrand().equalsIgnoreCase("oppo")) {
                mHandler.post(new Runnable() {
                    public void run() {
                        MTKBle.this.startScan();
                    }
                });
            }
            BluetoothDevice device = WearableManager.getInstance().getRemoteDevice();
            KLog.i(TAG, "wearableManager远端设备" + device);
            L.file("wearableManager远端设备" + device, 4);
            if (device == null) {
                this.isConnecting = false;
                return;
            }
            this.isConnecting = true;
            this.isConnected = false;
            this.recnt_time_now++;
            if (this.recnt_time_now <= 3) {
                KLog.i(TAG, "重连第：" + this.recnt_time_now + "次");
                KLog.i(TAG, "wearableManager远端设备连接中--" + device.getName() + "/" + device.getAddress());
                L.file("wearableManager远端设备连接中--" + device.getName() + "/" + device.getAddress(), 4);
                mHandler.removeCallbacks(this.mTimeoutRunnable);
                mHandler.removeCallbacks(this.findCharacteristciTimeoutRunnable);
                mHandler.removeCallbacks(this.reconnectRunnable);
                mHandler.postDelayed(this.mTimeoutRunnable, 40000);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        WearableManager.getInstance().connect();
                    }
                }, 1000);
                return;
            }
            KLog.i(TAG, "重连次数超出了设定限制，不重连了1...");
            L.file("重连次数超出了设定限制，不重连了1...", 4);
            this.recnt_time_now = 0;
            this.isConnecting = false;
            this.isConnected = false;
            mHandler.removeCallbacks(this.mTimeoutRunnable);
            mHandler.removeCallbacks(this.findCharacteristciTimeoutRunnable);
            mHandler.removeCallbacks(this.reconnectRunnable);
        }
    }

    public void startScan() {
        Scanner.getInstance(this.mContext).setIScanCallback(this);
        Scanner.getInstance(this.mContext).startScan(ZeronerUUID.BAND_SERVICE_MAIN);
        WearableManager.getInstance().scanDevice(true);
    }

    public void stopScan() {
        Scanner.getInstance(this.mContext).stopScan();
        WearableManager.getInstance().scanDevice(false);
    }

    public void clearCache() {
        mHandler.post(new Runnable() {
            public void run() {
                if (MTKBle.this.mGatt != null) {
                    MTKBle.this.mGatt.close();
                    MTKBle.this.refreshDeviceCache(MTKBle.this.mGatt);
                }
            }
        });
    }

    public BluetoothDevice getBluetoothDev() {
        return WearableManager.getInstance().getRemoteDevice();
    }

    public String getDevAddress() {
        if (WearableManager.getInstance().getRemoteDevice() != null) {
            return WearableManager.getInstance().getRemoteDevice().getAddress();
        }
        return null;
    }

    public void setRemoteDevice(BluetoothDevice bluetoothDevice) {
        WearableManager.getInstance().setRemoteDevice(bluetoothDevice);
    }

    public void writeDataToWristBand(byte[] data) {
        writeCharacteristicNewAPI(ZeronerUUID.BAND_CHARACTERISTIC_NEW_WRITE, data);
    }

    /* access modifiers changed from: private */
    public void parseData(UUID uuid, byte[] datas) {
        if (datas.length >= 3) {
            MtkBroadcastSender.getInstance(this.mContext).onDataArrive(datas[2], MtkDataParser.parseData(datas[2], datas));
        }
    }

    /* access modifiers changed from: protected */
    public boolean refreshDeviceCache(BluetoothGatt gatt) {
        try {
            Method refresh = BluetoothGatt.class.getMethod("refresh", new Class[0]);
            if (refresh != null) {
                boolean success = ((Boolean) refresh.invoke(gatt, new Object[0])).booleanValue();
                KLog.i(TAG, "Refreshing result: " + success);
                return success;
            }
        } catch (Exception e) {
            KLog.i(TAG, "An exception occured while refreshing device " + e.toString());
        }
        return false;
    }

    public IntentFilter getIntentFilter() {
        return MtkBroadcastSender.getInstance(this.mContext).getIntentFilter();
    }

    public void switchStandardHeartRate(boolean enabled) {
        if (this.mHeartRateCharacter == null) {
            KLog.e("heart character not find in device");
            return;
        }
        KLog.i(TAG, "switchStandardHeartRate");
        if (this.mGatt == null || this.mHeartRateCharacter == null) {
            KLog.i("setCharacteristicNotification:mGatt==null");
            return;
        }
        boolean characteristicNotification = this.mGatt.setCharacteristicNotification(this.mHeartRateCharacter, enabled);
        List<BluetoothGattDescriptor> descriptorList = this.mHeartRateCharacter.getDescriptors();
        if (descriptorList != null && descriptorList.size() > 0) {
            for (BluetoothGattDescriptor descriptor : descriptorList) {
                if (enabled) {
                    KLog.i(TAG, "ENABLE_NOTIFICATION_VALUE");
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                } else {
                    KLog.i(TAG, "DISABLE_NOTIFICATION_VALUE");
                    descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                }
                GattRequestManager.getInstance().writeDescriptor(this.mGatt, descriptor);
                KLog.i(TAG, this.mHeartRateCharacter.getUuid() + "=====================" + true);
            }
        }
    }
}
