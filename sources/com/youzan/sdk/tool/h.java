package com.youzan.sdk.tool;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

/* compiled from: UserAgent */
public final class h {
    /* renamed from: ˊ reason: contains not printable characters */
    public static String m127(Context context, String youzanUA) {
        return m126(context) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + youzanUA;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private static String m126(Context context) {
        String VMCode = m125();
        String buildCode = Build.DISPLAY;
        String deviceModel = Build.MODEL;
        String str = "Linux";
        String str2 = "Android";
        return String.format("Dalvik/%s (%s; %s %s; %s Build/%s; %s)", new Object[]{VMCode, "Linux", "Android", VERSION.RELEASE, deviceModel, buildCode, a.m70(context)});
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private static String m125() {
        return System.getProperty("java.vm.version");
    }
}
