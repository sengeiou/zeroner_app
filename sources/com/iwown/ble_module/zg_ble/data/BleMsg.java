package com.iwown.ble_module.zg_ble.data;

public class BleMsg {
    public static final String KEY_INDICATE_BUNDLE_STATUS = "indicate_status";
    public static final String KEY_INDICATE_BUNDLE_VALUE = "indicate_value";
    public static final String KEY_NOTIFY_BUNDLE_STATUS = "notify_status";
    public static final String KEY_NOTIFY_BUNDLE_VALUE = "notify_value";
    public static final String KEY_READ_BUNDLE_STATUS = "read_status";
    public static final String KEY_READ_BUNDLE_VALUE = "read_value";
    public static final String KEY_READ_RSSI_BUNDLE_STATUS = "rssi_status";
    public static final String KEY_READ_RSSI_BUNDLE_VALUE = "rssi_value";
    public static final String KEY_SET_MTU_BUNDLE_STATUS = "mtu_status";
    public static final String KEY_SET_MTU_BUNDLE_VALUE = "mtu_value";
    public static final String KEY_WRITE_BUNDLE_STATUS = "write_status";
    public static final int MSG_CHA_INDICATE_DATA_CHANGE = 35;
    public static final int MSG_CHA_INDICATE_RESULT = 34;
    public static final int MSG_CHA_INDICATE_START = 33;
    public static final int MSG_CHA_NOTIFY_DATA_CHANGE = 19;
    public static final int MSG_CHA_NOTIFY_RESULT = 18;
    public static final int MSG_CHA_NOTIFY_START = 17;
    public static final int MSG_CHA_READ_RESULT = 66;
    public static final int MSG_CHA_READ_START = 65;
    public static final int MSG_CHA_WRITE_RESULT = 50;
    public static final int MSG_CHA_WRITE_START = 49;
    public static final int MSG_CONNECT_FAIL = 1;
    public static final int MSG_CONNECT_SUCCESS = 3;
    public static final int MSG_DISCONNECTED = 2;
    public static final int MSG_READ_RSSI_RESULT = 82;
    public static final int MSG_READ_RSSI_START = 81;
    public static final int MSG_SET_MTU_RESULT = 98;
    public static final int MSG_SET_MTU_START = 97;
}
