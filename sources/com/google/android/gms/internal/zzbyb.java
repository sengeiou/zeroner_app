package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.SessionReadResult;

public final class zzbyb extends zzeu implements zzbxz {
    zzbyb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.ISessionReadCallback");
    }

    public final void zza(SessionReadResult sessionReadResult) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) sessionReadResult);
        zzc(1, zzbe);
    }
}
