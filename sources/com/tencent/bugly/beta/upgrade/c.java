package com.tencent.bugly.beta.upgrade;

import android.text.TextUtils;
import com.tencent.bugly.beta.download.BetaReceiver;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;
import com.tencent.bugly.beta.download.a;
import com.tencent.bugly.beta.global.d;
import com.tencent.bugly.beta.global.e;
import com.tencent.bugly.beta.global.f;
import com.tencent.bugly.beta.ui.h;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.bg;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.y;

/* compiled from: BUGLY */
public class c {
    public static c a = new c();
    public BetaGrayStrategy b;
    public DownloadTask c;
    public DownloadListener d;
    public UpgradeListener e;
    public UpgradeStateListener f;
    public boolean g;
    public d h;
    public d i;
    public int j;
    private final Object k = new Object();
    private final Object l = new Object();
    private DownloadListener m = new a(3, this, Integer.valueOf(0));
    private a n = null;
    private d o;
    private boolean p;

    /* JADX WARNING: Removed duplicated region for block: B:44:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x024d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.tencent.bugly.beta.upgrade.BetaGrayStrategy a(com.tencent.bugly.proguard.y r15) {
        /*
            r14 = this;
            r10 = 3
            r7 = 2
            r2 = 0
            r8 = 1
            r1 = 0
            java.lang.Object r12 = r14.k
            monitor-enter(r12)
            java.lang.String r0 = "st.bch"
            android.os.Parcelable$Creator<com.tencent.bugly.beta.upgrade.BetaGrayStrategy> r3 = com.tencent.bugly.beta.upgrade.BetaGrayStrategy.CREATOR     // Catch:{ all -> 0x01ba }
            android.os.Parcelable r0 = com.tencent.bugly.beta.global.a.a(r0, r3)     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = (com.tencent.bugly.beta.upgrade.BetaGrayStrategy) r0     // Catch:{ all -> 0x01ba }
            if (r0 == 0) goto L_0x0020
            com.tencent.bugly.proguard.y r3 = r0.a     // Catch:{ all -> 0x01ba }
            if (r3 != 0) goto L_0x0020
            java.lang.String r0 = "st.bch"
            com.tencent.bugly.beta.global.a.a(r0)     // Catch:{ all -> 0x01ba }
            r0 = r1
        L_0x0020:
            if (r0 == 0) goto L_0x005e
            com.tencent.bugly.proguard.y r3 = r0.a     // Catch:{ all -> 0x01ba }
            if (r3 == 0) goto L_0x005e
            com.tencent.bugly.proguard.y r3 = r0.a     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.v r3 = r3.e     // Catch:{ all -> 0x01ba }
            int r3 = r3.c     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.beta.global.e r4 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            int r4 = r4.w     // Catch:{ all -> 0x01ba }
            if (r3 <= r4) goto L_0x0054
            com.tencent.bugly.proguard.y r3 = r0.a     // Catch:{ all -> 0x01ba }
            int r3 = r3.n     // Catch:{ all -> 0x01ba }
            if (r3 != r8) goto L_0x0054
            com.tencent.bugly.proguard.y r3 = r0.a     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.u r3 = r3.f     // Catch:{ all -> 0x01ba }
            if (r3 == 0) goto L_0x004e
            com.tencent.bugly.beta.global.e r3 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            java.lang.String r3 = r3.v     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.y r4 = r0.a     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.u r4 = r4.f     // Catch:{ all -> 0x01ba }
            java.lang.String r4 = r4.a     // Catch:{ all -> 0x01ba }
            boolean r3 = android.text.TextUtils.equals(r3, r4)     // Catch:{ all -> 0x01ba }
            if (r3 != 0) goto L_0x0054
        L_0x004e:
            com.tencent.bugly.proguard.y r3 = r0.a     // Catch:{ all -> 0x01ba }
            int r3 = r3.p     // Catch:{ all -> 0x01ba }
            if (r3 != r10) goto L_0x005e
        L_0x0054:
            java.lang.String r0 = "st.bch"
            com.tencent.bugly.beta.global.a.a(r0)     // Catch:{ all -> 0x01ba }
            r0 = 0
            r14.c = r0     // Catch:{ all -> 0x01ba }
            r0 = r1
        L_0x005e:
            if (r15 == 0) goto L_0x0267
            com.tencent.bugly.proguard.v r3 = r15.e     // Catch:{ all -> 0x01ba }
            int r3 = r3.c     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.beta.global.e r4 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            int r4 = r4.w     // Catch:{ all -> 0x01ba }
            if (r3 >= r4) goto L_0x0267
            java.lang.String r3 = "versionCode is too small, discard remote strategy: [new: %d] [current: %d]"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x01ba }
            r5 = 0
            com.tencent.bugly.proguard.v r6 = r15.e     // Catch:{ all -> 0x01ba }
            int r6 = r6.c     // Catch:{ all -> 0x01ba }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x01ba }
            r4[r5] = r6     // Catch:{ all -> 0x01ba }
            r5 = 1
            com.tencent.bugly.beta.global.e r6 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            int r6 = r6.w     // Catch:{ all -> 0x01ba }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x01ba }
            r4[r5] = r6     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.an.a(r3, r4)     // Catch:{ all -> 0x01ba }
            r3 = r1
        L_0x008a:
            if (r3 == 0) goto L_0x0263
            int r4 = r3.n     // Catch:{ all -> 0x01ba }
            if (r4 != r7) goto L_0x00e5
            if (r0 == 0) goto L_0x00e5
            com.tencent.bugly.proguard.y r4 = r0.a     // Catch:{ all -> 0x01ba }
            if (r4 == 0) goto L_0x00e5
            java.lang.String r4 = r3.m     // Catch:{ all -> 0x01ba }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x01ba }
            if (r4 != 0) goto L_0x00e5
            com.tencent.bugly.proguard.y r4 = r0.a     // Catch:{ all -> 0x01ba }
            java.lang.String r4 = r4.m     // Catch:{ all -> 0x01ba }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x01ba }
            if (r4 != 0) goto L_0x00e5
            java.lang.String r4 = r3.m     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.y r5 = r0.a     // Catch:{ all -> 0x01ba }
            java.lang.String r5 = r5.m     // Catch:{ all -> 0x01ba }
            boolean r4 = android.text.TextUtils.equals(r4, r5)     // Catch:{ all -> 0x01ba }
            if (r4 == 0) goto L_0x00e5
            java.lang.String r4 = "callback strategy: %s"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x01ba }
            r6 = 0
            java.lang.String r7 = r3.m     // Catch:{ all -> 0x01ba }
            r5[r6] = r7     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.an.a(r4, r5)     // Catch:{ all -> 0x01ba }
            java.lang.String r4 = "st.bch"
            com.tencent.bugly.beta.global.a.a(r4)     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.beta.global.e r4 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.beta.download.b r4 = r4.p     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.y r0 = r0.a     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.u r0 = r0.f     // Catch:{ all -> 0x01ba }
            java.lang.String r0 = r0.b     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.beta.global.e r5 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            java.io.File r5 = r5.t     // Catch:{ all -> 0x01ba }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ all -> 0x01ba }
            r6 = 0
            r7 = 0
            com.tencent.bugly.beta.download.DownloadTask r0 = r4.a(r0, r5, r6, r7)     // Catch:{ all -> 0x01ba }
            r4 = 1
            r0.delete(r4)     // Catch:{ all -> 0x01ba }
            r0 = r1
        L_0x00e5:
            int r4 = r3.n     // Catch:{ all -> 0x01ba }
            if (r4 == r8) goto L_0x0263
            java.lang.String r4 = "invalid strategy: %s"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x01ba }
            r6 = 0
            java.lang.String r3 = r3.m     // Catch:{ all -> 0x01ba }
            r5[r6] = r3     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.an.a(r4, r5)     // Catch:{ all -> 0x01ba }
            r3 = r0
            r9 = r1
        L_0x00f9:
            if (r9 == 0) goto L_0x024d
            if (r3 == 0) goto L_0x01b4
            com.tencent.bugly.proguard.y r0 = r3.a     // Catch:{ all -> 0x01ba }
            if (r0 == 0) goto L_0x01b4
            java.lang.String r0 = r9.m     // Catch:{ all -> 0x01ba }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x01ba }
            if (r0 != 0) goto L_0x01b4
            com.tencent.bugly.proguard.y r0 = r3.a     // Catch:{ all -> 0x01ba }
            java.lang.String r0 = r0.m     // Catch:{ all -> 0x01ba }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x01ba }
            if (r0 != 0) goto L_0x01b4
            java.lang.String r0 = r9.m     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.y r1 = r3.a     // Catch:{ all -> 0x01ba }
            java.lang.String r1 = r1.m     // Catch:{ all -> 0x01ba }
            boolean r0 = android.text.TextUtils.equals(r0, r1)     // Catch:{ all -> 0x01ba }
            if (r0 == 0) goto L_0x01b4
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r11 = new com.tencent.bugly.beta.upgrade.BetaGrayStrategy     // Catch:{ all -> 0x01ba }
            byte[] r0 = com.tencent.bugly.proguard.ap.a(r3)     // Catch:{ all -> 0x01ba }
            android.os.Parcel r0 = com.tencent.bugly.proguard.ap.d(r0)     // Catch:{ all -> 0x01ba }
            r11.<init>(r0)     // Catch:{ all -> 0x01ba }
            java.lang.String r0 = "same strategyId:[new: %s] [current: %s] keep has popup times: %d, interval: %d"
            r1 = 4
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x01ba }
            r4 = 0
            java.lang.String r5 = r9.m     // Catch:{ all -> 0x01ba }
            r1[r4] = r5     // Catch:{ all -> 0x01ba }
            r4 = 1
            com.tencent.bugly.proguard.y r5 = r3.a     // Catch:{ all -> 0x01ba }
            java.lang.String r5 = r5.m     // Catch:{ all -> 0x01ba }
            r1[r4] = r5     // Catch:{ all -> 0x01ba }
            r4 = 2
            int r5 = r11.b     // Catch:{ all -> 0x01ba }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x01ba }
            r1[r4] = r5     // Catch:{ all -> 0x01ba }
            r4 = 3
            long r6 = r9.i     // Catch:{ all -> 0x01ba }
            java.lang.Long r5 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x01ba }
            r1[r4] = r5     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.an.a(r0, r1)     // Catch:{ all -> 0x01ba }
        L_0x0153:
            r11.a = r9     // Catch:{ all -> 0x01ba }
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x01ba }
            r11.e = r0     // Catch:{ all -> 0x01ba }
            if (r3 == 0) goto L_0x0214
            com.tencent.bugly.proguard.y r0 = r3.a     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.u r0 = r0.f     // Catch:{ all -> 0x01ba }
            java.lang.String r0 = r0.b     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.u r1 = r9.f     // Catch:{ all -> 0x01ba }
            java.lang.String r1 = r1.b     // Catch:{ all -> 0x01ba }
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x01ba }
            if (r0 != 0) goto L_0x01d1
            com.tencent.bugly.beta.download.DownloadTask r0 = r14.c     // Catch:{ all -> 0x01ba }
            if (r0 != 0) goto L_0x01bd
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.beta.download.b r0 = r0.p     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.y r1 = r3.a     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.u r1 = r1.f     // Catch:{ all -> 0x01ba }
            java.lang.String r1 = r1.b     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.beta.global.e r4 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            java.io.File r4 = r4.t     // Catch:{ all -> 0x01ba }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ all -> 0x01ba }
            r5 = 0
            r6 = 0
            com.tencent.bugly.beta.download.DownloadTask r0 = r0.a(r1, r4, r5, r6)     // Catch:{ all -> 0x01ba }
            r1 = 1
            r0.delete(r1)     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            java.io.File r0 = r0.t     // Catch:{ all -> 0x01ba }
            java.io.File[] r1 = r0.listFiles()     // Catch:{ all -> 0x01ba }
            int r4 = r1.length     // Catch:{ all -> 0x01ba }
            r0 = r2
        L_0x0197:
            if (r0 >= r4) goto L_0x01d1
            r2 = r1[r0]     // Catch:{ all -> 0x01ba }
            boolean r5 = r2.delete()     // Catch:{ all -> 0x01ba }
            if (r5 != 0) goto L_0x01b1
            java.lang.String r5 = "cannot deleteCache file:%s"
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x01ba }
            r7 = 0
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ all -> 0x01ba }
            r6[r7] = r2     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.an.e(r5, r6)     // Catch:{ all -> 0x01ba }
        L_0x01b1:
            int r0 = r0 + 1
            goto L_0x0197
        L_0x01b4:
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r11 = new com.tencent.bugly.beta.upgrade.BetaGrayStrategy     // Catch:{ all -> 0x01ba }
            r11.<init>()     // Catch:{ all -> 0x01ba }
            goto L_0x0153
        L_0x01ba:
            r0 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x01ba }
            throw r0
        L_0x01bd:
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.tencent.bugly.beta.download.c> r0 = com.tencent.bugly.beta.download.BetaReceiver.netListeners     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.beta.download.DownloadTask r1 = r14.c     // Catch:{ all -> 0x01ba }
            java.lang.String r1 = r1.getDownloadUrl()     // Catch:{ all -> 0x01ba }
            r0.remove(r1)     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.beta.download.DownloadTask r0 = r14.c     // Catch:{ all -> 0x01ba }
            r1 = 1
            r0.delete(r1)     // Catch:{ all -> 0x01ba }
            r0 = 0
            r14.c = r0     // Catch:{ all -> 0x01ba }
        L_0x01d1:
            com.tencent.bugly.proguard.y r0 = r3.a     // Catch:{ all -> 0x01ba }
            int r0 = r0.p     // Catch:{ all -> 0x01ba }
            if (r0 != r10) goto L_0x0214
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            java.io.File r0 = r0.H     // Catch:{ all -> 0x01ba }
            if (r0 == 0) goto L_0x01f2
            boolean r1 = r0.exists()     // Catch:{ all -> 0x01ba }
            if (r1 == 0) goto L_0x01f2
            boolean r0 = r0.delete()     // Catch:{ all -> 0x01ba }
            if (r0 == 0) goto L_0x01f2
            java.lang.String r0 = "delete tmpPatchFile"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.an.a(r0, r1)     // Catch:{ all -> 0x01ba }
        L_0x01f2:
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            java.io.File r0 = r0.G     // Catch:{ all -> 0x01ba }
            if (r0 == 0) goto L_0x0214
            boolean r1 = r0.exists()     // Catch:{ all -> 0x01ba }
            if (r1 == 0) goto L_0x0214
            boolean r0 = r0.delete()     // Catch:{ all -> 0x01ba }
            if (r0 == 0) goto L_0x0214
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x01ba }
            java.lang.String r1 = ""
            r0.L = r1     // Catch:{ all -> 0x01ba }
            java.lang.String r0 = "delete patchFile"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.an.a(r0, r1)     // Catch:{ all -> 0x01ba }
        L_0x0214:
            java.lang.String r0 = "st.bch"
            com.tencent.bugly.beta.global.a.a(r0, r11)     // Catch:{ all -> 0x01ba }
            java.lang.String r0 = "onUpgradeReceived: %s [type: %d]"
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x01ba }
            r2 = 0
            r1[r2] = r9     // Catch:{ all -> 0x01ba }
            r2 = 1
            byte r3 = r9.g     // Catch:{ all -> 0x01ba }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x01ba }
            r1[r2] = r3     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.an.a(r0, r1)     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.p r13 = com.tencent.bugly.proguard.p.a     // Catch:{ all -> 0x01ba }
            com.tencent.bugly.proguard.w r0 = new com.tencent.bugly.proguard.w     // Catch:{ all -> 0x01ba }
            java.lang.String r1 = "rcv"
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x01ba }
            r4 = 0
            r5 = 0
            com.tencent.bugly.proguard.v r7 = r9.e     // Catch:{ all -> 0x01ba }
            java.lang.String r8 = r9.m     // Catch:{ all -> 0x01ba }
            int r9 = r9.p     // Catch:{ all -> 0x01ba }
            r10 = 0
            r0.<init>(r1, r2, r4, r5, r7, r8, r9, r10)     // Catch:{ all -> 0x01ba }
            r13.a(r0)     // Catch:{ all -> 0x01ba }
            r0 = r11
        L_0x024b:
            monitor-exit(r12)     // Catch:{ all -> 0x01ba }
            return r0
        L_0x024d:
            if (r3 == 0) goto L_0x0261
            com.tencent.bugly.proguard.y r0 = r3.a     // Catch:{ all -> 0x01ba }
            if (r0 == 0) goto L_0x0261
            com.tencent.bugly.proguard.y r0 = r3.a     // Catch:{ all -> 0x01ba }
            int r0 = r0.p     // Catch:{ all -> 0x01ba }
            if (r0 == r10) goto L_0x0261
            java.lang.String r0 = "st.bch"
            com.tencent.bugly.beta.global.a.a(r0)     // Catch:{ all -> 0x01ba }
            r0 = r1
            goto L_0x024b
        L_0x0261:
            r0 = r1
            goto L_0x024b
        L_0x0263:
            r9 = r3
            r3 = r0
            goto L_0x00f9
        L_0x0267:
            r3 = r15
            goto L_0x008a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.upgrade.c.a(com.tencent.bugly.proguard.y):com.tencent.bugly.beta.upgrade.BetaGrayStrategy");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(boolean r11, boolean r12, int r13, com.tencent.bugly.proguard.y r14, java.lang.String r15) {
        /*
            r10 = this;
            r9 = 4
            r8 = 2
            r7 = 3
            r6 = 1
            java.lang.Object r1 = r10.k
            monitor-enter(r1)
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.a(r14)     // Catch:{ all -> 0x00d7 }
            r10.b = r0     // Catch:{ all -> 0x00d7 }
            r10.g = r11     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.upgrade.UpgradeListener r0 = r10.e     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x009e
            java.lang.String r0 = "你已放弃让SDK来处理策略"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.an.a(r0, r2)     // Catch:{ all -> 0x00d7 }
            r0 = 3
            r10.j = r0     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.b     // Catch:{ all -> 0x00d7 }
            if (r0 != 0) goto L_0x002c
            java.lang.String r0 = "betaStrategy is null"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.an.a(r0, r2)     // Catch:{ all -> 0x00d7 }
        L_0x002c:
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.b     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x0057
            com.tencent.bugly.beta.download.DownloadTask r0 = r10.c     // Catch:{ all -> 0x00d7 }
            if (r0 != 0) goto L_0x0057
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.download.b r0 = r0.p     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r2 = r10.b     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.y r2 = r2.a     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.u r2 = r2.f     // Catch:{ all -> 0x00d7 }
            java.lang.String r2 = r2.b     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.global.e r3 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x00d7 }
            java.io.File r3 = r3.t     // Catch:{ all -> 0x00d7 }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x00d7 }
            r4 = 0
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r5 = r10.b     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.y r5 = r5.a     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.u r5 = r5.f     // Catch:{ all -> 0x00d7 }
            java.lang.String r5 = r5.a     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.download.DownloadTask r0 = r0.a(r2, r3, r4, r5)     // Catch:{ all -> 0x00d7 }
            r10.c = r0     // Catch:{ all -> 0x00d7 }
        L_0x0057:
            com.tencent.bugly.beta.download.DownloadTask r0 = r10.c     // Catch:{ all -> 0x00d7 }
            if (r0 != 0) goto L_0x00cf
            java.lang.String r0 = "用户自定义activity，创建task失败 [strategy:%s]"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x00d7 }
            r3 = 0
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r4 = r10.b     // Catch:{ all -> 0x00d7 }
            r2[r3] = r4     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.an.a(r0, r2)     // Catch:{ all -> 0x00d7 }
            r0 = 0
            r10.b = r0     // Catch:{ all -> 0x00d7 }
            java.lang.String r0 = "st.bch"
            com.tencent.bugly.beta.global.a.a(r0)     // Catch:{ all -> 0x00d7 }
        L_0x0072:
            com.tencent.bugly.beta.global.d r0 = new com.tencent.bugly.beta.global.d     // Catch:{ all -> 0x00d7 }
            r2 = 16
            r3 = 5
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00d7 }
            r4 = 0
            com.tencent.bugly.beta.upgrade.UpgradeListener r5 = r10.e     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r4 = 1
            java.lang.Integer r5 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r4 = 2
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r5 = r10.b     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r4 = 3
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r11)     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r4 = 4
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r12)     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r0.<init>(r2, r3)     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.utils.e.a(r0)     // Catch:{ all -> 0x00d7 }
        L_0x009e:
            if (r13 == 0) goto L_0x00ec
            if (r11 == 0) goto L_0x00ec
            if (r12 != 0) goto L_0x00ec
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.b     // Catch:{ all -> 0x00d7 }
            if (r0 != 0) goto L_0x00ec
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r0 = r10.f     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x00da
            com.tencent.bugly.beta.global.d r0 = new com.tencent.bugly.beta.global.d     // Catch:{ all -> 0x00d7 }
            r2 = 18
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00d7 }
            r4 = 0
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r5 = r10.f     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r4 = 1
            r5 = -1
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r4 = 2
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r11)     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r0.<init>(r2, r3)     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.utils.e.a(r0)     // Catch:{ all -> 0x00d7 }
        L_0x00cd:
            monitor-exit(r1)     // Catch:{ all -> 0x00d7 }
        L_0x00ce:
            return
        L_0x00cf:
            com.tencent.bugly.beta.download.DownloadTask r0 = r10.c     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.download.DownloadListener r2 = r10.m     // Catch:{ all -> 0x00d7 }
            r0.addListener(r2)     // Catch:{ all -> 0x00d7 }
            goto L_0x0072
        L_0x00d7:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00d7 }
            throw r0
        L_0x00da:
            com.tencent.bugly.beta.global.d r0 = new com.tencent.bugly.beta.global.d     // Catch:{ all -> 0x00d7 }
            r2 = 5
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00d7 }
            r4 = 0
            java.lang.String r5 = com.tencent.bugly.beta.Beta.strToastCheckUpgradeError     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r0.<init>(r2, r3)     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.utils.e.a(r0)     // Catch:{ all -> 0x00d7 }
            goto L_0x00cd
        L_0x00ec:
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.b     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x01c0
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.b     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.y r0 = r0.a     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x01c0
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r0 = r10.f     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x011b
            com.tencent.bugly.beta.global.d r0 = new com.tencent.bugly.beta.global.d     // Catch:{ all -> 0x00d7 }
            r2 = 18
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00d7 }
            r4 = 0
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r5 = r10.f     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r4 = 1
            r5 = 0
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r4 = 2
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r11)     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r0.<init>(r2, r3)     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.utils.e.a(r0)     // Catch:{ all -> 0x00d7 }
        L_0x011b:
            com.tencent.bugly.beta.upgrade.UpgradeListener r0 = r10.e     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x0121
            monitor-exit(r1)     // Catch:{ all -> 0x00d7 }
            goto L_0x00ce
        L_0x0121:
            if (r11 != 0) goto L_0x0190
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.b     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.y r0 = r0.a     // Catch:{ all -> 0x00d7 }
            byte r0 = r0.g     // Catch:{ all -> 0x00d7 }
            if (r0 == r8) goto L_0x0190
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.b     // Catch:{ all -> 0x00d7 }
            boolean r0 = r0.d     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x0165
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x00d7 }
            boolean r0 = r0.e     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x0165
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x00d7 }
            android.content.Context r0 = r0.s     // Catch:{ all -> 0x00d7 }
            int r0 = com.tencent.bugly.beta.global.a.a(r0)     // Catch:{ all -> 0x00d7 }
            if (r0 != r6) goto L_0x0147
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x00d7 }
            boolean r0 = r0.S     // Catch:{ all -> 0x00d7 }
            if (r0 != 0) goto L_0x0157
        L_0x0147:
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x00d7 }
            android.content.Context r0 = r0.s     // Catch:{ all -> 0x00d7 }
            int r0 = com.tencent.bugly.beta.global.a.a(r0)     // Catch:{ all -> 0x00d7 }
            if (r0 != r9) goto L_0x015d
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x00d7 }
            boolean r0 = r0.T     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x015d
        L_0x0157:
            r10.c()     // Catch:{ all -> 0x00d7 }
            monitor-exit(r1)     // Catch:{ all -> 0x00d7 }
            goto L_0x00ce
        L_0x015d:
            if (r12 != 0) goto L_0x0162
            r10.a(r11)     // Catch:{ all -> 0x00d7 }
        L_0x0162:
            monitor-exit(r1)     // Catch:{ all -> 0x00d7 }
            goto L_0x00ce
        L_0x0165:
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.b     // Catch:{ all -> 0x00d7 }
            long r2 = r0.c     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.b     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.y r0 = r0.a     // Catch:{ all -> 0x00d7 }
            long r4 = r0.i     // Catch:{ all -> 0x00d7 }
            long r2 = r2 + r4
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00d7 }
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 > 0) goto L_0x018d
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.b     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.y r0 = r0.a     // Catch:{ all -> 0x00d7 }
            int r0 = r0.h     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r2 = r10.b     // Catch:{ all -> 0x00d7 }
            int r2 = r2.b     // Catch:{ all -> 0x00d7 }
            int r0 = r0 - r2
            if (r0 <= 0) goto L_0x018d
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = r10.b     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.proguard.y r0 = r0.a     // Catch:{ all -> 0x00d7 }
            byte r0 = r0.g     // Catch:{ all -> 0x00d7 }
            if (r0 != r7) goto L_0x0190
        L_0x018d:
            monitor-exit(r1)     // Catch:{ all -> 0x00d7 }
            goto L_0x00ce
        L_0x0190:
            if (r12 != 0) goto L_0x01bd
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x00d7 }
            android.content.Context r0 = r0.s     // Catch:{ all -> 0x00d7 }
            int r0 = com.tencent.bugly.beta.global.a.a(r0)     // Catch:{ all -> 0x00d7 }
            if (r0 != r6) goto L_0x01a2
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x00d7 }
            boolean r0 = r0.S     // Catch:{ all -> 0x00d7 }
            if (r0 != 0) goto L_0x01b2
        L_0x01a2:
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x00d7 }
            android.content.Context r0 = r0.s     // Catch:{ all -> 0x00d7 }
            int r0 = com.tencent.bugly.beta.global.a.a(r0)     // Catch:{ all -> 0x00d7 }
            if (r0 != r9) goto L_0x01ba
            com.tencent.bugly.beta.global.e r0 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x00d7 }
            boolean r0 = r0.T     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x01ba
        L_0x01b2:
            if (r11 != 0) goto L_0x01ba
            r10.c()     // Catch:{ all -> 0x00d7 }
            monitor-exit(r1)     // Catch:{ all -> 0x00d7 }
            goto L_0x00ce
        L_0x01ba:
            r10.a(r11)     // Catch:{ all -> 0x00d7 }
        L_0x01bd:
            monitor-exit(r1)     // Catch:{ all -> 0x00d7 }
            goto L_0x00ce
        L_0x01c0:
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r0 = r10.f     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x01e6
            com.tencent.bugly.beta.global.d r0 = new com.tencent.bugly.beta.global.d     // Catch:{ all -> 0x00d7 }
            r2 = 18
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00d7 }
            r4 = 0
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r5 = r10.f     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r4 = 1
            r5 = 1
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r4 = 2
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r11)     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r0.<init>(r2, r3)     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.utils.e.a(r0)     // Catch:{ all -> 0x00d7 }
            goto L_0x01bd
        L_0x01e6:
            if (r11 == 0) goto L_0x01bd
            if (r12 != 0) goto L_0x01bd
            com.tencent.bugly.beta.global.d r0 = new com.tencent.bugly.beta.global.d     // Catch:{ all -> 0x00d7 }
            r2 = 5
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00d7 }
            r4 = 0
            java.lang.String r5 = com.tencent.bugly.beta.Beta.strToastYourAreTheLatestVersion     // Catch:{ all -> 0x00d7 }
            r3[r4] = r5     // Catch:{ all -> 0x00d7 }
            r0.<init>(r2, r3)     // Catch:{ all -> 0x00d7 }
            com.tencent.bugly.beta.utils.e.a(r0)     // Catch:{ all -> 0x00d7 }
            goto L_0x01bd
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.upgrade.c.a(boolean, boolean, int, com.tencent.bugly.proguard.y, java.lang.String):void");
    }

    private y a() {
        if (this.b == null) {
            return null;
        }
        return this.b.a;
    }

    private DownloadTask b() {
        y a2 = a();
        if (a2 == null) {
            return null;
        }
        if (this.c == null) {
            this.c = e.E.p.a(a2.f.b, e.E.t.getAbsolutePath(), null, this.b.a.f.a);
        }
        return this.c;
    }

    private void c() {
        y a2 = a();
        if (a2 != null) {
            if (this.c == null) {
                this.c = b();
            }
            if (this.c != null) {
                com.tencent.bugly.beta.global.a.a("st.bch", this.b);
                BetaReceiver.addTask(this.c);
                if (this.c.getStatus() != 1) {
                    this.c.download();
                } else if (this.g && com.tencent.bugly.beta.global.a.a(e.E.s, this.c.getSaveFile(), a2.f.a)) {
                    p.a.a(new w("install", System.currentTimeMillis(), 0, 0, a2.e, a2.m, a2.p, null));
                } else if (e.E.d) {
                    a(this.g);
                }
            }
        }
    }

    private void a(boolean z) {
        boolean z2 = false;
        y a2 = a();
        if (a2 != null) {
            if (System.currentTimeMillis() <= a2.a() - 86400000) {
                an.e(System.currentTimeMillis() + "ms", new Object[0]);
                return;
            }
            f.a.a(e.E.p, a2.l);
            if (this.c == null) {
                this.c = b();
            }
            if (this.c == null) {
                return;
            }
            if (z || this.c.getStatus() != 2) {
                this.c.addListener(this.m);
                if (this.d != null) {
                    this.c.addListener(this.d);
                }
                h hVar = h.v;
                hVar.a(a2, this.c);
                hVar.r = new d(3, this.b, this.c);
                hVar.s = new d(4, this.b, this.c, Boolean.valueOf(z));
                this.b.c = System.currentTimeMillis();
                com.tencent.bugly.beta.global.a.a("st.bch", this.b);
                if (z) {
                    f.a.a((Runnable) new d(2, hVar, Boolean.valueOf(z)), 3000);
                    return;
                }
                f fVar = f.a;
                Object[] objArr = new Object[2];
                objArr[0] = hVar;
                if (z || a2.g == 2) {
                    z2 = true;
                }
                objArr[1] = Boolean.valueOf(z2);
                fVar.a(new d(2, objArr));
                return;
            }
            an.a("Task is downloading %s %s", a2.m, this.c.getDownloadUrl());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00bf, code lost:
        if (r8.a.p == 2) goto L_0x00c1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(boolean r12, boolean r13, int r14) {
        /*
            r11 = this;
            r3 = 3
            r10 = 2
            r2 = 1
            r7 = 0
            java.lang.Object r9 = r11.l
            monitor-enter(r9)
            java.lang.String r0 = "st.bch"
            android.os.Parcelable$Creator<com.tencent.bugly.beta.upgrade.BetaGrayStrategy> r1 = com.tencent.bugly.beta.upgrade.BetaGrayStrategy.CREATOR     // Catch:{ all -> 0x00dc }
            android.os.Parcelable r0 = com.tencent.bugly.beta.global.a.a(r0, r1)     // Catch:{ all -> 0x00dc }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = (com.tencent.bugly.beta.upgrade.BetaGrayStrategy) r0     // Catch:{ all -> 0x00dc }
            if (r12 == 0) goto L_0x0109
            if (r0 == 0) goto L_0x0109
            com.tencent.bugly.proguard.y r1 = r0.a     // Catch:{ all -> 0x00dc }
            if (r1 == 0) goto L_0x0109
            com.tencent.bugly.proguard.y r1 = r0.a     // Catch:{ all -> 0x00dc }
            int r1 = r1.p     // Catch:{ all -> 0x00dc }
            if (r1 != r3) goto L_0x0109
            r0 = 0
            r8 = r0
        L_0x0022:
            com.tencent.bugly.beta.upgrade.a r0 = r11.n     // Catch:{ all -> 0x00dc }
            if (r0 == 0) goto L_0x0030
            com.tencent.bugly.beta.upgrade.a r0 = r11.n     // Catch:{ all -> 0x00dc }
            boolean r0 = r0.d     // Catch:{ all -> 0x00dc }
            if (r0 != 0) goto L_0x0030
            boolean r0 = r11.p     // Catch:{ all -> 0x00dc }
            if (r0 == r12) goto L_0x00df
        L_0x0030:
            r11.p = r12     // Catch:{ all -> 0x00dc }
            com.tencent.bugly.beta.upgrade.a r0 = r11.n     // Catch:{ all -> 0x00dc }
            if (r0 == 0) goto L_0x003b
            com.tencent.bugly.beta.upgrade.a r0 = r11.n     // Catch:{ all -> 0x00dc }
            r1 = 1
            r0.d = r1     // Catch:{ all -> 0x00dc }
        L_0x003b:
            com.tencent.bugly.beta.upgrade.a r0 = new com.tencent.bugly.beta.upgrade.a     // Catch:{ all -> 0x00dc }
            r1 = 1
            r3 = 804(0x324, float:1.127E-42)
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x00dc }
            r5 = 0
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r12)     // Catch:{ all -> 0x00dc }
            r4[r5] = r6     // Catch:{ all -> 0x00dc }
            r5 = 1
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r13)     // Catch:{ all -> 0x00dc }
            r4[r5] = r6     // Catch:{ all -> 0x00dc }
            r5 = 2
            r4[r5] = r8     // Catch:{ all -> 0x00dc }
            r0.<init>(r1, r3, r4)     // Catch:{ all -> 0x00dc }
            r11.n = r0     // Catch:{ all -> 0x00dc }
            com.tencent.bugly.beta.global.d r0 = new com.tencent.bugly.beta.global.d     // Catch:{ all -> 0x00dc }
            r1 = 12
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00dc }
            r4 = 0
            r5 = 0
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x00dc }
            r3[r4] = r5     // Catch:{ all -> 0x00dc }
            r4 = 1
            com.tencent.bugly.beta.upgrade.a r5 = r11.n     // Catch:{ all -> 0x00dc }
            r3[r4] = r5     // Catch:{ all -> 0x00dc }
            r0.<init>(r1, r3)     // Catch:{ all -> 0x00dc }
            r11.o = r0     // Catch:{ all -> 0x00dc }
            java.lang.String r3 = ""
            r4 = 0
            if (r8 == 0) goto L_0x0085
            com.tencent.bugly.proguard.y r0 = r8.a     // Catch:{ Throwable -> 0x00d1 }
            if (r0 == 0) goto L_0x0085
            com.tencent.bugly.proguard.y r0 = r8.a     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r3 = r0.m     // Catch:{ Throwable -> 0x00d1 }
            com.tencent.bugly.proguard.y r0 = r8.a     // Catch:{ Throwable -> 0x00d1 }
            long r4 = r0.o     // Catch:{ Throwable -> 0x00d1 }
        L_0x0085:
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ Throwable -> 0x00d1 }
            r6.<init>()     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r0 = "G16"
            com.tencent.bugly.beta.global.e r1 = com.tencent.bugly.beta.global.e.E     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r1 = r1.L     // Catch:{ Throwable -> 0x00d1 }
            r6.put(r0, r1)     // Catch:{ Throwable -> 0x00d1 }
            com.tencent.bugly.proguard.z r1 = new com.tencent.bugly.proguard.z     // Catch:{ Throwable -> 0x00d1 }
            if (r12 == 0) goto L_0x00cf
        L_0x0098:
            r1.<init>(r2, r3, r4, r6)     // Catch:{ Throwable -> 0x00d1 }
            byte[] r2 = com.tencent.bugly.proguard.ah.a(r1)     // Catch:{ Throwable -> 0x00d1 }
            com.tencent.bugly.beta.upgrade.b r0 = com.tencent.bugly.beta.upgrade.b.a     // Catch:{ Throwable -> 0x00d1 }
            r1 = 804(0x324, float:1.127E-42)
            com.tencent.bugly.beta.upgrade.a r3 = r11.n     // Catch:{ Throwable -> 0x00d1 }
            com.tencent.bugly.beta.global.e r4 = com.tencent.bugly.beta.global.e.E     // Catch:{ Throwable -> 0x00d1 }
            com.tencent.bugly.beta.upgrade.BetaUploadStrategy r4 = r4.F     // Catch:{ Throwable -> 0x00d1 }
            com.tencent.bugly.proguard.bg r4 = r4.a     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r5 = r4.e     // Catch:{ Throwable -> 0x00d1 }
            r4 = r12
            r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x00d1 }
        L_0x00b1:
            if (r12 == 0) goto L_0x00b5
            if (r13 == 0) goto L_0x00c1
        L_0x00b5:
            if (r8 == 0) goto L_0x00cd
            com.tencent.bugly.proguard.y r0 = r8.a     // Catch:{ all -> 0x00dc }
            if (r0 == 0) goto L_0x00cd
            com.tencent.bugly.proguard.y r0 = r8.a     // Catch:{ all -> 0x00dc }
            int r0 = r0.p     // Catch:{ all -> 0x00dc }
            if (r0 != r10) goto L_0x00cd
        L_0x00c1:
            com.tencent.bugly.beta.global.d r0 = r11.o     // Catch:{ all -> 0x00dc }
            com.tencent.bugly.beta.utils.e.b(r0)     // Catch:{ all -> 0x00dc }
            com.tencent.bugly.beta.global.d r0 = r11.o     // Catch:{ all -> 0x00dc }
            r2 = 6000(0x1770, double:2.9644E-320)
            com.tencent.bugly.beta.utils.e.a(r0, r2)     // Catch:{ all -> 0x00dc }
        L_0x00cd:
            monitor-exit(r9)     // Catch:{ all -> 0x00dc }
            return
        L_0x00cf:
            r2 = r7
            goto L_0x0098
        L_0x00d1:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.b(r0)     // Catch:{ all -> 0x00dc }
            if (r1 != 0) goto L_0x00b1
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x00dc }
            goto L_0x00b1
        L_0x00dc:
            r0 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x00dc }
            throw r0
        L_0x00df:
            com.tencent.bugly.beta.upgrade.a r1 = r11.n     // Catch:{ all -> 0x00dc }
            monitor-enter(r1)     // Catch:{ all -> 0x00dc }
            com.tencent.bugly.beta.upgrade.a r0 = r11.n     // Catch:{ all -> 0x0106 }
            java.lang.Object[] r0 = r0.c     // Catch:{ all -> 0x0106 }
            r2 = 0
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r12)     // Catch:{ all -> 0x0106 }
            r0[r2] = r3     // Catch:{ all -> 0x0106 }
            com.tencent.bugly.beta.upgrade.a r0 = r11.n     // Catch:{ all -> 0x0106 }
            java.lang.Object[] r0 = r0.c     // Catch:{ all -> 0x0106 }
            r2 = 1
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r13)     // Catch:{ all -> 0x0106 }
            r0[r2] = r3     // Catch:{ all -> 0x0106 }
            monitor-exit(r1)     // Catch:{ all -> 0x0106 }
            com.tencent.bugly.beta.global.d r0 = r11.o     // Catch:{ all -> 0x00dc }
            java.lang.Object[] r0 = r0.b     // Catch:{ all -> 0x00dc }
            r1 = 0
            r2 = 0
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ all -> 0x00dc }
            r0[r1] = r2     // Catch:{ all -> 0x00dc }
            goto L_0x00b1
        L_0x0106:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0106 }
            throw r0     // Catch:{ all -> 0x00dc }
        L_0x0109:
            r8 = r0
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.upgrade.c.a(boolean, boolean, int):void");
    }

    public void a(bg bgVar) {
        if (e.E.F == null) {
            e.E.F = new BetaUploadStrategy();
        }
        if (bgVar != null && e.E.F.b != bgVar.h) {
            e.E.F.b = bgVar.h;
            e.E.F.a.b = bgVar.b;
            e.E.F.a.c = bgVar.c;
            e.E.F.a.h = bgVar.h;
            if (ap.c(bgVar.d)) {
                e.E.F.a.d = bgVar.d;
            }
            if (ap.c(bgVar.e)) {
                e.E.F.a.e = bgVar.e;
            }
            if (bgVar.f != null && !TextUtils.isEmpty(bgVar.f.a)) {
                e.E.F.a.f.a = bgVar.f.a;
            }
            if (bgVar.g != null && bgVar.g.size() > 0) {
                e.E.F.a.g = bgVar.g;
            }
            if (ap.c(bgVar.i)) {
                e.E.F.a.i = bgVar.i;
            }
            if (ap.c(bgVar.j)) {
                e.E.F.a.j = bgVar.j;
            }
            com.tencent.bugly.beta.global.a.a("us.bch", e.E.F);
        }
    }
}
