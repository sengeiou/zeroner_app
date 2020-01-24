package com.google.android.gms.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcq;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.zzan;
import com.google.android.gms.fitness.request.zzao;
import com.google.android.gms.internal.zzbwh;
import com.google.android.gms.internal.zzbxs;
import com.google.android.gms.internal.zzcac;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzo extends zzcq<zzbwh, OnDataPointListener> {
    private /* synthetic */ zzci zzgpb;
    private /* synthetic */ SensorRequest zzgzo;

    zzo(SensorsClient sensorsClient, zzci zzci, zzci zzci2, SensorRequest sensorRequest) {
        this.zzgpb = zzci2;
        this.zzgzo = sensorRequest;
        super(zzci);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zzb(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzbxs) ((zzbwh) zzb).zzakn()).zza(new zzao(this.zzgzo, zzan.zzaqv().zzc(this.zzgpb), null, zzcac.zza(taskCompletionSource)));
    }
}
