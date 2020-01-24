package com.iwown.lib_common.views.utils;

public class OneClickUtil {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private String methodName;

    public OneClickUtil(String methodName2) {
        this.methodName = methodName2;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public boolean check() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastClickTime <= 1000) {
            return true;
        }
        this.lastClickTime = currentTime;
        return false;
    }
}
