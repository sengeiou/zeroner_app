package com.iwown.ble_module.iwown.utils;

import android.os.ParcelUuid;
import java.util.UUID;

public class Constants {
    public static final int HEART_RATE_TYPE = 1055;
    public static final int Language_Arabic = 10;
    public static final int Language_Chinese = 1;
    public static final int Language_English = 0;
    public static final int Language_French = 4;
    public static final int Language_German = 5;
    public static final int Language_Italian = 2;
    public static final int Language_Japanese = 3;
    public static final int Language_Korean = 9;
    public static final int Language_Polish = 12;
    public static final int Language_Portuguese = 6;
    public static final int Language_Russian = 8;
    public static final int Language_Simple = 255;
    public static final int Language_Spanish = 7;
    public static final int Language_Vietnamese = 11;
    public static final int SDK_TYPE = 1;

    public static class CMD {
        public static final int CMD_GRP_CONFIG = 1;
        public static final int CMD_GRP_DATALOG = 2;
        public static final int CMD_GRP_DEVICE = 0;
        public static final int CMD_GRP_MSG = 3;
        public static final int CMD_HEART_RATE_MSG = 5;
        public static final int CMD_ID_0 = 0;
        public static final int CMD_ID_1 = 1;
        public static final int CMD_ID_14 = 14;
        public static final int CMD_ID_2 = 2;
        public static final int CMD_ID_3 = 3;
        public static final int CMD_ID_4 = 4;
        public static final int CMD_ID_5 = 5;
        public static final int CMD_ID_6 = 6;
        public static final int CMD_ID_7 = 7;
        public static final int CMD_ID_8 = 8;
        public static final int CMD_ID_9 = 9;
        public static final int CMD_ID_A = 10;
        public static final int CMD_ID_B = 11;
        public static final int CMD_ID_C = 12;
        public static final int CMD_ID_CONFIG_SET_AC = 4;
        public static final int CMD_ID_D = 13;
        public static final int CMD_PHONE_MSG = 4;
    }

    public static class CustomUUID {
        public static final UUID BAND_CHARACTERISTIC_NEW_INDICATE = UUID.fromString("0000ff23-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_CHARACTERISTIC_NEW_NOTIFY = UUID.fromString("0000ff22-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_CHARACTERISTIC_NEW_WRITE = UUID.fromString("0000ff21-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_DES_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_SERVICE_MAIN = UUID.fromString("0000ff20-0000-1000-8000-00805f9b34fb");
    }

    public static class R1UUID {
        public static final UUID BAND_CHARACTERISTIC_CUSTOM_NOTIFY_HEART = UUID.fromString("00002AFF-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_CHARACTERISTIC_NEW_NOTIFY = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");
        public static final UUID BAND_SERVICE_MAIN_R1 = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
        public static final ParcelUuid BAND_SERVICE_R1_Parcel = ParcelUuid.fromString("0000180D-0000-1000-8000-00805f9b34fb");
    }
}
