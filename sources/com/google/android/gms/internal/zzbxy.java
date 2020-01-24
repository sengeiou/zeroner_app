package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

public final class zzbxy extends zzeu implements zzbxw {
    zzbxy(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IListSubscriptionsCallback");
    }

    public final void zza(ListSubscriptionsResult listSubscriptionsResult) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) listSubscriptionsResult);
        zzc(1, zzbe);
    }
}
