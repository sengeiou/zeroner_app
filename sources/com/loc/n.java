package com.loc;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;

/* compiled from: ClientInfo */
public final class n {

    /* compiled from: ClientInfo */
    private static class a {
        String a;
        String b;
        String c;
        String d;
        String e;
        String f;
        String g;
        String h;
        String i;
        String j;
        String k;
        String l;
        String m;
        String n;
        String o;
        String p;
        String q;
        String r;
        String s;
        String t;
        String u;
        String v;
        String w;
        String x;
        String y;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public static String a() {
        Throwable th;
        String str;
        String str2 = null;
        try {
            String valueOf = String.valueOf(System.currentTimeMillis());
            String str3 = "1";
            try {
                if (!l.a()) {
                    str3 = "0";
                }
                int length = valueOf.length();
                return valueOf.substring(0, length - 2) + str3 + valueOf.substring(length - 1);
            } catch (Throwable th2) {
                Throwable th3 = th2;
                str = valueOf;
                th = th3;
                ag.a(th, "CI", "TS");
                return str;
            }
        } catch (Throwable th4) {
            Throwable th5 = th4;
            str = str2;
            th = th5;
            ag.a(th, "CI", "TS");
            return str;
        }
    }

    public static String a(Context context, String str, String str2) {
        boolean z = false;
        try {
            return s.b(l.e(context) + ":" + str.substring(0, str.length() - 3) + ":" + str2);
        } catch (Throwable th) {
            ag.a(th, "CI", "Sco");
            return z;
        }
    }

    private static void a(ByteArrayOutputStream byteArrayOutputStream, String str) {
        if (!TextUtils.isEmpty(str)) {
            w.a(byteArrayOutputStream, str.getBytes().length > 255 ? -1 : (byte) str.getBytes().length, w.a(str));
        } else {
            w.a(byteArrayOutputStream, 0, new byte[0]);
        }
    }

