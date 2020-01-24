package com.tencent.bugly.beta.download;

import com.tencent.bugly.beta.ui.h;

/* compiled from: BUGLY */
public class a implements DownloadListener {
    final int a;
    final Object[] b;

    public a(int i, Object... objArr) {
        this.a = i;
        this.b = objArr;
    }

    public void onReceive(DownloadTask task) {
        switch (this.a) {
            case 2:
                ((h) this.b[0]).a(task);
                return;
            default:
                return;
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:90:? A[Catch:{  }, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCompleted(com.tencent.bugly.beta.download.DownloadTask r16) {
        /*
            r15 = this;
            int r1 = r15.a     // Catch:{ Exception -> 0x001e }
            switch(r1) {
                case 1: goto L_0x0006;
                case 2: goto L_0x00c7;
                case 3: goto L_0x00d5;
                case 4: goto L_0x021a;
                default: goto L_0x0005;
            }     // Catch:{ Exception -> 0x001e }
        L_0x0005:
            return
        L_0x0006:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x001e }
            r2 = 0
            r4 = r1[r2]     // Catch:{ Exception -> 0x001e }
            monitor-enter(r4)     // Catch:{ Exception -> 0x001e }
            java.lang.Object[] r1 = r15.b     // Catch:{ all -> 0x001b }
            r2 = 1
            r1 = r1[r2]     // Catch:{ all -> 0x001b }
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ all -> 0x001b }
            int r2 = r1.size()     // Catch:{ all -> 0x001b }
            if (r2 != 0) goto L_0x0029
            monitor-exit(r4)     // Catch:{ all -> 0x001b }
            goto L_0x0005
        L_0x001b:
            r1 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x001b }
            throw r1     // Catch:{ Exception -> 0x001e }
        L_0x001e:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.b(r1)
            if (r2 != 0) goto L_0x0005
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0005
        L_0x0029:
            r3 = 0
            java.util.Collection r2 = r1.values()     // Catch:{ all -> 0x001b }
            java.util.Iterator r5 = r2.iterator()     // Catch:{ all -> 0x001b }
        L_0x0032:
            boolean r2 = r5.hasNext()     // Catch:{ all -> 0x001b }
            if (r2 == 0) goto L_0x0049
            java.lang.Object r2 = r5.next()     // Catch:{ all -> 0x001b }
            com.tencent.bugly.beta.download.DownloadTask r2 = (com.tencent.bugly.beta.download.DownloadTask) r2     // Catch:{ all -> 0x001b }
            int r2 = r2.getStatus()     // Catch:{ all -> 0x001b }
            r6 = 1
            if (r2 != r6) goto L_0x0354
            int r2 = r3 + 1
        L_0x0047:
            r3 = r2
            goto L_0x0032
        L_0x0049:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x001b }
            r2.<init>()     // Catch:{ all -> 0x001b }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x001b }
            java.lang.String r5 = " has completed"
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ all -> 0x001b }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x001b }
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x001b }
            com.tencent.bugly.proguard.an.c(r2, r5)     // Catch:{ all -> 0x001b }
            int r2 = r1.size()     // Catch:{ all -> 0x001b }
            if (r3 >= r2) goto L_0x006b
            monitor-exit(r4)     // Catch:{ all -> 0x001b }
            goto L_0x0005
        L_0x006b:
            java.util.Set r2 = r1.keySet()     // Catch:{ all -> 0x001b }
            java.util.Iterator r5 = r2.iterator()     // Catch:{ all -> 0x001b }
        L_0x0073:
            boolean r2 = r5.hasNext()     // Catch:{ all -> 0x001b }
            if (r2 == 0) goto L_0x00af
            java.lang.Object r2 = r5.next()     // Catch:{ all -> 0x001b }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x001b }
            java.lang.Object r3 = r1.get(r2)     // Catch:{ all -> 0x001b }
            com.tencent.bugly.beta.download.DownloadTask r3 = (com.tencent.bugly.beta.download.DownloadTask) r3     // Catch:{ all -> 0x001b }
            java.io.File r3 = r3.getSaveFile()     // Catch:{ all -> 0x001b }
            if (r3 == 0) goto L_0x0073
            java.lang.Object r3 = r1.get(r2)     // Catch:{ all -> 0x001b }
            com.tencent.bugly.beta.download.DownloadTask r3 = (com.tencent.bugly.beta.download.DownloadTask) r3     // Catch:{ all -> 0x001b }
            java.io.File r3 = r3.getSaveFile()     // Catch:{ all -> 0x001b }
            boolean r3 = r3.exists()     // Catch:{ all -> 0x001b }
            if (r3 == 0) goto L_0x0073
            com.tencent.bugly.beta.global.ResBean r6 = com.tencent.bugly.beta.global.ResBean.a     // Catch:{ all -> 0x001b }
            java.lang.Object r3 = r1.get(r2)     // Catch:{ all -> 0x001b }
            com.tencent.bugly.beta.download.DownloadTask r3 = (com.tencent.bugly.beta.download.DownloadTask) r3     // Catch:{ all -> 0x001b }
            java.io.File r3 = r3.getSaveFile()     // Catch:{ all -> 0x001b }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x001b }
            r6.a(r2, r3)     // Catch:{ all -> 0x001b }
            goto L_0x0073
        L_0x00af:
            java.lang.String r1 = "rb.bch"
            com.tencent.bugly.beta.global.ResBean r2 = com.tencent.bugly.beta.global.ResBean.a     // Catch:{ all -> 0x001b }
            com.tencent.bugly.beta.global.a.a(r1, r2)     // Catch:{ all -> 0x001b }
            java.lang.Object[] r1 = r15.b     // Catch:{ all -> 0x001b }
            r2 = 0
            r1 = r1[r2]     // Catch:{ all -> 0x001b }
            com.tencent.bugly.beta.global.f r1 = (com.tencent.bugly.beta.global.f) r1     // Catch:{ all -> 0x001b }
            r1.a()     // Catch:{ all -> 0x001b }
            r1.b()     // Catch:{ all -> 0x001b }
            monitor-exit(r4)     // Catch:{ all -> 0x001b }
            goto L_0x0005
        L_0x00c7:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x001e }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.ui.h r1 = (com.tencent.bugly.beta.ui.h) r1     // Catch:{ Exception -> 0x001e }
            r0 = r16
            r1.a(r0)     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        L_0x00d5:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x001e }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x001e }
            r0 = r1
            com.tencent.bugly.beta.upgrade.c r0 = (com.tencent.bugly.beta.upgrade.c) r0     // Catch:{ Exception -> 0x001e }
            r12 = r0
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r13 = r12.b     // Catch:{ Exception -> 0x001e }
            if (r13 == 0) goto L_0x0005
            com.tencent.bugly.proguard.y r1 = r13.a     // Catch:{ Exception -> 0x001e }
            if (r1 == 0) goto L_0x0005
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r1 = r12.f     // Catch:{ Exception -> 0x001e }
            if (r1 == 0) goto L_0x010d
            com.tencent.bugly.beta.global.d r1 = new com.tencent.bugly.beta.global.d     // Catch:{ Exception -> 0x001e }
            r2 = 18
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x001e }
            r4 = 0
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r5 = r12.f     // Catch:{ Exception -> 0x001e }
            r3[r4] = r5     // Catch:{ Exception -> 0x001e }
            r4 = 1
            r5 = 3
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Exception -> 0x001e }
            r3[r4] = r5     // Catch:{ Exception -> 0x001e }
            r4 = 2
            boolean r5 = r12.g     // Catch:{ Exception -> 0x001e }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ Exception -> 0x001e }
            r3[r4] = r5     // Catch:{ Exception -> 0x001e }
            r1.<init>(r2, r3)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.utils.e.a(r1)     // Catch:{ Exception -> 0x001e }
        L_0x010d:
            java.lang.String r1 = "apk download completed"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.an.a(r1, r2)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.p r14 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.w r1 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x001e }
            java.lang.String r2 = "download"
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x001e }
            r5 = 0
            long r6 = r16.getCostTime()     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r8 = r13.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.v r8 = r8.e     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r9 = r13.a     // Catch:{ Exception -> 0x001e }
            java.lang.String r9 = r9.m     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r10 = r13.a     // Catch:{ Exception -> 0x001e }
            int r10 = r10.p     // Catch:{ Exception -> 0x001e }
            r11 = 0
            r1.<init>(r2, r3, r5, r6, r8, r9, r10, r11)     // Catch:{ Exception -> 0x001e }
            r14.a(r1)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.global.e r1 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x001e }
            boolean r1 = r1.ad     // Catch:{ Exception -> 0x001e }
            if (r1 == 0) goto L_0x0005
            java.io.File r2 = r16.getSaveFile()     // Catch:{ Exception -> 0x001e }
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x001e }
            r3 = 1
            r1 = r1[r3]     // Catch:{ Exception -> 0x001e }
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.global.e r3 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x001e }
            android.content.Context r3 = r3.s     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r4 = r13.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.u r4 = r4.f     // Catch:{ Exception -> 0x001e }
            java.lang.String r4 = r4.a     // Catch:{ Exception -> 0x001e }
            boolean r2 = com.tencent.bugly.beta.global.a.a(r3, r2, r4)     // Catch:{ Exception -> 0x001e }
            if (r2 == 0) goto L_0x0187
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x001e }
            r2 = 1
            r3 = 0
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x001e }
            r1[r2] = r3     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.p r12 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.w r1 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x001e }
            java.lang.String r2 = "install"
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x001e }
            r5 = 0
            r6 = 0
            com.tencent.bugly.proguard.y r8 = r13.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.v r8 = r8.e     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r9 = r13.a     // Catch:{ Exception -> 0x001e }
            java.lang.String r9 = r9.m     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r10 = r13.a     // Catch:{ Exception -> 0x001e }
            int r10 = r10.p     // Catch:{ Exception -> 0x001e }
            r11 = 0
            r1.<init>(r2, r3, r5, r6, r8, r9, r10, r11)     // Catch:{ Exception -> 0x001e }
            r12.a(r1)     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        L_0x0187:
            int r2 = r1.intValue()     // Catch:{ Exception -> 0x001e }
            r3 = 2
            if (r2 >= r3) goto L_0x01fe
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x001e }
            r3 = 24
            if (r2 < r3) goto L_0x01b0
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x001e }
            r2 = 1
            r3 = 0
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x001e }
            r1[r2] = r3     // Catch:{ Exception -> 0x001e }
            r1 = 2080(0x820, float:2.915E-42)
            java.lang.String r2 = "安装失败，请检查您的App是否兼容7.0设备"
            r0 = r16
            r15.onFailed(r0, r1, r2)     // Catch:{ Exception -> 0x001e }
            r1 = 1
            r0 = r16
            r0.delete(r1)     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        L_0x01b0:
            java.lang.Object[] r2 = r15.b     // Catch:{ Exception -> 0x001e }
            r3 = 1
            int r1 = r1.intValue()     // Catch:{ Exception -> 0x001e }
            int r1 = r1 + 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x001e }
            r2[r3] = r1     // Catch:{ Exception -> 0x001e }
            r1 = 1
            r0 = r16
            r0.delete(r1)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.global.e r1 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.b r1 = r1.p     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r2 = r13.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.u r2 = r2.f     // Catch:{ Exception -> 0x001e }
            java.lang.String r2 = r2.b     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.global.e r3 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x001e }
            java.io.File r3 = r3.t     // Catch:{ Exception -> 0x001e }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ Exception -> 0x001e }
            r4 = 0
            com.tencent.bugly.proguard.y r5 = r13.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.u r5 = r5.f     // Catch:{ Exception -> 0x001e }
            java.lang.String r5 = r5.b     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r1 = r1.a(r2, r3, r4, r5)     // Catch:{ Exception -> 0x001e }
            r12.c = r1     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.ui.h r1 = com.tencent.bugly.beta.ui.h.v     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r2 = r13.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r3 = r12.c     // Catch:{ Exception -> 0x001e }
            r1.a(r2, r3)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r1 = r12.c     // Catch:{ Exception -> 0x001e }
            r1.addListener(r15)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r1 = r12.c     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.BetaReceiver.addTask(r1)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r1 = r12.c     // Catch:{ Exception -> 0x001e }
            r1.download()     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        L_0x01fe:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x001e }
            r2 = 1
            r3 = 0
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x001e }
            r1[r2] = r3     // Catch:{ Exception -> 0x001e }
            r1 = 2080(0x820, float:2.915E-42)
            java.lang.String r2 = "file md5 verify fail"
            r0 = r16
            r15.onFailed(r0, r1, r2)     // Catch:{ Exception -> 0x001e }
            r1 = 1
            r0 = r16
            r0.delete(r1)     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        L_0x021a:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x001e }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.q r1 = (com.tencent.bugly.proguard.q) r1     // Catch:{ Exception -> 0x001e }
            java.lang.Object[] r2 = r15.b     // Catch:{ Exception -> 0x001e }
            r3 = 1
            r2 = r2[r3]     // Catch:{ Exception -> 0x001e }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r3 = r1.b     // Catch:{ Exception -> 0x001e }
            if (r3 == 0) goto L_0x0005
            com.tencent.bugly.proguard.y r4 = r3.a     // Catch:{ Exception -> 0x001e }
            if (r4 == 0) goto L_0x0005
            java.lang.String r4 = "patch download success !!!"
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.an.a(r4, r5)     // Catch:{ Exception -> 0x001e }
            java.io.File r4 = r16.getSaveFile()     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r5 = r3.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.u r5 = r5.f     // Catch:{ Exception -> 0x001e }
            java.lang.String r5 = r5.a     // Catch:{ Exception -> 0x001e }
            java.lang.String r6 = "SHA"
            boolean r5 = com.tencent.bugly.beta.global.a.a(r4, r5, r6)     // Catch:{ Exception -> 0x001e }
            if (r5 == 0) goto L_0x02dc
            java.lang.Object[] r2 = r15.b     // Catch:{ Exception -> 0x001e }
            r3 = 1
            r5 = 0
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Exception -> 0x001e }
            r2[r3] = r5     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x001e }
            java.io.File r2 = r2.H     // Catch:{ Exception -> 0x001e }
            boolean r2 = com.tencent.bugly.beta.global.a.a(r4, r2)     // Catch:{ Exception -> 0x001e }
            if (r2 == 0) goto L_0x02c7
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x001e }
            r2.<init>()     // Catch:{ Exception -> 0x001e }
            java.lang.String r3 = "copy "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x001e }
            java.lang.String r3 = r4.getAbsolutePath()     // Catch:{ Exception -> 0x001e }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x001e }
            java.lang.String r3 = " to "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.global.e r3 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x001e }
            java.io.File r3 = r3.H     // Catch:{ Exception -> 0x001e }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ Exception -> 0x001e }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x001e }
            java.lang.String r3 = " success!"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x001e }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x001e }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.an.c(r2, r3)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r2 = r1.c     // Catch:{ Exception -> 0x001e }
            if (r2 == 0) goto L_0x02ab
            java.lang.String r2 = "delete temp file"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.an.c(r2, r3)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r1 = r1.c     // Catch:{ Exception -> 0x001e }
            r2 = 1
            r1.delete(r2)     // Catch:{ Exception -> 0x001e }
        L_0x02ab:
            java.lang.String r1 = "UPLOAD_PATCH_RESULT"
            r2 = 0
            com.tencent.bugly.beta.global.a.a(r1, r2)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.tinker.TinkerManager r1 = com.tencent.bugly.beta.tinker.TinkerManager.getInstance()     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x001e }
            java.io.File r2 = r2.H     // Catch:{ Exception -> 0x001e }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.global.e r3 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x001e }
            boolean r3 = r3.V     // Catch:{ Exception -> 0x001e }
            r1.onDownloadSuccess(r2, r3)     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        L_0x02c7:
            java.lang.String r1 = "copy file failed"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.an.a(r1, r2)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.tinker.TinkerManager r1 = com.tencent.bugly.beta.tinker.TinkerManager.getInstance()     // Catch:{ Exception -> 0x001e }
            java.lang.String r2 = "copy file failure."
            r1.onDownloadFailure(r2)     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        L_0x02dc:
            int r4 = r2.intValue()     // Catch:{ Exception -> 0x001e }
            r5 = 2
            if (r4 >= r5) goto L_0x032e
            java.lang.Object[] r4 = r15.b     // Catch:{ Exception -> 0x001e }
            r5 = 1
            int r2 = r2.intValue()     // Catch:{ Exception -> 0x001e }
            int r2 = r2 + 1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x001e }
            r4[r5] = r2     // Catch:{ Exception -> 0x001e }
            r2 = 1
            r0 = r16
            r0.delete(r2)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.b r2 = r2.p     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r4 = r3.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.u r4 = r4.f     // Catch:{ Exception -> 0x001e }
            java.lang.String r4 = r4.b     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.global.e r5 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x001e }
            java.io.File r5 = r5.t     // Catch:{ Exception -> 0x001e }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ Exception -> 0x001e }
            r6 = 0
            com.tencent.bugly.proguard.y r3 = r3.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.u r3 = r3.f     // Catch:{ Exception -> 0x001e }
            java.lang.String r3 = r3.b     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r2 = r2.a(r4, r5, r6, r3)     // Catch:{ Exception -> 0x001e }
            r1.c = r2     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r2 = r1.c     // Catch:{ Exception -> 0x001e }
            r3 = 0
            r2.setNeededNotify(r3)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r2 = r1.c     // Catch:{ Exception -> 0x001e }
            r2.addListener(r15)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r2 = r1.c     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.BetaReceiver.addTask(r2)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.download.DownloadTask r1 = r1.c     // Catch:{ Exception -> 0x001e }
            r1.download()     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        L_0x032e:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x001e }
            r2 = 1
            r3 = 0
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x001e }
            r1[r2] = r3     // Catch:{ Exception -> 0x001e }
            r1 = 2080(0x820, float:2.915E-42)
            java.lang.String r2 = "file sha1 verify fail"
            r0 = r16
            r15.onFailed(r0, r1, r2)     // Catch:{ Exception -> 0x001e }
            r1 = 1
            r0 = r16
            r0.delete(r1)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.tinker.TinkerManager r1 = com.tencent.bugly.beta.tinker.TinkerManager.getInstance()     // Catch:{ Exception -> 0x001e }
            java.lang.String r2 = "retry download patch too many times."
            r1.onDownloadFailure(r2)     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        L_0x0354:
            r2 = r3
            goto L_0x0047
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.download.a.onCompleted(com.tencent.bugly.beta.download.DownloadTask):void");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:29:? A[Catch:{  }, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onFailed(com.tencent.bugly.beta.download.DownloadTask r14, int r15, java.lang.String r16) {
        /*
            r13 = this;
            int r1 = r13.a     // Catch:{ Exception -> 0x001e }
            switch(r1) {
                case 1: goto L_0x0006;
                case 2: goto L_0x0029;
                case 3: goto L_0x0034;
                case 4: goto L_0x0084;
                default: goto L_0x0005;
            }     // Catch:{ Exception -> 0x001e }
        L_0x0005:
            return
        L_0x0006:
            java.lang.Object[] r1 = r13.b     // Catch:{ Exception -> 0x001e }
            r2 = 0
            r2 = r1[r2]     // Catch:{ Exception -> 0x001e }
            monitor-enter(r2)     // Catch:{ Exception -> 0x001e }
            java.lang.Object[] r1 = r13.b     // Catch:{ all -> 0x001b }
            r3 = 0
            r1 = r1[r3]     // Catch:{ all -> 0x001b }
            com.tencent.bugly.beta.global.f r1 = (com.tencent.bugly.beta.global.f) r1     // Catch:{ all -> 0x001b }
            r1.a()     // Catch:{ all -> 0x001b }
            r1.b()     // Catch:{ all -> 0x001b }
            monitor-exit(r2)     // Catch:{ all -> 0x001b }
            goto L_0x0005
        L_0x001b:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x001b }
            throw r1     // Catch:{ Exception -> 0x001e }
        L_0x001e:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.b(r1)
            if (r2 != 0) goto L_0x0005
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0005
        L_0x0029:
            java.lang.Object[] r1 = r13.b     // Catch:{ Exception -> 0x001e }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.ui.h r1 = (com.tencent.bugly.beta.ui.h) r1     // Catch:{ Exception -> 0x001e }
            r1.a(r14)     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        L_0x0034:
            java.lang.Object[] r1 = r13.b     // Catch:{ Exception -> 0x001e }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x001e }
            r0 = r1
            com.tencent.bugly.beta.upgrade.c r0 = (com.tencent.bugly.beta.upgrade.c) r0     // Catch:{ Exception -> 0x001e }
            r10 = r0
            if (r14 == 0) goto L_0x0068
            com.tencent.bugly.proguard.p r12 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.w r1 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x001e }
            java.lang.String r2 = "download"
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x001e }
            r5 = 1
            long r6 = r14.getCostTime()     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r8 = r10.b     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r8 = r8.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.v r8 = r8.e     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r9 = r10.b     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r9 = r9.a     // Catch:{ Exception -> 0x001e }
            java.lang.String r9 = r9.m     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r10 = r10.b     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r10 = r10.a     // Catch:{ Exception -> 0x001e }
            int r10 = r10.p     // Catch:{ Exception -> 0x001e }
            r11 = 0
            r1.<init>(r2, r3, r5, r6, r8, r9, r10, r11)     // Catch:{ Exception -> 0x001e }
            r12.a(r1)     // Catch:{ Exception -> 0x001e }
        L_0x0068:
            java.lang.String r1 = "upgrade failed：(%d)%s"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x001e }
            r3 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r15)     // Catch:{ Exception -> 0x001e }
            r2[r3] = r4     // Catch:{ Exception -> 0x001e }
            r3 = 1
            r2[r3] = r16     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.an.e(r1, r2)     // Catch:{ Exception -> 0x001e }
            java.lang.String r1 = com.tencent.bugly.proguard.an.b     // Catch:{ Exception -> 0x001e }
            java.lang.String r2 = "download fail, please try later"
            android.util.Log.e(r1, r2)     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        L_0x0084:
            java.lang.Object[] r1 = r13.b     // Catch:{ Exception -> 0x001e }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x001e }
            r0 = r1
            com.tencent.bugly.proguard.q r0 = (com.tencent.bugly.proguard.q) r0     // Catch:{ Exception -> 0x001e }
            r10 = r0
            if (r14 == 0) goto L_0x00b8
            com.tencent.bugly.proguard.p r12 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.w r1 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x001e }
            java.lang.String r2 = "download"
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x001e }
            r5 = 1
            long r6 = r14.getCostTime()     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r8 = r10.b     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r8 = r8.a     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.v r8 = r8.e     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r9 = r10.b     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r9 = r9.a     // Catch:{ Exception -> 0x001e }
            java.lang.String r9 = r9.m     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r10 = r10.b     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.y r10 = r10.a     // Catch:{ Exception -> 0x001e }
            int r10 = r10.p     // Catch:{ Exception -> 0x001e }
            r11 = 0
            r1.<init>(r2, r3, r5, r6, r8, r9, r10, r11)     // Catch:{ Exception -> 0x001e }
            r12.a(r1)     // Catch:{ Exception -> 0x001e }
        L_0x00b8:
            java.lang.String r1 = "hotfix download failed：(%d)%s"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x001e }
            r3 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r15)     // Catch:{ Exception -> 0x001e }
            r2[r3] = r4     // Catch:{ Exception -> 0x001e }
            r3 = 1
            r2[r3] = r16     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.proguard.an.e(r1, r2)     // Catch:{ Exception -> 0x001e }
            com.tencent.bugly.beta.tinker.TinkerManager r1 = com.tencent.bugly.beta.tinker.TinkerManager.getInstance()     // Catch:{ Exception -> 0x001e }
            r0 = r16
            r1.onDownloadFailure(r0)     // Catch:{ Exception -> 0x001e }
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.download.a.onFailed(com.tencent.bugly.beta.download.DownloadTask, int, java.lang.String):void");
    }
}
