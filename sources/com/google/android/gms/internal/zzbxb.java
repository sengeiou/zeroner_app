package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DataSourcesResult;

public final class zzbxb extends zzeu implements zzbwz {
    zzbxb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IDataSourcesCallback");
    }

    public final void zza(DataSourcesResult dataSourcesResult) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) dataSourcesResult);
        zzc(1, zzbe);
    }
}
