package com.loc;

import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/* compiled from: WifiManagerWrapper */
public final class cw {
    static long c = 0;
    static long d = 0;
    static long e = 0;
    static long f = 0;
    static long g = 0;
    public static HashMap<String, Long> q = new HashMap<>(36);
    public static long r = 0;
    static int s = 0;
    WifiManager a;
    ArrayList<ScanResult> b = new ArrayList<>();
    Context h;
    boolean i = false;
    StringBuilder j = null;
    boolean k = true;
    boolean l = true;
    boolean m = true;
    String n = null;
    TreeMap<Integer, ScanResult> o = null;
    public boolean p = true;
    ConnectivityManager t = null;
    volatile boolean u = false;
    private volatile WifiInfo v = null;
    private long w = 30000;

    public cw(Context context, WifiManager wifiManager) {
        this.a = wifiManager;
        this.h = context;
    }

    public static long a() {
        return ((dr.c() - r) / 1000) + 1;
    }

    private static boolean a(int i2) {
        int i3 = 20;
        try {
            i3 = WifiManager.calculateSignalLevel(i2, 20);
        } catch (ArithmeticException e2) {
            di.a(e2, "Aps", "wifiSigFine");
        }
        return i3 > 0;
    }

    public static boolean a(WifiInfo wifiInfo) {
        return wifiInfo != null && !TextUtils.isEmpty(wifiInfo.getSSID()) && dr.a(wifiInfo.getBSSID());
    }

    public static String l() {
        return String.valueOf(dr.c() - f);
    }

    private List<ScanResult> m() {
        if (this.a != null) {
            try {
                List<ScanResult> scanResults = this.a.getScanResults();
                if (VERSION.SDK_INT >= 17) {
                    HashMap<String, Long> hashMap = new HashMap<>(36);
                    for (ScanResult scanResult : scanResults) {
                        hashMap.put(scanResult.BSSID, Long.valueOf(scanResult.timestamp));
                    }
                    if (q.isEmpty() || !q.equals(hashMap)) {
                        q = hashMap;
                        r = dr.c();
                    }
                } else {
                    r = dr.c();
                }
                this.n = null;
                return scanResults;
            } catch (SecurityException e2) {
                this.n = e2.getMessage();
            } catch (Throwable th) {
                this.n = null;
                di.a(th, "WifiManagerWrapper", "getScanResults");
            }
        }
        return null;
    }

