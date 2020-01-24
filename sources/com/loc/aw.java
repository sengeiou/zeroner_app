package com.loc;

import android.content.Context;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import java.io.File;
import java.util.List;

/* compiled from: DexFileManager */
public final class aw {

    /* compiled from: DexFileManager */
    public static class a {
        static az a(ao aoVar, String str) {
            List b = aoVar.b(az.b(str), az.class);
            if (b == null || b.size() <= 0) {
                return null;
            }
            return (az) b.get(0);
        }

        public static List<az> a(ao aoVar, String str, String str2) {
            return aoVar.b(az.b(str, str2), az.class);
        }
    }

    static String a(Context context) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "pngex";
    }

    static String a(Context context, ao aoVar, v vVar) {
        List b = aoVar.b(az.b(vVar.a(), "copy"), az.class);
        if (b == null || b.size() == 0) {
            return null;
        }
        bc.a(b);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= b.size()) {
                return null;
            }
            az azVar = (az) b.get(i2);
            String a2 = azVar.a();
            if (bc.a(aoVar, a2, a(context, a2), vVar)) {
                try {
                    a(context, aoVar, vVar, a(context, azVar.a()), azVar.e());
                    return azVar.e();
                } catch (Throwable th) {
                    ag.a(th, "FileManager", "loadAvailableD");
                }
            } else {
                c(context, aoVar, azVar.a());
                i = i2 + 1;
            }
        }
    }

    public static String a(Context context, String str) {
        return a(context) + File.separator + str;
    }

    static String a(Context context, String str, String str2) {
        return s.b(str + str2 + p.v(context)) + ShareConstants.JAR_SUFFIX;
    }

    static String a(String str) {
        return str + ".o";
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0078  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void a(android.content.Context r12, com.loc.ao r13, com.loc.v r14, java.lang.String r15, java.lang.String r16) throws java.lang.Throwable {
        /*
            r4 = 0
            r2 = 0
            r1 = 0
            java.lang.String r3 = r14.a()     // Catch:{ Throwable -> 0x00e9, all -> 0x00dd }
            com.loc.bb r0 = com.loc.bb.b()     // Catch:{ Throwable -> 0x00e9, all -> 0x00dd }
            com.loc.bb$a r6 = r0.a(r14)     // Catch:{ Throwable -> 0x00e9, all -> 0x00dd }
            if (r6 == 0) goto L_0x001a
            boolean r0 = r6.a     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
            if (r0 == 0) goto L_0x001a
            monitor-enter(r6)     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
            r6.wait()     // Catch:{ all -> 0x0066 }
            monitor-exit(r6)     // Catch:{ all -> 0x0066 }
        L_0x001a:
            r0 = 1
            r6.b = r0     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
            java.lang.String r0 = r14.b()     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
            java.lang.String r1 = a(r12, r3, r0)     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
            a(r12, r13, r1)     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
            r0.<init>(r15)     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
            r8.<init>(r0)     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
            r0 = 32
            byte[] r0 = new byte[r0]     // Catch:{ Throwable -> 0x00ec, all -> 0x00e4 }
            r8.read(r0)     // Catch:{ Throwable -> 0x00ec, all -> 0x00e4 }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x00ec, all -> 0x00e4 }
            java.lang.String r0 = r14.b()     // Catch:{ Throwable -> 0x00ec, all -> 0x00e4 }
            java.lang.String r0 = b(r12, r3, r0)     // Catch:{ Throwable -> 0x00ec, all -> 0x00e4 }
            r4.<init>(r0)     // Catch:{ Throwable -> 0x00ec, all -> 0x00e4 }
            java.io.RandomAccessFile r7 = new java.io.RandomAccessFile     // Catch:{ Throwable -> 0x00ec, all -> 0x00e4 }
            java.lang.String r0 = "rw"
            r7.<init>(r4, r0)     // Catch:{ Throwable -> 0x00ec, all -> 0x00e4 }
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r0]     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            r0 = 0
        L_0x0053:
            int r5 = r8.read(r2)     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            if (r5 <= 0) goto L_0x0090
            r9 = 1024(0x400, float:1.435E-42)
            if (r5 != r9) goto L_0x007c
            long r10 = (long) r0     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            r7.seek(r10)     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            r7.write(r2)     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
        L_0x0064:
            int r0 = r0 + r5
            goto L_0x0053
        L_0x0066:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
            throw r0     // Catch:{ Throwable -> 0x0069, all -> 0x00e1 }
        L_0x0069:
            r0 = move-exception
            r1 = r6
            r3 = r4
        L_0x006c:
            throw r0     // Catch:{ all -> 0x006d }
        L_0x006d:
            r0 = move-exception
            r6 = r1
            r8 = r3
        L_0x0070:
            com.loc.bc.a(r8)     // Catch:{ Throwable -> 0x00cf }
        L_0x0073:
            com.loc.bc.a(r2)     // Catch:{ Throwable -> 0x00d4 }
        L_0x0076:
            if (r6 == 0) goto L_0x007b
            r1 = 0
            r6.b = r1     // Catch:{ Throwable -> 0x00db }
        L_0x007b:
            throw r0
        L_0x007c:
            byte[] r9 = new byte[r5]     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            r10 = 0
            r11 = 0
            java.lang.System.arraycopy(r2, r10, r9, r11, r5)     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            long r10 = (long) r0     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            r7.seek(r10)     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            r7.write(r9)     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            goto L_0x0064
        L_0x008b:
            r0 = move-exception
            r1 = r6
            r2 = r7
            r3 = r8
            goto L_0x006c
        L_0x0090:
            java.lang.String r0 = r4.getAbsolutePath()     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            java.lang.String r2 = com.loc.s.a(r0)     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            com.loc.az$a r0 = new com.loc.az$a     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            java.lang.String r4 = r14.b()     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            r5 = r16
            r0.<init>(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            java.lang.String r1 = "used"
            com.loc.az$a r0 = r0.a(r1)     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            com.loc.az r0 = r0.a()     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            java.lang.String r1 = r0.a()     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            java.lang.String r1 = com.loc.az.b(r1)     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            r13.a(r0, r1)     // Catch:{ Throwable -> 0x008b, all -> 0x00e6 }
            com.loc.bc.a(r8)     // Catch:{ Throwable -> 0x00c5 }
        L_0x00bc:
            com.loc.bc.a(r7)     // Catch:{ Throwable -> 0x00ca }
        L_0x00bf:
            if (r6 == 0) goto L_0x00c4
            r0 = 0
            r6.b = r0     // Catch:{ Throwable -> 0x00d9 }
        L_0x00c4:
            return
        L_0x00c5:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00bc
        L_0x00ca:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00bf
        L_0x00cf:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0073
        L_0x00d4:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0076
        L_0x00d9:
            r0 = move-exception
            goto L_0x00c4
        L_0x00db:
            r1 = move-exception
            goto L_0x007b
        L_0x00dd:
            r0 = move-exception
            r6 = r1
            r8 = r4
            goto L_0x0070
        L_0x00e1:
            r0 = move-exception
            r8 = r4
            goto L_0x0070
        L_0x00e4:
            r0 = move-exception
            goto L_0x0070
        L_0x00e6:
            r0 = move-exception
            r2 = r7
            goto L_0x0070
        L_0x00e9:
            r0 = move-exception
            r3 = r4
            goto L_0x006c
        L_0x00ec:
            r0 = move-exception
            r1 = r6
            r3 = r8
            goto L_0x006c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aw.a(android.content.Context, com.loc.ao, com.loc.v, java.lang.String, java.lang.String):void");
    }

    static void a(Context context, ao aoVar, String str) {
        c(context, aoVar, a(str));
        c(context, aoVar, str);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void a(android.content.Context r8, com.loc.v r9) {
        /*
            com.loc.bb r0 = com.loc.bb.b()     // Catch:{ Throwable -> 0x002c }
            com.loc.bb$a r6 = r0.a(r9)     // Catch:{ Throwable -> 0x002c }
            if (r6 == 0) goto L_0x0013
            boolean r0 = r6.a     // Catch:{ Throwable -> 0x002c }
            if (r0 == 0) goto L_0x0013
            monitor-enter(r6)     // Catch:{ Throwable -> 0x002c }
            r6.wait()     // Catch:{ all -> 0x0029 }
            monitor-exit(r6)     // Catch:{ all -> 0x0029 }
        L_0x0013:
            r0 = 1
            r6.b = r0     // Catch:{ Throwable -> 0x002c }
            java.lang.String r0 = r9.a()     // Catch:{ Throwable -> 0x002c }
            java.lang.String r1 = r9.b()     // Catch:{ Throwable -> 0x002c }
            java.lang.String r0 = b(r8, r0, r1)     // Catch:{ Throwable -> 0x002c }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x002c }
            if (r1 == 0) goto L_0x0037
        L_0x0028:
            return
        L_0x0029:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ Throwable -> 0x002c }
            throw r0     // Catch:{ Throwable -> 0x002c }
        L_0x002c:
            r0 = move-exception
            java.lang.String r1 = "BaseLoader"
            java.lang.String r2 = "getInstanceByThread()"
            com.loc.ag.a(r0, r1, r2)
            goto L_0x0028
        L_0x0037:
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x002c }
            r1.<init>(r0)     // Catch:{ Throwable -> 0x002c }
            java.io.File r2 = r1.getParentFile()     // Catch:{ Throwable -> 0x002c }
            boolean r3 = r1.exists()     // Catch:{ Throwable -> 0x002c }
            if (r3 != 0) goto L_0x005a
            if (r2 == 0) goto L_0x0028
            boolean r0 = r2.exists()     // Catch:{ Throwable -> 0x002c }
            if (r0 == 0) goto L_0x0028
            java.lang.String r0 = r9.a()     // Catch:{ Throwable -> 0x002c }
            java.lang.String r1 = r9.b()     // Catch:{ Throwable -> 0x002c }
            c(r8, r0, r1)     // Catch:{ Throwable -> 0x002c }
            goto L_0x0028
        L_0x005a:
            java.lang.String r2 = r1.getName()     // Catch:{ Throwable -> 0x002c }
            java.lang.String r2 = a(r2)     // Catch:{ Throwable -> 0x002c }
            java.lang.String r2 = a(r8, r2)     // Catch:{ Throwable -> 0x002c }
            r3 = 0
            dalvik.system.DexFile r0 = dalvik.system.DexFile.loadDex(r0, r2, r3)     // Catch:{ Throwable -> 0x002c }
            if (r0 == 0) goto L_0x00c0
            r0.close()     // Catch:{ Throwable -> 0x002c }
            r5 = 0
            com.loc.ao r7 = new com.loc.ao     // Catch:{ Throwable -> 0x002c }
            com.loc.ay r0 = com.loc.ay.b()     // Catch:{ Throwable -> 0x002c }
            r7.<init>(r8, r0)     // Catch:{ Throwable -> 0x002c }
            java.lang.String r0 = r1.getName()     // Catch:{ Throwable -> 0x002c }
            com.loc.az r0 = com.loc.aw.a.a(r7, r0)     // Catch:{ Throwable -> 0x002c }
            if (r0 == 0) goto L_0x0088
            java.lang.String r5 = r0.e()     // Catch:{ Throwable -> 0x002c }
        L_0x0088:
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x002c }
            r0.<init>(r2)     // Catch:{ Throwable -> 0x002c }
            boolean r1 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x002c }
            if (r1 != 0) goto L_0x00c0
            boolean r1 = r0.exists()     // Catch:{ Throwable -> 0x002c }
            if (r1 == 0) goto L_0x00c0
            java.lang.String r2 = com.loc.s.a(r2)     // Catch:{ Throwable -> 0x002c }
            java.lang.String r1 = r0.getName()     // Catch:{ Throwable -> 0x002c }
            java.lang.String r3 = r9.a()     // Catch:{ Throwable -> 0x002c }
            java.lang.String r4 = r9.b()     // Catch:{ Throwable -> 0x002c }
            com.loc.az$a r0 = new com.loc.az$a     // Catch:{ Throwable -> 0x002c }
            r0.<init>(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x002c }
            java.lang.String r2 = "useod"
            com.loc.az$a r0 = r0.a(r2)     // Catch:{ Throwable -> 0x002c }
            com.loc.az r0 = r0.a()     // Catch:{ Throwable -> 0x002c }
            java.lang.String r1 = com.loc.az.b(r1)     // Catch:{ Throwable -> 0x002c }
            r7.a(r0, r1)     // Catch:{ Throwable -> 0x002c }
        L_0x00c0:
            r0 = 0
            r6.b = r0     // Catch:{ Throwable -> 0x002c }
            goto L_0x0028
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aw.a(android.content.Context, com.loc.v):void");
    }

    static void a(Context context, File file, v vVar) {
        File parentFile = file.getParentFile();
        if (!file.exists() && parentFile != null && parentFile.exists()) {
            c(context, vVar.a(), vVar.b());
        }
    }

    static void a(ao aoVar, Context context, String str) {
        List<az> a2 = a.a(aoVar, str, "used");
        if (a2 != null && a2.size() > 0) {
            for (az azVar : a2) {
                if (azVar != null && azVar.c().equals(str)) {
                    a(context, aoVar, azVar.a());
                    List b = aoVar.b(az.a(str, azVar.e()), az.class);
                    if (b != null && b.size() > 0) {
                        az azVar2 = (az) b.get(0);
                        azVar2.c("errorstatus");
                        aoVar.a((Object) azVar2, az.b(azVar2.a()));
                        File file = new File(a(context, azVar2.a()));
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
            }
        }
    }

    static String b(Context context, String str, String str2) {
        return a(context, a(context, str, str2));
    }

    static void b(Context context, String str) {
        ao aoVar = new ao(context, ay.b());
        List a2 = a.a(aoVar, str, "copy");
        bc.a(a2);
        if (a2 != null && a2.size() > 1) {
            int size = a2.size();
            for (int i = 1; i < size; i++) {
                c(context, aoVar, ((az) a2.get(i)).a());
            }
        }
    }

    /* access modifiers changed from: private */
    public static void c(Context context, ao aoVar, String str) {
        File file = new File(a(context, str));
        if (file.exists()) {
            file.delete();
        }
        aoVar.a(az.b(str), az.class);
    }

    private static void c(final Context context, final String str, final String str2) {
        try {
            bb.b().a().submit(new Runnable() {
                public final void run() {
                    try {
                        ao aoVar = new ao(context, ay.b());
                        List<az> b2 = aoVar.b(az.a(str), az.class);
                        if (b2 != null && b2.size() > 0) {
                            for (az azVar : b2) {
                                if (!str2.equalsIgnoreCase(azVar.d())) {
                                    aw.c(context, aoVar, azVar.a());
                                }
                            }
                        }
                    } catch (Throwable th) {
                        ag.a(th, "FileManager", "clearUnSuitableV");
                    }
                }
            });
        } catch (Throwable th) {
        }
    }
}
