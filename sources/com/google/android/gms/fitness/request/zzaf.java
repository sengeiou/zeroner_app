package com.google.android.gms.fitness.request;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;

public abstract class zzaf extends zzev implements zzae {
    public zzaf() {
        attachInterface(this, "com.google.android.gms.fitness.request.IBleScanCallback");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                onDeviceFound((BleDevice) zzew.zza(parcel, BleDevice.CREATOR));
                break;
            case 2:
                onScanStopped();
                break;
            default:
                return false;
        }
        return true;
    }
}
