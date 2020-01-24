package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.SessionInsertRequest;

final class zzbzw extends zzbws {
    private /* synthetic */ SessionInsertRequest zzhfq;

    zzbzw(zzbzt zzbzt, GoogleApiClient googleApiClient, SessionInsertRequest sessionInsertRequest) {
        this.zzhfq = sessionInsertRequest;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxu) ((zzbwn) zzb).zzakn()).zza(new SessionInsertRequest(this.zzhfq, (zzbyf) new zzcac(this)));
    }
}
