package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.fitness.result.DailyTotalResult;

final class zzbzg extends zzbwu {
    private /* synthetic */ zzbzf zzhff;

    zzbzg(zzbzf zzbzf) {
        this.zzhff = zzbzf;
    }

    public final void zza(DailyTotalResult dailyTotalResult) throws RemoteException {
        this.zzhff.setResult(dailyTotalResult);
    }
}
