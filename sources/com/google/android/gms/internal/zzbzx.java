package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.SessionReadResult;

final class zzbzx extends zzbwq<SessionReadResult> {
    private /* synthetic */ SessionReadRequest zzhfr;

    zzbzx(zzbzt zzbzt, GoogleApiClient googleApiClient, SessionReadRequest sessionReadRequest) {
        this.zzhfr = sessionReadRequest;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxu) ((zzbwn) zzb).zzakn()).zza(new SessionReadRequest(this.zzhfr, (zzbxz) new zzcaa(this, null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return SessionReadResult.zzaf(status);
    }
}
