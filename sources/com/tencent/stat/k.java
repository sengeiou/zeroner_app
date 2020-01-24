package com.tencent.stat;

import com.tencent.stat.a.e;

class k implements Runnable {
    /* access modifiers changed from: private */
    public e a;
    private StatReportStrategy b = null;
    private c c = new l(this);

    public k(e eVar) {
        this.a = eVar;
        this.b = StatConfig.getStatSendStrategy();
    }

    private void a() {
        if (n.b().a() > 0) {
            n.b().a(this.a, (c) null);
            n.b().a(-1);
            return;
        }
        a(true);
    }

    private void a(boolean z) {
        d.b().a(this.a, this.c);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r8 = this;
            boolean r0 = com.tencent.stat.StatConfig.isEnableStatService()     // Catch:{ Throwable -> 0x0041 }
            if (r0 != 0) goto L_0x0007
        L_0x0006:
            return
        L_0x0007:
            com.tencent.stat.a.e r0 = r8.a     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.a.f r0 = r0.a()     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.a.f r1 = com.tencent.stat.a.f.ERROR     // Catch:{ Throwable -> 0x0041 }
            if (r0 == r1) goto L_0x004a
            com.tencent.stat.a.e r0 = r8.a     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r0 = r0.d()     // Catch:{ Throwable -> 0x0041 }
            int r0 = r0.length()     // Catch:{ Throwable -> 0x0041 }
            int r1 = com.tencent.stat.StatConfig.getMaxReportEventLength()     // Catch:{ Throwable -> 0x0041 }
            if (r0 <= r1) goto L_0x004a
            com.tencent.stat.common.StatLogger r0 = com.tencent.stat.StatService.i     // Catch:{ Throwable -> 0x0041 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0041 }
            r1.<init>()     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r2 = "Event length exceed StatConfig.getMaxReportEventLength(): "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0041 }
            int r2 = com.tencent.stat.StatConfig.getMaxReportEventLength()     // Catch:{ Throwable -> 0x0041 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0041 }
            r0.e(r1)     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0006
        L_0x0041:
            r0 = move-exception
            com.tencent.stat.common.StatLogger r1 = com.tencent.stat.StatService.i
            r1.e(r0)
            goto L_0x0006
        L_0x004a:
            int r0 = com.tencent.stat.StatConfig.getMaxSessionStatReportCount()     // Catch:{ Throwable -> 0x0041 }
            if (r0 <= 0) goto L_0x0068
            int r0 = com.tencent.stat.StatConfig.getCurSessionStatReportCount()     // Catch:{ Throwable -> 0x0041 }
            int r1 = com.tencent.stat.StatConfig.getMaxSessionStatReportCount()     // Catch:{ Throwable -> 0x0041 }
            if (r0 < r1) goto L_0x0065
            com.tencent.stat.common.StatLogger r0 = com.tencent.stat.StatService.i     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r1 = "Times for reporting events has reached the limit of StatConfig.getMaxSessionStatReportCount() in current session."
            r0.e(r1)     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0006
        L_0x0065:
            com.tencent.stat.StatConfig.c()     // Catch:{ Throwable -> 0x0041 }
        L_0x0068:
            com.tencent.stat.common.StatLogger r0 = com.tencent.stat.StatService.i     // Catch:{ Throwable -> 0x0041 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0041 }
            r1.<init>()     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r2 = "Lauch stat task in thread:"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0041 }
            java.lang.Thread r2 = java.lang.Thread.currentThread()     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r2 = r2.getName()     // Catch:{ Throwable -> 0x0041 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0041 }
            r0.i(r1)     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.a.e r0 = r8.a     // Catch:{ Throwable -> 0x0041 }
            android.content.Context r0 = r0.c()     // Catch:{ Throwable -> 0x0041 }
            boolean r1 = com.tencent.stat.common.k.h(r0)     // Catch:{ Throwable -> 0x0041 }
            if (r1 != 0) goto L_0x00a3
            com.tencent.stat.n r0 = com.tencent.stat.n.a(r0)     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.a.e r1 = r8.a     // Catch:{ Throwable -> 0x0041 }
            r2 = 0
            r0.a(r1, r2)     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0006
        L_0x00a3:
            boolean r1 = com.tencent.stat.StatConfig.isEnableSmartReporting()     // Catch:{ Throwable -> 0x0041 }
            if (r1 == 0) goto L_0x00b9
            com.tencent.stat.StatReportStrategy r1 = r8.b     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.StatReportStrategy r2 = com.tencent.stat.StatReportStrategy.ONLY_WIFI_NO_CACHE     // Catch:{ Throwable -> 0x0041 }
            if (r1 == r2) goto L_0x00b9
            boolean r1 = com.tencent.stat.common.k.g(r0)     // Catch:{ Throwable -> 0x0041 }
            if (r1 == 0) goto L_0x00b9
            com.tencent.stat.StatReportStrategy r1 = com.tencent.stat.StatReportStrategy.INSTANT     // Catch:{ Throwable -> 0x0041 }
            r8.b = r1     // Catch:{ Throwable -> 0x0041 }
        L_0x00b9:
            int[] r1 = com.tencent.stat.h.a     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.StatReportStrategy r2 = r8.b     // Catch:{ Throwable -> 0x0041 }
            int r2 = r2.ordinal()     // Catch:{ Throwable -> 0x0041 }
            r1 = r1[r2]     // Catch:{ Throwable -> 0x0041 }
            switch(r1) {
                case 1: goto L_0x00e7;
                case 2: goto L_0x00ec;
                case 3: goto L_0x0103;
                case 4: goto L_0x0103;
                case 5: goto L_0x010f;
                case 6: goto L_0x012b;
                case 7: goto L_0x018b;
                default: goto L_0x00c6;
            }     // Catch:{ Throwable -> 0x0041 }
        L_0x00c6:
            com.tencent.stat.common.StatLogger r0 = com.tencent.stat.StatService.i     // Catch:{ Throwable -> 0x0041 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0041 }
            r1.<init>()     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r2 = "Invalid stat strategy:"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.StatReportStrategy r2 = com.tencent.stat.StatConfig.getStatSendStrategy()     // Catch:{ Throwable -> 0x0041 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0041 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0041 }
            r0.error(r1)     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0006
        L_0x00e7:
            r8.a()     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0006
        L_0x00ec:
            boolean r1 = com.tencent.stat.common.k.e(r0)     // Catch:{ Throwable -> 0x0041 }
            if (r1 == 0) goto L_0x00f7
            r8.a()     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0006
        L_0x00f7:
            com.tencent.stat.n r0 = com.tencent.stat.n.a(r0)     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.a.e r1 = r8.a     // Catch:{ Throwable -> 0x0041 }
            r2 = 0
            r0.a(r1, r2)     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0006
        L_0x0103:
            com.tencent.stat.n r0 = com.tencent.stat.n.a(r0)     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.a.e r1 = r8.a     // Catch:{ Throwable -> 0x0041 }
            r2 = 0
            r0.a(r1, r2)     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0006
        L_0x010f:
            com.tencent.stat.a.e r1 = r8.a     // Catch:{ Throwable -> 0x0041 }
            android.content.Context r1 = r1.c()     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.n r1 = com.tencent.stat.n.a(r1)     // Catch:{ Throwable -> 0x0041 }
            if (r1 == 0) goto L_0x0006
            com.tencent.stat.n r0 = com.tencent.stat.n.a(r0)     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.a.e r1 = r8.a     // Catch:{ Throwable -> 0x0041 }
            com.tencent.stat.m r2 = new com.tencent.stat.m     // Catch:{ Throwable -> 0x0041 }
            r2.<init>(r8)     // Catch:{ Throwable -> 0x0041 }
            r0.a(r1, r2)     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0006
        L_0x012b:
            com.tencent.stat.n r1 = com.tencent.stat.n.a(r0)     // Catch:{ Exception -> 0x0181 }
            com.tencent.stat.a.e r2 = r8.a     // Catch:{ Exception -> 0x0181 }
            r3 = 0
            r1.a(r2, r3)     // Catch:{ Exception -> 0x0181 }
            java.lang.String r1 = "last_period_ts"
            r2 = 0
            long r2 = com.tencent.stat.common.p.a(r0, r1, r2)     // Catch:{ Exception -> 0x0181 }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ Exception -> 0x0181 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0181 }
            java.lang.Long r3 = java.lang.Long.valueOf(r4)     // Catch:{ Exception -> 0x0181 }
            long r4 = r3.longValue()     // Catch:{ Exception -> 0x0181 }
            long r6 = r2.longValue()     // Catch:{ Exception -> 0x0181 }
            long r4 = r4 - r6
            java.lang.Long r2 = java.lang.Long.valueOf(r4)     // Catch:{ Exception -> 0x0181 }
            long r4 = r2.longValue()     // Catch:{ Exception -> 0x0181 }
            r6 = 60000(0xea60, double:2.9644E-319)
            long r4 = r4 / r6
            java.lang.Long r2 = java.lang.Long.valueOf(r4)     // Catch:{ Exception -> 0x0181 }
            long r4 = r2.longValue()     // Catch:{ Exception -> 0x0181 }
            int r2 = com.tencent.stat.StatConfig.getSendPeriodMinutes()     // Catch:{ Exception -> 0x0181 }
            long r6 = (long) r2     // Catch:{ Exception -> 0x0181 }
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x0006
            com.tencent.stat.n r2 = com.tencent.stat.n.a(r0)     // Catch:{ Exception -> 0x0181 }
            r4 = -1
            r2.a(r4)     // Catch:{ Exception -> 0x0181 }
            long r2 = r3.longValue()     // Catch:{ Exception -> 0x0181 }
            com.tencent.stat.common.p.b(r0, r1, r2)     // Catch:{ Exception -> 0x0181 }
            goto L_0x0006
        L_0x0181:
            r0 = move-exception
            com.tencent.stat.common.StatLogger r1 = com.tencent.stat.StatService.i     // Catch:{ Throwable -> 0x0041 }
            r1.e(r0)     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0006
        L_0x018b:
            boolean r0 = com.tencent.stat.common.k.e(r0)     // Catch:{ Throwable -> 0x0041 }
            if (r0 == 0) goto L_0x0006
            r0 = 0
            r8.a(r0)     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.k.run():void");
    }
}
