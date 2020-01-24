package com.google.android.gms.fitness;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.result.DataTypeResult;

final /* synthetic */ class zzf implements zzbo {
    static final zzbo zzgnw = new zzf();

    private zzf() {
    }

    public final Object zzb(Result result) {
        return ((DataTypeResult) result).getDataType();
    }
}
