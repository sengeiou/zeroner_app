package com.tencent.bugly.proguard;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.beta.download.BetaReceiver;
import com.tencent.bugly.beta.download.DownloadTask;
import com.tencent.bugly.beta.global.d;
import com.tencent.bugly.beta.ui.c;
import com.tencent.bugly.beta.utils.e;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: BUGLY */
public class t extends DownloadTask implements Runnable {
    public long k = 0;
    private File l;
    private long m = 0;

    public t(String str, String str2, long j, long j2, String str3) {
        super(str, "", "", str3);
        this.l = new File(str2);
        this.b = this.l.getParent();
        this.c = this.l.getName();
        this.e = j;
        this.f = j2;
        getStatus();
    }

    public t(String str, String str2, String str3, String str4) {
        super(str, str2, str3, str4);
        getStatus();
    }

    public File getSaveFile() {
        return this.l;
    }

    public void download() {
        if (getStatus() == 1) {
            b();
        } else if (getStatus() != 2) {
            if (getSaveFile() == null || !getSaveFile().exists()) {
                this.e = 0;
                this.f = 0;
                this.k = 0;
            } else {
                this.e = getSaveFile().length();
            }
            if (this.g) {
                c.a.a(this);
            }
            this.m = System.currentTimeMillis();
            this.i = 2;
            s.a.b.put(getDownloadUrl(), this);
            s.a.a(this);
        }
    }

    public void delete(boolean deleteFile) {
        stop();
        if (deleteFile) {
            if (getSaveFile() != null && getSaveFile().exists() && !getSaveFile().isDirectory()) {
                getSaveFile().delete();
            }
            p.a.b((DownloadTask) this);
        }
        BetaReceiver.netListeners.remove(getDownloadUrl());
        this.c = null;
        this.e = 0;
        this.f = 0;
        this.i = 4;
    }

    public void stop() {
        if (this.i != 5) {
            this.i = 3;
        }
    }

