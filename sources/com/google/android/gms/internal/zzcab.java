package com.google.android.gms.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.fitness.result.SessionStopResult;

final class zzcab extends zzbyd {
    private final zzn<SessionStopResult> zzgbw;

    private zzcab(zzn<SessionStopResult> zzn) {
        this.zzgbw = zzn;
    }

    /* synthetic */ zzcab(zzn zzn, zzbzu zzbzu) {
        this(zzn);
    }

    public final void zza(SessionStopResult sessionStopResult) {
        this.zzgbw.setResult(sessionStopResult);
    }
}
