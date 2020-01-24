package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.GoalsResult;

public final class zzbxh extends zzeu implements zzbxf {
    zzbxh(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoalsReadCallback");
    }

    public final void zza(GoalsResult goalsResult) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) goalsResult);
        zzc(1, zzbe);
    }
}
