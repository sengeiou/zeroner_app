package com.loc;

import android.content.Context;

/* compiled from: LocNetManager */
public final class dd {
    private static dd b = null;
    bm a = null;
    private Context c = null;
    private int d = 0;
    private int e = di.f;
    private boolean f = false;
    private int g = 0;

    private dd(Context context) {
        try {
            r.a().a(context);
        } catch (Throwable th) {
        }
        this.c = context;
        this.a = bm.a();
    }

    public static dd a(Context context) {
        if (b == null) {
            b = new dd(context);
        }
        return b;
    }

    public final int a() {
        return this.d;
    }

    public final br a(de deVar) throws Throwable {
        long c2 = dr.c();
        boolean z = this.f || dr.k(this.c);
        bm bmVar = this.a;
        br a2 = bm.a(deVar, z);
        this.d = Long.valueOf(dr.c() - c2).intValue();
        return a2;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.loc.de a(android.content.Context r8, byte[] r9, java.lang.String r10, boolean r11) {
        /*
            r7 = this;
            r1 = 0
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ Throwable -> 0x0130 }
            r0 = 16
            r2.<init>(r0)     // Catch:{ Throwable -> 0x0130 }
            com.loc.de r0 = new com.loc.de     // Catch:{ Throwable -> 0x0130 }
            com.loc.v r3 = com.loc.di.b()     // Catch:{ Throwable -> 0x0130 }
            r0.<init>(r8, r3)     // Catch:{ Throwable -> 0x0130 }
            java.lang.String r1 = "Content-Type"
            java.lang.String r3 = "application/octet-stream"
            r2.put(r1, r3)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r1 = "Accept-Encoding"
            java.lang.String r3 = "gzip"
            r2.put(r1, r3)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r1 = "gzipped"
            java.lang.String r3 = "1"
            r2.put(r1, r3)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r1 = "Connection"
            java.lang.String r3 = "Keep-Alive"
            r2.put(r1, r3)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r1 = "User-Agent"
            java.lang.String r3 = "AMAP_Location_SDK_Android 4.7.0"
            r2.put(r1, r3)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r1 = "KEY"
            java.lang.String r3 = com.loc.l.f(r8)     // Catch:{ Throwable -> 0x011a }
            r2.put(r1, r3)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r1 = "enginever"
            java.lang.String r3 = "5.1"
            r2.put(r1, r3)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r1 = com.loc.n.a()     // Catch:{ Throwable -> 0x011a }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x011a }
            java.lang.String r4 = "key="
            r3.<init>(r4)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r4 = com.loc.l.f(r8)     // Catch:{ Throwable -> 0x011a }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x011a }
            java.lang.String r3 = com.loc.n.a(r8, r1, r3)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r4 = "ts"
            r2.put(r4, r1)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r1 = "scode"
            r2.put(r1, r3)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r1 = "encr"
            java.lang.String r3 = "1"
            r2.put(r1, r3)     // Catch:{ Throwable -> 0x011a }
            r0.f = r2     // Catch:{ Throwable -> 0x011a }
            java.lang.String r1 = "loc"
            if (r11 != 0) goto L_0x008c
            java.lang.String r1 = "locf"
        L_0x008c:
            r2 = 1
            r0.m = r2     // Catch:{ Throwable -> 0x011a }
            java.util.Locale r2 = java.util.Locale.US     // Catch:{ Throwable -> 0x011a }
            java.lang.String r3 = "platform=Android&sdkversion=%s&product=%s&loc_channel=%s"
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x011a }
            r5 = 0
            java.lang.String r6 = "4.7.0"
            r4[r5] = r6     // Catch:{ Throwable -> 0x011a }
            r5 = 1
            r4[r5] = r1     // Catch:{ Throwable -> 0x011a }
            r1 = 2
            r5 = 3
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x011a }
            r4[r1] = r5     // Catch:{ Throwable -> 0x011a }
            java.lang.String r1 = java.lang.String.format(r2, r3, r4)     // Catch:{ Throwable -> 0x011a }
            r0.k = r1     // Catch:{ Throwable -> 0x011a }
            r0.j = r11     // Catch:{ Throwable -> 0x011a }
            r0.g = r10     // Catch:{ Throwable -> 0x011a }
            byte[] r1 = com.loc.dr.a(r9)     // Catch:{ Throwable -> 0x011a }
            r0.h = r1     // Catch:{ Throwable -> 0x011a }
            java.net.Proxy r1 = com.loc.t.a(r8)     // Catch:{ Throwable -> 0x011a }
            r0.a(r1)     // Catch:{ Throwable -> 0x011a }
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ Throwable -> 0x011a }
            r2 = 16
            r1.<init>(r2)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r2 = "output"
            java.lang.String r3 = "bin"
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x011a }
            java.lang.String r2 = "policy"
            java.lang.String r3 = "3103"
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x011a }
            int r2 = r7.g     // Catch:{ Throwable -> 0x011a }
            switch(r2) {
                case 0: goto L_0x0113;
                case 1: goto L_0x011c;
                case 2: goto L_0x0126;
                default: goto L_0x00dd;
            }     // Catch:{ Throwable -> 0x011a }
        L_0x00dd:
            java.lang.String r2 = "custom"
            r1.remove(r2)     // Catch:{ Throwable -> 0x011a }
        L_0x00e3:
            r0.l = r1     // Catch:{ Throwable -> 0x011a }
            int r1 = r7.e     // Catch:{ Throwable -> 0x011a }
            r0.a(r1)     // Catch:{ Throwable -> 0x011a }
            int r1 = r7.e     // Catch:{ Throwable -> 0x011a }
            r0.b(r1)     // Catch:{ Throwable -> 0x011a }
            boolean r1 = r7.f     // Catch:{ Throwable -> 0x011a }
            if (r1 != 0) goto L_0x00f9
            boolean r1 = com.loc.dr.k(r8)     // Catch:{ Throwable -> 0x011a }
            if (r1 == 0) goto L_0x0112
        L_0x00f9:
            java.lang.String r1 = "http:"
            boolean r1 = r10.startsWith(r1)     // Catch:{ Throwable -> 0x011a }
            if (r1 == 0) goto L_0x0112
            java.lang.String r1 = r0.c()     // Catch:{ Throwable -> 0x011a }
            java.lang.String r2 = "https:"
            java.lang.String r3 = "https:"
            java.lang.String r1 = r1.replace(r2, r3)     // Catch:{ Throwable -> 0x011a }
            r0.g = r1     // Catch:{ Throwable -> 0x011a }
        L_0x0112:
            return r0
        L_0x0113:
            java.lang.String r2 = "custom"
            r1.remove(r2)     // Catch:{ Throwable -> 0x011a }
            goto L_0x00e3
        L_0x011a:
            r1 = move-exception
            goto L_0x0112
        L_0x011c:
            java.lang.String r2 = "custom"
            java.lang.String r3 = "language:cn"
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x011a }
            goto L_0x00e3
        L_0x0126:
            java.lang.String r2 = "custom"
            java.lang.String r3 = "language:en"
            r1.put(r2, r3)     // Catch:{ Throwable -> 0x011a }
            goto L_0x00e3
        L_0x0130:
            r0 = move-exception
            r0 = r1
            goto L_0x0112
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.dd.a(android.content.Context, byte[], java.lang.String, boolean):com.loc.de");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x00d7 A[Catch:{ Throwable -> 0x011e, Throwable -> 0x00fb }] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0114 A[SYNTHETIC, Splitter:B:19:0x0114] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String a(android.content.Context r11, double r12, double r14) {
        /*
            r10 = this;
            r1 = 0
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Throwable -> 0x00fb }
            r2 = 16
            r0.<init>(r2)     // Catch:{ Throwable -> 0x00fb }
            com.loc.de r2 = new com.loc.de     // Catch:{ Throwable -> 0x00fb }
            com.loc.v r3 = com.loc.di.b()     // Catch:{ Throwable -> 0x00fb }
            r2.<init>(r11, r3)     // Catch:{ Throwable -> 0x00fb }
            r0.clear()     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r3 = "Content-Type"
            java.lang.String r4 = "application/x-www-form-urlencoded"
            r0.put(r3, r4)     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r3 = "Connection"
            java.lang.String r4 = "Keep-Alive"
            r0.put(r3, r4)     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r3 = "User-Agent"
            java.lang.String r4 = "AMAP_Location_SDK_Android 4.7.0"
            r0.put(r3, r4)     // Catch:{ Throwable -> 0x00fb }
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ Throwable -> 0x00fb }
            r4 = 16
            r3.<init>(r4)     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r4 = "custom"
            java.lang.String r5 = "26260A1F00020002"
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r4 = "key"
            java.lang.String r5 = com.loc.l.f(r11)     // Catch:{ Throwable -> 0x00fb }
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x00fb }
            int r4 = r10.g     // Catch:{ Throwable -> 0x00fb }
            switch(r4) {
                case 0: goto L_0x00f3;
                case 1: goto L_0x00fe;
                case 2: goto L_0x0109;
                default: goto L_0x004e;
            }     // Catch:{ Throwable -> 0x00fb }
        L_0x004e:
            java.lang.String r4 = "language"
            r3.remove(r4)     // Catch:{ Throwable -> 0x00fb }
        L_0x0054:
            java.lang.String r4 = com.loc.n.a()     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r5 = com.loc.w.b(r3)     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r5 = com.loc.n.a(r11, r4, r5)     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r6 = "ts"
            r3.put(r6, r4)     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r4 = "scode"
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x00fb }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r5 = "output=json&radius=1000&extensions=all&location="
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00fb }
            java.lang.StringBuilder r4 = r4.append(r14)     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r5 = ","
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x00fb }
            java.lang.StringBuilder r4 = r4.append(r12)     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r5 = "UTF-8"
            byte[] r4 = r4.getBytes(r5)     // Catch:{ Throwable -> 0x00fb }
            r2.b(r4)     // Catch:{ Throwable -> 0x00fb }
            r4 = 0
            r2.m = r4     // Catch:{ Throwable -> 0x00fb }
            r4 = 1
            r2.j = r4     // Catch:{ Throwable -> 0x00fb }
            java.util.Locale r4 = java.util.Locale.US     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r5 = "platform=Android&sdkversion=%s&product=%s&loc_channel=%s"
            r6 = 3
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x00fb }
            r7 = 0
            java.lang.String r8 = "4.7.0"
            r6[r7] = r8     // Catch:{ Throwable -> 0x00fb }
            r7 = 1
            java.lang.String r8 = "loc"
            r6[r7] = r8     // Catch:{ Throwable -> 0x00fb }
            r7 = 2
            r8 = 3
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Throwable -> 0x00fb }
            r6[r7] = r8     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r4 = java.lang.String.format(r4, r5, r6)     // Catch:{ Throwable -> 0x00fb }
            r2.k = r4     // Catch:{ Throwable -> 0x00fb }
            r2.l = r3     // Catch:{ Throwable -> 0x00fb }
            r2.f = r0     // Catch:{ Throwable -> 0x00fb }
            java.net.Proxy r0 = com.loc.t.a(r11)     // Catch:{ Throwable -> 0x00fb }
            r2.a(r0)     // Catch:{ Throwable -> 0x00fb }
            int r0 = com.loc.di.f     // Catch:{ Throwable -> 0x00fb }
            r2.a(r0)     // Catch:{ Throwable -> 0x00fb }
            int r0 = com.loc.di.f     // Catch:{ Throwable -> 0x00fb }
            r2.b(r0)     // Catch:{ Throwable -> 0x00fb }
            java.lang.String r0 = "http://restapi.amap.com/v3/geocode/regeo"
            boolean r3 = com.loc.dr.k(r11)     // Catch:{ Throwable -> 0x011e }
            if (r3 == 0) goto L_0x0114
            java.lang.String r3 = "http:"
            java.lang.String r4 = "https:"
            java.lang.String r0 = r0.replace(r3, r4)     // Catch:{ Throwable -> 0x011e }
            r2.g = r0     // Catch:{ Throwable -> 0x011e }
            com.loc.bm r0 = r10.a     // Catch:{ Throwable -> 0x011e }
            byte[] r0 = com.loc.bm.a(r2)     // Catch:{ Throwable -> 0x011e }
            r2 = r0
        L_0x00ea:
            java.lang.String r0 = new java.lang.String     // Catch:{ Throwable -> 0x011e }
            java.lang.String r3 = "utf-8"
            r0.<init>(r2, r3)     // Catch:{ Throwable -> 0x011e }
        L_0x00f2:
            return r0
        L_0x00f3:
            java.lang.String r4 = "language"
            r3.remove(r4)     // Catch:{ Throwable -> 0x00fb }
            goto L_0x0054
        L_0x00fb:
            r0 = move-exception
            r0 = r1
            goto L_0x00f2
        L_0x00fe:
            java.lang.String r4 = "language"
            java.lang.String r5 = "zh-CN"
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x00fb }
            goto L_0x0054
        L_0x0109:
            java.lang.String r4 = "language"
            java.lang.String r5 = "en"
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x00fb }
            goto L_0x0054
        L_0x0114:
            r2.g = r0     // Catch:{ Throwable -> 0x011e }
            com.loc.bm r0 = r10.a     // Catch:{ Throwable -> 0x011e }
            byte[] r0 = com.loc.bm.b(r2)     // Catch:{ Throwable -> 0x011e }
            r2 = r0
            goto L_0x00ea
        L_0x011e:
            r0 = move-exception
            java.lang.String r2 = "LocNetManager"
            java.lang.String r3 = "post"
            com.loc.di.a(r0, r2, r3)     // Catch:{ Throwable -> 0x00fb }
            r0 = r1
            goto L_0x00f2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.dd.a(android.content.Context, double, double):java.lang.String");
    }

    public final void a(long j, boolean z, int i) {
        try {
            this.f = z;
            try {
                r.a().a(z);
            } catch (Throwable th) {
            }
            this.e = Long.valueOf(j).intValue();
            this.g = i;
        } catch (Throwable th2) {
            di.a(th2, "LocNetManager", "setOption");
        }
    }
}