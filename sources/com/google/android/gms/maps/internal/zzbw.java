package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzbw extends zzeu implements IStreetViewPanoramaViewDelegate {
    zzbw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
    }

    public final IStreetViewPanoramaDelegate getStreetViewPanorama() throws RemoteException {
        IStreetViewPanoramaDelegate zzbu;
        Parcel zza = zza(1, zzbe());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzbu = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
            zzbu = queryLocalInterface instanceof IStreetViewPanoramaDelegate ? (IStreetViewPanoramaDelegate) queryLocalInterface : new zzbu(readStrongBinder);
        }
        zza.recycle();
        return zzbu;
    }

    public final void getStreetViewPanoramaAsync(zzbp zzbp) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (IInterface) zzbp);
        zzb(9, zzbe);
    }

    public final IObjectWrapper getView() throws RemoteException {
        Parcel zza = zza(8, zzbe());
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) bundle);
        zzb(2, zzbe);
    }

    public final void onDestroy() throws RemoteException {
        zzb(5, zzbe());
    }

    public final void onLowMemory() throws RemoteException {
        zzb(6, zzbe());
    }

    public final void onPause() throws RemoteException {
        zzb(4, zzbe());
    }

    public final void onResume() throws RemoteException {
        zzb(3, zzbe());
    }

    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) bundle);
        Parcel zza = zza(7, zzbe);
        if (zza.readInt() != 0) {
            bundle.readFromParcel(zza);
        }
        zza.recycle();
    }
}
