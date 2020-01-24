package com.loc;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: SDKLogHandler */
public final class aj extends ag implements UncaughtExceptionHandler {
    private static ExecutorService e;
    private static Set<Integer> f = Collections.synchronizedSet(new HashSet());
    private static WeakReference<Context> g;
    private static final ThreadFactory h = new ThreadFactory() {
        private final AtomicInteger a = new AtomicInteger(1);

        public final Thread newThread(Runnable runnable) {
            return new Thread(runnable, "pama#" + this.a.getAndIncrement());
        }
    };
    /* access modifiers changed from: private */
    public Context d;
    private List i;

    private aj(Context context) {
        this.d = context;
        try {
            this.b = Thread.getDefaultUncaughtExceptionHandler();
            if (this.b == null) {
                Thread.setDefaultUncaughtExceptionHandler(this);
                this.c = true;
                return;
            }
            String obj = this.b.toString();
            if (obj.startsWith("com.amap.apis.utils.core.dynamiccore") || (obj.indexOf("com.amap.api") == -1 && obj.indexOf("com.loc") == -1)) {
                Thread.setDefaultUncaughtExceptionHandler(this);
                this.c = true;
                return;
            }
            this.c = false;
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
        }
    }

    public static synchronized aj a(Context context, v vVar) throws k {
        aj ajVar;
        synchronized (aj.class) {
            if (vVar == null) {
                throw new k("sdk info is null");
            } else if (vVar.a() == null || "".equals(vVar.a())) {
                throw new k("sdk name is invalid");
            } else {
                try {
                    new al().a(context);
                    if (!f.add(Integer.valueOf(vVar.hashCode()))) {
                        ajVar = (aj) ag.a;
                    } else {
                        if (ag.a == null) {
                            ag.a = new aj(context);
                        } else {
                            ag.a.c = false;
                        }
                        ag.a.a(context, vVar, ag.a.c);
                        ajVar = (aj) ag.a;
                    }
                } catch (Throwable th) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
        return ajVar;
    }

    public static void a(v vVar, String str, k kVar) {
        if (kVar != null) {
            a(vVar, str, kVar.c(), kVar.d(), kVar.b());
        }
    }

    public static void a(v vVar, String str, String str2, String str3, String str4) {
        try {
            if (ag.a != null) {
                StringBuilder sb = new StringBuilder("path:");
                sb.append(str).append(",type:").append(str2).append(",gsid:").append(str3).append(",code:").append(str4);
                ag.a.a(vVar, sb.toString(), "networkError");
            }
        } catch (Throwable th) {
        }
    }

    public static synchronized void b() {
        synchronized (aj.class) {
            try {
                if (e != null) {
                    e.shutdown();
                }
                bh.a();
            } catch (Throwable th) {
                ThrowableExtension.printStackTrace(th);
            }
            try {
                if (!(ag.a == null || Thread.getDefaultUncaughtExceptionHandler() != ag.a || ag.a.b == null)) {
                    Thread.setDefaultUncaughtExceptionHandler(ag.a.b);
                }
                ag.a = null;
            } catch (Throwable th2) {
                ThrowableExtension.printStackTrace(th2);
            }
        }
        return;
    }

    public static void b(v vVar, String str, String str2) {
        try {
            if (ag.a != null) {
                ag.a.a(vVar, str, str2);
            }
        } catch (Throwable th) {
        }
    }

    public static void b(Throwable th, String str, String str2) {
        try {
            if (ag.a != null) {
                ag.a.a(th, 1, str, str2);
            }
        } catch (Throwable th2) {
        }
    }

    public static void c() {
        if (g != null && g.get() != null) {
            ah.b((Context) g.get());
        } else if (ag.a != null) {
            ag.a.a();
        }
    }

    public static synchronized ExecutorService d() {
        ExecutorService executorService;
        synchronized (aj.class) {
            try {
                if (e == null || e.isShutdown()) {
                    e = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(256), h);
                }
            } catch (Throwable th) {
                ThrowableExtension.printStackTrace(th);
            }
            executorService = e;
        }
        return executorService;
    }

    /* access modifiers changed from: protected */
    public final void a() {
        ah.b(this.d);
    }

    /* access modifiers changed from: protected */
    public final void a(final Context context, final v vVar, final boolean z) {
        try {
            ExecutorService d2 = d();
            if (d2 != null && !d2.isShutdown()) {
                d2.submit(new Runnable() {
                    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final void run() {
                        /*
                            r4 = this;
                            android.os.Looper r1 = android.os.Looper.getMainLooper()     // Catch:{ Throwable -> 0x0024 }
                            monitor-enter(r1)     // Catch:{ Throwable -> 0x0024 }
                            com.loc.at r0 = new com.loc.at     // Catch:{ all -> 0x0021 }
                            android.content.Context r2 = r3     // Catch:{ all -> 0x0021 }
                            r3 = 1
                            r0.<init>(r2, r3)     // Catch:{ all -> 0x0021 }
                            com.loc.v r2 = r4     // Catch:{ all -> 0x0021 }
                            r0.a(r2)     // Catch:{ all -> 0x0021 }
                            monitor-exit(r1)     // Catch:{ all -> 0x0021 }
                            boolean r0 = r5     // Catch:{ Throwable -> 0x0024 }
                            if (r0 == 0) goto L_0x0020
                            com.loc.aj r0 = com.loc.aj.this     // Catch:{ Throwable -> 0x0024 }
                            android.content.Context r0 = r0.d     // Catch:{ Throwable -> 0x0024 }
                            com.loc.ak.a(r0)     // Catch:{ Throwable -> 0x0024 }
                        L_0x0020:
                            return
                        L_0x0021:
                            r0 = move-exception
                            monitor-exit(r1)     // Catch:{ Throwable -> 0x0024 }
                            throw r0     // Catch:{ Throwable -> 0x0024 }
                        L_0x0024:
                            r0 = move-exception
                            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
                            goto L_0x0020
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.loc.aj.AnonymousClass1.run():void");
                    }
                });
            }
        } catch (RejectedExecutionException e2) {
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
        }
    }

    /* access modifiers changed from: protected */
    public final void a(v vVar, String str, String str2) {
        ak.b(vVar, this.d, str2, str);
    }

    /* access modifiers changed from: protected */
    public final void a(Throwable th, int i2, String str, String str2) {
        ak.a(this.d, th, i2, str, str2);
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        int i2 = 0;
        while (i2 < this.i.size() && i2 < 10) {
            try {
                this.i.get(i2);
                i2++;
            } catch (Throwable th2) {
            }
        }
        if (th != null) {
            a(th, 0, null, null);
            if (this.b != null) {
                try {
                    Thread.setDefaultUncaughtExceptionHandler(this.b);
                } catch (Throwable th3) {
                }
                this.b.uncaughtException(thread, th);
            }
        }
    }
}
