package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zzg;
import com.google.android.gms.fitness.result.DailyTotalResult;

final class zzbzf extends zzbvy<DailyTotalResult> {
    private /* synthetic */ DataType zzhfd;
    private /* synthetic */ boolean zzhfe;

    zzbzf(zzbyy zzbyy, GoogleApiClient googleApiClient, DataType dataType, boolean z) {
        this.zzhfd = dataType;
        this.zzhfe = z;
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzbvv zzbvv = (zzbvv) zzb;
        ((zzbxo) zzbvv.zzakn()).zza(new zzg(new zzbzg(this), this.zzhfd, this.zzhfe));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return DailyTotalResult.zza(status, this.zzhfd);
    }
}
