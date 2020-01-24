package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: DynamicExceptionHandler */
public final class ax implements UncaughtExceptionHandler {
    private static ax a;
    private UncaughtExceptionHandler b = Thread.getDefaultUncaughtExceptionHandler();
    private Context c;
    private v d;

    private ax(Context context, v vVar) {
        this.c = context.getApplicationContext();
        this.d = vVar;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    static synchronized ax a(Context context, v vVar) {
        ax axVar;
        synchronized (ax.class) {
            if (a == null) {
                a = new ax(context, vVar);
            }
            axVar = a;
        }
        return axVar;
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        String a2 = w.a(th);
        try {
            if (!TextUtils.isEmpty(a2)) {
                if ((a2.contains("amapdynamic") || a2.contains("admic")) && a2.contains("com.amap.api")) {
                    ao aoVar = new ao(this.c, ay.b());
                    if (a2.contains("loc")) {
                        aw.a(aoVar, this.c, "loc");
                    }
                    if (a2.contains("navi")) {
                        aw.a(aoVar, this.c, "navi");
                    }
                    if (a2.contains("sea")) {
                        aw.a(aoVar, this.c, "sea");
                    }
                    if (a2.contains("2dmap")) {
                        aw.a(aoVar, this.c, "2dmap");
                    }
                    if (a2.contains("3dmap")) {
                        aw.a(aoVar, this.c, "3dmap");
                    }
                } else if (a2.contains("com.autonavi.aps.amapapi.offline")) {
                    aw.a(new ao(this.c, ay.b()), this.c, "OfflineLocation");
                } else if (a2.contains("com.data.carrier_v4")) {
                    aw.a(new ao(this.c, ay.b()), this.c, "Collection");
                } else if (a2.contains("com.autonavi.aps.amapapi.httpdns") || a2.contains("com.autonavi.httpdns")) {
                    aw.a(new ao(this.c, ay.b()), this.c, "HttpDNS");
                } else if (a2.contains("com.amap.api.aiunet")) {
                    aw.a(new ao(this.c, ay.b()), this.c, "aiu");
                } else if (a2.contains("com.amap.co") || a2.contains("com.amap.opensdk.co") || a2.contains("com.amap.location")) {
                    aw.a(new ao(this.c, ay.b()), this.c, "co");
                }
            }
        } catch (Throwable th2) {
            ag.a(th2, "DynamicExceptionHandler", "uncaughtException");
        }
        if (this.b != null) {
            this.b.uncaughtException(thread, th);
        }
    }
}
