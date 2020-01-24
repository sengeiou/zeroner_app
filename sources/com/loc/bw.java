package com.loc;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import java.lang.ref.WeakReference;

/* compiled from: OfflineLocManager */
public class bw {
    static int a = 1000;
    static boolean b = false;
    static int c = 20;
    /* access modifiers changed from: private */
    public static WeakReference<bs> d;
    /* access modifiers changed from: private */
    public static int e = 10;

    public static synchronized void a(int i, boolean z, int i2) {
        synchronized (bw.class) {
            a = i;
            b = z;
            if (i2 < 10 || i2 > 100) {
                i2 = 20;
            }
            c = i2;
            if (i2 / 5 > e) {
                e = c / 5;
            }
        }
    }

    public static void a(final Context context) {
        aj.d().submit(new Runnable() {
            public final void run() {
                try {
                    bs a2 = bz.a(bw.d);
                    bz.a(context, a2, ah.i, bw.a, 2097152, Constants.VIA_SHARE_TYPE_INFO);
                    a2.h = 14400000;
                    if (a2.g == null) {
                        z zVar = new z(new ab(new ad()));
                        a2.g = new cd(new cc(context, new ch(), zVar, new String(ae.a(10)), l.f(context), p.v(context), p.m(context), p.h(context), p.a(), Build.MANUFACTURER, Build.DEVICE, p.y(context), l.c(context), Build.MODEL, l.d(context), l.b(context)));
                    }
                    if (TextUtils.isEmpty(a2.i)) {
                        a2.i = "fKey";
                    }
                    a2.f = new cl(context, a2.h, a2.i, new cj(context, bw.b, bw.e * 1024, bw.c * 1024));
                    bt.a(a2);
                } catch (Throwable th) {
                    aj.b(th, "ofm", "uold");
                }
            }
        });
    }

    public static synchronized void a(final bv bvVar, final Context context) {
        synchronized (bw.class) {
            aj.d().submit(new Runnable() {
                public final void run() {
                    try {
                        synchronized (bw.class) {
                            String l = Long.toString(System.currentTimeMillis());
                            bs a2 = bz.a(bw.d);
                            bz.a(context, a2, ah.i, bw.a, 2097152, Constants.VIA_SHARE_TYPE_INFO);
                            if (a2.e == null) {
                                a2.e = new z(new ab(new ad(new ab())));
                            }
                            bt.a(l, bvVar.a(), a2);
                        }
                    } catch (Throwable th) {
                        aj.b(th, "ofm", "aple");
                    }
                }
            });
        }
    }
}
