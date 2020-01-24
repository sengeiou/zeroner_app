package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DailyTotalResult;

public final class zzbwv extends zzeu implements zzbwt {
    zzbwv(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IDailyTotalCallback");
    }

    public final void zza(DailyTotalResult dailyTotalResult) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) dailyTotalResult);
        zzc(1, zzbe);
    }
}
