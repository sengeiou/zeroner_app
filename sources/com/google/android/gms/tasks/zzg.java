package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzg<TResult> implements zzk<TResult> {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private final Executor zzkev;
    /* access modifiers changed from: private */
    public OnFailureListener zzkuf;

    public zzg(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzkev = executor;
        this.zzkuf = onFailureListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzkuf = null;
        }
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        if (!task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzkuf != null) {
                    this.zzkev.execute(new zzh(this, task));
                }
            }
        }
    }
}
