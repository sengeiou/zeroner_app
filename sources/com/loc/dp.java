package com.loc;

import android.content.Context;

/* compiled from: RollBackDynamic */
public final class dp {
    static boolean a = false;
    static boolean b = false;
    static boolean c = false;
    static boolean d = false;
    static int e = 0;
    static int f = 0;
    static boolean g = true;
    static boolean h = false;

    public static void a(Context context) {
        try {
            if (e(context) && !a) {
                dq.a(context, "loc", "startMark", dq.b(context, "loc", "startMark", 0) + 1);
                a = true;
            }
        } catch (Throwable th) {
            di.a(th, "RollBackDynamic", "AddStartMark");
        }
    }

    private static void a(Context context, int i) {
        try {
            if (e(context)) {
                dq.a(context, "loc", "endMark", i);
                dq.a(context, "loc", "startMark", i);
            }
        } catch (Throwable th) {
            di.a(th, "RollBackDynamic", "resetMark");
        }
    }

    public static void a(Context context, v vVar) {
        if (!d) {
            c = ba.b(context, vVar);
            d = true;
            if (!c && di.d()) {
                ba.a(context, "loc");
                Cdo.a("dexrollbackstatistics", "RollBack because of version error");
            }
        }
    }

    public static void a(Context context, String str, String str2) {
        try {
            ba.a(context, str);
            Cdo.a("dexrollbackstatistics", "RollBack because of " + str2);
        } catch (Throwable th) {
            di.a(th, "RollBackDynamic", "rollBackDynamicFile");
        }
    }

    public static void b(Context context) {
        try {
            if (e(context) && !b) {
                dq.a(context, "loc", "endMark", dq.b(context, "loc", "endMark", 0) + 1);
                b = true;
            }
        } catch (Throwable th) {
            di.a(th, "RollBackDynamic", "AddEndMark");
        }
    }

    public static boolean c(Context context) {
        try {
            if (!e(context)) {
                return false;
            }
            if (h) {
                return g;
            }
            if (e == 0) {
                e = dq.b(context, "loc", "startMark", 0);
            }
            if (f == 0) {
                f = dq.b(context, "loc", "endMark", 0);
            }
            if (!a && !b) {
                if (e < f) {
                    a(context, 0);
                    g = true;
                }
                if (e - f > 0 && e > 99) {
                    a(context, 0);
                    g = true;
                }
                if (e - f > 0 && e < 99) {
                    a(context, -2);
                    g = false;
                }
                if (e - f > 0 && f < 0) {
                    a(context, "loc", "checkMark");
                    g = false;
                }
            }
            dq.a(context, "loc", "isload", g);
            h = true;
            return g;
        } catch (Throwable th) {
            di.a(th, "RollBackDynamic", "checkMark");
        }
    }

    public static boolean d(Context context) {
        try {
            if (!e(context)) {
                return false;
            }
            return dq.b(context, "loc", "isload", false);
        } catch (Throwable th) {
            di.a(th, "RollBackDynamic", "isLoad");
            return true;
        }
    }

    private static boolean e(Context context) {
        if (!d) {
            a(context, di.b());
        }
        return c;
    }
}
