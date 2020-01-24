package com.tencent.bugly.crashreport.crash.anr;

import android.app.ActivityManager;
import android.app.ActivityManager.ProcessErrorStateInfo;
import android.content.Context;
import android.os.FileObserver;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.ae;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ao;
import com.tencent.bugly.proguard.ap;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

/* compiled from: BUGLY */
public class b {
    private AtomicInteger a = new AtomicInteger(0);
    private long b = -1;
    private final Context c;
    private final a d;
    private final am e;
    private final com.tencent.bugly.crashreport.common.strategy.a f;
    private final String g;
    private final com.tencent.bugly.crashreport.crash.b h;
    private FileObserver i;
    private boolean j = true;

    public b(Context context, com.tencent.bugly.crashreport.common.strategy.a aVar, a aVar2, am amVar, ae aeVar, com.tencent.bugly.crashreport.crash.b bVar, BuglyStrategy.a aVar3) {
        this.c = ap.a(context);
        this.g = context.getDir("bugly", 0).getAbsolutePath();
        this.d = aVar2;
        this.e = amVar;
        this.f = aVar;
        this.h = bVar;
    }

    /* access modifiers changed from: protected */
    public ProcessErrorStateInfo a(Context context, long j2) {
        if (j2 < 0) {
            j2 = 0;
        }
        an.c("to find!", new Object[0]);
        ActivityManager activityManager = (ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME);
        long j3 = j2 / 500;
        int i2 = 0;
        while (true) {
            an.c("waiting!", new Object[0]);
            List<ProcessErrorStateInfo> processesInErrorState = activityManager.getProcessesInErrorState();
            if (processesInErrorState != null) {
                for (ProcessErrorStateInfo processErrorStateInfo : processesInErrorState) {
                    if (processErrorStateInfo.condition == 2) {
                        an.c("found!", new Object[0]);
                        return processErrorStateInfo;
                    }
                }
            }
            ap.b(500);
            int i3 = i2 + 1;
            if (((long) i2) >= j3) {
                an.c("end!", new Object[0]);
                return null;
            }
            i2 = i3;
        }
    }

    /* access modifiers changed from: protected */
    public a a(Context context, ProcessErrorStateInfo processErrorStateInfo, long j2, Map<String, String> map) {
        int size;
        File file = new File(context.getFilesDir(), "bugly/bugly_trace_" + j2 + ".txt");
        a aVar = new a();
        aVar.c = j2;
        aVar.d = file.getAbsolutePath();
        aVar.a = processErrorStateInfo.processName;
        aVar.f = processErrorStateInfo.shortMsg;
        aVar.e = processErrorStateInfo.longMsg;
        aVar.b = map;
        if (map != null) {
            for (String str : map.keySet()) {
                if (str.startsWith("main(")) {
                    aVar.g = (String) map.get(str);
                }
            }
        }
        String str2 = "anr tm:%d\ntr:%s\nproc:%s\nsMsg:%s\n lMsg:%s\n threads:%d";
        Object[] objArr = new Object[6];
        objArr[0] = Long.valueOf(aVar.c);
        objArr[1] = aVar.d;
        objArr[2] = aVar.a;
        objArr[3] = aVar.f;
        objArr[4] = aVar.e;
        if (aVar.b == null) {
            size = 0;
        } else {
            size = aVar.b.size();
        }
        objArr[5] = Integer.valueOf(size);
        an.c(str2, objArr);
        return aVar;
    }

