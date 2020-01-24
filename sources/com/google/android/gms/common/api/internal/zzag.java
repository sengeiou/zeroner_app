package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzag implements OnCompleteListener<TResult> {
    private /* synthetic */ TaskCompletionSource zzeos;
    private /* synthetic */ zzae zzfqr;

    zzag(zzae zzae, TaskCompletionSource taskCompletionSource) {
        this.zzfqr = zzae;
        this.zzeos = taskCompletionSource;
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzfqr.zzfqp.remove(this.zzeos);
    }
}
