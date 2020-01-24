package com.google.android.gms.common.util;

import android.os.Process;

public final class zzs {
    private static String zzget = null;
    private static final int zzgeu = Process.myPid();

    public static String zzamo() {
        if (zzget == null) {
            zzget = zzcj(zzgeu);
        }
        return zzget;
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String zzcj(int r7) {
        /*
            r0 = 0
            if (r7 > 0) goto L_0x0004
        L_0x0003:
            return r0
        L_0x0004:
            android.os.StrictMode$ThreadPolicy r2 = android.os.StrictMode.allowThreadDiskReads()     // Catch:{ IOException -> 0x0043, all -> 0x0049 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ all -> 0x003e }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ all -> 0x003e }
            r4 = 25
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x003e }
            r5.<init>(r4)     // Catch:{ all -> 0x003e }
            java.lang.String r4 = "/proc/"
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ all -> 0x003e }
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ all -> 0x003e }
            java.lang.String r5 = "/cmdline"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x003e }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x003e }
            r3.<init>(r4)     // Catch:{ all -> 0x003e }
            r1.<init>(r3)     // Catch:{ all -> 0x003e }
            android.os.StrictMode.setThreadPolicy(r2)     // Catch:{ IOException -> 0x0053, all -> 0x0051 }
            java.lang.String r2 = r1.readLine()     // Catch:{ IOException -> 0x0053, all -> 0x0051 }
            java.lang.String r0 = r2.trim()     // Catch:{ IOException -> 0x0053, all -> 0x0051 }
            com.google.android.gms.common.util.zzn.closeQuietly(r1)
            goto L_0x0003
        L_0x003e:
            r1 = move-exception
            android.os.StrictMode.setThreadPolicy(r2)     // Catch:{ IOException -> 0x0043, all -> 0x0049 }
            throw r1     // Catch:{ IOException -> 0x0043, all -> 0x0049 }
        L_0x0043:
            r1 = move-exception
            r1 = r0
        L_0x0045:
            com.google.android.gms.common.util.zzn.closeQuietly(r1)
            goto L_0x0003
        L_0x0049:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x004d:
            com.google.android.gms.common.util.zzn.closeQuietly(r1)
            throw r0
        L_0x0051:
            r0 = move-exception
            goto L_0x004d
        L_0x0053:
            r2 = move-exception
            goto L_0x0045
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.zzs.zzcj(int):java.lang.String");
    }
}
