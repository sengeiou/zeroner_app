package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzdd;
import com.google.android.gms.internal.zzcfk;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzh extends zzdd<zzcfk, LocationAvailability> {
    zzh(FusedLocationProviderClient fusedLocationProviderClient) {
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(((zzcfk) zzb).zzavk());
    }
}
