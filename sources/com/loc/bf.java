package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import dalvik.system.DexFile;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/* compiled from: DynamicLoader */
final class bf extends bd {
    private PublicKey i = null;

    bf(final Context context, v vVar) throws Exception {
        a aVar = null;
        super(context, vVar);
        final String b = aw.b(context, vVar.a(), vVar.b());
        final String a = aw.a(context);
        if (TextUtils.isEmpty(b) || TextUtils.isEmpty(a)) {
            throw new Exception("dexPath or dexOutputDir is null.");
        }
        File file = new File(b);
        if (bb.b().a(this.e).b) {
            throw new Exception("file is downloading");
        }
        String str = a + File.separator + aw.a(file.getName());
        try {
            if (this.c == null) {
                aVar = bb.b().a(this.e);
                if (aVar != null) {
                    aVar.a = true;
                }
                b();
                if (aVar.b) {
                    throw new Exception("file is downloading");
                }
                this.c = DexFile.loadDex(b, str, 0);
                if (aVar != null) {
                    try {
                        aVar.a = false;
                        synchronized (aVar) {
                            aVar.notify();
                        }
                    } catch (Throwable th) {
                    }
                }
            }
            try {
                bb.b().a().submit(new Runnable() {
                    public final void run() {
                        try {
                            bf.this.a(context, b, a);
                        } catch (Throwable th) {
                            ag.a(th, "dLoader", "run()");
                        }
                    }
                });
            } catch (Throwable th2) {
            }
        } catch (Throwable th3) {
            if (aVar != null) {
                try {
                    aVar.a = false;
                    synchronized (aVar) {
                        aVar.notify();
                    }
                } catch (Throwable th4) {
                }
            }
            throw th3;
        }
    }