    /* access modifiers changed from: protected */
    public CrashDetailBean a(a aVar) {
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        try {
            crashDetailBean.C = com.tencent.bugly.crashreport.common.info.b.i();
            crashDetailBean.D = com.tencent.bugly.crashreport.common.info.b.g();
            crashDetailBean.E = com.tencent.bugly.crashreport.common.info.b.k();
            crashDetailBean.F = this.d.p();
            crashDetailBean.G = this.d.o();
            crashDetailBean.H = this.d.q();
            crashDetailBean.w = ap.a(this.c, c.e, c.h);
            crashDetailBean.b = 3;
            crashDetailBean.e = this.d.h();
            crashDetailBean.f = this.d.o;
            crashDetailBean.g = this.d.w();
            crashDetailBean.m = this.d.g();
            crashDetailBean.n = "ANR_EXCEPTION";
            crashDetailBean.o = aVar.f;
            crashDetailBean.q = aVar.g;
            crashDetailBean.P = new HashMap();
            crashDetailBean.P.put("BUGLY_CR_01", aVar.e);
            int i2 = -1;
            if (crashDetailBean.q != null) {
                i2 = crashDetailBean.q.indexOf("\n");
            }
            crashDetailBean.p = i2 > 0 ? crashDetailBean.q.substring(0, i2) : "GET_FAIL";
            crashDetailBean.r = aVar.c;
            if (crashDetailBean.q != null) {
                crashDetailBean.u = ap.b(crashDetailBean.q.getBytes());
            }
            crashDetailBean.z = aVar.b;
            crashDetailBean.A = this.d.e;
            crashDetailBean.B = "main(1)";
            crashDetailBean.I = this.d.y();
            crashDetailBean.h = this.d.v();
            crashDetailBean.i = this.d.J();
            crashDetailBean.v = aVar.d;
            crashDetailBean.M = this.d.s;
            crashDetailBean.N = this.d.a;
            crashDetailBean.O = this.d.a();
            crashDetailBean.Q = this.d.H();
            crashDetailBean.R = this.d.I();
            crashDetailBean.S = this.d.B();
            crashDetailBean.T = this.d.G();
            this.h.c(crashDetailBean);
            crashDetailBean.y = ao.a();
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
        }
        return crashDetailBean;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0192 A[Catch:{ all -> 0x0200 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x01c7 A[SYNTHETIC, Splitter:B:52:0x01c7] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01ef A[SYNTHETIC, Splitter:B:70:0x01ef] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(java.lang.String r12, java.lang.String r13, java.lang.String r14) {
        /*
            r11 = this;
            r10 = 3
            r4 = 2
            r2 = 1
            r3 = 0
            com.tencent.bugly.crashreport.crash.anr.TraceFileHelper$a r5 = com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.readTargetDumpInfo(r14, r12, r2)
            if (r5 == 0) goto L_0x0016
            java.util.Map<java.lang.String, java.lang.String[]> r0 = r5.d
            if (r0 == 0) goto L_0x0016
            java.util.Map<java.lang.String, java.lang.String[]> r0 = r5.d
            int r0 = r0.size()
            if (r0 > 0) goto L_0x0022
        L_0x0016:
            java.lang.String r0 = "not found trace dump for %s"
            java.lang.Object[] r1 = new java.lang.Object[r2]
            r1[r3] = r14
            com.tencent.bugly.proguard.an.e(r0, r1)
            r0 = r3
        L_0x0021:
            return r0
        L_0x0022:
            java.io.File r0 = new java.io.File
            r0.<init>(r13)
            boolean r1 = r0.exists()     // Catch:{ Exception -> 0x0059 }
            if (r1 != 0) goto L_0x0041
            java.io.File r1 = r0.getParentFile()     // Catch:{ Exception -> 0x0059 }
            boolean r1 = r1.exists()     // Catch:{ Exception -> 0x0059 }
            if (r1 != 0) goto L_0x003e
            java.io.File r1 = r0.getParentFile()     // Catch:{ Exception -> 0x0059 }
            r1.mkdirs()     // Catch:{ Exception -> 0x0059 }
        L_0x003e:
            r0.createNewFile()     // Catch:{ Exception -> 0x0059 }
        L_0x0041:
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x004d
            boolean r1 = r0.canWrite()
            if (r1 != 0) goto L_0x0095
        L_0x004d:
            java.lang.String r0 = "backup file create fail %s"
            java.lang.Object[] r1 = new java.lang.Object[r2]
            r1[r3] = r13
            com.tencent.bugly.proguard.an.e(r0, r1)
            r0 = r3
            goto L_0x0021
        L_0x0059:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x0063
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
        L_0x0063:
            java.lang.String r1 = "backup file create error! %s  %s"
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.Class r6 = r0.getClass()
            java.lang.String r6 = r6.getName()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = ":"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r5.append(r0)
            java.lang.String r0 = r0.toString()
            r4[r3] = r0
            r4[r2] = r13
            com.tencent.bugly.proguard.an.e(r1, r4)
            r0 = r3
            goto L_0x0021
        L_0x0095:
            r1 = 0
            java.io.BufferedWriter r4 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x0203, all -> 0x01eb }
            java.io.FileWriter r6 = new java.io.FileWriter     // Catch:{ IOException -> 0x0203, all -> 0x01eb }
            r7 = 0
            r6.<init>(r0, r7)     // Catch:{ IOException -> 0x0203, all -> 0x01eb }
            r4.<init>(r6)     // Catch:{ IOException -> 0x0203, all -> 0x01eb }
            java.util.Map<java.lang.String, java.lang.String[]> r0 = r5.d     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r1 = "main"
            java.lang.Object r0 = r0.get(r1)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String[] r0 = (java.lang.String[]) r0     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            if (r0 == 0) goto L_0x00f1
            int r1 = r0.length     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            if (r1 < r10) goto L_0x00f1
            r1 = 0
            r1 = r0[r1]     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            r6 = 1
            r6 = r0[r6]     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            r7 = 2
            r0 = r0[r7]     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            r7.<init>()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r8 = "\"main\" tid="
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.StringBuilder r0 = r7.append(r0)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r7 = " :\n"
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r1 = "\n"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.StringBuilder r0 = r0.append(r6)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r1 = "\n\n"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            r4.write(r0)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            r4.flush()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
        L_0x00f1:
            java.util.Map<java.lang.String, java.lang.String[]> r0 = r5.d     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.util.Set r0 = r0.entrySet()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.util.Iterator r5 = r0.iterator()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
        L_0x00fb:
            boolean r0 = r5.hasNext()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            if (r0 == 0) goto L_0x01cd
            java.lang.Object r0 = r5.next()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.Object r1 = r0.getKey()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r6 = "main"
            boolean r1 = r1.equals(r6)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            if (r1 != 0) goto L_0x00fb
            java.lang.Object r1 = r0.getValue()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            if (r1 == 0) goto L_0x00fb
            java.lang.Object r1 = r0.getValue()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String[] r1 = (java.lang.String[]) r1     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            int r1 = r1.length     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            if (r1 < r10) goto L_0x00fb
            java.lang.Object r1 = r0.getValue()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String[] r1 = (java.lang.String[]) r1     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            r6 = 0
            r6 = r1[r6]     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.Object r1 = r0.getValue()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String[] r1 = (java.lang.String[]) r1     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            r7 = 1
            r7 = r1[r7]     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.Object r1 = r0.getValue()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String[] r1 = (java.lang.String[]) r1     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            r8 = 2
            r1 = r1[r8]     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            r8.<init>()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r9 = "\""
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.Object r0 = r0.getKey()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.StringBuilder r0 = r8.append(r0)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r8 = "\" tid="
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r1 = " :\n"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.StringBuilder r0 = r0.append(r6)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r1 = "\n"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r1 = "\n\n"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            r4.write(r0)     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            r4.flush()     // Catch:{ IOException -> 0x018a, all -> 0x01fe }
            goto L_0x00fb
        L_0x018a:
            r0 = move-exception
            r1 = r4
        L_0x018c:
            boolean r2 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x0200 }
            if (r2 != 0) goto L_0x0195
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0200 }
        L_0x0195:
            java.lang.String r2 = "dump trace fail %s"
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0200 }
            r5 = 0
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0200 }
            r6.<init>()     // Catch:{ all -> 0x0200 }
            java.lang.Class r7 = r0.getClass()     // Catch:{ all -> 0x0200 }
            java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x0200 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x0200 }
            java.lang.String r7 = ":"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x0200 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0200 }
            java.lang.StringBuilder r0 = r6.append(r0)     // Catch:{ all -> 0x0200 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0200 }
            r4[r5] = r0     // Catch:{ all -> 0x0200 }
            com.tencent.bugly.proguard.an.e(r2, r4)     // Catch:{ all -> 0x0200 }
            if (r1 == 0) goto L_0x01ca
            r1.close()     // Catch:{ IOException -> 0x01e0 }
        L_0x01ca:
            r0 = r3
            goto L_0x0021
        L_0x01cd:
            if (r4 == 0) goto L_0x01d2
            r4.close()     // Catch:{ IOException -> 0x01d5 }
        L_0x01d2:
            r0 = r2
            goto L_0x0021
        L_0x01d5:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x01d2
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x01d2
        L_0x01e0:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x01ca
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x01ca
        L_0x01eb:
            r0 = move-exception
            r4 = r1
        L_0x01ed:
            if (r4 == 0) goto L_0x01f2
            r4.close()     // Catch:{ IOException -> 0x01f3 }
        L_0x01f2:
            throw r0
        L_0x01f3:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.a(r1)
            if (r2 != 0) goto L_0x01f2
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x01f2
        L_0x01fe:
            r0 = move-exception
            goto L_0x01ed
        L_0x0200:
            r0 = move-exception
            r4 = r1
            goto L_0x01ed
        L_0x0203:
            r0 = move-exception
            goto L_0x018c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.anr.b.a(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    public boolean a() {
        return this.a.get() != 0;
    }

    /* access modifiers changed from: protected */
    public boolean a(Context context, String str, ProcessErrorStateInfo processErrorStateInfo, long j2, Map<String, String> map) {
        a a2 = a(context, processErrorStateInfo, j2, map);
        if (!this.f.b()) {
            an.e("crash report sync remote fail, will not upload to Bugly , print local for helpful!", new Object[0]);
            com.tencent.bugly.crashreport.crash.b.a("ANR", ap.a(), a2.a, "main", a2.e, null);
            return false;
        } else if (!this.f.c().j) {
            an.d("ANR Report is closed!", new Object[0]);
            return false;
        } else {
            an.a("found visiable anr , start to upload!", new Object[0]);
            CrashDetailBean a3 = a(a2);
            if (a3 == null) {
                an.e("pack anr fail!", new Object[0]);
                return false;
            }
            c.a().a(a3);
            if (a3.a >= 0) {
                an.a("backup anr record success!", new Object[0]);
            } else {
                an.d("backup anr record fail!", new Object[0]);
            }
            if (str != null && new File(str).exists()) {
                this.a.set(3);
                if (a(str, a2.d, a2.a)) {
                    an.a("backup trace success", new Object[0]);
                }
            }
            com.tencent.bugly.crashreport.crash.b.a("ANR", ap.a(), a2.a, "main", a2.e, a3);
            if (!this.h.a(a3)) {
                this.h.a(a3, 3000, true);
            }
            this.h.b(a3);
            return true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        com.tencent.bugly.proguard.an.c("read trace first dump for create time!", new java.lang.Object[0]);
        r2 = com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.readFirstDumpInfo(r11, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002e, code lost:
        if (r2 == null) goto L_0x011e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0030, code lost:
        r4 = r2.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0034, code lost:
        if (r4 != -1) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0036, code lost:
        com.tencent.bugly.proguard.an.d("trace dump fail could not get time!", new java.lang.Object[0]);
        r4 = java.lang.System.currentTimeMillis();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004d, code lost:
        if (java.lang.Math.abs(r4 - r10.b) >= 10000) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004f, code lost:
        com.tencent.bugly.proguard.an.d("should not process ANR too Fre in %d", java.lang.Integer.valueOf(10000));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0061, code lost:
        r10.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        r10.b = r4;
        r10.a.set(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r6 = com.tencent.bugly.proguard.ap.a(com.tencent.bugly.crashreport.crash.c.f, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0079, code lost:
        if (r6 == null) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x007f, code lost:
        if (r6.size() > 0) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0081, code lost:
        com.tencent.bugly.proguard.an.d("can't get all thread skip this anr", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008a, code lost:
        r10.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0090, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        com.tencent.bugly.proguard.an.a(r0);
        com.tencent.bugly.proguard.an.e("get all thread stack fail!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009d, code lost:
        r10.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r3 = a(r10.c, 10000);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ac, code lost:
        if (r3 != null) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ae, code lost:
        com.tencent.bugly.proguard.an.c("proc state is unvisiable!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b7, code lost:
        r10.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c4, code lost:
        if (r3.pid == android.os.Process.myPid()) goto L_0x00db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00c6, code lost:
        com.tencent.bugly.proguard.an.c("not mind proc!", r3.processName);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00d4, code lost:
        r10.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        com.tencent.bugly.proguard.an.a("found visiable anr , start to process!", new java.lang.Object[0]);
        a(r10.c, r11, r3, r4, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00eb, code lost:
        r10.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f2, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00f7, code lost:
        if (com.tencent.bugly.proguard.an.a(r0) == false) goto L_0x00f9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00f9, code lost:
        com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00fc, code lost:
        com.tencent.bugly.proguard.an.e("handle anr error %s", r0.getClass().toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0117, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0118, code lost:
        r10.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x011d, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x011e, code lost:
        r4 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:?, code lost:
        return;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.String r11) {
        /*
            r10 = this;
            r8 = 10000(0x2710, double:4.9407E-320)
            r0 = -1
            r7 = 0
            monitor-enter(r10)
            java.util.concurrent.atomic.AtomicInteger r2 = r10.a     // Catch:{ all -> 0x0067 }
            int r2 = r2.get()     // Catch:{ all -> 0x0067 }
            if (r2 == 0) goto L_0x0019
            java.lang.String r0 = "trace started return "
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0067 }
            com.tencent.bugly.proguard.an.c(r0, r1)     // Catch:{ all -> 0x0067 }
            monitor-exit(r10)     // Catch:{ all -> 0x0067 }
        L_0x0018:
            return
        L_0x0019:
            java.util.concurrent.atomic.AtomicInteger r2 = r10.a     // Catch:{ all -> 0x0067 }
            r3 = 1
            r2.set(r3)     // Catch:{ all -> 0x0067 }
            monitor-exit(r10)     // Catch:{ all -> 0x0067 }
            java.lang.String r2 = "read trace first dump for create time!"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00f2 }
            com.tencent.bugly.proguard.an.c(r2, r3)     // Catch:{ Throwable -> 0x00f2 }
            r2 = 0
            com.tencent.bugly.crashreport.crash.anr.TraceFileHelper$a r2 = com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.readFirstDumpInfo(r11, r2)     // Catch:{ Throwable -> 0x00f2 }
            if (r2 == 0) goto L_0x011e
            long r4 = r2.c     // Catch:{ Throwable -> 0x00f2 }
        L_0x0032:
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 != 0) goto L_0x0043
            java.lang.String r0 = "trace dump fail could not get time!"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00f2 }
            com.tencent.bugly.proguard.an.d(r0, r1)     // Catch:{ Throwable -> 0x00f2 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00f2 }
        L_0x0043:
            long r0 = r10.b     // Catch:{ Throwable -> 0x00f2 }
            long r0 = r4 - r0
            long r0 = java.lang.Math.abs(r0)     // Catch:{ Throwable -> 0x00f2 }
            int r0 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r0 >= 0) goto L_0x006a
            java.lang.String r0 = "should not process ANR too Fre in %d"
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00f2 }
            r2 = 0
            r3 = 10000(0x2710, float:1.4013E-41)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x00f2 }
            r1[r2] = r3     // Catch:{ Throwable -> 0x00f2 }
            com.tencent.bugly.proguard.an.d(r0, r1)     // Catch:{ Throwable -> 0x00f2 }
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a
            r0.set(r7)
            goto L_0x0018
        L_0x0067:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0067 }
            throw r0
        L_0x006a:
            r10.b = r4     // Catch:{ Throwable -> 0x00f2 }
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a     // Catch:{ Throwable -> 0x00f2 }
            r1 = 1
            r0.set(r1)     // Catch:{ Throwable -> 0x00f2 }
            int r0 = com.tencent.bugly.crashreport.crash.c.f     // Catch:{ Throwable -> 0x0090 }
            r1 = 0
            java.util.Map r6 = com.tencent.bugly.proguard.ap.a(r0, r1)     // Catch:{ Throwable -> 0x0090 }
            if (r6 == 0) goto L_0x0081
            int r0 = r6.size()     // Catch:{ Throwable -> 0x00f2 }
            if (r0 > 0) goto L_0x00a4
        L_0x0081:
            java.lang.String r0 = "can't get all thread skip this anr"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00f2 }
            com.tencent.bugly.proguard.an.d(r0, r1)     // Catch:{ Throwable -> 0x00f2 }
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a
            r0.set(r7)
            goto L_0x0018
        L_0x0090:
            r0 = move-exception
            com.tencent.bugly.proguard.an.a(r0)     // Catch:{ Throwable -> 0x00f2 }
            java.lang.String r0 = "get all thread stack fail!"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00f2 }
            com.tencent.bugly.proguard.an.e(r0, r1)     // Catch:{ Throwable -> 0x00f2 }
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a
            r0.set(r7)
            goto L_0x0018
        L_0x00a4:
            android.content.Context r0 = r10.c     // Catch:{ Throwable -> 0x00f2 }
            r2 = 10000(0x2710, double:4.9407E-320)
            android.app.ActivityManager$ProcessErrorStateInfo r3 = r10.a(r0, r2)     // Catch:{ Throwable -> 0x00f2 }
            if (r3 != 0) goto L_0x00be
            java.lang.String r0 = "proc state is unvisiable!"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00f2 }
            com.tencent.bugly.proguard.an.c(r0, r1)     // Catch:{ Throwable -> 0x00f2 }
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a
            r0.set(r7)
            goto L_0x0018
        L_0x00be:
            int r0 = r3.pid     // Catch:{ Throwable -> 0x00f2 }
            int r1 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x00f2 }
            if (r0 == r1) goto L_0x00db
            java.lang.String r0 = "not mind proc!"
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00f2 }
            r2 = 0
            java.lang.String r3 = r3.processName     // Catch:{ Throwable -> 0x00f2 }
            r1[r2] = r3     // Catch:{ Throwable -> 0x00f2 }
            com.tencent.bugly.proguard.an.c(r0, r1)     // Catch:{ Throwable -> 0x00f2 }
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a
            r0.set(r7)
            goto L_0x0018
        L_0x00db:
            java.lang.String r0 = "found visiable anr , start to process!"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00f2 }
            com.tencent.bugly.proguard.an.a(r0, r1)     // Catch:{ Throwable -> 0x00f2 }
            android.content.Context r1 = r10.c     // Catch:{ Throwable -> 0x00f2 }
            r0 = r10
            r2 = r11
            r0.a(r1, r2, r3, r4, r6)     // Catch:{ Throwable -> 0x00f2 }
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a
            r0.set(r7)
            goto L_0x0018
        L_0x00f2:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x0117 }
            if (r1 != 0) goto L_0x00fc
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0117 }
        L_0x00fc:
            java.lang.String r1 = "handle anr error %s"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0117 }
            r3 = 0
            java.lang.Class r0 = r0.getClass()     // Catch:{ all -> 0x0117 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0117 }
            r2[r3] = r0     // Catch:{ all -> 0x0117 }
            com.tencent.bugly.proguard.an.e(r1, r2)     // Catch:{ all -> 0x0117 }
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a
            r0.set(r7)
            goto L_0x0018
        L_0x0117:
            r0 = move-exception
            java.util.concurrent.atomic.AtomicInteger r1 = r10.a
            r1.set(r7)
            throw r0
        L_0x011e:
            r4 = r0
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.anr.b.a(java.lang.String):void");
    }

    /* access modifiers changed from: protected */
    public synchronized void b() {
        if (d()) {
            an.d("start when started!", new Object[0]);
        } else {
            this.i = new FileObserver("/data/anr/", 8) {
                public void onEvent(int event, String path) {
                    if (path != null) {
                        String str = "/data/anr/" + path;
                        if (!str.contains("trace")) {
                            an.d("not anr file %s", str);
                            return;
                        }
                        b.this.a(str);
                    }
                }
            };
            try {
                this.i.startWatching();
                an.a("start anr monitor!", new Object[0]);
                this.e.a(new Runnable() {
                    public void run() {
                        b.this.f();
                    }
                });
            } catch (Throwable th) {
                this.i = null;
                an.d("start anr monitor failed!", new Object[0]);
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void c() {
        if (!d()) {
            an.d("close when closed!", new Object[0]);
        } else {
            try {
                this.i.stopWatching();
                this.i = null;
                an.d("close anr monitor!", new Object[0]);
            } catch (Throwable th) {
                an.d("stop anr monitor failed!", new Object[0]);
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public synchronized boolean d() {
        return this.i != null;
    }

    /* access modifiers changed from: protected */
    public synchronized void a(boolean z) {
        if (z) {
            b();
        } else {
            c();
        }
    }

    public synchronized boolean e() {
        return this.j;
    }

    private synchronized void c(boolean z) {
        if (this.j != z) {
            an.a("user change anr %b", Boolean.valueOf(z));
            this.j = z;
        }
    }

    public void b(boolean z) {
        c(z);
        boolean e2 = e();
        com.tencent.bugly.crashreport.common.strategy.a a2 = com.tencent.bugly.crashreport.common.strategy.a.a();
        if (a2 != null) {
            e2 = e2 && a2.c().g;
        }
        if (e2 != d()) {
            an.a("anr changed to %b", Boolean.valueOf(e2));
            a(e2);
        }
    }

    /* access modifiers changed from: protected */
    public void f() {
        long b2 = ap.b() - c.g;
        File file = new File(this.g);
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                String str = "bugly_trace_";
                String str2 = ".txt";
                int length = str.length();
                int i2 = 0;
                for (File file2 : listFiles) {
                    String name = file2.getName();
                    if (name.startsWith(str)) {
                        try {
                            int indexOf = name.indexOf(str2);
                            if (indexOf > 0 && Long.parseLong(name.substring(length, indexOf)) >= b2) {
                            }
                        } catch (Throwable th) {
                            an.e("tomb format error delete %s", name);
                        }
                        if (file2.delete()) {
                            i2++;
                        }
                    }
                }
                an.c("clean tombs %d", Integer.valueOf(i2));
            }
        }
    }

    public synchronized void a(StrategyBean strategyBean) {
        boolean z = true;
        synchronized (this) {
            if (strategyBean != null) {
                if (strategyBean.j != d()) {
                    an.d("server anr changed to %b", Boolean.valueOf(strategyBean.j));
                }
                if (!strategyBean.j || !e()) {
                    z = false;
                }
                if (z != d()) {
                    an.a("anr changed to %b", Boolean.valueOf(z));
                    a(z);
                }
            }
        }
    }

    public void g() {
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            if (i2 < 30) {
                try {
                    an.a("try main sleep for make a test anr! try:%d/30 , kill it if you don't want to wait!", Integer.valueOf(i3));
                    ap.b((long) BootloaderScanner.TIMEOUT);
                    i2 = i3;
                } catch (Throwable th) {
                    if (!an.a(th)) {
                        ThrowableExtension.printStackTrace(th);
                        return;
                    }
                    return;
                }
            } else {
                return;
            }
        }
    }
}
