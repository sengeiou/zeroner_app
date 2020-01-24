package com.loc;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.fence.GeoFenceManagerBase;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressLint({"NewApi"})
/* compiled from: GeoFenceManager */
public class a implements GeoFenceManagerBase {
    Cdo a = null;
    Context b = null;
    PendingIntent c = null;
    String d = null;
    GeoFenceListener e = null;
    volatile int f = 1;
    ArrayList<GeoFence> g = new ArrayList<>();
    c h = null;
    Object i = new Object();
    Object j = new Object();
    C0000a k = null;
    b l = null;
    volatile boolean m = false;
    volatile boolean n = false;
    volatile boolean o = false;
    b p = null;
    c q = null;
    AMapLocationClient r = null;
    volatile AMapLocation s = null;
    long t = 0;
    AMapLocationClientOption u = null;
    int v = 0;
    AMapLocationListener w = new AMapLocationListener() {
        /* JADX WARNING: Removed duplicated region for block: B:12:0x0034 A[Catch:{ Throwable -> 0x0043 }] */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0071 A[Catch:{ Throwable -> 0x0043 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onLocationChanged(com.amap.api.location.AMapLocation r10) {
            /*
                r9 = this;
                r0 = 8
                r1 = 1
                r2 = 0
                com.loc.a r3 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                boolean r3 = r3.y     // Catch:{ Throwable -> 0x0043 }
                if (r3 == 0) goto L_0x000b
            L_0x000a:
                return
            L_0x000b:
                com.loc.a r3 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                boolean r3 = r3.o     // Catch:{ Throwable -> 0x0043 }
                if (r3 == 0) goto L_0x000a
                com.loc.a r3 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                r3.s = r10     // Catch:{ Throwable -> 0x0043 }
                if (r10 == 0) goto L_0x006f
                int r0 = r10.getErrorCode()     // Catch:{ Throwable -> 0x0043 }
                int r3 = r10.getErrorCode()     // Catch:{ Throwable -> 0x0043 }
                if (r3 != 0) goto L_0x0045
                com.loc.a r2 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                long r4 = com.loc.dr.c()     // Catch:{ Throwable -> 0x0043 }
                r2.t = r4     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r2 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                r3 = 5
                r4 = 0
                r6 = 0
                r2.a(r3, r4, r6)     // Catch:{ Throwable -> 0x0043 }
            L_0x0032:
                if (r1 == 0) goto L_0x0071
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                r1 = 0
                r0.v = r1     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                r1 = 6
                r2 = 0
                r4 = 0
                r0.a(r1, r2, r4)     // Catch:{ Throwable -> 0x0043 }
                goto L_0x000a
            L_0x0043:
                r0 = move-exception
                goto L_0x000a
            L_0x0045:
                com.loc.a r1 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r1 = "定位失败"
                int r3 = r10.getErrorCode()     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r4 = r10.getErrorInfo()     // Catch:{ Throwable -> 0x0043 }
                r5 = 1
                java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ Throwable -> 0x0043 }
                r6 = 0
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r8 = "locationDetail:"
                r7.<init>(r8)     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r8 = r10.getLocationDetail()     // Catch:{ Throwable -> 0x0043 }
                java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0043 }
                r5[r6] = r7     // Catch:{ Throwable -> 0x0043 }
                com.loc.a.a(r1, r3, r4, r5)     // Catch:{ Throwable -> 0x0043 }
            L_0x006f:
                r1 = r2
                goto L_0x0032
            L_0x0071:
                android.os.Bundle r1 = new android.os.Bundle     // Catch:{ Throwable -> 0x0043 }
                r1.<init>()     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r2 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                boolean r2 = r2.m     // Catch:{ Throwable -> 0x0043 }
                if (r2 != 0) goto L_0x0093
                com.loc.a r2 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                r3 = 7
                r2.a(r3)     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r2 = "interval"
                r4 = 2000(0x7d0, double:9.88E-321)
                r1.putLong(r2, r4)     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r2 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                r3 = 8
                r4 = 2000(0x7d0, double:9.88E-321)
                r2.a(r3, r1, r4)     // Catch:{ Throwable -> 0x0043 }
            L_0x0093:
                com.loc.a r2 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                int r3 = r2.v     // Catch:{ Throwable -> 0x0043 }
                int r3 = r3 + 1
                r2.v = r3     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r2 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                int r2 = r2.v     // Catch:{ Throwable -> 0x0043 }
                r3 = 3
                if (r2 < r3) goto L_0x000a
                java.lang.String r2 = "location_errorcode"
                r1.putInt(r2, r0)     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                r2 = 1002(0x3ea, float:1.404E-42)
                r0.a(r2, r1)     // Catch:{ Throwable -> 0x0043 }
                goto L_0x000a
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.a.AnonymousClass1.onLocationChanged(com.amap.api.location.AMapLocation):void");
        }
    };
    final int x = 3;
    volatile boolean y = false;
    private Object z = new Object();

    /* renamed from: com.loc.a$a reason: collision with other inner class name */
    /* compiled from: GeoFenceManager */
    class C0000a extends Handler {
        public C0000a(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 0:
                        a.this.b(message.getData());
                        return;
                    case 1:
                        a.this.c(message.getData());
                        return;
                    case 2:
                        a.this.e(message.getData());
                        return;
                    case 3:
                        a.this.d(message.getData());
                        return;
                    case 4:
                        a.this.f(message.getData());
                        return;
                    case 5:
                        a.this.c();
                        return;
                    case 6:
                        a.this.a(a.this.s);
                        return;
                    case 7:
                        a.this.b();
                        return;
                    case 8:
                        a.this.j(message.getData());
                        return;
                    case 9:
                        a.this.a(message.getData());
                        return;
                    case 10:
                        a.this.a();
                        return;
                    case 11:
                        a.this.h(message.getData());
                        return;
                    case 12:
                        a.this.g(message.getData());
                        return;
                    case 13:
                        a.this.d();
                        return;
                    default:
                        return;
                }
            } catch (Throwable th) {
            }
        }
    }

    /* compiled from: GeoFenceManager */
    static class b extends HandlerThread {
        public b(String str) {
            super(str);
        }

        public final void run() {
            try {
                super.run();
            } catch (Throwable th) {
            }
        }
    }

    /* compiled from: GeoFenceManager */
    class c extends Handler {
        public c() {
        }

        public c(Looper looper) {
            super(looper);
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void handleMessage(android.os.Message r3) {
            /*
                r2 = this;
                android.os.Bundle r0 = r3.getData()     // Catch:{ Throwable -> 0x0010 }
                int r1 = r3.what     // Catch:{ Throwable -> 0x0010 }
                switch(r1) {
                    case 1000: goto L_0x000a;
                    case 1001: goto L_0x0012;
                    case 1002: goto L_0x0026;
                    default: goto L_0x0009;
                }     // Catch:{ Throwable -> 0x0010 }
            L_0x0009:
                return
            L_0x000a:
                com.loc.a r1 = com.loc.a.this     // Catch:{ Throwable -> 0x0010 }
                r1.i(r0)     // Catch:{ Throwable -> 0x0010 }
                goto L_0x0009
            L_0x0010:
                r0 = move-exception
                goto L_0x0009
            L_0x0012:
                java.lang.String r1 = "geoFence"
                android.os.Parcelable r0 = r0.getParcelable(r1)     // Catch:{ Throwable -> 0x0021 }
                com.amap.api.fence.GeoFence r0 = (com.amap.api.fence.GeoFence) r0     // Catch:{ Throwable -> 0x0021 }
                com.loc.a r1 = com.loc.a.this     // Catch:{ Throwable -> 0x0021 }
                r1.a(r0)     // Catch:{ Throwable -> 0x0021 }
                goto L_0x0009
            L_0x0021:
                r0 = move-exception
                com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ Throwable -> 0x0010 }
                goto L_0x0009
            L_0x0026:
                java.lang.String r1 = "location_errorcode"
                int r0 = r0.getInt(r1)     // Catch:{ Throwable -> 0x0033 }
                com.loc.a r1 = com.loc.a.this     // Catch:{ Throwable -> 0x0033 }
                r1.b(r0)     // Catch:{ Throwable -> 0x0033 }
                goto L_0x0009
            L_0x0033:
                r0 = move-exception
                com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ Throwable -> 0x0010 }
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.a.c.handleMessage(android.os.Message):void");
        }
    }

    public a(Context context) {
        try {
            this.b = context.getApplicationContext();
            e();
        } catch (Throwable th) {
            di.a(th, "GeoFenceManger", "<init>");
        }
    }

    static float a(DPoint dPoint, List<DPoint> list) {
        float f2 = Float.MAX_VALUE;
        if (dPoint == null || list == null || list.isEmpty()) {
            return Float.MAX_VALUE;
        }
        Iterator it = list.iterator();
        while (true) {
            float f3 = f2;
            if (!it.hasNext()) {
                return f3;
            }
            f2 = Math.min(f3, dr.a(dPoint, (DPoint) it.next()));
        }
    }

    private int a(List<GeoFence> list) {
        try {
            if (this.g == null) {
                this.g = new ArrayList<>();
            }
            for (GeoFence b2 : list) {
                b(b2);
            }
            return 0;
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "addGeoFenceList");
            a("添加围栏失败", 8, th.getMessage(), new String[0]);
            return 8;
        }
    }

    private static Bundle a(GeoFence geoFence, String str, String str2, int i2, int i3) {
        Bundle bundle = new Bundle();
        String str3 = GeoFence.BUNDLE_KEY_FENCEID;
        if (str == null) {
            str = "";
        }
        bundle.putString(str3, str);
        bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str2);
        bundle.putInt("event", i2);
        bundle.putInt(GeoFence.BUNDLE_KEY_LOCERRORCODE, i3);
        bundle.putParcelable(GeoFence.BUNDLE_KEY_FENCE, geoFence);
        return bundle;
    }

    private GeoFence a(Bundle bundle, boolean z2) {
        ArrayList arrayList;
        float f2 = 1000.0f;
        GeoFence geoFence = new GeoFence();
        ArrayList arrayList2 = new ArrayList();
        DPoint dPoint = new DPoint();
        if (z2) {
            geoFence.setType(1);
            arrayList = bundle.getParcelableArrayList("pointList");
            if (arrayList != null) {
                dPoint = b((List<DPoint>) arrayList);
            }
            geoFence.setMaxDis2Center(b(dPoint, (List<DPoint>) arrayList));
            geoFence.setMinDis2Center(a(dPoint, (List<DPoint>) arrayList));
        } else {
            geoFence.setType(0);
            dPoint = (DPoint) bundle.getParcelable("centerPoint");
            if (dPoint != null) {
                arrayList2.add(dPoint);
            }
            float f3 = bundle.getFloat("fenceRadius", 1000.0f);
            if (f3 > 0.0f) {
                f2 = f3;
            }
            geoFence.setRadius(f2);
            geoFence.setMinDis2Center(f2);
            geoFence.setMaxDis2Center(f2);
            arrayList = arrayList2;
        }
        geoFence.setActivatesAction(this.f);
        geoFence.setCustomId(bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID));
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(arrayList);
        geoFence.setPointList(arrayList3);
        geoFence.setCenter(dPoint);
        geoFence.setPendingIntentAction(this.d);
        geoFence.setExpiration(-1);
        geoFence.setPendingIntent(this.c);
        geoFence.setFenceId(c.a());
        if (this.a != null) {
            this.a.a(this.b, 2);
        }
        return geoFence;
    }

    static void a(String str, int i2, String str2, String... strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("===========================================\n");
        stringBuffer.append("              " + str + "                ").append("\n");
        stringBuffer.append("-------------------------------------------\n");
        stringBuffer.append("errorCode:" + i2).append("\n");
        stringBuffer.append("错误信息:" + str2).append("\n");
        if (strArr != null && strArr.length > 0) {
            for (String append : strArr) {
                stringBuffer.append(append).append("\n");
            }
        }
        stringBuffer.append("===========================================\n");
        Log.i("fenceErrLog", stringBuffer.toString());
    }

    private static boolean a(GeoFence geoFence, int i2) {
        boolean z2 = false;
        if ((i2 & 1) == 1) {
            try {
                if (geoFence.getStatus() == 1) {
                    z2 = true;
                }
            } catch (Throwable th) {
                di.a(th, "Utils", "remindStatus");
                return false;
            }
        }
        if ((i2 & 2) == 2 && geoFence.getStatus() == 2) {
            z2 = true;
        }
        if ((i2 & 4) == 4 && geoFence.getStatus() == 3) {
            return true;
        }
        return z2;
    }

    private static boolean a(AMapLocation aMapLocation, GeoFence geoFence) {
        boolean z2;
        boolean z3;
        try {
            if (dr.a(aMapLocation) && geoFence != null && geoFence.getPointList() != null && !geoFence.getPointList().isEmpty()) {
                switch (geoFence.getType()) {
                    case 0:
                    case 2:
                        DPoint center = geoFence.getCenter();
                        if (dr.a(new double[]{center.getLatitude(), center.getLongitude(), aMapLocation.getLatitude(), aMapLocation.getLongitude()}) <= geoFence.getRadius()) {
                            return true;
                        }
                        break;
                    case 1:
                    case 3:
                        z3 = false;
                        for (List list : geoFence.getPointList()) {
                            try {
                                z3 = list.size() < 3 ? false : di.a(new DPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()), list) ? true : z3;
                            } catch (Throwable th) {
                                th = th;
                                z2 = z3;
                                di.a(th, "Utils", "isInGeoFence");
                                return z2;
                            }
                        }
                        break;
                }
            }
            z3 = false;
            return z3;
        } catch (Throwable th2) {
            th = th2;
            z2 = false;
            di.a(th, "Utils", "isInGeoFence");
            return z2;
        }
    }

    static float b(DPoint dPoint, List<DPoint> list) {
        float f2 = Float.MIN_VALUE;
        if (dPoint == null || list == null || list.isEmpty()) {
            return Float.MIN_VALUE;
        }
        Iterator it = list.iterator();
        while (true) {
            float f3 = f2;
            if (!it.hasNext()) {
                return f3;
            }
            f2 = Math.max(f3, dr.a(dPoint, (DPoint) it.next()));
        }
    }

    private int b(GeoFence geoFence) {
        try {
            if (this.g == null) {
                this.g = new ArrayList<>();
            }
            if (this.g.contains(geoFence)) {
                return 17;
            }
            this.g.add(geoFence);
            return 0;
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "addGeoFence2List");
            a("添加围栏失败", 8, th.getMessage(), new String[0]);
            return 8;
        }
    }

    private static DPoint b(List<DPoint> list) {
        DPoint dPoint;
        double d2 = Utils.DOUBLE_EPSILON;
        DPoint dPoint2 = new DPoint();
        if (list != null) {
            try {
                double d3 = 0.0d;
                for (DPoint dPoint3 : list) {
                    d3 += dPoint3.getLatitude();
                    d2 += dPoint3.getLongitude();
                }
                dPoint = new DPoint(dr.c(d3 / ((double) list.size())), dr.c(d2 / ((double) list.size())));
            } catch (Throwable th) {
                di.a(th, "GeoFenceUtil", "getPolygonCenter");
                return dPoint2;
            }
        } else {
            dPoint = dPoint2;
        }
        return dPoint;
    }

    private void b(int i2, Bundle bundle) {
        int i3;
        int i4;
        String a2;
        Bundle bundle2 = new Bundle();
        int i5 = 0;
        try {
            ArrayList arrayList = new ArrayList();
            if (bundle == null || bundle.isEmpty()) {
                i4 = 1;
            } else {
                ArrayList arrayList2 = new ArrayList();
                String string = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                String string2 = bundle.getString("keyWords");
                String string3 = bundle.getString("city");
                String string4 = bundle.getString("poiType");
                DPoint dPoint = (DPoint) bundle.getParcelable("centerPoint");
                int i6 = bundle.getInt("searchSize", 10);
                float f2 = bundle.getFloat("aroundRadius", 3000.0f);
                boolean z2 = true;
                if (!TextUtils.isEmpty(string2)) {
                    switch (i2) {
                        case 1:
                            if (TextUtils.isEmpty(string4)) {
                                z2 = false;
                                break;
                            }
                            break;
                        case 2:
                            if (dPoint != null) {
                                if (dPoint.getLatitude() > 90.0d || dPoint.getLatitude() < -90.0d || dPoint.getLongitude() > 180.0d || dPoint.getLongitude() < -180.0d) {
                                    a("添加围栏失败", 0, "经纬度错误，传入的纬度：" + dPoint.getLatitude() + "传入的经度:" + dPoint.getLongitude(), new String[0]);
                                    z2 = false;
                                    break;
                                }
                            } else {
                                z2 = false;
                                break;
                            }
                    }
                } else {
                    z2 = false;
                }
                if (z2) {
                    Bundle bundle3 = new Bundle();
                    bundle3.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
                    Bundle bundle4 = bundle3;
                    bundle4.putString("pendingIntentAction", this.d);
                    bundle3.putLong("expiration", -1);
                    Bundle bundle5 = bundle3;
                    bundle5.putInt("activatesAction", this.f);
                    switch (i2) {
                        case 1:
                            bundle3.putFloat("fenceRadius", 1000.0f);
                            a2 = this.p.a(this.b, "http://restapi.amap.com/v3/place/text?", string2, string4, string3, String.valueOf(i6));
                            break;
                        case 2:
                            double c2 = dr.c(dPoint.getLatitude());
                            double c3 = dr.c(dPoint.getLongitude());
                            int intValue = Float.valueOf(f2).intValue();
                            bundle3.putFloat("fenceRadius", 200.0f);
                            a2 = this.p.a(this.b, "http://restapi.amap.com/v3/place/around?", string2, string4, String.valueOf(i6), String.valueOf(c2), String.valueOf(c3), String.valueOf(intValue));
                            break;
                        case 3:
                            a2 = this.p.a(this.b, "http://restapi.amap.com/v3/config/district?", string2);
                            break;
                        default:
                            a2 = null;
                            break;
                    }
                    if (a2 != null) {
                        if (1 == i2) {
                            c cVar = this.q;
                            i4 = c.a(a2, arrayList2, bundle3);
                        } else {
                            i4 = 0;
                        }
                        if (2 == i2) {
                            c cVar2 = this.q;
                            i4 = c.b(a2, arrayList2, bundle3);
                        }
                        if (3 == i2) {
                            i4 = this.q.c(a2, arrayList2, bundle3);
                        }
                        if (i4 != 10000) {
                            switch (i4) {
                                case 1:
                                case 4:
                                case 5:
                                case 7:
                                case 16:
                                case 17:
                                    break;
                                case 10000:
                                    i4 = 0;
                                    break;
                                case 10001:
                                case 10002:
                                case 10007:
                                case 10008:
                                case 10009:
                                case 10012:
                                case 10013:
                                    i4 = 7;
                                    break;
                                case 10003:
                                case 10004:
                                case 10005:
                                case 10006:
                                case 10010:
                                case 10011:
                                case 10014:
                                case 10015:
                                case 10016:
                                case 10017:
                                    i4 = 4;
                                    break;
                                case 20000:
                                case 20001:
                                case 20002:
                                    i4 = 1;
                                    break;
                                case 20003:
                                    i4 = 8;
                                    break;
                                default:
                                    i4 = 8;
                                    break;
                            }
                            if (i4 != 0) {
                                a("添加围栏失败", i4, "searchErrCode is " + i4, new String[0]);
                            }
                        } else if (arrayList2.isEmpty()) {
                            i4 = 16;
                        } else {
                            i4 = a((List<GeoFence>) arrayList2);
                            if (i4 == 0) {
                                arrayList.addAll(arrayList2);
                            }
                        }
                    } else {
                        i4 = 4;
                    }
                } else {
                    i4 = 1;
                }
                try {
                    bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
                    bundle2.putParcelableArrayList("resultList", arrayList);
                } catch (Throwable th) {
                    i5 = i4;
                    th = th;
                    bundle2.putInt("errorCode", i5);
                    a(1000, bundle2);
                    throw th;
                }
            }
            bundle2.putInt("errorCode", i4);
            a(1000, bundle2);
            return;
        } catch (Throwable th2) {
            th = th2;
            bundle2.putInt("errorCode", i5);
            a(1000, bundle2);
            throw th;
        }
        try {
            di.a(th, "GeoFenceManager", "doAddGeoFenceNearby");
            bundle2.putInt("errorCode", 8);
            a(1000, bundle2);
        } catch (Throwable th3) {
            th = th3;
            i5 = i3;
            bundle2.putInt("errorCode", i5);
            a(1000, bundle2);
            throw th;
        }
    }

    private static boolean b(AMapLocation aMapLocation, GeoFence geoFence) {
        Throwable th;
        boolean z2 = true;
        boolean z3 = false;
        try {
            if (a(aMapLocation, geoFence)) {
                if (geoFence.getEnterTime() == -1) {
                    if (geoFence.getStatus() != 1) {
                        geoFence.setEnterTime(dr.c());
                        geoFence.setStatus(1);
                        return true;
                    }
                } else if (geoFence.getStatus() != 3 && dr.c() - geoFence.getEnterTime() > 600000) {
                    geoFence.setStatus(3);
                    return true;
                }
            } else if (geoFence.getStatus() != 2) {
                try {
                    geoFence.setStatus(2);
                    geoFence.setEnterTime(-1);
                    z3 = true;
                } catch (Throwable th2) {
                    th = th2;
                    di.a(th, "Utils", "isFenceStatusChanged");
                    return z2;
                }
            }
            return z3;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            z2 = false;
            th = th4;
        }
    }

    private void e() {
        if (!this.o) {
            this.o = true;
        }
        if (!this.n) {
            try {
                if (Looper.myLooper() == null) {
                    this.h = new c(this.b.getMainLooper());
                } else {
                    this.h = new c();
                }
            } catch (Throwable th) {
                di.a(th, "GeoFenceManger", "init 1");
            }
            try {
                this.l = new b("fenceActionThread");
                this.l.setPriority(5);
                this.l.start();
                this.k = new C0000a(this.l.getLooper());
            } catch (Throwable th2) {
                di.a(th2, "GeoFenceManger", "init 2");
            }
            try {
                this.p = new b(this.b);
                this.q = new c();
                this.u = new AMapLocationClientOption();
                this.r = new AMapLocationClient(this.b);
                this.u.setLocationCacheEnable(true);
                this.u.setNeedAddress(false);
                this.r.setLocationListener(this.w);
                if (this.a == null) {
                    this.a = new Cdo();
                }
            } catch (Throwable th3) {
                di.a(th3, "GeoFenceManger", "initBase");
            }
            this.n = true;
            try {
                if (this.d != null && this.c == null) {
                    createPendingIntent(this.d);
                }
            } catch (Throwable th4) {
                di.a(th4, "GeoFenceManger", "init 4");
            }
        }
    }

    private void f() {
        if (!this.y && this.k != null) {
            boolean z2 = false;
            if (this.s != null && dr.a(this.s) && dr.c() - this.t < 10000) {
                z2 = true;
            }
            if (z2) {
                a(6, null, 0);
                a(5, null, 0);
                return;
            }
            a(7);
            a(7, null, 0);
        }
    }

    private void g() {
        try {
            if (this.m) {
                a(8);
            }
            if (this.r != null) {
                this.r.stopLocation();
            }
            this.m = false;
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a() {
        /*
            r4 = this;
            r3 = 0
            boolean r0 = r4.n     // Catch:{ Throwable -> 0x008c }
            if (r0 != 0) goto L_0x0006
        L_0x0005:
            return
        L_0x0006:
            java.util.ArrayList<com.amap.api.fence.GeoFence> r0 = r4.g     // Catch:{ Throwable -> 0x008c }
            if (r0 == 0) goto L_0x0012
            java.util.ArrayList<com.amap.api.fence.GeoFence> r0 = r4.g     // Catch:{ Throwable -> 0x008c }
            r0.clear()     // Catch:{ Throwable -> 0x008c }
            r0 = 0
            r4.g = r0     // Catch:{ Throwable -> 0x008c }
        L_0x0012:
            boolean r0 = r4.o     // Catch:{ Throwable -> 0x008c }
            if (r0 != 0) goto L_0x0005
            java.lang.Object r1 = r4.i     // Catch:{ Throwable -> 0x0081 }
            monitor-enter(r1)     // Catch:{ Throwable -> 0x0081 }
            com.loc.a$a r0 = r4.k     // Catch:{ all -> 0x007e }
            if (r0 == 0) goto L_0x0023
            com.loc.a$a r0 = r4.k     // Catch:{ all -> 0x007e }
            r2 = 0
            r0.removeCallbacksAndMessages(r2)     // Catch:{ all -> 0x007e }
        L_0x0023:
            r0 = 0
            r4.k = r0     // Catch:{ all -> 0x007e }
            monitor-exit(r1)     // Catch:{ all -> 0x007e }
        L_0x0027:
            com.amap.api.location.AMapLocationClient r0 = r4.r     // Catch:{ Throwable -> 0x008c }
            if (r0 == 0) goto L_0x0035
            com.amap.api.location.AMapLocationClient r0 = r4.r     // Catch:{ Throwable -> 0x008c }
            r0.stopLocation()     // Catch:{ Throwable -> 0x008c }
            com.amap.api.location.AMapLocationClient r0 = r4.r     // Catch:{ Throwable -> 0x008c }
            r0.onDestroy()     // Catch:{ Throwable -> 0x008c }
        L_0x0035:
            r0 = 0
            r4.r = r0     // Catch:{ Throwable -> 0x008c }
            com.loc.a$b r0 = r4.l     // Catch:{ Throwable -> 0x008c }
            if (r0 == 0) goto L_0x0047
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x008c }
            r1 = 18
            if (r0 < r1) goto L_0x008e
            com.loc.a$b r0 = r4.l     // Catch:{ Throwable -> 0x008c }
            r0.quitSafely()     // Catch:{ Throwable -> 0x008c }
        L_0x0047:
            r0 = 0
            r4.l = r0     // Catch:{ Throwable -> 0x008c }
            r0 = 0
            r4.p = r0     // Catch:{ Throwable -> 0x008c }
            java.lang.Object r1 = r4.z     // Catch:{ Throwable -> 0x008c }
            monitor-enter(r1)     // Catch:{ Throwable -> 0x008c }
            android.app.PendingIntent r0 = r4.c     // Catch:{ all -> 0x0094 }
            if (r0 == 0) goto L_0x0059
            android.app.PendingIntent r0 = r4.c     // Catch:{ all -> 0x0094 }
            r0.cancel()     // Catch:{ all -> 0x0094 }
        L_0x0059:
            r0 = 0
            r4.c = r0     // Catch:{ all -> 0x0094 }
            monitor-exit(r1)     // Catch:{ all -> 0x0094 }
            java.lang.Object r1 = r4.j     // Catch:{ Throwable -> 0x009a }
            monitor-enter(r1)     // Catch:{ Throwable -> 0x009a }
            com.loc.a$c r0 = r4.h     // Catch:{ all -> 0x0097 }
            if (r0 == 0) goto L_0x006a
            com.loc.a$c r0 = r4.h     // Catch:{ all -> 0x0097 }
            r2 = 0
            r0.removeCallbacksAndMessages(r2)     // Catch:{ all -> 0x0097 }
        L_0x006a:
            r0 = 0
            r4.h = r0     // Catch:{ all -> 0x0097 }
            monitor-exit(r1)     // Catch:{ all -> 0x0097 }
        L_0x006e:
            com.loc.do r0 = r4.a     // Catch:{ Throwable -> 0x008c }
            if (r0 == 0) goto L_0x0079
            com.loc.do r0 = r4.a     // Catch:{ Throwable -> 0x008c }
            android.content.Context r1 = r4.b     // Catch:{ Throwable -> 0x008c }
            r0.b(r1)     // Catch:{ Throwable -> 0x008c }
        L_0x0079:
            r4.m = r3
            r4.n = r3
            goto L_0x0005
        L_0x007e:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ Throwable -> 0x0081 }
            throw r0     // Catch:{ Throwable -> 0x0081 }
        L_0x0081:
            r0 = move-exception
            java.lang.String r1 = "GeoFenceManager"
            java.lang.String r2 = "destroyActionHandler"
            com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x008c }
            goto L_0x0027
        L_0x008c:
            r0 = move-exception
            goto L_0x0079
        L_0x008e:
            com.loc.a$b r0 = r4.l     // Catch:{ Throwable -> 0x008c }
            r0.quit()     // Catch:{ Throwable -> 0x008c }
            goto L_0x0047
        L_0x0094:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ Throwable -> 0x008c }
            throw r0     // Catch:{ Throwable -> 0x008c }
        L_0x0097:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ Throwable -> 0x009a }
            throw r0     // Catch:{ Throwable -> 0x009a }
        L_0x009a:
            r0 = move-exception
            java.lang.String r1 = "GeoFenceManager"
            java.lang.String r2 = "destroyResultHandler"
            com.loc.di.a(r0, r1, r2)     // Catch:{ Throwable -> 0x008c }
            goto L_0x006e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.a.a():void");
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i2) {
        try {
            synchronized (this.i) {
                if (this.k != null) {
                    this.k.removeMessages(i2);
                }
            }
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "removeActionHandlerMessage");
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i2, Bundle bundle) {
        try {
            synchronized (this.j) {
                if (this.h != null) {
                    Message obtainMessage = this.h.obtainMessage();
                    obtainMessage.what = i2;
                    obtainMessage.setData(bundle);
                    this.h.sendMessage(obtainMessage);
                }
            }
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "sendResultHandlerMessage");
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i2, Bundle bundle, long j2) {
        try {
            synchronized (this.i) {
                if (this.k != null) {
                    Message obtainMessage = this.k.obtainMessage();
                    obtainMessage.what = i2;
                    obtainMessage.setData(bundle);
                    this.k.sendMessageDelayed(obtainMessage, j2);
                }
            }
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "sendActionHandlerMessage");
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(Bundle bundle) {
        int i2;
        if (bundle != null) {
            try {
                i2 = bundle.getInt("activatesAction", 1);
            } catch (Throwable th) {
                di.a(th, "GeoFenceManager", "doSetActivatesAction");
                return;
            }
        } else {
            i2 = 1;
        }
        if (this.f != i2) {
            if (this.g != null && !this.g.isEmpty()) {
                Iterator it = this.g.iterator();
                while (it.hasNext()) {
                    GeoFence geoFence = (GeoFence) it.next();
                    geoFence.setStatus(0);
                    geoFence.setEnterTime(-1);
                }
            }
            f();
        }
        this.f = i2;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(com.amap.api.fence.GeoFence r7) {
        /*
            r6 = this;
            java.lang.Object r1 = r6.z     // Catch:{ Throwable -> 0x0053 }
            monitor-enter(r1)     // Catch:{ Throwable -> 0x0053 }
            android.content.Context r0 = r6.b     // Catch:{ all -> 0x0050 }
            if (r0 == 0) goto L_0x004e
            android.app.PendingIntent r0 = r6.c     // Catch:{ all -> 0x0050 }
            if (r0 != 0) goto L_0x0013
            android.app.PendingIntent r0 = r7.getPendingIntent()     // Catch:{ all -> 0x0050 }
            if (r0 != 0) goto L_0x0013
            monitor-exit(r1)     // Catch:{ all -> 0x0050 }
        L_0x0012:
            return
        L_0x0013:
            android.content.Intent r0 = new android.content.Intent     // Catch:{ all -> 0x0050 }
            r0.<init>()     // Catch:{ all -> 0x0050 }
            java.lang.String r2 = r7.getFenceId()     // Catch:{ all -> 0x0050 }
            java.lang.String r3 = r7.getCustomId()     // Catch:{ all -> 0x0050 }
            int r4 = r7.getStatus()     // Catch:{ all -> 0x0050 }
            r5 = 0
            android.os.Bundle r2 = a(r7, r2, r3, r4, r5)     // Catch:{ all -> 0x0050 }
            r0.putExtras(r2)     // Catch:{ all -> 0x0050 }
            java.lang.String r2 = r6.d     // Catch:{ all -> 0x0050 }
            if (r2 == 0) goto L_0x0035
            java.lang.String r2 = r6.d     // Catch:{ all -> 0x0050 }
            r0.setAction(r2)     // Catch:{ all -> 0x0050 }
        L_0x0035:
            android.content.Context r2 = r6.b     // Catch:{ all -> 0x0050 }
            java.lang.String r2 = com.loc.l.c(r2)     // Catch:{ all -> 0x0050 }
            r0.setPackage(r2)     // Catch:{ all -> 0x0050 }
            android.app.PendingIntent r2 = r7.getPendingIntent()     // Catch:{ all -> 0x0050 }
            if (r2 == 0) goto L_0x005e
            android.app.PendingIntent r2 = r7.getPendingIntent()     // Catch:{ all -> 0x0050 }
            android.content.Context r3 = r6.b     // Catch:{ all -> 0x0050 }
            r4 = 0
            r2.send(r3, r4, r0)     // Catch:{ all -> 0x0050 }
        L_0x004e:
            monitor-exit(r1)     // Catch:{ all -> 0x0050 }
            goto L_0x0012
        L_0x0050:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ Throwable -> 0x0053 }
            throw r0     // Catch:{ Throwable -> 0x0053 }
        L_0x0053:
            r0 = move-exception
            java.lang.String r1 = "GeoFenceManager"
            java.lang.String r2 = "resultTriggerGeoFence"
            com.loc.di.a(r0, r1, r2)
            goto L_0x0012
        L_0x005e:
            android.app.PendingIntent r2 = r6.c     // Catch:{ all -> 0x0050 }
            android.content.Context r3 = r6.b     // Catch:{ all -> 0x0050 }
            r4 = 0
            r2.send(r3, r4, r0)     // Catch:{ all -> 0x0050 }
            goto L_0x004e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.a.a(com.amap.api.fence.GeoFence):void");
    }

    /* access modifiers changed from: 0000 */
    public final void a(AMapLocation aMapLocation) {
        try {
            if (!this.y && this.g != null && !this.g.isEmpty() && aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                Iterator it = this.g.iterator();
                while (it.hasNext()) {
                    GeoFence geoFence = (GeoFence) it.next();
                    if (geoFence.isAble() && b(aMapLocation, geoFence) && a(geoFence, this.f)) {
                        geoFence.setCurrentLocation(aMapLocation);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("geoFence", geoFence);
                        a(1001, bundle);
                    }
                }
            }
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "doCheckFence");
        }
    }

    public void addDistrictGeoFence(String str, String str2) {
        try {
            e();
            Bundle bundle = new Bundle();
            bundle.putString("keyWords", str);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str2);
            a(4, bundle, 0);
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "addDistricetGeoFence");
        }
    }

    public void addKeywordGeoFence(String str, String str2, String str3, int i2, String str4) {
        int i3 = 25;
        try {
            e();
            int i4 = i2 <= 0 ? 10 : i2;
            if (i4 <= 25) {
                i3 = i4;
            }
            Bundle bundle = new Bundle();
            bundle.putString("keyWords", str);
            bundle.putString("poiType", str2);
            bundle.putString("city", str3);
            bundle.putInt("searchSize", i3);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str4);
            a(2, bundle, 0);
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "addKeywordGeoFence");
        }
    }

