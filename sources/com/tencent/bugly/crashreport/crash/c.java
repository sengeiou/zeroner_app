package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.crashreport.crash.anr.b;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.proguard.ae;
import com.tencent.bugly.proguard.ag;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: BUGLY */
public class c {
    public static int a = 0;
    public static boolean b = false;
    public static int c = 2;
    public static boolean d = true;
    public static int e = 20480;
    public static int f = 20480;
    public static long g = 604800000;
    public static String h = null;
    public static boolean i = false;
    public static String j = null;
    public static int k = 5000;
    public static boolean l = true;
    public static boolean m = false;
    public static String n = null;
    public static String o = null;
    private static c v;
    public final b p;
    public final a q = a.a();
    public final am r;
    public BuglyStrategy.a s;
    public f t;
    /* access modifiers changed from: private */
    public final Context u;
    /* access modifiers changed from: private */
    public final e w;
    private final NativeCrashHandler x;
    private final b y;
    private Boolean z;

    protected c(int i2, Context context, am amVar, boolean z2, BuglyStrategy.a aVar, f fVar, String str) {
        a = i2;
        Context a2 = ap.a(context);
        this.u = a2;
        this.r = amVar;
        this.s = aVar;
        this.t = fVar;
        ak a3 = ak.a();
        ae a4 = ae.a();
        this.p = new b(i2, a2, a3, a4, this.q, aVar, fVar);
        com.tencent.bugly.crashreport.common.info.a a5 = com.tencent.bugly.crashreport.common.info.a.a(a2);
        this.w = new e(a2, this.p, this.q, a5);
        this.x = NativeCrashHandler.getInstance(a2, a5, this.p, this.q, amVar, z2, str);
        a5.L = this.x;
        this.y = new b(a2, this.q, a5, amVar, a4, this.p, aVar);
    }

    public static synchronized c a(int i2, Context context, boolean z2, BuglyStrategy.a aVar, f fVar, String str) {
        c cVar;
        synchronized (c.class) {
            if (v == null) {
                v = new c(i2, context, am.a(), z2, aVar, fVar, str);
            }
            cVar = v;
        }
        return cVar;
    }

    public static synchronized c a() {
        c cVar;
        synchronized (c.class) {
            cVar = v;
        }
        return cVar;
    }

    public void a(StrategyBean strategyBean) {
        this.w.a(strategyBean);
        this.x.onStrategyChanged(strategyBean);
        this.y.a(strategyBean);
        a(3000);
    }

    public boolean b() {
        Boolean bool = this.z;
        if (bool != null) {
            return bool.booleanValue();
        }
        String str = com.tencent.bugly.crashreport.common.info.a.b().e;
        List<ag> a2 = ae.a().a(1);
        ArrayList arrayList = new ArrayList();
        if (a2 == null || a2.size() <= 0) {
            this.z = Boolean.valueOf(false);
            return false;
        }
        for (ag agVar : a2) {
            if (str.equals(agVar.c)) {
                this.z = Boolean.valueOf(true);
                arrayList.add(agVar);
            }
        }
        if (arrayList.size() > 0) {
            ae.a().a((List<ag>) arrayList);
        }
        return true;
    }

    public synchronized void c() {
        f();
        h();
        i();
    }

    public synchronized void d() {
        e();
        g();
        j();
    }

    public void e() {
        this.w.b();
    }

    public void f() {
        this.w.a();
    }

    public void g() {
        this.x.setUserOpened(false);
    }

    public void h() {
        this.x.setUserOpened(true);
    }

    public void i() {
        this.y.b(true);
    }

    public void j() {
        this.y.b(false);
    }

    public synchronized void a(boolean z2, boolean z3, boolean z4) {
        this.x.testNativeCrash(z2, z3, z4);
    }

    public synchronized void k() {
        this.y.g();
    }

    public boolean l() {
        return this.y.a();
    }

    public void a(Thread thread, Throwable th, boolean z2, String str, byte[] bArr, boolean z3) {
        final boolean z4 = z2;
        final Thread thread2 = thread;
        final Throwable th2 = th;
        final String str2 = str;
        final byte[] bArr2 = bArr;
        final boolean z5 = z3;
        this.r.a(new Runnable() {
            public void run() {
                try {
                    an.c("post a throwable %b", Boolean.valueOf(z4));
                    c.this.w.b(thread2, th2, false, str2, bArr2);
                    if (z5) {
                        an.a("clear user datas", new Object[0]);
                        com.tencent.bugly.crashreport.common.info.a.a(c.this.u).C();
                    }
                } catch (Throwable th) {
                    if (!an.b(th)) {
                        ThrowableExtension.printStackTrace(th);
                    }
                    an.e("java catch error: %s", th2.toString());
                }
            }
        });
    }

    public void a(CrashDetailBean crashDetailBean) {
        this.p.e(crashDetailBean);
    }

    public void a(long j2) {
        am.a().a(new Thread() {
            public void run() {
                List list;
                if (ap.a(c.this.u, "local_crash_lock", 10000)) {
                    List a2 = c.this.p.a();
                    if (a2 != null && a2.size() > 0) {
                        int size = a2.size();
                        if (((long) size) > 100) {
                            list = new ArrayList();
                            Collections.sort(a2);
                            for (int i = 0; ((long) i) < 100; i++) {
                                list.add(a2.get((size - 1) - i));
                            }
                        } else {
                            list = a2;
                        }
                        c.this.p.a(list, 0, false, false, false);
                    }
                    ap.c(c.this.u, "local_crash_lock");
                }
            }
        }, j2);
    }

    public void b(long j2) {
        this.x.checkUploadRecordCrash(j2);
    }
}
