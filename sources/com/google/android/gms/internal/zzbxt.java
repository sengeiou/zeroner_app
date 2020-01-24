package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.zzao;
import com.google.android.gms.fitness.request.zzar;

public final class zzbxt extends zzeu implements zzbxs {
    zzbxt(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitSensorsApi");
    }

    public final void zza(DataSourcesRequest dataSourcesRequest) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) dataSourcesRequest);
        zzb(1, zzbe);
    }

    public final void zza(zzao zzao) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzao);
        zzb(2, zzbe);
    }

    public final void zza(zzar zzar) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzar);
        zzb(3, zzbe);
    }
}
