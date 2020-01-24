package com.iwown.lib_common.network.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.utils.DataTimeUtils;
import java.math.BigDecimal;

public class BaseUtils {
    @SuppressLint({"StaticFieldLeak"})
    private static Context context;

    public static double getDouble1(float target_weight) {
        return new BigDecimal((double) target_weight).setScale(1, 4).doubleValue();
    }

    private BaseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(@NonNull Context context2) {
        context = context2.getApplicationContext();
    }

    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("u should init first");
    }

    public static int getPositionByTime(long preOrNextTimeByDay, int realLastPosition) {
        long size = System.currentTimeMillis() - preOrNextTimeByDay;
        if (size < 0) {
            throwMsgRuntimeException("传入需要计算的时间不对 " + DataTimeUtils.getyyyyMMddHHmmss(preOrNextTimeByDay));
        }
        int marginSize = (int) (size / 86400000);
        new DateUtil(System.currentTimeMillis(), false).addDay(-marginSize);
        return realLastPosition - marginSize;
    }

    public static RuntimeException throwMsgRuntimeException(Object msg) {
        return new RuntimeException("error " + msg);
    }
}
