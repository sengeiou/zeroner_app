package com.google.android.gms.internal;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.PrintStream;
import java.io.PrintWriter;

public final class zzdvl {
    private static zzdvm zzmag;
    private static int zzmah;

    static final class zza extends zzdvm {
        zza() {
        }

        public final void zza(Throwable th, PrintStream printStream) {
            ThrowableExtension.printStackTrace(th, printStream);
        }

        public final void zza(Throwable th, PrintWriter printWriter) {
            ThrowableExtension.printStackTrace(th, printWriter);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0019  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0073  */
    static {
        /*
            r2 = 1
            r1 = 0
            java.lang.Integer r0 = zzbqm()     // Catch:{ Throwable -> 0x0078 }
            if (r0 == 0) goto L_0x001d
            int r1 = r0.intValue()     // Catch:{ Throwable -> 0x002f }
            r3 = 19
            if (r1 < r3) goto L_0x001d
            com.google.android.gms.internal.zzdvq r1 = new com.google.android.gms.internal.zzdvq     // Catch:{ Throwable -> 0x002f }
            r1.<init>()     // Catch:{ Throwable -> 0x002f }
        L_0x0015:
            zzmag = r1
            if (r0 != 0) goto L_0x0073
            r0 = r2
        L_0x001a:
            zzmah = r0
            return
        L_0x001d:
            java.lang.String r1 = "com.google.devtools.build.android.desugar.runtime.twr_disable_mimic"
            boolean r1 = java.lang.Boolean.getBoolean(r1)     // Catch:{ Throwable -> 0x002f }
            if (r1 != 0) goto L_0x006b
            r1 = r2
        L_0x0027:
            if (r1 == 0) goto L_0x006d
            com.google.android.gms.internal.zzdvp r1 = new com.google.android.gms.internal.zzdvp     // Catch:{ Throwable -> 0x002f }
            r1.<init>()     // Catch:{ Throwable -> 0x002f }
            goto L_0x0015
        L_0x002f:
            r1 = move-exception
        L_0x0030:
            java.io.PrintStream r3 = java.lang.System.err
            java.lang.Class<com.google.android.gms.internal.zzdvl$zza> r4 = com.google.android.gms.internal.zzdvl.zza.class
            java.lang.String r4 = r4.getName()
            java.lang.String r5 = java.lang.String.valueOf(r4)
            int r5 = r5.length()
            int r5 = r5 + 132
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r5 = "An error has occured when initializing the try-with-resources desuguring strategy. The default strategy "
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.String r5 = "will be used. The error is: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.println(r4)
            java.io.PrintStream r3 = java.lang.System.err
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1, r3)
            com.google.android.gms.internal.zzdvl$zza r1 = new com.google.android.gms.internal.zzdvl$zza
            r1.<init>()
            goto L_0x0015
        L_0x006b:
            r1 = 0
            goto L_0x0027
        L_0x006d:
            com.google.android.gms.internal.zzdvl$zza r1 = new com.google.android.gms.internal.zzdvl$zza     // Catch:{ Throwable -> 0x002f }
            r1.<init>()     // Catch:{ Throwable -> 0x002f }
            goto L_0x0015
        L_0x0073:
            int r0 = r0.intValue()
            goto L_0x001a
        L_0x0078:
            r0 = move-exception
            r7 = r0
            r0 = r1
            r1 = r7
            goto L_0x0030
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdvl.<clinit>():void");
    }

    public static void zza(Throwable th, PrintStream printStream) {
        zzmag.zza(th, printStream);
    }

    public static void zza(Throwable th, PrintWriter printWriter) {
        zzmag.zza(th, printWriter);
    }

    private static Integer zzbqm() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            ThrowableExtension.printStackTrace((Throwable) e, System.err);
            return null;
        }
    }
}
