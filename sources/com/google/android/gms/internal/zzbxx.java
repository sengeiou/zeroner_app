package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

public abstract class zzbxx extends zzev implements zzbxw {
    public zzbxx() {
        attachInterface(this, "com.google.android.gms.fitness.internal.IListSubscriptionsCallback");
    }

    public static zzbxw zzax(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IListSubscriptionsCallback");
        return queryLocalInterface instanceof zzbxw ? (zzbxw) queryLocalInterface : new zzbxy(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zza((ListSubscriptionsResult) zzew.zza(parcel, ListSubscriptionsResult.CREATOR));
        return true;
    }
}
