package com.iwown.ble_module.mtk_ble.leprofiles;

import java.util.UUID;

public final class BleGattUuid {

    public static final class Char {
        public static final UUID ALERT_LEVEL = UUID.fromString("00002a06-0000-1000-8000-00805f9b34fb");
        public static final UUID ALERT_STATUS = UUID.fromString("00002abc-0000-1000-8000-00805f9b34fb");
        public static final UUID BATTERY_LEVEL = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");
    }

    public static final class Desc {
        public static final UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        public static final UUID CLIENT_CHAR_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    }

    public static final class Service {
        public static final UUID ALERT_NOTIFICATION = UUID.fromString("00001811-0000-1000-8000-00805f9b34fb");
        public static final UUID BATTERY_SERVICE = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");
        public static final UUID IMMEDIATE_ALERT = UUID.fromString("00001802-0000-1000-8000-00805f9b34fb");
    }

    public static final class ZeronerUUID {
        public static final UUID BAND_CHARACTERISTIC_NEW_INDICATE = UUID.fromString("0000ff23-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_CHARACTERISTIC_NEW_WRITE = UUID.fromString("0000ff21-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_DES_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_SERVICE_MAIN = UUID.fromString("0000ff20-0000-1000-8000-00805f9b34fb");
    }
}
