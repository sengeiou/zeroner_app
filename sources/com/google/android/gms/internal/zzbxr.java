package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.zzaj;
import com.google.android.gms.fitness.request.zzbj;
import com.google.android.gms.fitness.request.zzbn;

public final class zzbxr extends zzeu implements zzbxq {
    zzbxr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitRecordingApi");
    }

    public final void zza(zzaj zzaj) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzaj);
        zzb(3, zzbe);
    }

    public final void zza(zzbj zzbj) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzbj);
        zzb(1, zzbe);
    }

    public final void zza(zzbn zzbn) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzbn);
        zzb(2, zzbe);
    }
}
