package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.model.internal.zza;
import com.google.android.gms.maps.model.internal.zzb;

public final class zzf extends zzeu implements zze {
    zzf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.ICreator");
    }

    public final IMapViewDelegate zza(IObjectWrapper iObjectWrapper, GoogleMapOptions googleMapOptions) throws RemoteException {
        IMapViewDelegate zzk;
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (IInterface) iObjectWrapper);
        zzew.zza(zzbe, (Parcelable) googleMapOptions);
        Parcel zza = zza(3, zzbe);
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzk = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
            zzk = queryLocalInterface instanceof IMapViewDelegate ? (IMapViewDelegate) queryLocalInterface : new zzk(readStrongBinder);
        }
        zza.recycle();
        return zzk;
    }

    public final IStreetViewPanoramaViewDelegate zza(IObjectWrapper iObjectWrapper, StreetViewPanoramaOptions streetViewPanoramaOptions) throws RemoteException {
        IStreetViewPanoramaViewDelegate zzbw;
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (IInterface) iObjectWrapper);
        zzew.zza(zzbe, (Parcelable) streetViewPanoramaOptions);
        Parcel zza = zza(7, zzbe);
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzbw = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate");
            zzbw = queryLocalInterface instanceof IStreetViewPanoramaViewDelegate ? (IStreetViewPanoramaViewDelegate) queryLocalInterface : new zzbw(readStrongBinder);
        }
        zza.recycle();
        return zzbw;
    }

    public final IMapFragmentDelegate zzaa(IObjectWrapper iObjectWrapper) throws RemoteException {
        IMapFragmentDelegate zzj;
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (IInterface) iObjectWrapper);
        Parcel zza = zza(2, zzbe);
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzj = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
            zzj = queryLocalInterface instanceof IMapFragmentDelegate ? (IMapFragmentDelegate) queryLocalInterface : new zzj(readStrongBinder);
        }
        zza.recycle();
        return zzj;
    }

    public final IStreetViewPanoramaFragmentDelegate zzab(IObjectWrapper iObjectWrapper) throws RemoteException {
        IStreetViewPanoramaFragmentDelegate zzbv;
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (IInterface) iObjectWrapper);
        Parcel zza = zza(8, zzbe);
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzbv = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate");
            zzbv = queryLocalInterface instanceof IStreetViewPanoramaFragmentDelegate ? (IStreetViewPanoramaFragmentDelegate) queryLocalInterface : new zzbv(readStrongBinder);
        }
        zza.recycle();
        return zzbv;
    }

    public final ICameraUpdateFactoryDelegate zzawc() throws RemoteException {
        ICameraUpdateFactoryDelegate zzb;
        Parcel zza = zza(4, zzbe());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzb = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
            zzb = queryLocalInterface instanceof ICameraUpdateFactoryDelegate ? (ICameraUpdateFactoryDelegate) queryLocalInterface : new zzb(readStrongBinder);
        }
        zza.recycle();
        return zzb;
    }

    public final zza zzawd() throws RemoteException {
        Parcel zza = zza(5, zzbe());
        zza zzbf = zzb.zzbf(zza.readStrongBinder());
        zza.recycle();
        return zzbf;
    }

    public final void zzi(IObjectWrapper iObjectWrapper, int i) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (IInterface) iObjectWrapper);
        zzbe.writeInt(i);
        zzb(6, zzbe);
    }
}
