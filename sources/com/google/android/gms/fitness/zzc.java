package com.google.android.gms.fitness;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcq;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.request.zzd;
import com.google.android.gms.internal.zzbve;
import com.google.android.gms.internal.zzbxi;
import com.google.android.gms.internal.zzcac;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.List;

final class zzc extends zzcq<zzbve, BleScanCallback> {
    private /* synthetic */ zzci zzgpb;
    private /* synthetic */ List zzgyz;
    private /* synthetic */ int zzgza;

    zzc(BleClient bleClient, zzci zzci, zzci zzci2, List list, int i) {
        this.zzgpb = zzci2;
        this.zzgyz = list;
        this.zzgza = i;
        super(zzci);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zzb(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzbxi) ((zzbve) zzb).zzakn()).zza(new StartBleScanRequest(this.zzgyz, zzd.zzaqt().zza(this.zzgpb), this.zzgza, zzcac.zza(taskCompletionSource)));
    }
}
