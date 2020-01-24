package com.tencent.bugly.crashreport.common.info;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.consts.UserConst;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: BUGLY */
public class b {
    private static final String[] a = {"/su", "/su/bin/su", "/sbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su", "/system/xbin/su", "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr", "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd", "/system/bin/conbb", "/system/xbin/conbb"};
    private static final String[] b = {"com.ami.duosupdater.ui", "com.ami.launchmetro", "com.ami.syncduosservices", "com.bluestacks.home", "com.bluestacks.windowsfilemanager", "com.bluestacks.settings", "com.bluestacks.bluestackslocationprovider", "com.bluestacks.appsettings", "com.bluestacks.bstfolder", "com.bluestacks.BstCommandProcessor", "com.bluestacks.s2p", "com.bluestacks.setup", "com.kaopu001.tiantianserver", "com.kpzs.helpercenter", "com.kaopu001.tiantianime", "com.android.development_settings", "com.android.development", "com.android.customlocale2", "com.genymotion.superuser", "com.genymotion.clipboardproxy", "com.uc.xxzs.keyboard", "com.uc.xxzs", "com.blue.huang17.agent", "com.blue.huang17.launcher", "com.blue.huang17.ime", "com.microvirt.guide", "com.microvirt.market", "com.microvirt.memuime", "cn.itools.vm.launcher", "cn.itools.vm.proxy", "cn.itools.vm.softkeyboard", "cn.itools.avdmarket", "com.syd.IME", "com.bignox.app.store.hd", "com.bignox.launcher", "com.bignox.app.phone", "com.bignox.app.noxservice", "com.android.noxpush", "com.haimawan.push", "me.haima.helpcenter", "com.windroy.launcher", "com.windroy.superuser", "com.windroy.launcher", "com.windroy.ime", "com.android.flysilkworm", "com.android.emu.inputservice", "com.tiantian.ime", "com.microvirt.launcher", "me.le8.androidassist", "com.vphone.helper", "com.vphone.launcher", "com.duoyi.giftcenter.giftcenter"};
    private static final String[] c = {"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/qemud", "/dev/qemu_pipe", "/dev/socket/baseband_genyd", "/dev/socket/genyd"};
    private static String d = null;
    private static String e = null;

    public static String a() {
        try {
            return Build.MODEL;
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return "fail";
        }
    }

    public static String b() {
        try {
            return VERSION.RELEASE;
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return "fail";
        }
    }

    public static int c() {
        try {
            return VERSION.SDK_INT;
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return -1;
        }
    }

    public static String a(Context context) {
        String str;
        if (context == null) {
            return null;
        }
        if (!AppInfo.a(context, "android.permission.READ_PHONE_STATE")) {
            an.d("no READ_PHONE_STATE permission to get IMEI", new Object[0]);
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(UserConst.PHONE);
            if (telephonyManager != null) {
                str = telephonyManager.getDeviceId();
                if (str != null) {
                    try {
                        str = str.toLowerCase();
                    } catch (Throwable th) {
                    }
                }
            } else {
                str = null;
            }
        } catch (Throwable th2) {
            str = null;
            an.a("Failed to get IMEI.", new Object[0]);
            return str;
        }
        return str;
    }

    public static String b(Context context) {
        String str;
        if (context == null) {
            return null;
        }
        if (!AppInfo.a(context, "android.permission.READ_PHONE_STATE")) {
            an.d("no READ_PHONE_STATE permission to get IMSI", new Object[0]);
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(UserConst.PHONE);
            if (telephonyManager != null) {
                str = telephonyManager.getSubscriberId();
                if (str != null) {
                    try {
                        str = str.toLowerCase();
                    } catch (Throwable th) {
                    }
                }
            } else {
                str = null;
            }
        } catch (Throwable th2) {
            str = null;
            an.a("Failed to get IMSI.", new Object[0]);
            return str;
        }
        return str;
    }

