package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;

final class zzbze extends zzbvy<DataReadResult> {
    private /* synthetic */ DataReadRequest zzhfc;

    zzbze(zzbyy zzbyy, GoogleApiClient googleApiClient, DataReadRequest dataReadRequest) {
        this.zzhfc = dataReadRequest;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxo) ((zzbvv) zzb).zzakn()).zza(new DataReadRequest(this.zzhfc, (zzbww) new zzbzh(this, null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return DataReadResult.zza(status, this.zzhfc.getDataTypes(), this.zzhfc.getDataSources());
    }
}
