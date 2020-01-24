package com.tencent.bugly.crashreport.common.strategy;

import android.content.Context;
import android.os.Parcelable;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.crashreport.biz.b;
import com.tencent.bugly.proguard.ad;
import com.tencent.bugly.proguard.ae;
import com.tencent.bugly.proguard.ag;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.bg;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public class a {
    public static int a = 1000;
    public static long b = 259200000;
    private static a c = null;
    /* access modifiers changed from: private */
    public static String i = null;
    private final List<com.tencent.bugly.a> d;
    private final am e;
    private final StrategyBean f;
    /* access modifiers changed from: private */
    public StrategyBean g = null;
    /* access modifiers changed from: private */
    public Context h;

    protected a(Context context, List<com.tencent.bugly.a> list) {
        this.h = context;
        this.f = new StrategyBean();
        this.d = list;
        this.e = am.a();
    }

    public static synchronized a a(Context context, List<com.tencent.bugly.a> list) {
        a aVar;
        synchronized (a.class) {
            if (c == null) {
                c = new a(context, list);
            }
            aVar = c;
        }
        return aVar;
    }

    public void a(long j) {
        this.e.a(new Thread() {
            public void run() {
                try {
                    Map a2 = ae.a().a(a.a, (ad) null, true);
                    if (a2 != null) {
                        byte[] bArr = (byte[]) a2.get("key_imei");
                        byte[] bArr2 = (byte[]) a2.get("key_ip");
                        if (bArr != null) {
                            com.tencent.bugly.crashreport.common.info.a.a(a.this.h).e(new String(bArr));
                        }
                        if (bArr2 != null) {
                            com.tencent.bugly.crashreport.common.info.a.a(a.this.h).d(new String(bArr2));
                        }
                    }
                    a.this.g = a.this.d();
                    if (a.this.g != null && !ap.a(a.i) && ap.c(a.i)) {
                        a.this.g.r = a.i;
                        a.this.g.s = a.i;
                    }
                } catch (Throwable th) {
                    if (!an.a(th)) {
                        ThrowableExtension.printStackTrace(th);
                    }
                }
                a.this.a(a.this.g, false);
            }
        }, j);
    }

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            aVar = c;
        }
        return aVar;
    }

    public synchronized boolean b() {
        return this.g != null;
    }

    public StrategyBean c() {
        if (this.g != null) {
            return this.g;
        }
        return this.f;
    }

    /* access modifiers changed from: protected */
    public void a(StrategyBean strategyBean, boolean z) {
        an.c("[Strategy] Notify %s", b.class.getName());
        b.a(strategyBean, z);
        for (com.tencent.bugly.a aVar : this.d) {
            try {
                an.c("[Strategy] Notify %s", aVar.getClass().getName());
                aVar.onServerStrategyChanged(strategyBean);
            } catch (Throwable th) {
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
    }

    public void a(bg bgVar) {
        if (bgVar != null) {
            if (this.g == null || bgVar.h != this.g.p) {
                StrategyBean strategyBean = new StrategyBean();
                strategyBean.g = bgVar.a;
                strategyBean.i = bgVar.c;
                strategyBean.h = bgVar.b;
                if (ap.a(i) || !ap.c(i)) {
                    if (ap.c(bgVar.d)) {
                        an.c("[Strategy] Upload url changes to %s", bgVar.d);
                        strategyBean.r = bgVar.d;
                    }
                    if (ap.c(bgVar.e)) {
                        an.c("[Strategy] Exception upload url changes to %s", bgVar.e);
                        strategyBean.s = bgVar.e;
                    }
                }
                if (bgVar.f != null && !ap.a(bgVar.f.a)) {
                    strategyBean.u = bgVar.f.a;
                }
                if (bgVar.h != 0) {
                    strategyBean.p = bgVar.h;
                }
                if (bgVar.g != null && bgVar.g.size() > 0) {
                    strategyBean.v = bgVar.g;
                    String str = (String) bgVar.g.get("B11");
                    if (str == null || !str.equals("1")) {
                        strategyBean.j = false;
                    } else {
                        strategyBean.j = true;
                    }
                    String str2 = (String) bgVar.g.get("B3");
                    if (str2 != null) {
                        strategyBean.y = Long.valueOf(str2).longValue();
                    }
                    strategyBean.q = (long) bgVar.l;
                    strategyBean.x = (long) bgVar.l;
                    String str3 = (String) bgVar.g.get("B27");
                    if (str3 != null && str3.length() > 0) {
                        try {
                            int parseInt = Integer.parseInt(str3);
                            if (parseInt > 0) {
                                strategyBean.w = parseInt;
                            }
                        } catch (Exception e2) {
                            if (!an.a(e2)) {
                                ThrowableExtension.printStackTrace(e2);
                            }
                        }
                    }
                    String str4 = (String) bgVar.g.get("B25");
                    if (str4 == null || !str4.equals("1")) {
                        strategyBean.l = false;
                    } else {
                        strategyBean.l = true;
                    }
                }
                an.a("[Strategy] enableCrashReport:%b, enableQuery:%b, enableUserInfo:%b, enableAnr:%b, enableBlock:%b, enableSession:%b, enableSessionTimer:%b, sessionOverTime:%d, enableCocos:%b, strategyLastUpdateTime:%d", Boolean.valueOf(strategyBean.g), Boolean.valueOf(strategyBean.i), Boolean.valueOf(strategyBean.h), Boolean.valueOf(strategyBean.j), Boolean.valueOf(strategyBean.k), Boolean.valueOf(strategyBean.n), Boolean.valueOf(strategyBean.o), Long.valueOf(strategyBean.q), Boolean.valueOf(strategyBean.l), Long.valueOf(strategyBean.p));
                this.g = strategyBean;
                ae.a().b(2);
                ag agVar = new ag();
                agVar.b = 2;
                agVar.a = strategyBean.e;
                agVar.e = strategyBean.f;
                agVar.g = ap.a((Parcelable) strategyBean);
                ae.a().a(agVar);
                a(strategyBean, true);
            }
        }
    }

    public StrategyBean d() {
        List a2 = ae.a().a(2);
        if (a2 != null && a2.size() > 0) {
            ag agVar = (ag) a2.get(0);
            if (agVar.g != null) {
                return (StrategyBean) ap.a(agVar.g, StrategyBean.CREATOR);
            }
        }
        return null;
    }
}
