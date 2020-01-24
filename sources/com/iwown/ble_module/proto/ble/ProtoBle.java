package com.iwown.ble_module.proto.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;
import com.google.common.primitives.UnsignedBytes;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.utils.ThreadUtils;
import com.iwown.ble_module.iwown.utils.ThreadUtils.TimeTask;
import com.iwown.ble_module.mtk_ble.task.BleWriteDataTask;
import com.iwown.ble_module.proto.ble.Constants.ProtoBufUUID;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.proto.task.ITask;
import com.iwown.ble_module.utils.BluetoothUtils;
import com.iwown.ble_module.utils.Util;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

public class ProtoBle extends AbsBle {
    private static final String TAG = "ProtoBle";
    private static ProtoBle protoBle;
    private boolean isNeedReconnect;
    private Runnable mConnectRunnable = new Runnable() {
        public void run() {
            try {
                KLog.i(" connect(mWristBand.getAddress());");
                ProtoBle.this.connect(ProtoBle.this.mWristBand.getDev_addr());
            } catch (Exception e) {
                L.file("connect error : " + e.toString(), 4);
                ProtoBle.this.mIsConnecting = false;
                ProtoBle.this.mConnectTime = 0;
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
            ProtoBle.this.disconnect();
        }
    };
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        boolean isDataOver = false;
        int length = 0;
        byte[] newByte = new byte[0];

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            String address = gatt.getDevice().getAddress();
            ThreadUtils.cancel(ProtoBle.this.mDiscoverServiceTimeoutRunnable);
            L.file("address " + address + " status " + status, 4);
            if (status != 0) {
                ProtoBle.this.disconnect(address);
                return;
            }
            ProtoBle.this.mIsConnecting = false;
            if (ProtoBle.this.characteristics == null || ProtoBle.this.characteristics.size() <= 0) {
                ProtoBle.this.characteristics = new ArrayList();
            } else {
                ProtoBle.this.characteristics.clear();
            }
            List<BluetoothGattService> services = gatt.getServices();
            Iterator it = services.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                BluetoothGattService bluetoothGattService = (BluetoothGattService) it.next();
                if (bluetoothGattService.getUuid().equals(ProtoBufUUID.BAND_PROTO_DFU_SERVICE_UUID)) {
                    ProtoBle.this.characteristics.addAll(bluetoothGattService.getCharacteristics());
                    break;
                }
            }
            for (BluetoothGattService bluetoothGattService2 : services) {
                Log.e("yanxi", "onServicesDiscovered----" + bluetoothGattService2.getUuid());
                if (bluetoothGattService2.getUuid().equals(ProtoBufUUID.BAND_SERVICE_MAIN_UUID)) {
                    KLog.i("====sdk-type-4=pb===" + bluetoothGattService2.getUuid());
                    L.file("====sdk-type-4=pb===" + bluetoothGattService2.getUuid(), 4);
                    ProtoBle.this.mService.bleServiceDiscovered(address, bluetoothGattService2.getUuid());
                    ProtoBle.this.characteristics.addAll(bluetoothGattService2.getCharacteristics());
                    ProtoBle.this.searchCharacteristicByUUid();
                    return;
                }
            }
            ProtoBle.this.disconnect();
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (ProtoBufUUID.BAND_CHARACT_NOTIFY_UUID.equals(characteristic.getUuid())) {
                Log.e("yanxi", "onCharacteristicChanged");
                byte[] receiverData = characteristic.getValue();
                KLog.i("onCharacteristicChanged: " + Util.bytesToString(receiverData));
                if (receiverData[0] == 68 && receiverData[1] == 84) {
                    this.length = ((receiverData[3] & UnsignedBytes.MAX_VALUE) << 8) | (receiverData[2] & UnsignedBytes.MAX_VALUE);
                    KLog.i("yanxi111", "length:" + this.length + "");
                    if (receiverData.length - 8 >= this.length) {
                        this.isDataOver = true;
                        this.newByte = Arrays.copyOfRange(receiverData, 0, receiverData.length);
                        KLog.d("yanxi111", "不用拆分---" + Util.bytesToString(this.newByte, false));
                        int a = Util.crc16Modem(Arrays.copyOfRange(receiverData, 8, receiverData.length));
                        byte high = (byte) ((65280 & a) >> 8);
                        byte low = (byte) (a & 255);
                        KLog.d("yanxi", String.format("%02d", new Object[]{Byte.valueOf(high)}) + "---" + String.format("%02d", new Object[]{Byte.valueOf(low)}));
                        if (high == this.newByte[5] && low == this.newByte[4]) {
                            KLog.i("yanxi", "校验成功");
                            ProtoBle.this.parseData(characteristic.getUuid(), this.newByte);
                            ProtoBle.this.writeLog(this.newByte, 2);
                        }
                        this.newByte = new byte[0];
                        return;
                    }
                    this.isDataOver = false;
                    this.newByte = Arrays.copyOfRange(receiverData, 0, receiverData.length);
                } else if (!this.isDataOver) {
                    KLog.i("newbyte", this.newByte.length + "");
                    this.newByte = Util.concat(this.newByte, receiverData);
                    if (this.newByte.length - 8 == this.length) {
                        KLog.d("yanxi111", "拆分---" + Util.bytesToString(this.newByte, false));
                        int a2 = Util.crc16Modem(Arrays.copyOfRange(this.newByte, 8, this.newByte.length));
                        byte high2 = (byte) ((65280 & a2) >> 8);
                        byte low2 = (byte) (a2 & 255);
                        KLog.d("yanxi", String.format("%02X", new Object[]{Byte.valueOf(high2)}) + "---" + String.format("%02X", new Object[]{Byte.valueOf(low2)}));
                        if (high2 == this.newByte[5] && low2 == this.newByte[4]) {
                            KLog.i("yanxi", "校验成功");
                            ProtoBle.this.parseData(characteristic.getUuid(), this.newByte);
                            ProtoBle.this.writeLog(this.newByte, 2);
                        }
                        this.newByte = new byte[0];
                    }
                } else {
                    this.newByte = new byte[0];
                }
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            KLog.d("onCharacteristicWrite " + gatt.getDevice().getAddress() + " status " + status + " datas " + Util.bytesToString(characteristic.getValue()));
            BackgroundThreadManager.getInstance().removeTask();
            if (status == 0) {
                ProtoBle.this.mService.bleCharacteristicWrite(gatt.getDevice().getAddress(), characteristic.getUuid().toString(), status, characteristic.getValue());
            }
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            KLog.d("onCharacteristicRead: " + gatt.getDevice().getAddress() + " status " + status);
            if (status == 0) {
                ProtoBle.this.mService.bleCharacteristicRead(gatt.getDevice().getAddress(), characteristic.getUuid().toString(), status, characteristic.getValue(), characteristic);
            }
        }

