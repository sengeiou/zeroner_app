package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.GoalsReadRequest;
import com.google.android.gms.fitness.result.GoalsResult;
import java.util.Collections;

final class zzbyw extends zzbvt<GoalsResult> {
    private /* synthetic */ GoalsReadRequest zzheu;

    zzbyw(zzbyv zzbyv, GoogleApiClient googleApiClient, GoalsReadRequest goalsReadRequest) {
        this.zzheu = goalsReadRequest;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxm) ((zzbvq) zzb).zzakn()).zza(new GoalsReadRequest(this.zzheu, (zzbxf) new zzbyx(this)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new GoalsResult(status, Collections.emptyList());
    }
}
