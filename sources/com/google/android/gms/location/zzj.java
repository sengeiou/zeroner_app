package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzdn;
import com.google.android.gms.internal.zzcfk;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzj extends zzdn<zzcfk, LocationCallback> {
    private /* synthetic */ FusedLocationProviderClient zziix;

    zzj(FusedLocationProviderClient fusedLocationProviderClient, zzck zzck) {
        this.zziix = fusedLocationProviderClient;
        super(zzck);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zzc(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        try {
            ((zzcfk) zzb).zzb(zzajo(), this.zziix.zzc(taskCompletionSource));
        } catch (RuntimeException e) {
            taskCompletionSource.trySetException(e);
        }
    }
}
