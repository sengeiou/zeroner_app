package com.youzan.sdk;

import android.text.TextUtils;
import android.util.Log;

public final class YouzanLog {

    /* renamed from: ˊ reason: contains not printable characters */
    private static final String f16 = "YZSDK";

    /* renamed from: ˋ reason: contains not printable characters */
    private static final String f17 = "CONTENT IS NONE";

    /* renamed from: ˎ reason: contains not printable characters */
    private static boolean f18 = false;

    public static boolean isDebug() {
        return f18;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    static void m16(boolean isDebug) {
        f18 = isDebug;
    }

    public static void i(String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.i(f16, m15((Object) msg));
        }
    }

    public static void d(String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.d(f16, m15((Object) msg));
        }
    }

    public static void w(String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.w(f16, m15((Object) msg));
        }
    }

    public static void e(Object msg) {
        if (isDebug() && msg != null) {
            Log.e(f16, m15(msg));
        }
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private static String m15(Object msgObj) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length <= 4) {
            return f17;
        }
        String className = stackTrace[4].getFileName();
        String methodName = stackTrace[4].getMethodName();
        int lineNumber = stackTrace[4].getLineNumber();
        String methodName2 = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        StringBuilder stringBuilder = new StringBuilder(24);
        stringBuilder.append("(").append(className).append(':').append(lineNumber).append(")->").append(methodName2).append(" : ");
        stringBuilder.append(msgObj == null ? f17 : msgObj.toString());
        return stringBuilder.toString();
    }
}