        public void onConnectionStateChange(final BluetoothGatt gatt, int status, int newState) {
            String address = gatt.getDevice().getAddress();
            ProtoBle.this.mHandler.removeCallbacks(ProtoBle.this.mTimeoutRunnable);
            ThreadUtils.cancel(ProtoBle.this.mDiscoverServiceTimeoutRunnable);
            L.file("address " + address + " status " + status + " newState " + newState, 4);
            KLog.e("========= i7b onConnectionStateChange============", "address " + address + " status " + status + " newState " + newState);
            if (status != 0) {
                ProtoBle.this.notifyMyAll();
                ProtoBle.this.mIsConnected = false;
                ProtoBle.this.disconnect(address, false);
                ProtoBle.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        ProtoBle.this.refreshDeviceCache(gatt);
                        gatt.close();
                        ProtoBle.this.mIsDisconnecting = false;
                    }
                }, 1000);
                ProtoBle.this.mService.bleGattDisConnected(address);
                if (status == 257) {
                    ProtoBle.this.mService.bleConnectError257(address);
                } else {
                    ProtoBle.this.reconnect();
                }
            } else if (newState == 2) {
                ProtoBle.this.mIsConnected = true;
                ProtoBle.this.mHandler.removeCallbacks(ProtoBle.this.mReconnectRunnable);
                ProtoBle.this.mService.bleGattConnected(gatt.getDevice());
                ProtoBle.this.waitFor(2000);
                boolean discoverServices = gatt.discoverServices();
                L.file("discoverServices : " + discoverServices, 4);
                if (discoverServices) {
                    ThreadUtils.postDelay(ProtoBle.this.mDiscoverServiceTimeoutRunnable, 60000);
                } else {
                    ProtoBle.this.disconnect();
                }
            } else if (newState == 0) {
                ProtoBle.this.mIsConnected = false;
                ProtoBle.this.notifyMyAll();
                ProtoBle.this.mService.bleGattDisConnected(address);
                ProtoBle.this.disconnect(address, false);
                ProtoBle.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        ProtoBle.this.refreshDeviceCache(gatt);
                        gatt.close();
                        ProtoBle.this.mIsDisconnecting = false;
                    }
                }, 1000);
                ProtoBle.this.reconnect();
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean mIsDisconnecting;
    /* access modifiers changed from: private */
    public Runnable mReconnectRunnable = new Runnable() {
        public void run() {
            if (ProtoBle.this.mConnectTime < 3) {
                L.file("reconnect times : " + ProtoBle.this.mConnectTime, 4);
                ProtoBle.this.mConnectTime = ProtoBle.this.mConnectTime + 1;
                ProtoBle.this.connect();
                return;
            }
            ProtoBle.this.mConnectTime = 0;
        }
    };
    /* access modifiers changed from: private */
    public BleService mService;
    /* access modifiers changed from: private */
    public Runnable mTimeoutRunnable = new Runnable() {
        public void run() {
            ProtoBle.this.mIsConnecting = false;
            ProtoBle.this.disconnect();
            L.file("没有收到系统onConnectionStateChange回调", 4);
            ProtoBle.this.mService.bleNoCallback();
            ProtoBle.this.reconnect();
        }
    };
    private int mWriteErrorCount;

    private ProtoBle(BleService service) {
        super(service);
        protoBle = this;
        this.mService = service;
        this.mBluetoothGatts = new HashMap();
    }

    public static ProtoBle getInstance() {
        return protoBle;
    }

    public static ProtoBle getInstance(BleService service) {
        if (protoBle == null) {
            synchronized (ProtoBle.class) {
                if (protoBle == null) {
                    protoBle = new ProtoBle(service);
                }
            }
        }
        return protoBle;
    }

    public void setNeedReconnect(boolean needReconnect) {
        this.isNeedReconnect = needReconnect;
    }

    public boolean connect(String address) {
        BackgroundThreadManager.getInstance().setWriteUnbind(false);
        this.mIsConnecting = true;
        if (!BluetoothUtils.isEnabledBluetooth(this.mService)) {
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
        BackgroundThreadManager.getInstance().needWait();
        KLog.i("connect threadId = " + Thread.currentThread().getId() + "   name:" + Thread.currentThread().getName() + "  +++ CONNECT 设备:   address : " + address);
        L.file("connect threadId = " + Thread.currentThread().getId() + "   name:" + Thread.currentThread().getName() + "  +++ CONNECT 设备:   address : " + address, 4);
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
        this.mHandler.postDelayed(this.mTimeoutRunnable, 60000);
        BluetoothGatt gatt = connectGatt(this.mBtAdapter.getRemoteDevice(address));
        if (gatt == null) {
            L.file("gatt = null", 4);
            this.mBluetoothGatts.remove(address);
            return false;
        }
        L.file("gatt != null", 4);
        this.mBluetoothGatts.put(address, gatt);
        L.file("licl" + this.mBluetoothGatts.get(address), 4);
        return true;
    }

    public boolean connect() {
        L.file("mIsConnecting : " + this.mIsConnecting + "  mIsConnected : " + this.mIsConnected + " mWristBand" + this.mWristBand, 4);
        L.file(toString() + " thread: " + Thread.currentThread().getId(), 4);
        KLog.d("mIsConnecting : " + this.mIsConnecting + "  mIsConnected : " + this.mIsConnected + " mWristBand" + this.mWristBand);
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

    public void disconnect() {
        if (this.mWristBand != null) {
            disconnect(this.mWristBand.getDev_addr(), true);
        }
    }

    /* access modifiers changed from: private */
    public void reconnect() {
        if (BackgroundThreadManager.getInstance().isWriteUnbind() || !this.isNeedReconnect) {
            this.mWristBand = null;
            return;
        }
        KLog.i("=====================reconnect()");
        this.mHandler.post(new Runnable() {
            public void run() {
                ProtoBle.this.startScan(false);
            }
        });
        this.mHandler.removeCallbacks(this.mReconnectRunnable);
        this.mHandler.postDelayed(this.mReconnectRunnable, BootloaderScanner.TIMEOUT);
    }

    private BluetoothGatt connectGatt(BluetoothDevice device) {
        if (VERSION.SDK_INT < 21) {
            return device.connectGatt(this.mService, false, this.mGattCallback);
        }
        if (VERSION.SDK_INT >= 21 && VERSION.SDK_INT < 23) {
            try {
                return (BluetoothGatt) device.getClass().getMethod("connectGatt", new Class[]{Context.class, Boolean.TYPE, BluetoothGattCallback.class, Integer.TYPE}).invoke(device, new Object[]{this.mService, Boolean.valueOf(false), this.mGattCallback, Integer.valueOf(2)});
            } catch (NoSuchMethodException e) {
                ThrowableExtension.printStackTrace(e);
                return device.connectGatt(this.mService, false, this.mGattCallback);
            } catch (IllegalAccessException e2) {
                ThrowableExtension.printStackTrace(e2);
                return device.connectGatt(this.mService, false, this.mGattCallback);
            } catch (InvocationTargetException e3) {
                ThrowableExtension.printStackTrace(e3);
                return device.connectGatt(this.mService, false, this.mGattCallback);
            } catch (Exception e4) {
                return device.connectGatt(this.mService, false, this.mGattCallback);
            }
        } else if (VERSION.SDK_INT >= 23) {
            return device.connectGatt(this.mService, false, this.mGattCallback, 2);
        } else {
            return device.connectGatt(this.mService, false, this.mGattCallback);
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

    public void disconnect(String address, boolean needCloseGatt) {
        L.file("---disconnect---" + needCloseGatt, 4);
        KLog.d("---disconnect---" + needCloseGatt);
        this.mIsConnecting = false;
        this.mIsConnected = false;
        this.characteristics = null;
        this.mIsDisconnecting = true;
        KLog.d("mBluetoothGatts.containsKey? " + this.mBluetoothGatts.containsKey(address));
        KLog.d("licl: " + this.mBluetoothGatts.get(address));
        if (this.mBluetoothGatts.containsKey(address) && BluetoothUtils.isEnabledBluetooth(this.mService)) {
            BackgroundThreadManager.getInstance().needWait();
            final BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.remove(address);
            KLog.e(gatt);
            if (gatt != null) {
                gatt.disconnect();
                KLog.d("gatt disconnect");
                if (needCloseGatt) {
                    this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            L.file("gatt close", 4);
                            ProtoBle.this.refreshDeviceCache(gatt);
                            gatt.close();
                            ProtoBle.this.mIsDisconnecting = false;
                        }
                    }, 1000);
                }
            }
        }
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

    /* access modifiers changed from: private */
    public void notifyMyAll() {
        synchronized (mLock) {
            mLock.notifyAll();
        }
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

    public synchronized void disconnect(String address) {
        if (this.mBluetoothGatts.containsKey(address)) {
            BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.remove(address);
            if (gatt != null) {
                gatt.disconnect();
                gatt.close();
                Log.e(TAG, "执行到了设备断开的指令");
            }
        }
    }

    public void searchCharacteristicByUUid() {
        for (BluetoothGattCharacteristic characteristic : this.characteristics) {
            Log.e("yanxi", "searchCharacteristicByUUid---" + characteristic.getUuid().toString());
            if (ProtoBufUUID.BAND_CHARACT_NOTIFY_UUID.equals(characteristic.getUuid())) {
                if (setCharacteristicNotification(characteristic, true)) {
                    KLog.i("yanxi", "success");
                } else {
                    setCharacteristicNotification(characteristic, true);
                    KLog.i("yanxi", "failed");
                }
                this.mService.bleCharacteristicNotification(this.mWristBand.getDev_addr(), characteristic.getUuid(), false);
            } else if (ProtoBufUUID.BAND_CHARACT_WRITE_UUID.equals(characteristic.getUuid())) {
                KLog.i("yanxi", "connect success（UUID:" + characteristic.getUuid().toString() + "）");
                this.mConnectTime = 0;
                L.file("connect success（UUID:" + characteristic.getUuid().toString() + "）", 4);
                this.mService.bleCharacteristicNotification(this.mWristBand.getDev_addr(), characteristic.getUuid(), true);
                this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        BackgroundThreadManager.getInstance().wakeUp();
                    }
                }, 1000);
            }
        }
    }

    /* access modifiers changed from: private */
    public void parseData(UUID uuid, byte[] datas) {
        if (datas.length >= 8) {
            this.mService.bleCharacteristicChanged(this.mWristBand.getDev_addr(), uuid.toString(), datas);
        }
    }

    public void writeDataToWristBand(byte[] data) {
        writeCharacteristicNewAPI(ProtoBufUUID.BAND_CHARACT_WRITE_UUID, data);
    }

    public void writeCharacteristicNewAPI(UUID uuid, byte[] data) {
        boolean z = true;
        try {
            if (!BluetoothUtils.isEnabledBluetooth(this.mService) || this.mWristBand == null) {
                if (("---------isEnabledBluetooth------------------" + this.mWristBand) != null) {
                    z = false;
                }
                KLog.i(Boolean.valueOf(z));
                return;
            }
            BluetoothGattCharacteristic characteristic = getCharacteristic(uuid);
            BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.get(this.mWristBand.getDev_addr());
            if (gatt != null && characteristic != null) {
                characteristic.setWriteType(2);
                characteristic.setValue(data);
                if (gatt.writeCharacteristic(characteristic)) {
                    KLog.i("将数据" + Util.bytesToString(data) + "写入特征（UUID:" + uuid.toString() + "）成功，等待回调响应...");
                    if (data.length > 1) {
                        writeLog(data, 1);
                        return;
                    }
                    return;
                }
                this.mWriteErrorCount++;
                KLog.i("ThreadId = " + Thread.currentThread().getId() + "  --写入失败！UUID：" + uuid.toString() + "，数据为：" + Util.bytesToString(data));
                L.file("ThreadId = " + Thread.currentThread().getId() + "  --写入失败！UUID：" + uuid.toString() + "，数据为：" + Util.bytesToString(data), 4);
                if (this.mWriteErrorCount >= 10) {
                    L.file("写入失败10次判断为断连", 4);
                    disconnect();
                }
                ITask nowTask = BackgroundThreadManager.getInstance().getNowTask();
                if (nowTask instanceof BleWriteDataTask) {
                    BleWriteDataTask task = (BleWriteDataTask) nowTask;
                    if (task.getRetryCount() < 5) {
                        task.setNeedRetry(true);
                    } else {
                        task.setNeedRetry(false);
                    }
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
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
        } else if (!gatt.setCharacteristicNotification(characteristic, enabled)) {
            KLog.e("Enabling notification failed!");
            return false;
        } else {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(ProtoBufUUID.BAND_DES_UUID);
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

    /* access modifiers changed from: private */
    public void writeLog(byte[] data, int downOrUp) {
        ProtoUtils.writeLog(data, downOrUp);
    }
}
