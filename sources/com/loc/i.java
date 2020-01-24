package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import java.util.List;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.json.JSONObject;

/* compiled from: LastLocationManager */
public final class i {
    static cz b = null;
    static ao e = null;
    static long g = 0;
    String a = null;
    cz c = null;
    cz d = null;
    long f = 0;
    boolean h = false;
    private Context i;

    public i(Context context) {
        this.i = context.getApplicationContext();
    }

    private void e() {
        if (b == null || dr.c() - g > 180000) {
            cz f2 = f();
            g = dr.c();
            if (f2 != null && dr.a(f2.a())) {
                b = f2;
            }
        }
    }

    private cz f() {
        cz czVar;
        Throwable th;
        String str;
        byte[] b2;
        byte[] d2;
        String str2 = null;
        if (this.i == null) {
            return null;
        }
        a();
        try {
            if (e == null) {
                return null;
            }
            List b3 = e.b("_id=1", cz.class);
            if (b3 == null || b3.size() <= 0) {
                czVar = null;
            } else {
                czVar = (cz) b3.get(0);
                try {
                    byte[] b4 = q.b(czVar.c());
                    if (b4 != null && b4.length > 0) {
                        byte[] d3 = cx.d(b4, this.a);
                        if (d3 != null && d3.length > 0) {
                            str = new String(d3, "UTF-8");
                            b2 = q.b(czVar.b());
                            if (b2 != null && b2.length > 0) {
                                d2 = cx.d(b2, this.a);
                                if (d2 != null && d2.length > 0) {
                                    str2 = new String(d2, "UTF-8");
                                }
                            }
                            czVar.a(str2);
                            str2 = str;
                        }
                    }
                    str = null;
                    b2 = q.b(czVar.b());
                    d2 = cx.d(b2, this.a);
                    str2 = new String(d2, "UTF-8");
                    czVar.a(str2);
                    str2 = str;
                } catch (Throwable th2) {
                    th = th2;
                    di.a(th, "LastLocationManager", "readLastFix");
                    return czVar;
                }
            }
            if (TextUtils.isEmpty(str2)) {
                return czVar;
            }
            AMapLocation aMapLocation = new AMapLocation("");
            di.a(aMapLocation, new JSONObject(str2));
            if (!dr.b(aMapLocation)) {
                return czVar;
            }
            czVar.a(aMapLocation);
            return czVar;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            czVar = null;
            th = th4;
            di.a(th, "LastLocationManager", "readLastFix");
            return czVar;
        }
    }

    public final AMapLocation a(AMapLocation aMapLocation, String str, long j) {
        boolean z = true;
        if (aMapLocation == null || aMapLocation.getErrorCode() == 0 || aMapLocation.getLocationType() == 1 || aMapLocation.getErrorCode() == 7) {
            return aMapLocation;
        }
        try {
            e();
            if (b == null || b.a() == null) {
                return aMapLocation;
            }
            if (TextUtils.isEmpty(str)) {
                long c2 = dr.c() - b.d();
                if (c2 < 0 || c2 > j) {
                    z = false;
                }
                aMapLocation.setTrustedLevel(3);
            } else {
                if (!dr.a(b.b(), str)) {
                    z = false;
                }
                aMapLocation.setTrustedLevel(2);
            }
            if (!z) {
                return aMapLocation;
            }
            AMapLocation a2 = b.a();
            try {
                a2.setLocationType(9);
                a2.setFixLastLocation(true);
                a2.setLocationDetail(aMapLocation.getLocationDetail());
                return a2;
            } catch (Throwable th) {
                aMapLocation = a2;
                th = th;
                di.a(th, "LastLocationManager", "fixLastLocation");
                return aMapLocation;
            }
        } catch (Throwable th2) {
            th = th2;
            di.a(th, "LastLocationManager", "fixLastLocation");
            return aMapLocation;
        }
    }

    public final void a() {
        if (!this.h) {
            try {
                if (this.a == null) {
                    this.a = cx.a(MessageDigestAlgorithms.MD5, p.v(this.i));
                }
                if (e == null) {
                    e = new ao(this.i, ao.a(da.class), dr.j());
                }
            } catch (Throwable th) {
                di.a(th, "LastLocationManager", "<init>:DBOperation");
            }
            this.h = true;
        }
    }

    public final boolean a(AMapLocation aMapLocation, String str) {
        if (this.i == null || aMapLocation == null || !dr.a(aMapLocation) || aMapLocation.getLocationType() == 2 || aMapLocation.isMock() || aMapLocation.isFixLastLocation()) {
            return false;
        }
        cz czVar = new cz();
        czVar.a(aMapLocation);
        if (aMapLocation.getLocationType() == 1) {
            czVar.a((String) null);
        } else {
            czVar.a(str);
        }
        try {
            b = czVar;
            g = dr.c();
            this.c = czVar;
            return (this.d == null || dr.a(this.d.a(), czVar.a()) > 500.0f) && dr.c() - this.f > 30000;
        } catch (Throwable th) {
            di.a(th, "LastLocationManager", "setLastFix");
            return false;
        }
    }

    public final AMapLocation b() {
        e();
        if (b != null && dr.a(b.a())) {
            return b.a();
        }
        return null;
    }

    public final void c() {
        try {
            d();
            this.f = 0;
            this.h = false;
            this.c = null;
            this.d = null;
        } catch (Throwable th) {
            di.a(th, "LastLocationManager", "destroy");
        }
    }

    public final void d() {
        String str;
        String str2 = null;
        try {
            a();
            if (this.c != null && dr.a(this.c.a()) && e != null && this.c != this.d && this.c.d() == 0) {
                String str3 = this.c.a().toStr();
                String b2 = this.c.b();
                this.d = this.c;
                if (!TextUtils.isEmpty(str3)) {
                    str = q.b(cx.c(str3.getBytes("UTF-8"), this.a));
                    if (!TextUtils.isEmpty(b2)) {
                        str2 = q.b(cx.c(b2.getBytes("UTF-8"), this.a));
                    }
                } else {
                    str = null;
                }
                if (!TextUtils.isEmpty(str)) {
                    cz czVar = new cz();
                    czVar.b(str);
                    czVar.a(dr.c());
                    czVar.a(str2);
                    e.a((Object) czVar, "_id=1");
                    this.f = dr.c();
                    if (b != null) {
                        b.a(dr.c());
                    }
                }
            }
        } catch (Throwable th) {
            di.a(th, "LastLocationManager", "saveLastFix");
        }
    }
}
