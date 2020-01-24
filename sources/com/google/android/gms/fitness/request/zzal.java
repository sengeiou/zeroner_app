package com.google.android.gms.fitness.request;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.zzu;

public final class zzal extends zzu {
    private final zzci<OnDataPointListener> zzfus;

    private zzal(zzci<OnDataPointListener> zzci) {
        this.zzfus = (zzci) zzbq.checkNotNull(zzci);
    }

    /* synthetic */ zzal(zzci zzci, zzam zzam) {
        this(zzci);
    }

    public final void release() {
        this.zzfus.clear();
    }

    public final void zzc(DataPoint dataPoint) throws RemoteException {
        this.zzfus.zza(new zzam(this, dataPoint));
    }
}
