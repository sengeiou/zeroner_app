package com.google.android.gms.common.api;

final class zza implements com.google.android.gms.common.api.PendingResult.zza {
    private /* synthetic */ Batch zzfmb;

    zza(Batch batch) {
        this.zzfmb = batch;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzr(com.google.android.gms.common.api.Status r6) {
        /*
            r5 = this;
            com.google.android.gms.common.api.Batch r0 = r5.zzfmb
            java.lang.Object r1 = r0.mLock
            monitor-enter(r1)
            com.google.android.gms.common.api.Batch r0 = r5.zzfmb     // Catch:{ all -> 0x0039 }
            boolean r0 = r0.isCanceled()     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x0011
            monitor-exit(r1)     // Catch:{ all -> 0x0039 }
        L_0x0010:
            return
        L_0x0011:
            boolean r0 = r6.isCanceled()     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x003c
            com.google.android.gms.common.api.Batch r0 = r5.zzfmb     // Catch:{ all -> 0x0039 }
            r2 = 1
            r0.zzflz = true     // Catch:{ all -> 0x0039 }
        L_0x001d:
            com.google.android.gms.common.api.Batch r0 = r5.zzfmb     // Catch:{ all -> 0x0039 }
            r0.zzflx = r0.zzflx - 1     // Catch:{ all -> 0x0039 }
            com.google.android.gms.common.api.Batch r0 = r5.zzfmb     // Catch:{ all -> 0x0039 }
            int r0 = r0.zzflx     // Catch:{ all -> 0x0039 }
            if (r0 != 0) goto L_0x0037
            com.google.android.gms.common.api.Batch r0 = r5.zzfmb     // Catch:{ all -> 0x0039 }
            boolean r0 = r0.zzflz     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x0049
            com.google.android.gms.common.api.Batch r0 = r5.zzfmb     // Catch:{ all -> 0x0039 }
            com.google.android.gms.common.api.zza.super.cancel()     // Catch:{ all -> 0x0039 }
        L_0x0037:
            monitor-exit(r1)     // Catch:{ all -> 0x0039 }
            goto L_0x0010
        L_0x0039:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0039 }
            throw r0
        L_0x003c:
            boolean r0 = r6.isSuccess()     // Catch:{ all -> 0x0039 }
            if (r0 != 0) goto L_0x001d
            com.google.android.gms.common.api.Batch r0 = r5.zzfmb     // Catch:{ all -> 0x0039 }
            r2 = 1
            r0.zzfly = true     // Catch:{ all -> 0x0039 }
            goto L_0x001d
        L_0x0049:
            com.google.android.gms.common.api.Batch r0 = r5.zzfmb     // Catch:{ all -> 0x0039 }
            boolean r0 = r0.zzfly     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x0069
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x0039 }
            r2 = 13
            r0.<init>(r2)     // Catch:{ all -> 0x0039 }
        L_0x0058:
            com.google.android.gms.common.api.Batch r2 = r5.zzfmb     // Catch:{ all -> 0x0039 }
            com.google.android.gms.common.api.BatchResult r3 = new com.google.android.gms.common.api.BatchResult     // Catch:{ all -> 0x0039 }
            com.google.android.gms.common.api.Batch r4 = r5.zzfmb     // Catch:{ all -> 0x0039 }
            com.google.android.gms.common.api.PendingResult[] r4 = r4.zzfma     // Catch:{ all -> 0x0039 }
            r3.<init>(r0, r4)     // Catch:{ all -> 0x0039 }
            r2.setResult(r3)     // Catch:{ all -> 0x0039 }
            goto L_0x0037
        L_0x0069:
            com.google.android.gms.common.api.Status r0 = com.google.android.gms.common.api.Status.zzfni     // Catch:{ all -> 0x0039 }
            goto L_0x0058
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.zza.zzr(com.google.android.gms.common.api.Status):void");
    }
}
