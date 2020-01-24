package com.loc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.alibaba.android.arouter.utils.Consts;
import com.iwown.my_module.utility.Constants;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.ArrayList;

@SuppressLint({"NewApi"})
/* compiled from: Req */
public final class dg {
    protected static String J = null;
    protected static String L = null;
    protected String A = null;
    protected String B = null;
    protected ArrayList<ct> C = new ArrayList<>();
    protected String D = null;
    protected String E = null;
    protected ArrayList<ScanResult> F = new ArrayList<>();
    protected String G = null;
    protected String H = null;
    protected byte[] I = null;
    protected String K = null;
    protected String M = null;
    protected String N = null;
    private byte[] O = null;
    private int P = 0;
    public String a = "1";
    protected short b = 0;
    protected String c = null;
    protected String d = null;
    protected String e = null;
    protected String f = null;
    protected String g = null;
    public String h = null;
    public String i = null;
    protected String j = null;
    protected String k = null;
    protected String l = null;
    protected String m = null;
    protected String n = null;
    protected String o = null;
    protected String p = null;
    protected String q = null;
    protected String r = null;
    protected String s = null;
    protected String t = null;
    protected String u = null;
    protected String v = null;
    protected String w = null;
    protected String x = null;
    protected String y = null;
    protected int z = 0;

    private static int a(String str, byte[] bArr, int i2) {
        int i3 = Opcodes.NEG_FLOAT;
        try {
            if (TextUtils.isEmpty(str)) {
                bArr[i2] = 0;
                return i2 + 1;
            }
            byte[] bytes = str.getBytes("GBK");
            int length = bytes.length;
            if (length <= 127) {
                i3 = length;
            }
            bArr[i2] = (byte) i3;
            int i4 = i2 + 1;
            System.arraycopy(bytes, 0, bArr, i4, i3);
            return i3 + i4;
        } catch (Throwable th) {
            di.a(th, "Req", "copyContentWithByteLen");
            bArr[i2] = 0;
            return i2 + 1;
        }
    }

