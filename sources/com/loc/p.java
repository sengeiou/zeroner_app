package com.loc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.google.common.primitives.UnsignedBytes;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.consts.UserConst;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: DeviceInfo */
public final class p {
    static String a = "";
    static String b = "";
    public static boolean c = false;
    static String d = "";
    static boolean e = false;
    static int f = -1;
    static String g = "";
    static String h = "";
    private static String i = null;
    private static boolean j = false;
    private static String k = "";
    private static String l = "";
    private static String m = "";
    private static String n = "";
    private static String o = "";
    private static String p = "";
    private static boolean q = false;
    private static long r;
    private static int s;
    private static String t;
    private static String u = "";

    static String A(Context context) {
        try {
            return D(context);
        } catch (Throwable th) {
            return "";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0092 A[SYNTHETIC, Splitter:B:32:0x0092] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0099 A[SYNTHETIC, Splitter:B:37:0x0099] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String B(android.content.Context r9) {
        /*
            r1 = 1
            r3 = 0
            r0 = 0
            java.lang.String r2 = "android.permission.READ_EXTERNAL_STORAGE"
            boolean r2 = com.loc.w.a(r9, r2)     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            if (r2 == 0) goto L_0x00a7
            java.lang.String r2 = "mounted"
            java.lang.String r4 = android.os.Environment.getExternalStorageState()     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            boolean r2 = r2.equals(r4)     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            if (r2 == 0) goto L_0x00a7
            java.io.File r2 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            r4.<init>()     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            java.lang.String r4 = "/.UTSystemConfig/Global/Alvin2.xml"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            r5.<init>(r2)     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            org.xmlpull.v1.XmlPullParser r6 = android.util.Xml.newPullParser()     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            int r4 = r6.getEventType()     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            r2.<init>(r5)     // Catch:{ Throwable -> 0x0096, all -> 0x008d }
            java.lang.String r0 = "utf-8"
            r6.setInput(r2, r0)     // Catch:{ Throwable -> 0x00af, all -> 0x00ad }
            r0 = r3
        L_0x004e:
            if (r1 == r4) goto L_0x00a6
            switch(r4) {
                case 0: goto L_0x0053;
                case 1: goto L_0x0053;
                case 2: goto L_0x0058;
                case 3: goto L_0x008b;
                case 4: goto L_0x007f;
                default: goto L_0x0053;
            }     // Catch:{ Throwable -> 0x00af, all -> 0x00ad }
        L_0x0053:
            int r4 = r6.next()     // Catch:{ Throwable -> 0x00af, all -> 0x00ad }
            goto L_0x004e
        L_0x0058:
            int r4 = r6.getAttributeCount()     // Catch:{ Throwable -> 0x00af, all -> 0x00ad }
            if (r4 <= 0) goto L_0x0053
            int r5 = r6.getAttributeCount()     // Catch:{ Throwable -> 0x00af, all -> 0x00ad }
            r4 = r3
        L_0x0063:
            if (r4 >= r5) goto L_0x0053
            java.lang.String r7 = r6.getAttributeValue(r4)     // Catch:{ Throwable -> 0x00af, all -> 0x00ad }
            java.lang.String r8 = "UTDID2"
            boolean r8 = r8.equals(r7)     // Catch:{ Throwable -> 0x00af, all -> 0x00ad }
            if (r8 != 0) goto L_0x007b
            java.lang.String r8 = "UTDID"
            boolean r7 = r8.equals(r7)     // Catch:{ Throwable -> 0x00af, all -> 0x00ad }
            if (r7 == 0) goto L_0x007c
        L_0x007b:
            r0 = r1
        L_0x007c:
            int r4 = r4 + 1
            goto L_0x0063
        L_0x007f:
            if (r0 == 0) goto L_0x0053
            java.lang.String r0 = r6.getText()     // Catch:{ Throwable -> 0x00af, all -> 0x00ad }
            if (r2 == 0) goto L_0x008a
            r2.close()     // Catch:{ Throwable -> 0x00a4 }
        L_0x008a:
            return r0
        L_0x008b:
            r0 = r3
            goto L_0x0053
        L_0x008d:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0090:
            if (r2 == 0) goto L_0x0095
            r2.close()     // Catch:{ Throwable -> 0x00a2 }
        L_0x0095:
            throw r0
        L_0x0096:
            r1 = move-exception
        L_0x0097:
            if (r0 == 0) goto L_0x009c
            r0.close()     // Catch:{ Throwable -> 0x00a0 }
        L_0x009c:
            java.lang.String r0 = ""
            goto L_0x008a
        L_0x00a0:
            r0 = move-exception
            goto L_0x009c
        L_0x00a2:
            r1 = move-exception
            goto L_0x0095
        L_0x00a4:
            r1 = move-exception
            goto L_0x008a
        L_0x00a6:
            r0 = r2
        L_0x00a7:
            if (r0 == 0) goto L_0x009c
            r0.close()     // Catch:{ Throwable -> 0x00a0 }
            goto L_0x009c
        L_0x00ad:
            r0 = move-exception
            goto L_0x0090
        L_0x00af:
            r0 = move-exception
            r0 = r2
            goto L_0x0097
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.p.B(android.content.Context):java.lang.String");
    }

    private static String C(Context context) throws InvocationTargetException, IllegalAccessException {
        if (VERSION.SDK_INT >= 29) {
            return "";
        }
        if (u != null && !"".equals(u)) {
            return u;
        }
        if (!b(context, w.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU="))) {
            return u;
        }
        TelephonyManager H = H(context);
        if (H == null) {
            return "";
        }
        Method a2 = w.a(H.getClass(), "UZ2V0U3Vic2NyaWJlcklk", (Class<?>[]) new Class[0]);
        if (a2 != null) {
            u = (String) a2.invoke(H, new Object[0]);
        }
        if (u == null) {
            u = "";
        }
        return u;
    }

    private static String D(Context context) {
        if (!b(context, w.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU="))) {
            return null;
        }
        TelephonyManager H = H(context);
        if (H == null) {
            return "";
        }
        String simOperatorName = H.getSimOperatorName();
        return TextUtils.isEmpty(simOperatorName) ? H.getNetworkOperatorName() : simOperatorName;
    }

    private static int E(Context context) {
        if (context == null || !b(context, w.c("AYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19ORVRXT1JLX1NUQVRF"))) {
            return -1;
        }
        ConnectivityManager F = F(context);
        if (F == null) {
            return -1;
        }
        NetworkInfo activeNetworkInfo = F.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.getType();
        }
        return -1;
    }

    private static ConnectivityManager F(Context context) {
        return (ConnectivityManager) context.getSystemService("connectivity");
    }

    private static int G(Context context) {
        if (!b(context, w.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU="))) {
            return -1;
        }
        TelephonyManager H = H(context);
        if (H != null) {
            return H.getNetworkType();
        }
        return -1;
    }

    private static TelephonyManager H(Context context) {
        return (TelephonyManager) context.getSystemService(UserConst.PHONE);
    }

    public static String a() {
        return i;
    }

    public static String a(Context context) {
        try {
            if (!TextUtils.isEmpty(d)) {
                return d;
            }
            v a2 = ae.a();
            if (ba.b(context, a2)) {
                Class a3 = ba.a(context, a2, w.c("WY29tLmFtYXAuYXBpLmFpdW5ldC5OZXRSZXVlc3RQYXJhbQ"));
                if (a3 != null) {
                    d = (String) a3.getMethod("getAdiuExtras", new Class[0]).invoke(a3, new Object[0]);
                }
                return d;
            }
            return "";
        } catch (Throwable th) {
        }
    }

    public static String a(Context context, String str) {
        int i2 = 0;
        if (VERSION.SDK_INT >= 29) {
            return "";
        }
        try {
            if (VERSION.SDK_INT < 21) {
                return "";
            }
            if (!TextUtils.isEmpty(g)) {
                return g;
            }
            TelephonyManager H = H(context);
            if (f == -1) {
                Method a2 = w.a(TelephonyManager.class, "UZ2V0UGhvbmVDb3VudA=", (Class<?>[]) new Class[0]);
                if (a2 != null) {
                    f = ((Integer) a2.invoke(H, new Object[0])).intValue();
                } else {
                    f = 0;
                }
            }
            Method a3 = w.a(TelephonyManager.class, "MZ2V0SW1laQ=", (Class<?>[]) new Class[]{Integer.TYPE});
            if (a3 == null) {
                f = 0;
                return "";
            }
            StringBuilder sb = new StringBuilder();
            while (i2 < f) {
                try {
                    sb.append((String) a3.invoke(H, new Object[]{Integer.valueOf(i2)})).append(str);
                    i2++;
                } catch (Throwable th) {
                }
            }
            String sb2 = sb.toString();
            if (sb2.length() == 0) {
                f = 0;
                return "";
            }
            String substring = sb2.substring(0, sb2.length() - 1);
            g = substring;
            return substring;
        } catch (Throwable th2) {
            return "";
        }
    }

    private static List<ScanResult> a(List<ScanResult> list) {
        int size = list.size();
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= size - 1) {
                return list;
            }
            int i4 = 1;
            while (true) {
                int i5 = i4;
                if (i5 >= size - i3) {
                    break;
                }
                if (((ScanResult) list.get(i5 - 1)).level > ((ScanResult) list.get(i5)).level) {
                    ScanResult scanResult = (ScanResult) list.get(i5 - 1);
                    list.set(i5 - 1, list.get(i5));
                    list.set(i5, scanResult);
                }
                i4 = i5 + 1;
            }
            i2 = i3 + 1;
        }
    }

    public static void a(String str) {
        i = str;
    }

    public static String b(final Context context) {
        try {
            if (!TextUtils.isEmpty(b)) {
                return b;
            }
            v a2 = ae.a();
            if (a2 == null) {
                return null;
            }
            if (ba.b(context, a2)) {
                final Class a3 = ba.a(context, a2, w.c("WY29tLmFtYXAuYXBpLmFpdW5ldC5OZXRSZXVlc3RQYXJhbQ"));
                if (a3 == null) {
                    return b;
                }
                String str = (String) a3.getMethod("getADIU", new Class[]{Context.class}).invoke(a3, new Object[]{context});
                if (!TextUtils.isEmpty(str)) {
                    b = str;
                    return str;
                } else if (!j) {
                    j = true;
                    aj.d().submit(new Runnable() {
                        public final void run() {
                            try {
                                Map map = (Map) a3.getMethod("getGetParams", new Class[0]).invoke(a3, new Object[0]);
                                if (map != null) {
                                    String str = (String) a3.getMethod("getPostParam", new Class[]{String.class, String.class, String.class, String.class}).invoke(a3, new Object[]{p.h(context), p.v(context), p.m(context), p.y(context)});
                                    if (!TextUtils.isEmpty(str)) {
                                        bm.a();
                                        byte[] a2 = bm.a(new bl(str.getBytes(), map));
                                        a3.getMethod("parseResult", new Class[]{Context.class, String.class}).invoke(a3, new Object[]{context, new String(a2)});
                                    }
                                }
                            } catch (Throwable th) {
                            }
                        }
                    });
                }
            }
            return "";
        } catch (Throwable th) {
        }
    }

    public static void b() {
        try {
            if (VERSION.SDK_INT > 14) {
                TrafficStats.class.getDeclaredMethod("setThreadStatsTag", new Class[]{Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(40964)});
            }
        } catch (Throwable th) {
        }
    }

    private static boolean b(Context context, String str) {
        return context != null && context.checkCallingOrSelfPermission(str) == 0;
    }

    public static long c() {
        long blockCount;
        long blockSize;
        if (r != 0) {
            return r;
        }
        try {
            StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
            StatFs statFs2 = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (VERSION.SDK_INT >= 18) {
                blockCount = (statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
                blockSize = (statFs2.getBlockSizeLong() * statFs2.getBlockCountLong()) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
            } else {
                blockCount = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
                blockSize = (((long) statFs2.getBlockSize()) * ((long) statFs2.getBlockCount())) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
            }
            r = blockSize + blockCount;
        } catch (Throwable th) {
        }
        return r;
    }

    public static String c(Context context) {
        try {
            return D(context);
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return "";
        }
    }

    public static String d() {
        if (!TextUtils.isEmpty(t)) {
            return t;
        }
        String property = System.getProperty("os.arch");
        t = property;
        return property;
    }

    public static String d(Context context) {
        if (VERSION.SDK_INT >= 29) {
            return "";
        }
        String str = "";
        String str2 = "";
        try {
            String y = y(context);
            return (y == null || y.length() < 5) ? str2 : y.substring(3, 5);
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return str;
        }
    }

    public static int e(Context context) {
        try {
            return G(context);
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return -1;
        }
    }

    private static String e() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] bArr = null;
                    if (VERSION.SDK_INT >= 9) {
                        bArr = networkInterface.getHardwareAddress();
                    }
                    if (bArr == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b2 : bArr) {
                        String upperCase = Integer.toHexString(b2 & UnsignedBytes.MAX_VALUE).toUpperCase();
                        if (upperCase.length() == 1) {
                            sb.append("0");
                        }
                        sb.append(upperCase).append(":");
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
        } catch (Exception e2) {
        }
        return "";
    }

    public static int f(Context context) {
        try {
            return E(context);
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return -1;
        }
    }

    public static String g(Context context) {
        try {
            return C(context);
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return "";
        }
    }

    public static String h(Context context) {
        if (VERSION.SDK_INT >= 29) {
            return "";
        }
        try {
            if (a != null && !"".equals(a)) {
                return a;
            }
            if (b(context, w.c("WYW5kcm9pZC5wZXJtaXNzaW9uLldSSVRFX1NFVFRJTkdT"))) {
                a = System.getString(context.getContentResolver(), "mqBRboGZkQPcAkyk");
            }
            if (a != null && !"".equals(a)) {
                return a;
            }
            try {
                a = B(context);
            } catch (Throwable th) {
            }
            return a == null ? "" : a;
        } catch (Throwable th2) {
        }
    }

    public static String i(Context context) {
        if (VERSION.SDK_INT >= 29) {
            return "";
        }
        if (!TextUtils.isEmpty(l)) {
            return l;
        }
        if (!b(context, w.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU="))) {
            return "";
        }
        try {
            if (VERSION.SDK_INT >= 26) {
                return (String) w.a(Build.class, "MZ2V0U2VyaWFs", (Class<?>[]) new Class[0]).invoke(Build.class, new Object[0]);
            }
            if (VERSION.SDK_INT >= 9) {
                l = Build.SERIAL;
            }
            return l == null ? "" : l;
        } catch (Throwable th) {
        }
    }

    public static String j(Context context) {
        if (!TextUtils.isEmpty(k)) {
            return k;
        }
        try {
            String string = Secure.getString(context.getContentResolver(), w.c(new String(ae.a(13))));
            k = string;
            return string == null ? "" : k;
        } catch (Throwable th) {
            return k;
        }
    }

    static String k(Context context) {
        String str;
        String str2 = "";
        if (context == null) {
            return str2;
        }
        try {
            if (!b(context, w.c("WYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19XSUZJX1NUQVRF"))) {
                return str2;
            }
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager == null) {
                return str2;
            }
            if (wifiManager.isWifiEnabled()) {
                str = wifiManager.getConnectionInfo().getBSSID();
                return str;
            }
            str = str2;
            return str;
        } catch (Throwable th) {
        }
    }

    static String l(Context context) {
        StringBuilder sb = new StringBuilder();
        if (context != null) {
            try {
                if (b(context, w.c("WYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19XSUZJX1NUQVRF"))) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    if (wifiManager == null) {
                        return "";
                    }
                    if (wifiManager.isWifiEnabled()) {
                        List scanResults = wifiManager.getScanResults();
                        if (scanResults == null || scanResults.size() == 0) {
                            return sb.toString();
                        }
                        List a2 = a(scanResults);
                        boolean z = true;
                        int i2 = 0;
                        while (i2 < a2.size() && i2 < 7) {
                            ScanResult scanResult = (ScanResult) a2.get(i2);
                            if (z) {
                                z = false;
                            } else {
                                sb.append(";");
                            }
                            sb.append(scanResult.BSSID);
                            i2++;
                        }
                    }
                    return sb.toString();
                }
            } catch (Throwable th) {
            }
        }
        return sb.toString();
    }

    public static String m(Context context) {
        try {
            if (m != null && !"".equals(m)) {
                return m;
            }
            if (!b(context, w.c("WYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19XSUZJX1NUQVRF"))) {
                return m;
            }
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager == null) {
                return "";
            }
            m = wifiManager.getConnectionInfo().getMacAddress();
            if (w.c("YMDI6MDA6MDA6MDA6MDA6MDA").equals(m) || w.c("YMDA6MDA6MDA6MDA6MDA6MDA").equals(m)) {
                m = e();
            }
            return m;
        } catch (Throwable th) {
        }
    }

    static String[] n(Context context) {
        try {
            if (!b(context, w.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU=")) || !b(context, w.c("EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19DT0FSU0VfTE9DQVRJT04="))) {
                return new String[]{"", ""};
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(UserConst.PHONE);
            if (telephonyManager == null) {
                return new String[]{"", ""};
            }
            CellLocation cellLocation = telephonyManager.getCellLocation();
            if (cellLocation instanceof GsmCellLocation) {
                GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                return new String[]{gsmCellLocation.getLac() + "||" + gsmCellLocation.getCid(), "gsm"};
            }
            if (cellLocation instanceof CdmaCellLocation) {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                int systemId = cdmaCellLocation.getSystemId();
                int networkId = cdmaCellLocation.getNetworkId();
                return new String[]{systemId + "||" + networkId + "||" + cdmaCellLocation.getBaseStationId(), "cdma"};
            }
            return new String[]{"", ""};
        } catch (Throwable th) {
        }
    }

    static String o(Context context) {
        String str = "";
        try {
            TelephonyManager H = H(context);
            if (H == null) {
                return "";
            }
            String networkOperator = H.getNetworkOperator();
            return (TextUtils.isEmpty(networkOperator) || networkOperator.length() < 3) ? str : networkOperator.substring(0, 3);
        } catch (Throwable th) {
            return str;
        }
    }

    static String p(Context context) {
        String str = "";
        try {
            TelephonyManager H = H(context);
            if (H == null) {
                return "";
            }
            String networkOperator = H.getNetworkOperator();
            return (TextUtils.isEmpty(networkOperator) || networkOperator.length() < 3) ? str : networkOperator.substring(3);
        } catch (Throwable th) {
            return str;
        }
    }

    public static int q(Context context) {
        try {
            return G(context);
        } catch (Throwable th) {
            return -1;
        }
    }

    public static int r(Context context) {
        try {
            return E(context);
        } catch (Throwable th) {
            return -1;
        }
    }

    public static NetworkInfo s(Context context) {
        if (!b(context, w.c("AYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19ORVRXT1JLX1NUQVRF"))) {
            return null;
        }
        ConnectivityManager F = F(context);
        if (F != null) {
            return F.getActiveNetworkInfo();
        }
        return null;
    }

    static String t(Context context) {
        try {
            NetworkInfo s2 = s(context);
            if (s2 == null) {
                return null;
            }
            return s2.getExtraInfo();
        } catch (Throwable th) {
            return null;
        }
    }

    static String u(Context context) {
        try {
            if (n != null && !"".equals(n)) {
                return n;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager == null) {
                return "";
            }
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int i2 = displayMetrics.widthPixels;
            int i3 = displayMetrics.heightPixels;
            n = i3 > i2 ? i2 + "*" + i3 : i3 + "*" + i2;
            return n;
        } catch (Throwable th) {
        }
    }

    public static String v(Context context) {
        try {
            if (VERSION.SDK_INT >= 29) {
                return "";
            }
            if (o != null && !"".equals(o)) {
                return o;
            }
            if (!b(context, w.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU="))) {
                return o;
            }
            TelephonyManager H = H(context);
            if (H == null) {
                return "";
            }
            Method a2 = w.a(H.getClass(), "QZ2V0RGV2aWNlSWQ", (Class<?>[]) new Class[0]);
            if (VERSION.SDK_INT >= 26) {
                a2 = w.a(H.getClass(), "QZ2V0SW1laQ==", (Class<?>[]) new Class[0]);
            }
            if (a2 != null) {
                o = (String) a2.invoke(H, new Object[0]);
            }
            if (o == null) {
                o = "";
            }
            return o;
        } catch (Throwable th) {
        }
    }

    public static String w(Context context) {
        return v(context) + "#" + b(context);
    }

    public static String x(Context context) {
        if (VERSION.SDK_INT >= 29) {
            return "";
        }
        try {
            if (p != null && !"".equals(p)) {
                return p;
            }
            if (!b(context, w.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU="))) {
                return p;
            }
            TelephonyManager H = H(context);
            if (H == null) {
                return "";
            }
            if (VERSION.SDK_INT >= 26) {
                Method a2 = w.a(H.getClass(), "QZ2V0TWVpZA==", (Class<?>[]) new Class[0]);
                if (a2 != null) {
                    p = (String) a2.invoke(H, new Object[0]);
                }
                if (p == null) {
                    p = "";
                }
            }
            return p;
        } catch (Throwable th) {
        }
    }

    public static String y(Context context) {
        String str = "";
        try {
            return C(context);
        } catch (Throwable th) {
            return str;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x006a A[SYNTHETIC, Splitter:B:26:0x006a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int z(android.content.Context r5) {
        /*
            r1 = 0
            int r0 = s
            if (r0 == 0) goto L_0x0008
            int r1 = s
        L_0x0007:
            return r1
        L_0x0008:
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 16
            if (r0 < r2) goto L_0x002c
            java.lang.String r0 = "activity"
            java.lang.Object r0 = r5.getSystemService(r0)
            android.app.ActivityManager r0 = (android.app.ActivityManager) r0
            if (r0 == 0) goto L_0x0007
            android.app.ActivityManager$MemoryInfo r1 = new android.app.ActivityManager$MemoryInfo
            r1.<init>()
            r0.getMemoryInfo(r1)
            long r0 = r1.totalMem
            r2 = 1024(0x400, double:5.06E-321)
            long r0 = r0 / r2
            int r0 = (int) r0
        L_0x0027:
            int r1 = r0 / 1024
            s = r1
            goto L_0x0007
        L_0x002c:
            r0 = 0
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x005d, all -> 0x0065 }
            java.lang.String r2 = "/proc/meminfo"
            r3.<init>(r2)     // Catch:{ Throwable -> 0x005d, all -> 0x0065 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x005d, all -> 0x0065 }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x005d, all -> 0x0065 }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x005d, all -> 0x0065 }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x005d, all -> 0x0065 }
            java.lang.String r0 = r2.readLine()     // Catch:{ Throwable -> 0x0074, all -> 0x0072 }
            java.lang.String r3 = "\\s+"
            java.lang.String[] r0 = r0.split(r3)     // Catch:{ Throwable -> 0x0074, all -> 0x0072 }
            r3 = 1
            r0 = r0[r3]     // Catch:{ Throwable -> 0x0074, all -> 0x0072 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x0074, all -> 0x0072 }
            int r0 = r0.intValue()     // Catch:{ Throwable -> 0x0074, all -> 0x0072 }
            if (r2 == 0) goto L_0x0027
            r2.close()     // Catch:{ IOException -> 0x005b }
            goto L_0x0027
        L_0x005b:
            r1 = move-exception
            goto L_0x0027
        L_0x005d:
            r2 = move-exception
        L_0x005e:
            if (r0 == 0) goto L_0x0063
            r0.close()     // Catch:{ IOException -> 0x006e }
        L_0x0063:
            r0 = r1
            goto L_0x0027
        L_0x0065:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0068:
            if (r2 == 0) goto L_0x006d
            r2.close()     // Catch:{ IOException -> 0x0070 }
        L_0x006d:
            throw r0
        L_0x006e:
            r0 = move-exception
            goto L_0x0063
        L_0x0070:
            r1 = move-exception
            goto L_0x006d
        L_0x0072:
            r0 = move-exception
            goto L_0x0068
        L_0x0074:
            r0 = move-exception
            r0 = r2
            goto L_0x005e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.p.z(android.content.Context):int");
    }
}
