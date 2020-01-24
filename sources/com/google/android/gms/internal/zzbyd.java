package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.SessionStopResult;

public abstract class zzbyd extends zzev implements zzbyc {
    public zzbyd() {
        attachInterface(this, "com.google.android.gms.fitness.internal.ISessionStopCallback");
    }

    public static zzbyc zzaz(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.ISessionStopCallback");
        return queryLocalInterface instanceof zzbyc ? (zzbyc) queryLocalInterface : new zzbye(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zza((SessionStopResult) zzew.zza(parcel, SessionStopResult.CREATOR));
        return true;
    }
}
