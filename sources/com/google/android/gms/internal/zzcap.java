package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.service.FitnessSensorServiceRequest;

public abstract class zzcap extends zzev implements zzcao {
    public zzcap() {
        attachInterface(this, "com.google.android.gms.fitness.internal.service.IFitnessSensorService");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zza((zzcak) zzew.zza(parcel, zzcak.CREATOR), zzbxa.zzau(parcel.readStrongBinder()));
                break;
            case 2:
                zza((FitnessSensorServiceRequest) zzew.zza(parcel, FitnessSensorServiceRequest.CREATOR), zzbyg.zzba(parcel.readStrongBinder()));
                break;
            case 3:
                zza((zzcam) zzew.zza(parcel, zzcam.CREATOR), zzbyg.zzba(parcel.readStrongBinder()));
                break;
            default:
                return false;
        }
        return true;
    }
}
