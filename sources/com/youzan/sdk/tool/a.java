package com.youzan.sdk.tool;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Process;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: Environment */
public final class a {

    /* renamed from: ˊ reason: contains not printable characters */
    static final String f319 = "com.eg.android.AlipayGphone";

    /* renamed from: ˋ reason: contains not printable characters */
    private static final int f320 = 255;

    /* renamed from: ˎ reason: contains not printable characters */
    private static final int f321 = 65535;

    /* renamed from: ˏ reason: contains not printable characters */
    private static final AtomicInteger f322 = new AtomicInteger(255);

    /* renamed from: ˊ reason: contains not printable characters */
    public static boolean m69(Context context) {
        if (context != null) {
            String processName = m67(context, Process.myPid());
            if (processName != null) {
                return processName.equals(context.getPackageName());
            }
        }
        return false;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private static String m67(Context context, int pid) {
        if (context != null) {
            ActivityManager am = (ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME);
            if (am != null) {
                List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
                if (runningApps != null) {
                    for (RunningAppProcessInfo procInfo : runningApps) {
                        if (procInfo.pid == pid) {
                            return procInfo.processName;
                        }
                    }
                }
            }
        }
        return null;
    }

    /* renamed from: ˋ reason: contains not printable characters */
    static String m70(Context context) {
        try {
            String pkName = context.getPackageName();
            return "App/" + pkName + "_v" + context.getPackageManager().getPackageInfo(pkName, 0).versionName;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static int m66() {
        int result;
        int newValue;
        do {
            result = f322.get();
            newValue = result + 1;
            if (newValue >= 65535) {
                newValue = 255;
            }
        } while (!f322.compareAndSet(result, newValue));
        return result;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public static void m68(Context context, String text) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("textMessage", text));
    }

    /* renamed from: ˋ reason: contains not printable characters */
    public static boolean m71(Context context, String pkg) {
        if (context == null || TextUtils.isEmpty(pkg)) {
            return true;
        }
        try {
            synchronized (PackageManager.class) {
                context.getPackageManager().getPackageInfo(pkg, 1);
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    /* renamed from: ˎ reason: contains not printable characters */
    public static boolean m72(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        if (cm == null) {
            return true;
        }
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }
}
