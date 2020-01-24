package com.loc;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.Closeable;
import java.io.File;
import java.io.RandomAccessFile;

/* compiled from: DexDownLoad */
public final class au implements com.loc.bo.a {
    protected av a;
    protected v b;
    protected String c;
    protected RandomAccessFile d;
    protected Context e;
    /* access modifiers changed from: private */
    public bo f;

    /* compiled from: DexDownLoad */
    class a implements Runnable {
        private int b = 0;
        private ao c;
        private String d;

        a() {
        }

        a(ao aoVar) {
            this.c = aoVar;
        }

        public final void run() {
            switch (this.b) {
                case 0:
                    try {
                        if (au.this.e()) {
                            bx bxVar = new bx(au.this.e, au.this.b.a(), au.this.b.b(), "O008");
                            bxVar.a("{\"param_int_first\":0}");
                            by.a(bxVar, au.this.e);
                            au.this.f.a(au.this);
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        ag.a(th, "dDownLoad", "run()");
                        return;
                    }
                case 1:
                case 2:
                    try {
                        aw.a(au.this.e, this.c, au.this.b, au.this.c, au.this.a.e);
                        aw.a(au.this.e, au.this.b);
                        return;
                    } catch (Throwable th2) {
                        ag.a(th2, "dDownLoad", "onFinish2");
                        return;
                    }
                case 3:
                    try {
                        aw.a(au.this.e, this.c, au.this.b, au.this.c, this.d);
                        aw.a(au.this.e, au.this.b);
                        return;
                    } catch (Throwable th3) {
                        ag.a(th3, "dDownLoad", "processDownloadedFile()");
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public au(Context context, av avVar, v vVar) {
        try {
            this.e = context.getApplicationContext();
            this.b = vVar;
            if (avVar != null) {
                this.a = avVar;
                this.f = new bo(new be(this.a));
                this.c = aw.a(context, this.a.b);
            }
        } catch (Throwable th) {
            ag.a(th, "dDownLoad", "DexDownLoad()");
        }
    }

    public final void a() {
        try {
            bb.b().a().submit(new a());
        } catch (Throwable th) {
            ag.a(th, "dDownLoad", "startDownload()");
        }
    }

    public final void a(byte[] bArr, long j) {
        try {
            if (this.d == null) {
                File file = new File(this.c);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.d = new RandomAccessFile(file, "rw");
            }
            this.d.seek(j);
            this.d.write(bArr);
        } catch (Throwable th) {
            ag.a(th, "dDownLoad", "onDownload()");
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b() {
        /*
            r7 = this;
            java.io.RandomAccessFile r0 = r7.d     // Catch:{ Throwable -> 0x009e }
            if (r0 != 0) goto L_0x0005
        L_0x0004:
            return
        L_0x0005:
            java.io.RandomAccessFile r0 = r7.d     // Catch:{ Throwable -> 0x009e }
            com.loc.bc.a(r0)     // Catch:{ Throwable -> 0x009e }
            com.loc.av r0 = r7.a     // Catch:{ Throwable -> 0x009e }
            java.lang.String r2 = r0.b()     // Catch:{ Throwable -> 0x009e }
            java.lang.String r0 = r7.c     // Catch:{ Throwable -> 0x009e }
            boolean r0 = com.loc.bc.a(r0, r2)     // Catch:{ Throwable -> 0x009e }
            if (r0 == 0) goto L_0x00c0
            com.loc.av r0 = r7.a     // Catch:{ Throwable -> 0x009e }
            java.lang.String r4 = r0.d     // Catch:{ Throwable -> 0x009e }
            com.loc.ao r6 = new com.loc.ao     // Catch:{ Throwable -> 0x009e }
            android.content.Context r0 = r7.e     // Catch:{ Throwable -> 0x009e }
            com.loc.ay r1 = com.loc.ay.b()     // Catch:{ Throwable -> 0x009e }
            r6.<init>(r0, r1)     // Catch:{ Throwable -> 0x009e }
            com.loc.az$a r0 = new com.loc.az$a     // Catch:{ Throwable -> 0x009e }
            com.loc.av r1 = r7.a     // Catch:{ Throwable -> 0x009e }
            java.lang.String r1 = r1.b     // Catch:{ Throwable -> 0x009e }
            com.loc.av r3 = r7.a     // Catch:{ Throwable -> 0x009e }
            java.lang.String r3 = r3.c     // Catch:{ Throwable -> 0x009e }
            com.loc.av r5 = r7.a     // Catch:{ Throwable -> 0x009e }
            java.lang.String r5 = r5.e     // Catch:{ Throwable -> 0x009e }
            r0.<init>(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x009e }
            java.lang.String r1 = "copy"
            com.loc.az$a r0 = r0.a(r1)     // Catch:{ Throwable -> 0x009e }
            com.loc.az r0 = r0.a()     // Catch:{ Throwable -> 0x009e }
            com.loc.av r1 = r7.a     // Catch:{ Throwable -> 0x009e }
            java.lang.String r1 = r1.b     // Catch:{ Throwable -> 0x009e }
            com.loc.av r2 = r7.a     // Catch:{ Throwable -> 0x009e }
            java.lang.String r2 = r2.c     // Catch:{ Throwable -> 0x009e }
            com.loc.av r3 = r7.a     // Catch:{ Throwable -> 0x009e }
            java.lang.String r3 = r3.e     // Catch:{ Throwable -> 0x009e }
            java.lang.String r1 = com.loc.az.a(r1, r2, r4, r3)     // Catch:{ Throwable -> 0x009e }
            r6.a(r0, r1)     // Catch:{ Throwable -> 0x009e }
            android.content.Context r0 = r7.e     // Catch:{ Throwable -> 0x009e }
            com.loc.av r1 = r7.a     // Catch:{ Throwable -> 0x009e }
            java.lang.String r1 = r1.c     // Catch:{ Throwable -> 0x009e }
            r2 = 0
            android.content.SharedPreferences r0 = r0.getSharedPreferences(r1, r2)     // Catch:{ Throwable -> 0x00aa }
            android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch:{ Throwable -> 0x00aa }
            r0.clear()     // Catch:{ Throwable -> 0x00aa }
            r0.commit()     // Catch:{ Throwable -> 0x00aa }
        L_0x006b:
            com.loc.bb r0 = com.loc.bb.b()     // Catch:{ Throwable -> 0x00b5 }
            java.util.concurrent.ExecutorService r0 = r0.a()     // Catch:{ Throwable -> 0x00b5 }
            com.loc.au$a r1 = new com.loc.au$a     // Catch:{ Throwable -> 0x00b5 }
            r1.<init>(r6)     // Catch:{ Throwable -> 0x00b5 }
            r0.submit(r1)     // Catch:{ Throwable -> 0x00b5 }
        L_0x007b:
            com.loc.bx r0 = new com.loc.bx     // Catch:{ Throwable -> 0x009e }
            android.content.Context r1 = r7.e     // Catch:{ Throwable -> 0x009e }
            com.loc.v r2 = r7.b     // Catch:{ Throwable -> 0x009e }
            java.lang.String r2 = r2.a()     // Catch:{ Throwable -> 0x009e }
            com.loc.v r3 = r7.b     // Catch:{ Throwable -> 0x009e }
            java.lang.String r3 = r3.b()     // Catch:{ Throwable -> 0x009e }
            java.lang.String r4 = "O008"
            r0.<init>(r1, r2, r3, r4)     // Catch:{ Throwable -> 0x009e }
            java.lang.String r1 = "{\"param_int_first\":1}"
            r0.a(r1)     // Catch:{ Throwable -> 0x009e }
            android.content.Context r1 = r7.e     // Catch:{ Throwable -> 0x009e }
            com.loc.by.a(r0, r1)     // Catch:{ Throwable -> 0x009e }
            goto L_0x0004
        L_0x009e:
            r0 = move-exception
            java.lang.String r1 = "dDownLoad"
            java.lang.String r2 = "onFinish()"
            com.loc.ag.a(r0, r1, r2)
            goto L_0x0004
        L_0x00aa:
            r0 = move-exception
            java.lang.String r1 = "dDownLoad"
            java.lang.String r2 = "clearMarker()"
            com.loc.ag.a(r0, r1, r2)     // Catch:{ Throwable -> 0x009e }
            goto L_0x006b
        L_0x00b5:
            r0 = move-exception
            java.lang.String r1 = "dDownLoad"
            java.lang.String r2 = "onFinish1"
            com.loc.ag.a(r0, r1, r2)     // Catch:{ Throwable -> 0x009e }
            goto L_0x007b
        L_0x00c0:
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x00cc }
            java.lang.String r1 = r7.c     // Catch:{ Throwable -> 0x00cc }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x00cc }
            r0.delete()     // Catch:{ Throwable -> 0x00cc }
            goto L_0x0004
        L_0x00cc:
            r0 = move-exception
            java.lang.String r1 = "dDownLoad"
            java.lang.String r2 = "onFinish"
            com.loc.ag.a(r0, r1, r2)     // Catch:{ Throwable -> 0x009e }
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.au.b():void");
    }

    public final void c() {
    }

    public final void d() {
        try {
            bc.a((Closeable) this.d);
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean e() {
        boolean z = this.a != null && this.a.c();
        try {
            if (w.b(this.e) || !bc.a(this.b, this.a) || !bc.a(this.a) || !bc.a(this.e, z) || bc.a(this.e, this.a, this.b)) {
                return false;
            }
            Context context = this.e;
            v vVar = this.b;
            av avVar = this.a;
            boolean z2 = avVar.d() ? true : !w.a(context, avVar.e());
            if (!z2) {
                return false;
            }
            aw.b(this.e, this.b.a());
            return true;
        } catch (Throwable th) {
            ag.a(th, "dDownLoad", "isNeedDownload()");
            return false;
        }
    }
}
