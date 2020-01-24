package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DataTypeResult;

public final class zzbxe extends zzeu implements zzbxc {
    zzbxe(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IDataTypeCallback");
    }

    public final void zza(DataTypeResult dataTypeResult) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) dataTypeResult);
        zzc(1, zzbe);
    }
}
