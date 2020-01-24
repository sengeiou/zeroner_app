package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zzbn;

final class zzbzm extends zzbwg {
    private /* synthetic */ DataType zzhfd;

    zzbzm(zzbzi zzbzi, GoogleApiClient googleApiClient, DataType dataType) {
        this.zzhfd = dataType;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxq) ((zzbwb) zzb).zzakn()).zza(new zzbn(this.zzhfd, null, new zzcac(this)));
    }
}
