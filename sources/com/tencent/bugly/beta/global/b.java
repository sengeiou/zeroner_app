package com.tencent.bugly.beta.global;

import android.view.View.OnClickListener;

/* compiled from: BUGLY */
public class b implements OnClickListener {
    final int a;
    final Object[] b;

    public b(int i, Object... objArr) {
        this.a = i;
        this.b = objArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:? A[Catch:{ Exception -> 0x0027 }, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x002e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r16) {
        /*
            r15 = this;
            int r1 = r15.a     // Catch:{ Exception -> 0x0027 }
            switch(r1) {
                case 1: goto L_0x0006;
                case 2: goto L_0x0032;
                case 3: goto L_0x003d;
                case 4: goto L_0x0059;
                case 5: goto L_0x00d1;
                case 6: goto L_0x00eb;
                case 7: goto L_0x0100;
                case 8: goto L_0x0114;
                default: goto L_0x0005;
            }     // Catch:{ Exception -> 0x0027 }
        L_0x0005:
            return
        L_0x0006:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x0027 }
            r2 = 1
            r1 = r1[r2]     // Catch:{ Exception -> 0x0027 }
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1     // Catch:{ Exception -> 0x0027 }
            int r1 = r1.getChildCount()     // Catch:{ Exception -> 0x0027 }
            if (r1 > 0) goto L_0x0005
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x0027 }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x0027 }
            android.app.Activity r1 = (android.app.Activity) r1     // Catch:{ Exception -> 0x0027 }
            r1.finish()     // Catch:{ Exception -> 0x0027 }
            java.lang.String r1 = "BetaAct closed by empty view"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.proguard.an.a(r1, r2)     // Catch:{ Exception -> 0x0027 }
            goto L_0x0005
        L_0x0027:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.b(r1)
            if (r2 != 0) goto L_0x0005
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0005
        L_0x0032:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x0027 }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.beta.ui.a r1 = (com.tencent.bugly.beta.ui.a) r1     // Catch:{ Exception -> 0x0027 }
            r1.a()     // Catch:{ Exception -> 0x0027 }
            goto L_0x0005
        L_0x003d:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x0027 }
            r2 = 1
            r1 = r1[r2]     // Catch:{ Exception -> 0x0027 }
            if (r1 == 0) goto L_0x004e
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x0027 }
            r2 = 1
            r1 = r1[r2]     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.beta.download.DownloadTask r1 = (com.tencent.bugly.beta.download.DownloadTask) r1     // Catch:{ Exception -> 0x0027 }
            r1.download()     // Catch:{ Exception -> 0x0027 }
        L_0x004e:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x0027 }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.beta.ui.a r1 = (com.tencent.bugly.beta.ui.a) r1     // Catch:{ Exception -> 0x0027 }
            r1.a()     // Catch:{ Exception -> 0x0027 }
            goto L_0x0005
        L_0x0059:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x0027 }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x0027 }
            r0 = r1
            com.tencent.bugly.beta.ui.h r0 = (com.tencent.bugly.beta.ui.h) r0     // Catch:{ Exception -> 0x0027 }
            r12 = r0
            com.tencent.bugly.beta.download.DownloadTask r1 = r12.q     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.proguard.y r13 = r12.p     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.beta.download.BetaReceiver.addTask(r1)     // Catch:{ Exception -> 0x0027 }
            java.lang.Runnable r2 = r12.r     // Catch:{ Exception -> 0x0027 }
            if (r2 == 0) goto L_0x0072
            java.lang.Runnable r2 = r12.r     // Catch:{ Exception -> 0x0027 }
            r2.run()     // Catch:{ Exception -> 0x0027 }
        L_0x0072:
            int r2 = r1.getStatus()     // Catch:{ Exception -> 0x0027 }
            r3 = 1
            if (r2 != r3) goto L_0x00b0
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0027 }
            android.content.Context r2 = r2.s     // Catch:{ Exception -> 0x0027 }
            java.io.File r3 = r1.getSaveFile()     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.proguard.u r4 = r13.f     // Catch:{ Exception -> 0x0027 }
            java.lang.String r4 = r4.a     // Catch:{ Exception -> 0x0027 }
            boolean r2 = com.tencent.bugly.beta.global.a.a(r2, r3, r4)     // Catch:{ Exception -> 0x0027 }
            if (r2 == 0) goto L_0x00b0
            com.tencent.bugly.proguard.p r14 = com.tencent.bugly.proguard.p.a     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.proguard.w r1 = new com.tencent.bugly.proguard.w     // Catch:{ Exception -> 0x0027 }
            java.lang.String r2 = "install"
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0027 }
            r5 = 0
            r6 = 0
            com.tencent.bugly.proguard.v r8 = r13.e     // Catch:{ Exception -> 0x0027 }
            java.lang.String r9 = r13.m     // Catch:{ Exception -> 0x0027 }
            int r10 = r13.p     // Catch:{ Exception -> 0x0027 }
            r11 = 0
            r1.<init>(r2, r3, r5, r6, r8, r9, r10, r11)     // Catch:{ Exception -> 0x0027 }
            r14.a(r1)     // Catch:{ Exception -> 0x0027 }
        L_0x00a6:
            byte r1 = r13.g     // Catch:{ Exception -> 0x0027 }
            r2 = 2
            if (r1 == r2) goto L_0x0005
            r12.a()     // Catch:{ Exception -> 0x0027 }
            goto L_0x0005
        L_0x00b0:
            com.tencent.bugly.beta.global.e r2 = com.tencent.bugly.beta.global.e.E     // Catch:{ Exception -> 0x0027 }
            android.content.Context r2 = r2.s     // Catch:{ Exception -> 0x0027 }
            int r2 = com.tencent.bugly.beta.global.a.a(r2)     // Catch:{ Exception -> 0x0027 }
            r3 = 4
            if (r2 == r3) goto L_0x00c1
            r3 = 3
            if (r2 == r3) goto L_0x00c1
            r3 = 2
            if (r2 != r3) goto L_0x00cd
        L_0x00c1:
            com.tencent.bugly.beta.ui.f r2 = new com.tencent.bugly.beta.ui.f     // Catch:{ Exception -> 0x0027 }
            r2.<init>()     // Catch:{ Exception -> 0x0027 }
            r2.n = r1     // Catch:{ Exception -> 0x0027 }
            r1 = 1
            com.tencent.bugly.beta.ui.g.a(r2, r1)     // Catch:{ Exception -> 0x0027 }
            goto L_0x00a6
        L_0x00cd:
            r1.download()     // Catch:{ Exception -> 0x0027 }
            goto L_0x00a6
        L_0x00d1:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x0027 }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.beta.ui.h r1 = (com.tencent.bugly.beta.ui.h) r1     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.beta.download.DownloadTask r2 = r1.q     // Catch:{ Exception -> 0x0027 }
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.tencent.bugly.beta.download.c> r3 = com.tencent.bugly.beta.download.BetaReceiver.netListeners     // Catch:{ Exception -> 0x0027 }
            java.lang.String r4 = r2.getDownloadUrl()     // Catch:{ Exception -> 0x0027 }
            r3.remove(r4)     // Catch:{ Exception -> 0x0027 }
            r2.stop()     // Catch:{ Exception -> 0x0027 }
            r1.a(r2)     // Catch:{ Exception -> 0x0027 }
            goto L_0x0005
        L_0x00eb:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x0027 }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.beta.ui.h r1 = (com.tencent.bugly.beta.ui.h) r1     // Catch:{ Exception -> 0x0027 }
            java.lang.Runnable r2 = r1.s     // Catch:{ Exception -> 0x0027 }
            if (r2 == 0) goto L_0x00fb
            java.lang.Runnable r2 = r1.s     // Catch:{ Exception -> 0x0027 }
            r2.run()     // Catch:{ Exception -> 0x0027 }
        L_0x00fb:
            r1.a()     // Catch:{ Exception -> 0x0027 }
            goto L_0x0005
        L_0x0100:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x0027 }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.beta.ui.e r1 = (com.tencent.bugly.beta.ui.e) r1     // Catch:{ Exception -> 0x0027 }
            int r1 = android.os.Process.myPid()     // Catch:{ Exception -> 0x0027 }
            android.os.Process.killProcess(r1)     // Catch:{ Exception -> 0x0027 }
            r1 = 0
            java.lang.System.exit(r1)     // Catch:{ Exception -> 0x0027 }
            goto L_0x0005
        L_0x0114:
            java.lang.Object[] r1 = r15.b     // Catch:{ Exception -> 0x0027 }
            r2 = 0
            r1 = r1[r2]     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.beta.ui.e r1 = (com.tencent.bugly.beta.ui.e) r1     // Catch:{ Exception -> 0x0027 }
            r1.a()     // Catch:{ Exception -> 0x0027 }
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.beta.global.b.onClick(android.view.View):void");
    }
}
