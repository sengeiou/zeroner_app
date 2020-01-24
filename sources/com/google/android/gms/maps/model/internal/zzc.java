package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzc extends zzeu implements zza {
    zzc(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
    }

    public final IObjectWrapper zzawg() throws RemoteException {
        Parcel zza = zza(4, zzbe());
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final IObjectWrapper zzd(Bitmap bitmap) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) bitmap);
        Parcel zza = zza(6, zzbe);
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final IObjectWrapper zze(float f) throws RemoteException {
        Parcel zzbe = zzbe();
        zzbe.writeFloat(f);
        Parcel zza = zza(5, zzbe);
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final IObjectWrapper zzea(int i) throws RemoteException {
        Parcel zzbe = zzbe();
        zzbe.writeInt(i);
        Parcel zza = zza(1, zzbe);
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final IObjectWrapper zzin(String str) throws RemoteException {
        Parcel zzbe = zzbe();
        zzbe.writeString(str);
        Parcel zza = zza(2, zzbe);
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final IObjectWrapper zzio(String str) throws RemoteException {
        Parcel zzbe = zzbe();
        zzbe.writeString(str);
        Parcel zza = zza(3, zzbe);
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final IObjectWrapper zzip(String str) throws RemoteException {
        Parcel zzbe = zzbe();
        zzbe.writeString(str);
        Parcel zza = zza(7, zzbe);
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }
}