    private static void a(JarFile jarFile, JarEntry jarEntry) throws IOException {
        try {
            InputStream inputStream = jarFile.getInputStream(jarEntry);
            do {
            } while (inputStream.read(new byte[8192]) > 0);
            try {
                bc.a((Closeable) inputStream);
            } catch (Throwable th) {
                ThrowableExtension.printStackTrace(th);
            }
        } catch (Throwable th2) {
            ThrowableExtension.printStackTrace(th2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x004c A[SYNTHETIC, Splitter:B:34:0x004c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.io.File r6) {
        /*
            r5 = this;
            r0 = 0
            r2 = 0
            java.security.PublicKey r1 = r5.i     // Catch:{ Throwable -> 0x0037 }
            if (r1 != 0) goto L_0x000c
            java.security.PublicKey r1 = com.loc.bc.a()     // Catch:{ Throwable -> 0x0037 }
            r5.i = r1     // Catch:{ Throwable -> 0x0037 }
        L_0x000c:
            java.util.jar.JarFile r3 = new java.util.jar.JarFile     // Catch:{ Throwable -> 0x0037 }
            r3.<init>(r6)     // Catch:{ Throwable -> 0x0037 }
            java.lang.String r1 = "classes.dex"
            java.util.jar.JarEntry r1 = r3.getJarEntry(r1)     // Catch:{ Throwable -> 0x0057, all -> 0x0054 }
            if (r1 != 0) goto L_0x001e
            r3.close()     // Catch:{ Throwable -> 0x0050 }
        L_0x001d:
            return r0
        L_0x001e:
            a(r3, r1)     // Catch:{ Throwable -> 0x0057, all -> 0x0054 }
            java.security.cert.Certificate[] r1 = r1.getCertificates()     // Catch:{ Throwable -> 0x0057, all -> 0x0054 }
            if (r1 != 0) goto L_0x002d
            r3.close()     // Catch:{ Throwable -> 0x002b }
            goto L_0x001d
        L_0x002b:
            r1 = move-exception
            goto L_0x001d
        L_0x002d:
            boolean r0 = r5.a(r1)     // Catch:{ Throwable -> 0x0057, all -> 0x0054 }
            r3.close()     // Catch:{ Throwable -> 0x0035 }
            goto L_0x001d
        L_0x0035:
            r1 = move-exception
            goto L_0x001d
        L_0x0037:
            r1 = move-exception
        L_0x0038:
            java.lang.String r3 = "DyLoader"
            java.lang.String r4 = "verify"
            com.loc.ag.a(r1, r3, r4)     // Catch:{ all -> 0x0049 }
            if (r2 == 0) goto L_0x001d
            r2.close()     // Catch:{ Throwable -> 0x0047 }
            goto L_0x001d
        L_0x0047:
            r1 = move-exception
            goto L_0x001d
        L_0x0049:
            r0 = move-exception
        L_0x004a:
            if (r2 == 0) goto L_0x004f
            r2.close()     // Catch:{ Throwable -> 0x0052 }
        L_0x004f:
            throw r0
        L_0x0050:
            r1 = move-exception
            goto L_0x001d
        L_0x0052:
            r1 = move-exception
            goto L_0x004f
        L_0x0054:
            r0 = move-exception
            r2 = r3
            goto L_0x004a
        L_0x0057:
            r1 = move-exception
            r2 = r3
            goto L_0x0038
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bf.a(java.io.File):boolean");
    }

    private boolean a(Certificate[] certificateArr) {
        try {
            if (certificateArr.length > 0) {
                int length = certificateArr.length - 1;
                if (length >= 0) {
                    certificateArr[length].verify(this.i);
                    return true;
                }
            }
        } catch (Exception e) {
            ag.a((Throwable) e, "DyLoader", "check");
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00c2 A[Catch:{ Throwable -> 0x0103 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.content.Context r9, java.lang.String r10, java.lang.String r11) throws java.lang.Exception {
        /*
            r8 = this;
            r0 = 0
            java.util.Date r1 = new java.util.Date
            r1.<init>()
            r1.getTime()
            com.loc.ao r6 = new com.loc.ao     // Catch:{ Throwable -> 0x0103 }
            com.loc.ay r1 = com.loc.ay.b()     // Catch:{ Throwable -> 0x0103 }
            r6.<init>(r9, r1)     // Catch:{ Throwable -> 0x0103 }
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x0103 }
            r2.<init>(r10)     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r1 = r2.getName()     // Catch:{ Throwable -> 0x0103 }
            com.loc.az r1 = com.loc.aw.a.a(r6, r1)     // Catch:{ Throwable -> 0x0103 }
            if (r1 == 0) goto L_0x0027
            java.lang.String r1 = r1.e()     // Catch:{ Throwable -> 0x0103 }
            r8.f = r1     // Catch:{ Throwable -> 0x0103 }
        L_0x0027:
            com.loc.v r1 = r8.e     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r3 = r2.getAbsolutePath()     // Catch:{ Throwable -> 0x0103 }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x0103 }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x0103 }
            boolean r4 = r8.a(r4)     // Catch:{ Throwable -> 0x0103 }
            if (r4 == 0) goto L_0x0076
            android.content.Context r4 = r8.a     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r5 = r1.a()     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r7 = r1.b()     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r4 = com.loc.aw.a(r4, r5, r7)     // Catch:{ Throwable -> 0x0103 }
            boolean r1 = com.loc.bc.a(r6, r4, r3, r1)     // Catch:{ Throwable -> 0x0103 }
        L_0x004a:
            if (r1 != 0) goto L_0x006f
            r1 = 0
            r8.d = r1     // Catch:{ Throwable -> 0x0103 }
            android.content.Context r1 = r8.a     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r3 = r2.getName()     // Catch:{ Throwable -> 0x0103 }
            com.loc.aw.a(r1, r6, r3)     // Catch:{ Throwable -> 0x0103 }
            android.content.Context r1 = r8.a     // Catch:{ Throwable -> 0x0103 }
            com.loc.v r3 = r8.e     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r1 = com.loc.aw.a(r1, r6, r3)     // Catch:{ Throwable -> 0x0103 }
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0103 }
            if (r3 != 0) goto L_0x006f
            r8.f = r1     // Catch:{ Throwable -> 0x0103 }
            android.content.Context r1 = r8.a     // Catch:{ Throwable -> 0x0103 }
            com.loc.v r3 = r8.e     // Catch:{ Throwable -> 0x0103 }
            com.loc.aw.a(r1, r3)     // Catch:{ Throwable -> 0x0103 }
        L_0x006f:
            boolean r1 = r2.exists()     // Catch:{ Throwable -> 0x0103 }
            if (r1 != 0) goto L_0x0078
        L_0x0075:
            return
        L_0x0076:
            r1 = r0
            goto L_0x004a
        L_0x0078:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0103 }
            r1.<init>()     // Catch:{ Throwable -> 0x0103 }
            java.lang.StringBuilder r1 = r1.append(r11)     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r3 = java.io.File.separator     // Catch:{ Throwable -> 0x0103 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r3 = r2.getName()     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r3 = com.loc.aw.a(r3)     // Catch:{ Throwable -> 0x0103 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0103 }
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0103 }
            r3.<init>(r1)     // Catch:{ Throwable -> 0x0103 }
            boolean r1 = r3.exists()     // Catch:{ Throwable -> 0x0103 }
            if (r1 == 0) goto L_0x00c9
            java.lang.String r1 = r2.getName()     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r1 = com.loc.aw.a(r1)     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r5 = r8.f     // Catch:{ Throwable -> 0x0103 }
            android.content.Context r2 = r8.a     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r2 = com.loc.aw.a(r2, r1)     // Catch:{ Throwable -> 0x0103 }
            com.loc.v r3 = r8.e     // Catch:{ Throwable -> 0x0103 }
            boolean r3 = com.loc.bc.a(r6, r1, r2, r3)     // Catch:{ Throwable -> 0x0103 }
            if (r3 != 0) goto L_0x0101
            com.loc.az r3 = com.loc.aw.a.a(r6, r1)     // Catch:{ Throwable -> 0x0103 }
            if (r3 == 0) goto L_0x00d2
        L_0x00c0:
            if (r0 != 0) goto L_0x00c9
            android.content.Context r0 = r8.a     // Catch:{ Throwable -> 0x0103 }
            com.loc.v r1 = r8.e     // Catch:{ Throwable -> 0x0103 }
            com.loc.aw.a(r0, r1)     // Catch:{ Throwable -> 0x0103 }
        L_0x00c9:
            java.util.Date r0 = new java.util.Date
            r0.<init>()
            r0.getTime()
            goto L_0x0075
        L_0x00d2:
            java.lang.String r0 = r8.f     // Catch:{ Throwable -> 0x0103 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0103 }
            if (r0 != 0) goto L_0x0101
            com.loc.az$a r0 = new com.loc.az$a     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r2 = com.loc.s.a(r2)     // Catch:{ Throwable -> 0x0103 }
            com.loc.v r3 = r8.e     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r3 = r3.a()     // Catch:{ Throwable -> 0x0103 }
            com.loc.v r4 = r8.e     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r4 = r4.b()     // Catch:{ Throwable -> 0x0103 }
            r0.<init>(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r2 = "useod"
            com.loc.az$a r0 = r0.a(r2)     // Catch:{ Throwable -> 0x0103 }
            com.loc.az r0 = r0.a()     // Catch:{ Throwable -> 0x0103 }
            java.lang.String r1 = com.loc.az.b(r1)     // Catch:{ Throwable -> 0x0103 }
            r6.a(r0, r1)     // Catch:{ Throwable -> 0x0103 }
        L_0x0101:
            r0 = 1
            goto L_0x00c0
        L_0x0103:
            r0 = move-exception
            java.lang.String r1 = "dLoader"
            java.lang.String r2 = "verifyD()"
            com.loc.ag.a(r0, r1, r2)
            goto L_0x00c9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bf.a(android.content.Context, java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0026, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        com.loc.ag.a(r0, "dLoader", "findCl");
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        throw r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x000b A[ExcHandler: ClassNotFoundException (r0v3 'e' java.lang.ClassNotFoundException A[CUSTOM_DECLARE]), Splitter:B:1:0x0001] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Class<?> findClass(java.lang.String r7) throws java.lang.ClassNotFoundException {
        /*
            r6 = this;
            r4 = 0
            dalvik.system.DexFile r0 = r6.c     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            if (r0 != 0) goto L_0x0011
            java.lang.ClassNotFoundException r0 = new java.lang.ClassNotFoundException     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            r0.<init>(r7)     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            throw r0     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
        L_0x000b:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x000d }
        L_0x000d:
            r0 = move-exception
            r6.h = r4
            throw r0
        L_0x0011:
            r1 = 0
            java.util.Map r2 = r6.b     // Catch:{ Throwable -> 0x0026, ClassNotFoundException -> 0x000b }
            monitor-enter(r2)     // Catch:{ Throwable -> 0x0026, ClassNotFoundException -> 0x000b }
            java.util.Map r0 = r6.b     // Catch:{ all -> 0x0023 }
            java.lang.Object r0 = r0.get(r7)     // Catch:{ all -> 0x0023 }
            java.lang.Class r0 = (java.lang.Class) r0     // Catch:{ all -> 0x0023 }
            monitor-exit(r2)     // Catch:{ all -> 0x0086 }
        L_0x001e:
            if (r0 == 0) goto L_0x0032
            r6.h = r4
        L_0x0022:
            return r0
        L_0x0023:
            r0 = move-exception
        L_0x0024:
            monitor-exit(r2)     // Catch:{ Throwable -> 0x0026, ClassNotFoundException -> 0x000b }
            throw r0     // Catch:{ Throwable -> 0x0026, ClassNotFoundException -> 0x000b }
        L_0x0026:
            r0 = move-exception
            java.lang.String r2 = "dLoader"
            java.lang.String r3 = "findCl"
            com.loc.ag.a(r0, r2, r3)     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            r0 = r1
            goto L_0x001e
        L_0x0032:
            boolean r0 = r6.g     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            if (r0 == 0) goto L_0x004c
            java.lang.ClassNotFoundException r0 = new java.lang.ClassNotFoundException     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            r0.<init>(r7)     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            throw r0     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
        L_0x003c:
            r0 = move-exception
            java.lang.String r1 = "dLoader"
            java.lang.String r2 = "findCl"
            com.loc.ag.a(r0, r1, r2)     // Catch:{ all -> 0x000d }
            java.lang.ClassNotFoundException r0 = new java.lang.ClassNotFoundException     // Catch:{ all -> 0x000d }
            r0.<init>(r7)     // Catch:{ all -> 0x000d }
            throw r0     // Catch:{ all -> 0x000d }
        L_0x004c:
            r0 = 1
            r6.h = r0     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            dalvik.system.DexFile r0 = r6.c     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            java.lang.Class r0 = r0.loadClass(r7, r6)     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            dalvik.system.DexFile r1 = r6.c     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            monitor-enter(r1)     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            dalvik.system.DexFile r2 = r6.c     // Catch:{ all -> 0x0069 }
            r2.notify()     // Catch:{ all -> 0x0069 }
            monitor-exit(r1)     // Catch:{ all -> 0x0069 }
            r1 = 0
            r6.h = r1     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            if (r0 != 0) goto L_0x006c
            java.lang.ClassNotFoundException r0 = new java.lang.ClassNotFoundException     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            r0.<init>(r7)     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            throw r0     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
        L_0x0069:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            throw r0     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
        L_0x006c:
            java.util.Map r2 = r6.b     // Catch:{ Throwable -> 0x007b, ClassNotFoundException -> 0x000b }
            monitor-enter(r2)     // Catch:{ Throwable -> 0x007b, ClassNotFoundException -> 0x000b }
            java.util.Map r1 = r6.b     // Catch:{ all -> 0x0078 }
            r1.put(r7, r0)     // Catch:{ all -> 0x0078 }
            monitor-exit(r2)     // Catch:{ all -> 0x0078 }
        L_0x0075:
            r6.h = r4
            goto L_0x0022
        L_0x0078:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ Throwable -> 0x007b, ClassNotFoundException -> 0x000b }
            throw r1     // Catch:{ Throwable -> 0x007b, ClassNotFoundException -> 0x000b }
        L_0x007b:
            r1 = move-exception
            java.lang.String r2 = "dLoader"
            java.lang.String r3 = "findCl"
            com.loc.ag.a(r1, r2, r3)     // Catch:{ ClassNotFoundException -> 0x000b, Throwable -> 0x003c }
            goto L_0x0075
        L_0x0086:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x0024
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bf.findClass(java.lang.String):java.lang.Class");
    }
}
