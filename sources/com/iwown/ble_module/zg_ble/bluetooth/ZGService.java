package com.iwown.ble_module.zg_ble.bluetooth;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import com.iwown.ble_module.iwown.utils.Constants.R1UUID;
import com.socks.library.KLog;
import java.util.UUID;

public class ZGService extends Service {
    public static final String BLE_CHARACTERISTIC_CHANGED = "com.zeroner.sdk.zg.ble.characteristic_changed";
    public static final String BLE_CHARACTERISTIC_DISCOVERED = "com.zeroner.sdk.zg.ble.characteristic_notification";
    public static final String BLE_CHARACTERISTIC_INDICATION = "com.zeroner.sdk.zg.ble.characteristic_indication";
    public static final String BLE_CHARACTERISTIC_READ = "com.zeroner.sdk.zg.ble.characteristic_read";
    public static final String BLE_CHARACTERISTIC_WRITE = "com.zeroner.sdk.zg.ble.characteristic_write";
    public static final String BLE_CONNECT_ERROR_133 = "com.zeroner.sdk.zg.ble.BLE_CONNECT_ERROR_133";
    public static final String BLE_CONNECT_ERROR_257 = "com.zeroner.sdk.zg.ble.BLE_CONNECT_ERROR_257";
    public static final String BLE_DEVICE_FOUND = "com.zeroner.sdk.zg.ble.device_found";
    public static final String BLE_GATT_CONNECTED = "com.zeroner.sdk.zg.ble.gatt_connected";
    public static final String BLE_GATT_DISCONNECTED = "com.zeroner.sdk.zg.ble.gatt_disconnected";
    public static final String BLE_NOT_SUPPORTED = "com.zeroner.sdk.zg.zg.ble.not_supported";
    public static final String BLE_NO_BT_ADAPTER = "com.zeroner.sdk.zg.ble.no_bt_adapter";
    public static final String BLE_NO_CALLBACK = "com.zeroner.sdk.zg.ble.BLE_NO_CALLBACK";
    public static final String BLE_SERVICE_DISCOVERED = "com.zeroner.sdk.zg.ble.service_discovered";
    public static final String BLE_START_CONNECT = "com.zeroner.sdk.zg.ble.start_connect";
    public static final String BLE_STATUS_ABNORMAL = "com.zeroner.sdk.zg.ble.status_abnormal";
    public static final UUID DESC_CCC = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final int DEVICE_SOURCE_SCAN = 0;
    public static final String EXTRA_ADDR = "ADDRESS";
    public static final String EXTRA_CHARACTER_UUID = "CHARACTER_UUID";
    public static final String EXTRA_CONNECTED = "CONNECTED";
    public static final String EXTRA_DATA = "DATA";
    public static final String EXTRA_DEVICE = "DEVICE";
    public static final String EXTRA_HEART = "EXTRA_HEART";
    public static final String EXTRA_RSSI = "RSSI";
    public static final String EXTRA_SCAN_RECORD = "SCAN_RECORD";
    public static final String EXTRA_SERVICEUUID = "SERVICE_UUID";
    public static final String EXTRA_SOURCE = "SOURCE";
    public static final String EXTRA_STATUS = "STATUS";
    public static final String EXTRA_VALUE = "VALUE";
    private static final String TAG = "blelib";
    private final IBinder mBinder = new LocalBinder();
    private ZGIBle mBle;
    private String mNotificationAddress;

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public ZGService getService() {
            return ZGService.this;
        }
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BLE_NOT_SUPPORTED);
        intentFilter.addAction(BLE_NO_BT_ADAPTER);
        intentFilter.addAction(BLE_STATUS_ABNORMAL);
        intentFilter.addAction(BLE_DEVICE_FOUND);
        intentFilter.addAction(BLE_GATT_CONNECTED);
        intentFilter.addAction(BLE_GATT_DISCONNECTED);
        intentFilter.addAction(BLE_SERVICE_DISCOVERED);
        intentFilter.addAction(BLE_CHARACTERISTIC_READ);
        intentFilter.addAction(BLE_CHARACTERISTIC_DISCOVERED);
        intentFilter.addAction(BLE_CHARACTERISTIC_WRITE);
        intentFilter.addAction(BLE_CHARACTERISTIC_CHANGED);
        intentFilter.addAction(BLE_CONNECT_ERROR_257);
        intentFilter.addAction(BLE_START_CONNECT);
        intentFilter.addAction(BLE_NO_CALLBACK);
        return intentFilter;
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public void onCreate() {
        this.mBle = ZGBle.getInstance(this);
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 3);
            NotificationManager manager = (NotificationManager) getSystemService("notification");
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            manager.createNotificationChannel(channel);
            startForeground(1, new Builder(getApplicationContext(), "11111").build());
            return;
        }
        startForeground(1, new Notification());
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /* access modifiers changed from: protected */
    public void bleNotSupported() {
        sendBroadcast(new Intent(BLE_NOT_SUPPORTED));
    }

    /* access modifiers changed from: protected */
    public void bleNoBtAdapter() {
        sendBroadcast(new Intent(BLE_NO_BT_ADAPTER));
    }

    public ZGIBle getBle() {
        return this.mBle;
    }

    /* access modifiers changed from: protected */
    public void bleDeviceFound(BluetoothDevice device, int rssi, byte[] scanRecord, int source) {
        KLog.d(TAG, "device found " + device.getAddress());
        Intent intent = new Intent(BLE_DEVICE_FOUND);
        intent.putExtra("DEVICE", device);
        intent.putExtra("RSSI", rssi);
        intent.putExtra("SCAN_RECORD", scanRecord);
        intent.putExtra("SOURCE", source);
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public void bleGattConnected(BluetoothDevice device) {
        Intent intent = new Intent(BLE_GATT_CONNECTED);
        intent.putExtra("DEVICE", device);
        intent.putExtra("ADDRESS", device.getAddress());
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public void bleGattDisConnected(String address) {
        Intent intent = new Intent(BLE_GATT_DISCONNECTED);
        intent.putExtra("ADDRESS", address);
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public void bleServiceDiscovered(String address, UUID serviceUUId) {
        KLog.d("bleServiceDiscovered : " + address);
        Intent intent = new Intent(BLE_SERVICE_DISCOVERED);
        intent.putExtra("ADDRESS", address);
        intent.putExtra("SERVICE_UUID", serviceUUId.toString());
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public void bleConnectError257(String address) {
        KLog.d("bleConnectedError257 : " + address);
        Intent intent = new Intent(BLE_CONNECT_ERROR_257);
        intent.putExtra("ADDRESS", address);
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public void bleConnectError133(String address) {
        KLog.d("bleConnectedError257 : " + address);
        Intent intent = new Intent(BLE_CONNECT_ERROR_133);
        intent.putExtra("ADDRESS", address);
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public void bleCharacteristicRead(String address, String uuid, int status, byte[] value, BluetoothGattCharacteristic characteristic) {
        int format;
        Intent intent = new Intent(BLE_CHARACTERISTIC_READ);
        if (R1UUID.BAND_CHARACTERISTIC_NEW_NOTIFY.equals(characteristic.getUuid())) {
            if ((characteristic.getProperties() & 1) != 0) {
                format = 18;
                KLog.d("Heart rate format UINT16.");
            } else {
                format = 17;
                KLog.d("Heart rate format UINT8.");
            }
            int heartRate = characteristic.getIntValue(format, 1).intValue();
            KLog.d(String.format("Received heart rate: %d", new Object[]{Integer.valueOf(heartRate)}));
            intent.putExtra("EXTRA_HEART", String.valueOf(heartRate));
        } else {
            intent.putExtra("ADDRESS", address);
            intent.putExtra("CHARACTER_UUID", uuid);
            intent.putExtra("STATUS", status);
            intent.putExtra("VALUE", value);
        }
        sendBroadcast(intent);
    }

    public void bleStartConnect() {
        sendBroadcast(new Intent(BLE_START_CONNECT));
    }

    /* access modifiers changed from: protected */
    public void bleCharacteristicNotification(String address, UUID uuid, boolean mustConnected) {
        Intent intent = new Intent(BLE_CHARACTERISTIC_DISCOVERED);
        intent.putExtra("ADDRESS", address);
        intent.putExtra("CHARACTER_UUID", uuid.toString());
        intent.putExtra("CONNECTED", mustConnected);
        sendBroadcast(intent);
        setNotificationAddress(address);
    }

    /* access modifiers changed from: protected */
    public void bleCharacteristicIndication(String address, String uuid, int status) {
        Intent intent = new Intent(BLE_CHARACTERISTIC_INDICATION);
        intent.putExtra("ADDRESS", address);
        intent.putExtra("CHARACTER_UUID", uuid);
        intent.putExtra("STATUS", status);
        sendBroadcast(intent);
        setNotificationAddress(address);
    }

    /* access modifiers changed from: protected */
    public void bleCharacteristicWrite(String address, String uuid, int status, byte[] data) {
        Intent intent = new Intent(BLE_CHARACTERISTIC_WRITE);
        intent.putExtra("ADDRESS", address);
        intent.putExtra("CHARACTER_UUID", uuid);
        intent.putExtra("STATUS", status);
        intent.putExtra("DATA", data);
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public void bleCharacteristicChanged(String address, String uuid, byte[] value) {
        Intent intent = new Intent(BLE_CHARACTERISTIC_CHANGED);
        intent.putExtra("ADDRESS", address);
        intent.putExtra("CHARACTER_UUID", uuid);
        intent.putExtra("VALUE", value);
        sendBroadcast(intent);
    }

    public void bleCharacteristicChangedR1Heart(String address, BluetoothGattCharacteristic characteristic) {
        int format;
        Intent intent = new Intent(BLE_CHARACTERISTIC_CHANGED);
        intent.putExtra("ADDRESS", address);
        intent.putExtra("CHARACTER_UUID", characteristic.getUuid());
        if ((characteristic.getProperties() & 1) != 0) {
            format = 18;
            KLog.d("Heart rate format UINT16.");
        } else {
            format = 17;
            KLog.d("Heart rate format UINT8.");
        }
        int heartRate = characteristic.getIntValue(format, 1).intValue();
        KLog.d(String.format("Received heart rate: %d", new Object[]{Integer.valueOf(heartRate)}));
        intent.putExtra("EXTRA_HEART", String.valueOf(heartRate));
        sendBroadcast(intent);
    }

    /* access modifiers changed from: protected */
    public void bleStatusAbnormal(String reason) {
        Intent intent = new Intent(BLE_STATUS_ABNORMAL);
        intent.putExtra("VALUE", reason);
        sendBroadcast(intent);
    }

    public void bleNoCallback() {
        sendBroadcast(new Intent(BLE_NO_CALLBACK));
    }

    /* access modifiers changed from: protected */
    public String getNotificationAddress() {
        return this.mNotificationAddress;
    }

    /* access modifiers changed from: protected */
    public void setNotificationAddress(String mNotificationAddress2) {
        this.mNotificationAddress = mNotificationAddress2;
    }

    public void sendBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
