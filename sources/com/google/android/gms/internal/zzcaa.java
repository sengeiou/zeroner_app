package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.fitness.result.SessionReadResult;

final class zzcaa extends zzbya {
    private final zzn<SessionReadResult> zzgbw;

    private zzcaa(zzn<SessionReadResult> zzn) {
        this.zzgbw = zzn;
    }

    /* synthetic */ zzcaa(zzn zzn, zzbzu zzbzu) {
        this(zzn);
    }

    public final void zza(SessionReadResult sessionReadResult) throws RemoteException {
        this.zzgbw.setResult(sessionReadResult);
    }
}
