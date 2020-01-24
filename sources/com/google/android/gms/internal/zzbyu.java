package com.google.android.gms.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.fitness.result.DataTypeResult;

final class zzbyu extends zzbxd {
    private final zzn<DataTypeResult> zzgbw;

    private zzbyu(zzn<DataTypeResult> zzn) {
        this.zzgbw = zzn;
    }

    /* synthetic */ zzbyu(zzn zzn, zzbyr zzbyr) {
        this(zzn);
    }

    public final void zza(DataTypeResult dataTypeResult) {
        this.zzgbw.setResult(dataTypeResult);
    }
}
