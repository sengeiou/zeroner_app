package com.loc;

import android.content.Context;
import android.net.Proxy;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

/* compiled from: DnsManager */
public final class db {
    private static db c = null;
    de a = null;
    int b = 0;
    private Object d = null;
    private Context e = null;
    private ExecutorService f = null;
    private boolean g = false;
    private boolean h = true;
    private final int i = 2;
    private String j = "";
    private String k = "";
    private String[] l = null;
    private final int m = 5;
    private final int n = 2;

    /* compiled from: DnsManager */
    class a implements Runnable {
        de a = null;

        a(de deVar) {
            this.a = deVar;
        }

        public final void run() {
            db.this.b++;
            db.this.b(this.a);
            db.this.b--;
        }
    }

    private db() {
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private db(android.content.Context r11) {
        /*
            r10 = this;
            r2 = 2
            r8 = 0
            r1 = 0
            r7 = 1
            r6 = 0
            r10.<init>()
            r10.d = r1
            r10.e = r1
            r10.f = r1
            r10.g = r6
            r10.h = r7
            r10.a = r1
            r10.i = r2
            java.lang.String r0 = ""
            r10.j = r0
            java.lang.String r0 = ""
            r10.k = r0
            r10.l = r1
            r10.b = r6
            r0 = 5
            r10.m = r0
            r10.n = r2
            r10.e = r11
            android.content.Context r0 = r10.e
            java.lang.Object[] r1 = new java.lang.Object[r7]
            java.lang.String r2 = "DnsManager ==> init "
            r1[r6] = r2
            com.loc.dr.a()
            boolean r1 = com.loc.dh.p()     // Catch:{ Throwable -> 0x00c5 }
            if (r1 != 0) goto L_0x003f
        L_0x003e:
            return
        L_0x003f:
            java.lang.Object r1 = r10.d     // Catch:{ Throwable -> 0x00c5 }
            if (r1 != 0) goto L_0x003e
            java.lang.String r1 = "pref"
            java.lang.String r2 = "ok6"
            r3 = 0
            int r1 = com.loc.dq.b(r0, r1, r2, r3)     // Catch:{ Throwable -> 0x00c5 }
            java.lang.String r2 = "pref"
            java.lang.String r3 = "ok8"
            r4 = 0
            long r2 = com.loc.dq.b(r0, r2, r3, r4)     // Catch:{ Throwable -> 0x00c5 }
            if (r1 == 0) goto L_0x006d
            int r4 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r4 == 0) goto L_0x006d
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00c5 }
            long r2 = r4 - r2
            r4 = 259200000(0xf731400, double:1.280618154E-315)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x003e
        L_0x006d:
            java.lang.String r2 = "pref"
            java.lang.String r3 = "ok6"
            int r1 = r1 + 1
            com.loc.dq.a(r0, r2, r3, r1)     // Catch:{ Throwable -> 0x00c5 }
            java.lang.String r1 = "pref"
            java.lang.String r2 = "ok8"
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00c5 }
            com.loc.dq.a(r0, r1, r2, r4)     // Catch:{ Throwable -> 0x00c5 }
            boolean r1 = c()     // Catch:{ Throwable -> 0x00c5 }
            if (r1 == 0) goto L_0x00dc
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00c5 }
            r2 = 0
            java.lang.String r3 = "DnsManager ==> initForJar "
            r1[r2] = r3     // Catch:{ Throwable -> 0x00c5 }
            com.loc.dr.a()     // Catch:{ Throwable -> 0x00c5 }
            java.lang.String r1 = "com.autonavi.httpdns.HttpDnsManager"
            r2 = 1
            java.lang.Class[] r2 = new java.lang.Class[r2]     // Catch:{ Throwable -> 0x00d1 }
            r3 = 0
            java.lang.Class<android.content.Context> r4 = android.content.Context.class
            r2[r3] = r4     // Catch:{ Throwable -> 0x00d1 }
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00d1 }
            r4 = 0
            r3[r4] = r0     // Catch:{ Throwable -> 0x00d1 }
            java.lang.Object r1 = com.loc.dm.a(r1, (java.lang.Class<?>[]) r2, r3)     // Catch:{ Throwable -> 0x00d1 }
            r10.d = r1     // Catch:{ Throwable -> 0x00d1 }
        L_0x00ae:
            java.lang.String r1 = "pref"
            java.lang.String r2 = "ok6"
            r3 = 0
            com.loc.dq.a(r0, r1, r2, r3)     // Catch:{ Throwable -> 0x00c5 }
            java.lang.String r1 = "pref"
            java.lang.String r2 = "ok8"
            r4 = 0
            com.loc.dq.a(r0, r1, r2, r4)     // Catch:{ Throwable -> 0x00c5 }
            goto L_0x003e
        L_0x00c5:
            r0 = move-exception
            java.lang.String r1 = "APSCoManager"
            java.lang.String r2 = "init"
            com.loc.di.a(r0, r1, r2)
            goto L_0x003e
        L_0x00d1:
            r1 = move-exception
            java.lang.String r2 = "DnsManager"
            java.lang.String r3 = "initForJar"
            com.loc.di.a(r1, r2, r3)     // Catch:{ Throwable -> 0x00c5 }
            goto L_0x00ae
        L_0x00dc:
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00c5 }
            r2 = 0
            java.lang.String r3 = "DnsManager ==> initHttpDnsDex "
            r1[r2] = r3     // Catch:{ Throwable -> 0x00c5 }
            com.loc.dr.a()     // Catch:{ Throwable -> 0x00c5 }
            java.lang.String r1 = "HttpDNS"
            java.lang.String r2 = "1.0.0"
            com.loc.v r1 = com.loc.di.a(r1, r2)     // Catch:{ Throwable -> 0x011c }
            boolean r2 = com.loc.Cdo.a(r0, r1)     // Catch:{ Throwable -> 0x011c }
            if (r2 == 0) goto L_0x00ae
            java.lang.String r2 = "com.autonavi.httpdns.HttpDnsManager"
            r3 = 0
            r4 = 1
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x0127 }
            r5 = 0
            java.lang.Class<android.content.Context> r8 = android.content.Context.class
            r4[r5] = r8     // Catch:{ Throwable -> 0x0127 }
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0127 }
            r8 = 0
            r5[r8] = r0     // Catch:{ Throwable -> 0x0127 }
            java.lang.Object r1 = com.loc.ba.a(r0, r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0127 }
            r10.d = r1     // Catch:{ Throwable -> 0x0127 }
        L_0x0110:
            java.lang.Object r1 = r10.d     // Catch:{ Throwable -> 0x011c }
            if (r1 != 0) goto L_0x0129
            r1 = r6
        L_0x0115:
            java.lang.String r2 = "HttpDns"
            com.loc.Cdo.a(r0, r2, r1)     // Catch:{ Throwable -> 0x011c }
            goto L_0x00ae
        L_0x011c:
            r1 = move-exception
            java.lang.String r2 = "DNSManager"
            java.lang.String r3 = "initHttpDns"
            com.loc.di.a(r1, r2, r3)     // Catch:{ Throwable -> 0x00c5 }
            goto L_0x00ae
        L_0x0127:
            r1 = move-exception
            goto L_0x0110
        L_0x0129:
            r1 = r7
            goto L_0x0115
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.db.<init>(android.content.Context):void");
    }

    public static db a(Context context) {
        if (c == null) {
            c = new db(context);
        }
        return c;
    }

    private String a(String str) {
        int i2;
        String str2;
        String str3 = null;
        if (f()) {
            try {
                String[] strArr = (String[]) dm.a(this.d, "getIpsByHostAsync", str);
                if (strArr == null || strArr.length <= 0) {
                    str2 = null;
                } else {
                    if (this.l == null) {
                        this.l = strArr;
                        str3 = strArr[0];
                        i2 = 1;
                    } else if (a(strArr, this.l)) {
                        str3 = this.l[0];
                        i2 = 1;
                    } else {
                        this.l = strArr;
                        str2 = strArr[0];
                    }
                    Cdo.b(this.e, "HttpDns", i2);
                }
                str3 = str2;
                i2 = 1;
            } catch (Throwable th) {
                i2 = 0;
            }
            Cdo.b(this.e, "HttpDns", i2);
        }
        new Object[1][0] = "DnsManager ==> getIpAsync  host ： " + str + " ， ip ： " + str3;
        dr.a();
        return str3;
    }

    private static boolean a(String[] strArr, String[] strArr2) {
        if (strArr != null && strArr2 == null) {
            return false;
        }
        if (strArr == null && strArr2 != null) {
            return false;
        }
        if (strArr == null && strArr2 == null) {
            return true;
        }
        try {
            if (strArr.length != strArr2.length) {
                return false;
            }
            ArrayList arrayList = new ArrayList(12);
            ArrayList arrayList2 = new ArrayList(12);
            arrayList.addAll(Arrays.asList(strArr));
            arrayList2.addAll(Arrays.asList(strArr2));
            Collections.sort(arrayList);
            Collections.sort(arrayList2);
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (!((String) arrayList.get(i2)).equals(arrayList2.get(i2))) {
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean c() {
        try {
            Class.forName("com.autonavi.httpdns.HttpDnsManager");
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static void e() {
        c = null;
    }

    private boolean f() {
        return dh.p() && this.d != null && !g() && dq.b(this.e, "pref", "dns_faile_count_total", 0) < 2;
    }

    private boolean g() {
        int i2;
        String str = null;
        try {
            if (VERSION.SDK_INT >= 14) {
                str = System.getProperty("http.proxyHost");
                String property = System.getProperty("http.proxyPort");
                if (property == null) {
                    property = "-1";
                }
                i2 = Integer.parseInt(property);
            } else {
                str = Proxy.getHost(this.e);
                i2 = Proxy.getPort(this.e);
            }
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            i2 = -1;
        }
        return (str == null || i2 == -1) ? false : true;
    }

    public final void a() {
        if (TextUtils.isEmpty(this.k)) {
            return;
        }
        if (TextUtils.isEmpty(this.j) || !this.k.equals(this.j)) {
            this.j = this.k;
            dq.a(this.e, "ip", "last_ip", this.k);
        }
    }

    public final void a(de deVar) {
        try {
            this.g = false;
            if (f() && deVar != null) {
                this.a = deVar;
                String c2 = deVar.c();
                String host = new URL(c2).getHost();
                if (!"http://abroad.apilocate.amap.com/mobile/binary".equals(c2) && !"abroad.apilocate.amap.com".equals(host)) {
                    String str = "apilocate.amap.com".equalsIgnoreCase(host) ? "httpdns.apilocate.amap.com" : host;
                    String a2 = a(str);
                    if (this.h && TextUtils.isEmpty(a2)) {
                        this.h = false;
                        a2 = dq.b(this.e, "ip", "last_ip", "");
                        if (!TextUtils.isEmpty(a2)) {
                            this.j = a2;
                        }
                    }
                    if (!TextUtils.isEmpty(a2)) {
                        this.k = a2;
                        deVar.g = c2.replace(host, a2);
                        deVar.b().put("host", str);
                        deVar.a(str);
                        this.g = true;
                    }
                }
            }
        } catch (Throwable th) {
        }
    }

    public final void b() {
        if (this.g) {
            dq.a(this.e, "pref", "dns_faile_count_total", 0);
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void b(de deVar) {
        try {
            deVar.g = di.a();
            long b2 = dq.b(this.e, "pref", "dns_faile_count_total", 0);
            if (b2 < 2) {
                bm.a();
                bm.a(deVar, false);
                long j2 = b2 + 1;
                if (j2 >= 2) {
                    dp.a(this.e, "HttpDNS", "dns failed too much");
                }
                dq.a(this.e, "pref", "dns_faile_count_total", j2);
            }
        } catch (Throwable th) {
            dq.a(this.e, "pref", "dns_faile_count_total", 0);
        }
        return;
    }

    public final void d() {
        try {
            if (f()) {
                if (this.g && this.l != null) {
                    String[] strArr = this.l;
                    if (strArr != null) {
                        try {
                            if (strArr.length > 1) {
                                ArrayList arrayList = new ArrayList(12);
                                arrayList.addAll(Arrays.asList(strArr));
                                Iterator it = arrayList.iterator();
                                String str = (String) it.next();
                                it.remove();
                                arrayList.add(str);
                                arrayList.toArray(strArr);
                            }
                        } catch (Throwable th) {
                        }
                    }
                }
                if (this.b <= 5 && this.g) {
                    if (this.f == null) {
                        this.f = aj.d();
                    }
                    if (!this.f.isShutdown()) {
                        this.f.submit(new a(this.a));
                    }
                }
            }
        } catch (Throwable th2) {
        }
    }
}
