package com.iwown.ble_module.mtk_ble;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

class MtkBroadcastSender {
    private static final String ACTION_CONNECT_TIMEOUT = "com.zeroner.sdk.ble.ACTION_CONNECT_TIMEOUT";
    private static final String ACTION_RECONNECT = "com.zeroner_pro.sdk.ble.ACTION_RECONNECT";
    private static final String BLE_CHARACTERISTIC_CHANGED = "com.zeroner.sdk.ble.characteristic_changed";
    private static final String BLE_CHARACTERISTIC_INDICATION = "com.zeroner.sdk.ble.characteristic_indication";
    private static final String BLE_CHARACTERISTIC_NOTIFICATION = "com.zeroner.sdk.ble.characteristic_notification";
    private static final String BLE_CHARACTERISTIC_READ = "com.zeroner.sdk.ble.characteristic_read";
    private static final String BLE_CHARACTERISTIC_WRITE = "com.zeroner.sdk.ble.characteristic_write";
    private static final String BLE_CONNECT_ERROR_133 = "com.zeroner.sdk.ble.BLE_CONNECT_ERROR_133";
    private static final String BLE_CONNECT_ERROR_257 = "com.zeroner.sdk.ble.BLE_CONNECT_ERROR_257";
    private static final String BLE_GATT_DISCONNECTED = "com.zeroner.sdk.ble.gatt_disconnected";
    private static final String BLE_NOT_SUPPORTED = "com.zeroner.sdk.ble.not_supported";
    private static final String BLE_NO_BT_ADAPTER = "com.zeroner.sdk.ble.no_bt_adapter";
    private static final String BLE_REQUEST_FAILED = "com.zeroner.sdk.ble.request_failed";
    private static final String BLE_STATUS_ABNORMAL = "com.zeroner.sdk.ble.status_abnormal";
    private static final String EXTRA_ADDR = "ADDRESS";
    private static final String EXTRA_CAN_WRITE_DATA = "CAN_WRITE_DATA";
    public static final String EXTRA_CHARACTERISTIC_UUID = "SERVICE_CHARACTERISTIC_UUID";
    public static final String EXTRA_CONNECTED = "CONNECTED";
    public static final String EXTRA_CONNECT_NEW_STATE = "CONNECT_NEW_STATE";
    public static final String EXTRA_CONNECT_STATUS = "CONNTECT_STATUS";
    private static final String EXTRA_DATA = "DATA";
    public static final String EXTRA_DATA_TYPE = "DATA_TYPE";
    public static final String EXTRA_DATA_WRITED = "DATA_WRITED";
    public static final String EXTRA_DEVICE = "DEVICE";
    public static final String EXTRA_DEVICE_TYPE = "CONNECT_DEVICE_TYPE";
    public static final String EXTRA_ERROR = "ERROR";
    private static final String EXTRA_REASON = "REASON";
    private static final String EXTRA_REQUEST = "REQUEST";
    public static final String EXTRA_RESULT_PARSED = "RESULT_PARSED";
    private static final String EXTRA_RSSI = "RSSI";
    private static final String EXTRA_SCAN_RECORD = "SCAN_RECORD";
    public static final String EXTRA_SERVICE_DISCOVERED_STATE = "SERVICE_DISCOVERED_STATE";
    public static final String EXTRA_SERVICE_UUID = "SERVICE_DISCOVERED_UUID";
    private static final String EXTRA_SOURCE = "SOURCE";
    private static final String EXTRA_STATUS = "STATUS";
    private static final String EXTRA_UUID = "UUID";
    private static final String EXTRA_VALUE = "VALUE";
    public static final String ZERONER_CAN_WRITE_DATA_TO_DEV = "com.zeroner_pro.sdk.ble.CAN_WRITE_DATA_TO_DEV";
    public static final String ZERONER_CHARACTERISTIC_FOUND = "com.zeroner.sdk.ble.characteristic_found";
    public static final String ZERONER_CONNECTED_STATE = "com.zeroner.sdk.ble.gatt_connected";
    public static final String ZERONER_CONNECTED_WITH_STATE_CODE = "com.zeroner.sdk.ble.gatt_connected_with_state";
    public static final String ZERONER_DATA_RESULT = "com.zeroner_pro.sdk.ble.DATA_RESULT";
    public static final String ZERONER_DEVICE_FOUND = "com.zeroner.sdk.ble.device_found";
    public static final String ZERONER_ERROR = "com.zeroner_pro.sdk.ble.ZERONER_ERROR";
    public static final String ZERONER_NO_CALLBACK = "com.zeroner_pro.sdk.ble.ZERONER_NO_CALLBACK";
    public static final String ZERONER_ON_DATA_WRITE = "com.zeroner.sdk.ble.on_data_write";
    public static final String ZERONER_SERVICE_DISCOVERED = "com.zeroner.sdk.ble.service_discovered";
    public static final String ZERONER_START_CONNECT = "com.zeroner_pro.sdk.ble.ZERONER_START_CONNECT";
    public static final String ZERONER_TIME_HEART = "com.zeroner_pro.sdk.ble.ZERONER_TIME_HEART";
    private static MtkBroadcastSender instance = null;
    private Context mContext = null;

