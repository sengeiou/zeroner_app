package com.loc;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.lang.ref.WeakReference;

/* compiled from: Utils */
public final class bz {
    public static bs a(WeakReference<bs> weakReference) {
        if (weakReference == null || weakReference.get() == null) {
            weakReference = new WeakReference<>(new bs());
        }
        return (bs) weakReference.get();
    }

    public static String a(Context context, v vVar) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\"sim\":\"").append(p.g(context)).append("\",\"sdkversion\":\"").append(vVar.c()).append("\",\"product\":\"").append(vVar.a()).append("\",\"ed\":\"").append(vVar.d()).append("\",\"nt\":\"").append(p.e(context)).append("\",\"np\":\"").append(p.c(context)).append("\",\"mnc\":\"").append(p.d(context)).append("\",\"ant\":\"").append(p.f(context)).append("\"");
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
        }
        return sb.toString();
    }

    public static void a(Context context, bs bsVar, String str, int i, int i2, String str2) {
        bsVar.a = ah.c(context, str);
        bsVar.d = i;
        bsVar.b = (long) i2;
        bsVar.c = str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0054 A[SYNTHETIC, Splitter:B:40:0x0054] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x005e A[SYNTHETIC, Splitter:B:46:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0063 A[SYNTHETIC, Splitter:B:49:0x0063] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static byte[] a(com.loc.bh r6, java.lang.String r7) {
        /*
            r2 = 0
            r1 = 0
            r0 = 0
            byte[] r0 = new byte[r0]
            com.loc.bh$b r3 = r6.a(r7)     // Catch:{ Throwable -> 0x0042, all -> 0x005a }
            if (r3 != 0) goto L_0x0016
            if (r2 == 0) goto L_0x0010
            r1.close()     // Catch:{ Throwable -> 0x0076 }
        L_0x0010:
            if (r3 == 0) goto L_0x0015
            r3.close()     // Catch:{ Throwable -> 0x007b }
        L_0x0015:
            return r0
        L_0x0016:
            java.io.InputStream r2 = r3.a()     // Catch:{ Throwable -> 0x0089 }
            if (r2 != 0) goto L_0x002c
            if (r2 == 0) goto L_0x0021
            r2.close()     // Catch:{ Throwable -> 0x007d }
        L_0x0021:
            if (r3 == 0) goto L_0x0015
            r3.close()     // Catch:{ Throwable -> 0x0027 }
            goto L_0x0015
        L_0x0027:
            r1 = move-exception
        L_0x0028:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0015
        L_0x002c:
            int r1 = r2.available()     // Catch:{ Throwable -> 0x0089 }
            byte[] r0 = new byte[r1]     // Catch:{ Throwable -> 0x0089 }
            r2.read(r0)     // Catch:{ Throwable -> 0x0089 }
            if (r2 == 0) goto L_0x003a
            r2.close()     // Catch:{ Throwable -> 0x0082 }
        L_0x003a:
            if (r3 == 0) goto L_0x0015
            r3.close()     // Catch:{ Throwable -> 0x0040 }
            goto L_0x0015
        L_0x0040:
            r1 = move-exception
            goto L_0x0028
        L_0x0042:
            r1 = move-exception
            r3 = r2
        L_0x0044:
            java.lang.String r4 = "sui"
            java.lang.String r5 = "rdS"
            com.loc.aj.b(r1, r4, r5)     // Catch:{ all -> 0x0087 }
            if (r2 == 0) goto L_0x0052
            r2.close()     // Catch:{ Throwable -> 0x0071 }
        L_0x0052:
            if (r3 == 0) goto L_0x0015
            r3.close()     // Catch:{ Throwable -> 0x0058 }
            goto L_0x0015
        L_0x0058:
            r1 = move-exception
            goto L_0x0028
        L_0x005a:
            r0 = move-exception
            r3 = r2
        L_0x005c:
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ Throwable -> 0x0067 }
        L_0x0061:
            if (r3 == 0) goto L_0x0066
            r3.close()     // Catch:{ Throwable -> 0x006c }
        L_0x0066:
            throw r0
        L_0x0067:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0061
        L_0x006c:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0066
        L_0x0071:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0052
        L_0x0076:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0010
        L_0x007b:
            r1 = move-exception
            goto L_0x0028
        L_0x007d:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0021
        L_0x0082:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x003a
        L_0x0087:
            r0 = move-exception
            goto L_0x005c
        L_0x0089:
            r1 = move-exception
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bz.a(com.loc.bh, java.lang.String):byte[]");
    }
}
