package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzde;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcae implements zzn<Status> {
    private /* synthetic */ TaskCompletionSource zzhft;

    zzcae(TaskCompletionSource taskCompletionSource) {
        this.zzhft = taskCompletionSource;
    }

    public final /* synthetic */ void setResult(Object obj) {
        Status status = (Status) obj;
        zzde.zza(status, Boolean.valueOf(status.isSuccess()), this.zzhft);
    }

    public final void zzu(Status status) {
        throw new UnsupportedOperationException("This method should never get invoked");
    }
}
