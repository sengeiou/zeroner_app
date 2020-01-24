package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.DataTypeCreateRequest;
import com.google.android.gms.fitness.result.DataTypeResult;

final class zzbyr extends zzbvn<DataTypeResult> {
    private /* synthetic */ DataTypeCreateRequest zzhes;

    zzbyr(zzbyq zzbyq, GoogleApiClient googleApiClient, DataTypeCreateRequest dataTypeCreateRequest) {
        this.zzhes = dataTypeCreateRequest;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxk) ((zzbvk) zzb).zzakn()).zza(new DataTypeCreateRequest(this.zzhes, (zzbxc) new zzbyu(this, null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return DataTypeResult.zzad(status);
    }
}