    private MtkBroadcastSender(Context context) {
        this.mContext = context;
    }

    public static MtkBroadcastSender getInstance(Context context) {
        if (instance == null) {
            synchronized (MtkBroadcastSender.class) {
                if (instance == null) {
                    instance = new MtkBroadcastSender(context);
                }
            }
        }
        return instance;
    }

    public static MtkBroadcastSender getInstance() {
        return instance;
    }

    public void onConnectStateChangeWithStateCode(boolean isConnect, int status, int newState) {
        Intent intent = new Intent(ZERONER_CONNECTED_WITH_STATE_CODE);
        intent.putExtra("CONNECTED", isConnect);
        intent.putExtra(EXTRA_CONNECT_STATUS, status);
        intent.putExtra(EXTRA_CONNECT_NEW_STATE, newState);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }

    public void onCharacteristicFound(String uuid) {
        Intent intent = new Intent(ZERONER_CHARACTERISTIC_FOUND);
        intent.putExtra(EXTRA_CHARACTERISTIC_UUID, uuid);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }

    public void onConnectStateChange(boolean isConnect) {
        Intent intent = new Intent("com.zeroner.sdk.ble.gatt_connected");
        intent.putExtra("CONNECTED", isConnect);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }

    public void onServicesDiscovered(String service_uuid, int state) {
        Intent intent = new Intent("com.zeroner.sdk.ble.service_discovered");
        intent.putExtra(EXTRA_SERVICE_DISCOVERED_STATE, state);
        intent.putExtra(EXTRA_SERVICE_UUID, service_uuid);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }

    public void onCanWriteDataToDev() {
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(new Intent(ZERONER_CAN_WRITE_DATA_TO_DEV));
    }

    public void onDataArrive(int dataType, String result) {
        Intent intent = new Intent(ZERONER_DATA_RESULT);
        intent.putExtra(EXTRA_RESULT_PARSED, result);
        intent.putExtra(EXTRA_DATA_TYPE, dataType);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }

    public void onError(int errorCode) {
        Intent intent = new Intent(ZERONER_ERROR);
        intent.putExtra(EXTRA_ERROR, errorCode);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }

    public void onStartConnect() {
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(new Intent(ZERONER_START_CONNECT));
    }

    public void onNoCallback() {
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(new Intent(ZERONER_NO_CALLBACK));
    }

    public void onDataWrite(byte[] data) {
        Intent intent = new Intent(ZERONER_ON_DATA_WRITE);
        intent.putExtra(EXTRA_DATA_WRITED, data);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zeroner.sdk.ble.device_found");
        intentFilter.addAction("com.zeroner.sdk.ble.gatt_connected");
        intentFilter.addAction(ZERONER_CONNECTED_WITH_STATE_CODE);
        intentFilter.addAction(ZERONER_CAN_WRITE_DATA_TO_DEV);
        intentFilter.addAction(ZERONER_DATA_RESULT);
        intentFilter.addAction(ZERONER_ERROR);
        intentFilter.addAction(ZERONER_START_CONNECT);
        intentFilter.addAction(ZERONER_NO_CALLBACK);
        intentFilter.addAction(ZERONER_CHARACTERISTIC_FOUND);
        intentFilter.addAction(ZERONER_ON_DATA_WRITE);
        return intentFilter;
    }
}
