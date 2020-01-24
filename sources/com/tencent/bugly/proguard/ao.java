package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: BUGLY */
public class ao {
    public static boolean a = true;
    private static SimpleDateFormat b;
    private static int c = 5120;
    private static StringBuilder d;
    /* access modifiers changed from: private */
    public static StringBuilder e;
    /* access modifiers changed from: private */
    public static boolean f;
    /* access modifiers changed from: private */
    public static a g;
    private static String h;
    private static String i;
    private static Context j;
    /* access modifiers changed from: private */
    public static String k;
    private static boolean l;
    private static boolean m = false;
    private static int n;
    /* access modifiers changed from: private */
    public static final Object o = new Object();

    /* compiled from: BUGLY */
    public static class a {
        /* access modifiers changed from: private */
        public boolean a;
        /* access modifiers changed from: private */
        public File b;
        private String c;
        private long d;
        /* access modifiers changed from: private */
        public long e = 30720;

        public a(String str) {
            if (str != null && !str.equals("")) {
                this.c = str;
                this.a = a();
            }
        }

        /* access modifiers changed from: private */
        public boolean a() {
            try {
                this.b = new File(this.c);
                if (this.b.exists() && !this.b.delete()) {
                    this.a = false;
                    return false;
                } else if (this.b.createNewFile()) {
                    return true;
                } else {
                    this.a = false;
                    return false;
                }
            } catch (Throwable th) {
                an.a(th);
                this.a = false;
                return false;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:17:0x003b A[SYNTHETIC, Splitter:B:17:0x003b] */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x0045 A[SYNTHETIC, Splitter:B:23:0x0045] */
        /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean a(java.lang.String r9) {
            /*
                r8 = this;
                r1 = 1
                r0 = 0
                boolean r2 = r8.a
                if (r2 != 0) goto L_0x0007
            L_0x0006:
                return r0
            L_0x0007:
                r3 = 0
                java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0031, all -> 0x0041 }
                java.io.File r4 = r8.b     // Catch:{ Throwable -> 0x0031, all -> 0x0041 }
                r5 = 1
                r2.<init>(r4, r5)     // Catch:{ Throwable -> 0x0031, all -> 0x0041 }
                java.lang.String r3 = "UTF-8"
                byte[] r3 = r9.getBytes(r3)     // Catch:{ Throwable -> 0x004f }
                r2.write(r3)     // Catch:{ Throwable -> 0x004f }
                r2.flush()     // Catch:{ Throwable -> 0x004f }
                r2.close()     // Catch:{ Throwable -> 0x004f }
                long r4 = r8.d     // Catch:{ Throwable -> 0x004f }
                int r3 = r3.length     // Catch:{ Throwable -> 0x004f }
                long r6 = (long) r3     // Catch:{ Throwable -> 0x004f }
                long r4 = r4 + r6
                r8.d = r4     // Catch:{ Throwable -> 0x004f }
                r3 = 1
                r8.a = r3     // Catch:{ Throwable -> 0x004f }
                if (r2 == 0) goto L_0x002f
                r2.close()     // Catch:{ IOException -> 0x0049 }
            L_0x002f:
                r0 = r1
                goto L_0x0006
            L_0x0031:
                r1 = move-exception
                r2 = r3
            L_0x0033:
                com.tencent.bugly.proguard.an.a(r1)     // Catch:{ all -> 0x004d }
                r1 = 0
                r8.a = r1     // Catch:{ all -> 0x004d }
                if (r2 == 0) goto L_0x0006
                r2.close()     // Catch:{ IOException -> 0x003f }
                goto L_0x0006
            L_0x003f:
                r1 = move-exception
                goto L_0x0006
            L_0x0041:
                r0 = move-exception
                r2 = r3
            L_0x0043:
                if (r2 == 0) goto L_0x0048
                r2.close()     // Catch:{ IOException -> 0x004b }
            L_0x0048:
                throw r0
            L_0x0049:
                r0 = move-exception
                goto L_0x002f
            L_0x004b:
                r1 = move-exception
                goto L_0x0048
            L_0x004d:
                r0 = move-exception
                goto L_0x0043
            L_0x004f:
                r1 = move-exception
                goto L_0x0033
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ao.a.a(java.lang.String):boolean");
        }
    }

    static {
        b = null;
        try {
            b = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
        }
    }

    private static boolean b(String str, String str2, String str3) {
        try {
            com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
            if (!(b2 == null || b2.L == null)) {
                return b2.L.appendLogToNative(str, str2, str3);
            }
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
        return false;
    }

    private static String f() {
        try {
            com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
            if (!(b2 == null || b2.L == null)) {
                return b2.L.getLogFromNative();
            }
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
        return null;
    }

    public static synchronized void a(Context context) {
        synchronized (ao.class) {
            if (!l && context != null && a) {
                try {
                    e = new StringBuilder(0);
                    d = new StringBuilder(0);
                    j = context;
                    com.tencent.bugly.crashreport.common.info.a a2 = com.tencent.bugly.crashreport.common.info.a.a(context);
                    h = a2.e;
                    a2.getClass();
                    i = "";
                    k = j.getFilesDir().getPath() + "/" + "buglylog_" + h + "_" + i + ".txt";
                    n = Process.myPid();
                } catch (Throwable th) {
                }
                l = true;
            }
        }
    }

    public static void a(int i2) {
        synchronized (o) {
            c = i2;
            if (i2 < 0) {
                c = 0;
            } else if (i2 > 10240) {
                c = 10240;
            }
        }
    }

    public static void a(boolean z) {
        an.a("[LogUtil] Whether can record user log into native: " + z, new Object[0]);
        m = z;
    }

    public static void a(String str, String str2, Throwable th) {
        if (th != null) {
            String message = th.getMessage();
            if (message == null) {
                message = "";
            }
            a(str, str2, message + 10 + ap.b(th));
        }
    }

    public static synchronized void a(String str, String str2, String str3) {
        synchronized (ao.class) {
            if (l && a) {
                if (!m || !b(str, str2, str3)) {
                    String a2 = a(str, str2, str3, (long) Process.myTid());
                    synchronized (o) {
                        e.append(a2);
                        if (e.length() > c) {
                            if (!f) {
                                f = true;
                                am.a().a(new Runnable() {
                                    public void run() {
                                        synchronized (ao.o) {
                                            try {
                                                if (ao.g == null) {
                                                    ao.g = new a(ao.k);
                                                } else if (ao.g.b == null || ao.g.b.length() + ((long) ao.e.length()) > ao.g.e) {
                                                    ao.g.a();
                                                }
                                                if (ao.g.a(ao.e.toString())) {
                                                    ao.e.setLength(0);
                                                    ao.f = false;
                                                }
                                            } catch (Throwable th) {
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }

    private static String a(String str, String str2, String str3, long j2) {
        String date;
        d.setLength(0);
        if (str3.length() > 30720) {
            str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
        }
        Date date2 = new Date();
        if (b != null) {
            date = b.format(date2);
        } else {
            date = date2.toString();
        }
        d.append(date).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(n).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(j2).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(str).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(str2).append(": ").append(str3).append("\u0001\r\n");
        return d.toString();
    }

    public static byte[] a() {
        if (!a) {
            return null;
        }
        if (m) {
            an.a("[LogUtil] Get user log from native.", new Object[0]);
            String f2 = f();
            if (f2 != null) {
                an.a("[LogUtil] Got user log from native: %d bytes", Integer.valueOf(f2.length()));
                return ap.a((File) null, f2, "BuglyNativeLog.txt");
            }
        }
        StringBuilder sb = new StringBuilder();
        synchronized (o) {
            if (g != null && g.a && g.b != null && g.b.length() > 0) {
                sb.append(ap.a(g.b, 30720, true));
            }
            if (e != null && e.length() > 0) {
                sb.append(e.toString());
            }
        }
        return ap.a((File) null, sb.toString(), "BuglyLog.txt");
    }
}
