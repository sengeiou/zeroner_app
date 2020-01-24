package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;

abstract class zzbvy<R extends Result> extends zzm<R, zzbvv> {
    public zzbvy(GoogleApiClient googleApiClient) {
        super(zzbvv.API, googleApiClient);
    }

    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((Result) obj);
    }
}
