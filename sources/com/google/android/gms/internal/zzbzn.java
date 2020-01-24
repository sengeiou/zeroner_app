package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.request.zzbn;

final class zzbzn extends zzbwg {
    private /* synthetic */ DataSource zzhfj;

    zzbzn(zzbzi zzbzi, GoogleApiClient googleApiClient, DataSource dataSource) {
        this.zzhfj = dataSource;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxq) ((zzbwb) zzb).zzakn()).zza(new zzbn(null, this.zzhfj, new zzcac(this)));
    }
}
