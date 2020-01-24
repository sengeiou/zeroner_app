package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;

abstract class zzay implements Runnable {
    private /* synthetic */ zzao zzfrl;

    private zzay(zzao zzao) {
        this.zzfrl = zzao;
    }

    /* synthetic */ zzay(zzao zzao, zzap zzap) {
        this(zzao);
    }

    @WorkerThread
    public void run() {
        this.zzfrl.zzfps.lock();
        try {
            if (!Thread.interrupted()) {
                zzaib();
                this.zzfrl.zzfps.unlock();
            }
        } catch (RuntimeException e) {
            this.zzfrl.zzfqv.zza(e);
        } finally {
            this.zzfrl.zzfps.unlock();
        }
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public abstract void zzaib();
}
