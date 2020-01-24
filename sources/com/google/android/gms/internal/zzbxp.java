package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.android.gms.fitness.request.zzg;
import com.google.android.gms.fitness.request.zzk;
import com.google.android.gms.fitness.request.zzw;

public final class zzbxp extends zzeu implements zzbxo {
    zzbxp(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
    }

    public final void zza(DataDeleteRequest dataDeleteRequest) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) dataDeleteRequest);
        zzb(3, zzbe);
    }

    public final void zza(DataReadRequest dataReadRequest) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) dataReadRequest);
        zzb(1, zzbe);
    }

    public final void zza(DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) dataUpdateListenerRegistrationRequest);
        zzb(10, zzbe);
    }

    public final void zza(DataUpdateRequest dataUpdateRequest) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) dataUpdateRequest);
        zzb(9, zzbe);
    }

    public final void zza(zzg zzg) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzg);
        zzb(7, zzbe);
    }

    public final void zza(zzk zzk) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzk);
        zzb(2, zzbe);
    }

    public final void zza(zzw zzw) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzw);
        zzb(11, zzbe);
    }
}
