package com.google.android.gms.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

final class zzbzo extends zzbxx {
    private final zzn<ListSubscriptionsResult> zzgbw;

    private zzbzo(zzn<ListSubscriptionsResult> zzn) {
        this.zzgbw = zzn;
    }

    /* synthetic */ zzbzo(zzn zzn, zzbzj zzbzj) {
        this(zzn);
    }

    public final void zza(ListSubscriptionsResult listSubscriptionsResult) {
        this.zzgbw.setResult(listSubscriptionsResult);
    }
}
