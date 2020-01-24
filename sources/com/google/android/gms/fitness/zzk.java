package com.google.android.gms.fitness;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.result.DailyTotalResult;

final /* synthetic */ class zzk implements zzbo {
    static final zzbo zzgnw = new zzk();

    private zzk() {
    }

    public final Object zzb(Result result) {
        return ((DailyTotalResult) result).getTotal();
    }
}
