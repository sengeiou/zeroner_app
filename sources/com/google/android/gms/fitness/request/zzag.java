package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzag extends zzeu implements zzae {
    zzag(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.request.IBleScanCallback");
    }

    public final void onDeviceFound(BleDevice bleDevice) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) bleDevice);
        zzc(1, zzbe);
    }

    public final void onScanStopped() throws RemoteException {
        zzc(2, zzbe());
    }
}
