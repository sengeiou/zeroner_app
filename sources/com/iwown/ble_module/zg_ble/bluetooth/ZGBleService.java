package com.iwown.ble_module.zg_ble.bluetooth;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.socks.library.KLog;
import java.util.UUID;

public class ZGBleService extends Service {
    public static final String BLE_CHARACTERISTIC_CHANGED = "com.zeroner.sdk.ble.characteristic_changed";
    public static final String BLE_CHARACTERISTIC_DISCOVERED = "com.zeroner.sdk.ble.characteristic_notification";
    public static final String BLE_CHARACTERISTIC_INDICATION = "com.zeroner.sdk.ble.characteristic_indication";
    public static final String BLE_CHARACTERISTIC_READ = "com.zeroner.sdk.ble.characteristic_read";
    public static final String BLE_CHARACTERISTIC_WRITE = "com.zeroner.sdk.ble.characteristic_write";
    public static final String BLE_CONNECT_ERROR_133 = "com.zeroner.sdk.ble.BLE_CONNECT_ERROR_133";
    public static final String BLE_CONNECT_ERROR_257 = "com.zeroner.sdk.ble.BLE_CONNECT_ERROR_257";
    public static final String BLE_DEVICE_FOUND = "com.zeroner.sdk.ble.device_found";
    public static final String BLE_GATT_CONNECTED = "com.zeroner.sdk.ble.gatt_connected";
    public static final String BLE_GATT_DISCONNECTED = "com.zeroner.sdk.ble.gatt_disconnected";
    public static final String BLE_NOT_SUPPORTED = "com.zeroner.sdk.ble.not_supported";
    public static final String BLE_NO_BT_ADAPTER = "com.zeroner.sdk.ble.no_bt_adapter";
    public static final String BLE_NO_CALLBACK = "com.zeroner.sdk.ble.BLE_NO_CALLBACK";
    public static final String BLE_SERVICE_DISCOVERED = "com.zeroner.sdk.ble.service_discovered";
    public static final String BLE_START_CONNECT = "com.zeroner.sdk.ble.start_connect";
    public static final String BLE_STATUS_ABNORMAL = "com.zeroner.sdk.ble.status_abnormal";
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
    private final String TAG = "ZGZGBleService";
    private ZGBinder zgBinder;

    public class ZGBinder extends Binder {
        public ZGBinder() {
        }

        public ZGBleService getService() {
            return ZGBleService.this;
        }
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BLE_NOT_SUPPORTED);
        intentFilter.addAction(BLE_NO_BT_ADAPTER);
        intentFilter.addAction(BLE_STATUS_ABNORMAL);
        intentFilter.addAction("com.zeroner.sdk.ble.device_found");
        intentFilter.addAction("com.zeroner.sdk.ble.gatt_connected");
        intentFilter.addAction(BLE_GATT_DISCONNECTED);
        intentFilter.addAction("com.zeroner.sdk.ble.service_discovered");
        intentFilter.addAction(BLE_CHARACTERISTIC_READ);
        intentFilter.addAction(BLE_CHARACTERISTIC_DISCOVERED);
        intentFilter.addAction(BLE_CHARACTERISTIC_WRITE);
        intentFilter.addAction(BLE_CHARACTERISTIC_CHANGED);
        intentFilter.addAction(BLE_CONNECT_ERROR_257);
        intentFilter.addAction(BLE_START_CONNECT);
        intentFilter.addAction(BLE_NO_CALLBACK);
        return intentFilter;
    }

    public void bleGattDisConnected(String address) {
        Intent intent = new Intent(BLE_GATT_DISCONNECTED);
        intent.putExtra("ADDRESS", address);
        sendBroadcast(intent);
    }

    public void onCreate() {
        super.onCreate();
        KLog.e("ZGZGBleService", " onCreate");
        this.zgBinder = new ZGBinder();
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
        KLog.e("ZGZGBleService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return this.zgBinder;
    }

    public boolean onUnbind(Intent intent) {
        KLog.e("ZGZGBleService", " onUnbind");
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        super.onRebind(intent);
        KLog.e("ZGZGBleService", " onRebind");
    }

    public void onDestroy() {
        super.onDestroy();
        KLog.e("ZGZGBleService", " onDestroy");
    }

    public void boradCastDataChange(BluetoothGattCharacteristic characteristic, String mac) {
        UUID uuid = characteristic.getUuid();
        byte[] value = characteristic.getValue();
        Intent intent = new Intent(BLE_CHARACTERISTIC_CHANGED);
        intent.putExtra("ADDRESS", mac);
        intent.putExtra("CHARACTER_UUID", uuid);
        intent.putExtra("VALUE", value);
        sendBroadcast(intent);
    }

    public void sendBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
