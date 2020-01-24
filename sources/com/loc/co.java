package com.loc;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.amap.api.location.AMapLocationClientOption.GeoLanguage;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import com.github.mikephil.charting.utils.Utils;
import com.tencent.connect.common.Constants;
import coms.mediatek.wearableProfiles.WearableClientProfile;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"NewApi"})
/* compiled from: Aps */
public final class co {
    static int D = -1;
    public static boolean H = true;
    private static boolean M = false;
    private static int O = -1;
    int A = 12;
    cr B = null;
    boolean C = false;
    cq E = null;
    String F = null;
    cv G = null;
    IntentFilter I = null;
    LocationManager J = null;
    private int K = 0;
    private String L = null;
    private String N = null;
    private boolean P = true;
    Context a = null;
    ConnectivityManager b = null;
    cw c = null;
    cu d = null;
    cy e = null;
    cp f = null;
    df g = null;
    ArrayList<ScanResult> h = new ArrayList<>();
    a i = null;
    AMapLocationClientOption j = new AMapLocationClientOption();
    AMapLocationServer k = null;
    long l = 0;
    dg m = null;
    boolean n = false;
    dd o = null;
    StringBuilder p = new StringBuilder();
    boolean q = true;
    boolean r = true;
    GeoLanguage s = GeoLanguage.DEFAULT;
    boolean t = true;
    boolean u = false;
    WifiInfo v = null;
    boolean w = true;
    StringBuilder x = null;
    boolean y = false;
    public boolean z = false;

    /* compiled from: Aps */
    class a extends BroadcastReceiver {
        a() {
        }

        public final void onReceive(Context context, Intent intent) {
            if (context != null && intent != null) {
                try {
                    String action = intent.getAction();
                    if (TextUtils.isEmpty(action)) {
                        return;
                    }
                    if (action.equals("android.net.wifi.SCAN_RESULTS")) {
                        if (co.this.c != null) {
                            co.this.c.e();
                        }
                    } else if (action.equals("android.net.wifi.WIFI_STATE_CHANGED") && co.this.c != null) {
                        co.this.c.f();
                    }
                } catch (Throwable th) {
                    di.a(th, "Aps", "onReceive");
                }
            }
        }
    }

    private static AMapLocationServer a(int i2, String str) {
        AMapLocationServer aMapLocationServer = new AMapLocationServer("");
        aMapLocationServer.setErrorCode(i2);
        aMapLocationServer.setLocationDetail(str);
        if (i2 == 15) {
            Cdo.a((String) null, 2151);
        }
        return aMapLocationServer;
    }

    private AMapLocationServer a(AMapLocationServer aMapLocationServer, br brVar) {
        if (brVar != null) {
            try {
                if (!(brVar.a == null || brVar.a.length == 0)) {
                    df dfVar = new df();
                    String str = new String(brVar.a, "UTF-8");
                    if (str.contains("\"status\":\"0\"")) {
                        AMapLocationServer a2 = dfVar.a(str, this.a, brVar);
                        a2.h(this.x.toString());
                        return a2;
                    } else if (!str.contains("</body></html>")) {
                        return null;
                    } else {
                        aMapLocationServer.setErrorCode(5);
                        if (this.c == null || !this.c.a(this.b)) {
                            this.p.append("请求可能被劫持了#0502");
                            Cdo.a((String) null, 2052);
                        } else {
                            this.p.append("您连接的是一个需要登录的网络，请确认已经登入网络#0501");
                            Cdo.a((String) null, 2051);
                        }
                        aMapLocationServer.setLocationDetail(this.p.toString());
                        return aMapLocationServer;
                    }
                }
            } catch (Throwable th) {
                aMapLocationServer.setErrorCode(4);
                di.a(th, "Aps", "checkResponseEntity");
                this.p.append("check response exception ex is" + th.getMessage() + "#0403");
                aMapLocationServer.setLocationDetail(this.p.toString());
                return aMapLocationServer;
            }
        }
        aMapLocationServer.setErrorCode(4);
        this.p.append("网络异常,请求异常#0403");
        aMapLocationServer.h(this.x.toString());
        aMapLocationServer.setLocationDetail(this.p.toString());
        if (brVar == null) {
            return aMapLocationServer;
        }
        Cdo.a(brVar.d, 2041);
        return aMapLocationServer;
    }

