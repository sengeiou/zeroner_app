package com.google.android.gms.fitness;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.result.DataSourcesResult;

final /* synthetic */ class zzn implements zzbo {
    static final zzbo zzgnw = new zzn();

    private zzn() {
    }

    public final Object zzb(Result result) {
        return ((DataSourcesResult) result).getDataSources();
    }
}
