package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;

final class zzcen extends zzcev {
    private final zzn<Status> zzgbw;

    public zzcen(zzn<Status> zzn) {
        this.zzgbw = zzn;
    }

    public final void zza(zzceo zzceo) {
        this.zzgbw.setResult(zzceo.getStatus());
    }
}