    @SuppressLint({"NewApi"})
    private AMapLocationServer a(boolean z2, boolean z3) {
        AMapLocationServer aMapLocationServer;
        AMapLocationServer aMapLocationServer2 = new AMapLocationServer("");
        try {
            if (this.m == null) {
                this.m = new dg();
            }
            if (this.j == null) {
                this.j = new AMapLocationClientOption();
            }
            this.m.a(this.a, this.j.isNeedAddress(), this.j.isOffset(), this.d, this.c, this.b, this.G != null ? this.G.c() : null, this.F);
            byte[] a2 = this.m.a();
            this.l = dr.c();
            try {
                di.c(this.a);
                de a3 = this.o.a(this.a, a2, di.a(), z3);
                db.a(this.a).a(a3);
                br a4 = this.o.a(a3);
                db.a(this.a).a();
                String str = "";
                if (a4 != null) {
                    db.a(this.a).b();
                    aMapLocationServer2.a((long) this.o.a());
                    if (!TextUtils.isEmpty(a4.c)) {
                        this.p.append("#csid:" + a4.c);
                    }
                    str = a4.d;
                    aMapLocationServer2.h(this.x.toString());
                }
                if (!z2) {
                    AMapLocationServer a5 = a(aMapLocationServer2, a4);
                    if (a5 != null) {
                        return a5;
                    }
                    byte[] a6 = cx.a(a4.a);
                    if (a6 == null) {
                        aMapLocationServer2.setErrorCode(5);
                        this.p.append("解密数据失败#0503");
                        aMapLocationServer2.setLocationDetail(this.p.toString());
                        Cdo.a(str, 2053);
                        return aMapLocationServer2;
                    }
                    AMapLocationServer a7 = this.g.a(aMapLocationServer2, a6);
                    if (!dr.a(a7)) {
                        this.L = a7.b();
                        if (!TextUtils.isEmpty(this.L)) {
                            Cdo.a(str, 2062);
                        } else {
                            Cdo.a(str, 2061);
                        }
                        a7.setErrorCode(6);
                        this.p.append("location faile retype:" + a7.d() + " rdesc:" + (TextUtils.isEmpty(this.L) ? "" : this.L) + "#0601");
                        a7.h(this.x.toString());
                        a7.setLocationDetail(this.p.toString());
                        return a7;
                    }
                    if (a7.getErrorCode() == 0 && a7.getLocationType() == 0) {
                        if ("-5".equals(a7.d()) || "1".equals(a7.d()) || "2".equals(a7.d()) || Constants.VIA_REPORT_TYPE_MAKE_FRIEND.equals(a7.d()) || "24".equals(a7.d()) || "-1".equals(a7.d())) {
                            a7.setLocationType(5);
                        } else {
                            a7.setLocationType(6);
                        }
                    }
                    a7.setOffset(this.r);
                    a7.a(this.q);
                    a7.f(String.valueOf(this.s));
                    aMapLocationServer = a7;
                } else {
                    aMapLocationServer = aMapLocationServer2;
                }
                aMapLocationServer.e("new");
                aMapLocationServer.setLocationDetail(this.p.toString());
                this.F = aMapLocationServer.a();
                return aMapLocationServer;
            } catch (Throwable th) {
                db.a(this.a).d();
                di.a(th, "Aps", "getApsLoc req");
                Cdo.a("/mobile/binary", th);
                if (!dr.d(this.a)) {
                    this.p.append("网络异常，未连接到网络，请连接网络#0401");
                } else if (!(th instanceof k)) {
                    this.p.append("网络异常,请求异常#0403");
                } else if (((k) th).a().contains("网络异常状态码")) {
                    this.p.append("网络异常，状态码错误#0404").append(((k) th).e());
                } else if (((k) th).e() == 23 || Math.abs((dr.c() - this.l) - this.j.getHttpTimeOut()) < 500) {
                    this.p.append("网络异常，连接超时#0402");
                } else {
                    this.p.append("网络异常,请求异常#0403");
                }
                AMapLocationServer a8 = a(4, this.p.toString());
                a8.h(this.x.toString());
                return a8;
            }
        } catch (Throwable th2) {
            this.p.append("get parames error:" + th2.getMessage() + "#0301");
            Cdo.a((String) null, 2031);
            AMapLocationServer a9 = a(3, this.p.toString());
            a9.h(this.x.toString());
            return a9;
        }
    }

    private StringBuilder a(StringBuilder sb) {
        if (sb == null) {
            sb = new StringBuilder(700);
        } else {
            sb.delete(0, sb.length());
        }
        sb.append(this.d.l());
        sb.append(this.c.j());
        return sb;
    }

