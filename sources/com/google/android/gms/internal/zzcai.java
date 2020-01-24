package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.BleDevicesResult;

public final class zzcai extends zzeu implements zzcag {
    zzcai(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.ble.IBleDevicesCallback");
    }

    public final void zza(BleDevicesResult bleDevicesResult) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) bleDevicesResult);
        zzc(1, zzbe);
    }
}
