package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.zzs;
import com.google.android.gms.fitness.result.DataTypeResult;

final class zzbys extends zzbvn<DataTypeResult> {
    private /* synthetic */ String zzhet;

    zzbys(zzbyq zzbyq, GoogleApiClient googleApiClient, String str) {
        this.zzhet = str;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxk) ((zzbvk) zzb).zzakn()).zza(new zzs(this.zzhet, new zzbyu(this, null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return DataTypeResult.zzad(status);
    }
}
