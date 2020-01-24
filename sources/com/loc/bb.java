package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: LoaderFactory */
public final class bb {
    private static final bb a = new bb();
    private static final ThreadFactory d = new b();
    private final Map<String, bd> b = new HashMap();
    private final Map<String, a> c = new HashMap();
    private ExecutorService e = null;

    /* compiled from: LoaderFactory */
    class a {
        volatile boolean a = false;
        volatile boolean b = false;

        a() {
        }
    }

    /* compiled from: LoaderFactory */
    static class b implements ThreadFactory {
        private final AtomicInteger a = new AtomicInteger(1);

        b() {
        }

        public final Thread newThread(Runnable runnable) {
            return new Thread(runnable, "amapD#" + this.a.getAndIncrement());
        }
    }

    private bb() {
    }

    public static bb b() {
        return a;
    }

    private static boolean b(v vVar) {
        return vVar != null && !TextUtils.isEmpty(vVar.b()) && !TextUtils.isEmpty(vVar.a());
    }

    /* access modifiers changed from: 0000 */
    public final a a(v vVar) {
        a aVar;
        synchronized (this.c) {
            if (!b(vVar)) {
                aVar = null;
            } else {
                String a2 = vVar.a();
                aVar = (a) this.c.get(a2);
                if (aVar == null) {
                    try {
                        a aVar2 = new a();
                        try {
                            this.c.put(a2, aVar2);
                            aVar = aVar2;
                        } catch (Throwable th) {
                            aVar = aVar2;
                        }
                    } catch (Throwable th2) {
                    }
                }
            }
        }
        return aVar;
    }

    /* access modifiers changed from: 0000 */
    public final bd a(Context context, v vVar) throws Exception {
        bd bdVar;
        if (!b(vVar) || context == null) {
            return null;
        }
        String a2 = vVar.a();
        synchronized (this.b) {
            bdVar = (bd) this.b.get(a2);
            if (bdVar == null) {
                try {
                    bd bfVar = new bf(context.getApplicationContext(), vVar);
                    try {
                        this.b.put(a2, bfVar);
                        ax.a(context, vVar);
                        bdVar = bfVar;
                    } catch (Throwable th) {
                        bdVar = bfVar;
                    }
                } catch (Throwable th2) {
                }
            }
        }
        return bdVar;
    }

    public final ExecutorService a() {
        try {
            if (this.e == null || this.e.isShutdown()) {
                this.e = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(128), d);
            }
        } catch (Throwable th) {
        }
        return this.e;
    }
}
