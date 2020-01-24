package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DataSourcesResult;

public abstract class zzbxa extends zzev implements zzbwz {
    public zzbxa() {
        attachInterface(this, "com.google.android.gms.fitness.internal.IDataSourcesCallback");
    }

    public static zzbwz zzau(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IDataSourcesCallback");
        return queryLocalInterface instanceof zzbwz ? (zzbwz) queryLocalInterface : new zzbxb(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zza((DataSourcesResult) zzew.zza(parcel, DataSourcesResult.CREATOR));
        return true;
    }
}
