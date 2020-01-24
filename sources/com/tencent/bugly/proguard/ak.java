package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import android.util.Base64;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.b;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

/* compiled from: BUGLY */
public class ak {
    private static ak c = null;
    public aj a;
    public boolean b = true;
    private final ae d;
    private final Context e;
    private Map<Integer, Long> f = new HashMap();
    private long g;
    private long h;
    private LinkedBlockingQueue<Runnable> i = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Runnable> j = new LinkedBlockingQueue<>();
    /* access modifiers changed from: private */
    public final Object k = new Object();
    private String l = null;
    private byte[] m = null;
    private long n = 0;
    /* access modifiers changed from: private */
    public byte[] o = null;
    private long p = 0;
    /* access modifiers changed from: private */
    public String q = null;
    private long r = 0;
    private final Object s = new Object();
    /* access modifiers changed from: private */
    public boolean t = false;
    /* access modifiers changed from: private */
    public final Object u = new Object();
    private int v = 0;

    /* compiled from: BUGLY */
    class a implements Runnable {
        private final Context b;
        private final Runnable c;
        private final long d;

        public a(Context context) {
            this.b = context;
            this.c = null;
            this.d = 0;
        }

        public a(Context context, Runnable runnable, long j) {
            this.b = context;
            this.c = runnable;
            this.d = j;
        }

