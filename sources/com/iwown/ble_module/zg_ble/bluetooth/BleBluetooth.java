package com.iwown.ble_module.zg_ble.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.callback.BleGattCallback;
import com.iwown.ble_module.zg_ble.data.BleConnectState;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.exception.ConnectException;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.ble_module.zg_ble.task.TaskHandler;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BleBluetooth {
    protected static final long CONNECT_DELAY_MILLIS = 10000;
    protected static final int GATT_CLOSE_DELAY_MILLIS = 1000;
    private static BleBluetooth instance;
    /* access modifiers changed from: private */
    public static boolean isSetNotifyDescriptor;
    /* access modifiers changed from: private */
    public BleDevice bleDevice;
    /* access modifiers changed from: private */
    public BleGattCallback bleGattCallback;
    /* access modifiers changed from: private */
    public BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    private Runnable connectRunnable = new Runnable() {
        public void run() {
            try {
                BluetoothDevice remoteDevice = BleBluetooth.this.bluetoothAdapter.getRemoteDevice(BleBluetooth.this.bleDevice.getAddress());
                if (remoteDevice == null) {
                    KLog.e("remoteDevice is null");
                    L.file("remoteDevice is null", 4);
                    return;
                }
                BleBluetooth.this.addConnectGattCallback(BleBluetooth.this.bleGattCallback);
                BluetoothDevice bluetoothDevice = remoteDevice;
                if (0 != 0) {
                    if (BleBluetooth.this.bleGattCallback != null) {
                        BleBluetooth.this.bleGattCallback.onStartConnect();
                    }
                    BleBluetooth.this.mainHandler.removeCallbacks(BleBluetooth.this.connectTimeout);
                    BleBluetooth.this.mainHandler.postDelayed(BleBluetooth.this.connectTimeout, BleBluetooth.CONNECT_DELAY_MILLIS);
                }
                if (0 == 0) {
                    L.file("gatt = null", 4);
                    BleBluetooth.this.mBluetoothGatts.remove(BleBluetooth.this.bleDevice.getAddress());
                    return;
                }
                L.file("gatt != null", 4);
                BleBluetooth.this.mBluetoothGatts.put(BleBluetooth.this.bleDevice.getAddress(), null);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    };
    /* access modifiers changed from: private */
    public BleConnectState connectState = BleConnectState.CONNECT_IDLE;
    /* access modifiers changed from: private */
    public Runnable connectTimeout = new Runnable() {
        public void run() {
            KLog.e("===============Timeout=============");
            BleBluetooth.this.connectState = BleConnectState.CONNECT_FAILURE;
            BleBluetooth.this.reconnect();
        }
    };
    private BluetoothGattCallback coreGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt1, int status, int newState) {
            boolean z = true;
            super.onConnectionStateChange(gatt1, status, newState);
            String address = gatt1.getDevice().getAddress();
            String name = gatt1.getDevice().getName();
            BleBluetooth.this.mainHandler.removeCallbacks(BleBluetooth.this.connectTimeout);
            KLog.e("BluetoothGattCallback：onConnectionStateChange  status: " + status + "\n newState: " + newState + "\n address: " + address + "\n  name: " + name);
            L.file("BluetoothGattCallback：onConnectionStateChange  status: " + status + "\n newState: " + newState + "\n address: " + address + "\n  name: " + name, 4);
            if (status != 0) {
                BleBluetooth.this.connectState = BleConnectState.CONNECT_FAILURE;
                if (status == 133) {
                    KLog.d("发生设备初始连接133情况，需要重新扫描连接设备");
                    BleBluetooth.this.connectState = BleConnectState.CONNECT_FAILURE;
                    BleBluetooth.this.disconnect();
                    BleBluetooth.this.refreshDeviceCache(gatt1);
                    BleBluetooth.this.closeBluetoothGatt(gatt1);
                    if (BleBluetooth.this.mConnectTime <= 3) {
                        BleBluetooth.this.reconnect500();
                        return;
                    }
                    return;
                }
                BleBluetooth.this.disconnect();
                if (BleBluetooth.this.bleGattCallback != null) {
                    BleBluetooth.this.bleGattCallback.onConnectFail(new ConnectException(gatt1, status));
                }
                BleBluetooth.this.refreshDeviceCache(gatt1);
                BleBluetooth.this.closeBluetoothGatt(gatt1);
                if (status == 257) {
                    if (BleBluetooth.this.bleGattCallback != null) {
                        BleBluetooth.this.bleGattCallback.onConnectFail(new ConnectException(gatt1, status));
                    }
                } else if (BleBluetooth.this.mConnectTime <= 3) {
                    BleBluetooth.this.reconnect();
                }
            } else if (newState == 2) {
                BleBluetooth.this.mainHandler.removeCallbacks(BleBluetooth.this.discoverRunnable);
                BleBluetooth.this.mainHandler.postDelayed(BleBluetooth.this.discoverRunnable, 2000);
                BleBluetooth.this.mConnectTime = 0;
            } else if (newState == 0) {
                BleBluetooth.this.closeBluetoothGatt(gatt1);
                KLog.e("connectState " + BleBluetooth.this.connectState);
                StringBuilder append = new StringBuilder().append("ing ").append(BleBluetooth.this.connectState == BleConnectState.CONNECT_CONNECTING).append("  CONNECT_CONNECTED");
                if (BleBluetooth.this.connectState != BleConnectState.CONNECT_CONNECTED) {
                    z = false;
                }
                KLog.e(append.append(z).toString());
                if (BleBluetooth.this.connectState == BleConnectState.CONNECT_CONNECTING) {
                    BleBluetooth.this.connectState = BleConnectState.CONNECT_FAILURE;
                    if (BleBluetooth.this.bleGattCallback != null) {
                        BleBluetooth.this.bleGattCallback.onConnectFail(new ConnectException(gatt1, status));
                    }
                } else if (BleBluetooth.this.connectState == BleConnectState.CONNECT_CONNECTED) {
                    BleBluetooth.this.connectState = BleConnectState.CONNECT_DISCONNECT;
                    if (BleBluetooth.this.bleGattCallback != null) {
                        BleBluetooth.this.bleGattCallback.onDisConnected(BleBluetooth.this.isActiveDisconnect, BleBluetooth.this.bleDevice, gatt1, status);
                    }
                    BleBluetooth.this.removeConnectGattCallback();
                    BleBluetooth.this.mainHandler.removeCallbacksAndMessages(this);
                } else {
                    BleBluetooth.this.connectState = BleConnectState.CONNECT_FAILURE;
                }
                if (BleBluetooth.this.mConnectTime <= 3) {
                    BleBluetooth.this.reconnect();
                }
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt1, int status) {
            super.onServicesDiscovered(gatt1, status);
            KLog.e("BluetoothGattCallback：onServicesDiscovered  status: " + status + " currentThread: " + Thread.currentThread().getId());
            L.file("BluetoothGattCallback：onServicesDiscovered  status: " + status, 4);
            BluetoothGatt gatt = (BluetoothGatt) BleBluetooth.this.mBluetoothGatts.get(BleBluetooth.this.bleDevice.getAddress());
            if (status == 0) {
                BleBluetooth.this.connectState = BleConnectState.CONNECT_CONNECTED;
                BleBluetooth.this.isActiveDisconnect = false;
                if (BleBluetooth.this.bleGattCallback != null) {
                    BleBluetooth.this.bleGattCallback.onConnectSuccess(BleBluetooth.this.getDevice(), gatt, status);
                    BleBluetooth.this.setNotifyCharacteristic(true);
                    return;
                }
                return;
            }
            BleBluetooth.this.closeBluetoothGatt(gatt);
            BleBluetooth.this.connectState = BleConnectState.CONNECT_FAILURE;
            if (BleBluetooth.this.bleGattCallback != null) {
                BleBluetooth.this.bleGattCallback.onConnectFail(new ConnectException(gatt, status));
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            if (BleBluetooth.this.getConnectState() == BleConnectState.CONNECT_CONNECTED && ZGUUID.BAND_CHARACTERISTIC_NEW_NOTIFY_ZG.equals(characteristic.getUuid())) {
                KLog.d("onCharacteristicChanged " + ByteUtil.bytesToString1(characteristic.getValue()));
                L.file(ByteUtil.bytesToString1(characteristic.getValue()), 2);
                byte[] receiverData = characteristic.getValue();
                byte packNo = receiverData[2];
                if (packNo >= 0 && ((receiverData[0] != 1 || packNo != 90) && (receiverData[2] != -127 || receiverData[4] != 2))) {
                    return;
                }
                if (receiverData[0] != -116 || (receiverData[1] != 0 && receiverData[2] == -127 && receiverData[4] == -1)) {
                    KLog.d("蓝牙数据回调 开始下一个");
                    L.file("蓝牙数据回调 开始下一个", 1);
                    BleHandler.getInstance().setSendStatusOver();
                }
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            KLog.d("onDescriptorWrite " + status + "   " + BleBluetooth.isSetNotifyDescriptor);
            L.file("onDescriptorWrite " + status + "   " + BleBluetooth.isSetNotifyDescriptor, 4);
            if (status != 0) {
                BleBluetooth.this.disconnect();
                if (BleBluetooth.this.mConnectTime <= 3) {
                    BleBluetooth.this.reconnect500();
                }
            } else if (BleBluetooth.isSetNotifyDescriptor) {
                KLog.d("第一次连接 清楚队列 旧TaskConsumer去掉 换新的");
                TaskHandler.getInstance().initStatus();
                TaskHandler.getInstance().getTaskConsumer().wakeUp();
                BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().setConnectMode()));
                BleBluetooth.isSetNotifyDescriptor = false;
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            KLog.d("onCharacteristicRead");
        }
    };
    /* access modifiers changed from: private */
    public Runnable discoverRunnable = new Runnable() {
        public void run() {
            try {
                BleBluetooth.this.discoverServices(BleBluetooth.this.bleDevice.getAddress());
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean isActiveDisconnect = false;
    private boolean isNeedReconnect = true;
    protected Map<String, BluetoothGatt> mBluetoothGatts = new HashMap();
    /* access modifiers changed from: private */
    public int mConnectTime;
    /* access modifiers changed from: private */
    public boolean mIsDisconnecting;
    private Runnable mReconnectRunnable = new Runnable() {
        public void run() {
            if (BleBluetooth.this.mConnectTime <= 3) {
                KLog.e("=======reconnect times : " + BleBluetooth.this.mConnectTime);
                BleBluetooth.this.mConnectTime = BleBluetooth.this.mConnectTime + 1;
            }
        }
    };
    private Runnable mWriteFialRunnable = new Runnable() {
        public void run() {
            if (BleBluetooth.this.mWriteFialTime <= 3) {
                KLog.d("mWriteFialTime times : " + BleBluetooth.this.mWriteFialTime);
                BleBluetooth.this.mWriteFialTime = BleBluetooth.this.mWriteFialTime + 1;
                BleHandler.getInstance().addMessageFirstImmediately(new BleMessage(BleBluetooth.this.wirteFailData));
                return;
            }
            BleBluetooth.this.mWriteFialTime = 0;
            BleHandler.getInstance().setSendStatusOver();
        }
    };
    /* access modifiers changed from: private */
    public int mWriteFialTime;
    protected Handler mainHandler = new Handler(Looper.myLooper());
    /* access modifiers changed from: private */
    public byte[] wirteFailData;

    public static class ZGUUID {
        public static final UUID BAND_CHARACTERISTIC_NEW_NOTIFY_ZG = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
        public static final UUID BAND_CHARACTERISTIC_NEW_WRITE_ZG = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
        public static final UUID BAND_DES_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_SERVICE_MAIN_ZG = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
        public static final ParcelUuid BAND_SERVICE_MAIN_ZG_Parce = ParcelUuid.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    }

    public static BleBluetooth getInstance() {
        if (instance == null) {
            instance = new BleBluetooth();
        }
        return instance;
    }

    public BleDevice getBleDevice() {
        return this.bleDevice;
    }

    public void setBleDevice(BleDevice bleDevice2) {
        this.bleDevice = bleDevice2;
    }

    private BleBluetooth(BleDevice bleDevice2) {
        this.bleDevice = bleDevice2;
    }

    private BleBluetooth() {
    }

    public synchronized void addConnectGattCallback(BleGattCallback callback) {
        this.bleGattCallback = callback;
    }

    public synchronized void removeConnectGattCallback() {
        this.bleGattCallback = null;
    }

    public void setNeedReconnect(boolean needReconnect) {
        this.isNeedReconnect = needReconnect;
    }

    public boolean isNeedReconnect() {
        return this.isNeedReconnect;
    }

    public BleConnectState getConnectState() {
        return this.connectState;
    }

    public BleDevice getDevice() {
        return this.bleDevice;
    }

    /* access modifiers changed from: private */
    public boolean refreshDeviceCache(BluetoothGatt gatt) {
        try {
            Method refresh = BluetoothGatt.class.getMethod("refresh", new Class[0]);
            if (refresh != null) {
                boolean success = ((Boolean) refresh.invoke(gatt, new Object[0])).booleanValue();
                KLog.e("refreshDeviceCache, is success:  " + success);
                return success;
            }
        } catch (Exception e) {
            KLog.e("exception occur while refreshing device: " + e.getMessage());
        }
        return false;
    }

    public void disconnect() {
        L.file("disconnect", 4);
        this.mIsDisconnecting = true;
        if (this.mBluetoothGatts.containsKey(this.bleDevice.getAddress())) {
            KLog.e("disconnect:" + this.bleDevice.getAddress());
            disconnectGatt(this.bleDevice.getAddress());
            return;
        }
        KLog.e("gatt is not in mBluetoothGatts");
        for (String key : this.mBluetoothGatts.keySet()) {
            disconnectGatt(key);
        }
    }

    public void disconnectGatt(String address) {
        final BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.remove(address);
        if (gatt != null) {
            KLog.e("disconnect gatt !=null ");
            this.isActiveDisconnect = true;
            gatt.disconnect();
            this.mainHandler.postDelayed(new Runnable() {
                public void run() {
                    try {
                        BleBluetooth.this.refreshDeviceCache(gatt);
                        gatt.close();
                        BleBluetooth.this.mIsDisconnecting = false;
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }, 1000);
        }
    }

    /* access modifiers changed from: private */
    public void closeBluetoothGatt(BluetoothGatt gatt) {
        if (gatt != null) {
            gatt.close();
        }
    }

    public void destroy() {
        L.file("destroy", 4);
        KLog.e("destroy " + this.connectState);
        setNeedReconnect(false);
        disconnect();
        this.bleDevice = null;
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
        disconnect();
        return ret;
    }

    public void setNotifyCharacteristic(boolean enabled) {
        BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.get(this.bleDevice.getAddress());
        if (gatt != null) {
            BluetoothGattService service = gatt.getService(ZGUUID.BAND_SERVICE_MAIN_ZG);
            KLog.e("service " + service);
            if (service != null) {
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(ZGUUID.BAND_CHARACTERISTIC_NEW_NOTIFY_ZG);
                if (characteristic != null) {
                    boolean success = gatt.setCharacteristicNotification(characteristic, enabled);
                    KLog.e("setNotifyCharacteristic " + success);
                    if (success) {
                        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(ZGUUID.BAND_DES_UUID);
                        if (descriptor == null) {
                            KLog.e("descriptor is null");
                            return;
                        }
                        int properties = characteristic.getProperties();
                        KLog.d("properties = " + properties);
                        if ((properties & 32) != 0) {
                            descriptor.setValue(enabled ? BluetoothGattDescriptor.ENABLE_INDICATION_VALUE : new byte[]{0, 0});
                        } else if ((properties & 16) != 0) {
                            descriptor.setValue(enabled ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : new byte[]{0, 0});
                        } else {
                            descriptor.setValue(enabled ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : new byte[]{0, 0});
                        }
                        gatt.writeDescriptor(descriptor);
                        isSetNotifyDescriptor = true;
                    }
                }
            }
        }
    }

    public void writeCharacteristic(byte[] data) {
        if (this.connectState != BleConnectState.CONNECT_CONNECTED) {
            KLog.e("bracelet disconnect");
        } else if (data == null || data.length <= 0) {
            KLog.e("the data to be written is empty");
        } else {
            BluetoothGatt gatt = (BluetoothGatt) this.mBluetoothGatts.get(this.bleDevice.getAddress());
            if (gatt == null) {
                KLog.e(" bluetoothGatt is null");
                BleHandler.getInstance().setSendStatusOver();
                return;
            }
            BluetoothGattService service = gatt.getService(ZGUUID.BAND_SERVICE_MAIN_ZG);
            if (service == null) {
                KLog.e(" BluetoothGattService is null");
                return;
            }
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(ZGUUID.BAND_CHARACTERISTIC_NEW_WRITE_ZG);
            if (characteristic == null || (characteristic.getProperties() & 12) == 0) {
                KLog.e("this characteristic not support write!");
            } else if (!characteristic.setValue(data)) {
                KLog.e("Updates the locally stored value of this characteristic fail");
            } else if (!gatt.writeCharacteristic(characteristic)) {
                KLog.e("gatt writeCharacteristic fail");
                this.wirteFailData = data;
                this.mainHandler.removeCallbacks(this.mWriteFialRunnable);
                this.mainHandler.postDelayed(this.mWriteFialRunnable, 1000);
            }
        }
    }

    /* access modifiers changed from: private */
    public void reconnect500() {
        if (this.isNeedReconnect) {
            L.file("reconnect500 ", 4);
            this.mainHandler.removeCallbacks(this.mReconnectRunnable);
            this.mainHandler.postDelayed(this.mReconnectRunnable, 3000);
        }
    }

    /* access modifiers changed from: private */
    public void reconnect() {
        if (this.isNeedReconnect && this.connectState != BleConnectState.CONNECT_CONNECTING) {
            L.file("=====================reconnect() ", 4);
            this.mainHandler.removeCallbacks(this.mReconnectRunnable);
            this.mainHandler.postDelayed(this.mReconnectRunnable, CONNECT_DELAY_MILLIS);
        }
    }

    public static boolean isEnabledBluetooth(Context context) {
        BluetoothAdapter adapter = ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter();
        if (!context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            return false;
        }
        if (adapter == null) {
            return false;
        }
        return adapter.isEnabled();
    }
}
