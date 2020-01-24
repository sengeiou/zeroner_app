package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import java.util.Collections;

final class zzbzq extends zzbwk<DataSourcesResult> {
    private /* synthetic */ DataSourcesRequest zzhfk;

    zzbzq(zzbzp zzbzp, GoogleApiClient googleApiClient, DataSourcesRequest dataSourcesRequest) {
        this.zzhfk = dataSourcesRequest;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxs) ((zzbwh) zzb).zzakn()).zza(new DataSourcesRequest(this.zzhfk, (zzbwz) new zzbvd(this)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new DataSourcesResult(Collections.emptyList(), status);
    }
}
