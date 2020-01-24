package com.tencent.bugly.beta.global;

/* compiled from: BUGLY */
public class d implements Runnable {
    int a;
    public final Object[] b;

    public d(int i, Object... objArr) {
        this.a = i;
        this.b = objArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:229:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0167  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r17 = this;
            r5 = 0
            r6 = 2
            r16 = 1
            r0 = r17
            int r2 = r0.a     // Catch:{ Exception -> 0x0160 }
            switch(r2) {
                case 1: goto L_0x000c;
                case 2: goto L_0x018b;
                case 3: goto L_0x01a6;
                case 4: goto L_0x0221;
                case 5: goto L_0x0288;
                case 6: goto L_0x029a;
                case 7: goto L_0x02c8;
                case 8: goto L_0x02d6;
                case 9: goto L_0x0300;
                case 10: goto L_0x0351;
                case 11: goto L_0x0391;
                case 12: goto L_0x03eb;
                case 13: goto L_0x04a2;
                case 14: goto L_0x057d;
                case 15: goto L_0x0445;
                case 16: goto L_0x05f3;
                case 17: goto L_0x063d;
                case 18: goto L_0x06be;
                case 19: goto L_0x06ff;
                default: goto L_0x000b;
            }
        L_0x000b:
            return
        L_0x000c:
            java.lang.String r2 = "Beta async init start..."
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0154 }
            com.tencent.bugly.proguard.an.a(r2, r3)     // Catch:{ Exception -> 0x0154 }
            com.tencent.bugly.beta.global.e r14 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0154 }
            com.tencent.bugly.beta.global.e r15 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0154 }
            monitor-enter(r15)     // Catch:{ Exception -> 0x0154 }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x016c }
            android.content.pm.PackageInfo r3 = r14.z     // Catch:{ Exception -> 0x016c }
            android.content.pm.ApplicationInfo r3 = r3.applicationInfo     // Catch:{ Exception -> 0x016c }
            java.lang.String r3 = r3.publicSourceDir     // Catch:{ Exception -> 0x016c }
            r2.<init>(r3)     // Catch:{ Exception -> 0x016c }
            java.lang.String r3 = "MD5"
            java.lang.String r2 = com.tencent.bugly.proguard.ap.a(r2, r3)     // Catch:{ Exception -> 0x016c }
            r14.v = r2     // Catch:{ Exception -> 0x016c }
        L_0x002e:
            java.lang.String r2 = r14.v     // Catch:{ all -> 0x0172 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0172 }
            if (r2 == 0) goto L_0x0044
            java.lang.String r2 = "null"
            r14.v = r2     // Catch:{ all -> 0x0172 }
            java.lang.String r2 = "无法获取md5值"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.proguard.an.e(r2, r3)     // Catch:{ all -> 0x0172 }
        L_0x0044:
            boolean r2 = r14.C     // Catch:{ all -> 0x0172 }
            if (r2 == 0) goto L_0x004d
            java.io.File r2 = r14.t     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.beta.global.a.a(r2)     // Catch:{ all -> 0x0172 }
        L_0x004d:
            java.lang.String r2 = "st.bch"
            android.os.Parcelable$Creator<com.tencent.bugly.beta.upgrade.BetaGrayStrategy> r3 = com.tencent.bugly.beta.upgrade.BetaGrayStrategy.CREATOR     // Catch:{ all -> 0x0172 }
            android.os.Parcelable r2 = com.tencent.bugly.beta.global.a.a(r2, r3)     // Catch:{ all -> 0x0172 }
            r0 = r2
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = (com.tencent.bugly.beta.upgrade.BetaGrayStrategy) r0     // Catch:{ all -> 0x0172 }
            r13 = r0
            if (r13 == 0) goto L_0x0103
            com.tencent.bugly.proguard.y r2 = r13.a     // Catch:{ all -> 0x0172 }
            if (r2 == 0) goto L_0x0103
            com.tencent.bugly.proguard.y r2 = r13.a     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.proguard.v r2 = r2.e     // Catch:{ all -> 0x0172 }
            java.lang.String r2 = r2.i     // Catch:{ all -> 0x0172 }
            java.lang.String r2 = r2.toUpperCase()     // Catch:{ all -> 0x0172 }
            java.lang.String r3 = r14.v     // Catch:{ all -> 0x0172 }
            boolean r2 = android.text.TextUtils.equals(r2, r3)     // Catch:{ all -> 0x0172 }
            if (r2 == 0) goto L_0x00ec
            java.lang.String r2 = "st.bch"
            boolean r2 = com.tencent.bugly.beta.global.a.a(r2)     // Catch:{ all -> 0x0172 }
            if (r2 == 0) goto L_0x0180
            java.lang.String r2 = "installApkMd5"
            r3 = 0
            java.lang.String r2 = com.tencent.bugly.beta.global.a.b(r2, r3)     // Catch:{ all -> 0x0172 }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0172 }
            if (r3 != 0) goto L_0x0175
            java.lang.String r3 = r14.v     // Catch:{ all -> 0x0172 }
            boolean r2 = android.text.TextUtils.equals(r2, r3)     // Catch:{ all -> 0x0172 }
            if (r2 == 0) goto L_0x0175
            com.tencent.bugly.proguard.p r16 = com.tencent.bugly.proguard.p.a     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.proguard.w r2 = new com.tencent.bugly.proguard.w     // Catch:{ all -> 0x0172 }
            java.lang.String r3 = "active"
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0172 }
            r6 = 0
            r7 = 0
            r9 = 0
            com.tencent.bugly.proguard.y r10 = r13.a     // Catch:{ all -> 0x0172 }
            java.lang.String r10 = r10.m     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.proguard.y r11 = r13.a     // Catch:{ all -> 0x0172 }
            int r11 = r11.p     // Catch:{ all -> 0x0172 }
            r12 = 0
            r2.<init>(r3, r4, r6, r7, r9, r10, r11, r12)     // Catch:{ all -> 0x0172 }
            r0 = r16
            r0.a(r2)     // Catch:{ all -> 0x0172 }
        L_0x00b1:
            android.content.SharedPreferences r2 = r14.A     // Catch:{ all -> 0x0172 }
            android.content.SharedPreferences$Editor r2 = r2.edit()     // Catch:{ all -> 0x0172 }
            java.lang.String r3 = "installApkMd5"
            android.content.SharedPreferences$Editor r2 = r2.remove(r3)     // Catch:{ all -> 0x0172 }
            r2.apply()     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.beta.download.b r2 = r14.p     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.proguard.y r3 = r13.a     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.proguard.u r3 = r3.f     // Catch:{ all -> 0x0172 }
            java.lang.String r3 = r3.b     // Catch:{ all -> 0x0172 }
            java.io.File r4 = r14.t     // Catch:{ all -> 0x0172 }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ all -> 0x0172 }
            r5 = 0
            r6 = 0
            com.tencent.bugly.beta.download.DownloadTask r2 = r2.a(r3, r4, r5, r6)     // Catch:{ all -> 0x0172 }
            r3 = 1
            r2.delete(r3)     // Catch:{ all -> 0x0172 }
            java.io.File r2 = r14.t     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.beta.global.a.a(r2)     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.beta.global.f r2 = com.tencent.bugly.beta.global.f.a     // Catch:{ all -> 0x0172 }
            r2.a()     // Catch:{ all -> 0x0172 }
            java.lang.String r2 = "upgrade success"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.proguard.an.a(r2, r3)     // Catch:{ all -> 0x0172 }
        L_0x00ec:
            java.lang.String r2 = "[this md5:%s] [strategy md5:%s]"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0172 }
            r4 = 0
            java.lang.String r5 = r14.v     // Catch:{ all -> 0x0172 }
            r3[r4] = r5     // Catch:{ all -> 0x0172 }
            r4 = 1
            com.tencent.bugly.proguard.y r5 = r13.a     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.proguard.v r5 = r5.e     // Catch:{ all -> 0x0172 }
            java.lang.String r5 = r5.i     // Catch:{ all -> 0x0172 }
            r3[r4] = r5     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.proguard.an.a(r2, r3)     // Catch:{ all -> 0x0172 }
        L_0x0103:
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ all -> 0x0172 }
            r2.notifyAll()     // Catch:{ all -> 0x0172 }
            monitor-exit(r15)     // Catch:{ all -> 0x0172 }
            android.content.Context r2 = r14.s     // Catch:{ Exception -> 0x0154 }
            com.tencent.bugly.beta.download.BetaReceiver r3 = new com.tencent.bugly.beta.download.BetaReceiver     // Catch:{ Exception -> 0x0154 }
            r3.<init>()     // Catch:{ Exception -> 0x0154 }
            android.content.IntentFilter r4 = new android.content.IntentFilter     // Catch:{ Exception -> 0x0154 }
            java.lang.String r5 = "android.net.conn.CONNECTIVITY_CHANGE"
            r4.<init>(r5)     // Catch:{ Exception -> 0x0154 }
            r2.registerReceiver(r3, r4)     // Catch:{ Exception -> 0x0154 }
            boolean r2 = r14.d     // Catch:{ Exception -> 0x0154 }
            if (r2 == 0) goto L_0x0124
            r2 = 0
            r3 = 0
            com.tencent.bugly.beta.Beta.checkUpgrade(r2, r3)     // Catch:{ Exception -> 0x0154 }
        L_0x0124:
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0154 }
            com.tencent.bugly.beta.upgrade.BetaUploadStrategy r2 = r2.F     // Catch:{ Exception -> 0x0154 }
            com.tencent.bugly.proguard.bg r2 = r2.a     // Catch:{ Exception -> 0x0154 }
            boolean r2 = r2.b     // Catch:{ Exception -> 0x0154 }
            if (r2 == 0) goto L_0x0149
            com.tencent.bugly.proguard.p r2 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x0154 }
            java.util.List r2 = r2.a()     // Catch:{ Exception -> 0x0154 }
            java.util.ArrayList r2 = (java.util.ArrayList) r2     // Catch:{ Exception -> 0x0154 }
            if (r2 == 0) goto L_0x0149
            boolean r3 = r2.isEmpty()     // Catch:{ Exception -> 0x0154 }
            if (r3 != 0) goto L_0x0149
            com.tencent.bugly.beta.upgrade.b r3 = com.tencent.bugly.beta.upgrade.b.a     // Catch:{ Exception -> 0x0154 }
            com.tencent.bugly.proguard.x r4 = new com.tencent.bugly.proguard.x     // Catch:{ Exception -> 0x0154 }
            r4.<init>(r2)     // Catch:{ Exception -> 0x0154 }
            r2 = 1
            r3.a(r4, r2)     // Catch:{ Exception -> 0x0154 }
        L_0x0149:
            java.lang.String r2 = "Beta async init end..."
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0154 }
            com.tencent.bugly.proguard.an.a(r2, r3)     // Catch:{ Exception -> 0x0154 }
            goto L_0x000b
        L_0x0154:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.b(r2)     // Catch:{ Exception -> 0x0160 }
            if (r3 != 0) goto L_0x000b
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x0160:
            r2 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.b(r2)
            if (r3 != 0) goto L_0x000b
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x000b
        L_0x016c:
            r2 = move-exception
            r2 = 0
            r14.v = r2     // Catch:{ all -> 0x0172 }
            goto L_0x002e
        L_0x0172:
            r2 = move-exception
            monitor-exit(r15)     // Catch:{ all -> 0x0172 }
            throw r2     // Catch:{ Exception -> 0x0154 }
        L_0x0175:
            java.lang.String r2 = "activated from the other way"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.proguard.an.a(r2, r3)     // Catch:{ all -> 0x0172 }
            goto L_0x00b1
        L_0x0180:
            java.lang.String r2 = "delete strategy failed"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0172 }
            com.tencent.bugly.proguard.an.d(r2, r3)     // Catch:{ all -> 0x0172 }
            goto L_0x00ec
        L_0x018b:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.ui.b r2 = (com.tencent.bugly.beta.ui.b) r2     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 1
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ Exception -> 0x0160 }
            boolean r3 = r3.booleanValue()     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.ui.g.a(r2, r3)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x01a6:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            r0 = r2
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = (com.tencent.bugly.beta.upgrade.BetaGrayStrategy) r0     // Catch:{ Exception -> 0x0160 }
            r11 = r0
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 1
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.download.DownloadTask r2 = (com.tencent.bugly.beta.download.DownloadTask) r2     // Catch:{ Exception -> 0x0160 }
            boolean r3 = r11.d     // Catch:{ Exception -> 0x0160 }
            if (r3 != 0) goto L_0x01cd
            r3 = 1
            r11.d = r3     // Catch:{ Exception -> 0x0160 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0160 }
            r11.c = r4     // Catch:{ Exception -> 0x0160 }
            java.lang.String r3 = "st.bch"
            com.tencent.bugly.beta.global.a.a(r3, r11)     // Catch:{ Exception -> 0x0160 }
        L_0x01cd:
            int r3 = r2.getStatus()     // Catch:{ Exception -> 0x0160 }
            if (r3 == r6) goto L_0x000b
            int r2 = r2.getStatus()     // Catch:{ Exception -> 0x0160 }
            r0 = r16
            if (r2 != r0) goto L_0x01fe
            com.tencent.bugly.proguard.p r13 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.w r2 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x0160 }
            java.lang.String r3 = "pop"
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0160 }
            r6 = 4
            r7 = 0
            com.tencent.bugly.proguard.y r9 = r11.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.v r9 = r9.e     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r10 = r11.a     // Catch:{ Exception -> 0x0160 }
            java.lang.String r10 = r10.m     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r11 = r11.a     // Catch:{ Exception -> 0x0160 }
            int r11 = r11.p     // Catch:{ Exception -> 0x0160 }
            r12 = 0
            r2.<init>(r3, r4, r6, r7, r9, r10, r11, r12)     // Catch:{ Exception -> 0x0160 }
            r13.a(r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x01fe:
            com.tencent.bugly.proguard.p r13 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.w r2 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x0160 }
            java.lang.String r3 = "pop"
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0160 }
            r6 = 3
            r7 = 0
            com.tencent.bugly.proguard.y r9 = r11.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.v r9 = r9.e     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r10 = r11.a     // Catch:{ Exception -> 0x0160 }
            java.lang.String r10 = r10.m     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r11 = r11.a     // Catch:{ Exception -> 0x0160 }
            int r11 = r11.p     // Catch:{ Exception -> 0x0160 }
            r12 = 0
            r2.<init>(r3, r4, r6, r7, r9, r10, r11, r12)     // Catch:{ Exception -> 0x0160 }
            r13.a(r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x0221:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            r0 = r2
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = (com.tencent.bugly.beta.upgrade.BetaGrayStrategy) r0     // Catch:{ Exception -> 0x0160 }
            r11 = r0
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 1
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.download.DownloadTask r2 = (com.tencent.bugly.beta.download.DownloadTask) r2     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 2
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ Exception -> 0x0160 }
            boolean r3 = r3.booleanValue()     // Catch:{ Exception -> 0x0160 }
            int r2 = r2.getStatus()     // Catch:{ Exception -> 0x0160 }
            if (r2 == r6) goto L_0x000b
            r2 = 0
            r11.d = r2     // Catch:{ Exception -> 0x0160 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0160 }
            r11.c = r4     // Catch:{ Exception -> 0x0160 }
            if (r3 != 0) goto L_0x025f
            com.tencent.bugly.proguard.y r2 = r11.a     // Catch:{ Exception -> 0x0160 }
            byte r2 = r2.g     // Catch:{ Exception -> 0x0160 }
            if (r2 == r6) goto L_0x025f
            int r2 = r11.b     // Catch:{ Exception -> 0x0160 }
            int r2 = r2 + 1
            r11.b = r2     // Catch:{ Exception -> 0x0160 }
        L_0x025f:
            java.lang.String r2 = "st.bch"
            com.tencent.bugly.beta.global.a.a(r2, r11)     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.p r13 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.w r2 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x0160 }
            java.lang.String r3 = "pop"
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0160 }
            r6 = 2
            r7 = 0
            com.tencent.bugly.proguard.y r9 = r11.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.v r9 = r9.e     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r10 = r11.a     // Catch:{ Exception -> 0x0160 }
            java.lang.String r10 = r10.m     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r11 = r11.a     // Catch:{ Exception -> 0x0160 }
            int r11 = r11.p     // Catch:{ Exception -> 0x0160 }
            r12 = 0
            r2.<init>(r3, r4, r6, r7, r9, r10, r11, r12)     // Catch:{ Exception -> 0x0160 }
            r13.a(r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x0288:
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0160 }
            android.content.Context r3 = r2.s     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 0
            r2 = r2[r4]     // Catch:{ Exception -> 0x0160 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.utils.f.a(r3, r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x029a:
            monitor-enter(r17)     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ all -> 0x02c5 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ all -> 0x02c5 }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ all -> 0x02c5 }
            boolean r2 = r2.booleanValue()     // Catch:{ all -> 0x02c5 }
            if (r2 != 0) goto L_0x02c2
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ all -> 0x02c5 }
            r3 = 0
            r4 = 1
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)     // Catch:{ all -> 0x02c5 }
            r2[r3] = r4     // Catch:{ all -> 0x02c5 }
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ all -> 0x02c5 }
            r3 = 1
            r2 = r2[r3]     // Catch:{ all -> 0x02c5 }
            java.lang.Runnable r2 = (java.lang.Runnable) r2     // Catch:{ all -> 0x02c5 }
            r2.run()     // Catch:{ all -> 0x02c5 }
        L_0x02c2:
            monitor-exit(r17)     // Catch:{ all -> 0x02c5 }
            goto L_0x000b
        L_0x02c5:
            r2 = move-exception
            monitor-exit(r17)     // Catch:{ all -> 0x02c5 }
            throw r2     // Catch:{ Exception -> 0x0160 }
        L_0x02c8:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.ui.h r2 = (com.tencent.bugly.beta.ui.h) r2     // Catch:{ Exception -> 0x0160 }
            r2.c()     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x02d6:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            java.util.List r2 = (java.util.List) r2     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x000b
            java.util.Iterator r4 = r2.iterator()     // Catch:{ Exception -> 0x0160 }
        L_0x02e5:
            boolean r2 = r4.hasNext()     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x000b
            java.lang.Object r2 = r4.next()     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.download.DownloadListener r2 = (com.tencent.bugly.beta.download.DownloadListener) r2     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x02e5
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r5 = 1
            r3 = r3[r5]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.download.DownloadTask r3 = (com.tencent.bugly.beta.download.DownloadTask) r3     // Catch:{ Exception -> 0x0160 }
            r2.onCompleted(r3)     // Catch:{ Exception -> 0x0160 }
            goto L_0x02e5
        L_0x0300:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            java.util.List r2 = (java.util.List) r2     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x032a
            java.util.Iterator r4 = r2.iterator()     // Catch:{ Exception -> 0x0160 }
        L_0x030f:
            boolean r2 = r4.hasNext()     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x032a
            java.lang.Object r2 = r4.next()     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.download.DownloadListener r2 = (com.tencent.bugly.beta.download.DownloadListener) r2     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x030f
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r5 = 1
            r3 = r3[r5]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.download.DownloadTask r3 = (com.tencent.bugly.beta.download.DownloadTask) r3     // Catch:{ Exception -> 0x0160 }
            r2.onReceive(r3)     // Catch:{ Exception -> 0x0160 }
            goto L_0x030f
        L_0x032a:
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.interfaces.BetaPatchListener r2 = r2.W     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x000b
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0160 }
            int r2 = r2.ae     // Catch:{ Exception -> 0x0160 }
            r3 = 3
            if (r2 != r3) goto L_0x000b
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 1
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.download.DownloadTask r2 = (com.tencent.bugly.beta.download.DownloadTask) r2     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.global.e r3 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.interfaces.BetaPatchListener r3 = r3.W     // Catch:{ Exception -> 0x0160 }
            long r4 = r2.getSavedLength()     // Catch:{ Exception -> 0x0160 }
            long r6 = r2.getTotalLength()     // Catch:{ Exception -> 0x0160 }
            r3.onDownloadReceived(r4, r6)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x0351:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            java.util.List r2 = (java.util.List) r2     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x000b
            java.util.Iterator r5 = r2.iterator()     // Catch:{ Exception -> 0x0160 }
        L_0x0360:
            boolean r2 = r5.hasNext()     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x000b
            java.lang.Object r2 = r5.next()     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.download.DownloadListener r2 = (com.tencent.bugly.beta.download.DownloadListener) r2     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x0360
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 1
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.download.DownloadTask r3 = (com.tencent.bugly.beta.download.DownloadTask) r3     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r4 = r0.b     // Catch:{ Exception -> 0x0160 }
            r6 = 2
            r4 = r4[r6]     // Catch:{ Exception -> 0x0160 }
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch:{ Exception -> 0x0160 }
            int r6 = r4.intValue()     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r4 = r0.b     // Catch:{ Exception -> 0x0160 }
            r7 = 3
            r4 = r4[r7]     // Catch:{ Exception -> 0x0160 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x0160 }
            r2.onFailed(r3, r6, r4)     // Catch:{ Exception -> 0x0160 }
            goto L_0x0360
        L_0x0391:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.ui.b r2 = (com.tencent.bugly.beta.ui.b) r2     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 1
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ Exception -> 0x0160 }
            boolean r4 = r3.booleanValue()     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r5 = 2
            r3 = r3[r5]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ Exception -> 0x0160 }
            boolean r5 = r3.booleanValue()     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r6 = 3
            r3 = r3[r6]     // Catch:{ Exception -> 0x0160 }
            java.lang.Long r3 = (java.lang.Long) r3     // Catch:{ Exception -> 0x0160 }
            long r6 = r3.longValue()     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.ui.g.a(r2, r4, r5, r6)     // Catch:{ Exception -> 0x0160 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0160 }
            r2.<init>()     // Catch:{ Exception -> 0x0160 }
            java.lang.String r3 = "BetaAct TYPE_actCanShow checking : "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 0
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            int r3 = r3.hashCode()     // Catch:{ Exception -> 0x0160 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x0160 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.an.a(r2, r3)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x03eb:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ Exception -> 0x0160 }
            boolean r2 = r2.booleanValue()     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 1
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.upgrade.a r3 = (com.tencent.bugly.beta.upgrade.a) r3     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r4 = r0.b     // Catch:{ Exception -> 0x0160 }
            r5 = 1
            r12 = r4[r5]     // Catch:{ Exception -> 0x0160 }
            monitor-enter(r12)     // Catch:{ Exception -> 0x0160 }
            if (r2 != 0) goto L_0x043f
            boolean r2 = r3.d     // Catch:{ all -> 0x0442 }
            if (r2 != 0) goto L_0x043f
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ all -> 0x0442 }
            r4 = 0
            r5 = 1
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x0442 }
            r2[r4] = r5     // Catch:{ all -> 0x0442 }
            int r4 = r3.b     // Catch:{ all -> 0x0442 }
            r5 = 0
            r6 = 0
            r8 = 0
            r10 = 0
            java.lang.String r11 = "request is not finished"
            r3.a(r4, r5, r6, r8, r10, r11)     // Catch:{ all -> 0x0442 }
            java.lang.Object[] r2 = r3.c     // Catch:{ all -> 0x0442 }
            r4 = 1
            r5 = 1
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x0442 }
            r2[r4] = r5     // Catch:{ all -> 0x0442 }
            r2 = 0
            r3.d = r2     // Catch:{ all -> 0x0442 }
            java.lang.String r2 = "request is not finished"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0442 }
            com.tencent.bugly.proguard.an.a(r2, r3)     // Catch:{ all -> 0x0442 }
        L_0x043f:
            monitor-exit(r12)     // Catch:{ all -> 0x0442 }
            goto L_0x000b
        L_0x0442:
            r2 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x0442 }
            throw r2     // Catch:{ Exception -> 0x0160 }
        L_0x0445:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.ui.b r2 = (com.tencent.bugly.beta.ui.b) r2     // Catch:{ Exception -> 0x0160 }
            boolean r2 = r2.b()     // Catch:{ Exception -> 0x0160 }
            if (r2 != 0) goto L_0x0464
            java.lang.String r2 = com.tencent.bugly.beta.ui.g.a()     // Catch:{ Exception -> 0x0160 }
            java.lang.Class<com.tencent.bugly.beta.ui.BetaActivity> r3 = com.tencent.bugly.beta.ui.BetaActivity.class
            java.lang.String r3 = r3.getCanonicalName()     // Catch:{ Exception -> 0x0160 }
            boolean r2 = android.text.TextUtils.equals(r2, r3)     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x046d
        L_0x0464:
            r2 = 3000(0xbb8, double:1.482E-320)
            r0 = r17
            com.tencent.bugly.beta.utils.e.a(r0, r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x046d:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.ui.b r2 = (com.tencent.bugly.beta.ui.b) r2     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 1
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ Exception -> 0x0160 }
            boolean r4 = r3.booleanValue()     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r5 = 2
            r3 = r3[r5]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ Exception -> 0x0160 }
            boolean r5 = r3.booleanValue()     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r6 = 3
            r3 = r3[r6]     // Catch:{ Exception -> 0x0160 }
            java.lang.Long r3 = (java.lang.Long) r3     // Catch:{ Exception -> 0x0160 }
            long r6 = r3.longValue()     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.ui.g.a(r2, r4, r5, r6)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x04a2:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            r0 = r2
            com.tencent.bugly.beta.download.DownloadTask r0 = (com.tencent.bugly.beta.download.DownloadTask) r0     // Catch:{ Exception -> 0x0160 }
            r13 = r0
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 1
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            r0 = r2
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = (com.tencent.bugly.beta.upgrade.BetaGrayStrategy) r0     // Catch:{ Exception -> 0x0160 }
            r14 = r0
            if (r13 == 0) goto L_0x04bc
            if (r14 != 0) goto L_0x04c7
        L_0x04bc:
            java.lang.String r2 = "strategyTask or betaStrategy is null"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.an.a(r2, r3)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x04c7:
            int r2 = r13.getStatus()     // Catch:{ Exception -> 0x0160 }
            switch(r2) {
                case 0: goto L_0x04d0;
                case 1: goto L_0x04d0;
                case 2: goto L_0x0578;
                case 3: goto L_0x04d0;
                case 4: goto L_0x04d0;
                case 5: goto L_0x04d0;
                default: goto L_0x04ce;
            }     // Catch:{ Exception -> 0x0160 }
        L_0x04ce:
            goto L_0x000b
        L_0x04d0:
            boolean r2 = r14.d     // Catch:{ Exception -> 0x0160 }
            if (r2 != 0) goto L_0x04e3
            r2 = 1
            r14.d = r2     // Catch:{ Exception -> 0x0160 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0160 }
            r14.c = r2     // Catch:{ Exception -> 0x0160 }
            java.lang.String r2 = "st.bch"
            com.tencent.bugly.beta.global.a.a(r2, r14)     // Catch:{ Exception -> 0x0160 }
        L_0x04e3:
            int r2 = r13.getStatus()     // Catch:{ Exception -> 0x0160 }
            if (r2 == r6) goto L_0x0512
            int r2 = r13.getStatus()     // Catch:{ Exception -> 0x0160 }
            r0 = r16
            if (r2 != r0) goto L_0x0551
            com.tencent.bugly.proguard.p r15 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.w r2 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x0160 }
            java.lang.String r3 = "pop"
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0160 }
            r6 = 4
            r7 = 0
            com.tencent.bugly.proguard.y r9 = r14.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.v r9 = r9.e     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r10 = r14.a     // Catch:{ Exception -> 0x0160 }
            java.lang.String r10 = r10.m     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r11 = r14.a     // Catch:{ Exception -> 0x0160 }
            int r11 = r11.p     // Catch:{ Exception -> 0x0160 }
            r12 = 0
            r2.<init>(r3, r4, r6, r7, r9, r10, r11, r12)     // Catch:{ Exception -> 0x0160 }
            r15.a(r2)     // Catch:{ Exception -> 0x0160 }
        L_0x0512:
            int r2 = r13.getStatus()     // Catch:{ Exception -> 0x0160 }
            r0 = r16
            if (r2 != r0) goto L_0x0573
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0160 }
            android.content.Context r2 = r2.s     // Catch:{ Exception -> 0x0160 }
            java.io.File r3 = r13.getSaveFile()     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r4 = r14.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.u r4 = r4.f     // Catch:{ Exception -> 0x0160 }
            java.lang.String r4 = r4.a     // Catch:{ Exception -> 0x0160 }
            boolean r2 = com.tencent.bugly.beta.global.a.a(r2, r3, r4)     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x0573
            com.tencent.bugly.proguard.p r13 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.w r2 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x0160 }
            java.lang.String r3 = "install"
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0160 }
            r6 = 0
            r7 = 0
            com.tencent.bugly.proguard.y r9 = r14.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.v r9 = r9.e     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r10 = r14.a     // Catch:{ Exception -> 0x0160 }
            java.lang.String r10 = r10.m     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r11 = r14.a     // Catch:{ Exception -> 0x0160 }
            int r11 = r11.p     // Catch:{ Exception -> 0x0160 }
            r12 = 0
            r2.<init>(r3, r4, r6, r7, r9, r10, r11, r12)     // Catch:{ Exception -> 0x0160 }
            r13.a(r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x0551:
            com.tencent.bugly.proguard.p r15 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.w r2 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x0160 }
            java.lang.String r3 = "pop"
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0160 }
            r6 = 3
            r7 = 0
            com.tencent.bugly.proguard.y r9 = r14.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.v r9 = r9.e     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r10 = r14.a     // Catch:{ Exception -> 0x0160 }
            java.lang.String r10 = r10.m     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r11 = r14.a     // Catch:{ Exception -> 0x0160 }
            int r11 = r11.p     // Catch:{ Exception -> 0x0160 }
            r12 = 0
            r2.<init>(r3, r4, r6, r7, r9, r10, r11, r12)     // Catch:{ Exception -> 0x0160 }
            r15.a(r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x0512
        L_0x0573:
            r13.download()     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x0578:
            r13.stop()     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x057d:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.download.DownloadTask r2 = (com.tencent.bugly.beta.download.DownloadTask) r2     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 1
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            r0 = r3
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r0 = (com.tencent.bugly.beta.upgrade.BetaGrayStrategy) r0     // Catch:{ Exception -> 0x0160 }
            r11 = r0
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 2
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ Exception -> 0x0160 }
            boolean r3 = r3.booleanValue()     // Catch:{ Exception -> 0x0160 }
            if (r2 == 0) goto L_0x05a2
            if (r11 != 0) goto L_0x05ad
        L_0x05a2:
            java.lang.String r2 = "strategyTask or betaStrategy is null"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.an.a(r2, r3)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x05ad:
            int r2 = r2.getStatus()     // Catch:{ Exception -> 0x0160 }
            if (r2 == r6) goto L_0x000b
            r2 = 0
            r11.d = r2     // Catch:{ Exception -> 0x0160 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0160 }
            r11.c = r4     // Catch:{ Exception -> 0x0160 }
            if (r3 != 0) goto L_0x05ca
            com.tencent.bugly.proguard.y r2 = r11.a     // Catch:{ Exception -> 0x0160 }
            byte r2 = r2.g     // Catch:{ Exception -> 0x0160 }
            if (r2 == r6) goto L_0x05ca
            int r2 = r11.b     // Catch:{ Exception -> 0x0160 }
            int r2 = r2 + 1
            r11.b = r2     // Catch:{ Exception -> 0x0160 }
        L_0x05ca:
            java.lang.String r2 = "st.bch"
            com.tencent.bugly.beta.global.a.a(r2, r11)     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.p r13 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.w r2 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x0160 }
            java.lang.String r3 = "pop"
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0160 }
            r6 = 2
            r7 = 0
            com.tencent.bugly.proguard.y r9 = r11.a     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.v r9 = r9.e     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r10 = r11.a     // Catch:{ Exception -> 0x0160 }
            java.lang.String r10 = r10.m     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r11 = r11.a     // Catch:{ Exception -> 0x0160 }
            int r11 = r11.p     // Catch:{ Exception -> 0x0160 }
            r12 = 0
            r2.<init>(r3, r4, r6, r7, r9, r10, r11, r12)     // Catch:{ Exception -> 0x0160 }
            r13.a(r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x05f3:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.upgrade.UpgradeListener r2 = (com.tencent.bugly.beta.upgrade.UpgradeListener) r2     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 1
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ Exception -> 0x0160 }
            int r6 = r3.intValue()     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 2
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.upgrade.BetaGrayStrategy r3 = (com.tencent.bugly.beta.upgrade.BetaGrayStrategy) r3     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r4 = r0.b     // Catch:{ Exception -> 0x0160 }
            r7 = 3
            r4 = r4[r7]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ Exception -> 0x0160 }
            boolean r7 = r4.booleanValue()     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r4 = r0.b     // Catch:{ Exception -> 0x0160 }
            r8 = 4
            r4 = r4[r8]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ Exception -> 0x0160 }
            boolean r8 = r4.booleanValue()     // Catch:{ Exception -> 0x0160 }
            if (r3 != 0) goto L_0x0634
            r3 = r5
        L_0x062f:
            r2.onUpgrade(r6, r3, r7, r8)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x0634:
            com.tencent.bugly.beta.UpgradeInfo r4 = new com.tencent.bugly.beta.UpgradeInfo     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.y r3 = r3.a     // Catch:{ Exception -> 0x0160 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x0160 }
            r3 = r4
            goto L_0x062f
        L_0x063d:
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x06a9 }
            android.content.Context r2 = r2.s     // Catch:{ Exception -> 0x06a9 }
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch:{ Exception -> 0x06a9 }
            if (r2 == 0) goto L_0x0663
            android.content.ComponentName r3 = new android.content.ComponentName     // Catch:{ Exception -> 0x06a9 }
            com.tencent.bugly.beta.global.e r4 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x06a9 }
            android.content.Context r4 = r4.s     // Catch:{ Exception -> 0x06a9 }
            java.lang.Class<com.tencent.bugly.beta.ui.BetaActivity> r5 = com.tencent.bugly.beta.ui.BetaActivity.class
            java.lang.String r5 = r5.getName()     // Catch:{ Exception -> 0x06a9 }
            r3.<init>(r4, r5)     // Catch:{ Exception -> 0x06a9 }
            if (r3 == 0) goto L_0x0663
            int r4 = r2.getComponentEnabledSetting(r3)     // Catch:{ Exception -> 0x06a9 }
            if (r4 != r6) goto L_0x0663
            r4 = 1
            r5 = 1
            r2.setComponentEnabledSetting(r3, r4, r5)     // Catch:{ Exception -> 0x06a9 }
        L_0x0663:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x06a9 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x06a9 }
            java.util.Map r2 = (java.util.Map) r2     // Catch:{ Exception -> 0x06a9 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x06a9 }
            r4 = 1
            r3 = r3[r4]     // Catch:{ Exception -> 0x06a9 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ Exception -> 0x06a9 }
            r0 = r17
            java.lang.Object[] r4 = r0.b     // Catch:{ Exception -> 0x06a9 }
            r5 = 2
            r4 = r4[r5]     // Catch:{ Exception -> 0x06a9 }
            com.tencent.bugly.beta.ui.b r4 = (com.tencent.bugly.beta.ui.b) r4     // Catch:{ Exception -> 0x06a9 }
            r2.put(r3, r4)     // Catch:{ Exception -> 0x06a9 }
            android.content.Intent r3 = new android.content.Intent     // Catch:{ Exception -> 0x06a9 }
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x06a9 }
            android.content.Context r2 = r2.s     // Catch:{ Exception -> 0x06a9 }
            java.lang.Class<com.tencent.bugly.beta.ui.BetaActivity> r4 = com.tencent.bugly.beta.ui.BetaActivity.class
            r3.<init>(r2, r4)     // Catch:{ Exception -> 0x06a9 }
            java.lang.String r4 = "frag"
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x06a9 }
            r5 = 1
            r2 = r2[r5]     // Catch:{ Exception -> 0x06a9 }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ Exception -> 0x06a9 }
            r3.putExtra(r4, r2)     // Catch:{ Exception -> 0x06a9 }
            r2 = 268435456(0x10000000, float:2.5243549E-29)
            r3.setFlags(r2)     // Catch:{ Exception -> 0x06a9 }
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x06a9 }
            android.content.Context r2 = r2.s     // Catch:{ Exception -> 0x06a9 }
            r2.startActivity(r3)     // Catch:{ Exception -> 0x06a9 }
            goto L_0x000b
        L_0x06a9:
            r2 = move-exception
            java.lang.String r3 = "无法正常弹窗，请在AndroidManifest.xml中添加BetaActivity声明：\n<activity\n    android:name=\"com.tencent.bugly.beta.ui.BetaActivity\"\n    android:theme=\"@android:style/Theme.Translucent\" />"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.proguard.an.e(r3, r4)     // Catch:{ Exception -> 0x0160 }
            boolean r3 = com.tencent.bugly.proguard.an.b(r2)     // Catch:{ Exception -> 0x0160 }
            if (r3 != 0) goto L_0x000b
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x06be:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.upgrade.UpgradeStateListener r2 = (com.tencent.bugly.beta.upgrade.UpgradeStateListener) r2     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 1
            r3 = r3[r4]     // Catch:{ Exception -> 0x0160 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ Exception -> 0x0160 }
            int r4 = r3.intValue()     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r3 = r0.b     // Catch:{ Exception -> 0x0160 }
            r5 = 2
            r3 = r3[r5]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ Exception -> 0x0160 }
            boolean r3 = r3.booleanValue()     // Catch:{ Exception -> 0x0160 }
            switch(r4) {
                case -1: goto L_0x06e6;
                case 0: goto L_0x06eb;
                case 1: goto L_0x06f0;
                case 2: goto L_0x06f5;
                case 3: goto L_0x06fa;
                default: goto L_0x06e4;
            }     // Catch:{ Exception -> 0x0160 }
        L_0x06e4:
            goto L_0x000b
        L_0x06e6:
            r2.onUpgradeFailed(r3)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x06eb:
            r2.onUpgradeSuccess(r3)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x06f0:
            r2.onUpgradeNoVersion(r3)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x06f5:
            r2.onUpgrading(r3)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x06fa:
            r2.onDownloadCompleted(r3)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        L_0x06ff:
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ Exception -> 0x0160 }
            boolean r3 = r2.booleanValue()     // Catch:{ Exception -> 0x0160 }
            r0 = r17
            java.lang.Object[] r2 = r0.b     // Catch:{ Exception -> 0x0160 }
            r4 = 1
            r2 = r2[r4]     // Catch:{ Exception -> 0x0160 }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ Exception -> 0x0160 }
            boolean r2 = r2.booleanValue()     // Catch:{ Exception -> 0x0160 }
            com.tencent.bugly.beta.Beta.checkUpgrade(r3, r2)     // Catch:{ Exception -> 0x0160 }
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.global.d.run():void");
    }
}
