package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.zzbb;
import com.google.android.gms.fitness.result.SessionStopResult;
import java.util.Collections;

final class zzbzv extends zzbwq<SessionStopResult> {
    private /* synthetic */ String val$name = null;
    private /* synthetic */ String zzhfp;

    zzbzv(zzbzt zzbzt, GoogleApiClient googleApiClient, String str, String str2) {
        this.zzhfp = str2;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxu) ((zzbwn) zzb).zzakn()).zza(new zzbb(this.val$name, this.zzhfp, new zzcab(this, null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new SessionStopResult(status, Collections.emptyList());
    }
}