    public void addNearbyGeoFence(String str, String str2, DPoint dPoint, float f2, int i2, String str3) {
        int i3 = 25;
        try {
            e();
            if (f2 <= 0.0f || f2 > 50000.0f) {
                f2 = 3000.0f;
            }
            int i4 = i2 <= 0 ? 10 : i2;
            if (i4 <= 25) {
                i3 = i4;
            }
            Bundle bundle = new Bundle();
            bundle.putString("keyWords", str);
            bundle.putString("poiType", str2);
            bundle.putParcelable("centerPoint", dPoint);
            bundle.putFloat("aroundRadius", f2);
            bundle.putInt("searchSize", i3);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str3);
            a(3, bundle, 0);
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "addNearbyGeoFence");
        }
    }

    public void addPolygonGeoFence(List<DPoint> list, String str) {
        try {
            e();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("pointList", new ArrayList(list));
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
            a(1, bundle, 0);
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "addPolygonGeoFence");
        }
    }

    public void addRoundGeoFence(DPoint dPoint, float f2, String str) {
        try {
            e();
            Bundle bundle = new Bundle();
            bundle.putParcelable("centerPoint", dPoint);
            bundle.putFloat("fenceRadius", f2);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
            a(0, bundle, 0);
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "addRoundGeoFence");
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        try {
            if (this.r != null) {
                g();
                this.u.setOnceLocation(true);
                this.r.setLocationOption(this.u);
                this.r.startLocation();
            }
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "doStartOnceLocation");
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(int i2) {
        try {
            if (this.b != null) {
                synchronized (this.z) {
                    if (this.c != null) {
                        Intent intent = new Intent();
                        intent.putExtras(a(null, null, null, 4, i2));
                        this.c.send(this.b, 0, intent);
                    }
                }
            }
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "resultRemindLocationError");
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(Bundle bundle) {
        String str;
        int i2;
        try {
            ArrayList arrayList = new ArrayList();
            String str2 = "";
            if (bundle != null && !bundle.isEmpty()) {
                DPoint dPoint = (DPoint) bundle.getParcelable("centerPoint");
                str = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                if (dPoint == null) {
                    str2 = str;
                } else if (dPoint.getLatitude() > 90.0d || dPoint.getLatitude() < -90.0d || dPoint.getLongitude() > 180.0d || dPoint.getLongitude() < -180.0d) {
                    a("添加围栏失败", 1, "经纬度错误，传入的纬度：" + dPoint.getLatitude() + "传入的经度:" + dPoint.getLongitude(), new String[0]);
                    i2 = 1;
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("errorCode", i2);
                    bundle2.putParcelableArrayList("resultList", arrayList);
                    bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
                    a(1000, bundle2);
                } else {
                    GeoFence a2 = a(bundle, false);
                    i2 = b(a2);
                    if (i2 == 0) {
                        arrayList.add(a2);
                    }
                    Bundle bundle22 = new Bundle();
                    bundle22.putInt("errorCode", i2);
                    bundle22.putParcelableArrayList("resultList", arrayList);
                    bundle22.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
                    a(1000, bundle22);
                }
            }
            str = str2;
            i2 = 1;
            Bundle bundle222 = new Bundle();
            bundle222.putInt("errorCode", i2);
            bundle222.putParcelableArrayList("resultList", arrayList);
            bundle222.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
            a(1000, bundle222);
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "doAddGeoFenceRound");
        }
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        float f2;
        try {
            if (!this.y && dr.a(this.s)) {
                AMapLocation aMapLocation = this.s;
                ArrayList<GeoFence> arrayList = this.g;
                if (aMapLocation != null && aMapLocation.getErrorCode() == 0 && arrayList != null && !arrayList.isEmpty()) {
                    DPoint dPoint = new DPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    Iterator it = arrayList.iterator();
                    float f3 = Float.MAX_VALUE;
                    while (true) {
                        if (!it.hasNext()) {
                            f2 = f3;
                            break;
                        }
                        GeoFence geoFence = (GeoFence) it.next();
                        if (geoFence.isAble()) {
                            float a2 = dr.a(dPoint, geoFence.getCenter());
                            if (a2 > geoFence.getMinDis2Center() && a2 < geoFence.getMaxDis2Center()) {
                                f2 = 0.0f;
                                break;
                            }
                            if (a2 > geoFence.getMaxDis2Center()) {
                                f3 = Math.min(f3, a2 - geoFence.getMaxDis2Center());
                            }
                            f3 = a2 < geoFence.getMinDis2Center() ? Math.min(f3, geoFence.getMinDis2Center() - a2) : f3;
                        }
                    }
                } else {
                    f2 = Float.MAX_VALUE;
                }
                if (f2 == Float.MAX_VALUE) {
                    return;
                }
                if (f2 < 1000.0f) {
                    a(7);
                    Bundle bundle = new Bundle();
                    bundle.putLong("interval", 2000);
                    a(8, bundle, 500);
                } else if (f2 < 5000.0f) {
                    g();
                    a(7);
                    a(7, null, 10000);
                } else {
                    g();
                    a(7);
                    a(7, null, (long) (((f2 - 4000.0f) / 100.0f) * 1000.0f));
                }
            }
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "doCheckLocationPolicy");
        }
    }

    /* access modifiers changed from: 0000 */
    public final void c(Bundle bundle) {
        int i2 = 1;
        try {
            ArrayList arrayList = new ArrayList();
            String str = "";
            if (bundle != null && !bundle.isEmpty()) {
                ArrayList parcelableArrayList = bundle.getParcelableArrayList("pointList");
                str = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                if (parcelableArrayList != null && parcelableArrayList.size() > 2) {
                    GeoFence a2 = a(bundle, true);
                    i2 = b(a2);
                    if (i2 == 0) {
                        arrayList.add(a2);
                    }
                }
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
            bundle2.putInt("errorCode", i2);
            bundle2.putParcelableArrayList("resultList", arrayList);
            a(1000, bundle2);
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "doAddGeoFencePolygon");
        }
    }

    public PendingIntent createPendingIntent(String str) {
        synchronized (this.z) {
            try {
                Intent intent = new Intent(str);
                intent.setPackage(l.c(this.b));
                this.c = PendingIntent.getBroadcast(this.b, 0, intent, 0);
                this.d = str;
                if (this.g != null && !this.g.isEmpty()) {
                    Iterator it = this.g.iterator();
                    while (it.hasNext()) {
                        GeoFence geoFence = (GeoFence) it.next();
                        geoFence.setPendingIntent(this.c);
                        geoFence.setPendingIntentAction(this.d);
                    }
                }
            } catch (Throwable th) {
                di.a(th, "GeoFenceManager", "createPendingIntent");
            }
        }
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public final void d() {
        try {
            a(7);
            a(8);
            if (this.r != null) {
                this.r.stopLocation();
            }
            this.m = false;
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "doPauseGeoFence");
        }
    }

    /* access modifiers changed from: 0000 */
    public final void d(Bundle bundle) {
        b(2, bundle);
    }

    /* access modifiers changed from: 0000 */
    public final void e(Bundle bundle) {
        b(1, bundle);
    }

    /* access modifiers changed from: 0000 */
    public final void f(Bundle bundle) {
        b(3, bundle);
    }

    /* access modifiers changed from: 0000 */
    public final void g(Bundle bundle) {
        boolean z2;
        if (bundle != null) {
            try {
                if (!bundle.isEmpty()) {
                    String string = bundle.getString("fid");
                    if (!TextUtils.isEmpty(string)) {
                        boolean z3 = bundle.getBoolean("ab", true);
                        if (this.g != null && !this.g.isEmpty()) {
                            Iterator it = this.g.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                GeoFence geoFence = (GeoFence) it.next();
                                if (geoFence.getFenceId().equals(string)) {
                                    geoFence.setAble(z3);
                                    break;
                                }
                            }
                        }
                        if (!z3) {
                            if (this.g != null && !this.g.isEmpty()) {
                                Iterator it2 = this.g.iterator();
                                while (true) {
                                    if (it2.hasNext()) {
                                        if (((GeoFence) it2.next()).isAble()) {
                                            z2 = false;
                                            break;
                                        }
                                    } else {
                                        z2 = true;
                                        break;
                                    }
                                }
                            } else {
                                z2 = true;
                            }
                            if (z2) {
                                d();
                                return;
                            }
                            return;
                        }
                        f();
                    }
                }
            } catch (Throwable th) {
                di.a(th, "GeoFenceManager", "doSetGeoFenceAble");
            }
        }
    }

    public List<GeoFence> getAllGeoFence() {
        try {
            if (this.g == null) {
                this.g = new ArrayList<>();
            }
            return (ArrayList) this.g.clone();
        } catch (Throwable th) {
            return new ArrayList();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void h(Bundle bundle) {
        try {
            if (this.g != null) {
                GeoFence geoFence = (GeoFence) bundle.getParcelable("fc");
                if (this.g.contains(geoFence)) {
                    this.g.remove(geoFence);
                }
                if (this.g.size() <= 0) {
                    a();
                } else {
                    f();
                }
            }
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: 0000 */
    public final void i(Bundle bundle) {
        if (bundle != null) {
            try {
                if (!bundle.isEmpty()) {
                    int i2 = bundle.getInt("errorCode");
                    ArrayList parcelableArrayList = bundle.getParcelableArrayList("resultList");
                    ArrayList arrayList = parcelableArrayList == null ? new ArrayList() : parcelableArrayList;
                    String string = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                    String str = string == null ? "" : string;
                    if (this.e != null) {
                        this.e.onGeoFenceCreateFinished((ArrayList) arrayList.clone(), i2, str);
                    }
                    if (i2 == 0) {
                        f();
                    }
                }
            } catch (Throwable th) {
                di.a(th, "GeoFenceManager", "resultAddGeoFenceFinished");
            }
        }
    }

    public boolean isPause() {
        return this.y;
    }

    /* access modifiers changed from: 0000 */
    public final void j(Bundle bundle) {
        long j2 = 2000;
        try {
            if (this.r != null) {
                if (bundle != null && !bundle.isEmpty()) {
                    j2 = bundle.getLong("interval", 2000);
                }
                this.u.setOnceLocation(false);
                this.u.setInterval(j2);
                this.r.setLocationOption(this.u);
                if (!this.m) {
                    this.r.stopLocation();
                    this.r.startLocation();
                    this.m = true;
                }
            }
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "doStartContinueLocation");
        }
    }

    public void pauseGeoFence() {
        try {
            e();
            this.y = true;
            a(13, null, 0);
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "pauseGeoFence");
        }
    }

    public void removeGeoFence() {
        try {
            this.o = false;
            a(10, null, 0);
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "removeGeoFence");
        }
    }

    public boolean removeGeoFence(GeoFence geoFence) {
        try {
            if (this.g == null || this.g.isEmpty()) {
                this.o = false;
                a(10, null, 0);
                return true;
            } else if (!this.g.contains(geoFence)) {
                return false;
            } else {
                if (this.g.size() == 1) {
                    this.o = false;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("fc", geoFence);
                a(11, bundle, 0);
                return true;
            }
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "removeGeoFence(GeoFence)");
            return false;
        }
    }

    public void resumeGeoFence() {
        try {
            e();
            if (this.y) {
                this.y = false;
                f();
            }
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "resumeGeoFence");
        }
    }

    public void setActivateAction(int i2) {
        try {
            e();
            if (i2 > 7 || i2 <= 0) {
                i2 = 1;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("activatesAction", i2);
            a(9, bundle, 0);
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "setActivateAction");
        }
    }

    public void setGeoFenceAble(String str, boolean z2) {
        try {
            e();
            Bundle bundle = new Bundle();
            bundle.putString("fid", str);
            bundle.putBoolean("ab", z2);
            a(12, bundle, 0);
        } catch (Throwable th) {
            di.a(th, "GeoFenceManager", "setGeoFenceAble");
        }
    }

    public void setGeoFenceListener(GeoFenceListener geoFenceListener) {
        try {
            this.e = geoFenceListener;
        } catch (Throwable th) {
        }
    }
}