    public int getStatus() {
        if (getSaveFile() != null && getSaveFile().exists() && getSaveFile().length() == this.f && !s.a.b.contains(this)) {
            this.e = this.f;
            this.i = 1;
        }
        if (getSaveFile() != null && getSaveFile().exists() && getSaveFile().length() > 0 && getSaveFile().length() < this.f && !s.a.b.contains(this)) {
            this.e = getSaveFile().length();
            this.i = 3;
        }
        if ((getSaveFile() == null || !getSaveFile().exists()) && !s.a.b.contains(this)) {
            this.i = 0;
        }
        return this.i;
    }

    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r2v3, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.io.RandomAccessFile] */
    /* JADX WARNING: type inference failed for: r2v4, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v5, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v7, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v8, types: [java.io.RandomAccessFile] */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r1v9, types: [java.io.RandomAccessFile] */
    /* JADX WARNING: type inference failed for: r3v16, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r2v11, types: [java.io.RandomAccessFile] */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r2v16 */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* JADX WARNING: type inference failed for: r1v22 */
    /* JADX WARNING: type inference failed for: r2v19 */
    /* JADX WARNING: type inference failed for: r2v20 */
    /* JADX WARNING: type inference failed for: r2v21 */
    /* JADX WARNING: type inference failed for: r2v22 */
    /* JADX WARNING: type inference failed for: r1v23 */
    /* JADX WARNING: type inference failed for: r1v24 */
    /* JADX WARNING: type inference failed for: r1v25 */
    /* JADX WARNING: type inference failed for: r2v23 */
    /* JADX WARNING: type inference failed for: r2v24 */
    /* JADX WARNING: type inference failed for: r2v25 */
    /* JADX WARNING: type inference failed for: r2v26 */
    /* JADX WARNING: type inference failed for: r2v27 */
    /* JADX WARNING: type inference failed for: r2v28 */
    /* JADX WARNING: type inference failed for: r1v26 */
    /* JADX WARNING: type inference failed for: r1v27 */
    /* JADX WARNING: type inference failed for: r2v29 */
    /* JADX WARNING: Code restructure failed: missing block: B:101:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01c7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01c8, code lost:
        com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x01cd, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x01ce, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x01cf, code lost:
        if (r2 != 0) goto L_0x01d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x01d4, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01d5, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01d6, code lost:
        com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01da, code lost:
        r0 = th;
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x01dc, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x01dd, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x01e9, code lost:
        r0 = r4;
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x009a, code lost:
        a(2000, "tLen <= 0 ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00a2, code lost:
        if (r1 == 0) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00a4, code lost:
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r1.close();
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00a7, code lost:
        if (r2 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00c2, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00c3, code lost:
        com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00fa, code lost:
        b();
        com.tencent.bugly.proguard.an.e("mSavedLength > mTotalLength,重新下载!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0106, code lost:
        if (r2 == 0) goto L_0x010b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x010b, code lost:
        if (r3 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0111, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0112, code lost:
        com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        b();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x015e, code lost:
        if (r2 == 0) goto L_0x01e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0163, code lost:
        r0 = r4;
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0166, code lost:
        if (r0 < 3) goto L_0x017b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x016e, code lost:
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
        com.tencent.bugly.proguard.an.b(com.tencent.bugly.proguard.t.class, "have retry %d times", java.lang.Integer.valueOf(3));
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x017b, code lost:
        if (r2 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0182, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0183, code lost:
        com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01b3, code lost:
        r0 = e;
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:?, code lost:
        a(2000, r0.getMessage());
        com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01c0, code lost:
        if (r2 == 0) goto L_?;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v6
      assigns: []
      uses: []
      mth insns count: 218
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01c2 A[SYNTHETIC, Splitter:B:100:0x01c2] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x01d1 A[SYNTHETIC, Splitter:B:107:0x01d1] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x01a7 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:142:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0149 A[SYNTHETIC, Splitter:B:58:0x0149] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01a4 A[SYNTHETIC, Splitter:B:87:0x01a4] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x01af A[Catch:{ Exception -> 0x01b3 }] */
    /* JADX WARNING: Unknown variable types count: 18 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r12 = this;
            java.net.URL r5 = new java.net.URL     // Catch:{ MalformedURLException -> 0x00ad }
            java.lang.String r0 = r12.getDownloadUrl()     // Catch:{ MalformedURLException -> 0x00ad }
            r5.<init>(r0)     // Catch:{ MalformedURLException -> 0x00ad }
            r2 = 0
            r1 = 0
            r0 = 0
        L_0x000c:
            r3 = 3
            if (r0 >= r3) goto L_0x0165
            int r4 = r0 + 1
            java.net.URLConnection r0 = r5.openConnection()     // Catch:{ IOException -> 0x01e4 }
            javax.net.ssl.HttpsURLConnection r0 = (javax.net.ssl.HttpsURLConnection) r0     // Catch:{ IOException -> 0x01e4 }
            r3 = 5000(0x1388, float:7.006E-42)
            r0.setConnectTimeout(r3)     // Catch:{ IOException -> 0x01e4 }
            java.lang.String r3 = "GET"
            r0.setRequestMethod(r3)     // Catch:{ IOException -> 0x01e4 }
            java.lang.String r3 = "Referer"
            java.lang.String r6 = r5.toString()     // Catch:{ IOException -> 0x01e4 }
            r0.setRequestProperty(r3, r6)     // Catch:{ IOException -> 0x01e4 }
            java.lang.String r3 = "Charset"
            java.lang.String r6 = "UTF-8"
            r0.setRequestProperty(r3, r6)     // Catch:{ IOException -> 0x01e4 }
            java.lang.String r3 = "Range"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01e4 }
            r6.<init>()     // Catch:{ IOException -> 0x01e4 }
            java.lang.String r7 = "bytes="
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x01e4 }
            long r8 = r12.e     // Catch:{ IOException -> 0x01e4 }
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ IOException -> 0x01e4 }
            java.lang.String r7 = "-"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x01e4 }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x01e4 }
            r0.setRequestProperty(r3, r6)     // Catch:{ IOException -> 0x01e4 }
            java.lang.String r3 = "Connection"
            java.lang.String r6 = "Keep-Alive"
            r0.setRequestProperty(r3, r6)     // Catch:{ IOException -> 0x01e4 }
            r0.connect()     // Catch:{ IOException -> 0x01e4 }
            java.lang.String r3 = r12.a(r0)     // Catch:{ IOException -> 0x01e4 }
            r12.c = r3     // Catch:{ IOException -> 0x01e4 }
            java.io.File r3 = new java.io.File     // Catch:{ IOException -> 0x01e4 }
            java.lang.String r6 = r12.b     // Catch:{ IOException -> 0x01e4 }
            r3.<init>(r6)     // Catch:{ IOException -> 0x01e4 }
            boolean r6 = r3.exists()     // Catch:{ IOException -> 0x01e4 }
            if (r6 != 0) goto L_0x007a
            r3.mkdirs()     // Catch:{ IOException -> 0x01e4 }
        L_0x007a:
            java.io.File r6 = new java.io.File     // Catch:{ IOException -> 0x01e4 }
            java.lang.String r7 = r12.c     // Catch:{ IOException -> 0x01e4 }
            r6.<init>(r3, r7)     // Catch:{ IOException -> 0x01e4 }
            r12.l = r6     // Catch:{ IOException -> 0x01e4 }
            long r6 = r12.f     // Catch:{ IOException -> 0x01e4 }
            r8 = 0
            int r3 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r3 != 0) goto L_0x00c7
            int r3 = r0.getContentLength()     // Catch:{ IOException -> 0x01e4 }
            long r6 = (long) r3     // Catch:{ IOException -> 0x01e4 }
            r12.f = r6     // Catch:{ IOException -> 0x01e4 }
            long r6 = r12.f     // Catch:{ IOException -> 0x01e4 }
            r8 = 0
            int r3 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r3 > 0) goto L_0x00c7
            r0 = 2000(0x7d0, float:2.803E-42)
            java.lang.String r3 = "tLen <= 0 "
            r12.a(r0, r3)     // Catch:{ IOException -> 0x01e4 }
            if (r1 == 0) goto L_0x00a7
            r1.close()     // Catch:{ Exception -> 0x01b3 }
        L_0x00a7:
            if (r2 == 0) goto L_0x00ac
            r2.close()     // Catch:{ Exception -> 0x00c2 }
        L_0x00ac:
            return
        L_0x00ad:
            r0 = move-exception
            java.lang.String r1 = r0.getMessage()
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.an.a(r1, r2)
            r1 = 2010(0x7da, float:2.817E-42)
            java.lang.String r0 = r0.getMessage()
            r12.a(r1, r0)
            goto L_0x00ac
        L_0x00c2:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00ac
        L_0x00c7:
            com.tencent.bugly.proguard.p r3 = com.tencent.bugly.proguard.p.a     // Catch:{ IOException -> 0x01e4 }
            r3.a(r12)     // Catch:{ IOException -> 0x01e4 }
            java.io.InputStream r3 = r0.getInputStream()     // Catch:{ IOException -> 0x01e4 }
            r0 = 307200(0x4b000, float:4.30479E-40)
            byte[] r6 = new byte[r0]     // Catch:{ IOException -> 0x01e6, all -> 0x01e1 }
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch:{ IOException -> 0x01e6, all -> 0x01e1 }
            java.io.File r0 = r12.l     // Catch:{ IOException -> 0x01e6, all -> 0x01e1 }
            java.lang.String r7 = "rwd"
            r2.<init>(r0, r7)     // Catch:{ IOException -> 0x01e6, all -> 0x01e1 }
            long r0 = r12.e     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            r2.seek(r0)     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            r1 = 0
        L_0x00e5:
            int r7 = r3.read(r6)     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            r0 = -1
            if (r7 == r0) goto L_0x015b
            long r8 = r12.e     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            long r10 = (long) r7     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            long r8 = r8 + r10
            r12.e = r8     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            long r8 = r12.e     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            long r10 = r12.f     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            int r0 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r0 <= 0) goto L_0x0116
            r12.b()     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            java.lang.String r0 = "mSavedLength > mTotalLength,重新下载!"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            com.tencent.bugly.proguard.an.e(r0, r1)     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            if (r2 == 0) goto L_0x010b
            r2.close()     // Catch:{ Exception -> 0x01dc, all -> 0x01cd }
        L_0x010b:
            if (r3 == 0) goto L_0x00ac
            r3.close()     // Catch:{ Exception -> 0x0111 }
            goto L_0x00ac
        L_0x0111:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00ac
        L_0x0116:
            r0 = 1120403456(0x42c80000, float:100.0)
            long r8 = r12.e     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            float r8 = (float) r8     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            long r10 = r12.f     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            float r9 = (float) r10     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            float r8 = r8 / r9
            float r0 = r0 * r8
            float r8 = r0 - r1
            double r8 = (double) r8     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            r10 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 < 0) goto L_0x01ed
            r12.a()     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
        L_0x012c:
            r1 = 0
            r2.write(r6, r1, r7)     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            java.io.File r1 = r12.getSaveFile()     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            if (r1 == 0) goto L_0x0147
            java.io.File r1 = r12.getSaveFile()     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            boolean r1 = r1.exists()     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            if (r1 == 0) goto L_0x0147
            int r1 = r12.getStatus()     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            r7 = 3
            if (r1 != r7) goto L_0x0159
        L_0x0147:
            if (r2 == 0) goto L_0x014c
            r2.close()     // Catch:{ Exception -> 0x01dc, all -> 0x01cd }
        L_0x014c:
            if (r3 == 0) goto L_0x00ac
            r3.close()     // Catch:{ Exception -> 0x0153 }
            goto L_0x00ac
        L_0x0153:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00ac
        L_0x0159:
            r1 = r0
            goto L_0x00e5
        L_0x015b:
            r12.b()     // Catch:{ IOException -> 0x0188, all -> 0x01aa }
            if (r2 == 0) goto L_0x01e9
            r2.close()     // Catch:{ Exception -> 0x01dc, all -> 0x01cd }
            r0 = r4
            r2 = r3
        L_0x0165:
            r1 = 3
            if (r0 < r1) goto L_0x017b
            java.lang.Class<com.tencent.bugly.proguard.t> r0 = com.tencent.bugly.proguard.t.class
            java.lang.String r1 = "have retry %d times"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x01b3 }
            r4 = 0
            r5 = 3
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Exception -> 0x01b3 }
            r3[r4] = r5     // Catch:{ Exception -> 0x01b3 }
            com.tencent.bugly.proguard.an.b(r0, r1, r3)     // Catch:{ Exception -> 0x01b3 }
        L_0x017b:
            if (r2 == 0) goto L_0x00ac
            r2.close()     // Catch:{ Exception -> 0x0182 }
            goto L_0x00ac
        L_0x0182:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00ac
        L_0x0188:
            r0 = move-exception
            r1 = r2
            r2 = r3
        L_0x018b:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x01df }
            r3 = 2020(0x7e4, float:2.83E-42)
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x01df }
            r12.a(r3, r0)     // Catch:{ all -> 0x01df }
            java.lang.Class<com.tencent.bugly.proguard.t> r0 = com.tencent.bugly.proguard.t.class
            java.lang.String r3 = "IOException,stop download!"
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x01df }
            com.tencent.bugly.proguard.an.b(r0, r3, r6)     // Catch:{ all -> 0x01df }
            if (r1 == 0) goto L_0x01a7
            r1.close()     // Catch:{ Exception -> 0x01b3 }
        L_0x01a7:
            r0 = r4
            goto L_0x000c
        L_0x01aa:
            r0 = move-exception
            r1 = r2
            r2 = r3
        L_0x01ad:
            if (r1 == 0) goto L_0x01b2
            r1.close()     // Catch:{ Exception -> 0x01b3 }
        L_0x01b2:
            throw r0     // Catch:{ Exception -> 0x01b3 }
        L_0x01b3:
            r0 = move-exception
        L_0x01b4:
            r1 = 2000(0x7d0, float:2.803E-42)
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x01da }
            r12.a(r1, r3)     // Catch:{ all -> 0x01da }
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x01da }
            if (r2 == 0) goto L_0x00ac
            r2.close()     // Catch:{ Exception -> 0x01c7 }
            goto L_0x00ac
        L_0x01c7:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x00ac
        L_0x01cd:
            r0 = move-exception
            r2 = r3
        L_0x01cf:
            if (r2 == 0) goto L_0x01d4
            r2.close()     // Catch:{ Exception -> 0x01d5 }
        L_0x01d4:
            throw r0
        L_0x01d5:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x01d4
        L_0x01da:
            r0 = move-exception
            goto L_0x01cf
        L_0x01dc:
            r0 = move-exception
            r2 = r3
            goto L_0x01b4
        L_0x01df:
            r0 = move-exception
            goto L_0x01ad
        L_0x01e1:
            r0 = move-exception
            r2 = r3
            goto L_0x01ad
        L_0x01e4:
            r0 = move-exception
            goto L_0x018b
        L_0x01e6:
            r0 = move-exception
            r2 = r3
            goto L_0x018b
        L_0x01e9:
            r0 = r4
            r2 = r3
            goto L_0x0165
        L_0x01ed:
            r0 = r1
            goto L_0x012c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.t.run():void");
    }

    private String a(HttpURLConnection httpURLConnection) {
        try {
            if (!TextUtils.isEmpty(this.c)) {
                return this.c;
            }
            Map headerFields = httpURLConnection.getHeaderFields();
            if (headerFields != null) {
                for (String str : headerFields.keySet()) {
                    if (str != null) {
                        List<String> list = (List) headerFields.get(str);
                        if (list != null) {
                            for (String str2 : list) {
                                if (str2 != null && "content-disposition".equals(str.toLowerCase())) {
                                    Matcher matcher = Pattern.compile(".*filename=(.*)").matcher(str2.toLowerCase());
                                    if (matcher.find()) {
                                        return matcher.group(1);
                                    }
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }
                }
            }
            String substring = getDownloadUrl().substring(getDownloadUrl().lastIndexOf(47) + 1);
            if (!TextUtils.isEmpty(substring)) {
                return substring;
            }
            return UUID.randomUUID() + ShareConstants.PATCH_SUFFIX;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: protected */
    public void a(int i, String str) {
        this.i = 5;
        c.a.a();
        s.a.b.remove(getDownloadUrl());
        e.a(new d(10, this.d, this, Integer.valueOf(i), str));
    }

    /* access modifiers changed from: protected */
    public void a() {
        this.k += System.currentTimeMillis() - this.m;
        p.a.a((DownloadTask) this);
        this.m = System.currentTimeMillis();
        c.a.a();
        e.a(new d(9, this.d, this));
    }

    /* access modifiers changed from: protected */
    public void b() {
        this.i = 1;
        a();
        s.a.b.remove(getDownloadUrl());
        BetaReceiver.netListeners.remove(getDownloadUrl());
        e.a(new d(8, this.d, this));
    }

    public long getCostTime() {
        return this.k;
    }
}