    public static String c(Context context) {
        String str = "fail";
        if (context == null) {
            return str;
        }
        try {
            String string = Secure.getString(context.getContentResolver(), "android_id");
            if (string == null) {
                return "null";
            }
            try {
                return string.toLowerCase();
            } catch (Throwable th) {
                Throwable th2 = th;
                str = string;
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
        if (an.a(th)) {
            return str;
        }
        an.a("Failed to get Android ID.", new Object[0]);
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
        if (r0.equals("02:00:00:00:00:00") != false) goto L_0x0028;
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0076  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String d(android.content.Context r8) {
        /*
            r6 = 1
            r5 = 0
            java.lang.String r1 = "fail"
            if (r8 != 0) goto L_0x0008
        L_0x0007:
            return r1
        L_0x0008:
            java.lang.String r0 = "wifi"
            java.lang.Object r0 = r8.getSystemService(r0)     // Catch:{ Throwable -> 0x006f }
            android.net.wifi.WifiManager r0 = (android.net.wifi.WifiManager) r0     // Catch:{ Throwable -> 0x006f }
            if (r0 == 0) goto L_0x0079
            android.net.wifi.WifiInfo r0 = r0.getConnectionInfo()     // Catch:{ Throwable -> 0x006f }
            if (r0 == 0) goto L_0x0079
            java.lang.String r0 = r0.getMacAddress()     // Catch:{ Throwable -> 0x006f }
            if (r0 == 0) goto L_0x0028
            java.lang.String r1 = "02:00:00:00:00:00"
            boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x007b }
            if (r1 == 0) goto L_0x005b
        L_0x0028:
            java.lang.String r1 = "wifi.interface"
            java.lang.String r1 = com.tencent.bugly.proguard.ap.b(r8, r1)     // Catch:{ Throwable -> 0x007b }
            java.lang.String r2 = "MAC interface: %s"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x007b }
            r4 = 0
            r3[r4] = r1     // Catch:{ Throwable -> 0x007b }
            com.tencent.bugly.proguard.an.c(r2, r3)     // Catch:{ Throwable -> 0x007b }
            java.net.NetworkInterface r1 = java.net.NetworkInterface.getByName(r1)     // Catch:{ Throwable -> 0x007b }
            if (r1 != 0) goto L_0x0048
            java.lang.String r1 = "wlan0"
            java.net.NetworkInterface r1 = java.net.NetworkInterface.getByName(r1)     // Catch:{ Throwable -> 0x007b }
        L_0x0048:
            if (r1 != 0) goto L_0x0051
            java.lang.String r1 = "eth0"
            java.net.NetworkInterface r1 = java.net.NetworkInterface.getByName(r1)     // Catch:{ Throwable -> 0x007b }
        L_0x0051:
            if (r1 == 0) goto L_0x005b
            byte[] r1 = r1.getHardwareAddress()     // Catch:{ Throwable -> 0x007b }
            java.lang.String r0 = com.tencent.bugly.proguard.ap.e(r1)     // Catch:{ Throwable -> 0x007b }
        L_0x005b:
            if (r0 != 0) goto L_0x0060
            java.lang.String r0 = "null"
        L_0x0060:
            java.lang.String r1 = "MAC address: %s"
            java.lang.Object[] r2 = new java.lang.Object[r6]
            r2[r5] = r0
            com.tencent.bugly.proguard.an.c(r1, r2)
            java.lang.String r1 = r0.toLowerCase()
            goto L_0x0007
        L_0x006f:
            r0 = move-exception
        L_0x0070:
            boolean r2 = com.tencent.bugly.proguard.an.a(r0)
            if (r2 != 0) goto L_0x0079
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
        L_0x0079:
            r0 = r1
            goto L_0x005b
        L_0x007b:
            r1 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x0070
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.d(android.content.Context):java.lang.String");
    }

    public static String e(Context context) {
        String str;
        String str2 = "fail";
        if (context == null) {
            return str2;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(UserConst.PHONE);
            if (telephonyManager != null) {
                str = telephonyManager.getSimSerialNumber();
                if (str == null) {
                    str = "null";
                }
            } else {
                str = str2;
            }
        } catch (Throwable th) {
            str = str2;
            an.a("Failed to get SIM serial number.", new Object[0]);
        }
        return str;
    }

    public static String d() {
        try {
            return Build.SERIAL;
        } catch (Throwable th) {
            an.a("Failed to get hardware serial number.", new Object[0]);
            return "fail";
        }
    }

    public static boolean e() {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                return true;
            }
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
        return false;
    }

    public static String a(Context context, boolean z) {
        String str = null;
        if (z) {
            try {
                String b2 = ap.b(context, "ro.product.cpu.abilist");
                if (ap.a(b2) || b2.equals("fail")) {
                    b2 = ap.b(context, "ro.product.cpu.abi");
                }
                if (!ap.a(b2) && !b2.equals("fail")) {
                    an.b(b.class, "ABI list: " + b2, new Object[0]);
                    str = b2.split(",")[0];
                }
            } catch (Throwable th) {
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
                return "fail";
            }
        }
        if (str == null) {
            str = System.getProperty("os.arch");
        }
        return "" + str;
    }

    public static long f() {
        long j = -1;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (an.a(th)) {
                return j;
            }
            ThrowableExtension.printStackTrace(th);
            return j;
        }
    }

    public static long g() {
        long j = -1;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (an.a(th)) {
                return j;
            }
            ThrowableExtension.printStackTrace(th);
            return j;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x0086 A[Catch:{ all -> 0x00d5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x008b A[SYNTHETIC, Splitter:B:48:0x008b] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0090 A[SYNTHETIC, Splitter:B:51:0x0090] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00b1 A[SYNTHETIC, Splitter:B:65:0x00b1] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00b6 A[SYNTHETIC, Splitter:B:68:0x00b6] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long h() {
        /*
            r1 = 0
            java.lang.String r0 = "/proc/meminfo"
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x007e, all -> 0x00ac }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x007e, all -> 0x00ac }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00d9, all -> 0x00d0 }
            r0 = 2048(0x800, float:2.87E-42)
            r2.<init>(r3, r0)     // Catch:{ Throwable -> 0x00d9, all -> 0x00d0 }
            java.lang.String r0 = r2.readLine()     // Catch:{ Throwable -> 0x00dc, all -> 0x00d3 }
            if (r0 != 0) goto L_0x0039
            r0 = -1
            if (r2 == 0) goto L_0x001d
            r2.close()     // Catch:{ IOException -> 0x0023 }
        L_0x001d:
            if (r3 == 0) goto L_0x0022
            r3.close()     // Catch:{ IOException -> 0x002e }
        L_0x0022:
            return r0
        L_0x0023:
            r2 = move-exception
            boolean r4 = com.tencent.bugly.proguard.an.a(r2)
            if (r4 != 0) goto L_0x001d
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x001d
        L_0x002e:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r2)
            if (r3 != 0) goto L_0x0022
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0022
        L_0x0039:
            java.lang.String r1 = ":\\s+"
            r4 = 2
            java.lang.String[] r0 = r0.split(r1, r4)     // Catch:{ Throwable -> 0x00dc, all -> 0x00d3 }
            r1 = 1
            r0 = r0[r1]     // Catch:{ Throwable -> 0x00dc, all -> 0x00d3 }
            java.lang.String r0 = r0.toLowerCase()     // Catch:{ Throwable -> 0x00dc, all -> 0x00d3 }
            java.lang.String r1 = "kb"
            java.lang.String r4 = ""
            java.lang.String r0 = r0.replace(r1, r4)     // Catch:{ Throwable -> 0x00dc, all -> 0x00d3 }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x00dc, all -> 0x00d3 }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ Throwable -> 0x00dc, all -> 0x00d3 }
            r4 = 1024(0x400, double:5.06E-321)
            long r0 = r0 * r4
            if (r2 == 0) goto L_0x0062
            r2.close()     // Catch:{ IOException -> 0x0073 }
        L_0x0062:
            if (r3 == 0) goto L_0x0022
            r3.close()     // Catch:{ IOException -> 0x0068 }
            goto L_0x0022
        L_0x0068:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r2)
            if (r3 != 0) goto L_0x0022
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0022
        L_0x0073:
            r2 = move-exception
            boolean r4 = com.tencent.bugly.proguard.an.a(r2)
            if (r4 != 0) goto L_0x0062
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0062
        L_0x007e:
            r0 = move-exception
            r2 = r1
        L_0x0080:
            boolean r3 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x00d5 }
            if (r3 != 0) goto L_0x0089
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x00d5 }
        L_0x0089:
            if (r1 == 0) goto L_0x008e
            r1.close()     // Catch:{ IOException -> 0x0096 }
        L_0x008e:
            if (r2 == 0) goto L_0x0093
            r2.close()     // Catch:{ IOException -> 0x00a1 }
        L_0x0093:
            r0 = -2
            goto L_0x0022
        L_0x0096:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x008e
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x008e
        L_0x00a1:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x0093
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0093
        L_0x00ac:
            r0 = move-exception
            r2 = r1
            r3 = r1
        L_0x00af:
            if (r2 == 0) goto L_0x00b4
            r2.close()     // Catch:{ IOException -> 0x00ba }
        L_0x00b4:
            if (r3 == 0) goto L_0x00b9
            r3.close()     // Catch:{ IOException -> 0x00c5 }
        L_0x00b9:
            throw r0
        L_0x00ba:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.a(r1)
            if (r2 != 0) goto L_0x00b4
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00b4
        L_0x00c5:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.a(r1)
            if (r2 != 0) goto L_0x00b9
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00b9
        L_0x00d0:
            r0 = move-exception
            r2 = r1
            goto L_0x00af
        L_0x00d3:
            r0 = move-exception
            goto L_0x00af
        L_0x00d5:
            r0 = move-exception
            r3 = r2
            r2 = r1
            goto L_0x00af
        L_0x00d9:
            r0 = move-exception
            r2 = r3
            goto L_0x0080
        L_0x00dc:
            r0 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x0080
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.h():long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x0150 A[SYNTHETIC, Splitter:B:100:0x0150] */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0155 A[SYNTHETIC, Splitter:B:103:0x0155] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0124 A[Catch:{ all -> 0x0174 }] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0129 A[SYNTHETIC, Splitter:B:83:0x0129] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x012e A[SYNTHETIC, Splitter:B:86:0x012e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long i() {
        /*
            r3 = 0
            r10 = 1024(0x400, double:5.06E-321)
            r0 = -1
            java.lang.String r2 = "/proc/meminfo"
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x011c, all -> 0x014b }
            r4.<init>(r2)     // Catch:{ Throwable -> 0x011c, all -> 0x014b }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0178, all -> 0x016f }
            r5 = 2048(0x800, float:2.87E-42)
            r2.<init>(r4, r5)     // Catch:{ Throwable -> 0x0178, all -> 0x016f }
            r2.readLine()     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            java.lang.String r3 = r2.readLine()     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            if (r3 != 0) goto L_0x003e
            if (r2 == 0) goto L_0x0022
            r2.close()     // Catch:{ IOException -> 0x0028 }
        L_0x0022:
            if (r4 == 0) goto L_0x0027
            r4.close()     // Catch:{ IOException -> 0x0033 }
        L_0x0027:
            return r0
        L_0x0028:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r2)
            if (r3 != 0) goto L_0x0022
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0022
        L_0x0033:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r2)
            if (r3 != 0) goto L_0x0027
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0027
        L_0x003e:
            java.lang.String r5 = ":\\s+"
            r6 = 2
            java.lang.String[] r3 = r3.split(r5, r6)     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            r5 = 1
            r3 = r3[r5]     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            java.lang.String r3 = r3.toLowerCase()     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            java.lang.String r5 = "kb"
            java.lang.String r6 = ""
            java.lang.String r3 = r3.replace(r5, r6)     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            java.lang.String r3 = r3.trim()     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            r6 = 0
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            long r8 = r8 * r10
            long r6 = r6 + r8
            java.lang.String r3 = r2.readLine()     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            if (r3 != 0) goto L_0x008a
            if (r2 == 0) goto L_0x006e
            r2.close()     // Catch:{ IOException -> 0x007f }
        L_0x006e:
            if (r4 == 0) goto L_0x0027
            r4.close()     // Catch:{ IOException -> 0x0074 }
            goto L_0x0027
        L_0x0074:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r2)
            if (r3 != 0) goto L_0x0027
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0027
        L_0x007f:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r2)
            if (r3 != 0) goto L_0x006e
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x006e
        L_0x008a:
            java.lang.String r5 = ":\\s+"
            r8 = 2
            java.lang.String[] r3 = r3.split(r5, r8)     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            r5 = 1
            r3 = r3[r5]     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            java.lang.String r3 = r3.toLowerCase()     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            java.lang.String r5 = "kb"
            java.lang.String r8 = ""
            java.lang.String r3 = r3.replace(r5, r8)     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            java.lang.String r3 = r3.trim()     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            long r8 = java.lang.Long.parseLong(r3)     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            long r8 = r8 * r10
            long r6 = r6 + r8
            java.lang.String r3 = r2.readLine()     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            if (r3 != 0) goto L_0x00d6
            if (r2 == 0) goto L_0x00b8
            r2.close()     // Catch:{ IOException -> 0x00cb }
        L_0x00b8:
            if (r4 == 0) goto L_0x0027
            r4.close()     // Catch:{ IOException -> 0x00bf }
            goto L_0x0027
        L_0x00bf:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r2)
            if (r3 != 0) goto L_0x0027
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0027
        L_0x00cb:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r2)
            if (r3 != 0) goto L_0x00b8
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x00b8
        L_0x00d6:
            java.lang.String r0 = ":\\s+"
            r1 = 2
            java.lang.String[] r0 = r3.split(r0, r1)     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            r1 = 1
            r0 = r0[r1]     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            java.lang.String r0 = r0.toLowerCase()     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            java.lang.String r1 = "kb"
            java.lang.String r3 = ""
            java.lang.String r0 = r0.replace(r1, r3)     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ Throwable -> 0x017c, all -> 0x0172 }
            long r0 = r0 * r10
            long r0 = r0 + r6
            if (r2 == 0) goto L_0x00fe
            r2.close()     // Catch:{ IOException -> 0x0111 }
        L_0x00fe:
            if (r4 == 0) goto L_0x0027
            r4.close()     // Catch:{ IOException -> 0x0105 }
            goto L_0x0027
        L_0x0105:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r2)
            if (r3 != 0) goto L_0x0027
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0027
        L_0x0111:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r2)
            if (r3 != 0) goto L_0x00fe
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x00fe
        L_0x011c:
            r0 = move-exception
            r1 = r3
        L_0x011e:
            boolean r2 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x0174 }
            if (r2 != 0) goto L_0x0127
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0174 }
        L_0x0127:
            if (r1 == 0) goto L_0x012c
            r1.close()     // Catch:{ IOException -> 0x0135 }
        L_0x012c:
            if (r3 == 0) goto L_0x0131
            r3.close()     // Catch:{ IOException -> 0x0140 }
        L_0x0131:
            r0 = -2
            goto L_0x0027
        L_0x0135:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x012c
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x012c
        L_0x0140:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x0131
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0131
        L_0x014b:
            r0 = move-exception
            r2 = r3
            r4 = r3
        L_0x014e:
            if (r2 == 0) goto L_0x0153
            r2.close()     // Catch:{ IOException -> 0x0159 }
        L_0x0153:
            if (r4 == 0) goto L_0x0158
            r4.close()     // Catch:{ IOException -> 0x0164 }
        L_0x0158:
            throw r0
        L_0x0159:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.a(r1)
            if (r2 != 0) goto L_0x0153
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0153
        L_0x0164:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.a(r1)
            if (r2 != 0) goto L_0x0158
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0158
        L_0x016f:
            r0 = move-exception
            r2 = r3
            goto L_0x014e
        L_0x0172:
            r0 = move-exception
            goto L_0x014e
        L_0x0174:
            r0 = move-exception
            r2 = r1
            r4 = r3
            goto L_0x014e
        L_0x0178:
            r0 = move-exception
            r1 = r3
            r3 = r4
            goto L_0x011e
        L_0x017c:
            r0 = move-exception
            r1 = r2
            r3 = r4
            goto L_0x011e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.i():long");
    }

    public static long j() {
        if (!e()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount());
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return -2;
        }
    }

    public static long k() {
        if (!e()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return -2;
        }
    }

    public static String l() {
        String str = "fail";
        try {
            return Locale.getDefault().getCountry();
        } catch (Throwable th) {
            if (an.a(th)) {
                return str;
            }
            ThrowableExtension.printStackTrace(th);
            return str;
        }
    }

    public static String m() {
        String str = "fail";
        try {
            return Build.BRAND;
        } catch (Throwable th) {
            if (an.a(th)) {
                return str;
            }
            ThrowableExtension.printStackTrace(th);
            return str;
        }
    }

    public static String f(Context context) {
        String str;
        String str2 = "unknown";
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getType() == 1) {
                str = "WIFI";
            } else {
                if (activeNetworkInfo.getType() == 0) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(UserConst.PHONE);
                    if (telephonyManager != null) {
                        int networkType = telephonyManager.getNetworkType();
                        switch (networkType) {
                            case 1:
                                str = "GPRS";
                                break;
                            case 2:
                                str = "EDGE";
                                break;
                            case 3:
                                str = "UMTS";
                                break;
                            case 4:
                                str = "CDMA";
                                break;
                            case 5:
                                str = "EVDO_0";
                                break;
                            case 6:
                                str = "EVDO_A";
                                break;
                            case 7:
                                str = "1xRTT";
                                break;
                            case 8:
                                str = "HSDPA";
                                break;
                            case 9:
                                str = "HSUPA";
                                break;
                            case 10:
                                str = "HSPA";
                                break;
                            case 11:
                                str = "iDen";
                                break;
                            case 12:
                                str = "EVDO_B";
                                break;
                            case 13:
                                str = "LTE";
                                break;
                            case 14:
                                str = "eHRPD";
                                break;
                            case 15:
                                str = "HSPA+";
                                break;
                            default:
                                str = "MOBILE(" + networkType + ")";
                                break;
                        }
                    }
                }
                str = str2;
            }
            return str;
        } catch (Exception e2) {
            if (an.a(e2)) {
                return str2;
            }
            ThrowableExtension.printStackTrace(e2);
            return str2;
        }
    }

    public static String g(Context context) {
        String b2 = ap.b(context, "ro.miui.ui.version.name");
        if (!ap.a(b2) && !b2.equals("fail")) {
            return "XiaoMi/MIUI/" + b2;
        }
        String b3 = ap.b(context, "ro.build.version.emui");
        if (!ap.a(b3) && !b3.equals("fail")) {
            return "HuaWei/EMOTION/" + b3;
        }
        String b4 = ap.b(context, "ro.lenovo.series");
        if (ap.a(b4) || b4.equals("fail")) {
            String b5 = ap.b(context, "ro.build.nubia.rom.name");
            if (!ap.a(b5) && !b5.equals("fail")) {
                return "Zte/NUBIA/" + b5 + "_" + ap.b(context, "ro.build.nubia.rom.code");
            }
            String b6 = ap.b(context, "ro.meizu.product.model");
            if (!ap.a(b6) && !b6.equals("fail")) {
                return "Meizu/FLYME/" + ap.b(context, "ro.build.display.id");
            }
            String b7 = ap.b(context, "ro.build.version.opporom");
            if (!ap.a(b7) && !b7.equals("fail")) {
                return "Oppo/COLOROS/" + b7;
            }
            String b8 = ap.b(context, "ro.vivo.os.build.display.id");
            if (!ap.a(b8) && !b8.equals("fail")) {
                return "vivo/FUNTOUCH/" + b8;
            }
            String b9 = ap.b(context, "ro.aa.romver");
            if (!ap.a(b9) && !b9.equals("fail")) {
                return "htc/" + b9 + "/" + ap.b(context, "ro.build.description");
            }
            String b10 = ap.b(context, "ro.lewa.version");
            if (!ap.a(b10) && !b10.equals("fail")) {
                return "tcl/" + b10 + "/" + ap.b(context, "ro.build.display.id");
            }
            String b11 = ap.b(context, "ro.gn.gnromvernumber");
            if (!ap.a(b11) && !b11.equals("fail")) {
                return "amigo/" + b11 + "/" + ap.b(context, "ro.build.display.id");
            }
            String b12 = ap.b(context, "ro.build.tyd.kbstyle_version");
            if (ap.a(b12) || b12.equals("fail")) {
                return ap.b(context, "ro.build.fingerprint") + "/" + ap.b(context, "ro.build.rom.id");
            }
            return "dido/" + b12;
        }
        return "Lenovo/VIBE/" + ap.b(context, "ro.build.version.incremental");
    }

    public static String h(Context context) {
        return ap.b(context, "ro.board.platform");
    }

    public static boolean n() {
        boolean z;
        boolean z2;
        String[] strArr = a;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else if (new File(strArr[i]).exists()) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (Build.TAGS == null || !Build.TAGS.contains("test-keys")) {
            z2 = false;
        } else {
            z2 = true;
        }
        return z2 || z;
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r0v4, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r1v16 */
    /* JADX WARNING: type inference failed for: r1v19 */
    /* JADX WARNING: type inference failed for: r1v20, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r0v8, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v22, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r1v23, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r1v24, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r1v25 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* JADX WARNING: type inference failed for: r2v19 */
    /* JADX WARNING: type inference failed for: r2v20 */
    /* JADX WARNING: type inference failed for: r1v26 */
    /* JADX WARNING: type inference failed for: r1v27 */
    /* JADX WARNING: type inference failed for: r1v28 */
    /* JADX WARNING: type inference failed for: r1v29 */
    /* JADX WARNING: type inference failed for: r1v30 */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00b0, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00b1, code lost:
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00b3, code lost:
        r0 = th;
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00b8, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00b9, code lost:
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00bb, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00bc, code lost:
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00be, code lost:
        r1 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00c4, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v8
      assigns: []
      uses: []
      mth insns count: 85
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0099 A[SYNTHETIC, Splitter:B:42:0x0099] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00a7 A[SYNTHETIC, Splitter:B:49:0x00a7] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00b3 A[ExcHandler: all (th java.lang.Throwable), PHI: r2 
      PHI: (r2v6 ?) = (r2v18 ?), (r2v20 ?) binds: [B:12:0x0031, B:23:0x005f] A[DONT_GENERATE, DONT_INLINE], Splitter:B:12:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00be A[ExcHandler: Throwable (th java.lang.Throwable), PHI: r1 
      PHI: (r1v8 ?) = (r1v26 ?), (r1v27 ?), (r1v28 ?), (r1v29 ?), (r1v30 ?) binds: [B:31:0x0086, B:32:?, B:27:0x007d, B:16:0x004f, B:5:0x0021] A[DONT_GENERATE, DONT_INLINE], Splitter:B:5:0x0021] */
    /* JADX WARNING: Unknown variable types count: 10 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String o() {
        /*
            r0 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
            r3.<init>()     // Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
            java.lang.String r2 = "/sys/block/mmcblk0/device/type"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
            if (r1 == 0) goto L_0x00c8
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
            java.lang.String r4 = "/sys/block/mmcblk0/device/type"
            r2.<init>(r4)     // Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x00be, all -> 0x00b0 }
            if (r2 == 0) goto L_0x002a
            r3.append(r2)     // Catch:{ Throwable -> 0x00be, all -> 0x00b0 }
        L_0x002a:
            r1.close()     // Catch:{ Throwable -> 0x00be, all -> 0x00b0 }
            r2 = r1
        L_0x002e:
            java.lang.String r1 = ","
            r3.append(r1)     // Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
            java.lang.String r4 = "/sys/block/mmcblk0/device/name"
            r1.<init>(r4)     // Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
            if (r1 == 0) goto L_0x005c
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
            java.lang.String r5 = "/sys/block/mmcblk0/device/name"
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x00be, all -> 0x00b5 }
            if (r2 == 0) goto L_0x0058
            r3.append(r2)     // Catch:{ Throwable -> 0x00be, all -> 0x00b5 }
        L_0x0058:
            r1.close()     // Catch:{ Throwable -> 0x00be, all -> 0x00b5 }
            r2 = r1
        L_0x005c:
            java.lang.String r1 = ","
            r3.append(r1)     // Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
            java.lang.String r4 = "/sys/block/mmcblk0/device/cid"
            r1.<init>(r4)     // Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
            if (r1 == 0) goto L_0x00c6
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
            java.lang.String r5 = "/sys/block/mmcblk0/device/cid"
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x00be, all -> 0x00b8 }
            if (r2 == 0) goto L_0x0086
            r3.append(r2)     // Catch:{ Throwable -> 0x00be, all -> 0x00b8 }
        L_0x0086:
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x00be, all -> 0x00bb }
            if (r1 == 0) goto L_0x008f
            r1.close()     // Catch:{ IOException -> 0x0090 }
        L_0x008f:
            return r0
        L_0x0090:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x008f
        L_0x0095:
            r1 = move-exception
            r1 = r0
        L_0x0097:
            if (r1 == 0) goto L_0x008f
            r1.close()     // Catch:{ IOException -> 0x009d }
            goto L_0x008f
        L_0x009d:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x008f
        L_0x00a2:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x00a5:
            if (r2 == 0) goto L_0x00aa
            r2.close()     // Catch:{ IOException -> 0x00ab }
        L_0x00aa:
            throw r0
        L_0x00ab:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x00aa
        L_0x00b0:
            r0 = move-exception
            r2 = r1
            goto L_0x00a5
        L_0x00b3:
            r0 = move-exception
            goto L_0x00a5
        L_0x00b5:
            r0 = move-exception
            r2 = r1
            goto L_0x00a5
        L_0x00b8:
            r0 = move-exception
            r2 = r1
            goto L_0x00a5
        L_0x00bb:
            r0 = move-exception
            r2 = r1
            goto L_0x00a5
        L_0x00be:
            r2 = move-exception
            goto L_0x0097
        L_0x00c0:
            r1 = move-exception
            r1 = r2
            goto L_0x0097
        L_0x00c3:
            r1 = move-exception
            r1 = r2
            goto L_0x0097
        L_0x00c6:
            r1 = r2
            goto L_0x0086
        L_0x00c8:
            r2 = r0
            goto L_0x002e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.o():java.lang.String");
    }

    public static String i(Context context) {
        StringBuilder sb = new StringBuilder();
        String b2 = ap.b(context, "ro.genymotion.version");
        if (b2 != null) {
            sb.append("ro.genymotion.version");
            sb.append("|");
            sb.append(b2);
            sb.append("\n");
        }
        String b3 = ap.b(context, "androVM.vbox_dpi");
        if (b3 != null) {
            sb.append("androVM.vbox_dpi");
            sb.append("|");
            sb.append(b3);
            sb.append("\n");
        }
        String b4 = ap.b(context, "qemu.sf.fake_camera");
        if (b4 != null) {
            sb.append("qemu.sf.fake_camera");
            sb.append("|");
            sb.append(b4);
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a8 A[SYNTHETIC, Splitter:B:33:0x00a8] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b9 A[SYNTHETIC, Splitter:B:41:0x00b9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String j(android.content.Context r5) {
        /*
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r0 = d
            if (r0 != 0) goto L_0x0012
            java.lang.String r0 = "ro.secure"
            java.lang.String r0 = com.tencent.bugly.proguard.ap.b(r5, r0)
            d = r0
        L_0x0012:
            java.lang.String r0 = d
            if (r0 == 0) goto L_0x002d
            java.lang.String r0 = "ro.secure"
            r3.append(r0)
            java.lang.String r0 = "|"
            r3.append(r0)
            java.lang.String r0 = d
            r3.append(r0)
            java.lang.String r0 = "\n"
            r3.append(r0)
        L_0x002d:
            java.lang.String r0 = e
            if (r0 != 0) goto L_0x003a
            java.lang.String r0 = "ro.debuggable"
            java.lang.String r0 = com.tencent.bugly.proguard.ap.b(r5, r0)
            e = r0
        L_0x003a:
            java.lang.String r0 = e
            if (r0 == 0) goto L_0x0055
            java.lang.String r0 = "ro.debuggable"
            r3.append(r0)
            java.lang.String r0 = "|"
            r3.append(r0)
            java.lang.String r0 = e
            r3.append(r0)
            java.lang.String r0 = "\n"
            r3.append(r0)
        L_0x0055:
            r2 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00a1, all -> 0x00b5 }
            java.io.FileReader r0 = new java.io.FileReader     // Catch:{ Throwable -> 0x00a1, all -> 0x00b5 }
            java.lang.String r4 = "/proc/self/status"
            r0.<init>(r4)     // Catch:{ Throwable -> 0x00a1, all -> 0x00b5 }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x00a1, all -> 0x00b5 }
        L_0x0063:
            java.lang.String r0 = r1.readLine()     // Catch:{ Throwable -> 0x00c4 }
            if (r0 == 0) goto L_0x0072
            java.lang.String r2 = "TracerPid:"
            boolean r2 = r0.startsWith(r2)     // Catch:{ Throwable -> 0x00c4 }
            if (r2 == 0) goto L_0x0063
        L_0x0072:
            if (r0 == 0) goto L_0x0092
            java.lang.String r2 = "TracerPid:"
            int r2 = r2.length()     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r0 = r0.substring(r2)     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r2 = "tracer_pid"
            r3.append(r2)     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r2 = "|"
            r3.append(r2)     // Catch:{ Throwable -> 0x00c4 }
            r3.append(r0)     // Catch:{ Throwable -> 0x00c4 }
        L_0x0092:
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x00c4 }
            if (r1 == 0) goto L_0x009b
            r1.close()     // Catch:{ IOException -> 0x009c }
        L_0x009b:
            return r0
        L_0x009c:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x009b
        L_0x00a1:
            r0 = move-exception
            r1 = r2
        L_0x00a3:
            com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x00c2 }
            if (r1 == 0) goto L_0x00ab
            r1.close()     // Catch:{ IOException -> 0x00b0 }
        L_0x00ab:
            java.lang.String r0 = r3.toString()
            goto L_0x009b
        L_0x00b0:
            r0 = move-exception
            com.tencent.bugly.proguard.an.a(r0)
            goto L_0x00ab
        L_0x00b5:
            r0 = move-exception
            r1 = r2
        L_0x00b7:
            if (r1 == 0) goto L_0x00bc
            r1.close()     // Catch:{ IOException -> 0x00bd }
        L_0x00bc:
            throw r0
        L_0x00bd:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x00bc
        L_0x00c2:
            r0 = move-exception
            goto L_0x00b7
        L_0x00c4:
            r0 = move-exception
            goto L_0x00a3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.j(android.content.Context):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00bd, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c4, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c5, code lost:
        com.tencent.bugly.proguard.an.a(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c9, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d0, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d1, code lost:
        com.tencent.bugly.proguard.an.a(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00d5, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00d6, code lost:
        r5 = r1;
        r1 = r0;
        r0 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00df, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00e0, code lost:
        r5 = r1;
        r1 = r0;
        r0 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00ea, code lost:
        r0 = r1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00c0 A[SYNTHETIC, Splitter:B:42:0x00c0] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00c9 A[ExcHandler: all (th java.lang.Throwable), PHI: r1 
      PHI: (r1v3 java.io.BufferedReader) = (r1v0 java.io.BufferedReader), (r1v5 java.io.BufferedReader), (r1v6 java.io.BufferedReader) binds: [B:1:0x0006, B:12:0x003d, B:23:0x0077] A[DONT_GENERATE, DONT_INLINE], Splitter:B:1:0x0006] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00cc A[SYNTHETIC, Splitter:B:48:0x00cc] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e4 A[ExcHandler: Throwable (th java.lang.Throwable), PHI: r0 
      PHI: (r0v9 java.io.BufferedReader) = (r0v23 java.io.BufferedReader), (r0v25 java.io.BufferedReader), (r0v27 java.io.BufferedReader) binds: [B:27:0x0095, B:16:0x005b, B:5:0x0021] A[DONT_GENERATE, DONT_INLINE], Splitter:B:5:0x0021] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String k(android.content.Context r6) {
        /*
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r1 = 0
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
            java.lang.String r3 = "/sys/class/power_supply/ac/online"
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
            boolean r0 = r0.exists()     // Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
            if (r0 == 0) goto L_0x003a
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
            java.lang.String r4 = "/sys/class/power_supply/ac/online"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
            java.lang.String r1 = r0.readLine()     // Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
            if (r1 == 0) goto L_0x0036
            java.lang.String r3 = "ac_online"
            r2.append(r3)     // Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
            java.lang.String r3 = "|"
            r2.append(r3)     // Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
            r2.append(r1)     // Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        L_0x0036:
            r0.close()     // Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
            r1 = r0
        L_0x003a:
            java.lang.String r0 = "\n"
            r2.append(r0)     // Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
            java.lang.String r3 = "/sys/class/power_supply/usb/online"
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
            boolean r0 = r0.exists()     // Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
            if (r0 == 0) goto L_0x0074
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
            java.lang.String r4 = "/sys/class/power_supply/usb/online"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
            java.lang.String r1 = r0.readLine()     // Catch:{ Throwable -> 0x00e4, all -> 0x00da }
            if (r1 == 0) goto L_0x0070
            java.lang.String r3 = "usb_online"
            r2.append(r3)     // Catch:{ Throwable -> 0x00e4, all -> 0x00da }
            java.lang.String r3 = "|"
            r2.append(r3)     // Catch:{ Throwable -> 0x00e4, all -> 0x00da }
            r2.append(r1)     // Catch:{ Throwable -> 0x00e4, all -> 0x00da }
        L_0x0070:
            r0.close()     // Catch:{ Throwable -> 0x00e4, all -> 0x00da }
            r1 = r0
        L_0x0074:
            java.lang.String r0 = "\n"
            r2.append(r0)     // Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
            java.lang.String r3 = "/sys/class/power_supply/battery/capacity"
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
            boolean r0 = r0.exists()     // Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
            if (r0 == 0) goto L_0x00ec
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
            java.lang.String r4 = "/sys/class/power_supply/battery/capacity"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
            java.lang.String r1 = r0.readLine()     // Catch:{ Throwable -> 0x00e4, all -> 0x00df }
            if (r1 == 0) goto L_0x00aa
            java.lang.String r3 = "battery_capacity"
            r2.append(r3)     // Catch:{ Throwable -> 0x00e4, all -> 0x00df }
            java.lang.String r3 = "|"
            r2.append(r3)     // Catch:{ Throwable -> 0x00e4, all -> 0x00df }
            r2.append(r1)     // Catch:{ Throwable -> 0x00e4, all -> 0x00df }
        L_0x00aa:
            r0.close()     // Catch:{ Throwable -> 0x00e4, all -> 0x00df }
        L_0x00ad:
            if (r0 == 0) goto L_0x00b2
            r0.close()     // Catch:{ IOException -> 0x00b7 }
        L_0x00b2:
            java.lang.String r0 = r2.toString()
            return r0
        L_0x00b7:
            r0 = move-exception
            com.tencent.bugly.proguard.an.a(r0)
            goto L_0x00b2
        L_0x00bc:
            r0 = move-exception
            r0 = r1
        L_0x00be:
            if (r0 == 0) goto L_0x00b2
            r0.close()     // Catch:{ IOException -> 0x00c4 }
            goto L_0x00b2
        L_0x00c4:
            r0 = move-exception
            com.tencent.bugly.proguard.an.a(r0)
            goto L_0x00b2
        L_0x00c9:
            r0 = move-exception
        L_0x00ca:
            if (r1 == 0) goto L_0x00cf
            r1.close()     // Catch:{ IOException -> 0x00d0 }
        L_0x00cf:
            throw r0
        L_0x00d0:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x00cf
        L_0x00d5:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x00ca
        L_0x00da:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x00ca
        L_0x00df:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x00ca
        L_0x00e4:
            r1 = move-exception
            goto L_0x00be
        L_0x00e6:
            r0 = move-exception
            r0 = r1
            goto L_0x00be
        L_0x00e9:
            r0 = move-exception
            r0 = r1
            goto L_0x00be
        L_0x00ec:
            r0 = r1
            goto L_0x00ad
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.k(android.content.Context):java.lang.String");
    }

    public static String l(Context context) {
        StringBuilder sb = new StringBuilder();
        String b2 = ap.b(context, "gsm.sim.state");
        if (b2 != null) {
            sb.append("gsm.sim.state");
            sb.append("|");
            sb.append(b2);
        }
        sb.append("\n");
        String b3 = ap.b(context, "gsm.sim.state2");
        if (b3 != null) {
            sb.append("gsm.sim.state2");
            sb.append("|");
            sb.append(b3);
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0046 A[SYNTHETIC, Splitter:B:21:0x0046] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0053 A[SYNTHETIC, Splitter:B:28:0x0053] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long m(android.content.Context r8) {
        /*
            r0 = 0
            r2 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0039, all -> 0x004f }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x0039, all -> 0x004f }
            java.lang.String r4 = "/proc/uptime"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0039, all -> 0x004f }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x0039, all -> 0x004f }
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x005e }
            if (r2 == 0) goto L_0x002d
            java.lang.String r3 = " "
            java.lang.String[] r2 = r2.split(r3)     // Catch:{ Throwable -> 0x005e }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Throwable -> 0x005e }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x005e }
            r6 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r6
            float r3 = (float) r4     // Catch:{ Throwable -> 0x005e }
            float r0 = java.lang.Float.parseFloat(r2)     // Catch:{ Throwable -> 0x005e }
            float r0 = r3 - r0
        L_0x002d:
            if (r1 == 0) goto L_0x0032
            r1.close()     // Catch:{ IOException -> 0x0034 }
        L_0x0032:
            long r0 = (long) r0
            return r0
        L_0x0034:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x0032
        L_0x0039:
            r1 = move-exception
            r1 = r2
        L_0x003b:
            java.lang.String r2 = "Failed to get boot time of device."
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x005c }
            com.tencent.bugly.proguard.an.a(r2, r3)     // Catch:{ all -> 0x005c }
            if (r1 == 0) goto L_0x0032
            r1.close()     // Catch:{ IOException -> 0x004a }
            goto L_0x0032
        L_0x004a:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x0032
        L_0x004f:
            r0 = move-exception
            r1 = r2
        L_0x0051:
            if (r1 == 0) goto L_0x0056
            r1.close()     // Catch:{ IOException -> 0x0057 }
        L_0x0056:
            throw r0
        L_0x0057:
            r1 = move-exception
            com.tencent.bugly.proguard.an.a(r1)
            goto L_0x0056
        L_0x005c:
            r0 = move-exception
            goto L_0x0051
        L_0x005e:
            r2 = move-exception
            goto L_0x003b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.m(android.content.Context):long");
    }

    public static boolean n(Context context) {
        return (o(context) == null && p() == null) ? false : true;
    }

    public static String o(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < b.length; i++) {
            try {
                packageManager.getPackageInfo(b[i], 1);
                arrayList.add(Integer.valueOf(i));
            } catch (NameNotFoundException e2) {
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList.toString();
    }

    public static String p() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < c.length; i++) {
            if (0 == i) {
                if (!new File(c[i]).exists()) {
                    arrayList.add(Integer.valueOf(i));
                }
            } else if (new File(c[i]).exists()) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList.toString();
    }

    public static boolean p(Context context) {
        return (((q(context) | r()) | s()) | q()) > 0;
    }

    public static int q() {
        try {
            Method method = Class.forName("android.app.ActivityManagerNative").getMethod("getDefault", new Class[0]);
            method.setAccessible(true);
            if (method.invoke(null, new Object[0]).getClass().getName().startsWith("$Proxy")) {
                return 256;
            }
            return 0;
        } catch (Exception e2) {
            return 256;
        }
    }

    public static int q(Context context) {
        int i = 0;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getInstallerPackageName("de.robv.android.xposed.installer");
            i = 1;
        } catch (Exception e2) {
        }
        try {
            packageManager.getInstallerPackageName("com.saurik.substrate");
            return i | 2;
        } catch (Exception e3) {
            return i;
        }
    }

    public static int r() {
        StackTraceElement[] stackTrace;
        int i = 0;
        try {
            throw new Exception("detect hook");
        } catch (Exception e2) {
            int i2 = 0;
            for (StackTraceElement stackTraceElement : e2.getStackTrace()) {
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("main")) {
                    i2 |= 4;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    i2 |= 8;
                }
                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") && stackTraceElement.getMethodName().equals("invoked")) {
                    i2 |= 16;
                }
                if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit")) {
                    i++;
                    if (i == 2) {
                        i2 |= 32;
                    }
                }
            }
            return i2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x006a A[SYNTHETIC, Splitter:B:16:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b9 A[SYNTHETIC, Splitter:B:43:0x00b9] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00cb A[SYNTHETIC, Splitter:B:52:0x00cb] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00d8 A[SYNTHETIC, Splitter:B:59:0x00d8] */
    /* JADX WARNING: Removed duplicated region for block: B:86:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:90:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:92:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:40:0x00b4=Splitter:B:40:0x00b4, B:49:0x00c6=Splitter:B:49:0x00c6} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int s() {
        /*
            r3 = 0
            r2 = 0
            java.util.HashSet r1 = new java.util.HashSet     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            r1.<init>()     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            r7.<init>()     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            java.lang.String r8 = "/proc/"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            int r8 = android.os.Process.myPid()     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            java.lang.String r8 = "/maps"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            java.lang.String r7 = r7.toString()     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            r6.<init>(r7)     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            java.lang.String r7 = "utf-8"
            r5.<init>(r6, r7)     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
            r4.<init>(r5)     // Catch:{ UnsupportedEncodingException -> 0x00f2, FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
        L_0x0038:
            java.lang.String r2 = r4.readLine()     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
            if (r2 == 0) goto L_0x006e
            java.lang.String r5 = ".so"
            boolean r5 = r2.endsWith(r5)     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
            if (r5 != 0) goto L_0x0050
            java.lang.String r5 = ".jar"
            boolean r5 = r2.endsWith(r5)     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
            if (r5 == 0) goto L_0x0038
        L_0x0050:
            java.lang.String r5 = " "
            int r5 = r2.lastIndexOf(r5)     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
            int r5 = r5 + 1
            java.lang.String r2 = r2.substring(r5)     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
            r1.add(r2)     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
            goto L_0x0038
        L_0x0061:
            r1 = move-exception
            r2 = r1
            r1 = r3
            r3 = r4
        L_0x0065:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)     // Catch:{ all -> 0x00e3 }
            if (r3 == 0) goto L_0x006d
            r3.close()     // Catch:{ IOException -> 0x00ab }
        L_0x006d:
            return r1
        L_0x006e:
            java.util.Iterator r5 = r1.iterator()     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
        L_0x0072:
            boolean r1 = r5.hasNext()     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
            if (r1 == 0) goto L_0x009e
            java.lang.Object r2 = r5.next()     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
            r0 = r2
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
            r1 = r0
            java.lang.String r1 = r1.toLowerCase()     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
            java.lang.String r6 = "xposed"
            boolean r1 = r1.contains(r6)     // Catch:{ UnsupportedEncodingException -> 0x0061, FileNotFoundException -> 0x00ec, IOException -> 0x00e6 }
            if (r1 == 0) goto L_0x0100
            r1 = r3 | 64
        L_0x008f:
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ UnsupportedEncodingException -> 0x00f9, FileNotFoundException -> 0x00f0, IOException -> 0x00ea }
            java.lang.String r3 = "com.saurik.substrate"
            boolean r2 = r2.contains(r3)     // Catch:{ UnsupportedEncodingException -> 0x00f9, FileNotFoundException -> 0x00f0, IOException -> 0x00ea }
            if (r2 == 0) goto L_0x009c
            r1 = r1 | 128(0x80, float:1.794E-43)
        L_0x009c:
            r3 = r1
            goto L_0x0072
        L_0x009e:
            if (r4 == 0) goto L_0x00fd
            r4.close()     // Catch:{ IOException -> 0x00a5 }
            r1 = r3
            goto L_0x006d
        L_0x00a5:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            r1 = r3
            goto L_0x006d
        L_0x00ab:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x006d
        L_0x00b0:
            r1 = move-exception
            r4 = r2
            r2 = r1
            r1 = r3
        L_0x00b4:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)     // Catch:{ all -> 0x00e1 }
            if (r4 == 0) goto L_0x006d
            r4.close()     // Catch:{ IOException -> 0x00bd }
            goto L_0x006d
        L_0x00bd:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x006d
        L_0x00c2:
            r1 = move-exception
            r4 = r2
            r2 = r1
            r1 = r3
        L_0x00c6:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)     // Catch:{ all -> 0x00e1 }
            if (r4 == 0) goto L_0x006d
            r4.close()     // Catch:{ IOException -> 0x00cf }
            goto L_0x006d
        L_0x00cf:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x006d
        L_0x00d4:
            r1 = move-exception
            r4 = r2
        L_0x00d6:
            if (r4 == 0) goto L_0x00db
            r4.close()     // Catch:{ IOException -> 0x00dc }
        L_0x00db:
            throw r1
        L_0x00dc:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x00db
        L_0x00e1:
            r1 = move-exception
            goto L_0x00d6
        L_0x00e3:
            r1 = move-exception
            r4 = r3
            goto L_0x00d6
        L_0x00e6:
            r1 = move-exception
            r2 = r1
            r1 = r3
            goto L_0x00c6
        L_0x00ea:
            r2 = move-exception
            goto L_0x00c6
        L_0x00ec:
            r1 = move-exception
            r2 = r1
            r1 = r3
            goto L_0x00b4
        L_0x00f0:
            r2 = move-exception
            goto L_0x00b4
        L_0x00f2:
            r1 = move-exception
            r9 = r1
            r1 = r3
            r3 = r2
            r2 = r9
            goto L_0x0065
        L_0x00f9:
            r2 = move-exception
            r3 = r4
            goto L_0x0065
        L_0x00fd:
            r1 = r3
            goto L_0x006d
        L_0x0100:
            r1 = r3
            goto L_0x008f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.s():int");
    }
}
