package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DataTypeResult;

public abstract class zzbxd extends zzev implements zzbxc {
    public zzbxd() {
        attachInterface(this, "com.google.android.gms.fitness.internal.IDataTypeCallback");
    }

    public static zzbxc zzav(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IDataTypeCallback");
        return queryLocalInterface instanceof zzbxc ? (zzbxc) queryLocalInterface : new zzbxe(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zza((DataTypeResult) zzew.zza(parcel, DataTypeResult.CREATOR));
        return true;
    }
}
