package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.fitness.result.GoalsResult;

final class zzbyx extends zzbxg {
    private /* synthetic */ zzbyw zzhev;

    zzbyx(zzbyw zzbyw) {
        this.zzhev = zzbyw;
    }

    public final void zza(GoalsResult goalsResult) throws RemoteException {
        this.zzhev.setResult(goalsResult);
    }
}
