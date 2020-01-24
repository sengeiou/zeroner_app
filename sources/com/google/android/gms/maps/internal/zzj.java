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
import com.google.android.gms.maps.GoogleMapOptions;

public final class zzj extends zzeu implements IMapFragmentDelegate {
    zzj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IMapFragmentDelegate");
    }

    public final IGoogleMapDelegate getMap() throws RemoteException {
        IGoogleMapDelegate zzg;
        Parcel zza = zza(1, zzbe());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzg = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            zzg = queryLocalInterface instanceof IGoogleMapDelegate ? (IGoogleMapDelegate) queryLocalInterface : new zzg(readStrongBinder);
        }
        zza.recycle();
        return zzg;
    }

    public final void getMapAsync(zzap zzap) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (IInterface) zzap);
        zzb(12, zzbe);
    }

    public final boolean isReady() throws RemoteException {
        Parcel zza = zza(11, zzbe());
        boolean zza2 = zzew.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) bundle);
        zzb(3, zzbe);
    }

    public final IObjectWrapper onCreateView(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, Bundle bundle) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (IInterface) iObjectWrapper);
        zzew.zza(zzbe, (IInterface) iObjectWrapper2);
        zzew.zza(zzbe, (Parcelable) bundle);
        Parcel zza = zza(4, zzbe);
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final void onDestroy() throws RemoteException {
        zzb(8, zzbe());
    }

    public final void onDestroyView() throws RemoteException {
        zzb(7, zzbe());
    }

    public final void onEnterAmbient(Bundle bundle) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) bundle);
        zzb(13, zzbe);
    }

    public final void onExitAmbient() throws RemoteException {
        zzb(14, zzbe());
    }

    public final void onInflate(IObjectWrapper iObjectWrapper, GoogleMapOptions googleMapOptions, Bundle bundle) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (IInterface) iObjectWrapper);
        zzew.zza(zzbe, (Parcelable) googleMapOptions);
        zzew.zza(zzbe, (Parcelable) bundle);
        zzb(2, zzbe);
    }

    public final void onLowMemory() throws RemoteException {
        zzb(9, zzbe());
    }

    public final void onPause() throws RemoteException {
        zzb(6, zzbe());
    }

    public final void onResume() throws RemoteException {
        zzb(5, zzbe());
    }

    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) bundle);
        Parcel zza = zza(10, zzbe);
        if (zza.readInt() != 0) {
            bundle.readFromParcel(zza);
        }
        zza.recycle();
    }

    public final void onStart() throws RemoteException {
        zzb(15, zzbe());
    }

    public final void onStop() throws RemoteException {
        zzb(16, zzbe());
    }
}
