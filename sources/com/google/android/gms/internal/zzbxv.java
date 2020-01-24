package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.request.zzax;
import com.google.android.gms.fitness.request.zzaz;
import com.google.android.gms.fitness.request.zzbb;
import com.google.android.gms.fitness.request.zzbd;

public final class zzbxv extends zzeu implements zzbxu {
    zzbxv(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
    }

    public final void zza(SessionInsertRequest sessionInsertRequest) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) sessionInsertRequest);
        zzb(3, zzbe);
    }

    public final void zza(SessionReadRequest sessionReadRequest) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) sessionReadRequest);
        zzb(4, zzbe);
    }

    public final void zza(zzax zzax) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzax);
        zzb(5, zzbe);
    }

    public final void zza(zzaz zzaz) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzaz);
        zzb(1, zzbe);
    }

    public final void zza(zzbb zzbb) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzbb);
        zzb(2, zzbe);
    }

    public final void zza(zzbd zzbd) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzbd);
        zzb(6, zzbe);
    }
}
