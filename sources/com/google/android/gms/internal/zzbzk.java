package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zzaj;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

final class zzbzk extends zzbwe<ListSubscriptionsResult> {
    private /* synthetic */ DataType zzhfd;

    zzbzk(zzbzi zzbzi, GoogleApiClient googleApiClient, DataType dataType) {
        this.zzhfd = dataType;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzbxq) ((zzbwb) zzb).zzakn()).zza(new zzaj(this.zzhfd, new zzbzo(this, null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return ListSubscriptionsResult.zzae(status);
    }
}
