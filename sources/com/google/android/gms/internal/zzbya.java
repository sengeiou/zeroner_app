package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.SessionReadResult;

public abstract class zzbya extends zzev implements zzbxz {
    public zzbya() {
        attachInterface(this, "com.google.android.gms.fitness.internal.ISessionReadCallback");
    }

    public static zzbxz zzay(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.ISessionReadCallback");
        return queryLocalInterface instanceof zzbxz ? (zzbxz) queryLocalInterface : new zzbyb(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zza((SessionReadResult) zzew.zza(parcel, SessionReadResult.CREATOR));
        return true;
    }
}
