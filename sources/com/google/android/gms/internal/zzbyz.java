package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.request.zzk;

final class zzbyz extends zzbwa {
    private /* synthetic */ DataSet zzhew;
    private /* synthetic */ boolean zzhex = false;

    zzbyz(zzbyy zzbyy, GoogleApiClient googleApiClient, DataSet dataSet, boolean z) {
        this.zzhew = dataSet;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxo) ((zzbvv) zzb).zzakn()).zza(new zzk(this.zzhew, new zzcac(this), this.zzhex));
    }
}
