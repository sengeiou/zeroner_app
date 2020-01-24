package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.internal.zzceo;
import com.google.android.gms.internal.zzcev;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzk extends zzcev {
    private /* synthetic */ TaskCompletionSource zzeos;

    zzk(FusedLocationProviderClient fusedLocationProviderClient, TaskCompletionSource taskCompletionSource) {
        this.zzeos = taskCompletionSource;
    }

    public final void zza(zzceo zzceo) throws RemoteException {
        Status status = zzceo.getStatus();
        if (status == null) {
            this.zzeos.trySetException(new ApiException(new Status(8, "Got null status from location service")));
        } else if (status.getStatusCode() == 0) {
            this.zzeos.setResult(Boolean.valueOf(true));
        } else {
            this.zzeos.trySetException(zzb.zzy(status));
        }
    }
}
