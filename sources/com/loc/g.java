package com.loc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationClientOption.GeoLanguage;
import com.amap.api.location.DPoint;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/* compiled from: GpsLocation */
public final class g {
    static AMapLocation j = null;
    static long k = 0;
    static Object l = new Object();
    static long q = 0;
    static boolean t = false;
    static boolean u = false;
    /* access modifiers changed from: private */
    public long A = 0;
    private int B = 0;
    /* access modifiers changed from: private */
    public int C = 0;
    /* access modifiers changed from: private */
    public GpsStatus D = null;
    private Listener E = new Listener() {
        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onGpsStatusChanged(int r5) {
            /*
                r4 = this;
                r1 = 0
                com.loc.g r0 = com.loc.g.this     // Catch:{ Throwable -> 0x0026 }
                android.location.LocationManager r0 = r0.b     // Catch:{ Throwable -> 0x0026 }
                if (r0 != 0) goto L_0x0008
            L_0x0007:
                return
            L_0x0008:
                com.loc.g r0 = com.loc.g.this     // Catch:{ Throwable -> 0x0026 }
                com.loc.g r2 = com.loc.g.this     // Catch:{ Throwable -> 0x0026 }
                android.location.LocationManager r2 = r2.b     // Catch:{ Throwable -> 0x0026 }
                com.loc.g r3 = com.loc.g.this     // Catch:{ Throwable -> 0x0026 }
                android.location.GpsStatus r3 = r3.D     // Catch:{ Throwable -> 0x0026 }
                android.location.GpsStatus r2 = r2.getGpsStatus(r3)     // Catch:{ Throwable -> 0x0026 }
                r0.D = r2     // Catch:{ Throwable -> 0x0026 }
                switch(r5) {
                    case 1: goto L_0x0007;
                    case 2: goto L_0x001f;
                    case 3: goto L_0x0007;
                    case 4: goto L_0x0031;
                    default: goto L_0x001e;
                }     // Catch:{ Throwable -> 0x0026 }
            L_0x001e:
                goto L_0x0007
            L_0x001f:
                com.loc.g r0 = com.loc.g.this     // Catch:{ Throwable -> 0x0026 }
                r1 = 0
                r0.C = r1     // Catch:{ Throwable -> 0x0026 }
                goto L_0x0007
            L_0x0026:
                r0 = move-exception
                java.lang.String r1 = "GpsLocation"
                java.lang.String r2 = "onGpsStatusChanged"
                com.loc.di.a(r0, r1, r2)
                goto L_0x0007
            L_0x0031:
                com.loc.g r0 = com.loc.g.this     // Catch:{ Throwable -> 0x006b }
                android.location.GpsStatus r0 = r0.D     // Catch:{ Throwable -> 0x006b }
                if (r0 == 0) goto L_0x0075
                com.loc.g r0 = com.loc.g.this     // Catch:{ Throwable -> 0x006b }
                android.location.GpsStatus r0 = r0.D     // Catch:{ Throwable -> 0x006b }
                java.lang.Iterable r0 = r0.getSatellites()     // Catch:{ Throwable -> 0x006b }
                if (r0 == 0) goto L_0x0075
                java.util.Iterator r2 = r0.iterator()     // Catch:{ Throwable -> 0x006b }
                com.loc.g r0 = com.loc.g.this     // Catch:{ Throwable -> 0x006b }
                android.location.GpsStatus r0 = r0.D     // Catch:{ Throwable -> 0x006b }
                int r3 = r0.getMaxSatellites()     // Catch:{ Throwable -> 0x006b }
            L_0x0053:
                boolean r0 = r2.hasNext()     // Catch:{ Throwable -> 0x006b }
                if (r0 == 0) goto L_0x0075
                if (r1 >= r3) goto L_0x0075
                java.lang.Object r0 = r2.next()     // Catch:{ Throwable -> 0x006b }
                android.location.GpsSatellite r0 = (android.location.GpsSatellite) r0     // Catch:{ Throwable -> 0x006b }
                boolean r0 = r0.usedInFix()     // Catch:{ Throwable -> 0x006b }
                if (r0 == 0) goto L_0x007b
                int r0 = r1 + 1
            L_0x0069:
                r1 = r0
                goto L_0x0053
            L_0x006b:
                r0 = move-exception
                java.lang.String r2 = "GpsLocation"
                java.lang.String r3 = "GPS_EVENT_SATELLITE_STATUS"
                com.loc.di.a(r0, r2, r3)     // Catch:{ Throwable -> 0x0026 }
            L_0x0075:
                com.loc.g r0 = com.loc.g.this     // Catch:{ Throwable -> 0x0026 }
                r0.C = r1     // Catch:{ Throwable -> 0x0026 }
                goto L_0x0007
            L_0x007b:
                r0 = r1
                goto L_0x0069
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.g.AnonymousClass2.onGpsStatusChanged(int):void");
        }
    };
    /* access modifiers changed from: private */
    public String F = null;
    /* access modifiers changed from: private */
    public boolean G = false;
    /* access modifiers changed from: private */
    public int H = 0;
    private boolean I = false;
    Handler a;
    LocationManager b;
    AMapLocationClientOption c;
    long d = 0;
    boolean e = false;
    cq f = null;
    int g = 240;
    int h = 80;
    AMapLocation i = null;
    long m = 0;
    float n = 0.0f;
    Object o = new Object();
    Object p = new Object();
    GeoLanguage r = GeoLanguage.DEFAULT;
    boolean s = true;
    long v = 0;
    int w = 0;
    LocationListener x = new LocationListener() {
        public final void onLocationChanged(Location location) {
            if (g.this.a != null) {
                g.this.a.removeMessages(8);
            }
            if (location != null) {
                try {
                    AMapLocation aMapLocation = new AMapLocation(location);
                    if (dr.a(aMapLocation)) {
                        aMapLocation.setProvider("gps");
                        aMapLocation.setLocationType(1);
                        if (!g.this.e && dr.a(aMapLocation)) {
                            Cdo.a(g.this.z, dr.c() - g.this.A, di.a(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
                            g.this.e = true;
                        }
                        if (dr.a(location, g.this.C)) {
                            aMapLocation.setMock(true);
                            aMapLocation.setTrustedLevel(4);
                            if (!g.this.c.isMockEnable()) {
                                if (g.this.w > 3) {
                                    Cdo.a((String) null, 2152);
                                    aMapLocation.setErrorCode(15);
                                    aMapLocation.setLocationDetail("GpsLocation has been mocked!#1501");
                                    aMapLocation.setLatitude(Utils.DOUBLE_EPSILON);
                                    aMapLocation.setLongitude(Utils.DOUBLE_EPSILON);
                                    aMapLocation.setAltitude(Utils.DOUBLE_EPSILON);
                                    aMapLocation.setSpeed(0.0f);
                                    aMapLocation.setAccuracy(0.0f);
                                    aMapLocation.setBearing(0.0f);
                                    aMapLocation.setExtras(null);
                                    g.this.c(aMapLocation);
                                    return;
                                }
                                g.this.w++;
                                return;
                            }
                        } else {
                            g.this.w = 0;
                        }
                        aMapLocation.setSatellites(g.this.C);
                        g.b(g.this, aMapLocation);
                        g.c(g.this, aMapLocation);
                        g gVar = g.this;
                        g.b(aMapLocation);
                        AMapLocation d = g.d(g.this, aMapLocation);
                        g.e(g.this, d);
                        g.this.a(d);
                        synchronized (g.this.o) {
                            g.a(g.this, d, g.this.y);
                        }
                        try {
                            if (dr.a(d)) {
                                if (g.this.i != null) {
                                    g.this.m = location.getTime() - g.this.i.getTime();
                                    g.this.n = dr.a(g.this.i, d);
                                }
                                synchronized (g.this.p) {
                                    g.this.i = d.clone();
                                }
                                g.this.F = null;
                                g.this.G = false;
                                g.this.H = 0;
                            }
                        } catch (Throwable th) {
                            di.a(th, "GpsLocation", "onLocationChangedLast");
                        }
                        g.this.c(d);
                    }
                } catch (Throwable th2) {
                    di.a(th2, "GpsLocation", "onLocationChanged");
                }
            }
        }

        public final void onProviderDisabled(String str) {
            try {
                if ("gps".equalsIgnoreCase(str)) {
                    g.this.d = 0;
                    g.this.C = 0;
                }
            } catch (Throwable th) {
            }
        }

        public final void onProviderEnabled(String str) {
        }

        public final void onStatusChanged(String str, int i, Bundle bundle) {
            if (i == 0) {
                try {
                    g.this.d = 0;
                    g.this.C = 0;
                } catch (Throwable th) {
                }
            }
        }
    };
    public AMapLocation y = null;
    /* access modifiers changed from: private */
    public Context z;

    public g(Context context, Handler handler) {
        this.z = context;
        this.a = handler;
        try {
            this.b = (LocationManager) this.z.getSystemService("location");
        } catch (Throwable th) {
            di.a(th, "GpsLocation", "<init>");
        }
        this.f = new cq();
    }

    private void a(int i2, int i3, String str, long j2) {
        try {
            if (this.a != null && this.c.getLocationMode() == AMapLocationMode.Device_Sensors) {
                Message obtain = Message.obtain();
                AMapLocation aMapLocation = new AMapLocation("");
                aMapLocation.setProvider("gps");
                aMapLocation.setErrorCode(i3);
                aMapLocation.setLocationDetail(str);
                aMapLocation.setLocationType(1);
                obtain.obj = aMapLocation;
                obtain.what = i2;
                this.a.sendMessageDelayed(obtain, j2);
            }
        } catch (Throwable th) {
        }
    }

    static /* synthetic */ void a(g gVar, AMapLocation aMapLocation, AMapLocation aMapLocation2) {
        if (aMapLocation2 != null && gVar.c.isNeedAddress() && dr.a(aMapLocation, aMapLocation2) < ((float) gVar.g)) {
            di.a(aMapLocation, aMapLocation2);
        }
    }

    private static boolean a(LocationManager locationManager) {
        try {
            if (t) {
                return u;
            }
            List allProviders = locationManager.getAllProviders();
            if (allProviders == null || allProviders.size() <= 0) {
                u = false;
            } else {
                u = allProviders.contains("gps");
            }
            t = true;
            return u;
        } catch (Throwable th) {
            return u;
        }
    }

    private boolean a(String str) {
        try {
            ArrayList d2 = dr.d(str);
            ArrayList d3 = dr.d(this.F);
            if (d2 == null || d2.size() < 8 || d3 == null || d3.size() < 8) {
                return false;
            }
            return dr.a(this.F, str);
        } catch (Throwable th) {
            return false;
        }
    }

    static /* synthetic */ void b(AMapLocation aMapLocation) {
        if (dr.a(aMapLocation) && dh.E()) {
            long time = aMapLocation.getTime();
            long currentTimeMillis = System.currentTimeMillis();
            long a2 = dj.a(time, currentTimeMillis, dh.F());
            if (a2 != time) {
                aMapLocation.setTime(a2);
                Cdo.a(time, currentTimeMillis);
            }
        }
    }

    static /* synthetic */ void b(g gVar, AMapLocation aMapLocation) {
        try {
            if (!di.a(aMapLocation.getLatitude(), aMapLocation.getLongitude()) || !gVar.c.isOffset()) {
                aMapLocation.setOffset(false);
                aMapLocation.setCoordType(AMapLocation.COORD_TYPE_WGS84);
                return;
            }
            DPoint a2 = dk.a(gVar.z, new DPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
            aMapLocation.setLatitude(a2.getLatitude());
            aMapLocation.setLongitude(a2.getLongitude());
            aMapLocation.setOffset(gVar.c.isOffset());
            aMapLocation.setCoordType(AMapLocation.COORD_TYPE_GCJ02);
        } catch (Throwable th) {
            aMapLocation.setOffset(false);
            aMapLocation.setCoordType(AMapLocation.COORD_TYPE_WGS84);
        }
    }

    /* access modifiers changed from: private */
    public void c(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode() == 15 && !AMapLocationMode.Device_Sensors.equals(this.c.getLocationMode())) {
            return;
        }
        if (this.c.getLocationMode().equals(AMapLocationMode.Device_Sensors) && this.c.getDeviceModeDistanceFilter() > 0.0f) {
            d(aMapLocation);
        } else if (dr.c() - this.v >= this.c.getInterval() - 200) {
            this.v = dr.c();
            d(aMapLocation);
        }
    }

    static /* synthetic */ void c(g gVar, AMapLocation aMapLocation) {
        try {
            if (gVar.C >= 4) {
                aMapLocation.setGpsAccuracyStatus(1);
            } else if (gVar.C == 0) {
                aMapLocation.setGpsAccuracyStatus(-1);
            } else {
                aMapLocation.setGpsAccuracyStatus(0);
            }
        } catch (Throwable th) {
        }
    }

    static /* synthetic */ AMapLocation d(g gVar, AMapLocation aMapLocation) {
        if (!dr.a(aMapLocation) || gVar.B < 3) {
            return aMapLocation;
        }
        if (aMapLocation.getAccuracy() < 0.0f || aMapLocation.getAccuracy() == Float.MAX_VALUE) {
            aMapLocation.setAccuracy(0.0f);
        }
        if (aMapLocation.getSpeed() < 0.0f || aMapLocation.getSpeed() == Float.MAX_VALUE) {
            aMapLocation.setSpeed(0.0f);
        }
        return gVar.f.a(aMapLocation);
    }

    private void d(AMapLocation aMapLocation) {
        if (this.a != null) {
            Message obtain = Message.obtain();
            obtain.obj = aMapLocation;
            obtain.what = 2;
            this.a.sendMessage(obtain);
        }
    }

    static /* synthetic */ void e(g gVar, AMapLocation aMapLocation) {
        if (dr.a(aMapLocation)) {
            gVar.d = dr.c();
            synchronized (l) {
                k = dr.c();
                j = aMapLocation.clone();
            }
            gVar.B++;
        }
    }

    private static boolean g() {
        try {
            return ((Boolean) dm.a(w.c("KY29tLmFtYXAuYXBpLm5hdmkuQU1hcE5hdmk="), w.c("UaXNOYXZpU3RhcnRlZA=="), (Object[]) null, (Class<?>[]) null)).booleanValue();
        } catch (Throwable th) {
            return false;
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.amap.api.location.AMapLocation h() {
        /*
            r15 = this;
            r1 = 0
            r2 = 0
            com.amap.api.location.AMapLocation r0 = r15.i     // Catch:{ Throwable -> 0x0112 }
            boolean r0 = com.loc.dr.a(r0)     // Catch:{ Throwable -> 0x0112 }
            if (r0 != 0) goto L_0x000c
            r0 = r2
        L_0x000b:
            return r0
        L_0x000c:
            boolean r0 = com.loc.dh.t()     // Catch:{ Throwable -> 0x0112 }
            if (r0 != 0) goto L_0x0014
            r0 = r2
            goto L_0x000b
        L_0x0014:
            boolean r0 = g()     // Catch:{ Throwable -> 0x0112 }
            if (r0 == 0) goto L_0x0113
            java.lang.String r0 = "KY29tLmFtYXAuYXBpLm5hdmkuQU1hcE5hdmk="
            java.lang.String r0 = com.loc.w.c(r0)     // Catch:{ Throwable -> 0x0112 }
            java.lang.String r3 = "UZ2V0TmF2aUxvY2F0aW9u"
            java.lang.String r3 = com.loc.w.c(r3)     // Catch:{ Throwable -> 0x0112 }
            r4 = 0
            r5 = 0
            java.lang.Object r0 = com.loc.dm.a(r0, r3, r4, (java.lang.Class<?>[]) r5)     // Catch:{ Throwable -> 0x0112 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0112 }
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0112 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x0112 }
            java.lang.String r0 = "time"
            long r6 = r3.optLong(r0)     // Catch:{ Throwable -> 0x0112 }
            boolean r0 = r15.I     // Catch:{ Throwable -> 0x0112 }
            if (r0 != 0) goto L_0x004c
            r0 = 1
            r15.I = r0     // Catch:{ Throwable -> 0x0112 }
            java.lang.String r0 = "useNaviLoc"
            java.lang.String r4 = "use NaviLoc"
            com.loc.Cdo.a(r0, r4)     // Catch:{ Throwable -> 0x0112 }
        L_0x004c:
            long r4 = com.loc.dr.b()     // Catch:{ Throwable -> 0x0112 }
            long r4 = r4 - r6
            r8 = 5500(0x157c, double:2.7174E-320)
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 > 0) goto L_0x0113
            java.lang.String r0 = "lat"
            r4 = 0
            double r8 = r3.optDouble(r0, r4)     // Catch:{ Throwable -> 0x0112 }
            java.lang.String r0 = "lng"
            r4 = 0
            double r10 = r3.optDouble(r0, r4)     // Catch:{ Throwable -> 0x0112 }
            java.lang.String r0 = "accuracy"
            java.lang.String r4 = "0"
            java.lang.String r0 = r3.optString(r0, r4)     // Catch:{ NumberFormatException -> 0x0105 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ NumberFormatException -> 0x0105 }
            r4 = r0
        L_0x0078:
            java.lang.String r0 = "altitude"
            r12 = 0
            double r12 = r3.optDouble(r0, r12)     // Catch:{ Throwable -> 0x0112 }
            java.lang.String r0 = "bearing"
            java.lang.String r5 = "0"
            java.lang.String r0 = r3.optString(r0, r5)     // Catch:{ NumberFormatException -> 0x0109 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ NumberFormatException -> 0x0109 }
        L_0x008f:
            java.lang.String r5 = "speed"
            java.lang.String r14 = "0"
            java.lang.String r3 = r3.optString(r5, r14)     // Catch:{ NumberFormatException -> 0x010c }
            float r1 = java.lang.Float.parseFloat(r3)     // Catch:{ NumberFormatException -> 0x010c }
            r3 = 1092616192(0x41200000, float:10.0)
            float r1 = r1 * r3
            r3 = 1108344832(0x42100000, float:36.0)
            float r1 = r1 / r3
            r3 = r1
        L_0x00a4:
            com.amap.api.location.AMapLocation r1 = new com.amap.api.location.AMapLocation     // Catch:{ Throwable -> 0x0112 }
            java.lang.String r5 = "lbs"
            r1.<init>(r5)     // Catch:{ Throwable -> 0x0112 }
            r5 = 9
            r1.setLocationType(r5)     // Catch:{ Throwable -> 0x0112 }
            r1.setLatitude(r8)     // Catch:{ Throwable -> 0x0112 }
            r1.setLongitude(r10)     // Catch:{ Throwable -> 0x0112 }
            r1.setAccuracy(r4)     // Catch:{ Throwable -> 0x0112 }
            r1.setAltitude(r12)     // Catch:{ Throwable -> 0x0112 }
            r1.setBearing(r0)     // Catch:{ Throwable -> 0x0112 }
            r1.setSpeed(r3)     // Catch:{ Throwable -> 0x0112 }
            r1.setTime(r6)     // Catch:{ Throwable -> 0x0112 }
            java.lang.String r5 = "GCJ02"
            r1.setCoordType(r5)     // Catch:{ Throwable -> 0x0112 }
            com.amap.api.location.AMapLocation r5 = r15.i     // Catch:{ Throwable -> 0x0112 }
            float r5 = com.loc.dr.a(r1, r5)     // Catch:{ Throwable -> 0x0112 }
            r12 = 1133903872(0x43960000, float:300.0)
            int r5 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r5 > 0) goto L_0x0116
            java.lang.Object r5 = r15.p     // Catch:{ Throwable -> 0x0112 }
            monitor-enter(r5)     // Catch:{ Throwable -> 0x0112 }
            com.amap.api.location.AMapLocation r12 = r15.i     // Catch:{ all -> 0x010f }
            r12.setLongitude(r10)     // Catch:{ all -> 0x010f }
            com.amap.api.location.AMapLocation r10 = r15.i     // Catch:{ all -> 0x010f }
            r10.setLatitude(r8)     // Catch:{ all -> 0x010f }
            com.amap.api.location.AMapLocation r8 = r15.i     // Catch:{ all -> 0x010f }
            r8.setAccuracy(r4)     // Catch:{ all -> 0x010f }
            com.amap.api.location.AMapLocation r4 = r15.i     // Catch:{ all -> 0x010f }
            r4.setBearing(r0)     // Catch:{ all -> 0x010f }
            com.amap.api.location.AMapLocation r0 = r15.i     // Catch:{ all -> 0x010f }
            r0.setSpeed(r3)     // Catch:{ all -> 0x010f }
            com.amap.api.location.AMapLocation r0 = r15.i     // Catch:{ all -> 0x010f }
            r0.setTime(r6)     // Catch:{ all -> 0x010f }
            com.amap.api.location.AMapLocation r0 = r15.i     // Catch:{ all -> 0x010f }
            java.lang.String r3 = "GCJ02"
            r0.setCoordType(r3)     // Catch:{ all -> 0x010f }
            monitor-exit(r5)     // Catch:{ all -> 0x010f }
            r0 = r1
            goto L_0x000b
        L_0x0105:
            r0 = move-exception
            r4 = r1
            goto L_0x0078
        L_0x0109:
            r0 = move-exception
            r0 = r1
            goto L_0x008f
        L_0x010c:
            r3 = move-exception
            r3 = r1
            goto L_0x00a4
        L_0x010f:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ Throwable -> 0x0112 }
            throw r0     // Catch:{ Throwable -> 0x0112 }
        L_0x0112:
            r0 = move-exception
        L_0x0113:
            r0 = r2
            goto L_0x000b
        L_0x0116:
            r0 = r2
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.g.h():com.amap.api.location.AMapLocation");
    }

    public final AMapLocation a(AMapLocation aMapLocation, String str) {
        long j2;
        if (this.i == null) {
            return aMapLocation;
        }
        if ((!this.c.isMockEnable() && this.i.isMock()) || !dr.a(this.i)) {
            return aMapLocation;
        }
        AMapLocation h2 = h();
        if (h2 == null || !dr.a(h2)) {
            float speed = this.i.getSpeed();
            if (speed == 0.0f && this.m > 0 && this.m < 8 && this.n > 0.0f) {
                speed = this.n / ((float) this.m);
            }
            if (aMapLocation == null || !dr.a(aMapLocation)) {
                j2 = 30000;
            } else if (aMapLocation.getAccuracy() < 200.0f) {
                this.H++;
                if (this.F == null && this.H >= 2) {
                    this.G = true;
                }
                j2 = speed > 5.0f ? 10000 : 15000;
            } else {
                if (!TextUtils.isEmpty(this.F)) {
                    this.G = false;
                    this.H = 0;
                }
                j2 = speed > 5.0f ? 20000 : 30000;
            }
            long c2 = dr.c() - this.d;
            if (c2 > 30000) {
                return aMapLocation;
            }
            if (c2 < j2) {
                if (this.F == null && this.H >= 2) {
                    this.F = str;
                }
                AMapLocation clone = this.i.clone();
                clone.setTrustedLevel(2);
                return clone;
            } else if (!this.G || !a(str)) {
                this.F = null;
                this.H = 0;
                synchronized (this.p) {
                    this.i = null;
                }
                this.m = 0;
                this.n = 0.0f;
                return aMapLocation;
            } else {
                AMapLocation clone2 = this.i.clone();
                clone2.setTrustedLevel(3);
                return clone2;
            }
        } else {
            h2.setTrustedLevel(2);
            return h2;
        }
    }

    public final void a() {
        if (this.b != null) {
            try {
                if (this.x != null) {
                    this.b.removeUpdates(this.x);
                }
            } catch (Throwable th) {
            }
            try {
                if (this.E != null) {
                    this.b.removeGpsStatusListener(this.E);
                }
            } catch (Throwable th2) {
            }
            try {
                if (this.a != null) {
                    this.a.removeMessages(8);
                }
            } catch (Throwable th3) {
            }
            this.C = 0;
            this.A = 0;
            this.v = 0;
            this.d = 0;
            this.B = 0;
            this.w = 0;
            this.f.a();
            this.i = null;
            this.m = 0;
            this.n = 0.0f;
            this.F = null;
            this.I = false;
        }
    }

    public final void a(Bundle bundle) {
        if (bundle != null) {
            try {
                bundle.setClassLoader(AMapLocation.class.getClassLoader());
                this.g = bundle.getInt("I_MAX_GEO_DIS");
                this.h = bundle.getInt("I_MIN_GEO_DIS");
                AMapLocation aMapLocation = (AMapLocation) bundle.getParcelable("loc");
                if (!TextUtils.isEmpty(aMapLocation.getAdCode())) {
                    synchronized (this.o) {
                        this.y = aMapLocation;
                    }
                }
            } catch (Throwable th) {
                di.a(th, "GpsLocation", "setLastGeoLocation");
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(AMapLocation aMapLocation) {
        if (dr.a(aMapLocation) && this.a != null && this.c.isNeedAddress()) {
            long c2 = dr.c();
            if (this.c.getInterval() <= 8000 || c2 - this.v > this.c.getInterval() - 8000) {
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", aMapLocation.getLatitude());
                bundle.putDouble("lon", aMapLocation.getLongitude());
                Message obtain = Message.obtain();
                obtain.setData(bundle);
                obtain.what = 5;
                synchronized (this.o) {
                    if (this.y == null) {
                        this.a.sendMessage(obtain);
                    } else if (dr.a(aMapLocation, this.y) > ((float) this.h)) {
                        this.a.sendMessage(obtain);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00e8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e9, code lost:
        r12.s = false;
        com.loc.Cdo.a((java.lang.String) null, 2121);
        a(2, 12, r0.getMessage() + "#1201", 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00e8 A[ExcHandler: SecurityException (r0v4 'e' java.lang.SecurityException A[CUSTOM_DECLARE]), Splitter:B:26:0x00a0] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(com.amap.api.location.AMapLocationClientOption r13) {
        /*
            r12 = this;
            r10 = 0
            r8 = 0
            r7 = 0
            r12.c = r13
            com.amap.api.location.AMapLocationClientOption r0 = r12.c
            if (r0 != 0) goto L_0x0011
            com.amap.api.location.AMapLocationClientOption r0 = new com.amap.api.location.AMapLocationClientOption
            r0.<init>()
            r12.c = r0
        L_0x0011:
            android.content.Context r0 = r12.z     // Catch:{ Throwable -> 0x013f }
            java.lang.String r1 = "pref"
            java.lang.String r2 = "lagt"
            long r4 = q     // Catch:{ Throwable -> 0x013f }
            long r0 = com.loc.dq.b(r0, r1, r2, r4)     // Catch:{ Throwable -> 0x013f }
            q = r0     // Catch:{ Throwable -> 0x013f }
        L_0x0021:
            android.location.LocationManager r0 = r12.b
            if (r0 != 0) goto L_0x0026
        L_0x0025:
            return
        L_0x0026:
            long r0 = com.loc.dr.c()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            long r2 = k     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            long r0 = r0 - r2
            r2 = 5000(0x1388, double:2.4703E-320)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x0056
            com.amap.api.location.AMapLocation r0 = j     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            boolean r0 = com.loc.dr.a(r0)     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            if (r0 == 0) goto L_0x0056
            com.amap.api.location.AMapLocationClientOption r0 = r12.c     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            boolean r0 = r0.isMockEnable()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            if (r0 != 0) goto L_0x004b
            com.amap.api.location.AMapLocation r0 = j     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            boolean r0 = r0.isMock()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            if (r0 != 0) goto L_0x0056
        L_0x004b:
            long r0 = com.loc.dr.c()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            r12.d = r0     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            com.amap.api.location.AMapLocation r0 = j     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            r12.c(r0)     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
        L_0x0056:
            r0 = 1
            r12.s = r0     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            android.os.Looper r6 = android.os.Looper.myLooper()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            if (r6 != 0) goto L_0x0065
            android.content.Context r0 = r12.z     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            android.os.Looper r6 = r0.getMainLooper()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
        L_0x0065:
            long r0 = com.loc.dr.c()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            r12.A = r0     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            android.location.LocationManager r0 = r12.b     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            boolean r0 = a(r0)     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            if (r0 == 0) goto L_0x012d
            long r0 = com.loc.dr.b()     // Catch:{ Throwable -> 0x013c, SecurityException -> 0x00e8 }
            long r2 = q     // Catch:{ Throwable -> 0x013c, SecurityException -> 0x00e8 }
            long r0 = r0 - r2
            r2 = 259200000(0xf731400, double:1.280618154E-315)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x00a0
            android.location.LocationManager r0 = r12.b     // Catch:{ Throwable -> 0x013c, SecurityException -> 0x00e8 }
            java.lang.String r1 = "gps"
            java.lang.String r2 = "force_xtra_injection"
            r3 = 0
            r0.sendExtraCommand(r1, r2, r3)     // Catch:{ Throwable -> 0x013c, SecurityException -> 0x00e8 }
            long r0 = com.loc.dr.b()     // Catch:{ Throwable -> 0x013c, SecurityException -> 0x00e8 }
            q = r0     // Catch:{ Throwable -> 0x013c, SecurityException -> 0x00e8 }
            android.content.Context r0 = r12.z     // Catch:{ Throwable -> 0x013c, SecurityException -> 0x00e8 }
            java.lang.String r1 = "pref"
            java.lang.String r2 = "lagt"
            long r4 = q     // Catch:{ Throwable -> 0x013c, SecurityException -> 0x00e8 }
            com.loc.dq.a(r0, r1, r2, r4)     // Catch:{ Throwable -> 0x013c, SecurityException -> 0x00e8 }
        L_0x00a0:
            com.amap.api.location.AMapLocationClientOption r0 = r12.c     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            com.amap.api.location.AMapLocationClientOption$AMapLocationMode r0 = r0.getLocationMode()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            com.amap.api.location.AMapLocationClientOption$AMapLocationMode r1 = com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Device_Sensors     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            boolean r0 = r0.equals(r1)     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            if (r0 == 0) goto L_0x0113
            com.amap.api.location.AMapLocationClientOption r0 = r12.c     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            float r0 = r0.getDeviceModeDistanceFilter()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            int r0 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r0 <= 0) goto L_0x0113
            android.location.LocationManager r0 = r12.b     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            java.lang.String r1 = "gps"
            com.amap.api.location.AMapLocationClientOption r2 = r12.c     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            long r2 = r2.getInterval()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            com.amap.api.location.AMapLocationClientOption r4 = r12.c     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            float r4 = r4.getDeviceModeDistanceFilter()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            android.location.LocationListener r5 = r12.x     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            r0.requestLocationUpdates(r1, r2, r4, r5, r6)     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
        L_0x00ce:
            android.location.LocationManager r0 = r12.b     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            android.location.GpsStatus$Listener r1 = r12.E     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            r0.addGpsStatusListener(r1)     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            r1 = 8
            r2 = 14
            java.lang.String r3 = "no enough satellites#1401"
            com.amap.api.location.AMapLocationClientOption r0 = r12.c     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            long r4 = r0.getHttpTimeOut()     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            r0 = r12
            r0.a(r1, r2, r3, r4)     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            goto L_0x0025
        L_0x00e8:
            r0 = move-exception
            r1 = 0
            r12.s = r1
            r1 = 2121(0x849, float:2.972E-42)
            com.loc.Cdo.a(r8, r1)
            r1 = 2
            r2 = 12
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r3 = "#1201"
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r3 = r0.toString()
            r0 = r12
            r4 = r10
            r0.a(r1, r2, r3, r4)
            goto L_0x0025
        L_0x0113:
            android.location.LocationManager r0 = r12.b     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            java.lang.String r1 = "gps"
            r2 = 900(0x384, double:4.447E-321)
            r4 = 0
            android.location.LocationListener r5 = r12.x     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            r0.requestLocationUpdates(r1, r2, r4, r5, r6)     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            goto L_0x00ce
        L_0x0121:
            r0 = move-exception
            java.lang.String r1 = "GpsLocation"
            java.lang.String r2 = "requestLocationUpdates part2"
            com.loc.di.a(r0, r1, r2)
            goto L_0x0025
        L_0x012d:
            r1 = 8
            r2 = 14
            java.lang.String r3 = "no gps provider#1402"
            r4 = 0
            r0 = r12
            r0.a(r1, r2, r3, r4)     // Catch:{ SecurityException -> 0x00e8, Throwable -> 0x0121 }
            goto L_0x0025
        L_0x013c:
            r0 = move-exception
            goto L_0x00a0
        L_0x013f:
            r0 = move-exception
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.g.a(com.amap.api.location.AMapLocationClientOption):void");
    }

    public final void b(AMapLocationClientOption aMapLocationClientOption) {
        if (aMapLocationClientOption == null) {
            aMapLocationClientOption = new AMapLocationClientOption();
        }
        this.c = aMapLocationClientOption;
        if (!(this.c.getLocationMode() == AMapLocationMode.Device_Sensors || this.a == null)) {
            this.a.removeMessages(8);
        }
        if (this.r != this.c.getGeoLanguage()) {
            synchronized (this.o) {
                this.y = null;
            }
        }
        this.r = this.c.getGeoLanguage();
    }

    public final boolean b() {
        return dr.c() - this.d <= 2800;
    }

    public final void c() {
        this.w = 0;
    }

    @SuppressLint({"NewApi"})
    public final int d() {
        if (this.b == null || !a(this.b)) {
            return 1;
        }
        if (VERSION.SDK_INT >= 19) {
            int i2 = Secure.getInt(this.z.getContentResolver(), "location_mode", 0);
            if (i2 == 0) {
                return 2;
            }
            if (i2 == 2) {
                return 3;
            }
        } else if (!this.b.isProviderEnabled("gps")) {
            return 2;
        }
        return !this.s ? 4 : 0;
    }

    public final int e() {
        return this.C;
    }

    public final boolean f() {
        return dr.c() - this.d > 300000;
    }
}
