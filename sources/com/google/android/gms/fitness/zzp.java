package com.google.android.gms.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzdn;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.zzal;
import com.google.android.gms.fitness.request.zzan;
import com.google.android.gms.fitness.request.zzar;
import com.google.android.gms.internal.zzbwh;
import com.google.android.gms.internal.zzbxs;
import com.google.android.gms.internal.zzcac;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzp extends zzdn<zzbwh, OnDataPointListener> {
    private /* synthetic */ zzci zzgpb;

    zzp(SensorsClient sensorsClient, zzck zzck, zzci zzci) {
        this.zzgpb = zzci;
        super(zzck);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zzc(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzbwh zzbwh = (zzbwh) zzb;
        zzal zzd = zzan.zzaqv().zzd(this.zzgpb);
        if (zzd == null) {
            taskCompletionSource.setResult(Boolean.valueOf(false));
            return;
        }
        ((zzbxs) zzbwh.zzakn()).zza(new zzar(zzd, null, zzcac.zzb(taskCompletionSource)));
    }
}
