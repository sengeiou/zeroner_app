package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzcac extends zzbyg {
    private final zzn<Status> zzgbw;

    public zzcac(zzn<Status> zzn) {
        this.zzgbw = zzn;
    }

    public static zzcac zza(TaskCompletionSource<Void> taskCompletionSource) {
        return new zzcac(new zzcad(taskCompletionSource));
    }

    public static zzcac zzb(TaskCompletionSource<Boolean> taskCompletionSource) {
        return new zzcac(new zzcae(taskCompletionSource));
    }

    public final void zzn(Status status) {
        this.zzgbw.setResult(status);
    }
}
