package com.iwown.ble_module.zg_ble.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.ParcelUuid;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.utils.ThreadUtils;
import com.iwown.ble_module.iwown.utils.ThreadUtils.TimeTask;
import com.iwown.ble_module.utils.BluetoothUtils;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.callback.BleGattCallback;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.ble_module.zg_ble.task.TaskHandler;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.iwown.ble_module.zg_ble.utils.Util;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.HashMap;
import java.util.UUID;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

public class ZGBle extends ZGAbsBle {
    private static ZGBle instance;
    private BleGattCallback bleGattCallback;
    private int dataLength;
    private byte[] datas;
    private boolean isDataOver;
    private boolean isNeedReconnect;
    private Runnable mConnectRunnable = new Runnable() {
        public void run() {
            try {
                KLog.i(" connect(mWristBand.getAddress());");
                ZGBle.this.connect(ZGBle.this.mWristBand.getDev_addr());
            } catch (Exception e) {
                L.file("connect error : " + e.toString(), 4);
                ZGBle.this.mIsConnecting = false;
                ZGBle.this.mConnectTime = 0;
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
            ZGBle.this.disconnect();
        }
    };
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(final BluetoothGatt gatt, int status, int newState) {
            String address = gatt.getDevice().getAddress();
            ZGBle.this.mHandler.removeCallbacks(ZGBle.this.mTimeoutRunnable);
            ThreadUtils.cancel(ZGBle.this.mDiscoverServiceTimeoutRunnable);
            L.file("address " + address + " status " + status + " newState " + newState, 4);
            KLog.e("========= zg onConnectionStateChange============", "address " + address + " status " + status + " newState " + newState);
            if (status != 0) {
                ZGBle.this.notifyMyAll();
                ZGBle.this.mIsConnected = false;
                ZGBle.this.disconnect(address, false);
                ZGBle.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        ZGBle.this.refreshDeviceCache(gatt);
                        gatt.close();
                        ZGBle.this.mIsDisconnecting = false;
                    }
                }, 1000);
                ZGBle.this.mService.bleGattDisConnected(address);
                if (status == 257) {
                    ZGBle.this.mService.bleConnectError257(address);
                } else {
                    ZGBle.this.reconnect();
                }
            } else if (newState == 2) {
                ZGBle.this.mIsConnected = true;
                ZGBle.this.mHandler.removeCallbacks(ZGBle.this.mReconnectRunnable);
                ZGBle.this.mService.bleGattConnected(gatt.getDevice());
                ZGBle.this.waitFor(2000);
                boolean discoverServices = gatt.discoverServices();
                L.file("discoverServices : " + discoverServices, 4);
                if (discoverServices) {
                    ThreadUtils.postDelay(ZGBle.this.mDiscoverServiceTimeoutRunnable, 60000);
                } else {
                    ZGBle.this.disconnect();
                }
            } else if (newState == 0) {
                ZGBle.this.mIsConnected = false;
                ZGBle.this.notifyMyAll();
                ZGBle.this.mService.bleGattDisConnected(address);
                ZGBle.this.disconnect(address, false);
                ZGBle.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        ZGBle.this.refreshDeviceCache(gatt);
                        gatt.close();
                        ZGBle.this.mIsDisconnecting = false;
                    }
                }, 1000);
                ZGBle.this.reconnect();
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            String address = gatt.getDevice().getAddress();
            ThreadUtils.cancel(ZGBle.this.mDiscoverServiceTimeoutRunnable);
            L.file("address " + address + " status " + status, 4);
            if (status != 0) {
                ZGBle.this.disconnect();
                return;
            }
            ZGBle.this.mIsConnecting = false;
            for (BluetoothGattService bluetoothGattService : gatt.getServices()) {
                if (bluetoothGattService.getUuid().equals(ZGUUID.BAND_SERVICE_MAIN_ZG)) {
                    KLog.i("====sdk-type-3=zg===" + bluetoothGattService.getUuid());
                    L.file("====sdk-type-3=zg===" + bluetoothGattService.getUuid(), 4);
                    ZGBle.this.mService.bleServiceDiscovered(address, bluetoothGattService.getUuid());
                    ZGBle.this.characteristics = bluetoothGattService.getCharacteristics();
                    ZGBle.this.searchCharacteristicByUUid();
                    return;
                }
            }
            ZGBle.this.disconnect();
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            KLog.d("onCharacteristicRead: " + gatt.getDevice().getAddress() + " status " + status);
            if (status == 0) {
                ZGBle.this.mService.bleCharacteristicRead(gatt.getDevice().getAddress(), characteristic.getUuid().toString(), status, characteristic.getValue(), characteristic);
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (com.iwown.ble_module.zg_ble.bluetooth.BleBluetooth.ZGUUID.BAND_CHARACTERISTIC_NEW_NOTIFY_ZG.equals(characteristic.getUuid())) {
                L.file(ByteUtil.bytesToString1(characteristic.getValue()), 2);
                byte[] receiverData = characteristic.getValue();
                KLog.e("no2855=数据onCharacteristicChanged: " + Util.bytesToString(receiverData));
                byte packNo = receiverData[2];
                if (packNo < 0 || ((receiverData[0] == 1 && packNo == 90) || (receiverData[2] == -127 && receiverData[4] == 2))) {
                    boolean isSendOver = true;
                    if (receiverData[0] == -116 && receiverData[1] != 0) {
                        if (receiverData[1] == 90) {
                            isSendOver = true;
                        } else if (!(receiverData[2] == -127 && receiverData[4] == -1)) {
                            isSendOver = false;
                            if (receiverData[2] == -113) {
                                BleHandler.getInstance().setSendStatusNotOver();
                            }
                        }
                    }
                    if (isSendOver) {
                        KLog.d("蓝牙数据回调 开始下一个");
                        L.file("zg蓝牙数据回调 开始下一个", 1);
                        BleHandler.getInstance().setSendStatusOver();
                    }
                }
                ZGBle.this.parseData(characteristic.getUuid(), receiverData);
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == 0) {
                if (characteristic.getValue()[2] > 0) {
                }
                ZGBle.this.mService.bleCharacteristicWrite(gatt.getDevice().getAddress(), characteristic.getUuid().toString(), status, characteristic.getValue());
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        }
    };
    /* access modifiers changed from: private */
    public boolean mIsConnecting;
    /* access modifiers changed from: private */
    public boolean mIsDisconnecting;
    /* access modifiers changed from: private */
    public Runnable mReconnectRunnable = new Runnable() {
        public void run() {
            if (ZGBle.this.mConnectTime < 3) {
                L.file("reconnect times : " + ZGBle.this.mConnectTime, 4);
                KLog.e("reconnect times : " + ZGBle.this.mConnectTime);
                ZGBle.this.mConnectTime = ZGBle.this.mConnectTime + 1;
                ZGBle.this.connect();
            }
        }
    };
    /* access modifiers changed from: private */
    public ZGService mService;
    /* access modifiers changed from: private */
    public Runnable mTimeoutRunnable = new Runnable() {
        public void run() {
            ZGBle.this.mIsConnecting = false;
            ZGBle.this.disconnect();
            L.file("没有收到系统onConnectionStateChange回调", 4);
            ZGBle.this.mService.bleNoCallback();
            ZGBle.this.reconnect();
        }
    };
    private int mWriteErrorCount;

    public static class ZGUUID {
        public static final UUID BAND_CHARACTERISTIC_NEW_NOTIFY_ZG = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
        public static final UUID BAND_CHARACTERISTIC_NEW_WRITE_ZG = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
        public static final UUID BAND_DES_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_SERVICE_MAIN_ZG = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
        public static final ParcelUuid BAND_SERVICE_MAIN_ZG_Parce = ParcelUuid.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    }

    private ZGBle(ZGService service) {
        super(service);
        instance = this;
        this.mService = service;
        this.mBluetoothGatts = new HashMap();
    }

    public static ZGBle getInstance(ZGService service) {
        if (instance == null) {
            synchronized (ZGIBle.class) {
                if (instance == null) {
                    instance = new ZGBle(service);
                }
            }
        }
        return instance;
    }

    public static ZGBle getInstance() {
        return instance;
    }

    public void setNeedReconnect(boolean needReconnect) {
        this.isNeedReconnect = needReconnect;
    }

    public boolean connect(String address) {
        clearDataState();
        this.mIsConnecting = true;
        if (!BluetoothUtils.isEnabledBluetooth(this.mService)) {
            L.file("蓝牙不可用", 4);
            KLog.e("connect 时检测到蓝牙不可用");
            this.mIsConnecting = false;
            return false;
        } else if (!this.isNeedReconnect) {
            this.mIsConnecting = false;
            return false;
        } else {
            this.mService.bleStartConnect();
            if (com.iwown.ble_module.utils.Util.getBrand().equalsIgnoreCase("oppo")) {
                stopScan();
            }
            this.mConnectTime++;
            this.mWriteErrorCount = 0;
            KLog.i("connect threadId = " + Thread.currentThread().getId() + "   name:" + Thread.currentThread().getName() + "  +++ CONNECT 设备:   address : " + address);
            L.file("connect threadId = " + Thread.currentThread().getId() + "   name:" + Thread.currentThread().getName() + "  +++ CONNECT 设备:   address : " + address, 4);
            this.mHandler.removeCallbacks(this.mTimeoutRunnable);
            this.mHandler.postDelayed(this.mTimeoutRunnable, 60000);
            BluetoothDevice device = this.mBtAdapter.getRemoteDevice(address);
            Log.i("scow", String.format("----------connect device %s----------", new Object[]{address}));
            BluetoothGatt gatt = device.connectGatt(this.mService, false, this.mGattCallback);
            if (gatt == null) {
                L.file("gatt = null", 4);
                this.mBluetoothGatts.remove(address);
                return false;
            }
            L.file("gatt != null", 4);
            this.mBluetoothGatts.put(address, gatt);
            return true;
        }
    }

    public void disconnect(String address, boolean needCloseGatt) {
        L.file("---disconnect---" + needCloseGatt, 4);
        KLog.e("-no2855--disconnect---");
        this.mIsConnecting = false;
        clearDataState();
        this.mIsConnected = false;
        this.characteristics = null;
        this.mIsDisconnecting = true;
        if (this.mBluetoothGatts.containsKey(address) && BluetoothUtils.isEnabledBluetooth(this.mService)) {
            final BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.remove(address);
            if (gatt != null) {
                gatt.disconnect();
                if (needCloseGatt) {
                    this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            L.file("gatt close", 4);
                            ZGBle.this.refreshDeviceCache(gatt);
                            gatt.close();
                            ZGBle.this.mIsDisconnecting = false;
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

    private void clearDataState() {
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
        if (!this.isNeedReconnect) {
            this.mWristBand = null;
            return;
        }
        KLog.i("=====================reconnect()");
        if (com.iwown.ble_module.utils.Util.getBrand().equalsIgnoreCase("oppo")) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    ZGBle.this.startScan(false);
                }
            });
        }
        this.mHandler.removeCallbacks(this.mReconnectRunnable);
        this.mHandler.postDelayed(this.mReconnectRunnable, BootloaderScanner.TIMEOUT);
    }

    public boolean connect() {
        L.file("mIsConnecting : " + this.mIsConnecting + "  mIsConnected : " + this.mIsConnected + " mWristBand" + this.mWristBand, 4);
        KLog.i("mIsConnecting : " + this.mIsConnecting + "  mIsConnected : " + this.mIsConnected + " mWristBand" + this.mWristBand);
        if (!isConnecting() && !isConnected()) {
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

    public boolean isConnecting() {
        return this.mIsConnecting;
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

    public void searchCharacteristicByUUid() {
        for (BluetoothGattCharacteristic characteristic : this.characteristics) {
            if (ZGUUID.BAND_CHARACTERISTIC_NEW_NOTIFY_ZG.equals(characteristic.getUuid())) {
                if (setCharacteristicNotification(characteristic, true)) {
                    this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                        }
                    }, 0);
                } else {
                    setCharacteristicNotification(characteristic, true);
                    L.file("UUID:" + characteristic.getUuid().toString() + "）set notify failure", 4);
                }
                this.mService.bleCharacteristicNotification(this.mWristBand.getDev_addr(), characteristic.getUuid(), false);
            } else if (ZGUUID.BAND_CHARACTERISTIC_NEW_WRITE_ZG.equals(characteristic.getUuid())) {
                this.mConnectTime = 0;
                L.file("connect success（UUID:" + characteristic.getUuid().toString() + "）", 4);
                this.mService.bleCharacteristicNotification(this.mWristBand.getDev_addr(), characteristic.getUuid(), true);
                this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        KLog.d("第一次连接 清楚队列 旧TaskConsumer去掉 换新的");
                        TaskHandler.getInstance().initStatus();
                        BleHandler.getInstance().addMessageFirstImmediately(new BleMessage(BleDataOrderHandler.getInstance().setConnectMode()));
                        TaskHandler.getInstance().getTaskConsumer().wakeUp();
                    }
                }, 1000);
            }
        }
    }

    public boolean setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.get(this.mWristBand.getDev_addr());
        if (gatt == null || characteristic == null) {
            return false;
        }
        if (this.mBtAdapter == null || gatt == null) {
            KLog.w("BluetoothAdapter not initialized");
            return false;
        }
        gatt.setCharacteristicNotification(characteristic, enabled);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(ZGUUID.BAND_DES_UUID);
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

    public void disconnect() {
        if (this.mWristBand != null) {
            disconnect(this.mWristBand.getDev_addr(), true);
        }
    }

    /* access modifiers changed from: private */
    public void parseData(UUID uuid, byte[] datas2) {
        if (datas2.length >= 3) {
            this.mService.bleCharacteristicChanged(this.mWristBand.getDev_addr(), uuid.toString(), datas2);
        }
    }

    public void writeDataToWristBand(byte[] data) {
        writeCharacteristicNewAPI(ZGUUID.BAND_CHARACTERISTIC_NEW_WRITE_ZG, data);
    }

    public void writeCharacteristicNewAPI(UUID uuid, byte[] data) {
        try {
            if (BluetoothUtils.isEnabledBluetooth(this.mService) && this.mWristBand != null && isConnected()) {
                BluetoothGattCharacteristic characteristic = getCharacteristic(uuid);
                BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.get(this.mWristBand.getDev_addr());
                if (gatt != null && characteristic != null) {
                    characteristic.setWriteType(1);
                    characteristic.setValue(data);
                    if (gatt.writeCharacteristic(characteristic)) {
                        KLog.i("将数据" + Util.bytesToString(data) + "写入特征（UUID:" + uuid.toString() + "）成功，等待回调响应...");
                        L.file("写入数据" + Util.bytesToString(data), 1);
                        return;
                    }
                    L.file("ThreadId = " + Thread.currentThread().getId() + "  --写入失败！UUID：" + uuid.toString() + "，数据为：" + Util.bytesToString(data), 4);
                    this.mWriteErrorCount++;
                    if (this.mWriteErrorCount >= 10) {
                        L.file("写入失败10次判断为断连", 4);
                        disconnect();
                    }
                    BleHandler.getInstance().addMessageFirstImmediately(new BleMessage(data));
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public BluetoothGattCharacteristic getWriteCharacter() {
        return getCharacteristic(ZGUUID.BAND_CHARACTERISTIC_NEW_WRITE_ZG);
    }

    public BluetoothGattCharacteristic getNotifyCharacter() {
        return getCharacteristic(ZGUUID.BAND_CHARACTERISTIC_NEW_NOTIFY_ZG);
    }
}
