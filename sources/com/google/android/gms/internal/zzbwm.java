package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;

abstract class zzbwm extends zzbwk<Status> {
    zzbwm(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    /* renamed from: zzab */
    public Status zzb(Status status) {
        zzbq.checkArgument(!status.isSuccess());
        return status;
    }
}
