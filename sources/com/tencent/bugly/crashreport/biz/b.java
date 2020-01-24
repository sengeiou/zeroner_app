package com.tencent.bugly.crashreport.biz;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import java.util.List;

/* compiled from: BUGLY */
public class b {
    public static boolean a;
    public static a b;
    /* access modifiers changed from: private */
    public static int c = 10;
    /* access modifiers changed from: private */
    public static long d = 300000;
    /* access modifiers changed from: private */
    public static long e = 30000;
    /* access modifiers changed from: private */
    public static long f = 0;
    /* access modifiers changed from: private */
    public static int g;
    /* access modifiers changed from: private */
    public static long h;
    /* access modifiers changed from: private */
    public static long i;
    /* access modifiers changed from: private */
    public static long j = 0;
    private static ActivityLifecycleCallbacks k = null;
    /* access modifiers changed from: private */
    public static Class<?> l = null;
    /* access modifiers changed from: private */
    public static boolean m = true;

    static /* synthetic */ int g() {
        int i2 = g;
        g = i2 + 1;
        return i2;
    }

    private static void m() {
        StackTraceElement[] stackTrace;
        a b2 = a.b();
        if (b2 != null) {
            String str = null;
            boolean z = false;
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (stackTraceElement.getMethodName().equals("onCreate")) {
                    str = stackTraceElement.getClassName();
                }
                if (stackTraceElement.getClassName().equals("android.app.Activity")) {
                    z = true;
                }
            }
            if (str == null) {
                str = "unknown";
            } else if (z) {
                b2.a(true);
            } else {
                str = "background";
            }
            b2.u = str;
        }
    }

    private static boolean b(Context context) {
        a a2 = a.a(context);
        List a3 = b.a(a2.e);
        if (a3 == null) {
            return true;
        }
        for (int i2 = 0; i2 < a3.size(); i2++) {
            UserInfoBean userInfoBean = (UserInfoBean) a3.get(i2);
            if (userInfoBean.n.equals(a2.o) && userInfoBean.b == 1) {
                long b2 = ap.b();
                if (b2 <= 0) {
                    return true;
                }
                if (userInfoBean.e >= b2) {
                    if (userInfoBean.f > 0) {
                        return false;
                    }
                    b.b();
                    return false;
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void c(Context context, BuglyStrategy buglyStrategy) {
        boolean z;
        boolean z2 = false;
        boolean z3 = true;
        if (buglyStrategy != null) {
            boolean recordUserInfoOnceADay = buglyStrategy.recordUserInfoOnceADay();
            z3 = buglyStrategy.isEnableUserInfo();
            z = recordUserInfoOnceADay;
        } else {
            z = false;
        }
        if (!z) {
            z2 = z3;
        } else if (!b(context)) {
            return;
        }
        m();
        if (z2) {
            c(context);
        }
        if (m) {
            n();
            b.a();
            b.b(21600000);
        }
    }

    public static void a(final Context context, final BuglyStrategy buglyStrategy) {
        long j2;
        if (!a) {
            m = a.a(context).g;
            b = new a(context, m);
            a = true;
            if (buglyStrategy != null) {
                l = buglyStrategy.getUserInfoActivity();
                j2 = buglyStrategy.getAppReportDelay();
            } else {
                j2 = 0;
            }
            if (j2 <= 0) {
                c(context, buglyStrategy);
            } else {
                am.a().a(new Runnable() {
                    public void run() {
                        b.c(context, buglyStrategy);
                    }
                }, j2);
            }
        }
    }

    public static void a(long j2) {
        if (j2 < 0) {
            j2 = com.tencent.bugly.crashreport.common.strategy.a.a().c().q;
        }
        f = j2;
    }

    public static void a(StrategyBean strategyBean, boolean z) {
        if (b != null && !z) {
            b.b();
        }
        if (strategyBean != null) {
            if (strategyBean.q > 0) {
                e = strategyBean.q;
            }
            if (strategyBean.w > 0) {
                c = strategyBean.w;
            }
            if (strategyBean.x > 0) {
                d = strategyBean.x;
            }
        }
    }

    private static void n() {
        i = System.currentTimeMillis();
        b.a(1, false, 0);
        an.a("[session] launch app, new start", new Object[0]);
    }

    public static void a() {
        if (b != null) {
            b.a(2, false, 0);
        }
    }

    /* access modifiers changed from: private */
    public static String b(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(ap.a());
        sb.append("  ");
        sb.append(str);
        sb.append("  ");
        sb.append(str2);
        sb.append("\n");
        return sb.toString();
    }

    @TargetApi(14)
    private static void c(Context context) {
        Application application = null;
        if (VERSION.SDK_INT >= 14) {
            if (context.getApplicationContext() instanceof Application) {
                application = (Application) context.getApplicationContext();
            }
            if (application != null) {
                try {
                    if (k == null) {
                        k = new ActivityLifecycleCallbacks() {
                            public void onActivityStopped(Activity activity) {
                            }

                            public void onActivityStarted(Activity activity) {
                            }

                            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                            }

                            public void onActivityResumed(Activity activity) {
                                String str = "unknown";
                                if (activity != null) {
                                    str = activity.getClass().getName();
                                }
                                if (b.l == null || b.l.getName().equals(str)) {
                                    an.c(">>> %s onResumed <<<", str);
                                    a b = a.b();
                                    if (b != null) {
                                        b.J.add(b.b(str, "onResumed"));
                                        b.a(true);
                                        b.u = str;
                                        b.v = System.currentTimeMillis();
                                        b.y = b.v - b.i;
                                        long d = b.v - b.h;
                                        if (d > (b.f > 0 ? b.f : b.e)) {
                                            b.d();
                                            b.g();
                                            an.a("[session] launch app one times (app in background %d seconds and over %d seconds)", Long.valueOf(d / 1000), Long.valueOf(b.e / 1000));
                                            if (b.g % b.c == 0) {
                                                b.b.a(4, b.m, 0);
                                                return;
                                            }
                                            b.b.a(4, false, 0);
                                            long currentTimeMillis = System.currentTimeMillis();
                                            if (currentTimeMillis - b.j > b.d) {
                                                b.j = currentTimeMillis;
                                                an.a("add a timer to upload hot start user info", new Object[0]);
                                                if (b.m) {
                                                    b.b.a(b.d);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            public void onActivityPaused(Activity activity) {
                                String str = "unknown";
                                if (activity != null) {
                                    str = activity.getClass().getName();
                                }
                                if (b.l == null || b.l.getName().equals(str)) {
                                    an.c(">>> %s onPaused <<<", str);
                                    a b = a.b();
                                    if (b != null) {
                                        b.J.add(b.b(str, "onPaused"));
                                        b.a(false);
                                        b.w = System.currentTimeMillis();
                                        b.x = b.w - b.v;
                                        b.h = b.w;
                                        if (b.x < 0) {
                                            b.x = 0;
                                        }
                                        if (activity != null) {
                                            b.u = "background";
                                        } else {
                                            b.u = "unknown";
                                        }
                                    }
                                }
                            }

                            public void onActivityDestroyed(Activity activity) {
                                String str = "unknown";
                                if (activity != null) {
                                    str = activity.getClass().getName();
                                }
                                if (b.l == null || b.l.getName().equals(str)) {
                                    an.c(">>> %s onDestroyed <<<", str);
                                    a b = a.b();
                                    if (b != null) {
                                        b.J.add(b.b(str, "onDestroyed"));
                                    }
                                }
                            }

                            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                                String str = "unknown";
                                if (activity != null) {
                                    str = activity.getClass().getName();
                                }
                                if (b.l == null || b.l.getName().equals(str)) {
                                    an.c(">>> %s onCreated <<<", str);
                                    a b = a.b();
                                    if (b != null) {
                                        b.J.add(b.b(str, "onCreated"));
                                    }
                                }
                            }
                        };
                    }
                    application.registerActivityLifecycleCallbacks(k);
                } catch (Exception e2) {
                    if (!an.a(e2)) {
                        ThrowableExtension.printStackTrace(e2);
                    }
                }
            }
        }
    }

    @TargetApi(14)
    private static void d(Context context) {
        Application application = null;
        if (VERSION.SDK_INT >= 14) {
            if (context.getApplicationContext() instanceof Application) {
                application = (Application) context.getApplicationContext();
            }
            if (application != null) {
                try {
                    if (k != null) {
                        application.unregisterActivityLifecycleCallbacks(k);
                    }
                } catch (Exception e2) {
                    if (!an.a(e2)) {
                        ThrowableExtension.printStackTrace(e2);
                    }
                }
            }
        }
    }

    public static void a(Context context) {
        if (a && context != null) {
            d(context);
            a = false;
        }
    }
}
