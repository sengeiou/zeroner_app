package com.loc;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.text.TextUtils;
import com.alibaba.android.arouter.utils.Consts;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import java.io.File;
import java.util.List;
import org.json.JSONObject;

/* compiled from: CoManager */
public final class cv {
    boolean a = false;
    boolean b = false;
    private Context c;
    private Object d = null;
    private int e = -1;
    private boolean f = false;

    public cv(Context context) {
        this.c = context;
    }

    private static String a(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sv", "4.7.0");
            jSONObject.put("als", "S128DF1572465B890OE3F7A13167KLEI");
            jSONObject.put("pn", l.c(context));
            jSONObject.put("ak", l.f(context));
            jSONObject.put("ud", p.h(context));
            jSONObject.put("au", p.b(context));
            return jSONObject.toString();
        } catch (Throwable th) {
            return null;
        }
    }

    private static String a(cu cuVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            if (cuVar == null) {
                return null;
            }
            ct c2 = cuVar.c();
            ct d2 = cuVar.d();
            if (c2 != null) {
                jSONObject.put("mainCgi", c2.a());
            }
            if (d2 != null) {
                jSONObject.put("mainCgi2", d2.a());
            }
            return jSONObject.toString();
        } catch (Throwable th) {
            di.a(th, "APSCoManager", "buildCgiJsonStr");
            return null;
        }
    }

    private String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String str2 = this.c.getApplicationContext().getFilesDir().getAbsolutePath() + File.separator + "loc_cozip";
        String substring = str.substring(str.lastIndexOf(File.separator) + 1, str.lastIndexOf(Consts.DOT));
        boolean c2 = dr.c(str2, substring);
        boolean b2 = dq.b(this.c, "pref", "ok4", false);
        if (!c2 || b2) {
            dq.a(this.c, "pref", "ok4", false);
            a(str, str2, substring);
        }
        String a2 = w.a(this.c);
        if (TextUtils.isEmpty(a2)) {
            return null;
        }
        stringBuffer.append(str2).append(File.separator).append(substring).append(File.separator).append("libs").append(File.separator).append(a2).append(File.separator).append("libapssdk.so");
        String stringBuffer2 = stringBuffer.toString();
        File file = new File(stringBuffer2);
        if (!file.exists()) {
            a(str, str2, substring);
        }
        if (file.exists()) {
            return stringBuffer2;
        }
        return null;
    }

    private void a(cu cuVar, List<ScanResult> list, AMapLocationServer aMapLocationServer, int i) {
        try {
            if (e() && dr.a(aMapLocationServer)) {
                f();
                if (this.d != null) {
                    String a2 = a(cuVar);
                    ScanResult[] a3 = a(list);
                    if (i == 1) {
                        dm.a(this.d, "trainingFps", new Class[]{String.class, ScanResult[].class}, new Object[]{a2, a3});
                    } else if (i == 2) {
                        dm.a(this.d, "correctOfflineLocation", new Class[]{String.class, ScanResult[].class, Double.TYPE, Double.TYPE}, new Object[]{a2, a3, Double.valueOf(aMapLocationServer.getLatitude()), Double.valueOf(aMapLocationServer.getLongitude())});
                    } else {
                        return;
                    }
                    this.b = true;
                }
            }
        } catch (Throwable th) {
            di.a(th, "APSCoManager", "action-" + (1 == i ? "training" : "correct"));
        }
    }

    private static void a(String str, String str2, String str3) {
        if (!str2.endsWith(File.separator)) {
            str2 = str2 + File.separator;
        }
        String str4 = str2 + str3;
        dr.e(str2);
        dr.b(str, str4);
    }

    public static boolean a() {
        try {
            Class.forName("com.amap.opensdk.co.CoManager");
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private static ScanResult[] a(List<ScanResult> list) {
        if (list != null) {
            try {
                if (list.size() > 0) {
                    ScanResult[] scanResultArr = new ScanResult[list.size()];
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 >= list.size()) {
                            return scanResultArr;
                        }
                        scanResultArr[i2] = (ScanResult) list.get(i2);
                        i = i2 + 1;
                    }
                }
            } catch (Throwable th) {
                di.a(th, "APSCoManager", "buildScanResults");
            }
        }
        return null;
    }

    private void b(String str) {
        if (this.d != null && !TextUtils.isEmpty(str)) {
            dm.a(this.d, "loadSo", str);
        }
    }

    private boolean e() {
        if (!dh.x()) {
            d();
            return false;
        } else if (dh.y()) {
            return true;
        } else {
            if (!this.b) {
                return false;
            }
            try {
                if (this.d != null) {
                    dm.a(this.d, "destroyOfflineLoc", new Object[0]);
                }
            } catch (Throwable th) {
                di.a(th, "APSCoManager", "destroyOffline");
            }
            this.b = false;
            return false;
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void f() {
        /*
            r9 = this;
            r6 = 0
            r2 = 0
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.String r1 = "CoManager ==> init "
            r0[r2] = r1
            com.loc.dr.a()
            java.lang.Object r0 = r9.d     // Catch:{ Throwable -> 0x00dd }
            if (r0 != 0) goto L_0x00af
            android.content.Context r0 = r9.c     // Catch:{ Throwable -> 0x00dd }
            java.lang.String r1 = "pref"
            java.lang.String r2 = "ok5"
            r3 = 0
            int r0 = com.loc.dq.b(r0, r1, r2, r3)     // Catch:{ Throwable -> 0x00dd }
            android.content.Context r1 = r9.c     // Catch:{ Throwable -> 0x00dd }
            java.lang.String r2 = "pref"
            java.lang.String r3 = "ok7"
            r4 = 0
            long r2 = com.loc.dq.b(r1, r2, r3, r4)     // Catch:{ Throwable -> 0x00dd }
            if (r0 == 0) goto L_0x0041
            int r1 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r1 == 0) goto L_0x0041
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00dd }
            long r2 = r4 - r2
            r4 = 259200000(0xf731400, double:1.280618154E-315)
            int r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r1 >= 0) goto L_0x0041
        L_0x0040:
            return
        L_0x0041:
            android.content.Context r1 = r9.c     // Catch:{ Throwable -> 0x00dd }
            java.lang.String r2 = "pref"
            java.lang.String r3 = "ok5"
            int r0 = r0 + 1
            com.loc.dq.a(r1, r2, r3, r0)     // Catch:{ Throwable -> 0x00dd }
            android.content.Context r0 = r9.c     // Catch:{ Throwable -> 0x00dd }
            java.lang.String r1 = "pref"
            java.lang.String r2 = "ok7"
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00dd }
            com.loc.dq.a(r0, r1, r2, r4)     // Catch:{ Throwable -> 0x00dd }
            boolean r0 = a()     // Catch:{ Throwable -> 0x00dd }
            if (r0 == 0) goto L_0x00f4
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x00dd }
            r1 = 0
            java.lang.String r2 = "CoManager ==> initForJar "
            r0[r1] = r2     // Catch:{ Throwable -> 0x00dd }
            com.loc.dr.a()     // Catch:{ Throwable -> 0x00dd }
            java.lang.String r0 = "com.amap.opensdk.co.CoManager"
            r1 = 1
            java.lang.Class[] r1 = new java.lang.Class[r1]     // Catch:{ Throwable -> 0x00e9 }
            r2 = 0
            java.lang.Class<android.content.Context> r3 = android.content.Context.class
            r1[r2] = r3     // Catch:{ Throwable -> 0x00e9 }
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x00e9 }
            r3 = 0
            android.content.Context r4 = r9.c     // Catch:{ Throwable -> 0x00e9 }
            r2[r3] = r4     // Catch:{ Throwable -> 0x00e9 }
            java.lang.Object r0 = com.loc.dm.a(r0, (java.lang.Class<?>[]) r1, r2)     // Catch:{ Throwable -> 0x00e9 }
            r9.d = r0     // Catch:{ Throwable -> 0x00e9 }
            r9.g()     // Catch:{ Throwable -> 0x00e9 }
            java.lang.Object r0 = r9.d     // Catch:{ Throwable -> 0x00e9 }
            java.lang.String r1 = "loadLocalSo"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x00e9 }
            com.loc.dm.a(r0, r1, r2)     // Catch:{ Throwable -> 0x00e9 }
        L_0x0096:
            android.content.Context r0 = r9.c     // Catch:{ Throwable -> 0x00dd }
            java.lang.String r1 = "pref"
            java.lang.String r2 = "ok5"
            r3 = 0
            com.loc.dq.a(r0, r1, r2, r3)     // Catch:{ Throwable -> 0x00dd }
            android.content.Context r0 = r9.c     // Catch:{ Throwable -> 0x00dd }
            java.lang.String r1 = "pref"
            java.lang.String r2 = "ok7"
            r4 = 0
            com.loc.dq.a(r0, r1, r2, r4)     // Catch:{ Throwable -> 0x00dd }
        L_0x00af:
            int r0 = com.loc.dh.A()     // Catch:{ Throwable -> 0x00d1 }
            int r1 = r9.e     // Catch:{ Throwable -> 0x00d1 }
            if (r1 == r0) goto L_0x0040
            r9.e = r0     // Catch:{ Throwable -> 0x00d1 }
            java.lang.Object r1 = r9.d     // Catch:{ Throwable -> 0x00d1 }
            if (r1 == 0) goto L_0x0040
            java.lang.Object r1 = r9.d     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r2 = "setCloudConfigVersion"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00d1 }
            r4 = 0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x00d1 }
            r3[r4] = r0     // Catch:{ Throwable -> 0x00d1 }
            com.loc.dm.a(r1, r2, r3)     // Catch:{ Throwable -> 0x00d1 }
            goto L_0x0040
        L_0x00d1:
            r0 = move-exception
            java.lang.String r1 = "APSCoManager"
            java.lang.String r2 = "setCloudVersion"
            com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x00dd }
            goto L_0x0040
        L_0x00dd:
            r0 = move-exception
            java.lang.String r1 = "APSCoManager"
            java.lang.String r2 = "init"
            com.loc.di.a(r0, r1, r2)
            goto L_0x0040
        L_0x00e9:
            r0 = move-exception
            java.lang.String r1 = "APSCoManager"
            java.lang.String r2 = "initForJar"
            com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x00dd }
            goto L_0x0096
        L_0x00f4:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x00dd }
            r1 = 28
            if (r0 <= r1) goto L_0x016b
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x00dd }
            r1 = 0
            java.lang.String r2 = "CoManager ==> initForDex for Android Q"
            r0[r1] = r2     // Catch:{ Throwable -> 0x00dd }
            com.loc.dr.a()     // Catch:{ Throwable -> 0x00dd }
            boolean r0 = r9.f     // Catch:{ Throwable -> 0x0157 }
            if (r0 != 0) goto L_0x0152
            boolean r0 = com.loc.dh.B()     // Catch:{ Throwable -> 0x0157 }
            if (r0 == 0) goto L_0x0152
            java.lang.String r0 = "co"
            java.lang.String r1 = "1.0.0"
            com.loc.v r1 = com.loc.di.a(r0, r1)     // Catch:{ Throwable -> 0x0157 }
            android.content.Context r0 = r9.c     // Catch:{ Throwable -> 0x0157 }
            boolean r0 = com.loc.Cdo.a(r0, r1)     // Catch:{ Throwable -> 0x0157 }
            r9.f = r0     // Catch:{ Throwable -> 0x0157 }
            boolean r0 = r9.f     // Catch:{ Throwable -> 0x0157 }
            if (r0 == 0) goto L_0x0152
            android.content.Context r0 = r9.c     // Catch:{ Throwable -> 0x01e0 }
            java.lang.String r0 = com.loc.ba.a(r0, r1)     // Catch:{ Throwable -> 0x01e0 }
            java.lang.String r6 = r9.a(r0)     // Catch:{ Throwable -> 0x01e0 }
            android.content.Context r0 = r9.c     // Catch:{ Throwable -> 0x01e3 }
            java.lang.String r2 = "com.amap.opensdk.co.CoManager"
            r3 = 0
            r4 = 1
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x01e3 }
            r5 = 0
            java.lang.Class<android.content.Context> r7 = android.content.Context.class
            r4[r5] = r7     // Catch:{ Throwable -> 0x01e3 }
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x01e3 }
            r7 = 0
            android.content.Context r8 = r9.c     // Catch:{ Throwable -> 0x01e3 }
            r5[r7] = r8     // Catch:{ Throwable -> 0x01e3 }
            java.lang.Object r0 = com.loc.ba.a(r0, r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x01e3 }
            r9.d = r0     // Catch:{ Throwable -> 0x01e3 }
        L_0x014c:
            r9.g()     // Catch:{ Throwable -> 0x01e0 }
            r9.b(r6)     // Catch:{ Throwable -> 0x01e0 }
        L_0x0152:
            r0 = 1
            r9.f = r0     // Catch:{ Throwable -> 0x00dd }
            goto L_0x0096
        L_0x0157:
            r0 = move-exception
            java.lang.String r1 = "APSCoManager"
            java.lang.String r2 = "initForDexQ"
            com.loc.di.a(r0, r1, r2)     // Catch:{ all -> 0x0166 }
            r0 = 1
            r9.f = r0     // Catch:{ Throwable -> 0x00dd }
            goto L_0x0096
        L_0x0166:
            r0 = move-exception
            r1 = 1
            r9.f = r1     // Catch:{ Throwable -> 0x00dd }
            throw r0     // Catch:{ Throwable -> 0x00dd }
        L_0x016b:
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x00dd }
            r1 = 0
            java.lang.String r2 = "CoManager ==> initForDex "
            r0[r1] = r2     // Catch:{ Throwable -> 0x00dd }
            com.loc.dr.a()     // Catch:{ Throwable -> 0x00dd }
            boolean r0 = r9.f     // Catch:{ Throwable -> 0x01c8 }
            if (r0 != 0) goto L_0x01c3
            boolean r0 = com.loc.dh.B()     // Catch:{ Throwable -> 0x01c8 }
            if (r0 == 0) goto L_0x01c3
            java.lang.String r0 = "co"
            java.lang.String r1 = "1.0.0"
            com.loc.v r1 = com.loc.di.a(r0, r1)     // Catch:{ Throwable -> 0x01c8 }
            android.content.Context r0 = r9.c     // Catch:{ Throwable -> 0x01c8 }
            boolean r0 = com.loc.Cdo.a(r0, r1)     // Catch:{ Throwable -> 0x01c8 }
            r9.f = r0     // Catch:{ Throwable -> 0x01c8 }
            boolean r0 = r9.f     // Catch:{ Throwable -> 0x01c8 }
            if (r0 == 0) goto L_0x01c3
            android.content.Context r0 = r9.c     // Catch:{ Throwable -> 0x01de }
            java.lang.String r2 = "com.amap.opensdk.co.CoManager"
            r3 = 0
            r4 = 1
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x01de }
            r5 = 0
            java.lang.Class<android.content.Context> r6 = android.content.Context.class
            r4[r5] = r6     // Catch:{ Throwable -> 0x01de }
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x01de }
            r6 = 0
            android.content.Context r7 = r9.c     // Catch:{ Throwable -> 0x01de }
            r5[r6] = r7     // Catch:{ Throwable -> 0x01de }
            java.lang.Object r0 = com.loc.ba.a(r0, r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x01de }
            r9.d = r0     // Catch:{ Throwable -> 0x01de }
        L_0x01b3:
            r9.g()     // Catch:{ Throwable -> 0x01dc }
            android.content.Context r0 = r9.c     // Catch:{ Throwable -> 0x01dc }
            java.lang.String r0 = com.loc.ba.a(r0, r1)     // Catch:{ Throwable -> 0x01dc }
            java.lang.String r0 = r9.a(r0)     // Catch:{ Throwable -> 0x01dc }
            r9.b(r0)     // Catch:{ Throwable -> 0x01dc }
        L_0x01c3:
            r0 = 1
            r9.f = r0     // Catch:{ Throwable -> 0x00dd }
            goto L_0x0096
        L_0x01c8:
            r0 = move-exception
            java.lang.String r1 = "APSCoManager"
            java.lang.String r2 = "initForDex"
            com.loc.di.a(r0, r1, r2)     // Catch:{ all -> 0x01d7 }
            r0 = 1
            r9.f = r0     // Catch:{ Throwable -> 0x00dd }
            goto L_0x0096
        L_0x01d7:
            r0 = move-exception
            r1 = 1
            r9.f = r1     // Catch:{ Throwable -> 0x00dd }
            throw r0     // Catch:{ Throwable -> 0x00dd }
        L_0x01dc:
            r0 = move-exception
            goto L_0x01c3
        L_0x01de:
            r0 = move-exception
            goto L_0x01b3
        L_0x01e0:
            r0 = move-exception
            goto L_0x0152
        L_0x01e3:
            r0 = move-exception
            goto L_0x014c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cv.f():void");
    }

    private void g() {
        try {
            if (this.c != null) {
                String a2 = a(this.c);
                if (this.d != null) {
                    dm.a(this.d, "init", a2);
                }
            }
        } catch (Throwable th) {
            di.a(th, "APSCoManager", "setConfig");
        }
    }

    public final AMapLocationServer a(cu cuVar, List<ScanResult> list, AMapLocationServer aMapLocationServer) {
        try {
            if (!e()) {
                return aMapLocationServer;
            }
            if (aMapLocationServer != null && aMapLocationServer.getErrorCode() == 7) {
                return aMapLocationServer;
            }
            f();
            if (this.d == null) {
                return aMapLocationServer;
            }
            this.b = true;
            Object a2 = dm.a(this.d, "getOfflineLoc", new Class[]{String.class, ScanResult[].class, Boolean.TYPE}, new Object[]{a(cuVar), a(list), Boolean.valueOf(false)});
            if (a2 == null) {
                return aMapLocationServer;
            }
            JSONObject jSONObject = new JSONObject((String) a2);
            AMapLocationServer aMapLocationServer2 = new AMapLocationServer("lbs");
            aMapLocationServer2.b(jSONObject);
            if (dr.a(aMapLocationServer2)) {
                StringBuffer stringBuffer = new StringBuffer();
                if (aMapLocationServer2.e().equals("file")) {
                    stringBuffer.append("基站离线定位");
                } else if (aMapLocationServer2.e().equals("wifioff")) {
                    stringBuffer.append("WIFI离线定位");
                } else {
                    stringBuffer.append("离线定位，").append(aMapLocationServer2.e());
                }
                if (aMapLocationServer != null) {
                    stringBuffer.append("，在线定位失败原因:" + aMapLocationServer.getErrorInfo());
                }
                aMapLocationServer2.setTrustedLevel(2);
                aMapLocationServer2.setLocationDetail(stringBuffer.toString());
                aMapLocationServer2.setLocationType(8);
            }
            return aMapLocationServer2;
        } catch (Throwable th) {
            di.a(th, "APSCoManager", "getOffLoc");
            return aMapLocationServer;
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b() {
        /*
            r3 = this;
            boolean r0 = com.loc.dh.x()     // Catch:{ Throwable -> 0x0027 }
            if (r0 != 0) goto L_0x000a
            r3.d()     // Catch:{ Throwable -> 0x0027 }
        L_0x0009:
            return
        L_0x000a:
            boolean r0 = com.loc.dh.z()     // Catch:{ Throwable -> 0x0027 }
            if (r0 != 0) goto L_0x003d
            boolean r0 = r3.a     // Catch:{ Throwable -> 0x0027 }
            if (r0 == 0) goto L_0x0009
            java.lang.Object r0 = r3.d     // Catch:{ Throwable -> 0x0032 }
            if (r0 == 0) goto L_0x0023
            java.lang.Object r0 = r3.d     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r1 = "destroyCollect"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0032 }
            com.loc.dm.a(r0, r1, r2)     // Catch:{ Throwable -> 0x0032 }
        L_0x0023:
            r0 = 0
            r3.a = r0     // Catch:{ Throwable -> 0x0027 }
            goto L_0x0009
        L_0x0027:
            r0 = move-exception
            java.lang.String r1 = "APSCoManager"
            java.lang.String r2 = "startCollection"
            com.loc.di.a(r0, r1, r2)
            goto L_0x0009
        L_0x0032:
            r0 = move-exception
            java.lang.String r1 = "APSCoManager"
            java.lang.String r2 = "destroyCollection"
            com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x0027 }
            goto L_0x0023
        L_0x003d:
            boolean r0 = r3.a     // Catch:{ Throwable -> 0x0027 }
            if (r0 != 0) goto L_0x0009
            r3.f()     // Catch:{ Throwable -> 0x0027 }
            java.lang.Object r0 = r3.d     // Catch:{ Throwable -> 0x0027 }
            if (r0 == 0) goto L_0x0009
            java.lang.Object r0 = r3.d     // Catch:{ Throwable -> 0x0027 }
            java.lang.String r1 = "startCollect"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0027 }
            com.loc.dm.a(r0, r1, r2)     // Catch:{ Throwable -> 0x0027 }
            r0 = 1
            r3.a = r0     // Catch:{ Throwable -> 0x0027 }
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cv.b():void");
    }

    public final void b(cu cuVar, List<ScanResult> list, AMapLocationServer aMapLocationServer) {
        try {
            a(cuVar, list, aMapLocationServer, 1);
        } catch (Throwable th) {
            di.a(th, "APSCoManager", "trainingFps");
        }
    }

    public final String c() {
        String str;
        try {
            if (!dh.x()) {
                d();
                return null;
            }
            if (this.d != null) {
                str = (String) dm.a(this.d, "getCollectVersion", new Object[0]);
                return str;
            }
            str = null;
            return str;
        } catch (Throwable th) {
            di.a(th, "APSCoManager", "getCollectionVersion");
        }
    }

    public final void c(cu cuVar, List<ScanResult> list, AMapLocationServer aMapLocationServer) {
        try {
            a(cuVar, list, aMapLocationServer, 2);
        } catch (Throwable th) {
            di.a(th, "APSCoManager", "correctOffLoc");
        }
    }

    public final void d() {
        try {
            if (this.d != null) {
                dm.a(this.d, "destroy", new Object[0]);
            }
            this.a = false;
            this.b = false;
            this.d = null;
        } catch (Throwable th) {
            di.a(th, "APSCoManager", "destroy");
        }
    }
}
