package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzag;

public final class zzcfa extends zzeu implements zzcez {
    zzcfa(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.internal.IGoogleLocationManagerService");
    }

    public final void zza(long j, boolean z, PendingIntent pendingIntent) throws RemoteException {
        Parcel zzbe = zzbe();
        zzbe.writeLong(j);
        zzew.zza(zzbe, true);
        zzew.zza(zzbe, (Parcelable) pendingIntent);
        zzb(5, zzbe);
    }

    public final void zza(zzcdz zzcdz) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzcdz);
        zzb(75, zzbe);
    }

    public final void zza(zzceu zzceu) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (IInterface) zzceu);
        zzb(67, zzbe);
    }

    public final void zza(zzcfq zzcfq) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzcfq);
        zzb(59, zzbe);
    }

    public final void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzcex zzcex) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) geofencingRequest);
        zzew.zza(zzbe, (Parcelable) pendingIntent);
        zzew.zza(zzbe, (IInterface) zzcex);
        zzb(57, zzbe);
    }

    public final void zza(LocationSettingsRequest locationSettingsRequest, zzcfb zzcfb, String str) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) locationSettingsRequest);
        zzew.zza(zzbe, (IInterface) zzcfb);
        zzbe.writeString(str);
        zzb(63, zzbe);
    }

    public final void zza(zzag zzag, zzcex zzcex) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) zzag);
        zzew.zza(zzbe, (IInterface) zzcex);
        zzb(74, zzbe);
    }

    public final void zzbj(boolean z) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, z);
        zzb(12, zzbe);
    }

    public final void zzc(PendingIntent pendingIntent) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) pendingIntent);
        zzb(6, zzbe);
    }

    public final void zzc(Location location) throws RemoteException {
        Parcel zzbe = zzbe();
        zzew.zza(zzbe, (Parcelable) location);
        zzb(13, zzbe);
    }

    public final Location zzif(String str) throws RemoteException {
        Parcel zzbe = zzbe();
        zzbe.writeString(str);
        Parcel zza = zza(21, zzbe);
        Location location = (Location) zzew.zza(zza, Location.CREATOR);
        zza.recycle();
        return location;
    }

    public final LocationAvailability zzig(String str) throws RemoteException {
        Parcel zzbe = zzbe();
        zzbe.writeString(str);
        Parcel zza = zza(34, zzbe);
        LocationAvailability locationAvailability = (LocationAvailability) zzew.zza(zza, LocationAvailability.CREATOR);
        zza.recycle();
        return locationAvailability;
    }
}