    public static void b(Context context) {
        try {
            if (O == -1 || dh.g(context)) {
                O = 1;
                dh.a(context);
            }
        } catch (Throwable th) {
            di.a(th, "Aps", "initAuth");
        }
    }

    private void b(AMapLocationServer aMapLocationServer) {
        if (aMapLocationServer != null) {
            this.k = aMapLocationServer;
        }
    }

    private void l() {
        int i2;
        boolean z2 = true;
        if (this.o != null) {
            try {
                if (this.j == null) {
                    this.j = new AMapLocationClientOption();
                }
                if (this.j.getGeoLanguage() != null) {
                    switch (this.j.getGeoLanguage()) {
                        case DEFAULT:
                            i2 = 0;
                            break;
                        case ZH:
                            i2 = 1;
                            break;
                        case EN:
                            i2 = 2;
                            break;
                        default:
                            i2 = 0;
                            break;
                    }
                } else {
                    i2 = 0;
                }
                dd ddVar = this.o;
                long httpTimeOut = this.j.getHttpTimeOut();
                if (!this.j.getLocationProtocol().equals(AMapLocationProtocol.HTTPS)) {
                    z2 = false;
                }
                ddVar.a(httpTimeOut, z2, i2);
            } catch (Throwable th) {
            }
        }
    }

