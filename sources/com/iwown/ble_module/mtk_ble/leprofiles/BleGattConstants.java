package com.iwown.ble_module.mtk_ble.leprofiles;

public class BleGattConstants {
    public static final int BATTERY_LEVEL_1 = 33;
    public static final int BATTERY_LEVEL_2 = 66;
    public static final int BATTERY_LEVEL_3 = 100;
    public static final int FMP_LEVEL_HIGH = 2;
    public static final int FMP_LEVEL_MILD = 1;
    public static final int FMP_LEVEL_NO = 0;
    public static final int STATE_FIND_ME_ALERT = 11;
    public static final int STATE_FIND_ME_NO_ALERT = 10;

    public class GattFeaturFlags {
        public static final int FLAG_SUPPORT_BAS_CLIENT = 4;
        public static final int FLAG_SUPPORT_FMP_CLIENT = 1;
        public static final int FLAG_SUPPORT_FMP_SERVER = 2;

        public GattFeaturFlags() {
        }
    }
}
