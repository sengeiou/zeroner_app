package com.google.android.gms.fitness;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.result.SessionStopResult;

final /* synthetic */ class zzq implements zzbo {
    static final zzbo zzgnw = new zzq();

    private zzq() {
    }

    public final Object zzb(Result result) {
        return ((SessionStopResult) result).getSessions();
    }
}
