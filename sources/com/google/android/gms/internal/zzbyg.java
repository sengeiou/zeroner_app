package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public abstract class zzbyg extends zzev implements zzbyf {
    public zzbyg() {
        attachInterface(this, "com.google.android.gms.fitness.internal.IStatusCallback");
    }

    public static zzbyf zzba(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IStatusCallback");
        return queryLocalInterface instanceof zzbyf ? (zzbyf) queryLocalInterface : new zzbyh(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zzn((Status) zzew.zza(parcel, Status.CREATOR));
        return true;
    }
}
