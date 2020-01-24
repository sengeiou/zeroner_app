package com.tencent.bugly.proguard;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.a;
import java.util.Map;
import java.util.UUID;

/* compiled from: BUGLY */
public class al implements Runnable {
    public int a;
    public int b;
    protected int c;
    protected long d;
    protected long e;
    protected boolean f;
    private final Context g;
    private final int h;
    private final byte[] i;
    private final a j;
    private final com.tencent.bugly.crashreport.common.strategy.a k;
    private final ai l;
    private final ak m;
    private final int n;
    private final aj o;
    private final aj p;
    private String q;
    private final String r;
    private final Map<String, String> s;
    private boolean t;

    public al(Context context, int i2, int i3, byte[] bArr, String str, String str2, aj ajVar, boolean z, boolean z2) {
        this(context, i2, i3, bArr, str, str2, ajVar, z, 2, BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH, z2, null);
    }

    public al(Context context, int i2, int i3, byte[] bArr, String str, String str2, aj ajVar, boolean z, int i4, int i5, boolean z2, Map<String, String> map) {
        this.a = 2;
        this.b = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
        this.q = null;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = true;
        this.t = false;
        this.g = context;
        this.j = a.a(context);
        this.i = bArr;
        this.k = com.tencent.bugly.crashreport.common.strategy.a.a();
        this.l = ai.a(context);
        this.m = ak.a();
        this.n = i2;
        this.q = str;
        this.r = str2;
        this.o = ajVar;
        this.p = this.m.a;
        this.f = z;
        this.h = i3;
        if (i4 > 0) {
            this.a = i4;
        }
        if (i5 > 0) {
            this.b = i5;
        }
        this.t = z2;
        this.s = map;
    }

    /* access modifiers changed from: protected */
    public void a() {
        this.m.a(this.n, System.currentTimeMillis());
        if (this.o != null) {
            this.o.a(this.h);
        }
        if (this.p != null) {
            this.p.a(this.h);
        }
    }

    /* access modifiers changed from: protected */
    public void a(int i2, String str) {
        an.e("[Upload] Failed to upload(%d): %s", Integer.valueOf(i2), str);
    }

