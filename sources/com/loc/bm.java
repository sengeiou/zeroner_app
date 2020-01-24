package com.loc;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.net.URLConnection;
import java.util.Map;

/* compiled from: BaseNetManager */
public final class bm {
    public static int a = 0;
    public static String b = "";
    private static bm c;

    /* compiled from: BaseNetManager */
    public interface a {
        URLConnection a();
    }

    public static bm a() {
        if (c == null) {
            c = new bm();
        }
        return c;
    }

    public static br a(bq bqVar, boolean z) throws k {
        String str;
        if (bqVar == null) {
            try {
                throw new k("requeust is null");
            } catch (k e) {
                throw e;
            } catch (Throwable th) {
                ThrowableExtension.printStackTrace(th);
                throw new k("未知的错误");
            }
        } else if (bqVar.c() == null || "".equals(bqVar.c())) {
            throw new k("request url is empty");
        } else {
            bp bpVar = new bp(bqVar.c, bqVar.d, bqVar.e == null ? null : bqVar.e, z);
            byte[] d = bqVar.d();
            if (d == null || d.length == 0) {
                str = bqVar.c();
            } else {
                Map b_ = bqVar.b_();
                if (b_ == null) {
                    str = bqVar.c();
                } else {
                    String a2 = bp.a(b_);
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(bqVar.c()).append("?").append(a2);
                    str = stringBuffer.toString();
                }
            }
            boolean k = bqVar.k();
            String j = bqVar.j();
            Map b2 = bqVar.b();
            byte[] d2 = bqVar.d();
            if (d2 == null || d2.length == 0) {
                String a3 = bp.a(bqVar.b_());
                if (!TextUtils.isEmpty(a3)) {
                    d2 = w.a(a3);
                }
            }
            return bpVar.a(str, k, j, b2, d2);
        }
    }

    public static byte[] a(bq bqVar) throws k {
        try {
            br a2 = a(bqVar, true);
            if (a2 != null) {
                return a2.a;
            }
            return null;
        } catch (k e) {
            throw e;
        } catch (Throwable th) {
            throw new k("未知的错误");
        }
    }

    public static byte[] b(bq bqVar) throws k {
        try {
            br a2 = a(bqVar, false);
            if (a2 != null) {
                return a2.a;
            }
            return null;
        } catch (k e) {
            throw e;
        } catch (Throwable th) {
            ag.a(th, "bm", "msp");
            throw new k("未知的错误");
        }
    }
}
