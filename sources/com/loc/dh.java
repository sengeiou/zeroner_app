package com.loc;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.loc.m.a.b;
import java.util.ArrayList;
import java.util.List;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;
import org.json.JSONObject;

/* compiled from: AuthUtil */
public final class dh {
    private static boolean A = true;
    private static int B = -1;
    private static long C = 0;
    private static ArrayList<String> D = new ArrayList<>();
    private static boolean E = false;
    private static int F = -1;
    private static long G = 0;
    private static ArrayList<String> H = new ArrayList<>();
    private static String I;
    private static String J;
    private static boolean K = false;
    private static int L = 3000;
    private static int M = 3000;
    private static boolean N = true;
    private static long O = 300000;
    private static boolean P = false;
    private static List<dl> Q = new ArrayList();
    private static boolean R = false;
    private static long S = 0;
    private static int T = 0;
    private static int U = 0;
    private static List<String> V = new ArrayList();
    private static boolean W = true;
    private static int X = 80;
    private static boolean Y = false;
    private static boolean Z = true;
    public static boolean a = true;
    private static boolean aa = false;
    private static boolean ab = false;
    private static boolean ac = true;
    private static boolean ad = false;
    private static int ae = -1;
    private static boolean af = true;
    private static long ag = -1;
    private static boolean ah = true;
    private static int ai = 1;
    private static long aj = 0;
    static boolean b = false;
    static boolean c = false;
    static int d = 3600000;
    static long e = 0;
    static long f = 0;
    static boolean g = false;
    static boolean h = true;
    static boolean i = false;
    private static boolean j = false;
    private static boolean k = true;
    private static boolean l = false;
    private static long m = 0;
    private static long n = 0;
    private static long o = BootloaderScanner.TIMEOUT;
    private static boolean p = false;
    private static int q = 0;
    private static boolean r = false;
    private static int s = 0;
    private static boolean t = false;
    private static boolean u = true;
    private static int v = 1000;
    private static int w = 200;
    private static boolean x = false;
    private static int y = 20;
    private static boolean z = true;

    /* compiled from: AuthUtil */
    static class a {
        boolean a = false;
        String b = "0";
        boolean c = false;
        int d = 5;

        a() {
        }
    }

    public static int A() {
        return ae;
    }

    public static boolean B() {
        return i;
    }

    public static boolean C() {
        return af;
    }

    public static long D() {
        return ag;
    }

    public static boolean E() {
        return ah && ai > 0;
    }

    public static int F() {
        return ai;
    }

    public static long G() {
        return aj;
    }

