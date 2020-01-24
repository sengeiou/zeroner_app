package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcq;
import com.google.android.gms.internal.zzceu;
import com.google.android.gms.internal.zzcfk;
import com.google.android.gms.internal.zzcfo;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzi extends zzcq<zzcfk, LocationCallback> {
    private /* synthetic */ zzci zzhlf;
    private /* synthetic */ zzcfo zziiw;

    zzi(FusedLocationProviderClient fusedLocationProviderClient, zzci zzci, zzcfo zzcfo, zzci zzci2) {
        this.zziiw = zzcfo;
        this.zzhlf = zzci2;
        super(zzci);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zzb(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzcfk) zzb).zza(this.zziiw, this.zzhlf, (zzceu) new zza(taskCompletionSource));
    }
}
