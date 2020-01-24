package com.tencent.bugly.beta.global;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.util.DisplayMetrics;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.b;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.beta.ui.UILifecycleListener;
import com.tencent.bugly.beta.upgrade.BetaUploadStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* compiled from: BUGLY */
public class e {
    public static e E = new e();
    public static int a = 0;
    public SharedPreferences A;
    public DisplayMetrics B;
    public boolean C = true;
    public boolean D = false;
    public BetaUploadStrategy F;
    public File G;
    public File H;
    public File I;
    public String J = "";
    public String K = "";
    public String L = "";
    public String M = "";
    public boolean N = false;
    public int O = 0;
    public String P = "";
    public boolean Q = false;
    public boolean R = true;
    public boolean S = false;
    public boolean T = false;
    public boolean U = true;
    public boolean V = true;
    public BetaPatchListener W;
    public boolean X = false;
    public boolean Y = true;
    public boolean Z = false;
    public final List<String> aa = new ArrayList();
    public boolean ab = false;
    public boolean ac = false;
    public boolean ad;
    public int ae = 1;
    public long b = 3000;
    public long c = 0;
    public boolean d = true;
    public boolean e = true;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public UILifecycleListener<UpgradeInfo> k;
    public File l;
    public final List<Class<? extends Activity>> m = new ArrayList();
    public final List<Class<? extends Activity>> n = new ArrayList();
    public int o;
    public b p;
    public DownloadListener q;
    public File r;
    public Context s;
    public File t;
    public String u;
    public String v;
    public int w = Integer.MIN_VALUE;
    public String x = "";
    public String y = "";
    public PackageInfo z;

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ef, code lost:
        if (r10.t.mkdirs() != false) goto L_0x00f1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ff, code lost:
        if (r10.r.mkdirs() != false) goto L_0x0147;
     */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0190 A[Catch:{ Exception -> 0x01bf }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x019d A[Catch:{ Exception -> 0x01bf }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01b6 A[Catch:{ Exception -> 0x01bf }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(android.content.Context r11) {
        /*
            r10 = this;
            r1 = 1
            r2 = 0
            monitor-enter(r10)
            com.tencent.bugly.beta.global.e r0 = E     // Catch:{ all -> 0x01cb }
            android.content.Context r3 = r11.getApplicationContext()     // Catch:{ all -> 0x01cb }
            r0.s = r3     // Catch:{ all -> 0x01cb }
            android.content.Context r0 = r10.s     // Catch:{ all -> 0x01cb }
            android.content.pm.PackageManager r3 = r0.getPackageManager()     // Catch:{ all -> 0x01cb }
            android.content.Context r0 = r10.s     // Catch:{ Exception -> 0x01bf }
            java.lang.String r0 = r0.getPackageName()     // Catch:{ Exception -> 0x01bf }
            r4 = 16384(0x4000, float:2.2959E-41)
            android.content.pm.PackageInfo r0 = r3.getPackageInfo(r0, r4)     // Catch:{ Exception -> 0x01bf }
            r10.z = r0     // Catch:{ Exception -> 0x01bf }
            int r0 = r10.w     // Catch:{ Exception -> 0x01bf }
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r0 != r4) goto L_0x002b
            android.content.pm.PackageInfo r0 = r10.z     // Catch:{ Exception -> 0x01bf }
            int r0 = r0.versionCode     // Catch:{ Exception -> 0x01bf }
            r10.w = r0     // Catch:{ Exception -> 0x01bf }
        L_0x002b:
            java.lang.String r0 = r10.x     // Catch:{ Exception -> 0x01bf }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x01bf }
            if (r0 == 0) goto L_0x0039
            android.content.pm.PackageInfo r0 = r10.z     // Catch:{ Exception -> 0x01bf }
            java.lang.String r0 = r0.versionName     // Catch:{ Exception -> 0x01bf }
            r10.x = r0     // Catch:{ Exception -> 0x01bf }
        L_0x0039:
            android.content.pm.PackageInfo r0 = r10.z     // Catch:{ Exception -> 0x01bf }
            android.content.pm.ApplicationInfo r0 = r0.applicationInfo     // Catch:{ Exception -> 0x01bf }
            java.lang.CharSequence r0 = r0.loadLabel(r3)     // Catch:{ Exception -> 0x01bf }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x01bf }
            r10.y = r0     // Catch:{ Exception -> 0x01bf }
            java.lang.String r0 = r10.P     // Catch:{ Exception -> 0x01bf }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x01bf }
            if (r0 == 0) goto L_0x0058
            android.content.Context r0 = r10.s     // Catch:{ Exception -> 0x01bf }
            java.lang.String r4 = "BUGLY_CHANNEL"
            java.lang.String r0 = com.tencent.bugly.beta.global.a.a(r0, r4)     // Catch:{ Exception -> 0x01bf }
            r10.P = r0     // Catch:{ Exception -> 0x01bf }
        L_0x0058:
            java.lang.String r0 = "window"
            java.lang.Object r0 = r11.getSystemService(r0)     // Catch:{ all -> 0x01cb }
            android.view.WindowManager r0 = (android.view.WindowManager) r0     // Catch:{ all -> 0x01cb }
            android.util.DisplayMetrics r4 = new android.util.DisplayMetrics     // Catch:{ all -> 0x01cb }
            r4.<init>()     // Catch:{ all -> 0x01cb }
            r10.B = r4     // Catch:{ all -> 0x01cb }
            if (r0 == 0) goto L_0x0079
            android.view.Display r4 = r0.getDefaultDisplay()     // Catch:{ all -> 0x01cb }
            if (r4 == 0) goto L_0x0079
            android.view.Display r0 = r0.getDefaultDisplay()     // Catch:{ all -> 0x01cb }
            android.util.DisplayMetrics r4 = r10.B     // Catch:{ all -> 0x01cb }
            r0.getMetrics(r4)     // Catch:{ all -> 0x01cb }
        L_0x0079:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x01cb }
            r0.<init>()     // Catch:{ all -> 0x01cb }
            android.content.Context r4 = r10.s     // Catch:{ all -> 0x01cb }
            java.lang.String r4 = r4.getPackageName()     // Catch:{ all -> 0x01cb }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x01cb }
            java.lang.String r4 = "/.beta/"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x01cb }
            java.lang.String r4 = "/apk/"
            java.lang.String r5 = "/res/"
            java.io.File r6 = r10.l     // Catch:{ all -> 0x01cb }
            if (r6 == 0) goto L_0x00a1
            java.io.File r6 = r10.l     // Catch:{ all -> 0x01cb }
            boolean r6 = r6.exists()     // Catch:{ all -> 0x01cb }
            if (r6 != 0) goto L_0x01ce
        L_0x00a1:
            java.io.File r6 = new java.io.File     // Catch:{ all -> 0x01cb }
            java.lang.String r7 = android.os.Environment.DIRECTORY_DOWNLOADS     // Catch:{ all -> 0x01cb }
            java.io.File r7 = android.os.Environment.getExternalStoragePublicDirectory(r7)     // Catch:{ all -> 0x01cb }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01cb }
            r6.<init>(r7, r0)     // Catch:{ all -> 0x01cb }
            r10.l = r6     // Catch:{ all -> 0x01cb }
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x01cb }
            java.io.File r6 = r10.l     // Catch:{ all -> 0x01cb }
            r0.<init>(r6, r4)     // Catch:{ all -> 0x01cb }
            r10.t = r0     // Catch:{ all -> 0x01cb }
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x01cb }
            java.io.File r4 = r10.l     // Catch:{ all -> 0x01cb }
            r0.<init>(r4, r5)     // Catch:{ all -> 0x01cb }
            r10.r = r0     // Catch:{ all -> 0x01cb }
        L_0x00c4:
            java.lang.String r0 = "android.permission.WRITE_EXTERNAL_STORAGE"
            android.content.pm.PackageInfo r4 = r10.z     // Catch:{ all -> 0x01cb }
            java.lang.String r4 = r4.packageName     // Catch:{ all -> 0x01cb }
            int r0 = r3.checkPermission(r0, r4)     // Catch:{ all -> 0x01cb }
            if (r0 != 0) goto L_0x020c
            r0 = r1
        L_0x00d2:
            if (r0 == 0) goto L_0x0101
            java.lang.String r0 = "mounted"
            java.lang.String r1 = android.os.Environment.getExternalStorageState()     // Catch:{ Exception -> 0x020f }
            boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x020f }
            if (r0 == 0) goto L_0x0101
            java.io.File r0 = r10.t     // Catch:{ Exception -> 0x020f }
            boolean r0 = r0.exists()     // Catch:{ Exception -> 0x020f }
            if (r0 != 0) goto L_0x00f1
            java.io.File r0 = r10.t     // Catch:{ Exception -> 0x020f }
            boolean r0 = r0.mkdirs()     // Catch:{ Exception -> 0x020f }
            if (r0 == 0) goto L_0x0101
        L_0x00f1:
            java.io.File r0 = r10.r     // Catch:{ Exception -> 0x020f }
            boolean r0 = r0.exists()     // Catch:{ Exception -> 0x020f }
            if (r0 != 0) goto L_0x0147
            java.io.File r0 = r10.r     // Catch:{ Exception -> 0x020f }
            boolean r0 = r0.mkdirs()     // Catch:{ Exception -> 0x020f }
            if (r0 != 0) goto L_0x0147
        L_0x0101:
            android.content.Context r0 = r10.s     // Catch:{ Exception -> 0x020f }
            java.lang.String r1 = "apk"
            java.io.File r0 = r0.getExternalFilesDir(r1)     // Catch:{ Exception -> 0x020f }
            r10.t = r0     // Catch:{ Exception -> 0x020f }
            android.content.Context r0 = r10.s     // Catch:{ Exception -> 0x020f }
            java.lang.String r1 = "res"
            java.io.File r0 = r0.getExternalFilesDir(r1)     // Catch:{ Exception -> 0x020f }
            r10.r = r0     // Catch:{ Exception -> 0x020f }
            java.io.File r0 = r10.t     // Catch:{ Exception -> 0x020f }
            if (r0 == 0) goto L_0x012f
            java.io.File r0 = r10.t     // Catch:{ Exception -> 0x020f }
            boolean r0 = r0.exists()     // Catch:{ Exception -> 0x020f }
            if (r0 == 0) goto L_0x012f
            java.io.File r0 = r10.r     // Catch:{ Exception -> 0x020f }
            if (r0 == 0) goto L_0x012f
            java.io.File r0 = r10.r     // Catch:{ Exception -> 0x020f }
            boolean r0 = r0.exists()     // Catch:{ Exception -> 0x020f }
            if (r0 != 0) goto L_0x0147
        L_0x012f:
            android.content.Context r0 = r10.s     // Catch:{ Exception -> 0x020f }
            java.lang.String r1 = "apk"
            r2 = 2
            java.io.File r0 = r0.getDir(r1, r2)     // Catch:{ Exception -> 0x020f }
            r10.t = r0     // Catch:{ Exception -> 0x020f }
            android.content.Context r0 = r10.s     // Catch:{ Exception -> 0x020f }
            java.lang.String r1 = "res"
            r2 = 0
            java.io.File r0 = r0.getDir(r1, r2)     // Catch:{ Exception -> 0x020f }
            r10.r = r0     // Catch:{ Exception -> 0x020f }
        L_0x0147:
            java.lang.String r0 = "apkSaveDir: %s, resSaveDir: %s"
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x01cb }
            r2 = 0
            java.io.File r3 = r10.t     // Catch:{ all -> 0x01cb }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x01cb }
            r1[r2] = r3     // Catch:{ all -> 0x01cb }
            r2 = 1
            java.io.File r3 = r10.r     // Catch:{ all -> 0x01cb }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x01cb }
            r1[r2] = r3     // Catch:{ all -> 0x01cb }
            com.tencent.bugly.proguard.an.a(r0, r1)     // Catch:{ all -> 0x01cb }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x01cb }
            r0.<init>()     // Catch:{ all -> 0x01cb }
            java.lang.String r1 = r11.getPackageName()     // Catch:{ all -> 0x01cb }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x01cb }
            java.lang.String r1 = ".BETA_VALUES"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x01cb }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01cb }
            android.content.Context r1 = r10.s     // Catch:{ all -> 0x01cb }
            android.content.SharedPreferences r0 = com.tencent.bugly.proguard.ap.a(r0, r1)     // Catch:{ all -> 0x01cb }
            r10.A = r0     // Catch:{ all -> 0x01cb }
            java.lang.String r0 = "isFirstRun"
            r1 = 1
            boolean r0 = com.tencent.bugly.beta.global.a.b(r0, r1)     // Catch:{ all -> 0x01cb }
            r10.C = r0     // Catch:{ all -> 0x01cb }
            boolean r0 = r10.C     // Catch:{ all -> 0x01cb }
            if (r0 == 0) goto L_0x0197
            java.lang.String r0 = "isFirstRun"
            r1 = 0
            com.tencent.bugly.beta.global.a.a(r0, r1)     // Catch:{ all -> 0x01cb }
        L_0x0197:
            com.tencent.bugly.crashreport.common.info.a r0 = com.tencent.bugly.crashreport.common.info.a.b()     // Catch:{ all -> 0x01cb }
            if (r0 == 0) goto L_0x01a5
            com.tencent.bugly.crashreport.common.info.a r0 = com.tencent.bugly.crashreport.common.info.a.b()     // Catch:{ all -> 0x01cb }
            boolean r0 = r0.K     // Catch:{ all -> 0x01cb }
            r10.Q = r0     // Catch:{ all -> 0x01cb }
        L_0x01a5:
            java.lang.String r0 = "us.bch"
            android.os.Parcelable$Creator<com.tencent.bugly.beta.upgrade.BetaUploadStrategy> r1 = com.tencent.bugly.beta.upgrade.BetaUploadStrategy.CREATOR     // Catch:{ all -> 0x01cb }
            android.os.Parcelable r0 = com.tencent.bugly.beta.global.a.a(r0, r1)     // Catch:{ all -> 0x01cb }
            com.tencent.bugly.beta.upgrade.BetaUploadStrategy r0 = (com.tencent.bugly.beta.upgrade.BetaUploadStrategy) r0     // Catch:{ all -> 0x01cb }
            r10.F = r0     // Catch:{ all -> 0x01cb }
            com.tencent.bugly.beta.upgrade.BetaUploadStrategy r0 = r10.F     // Catch:{ all -> 0x01cb }
            if (r0 != 0) goto L_0x01bd
            com.tencent.bugly.beta.upgrade.BetaUploadStrategy r0 = new com.tencent.bugly.beta.upgrade.BetaUploadStrategy     // Catch:{ all -> 0x01cb }
            r0.<init>()     // Catch:{ all -> 0x01cb }
            r10.F = r0     // Catch:{ all -> 0x01cb }
        L_0x01bd:
            monitor-exit(r10)
            return
        L_0x01bf:
            r0 = move-exception
            boolean r4 = com.tencent.bugly.proguard.an.b(r0)     // Catch:{ all -> 0x01cb }
            if (r4 != 0) goto L_0x0058
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x01cb }
            goto L_0x0058
        L_0x01cb:
            r0 = move-exception
            monitor-exit(r10)
            throw r0
        L_0x01ce:
            java.io.File r6 = new java.io.File     // Catch:{ all -> 0x01cb }
            java.io.File r7 = r10.l     // Catch:{ all -> 0x01cb }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x01cb }
            r8.<init>()     // Catch:{ all -> 0x01cb }
            java.lang.String r9 = r0.toString()     // Catch:{ all -> 0x01cb }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x01cb }
            java.lang.StringBuilder r4 = r8.append(r4)     // Catch:{ all -> 0x01cb }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x01cb }
            r6.<init>(r7, r4)     // Catch:{ all -> 0x01cb }
            r10.t = r6     // Catch:{ all -> 0x01cb }
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x01cb }
            java.io.File r6 = r10.l     // Catch:{ all -> 0x01cb }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x01cb }
            r7.<init>()     // Catch:{ all -> 0x01cb }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01cb }
            java.lang.StringBuilder r0 = r7.append(r0)     // Catch:{ all -> 0x01cb }
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ all -> 0x01cb }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01cb }
            r4.<init>(r6, r0)     // Catch:{ all -> 0x01cb }
            r10.r = r4     // Catch:{ all -> 0x01cb }
            goto L_0x00c4
        L_0x020c:
            r0 = r2
            goto L_0x00d2
        L_0x020f:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.b(r0)     // Catch:{ all -> 0x01cb }
            if (r1 != 0) goto L_0x0147
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x01cb }
            goto L_0x0147
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.global.e.a(android.content.Context):void");
    }
}
