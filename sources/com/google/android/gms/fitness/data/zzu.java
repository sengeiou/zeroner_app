package com.google.android.gms.fitness.data;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzu extends zzev implements zzt {
    public zzu() {
        attachInterface(this, "com.google.android.gms.fitness.data.IDataSourceListener");
    }

    public static zzt zzar(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.data.IDataSourceListener");
        return queryLocalInterface instanceof zzt ? (zzt) queryLocalInterface : new zzv(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zzc((DataPoint) zzew.zza(parcel, DataPoint.CREATOR));
        return true;
    }
}
