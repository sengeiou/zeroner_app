package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import com.alibaba.android.arouter.utils.Consts;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.crashreport.common.info.b;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ao;
import com.tencent.bugly.proguard.ap;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: BUGLY */
public class d {
    /* access modifiers changed from: private */
    public static d a = null;
    private a b;
    private com.tencent.bugly.crashreport.common.info.a c;
    private b d;
    private Context e;

    private d(Context context) {
        c a2 = c.a();
        if (a2 != null) {
            this.b = a.a();
            this.c = com.tencent.bugly.crashreport.common.info.a.a(context);
            this.d = a2.p;
            this.e = context;
            am.a().a(new Runnable() {
                public void run() {
                    d.this.b();
                }
            });
        }
    }

    public static d a(Context context) {
        if (a == null) {
            a = new d(context);
        }
        return a;
    }

    public static void a(Thread thread, int i, String str, String str2, String str3, Map<String, String> map) {
        final Thread thread2 = thread;
        final int i2 = i;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final Map<String, String> map2 = map;
        am.a().a(new Runnable() {
            public void run() {
                try {
                    if (d.a == null) {
                        an.e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    } else {
                        d.a.c(thread2, i2, str4, str5, str6, map2);
                    }
                } catch (Throwable th) {
                    if (!an.b(th)) {
                        ThrowableExtension.printStackTrace(th);
                    }
                    an.e("[ExtraCrashManager] Crash error %s %s %s", str4, str5, str6);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void b() {
        an.c("[ExtraCrashManager] Trying to notify Bugly agents.", new Object[0]);
        try {
            Class cls = Class.forName("com.tencent.bugly.proguard.o");
            String str = "com.tencent.bugly";
            this.c.getClass();
            String str2 = "";
            if (!"".equals(str2)) {
                str = str + Consts.DOT + str2;
            }
            ap.a(cls, "sdkPackageName", (Object) str, (Object) null);
            an.c("[ExtraCrashManager] Bugly game agent has been notified.", new Object[0]);
        } catch (Throwable th) {
            an.a("[ExtraCrashManager] no game agent", new Object[0]);
        }
    }

    private CrashDetailBean b(Thread thread, int i, String str, String str2, String str3, Map<String, String> map) {
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.C = b.i();
        crashDetailBean.D = b.g();
        crashDetailBean.E = b.k();
        crashDetailBean.F = this.c.p();
        crashDetailBean.G = this.c.o();
        crashDetailBean.H = this.c.q();
        crashDetailBean.w = ap.a(this.e, c.e, c.h);
        crashDetailBean.b = i;
        crashDetailBean.e = this.c.h();
        crashDetailBean.f = this.c.o;
        crashDetailBean.g = this.c.w();
        crashDetailBean.m = this.c.g();
        crashDetailBean.n = "" + str;
        crashDetailBean.o = "" + str2;
        String str4 = "";
        if (str3 != null) {
            String[] split = str3.split("\n");
            if (split.length > 0) {
                str4 = split[0];
            }
        } else {
            str3 = "";
        }
        crashDetailBean.p = str4;
        crashDetailBean.q = str3;
        crashDetailBean.r = System.currentTimeMillis();
        crashDetailBean.u = ap.b(crashDetailBean.q.getBytes());
        crashDetailBean.z = ap.a(c.f, false);
        crashDetailBean.A = this.c.e;
        crashDetailBean.B = thread.getName() + "(" + thread.getId() + ")";
        crashDetailBean.I = this.c.y();
        crashDetailBean.h = this.c.v();
        crashDetailBean.N = this.c.a;
        crashDetailBean.O = this.c.a();
        crashDetailBean.Q = this.c.H();
        crashDetailBean.R = this.c.I();
        crashDetailBean.S = this.c.B();
        crashDetailBean.T = this.c.G();
        this.d.c(crashDetailBean);
        crashDetailBean.y = ao.a();
        if (crashDetailBean.P == null) {
            crashDetailBean.P = new LinkedHashMap();
        }
        if (map != null) {
            crashDetailBean.P.putAll(map);
        }
        return crashDetailBean;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0145 A[Catch:{ Throwable -> 0x013e, all -> 0x0152 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c(java.lang.Thread r10, int r11, java.lang.String r12, java.lang.String r13, java.lang.String r14, java.util.Map<java.lang.String, java.lang.String> r15) {
        /*
            r9 = this;
            r2 = 1
            r8 = 0
            if (r10 != 0) goto L_0x0008
            java.lang.Thread r10 = java.lang.Thread.currentThread()
        L_0x0008:
            switch(r11) {
                case 4: goto L_0x008f;
                case 5: goto L_0x001a;
                case 6: goto L_0x001a;
                case 7: goto L_0x000b;
                case 8: goto L_0x0093;
                default: goto L_0x000b;
            }
        L_0x000b:
            java.lang.String r0 = "[ExtraCrashManager] Unknown extra crash type: %d"
            java.lang.Object[] r1 = new java.lang.Object[r2]
            java.lang.Integer r2 = java.lang.Integer.valueOf(r11)
            r1[r8] = r2
            com.tencent.bugly.proguard.an.d(r0, r1)
        L_0x0019:
            return
        L_0x001a:
            java.lang.String r0 = "Cocos"
        L_0x001d:
            java.lang.String r1 = "[ExtraCrashManager] %s Crash Happen"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r8] = r0
            com.tencent.bugly.proguard.an.e(r1, r2)
            com.tencent.bugly.crashreport.common.strategy.a r1 = r9.b     // Catch:{ Throwable -> 0x013e }
            boolean r1 = r1.b()     // Catch:{ Throwable -> 0x013e }
            if (r1 != 0) goto L_0x0038
            java.lang.String r1 = "[ExtraCrashManager] There is no remote strategy, but still store it."
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x013e }
            com.tencent.bugly.proguard.an.d(r1, r2)     // Catch:{ Throwable -> 0x013e }
        L_0x0038:
            com.tencent.bugly.crashreport.common.strategy.a r1 = r9.b     // Catch:{ Throwable -> 0x013e }
            com.tencent.bugly.crashreport.common.strategy.StrategyBean r1 = r1.c()     // Catch:{ Throwable -> 0x013e }
            boolean r2 = r1.g     // Catch:{ Throwable -> 0x013e }
            if (r2 != 0) goto L_0x0097
            com.tencent.bugly.crashreport.common.strategy.a r2 = r9.b     // Catch:{ Throwable -> 0x013e }
            boolean r2 = r2.b()     // Catch:{ Throwable -> 0x013e }
            if (r2 == 0) goto L_0x0097
            java.lang.String r1 = "[ExtraCrashManager] Crash report was closed by remote , will not upload to Bugly , print local for helpful!"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x013e }
            com.tencent.bugly.proguard.an.e(r1, r2)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r1 = com.tencent.bugly.proguard.ap.a()     // Catch:{ Throwable -> 0x013e }
            com.tencent.bugly.crashreport.common.info.a r2 = r9.c     // Catch:{ Throwable -> 0x013e }
            java.lang.String r2 = r2.e     // Catch:{ Throwable -> 0x013e }
            java.lang.String r3 = r10.getName()     // Catch:{ Throwable -> 0x013e }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013e }
            r4.<init>()     // Catch:{ Throwable -> 0x013e }
            java.lang.StringBuilder r4 = r4.append(r12)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r5 = "\n"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x013e }
            java.lang.StringBuilder r4 = r4.append(r13)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r5 = "\n"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x013e }
            java.lang.StringBuilder r4 = r4.append(r14)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x013e }
            r5 = 0
            com.tencent.bugly.crashreport.crash.b.a(r0, r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.e(r0, r1)
            goto L_0x0019
        L_0x008f:
            java.lang.String r0 = "Unity"
            goto L_0x001d
        L_0x0093:
            java.lang.String r0 = "H5"
            goto L_0x001d
        L_0x0097:
            switch(r11) {
                case 4: goto L_0x009a;
                case 5: goto L_0x00be;
                case 6: goto L_0x00be;
                case 7: goto L_0x009a;
                case 8: goto L_0x00d8;
                default: goto L_0x009a;
            }
        L_0x009a:
            r1 = 8
            if (r11 != r1) goto L_0x015c
            r3 = 5
        L_0x009f:
            r1 = r9
            r2 = r10
            r4 = r12
            r5 = r13
            r6 = r14
            r7 = r15
            com.tencent.bugly.crashreport.crash.CrashDetailBean r5 = r1.b(r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x013e }
            if (r5 != 0) goto L_0x00f2
            java.lang.String r0 = "[ExtraCrashManager] Failed to package crash data."
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x013e }
            com.tencent.bugly.proguard.an.e(r0, r1)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.e(r0, r1)
            goto L_0x0019
        L_0x00be:
            boolean r1 = r1.l     // Catch:{ Throwable -> 0x013e }
            if (r1 != 0) goto L_0x009a
            java.lang.String r1 = "[ExtraCrashManager] %s report is disabled."
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x013e }
            r3 = 0
            r2[r3] = r0     // Catch:{ Throwable -> 0x013e }
            com.tencent.bugly.proguard.an.e(r1, r2)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.e(r0, r1)
            goto L_0x0019
        L_0x00d8:
            boolean r1 = r1.m     // Catch:{ Throwable -> 0x013e }
            if (r1 != 0) goto L_0x009a
            java.lang.String r1 = "[ExtraCrashManager] %s report is disabled."
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x013e }
            r3 = 0
            r2[r3] = r0     // Catch:{ Throwable -> 0x013e }
            com.tencent.bugly.proguard.an.e(r1, r2)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.e(r0, r1)
            goto L_0x0019
        L_0x00f2:
            java.lang.String r1 = com.tencent.bugly.proguard.ap.a()     // Catch:{ Throwable -> 0x013e }
            com.tencent.bugly.crashreport.common.info.a r2 = r9.c     // Catch:{ Throwable -> 0x013e }
            java.lang.String r2 = r2.e     // Catch:{ Throwable -> 0x013e }
            java.lang.String r3 = r10.getName()     // Catch:{ Throwable -> 0x013e }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013e }
            r4.<init>()     // Catch:{ Throwable -> 0x013e }
            java.lang.StringBuilder r4 = r4.append(r12)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r6 = "\n"
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x013e }
            java.lang.StringBuilder r4 = r4.append(r13)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r6 = "\n"
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x013e }
            java.lang.StringBuilder r4 = r4.append(r14)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x013e }
            com.tencent.bugly.crashreport.crash.b.a(r0, r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x013e }
            com.tencent.bugly.crashreport.crash.b r0 = r9.d     // Catch:{ Throwable -> 0x013e }
            boolean r0 = r0.a(r5)     // Catch:{ Throwable -> 0x013e }
            if (r0 != 0) goto L_0x0134
            com.tencent.bugly.crashreport.crash.b r0 = r9.d     // Catch:{ Throwable -> 0x013e }
            r2 = 3000(0xbb8, double:1.482E-320)
            r1 = 0
            r0.a(r5, r2, r1)     // Catch:{ Throwable -> 0x013e }
        L_0x0134:
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.e(r0, r1)
            goto L_0x0019
        L_0x013e:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x0152 }
            if (r1 != 0) goto L_0x0148
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0152 }
        L_0x0148:
            java.lang.String r0 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r1 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.e(r0, r1)
            goto L_0x0019
        L_0x0152:
            r0 = move-exception
            java.lang.String r1 = "[ExtraCrashManager] Successfully handled."
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.e(r1, r2)
            throw r0
        L_0x015c:
            r3 = r11
            goto L_0x009f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.d.c(java.lang.Thread, int, java.lang.String, java.lang.String, java.lang.String, java.util.Map):void");
    }
}
