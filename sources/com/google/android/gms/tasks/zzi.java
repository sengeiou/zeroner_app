package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzi<TResult> implements zzk<TResult> {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private final Executor zzkev;
    /* access modifiers changed from: private */
    public OnSuccessListener<? super TResult> zzkuh;

    public zzi(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzkev = executor;
        this.zzkuh = onSuccessListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzkuh = null;
        }
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        if (task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzkuh != null) {
                    this.zzkev.execute(new zzj(this, task));
                }
            }
        }
    }
}
