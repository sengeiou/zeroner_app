package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzde;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcad implements zzn<Status> {
    private /* synthetic */ TaskCompletionSource zzhft;

    zzcad(TaskCompletionSource taskCompletionSource) {
        this.zzhft = taskCompletionSource;
    }

    public final /* synthetic */ void setResult(Object obj) {
        zzde.zza((Status) obj, null, this.zzhft);
    }

    public final void zzu(Status status) {
        throw new UnsupportedOperationException("This method should never get invoked");
    }
}
