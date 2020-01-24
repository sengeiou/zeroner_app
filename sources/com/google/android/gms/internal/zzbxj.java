package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.request.zzah;
import com.google.android.gms.fitness.request.zzbh;
import com.google.android.gms.fitness.request.zzbl;
import com.google.android.gms.fitness.request.zze;

public final class zzbxj extends zzeu implements zzbxi {
    zzbxj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitBleApi");
    }

    public final void zza(StartBleScanRequest startBleScanRequest) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) startBleScanRequest);
        zzb(1, zzbe);
    }

    public final void zza(zzah zzah) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzah);
        zzb(5, zzbe);
    }

    public final void zza(zzbh zzbh) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzbh);
        zzb(2, zzbe);
    }

    public final void zza(zzbl zzbl) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzbl);
        zzb(4, zzbe);
    }

    public final void zza(zze zze) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zze);
        zzb(3, zzbe);
    }
}