        public void run() {
            if (!ap.a(this.b, "security_info", 30000)) {
                an.c("[UploadManager] Sleep %d try to lock security file again (pid=%d | tid=%d)", Integer.valueOf(5000), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                ap.b((long) BootloaderScanner.TIMEOUT);
                if (ap.a((Runnable) this, "BUGLY_ASYNC_UPLOAD") == null) {
                    an.d("[UploadManager] Failed to start a thread to execute task of initializing security context, try to post it into thread pool.", new Object[0]);
                    am a2 = am.a();
                    if (a2 != null) {
                        a2.a(this);
                    } else {
                        an.e("[UploadManager] Asynchronous thread pool is unavailable now, try next time.", new Object[0]);
                    }
                }
            } else {
                if (!ak.this.e()) {
                    an.d("[UploadManager] Failed to load security info from database", new Object[0]);
                    ak.this.b(false);
                }
                if (ak.this.q != null) {
                    if (ak.this.b()) {
                        an.c("[UploadManager] Sucessfully got session ID, try to execute upload tasks now (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                        if (this.c != null) {
                            ak.this.a(this.c, this.d);
                        }
                        ak.this.c(0);
                        ap.c(this.b, "security_info");
                        synchronized (ak.this.u) {
                            ak.this.t = false;
                        }
                        return;
                    }
                    an.a("[UploadManager] Session ID is expired, drop it.", new Object[0]);
                    ak.this.b(true);
                }
                byte[] a3 = ap.a(128);
                if (a3 == null || a3.length * 8 != 128) {
                    an.d("[UploadManager] Failed to create AES key (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                    ak.this.b(false);
                    ap.c(this.b, "security_info");
                    synchronized (ak.this.u) {
                        ak.this.t = false;
                    }
                    return;
                }
                ak.this.o = a3;
                an.c("[UploadManager] Execute one upload task for requesting session ID (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                if (this.c != null) {
                    ak.this.a(this.c, this.d);
                } else {
                    ak.this.c(1);
                }
            }
        }
    }

    static /* synthetic */ int b(ak akVar) {
        int i2 = akVar.v - 1;
        akVar.v = i2;
        return i2;
    }

    protected ak(Context context) {
        this.e = context;
        this.d = ae.a();
        try {
            Class.forName("android.util.Base64");
        } catch (ClassNotFoundException e2) {
            an.a("[UploadManager] Error: Can not find Base64 class, will not use stronger security way to upload", new Object[0]);
            this.b = false;
        }
        if (this.b) {
            StringBuilder sb = new StringBuilder();
            sb.append("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDP9x32s5pPtZBXzJBz2GWM/").append("sbTvVO2+RvW0PH01IdaBxc/").append("fB6fbHZocC9T3nl1+").append("J5eAFjIRVuV8vHDky7Qo82Mnh0PVvcZIEQvMMVKU8dsMQopxgsOs2gkSHJwgWdinKNS8CmWobo6pFwPUW11lMv714jAUZRq2GBOqiO2vQI6iwIDAQAB");
            this.l = sb.toString();
        }
    }

    public static synchronized ak a(Context context) {
        ak akVar;
        synchronized (ak.class) {
            if (c == null) {
                c = new ak(context);
            }
            akVar = c;
        }
        return akVar;
    }

    public static synchronized ak a() {
        ak akVar;
        synchronized (ak.class) {
            akVar = c;
        }
        return akVar;
    }

    public void a(int i2, int i3, byte[] bArr, String str, String str2, aj ajVar, long j2, boolean z) {
        try {
            b(new al(this.e, i2, i3, bArr, str, str2, ajVar, this.b, z), true, true, j2);
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
    }

    public void a(int i2, bd bdVar, String str, String str2, aj ajVar, long j2, boolean z) {
        a(i2, bdVar.g, ah.a((Object) bdVar), str, str2, ajVar, j2, z);
    }

    public void a(int i2, int i3, byte[] bArr, String str, String str2, aj ajVar, int i4, int i5, boolean z, Map<String, String> map) {
        try {
            b(new al(this.e, i2, i3, bArr, str, str2, ajVar, this.b, i4, i5, false, map), z, false, 0);
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
    }

    public void a(int i2, int i3, byte[] bArr, String str, String str2, aj ajVar, boolean z, Map<String, String> map) {
        a(i2, i3, bArr, str, str2, ajVar, 0, 0, z, map);
    }

    public void a(int i2, bd bdVar, String str, String str2, aj ajVar, boolean z) {
        a(i2, bdVar.g, ah.a((Object) bdVar), str, str2, ajVar, 0, 0, z, null);
    }

    public long a(boolean z) {
        long j2;
        long j3 = 0;
        long b2 = ap.b();
        int i2 = z ? 5 : 3;
        List a2 = this.d.a(i2);
        if (a2 == null || a2.size() <= 0) {
            j2 = z ? this.h : this.g;
        } else {
            try {
                ag agVar = (ag) a2.get(0);
                if (agVar.e >= b2) {
                    j3 = ap.c(agVar.g);
                    if (i2 == 3) {
                        this.g = j3;
                    } else {
                        this.h = j3;
                    }
                    a2.remove(agVar);
                }
                j2 = j3;
            } catch (Throwable th) {
                Throwable th2 = th;
                j2 = j3;
                an.a(th2);
            }
            if (a2.size() > 0) {
                this.d.a(a2);
            }
        }
        an.c("[UploadManager] Local network consume: %d KB", Long.valueOf(j2 / 1024));
        return j2;
    }

    /* access modifiers changed from: protected */
    public synchronized void a(long j2, boolean z) {
        int i2 = z ? 5 : 3;
        ag agVar = new ag();
        agVar.b = i2;
        agVar.e = ap.b();
        agVar.c = "";
        agVar.d = "";
        agVar.g = ap.c(j2);
        this.d.b(i2);
        this.d.a(agVar);
        if (z) {
            this.h = j2;
        } else {
            this.g = j2;
        }
        an.c("[UploadManager] Network total consume: %d KB", Long.valueOf(j2 / 1024));
    }

    public synchronized void a(int i2, long j2) {
        if (i2 >= 0) {
            this.f.put(Integer.valueOf(i2), Long.valueOf(j2));
            ag agVar = new ag();
            agVar.b = i2;
            agVar.e = j2;
            agVar.c = "";
            agVar.d = "";
            agVar.g = new byte[0];
            this.d.b(i2);
            this.d.a(agVar);
            an.c("[UploadManager] Uploading(ID:%d) time: %s", Integer.valueOf(i2), ap.a(j2));
        } else {
            an.e("[UploadManager] Unknown uploading ID: %d", Integer.valueOf(i2));
        }
    }

    public synchronized long a(int i2) {
        long j2;
        long j3;
        j2 = 0;
        if (i2 >= 0) {
            Long l2 = (Long) this.f.get(Integer.valueOf(i2));
            if (l2 != null) {
                j2 = l2.longValue();
            } else {
                List<ag> a2 = this.d.a(i2);
                if (a2 != null && a2.size() > 0) {
                    if (a2.size() > 1) {
                        for (ag agVar : a2) {
                            if (agVar.e > j2) {
                                j3 = agVar.e;
                            } else {
                                j3 = j2;
                            }
                            j2 = j3;
                        }
                        this.d.b(i2);
                    } else {
                        try {
                            j2 = ((ag) a2.get(0)).e;
                        } catch (Throwable th) {
                            an.a(th);
                        }
                    }
                }
            }
        } else {
            an.e("[UploadManager] Unknown upload ID: %d", Integer.valueOf(i2));
        }
        return j2;
    }

    public boolean b(int i2) {
        if (b.c) {
            an.c("Uploading frequency will not be checked if SDK is in debug mode.", new Object[0]);
            return true;
        }
        long currentTimeMillis = System.currentTimeMillis() - a(i2);
        an.c("[UploadManager] Time interval is %d seconds since last uploading(ID: %d).", Long.valueOf(currentTimeMillis / 1000), Integer.valueOf(i2));
        if (currentTimeMillis >= 30000) {
            return true;
        }
        an.a("[UploadManager] Data only be uploaded once in %d seconds.", Long.valueOf(30));
        return false;
    }

    private boolean c() {
        an.c("[UploadManager] Drop security info of database (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            ae a2 = ae.a();
            if (a2 != null) {
                return a2.a(555, "security_info", (ad) null, true);
            }
            an.d("[UploadManager] Failed to get Database", new Object[0]);
            return false;
        } catch (Throwable th) {
            an.a(th);
            return false;
        }
    }

    private boolean d() {
        an.c("[UploadManager] Record security info to database (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            ae a2 = ae.a();
            if (a2 == null) {
                an.d("[UploadManager] Failed to get database", new Object[0]);
                return false;
            }
            StringBuilder sb = new StringBuilder();
            if (this.o != null) {
                sb.append(Base64.encodeToString(this.o, 0));
                sb.append("#");
                if (this.p != 0) {
                    sb.append(Long.toString(this.p));
                } else {
                    sb.append("null");
                }
                sb.append("#");
                if (this.q != null) {
                    sb.append(this.q);
                } else {
                    sb.append("null");
                }
                sb.append("#");
                if (this.r != 0) {
                    sb.append(Long.toString(this.r));
                } else {
                    sb.append("null");
                }
                a2.a(555, "security_info", sb.toString().getBytes(), (ad) null, true);
                return true;
            }
            an.c("[UploadManager] AES key is null, will not record", new Object[0]);
            return false;
        } catch (Throwable th) {
            an.a(th);
            c();
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean e() {
        /*
            r8 = this;
            r3 = 2
            r1 = 0
            r2 = 1
            java.lang.String r0 = "[UploadManager] Load security info from database (pid=%d | tid=%d)"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            int r4 = android.os.Process.myPid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r1] = r4
            int r4 = android.os.Process.myTid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r2] = r4
            com.tencent.bugly.proguard.an.c(r0, r3)
            com.tencent.bugly.proguard.ae r0 = com.tencent.bugly.proguard.ae.a()     // Catch:{ Throwable -> 0x0108 }
            if (r0 != 0) goto L_0x0030
            java.lang.String r0 = "[UploadManager] Failed to get database"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0108 }
            com.tencent.bugly.proguard.an.d(r0, r2)     // Catch:{ Throwable -> 0x0108 }
            r0 = r1
        L_0x002f:
            return r0
        L_0x0030:
            r3 = 555(0x22b, float:7.78E-43)
            r4 = 0
            r5 = 1
            java.util.Map r0 = r0.a(r3, r4, r5)     // Catch:{ Throwable -> 0x0108 }
            if (r0 == 0) goto L_0x00dd
            java.lang.String r3 = "security_info"
            boolean r3 = r0.containsKey(r3)     // Catch:{ Throwable -> 0x0108 }
            if (r3 == 0) goto L_0x00dd
            java.lang.String r3 = new java.lang.String     // Catch:{ Throwable -> 0x0108 }
            java.lang.String r4 = "security_info"
            java.lang.Object r0 = r0.get(r4)     // Catch:{ Throwable -> 0x0108 }
            byte[] r0 = (byte[]) r0     // Catch:{ Throwable -> 0x0108 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x0108 }
            java.lang.String r0 = "#"
            java.lang.String[] r4 = r3.split(r0)     // Catch:{ Throwable -> 0x0108 }
            int r0 = r4.length     // Catch:{ Throwable -> 0x0108 }
            r5 = 4
            if (r0 != r5) goto L_0x00f2
            r0 = 0
            r0 = r4[r0]     // Catch:{ Throwable -> 0x0108 }
            boolean r0 = r0.isEmpty()     // Catch:{ Throwable -> 0x0108 }
            if (r0 != 0) goto L_0x010f
            r0 = 0
            r0 = r4[r0]     // Catch:{ Throwable -> 0x0108 }
            java.lang.String r3 = "null"
            boolean r0 = r0.equals(r3)     // Catch:{ Throwable -> 0x0108 }
            if (r0 != 0) goto L_0x010f
            r0 = 0
            r0 = r4[r0]     // Catch:{ Throwable -> 0x00e0 }
            r3 = 0
            byte[] r0 = android.util.Base64.decode(r0, r3)     // Catch:{ Throwable -> 0x00e0 }
            r8.o = r0     // Catch:{ Throwable -> 0x00e0 }
            r0 = r1
        L_0x007c:
            if (r0 != 0) goto L_0x009c
            r3 = 1
            r3 = r4[r3]     // Catch:{ Throwable -> 0x0108 }
            boolean r3 = r3.isEmpty()     // Catch:{ Throwable -> 0x0108 }
            if (r3 != 0) goto L_0x009c
            r3 = 1
            r3 = r4[r3]     // Catch:{ Throwable -> 0x0108 }
            java.lang.String r5 = "null"
            boolean r3 = r3.equals(r5)     // Catch:{ Throwable -> 0x0108 }
            if (r3 != 0) goto L_0x009c
            r3 = 1
            r3 = r4[r3]     // Catch:{ Throwable -> 0x00e6 }
            long r6 = java.lang.Long.parseLong(r3)     // Catch:{ Throwable -> 0x00e6 }
            r8.p = r6     // Catch:{ Throwable -> 0x00e6 }
        L_0x009c:
            if (r0 != 0) goto L_0x00b8
            r3 = 2
            r3 = r4[r3]     // Catch:{ Throwable -> 0x0108 }
            boolean r3 = r3.isEmpty()     // Catch:{ Throwable -> 0x0108 }
            if (r3 != 0) goto L_0x00b8
            r3 = 2
            r3 = r4[r3]     // Catch:{ Throwable -> 0x0108 }
            java.lang.String r5 = "null"
            boolean r3 = r3.equals(r5)     // Catch:{ Throwable -> 0x0108 }
            if (r3 != 0) goto L_0x00b8
            r3 = 2
            r3 = r4[r3]     // Catch:{ Throwable -> 0x0108 }
            r8.q = r3     // Catch:{ Throwable -> 0x0108 }
        L_0x00b8:
            if (r0 != 0) goto L_0x00d8
            r3 = 3
            r3 = r4[r3]     // Catch:{ Throwable -> 0x0108 }
            boolean r3 = r3.isEmpty()     // Catch:{ Throwable -> 0x0108 }
            if (r3 != 0) goto L_0x00d8
            r3 = 3
            r3 = r4[r3]     // Catch:{ Throwable -> 0x0108 }
            java.lang.String r5 = "null"
            boolean r3 = r3.equals(r5)     // Catch:{ Throwable -> 0x0108 }
            if (r3 != 0) goto L_0x00d8
            r3 = 3
            r3 = r4[r3]     // Catch:{ Throwable -> 0x00ec }
            long r4 = java.lang.Long.parseLong(r3)     // Catch:{ Throwable -> 0x00ec }
            r8.r = r4     // Catch:{ Throwable -> 0x00ec }
        L_0x00d8:
            if (r0 == 0) goto L_0x00dd
            r8.c()     // Catch:{ Throwable -> 0x0108 }
        L_0x00dd:
            r0 = r2
            goto L_0x002f
        L_0x00e0:
            r0 = move-exception
            com.tencent.bugly.proguard.an.a(r0)     // Catch:{ Throwable -> 0x0108 }
            r0 = r2
            goto L_0x007c
        L_0x00e6:
            r0 = move-exception
            com.tencent.bugly.proguard.an.a(r0)     // Catch:{ Throwable -> 0x0108 }
            r0 = r2
            goto L_0x009c
        L_0x00ec:
            r0 = move-exception
            com.tencent.bugly.proguard.an.a(r0)     // Catch:{ Throwable -> 0x0108 }
            r0 = r2
            goto L_0x00d8
        L_0x00f2:
            java.lang.String r0 = "SecurityInfo = %s, Strings.length = %d"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0108 }
            r6 = 0
            r5[r6] = r3     // Catch:{ Throwable -> 0x0108 }
            r3 = 1
            int r4 = r4.length     // Catch:{ Throwable -> 0x0108 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0108 }
            r5[r3] = r4     // Catch:{ Throwable -> 0x0108 }
            com.tencent.bugly.proguard.an.a(r0, r5)     // Catch:{ Throwable -> 0x0108 }
            r0 = r2
            goto L_0x00d8
        L_0x0108:
            r0 = move-exception
            com.tencent.bugly.proguard.an.a(r0)
            r0 = r1
            goto L_0x002f
        L_0x010f:
            r0 = r1
            goto L_0x007c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ak.e():boolean");
    }

    /* access modifiers changed from: protected */
    public boolean b() {
        if (this.q == null || this.r == 0) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis() + this.n;
        if (this.r >= currentTimeMillis) {
            return true;
        }
        an.c("[UploadManager] Session ID expired time from server is: %d(%s), but now is: %d(%s)", Long.valueOf(this.r), new Date(this.r).toString(), Long.valueOf(currentTimeMillis), new Date(currentTimeMillis).toString());
        return false;
    }

    /* access modifiers changed from: protected */
    public void b(boolean z) {
        synchronized (this.s) {
            an.c("[UploadManager] Clear security context (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            this.o = null;
            this.q = null;
            this.r = 0;
        }
        if (z) {
            c();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:107:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0086, code lost:
        if (r15 <= 0) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0088, code lost:
        com.tencent.bugly.proguard.an.c("[UploadManager] Execute urgent upload tasks of queue which has %d tasks (pid=%d | tid=%d)", java.lang.Integer.valueOf(r15), java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00aa, code lost:
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00ab, code lost:
        if (r3 >= r15) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ad, code lost:
        r0 = (java.lang.Runnable) r5.poll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b3, code lost:
        if (r0 != null) goto L_0x0131;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b5, code lost:
        if (r1 <= 0) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b7, code lost:
        com.tencent.bugly.proguard.an.c("[UploadManager] Execute upload tasks of queue which has %d tasks (pid=%d | tid=%d)", java.lang.Integer.valueOf(r1), java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00d9, code lost:
        if (r4 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00db, code lost:
        r4.a(new com.tencent.bugly.proguard.ak.AnonymousClass2(r14));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0131, code lost:
        r7 = r14.k;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0133, code lost:
        monitor-enter(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0136, code lost:
        if (r14.v < 2) goto L_0x0143;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0138, code lost:
        if (r4 == null) goto L_0x0143;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x013a, code lost:
        r4.a(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x013d, code lost:
        monitor-exit(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x013e, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0143, code lost:
        monitor-exit(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0144, code lost:
        com.tencent.bugly.proguard.an.a("[UploadManager] Create and start a new thread to execute a upload task: %s", "BUGLY_ASYNC_UPLOAD");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x015d, code lost:
        if (com.tencent.bugly.proguard.ap.a((java.lang.Runnable) new com.tencent.bugly.proguard.ak.AnonymousClass1(r14), "BUGLY_ASYNC_UPLOAD") == null) goto L_0x0170;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x015f, code lost:
        r7 = r14.k;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0161, code lost:
        monitor-enter(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
        r14.v++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0168, code lost:
        monitor-exit(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0170, code lost:
        com.tencent.bugly.proguard.an.d("[UploadManager] Failed to start a thread to execute asynchronous upload task, will try again next time.", new java.lang.Object[0]);
        a(r0, true);
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x006e A[Catch:{ Throwable -> 0x0100 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007b A[Catch:{ Throwable -> 0x0100 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c(int r15) {
        /*
            r14 = this;
            r13 = 3
            r12 = 2
            r11 = 1
            r2 = 0
            if (r15 >= 0) goto L_0x000f
            java.lang.String r0 = "[UploadManager] Number of task to execute should >= 0"
            java.lang.Object[] r1 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.an.a(r0, r1)
        L_0x000e:
            return
        L_0x000f:
            com.tencent.bugly.proguard.am r4 = com.tencent.bugly.proguard.am.a()
            java.util.concurrent.LinkedBlockingQueue r5 = new java.util.concurrent.LinkedBlockingQueue
            r5.<init>()
            java.util.concurrent.LinkedBlockingQueue r6 = new java.util.concurrent.LinkedBlockingQueue
            r6.<init>()
            java.lang.Object r7 = r14.k
            monitor-enter(r7)
            java.lang.String r0 = "[UploadManager] Try to poll all upload task need and put them into temp queue (pid=%d | tid=%d)"
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x005a }
            r3 = 0
            int r8 = android.os.Process.myPid()     // Catch:{ all -> 0x005a }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x005a }
            r1[r3] = r8     // Catch:{ all -> 0x005a }
            r3 = 1
            int r8 = android.os.Process.myTid()     // Catch:{ all -> 0x005a }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x005a }
            r1[r3] = r8     // Catch:{ all -> 0x005a }
            com.tencent.bugly.proguard.an.c(r0, r1)     // Catch:{ all -> 0x005a }
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r0 = r14.i     // Catch:{ all -> 0x005a }
            int r1 = r0.size()     // Catch:{ all -> 0x005a }
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r0 = r14.j     // Catch:{ all -> 0x005a }
            int r0 = r0.size()     // Catch:{ all -> 0x005a }
            if (r1 != 0) goto L_0x005d
            if (r0 != 0) goto L_0x005d
            java.lang.String r0 = "[UploadManager] There is no upload task in queue."
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x005a }
            com.tencent.bugly.proguard.an.c(r0, r1)     // Catch:{ all -> 0x005a }
            monitor-exit(r7)     // Catch:{ all -> 0x005a }
            goto L_0x000e
        L_0x005a:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x005a }
            throw r0
        L_0x005d:
            if (r15 != 0) goto L_0x00e5
            int r3 = r1 + r0
            r15 = r1
        L_0x0062:
            if (r4 == 0) goto L_0x006a
            boolean r1 = r4.c()     // Catch:{ all -> 0x005a }
            if (r1 != 0) goto L_0x017c
        L_0x006a:
            r1 = r2
        L_0x006b:
            r3 = r2
        L_0x006c:
            if (r3 >= r15) goto L_0x0078
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r0 = r14.i     // Catch:{ all -> 0x005a }
            java.lang.Object r0 = r0.peek()     // Catch:{ all -> 0x005a }
            java.lang.Runnable r0 = (java.lang.Runnable) r0     // Catch:{ all -> 0x005a }
            if (r0 != 0) goto L_0x00f3
        L_0x0078:
            r3 = r2
        L_0x0079:
            if (r3 >= r1) goto L_0x0085
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r0 = r14.j     // Catch:{ all -> 0x005a }
            java.lang.Object r0 = r0.peek()     // Catch:{ all -> 0x005a }
            java.lang.Runnable r0 = (java.lang.Runnable) r0     // Catch:{ all -> 0x005a }
            if (r0 != 0) goto L_0x0112
        L_0x0085:
            monitor-exit(r7)     // Catch:{ all -> 0x005a }
            if (r15 <= 0) goto L_0x00aa
            java.lang.String r0 = "[UploadManager] Execute urgent upload tasks of queue which has %d tasks (pid=%d | tid=%d)"
            java.lang.Object[] r3 = new java.lang.Object[r13]
            java.lang.Integer r7 = java.lang.Integer.valueOf(r15)
            r3[r2] = r7
            int r7 = android.os.Process.myPid()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r3[r11] = r7
            int r7 = android.os.Process.myTid()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r3[r12] = r7
            com.tencent.bugly.proguard.an.c(r0, r3)
        L_0x00aa:
            r3 = r2
        L_0x00ab:
            if (r3 >= r15) goto L_0x00b5
            java.lang.Object r0 = r5.poll()
            java.lang.Runnable r0 = (java.lang.Runnable) r0
            if (r0 != 0) goto L_0x0131
        L_0x00b5:
            if (r1 <= 0) goto L_0x00d9
            java.lang.String r0 = "[UploadManager] Execute upload tasks of queue which has %d tasks (pid=%d | tid=%d)"
            java.lang.Object[] r3 = new java.lang.Object[r13]
            java.lang.Integer r5 = java.lang.Integer.valueOf(r1)
            r3[r2] = r5
            int r2 = android.os.Process.myPid()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3[r11] = r2
            int r2 = android.os.Process.myTid()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3[r12] = r2
            com.tencent.bugly.proguard.an.c(r0, r3)
        L_0x00d9:
            if (r4 == 0) goto L_0x000e
            com.tencent.bugly.proguard.ak$2 r0 = new com.tencent.bugly.proguard.ak$2
            r0.<init>(r1, r6)
            r4.a(r0)
            goto L_0x000e
        L_0x00e5:
            if (r15 >= r1) goto L_0x00ea
            r0 = r2
            goto L_0x0062
        L_0x00ea:
            int r3 = r1 + r0
            if (r15 >= r3) goto L_0x017f
            int r0 = r15 - r1
            r15 = r1
            goto L_0x0062
        L_0x00f3:
            r5.put(r0)     // Catch:{ Throwable -> 0x0100 }
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r0 = r14.i     // Catch:{ Throwable -> 0x0100 }
            r0.poll()     // Catch:{ Throwable -> 0x0100 }
        L_0x00fb:
            int r0 = r3 + 1
            r3 = r0
            goto L_0x006c
        L_0x0100:
            r0 = move-exception
            java.lang.String r8 = "[UploadManager] Failed to add upload task to temp urgent queue: %s"
            r9 = 1
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x005a }
            r10 = 0
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x005a }
            r9[r10] = r0     // Catch:{ all -> 0x005a }
            com.tencent.bugly.proguard.an.e(r8, r9)     // Catch:{ all -> 0x005a }
            goto L_0x00fb
        L_0x0112:
            r6.put(r0)     // Catch:{ Throwable -> 0x011f }
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r0 = r14.j     // Catch:{ Throwable -> 0x011f }
            r0.poll()     // Catch:{ Throwable -> 0x011f }
        L_0x011a:
            int r0 = r3 + 1
            r3 = r0
            goto L_0x0079
        L_0x011f:
            r0 = move-exception
            java.lang.String r8 = "[UploadManager] Failed to add upload task to temp urgent queue: %s"
            r9 = 1
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x005a }
            r10 = 0
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x005a }
            r9[r10] = r0     // Catch:{ all -> 0x005a }
            com.tencent.bugly.proguard.an.e(r8, r9)     // Catch:{ all -> 0x005a }
            goto L_0x011a
        L_0x0131:
            java.lang.Object r7 = r14.k
            monitor-enter(r7)
            int r8 = r14.v     // Catch:{ all -> 0x016d }
            if (r8 < r12) goto L_0x0143
            if (r4 == 0) goto L_0x0143
            r4.a(r0)     // Catch:{ all -> 0x016d }
            monitor-exit(r7)     // Catch:{ all -> 0x016d }
        L_0x013e:
            int r0 = r3 + 1
            r3 = r0
            goto L_0x00ab
        L_0x0143:
            monitor-exit(r7)     // Catch:{ all -> 0x016d }
            java.lang.String r7 = "[UploadManager] Create and start a new thread to execute a upload task: %s"
            java.lang.Object[] r8 = new java.lang.Object[r11]
            java.lang.String r9 = "BUGLY_ASYNC_UPLOAD"
            r8[r2] = r9
            com.tencent.bugly.proguard.an.a(r7, r8)
            com.tencent.bugly.proguard.ak$1 r7 = new com.tencent.bugly.proguard.ak$1
            r7.<init>(r0)
            java.lang.String r8 = "BUGLY_ASYNC_UPLOAD"
            java.lang.Thread r7 = com.tencent.bugly.proguard.ap.a(r7, r8)
            if (r7 == 0) goto L_0x0170
            java.lang.Object r7 = r14.k
            monitor-enter(r7)
            int r0 = r14.v     // Catch:{ all -> 0x016a }
            int r0 = r0 + 1
            r14.v = r0     // Catch:{ all -> 0x016a }
            monitor-exit(r7)     // Catch:{ all -> 0x016a }
            goto L_0x013e
        L_0x016a:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x016a }
            throw r0
        L_0x016d:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x016d }
            throw r0
        L_0x0170:
            java.lang.String r7 = "[UploadManager] Failed to start a thread to execute asynchronous upload task, will try again next time."
            java.lang.Object[] r8 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.an.d(r7, r8)
            r14.a(r0, r11)
            goto L_0x013e
        L_0x017c:
            r1 = r0
            goto L_0x006b
        L_0x017f:
            r15 = r1
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ak.c(int):void");
    }

    private boolean a(Runnable runnable, boolean z) {
        if (runnable == null) {
            an.a("[UploadManager] Upload task should not be null", new Object[0]);
            return false;
        }
        try {
            an.c("[UploadManager] Add upload task to queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            synchronized (this.k) {
                if (z) {
                    this.i.put(runnable);
                } else {
                    this.j.put(runnable);
                }
            }
            return true;
        } catch (Throwable th) {
            an.e("[UploadManager] Failed to add upload task to queue: %s", th.getMessage());
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void a(Runnable runnable, long j2) {
        if (runnable == null) {
            an.d("[UploadManager] Upload task should not be null", new Object[0]);
            return;
        }
        an.c("[UploadManager] Execute synchronized upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        Thread a2 = ap.a(runnable, "BUGLY_SYNC_UPLOAD");
        if (a2 == null) {
            an.e("[UploadManager] Failed to start a thread to execute synchronized upload task, add it to queue.", new Object[0]);
            a(runnable, true);
            return;
        }
        try {
            a2.join(j2);
        } catch (Throwable th) {
            an.e("[UploadManager] Failed to join upload synchronized task with message: %s. Add it to queue.", th.getMessage());
            a(runnable, true);
            c(0);
        }
    }

    private void a(Runnable runnable, boolean z, boolean z2, long j2) {
        an.c("[UploadManager] Initialize security context now (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        if (z2) {
            a((Runnable) new a(this.e, runnable, j2), 0);
            return;
        }
        a(runnable, z);
        a aVar = new a(this.e);
        an.a("[UploadManager] Create and start a new thread to execute a task of initializing security context: %s", "BUGLY_ASYNC_UPLOAD");
        if (ap.a((Runnable) aVar, "BUGLY_ASYNC_UPLOAD") == null) {
            an.d("[UploadManager] Failed to start a thread to execute task of initializing security context, try to post it into thread pool.", new Object[0]);
            am a2 = am.a();
            if (a2 != null) {
                a2.a(aVar);
                return;
            }
            an.e("[UploadManager] Asynchronous thread pool is unavailable now, try next time.", new Object[0]);
            synchronized (this.u) {
                this.t = false;
            }
        }
    }

    private void b(Runnable runnable, boolean z, boolean z2, long j2) {
        if (runnable == null) {
            an.d("[UploadManager] Upload task should not be null", new Object[0]);
        }
        an.c("[UploadManager] Add upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        if (this.q != null) {
            if (b()) {
                an.c("[UploadManager] Sucessfully got session ID, try to execute upload task now (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                if (z2) {
                    a(runnable, j2);
                    return;
                }
                a(runnable, z);
                c(0);
                return;
            }
            an.a("[UploadManager] Session ID is expired, drop it (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            b(false);
        }
        synchronized (this.u) {
            if (this.t) {
                a(runnable, z);
                return;
            }
            this.t = true;
            a(runnable, z, z2, j2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004d, code lost:
        if (r11 == null) goto L_0x013f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x004f, code lost:
        com.tencent.bugly.proguard.an.c("[UploadManager] Record security context (pid=%d | tid=%d)", java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r3 = r11.h;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006d, code lost:
        if (r3 == null) goto L_0x0128;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0076, code lost:
        if (r3.containsKey("S1") == false) goto L_0x0128;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x007f, code lost:
        if (r3.containsKey("S2") == false) goto L_0x0128;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0081, code lost:
        r9.n = r11.e - java.lang.System.currentTimeMillis();
        com.tencent.bugly.proguard.an.c("[UploadManager] Time lag of server is: %d", java.lang.Long.valueOf(r9.n));
        r9.q = (java.lang.String) r3.get("S1");
        com.tencent.bugly.proguard.an.c("[UploadManager] Session ID from server is: %s", r9.q);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00bb, code lost:
        if (r9.q.length() <= 0) goto L_0x0135;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r9.r = java.lang.Long.parseLong((java.lang.String) r3.get("S2"));
        com.tencent.bugly.proguard.an.c("[UploadManager] Session expired time from server is: %d(%s)", java.lang.Long.valueOf(r9.r), new java.util.Date(r9.r).toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f2, code lost:
        if (r9.r >= 1000) goto L_0x0102;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00f4, code lost:
        com.tencent.bugly.proguard.an.d("[UploadManager] Session expired time from server is less than 1 second, will set to default value", new java.lang.Object[0]);
        r9.r = 259200000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        com.tencent.bugly.proguard.an.d("[UploadManager] Session expired time is invalid, will set to default value", new java.lang.Object[0]);
        r9.r = 259200000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0124, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0135, code lost:
        com.tencent.bugly.proguard.an.c("[UploadManager] Session ID from server is invalid, try next time", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x013f, code lost:
        com.tencent.bugly.proguard.an.c("[UploadManager] Fail to init security context and clear local info (pid=%d | tid=%d)", java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
        b(false);
     */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x010f  */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x002c A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r10, com.tencent.bugly.proguard.be r11) {
        /*
            r9 = this;
            r4 = 2
            r1 = 1
            r2 = 0
            boolean r0 = r9.b
            if (r0 != 0) goto L_0x0008
        L_0x0007:
            return
        L_0x0008:
            if (r10 != r4) goto L_0x0040
            java.lang.String r0 = "[UploadManager] Session ID is invalid, will clear security context (pid=%d | tid=%d)"
            java.lang.Object[] r3 = new java.lang.Object[r4]
            int r4 = android.os.Process.myPid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r2] = r4
            int r2 = android.os.Process.myTid()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3[r1] = r2
            com.tencent.bugly.proguard.an.c(r0, r3)
            r9.b(r1)
        L_0x0029:
            java.lang.Object r1 = r9.u
            monitor-enter(r1)
            boolean r0 = r9.t     // Catch:{ all -> 0x003d }
            if (r0 == 0) goto L_0x003b
            r0 = 0
            r9.t = r0     // Catch:{ all -> 0x003d }
            android.content.Context r0 = r9.e     // Catch:{ all -> 0x003d }
            java.lang.String r2 = "security_info"
            com.tencent.bugly.proguard.ap.c(r0, r2)     // Catch:{ all -> 0x003d }
        L_0x003b:
            monitor-exit(r1)     // Catch:{ all -> 0x003d }
            goto L_0x0007
        L_0x003d:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x003d }
            throw r0
        L_0x0040:
            java.lang.Object r3 = r9.u
            monitor-enter(r3)
            boolean r0 = r9.t     // Catch:{ all -> 0x0049 }
            if (r0 != 0) goto L_0x004c
            monitor-exit(r3)     // Catch:{ all -> 0x0049 }
            goto L_0x0007
        L_0x0049:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0049 }
            throw r0
        L_0x004c:
            monitor-exit(r3)     // Catch:{ all -> 0x0049 }
            if (r11 == 0) goto L_0x013f
            java.lang.String r0 = "[UploadManager] Record security context (pid=%d | tid=%d)"
            java.lang.Object[] r3 = new java.lang.Object[r4]
            int r4 = android.os.Process.myPid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r2] = r4
            int r4 = android.os.Process.myTid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r1] = r4
            com.tencent.bugly.proguard.an.c(r0, r3)
            java.util.Map<java.lang.String, java.lang.String> r3 = r11.h     // Catch:{ Throwable -> 0x0124 }
            if (r3 == 0) goto L_0x0128
            java.lang.String r0 = "S1"
            boolean r0 = r3.containsKey(r0)     // Catch:{ Throwable -> 0x0124 }
            if (r0 == 0) goto L_0x0128
            java.lang.String r0 = "S2"
            boolean r0 = r3.containsKey(r0)     // Catch:{ Throwable -> 0x0124 }
            if (r0 == 0) goto L_0x0128
            long r4 = r11.e     // Catch:{ Throwable -> 0x0124 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0124 }
            long r4 = r4 - r6
            r9.n = r4     // Catch:{ Throwable -> 0x0124 }
            java.lang.String r0 = "[UploadManager] Time lag of server is: %d"
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0124 }
            r5 = 0
            long r6 = r9.n     // Catch:{ Throwable -> 0x0124 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ Throwable -> 0x0124 }
            r4[r5] = r6     // Catch:{ Throwable -> 0x0124 }
            com.tencent.bugly.proguard.an.c(r0, r4)     // Catch:{ Throwable -> 0x0124 }
            java.lang.String r0 = "S1"
            java.lang.Object r0 = r3.get(r0)     // Catch:{ Throwable -> 0x0124 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0124 }
            r9.q = r0     // Catch:{ Throwable -> 0x0124 }
            java.lang.String r0 = "[UploadManager] Session ID from server is: %s"
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0124 }
            r5 = 0
            java.lang.String r6 = r9.q     // Catch:{ Throwable -> 0x0124 }
            r4[r5] = r6     // Catch:{ Throwable -> 0x0124 }
            com.tencent.bugly.proguard.an.c(r0, r4)     // Catch:{ Throwable -> 0x0124 }
            java.lang.String r0 = r9.q     // Catch:{ Throwable -> 0x0124 }
            int r0 = r0.length()     // Catch:{ Throwable -> 0x0124 }
            if (r0 <= 0) goto L_0x0135
            java.lang.String r0 = "S2"
            java.lang.Object r0 = r3.get(r0)     // Catch:{ NumberFormatException -> 0x0114 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ NumberFormatException -> 0x0114 }
            long r4 = java.lang.Long.parseLong(r0)     // Catch:{ NumberFormatException -> 0x0114 }
            r9.r = r4     // Catch:{ NumberFormatException -> 0x0114 }
            java.lang.String r0 = "[UploadManager] Session expired time from server is: %d(%s)"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ NumberFormatException -> 0x0114 }
            r4 = 0
            long r6 = r9.r     // Catch:{ NumberFormatException -> 0x0114 }
            java.lang.Long r5 = java.lang.Long.valueOf(r6)     // Catch:{ NumberFormatException -> 0x0114 }
            r3[r4] = r5     // Catch:{ NumberFormatException -> 0x0114 }
            r4 = 1
            java.util.Date r5 = new java.util.Date     // Catch:{ NumberFormatException -> 0x0114 }
            long r6 = r9.r     // Catch:{ NumberFormatException -> 0x0114 }
            r5.<init>(r6)     // Catch:{ NumberFormatException -> 0x0114 }
            java.lang.String r5 = r5.toString()     // Catch:{ NumberFormatException -> 0x0114 }
            r3[r4] = r5     // Catch:{ NumberFormatException -> 0x0114 }
            com.tencent.bugly.proguard.an.c(r0, r3)     // Catch:{ NumberFormatException -> 0x0114 }
            long r4 = r9.r     // Catch:{ NumberFormatException -> 0x0114 }
            r6 = 1000(0x3e8, double:4.94E-321)
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 >= 0) goto L_0x0102
            java.lang.String r0 = "[UploadManager] Session expired time from server is less than 1 second, will set to default value"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ NumberFormatException -> 0x0114 }
            com.tencent.bugly.proguard.an.d(r0, r3)     // Catch:{ NumberFormatException -> 0x0114 }
            r4 = 259200000(0xf731400, double:1.280618154E-315)
            r9.r = r4     // Catch:{ NumberFormatException -> 0x0114 }
        L_0x0102:
            boolean r0 = r9.d()     // Catch:{ Throwable -> 0x0124 }
            if (r0 == 0) goto L_0x012a
            r0 = r2
        L_0x0109:
            r1 = 0
            r9.c(r1)     // Catch:{ Throwable -> 0x0160 }
        L_0x010d:
            if (r0 == 0) goto L_0x0029
            r9.b(r2)
            goto L_0x0029
        L_0x0114:
            r0 = move-exception
            java.lang.String r0 = "[UploadManager] Session expired time is invalid, will set to default value"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0124 }
            com.tencent.bugly.proguard.an.d(r0, r3)     // Catch:{ Throwable -> 0x0124 }
            r4 = 259200000(0xf731400, double:1.280618154E-315)
            r9.r = r4     // Catch:{ Throwable -> 0x0124 }
            goto L_0x0102
        L_0x0124:
            r0 = move-exception
        L_0x0125:
            com.tencent.bugly.proguard.an.a(r0)
        L_0x0128:
            r0 = r1
            goto L_0x010d
        L_0x012a:
            java.lang.String r0 = "[UploadManager] Failed to record database"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0124 }
            com.tencent.bugly.proguard.an.c(r0, r3)     // Catch:{ Throwable -> 0x0124 }
            r0 = r1
            goto L_0x0109
        L_0x0135:
            java.lang.String r0 = "[UploadManager] Session ID from server is invalid, try next time"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0124 }
            com.tencent.bugly.proguard.an.c(r0, r3)     // Catch:{ Throwable -> 0x0124 }
            goto L_0x0128
        L_0x013f:
            java.lang.String r0 = "[UploadManager] Fail to init security context and clear local info (pid=%d | tid=%d)"
            java.lang.Object[] r3 = new java.lang.Object[r4]
            int r4 = android.os.Process.myPid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r2] = r4
            int r4 = android.os.Process.myTid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r1] = r4
            com.tencent.bugly.proguard.an.c(r0, r3)
            r9.b(r2)
            goto L_0x0029
        L_0x0160:
            r1 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
            goto L_0x0125
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ak.a(int, com.tencent.bugly.proguard.be):void");
    }

    public byte[] a(byte[] bArr) {
        if (this.o != null && this.o.length * 8 == 128) {
            return ap.a(1, bArr, this.o);
        }
        an.d("[UploadManager] AES key is invalid (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        return null;
    }

    public byte[] b(byte[] bArr) {
        if (this.o != null && this.o.length * 8 == 128) {
            return ap.a(2, bArr, this.o);
        }
        an.d("[UploadManager] AES key is invalid (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        return null;
    }

    public boolean a(Map<String, String> map) {
        if (map == null) {
            return false;
        }
        an.c("[UploadManager] Integrate security to HTTP headers (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        if (this.q != null) {
            map.put("secureSessionId", this.q);
            return true;
        } else if (this.o == null || this.o.length * 8 != 128) {
            an.d("[UploadManager] AES key is invalid", new Object[0]);
            return false;
        } else {
            if (this.m == null) {
                this.m = Base64.decode(this.l, 0);
                if (this.m == null) {
                    an.d("[UploadManager] Failed to decode RSA public key", new Object[0]);
                    return false;
                }
            }
            byte[] b2 = ap.b(1, this.o, this.m);
            if (b2 == null) {
                an.d("[UploadManager] Failed to encrypt AES key", new Object[0]);
                return false;
            }
            String encodeToString = Base64.encodeToString(b2, 0);
            if (encodeToString == null) {
                an.d("[UploadManager] Failed to encode AES key", new Object[0]);
                return false;
            }
            map.put("raKey", encodeToString);
            return true;
        }
    }
}
