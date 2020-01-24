package com.tencent.stat.common;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alibaba.android.arouter.utils.Consts;
import com.google.common.primitives.UnsignedBytes;
import com.iwown.data_link.consts.UserConst;
import com.tencent.stat.StatConfig;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import com.tencent.wxop.stat.common.StatConstants;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.http.HttpHost;
import org.json.JSONObject;

public class k {
    private static String a = null;
    private static String b = null;
    private static String c = null;
    private static String d = null;
    private static Random e = null;
    /* access modifiers changed from: private */
    public static StatLogger f = null;
    private static String g = null;
    private static l h = null;
    private static n i = null;
    private static String j = "__MTA_FIRST_ACTIVATE__";
    private static int k = -1;

    public static String A(Context context) {
        try {
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            if (sensorManager != null) {
                List sensorList = sensorManager.getSensorList(-1);
                if (sensorList != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i2 = 0; i2 < sensorList.size(); i2++) {
                        sb.append(((Sensor) sensorList.get(i2)).getType());
                        if (i2 != sensorList.size() - 1) {
                            sb.append(",");
                        }
                    }
                    return sb.toString();
                }
            }
        } catch (Throwable th) {
            f.e((Object) th);
        }
        return "";
    }

    public static WifiInfo B(Context context) {
        if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager != null) {
                return wifiManager.getConnectionInfo();
            }
        }
        return null;
    }

    public static String C(Context context) {
        try {
            WifiInfo B = B(context);
            if (B != null) {
                return B.getBSSID();
            }
        } catch (Throwable th) {
            f.e((Object) th);
        }
        return null;
    }

    public static String D(Context context) {
        try {
            WifiInfo B = B(context);
            if (B != null) {
                return B.getSSID();
            }
        } catch (Throwable th) {
            f.e((Object) th);
        }
        return null;
    }

    public static synchronized int E(Context context) {
        int i2;
        synchronized (k.class) {
            if (k != -1) {
                i2 = k;
            } else {
                F(context);
                i2 = k;
            }
        }
        return i2;
    }

    public static void F(Context context) {
        k = p.a(context, j, 1);
        f.e((Object) Integer.valueOf(k));
        if (k == 1) {
            p.b(context, j, 0);
        }
    }

    private static long G(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME);
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static int a() {
        return h().nextInt(Integer.MAX_VALUE);
    }

    public static Long a(String str, String str2, int i2, int i3, Long l) {
        if (str == null || str2 == null) {
            return l;
        }
        if (str2.equalsIgnoreCase(Consts.DOT) || str2.equalsIgnoreCase("|")) {
            str2 = "\\" + str2;
        }
        String[] split = str.split(str2);
        if (split.length != i3) {
            return l;
        }
        try {
            Long valueOf = Long.valueOf(0);
            int i4 = 0;
            while (i4 < split.length) {
                Long valueOf2 = Long.valueOf(((long) i2) * (valueOf.longValue() + Long.valueOf(split[i4]).longValue()));
                i4++;
                valueOf = valueOf2;
            }
            return valueOf;
        } catch (NumberFormatException e2) {
            return l;
        }
    }

    public static String a(long j2) {
        return new SimpleDateFormat("yyyyMMdd").format(new Date(j2));
    }

    public static String a(String str) {
        if (str == null) {
            return "0";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b2 : digest) {
                byte b3 = b2 & UnsignedBytes.MAX_VALUE;
                if (b3 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(b3));
            }
            return stringBuffer.toString();
        } catch (Throwable th) {
            return "0";
        }
    }

    public static HttpHost a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
                return null;
            }
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getTypeName() != null && activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                return null;
            }
            String extraInfo = activeNetworkInfo.getExtraInfo();
            if (extraInfo == null) {
                return null;
            }
            if (extraInfo.equals("cmwap") || extraInfo.equals("3gwap") || extraInfo.equals("uniwap")) {
                return new HttpHost("10.0.0.172", 80);
            }
            if (extraInfo.equals("ctwap")) {
                return new HttpHost("10.0.0.200", 80);
            }
            return null;
        } catch (Throwable th) {
            f.e((Object) th);
        }
    }

    public static void a(JSONObject jSONObject, String str, String str2) {
        if (str2 != null) {
            try {
                if (str2.length() > 0) {
                    jSONObject.put(str, str2);
                }
            } catch (Throwable th) {
                f.e((Object) th);
            }
        }
    }

    public static boolean a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable th) {
            f.e((Object) th);
            return false;
        }
    }

    public static byte[] a(byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
        byte[] bArr2 = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length * 2);
        while (true) {
            int read = gZIPInputStream.read(bArr2);
            if (read != -1) {
                byteArrayOutputStream.write(bArr2, 0, read);
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayInputStream.close();
                gZIPInputStream.close();
                byteArrayOutputStream.close();
                return byteArray;
            }
        }
    }

    public static long b(String str) {
        return a(str, Consts.DOT, 100, 3, Long.valueOf(0)).longValue();
    }

    public static synchronized StatLogger b() {
        StatLogger statLogger;
        synchronized (k.class) {
            if (f == null) {
                f = new StatLogger(StatConstants.LOG_TAG);
                f.setDebugEnable(false);
            }
            statLogger = f;
        }
        return statLogger;
    }

    public static synchronized String b(Context context) {
        String str;
        synchronized (k.class) {
            if (a == null || a.trim().length() == 0) {
                a = l(context);
                if (a == null || a.trim().length() == 0) {
                    a = Integer.toString(h().nextInt(Integer.MAX_VALUE));
                }
                str = a;
            } else {
                str = a;
            }
        }
        return str;
    }

    public static String b(Context context, String str) {
        if (!StatConfig.isEnableConcurrentProcess()) {
            return str;
        }
        if (g == null) {
            g = u(context);
        }
        return g != null ? str + "_" + g : str;
    }

    public static long c() {
        try {
            Calendar instance = Calendar.getInstance();
            instance.set(11, 0);
            instance.set(12, 0);
            instance.set(13, 0);
            instance.set(14, 0);
            return instance.getTimeInMillis() + 86400000;
        } catch (Throwable th) {
            f.e((Object) th);
            return System.currentTimeMillis() + 86400000;
        }
    }

    public static synchronized String c(Context context) {
        String str;
        synchronized (k.class) {
            if (c == null || "" == c) {
                c = f(context);
            }
            str = c;
        }
        return str;
    }

    public static String c(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(g.b(e.a(str.getBytes("UTF-8")), 0), "UTF-8");
        } catch (Throwable th) {
            f.e((Object) th);
            return str;
        }
    }

    public static int d() {
        return VERSION.SDK_INT;
    }

    public static DisplayMetrics d(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static String d(String str) {
        if (str == null) {
            return null;
        }
        if (VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(e.b(g.a(str.getBytes("UTF-8"), 0)), "UTF-8");
        } catch (Throwable th) {
            f.e((Object) th);
            return str;
        }
    }

    public static String e() {
        long f2 = f() / 1000000;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return String.valueOf((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / 1000000) + "/" + String.valueOf(f2);
    }

    public static boolean e(Context context) {
        try {
            if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
                    if (allNetworkInfo != null) {
                        for (int i2 = 0; i2 < allNetworkInfo.length; i2++) {
                            if (allNetworkInfo[i2].getTypeName().equalsIgnoreCase("WIFI") && allNetworkInfo[i2].isConnected()) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
            f.warn("can not get the permission of android.permission.ACCESS_WIFI_STATE");
            return false;
        } catch (Throwable th) {
            f.e((Object) th);
        }
    }

    public static long f() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
    }

    public static String f(Context context) {
        if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                return wifiManager == null ? "" : wifiManager.getConnectionInfo().getMacAddress();
            } catch (Exception e2) {
                f.e(e2);
                return "";
            }
        } else {
            f.e((Object) "Could not get permission of android.permission.ACCESS_WIFI_STATE");
            return "";
        }
    }

    public static boolean g(Context context) {
        try {
            if (!a(context, "android.permission.INTERNET") || !a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                f.warn("can not get the permisson of android.permission.INTERNET");
                return false;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI");
            }
            return false;
        } catch (Throwable th) {
            f.e((Object) th);
        }
    }

    private static synchronized Random h() {
        Random random;
        synchronized (k.class) {
            if (e == null) {
                e = new Random();
            }
            random = e;
        }
        return random;
    }

    public static boolean h(Context context) {
        try {
            if (!a(context, "android.permission.INTERNET") || !a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                f.warn("can not get the permisson of android.permission.INTERNET");
                return false;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                    return true;
                }
                f.w("Network error");
                return false;
            }
            return false;
        } catch (Throwable th) {
        }
    }

    private static long i() {
        long j2 = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            j2 = (long) (Integer.valueOf(bufferedReader.readLine().split("\\s+")[1]).intValue() * 1024);
            bufferedReader.close();
            return j2;
        } catch (IOException e2) {
            return j2;
        }
    }

    public static String i(Context context) {
        if (b != null) {
            return b;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("TA_APPKEY");
                if (string != null) {
                    b = string;
                    return string;
                }
                f.w("Could not read APPKEY meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            f.w("Could not read APPKEY meta-data from AndroidManifest.xml");
        }
        return null;
    }

    public static String j(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                Object obj = applicationInfo.metaData.get("InstallChannel");
                if (obj != null) {
                    return obj.toString();
                }
                f.w("Could not read InstallChannel meta-data from AndroidManifest.xml");
            }
        } catch (Throwable th) {
            f.e((Object) "Could not read InstallChannel meta-data from AndroidManifest.xml");
        }
        return null;
    }

    public static String k(Context context) {
        if (context == null) {
            return null;
        }
        return context.getClass().getName();
    }

    public static String l(Context context) {
        try {
            if (a(context, "android.permission.READ_PHONE_STATE")) {
                String str = "";
                if (o(context)) {
                    str = ((TelephonyManager) context.getSystemService(UserConst.PHONE)).getDeviceId();
                }
                if (str != null) {
                    return str;
                }
            } else {
                f.e((Object) "Could not get permission of android.permission.READ_PHONE_STATE");
            }
        } catch (Throwable th) {
            f.e((Object) th);
        }
        return null;
    }

    public static String m(Context context) {
        try {
            if (!a(context, "android.permission.READ_PHONE_STATE")) {
                f.e((Object) "Could not get permission of android.permission.READ_PHONE_STATE");
                return null;
            } else if (!o(context)) {
                return null;
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(UserConst.PHONE);
                return telephonyManager != null ? telephonyManager.getSimOperator() : null;
            }
        } catch (Throwable th) {
            f.e((Object) th);
            return null;
        }
    }

    public static String n(Context context) {
        String str = "";
        try {
            String str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return str2 == null ? "" : str2;
        } catch (Throwable th) {
            Throwable th2 = th;
            String str3 = str;
            f.e((Object) th2);
            return str3;
        }
    }

    public static boolean o(Context context) {
        return context.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0;
    }

    public static String p(Context context) {
        try {
            if (!a(context, "android.permission.INTERNET") || !a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                f.e((Object) "can not get the permission of android.permission.ACCESS_WIFI_STATE");
                return null;
            }
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                String typeName = activeNetworkInfo.getTypeName();
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (typeName != null) {
                    return typeName.equalsIgnoreCase("WIFI") ? "WIFI" : typeName.equalsIgnoreCase("MOBILE") ? extraInfo == null ? "MOBILE" : extraInfo : extraInfo == null ? typeName : extraInfo;
                }
            }
            return null;
        } catch (Throwable th) {
            f.e((Object) th);
        }
    }

    public static Integer q(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(UserConst.PHONE);
            if (telephonyManager != null) {
                return Integer.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static String r(Context context) {
        Object th;
        String str;
        String str2 = "";
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (str != null) {
                try {
                    if (str.length() != 0) {
                        return str;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    f.e(th);
                    return str;
                }
            }
            return "unknown";
        } catch (Throwable th3) {
            Object obj = th3;
            str = str2;
            th = obj;
            f.e(th);
            return str;
        }
    }

    public static int s(Context context) {
        try {
            if (o.a()) {
                return 1;
            }
        } catch (Throwable th) {
            f.e((Object) th);
        }
        return 0;
    }

    public static String t(Context context) {
        try {
            if (a(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                String externalStorageState = Environment.getExternalStorageState();
                if (externalStorageState == null || !externalStorageState.equals("mounted")) {
                    return null;
                }
                String path = Environment.getExternalStorageDirectory().getPath();
                if (path == null) {
                    return null;
                }
                StatFs statFs = new StatFs(path);
                long blockCount = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1000000;
                return String.valueOf((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / 1000000) + "/" + String.valueOf(blockCount);
            }
            f.warn("can not get the permission of android.permission.WRITE_EXTERNAL_STORAGE");
            return null;
        } catch (Throwable th) {
            f.e((Object) th);
            return null;
        }
    }

    static String u(Context context) {
        try {
            int myPid = Process.myPid();
            for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME)).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == myPid) {
                    return runningAppProcessInfo.processName;
                }
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static String v(Context context) {
        return b(context, StatConstants.a);
    }

    public static synchronized Integer w(Context context) {
        Integer valueOf;
        int i2 = 0;
        synchronized (k.class) {
            try {
                int a2 = p.a(context, "MTA_EVENT_INDEX", 0);
                if (a2 < 2147483646) {
                    i2 = a2;
                }
                p.b(context, "MTA_EVENT_INDEX", i2 + 1);
            } catch (Throwable th) {
                f.e((Object) th);
            }
            valueOf = Integer.valueOf(i2 + 1);
        }
        return valueOf;
    }

    public static String x(Context context) {
        return String.valueOf(G(context) / 1000000) + "/" + String.valueOf(i() / 1000000);
    }

    public static synchronized l y(Context context) {
        l lVar;
        synchronized (k.class) {
            if (h == null) {
                h = new l();
            }
            lVar = h;
        }
        return lVar;
    }

    public static JSONObject z(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            y(context);
            int b2 = l.b();
            if (b2 > 0) {
                jSONObject.put("fx", b2 / 1000000);
            }
            y(context);
            int c2 = l.c();
            if (c2 > 0) {
                jSONObject.put("fn", c2 / 1000000);
            }
            y(context);
            int a2 = l.a();
            if (a2 > 0) {
                jSONObject.put("n", a2);
            }
            y(context);
            String d2 = l.d();
            if (d2 != null && d2.length() == 0) {
                y(context);
                jSONObject.put("na", l.d());
            }
        } catch (Exception e2) {
            f.e(e2);
        }
        return jSONObject;
    }
}