    private String a(String str, int i2) {
        String[] split = this.B.split("\\*")[i2].split(",");
        if ("lac".equals(str)) {
            return split[0];
        }
        if ("cellid".equals(str)) {
            return split[1];
        }
        if ("signal".equals(str)) {
            return split[2];
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000f, code lost:
        if (r0.length != 6) goto L_0x0011;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] a(java.lang.String r8) {
        /*
            r7 = this;
            r6 = 2
            r4 = 6
            r2 = 0
            java.lang.String r0 = ":"
            java.lang.String[] r0 = r8.split(r0)
            byte[] r1 = new byte[r4]
            if (r0 == 0) goto L_0x0011
            int r3 = r0.length     // Catch:{ Throwable -> 0x0045 }
            if (r3 == r4) goto L_0x0020
        L_0x0011:
            r0 = 6
            java.lang.String[] r0 = new java.lang.String[r0]     // Catch:{ Throwable -> 0x0045 }
            r3 = r2
        L_0x0015:
            int r4 = r0.length     // Catch:{ Throwable -> 0x0045 }
            if (r3 >= r4) goto L_0x0020
            java.lang.String r4 = "0"
            r0[r3] = r4     // Catch:{ Throwable -> 0x0045 }
            int r3 = r3 + 1
            goto L_0x0015
        L_0x0020:
            int r3 = r0.length     // Catch:{ Throwable -> 0x0045 }
            if (r2 >= r3) goto L_0x0043
            r3 = r0[r2]     // Catch:{ Throwable -> 0x0045 }
            int r3 = r3.length()     // Catch:{ Throwable -> 0x0045 }
            if (r3 <= r6) goto L_0x0035
            r3 = r0[r2]     // Catch:{ Throwable -> 0x0045 }
            r4 = 0
            r5 = 2
            java.lang.String r3 = r3.substring(r4, r5)     // Catch:{ Throwable -> 0x0045 }
            r0[r2] = r3     // Catch:{ Throwable -> 0x0045 }
        L_0x0035:
            r3 = r0[r2]     // Catch:{ Throwable -> 0x0045 }
            r4 = 16
            int r3 = java.lang.Integer.parseInt(r3, r4)     // Catch:{ Throwable -> 0x0045 }
            byte r3 = (byte) r3     // Catch:{ Throwable -> 0x0045 }
            r1[r2] = r3     // Catch:{ Throwable -> 0x0045 }
            int r2 = r2 + 1
            goto L_0x0020
        L_0x0043:
            r0 = r1
        L_0x0044:
            return r0
        L_0x0045:
            r0 = move-exception
            java.lang.String r1 = "Req"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "getMacBa "
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r8)
            java.lang.String r2 = r2.toString()
            com.loc.di.a(r0, r1, r2)
            java.lang.String r0 = "00:00:00:00:00:00"
            byte[] r0 = r7.a(r0)
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.dg.a(java.lang.String):byte[]");
    }

    private String b(String str) {
        if (!this.A.contains(str + ">")) {
            return "0";
        }
        int indexOf = this.A.indexOf(str + ">");
        return this.A.substring(indexOf + str.length() + 1, this.A.indexOf("</" + str));
    }

    public final void a(Context context, boolean z2, boolean z3, cu cuVar, cw cwVar, ConnectivityManager connectivityManager, String str, String str2) {
        String str3;
        String str4;
        String str5;
        String sb;
        String str6 = "0";
        String str7 = "0";
        String str8 = "0";
        String str9 = "0";
        String str10 = "0";
        String f2 = l.f(context);
        int g2 = dr.g();
        String str11 = "";
        String str12 = "";
        String str13 = "";
        this.K = str2;
        String str14 = "api_serverSDK_130905";
        String str15 = "S128DF1572465B890OE3F7A13167KLEI";
        if (!z3) {
            str3 = "UC_nlp_20131029";
            str4 = "BKZCHMBBSSUK7U8GLUKHBB56CCFF78U";
        } else {
            str3 = str14;
            str4 = str15;
        }
        StringBuilder sb2 = new StringBuilder();
        int e2 = cuVar.e();
        int f3 = cuVar.f();
        TelephonyManager g3 = cuVar.g();
        ArrayList a2 = cuVar.a();
        ArrayList b2 = cuVar.b();
        ArrayList c2 = cwVar.c();
        String str16 = f3 == 2 ? "1" : str6;
        if (g3 != null) {
            if (TextUtils.isEmpty(di.d)) {
                try {
                    di.d = p.v(context);
                } catch (Throwable th) {
                    di.a(th, "Aps", "getApsReq part4");
                }
            }
            if (TextUtils.isEmpty(di.d) && VERSION.SDK_INT < 29) {
                di.d = "888888888888888";
            }
            if (TextUtils.isEmpty(di.e)) {
                try {
                    di.e = g3.getSubscriberId();
                } catch (SecurityException e3) {
                } catch (Throwable th2) {
                    di.a(th2, "Aps", "getApsReq part2");
                }
            }
            if (TextUtils.isEmpty(di.e) && VERSION.SDK_INT < 29) {
                di.e = "888888888888888";
            }
        }
        NetworkInfo networkInfo = null;
        try {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Throwable th3) {
            di.a(th3, "Aps", "getApsReq part");
        }
        boolean a3 = cwVar.a(connectivityManager);
        if (dr.a(networkInfo) != -1) {
            String b3 = dr.b(g3);
            if (a3) {
                str11 = b3;
                str12 = "2";
            } else {
                str11 = b3;
                str12 = "1";
            }
        }
        if (!a2.isEmpty()) {
            StringBuilder sb3 = new StringBuilder();
            switch (f3) {
                case 1:
                    ct ctVar = (ct) a2.get(0);
                    sb3.delete(0, sb3.length());
                    sb3.append("<mcc>").append(ctVar.a).append("</mcc>");
                    sb3.append("<mnc>").append(ctVar.b).append("</mnc>");
                    sb3.append("<lac>").append(ctVar.c).append("</lac>");
                    sb3.append("<cellid>").append(ctVar.d);
                    sb3.append("</cellid>");
                    sb3.append("<signal>").append(ctVar.j);
                    sb3.append("</signal>");
                    String sb4 = sb3.toString();
                    int i2 = 1;
                    while (true) {
                        int i3 = i2;
                        if (i3 >= a2.size()) {
                            sb = sb4;
                            break;
                        } else {
                            ct ctVar2 = (ct) a2.get(i3);
                            sb2.append(ctVar2.c).append(",");
                            sb2.append(ctVar2.d).append(",");
                            sb2.append(ctVar2.j);
                            if (i3 < a2.size() - 1) {
                                sb2.append("*");
                            }
                            i2 = i3 + 1;
                        }
                    }
                case 2:
                    ct ctVar3 = (ct) a2.get(0);
                    sb3.delete(0, sb3.length());
                    sb3.append("<mcc>").append(ctVar3.a).append("</mcc>");
                    sb3.append("<sid>").append(ctVar3.g).append("</sid>");
                    sb3.append("<nid>").append(ctVar3.h).append("</nid>");
                    sb3.append("<bid>").append(ctVar3.i).append("</bid>");
                    if (ctVar3.f > 0 && ctVar3.e > 0) {
                        sb3.append("<lon>").append(ctVar3.f).append("</lon>");
                        sb3.append("<lat>").append(ctVar3.e).append("</lat>");
                    }
                    sb3.append("<signal>").append(ctVar3.j).append("</signal>");
                    sb = sb3.toString();
                    break;
                default:
                    sb = str13;
                    break;
            }
            sb3.delete(0, sb3.length());
            str5 = sb;
        } else {
            str5 = str13;
        }
        if ((e2 & 4) != 4 || b2.isEmpty()) {
            this.C.clear();
        } else {
            this.C.clear();
            this.C.addAll(b2);
        }
        StringBuilder sb5 = new StringBuilder();
        if (cwVar.g()) {
            if (a3) {
                WifiInfo h2 = cwVar.h();
                if (cw.a(h2)) {
                    sb5.append(h2.getBSSID()).append(",");
                    int rssi = h2.getRssi();
                    if (rssi < -128) {
                        rssi = 0;
                    } else if (rssi > 127) {
                        rssi = 0;
                    }
                    sb5.append(rssi).append(",");
                    String ssid = h2.getSSID();
                    int i4 = 32;
                    try {
                        i4 = h2.getSSID().getBytes("UTF-8").length;
                    } catch (Exception e4) {
                    }
                    if (i4 >= 32) {
                        ssid = "unkwn";
                    }
                    sb5.append(ssid.replace("*", Consts.DOT));
                }
            }
            if (!(c2 == null || this.F == null)) {
                this.F.clear();
                this.F.addAll(c2);
            }
        } else {
            cwVar.d();
            if (this.F != null) {
                this.F.clear();
            }
        }
        this.b = 0;
        if (!z2) {
            this.b = (short) (this.b | 2);
        }
        this.c = str3;
        this.d = str4;
        this.f = dr.e();
        this.g = new StringBuilder(Constants.APPSYSTEM).append(dr.f()).toString();
        this.h = dr.b(context);
        this.i = str16;
        this.j = str7;
        this.k = "0";
        this.l = str8;
        this.m = str9;
        this.n = str10;
        this.o = f2;
        this.p = di.d;
        this.q = di.e;
        this.s = String.valueOf(g2);
        this.t = dr.j(context);
        this.v = "4.7.0";
        this.w = str;
        this.u = "";
        this.x = str11;
        this.y = str12;
        this.z = e2;
        this.A = str5;
        this.B = sb2.toString();
        this.D = cuVar.k();
        this.G = cw.l();
        this.E = sb5.toString();
        try {
            if (TextUtils.isEmpty(J)) {
                J = p.h(context);
            }
        } catch (Throwable th4) {
        }
        try {
            if (TextUtils.isEmpty(L)) {
                L = p.b(context);
            }
        } catch (Throwable th5) {
        }
        try {
            if (TextUtils.isEmpty(this.N)) {
                this.N = p.i(context);
            }
        } catch (Throwable th6) {
        }
        sb2.delete(0, sb2.length());
        sb5.delete(0, sb5.length());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:316:0x08ae, code lost:
        if (r6 < 0) goto L_0x08b0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:231:0x06a8  */
    /* JADX WARNING: Removed duplicated region for block: B:234:0x06bd  */
    /* JADX WARNING: Removed duplicated region for block: B:239:0x06d7 A[Catch:{ Throwable -> 0x090a }] */
    /* JADX WARNING: Removed duplicated region for block: B:241:0x06da A[Catch:{ Throwable -> 0x090a }] */
    /* JADX WARNING: Removed duplicated region for block: B:247:0x06ef A[Catch:{ Throwable -> 0x0912 }] */
    /* JADX WARNING: Removed duplicated region for block: B:251:0x0704  */
    /* JADX WARNING: Removed duplicated region for block: B:265:0x0741  */
    /* JADX WARNING: Removed duplicated region for block: B:268:0x0754  */
    /* JADX WARNING: Removed duplicated region for block: B:271:0x0778  */
    /* JADX WARNING: Removed duplicated region for block: B:274:0x07ab  */
    /* JADX WARNING: Removed duplicated region for block: B:298:0x0842  */
    /* JADX WARNING: Removed duplicated region for block: B:329:0x08fb A[SYNTHETIC, Splitter:B:329:0x08fb] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final byte[] a() {
        /*
            r19 = this;
            r0 = r19
            java.lang.String r2 = r0.a
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0011
            java.lang.String r2 = ""
            r0 = r19
            r0.a = r2
        L_0x0011:
            r0 = r19
            java.lang.String r2 = r0.c
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0022
            java.lang.String r2 = ""
            r0 = r19
            r0.c = r2
        L_0x0022:
            r0 = r19
            java.lang.String r2 = r0.d
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0033
            java.lang.String r2 = ""
            r0 = r19
            r0.d = r2
        L_0x0033:
            r0 = r19
            java.lang.String r2 = r0.e
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0044
            java.lang.String r2 = ""
            r0 = r19
            r0.e = r2
        L_0x0044:
            r0 = r19
            java.lang.String r2 = r0.f
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0055
            java.lang.String r2 = ""
            r0 = r19
            r0.f = r2
        L_0x0055:
            r0 = r19
            java.lang.String r2 = r0.g
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0066
            java.lang.String r2 = ""
            r0 = r19
            r0.g = r2
        L_0x0066:
            r0 = r19
            java.lang.String r2 = r0.h
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0077
            java.lang.String r2 = ""
            r0 = r19
            r0.h = r2
        L_0x0077:
            r0 = r19
            java.lang.String r2 = r0.i
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0088
            java.lang.String r2 = ""
            r0 = r19
            r0.i = r2
        L_0x0088:
            r0 = r19
            java.lang.String r2 = r0.j
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x04ab
            java.lang.String r2 = "0"
            r0 = r19
            r0.j = r2
        L_0x0099:
            r0 = r19
            java.lang.String r2 = r0.k
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x04ce
            java.lang.String r2 = "0"
            r0 = r19
            r0.k = r2
        L_0x00aa:
            r0 = r19
            java.lang.String r2 = r0.l
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x00bb
            java.lang.String r2 = ""
            r0 = r19
            r0.l = r2
        L_0x00bb:
            r0 = r19
            java.lang.String r2 = r0.m
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x00cc
            java.lang.String r2 = ""
            r0 = r19
            r0.m = r2
        L_0x00cc:
            r0 = r19
            java.lang.String r2 = r0.n
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x00dd
            java.lang.String r2 = ""
            r0 = r19
            r0.n = r2
        L_0x00dd:
            r0 = r19
            java.lang.String r2 = r0.o
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x00ee
            java.lang.String r2 = ""
            r0 = r19
            r0.o = r2
        L_0x00ee:
            r0 = r19
            java.lang.String r2 = r0.p
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x00ff
            java.lang.String r2 = ""
            r0 = r19
            r0.p = r2
        L_0x00ff:
            r0 = r19
            java.lang.String r2 = r0.q
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0110
            java.lang.String r2 = ""
            r0 = r19
            r0.q = r2
        L_0x0110:
            r0 = r19
            java.lang.String r2 = r0.r
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0121
            java.lang.String r2 = ""
            r0 = r19
            r0.r = r2
        L_0x0121:
            r0 = r19
            java.lang.String r2 = r0.s
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0132
            java.lang.String r2 = ""
            r0 = r19
            r0.s = r2
        L_0x0132:
            r0 = r19
            java.lang.String r2 = r0.t
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0143
            java.lang.String r2 = ""
            r0 = r19
            r0.t = r2
        L_0x0143:
            r0 = r19
            java.lang.String r2 = r0.u
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0154
            java.lang.String r2 = ""
            r0 = r19
            r0.u = r2
        L_0x0154:
            r0 = r19
            java.lang.String r2 = r0.v
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0165
            java.lang.String r2 = ""
            r0 = r19
            r0.v = r2
        L_0x0165:
            r0 = r19
            java.lang.String r2 = r0.w
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0176
            java.lang.String r2 = ""
            r0 = r19
            r0.w = r2
        L_0x0176:
            r0 = r19
            java.lang.String r2 = r0.x
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x0187
            java.lang.String r2 = ""
            r0 = r19
            r0.x = r2
        L_0x0187:
            r0 = r19
            java.lang.String r2 = r0.y
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x04f1
            java.lang.String r2 = "0"
            r0 = r19
            r0.y = r2
        L_0x0198:
            r0 = r19
            int r2 = r0.z
            if (r2 <= 0) goto L_0x01a2
            r3 = 15
            if (r2 <= r3) goto L_0x0514
        L_0x01a2:
            r2 = 0
        L_0x01a3:
            if (r2 != 0) goto L_0x01aa
            r2 = 0
            r0 = r19
            r0.z = r2
        L_0x01aa:
            r0 = r19
            java.lang.String r2 = r0.A
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x01bb
            java.lang.String r2 = ""
            r0 = r19
            r0.A = r2
        L_0x01bb:
            r0 = r19
            java.lang.String r2 = r0.B
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x01cc
            java.lang.String r2 = ""
            r0 = r19
            r0.B = r2
        L_0x01cc:
            r0 = r19
            java.lang.String r2 = r0.E
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x01dd
            java.lang.String r2 = ""
            r0 = r19
            r0.E = r2
        L_0x01dd:
            r0 = r19
            java.lang.String r2 = r0.G
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x01ee
            java.lang.String r2 = ""
            r0 = r19
            r0.G = r2
        L_0x01ee:
            r0 = r19
            java.lang.String r2 = r0.H
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x01ff
            java.lang.String r2 = ""
            r0 = r19
            r0.H = r2
        L_0x01ff:
            java.lang.String r2 = J
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x020c
            java.lang.String r2 = ""
            J = r2
        L_0x020c:
            r0 = r19
            byte[] r2 = r0.I
            if (r2 != 0) goto L_0x0219
            r2 = 0
            byte[] r2 = new byte[r2]
            r0 = r19
            r0.I = r2
        L_0x0219:
            r0 = r19
            java.lang.String r2 = r0.N
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x022a
            java.lang.String r2 = ""
            r0 = r19
            r0.N = r2
        L_0x022a:
            r2 = 2
            byte[] r10 = new byte[r2]
            r2 = 4
            byte[] r8 = new byte[r2]
            r2 = 4096(0x1000, float:5.74E-42)
            r0 = r19
            byte[] r3 = r0.I
            if (r3 == 0) goto L_0x0241
            r0 = r19
            byte[] r2 = r0.I
            int r2 = r2.length
            int r2 = r2 + 1
            int r2 = r2 + 4096
        L_0x0241:
            r0 = r19
            byte[] r3 = r0.O
            if (r3 == 0) goto L_0x024d
            r0 = r19
            int r3 = r0.P
            if (r2 <= r3) goto L_0x0517
        L_0x024d:
            byte[] r3 = new byte[r2]
            r0 = r19
            r0.O = r3
            r0 = r19
            r0.P = r2
        L_0x0257:
            r2 = 0
            r0 = r19
            java.lang.String r4 = r0.a
            byte r4 = com.loc.dr.j(r4)
            r3[r2] = r4
            r0 = r19
            short r2 = r0.b
            r4 = 0
            byte[] r2 = com.loc.dr.a(r2, r4)
            r4 = 0
            r5 = 1
            int r6 = r2.length
            java.lang.System.arraycopy(r2, r4, r3, r5, r6)
            int r2 = r2.length
            int r2 = r2 + 1
            r0 = r19
            java.lang.String r4 = r0.c
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.d
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.o
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.e
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.f
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.g
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.u
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.h
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.p
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.q
            int r4 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r2 = r0.t     // Catch:{ Throwable -> 0x0537 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x0537 }
            if (r2 == 0) goto L_0x051e
            r2 = 0
            r3[r4] = r2     // Catch:{ Throwable -> 0x0537 }
            int r2 = r4 + 1
        L_0x02d3:
            r0 = r19
            java.lang.String r4 = r0.v
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.w
            int r2 = a(r4, r3, r2)
            java.lang.String r4 = J
            int r2 = a(r4, r3, r2)
            java.lang.String r4 = L
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.x
            int r2 = a(r4, r3, r2)
            r0 = r19
            java.lang.String r4 = r0.y
            byte r4 = java.lang.Byte.parseByte(r4)
            r3[r2] = r4
            int r2 = r2 + 1
            r0 = r19
            java.lang.String r4 = r0.j
            byte r4 = java.lang.Byte.parseByte(r4)
            r3[r2] = r4
            int r2 = r2 + 1
            r0 = r19
            int r4 = r0.z
            r5 = r4 & 3
            r0 = r19
            int r4 = r0.z
            byte r4 = (byte) r4
            r3[r2] = r4
            int r2 = r2 + 1
            r4 = 1
            if (r5 == r4) goto L_0x0324
            r4 = 2
            if (r5 != r4) goto L_0x03ac
        L_0x0324:
            java.lang.String r4 = "mcc"
            r0 = r19
            java.lang.String r4 = r0.b(r4)
            byte[] r4 = com.loc.dr.b(r4)
            r6 = 0
            int r7 = r4.length
            java.lang.System.arraycopy(r4, r6, r3, r2, r7)
            int r4 = r4.length
            int r2 = r2 + r4
            r4 = 1
            if (r5 != r4) goto L_0x0548
            java.lang.String r4 = "mnc"
            r0 = r19
            java.lang.String r4 = r0.b(r4)
            byte[] r4 = com.loc.dr.b(r4)
            r6 = 0
            int r7 = r4.length
            java.lang.System.arraycopy(r4, r6, r3, r2, r7)
            int r4 = r4.length
            int r2 = r2 + r4
            java.lang.String r4 = "lac"
            r0 = r19
            java.lang.String r4 = r0.b(r4)
            byte[] r4 = com.loc.dr.b(r4)
            r6 = 0
            int r7 = r4.length
            java.lang.System.arraycopy(r4, r6, r3, r2, r7)
            int r4 = r4.length
            int r2 = r2 + r4
            java.lang.String r4 = "cellid"
            r0 = r19
            java.lang.String r4 = r0.b(r4)
            byte[] r4 = com.loc.dr.c(r4)
            r6 = 0
            int r7 = r4.length
            java.lang.System.arraycopy(r4, r6, r3, r2, r7)
            int r4 = r4.length
            int r2 = r2 + r4
        L_0x0377:
            java.lang.String r4 = "signal"
            r0 = r19
            java.lang.String r4 = r0.b(r4)
            int r4 = java.lang.Integer.parseInt(r4)
            r6 = 127(0x7f, float:1.78E-43)
            if (r4 <= r6) goto L_0x05b1
            r4 = 0
        L_0x0389:
            byte r4 = (byte) r4
            r3[r2] = r4
            int r2 = r2 + 1
            r4 = 0
            byte[] r4 = com.loc.dr.a(r4, r10)
            r6 = 0
            int r7 = r4.length
            java.lang.System.arraycopy(r4, r6, r3, r2, r7)
            int r2 = r2 + 2
            r4 = 1
            if (r5 != r4) goto L_0x061b
            r0 = r19
            java.lang.String r4 = r0.B
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L_0x05b8
            r4 = 0
            r3[r2] = r4
            int r2 = r2 + 1
        L_0x03ac:
            r0 = r19
            java.lang.String r4 = r0.D
            if (r4 == 0) goto L_0x0626
            r0 = r19
            int r5 = r0.z
            r5 = r5 & 8
            r6 = 8
            if (r5 != r6) goto L_0x0626
            java.lang.String r5 = "GBK"
            byte[] r4 = r4.getBytes(r5)     // Catch:{ Exception -> 0x0625 }
            int r5 = r4.length     // Catch:{ Exception -> 0x0625 }
            r6 = 60
            int r5 = java.lang.Math.min(r5, r6)     // Catch:{ Exception -> 0x0625 }
            byte r6 = (byte) r5     // Catch:{ Exception -> 0x0625 }
            r3[r2] = r6     // Catch:{ Exception -> 0x0625 }
            int r2 = r2 + 1
            r6 = 0
            java.lang.System.arraycopy(r4, r6, r3, r2, r5)     // Catch:{ Exception -> 0x0625 }
            int r2 = r2 + r5
            r4 = r2
        L_0x03d5:
            r0 = r19
            java.util.ArrayList<com.loc.ct> r9 = r0.C
            int r5 = r9.size()
            r0 = r19
            int r2 = r0.z
            r2 = r2 & 4
            r6 = 4
            if (r2 != r6) goto L_0x0699
            if (r5 <= 0) goto L_0x0699
            r2 = 0
            java.lang.Object r2 = r9.get(r2)
            com.loc.ct r2 = (com.loc.ct) r2
            boolean r2 = r2.p
            if (r2 != 0) goto L_0x03f6
            int r2 = r5 + -1
            r5 = r2
        L_0x03f6:
            byte r2 = (byte) r5
            r3[r4] = r2
            int r4 = r4 + 1
            r2 = 0
            r7 = r2
        L_0x03fd:
            if (r7 >= r5) goto L_0x069e
            java.lang.Object r2 = r9.get(r7)
            com.loc.ct r2 = (com.loc.ct) r2
            boolean r6 = r2.p
            if (r6 == 0) goto L_0x04a6
            int r6 = r2.k
            r11 = 1
            if (r6 == r11) goto L_0x0418
            int r6 = r2.k
            r11 = 3
            if (r6 == r11) goto L_0x0418
            int r6 = r2.k
            r11 = 4
            if (r6 != r11) goto L_0x062e
        L_0x0418:
            int r6 = r2.k
            byte r6 = (byte) r6
            boolean r11 = r2.n
            if (r11 == 0) goto L_0x0422
            r6 = r6 | 8
            byte r6 = (byte) r6
        L_0x0422:
            r3[r4] = r6
            int r4 = r4 + 1
            int r6 = r2.a
            byte[] r6 = com.loc.dr.a(r6, r10)
            r11 = 0
            int r12 = r6.length
            java.lang.System.arraycopy(r6, r11, r3, r4, r12)
            int r6 = r6.length
            int r4 = r4 + r6
            int r6 = r2.b
            byte[] r6 = com.loc.dr.a(r6, r10)
            r11 = 0
            int r12 = r6.length
            java.lang.System.arraycopy(r6, r11, r3, r4, r12)
            int r6 = r6.length
            int r4 = r4 + r6
            int r6 = r2.c
            byte[] r6 = com.loc.dr.a(r6, r10)
            r11 = 0
            int r12 = r6.length
            java.lang.System.arraycopy(r6, r11, r3, r4, r12)
            int r6 = r6.length
            int r4 = r4 + r6
            int r6 = r2.d
            byte[] r6 = com.loc.dr.b(r6, r8)
            r11 = 0
            int r12 = r6.length
            java.lang.System.arraycopy(r6, r11, r3, r4, r12)
            int r6 = r6.length
            int r4 = r4 + r6
        L_0x045a:
            int r6 = r2.j
            r11 = 127(0x7f, float:1.78E-43)
            if (r6 <= r11) goto L_0x0691
            r6 = 99
        L_0x0462:
            byte r6 = (byte) r6
            r3[r4] = r6
            int r4 = r4 + 1
            short r6 = r2.l
            byte[] r6 = com.loc.dr.a(r6, r10)
            r11 = 0
            int r12 = r6.length
            java.lang.System.arraycopy(r6, r11, r3, r4, r12)
            int r6 = r6.length
            int r4 = r4 + r6
            java.lang.String r6 = "5.1"
            java.lang.Double r6 = java.lang.Double.valueOf(r6)
            double r12 = r6.doubleValue()
            r14 = 4617315517961601024(0x4014000000000000, double:5.0)
            int r6 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r6 < 0) goto L_0x04a6
            int r6 = r2.k
            r11 = 3
            if (r6 == r11) goto L_0x048f
            int r6 = r2.k
            r11 = 4
            if (r6 != r11) goto L_0x04a6
        L_0x048f:
            int r2 = r2.o
            r6 = 32767(0x7fff, float:4.5916E-41)
            if (r2 <= r6) goto L_0x0497
            r2 = 32767(0x7fff, float:4.5916E-41)
        L_0x0497:
            if (r2 >= 0) goto L_0x049b
            r2 = 32767(0x7fff, float:4.5916E-41)
        L_0x049b:
            byte[] r2 = com.loc.dr.a(r2, r10)
            r6 = 0
            int r11 = r2.length
            java.lang.System.arraycopy(r2, r6, r3, r4, r11)
            int r2 = r2.length
            int r4 = r4 + r2
        L_0x04a6:
            int r2 = r7 + 1
            r7 = r2
            goto L_0x03fd
        L_0x04ab:
            java.lang.String r2 = "0"
            r0 = r19
            java.lang.String r3 = r0.j
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0099
            java.lang.String r2 = "2"
            r0 = r19
            java.lang.String r3 = r0.j
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0099
            java.lang.String r2 = "0"
            r0 = r19
            r0.j = r2
            goto L_0x0099
        L_0x04ce:
            java.lang.String r2 = "0"
            r0 = r19
            java.lang.String r3 = r0.k
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x00aa
            java.lang.String r2 = "1"
            r0 = r19
            java.lang.String r3 = r0.k
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x00aa
            java.lang.String r2 = "0"
            r0 = r19
            r0.k = r2
            goto L_0x00aa
        L_0x04f1:
            java.lang.String r2 = "1"
            r0 = r19
            java.lang.String r3 = r0.y
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0198
            java.lang.String r2 = "2"
            r0 = r19
            java.lang.String r3 = r0.y
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0198
            java.lang.String r2 = "0"
            r0 = r19
            r0.y = r2
            goto L_0x0198
        L_0x0514:
            r2 = 1
            goto L_0x01a3
        L_0x0517:
            r0 = r19
            byte[] r2 = r0.O
            r3 = r2
            goto L_0x0257
        L_0x051e:
            r0 = r19
            java.lang.String r2 = r0.t     // Catch:{ Throwable -> 0x0537 }
            r0 = r19
            byte[] r2 = r0.a(r2)     // Catch:{ Throwable -> 0x0537 }
            int r5 = r2.length     // Catch:{ Throwable -> 0x0537 }
            byte r5 = (byte) r5     // Catch:{ Throwable -> 0x0537 }
            r3[r4] = r5     // Catch:{ Throwable -> 0x0537 }
            int r4 = r4 + 1
            r5 = 0
            int r6 = r2.length     // Catch:{ Throwable -> 0x0537 }
            java.lang.System.arraycopy(r2, r5, r3, r4, r6)     // Catch:{ Throwable -> 0x0537 }
            int r2 = r2.length     // Catch:{ Throwable -> 0x0537 }
            int r2 = r2 + r4
            goto L_0x02d3
        L_0x0537:
            r2 = move-exception
            java.lang.String r5 = "Req"
            java.lang.String r6 = "buildV4Dot219"
            com.loc.di.a(r2, r5, r6)
            r2 = 0
            r3[r4] = r2
            int r2 = r4 + 1
            goto L_0x02d3
        L_0x0548:
            r4 = 2
            if (r5 != r4) goto L_0x0377
            java.lang.String r4 = "sid"
            r0 = r19
            java.lang.String r4 = r0.b(r4)
            byte[] r4 = com.loc.dr.b(r4)
            r6 = 0
            int r7 = r4.length
            java.lang.System.arraycopy(r4, r6, r3, r2, r7)
            int r4 = r4.length
            int r2 = r2 + r4
            java.lang.String r4 = "nid"
            r0 = r19
            java.lang.String r4 = r0.b(r4)
            byte[] r4 = com.loc.dr.b(r4)
            r6 = 0
            int r7 = r4.length
            java.lang.System.arraycopy(r4, r6, r3, r2, r7)
            int r4 = r4.length
            int r2 = r2 + r4
            java.lang.String r4 = "bid"
            r0 = r19
            java.lang.String r4 = r0.b(r4)
            byte[] r4 = com.loc.dr.b(r4)
            r6 = 0
            int r7 = r4.length
            java.lang.System.arraycopy(r4, r6, r3, r2, r7)
            int r4 = r4.length
            int r2 = r2 + r4
            java.lang.String r4 = "lon"
            r0 = r19
            java.lang.String r4 = r0.b(r4)
            byte[] r4 = com.loc.dr.c(r4)
            r6 = 0
            int r7 = r4.length
            java.lang.System.arraycopy(r4, r6, r3, r2, r7)
            int r4 = r4.length
            int r2 = r2 + r4
            java.lang.String r4 = "lat"
            r0 = r19
            java.lang.String r4 = r0.b(r4)
            byte[] r4 = com.loc.dr.c(r4)
            r6 = 0
            int r7 = r4.length
            java.lang.System.arraycopy(r4, r6, r3, r2, r7)
            int r4 = r4.length
            int r2 = r2 + r4
            goto L_0x0377
        L_0x05b1:
            r6 = -128(0xffffffffffffff80, float:NaN)
            if (r4 >= r6) goto L_0x0389
            r4 = 0
            goto L_0x0389
        L_0x05b8:
            r0 = r19
            java.lang.String r4 = r0.B
            java.lang.String r5 = "\\*"
            java.lang.String[] r4 = r4.split(r5)
            int r6 = r4.length
            byte r4 = (byte) r6
            r3[r2] = r4
            int r4 = r2 + 1
            r2 = 0
            r18 = r2
            r2 = r4
            r4 = r18
        L_0x05cf:
            if (r4 >= r6) goto L_0x03ac
            java.lang.String r5 = "lac"
            r0 = r19
            java.lang.String r5 = r0.a(r5, r4)
            byte[] r5 = com.loc.dr.b(r5)
            r7 = 0
            int r9 = r5.length
            java.lang.System.arraycopy(r5, r7, r3, r2, r9)
            int r5 = r5.length
            int r2 = r2 + r5
            java.lang.String r5 = "cellid"
            r0 = r19
            java.lang.String r5 = r0.a(r5, r4)
            byte[] r5 = com.loc.dr.c(r5)
            r7 = 0
            int r9 = r5.length
            java.lang.System.arraycopy(r5, r7, r3, r2, r9)
            int r5 = r5.length
            int r5 = r5 + r2
            java.lang.String r2 = "signal"
            r0 = r19
            java.lang.String r2 = r0.a(r2, r4)
            int r2 = java.lang.Integer.parseInt(r2)
            r7 = 127(0x7f, float:1.78E-43)
            if (r2 <= r7) goto L_0x0615
            r2 = 0
        L_0x060b:
            byte r2 = (byte) r2
            r3[r5] = r2
            int r5 = r5 + 1
            int r2 = r4 + 1
            r4 = r2
            r2 = r5
            goto L_0x05cf
        L_0x0615:
            r7 = -128(0xffffffffffffff80, float:NaN)
            if (r2 >= r7) goto L_0x060b
            r2 = 0
            goto L_0x060b
        L_0x061b:
            r4 = 2
            if (r5 != r4) goto L_0x03ac
            r4 = 0
            r3[r2] = r4
            int r2 = r2 + 1
            goto L_0x03ac
        L_0x0625:
            r4 = move-exception
        L_0x0626:
            r4 = 0
            r3[r2] = r4
            int r2 = r2 + 1
            r4 = r2
            goto L_0x03d5
        L_0x062e:
            int r6 = r2.k
            r11 = 2
            if (r6 != r11) goto L_0x045a
            int r6 = r2.k
            byte r6 = (byte) r6
            boolean r11 = r2.n
            if (r11 == 0) goto L_0x063d
            r6 = r6 | 8
            byte r6 = (byte) r6
        L_0x063d:
            r3[r4] = r6
            int r4 = r4 + 1
            int r6 = r2.a
            byte[] r6 = com.loc.dr.a(r6, r10)
            r11 = 0
            int r12 = r6.length
            java.lang.System.arraycopy(r6, r11, r3, r4, r12)
            int r6 = r6.length
            int r4 = r4 + r6
            int r6 = r2.g
            byte[] r6 = com.loc.dr.a(r6, r10)
            r11 = 0
            int r12 = r6.length
            java.lang.System.arraycopy(r6, r11, r3, r4, r12)
            int r6 = r6.length
            int r4 = r4 + r6
            int r6 = r2.h
            byte[] r6 = com.loc.dr.a(r6, r10)
            r11 = 0
            int r12 = r6.length
            java.lang.System.arraycopy(r6, r11, r3, r4, r12)
            int r6 = r6.length
            int r4 = r4 + r6
            int r6 = r2.i
            byte[] r6 = com.loc.dr.a(r6, r10)
            r11 = 0
            int r12 = r6.length
            java.lang.System.arraycopy(r6, r11, r3, r4, r12)
            int r6 = r6.length
            int r4 = r4 + r6
            int r6 = r2.f
            byte[] r6 = com.loc.dr.b(r6, r8)
            r11 = 0
            int r12 = r6.length
            java.lang.System.arraycopy(r6, r11, r3, r4, r12)
            int r6 = r6.length
            int r4 = r4 + r6
            int r6 = r2.e
            byte[] r6 = com.loc.dr.b(r6, r8)
            r11 = 0
            int r12 = r6.length
            java.lang.System.arraycopy(r6, r11, r3, r4, r12)
            int r6 = r6.length
            int r4 = r4 + r6
            goto L_0x045a
        L_0x0691:
            r11 = -128(0xffffffffffffff80, float:NaN)
            if (r6 >= r11) goto L_0x0462
            r6 = 99
            goto L_0x0462
        L_0x0699:
            r2 = 0
            r3[r4] = r2
            int r4 = r4 + 1
        L_0x069e:
            r0 = r19
            java.lang.String r2 = r0.E
            int r2 = r2.length()
            if (r2 != 0) goto L_0x07ab
            r2 = 0
            r3[r4] = r2
            int r2 = r4 + 1
        L_0x06ad:
            r0 = r19
            java.util.ArrayList<android.net.wifi.ScanResult> r11 = r0.F
            int r4 = r11.size()
            r5 = 25
            int r12 = java.lang.Math.min(r4, r5)
            if (r12 != 0) goto L_0x0842
            r4 = 0
            r3[r2] = r4
            int r2 = r2 + 1
        L_0x06c2:
            r4 = 0
            r3[r2] = r4
            int r2 = r2 + 1
            r0 = r19
            java.lang.String r4 = r0.H     // Catch:{ Throwable -> 0x090a }
            java.lang.String r5 = "GBK"
            byte[] r4 = r4.getBytes(r5)     // Catch:{ Throwable -> 0x090a }
            int r5 = r4.length     // Catch:{ Throwable -> 0x090a }
            r6 = 127(0x7f, float:1.78E-43)
            if (r5 <= r6) goto L_0x06d8
            r4 = 0
        L_0x06d8:
            if (r4 != 0) goto L_0x08fb
            r4 = 0
            r3[r2] = r4     // Catch:{ Throwable -> 0x090a }
            int r2 = r2 + 1
        L_0x06df:
            r4 = 2
            byte[] r4 = new byte[r4]
            r4 = {0, 0} // fill-array
            r0 = r19
            java.lang.String r5 = r0.K     // Catch:{ Throwable -> 0x0912 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0912 }
            if (r5 != 0) goto L_0x06fb
            r0 = r19
            java.lang.String r4 = r0.K     // Catch:{ Throwable -> 0x0912 }
            int r4 = r4.length()     // Catch:{ Throwable -> 0x0912 }
            byte[] r4 = com.loc.dr.a(r4, r10)     // Catch:{ Throwable -> 0x0912 }
        L_0x06fb:
            r6 = 0
            r7 = 2
            java.lang.System.arraycopy(r4, r6, r3, r2, r7)     // Catch:{ Throwable -> 0x0912 }
            int r2 = r2 + 2
            if (r5 != 0) goto L_0x0716
            r0 = r19
            java.lang.String r4 = r0.K     // Catch:{ Throwable -> 0x0921 }
            java.lang.String r5 = "GBK"
            byte[] r4 = r4.getBytes(r5)     // Catch:{ Throwable -> 0x0921 }
            r5 = 0
            int r6 = r4.length     // Catch:{ Throwable -> 0x0921 }
            java.lang.System.arraycopy(r4, r5, r3, r2, r6)     // Catch:{ Throwable -> 0x0921 }
            int r4 = r4.length     // Catch:{ Throwable -> 0x0921 }
            int r2 = r2 + r4
        L_0x0716:
            r4 = 2
            byte[] r4 = new byte[r4]
            r5 = 0
            r6 = 0
            r4[r5] = r6
            r5 = 1
            r6 = 0
            r4[r5] = r6
            r4 = 0
            byte[] r4 = com.loc.dr.a(r4, r10)     // Catch:{ Throwable -> 0x0917 }
            r5 = 0
            r6 = 2
            java.lang.System.arraycopy(r4, r5, r3, r2, r6)     // Catch:{ Throwable -> 0x0917 }
            int r2 = r2 + 2
        L_0x072d:
            r4 = 2
            byte[] r4 = new byte[r4]
            r4 = {0, 0} // fill-array
            r5 = 0
            r6 = 2
            java.lang.System.arraycopy(r4, r5, r3, r2, r6)     // Catch:{ Throwable -> 0x091c }
            int r2 = r2 + 2
        L_0x073a:
            r4 = 0
            r0 = r19
            byte[] r5 = r0.I
            if (r5 == 0) goto L_0x0746
            r0 = r19
            byte[] r4 = r0.I
            int r4 = r4.length
        L_0x0746:
            r5 = 0
            byte[] r5 = com.loc.dr.a(r4, r5)
            r6 = 0
            int r7 = r5.length
            java.lang.System.arraycopy(r5, r6, r3, r2, r7)
            int r5 = r5.length
            int r2 = r2 + r5
            if (r4 <= 0) goto L_0x0767
            r0 = r19
            byte[] r4 = r0.I
            r5 = 0
            r0 = r19
            byte[] r6 = r0.I
            int r6 = r6.length
            java.lang.System.arraycopy(r4, r5, r3, r2, r6)
            r0 = r19
            byte[] r4 = r0.I
            int r4 = r4.length
            int r2 = r2 + r4
        L_0x0767:
            java.lang.String r4 = "5.1"
            java.lang.Double r4 = java.lang.Double.valueOf(r4)
            double r4 = r4.doubleValue()
            r6 = 4617315517961601024(0x4014000000000000, double:5.0)
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 < 0) goto L_0x0785
            r4 = 0
            r3[r2] = r4
            int r2 = r2 + 1
            r0 = r19
            java.lang.String r4 = r0.N
            int r2 = a(r4, r3, r2)
        L_0x0785:
            byte[] r4 = new byte[r2]
            r5 = 0
            r6 = 0
            java.lang.System.arraycopy(r3, r5, r4, r6, r2)
            java.util.zip.CRC32 r3 = new java.util.zip.CRC32
            r3.<init>()
            r3.update(r4)
            long r6 = r3.getValue()
            byte[] r3 = com.loc.dr.a(r6)
            int r5 = r3.length
            int r5 = r5 + r2
            byte[] r5 = new byte[r5]
            r6 = 0
            r7 = 0
            java.lang.System.arraycopy(r4, r6, r5, r7, r2)
            r4 = 0
            int r6 = r3.length
            java.lang.System.arraycopy(r3, r4, r5, r2, r6)
            return r5
        L_0x07ab:
            r2 = 1
            r3[r4] = r2
            int r4 = r4 + 1
            r0 = r19
            java.lang.String r2 = r0.E     // Catch:{ Throwable -> 0x0816 }
            java.lang.String r5 = ","
            java.lang.String[] r5 = r2.split(r5)     // Catch:{ Throwable -> 0x0816 }
            r2 = 0
            r2 = r5[r2]     // Catch:{ Throwable -> 0x0816 }
            r0 = r19
            byte[] r2 = r0.a(r2)     // Catch:{ Throwable -> 0x0816 }
            r6 = 0
            int r7 = r2.length     // Catch:{ Throwable -> 0x0816 }
            java.lang.System.arraycopy(r2, r6, r3, r4, r7)     // Catch:{ Throwable -> 0x0816 }
            int r2 = r2.length     // Catch:{ Throwable -> 0x0816 }
            int r4 = r4 + r2
            r2 = 2
            r2 = r5[r2]     // Catch:{ Throwable -> 0x0800 }
            java.lang.String r6 = "GBK"
            byte[] r6 = r2.getBytes(r6)     // Catch:{ Throwable -> 0x0800 }
            int r2 = r6.length     // Catch:{ Throwable -> 0x0800 }
            r7 = 127(0x7f, float:1.78E-43)
            if (r2 <= r7) goto L_0x07dc
            r2 = 127(0x7f, float:1.78E-43)
        L_0x07dc:
            byte r7 = (byte) r2     // Catch:{ Throwable -> 0x0800 }
            r3[r4] = r7     // Catch:{ Throwable -> 0x0800 }
            int r4 = r4 + 1
            r7 = 0
            java.lang.System.arraycopy(r6, r7, r3, r4, r2)     // Catch:{ Throwable -> 0x0800 }
            int r4 = r4 + r2
        L_0x07e6:
            r2 = 1
            r2 = r5[r2]     // Catch:{ Throwable -> 0x0816 }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Throwable -> 0x0816 }
            r5 = 127(0x7f, float:1.78E-43)
            if (r2 <= r5) goto L_0x0810
            r2 = 0
        L_0x07f2:
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0816 }
            byte r2 = java.lang.Byte.parseByte(r2)     // Catch:{ Throwable -> 0x0816 }
            r3[r4] = r2     // Catch:{ Throwable -> 0x0816 }
            int r2 = r4 + 1
            goto L_0x06ad
        L_0x0800:
            r2 = move-exception
            java.lang.String r6 = "Req"
            java.lang.String r7 = "buildV4Dot214"
            com.loc.di.a(r2, r6, r7)     // Catch:{ Throwable -> 0x0816 }
            r2 = 0
            r3[r4] = r2     // Catch:{ Throwable -> 0x0816 }
            int r4 = r4 + 1
            goto L_0x07e6
        L_0x0810:
            r5 = -128(0xffffffffffffff80, float:NaN)
            if (r2 >= r5) goto L_0x07f2
            r2 = 0
            goto L_0x07f2
        L_0x0816:
            r2 = move-exception
            java.lang.String r5 = "Req"
            java.lang.String r6 = "buildV4Dot216"
            com.loc.di.a(r2, r5, r6)
            java.lang.String r2 = "00:00:00:00:00:00"
            r0 = r19
            byte[] r2 = r0.a(r2)
            r5 = 0
            int r6 = r2.length
            java.lang.System.arraycopy(r2, r5, r3, r4, r6)
            int r2 = r2.length
            int r2 = r2 + r4
            r4 = 0
            r3[r2] = r4
            int r2 = r2 + 1
            java.lang.String r4 = "0"
            byte r4 = java.lang.Byte.parseByte(r4)
            r3[r2] = r4
            int r2 = r2 + 1
            goto L_0x06ad
        L_0x0842:
            byte r4 = (byte) r12
            r3[r2] = r4
            int r6 = r2 + 1
            int r2 = com.loc.dr.d()
            r4 = 17
            if (r2 < r4) goto L_0x08d5
            r2 = 1
            r9 = r2
        L_0x0851:
            r4 = 0
            if (r9 == 0) goto L_0x085c
            long r4 = com.loc.dr.c()
            r14 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r14
        L_0x085c:
            r2 = 0
            r8 = r2
        L_0x085e:
            if (r8 >= r12) goto L_0x08e6
            java.lang.Object r2 = r11.get(r8)
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2
            java.lang.String r7 = r2.BSSID
            r0 = r19
            byte[] r7 = r0.a(r7)
            r13 = 0
            int r14 = r7.length
            java.lang.System.arraycopy(r7, r13, r3, r6, r14)
            int r7 = r7.length
            int r6 = r6 + r7
            java.lang.String r7 = r2.SSID     // Catch:{ Exception -> 0x08d9 }
            java.lang.String r13 = "GBK"
            byte[] r7 = r7.getBytes(r13)     // Catch:{ Exception -> 0x08d9 }
            int r13 = r7.length     // Catch:{ Exception -> 0x08d9 }
            byte r13 = (byte) r13     // Catch:{ Exception -> 0x08d9 }
            r3[r6] = r13     // Catch:{ Exception -> 0x08d9 }
            int r6 = r6 + 1
            r13 = 0
            int r14 = r7.length     // Catch:{ Exception -> 0x08d9 }
            java.lang.System.arraycopy(r7, r13, r3, r6, r14)     // Catch:{ Exception -> 0x08d9 }
            int r7 = r7.length     // Catch:{ Exception -> 0x08d9 }
            int r6 = r6 + r7
        L_0x088b:
            int r7 = r2.level
            r13 = 127(0x7f, float:1.78E-43)
            if (r7 <= r13) goto L_0x08e0
            r7 = 0
        L_0x0892:
            java.lang.String r7 = java.lang.String.valueOf(r7)
            byte r7 = java.lang.Byte.parseByte(r7)
            r3[r6] = r7
            int r7 = r6 + 1
            if (r9 == 0) goto L_0x08b0
            long r14 = r2.timestamp
            r16 = 1000000(0xf4240, double:4.940656E-318)
            long r14 = r14 / r16
            r16 = 1
            long r14 = r14 + r16
            long r14 = r4 - r14
            int r6 = (int) r14
            if (r6 >= 0) goto L_0x08b1
        L_0x08b0:
            r6 = 0
        L_0x08b1:
            r13 = 65535(0xffff, float:9.1834E-41)
            if (r6 <= r13) goto L_0x08b9
            r6 = 65535(0xffff, float:9.1834E-41)
        L_0x08b9:
            byte[] r6 = com.loc.dr.a(r6, r10)
            r13 = 0
            int r14 = r6.length
            java.lang.System.arraycopy(r6, r13, r3, r7, r14)
            int r6 = r6.length
            int r6 = r6 + r7
            int r2 = r2.frequency
            byte[] r2 = com.loc.dr.a(r2, r10)
            r7 = 0
            int r13 = r2.length
            java.lang.System.arraycopy(r2, r7, r3, r6, r13)
            int r2 = r2.length
            int r6 = r6 + r2
            int r2 = r8 + 1
            r8 = r2
            goto L_0x085e
        L_0x08d5:
            r2 = 0
            r9 = r2
            goto L_0x0851
        L_0x08d9:
            r7 = move-exception
            r7 = 0
            r3[r6] = r7
            int r6 = r6 + 1
            goto L_0x088b
        L_0x08e0:
            r13 = -128(0xffffffffffffff80, float:NaN)
            if (r7 >= r13) goto L_0x0892
            r7 = 0
            goto L_0x0892
        L_0x08e6:
            r0 = r19
            java.lang.String r2 = r0.G
            int r2 = java.lang.Integer.parseInt(r2)
            byte[] r2 = com.loc.dr.a(r2, r10)
            r4 = 0
            int r5 = r2.length
            java.lang.System.arraycopy(r2, r4, r3, r6, r5)
            int r2 = r2.length
            int r2 = r2 + r6
            goto L_0x06c2
        L_0x08fb:
            int r5 = r4.length     // Catch:{ Throwable -> 0x090a }
            byte r5 = (byte) r5     // Catch:{ Throwable -> 0x090a }
            r3[r2] = r5     // Catch:{ Throwable -> 0x090a }
            int r2 = r2 + 1
            r5 = 0
            int r6 = r4.length     // Catch:{ Throwable -> 0x090a }
            java.lang.System.arraycopy(r4, r5, r3, r2, r6)     // Catch:{ Throwable -> 0x090a }
            int r4 = r4.length     // Catch:{ Throwable -> 0x090a }
            int r2 = r2 + r4
            goto L_0x06df
        L_0x090a:
            r4 = move-exception
            r4 = 0
            r3[r2] = r4
            int r2 = r2 + 1
            goto L_0x06df
        L_0x0912:
            r4 = move-exception
            int r2 = r2 + 2
            goto L_0x0716
        L_0x0917:
            r4 = move-exception
            int r2 = r2 + 2
            goto L_0x072d
        L_0x091c:
            r4 = move-exception
            int r2 = r2 + 2
            goto L_0x073a
        L_0x0921:
            r4 = move-exception
            goto L_0x0716
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.dg.a():byte[]");
    }
}
