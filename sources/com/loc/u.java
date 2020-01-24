package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;

@Deprecated
/* compiled from: SDKCoordinatorDownload */
public class u extends Thread implements com.loc.bo.a {
    protected static boolean g = false;
    private static String i = "sodownload";
    private static String j = "sofail";
    protected a a;
    protected RandomAccessFile b;
    protected String c;
    protected String d;
    protected String e;
    protected Context f;
    private bo h = new bo(this.a);

    /* compiled from: SDKCoordinatorDownload */
    public static class a extends bq {
        private String a;

        a(String str) {
            this.a = str;
        }

        public final Map<String, String> b() {
            return null;
        }

        public final Map<String, String> b_() {
            return null;
        }

        public final String c() {
            return this.a;
        }
    }

    public u(Context context, String str, String str2, String str3) {
        this.f = context;
        this.e = str3;
        this.c = a(context, str + "temp.so");
        this.d = a(context, "libwgs2gcj.so");
        this.a = new a(str2);
    }

    public static String a(Context context, String str) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "libso" + File.separator + str;
    }

    public void a() {
        if (this.a != null && !TextUtils.isEmpty(this.a.c()) && this.a.c().contains("libJni_wgs2gcj.so") && this.a.c().contains(w.a(this.f)) && !new File(this.d).exists()) {
            start();
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(byte[] r5, long r6) {
        /*
            r4 = this;
            java.io.RandomAccessFile r0 = r4.b     // Catch:{ Throwable -> 0x0035 }
            if (r0 != 0) goto L_0x0022
            java.io.File r0 = new java.io.File     // Catch:{ Throwable -> 0x0035 }
            java.lang.String r1 = r4.c     // Catch:{ Throwable -> 0x0035 }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0035 }
            java.io.File r1 = r0.getParentFile()     // Catch:{ Throwable -> 0x0035 }
            boolean r2 = r1.exists()     // Catch:{ Throwable -> 0x0035 }
            if (r2 != 0) goto L_0x0018
            r1.mkdirs()     // Catch:{ Throwable -> 0x0035 }
        L_0x0018:
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x0027 }
            java.lang.String r2 = "rw"
            r1.<init>(r0, r2)     // Catch:{ FileNotFoundException -> 0x0027 }
            r4.b = r1     // Catch:{ FileNotFoundException -> 0x0027 }
        L_0x0022:
            java.io.RandomAccessFile r0 = r4.b     // Catch:{ Throwable -> 0x0035 }
            if (r0 != 0) goto L_0x0043
        L_0x0026:
            return
        L_0x0027:
            r0 = move-exception
            java.lang.String r1 = "sdl"
            java.lang.String r2 = "oDd"
            com.loc.aj.b(r0, r1, r2)     // Catch:{ Throwable -> 0x0035 }
            r4.e()     // Catch:{ Throwable -> 0x0035 }
            goto L_0x0022
        L_0x0035:
            r0 = move-exception
            r4.e()
            java.lang.String r1 = "sdl"
            java.lang.String r2 = "oDd"
            com.loc.aj.b(r0, r1, r2)
            goto L_0x0026
        L_0x0043:
            java.io.RandomAccessFile r0 = r4.b     // Catch:{ IOException -> 0x004e }
            r0.seek(r6)     // Catch:{ IOException -> 0x004e }
            java.io.RandomAccessFile r0 = r4.b     // Catch:{ IOException -> 0x004e }
            r0.write(r5)     // Catch:{ IOException -> 0x004e }
            goto L_0x0026
        L_0x004e:
            r0 = move-exception
            r4.e()     // Catch:{ Throwable -> 0x0035 }
            java.lang.String r1 = "sdl"
            java.lang.String r2 = "oDd"
            com.loc.aj.b(r0, r1, r2)     // Catch:{ Throwable -> 0x0035 }
            goto L_0x0026
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.u.a(byte[], long):void");
    }

    public void b() {
        try {
            if (this.b != null) {
                this.b.close();
            }
            String a2 = s.a(this.c);
            if (a2 == null || !a2.equalsIgnoreCase(this.e)) {
                e();
            } else if (new File(this.d).exists()) {
                e();
            } else {
                new File(this.c).renameTo(new File(this.d));
            }
        } catch (Throwable th) {
            e();
            File file = new File(this.d);
            if (file.exists()) {
                file.delete();
            }
            aj.b(th, "sdl", "ofs");
        }
    }

    public final void c() {
        e();
    }

    public final void d() {
        try {
            if (this.b != null) {
                this.b.close();
            }
            e();
            File file = new File(a(this.f, "tempfile"));
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdir();
                }
                file.createNewFile();
            }
        } catch (Throwable th) {
            aj.b(th, "sdl", "oe");
        }
    }

    /* access modifiers changed from: protected */
    public final void e() {
        File file = new File(this.c);
        if (file.exists()) {
            file.delete();
        }
    }

    public void run() {
        try {
            File file = new File(a(this.f, "tempfile"));
            if (file.exists()) {
                file.delete();
            }
            this.h.a(this);
        } catch (Throwable th) {
            aj.b(th, "sdl", "run");
            e();
        }
    }
}