    private static a a(JSONObject jSONObject, String str) {
        Throwable th;
        a aVar;
        if (jSONObject != null) {
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject(str);
                if (jSONObject2 != null) {
                    aVar = new a();
                    try {
                        aVar.a = m.a(jSONObject2.optString("b"), false);
                        aVar.b = jSONObject2.optString("t");
                        aVar.c = m.a(jSONObject2.optString("st"), false);
                        aVar.d = jSONObject2.optInt("i", 0);
                        return aVar;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                aVar = null;
                th = th4;
                di.a(th, "AuthUtil", "getLocateObj");
                return aVar;
            }
        }
        return null;
    }

    public static boolean a() {
        return p;
    }

    public static boolean a(long j2) {
        long c2 = dr.c();
        return l && c2 - n <= m && c2 - j2 >= o;
    }

    public static boolean a(Context context) {
        boolean z2 = false;
        z = true;
        try {
            j = dq.b(context, "pref", "oda", false);
            com.loc.m.a a2 = m.a(context, di.b(), di.c(), j);
            if (a2 != null) {
                k = a2.a();
                z2 = a(context, a2);
            }
        } catch (Throwable th) {
            di.a(th, "AuthUtil", "getConfig");
        }
        f = dr.c();
        e = dr.c();
        return z2;
    }

    public static boolean a(Context context, long j2) {
        if (!K) {
            return false;
        }
        long b2 = dr.b();
        if (b2 - j2 < ((long) L)) {
            return false;
        }
        if (M == -1) {
            return true;
        }
        if (!dr.c(b2, dq.b(context, "pref", "ngpsTime", 0))) {
            try {
                Editor edit = context.getSharedPreferences("pref", 0).edit();
                edit.putLong("ngpsTime", b2);
                edit.putInt("ngpsCount", 0);
                dq.a(edit);
            } catch (Throwable th) {
                di.a(th, "AuthUtil", "resetPrefsNGPS");
            }
            dq.a(context, "pref", "ngpsCount", 1);
            return true;
        }
        int b3 = dq.b(context, "pref", "ngpsCount", 0);
        if (b3 >= M) {
            return false;
        }
        dq.a(context, "pref", "ngpsCount", b3 + 1);
        return true;
    }

    private static boolean a(Context context, b bVar, String str, String str2, boolean z2) {
        if (bVar == null) {
            return false;
        }
        try {
            boolean z3 = bVar.a;
            String str3 = bVar.b;
            String str4 = bVar.c;
            String str5 = bVar.d;
            boolean z4 = bVar.e;
            v a2 = di.a(str, str2);
            if (z3) {
                if (z4 && z2 && !TextUtils.isEmpty(str4) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str5)) {
                    av avVar = new av(str3, str4, k);
                    avVar.a(j);
                    ba.b(context, avVar, a2);
                }
            } else if (Cdo.a(context, a2)) {
                dp.a(context, str, "config|get dex able is false");
            }
            return z3 && z4;
        } catch (Throwable th) {
            di.a(th, "AuthUtil", "downLoadPluginDex");
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:128:0x022d A[SYNTHETIC, Splitter:B:128:0x022d] */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x0266 A[Catch:{ Throwable -> 0x0485 }] */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x0307 A[Catch:{ Throwable -> 0x0491, Throwable -> 0x049d }] */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x031c A[Catch:{ Throwable -> 0x0491, Throwable -> 0x049d }] */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x033d A[Catch:{ Throwable -> 0x0491, Throwable -> 0x049d }] */
    /* JADX WARNING: Removed duplicated region for block: B:177:0x035f A[Catch:{ Throwable -> 0x04c7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:186:0x038d A[Catch:{ Throwable -> 0x04d3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:193:0x03c6 A[Catch:{ Throwable -> 0x04e2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x03dd A[Catch:{ Throwable -> 0x058f }] */
    /* JADX WARNING: Removed duplicated region for block: B:294:0x059d A[Catch:{ Throwable -> 0x06e6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:300:0x05bd  */
    /* JADX WARNING: Removed duplicated region for block: B:324:0x0698 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:349:0x0724 A[Catch:{ Throwable -> 0x0768 }] */
    /* JADX WARNING: Removed duplicated region for block: B:377:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(android.content.Context r14, com.loc.m.a r15) {
        /*
            r9 = 30
            r6 = 0
            r8 = -1
            r0 = 1
            r1 = 0
            org.json.JSONObject r3 = r15.g     // Catch:{ Throwable -> 0x00b8 }
            if (r3 == 0) goto L_0x00c2
            java.lang.String r2 = "at"
            r4 = 123(0x7b, float:1.72E-43)
            int r2 = r3.optInt(r2, r4)     // Catch:{ Throwable -> 0x00ac }
            int r2 = r2 * 60
            int r2 = r2 * 1000
            d = r2     // Catch:{ Throwable -> 0x00ac }
        L_0x001a:
            java.lang.String r2 = "ila"
            java.lang.String r2 = r3.optString(r2)     // Catch:{ Throwable -> 0x0126 }
            boolean r4 = Y     // Catch:{ Throwable -> 0x0126 }
            boolean r2 = com.loc.m.a(r2, r4)     // Catch:{ Throwable -> 0x0126 }
            Y = r2     // Catch:{ Throwable -> 0x0126 }
        L_0x0029:
            java.lang.String r2 = "icbd"
            java.lang.String r2 = r3.optString(r2)     // Catch:{ Throwable -> 0x0132 }
            boolean r4 = c     // Catch:{ Throwable -> 0x0132 }
            boolean r2 = com.loc.m.a(r2, r4)     // Catch:{ Throwable -> 0x0132 }
            c = r2     // Catch:{ Throwable -> 0x0132 }
            if (r2 == 0) goto L_0x0040
            java.lang.String r2 = "loc"
            com.loc.ba.a(r14, r2)     // Catch:{ Throwable -> 0x0132 }
        L_0x0040:
            if (r14 == 0) goto L_0x0044
            if (r3 != 0) goto L_0x013e
        L_0x0044:
            java.lang.String r2 = "nla"
            java.lang.String r2 = r3.optString(r2)     // Catch:{ Throwable -> 0x077c }
            boolean r4 = Z     // Catch:{ Throwable -> 0x077c }
            boolean r2 = com.loc.m.a(r2, r4)     // Catch:{ Throwable -> 0x077c }
            Z = r2     // Catch:{ Throwable -> 0x077c }
        L_0x0053:
            java.lang.String r2 = "oda"
            java.lang.String r2 = r3.optString(r2)     // Catch:{ Throwable -> 0x0779 }
            boolean r4 = j     // Catch:{ Throwable -> 0x0779 }
            boolean r2 = com.loc.m.a(r2, r4)     // Catch:{ Throwable -> 0x0779 }
            j = r2     // Catch:{ Throwable -> 0x0779 }
            java.lang.String r2 = "pref"
            java.lang.String r4 = "oda"
            boolean r5 = j     // Catch:{ Throwable -> 0x0779 }
            com.loc.dq.a(r14, r2, r4, r5)     // Catch:{ Throwable -> 0x0779 }
        L_0x006d:
            java.lang.String r2 = "asw"
            java.lang.String r2 = r3.optString(r2)     // Catch:{ Throwable -> 0x0776 }
            boolean r4 = af     // Catch:{ Throwable -> 0x0776 }
            boolean r2 = com.loc.m.a(r2, r4)     // Catch:{ Throwable -> 0x0776 }
            af = r2     // Catch:{ Throwable -> 0x0776 }
            java.lang.String r2 = "pref"
            java.lang.String r4 = "asw"
            boolean r5 = af     // Catch:{ Throwable -> 0x0776 }
            com.loc.dq.a(r14, r2, r4, r5)     // Catch:{ Throwable -> 0x0776 }
        L_0x0087:
            java.lang.String r2 = "mlpl"
            org.json.JSONArray r3 = r3.optJSONArray(r2)     // Catch:{ Throwable -> 0x0773 }
            if (r3 == 0) goto L_0x00c2
            int r2 = r3.length()     // Catch:{ Throwable -> 0x0773 }
            if (r2 <= 0) goto L_0x00c2
            r2 = r1
        L_0x0097:
            int r4 = r3.length()     // Catch:{ Throwable -> 0x0773 }
            if (r2 >= r4) goto L_0x00c2
            java.lang.String r4 = r3.getString(r2)     // Catch:{ Throwable -> 0x0773 }
            boolean r4 = com.loc.dr.c(r14, r4)     // Catch:{ Throwable -> 0x0773 }
            aa = r4     // Catch:{ Throwable -> 0x0773 }
            if (r4 != 0) goto L_0x00c2
            int r2 = r2 + 1
            goto L_0x0097
        L_0x00ac:
            r2 = move-exception
            java.lang.String r4 = "AuthUtil"
            java.lang.String r5 = "requestSdkAuthInterval"
            com.loc.di.a(r2, r4, r5)     // Catch:{ Throwable -> 0x00b8 }
            goto L_0x001a
        L_0x00b8:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigAbleStatus"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
        L_0x00c2:
            org.json.JSONObject r3 = r15.h     // Catch:{ Throwable -> 0x01d6 }
            if (r3 == 0) goto L_0x0189
            java.lang.String r2 = "callamapflag"
            java.lang.String r2 = r3.optString(r2)     // Catch:{ Throwable -> 0x01d6 }
            boolean r4 = A     // Catch:{ Throwable -> 0x01d6 }
            boolean r2 = com.loc.m.a(r2, r4)     // Catch:{ Throwable -> 0x01d6 }
            A = r2     // Catch:{ Throwable -> 0x01d6 }
            java.lang.String r2 = "co"
            java.lang.String r2 = r3.optString(r2)     // Catch:{ Throwable -> 0x01d6 }
            r4 = 0
            boolean r2 = com.loc.m.a(r2, r4)     // Catch:{ Throwable -> 0x01d6 }
            if (r2 == 0) goto L_0x0166
            boolean r2 = A     // Catch:{ Throwable -> 0x01d6 }
            if (r2 == 0) goto L_0x0166
            r2 = r0
        L_0x00e8:
            b = r2     // Catch:{ Throwable -> 0x01d6 }
            boolean r2 = A     // Catch:{ Throwable -> 0x01d6 }
            if (r2 == 0) goto L_0x0189
            java.lang.String r2 = "count"
            int r4 = B     // Catch:{ Throwable -> 0x01d6 }
            int r2 = r3.optInt(r2, r4)     // Catch:{ Throwable -> 0x01d6 }
            B = r2     // Catch:{ Throwable -> 0x01d6 }
            java.lang.String r2 = "sysTime"
            long r4 = C     // Catch:{ Throwable -> 0x01d6 }
            long r4 = r3.optLong(r2, r4)     // Catch:{ Throwable -> 0x01d6 }
            C = r4     // Catch:{ Throwable -> 0x01d6 }
            java.lang.String r2 = "sn"
            org.json.JSONArray r3 = r3.optJSONArray(r2)     // Catch:{ Throwable -> 0x01d6 }
            if (r3 == 0) goto L_0x0168
            int r2 = r3.length()     // Catch:{ Throwable -> 0x01d6 }
            if (r2 <= 0) goto L_0x0168
            r2 = r1
        L_0x0114:
            int r4 = r3.length()     // Catch:{ Throwable -> 0x01d6 }
            if (r2 >= r4) goto L_0x0168
            java.util.ArrayList<java.lang.String> r4 = D     // Catch:{ Throwable -> 0x01d6 }
            java.lang.String r5 = r3.getString(r2)     // Catch:{ Throwable -> 0x01d6 }
            r4.add(r5)     // Catch:{ Throwable -> 0x01d6 }
            int r2 = r2 + 1
            goto L_0x0114
        L_0x0126:
            r2 = move-exception
            java.lang.String r4 = "AuthUtil"
            java.lang.String r5 = "loadConfigData_indoor"
            com.loc.di.a(r2, r4, r5)     // Catch:{ Throwable -> 0x00b8 }
            goto L_0x0029
        L_0x0132:
            r2 = move-exception
            java.lang.String r4 = "AuthUtil"
            java.lang.String r5 = "loadConfigData_CallBackDex"
            com.loc.di.a(r2, r4, r5)     // Catch:{ Throwable -> 0x00b8 }
            goto L_0x0040
        L_0x013e:
            java.lang.String r2 = "re"
            java.lang.String r2 = r3.optString(r2)     // Catch:{ Throwable -> 0x015a }
            boolean r4 = h     // Catch:{ Throwable -> 0x015a }
            boolean r2 = com.loc.m.a(r2, r4)     // Catch:{ Throwable -> 0x015a }
            h = r2     // Catch:{ Throwable -> 0x015a }
            java.lang.String r2 = "pref"
            java.lang.String r4 = "fr"
            boolean r5 = h     // Catch:{ Throwable -> 0x015a }
            com.loc.dq.a(r14, r2, r4, r5)     // Catch:{ Throwable -> 0x015a }
            goto L_0x0044
        L_0x015a:
            r2 = move-exception
            java.lang.String r4 = "AuthUtil"
            java.lang.String r5 = "checkReLocationAble"
            com.loc.di.a(r2, r4, r5)     // Catch:{ Throwable -> 0x00b8 }
            goto L_0x0044
        L_0x0166:
            r2 = r1
            goto L_0x00e8
        L_0x0168:
            int r2 = B     // Catch:{ Throwable -> 0x01d6 }
            if (r2 == r8) goto L_0x0189
            long r2 = C     // Catch:{ Throwable -> 0x01d6 }
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 == 0) goto L_0x0189
            java.lang.String r2 = "pref"
            java.lang.String r3 = "nowtime"
            r4 = 0
            long r2 = com.loc.dq.b(r14, r2, r3, r4)     // Catch:{ Throwable -> 0x01d6 }
            long r4 = C     // Catch:{ Throwable -> 0x01d6 }
            boolean r2 = com.loc.dr.b(r4, r2)     // Catch:{ Throwable -> 0x01d6 }
            if (r2 != 0) goto L_0x0189
            h(r14)     // Catch:{ Throwable -> 0x01d6 }
        L_0x0189:
            org.json.JSONObject r2 = r15.k     // Catch:{ Throwable -> 0x044b }
            if (r2 == 0) goto L_0x0205
            java.lang.String r3 = "amappushflag"
            java.lang.String r3 = r2.optString(r3)     // Catch:{ Throwable -> 0x044b }
            boolean r4 = E     // Catch:{ Throwable -> 0x044b }
            boolean r3 = com.loc.m.a(r3, r4)     // Catch:{ Throwable -> 0x044b }
            E = r3     // Catch:{ Throwable -> 0x044b }
            if (r3 == 0) goto L_0x0205
            java.lang.String r3 = "count"
            int r4 = F     // Catch:{ Throwable -> 0x044b }
            int r3 = r2.optInt(r3, r4)     // Catch:{ Throwable -> 0x044b }
            F = r3     // Catch:{ Throwable -> 0x044b }
            java.lang.String r3 = "sysTime"
            long r4 = G     // Catch:{ Throwable -> 0x044b }
            long r4 = r2.optLong(r3, r4)     // Catch:{ Throwable -> 0x044b }
            G = r4     // Catch:{ Throwable -> 0x044b }
            java.lang.String r3 = "sn"
            org.json.JSONArray r3 = r2.optJSONArray(r3)     // Catch:{ Throwable -> 0x044b }
            if (r3 == 0) goto L_0x01e4
            int r2 = r3.length()     // Catch:{ Throwable -> 0x044b }
            if (r2 <= 0) goto L_0x01e4
            r2 = r1
        L_0x01c4:
            int r4 = r3.length()     // Catch:{ Throwable -> 0x044b }
            if (r2 >= r4) goto L_0x01e4
            java.util.ArrayList<java.lang.String> r4 = H     // Catch:{ Throwable -> 0x044b }
            java.lang.String r5 = r3.getString(r2)     // Catch:{ Throwable -> 0x044b }
            r4.add(r5)     // Catch:{ Throwable -> 0x044b }
            int r2 = r2 + 1
            goto L_0x01c4
        L_0x01d6:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigDataCallAMapSer"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
            goto L_0x0189
        L_0x01e1:
            r0 = move-exception
            r0 = r1
        L_0x01e3:
            return r0
        L_0x01e4:
            int r2 = F     // Catch:{ Throwable -> 0x044b }
            if (r2 == r8) goto L_0x0205
            long r2 = G     // Catch:{ Throwable -> 0x044b }
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 == 0) goto L_0x0205
            java.lang.String r2 = "pref"
            java.lang.String r3 = "pushSerTime"
            r4 = 0
            long r2 = com.loc.dq.b(r14, r2, r3, r4)     // Catch:{ Throwable -> 0x044b }
            long r4 = G     // Catch:{ Throwable -> 0x044b }
            boolean r2 = com.loc.dr.b(r4, r2)     // Catch:{ Throwable -> 0x044b }
            if (r2 != 0) goto L_0x0205
            i(r14)     // Catch:{ Throwable -> 0x044b }
        L_0x0205:
            com.loc.v r2 = com.loc.di.b()     // Catch:{ Throwable -> 0x0467 }
            com.loc.m$a$d r3 = r15.y     // Catch:{ Throwable -> 0x0467 }
            if (r3 == 0) goto L_0x0473
            java.lang.String r4 = r3.b     // Catch:{ Throwable -> 0x0467 }
            java.lang.String r5 = r3.a     // Catch:{ Throwable -> 0x0467 }
            java.lang.String r3 = r3.c     // Catch:{ Throwable -> 0x0467 }
            boolean r6 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x0467 }
            if (r6 != 0) goto L_0x0225
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0467 }
            if (r6 != 0) goto L_0x0225
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0467 }
            if (r3 == 0) goto L_0x0457
        L_0x0225:
            r3 = 0
            com.loc.ba.b(r14, r3, r2)     // Catch:{ Throwable -> 0x0467 }
        L_0x0229:
            boolean r2 = a     // Catch:{ Throwable -> 0x01e1 }
            if (r2 == 0) goto L_0x0262
            com.loc.m$a$c r2 = r15.B     // Catch:{ Throwable -> 0x0479 }
            if (r2 == 0) goto L_0x0262
            java.lang.String r3 = r2.a     // Catch:{ Throwable -> 0x0479 }
            I = r3     // Catch:{ Throwable -> 0x0479 }
            java.lang.String r2 = r2.b     // Catch:{ Throwable -> 0x0479 }
            J = r2     // Catch:{ Throwable -> 0x0479 }
            java.lang.String r2 = I     // Catch:{ Throwable -> 0x0479 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x0479 }
            if (r2 != 0) goto L_0x0262
            java.lang.String r2 = J     // Catch:{ Throwable -> 0x0479 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x0479 }
            if (r2 != 0) goto L_0x0262
            com.loc.o r2 = new com.loc.o     // Catch:{ Throwable -> 0x0479 }
            java.lang.String r3 = "loc"
            java.lang.String r4 = I     // Catch:{ Throwable -> 0x0479 }
            java.lang.String r5 = J     // Catch:{ Throwable -> 0x0479 }
            r2.<init>(r14, r3, r4, r5)     // Catch:{ Throwable -> 0x0479 }
            boolean r3 = k     // Catch:{ Throwable -> 0x0479 }
            r2.b(r3)     // Catch:{ Throwable -> 0x0479 }
            boolean r3 = j     // Catch:{ Throwable -> 0x0479 }
            r2.a(r3)     // Catch:{ Throwable -> 0x0479 }
            r2.a()     // Catch:{ Throwable -> 0x0479 }
        L_0x0262:
            com.loc.m$a$a r2 = r15.x     // Catch:{ Throwable -> 0x0485 }
            if (r2 == 0) goto L_0x02ec
            boolean r3 = r2.a     // Catch:{ Throwable -> 0x0485 }
            u = r3     // Catch:{ Throwable -> 0x0485 }
            java.lang.String r3 = "pref"
            java.lang.String r4 = "exception"
            boolean r5 = u     // Catch:{ Throwable -> 0x0485 }
            com.loc.dq.a(r14, r3, r4, r5)     // Catch:{ Throwable -> 0x0485 }
            org.json.JSONObject r2 = r2.c     // Catch:{ Throwable -> 0x0485 }
            java.lang.String r3 = "fn"
            int r4 = v     // Catch:{ Throwable -> 0x0485 }
            int r3 = r2.optInt(r3, r4)     // Catch:{ Throwable -> 0x0485 }
            v = r3     // Catch:{ Throwable -> 0x0485 }
            java.lang.String r3 = "mpn"
            int r4 = w     // Catch:{ Throwable -> 0x0485 }
            int r3 = r2.optInt(r3, r4)     // Catch:{ Throwable -> 0x0485 }
            w = r3     // Catch:{ Throwable -> 0x0485 }
            r4 = 500(0x1f4, float:7.0E-43)
            if (r3 <= r4) goto L_0x0295
            r3 = 500(0x1f4, float:7.0E-43)
            w = r3     // Catch:{ Throwable -> 0x0485 }
        L_0x0295:
            int r3 = w     // Catch:{ Throwable -> 0x0485 }
            if (r3 >= r9) goto L_0x029d
            r3 = 30
            w = r3     // Catch:{ Throwable -> 0x0485 }
        L_0x029d:
            java.lang.String r3 = "igu"
            java.lang.String r3 = r2.optString(r3)     // Catch:{ Throwable -> 0x0485 }
            boolean r4 = x     // Catch:{ Throwable -> 0x0485 }
            boolean r3 = com.loc.m.a(r3, r4)     // Catch:{ Throwable -> 0x0485 }
            x = r3     // Catch:{ Throwable -> 0x0485 }
            java.lang.String r3 = "ms"
            int r4 = y     // Catch:{ Throwable -> 0x0485 }
            int r2 = r2.optInt(r3, r4)     // Catch:{ Throwable -> 0x0485 }
            y = r2     // Catch:{ Throwable -> 0x0485 }
            int r2 = v     // Catch:{ Throwable -> 0x0485 }
            boolean r3 = x     // Catch:{ Throwable -> 0x0485 }
            int r4 = y     // Catch:{ Throwable -> 0x0485 }
            com.loc.bw.a(r2, r3, r4)     // Catch:{ Throwable -> 0x0485 }
            java.lang.String r2 = "pref"
            java.lang.String r3 = "fn"
            int r4 = v     // Catch:{ Throwable -> 0x0485 }
            com.loc.dq.a(r14, r2, r3, r4)     // Catch:{ Throwable -> 0x0485 }
            java.lang.String r2 = "pref"
            java.lang.String r3 = "mpn"
            int r4 = w     // Catch:{ Throwable -> 0x0485 }
            com.loc.dq.a(r14, r2, r3, r4)     // Catch:{ Throwable -> 0x0485 }
            java.lang.String r2 = "pref"
            java.lang.String r3 = "igu"
            boolean r4 = x     // Catch:{ Throwable -> 0x0485 }
            com.loc.dq.a(r14, r2, r3, r4)     // Catch:{ Throwable -> 0x0485 }
            java.lang.String r2 = "pref"
            java.lang.String r3 = "ms"
            int r4 = y     // Catch:{ Throwable -> 0x0485 }
            com.loc.dq.a(r14, r2, r3, r4)     // Catch:{ Throwable -> 0x0485 }
        L_0x02ec:
            org.json.JSONObject r3 = r15.m     // Catch:{ Throwable -> 0x049d }
            if (r3 == 0) goto L_0x035b
            java.lang.String r2 = "able"
            java.lang.String r2 = r3.optString(r2)     // Catch:{ Throwable -> 0x049d }
            r4 = 0
            boolean r2 = com.loc.m.a(r2, r4)     // Catch:{ Throwable -> 0x049d }
            if (r2 == 0) goto L_0x035b
            java.lang.String r2 = "fs"
            com.loc.dh$a r2 = a(r3, r2)     // Catch:{ Throwable -> 0x049d }
            if (r2 == 0) goto L_0x0313
            boolean r4 = r2.c     // Catch:{ Throwable -> 0x049d }
            p = r4     // Catch:{ Throwable -> 0x049d }
            java.lang.String r2 = r2.b     // Catch:{ Throwable -> 0x0491 }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Throwable -> 0x0491 }
            q = r2     // Catch:{ Throwable -> 0x0491 }
        L_0x0313:
            java.lang.String r2 = "us"
            com.loc.dh$a r2 = a(r3, r2)     // Catch:{ Throwable -> 0x049d }
            if (r2 == 0) goto L_0x0334
            boolean r4 = r2.c     // Catch:{ Throwable -> 0x049d }
            r = r4     // Catch:{ Throwable -> 0x049d }
            boolean r4 = r2.a     // Catch:{ Throwable -> 0x049d }
            t = r4     // Catch:{ Throwable -> 0x049d }
            java.lang.String r2 = r2.b     // Catch:{ Throwable -> 0x04a9 }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Throwable -> 0x04a9 }
            s = r2     // Catch:{ Throwable -> 0x04a9 }
        L_0x032c:
            int r2 = s     // Catch:{ Throwable -> 0x049d }
            r4 = 2
            if (r2 >= r4) goto L_0x0334
            r2 = 0
            r = r2     // Catch:{ Throwable -> 0x049d }
        L_0x0334:
            java.lang.String r2 = "rs"
            com.loc.dh$a r2 = a(r3, r2)     // Catch:{ Throwable -> 0x049d }
            if (r2 == 0) goto L_0x035b
            boolean r3 = r2.c     // Catch:{ Throwable -> 0x049d }
            l = r3     // Catch:{ Throwable -> 0x049d }
            if (r3 == 0) goto L_0x0350
            long r4 = com.loc.dr.c()     // Catch:{ Throwable -> 0x049d }
            n = r4     // Catch:{ Throwable -> 0x049d }
            int r3 = r2.d     // Catch:{ Throwable -> 0x049d }
            int r3 = r3 * 1000
            long r4 = (long) r3     // Catch:{ Throwable -> 0x049d }
            o = r4     // Catch:{ Throwable -> 0x049d }
        L_0x0350:
            java.lang.String r2 = r2.b     // Catch:{ Throwable -> 0x04b5 }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Throwable -> 0x04b5 }
            int r2 = r2 * 1000
            long r2 = (long) r2     // Catch:{ Throwable -> 0x04b5 }
            m = r2     // Catch:{ Throwable -> 0x04b5 }
        L_0x035b:
            org.json.JSONObject r2 = r15.o     // Catch:{ Throwable -> 0x04c7 }
            if (r2 == 0) goto L_0x0389
            java.lang.String r3 = "able"
            java.lang.String r3 = r2.optString(r3)     // Catch:{ Throwable -> 0x04c7 }
            boolean r4 = K     // Catch:{ Throwable -> 0x04c7 }
            boolean r3 = com.loc.m.a(r3, r4)     // Catch:{ Throwable -> 0x04c7 }
            K = r3     // Catch:{ Throwable -> 0x04c7 }
            if (r3 == 0) goto L_0x0389
            java.lang.String r3 = "c"
            r4 = 0
            int r3 = r2.optInt(r3, r4)     // Catch:{ Throwable -> 0x04c7 }
            if (r3 != 0) goto L_0x04c1
            r3 = 3000(0xbb8, float:4.204E-42)
            L = r3     // Catch:{ Throwable -> 0x04c7 }
        L_0x037e:
            java.lang.String r3 = "t"
            int r2 = r2.getInt(r3)     // Catch:{ Throwable -> 0x04c7 }
            int r2 = r2 / 2
            M = r2     // Catch:{ Throwable -> 0x04c7 }
        L_0x0389:
            org.json.JSONObject r2 = r15.p     // Catch:{ Throwable -> 0x04d3 }
            if (r2 == 0) goto L_0x03c2
            java.lang.String r3 = "able"
            java.lang.String r3 = r2.optString(r3)     // Catch:{ Throwable -> 0x04d3 }
            boolean r4 = N     // Catch:{ Throwable -> 0x04d3 }
            boolean r3 = com.loc.m.a(r3, r4)     // Catch:{ Throwable -> 0x04d3 }
            N = r3     // Catch:{ Throwable -> 0x04d3 }
            if (r3 == 0) goto L_0x03ac
            java.lang.String r3 = "c"
            r4 = 300(0x12c, float:4.2E-43)
            int r2 = r2.optInt(r3, r4)     // Catch:{ Throwable -> 0x04d3 }
            int r2 = r2 * 1000
            long r2 = (long) r2     // Catch:{ Throwable -> 0x04d3 }
            O = r2     // Catch:{ Throwable -> 0x04d3 }
        L_0x03ac:
            java.lang.String r2 = "pref"
            java.lang.String r3 = "ca"
            boolean r4 = N     // Catch:{ Throwable -> 0x04d3 }
            com.loc.dq.a(r14, r2, r3, r4)     // Catch:{ Throwable -> 0x04d3 }
            java.lang.String r2 = "pref"
            java.lang.String r3 = "ct"
            long r4 = O     // Catch:{ Throwable -> 0x04d3 }
            com.loc.dq.a(r14, r2, r3, r4)     // Catch:{ Throwable -> 0x04d3 }
        L_0x03c2:
            com.loc.m$a$b r3 = r15.E     // Catch:{ Throwable -> 0x04e2 }
            if (r3 == 0) goto L_0x03d9
            boolean r2 = com.loc.db.c()     // Catch:{ Throwable -> 0x04e2 }
            if (r2 != 0) goto L_0x04df
            r2 = r0
        L_0x03cd:
            java.lang.String r4 = "HttpDNS"
            java.lang.String r5 = "1.0.0"
            boolean r2 = a(r14, r3, r4, r5, r2)     // Catch:{ Throwable -> 0x04e2 }
            P = r2     // Catch:{ Throwable -> 0x04e2 }
        L_0x03d9:
            org.json.JSONObject r4 = r15.j     // Catch:{ Throwable -> 0x058f }
            if (r4 == 0) goto L_0x0599
            java.lang.String r2 = "able"
            java.lang.String r2 = r4.optString(r2)     // Catch:{ Throwable -> 0x058f }
            boolean r3 = R     // Catch:{ Throwable -> 0x058f }
            boolean r2 = com.loc.m.a(r2, r3)     // Catch:{ Throwable -> 0x058f }
            R = r2     // Catch:{ Throwable -> 0x058f }
            java.lang.String r2 = "sysTime"
            long r6 = com.loc.dr.b()     // Catch:{ Throwable -> 0x058f }
            long r2 = r4.optLong(r2, r6)     // Catch:{ Throwable -> 0x058f }
            S = r2     // Catch:{ Throwable -> 0x058f }
            java.lang.String r2 = "n"
            r3 = 1
            int r2 = r4.optInt(r2, r3)     // Catch:{ Throwable -> 0x058f }
            T = r2     // Catch:{ Throwable -> 0x058f }
            java.lang.String r2 = "nh"
            r3 = 1
            int r2 = r4.optInt(r2, r3)     // Catch:{ Throwable -> 0x058f }
            U = r2     // Catch:{ Throwable -> 0x058f }
            int r2 = T     // Catch:{ Throwable -> 0x058f }
            if (r2 == r8) goto L_0x0417
            int r2 = T     // Catch:{ Throwable -> 0x058f }
            int r3 = U     // Catch:{ Throwable -> 0x058f }
            if (r2 < r3) goto L_0x077f
        L_0x0417:
            r2 = r0
        L_0x0418:
            boolean r3 = R     // Catch:{ Throwable -> 0x058f }
            if (r3 == 0) goto L_0x0599
            if (r2 == 0) goto L_0x0599
            java.lang.String r2 = "l"
            org.json.JSONArray r5 = r4.optJSONArray(r2)     // Catch:{ Throwable -> 0x058f }
            r2 = r1
        L_0x0426:
            int r3 = r5.length()     // Catch:{ Throwable -> 0x058f }
            if (r2 >= r3) goto L_0x0566
            org.json.JSONObject r6 = r5.optJSONObject(r2)     // Catch:{ Throwable -> 0x0563 }
            com.loc.dl r7 = new com.loc.dl     // Catch:{ Throwable -> 0x0563 }
            r7.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r3 = "able"
            java.lang.String r8 = "false"
            java.lang.String r3 = r6.optString(r3, r8)     // Catch:{ Throwable -> 0x0563 }
            r8 = 0
            boolean r3 = com.loc.m.a(r3, r8)     // Catch:{ Throwable -> 0x0563 }
            r7.a = r3     // Catch:{ Throwable -> 0x0563 }
            if (r3 != 0) goto L_0x04ee
        L_0x0448:
            int r2 = r2 + 1
            goto L_0x0426
        L_0x044b:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigDataCallAMapPush"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
            goto L_0x0205
        L_0x0457:
            com.loc.av r3 = new com.loc.av     // Catch:{ Throwable -> 0x0467 }
            boolean r6 = k     // Catch:{ Throwable -> 0x0467 }
            r3.<init>(r5, r4, r6)     // Catch:{ Throwable -> 0x0467 }
            r4 = 1
            r3.a(r4)     // Catch:{ Throwable -> 0x0467 }
            com.loc.ba.b(r14, r3, r2)     // Catch:{ Throwable -> 0x0467 }
            goto L_0x0229
        L_0x0467:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigDataSdkUpdate"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
            goto L_0x0229
        L_0x0473:
            r3 = 0
            com.loc.ba.b(r14, r3, r2)     // Catch:{ Throwable -> 0x0467 }
            goto L_0x0229
        L_0x0479:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigDataGroupOffset"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
            goto L_0x0262
        L_0x0485:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigDataUploadException"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
            goto L_0x02ec
        L_0x0491:
            r2 = move-exception
            java.lang.String r4 = "AuthUtil"
            java.lang.String r5 = "loadconfig part2"
            com.loc.di.a(r2, r4, r5)     // Catch:{ Throwable -> 0x049d }
            goto L_0x0313
        L_0x049d:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigDataLocate"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
            goto L_0x035b
        L_0x04a9:
            r2 = move-exception
            java.lang.String r4 = "AuthUtil"
            java.lang.String r5 = "loadconfig part1"
            com.loc.di.a(r2, r4, r5)     // Catch:{ Throwable -> 0x049d }
            goto L_0x032c
        L_0x04b5:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadconfig part"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x049d }
            goto L_0x035b
        L_0x04c1:
            int r3 = r3 * 1000
            L = r3     // Catch:{ Throwable -> 0x04c7 }
            goto L_0x037e
        L_0x04c7:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigDataNgps"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
            goto L_0x0389
        L_0x04d3:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigDataCacheAble"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
            goto L_0x03c2
        L_0x04df:
            r2 = r1
            goto L_0x03cd
        L_0x04e2:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigDataDnsDex"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
            goto L_0x03d9
        L_0x04ee:
            java.lang.String r3 = "pn"
            java.lang.String r8 = ""
            java.lang.String r3 = r6.optString(r3, r8)     // Catch:{ Throwable -> 0x0563 }
            r7.b = r3     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r3 = "cn"
            java.lang.String r8 = ""
            java.lang.String r3 = r6.optString(r3, r8)     // Catch:{ Throwable -> 0x0563 }
            r7.c = r3     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r3 = "a"
            java.lang.String r8 = ""
            java.lang.String r3 = r6.optString(r3, r8)     // Catch:{ Throwable -> 0x0563 }
            r7.e = r3     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r3 = "b"
            org.json.JSONArray r8 = r6.optJSONArray(r3)     // Catch:{ Throwable -> 0x0563 }
            if (r8 == 0) goto L_0x054b
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0563 }
            r9.<init>()     // Catch:{ Throwable -> 0x0563 }
            r3 = r1
        L_0x0521:
            int r10 = r8.length()     // Catch:{ Throwable -> 0x0563 }
            if (r3 >= r10) goto L_0x0549
            org.json.JSONObject r10 = r8.optJSONObject(r3)     // Catch:{ Throwable -> 0x0563 }
            java.util.HashMap r11 = new java.util.HashMap     // Catch:{ Throwable -> 0x0563 }
            r12 = 16
            r11.<init>(r12)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r12 = "k"
            java.lang.String r12 = r10.optString(r12)     // Catch:{ Throwable -> 0x0770 }
            java.lang.String r13 = "v"
            java.lang.String r10 = r10.optString(r13)     // Catch:{ Throwable -> 0x0770 }
            r11.put(r12, r10)     // Catch:{ Throwable -> 0x0770 }
            r9.add(r11)     // Catch:{ Throwable -> 0x0770 }
        L_0x0546:
            int r3 = r3 + 1
            goto L_0x0521
        L_0x0549:
            r7.d = r9     // Catch:{ Throwable -> 0x0563 }
        L_0x054b:
            java.lang.String r3 = "is"
            java.lang.String r8 = "false"
            java.lang.String r3 = r6.optString(r3, r8)     // Catch:{ Throwable -> 0x0563 }
            r6 = 0
            boolean r3 = com.loc.m.a(r3, r6)     // Catch:{ Throwable -> 0x0563 }
            r7.f = r3     // Catch:{ Throwable -> 0x0563 }
            java.util.List<com.loc.dl> r3 = Q     // Catch:{ Throwable -> 0x0563 }
            r3.add(r7)     // Catch:{ Throwable -> 0x0563 }
            goto L_0x0448
        L_0x0563:
            r3 = move-exception
            goto L_0x0448
        L_0x0566:
            java.lang.String r2 = "sl"
            org.json.JSONArray r3 = r4.optJSONArray(r2)     // Catch:{ Throwable -> 0x058f }
            if (r3 == 0) goto L_0x0599
            r2 = r1
        L_0x0570:
            int r4 = r3.length()     // Catch:{ Throwable -> 0x058f }
            if (r2 >= r4) goto L_0x0599
            org.json.JSONObject r4 = r3.optJSONObject(r2)     // Catch:{ Throwable -> 0x058f }
            java.lang.String r5 = "pan"
            java.lang.String r4 = r4.optString(r5)     // Catch:{ Throwable -> 0x058f }
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x058f }
            if (r5 != 0) goto L_0x058c
            java.util.List<java.lang.String> r5 = V     // Catch:{ Throwable -> 0x058f }
            r5.add(r4)     // Catch:{ Throwable -> 0x058f }
        L_0x058c:
            int r2 = r2 + 1
            goto L_0x0570
        L_0x058f:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigData_otherServiceList"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
        L_0x0599:
            org.json.JSONObject r2 = r15.i     // Catch:{ Throwable -> 0x06e6 }
            if (r2 == 0) goto L_0x05b9
            java.lang.String r3 = "able"
            java.lang.String r3 = r2.optString(r3)     // Catch:{ Throwable -> 0x06e6 }
            boolean r4 = W     // Catch:{ Throwable -> 0x06e6 }
            boolean r3 = com.loc.m.a(r3, r4)     // Catch:{ Throwable -> 0x06e6 }
            W = r3     // Catch:{ Throwable -> 0x06e6 }
            if (r3 == 0) goto L_0x05b9
            java.lang.String r3 = "c"
            int r4 = X     // Catch:{ Throwable -> 0x06e6 }
            int r2 = r2.optInt(r3, r4)     // Catch:{ Throwable -> 0x06e6 }
            X = r2     // Catch:{ Throwable -> 0x06e6 }
        L_0x05b9:
            org.json.JSONObject r3 = r15.w     // Catch:{ Throwable -> 0x01e1 }
            if (r3 == 0) goto L_0x0692
            java.lang.String r2 = "157"
            org.json.JSONObject r2 = r3.optJSONObject(r2)     // Catch:{ Throwable -> 0x070f }
            if (r2 == 0) goto L_0x0692
            java.lang.String r4 = "able"
            java.lang.String r4 = r2.optString(r4)     // Catch:{ Throwable -> 0x070f }
            boolean r5 = ab     // Catch:{ Throwable -> 0x070f }
            boolean r4 = com.loc.m.a(r4, r5)     // Catch:{ Throwable -> 0x070f }
            ab = r4     // Catch:{ Throwable -> 0x070f }
            java.lang.String r4 = "co"
            java.lang.String r5 = "1.0.0"
            com.loc.v r4 = com.loc.di.a(r4, r5)     // Catch:{ Throwable -> 0x070f }
            boolean r5 = ab     // Catch:{ Throwable -> 0x070f }
            if (r5 == 0) goto L_0x06f5
            java.lang.String r5 = "cv"
            r6 = -1
            int r5 = r2.optInt(r5, r6)     // Catch:{ Throwable -> 0x070f }
            ae = r5     // Catch:{ Throwable -> 0x070f }
            java.lang.String r5 = "co"
            java.lang.String r5 = r2.optString(r5)     // Catch:{ Throwable -> 0x070f }
            boolean r6 = ac     // Catch:{ Throwable -> 0x070f }
            boolean r5 = com.loc.m.a(r5, r6)     // Catch:{ Throwable -> 0x070f }
            ac = r5     // Catch:{ Throwable -> 0x070f }
            java.lang.String r5 = "oo"
            java.lang.String r5 = r2.optString(r5)     // Catch:{ Throwable -> 0x070f }
            boolean r6 = ad     // Catch:{ Throwable -> 0x070f }
            boolean r5 = com.loc.m.a(r5, r6)     // Catch:{ Throwable -> 0x070f }
            ad = r5     // Catch:{ Throwable -> 0x070f }
            java.lang.String r5 = "v"
            java.lang.String r5 = r2.optString(r5)     // Catch:{ Throwable -> 0x070f }
            java.lang.String r6 = "u"
            java.lang.String r6 = r2.optString(r6)     // Catch:{ Throwable -> 0x070f }
            java.lang.String r7 = "m"
            java.lang.String r2 = r2.optString(r7)     // Catch:{ Throwable -> 0x070f }
            boolean r7 = com.loc.cv.a()     // Catch:{ Throwable -> 0x070f }
            if (r7 != 0) goto L_0x0666
            boolean r7 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x070f }
            if (r7 != 0) goto L_0x0666
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x070f }
            if (r7 != 0) goto L_0x0666
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x070f }
            if (r5 != 0) goto L_0x0666
            com.loc.av r5 = new com.loc.av     // Catch:{ Throwable -> 0x070f }
            boolean r7 = k     // Catch:{ Throwable -> 0x070f }
            r5.<init>(r6, r2, r7)     // Catch:{ Throwable -> 0x070f }
            boolean r2 = j     // Catch:{ Throwable -> 0x070f }
            r5.a(r2)     // Catch:{ Throwable -> 0x070f }
            boolean r6 = com.loc.ba.a(r14, r5, r4)     // Catch:{ Throwable -> 0x070f }
            if (r6 != 0) goto L_0x06f2
            r2 = r0
        L_0x064b:
            i = r2     // Catch:{ Throwable -> 0x070f }
            if (r6 == 0) goto L_0x0666
            java.lang.String r2 = "pref"
            java.lang.String r6 = "ok4"
            r7 = 1
            com.loc.dq.a(r14, r2, r6, r7)     // Catch:{ Throwable -> 0x070f }
            java.lang.String r2 = "pref"
            java.lang.String r6 = "ok1"
            r7 = 0
            com.loc.dq.a(r14, r2, r6, r7)     // Catch:{ Throwable -> 0x070f }
            com.loc.ba.b(r14, r5, r4)     // Catch:{ Throwable -> 0x070f }
        L_0x0666:
            java.lang.String r2 = "pref"
            java.lang.String r4 = "ok0"
            boolean r5 = ab     // Catch:{ Throwable -> 0x070f }
            com.loc.dq.a(r14, r2, r4, r5)     // Catch:{ Throwable -> 0x070f }
            java.lang.String r2 = "pref"
            java.lang.String r4 = "ok1"
            boolean r5 = i     // Catch:{ Throwable -> 0x070f }
            com.loc.dq.a(r14, r2, r4, r5)     // Catch:{ Throwable -> 0x070f }
            java.lang.String r2 = "pref"
            java.lang.String r4 = "ok2"
            boolean r5 = ac     // Catch:{ Throwable -> 0x070f }
            com.loc.dq.a(r14, r2, r4, r5)     // Catch:{ Throwable -> 0x070f }
            java.lang.String r2 = "pref"
            java.lang.String r4 = "ok3"
            boolean r5 = ad     // Catch:{ Throwable -> 0x070f }
            com.loc.dq.a(r14, r2, r4, r5)     // Catch:{ Throwable -> 0x070f }
        L_0x0692:
            if (r14 == 0) goto L_0x0696
            if (r3 != 0) goto L_0x071b
        L_0x0696:
            if (r14 == 0) goto L_0x01e3
            if (r3 == 0) goto L_0x01e3
            java.lang.String r1 = "15U"
            org.json.JSONObject r1 = r3.optJSONObject(r1)     // Catch:{ Throwable -> 0x06e3 }
            if (r1 == 0) goto L_0x01e3
            java.lang.String r2 = "able"
            java.lang.String r2 = r1.optString(r2)     // Catch:{ Throwable -> 0x06e3 }
            boolean r3 = ah     // Catch:{ Throwable -> 0x06e3 }
            boolean r2 = com.loc.m.a(r2, r3)     // Catch:{ Throwable -> 0x06e3 }
            java.lang.String r3 = "yn"
            int r4 = ai     // Catch:{ Throwable -> 0x06e3 }
            int r3 = r1.optInt(r3, r4)     // Catch:{ Throwable -> 0x06e3 }
            java.lang.String r4 = "sysTime"
            long r6 = aj     // Catch:{ Throwable -> 0x06e3 }
            long r4 = r1.optLong(r4, r6)     // Catch:{ Throwable -> 0x06e3 }
            aj = r4     // Catch:{ Throwable -> 0x06e3 }
            java.lang.String r1 = "pref"
            java.lang.String r4 = "15ua"
            com.loc.dq.a(r14, r1, r4, r2)     // Catch:{ Throwable -> 0x06e3 }
            java.lang.String r1 = "pref"
            java.lang.String r2 = "15un"
            com.loc.dq.a(r14, r1, r2, r3)     // Catch:{ Throwable -> 0x06e3 }
            java.lang.String r1 = "pref"
            java.lang.String r2 = "15ust"
            long r4 = aj     // Catch:{ Throwable -> 0x06e3 }
            com.loc.dq.a(r14, r1, r2, r4)     // Catch:{ Throwable -> 0x06e3 }
            goto L_0x01e3
        L_0x06e3:
            r1 = move-exception
            goto L_0x01e3
        L_0x06e6:
            r2 = move-exception
            java.lang.String r3 = "AuthUtil"
            java.lang.String r4 = "loadConfigDataGpsGeoAble"
            com.loc.di.a(r2, r3, r4)     // Catch:{ Throwable -> 0x01e1 }
            goto L_0x05b9
        L_0x06f2:
            r2 = r1
            goto L_0x064b
        L_0x06f5:
            r2 = 0
            i = r2     // Catch:{ Throwable -> 0x070f }
            r2 = 0
            ac = r2     // Catch:{ Throwable -> 0x070f }
            r2 = 0
            ad = r2     // Catch:{ Throwable -> 0x070f }
            boolean r2 = com.loc.Cdo.a(r14, r4)     // Catch:{ Throwable -> 0x070f }
            if (r2 == 0) goto L_0x0666
            java.lang.String r2 = "co"
            java.lang.String r4 = "config|coDex able is false"
            com.loc.dp.a(r14, r2, r4)     // Catch:{ Throwable -> 0x070f }
            goto L_0x0666
        L_0x070f:
            r2 = move-exception
            java.lang.String r4 = "AuthUtil"
            java.lang.String r5 = "loadConfigDataNewCollectionOffline"
            com.loc.di.a(r2, r4, r5)     // Catch:{ Throwable -> 0x01e1 }
            goto L_0x0692
        L_0x071b:
            java.lang.String r1 = "15O"
            org.json.JSONObject r1 = r3.optJSONObject(r1)     // Catch:{ Throwable -> 0x0768 }
            if (r1 == 0) goto L_0x0696
            java.lang.String r2 = "able"
            java.lang.String r2 = r1.optString(r2)     // Catch:{ Throwable -> 0x0768 }
            r4 = 0
            boolean r2 = com.loc.m.a(r2, r4)     // Catch:{ Throwable -> 0x0768 }
            if (r2 == 0) goto L_0x076b
            java.lang.String r2 = "fl"
            org.json.JSONArray r2 = r1.optJSONArray(r2)     // Catch:{ Throwable -> 0x0768 }
            if (r2 == 0) goto L_0x074d
            int r4 = r2.length()     // Catch:{ Throwable -> 0x0768 }
            if (r4 <= 0) goto L_0x074d
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0768 }
            java.lang.String r4 = android.os.Build.MANUFACTURER     // Catch:{ Throwable -> 0x0768 }
            boolean r2 = r2.contains(r4)     // Catch:{ Throwable -> 0x0768 }
            if (r2 == 0) goto L_0x076b
        L_0x074d:
            java.lang.String r2 = "iv"
            r4 = 30
            int r1 = r1.optInt(r2, r4)     // Catch:{ Throwable -> 0x0768 }
            int r1 = r1 * 1000
            long r4 = (long) r1     // Catch:{ Throwable -> 0x0768 }
            ag = r4     // Catch:{ Throwable -> 0x0768 }
        L_0x075b:
            java.lang.String r1 = "pref"
            java.lang.String r2 = "awsi"
            long r4 = ag     // Catch:{ Throwable -> 0x0768 }
            com.loc.dq.a(r14, r1, r2, r4)     // Catch:{ Throwable -> 0x0768 }
            goto L_0x0696
        L_0x0768:
            r1 = move-exception
            goto L_0x0696
        L_0x076b:
            r4 = -1
            ag = r4     // Catch:{ Throwable -> 0x0768 }
            goto L_0x075b
        L_0x0770:
            r10 = move-exception
            goto L_0x0546
        L_0x0773:
            r2 = move-exception
            goto L_0x00c2
        L_0x0776:
            r2 = move-exception
            goto L_0x0087
        L_0x0779:
            r2 = move-exception
            goto L_0x006d
        L_0x077c:
            r2 = move-exception
            goto L_0x0053
        L_0x077f:
            r2 = r1
            goto L_0x0418
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.dh.a(android.content.Context, com.loc.m$a):boolean");
    }

    public static int b() {
        return q;
    }

    public static boolean b(long j2) {
        if (!N) {
            return false;
        }
        return O < 0 || dr.b() - j2 < O;
    }

    public static boolean b(Context context) {
        if (!A) {
            return false;
        }
        if (B == -1 || C == 0) {
            return true;
        }
        if (!dr.b(C, dq.b(context, "pref", "nowtime", 0))) {
            h(context);
            dq.a(context, "pref", "count", 1);
            return true;
        }
        int b2 = dq.b(context, "pref", "count", 0);
        if (b2 >= B) {
            return false;
        }
        dq.a(context, "pref", "count", b2 + 1);
        return true;
    }

    public static boolean c() {
        return r;
    }

    public static boolean c(Context context) {
        if (!E) {
            return false;
        }
        if (F == -1 || G == 0) {
            return true;
        }
        if (!dr.b(G, dq.b(context, "pref", "pushSerTime", 0))) {
            i(context);
            dq.a(context, "pref", "pushCount", 1);
            return true;
        }
        int b2 = dq.b(context, "pref", "pushCount", 0);
        if (b2 >= F) {
            return false;
        }
        dq.a(context, "pref", "pushCount", b2 + 1);
        return true;
    }

    public static int d() {
        return s;
    }

    public static void d(Context context) {
        try {
            u = dq.b(context, "pref", "exception", u);
            e(context);
        } catch (Throwable th) {
            di.a(th, "AuthUtil", "loadLastAbleState p1");
        }
        try {
            v = dq.b(context, "pref", "fn", v);
            w = dq.b(context, "pref", "mpn", w);
            x = dq.b(context, "pref", "igu", x);
            y = dq.b(context, "pref", "ms", y);
            bw.a(v, x, y);
        } catch (Throwable th2) {
        }
        try {
            N = dq.b(context, "pref", "ca", N);
            O = dq.b(context, "pref", "ct", O);
        } catch (Throwable th3) {
        }
        try {
            h = dq.b(context, "pref", "fr", h);
        } catch (Throwable th4) {
        }
        try {
            ab = dq.b(context, "pref", "ok0", ab);
            i = dq.b(context, "pref", "ok1", i);
            ac = dq.b(context, "pref", "ok2", ac);
            ad = dq.b(context, "pref", "ok3", ad);
        } catch (Throwable th5) {
        }
        try {
            af = dq.b(context, "pref", "asw", af);
        } catch (Throwable th6) {
        }
        try {
            ag = dq.b(context, "pref", "awsi", ag);
        } catch (Throwable th7) {
        }
        try {
            ah = dq.b(context, "pref", "15ua", ah);
            ai = dq.b(context, "pref", "15un", ai);
            aj = dq.b(context, "pref", "15ust", aj);
        } catch (Throwable th8) {
        }
    }

    public static void e(Context context) {
        try {
            v b2 = di.b();
            b2.a(u);
            aj.a(context, b2);
        } catch (Throwable th) {
        }
    }

    public static boolean e() {
        return t;
    }

    public static boolean f() {
        return b;
    }

    public static boolean f(Context context) {
        boolean z2 = T != -1 && T < U;
        if (!R || T == 0 || U == 0 || S == 0 || z2) {
            return false;
        }
        if (V != null && V.size() > 0) {
            for (String b2 : V) {
                if (dr.b(context, b2)) {
                    return false;
                }
            }
        }
        if (T == -1 && U == -1) {
            return true;
        }
        long b3 = dq.b(context, "pref", "ots", 0);
        long b4 = dq.b(context, "pref", "otsh", 0);
        int b5 = dq.b(context, "pref", "otn", 0);
        int b6 = dq.b(context, "pref", "otnh", 0);
        if (T != -1) {
            if (!dr.b(S, b3)) {
                try {
                    Editor edit = context.getSharedPreferences("pref", 0).edit();
                    edit.putLong("ots", S);
                    edit.putLong("otsh", S);
                    edit.putInt("otn", 0);
                    edit.putInt("otnh", 0);
                    dq.a(edit);
                } catch (Throwable th) {
                    di.a(th, "AuthUtil", "resetPrefsBind");
                }
                dq.a(context, "pref", "otn", 1);
                dq.a(context, "pref", "otnh", 1);
                return true;
            } else if (b5 < T) {
                if (U == -1) {
                    dq.a(context, "pref", "otn", b5 + 1);
                    dq.a(context, "pref", "otnh", 0);
                    return true;
                } else if (!dr.a(S, b4)) {
                    dq.a(context, "pref", "otsh", S);
                    dq.a(context, "pref", "otn", b5 + 1);
                    dq.a(context, "pref", "otnh", 1);
                    return true;
                } else if (b6 < U) {
                    int i2 = b6 + 1;
                    dq.a(context, "pref", "otn", b5 + 1);
                    dq.a(context, "pref", "otnh", i2);
                    return true;
                }
            }
        }
        if (T == -1) {
            dq.a(context, "pref", "otn", 0);
            if (U == -1) {
                dq.a(context, "pref", "otnh", 0);
                return true;
            } else if (!dr.a(S, b4)) {
                dq.a(context, "pref", "otsh", S);
                dq.a(context, "pref", "otnh", 1);
                return true;
            } else if (b6 < U) {
                dq.a(context, "pref", "otnh", b6 + 1);
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> g() {
        return D;
    }

    public static boolean g(Context context) {
        if (context == null) {
            return false;
        }
        try {
            if (dr.c() - f < ((long) d)) {
                return false;
            }
            g = true;
            return true;
        } catch (Throwable th) {
            di.a(th, "Aps", "isConfigNeedUpdate");
            return false;
        }
    }

    public static ArrayList<String> h() {
        return H;
    }

    private static void h(Context context) {
        try {
            Editor edit = context.getSharedPreferences("pref", 0).edit();
            edit.putLong("nowtime", C);
            edit.putInt("count", 0);
            dq.a(edit);
        } catch (Throwable th) {
            di.a(th, "AuthUtil", "resetPrefsBind");
        }
    }

    private static void i(Context context) {
        try {
            Editor edit = context.getSharedPreferences("pref", 0).edit();
            edit.putLong("pushSerTime", G);
            edit.putInt("pushCount", 0);
            dq.a(edit);
        } catch (Throwable th) {
            di.a(th, "AuthUtil", "resetPrefsBind");
        }
    }

    public static boolean i() {
        return u;
    }

    public static int j() {
        return w;
    }

    public static boolean k() {
        return z;
    }

    public static void l() {
        z = false;
    }

    public static boolean m() {
        return K;
    }

    public static long n() {
        return O;
    }

    public static boolean o() {
        return N;
    }

    public static boolean p() {
        return P;
    }

    public static List<dl> q() {
        return Q;
    }

    public static boolean r() {
        return W;
    }

    public static int s() {
        return X;
    }

    public static boolean t() {
        return Z;
    }

    public static boolean u() {
        return aa;
    }

    public static boolean v() {
        if (!g) {
            return g;
        }
        g = false;
        return true;
    }

    public static boolean w() {
        return h;
    }

    public static boolean x() {
        return ab;
    }

    public static boolean y() {
        return ad;
    }

    public static boolean z() {
        return ac;
    }
}
