package com.google.android.gms.common.internal;

import android.content.Context;

public final class zzbf {
    private static Object sLock = new Object();
    private static boolean zzcls;
    private static String zzgbc;
    private static int zzgbd;

    public static String zzcp(Context context) {
        zzcr(context);
        return zzgbc;
    }

    public static int zzcq(Context context) {
        zzcr(context);
        return zzgbd;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zzcr(android.content.Context r4) {
        /*
            java.lang.Object r1 = sLock
            monitor-enter(r1)
            boolean r0 = zzcls     // Catch:{ all -> 0x0020 }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r1)     // Catch:{ all -> 0x0020 }
        L_0x0008:
            return
        L_0x0009:
            r0 = 1
            zzcls = r0     // Catch:{ all -> 0x0020 }
            java.lang.String r0 = r4.getPackageName()     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzbhe r2 = com.google.android.gms.internal.zzbhf.zzdb(r4)     // Catch:{ all -> 0x0020 }
            r3 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r0 = r2.getApplicationInfo(r0, r3)     // Catch:{ NameNotFoundException -> 0x0037 }
            android.os.Bundle r0 = r0.metaData     // Catch:{ NameNotFoundException -> 0x0037 }
            if (r0 != 0) goto L_0x0023
            monitor-exit(r1)     // Catch:{ all -> 0x0020 }
            goto L_0x0008
        L_0x0020:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0020 }
            throw r0
        L_0x0023:
            java.lang.String r2 = "com.google.app.id"
            java.lang.String r2 = r0.getString(r2)     // Catch:{ NameNotFoundException -> 0x0037 }
            zzgbc = r2     // Catch:{ NameNotFoundException -> 0x0037 }
            java.lang.String r2 = "com.google.android.gms.version"
            int r0 = r0.getInt(r2)     // Catch:{ NameNotFoundException -> 0x0037 }
            zzgbd = r0     // Catch:{ NameNotFoundException -> 0x0037 }
        L_0x0035:
            monitor-exit(r1)     // Catch:{ all -> 0x0020 }
            goto L_0x0008
        L_0x0037:
            r0 = move-exception
            java.lang.String r2 = "MetadataValueReader"
            java.lang.String r3 = "This should never happen."
            android.util.Log.wtf(r2, r3, r0)     // Catch:{ all -> 0x0020 }
            goto L_0x0035
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzbf.zzcr(android.content.Context):void");
    }
}