    /* access modifiers changed from: protected */
    public void a(be beVar, boolean z, int i2, String str, int i3) {
        String str2;
        switch (this.h) {
            case 630:
            case 830:
                str2 = "crash";
                break;
            case 640:
            case 840:
                str2 = "userinfo";
                break;
            default:
                str2 = String.valueOf(this.h);
                break;
        }
        if (z) {
            an.a("[Upload] Success: %s", str2);
        } else {
            an.e("[Upload] Failed to upload(%d) %s: %s", Integer.valueOf(i2), str2, str);
            if (this.f) {
                this.m.a(i3, (be) null);
            }
        }
        if (this.d + this.e > 0) {
            this.m.a(this.m.a(this.t) + this.d + this.e, this.t);
        }
        if (this.o != null) {
            this.o.a(this.h, beVar, this.d, this.e, z, str);
        }
        if (this.p != null) {
            this.p.a(this.h, beVar, this.d, this.e, z, str);
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(be beVar, a aVar, com.tencent.bugly.crashreport.common.strategy.a aVar2) {
        if (beVar == null) {
            an.d("resp == null!", new Object[0]);
            return false;
        } else if (beVar.a != 0) {
            an.e("resp result error %d", Byte.valueOf(beVar.a));
            return false;
        } else {
            try {
                if (!ap.a(beVar.d) && !a.b().i().equals(beVar.d)) {
                    ae.a().a(com.tencent.bugly.crashreport.common.strategy.a.a, "key_ip", beVar.d.getBytes("UTF-8"), (ad) null, true);
                    aVar.d(beVar.d);
                }
                if (!ap.a(beVar.g) && !a.b().j().equals(beVar.g)) {
                    ae.a().a(com.tencent.bugly.crashreport.common.strategy.a.a, "key_imei", beVar.g.getBytes("UTF-8"), (ad) null, true);
                    aVar.e(beVar.g);
                }
            } catch (Throwable th) {
                an.a(th);
            }
            aVar.n = beVar.e;
            if (beVar.b == 510) {
                if (beVar.c == null) {
                    an.e("[Upload] Strategy data is null. Response cmd: %d", Integer.valueOf(beVar.b));
                    return false;
                }
                bg bgVar = (bg) ah.a(beVar.c, bg.class);
                if (bgVar == null) {
                    an.e("[Upload] Failed to decode strategy from server. Response cmd: %d", Integer.valueOf(beVar.b));
                    return false;
                }
                aVar2.a(bgVar);
            }
            return true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:133:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x02b3, code lost:
        if (r5 == 0) goto L_0x0356;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x02b6, code lost:
        if (r5 != 2) goto L_0x0339;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x02c1, code lost:
        if ((r12.d + r12.e) <= 0) goto L_0x02d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x02c3, code lost:
        r12.m.a((r12.m.a(r12.t) + r12.d) + r12.e, r12.t);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x02d8, code lost:
        r12.m.a(r5, (com.tencent.bugly.proguard.be) null);
        com.tencent.bugly.proguard.an.a("[Upload] Session ID is invalid, will try again immediately (pid=%d | tid=%d).", java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
        r12.m.a(r12.n, r12.h, r12.i, r12.q, r12.r, r12.o, r12.a, r12.b, true, r12.s);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0339, code lost:
        a(null, false, 1, "status of server is " + r5, r5);
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r12 = this;
            r0 = 0
            r12.c = r0     // Catch:{ Throwable -> 0x0032 }
            r0 = 0
            r12.d = r0     // Catch:{ Throwable -> 0x0032 }
            r0 = 0
            r12.e = r0     // Catch:{ Throwable -> 0x0032 }
            byte[] r0 = r12.i     // Catch:{ Throwable -> 0x0032 }
            android.content.Context r1 = r12.g     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r1 = com.tencent.bugly.crashreport.common.info.b.f(r1)     // Catch:{ Throwable -> 0x0032 }
            if (r1 != 0) goto L_0x0021
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.String r4 = "network is not available"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
        L_0x0020:
            return
        L_0x0021:
            if (r0 == 0) goto L_0x0026
            int r1 = r0.length     // Catch:{ Throwable -> 0x0032 }
            if (r1 != 0) goto L_0x003d
        L_0x0026:
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.String r4 = "request package is empty!"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x0032:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x0020
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0020
        L_0x003d:
            com.tencent.bugly.proguard.ak r1 = r12.m     // Catch:{ Throwable -> 0x0032 }
            boolean r2 = r12.t     // Catch:{ Throwable -> 0x0032 }
            long r2 = r1.a(r2)     // Catch:{ Throwable -> 0x0032 }
            r4 = 2097152(0x200000, double:1.0361308E-317)
            int r1 = r0.length     // Catch:{ Throwable -> 0x0032 }
            long r6 = (long) r1     // Catch:{ Throwable -> 0x0032 }
            long r6 = r6 + r2
            int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r1 < 0) goto L_0x008d
            java.lang.String r0 = "[Upload] Upload too much data, try next time: %d/%d"
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0032 }
            r6 = 0
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ Throwable -> 0x0032 }
            r1[r6] = r2     // Catch:{ Throwable -> 0x0032 }
            r2 = 1
            java.lang.Long r3 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x0032 }
            r1[r2] = r3     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.e(r0, r1)     // Catch:{ Throwable -> 0x0032 }
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0032 }
            r0.<init>()     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r6 = "over net consume: "
            java.lang.StringBuilder r0 = r0.append(r6)     // Catch:{ Throwable -> 0x0032 }
            r6 = 1024(0x400, double:5.06E-321)
            long r4 = r4 / r6
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r4 = "K"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r4 = r0.toString()     // Catch:{ Throwable -> 0x0032 }
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x008d:
            java.lang.String r1 = "[Upload] Run upload task with cmd: %d"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0032 }
            r3 = 0
            int r4 = r12.h     // Catch:{ Throwable -> 0x0032 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0032 }
            r2[r3] = r4     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.c(r1, r2)     // Catch:{ Throwable -> 0x0032 }
            android.content.Context r1 = r12.g     // Catch:{ Throwable -> 0x0032 }
            if (r1 == 0) goto L_0x00af
            com.tencent.bugly.crashreport.common.info.a r1 = r12.j     // Catch:{ Throwable -> 0x0032 }
            if (r1 == 0) goto L_0x00af
            com.tencent.bugly.crashreport.common.strategy.a r1 = r12.k     // Catch:{ Throwable -> 0x0032 }
            if (r1 == 0) goto L_0x00af
            com.tencent.bugly.proguard.ai r1 = r12.l     // Catch:{ Throwable -> 0x0032 }
            if (r1 != 0) goto L_0x00bc
        L_0x00af:
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.String r4 = "illegal access error"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x00bc:
            com.tencent.bugly.crashreport.common.strategy.a r1 = r12.k     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.crashreport.common.strategy.StrategyBean r7 = r1.c()     // Catch:{ Throwable -> 0x0032 }
            if (r7 != 0) goto L_0x00d1
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.String r4 = "illegal local strategy"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x00d1:
            r3 = 0
            java.util.HashMap r8 = new java.util.HashMap     // Catch:{ Throwable -> 0x0032 }
            r8.<init>()     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r1 = "prodId"
            com.tencent.bugly.crashreport.common.info.a r2 = r12.j     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r2 = r2.f()     // Catch:{ Throwable -> 0x0032 }
            r8.put(r1, r2)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r1 = "bundleId"
            com.tencent.bugly.crashreport.common.info.a r2 = r12.j     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r2 = r2.d     // Catch:{ Throwable -> 0x0032 }
            r8.put(r1, r2)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r1 = "appVer"
            com.tencent.bugly.crashreport.common.info.a r2 = r12.j     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r2 = r2.o     // Catch:{ Throwable -> 0x0032 }
            r8.put(r1, r2)     // Catch:{ Throwable -> 0x0032 }
            java.util.Map<java.lang.String, java.lang.String> r1 = r12.s     // Catch:{ Throwable -> 0x0032 }
            if (r1 == 0) goto L_0x0100
            java.util.Map<java.lang.String, java.lang.String> r1 = r12.s     // Catch:{ Throwable -> 0x0032 }
            r8.putAll(r1)     // Catch:{ Throwable -> 0x0032 }
        L_0x0100:
            boolean r1 = r12.f     // Catch:{ Throwable -> 0x0032 }
            if (r1 == 0) goto L_0x0173
            java.lang.String r1 = "cmd"
            int r2 = r12.h     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r2 = java.lang.Integer.toString(r2)     // Catch:{ Throwable -> 0x0032 }
            r8.put(r1, r2)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r1 = "platformId"
            r2 = 1
            java.lang.String r2 = java.lang.Byte.toString(r2)     // Catch:{ Throwable -> 0x0032 }
            r8.put(r1, r2)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r1 = "sdkVer"
            com.tencent.bugly.crashreport.common.info.a r2 = r12.j     // Catch:{ Throwable -> 0x0032 }
            r2.getClass()     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r2 = "2.6.5"
            r8.put(r1, r2)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r1 = "strategylastUpdateTime"
            long r4 = r7.p     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r2 = java.lang.Long.toString(r4)     // Catch:{ Throwable -> 0x0032 }
            r8.put(r1, r2)     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.ak r1 = r12.m     // Catch:{ Throwable -> 0x0032 }
            boolean r1 = r1.a(r8)     // Catch:{ Throwable -> 0x0032 }
            if (r1 != 0) goto L_0x014a
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.String r4 = "failed to add security info to HTTP headers"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x014a:
            r1 = 2
            byte[] r0 = com.tencent.bugly.proguard.ap.a(r0, r1)     // Catch:{ Throwable -> 0x0032 }
            if (r0 != 0) goto L_0x015e
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.String r4 = "failed to zip request body"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x015e:
            com.tencent.bugly.proguard.ak r1 = r12.m     // Catch:{ Throwable -> 0x0032 }
            byte[] r0 = r1.a(r0)     // Catch:{ Throwable -> 0x0032 }
            if (r0 != 0) goto L_0x0173
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.String r4 = "failed to encrypt request body"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x0173:
            r6 = r0
            r12.a()     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r2 = r12.q     // Catch:{ Throwable -> 0x0032 }
            r5 = -1
            r0 = 0
            r1 = r0
            r0 = r2
        L_0x017d:
            int r4 = r1 + 1
            int r2 = r12.a     // Catch:{ Throwable -> 0x0032 }
            if (r1 >= r2) goto L_0x0433
            r1 = 1
            if (r4 <= r1) goto L_0x01b0
            java.lang.String r1 = "[Upload] Failed to upload last time, wait and try(%d) again."
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0032 }
            r3 = 0
            java.lang.Integer r9 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0032 }
            r2[r3] = r9     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.d(r1, r2)     // Catch:{ Throwable -> 0x0032 }
            int r1 = r12.b     // Catch:{ Throwable -> 0x0032 }
            long r2 = (long) r1     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.ap.b(r2)     // Catch:{ Throwable -> 0x0032 }
            int r1 = r12.a     // Catch:{ Throwable -> 0x0032 }
            if (r4 != r1) goto L_0x01b0
            java.lang.String r0 = "[Upload] Use the back-up url at the last time: %s"
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0032 }
            r2 = 0
            java.lang.String r3 = r12.r     // Catch:{ Throwable -> 0x0032 }
            r1[r2] = r3     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.d(r0, r1)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r0 = r12.r     // Catch:{ Throwable -> 0x0032 }
        L_0x01b0:
            java.lang.String r1 = "[Upload] Send %d bytes"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0032 }
            r3 = 0
            int r9 = r6.length     // Catch:{ Throwable -> 0x0032 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x0032 }
            r2[r3] = r9     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.c(r1, r2)     // Catch:{ Throwable -> 0x0032 }
            boolean r1 = r12.f     // Catch:{ Throwable -> 0x0032 }
            if (r1 == 0) goto L_0x043f
            java.lang.String r0 = a(r0)     // Catch:{ Throwable -> 0x0032 }
            r2 = r0
        L_0x01ca:
            java.lang.String r0 = "[Upload] Upload to %s with cmd %d (pid=%d | tid=%d)."
            r1 = 4
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0032 }
            r3 = 0
            r1[r3] = r2     // Catch:{ Throwable -> 0x0032 }
            r3 = 1
            int r9 = r12.h     // Catch:{ Throwable -> 0x0032 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x0032 }
            r1[r3] = r9     // Catch:{ Throwable -> 0x0032 }
            r3 = 2
            int r9 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x0032 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x0032 }
            r1[r3] = r9     // Catch:{ Throwable -> 0x0032 }
            r3 = 3
            int r9 = android.os.Process.myTid()     // Catch:{ Throwable -> 0x0032 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x0032 }
            r1[r3] = r9     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.c(r0, r1)     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.ai r0 = r12.l     // Catch:{ Throwable -> 0x0032 }
            byte[] r1 = r0.a(r2, r6, r12, r8)     // Catch:{ Throwable -> 0x0032 }
            if (r1 != 0) goto L_0x0209
            r0 = 1
            java.lang.String r1 = "Failed to upload for no response!"
            r12.a(r0, r1)     // Catch:{ Throwable -> 0x0032 }
            r3 = 1
            r1 = r4
            r0 = r2
            goto L_0x017d
        L_0x0209:
            com.tencent.bugly.proguard.ai r0 = r12.l     // Catch:{ Throwable -> 0x0032 }
            java.util.Map<java.lang.String, java.lang.String> r3 = r0.b     // Catch:{ Throwable -> 0x0032 }
            boolean r0 = r12.f     // Catch:{ Throwable -> 0x0032 }
            if (r0 == 0) goto L_0x0356
            boolean r0 = a(r3)     // Catch:{ Throwable -> 0x0032 }
            if (r0 != 0) goto L_0x0280
            java.lang.String r0 = "[Upload] Headers from server is not valid, just try again (pid=%d | tid=%d)."
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0032 }
            r9 = 0
            int r10 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x0032 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Throwable -> 0x0032 }
            r1[r9] = r10     // Catch:{ Throwable -> 0x0032 }
            r9 = 1
            int r10 = android.os.Process.myTid()     // Catch:{ Throwable -> 0x0032 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Throwable -> 0x0032 }
            r1[r9] = r10     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.c(r0, r1)     // Catch:{ Throwable -> 0x0032 }
            r0 = 1
            java.lang.String r1 = "[Upload] Failed to upload for no status header."
            r12.a(r0, r1)     // Catch:{ Throwable -> 0x0032 }
            if (r3 == 0) goto L_0x0272
            java.util.Set r0 = r3.entrySet()     // Catch:{ Throwable -> 0x0032 }
            java.util.Iterator r1 = r0.iterator()     // Catch:{ Throwable -> 0x0032 }
        L_0x0247:
            boolean r0 = r1.hasNext()     // Catch:{ Throwable -> 0x0032 }
            if (r0 == 0) goto L_0x0272
            java.lang.Object r0 = r1.next()     // Catch:{ Throwable -> 0x0032 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r3 = "[key]: %s, [value]: %s"
            r9 = 2
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x0032 }
            r10 = 0
            java.lang.Object r11 = r0.getKey()     // Catch:{ Throwable -> 0x0032 }
            r9[r10] = r11     // Catch:{ Throwable -> 0x0032 }
            r10 = 1
            java.lang.Object r0 = r0.getValue()     // Catch:{ Throwable -> 0x0032 }
            r9[r10] = r0     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r0 = java.lang.String.format(r3, r9)     // Catch:{ Throwable -> 0x0032 }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.c(r0, r3)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0247
        L_0x0272:
            java.lang.String r0 = "[Upload] Failed to upload for no status header."
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.c(r0, r1)     // Catch:{ Throwable -> 0x0032 }
            r3 = 1
            r1 = r4
            r0 = r2
            goto L_0x017d
        L_0x0280:
            java.lang.String r0 = "status"
            java.lang.Object r0 = r3.get(r0)     // Catch:{ Throwable -> 0x0317 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0317 }
            int r5 = java.lang.Integer.parseInt(r0)     // Catch:{ Throwable -> 0x0317 }
            java.lang.String r0 = "[Upload] Status from server is %d (pid=%d | tid=%d)."
            r9 = 3
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x0317 }
            r10 = 0
            java.lang.Integer r11 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x0317 }
            r9[r10] = r11     // Catch:{ Throwable -> 0x0317 }
            r10 = 1
            int r11 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x0317 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ Throwable -> 0x0317 }
            r9[r10] = r11     // Catch:{ Throwable -> 0x0317 }
            r10 = 2
            int r11 = android.os.Process.myTid()     // Catch:{ Throwable -> 0x0317 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ Throwable -> 0x0317 }
            r9[r10] = r11     // Catch:{ Throwable -> 0x0317 }
            com.tencent.bugly.proguard.an.c(r0, r9)     // Catch:{ Throwable -> 0x0317 }
            if (r5 == 0) goto L_0x0356
            r0 = 2
            if (r5 != r0) goto L_0x0339
            long r0 = r12.d     // Catch:{ Throwable -> 0x0032 }
            long r2 = r12.e     // Catch:{ Throwable -> 0x0032 }
            long r0 = r0 + r2
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x02d8
            com.tencent.bugly.proguard.ak r0 = r12.m     // Catch:{ Throwable -> 0x0032 }
            boolean r1 = r12.t     // Catch:{ Throwable -> 0x0032 }
            long r0 = r0.a(r1)     // Catch:{ Throwable -> 0x0032 }
            long r2 = r12.d     // Catch:{ Throwable -> 0x0032 }
            long r0 = r0 + r2
            long r2 = r12.e     // Catch:{ Throwable -> 0x0032 }
            long r0 = r0 + r2
            com.tencent.bugly.proguard.ak r2 = r12.m     // Catch:{ Throwable -> 0x0032 }
            boolean r3 = r12.t     // Catch:{ Throwable -> 0x0032 }
            r2.a(r0, r3)     // Catch:{ Throwable -> 0x0032 }
        L_0x02d8:
            com.tencent.bugly.proguard.ak r0 = r12.m     // Catch:{ Throwable -> 0x0032 }
            r1 = 0
            r0.a(r5, r1)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r0 = "[Upload] Session ID is invalid, will try again immediately (pid=%d | tid=%d)."
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0032 }
            r2 = 0
            int r3 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x0032 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x0032 }
            r1[r2] = r3     // Catch:{ Throwable -> 0x0032 }
            r2 = 1
            int r3 = android.os.Process.myTid()     // Catch:{ Throwable -> 0x0032 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x0032 }
            r1[r2] = r3     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.a(r0, r1)     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.ak r0 = r12.m     // Catch:{ Throwable -> 0x0032 }
            int r1 = r12.n     // Catch:{ Throwable -> 0x0032 }
            int r2 = r12.h     // Catch:{ Throwable -> 0x0032 }
            byte[] r3 = r12.i     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r4 = r12.q     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r5 = r12.r     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.aj r6 = r12.o     // Catch:{ Throwable -> 0x0032 }
            int r7 = r12.a     // Catch:{ Throwable -> 0x0032 }
            int r8 = r12.b     // Catch:{ Throwable -> 0x0032 }
            r9 = 1
            java.util.Map<java.lang.String, java.lang.String> r10 = r12.s     // Catch:{ Throwable -> 0x0032 }
            r0.a(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x0317:
            r0 = move-exception
            r0 = 1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0032 }
            r1.<init>()     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r3 = "[Upload] Failed to upload for format of status header is invalid: "
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r3 = java.lang.Integer.toString(r5)     // Catch:{ Throwable -> 0x0032 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0032 }
            r12.a(r0, r1)     // Catch:{ Throwable -> 0x0032 }
            r3 = 1
            r1 = r4
            r0 = r2
            goto L_0x017d
        L_0x0339:
            r1 = 0
            r2 = 0
            r3 = 1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0032 }
            r0.<init>()     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r4 = "status of server is "
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x0032 }
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r4 = r0.toString()     // Catch:{ Throwable -> 0x0032 }
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x0356:
            java.lang.String r0 = "[Upload] Received %d bytes"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0032 }
            r4 = 0
            int r6 = r1.length     // Catch:{ Throwable -> 0x0032 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x0032 }
            r2[r4] = r6     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.c(r0, r2)     // Catch:{ Throwable -> 0x0032 }
            boolean r0 = r12.f     // Catch:{ Throwable -> 0x0032 }
            if (r0 == 0) goto L_0x03d0
            int r0 = r1.length     // Catch:{ Throwable -> 0x0032 }
            if (r0 != 0) goto L_0x03a7
            java.util.Set r0 = r3.entrySet()     // Catch:{ Throwable -> 0x0032 }
            java.util.Iterator r1 = r0.iterator()     // Catch:{ Throwable -> 0x0032 }
        L_0x0376:
            boolean r0 = r1.hasNext()     // Catch:{ Throwable -> 0x0032 }
            if (r0 == 0) goto L_0x039a
            java.lang.Object r0 = r1.next()     // Catch:{ Throwable -> 0x0032 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r2 = "[Upload] HTTP headers from server: key = %s, value = %s"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0032 }
            r4 = 0
            java.lang.Object r5 = r0.getKey()     // Catch:{ Throwable -> 0x0032 }
            r3[r4] = r5     // Catch:{ Throwable -> 0x0032 }
            r4 = 1
            java.lang.Object r0 = r0.getValue()     // Catch:{ Throwable -> 0x0032 }
            r3[r4] = r0     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.c(r2, r3)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0376
        L_0x039a:
            r1 = 0
            r2 = 0
            r3 = 1
            java.lang.String r4 = "response data from server is empty"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x03a7:
            com.tencent.bugly.proguard.ak r0 = r12.m     // Catch:{ Throwable -> 0x0032 }
            byte[] r0 = r0.b(r1)     // Catch:{ Throwable -> 0x0032 }
            if (r0 != 0) goto L_0x03bc
            r1 = 0
            r2 = 0
            r3 = 1
            java.lang.String r4 = "failed to decrypt response from server"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x03bc:
            r1 = 2
            byte[] r0 = com.tencent.bugly.proguard.ap.b(r0, r1)     // Catch:{ Throwable -> 0x0032 }
            if (r0 != 0) goto L_0x03d1
            r1 = 0
            r2 = 0
            r3 = 1
            java.lang.String r4 = "failed unzip(Gzip) response from server"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x03d0:
            r0 = r1
        L_0x03d1:
            boolean r1 = r12.f     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.be r1 = com.tencent.bugly.proguard.ah.a(r0, r7, r1)     // Catch:{ Throwable -> 0x0032 }
            if (r1 != 0) goto L_0x03e6
            r1 = 0
            r2 = 0
            r3 = 1
            java.lang.String r4 = "failed to decode response package"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x03e6:
            boolean r0 = r12.f     // Catch:{ Throwable -> 0x0032 }
            if (r0 == 0) goto L_0x03ef
            com.tencent.bugly.proguard.ak r0 = r12.m     // Catch:{ Throwable -> 0x0032 }
            r0.a(r5, r1)     // Catch:{ Throwable -> 0x0032 }
        L_0x03ef:
            java.lang.String r2 = "[Upload] Response cmd is: %d, length of sBuffer is: %d"
            r0 = 2
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x0032 }
            r0 = 0
            int r4 = r1.b     // Catch:{ Throwable -> 0x0032 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0032 }
            r3[r0] = r4     // Catch:{ Throwable -> 0x0032 }
            r4 = 1
            byte[] r0 = r1.c     // Catch:{ Throwable -> 0x0032 }
            if (r0 != 0) goto L_0x0423
            r0 = 0
        L_0x0404:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x0032 }
            r3[r4] = r0     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.proguard.an.c(r2, r3)     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.crashreport.common.info.a r0 = r12.j     // Catch:{ Throwable -> 0x0032 }
            com.tencent.bugly.crashreport.common.strategy.a r2 = r12.k     // Catch:{ Throwable -> 0x0032 }
            boolean r0 = r12.a(r1, r0, r2)     // Catch:{ Throwable -> 0x0032 }
            if (r0 != 0) goto L_0x0427
            r2 = 0
            r3 = 2
            java.lang.String r4 = "failed to process response package"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x0423:
            byte[] r0 = r1.c     // Catch:{ Throwable -> 0x0032 }
            int r0 = r0.length     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0404
        L_0x0427:
            r2 = 1
            r3 = 2
            java.lang.String r4 = "successfully uploaded"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x0433:
            r1 = 0
            r2 = 0
            java.lang.String r4 = "failed after many attempts"
            r5 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x0020
        L_0x043f:
            r2 = r0
            goto L_0x01ca
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.al.run():void");
    }

    public void a(String str, long j2, String str2) {
        this.c++;
        this.d += j2;
    }

    public void a(long j2) {
        this.e += j2;
    }

    private static String a(String str) {
        if (ap.a(str)) {
            return str;
        }
        try {
            return String.format("%s?aid=%s", new Object[]{str, UUID.randomUUID().toString()});
        } catch (Throwable th) {
            an.a(th);
            return str;
        }
    }

    private static boolean a(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            an.d("[Upload] Headers is empty.", new Object[0]);
            return false;
        } else if (!map.containsKey(NotificationCompat.CATEGORY_STATUS)) {
            an.d("[Upload] Headers does not contain %s", NotificationCompat.CATEGORY_STATUS);
            return false;
        } else if (!map.containsKey("Bugly-Version")) {
            an.d("[Upload] Headers does not contain %s", "Bugly-Version");
            return false;
        } else {
            String str = (String) map.get("Bugly-Version");
            if (!str.contains("bugly")) {
                an.d("[Upload] Bugly version is not valid: %s", str);
                return false;
            }
            an.c("[Upload] Bugly version from headers is: %s", str);
            return true;
        }
    }
}
