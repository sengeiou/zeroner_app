package com.loc;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ErrorLogManager */
public class ak {
    /* access modifiers changed from: private */
    public static WeakReference<bs> a;
    private static boolean b = true;
    private static WeakReference<cm> c;
    private static WeakReference<cm> d;
    private static String[] e = new String[10];
    private static int f = 0;
    private static boolean g = false;
    private static int h = 0;
    private static v i;

    private static v a(Context context, String str) {
        List<v> c2 = ah.c(context);
        if (c2 == null) {
            c2 = new ArrayList<>();
        }
        if (str == null || "".equals(str)) {
            return null;
        }
        for (v vVar : c2) {
            if (ah.a(vVar.f(), str)) {
                return vVar;
            }
        }
        if (str.contains("com.amap.api.col")) {
            try {
                return w.a();
            } catch (k e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
        if (str.contains("com.amap.co") || str.contains("com.amap.opensdk.co") || str.contains("com.amap.location")) {
            try {
                v b2 = w.b();
                b2.a(true);
                return b2;
            } catch (k e3) {
                ThrowableExtension.printStackTrace(e3);
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0180, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0188, code lost:
        r0 = r2;
        r2 = r3;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0187 A[ExcHandler: FileNotFoundException (e java.io.FileNotFoundException), Splitter:B:19:0x0039] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00c4 A[Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }, ExcHandler: EOFException (e java.io.EOFException), Splitter:B:44:0x0090] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00e0 A[SYNTHETIC, Splitter:B:68:0x00e0] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00e5 A[SYNTHETIC, Splitter:B:71:0x00e5] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00fa A[SYNTHETIC, Splitter:B:79:0x00fa] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00ff A[SYNTHETIC, Splitter:B:82:0x00ff] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0103  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x010b A[SYNTHETIC, Splitter:B:89:0x010b] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0110 A[SYNTHETIC, Splitter:B:92:0x0110] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.util.List<com.loc.v> r8) {
        /*
            r6 = 1024000(0xfa000, float:1.43493E-39)
            r5 = 0
            r1 = 0
            r0 = 0
            r2 = 0
            java.io.File r4 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0106, Throwable -> 0x00d2, all -> 0x00f5 }
            java.lang.String r3 = "/data/anr/traces.txt"
            r4.<init>(r3)     // Catch:{ FileNotFoundException -> 0x0106, Throwable -> 0x00d2, all -> 0x00f5 }
            boolean r3 = r4.exists()     // Catch:{ FileNotFoundException -> 0x0106, Throwable -> 0x00d2, all -> 0x00f5 }
            if (r3 != 0) goto L_0x0021
            if (r1 == 0) goto L_0x001a
            r2.close()     // Catch:{ Throwable -> 0x0149 }
        L_0x001a:
            if (r1 == 0) goto L_0x001f
            r0.close()     // Catch:{ Throwable -> 0x0155 }
        L_0x001f:
            r0 = r1
        L_0x0020:
            return r0
        L_0x0021:
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0106, Throwable -> 0x00d2, all -> 0x00f5 }
            r3.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0106, Throwable -> 0x00d2, all -> 0x00f5 }
            int r0 = r3.available()     // Catch:{ FileNotFoundException -> 0x0183, Throwable -> 0x017c, all -> 0x0176 }
            if (r0 <= r6) goto L_0x0031
            int r0 = r0 - r6
            long r6 = (long) r0     // Catch:{ FileNotFoundException -> 0x0183, Throwable -> 0x017c, all -> 0x0176 }
            r3.skip(r6)     // Catch:{ FileNotFoundException -> 0x0183, Throwable -> 0x017c, all -> 0x0176 }
        L_0x0031:
            com.loc.bj r2 = new com.loc.bj     // Catch:{ FileNotFoundException -> 0x0183, Throwable -> 0x017c, all -> 0x0176 }
            java.nio.charset.Charset r0 = com.loc.bk.a     // Catch:{ FileNotFoundException -> 0x0183, Throwable -> 0x017c, all -> 0x0176 }
            r2.<init>(r3, r0)     // Catch:{ FileNotFoundException -> 0x0183, Throwable -> 0x017c, all -> 0x0176 }
            r4 = r5
        L_0x0039:
            java.lang.String r0 = r2.a()     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            java.lang.String r0 = r0.trim()     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            java.lang.String r5 = "pid"
            boolean r5 = r0.contains(r5)     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            if (r5 == 0) goto L_0x018c
        L_0x004a:
            java.lang.String r4 = "\"main\""
            boolean r4 = r0.startsWith(r4)     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            if (r4 != 0) goto L_0x0058
            java.lang.String r0 = r2.a()     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            goto L_0x004a
        L_0x0058:
            r4 = 1
            r5 = r4
            r4 = r0
        L_0x005b:
            java.lang.String r0 = ""
            boolean r0 = r4.equals(r0)     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            if (r0 == 0) goto L_0x0079
            if (r5 == 0) goto L_0x0079
        L_0x0066:
            if (r2 == 0) goto L_0x006b
            r2.close()     // Catch:{ Throwable -> 0x0161 }
        L_0x006b:
            if (r3 == 0) goto L_0x0070
            r3.close()     // Catch:{ Throwable -> 0x016d }
        L_0x0070:
            boolean r0 = g
            if (r0 == 0) goto L_0x0103
            java.lang.String r0 = b()
            goto L_0x0020
        L_0x0079:
            if (r5 == 0) goto L_0x00cf
            int r0 = f     // Catch:{ Throwable -> 0x00b9, EOFException -> 0x00c4, FileNotFoundException -> 0x0187 }
            r6 = 9
            if (r0 <= r6) goto L_0x0084
            r0 = 0
            f = r0     // Catch:{ Throwable -> 0x00b9, EOFException -> 0x00c4, FileNotFoundException -> 0x0187 }
        L_0x0084:
            java.lang.String[] r0 = e     // Catch:{ Throwable -> 0x00b9, EOFException -> 0x00c4, FileNotFoundException -> 0x0187 }
            int r6 = f     // Catch:{ Throwable -> 0x00b9, EOFException -> 0x00c4, FileNotFoundException -> 0x0187 }
            r0[r6] = r4     // Catch:{ Throwable -> 0x00b9, EOFException -> 0x00c4, FileNotFoundException -> 0x0187 }
            int r0 = f     // Catch:{ Throwable -> 0x00b9, EOFException -> 0x00c4, FileNotFoundException -> 0x0187 }
            int r0 = r0 + 1
            f = r0     // Catch:{ Throwable -> 0x00b9, EOFException -> 0x00c4, FileNotFoundException -> 0x0187 }
        L_0x0090:
            int r0 = h     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            r6 = 5
            if (r0 == r6) goto L_0x0066
            boolean r0 = g     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            if (r0 != 0) goto L_0x00c9
            java.util.Iterator r6 = r8.iterator()     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
        L_0x009d:
            boolean r0 = r6.hasNext()     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            if (r0 == 0) goto L_0x00c6
            java.lang.Object r0 = r6.next()     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            com.loc.v r0 = (com.loc.v) r0     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            java.lang.String[] r7 = r0.f()     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            boolean r7 = com.loc.ah.b(r7, r4)     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            g = r7     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            if (r7 == 0) goto L_0x009d
            i = r0     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            r4 = r5
            goto L_0x0039
        L_0x00b9:
            r0 = move-exception
            java.lang.String r6 = "alg"
            java.lang.String r7 = "aDa"
            com.loc.aj.b(r0, r6, r7)     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            goto L_0x0090
        L_0x00c4:
            r0 = move-exception
            goto L_0x0066
        L_0x00c6:
            r4 = r5
            goto L_0x0039
        L_0x00c9:
            int r0 = h     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
            int r0 = r0 + 1
            h = r0     // Catch:{ EOFException -> 0x00c4, FileNotFoundException -> 0x0187, Throwable -> 0x0180 }
        L_0x00cf:
            r4 = r5
            goto L_0x0039
        L_0x00d2:
            r0 = move-exception
            r2 = r1
            r3 = r1
        L_0x00d5:
            java.lang.String r4 = "alg"
            java.lang.String r5 = "getA"
            com.loc.aj.b(r0, r4, r5)     // Catch:{ all -> 0x0179 }
            if (r2 == 0) goto L_0x00e3
            r2.close()     // Catch:{ Throwable -> 0x013e }
        L_0x00e3:
            if (r3 == 0) goto L_0x0070
            r3.close()     // Catch:{ Throwable -> 0x00e9 }
            goto L_0x0070
        L_0x00e9:
            r0 = move-exception
            java.lang.String r2 = "alg"
            java.lang.String r3 = "getA"
        L_0x00f0:
            com.loc.aj.b(r0, r2, r3)
            goto L_0x0070
        L_0x00f5:
            r0 = move-exception
            r2 = r1
            r3 = r1
        L_0x00f8:
            if (r2 == 0) goto L_0x00fd
            r2.close()     // Catch:{ Throwable -> 0x0128 }
        L_0x00fd:
            if (r3 == 0) goto L_0x0102
            r3.close()     // Catch:{ Throwable -> 0x0133 }
        L_0x0102:
            throw r0
        L_0x0103:
            r0 = r1
            goto L_0x0020
        L_0x0106:
            r0 = move-exception
            r0 = r1
            r2 = r1
        L_0x0109:
            if (r0 == 0) goto L_0x010e
            r0.close()     // Catch:{ Throwable -> 0x011d }
        L_0x010e:
            if (r2 == 0) goto L_0x0070
            r2.close()     // Catch:{ Throwable -> 0x0115 }
            goto L_0x0070
        L_0x0115:
            r0 = move-exception
            java.lang.String r2 = "alg"
            java.lang.String r3 = "getA"
            goto L_0x00f0
        L_0x011d:
            r0 = move-exception
            java.lang.String r3 = "alg"
            java.lang.String r4 = "getA"
            com.loc.aj.b(r0, r3, r4)
            goto L_0x010e
        L_0x0128:
            r1 = move-exception
            java.lang.String r2 = "alg"
            java.lang.String r4 = "getA"
            com.loc.aj.b(r1, r2, r4)
            goto L_0x00fd
        L_0x0133:
            r1 = move-exception
            java.lang.String r2 = "alg"
            java.lang.String r3 = "getA"
            com.loc.aj.b(r1, r2, r3)
            goto L_0x0102
        L_0x013e:
            r0 = move-exception
            java.lang.String r2 = "alg"
            java.lang.String r4 = "getA"
            com.loc.aj.b(r0, r2, r4)
            goto L_0x00e3
        L_0x0149:
            r2 = move-exception
            java.lang.String r3 = "alg"
            java.lang.String r4 = "getA"
            com.loc.aj.b(r2, r3, r4)
            goto L_0x001a
        L_0x0155:
            r0 = move-exception
            java.lang.String r2 = "alg"
            java.lang.String r3 = "getA"
            com.loc.aj.b(r0, r2, r3)
            goto L_0x001f
        L_0x0161:
            r0 = move-exception
            java.lang.String r2 = "alg"
            java.lang.String r4 = "getA"
            com.loc.aj.b(r0, r2, r4)
            goto L_0x006b
        L_0x016d:
            r0 = move-exception
            java.lang.String r2 = "alg"
            java.lang.String r3 = "getA"
            goto L_0x00f0
        L_0x0176:
            r0 = move-exception
            r2 = r1
            goto L_0x00f8
        L_0x0179:
            r0 = move-exception
            goto L_0x00f8
        L_0x017c:
            r0 = move-exception
            r2 = r1
            goto L_0x00d5
        L_0x0180:
            r0 = move-exception
            goto L_0x00d5
        L_0x0183:
            r0 = move-exception
            r0 = r1
            r2 = r3
            goto L_0x0109
        L_0x0187:
            r0 = move-exception
            r0 = r2
            r2 = r3
            goto L_0x0109
        L_0x018c:
            r5 = r4
            r4 = r0
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.ak.a(java.util.List):java.lang.String");
    }

    static void a(Context context) {
        List c2 = ah.c(context);
        if (c2 != null && c2.size() != 0) {
            String a2 = a(c2);
            if (a2 != null && !"".equals(a2) && i != null) {
                a(context, i, 2, "ANR", a2);
            }
        }
    }

    private static void a(final Context context, final cm cmVar, final String str) {
        aj.d().submit(new Runnable() {
            public final void run() {
                try {
                    synchronized (ak.class) {
                        bs a2 = bz.a(ak.a);
                        bz.a(context, a2, str, 1000, 40960, "1");
                        a2.f = cmVar;
                        if (a2.g == null) {
                            a2.g = new cd(new cc(context, new ch(), new z(new ab(new ad())), "EImtleSI6IiVzIiwicGxhdGZvcm0iOiJhbmRyb2lkIiwiZGl1IjoiJXMiLCJwa2ciOiIlcyIsIm1vZGVsIjoiJXMiLCJhcHBuYW1lIjoiJXMiLCJhcHB2ZXJzaW9uIjoiJXMiLCJzeXN2ZXJzaW9uIjoiJXMiLA=", l.f(context), p.v(context), l.c(context), Build.MODEL, l.b(context), l.d(context), VERSION.RELEASE));
                        }
                        a2.h = 3600000;
                        bt.a(a2);
                    }
                } catch (Throwable th) {
                    aj.b(th, "lg", "pul");
                }
            }
        });
    }

    private static void a(Context context, v vVar, int i2, String str, String str2) {
        String str3;
        String a2 = w.a(System.currentTimeMillis());
        String a3 = bz.a(context, vVar);
        l.a(context);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(a3).append(",\"timestamp\":\"");
        stringBuffer.append(a2);
        stringBuffer.append("\",\"et\":\"");
        stringBuffer.append(i2);
        stringBuffer.append("\",\"classname\":\"");
        stringBuffer.append(str);
        stringBuffer.append("\",");
        stringBuffer.append("\"detail\":\"");
        stringBuffer.append(str2);
        stringBuffer.append("\"");
        String stringBuffer2 = stringBuffer.toString();
        if (stringBuffer2 != null && !"".equals(stringBuffer2)) {
            String c2 = s.c(str2);
            if (i2 == 1) {
                str3 = ah.b;
            } else if (i2 == 2) {
                str3 = ah.d;
            } else if (i2 == 0) {
                str3 = ah.c;
            } else {
                return;
            }
            bs a4 = bz.a(a);
            bz.a(context, a4, str3, 1000, 40960, "1");
            if (a4.e == null) {
                a4.e = new y(new z(new ab(new ad())));
            }
            try {
                bt.a(c2, w.a(stringBuffer2.replaceAll("\n", "<br/>")), a4);
            } catch (Throwable th) {
            }
        }
    }

    public static void a(Context context, Throwable th, int i2, String str, String str2) {
        String a2 = w.a(th);
        v a3 = a(context, a2);
        if (a(a3)) {
            String replaceAll = a2.replaceAll("\n", "<br/>");
            String th2 = th.toString();
            if (th2 != null && !"".equals(th2)) {
                StringBuilder sb = new StringBuilder();
                if (str != null) {
                    sb.append("class:").append(str);
                }
                if (str2 != null) {
                    sb.append(" method:").append(str2).append("$<br/>");
                }
                sb.append(replaceAll);
                a(context, a3, i2, th2, sb.toString());
            }
        }
    }

    static void a(v vVar, Context context, String str, String str2) {
        if (a(vVar) && str != null && !"".equals(str)) {
            a(context, vVar, 0, str, str2);
        }
    }

    private static boolean a(v vVar) {
        return vVar != null && vVar.e();
    }

    private static String b() {
        StringBuilder sb = new StringBuilder();
        try {
            int i2 = f;
            while (i2 < 10 && i2 <= 9) {
                sb.append(e[i2]);
                i2++;
            }
            for (int i3 = 0; i3 < f; i3++) {
                sb.append(e[i3]);
            }
        } catch (Throwable th) {
            aj.b(th, "alg", "gLI");
        }
        return sb.toString();
    }

    static void b(Context context) {
        ck ckVar = new ck(b);
        b = false;
        a(context, ckVar, ah.c);
    }

    static void b(v vVar, Context context, String str, String str2) {
        if (a(vVar) && str != null && !"".equals(str)) {
            a(context, vVar, 1, str, str2);
        }
    }

    static void c(Context context) {
        if (c == null || c.get() == null) {
            c = new WeakReference<>(new cl(context, 3600000, "hKey", new cn(context)));
        }
        a(context, (cm) c.get(), ah.d);
    }

    static void d(Context context) {
        if (d == null || d.get() == null) {
            d = new WeakReference<>(new cl(context, 3600000, "gKey", new cn(context)));
        }
        a(context, (cm) d.get(), ah.b);
    }
}
