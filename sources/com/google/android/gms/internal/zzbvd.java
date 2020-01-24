package com.google.android.gms.internal;

import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.fitness.result.DataSourcesResult;

public final class zzbvd extends zzbxa {
    private final zzn<DataSourcesResult> zzhem;

    public zzbvd(zzn<DataSourcesResult> zzn) {
        this.zzhem = zzn;
    }

    public final void zza(DataSourcesResult dataSourcesResult) {
        this.zzhem.setResult(dataSourcesResult);
    }
}
