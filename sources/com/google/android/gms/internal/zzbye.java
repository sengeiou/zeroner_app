package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.SessionStopResult;

public final class zzbye extends zzeu implements zzbyc {
    zzbye(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.ISessionStopCallback");
    }

    public final void zza(SessionStopResult sessionStopResult) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) sessionStopResult);
        zzc(1, zzbe);
    }
}
