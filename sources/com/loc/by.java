package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Random;

/* compiled from: StatisticsManager */
public class by {
    /* access modifiers changed from: private */
    public static WeakReference<bs> a;

    public static void a(final Context context) {
        aj.d().submit(new Runnable() {
            public final void run() {
                try {
                    bs a2 = bz.a(by.a);
                    bz.a(context, a2, ah.h, 1000, 307200, "2");
                    if (a2.g == null) {
                        a2.g = new ca(new ce(context, new cb(new cf(new ch()))));
                    }
                    a2.h = 3600000;
                    if (TextUtils.isEmpty(a2.i)) {
                        a2.i = "cKey";
                    }
                    if (a2.f == null) {
                        a2.f = new cl(context, a2.h, a2.i, new ci(30, a2.a, new cn(context)));
                    }
                    bt.a(a2);
                } catch (Throwable th) {
                    aj.b(th, "stm", "usd");
                }
            }
        });
    }

    static /* synthetic */ void a(Context context, byte[] bArr) throws IOException {
        bs a2 = bz.a(a);
        bz.a(context, a2, ah.h, 1000, 307200, "2");
        if (a2.e == null) {
            a2.e = new ac();
        }
        try {
            bt.a(Integer.toString(new Random().nextInt(100)) + Long.toString(System.nanoTime()), bArr, a2);
        } catch (Throwable th) {
            aj.b(th, "stm", "wts");
        }
    }

    public static synchronized void a(final bx bxVar, final Context context) {
        synchronized (by.class) {
            aj.d().submit(new Runnable() {
                public final void run() {
                    try {
                        synchronized (by.class) {
                            by.a(context, bxVar.a());
                        }
                    } catch (Throwable th) {
                        aj.b(th, "stm", "as");
                    }
                }
            });
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        if (r3.size() == 0) goto L_0x000b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(final java.util.List<com.loc.bx> r3, final android.content.Context r4) {
        /*
            java.lang.Class<com.loc.by> r1 = com.loc.by.class
            monitor-enter(r1)
            if (r3 == 0) goto L_0x000b
            int r0 = r3.size()     // Catch:{ Throwable -> 0x000d }
            if (r0 != 0) goto L_0x000e
        L_0x000b:
            monitor-exit(r1)
            return
        L_0x000d:
            r0 = move-exception
        L_0x000e:
            java.util.concurrent.ExecutorService r0 = com.loc.aj.d()     // Catch:{ all -> 0x001b }
            com.loc.by$2 r2 = new com.loc.by$2     // Catch:{ all -> 0x001b }
            r2.<init>(r3, r4)     // Catch:{ all -> 0x001b }
            r0.submit(r2)     // Catch:{ all -> 0x001b }
            goto L_0x000b
        L_0x001b:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.by.a(java.util.List, android.content.Context):void");
    }
}
