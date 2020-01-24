package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;

abstract class zzbwa extends zzbvy<Status> {
    zzbwa(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        zzbq.checkArgument(!status.isSuccess());
        return status;
    }
}
