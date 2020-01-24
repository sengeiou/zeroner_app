package com.loc;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

/* compiled from: OfflineLocEntity */
public final class bv {
    private Context a;
    private v b;
    private String c;

    public bv(Context context, v vVar, String str) {
        this.a = context.getApplicationContext();
        this.b = vVar;
        this.c = str;
    }

    private static String a(Context context, v vVar, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\"sdkversion\":\"").append(vVar.c()).append("\",\"product\":\"").append(vVar.a()).append("\",\"nt\":\"").append(p.e(context)).append("\",\"details\":").append(str);
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
        }
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final byte[] a() {
        return w.a(a(this.a, this.b, this.c));
    }
}
