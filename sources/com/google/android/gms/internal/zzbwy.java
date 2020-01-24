package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DataReadResult;

public final class zzbwy extends zzeu implements zzbww {
    zzbwy(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IDataReadCallback");
    }

    public final void zza(DataReadResult dataReadResult) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) dataReadResult);
        zzc(1, zzbe);
    }
}
