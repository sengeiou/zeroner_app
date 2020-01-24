package com.iwown.ble_module.iwown.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ITask;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.iwown.task.ZeronerBleWriteDataTask;
import com.iwown.ble_module.iwown.utils.BluetoothEnableUtils;
import com.iwown.ble_module.iwown.utils.Constants.CustomUUID;
import com.iwown.ble_module.iwown.utils.Constants.R1UUID;
import com.iwown.ble_module.iwown.utils.ThreadUtils;
import com.iwown.ble_module.iwown.utils.ThreadUtils.TimeTask;
import com.iwown.ble_module.utils.Util;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

public class ZeronerBle extends AbsBle {
    private static ZeronerBle instance;
    private static final Object mLock = new Object();
    /* access modifiers changed from: private */
    public int dataLength;
    /* access modifiers changed from: private */
    public byte[] datas;
    /* access modifiers changed from: private */
    public boolean isDataOver;
    private boolean isNeedReconnect;
    private Runnable mConnectRunnable = new Runnable() {
        public void run() {
            try {
                ZeronerBle.this.connect(ZeronerBle.this.mWristBand.getAddress());
            } catch (Exception e) {
                L.file("connect error : " + e.toString(), 4);
                ZeronerBle.this.mIsConnecting = false;
                ZeronerBle.this.mConnectTime = 0;
            }
        }
    };
    /* access modifiers changed from: private */
    public int mConnectTime;
    /* access modifiers changed from: private */
    public TimeTask mDiscoverServiceTimeoutRunnable = new TimeTask() {
        /* access modifiers changed from: protected */
        public void task() {
            L.file("discoverService timeout", 4);
            ZeronerBle.this.disconnect();
        }
    };
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(final BluetoothGatt gatt, int status, int newState) {
            String address = gatt.getDevice().getAddress();
            ZeronerBle.this.mHandler.removeCallbacks(ZeronerBle.this.mTimeoutRunnable);
            ThreadUtils.cancel(ZeronerBle.this.mDiscoverServiceTimeoutRunnable);
            L.file("address " + address + " status " + status + " newState " + newState, 4);
            KLog.e("no2855address " + address + " status " + status + " newState " + newState);
            if (status != 0) {
                ZeronerBle.this.notifyMyAll();
                ZeronerBle.this.mIsConnected = false;
                ZeronerBle.this.disconnect(address, false);
                ZeronerBle.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        ZeronerBle.this.refreshDeviceCache(gatt);
                        gatt.close();
                        ZeronerBle.this.mIsDisconnecting = false;
                    }
                }, 1000);
                ZeronerBle.this.mService.bleGattDisConnected(address);
                if (status == 257) {
                    ZeronerBle.this.mService.bleConnectError257(address);
                } else {
                    ZeronerBle.this.reconnect();
                }
            } else if (newState == 2) {
                ZeronerBle.this.mIsConnected = true;
                ZeronerBle.this.mHandler.removeCallbacks(ZeronerBle.this.mReconnectRunnable);
                ZeronerBle.this.mService.bleGattConnected(gatt.getDevice());
                ZeronerBle.this.waitFor(2000);
                boolean discoverServices = gatt.discoverServices();
                L.file("discoverServices : " + discoverServices, 4);
                if (discoverServices) {
                    ThreadUtils.postDelay(ZeronerBle.this.mDiscoverServiceTimeoutRunnable, 60000);
                } else {
                    ZeronerBle.this.disconnect();
                }
            } else if (newState == 0) {
                ZeronerBle.this.mIsConnected = false;
                ZeronerBle.this.notifyMyAll();
                ZeronerBle.this.mService.bleGattDisConnected(address);
                ZeronerBle.this.disconnect(address, false);
                ZeronerBle.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        ZeronerBle.this.refreshDeviceCache(gatt);
                        gatt.close();
                        ZeronerBle.this.mIsDisconnecting = false;
                    }
                }, 1000);
                ZeronerBle.this.reconnect();
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            String address = gatt.getDevice().getAddress();
            ThreadUtils.cancel(ZeronerBle.this.mDiscoverServiceTimeoutRunnable);
            L.file("address " + address + " status " + status, 4);
            if (status != 0) {
                ZeronerBle.this.disconnect();
                return;
            }
            ZeronerBle.this.mIsConnecting = false;
            for (BluetoothGattService bluetoothGattService : gatt.getServices()) {
                if (bluetoothGattService.getUuid().equals(CustomUUID.BAND_SERVICE_MAIN)) {
                    KLog.i("====sdk-type-1===zeroner" + bluetoothGattService.getUuid());
                    L.file("====sdk-type-1===zeroner" + bluetoothGattService.getUuid(), 4);
                    ZeronerBle.this.characteristics = bluetoothGattService.getCharacteristics();
                    ZeronerBle.this.searchCharacteristicByUUid();
                    return;
                }
            }
            ZeronerBle.this.disconnect();
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            KLog.d("onCharacteristicRead " + gatt.getDevice().getAddress() + " status " + status);
            if (status == 0) {
                ZeronerBle.this.mService.bleCharacteristicRead(gatt.getDevice().getAddress(), characteristic.getUuid().toString(), status, characteristic.getValue(), characteristic);
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            KLog.d("onCharacteristicChanged " + gatt.getDevice().getAddress());
            if (CustomUUID.BAND_CHARACTERISTIC_NEW_NOTIFY.equals(characteristic.getUuid()) || CustomUUID.BAND_CHARACTERISTIC_NEW_INDICATE.equals(characteristic.getUuid())) {
                byte[] data = characteristic.getValue();
                try {
                    L.file(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + " : " + Util.bytesToString(data), 2);
                } catch (Exception e1) {
                    ThrowableExtension.printStackTrace(e1);
                }
                if (data != null && data.length != 0) {
                    KLog.i("接收原始数据 未处理 datas--->" + Util.bytesToString(data));
                    if (!ZeronerBle.this.isDataOver || ZeronerBle.this.dataLength != -1) {
                        if ((data[0] == 35 || data[0] == 34) && data.length > 3 && data[1] == -1) {
                            ZeronerBle.this.datas = new byte[0];
                            ZeronerBle.this.dataLength = data[3];
                        }
                    } else if (data[0] == 35 || data[0] == 34) {
                        ZeronerBle.this.dataLength = data[3];
                        ZeronerBle.this.datas = new byte[0];
                    }
                    if (ZeronerBle.this.dataLength != -1) {
                        ZeronerBle.this.datas = Util.concat(ZeronerBle.this.datas, data);
                    }
                    try {
                        if (ZeronerBle.this.dataLength != -1 && ZeronerBle.this.datas.length - 4 >= ZeronerBle.this.dataLength) {
                            KLog.i("接收数据长度 datas--->" + (ZeronerBle.this.datas.length - 4) + "    ff = " + ZeronerBle.this.datas[1]);
                            KLog.i("接收原始数据 datas--->" + Util.bytesToString(ZeronerBle.this.datas));
                            ZeronerBle.this.clearDataState();
                            ZeronerBle.this.parseData(characteristic.getUuid(), ZeronerBle.this.datas);
                        } else if (ZeronerBle.this.dataLength != -1) {
                            ZeronerBle.this.isDataOver = false;
                        }
                    } catch (Exception e) {
                        ZeronerBle.this.clearDataState();
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            String address = gatt.getDevice().getAddress();
            Object value = characteristic.getValue();
            KLog.d("onCharacteristicWrite " + address + " status " + status + " datas " + Util.bytesToString(characteristic.getValue()));
            if (ZeronerBackgroundThreadManager.getInstance().getNowTaskNotWait() instanceof ZeronerBleWriteDataTask) {
                ZeronerBleWriteDataTask task = (ZeronerBleWriteDataTask) ZeronerBackgroundThreadManager.getInstance().getNowTaskNotWait();
                int size = task.getBean().getData().size();
                if (size <= 0) {
                    ZeronerBackgroundThreadManager.getInstance().removeTask();
                } else if (value == task.getBean().getData().get(size - 1)) {
                    ZeronerBackgroundThreadManager.getInstance().removeTask();
                }
            } else {
                ZeronerBackgroundThreadManager.getInstance().removeTask();
            }
            if (status == 0) {
                ZeronerBle.this.mService.bleCharacteristicWrite(gatt.getDevice().getAddress(), characteristic.getUuid().toString(), status, characteristic.getValue());
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        }
    };
    /* access modifiers changed from: private */
    public boolean mIsDisconnecting;
    /* access modifiers changed from: private */
    public Runnable mReconnectRunnable = new Runnable() {
        public void run() {
            if (ZeronerBle.this.mConnectTime < 3) {
                L.file("reconnect times : " + ZeronerBle.this.mConnectTime, 4);
                ZeronerBle.this.mConnectTime = ZeronerBle.this.mConnectTime + 1;
                ZeronerBle.this.connect();
                return;
            }
            ZeronerBle.this.mConnectTime = 0;
        }
    };
    /* access modifiers changed from: private */
    public BleService mService;
    /* access modifiers changed from: private */
    public Runnable mTimeoutRunnable = new Runnable() {
        public void run() {
            ZeronerBle.this.mIsConnecting = false;
            ZeronerBle.this.disconnect();
            L.file("没有收到系统onConnectionStateChange回调", 4);
            ZeronerBle.this.mService.bleNoCallback();
            ZeronerBle.this.reconnect();
        }
    };
    private int mWriteErrorCount;

    private ZeronerBle(BleService service) {
        super(service);
        instance = this;
        this.mService = service;
        this.mBluetoothGatts = new HashMap();
    }

    public static ZeronerBle getInstance(BleService service) {
        if (instance == null) {
            return new ZeronerBle(service);
        }
        return instance;
    }

    public static ZeronerBle getInstance() {
        return instance;
    }

    public void setNeedReconnect(boolean needReconnect) {
        this.isNeedReconnect = needReconnect;
    }

    public boolean connect(String address) {
        ZeronerBackgroundThreadManager.getInstance().setWriteUnbind(false);
        clearDataState();
        this.mIsConnecting = true;
        if (!BluetoothEnableUtils.isEnabledBluetooth(this.mService)) {
            L.file("蓝牙不可用", 4);
            this.mIsConnecting = false;
            return false;
        }
        this.mService.bleStartConnect();
        if (Util.getBrand().equalsIgnoreCase("oppo")) {
            stopScan();
        }
        this.mConnectTime++;
        this.mWriteErrorCount = 0;
        ZeronerBackgroundThreadManager.getInstance().needWait();
        L.file("connect threadId = " + Thread.currentThread().getId() + "   name" + Thread.currentThread().getName() + "  +++ CONNECT 设备:   address : " + address, 4);
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
        this.mHandler.postDelayed(this.mTimeoutRunnable, 60000);
        BluetoothGatt gatt = this.mBtAdapter.getRemoteDevice(address).connectGatt(this.mService, false, this.mGattCallback);
        if (gatt == null) {
            L.file("gatt = null", 4);
            this.mBluetoothGatts.remove(address);
            return false;
        }
        L.file("gatt != null", 4);
        this.mBluetoothGatts.put(address, gatt);
        return true;
    }

    public void disconnect(String address, boolean needCloseGatt) {
        L.file("---disconnect---" + needCloseGatt, 4);
        this.mIsConnecting = false;
        clearDataState();
        this.mIsConnected = false;
        this.characteristics = null;
        this.mIsDisconnecting = true;
        if (this.mBluetoothGatts.containsKey(address) && BluetoothEnableUtils.isEnabledBluetooth(this.mService)) {
            ZeronerBackgroundThreadManager.getInstance().needWait();
            final BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.remove(address);
            if (gatt != null) {
                gatt.disconnect();
                if (needCloseGatt) {
                    this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            L.file("gatt close", 4);
                            ZeronerBle.this.refreshDeviceCache(gatt);
                            gatt.close();
                            ZeronerBle.this.mIsDisconnecting = false;
                        }
                    }, 1000);
                }
            }
        }
    }

    public boolean discoverServices(String address) {
        BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.get(address);
        if (gatt == null) {
            return false;
        }
        boolean ret = gatt.discoverServices();
        if (ret) {
            return ret;
        }
        disconnect(address, true);
        return ret;
    }

    /* access modifiers changed from: private */
    public void clearDataState() {
        this.isDataOver = true;
        this.dataLength = -1;
    }

    /* access modifiers changed from: private */
    public void waitFor(long time) {
        synchronized (mLock) {
            try {
                mLock.wait(time);
            } catch (InterruptedException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyMyAll() {
        synchronized (mLock) {
            mLock.notifyAll();
        }
    }

    /* access modifiers changed from: private */
    public void reconnect() {
        if (ZeronerBackgroundThreadManager.getInstance().isWriteUnbind() || !this.isNeedReconnect) {
            this.mWristBand = null;
            return;
        }
        if (Util.getBrand().equalsIgnoreCase("oppo")) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    ZeronerBle.this.startScan(true);
                }
            });
        }
        this.mHandler.removeCallbacks(this.mReconnectRunnable);
        this.mHandler.postDelayed(this.mReconnectRunnable, BootloaderScanner.TIMEOUT);
    }

    public void unbindDevice() {
        this.mHandler.removeCallbacks(this.mReconnectRunnable);
        this.mHandler.removeCallbacks(this.mConnectRunnable);
        if (isConnecting()) {
            disconnect();
            this.mHandler.removeCallbacks(this.mTimeoutRunnable);
        }
        this.mIsConnecting = false;
    }

    public synchronized boolean connect() {
        L.file("mIsConnecting : " + this.mIsConnecting + "  mIsConnected : " + this.mIsConnected + " mWristBand" + this.mWristBand, 4);
        if (!isConnecting() && !isConnected()) {
            if (Util.getBrand().equalsIgnoreCase("oppo")) {
                stopScan();
            }
            this.mHandler.removeCallbacks(this.mReconnectRunnable);
            this.mHandler.removeCallbacks(this.mConnectRunnable);
            if (this.mWristBand != null) {
                if (this.mIsDisconnecting) {
                    L.file("延迟2.5s进行连接", 4);
                    this.mHandler.postDelayed(this.mConnectRunnable, 1500);
                } else {
                    this.mHandler.post(this.mConnectRunnable);
                }
            }
        }
        return false;
    }

    public void searchCharacteristicByUUid() {
        for (BluetoothGattCharacteristic characteristic : this.characteristics) {
            if (CustomUUID.BAND_CHARACTERISTIC_NEW_INDICATE.equals(characteristic.getUuid())) {
                if (!setCharacteristicNotification(characteristic, true)) {
                    setCharacteristicNotification(characteristic, true);
                    L.file("UUID:" + characteristic.getUuid().toString() + "）set notify failure", 4);
                }
            } else if (CustomUUID.BAND_CHARACTERISTIC_NEW_NOTIFY.equals(characteristic.getUuid())) {
                if (!setCharacteristicNotification(characteristic, true)) {
                    setCharacteristicNotification(characteristic, true);
                    L.file("UUID:" + characteristic.getUuid().toString() + "）set notify failure", 4);
                }
            } else if (CustomUUID.BAND_CHARACTERISTIC_NEW_WRITE.equals(characteristic.getUuid())) {
                this.mConnectTime = 0;
                L.file("connect success（UUID:" + characteristic.getUuid().toString() + "）", 4);
                this.mService.bleCharacteristicNotification(this.mWristBand.getAddress(), characteristic.getUuid(), true);
                this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        ZeronerBackgroundThreadManager.getInstance().wakeUp();
                    }
                }, 1000);
            } else if (R1UUID.BAND_CHARACTERISTIC_NEW_NOTIFY.equals(characteristic.getUuid())) {
                this.mHeartRateCharacter = characteristic;
            }
        }
    }

    public boolean setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.get(this.mWristBand.getAddress());
        if (gatt == null || characteristic == null) {
            return false;
        }
        if (this.mBtAdapter == null || gatt == null) {
            KLog.w("BluetoothAdapter not initialized");
            return false;
        } else if (!gatt.setCharacteristicNotification(characteristic, enabled)) {
            KLog.e("Enabling notification failed!");
            return false;
        } else {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(CustomUUID.BAND_DES_UUID);
            int properties = characteristic.getProperties();
            KLog.d("properties = " + properties);
            if ((properties & 32) != 0) {
                descriptor.setValue(enabled ? BluetoothGattDescriptor.ENABLE_INDICATION_VALUE : new byte[]{0, 0});
            } else if ((properties & 16) != 0) {
                descriptor.setValue(enabled ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : new byte[]{0, 0});
            } else {
                descriptor.setValue(enabled ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : new byte[]{0, 0});
            }
            boolean rst = gatt.writeDescriptor(descriptor);
            if (rst) {
                L.file("UUID:" + characteristic.getUuid().toString() + "set notify success", 4);
            }
            return rst;
        }
    }

    public void disconnect() {
        if (this.mWristBand != null) {
            disconnect(this.mWristBand.getAddress(), true);
        }
    }

    /* access modifiers changed from: private */
    public void parseData(UUID uuid, byte[] datas2) {
        if (datas2.length >= 3) {
            this.mService.bleCharacteristicChanged(this.mWristBand.getAddress(), uuid.toString(), datas2);
        }
    }

    public void writeDataToWristBand(byte[] data) {
        writeCharacteristicNewAPI(CustomUUID.BAND_CHARACTERISTIC_NEW_WRITE, data);
    }

    public void writeCharacteristicNewAPI(UUID uuid, byte[] data) {
        try {
            if (BluetoothEnableUtils.isEnabledBluetooth(this.mService) && this.mWristBand != null) {
                BluetoothGattCharacteristic characteristic = getCharacteristic(uuid);
                BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.get(this.mWristBand.getAddress());
                if (gatt != null && characteristic != null) {
                    characteristic.setWriteType(1);
                    characteristic.setValue(data);
                    if (gatt.writeCharacteristic(characteristic)) {
                        try {
                            L.file(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + " : " + Util.bytesToString(data), 1);
                        } catch (Exception e1) {
                            ThrowableExtension.printStackTrace(e1);
                        }
                        KLog.i("将数据" + Util.bytesToString(data) + "写入特征（UUID:" + uuid.toString() + "）成功，等待回调响应...");
                        return;
                    }
                    L.file("ThreadId = " + Thread.currentThread().getId() + "  --写入失败！UUID：" + uuid.toString() + "，数据为：" + Util.bytesToString(data), 1);
                    this.mWriteErrorCount++;
                    if (this.mWriteErrorCount >= 10) {
                        L.file("写入失败10次判断为断连", 1);
                        disconnect();
                    }
                    ITask nowTask = ZeronerBackgroundThreadManager.getInstance().getNowTask();
                    if (nowTask instanceof ZeronerBleWriteDataTask) {
                        DataBean dataBean = ((ZeronerBleWriteDataTask) nowTask).getBean();
                        if (dataBean.getRetryCount() < 5) {
                            dataBean.setNeedRetry(true);
                        } else {
                            dataBean.setNeedRetry(false);
                        }
                    }
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void switchStandHeartRate(boolean enabled) {
        KLog.i("switchStandardHeartRate");
        if (setCharacteristicNotification(this.mHeartRateCharacter, enabled)) {
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                }
            }, 0);
            return;
        }
        setCharacteristicNotification(this.mHeartRateCharacter, enabled);
        KLog.i("UUID:" + this.mHeartRateCharacter.getUuid().toString() + "）set standard hr notify failure");
    }
}
