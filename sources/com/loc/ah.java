package com.loc;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.android.arouter.utils.Consts;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: Log */
public final class ah {
    public static final String a = "/a/";
    static final String b = "b";
    static final String c = "c";
    static final String d = "d";
    static final String e = "i";
    public static final String f = "g";
    public static final String g = "h";
    public static final String h = "e";
    public static final String i = "f";
    public static final String j = "j";

    public static String a(Context context) {
        return c(context, e);
    }

    public static String a(Context context, String str) {
        return context.getSharedPreferences("AMSKLG_CFG", 0).getString(str, "");
    }

    @TargetApi(9)
    public static void a(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences("AMSKLG_CFG", 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    static boolean a(String[] strArr, String str) {
        if (strArr == null || str == null) {
            return false;
        }
        try {
            for (String trim : str.split("\n")) {
                String trim2 = trim.trim();
                if (!TextUtils.isEmpty(trim2) && trim2.contains("uncaughtException")) {
                    return false;
                }
                if (b(strArr, trim2)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return false;
        }
    }

    public static void b(final Context context) {
        try {
            ExecutorService d2 = aj.d();
            if (d2 != null && !d2.isShutdown()) {
                d2.submit(new Runnable() {
                    public final void run() {
                        try {
                            bu.a(context);
                            ak.b(context);
                            ak.d(context);
                            ak.c(context);
                            by.a(context);
                            bw.a(context);
                        } catch (RejectedExecutionException e) {
                        } catch (Throwable th) {
                            aj.b(th, "Lg", "proL");
                        }
                    }
                });
            }
        } catch (Throwable th) {
            aj.b(th, "Lg", "proL");
        }
    }

    public static void b(Context context, String str) {
        Editor edit = context.getSharedPreferences("AMSKLG_CFG", 0).edit();
        edit.remove(str);
        edit.apply();
    }

    static boolean b(String[] strArr, String str) {
        if (strArr == null || str == null) {
            return false;
        }
        try {
            for (String str2 : strArr) {
                str = str.trim();
                if (str.startsWith("at ") && str.contains(str2 + Consts.DOT) && str.endsWith(")") && !str.contains("uncaughtException")) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return false;
        }
    }

    public static String c(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir().getAbsolutePath());
        sb.append(a);
        sb.append(str);
        return sb.toString();
    }

    static List<v> c(Context context) {
        List<v> list;
        Throwable th;
        List<v> list2 = null;
        try {
            synchronized (Looper.getMainLooper()) {
                try {
                    list = new at(context, false).a();
                    try {
                    } catch (Throwable th2) {
                        Throwable th3 = th2;
                        list2 = list;
                        th = th3;
                        try {
                            throw th;
                        } catch (Throwable th4) {
                            Throwable th5 = th4;
                            list = list2;
                            th = th5;
                            ThrowableExtension.printStackTrace(th);
                            return list;
                        }
                    }
                } catch (Throwable th6) {
                    th = th6;
                }
            }
        } catch (Throwable th7) {
            Throwable th8 = th7;
            list = null;
            th = th8;
            ThrowableExtension.printStackTrace(th);
            return list;
        }
    }
}
