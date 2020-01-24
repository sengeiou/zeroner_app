package com.google.android.gms.fitness;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

final /* synthetic */ class zzl implements zzbo {
    static final zzbo zzgnw = new zzl();

    private zzl() {
    }

    public final Object zzb(Result result) {
        return ((ListSubscriptionsResult) result).getSubscriptions();
    }
}
