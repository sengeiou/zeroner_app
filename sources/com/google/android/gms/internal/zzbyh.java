package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public final class zzbyh extends zzeu implements zzbyf {
    zzbyh(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IStatusCallback");
    }

    public final void zzn(Status status) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) status);
        zzc(1, zzbe);
    }
}
