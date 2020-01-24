package com.google.android.gms.fitness;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

final /* synthetic */ class zzm implements zzbo {
    static final zzbo zzgnw = new zzm();

    private zzm() {
    }

    public final Object zzb(Result result) {
        return ((ListSubscriptionsResult) result).getSubscriptions();
    }
}