    public static byte[] a(Context context, boolean z) {
        try {
            a aVar = new a(0);
            aVar.a = p.v(context);
            aVar.b = p.m(context);
            String h = p.h(context);
            if (h == null) {
                h = "";
            }
            aVar.c = h;
            aVar.d = l.c(context);
            aVar.e = Build.MODEL;
            aVar.f = Build.MANUFACTURER;
            aVar.g = Build.DEVICE;
            aVar.h = l.b(context);
            aVar.i = l.d(context);
            aVar.j = String.valueOf(VERSION.SDK_INT);
            aVar.k = p.y(context);
            aVar.l = p.u(context);
            aVar.m = p.r(context);
            aVar.n = p.q(context);
            aVar.o = p.A(context);
            aVar.p = p.p(context);
            if (z) {
                aVar.q = "";
            } else {
                aVar.q = p.l(context);
            }
            if (z) {
                aVar.r = "";
            } else {
                aVar.r = p.k(context);
            }
            if (z) {
                aVar.s = "";
                aVar.t = "";
            } else {
                String[] n = p.n(context);
                aVar.s = n[0];
                aVar.t = n[1];
            }
            aVar.w = p.a();
            String b = p.b(context);
            if (!TextUtils.isEmpty(b)) {
                aVar.x = b;
            } else {
                aVar.x = "";
            }
            aVar.y = "aid=" + p.j(context) + "|serial=" + p.i(context) + "|storage=" + p.c() + "|ram=" + p.z(context) + "|arch=" + p.d();
            String a2 = p.a(context);
            if (!TextUtils.isEmpty(a2)) {
                aVar.y += "|adiuExtras=" + a2;
            }
            String a3 = p.a(context, ",");
            if (!TextUtils.isEmpty(a3)) {
                aVar.y += "|multiImeis=" + a3;
            }
            String x = p.x(context);
            if (!TextUtils.isEmpty(x)) {
                aVar.y += "|meid=" + x;
            }
            return a(aVar);
        } catch (Throwable th) {
            ag.a(th, "CI", "gz");
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00e4 A[SYNTHETIC, Splitter:B:27:0x00e4] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] a(com.loc.n.a r8) {
        /*
            r1 = 0
            r5 = 117(0x75, float:1.64E-43)
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x00c9, all -> 0x00e0 }
            r2.<init>()     // Catch:{ Throwable -> 0x00c9, all -> 0x00e0 }
            java.lang.String r0 = r8.a     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.b     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.c     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.d     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.e     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.f     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.g     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.h     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.i     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.j     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.k     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.l     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.m     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.n     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.o     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.p     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.q     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.r     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.s     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.t     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.u     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.v     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.w     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.x     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = r8.y     // Catch:{ Throwable -> 0x00ef }
            a(r2, r0)     // Catch:{ Throwable -> 0x00ef }
            byte[] r0 = r2.toByteArray()     // Catch:{ Throwable -> 0x00ef }
            byte[] r3 = com.loc.w.b(r0)     // Catch:{ Throwable -> 0x00ef }
            java.security.PublicKey r0 = com.loc.w.d()     // Catch:{ Throwable -> 0x00ef }
            int r4 = r3.length     // Catch:{ Throwable -> 0x00ef }
            if (r4 <= r5) goto L_0x00bf
            r4 = 117(0x75, float:1.64E-43)
            byte[] r4 = new byte[r4]     // Catch:{ Throwable -> 0x00ef }
            r5 = 0
            r6 = 0
            r7 = 117(0x75, float:1.64E-43)
            java.lang.System.arraycopy(r3, r5, r4, r6, r7)     // Catch:{ Throwable -> 0x00ef }
            byte[] r4 = com.loc.q.a(r4, r0)     // Catch:{ Throwable -> 0x00ef }
            int r0 = r3.length     // Catch:{ Throwable -> 0x00ef }
            int r0 = r0 + 128
            int r0 = r0 + -117
            byte[] r0 = new byte[r0]     // Catch:{ Throwable -> 0x00ef }
            r5 = 0
            r6 = 0
            r7 = 128(0x80, float:1.794E-43)
            java.lang.System.arraycopy(r4, r5, r0, r6, r7)     // Catch:{ Throwable -> 0x00ef }
            r4 = 117(0x75, float:1.64E-43)
            r5 = 128(0x80, float:1.794E-43)
            int r6 = r3.length     // Catch:{ Throwable -> 0x00ef }
            int r6 = r6 + -117
            java.lang.System.arraycopy(r3, r4, r0, r5, r6)     // Catch:{ Throwable -> 0x00ef }
        L_0x00bb:
            r2.close()     // Catch:{ Throwable -> 0x00c4 }
        L_0x00be:
            return r0
        L_0x00bf:
            byte[] r0 = com.loc.q.a(r3, r0)     // Catch:{ Throwable -> 0x00ef }
            goto L_0x00bb
        L_0x00c4:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00be
        L_0x00c9:
            r0 = move-exception
            r2 = r1
        L_0x00cb:
            java.lang.String r3 = "CI"
            java.lang.String r4 = "gzx"
            com.loc.ag.a(r0, r3, r4)     // Catch:{ all -> 0x00ed }
            if (r2 == 0) goto L_0x00d9
            r2.close()     // Catch:{ Throwable -> 0x00db }
        L_0x00d9:
            r0 = r1
            goto L_0x00be
        L_0x00db:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00d9
        L_0x00e0:
            r0 = move-exception
            r2 = r1
        L_0x00e2:
            if (r2 == 0) goto L_0x00e7
            r2.close()     // Catch:{ Throwable -> 0x00e8 }
        L_0x00e7:
            throw r0
        L_0x00e8:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00e7
        L_0x00ed:
            r0 = move-exception
            goto L_0x00e2
        L_0x00ef:
            r0 = move-exception
            goto L_0x00cb
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.n.a(com.loc.n$a):byte[]");
    }
}
