package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.zzaz;

final class zzbzu extends zzbws {
    private /* synthetic */ Session zzhfo;

    zzbzu(zzbzt zzbzt, GoogleApiClient googleApiClient, Session session) {
        this.zzhfo = session;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxu) ((zzbwn) zzb).zzakn()).zza(new zzaz(this.zzhfo, new zzcac(this)));
    }
}