    private WifiInfo n() {
        try {
            if (this.a != null) {
                return this.a.getConnectionInfo();
            }
        } catch (Throwable th) {
            di.a(th, "WifiManagerWrapper", "getConnectionInfo");
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004a, code lost:
        if (r4 < r0) goto L_0x007f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0069 A[Catch:{ Throwable -> 0x0081 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:? A[Catch:{ Throwable -> 0x0081 }, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void o() {
        /*
            r8 = this;
            r2 = 30000(0x7530, double:1.4822E-319)
            boolean r0 = r8.p()
            if (r0 == 0) goto L_0x006f
            long r0 = com.loc.dr.c()     // Catch:{ Throwable -> 0x0081 }
            long r4 = c     // Catch:{ Throwable -> 0x0081 }
            long r4 = r0 - r4
            r0 = 4900(0x1324, double:2.421E-320)
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 < 0) goto L_0x007f
            android.net.ConnectivityManager r0 = r8.t     // Catch:{ Throwable -> 0x0081 }
            if (r0 != 0) goto L_0x0027
            android.content.Context r0 = r8.h     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r1 = "connectivity"
            java.lang.Object r0 = com.loc.dr.a(r0, r1)     // Catch:{ Throwable -> 0x0081 }
            android.net.ConnectivityManager r0 = (android.net.ConnectivityManager) r0     // Catch:{ Throwable -> 0x0081 }
            r8.t = r0     // Catch:{ Throwable -> 0x0081 }
        L_0x0027:
            android.net.ConnectivityManager r0 = r8.t     // Catch:{ Throwable -> 0x0081 }
            boolean r0 = r8.a(r0)     // Catch:{ Throwable -> 0x0081 }
            if (r0 == 0) goto L_0x0035
            r0 = 9900(0x26ac, double:4.8912E-320)
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 < 0) goto L_0x007f
        L_0x0035:
            int r0 = s     // Catch:{ Throwable -> 0x0081 }
            r1 = 1
            if (r0 <= r1) goto L_0x004c
            long r0 = r8.w     // Catch:{ Throwable -> 0x0081 }
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto L_0x0070
            long r0 = r8.w     // Catch:{ Throwable -> 0x0081 }
        L_0x0042:
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0081 }
            r3 = 28
            if (r2 < r3) goto L_0x004c
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 < 0) goto L_0x007f
        L_0x004c:
            android.net.wifi.WifiManager r0 = r8.a     // Catch:{ Throwable -> 0x0081 }
            if (r0 == 0) goto L_0x007f
            long r0 = com.loc.dr.c()     // Catch:{ Throwable -> 0x0081 }
            c = r0     // Catch:{ Throwable -> 0x0081 }
            int r0 = s     // Catch:{ Throwable -> 0x0081 }
            r1 = 2
            if (r0 >= r1) goto L_0x0061
            int r0 = s     // Catch:{ Throwable -> 0x0081 }
            int r0 = r0 + 1
            s = r0     // Catch:{ Throwable -> 0x0081 }
        L_0x0061:
            android.net.wifi.WifiManager r0 = r8.a     // Catch:{ Throwable -> 0x0081 }
            boolean r0 = r0.startScan()     // Catch:{ Throwable -> 0x0081 }
        L_0x0067:
            if (r0 == 0) goto L_0x006f
            long r0 = com.loc.dr.c()     // Catch:{ Throwable -> 0x0081 }
            e = r0     // Catch:{ Throwable -> 0x0081 }
        L_0x006f:
            return
        L_0x0070:
            long r0 = com.loc.dh.D()     // Catch:{ Throwable -> 0x0081 }
            r6 = -1
            int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r0 == 0) goto L_0x008c
            long r0 = com.loc.dh.D()     // Catch:{ Throwable -> 0x0081 }
            goto L_0x0042
        L_0x007f:
            r0 = 0
            goto L_0x0067
        L_0x0081:
            r0 = move-exception
            java.lang.String r1 = "WifiManager"
            java.lang.String r2 = "wifiScan"
            com.loc.di.a(r0, r1, r2)
            goto L_0x006f
        L_0x008c:
            r0 = r2
            goto L_0x0042
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cw.o():void");
    }

    private boolean p() {
        this.p = this.a == null ? false : dr.h(this.h);
        if (!this.p || !this.k) {
            return false;
        }
        if (e == 0) {
            return true;
        }
        if (dr.c() - e < 4900 || dr.c() - f < 1500) {
            return false;
        }
        return dr.c() - f > 4900 ? true : true;
    }

    public final void a(boolean z) {
        Context context = this.h;
        if (dh.C() && this.m && this.a != null && context != null && z && dr.d() > 17) {
            ContentResolver contentResolver = context.getContentResolver();
            String str = "android.provider.Settings$Global";
            try {
                if (((Integer) dm.a(str, "getInt", new Object[]{contentResolver, "wifi_scan_always_enabled"}, (Class<?>[]) new Class[]{ContentResolver.class, String.class})).intValue() == 0) {
                    dm.a(str, "putInt", new Object[]{contentResolver, "wifi_scan_always_enabled", Integer.valueOf(1)}, (Class<?>[]) new Class[]{ContentResolver.class, String.class, Integer.TYPE});
                }
            } catch (Throwable th) {
                di.a(th, "WifiManagerWrapper", "enableWifiAlwaysScan");
            }
        }
    }

    public final void a(boolean z, boolean z2, boolean z3, long j2) {
        this.k = z;
        this.l = z2;
        this.m = z3;
        if (j2 < 10000) {
            this.w = 10000;
        } else {
            this.w = j2;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0019, code lost:
        if (a(r2.getConnectionInfo()) != false) goto L_0x001b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(android.net.ConnectivityManager r5) {
        /*
            r4 = this;
            r0 = 1
            r1 = 0
            android.net.wifi.WifiManager r2 = r4.a
            if (r2 != 0) goto L_0x0007
        L_0x0006:
            return r1
        L_0x0007:
            android.net.NetworkInfo r3 = r5.getActiveNetworkInfo()     // Catch:{ Throwable -> 0x001d }
            int r3 = com.loc.dr.a(r3)     // Catch:{ Throwable -> 0x001d }
            if (r3 != r0) goto L_0x0027
            android.net.wifi.WifiInfo r2 = r2.getConnectionInfo()     // Catch:{ Throwable -> 0x001d }
            boolean r2 = a(r2)     // Catch:{ Throwable -> 0x001d }
            if (r2 == 0) goto L_0x0027
        L_0x001b:
            r1 = r0
            goto L_0x0006
        L_0x001d:
            r0 = move-exception
            java.lang.String r2 = "WifiManagerWrapper"
            java.lang.String r3 = "wifiAccess"
            com.loc.di.a(r0, r2, r3)
        L_0x0027:
            r0 = r1
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cw.a(android.net.ConnectivityManager):boolean");
    }

    public final String b() {
        return this.n;
    }

    public final void b(boolean z) {
        if (!z) {
            o();
        } else if (p()) {
            long c2 = dr.c();
            if (c2 - d >= 10000) {
                this.b.clear();
                g = f;
            }
            o();
            if (c2 - d >= 10000) {
                for (int i2 = 20; i2 > 0 && f == g; i2--) {
                    try {
                        Thread.sleep(150);
                    } catch (Throwable th) {
                    }
                }
            }
        }
        if (this.u) {
            this.u = false;
            d();
        }
        if (g != f) {
            List list = null;
            try {
                list = m();
            } catch (Throwable th2) {
                di.a(th2, "WifiManager", "updateScanResult");
            }
            g = f;
            if (list != null) {
                this.b.clear();
                this.b.addAll(list);
            } else {
                this.b.clear();
            }
        }
        if (dr.c() - f > 20000) {
            this.b.clear();
        }
        d = dr.c();
        if (this.b.isEmpty()) {
            f = dr.c();
            List m2 = m();
            if (m2 != null) {
                this.b.addAll(m2);
            }
        }
        if (this.b != null && !this.b.isEmpty()) {
            if (dr.c() - f > 3600000) {
                d();
            }
            if (this.o == null) {
                this.o = new TreeMap<>(Collections.reverseOrder());
            }
            this.o.clear();
            int size = this.b.size();
            for (int i3 = 0; i3 < size; i3++) {
                ScanResult scanResult = (ScanResult) this.b.get(i3);
                if (dr.a(scanResult != null ? scanResult.BSSID : "") && (size <= 20 || a(scanResult.level))) {
                    if (TextUtils.isEmpty(scanResult.SSID)) {
                        scanResult.SSID = "unkwn";
                    } else if (!"<unknown ssid>".equals(scanResult.SSID)) {
                        scanResult.SSID = String.valueOf(i3);
                    }
                    this.o.put(Integer.valueOf((scanResult.level * 25) + i3), scanResult);
                }
            }
            this.b.clear();
            for (ScanResult add : this.o.values()) {
                this.b.add(add);
            }
            this.o.clear();
        }
    }

    public final ArrayList<ScanResult> c() {
        if (this.b == null) {
            return null;
        }
        ArrayList<ScanResult> arrayList = new ArrayList<>();
        if (this.b.isEmpty()) {
            return arrayList;
        }
        arrayList.addAll(this.b);
        return arrayList;
    }

    public final void d() {
        this.v = null;
        this.b.clear();
    }

    public final void e() {
        if (this.a != null && dr.c() - f > 4900) {
            f = dr.c();
        }
    }

    public final void f() {
        int i2 = 4;
        if (this.a != null) {
            try {
                if (this.a != null) {
                    i2 = this.a.getWifiState();
                }
            } catch (Throwable th) {
                di.a(th, "Aps", "onReceive part");
            }
            if (this.b == null) {
                this.b = new ArrayList<>();
            }
            switch (i2) {
                case 0:
                case 1:
                case 4:
                    this.u = true;
                    return;
                default:
                    return;
            }
        }
    }

    public final boolean g() {
        return this.p;
    }

    public final WifiInfo h() {
        this.v = n();
        return this.v;
    }

    public final boolean i() {
        return this.i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0063 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String j() {
        /*
            r13 = this;
            r2 = 1
            r6 = 0
            java.lang.StringBuilder r0 = r13.j
            if (r0 != 0) goto L_0x007d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 700(0x2bc, float:9.81E-43)
            r0.<init>(r1)
            r13.j = r0
        L_0x000f:
            r13.i = r6
            java.lang.String r0 = ""
            android.net.wifi.WifiInfo r1 = r13.h()
            r13.v = r1
            android.net.wifi.WifiInfo r1 = r13.v
            boolean r1 = a(r1)
            if (r1 == 0) goto L_0x00bf
            android.net.wifi.WifiInfo r0 = r13.v
            java.lang.String r0 = r0.getBSSID()
            r1 = r0
        L_0x0029:
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r13.b
            int r7 = r0.size()
            r5 = r6
            r4 = r6
            r3 = r6
        L_0x0032:
            if (r5 >= r7) goto L_0x0089
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r13.b
            java.lang.Object r0 = r0.get(r5)
            android.net.wifi.ScanResult r0 = (android.net.wifi.ScanResult) r0
            java.lang.String r8 = r0.BSSID
            boolean r0 = r13.l
            if (r0 != 0) goto L_0x00bd
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r13.b
            java.lang.Object r0 = r0.get(r5)
            android.net.wifi.ScanResult r0 = (android.net.wifi.ScanResult) r0
            java.lang.String r0 = r0.SSID
            java.lang.String r9 = "<unknown ssid>"
            boolean r0 = r9.equals(r0)
            if (r0 != 0) goto L_0x00bd
            r0 = r2
        L_0x0056:
            java.lang.String r3 = "nb"
            boolean r9 = r1.equals(r8)
            if (r9 == 0) goto L_0x0063
            java.lang.String r3 = "access"
            r4 = r2
        L_0x0063:
            java.lang.StringBuilder r9 = r13.j
            java.util.Locale r10 = java.util.Locale.US
            java.lang.String r11 = "#%s,%s"
            r12 = 2
            java.lang.Object[] r12 = new java.lang.Object[r12]
            r12[r6] = r8
            r12[r2] = r3
            java.lang.String r3 = java.lang.String.format(r10, r11, r12)
            r9.append(r3)
            int r3 = r5 + 1
            r5 = r3
            r3 = r0
            goto L_0x0032
        L_0x007d:
            java.lang.StringBuilder r0 = r13.j
            java.lang.StringBuilder r1 = r13.j
            int r1 = r1.length()
            r0.delete(r6, r1)
            goto L_0x000f
        L_0x0089:
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r13.b
            int r0 = r0.size()
            if (r0 != 0) goto L_0x0092
            r3 = r2
        L_0x0092:
            boolean r0 = r13.l
            if (r0 != 0) goto L_0x009a
            if (r3 != 0) goto L_0x009a
            r13.i = r2
        L_0x009a:
            if (r4 != 0) goto L_0x00b6
            boolean r0 = android.text.TextUtils.isEmpty(r1)
            if (r0 != 0) goto L_0x00b6
            java.lang.StringBuilder r0 = r13.j
            java.lang.String r2 = "#"
            java.lang.StringBuilder r0 = r0.append(r2)
            r0.append(r1)
            java.lang.StringBuilder r0 = r13.j
            java.lang.String r1 = ",access"
            r0.append(r1)
        L_0x00b6:
            java.lang.StringBuilder r0 = r13.j
            java.lang.String r0 = r0.toString()
            return r0
        L_0x00bd:
            r0 = r3
            goto L_0x0056
        L_0x00bf:
            r1 = r0
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cw.j():java.lang.String");
    }

    public final void k() {
        d();
        this.b.clear();
    }
}
