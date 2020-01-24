package com.iwown.device_module.common.Bluetooth.receiver.zg.dao;

public class AgpsStatue {
    public static byte[] allAgps;
    public static int allPoint = 1;
    public static boolean blood = false;
    public static int count2048 = 0;
    public static int count256 = 0;
    public static boolean isAgps = false;
    public static int nowPoint = 0;
    public static int progress = 0;
    public static int sum2048;

    public static void clear() {
        allAgps = null;
        count256 = 0;
        count2048 = 0;
        nowPoint = 0;
        blood = false;
    }

    public static int getAgpsLength() {
        if (allAgps == null) {
            return 0;
        }
        return allAgps.length / 2048;
    }
}
