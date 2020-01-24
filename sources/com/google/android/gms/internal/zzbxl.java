package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.DataTypeCreateRequest;
import com.google.android.gms.fitness.request.zzaa;
import com.google.android.gms.fitness.request.zzs;

public final class zzbxl extends zzeu implements zzbxk {
    zzbxl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitConfigApi");
    }

    public final void zza(DataTypeCreateRequest dataTypeCreateRequest) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) dataTypeCreateRequest);
        zzb(1, zzbe);
    }

    public final void zza(zzaa zzaa) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzaa);
        zzb(22, zzbe);
    }

    public final void zza(zzs zzs) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzs);
        zzb(2, zzbe);
    }
}
