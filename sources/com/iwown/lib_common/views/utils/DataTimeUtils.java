package com.iwown.lib_common.views.utils;

import java.text.SimpleDateFormat;

public class DataTimeUtils {
    public static String getHM(long time) {
        return new SimpleDateFormat("HH:mm").format(Long.valueOf(time));
    }

    public static String getH(long time) {
        return new SimpleDateFormat("HH").format(Long.valueOf(time));
    }

    public static String getTime(long time) {
        return new SimpleDateFormat("yyyyMMdd").format(Long.valueOf(time));
    }

    public static String getyyyyMMddHHmm(long time) {
        return new SimpleDateFormat("yyyyMMdd HH:mm").format(Long.valueOf(time));
    }

    public static String getyyyyMMddHHmmss(long time) {
        return new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(Long.valueOf(time));
    }
}