    private void m() {
        try {
            if (this.i == null) {
                this.i = new a();
            }
            if (this.I == null) {
                this.I = new IntentFilter();
                this.I.addAction("android.net.wifi.WIFI_STATE_CHANGED");
                this.I.addAction("android.net.wifi.SCAN_RESULTS");
            }
            this.a.registerReceiver(this.i, this.I);
        } catch (Throwable th) {
            di.a(th, "Aps", "initBroadcastListener");
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String n() {
        /*
            r10 = this;
            r3 = 1
            r9 = 2121(0x849, float:2.972E-42)
            r8 = 12
            r2 = 0
            r7 = 0
            java.lang.String r4 = ""
            java.lang.String r5 = "network"
            com.loc.cu r0 = r10.d
            int r1 = r0.f()
            com.loc.cu r0 = r10.d
            com.loc.ct r6 = r0.c()
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r10.h
            if (r0 == 0) goto L_0x0025
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r10.h
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x005d
        L_0x0025:
            r0 = r3
        L_0x0026:
            if (r6 != 0) goto L_0x0188
            if (r0 == 0) goto L_0x0188
            android.net.ConnectivityManager r0 = r10.b
            if (r0 != 0) goto L_0x003b
            android.content.Context r0 = r10.a
            java.lang.String r1 = "connectivity"
            java.lang.Object r0 = com.loc.dr.a(r0, r1)
            android.net.ConnectivityManager r0 = (android.net.ConnectivityManager) r0
            r10.b = r0
        L_0x003b:
            android.content.Context r0 = r10.a
            boolean r0 = com.loc.dr.a(r0)
            if (r0 == 0) goto L_0x005f
            com.loc.cw r0 = r10.c
            boolean r0 = r0.g()
            if (r0 != 0) goto L_0x005f
            r0 = 18
            r10.A = r0
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "飞行模式下关闭了WIFI开关，请关闭飞行模式或者打开WIFI开关#1801"
            r0.append(r1)
            r0 = 2132(0x854, float:2.988E-42)
            com.loc.Cdo.a(r7, r0)
        L_0x005c:
            return r4
        L_0x005d:
            r0 = r2
            goto L_0x0026
        L_0x005f:
            int r0 = com.loc.dr.d()
            r1 = 28
            if (r0 < r1) goto L_0x009d
            android.location.LocationManager r0 = r10.J
            if (r0 != 0) goto L_0x007c
            android.content.Context r0 = r10.a
            android.content.Context r0 = r0.getApplicationContext()
            java.lang.String r1 = "location"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.location.LocationManager r0 = (android.location.LocationManager) r0
            r10.J = r0
        L_0x007c:
            android.location.LocationManager r0 = r10.J
            java.lang.String r1 = "isLocationEnabled"
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.Object r0 = com.loc.dm.a(r0, r1, r3)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 != 0) goto L_0x009d
            r10.A = r8
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "定位服务没有开启，请在设置中打开定位服务开关#1206"
            r0.append(r1)
            com.loc.Cdo.a(r7, r9)
            goto L_0x005c
        L_0x009d:
            android.content.Context r0 = r10.a
            boolean r0 = com.loc.dr.f(r0)
            if (r0 != 0) goto L_0x00b3
            r10.A = r8
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "定位权限被禁用,请授予应用定位权限#1201"
            r0.append(r1)
            com.loc.Cdo.a(r7, r9)
            goto L_0x005c
        L_0x00b3:
            int r0 = com.loc.dr.d()
            r1 = 24
            if (r0 < r1) goto L_0x00e1
            int r0 = com.loc.dr.d()
            r1 = 28
            if (r0 >= r1) goto L_0x00e1
            android.content.Context r0 = r10.a
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "location_mode"
            int r0 = android.provider.Settings.Secure.getInt(r0, r1, r2)
            if (r0 != 0) goto L_0x00e1
            r10.A = r8
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "定位服务没有开启，请在设置中打开定位服务开关#1206"
            r0.append(r1)
            com.loc.Cdo.a(r7, r9)
            goto L_0x005c
        L_0x00e1:
            com.loc.cu r0 = r10.d
            java.lang.String r0 = r0.j()
            com.loc.cw r1 = r10.c
            java.lang.String r1 = r1.b()
            com.loc.cw r2 = r10.c
            android.net.ConnectivityManager r3 = r10.b
            boolean r2 = r2.a(r3)
            if (r2 == 0) goto L_0x0108
            if (r1 == 0) goto L_0x0108
            r10.A = r8
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "获取基站与获取WIFI的权限都被禁用，请在安全软件中打开应用的定位权限#1202"
            r0.append(r1)
            com.loc.Cdo.a(r7, r9)
            goto L_0x005c
        L_0x0108:
            if (r0 == 0) goto L_0x012a
            r10.A = r8
            com.loc.cw r0 = r10.c
            boolean r0 = r0.g()
            if (r0 != 0) goto L_0x0121
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "WIFI开关关闭，并且获取基站权限被禁用，请在安全软件中打开应用的定位权限或者打开WIFI开关#1204"
            r0.append(r1)
        L_0x011c:
            com.loc.Cdo.a(r7, r9)
            goto L_0x005c
        L_0x0121:
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "获取的WIFI列表为空，并且获取基站权限被禁用，请在安全软件中打开应用的定位权限#1205"
            r0.append(r1)
            goto L_0x011c
        L_0x012a:
            com.loc.cw r0 = r10.c
            boolean r0 = r0.g()
            if (r0 != 0) goto L_0x014d
            com.loc.cu r0 = r10.d
            boolean r0 = r0.m()
            if (r0 != 0) goto L_0x014d
            r0 = 19
            r10.A = r0
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "没有检查到SIM卡，并且WIFI开关关闭，请打开WIFI开关或者插入SIM卡#1901"
            r0.append(r1)
            r0 = 2133(0x855, float:2.989E-42)
            com.loc.Cdo.a(r7, r0)
            goto L_0x005c
        L_0x014d:
            android.content.Context r0 = r10.a
            boolean r0 = com.loc.dr.g(r0)
            if (r0 != 0) goto L_0x0164
            r10.A = r8
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "后台定位服务没有开启，请在设置中打开后台定位服务开关#1207"
            r0.append(r1)
            com.loc.Cdo.a(r7, r9)
            goto L_0x005c
        L_0x0164:
            com.loc.cw r0 = r10.c
            boolean r0 = r0.g()
            if (r0 != 0) goto L_0x017f
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "获取到的基站为空，并且关闭了WIFI开关，请您打开WIFI开关再发起定位#1301"
            r0.append(r1)
        L_0x0174:
            r0 = 13
            r10.A = r0
            r0 = 2131(0x853, float:2.986E-42)
            com.loc.Cdo.a(r7, r0)
            goto L_0x005c
        L_0x017f:
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "获取到的基站和WIFI信息均为空，请移动到有WIFI的区域，若确定当前区域有WIFI，请检查是否授予APP定位权限#1302"
            r0.append(r1)
            goto L_0x0174
        L_0x0188:
            com.loc.cw r0 = r10.c
            android.net.wifi.WifiInfo r0 = r0.h()
            r10.v = r0
            com.loc.cw r0 = r10.c
            android.net.wifi.WifiInfo r0 = r10.v
            boolean r0 = com.loc.cw.a(r0)
            r10.w = r0
            switch(r1) {
                case 0: goto L_0x02ad;
                case 1: goto L_0x01e7;
                case 2: goto L_0x0244;
                default: goto L_0x019d;
            }
        L_0x019d:
            r0 = 11
            r10.A = r0
            r0 = 2111(0x83f, float:2.958E-42)
            com.loc.Cdo.a(r7, r0)
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "get cgi failure#1101"
            r0.append(r1)
        L_0x01ae:
            r0 = r4
        L_0x01af:
            java.lang.String r1 = "#"
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x01e4
            boolean r2 = r0.startsWith(r1)
            if (r2 != 0) goto L_0x01cf
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
        L_0x01cf:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = com.loc.dr.i()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
        L_0x01e4:
            r4 = r0
            goto L_0x005c
        L_0x01e7:
            if (r6 == 0) goto L_0x01ae
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            int r0 = r6.a
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r2 = "#"
            r0.append(r2)
            int r0 = r6.b
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r2 = "#"
            r0.append(r2)
            int r0 = r6.c
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r2 = "#"
            r0.append(r2)
            int r0 = r6.d
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r2 = "#"
            r0.append(r2)
            java.lang.StringBuilder r0 = r1.append(r5)
            java.lang.String r2 = "#"
            r0.append(r2)
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r10.h
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0234
            boolean r0 = r10.w
            if (r0 == 0) goto L_0x0240
        L_0x0234:
            java.lang.String r0 = "cgiwifi"
        L_0x0237:
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            goto L_0x01af
        L_0x0240:
            java.lang.String r0 = "cgi"
            goto L_0x0237
        L_0x0244:
            if (r6 == 0) goto L_0x01ae
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            int r0 = r6.a
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r2 = "#"
            r0.append(r2)
            int r0 = r6.b
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r2 = "#"
            r0.append(r2)
            int r0 = r6.g
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r2 = "#"
            r0.append(r2)
            int r0 = r6.h
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r2 = "#"
            r0.append(r2)
            int r0 = r6.i
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r2 = "#"
            r0.append(r2)
            java.lang.StringBuilder r0 = r1.append(r5)
            java.lang.String r2 = "#"
            r0.append(r2)
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r10.h
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x029d
            boolean r0 = r10.w
            if (r0 == 0) goto L_0x02a9
        L_0x029d:
            java.lang.String r0 = "cgiwifi"
        L_0x02a0:
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            goto L_0x01af
        L_0x02a9:
            java.lang.String r0 = "cgi"
            goto L_0x02a0
        L_0x02ad:
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r10.h
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x02b9
            boolean r0 = r10.w
            if (r0 == 0) goto L_0x0373
        L_0x02b9:
            r1 = r3
        L_0x02ba:
            boolean r0 = r10.w
            if (r0 == 0) goto L_0x02d8
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r10.h
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x02d8
            r0 = 2
            r10.A = r0
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "当前基站为伪基站，并且WIFI权限被禁用，请在安全软件中打开应用的定位权限#0201"
            r0.append(r1)
            r0 = 2021(0x7e5, float:2.832E-42)
            com.loc.Cdo.a(r7, r0)
            goto L_0x005c
        L_0x02d8:
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r10.h
            int r0 = r0.size()
            if (r0 != r3) goto L_0x031f
            r0 = 2
            r10.A = r0
            boolean r0 = r10.w
            if (r0 != 0) goto L_0x02f6
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "当前基站为伪基站，并且搜到的WIFI数量不足，请移动到WIFI比较丰富的区域#0202"
            r0.append(r1)
            r0 = 2022(0x7e6, float:2.833E-42)
            com.loc.Cdo.a(r7, r0)
            goto L_0x005c
        L_0x02f6:
            java.util.ArrayList<android.net.wifi.ScanResult> r0 = r10.h
            java.lang.Object r0 = r0.get(r2)
            android.net.wifi.ScanResult r0 = (android.net.wifi.ScanResult) r0
            java.lang.String r0 = r0.BSSID
            com.loc.cw r6 = r10.c
            android.net.wifi.WifiInfo r6 = r6.h()
            java.lang.String r6 = r6.getBSSID()
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x031f
            java.lang.StringBuilder r0 = r10.p
            java.lang.String r1 = "当前基站为伪基站，并且搜到的WIFI数量不足，请移动到WIFI比较丰富的区域#0202"
            r0.append(r1)
            r0 = 2021(0x7e5, float:2.832E-42)
            com.loc.Cdo.a(r7, r0)
            goto L_0x005c
        L_0x031f:
            java.util.Locale r0 = java.util.Locale.US
            java.lang.String r4 = "#%s#"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r3[r2] = r5
            java.lang.String r0 = java.lang.String.format(r0, r4, r3)
            if (r1 == 0) goto L_0x0344
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = "wifi"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            goto L_0x01af
        L_0x0344:
            java.lang.String r1 = "network"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x01af
            java.lang.String r0 = ""
            r1 = 2
            r10.A = r1
            com.loc.cw r1 = r10.c
            boolean r1 = r1.g()
            if (r1 != 0) goto L_0x036a
            java.lang.StringBuilder r1 = r10.p
            java.lang.String r2 = "当前基站为伪基站,并且关闭了WIFI开关，请在设置中打开WIFI开关#0203"
            r1.append(r2)
        L_0x0363:
            r1 = 2022(0x7e6, float:2.833E-42)
            com.loc.Cdo.a(r7, r1)
            goto L_0x01af
        L_0x036a:
            java.lang.StringBuilder r1 = r10.p
            java.lang.String r2 = "当前基站为伪基站,并且没有搜索到WIFI，请移动到WIFI比较丰富的区域#0204"
            r1.append(r2)
            goto L_0x0363
        L_0x0373:
            r1 = r2
            goto L_0x02ba
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.co.n():java.lang.String");
    }

    private boolean o() {
        this.h = this.c.c();
        return this.h == null || this.h.size() <= 0;
    }

    public final AMapLocationServer a(double d2, double d3) {
        try {
            String a2 = this.o.a(this.a, d2, d3);
            if (a2.contains("\"status\":\"1\"")) {
                AMapLocationServer a3 = this.g.a(a2);
                a3.setLatitude(d2);
                a3.setLongitude(d3);
                return a3;
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public final AMapLocationServer a(AMapLocationServer aMapLocationServer, String... strArr) {
        this.E.a(this.t);
        if (strArr == null || strArr.length == 0) {
            return this.E.a(aMapLocationServer);
        }
        if (strArr[0].equals("shake")) {
            return this.E.a(aMapLocationServer);
        }
        if (!strArr[0].equals("fusion")) {
            return aMapLocationServer;
        }
        cq cqVar = this.E;
        return aMapLocationServer;
    }

    public final AMapLocationServer a(boolean z2) {
        if (this.a == null) {
            this.p.append("context is null#0101");
            Cdo.a((String) null, (int) WearableClientProfile.MSG_DESCRIPTOR_READ);
            return a(1, this.p.toString());
        } else if (this.c.i()) {
            return a(15, "networkLocation has been mocked!#1502");
        } else {
            a();
            if (TextUtils.isEmpty(this.N)) {
                return a(this.A, this.p.toString());
            }
            AMapLocationServer a2 = a(false, z2);
            if (!dr.a(a2)) {
                return a2;
            }
            this.e.a(this.x.toString());
            this.e.a(this.d.c());
            b(a2);
            return a2;
        }
    }

    public final void a() {
        this.o = dd.a(this.a);
        l();
        if (this.b == null) {
            this.b = (ConnectivityManager) dr.a(this.a, "connectivity");
        }
        if (this.m == null) {
            this.m = new dg();
        }
    }

    public final void a(Context context) {
        try {
            if (this.a == null) {
                this.E = new cq();
                this.a = context.getApplicationContext();
                dh.d(this.a);
                dr.b(this.a);
                if (this.c == null) {
                    this.c = new cw(this.a, (WifiManager) dr.a(this.a, "wifi"));
                }
                if (this.d == null) {
                    this.d = new cu(this.a);
                }
                if (this.e == null) {
                    this.e = new cy();
                }
                if (this.g == null) {
                    this.g = new df();
                }
                if (this.G == null) {
                    this.G = new cv(this.a);
                }
            }
        } catch (Throwable th) {
            di.a(th, "Aps", "initBase");
        }
    }

    public final void a(AMapLocationClientOption aMapLocationClientOption) {
        boolean z2;
        boolean z3;
        boolean z4;
        this.j = aMapLocationClientOption;
        if (this.j == null) {
            this.j = new AMapLocationClientOption();
        }
        if (this.c != null) {
            cw cwVar = this.c;
            this.j.isWifiActiveScan();
            boolean isWifiScan = this.j.isWifiScan();
            boolean isMockEnable = this.j.isMockEnable();
            AMapLocationClientOption aMapLocationClientOption2 = this.j;
            cwVar.a(isWifiScan, isMockEnable, AMapLocationClientOption.isOpenAlwaysScanWifi(), aMapLocationClientOption.getScanWifiInterval());
        }
        l();
        if (this.e != null) {
            this.e.a(this.j);
        }
        if (this.g != null) {
            this.g.a(this.j);
        }
        GeoLanguage geoLanguage = GeoLanguage.DEFAULT;
        try {
            geoLanguage = this.j.getGeoLanguage();
            z3 = this.j.isNeedAddress();
            try {
                z4 = this.j.isOffset();
                try {
                    z2 = this.j.isLocationCacheEnable();
                    try {
                        this.u = this.j.isOnceLocationLatest();
                        this.C = this.j.isSensorEnable();
                        if (!(z4 == this.r && z3 == this.q && z2 == this.t && geoLanguage == this.s)) {
                            if (this.e != null) {
                                this.e.a();
                            }
                            b((AMapLocationServer) null);
                            this.P = false;
                            if (this.E != null) {
                                this.E.a();
                            }
                        }
                    } catch (Throwable th) {
                    }
                } catch (Throwable th2) {
                    z2 = true;
                }
            } catch (Throwable th3) {
                z2 = true;
                z4 = true;
            }
        } catch (Throwable th4) {
            z2 = true;
            z3 = true;
            z4 = true;
        }
        this.r = z4;
        this.q = z3;
        this.t = z2;
        this.s = geoLanguage;
    }

    public final void a(AMapLocationServer aMapLocationServer) {
        if (dr.a(aMapLocationServer)) {
            this.e.a(this.N, this.x, aMapLocationServer, this.a, true);
        }
    }

    public final void b() {
        if (this.B == null) {
            this.B = new cr(this.a);
        }
        if (this.f == null) {
            this.f = new cp(this.a);
        }
        m();
        this.c.b(false);
        this.h = this.c.c();
        this.d.a(false, o());
        this.e.a(this.a);
        this.f.b();
        try {
            if (this.a.checkCallingOrSelfPermission(w.c("EYW5kcm9pZC5wZXJtaXNzaW9uLldSSVRFX1NFQ1VSRV9TRVRUSU5HUw==")) == 0) {
                this.n = true;
            }
        } catch (Throwable th) {
        }
        this.z = true;
    }

    public final void c() {
        if (this.p.length() > 0) {
            this.p.delete(0, this.p.length());
        }
    }

    public final AMapLocationServer d() throws Throwable {
        boolean z2;
        c();
        if (this.a == null) {
            this.p.append("context is null#0101");
            return a(1, this.p.toString());
        }
        this.K++;
        if (this.K == 1 && this.c != null) {
            this.c.a(this.n);
        }
        long j2 = this.l;
        if (!this.P) {
            this.P = true;
            z2 = false;
        } else {
            if (dr.c() - j2 < 800) {
                long j3 = 0;
                if (dr.a(this.k)) {
                    j3 = dr.b() - this.k.getTime();
                }
                if (j3 <= 10000) {
                    z2 = true;
                }
            }
            z2 = false;
        }
        if (!z2 || !dr.a(this.k)) {
            if (this.B != null) {
                if (this.C) {
                    this.B.a();
                } else {
                    this.B.b();
                }
            }
            try {
                this.c.b(this.j.isOnceLocationLatest() || !this.j.isOnceLocation());
                this.h = this.c.c();
            } catch (Throwable th) {
                di.a(th, "Aps", "getLocation getScanResultsParam");
            }
            try {
                this.d.a(false, o());
            } catch (Throwable th2) {
                di.a(th2, "Aps", "getLocation getCgiListParam");
            }
            this.N = n();
            if (TextUtils.isEmpty(this.N)) {
                this.k = this.f.e();
                if (this.k == null) {
                    return a(this.A, this.p.toString());
                }
                this.k.setLocationDetail(this.p.toString());
                this.k.setTrustedLevel(4);
                return this.k;
            }
            this.x = a(this.x);
            if (this.c.i()) {
                AMapLocationServer a2 = a(15, "networkLocation has been mocked!#1502");
                a2.setMock(true);
                a2.setTrustedLevel(4);
                return a2;
            }
            boolean z3 = this.l == 0 ? true : dr.c() - this.l > 20000;
            AMapLocationServer a3 = this.e.a(this.d, z3, this.k, this.c, this.x, this.N, this.a);
            if (dr.a(a3)) {
                a3.setTrustedLevel(2);
                b(a3);
            } else {
                this.k = a(false, true);
                if (dr.a(this.k)) {
                    this.k.e("new");
                    this.e.a(this.x.toString());
                    this.e.a(this.d.c());
                    b(this.k);
                    if (this.G != null) {
                        this.G.c(this.d, this.h, this.k);
                    }
                }
            }
            try {
                if (!(this.c == null || this.k == null)) {
                    cw cwVar = this.c;
                    long a4 = cw.a();
                    if (a4 <= 15) {
                        this.k.setTrustedLevel(1);
                    } else if (a4 <= 120) {
                        this.k.setTrustedLevel(2);
                    } else if (a4 <= 600) {
                        this.k.setTrustedLevel(3);
                    } else {
                        this.k.setTrustedLevel(4);
                    }
                }
            } catch (Throwable th3) {
            }
            if (this.G != null) {
                this.G.b(this.d, this.h, this.k);
            }
            this.e.a(this.N, this.x, this.k, this.a, true);
            if (!dr.a(this.k) && this.G != null) {
                cv cvVar = this.G;
                Context context = this.a;
                this.k = cvVar.a(this.d, (List<ScanResult>) this.h, this.k);
            }
            this.x.delete(0, this.x.length());
            if (!this.C || this.B == null) {
                this.k.setAltitude(Utils.DOUBLE_EPSILON);
                this.k.setBearing(0.0f);
                this.k.setSpeed(0.0f);
            } else {
                this.k.setAltitude(this.B.c());
                this.k.setBearing(this.B.d());
                this.k.setSpeed((float) this.B.e());
            }
            return this.k;
        }
        if (this.t && dh.b(this.k.getTime())) {
            this.k.setLocationType(2);
        }
        return this.k;
    }

    public final void e() {
        try {
            a(this.a);
            a(this.j);
            Context context = this.a;
            i();
            a(a(true, true));
        } catch (Throwable th) {
            di.a(th, "Aps", "doFusionLocation");
        }
    }

    @SuppressLint({"NewApi"})
    public final void f() {
        this.F = null;
        this.y = false;
        this.z = false;
        if (this.G != null) {
            this.G.d();
        }
        if (this.f != null) {
            this.f.a();
        }
        if (this.e != null) {
            this.e.b(this.a);
        }
        if (this.E != null) {
            this.E.a();
        }
        if (this.g != null) {
            this.g = null;
        }
        dr.h();
        try {
            if (!(this.a == null || this.i == null)) {
                this.a.unregisterReceiver(this.i);
            }
        } catch (Throwable th) {
            di.a(th, "Aps", "destroy");
        } finally {
            this.i = null;
        }
        if (this.d != null) {
            this.d.h();
        }
        if (this.c != null) {
            this.c.k();
        }
        if (this.h != null) {
            this.h.clear();
        }
        if (this.B != null) {
            this.B.f();
        }
        db.e();
        this.k = null;
        this.a = null;
        this.x = null;
        this.J = null;
    }

    public final void g() {
        try {
            if (this.f != null) {
                this.f.c();
            }
        } catch (Throwable th) {
            di.a(th, "Aps", "bindAMapService");
        }
    }

    public final void h() {
        try {
            if (this.f != null) {
                this.f.d();
            }
        } catch (Throwable th) {
            di.a(th, "Aps", "bindOtherService");
        }
    }

    public final void i() {
        try {
            if (!this.y) {
                if (this.N != null) {
                    this.N = null;
                }
                if (this.x != null) {
                    this.x.delete(0, this.x.length());
                }
                if (this.u) {
                    m();
                }
                this.c.b(this.u);
                this.h = this.c.c();
                this.d.a(true, o());
                this.N = n();
                if (!TextUtils.isEmpty(this.N)) {
                    this.x = a(this.x);
                }
                this.y = true;
            }
        } catch (Throwable th) {
            di.a(th, "Aps", "initFirstLocateParam");
        }
    }

    public final AMapLocationServer j() {
        if (this.c.i()) {
            return a(15, "networkLocation has been mocked!#1502");
        }
        if (TextUtils.isEmpty(this.N)) {
            return a(this.A, this.p.toString());
        }
        AMapLocationServer a2 = this.e.a(this.a, this.N, this.x, true);
        if (!dr.a(a2)) {
            return a2;
        }
        b(a2);
        return a2;
    }

    public final void k() {
        if (this.G != null) {
            this.G.b();
        }
    }
}
