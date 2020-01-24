package com.google.android.gms.fitness.request;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.fitness.data.DataPoint;

final class zzam implements zzcl<OnDataPointListener> {
    private /* synthetic */ DataPoint zzhhg;

    zzam(zzal zzal, DataPoint dataPoint) {
        this.zzhhg = dataPoint;
    }

    public final void zzahz() {
    }

    public final /* synthetic */ void zzu(Object obj) {
        ((OnDataPointListener) obj).onDataPoint(this.zzhhg);
    }
}
