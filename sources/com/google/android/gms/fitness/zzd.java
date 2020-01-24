package com.google.android.gms.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzdn;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.zza;
import com.google.android.gms.fitness.request.zzae;
import com.google.android.gms.fitness.request.zzbh;
import com.google.android.gms.internal.zzbve;
import com.google.android.gms.internal.zzbxi;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzcac;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzd extends zzdn<zzbve, BleScanCallback> {
    private /* synthetic */ zzci zzgpb;

    zzd(BleClient bleClient, zzck zzck, zzci zzci) {
        this.zzgpb = zzci;
        super(zzck);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zzc(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzbve zzbve = (zzbve) zzb;
        zza zzb2 = com.google.android.gms.fitness.request.zzd.zzaqt().zzb(this.zzgpb);
        if (zzb2 == null) {
            taskCompletionSource.setResult(Boolean.valueOf(false));
            return;
        }
        ((zzbxi) zzbve.zzakn()).zza(new zzbh((zzae) zzb2, (zzbyf) zzcac.zzb(taskCompletionSource)));
    }
}
