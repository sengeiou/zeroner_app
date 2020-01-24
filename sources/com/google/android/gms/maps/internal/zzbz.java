package com.google.android.gms.maps.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class zzbz {
    private static final String TAG = zzbz.class.getSimpleName();
    private static Context zziub = null;
    private static zze zziuc;

    private static <T> T zza(ClassLoader classLoader, String str) {
        try {
            return zzd(((ClassLoader) zzbq.checkNotNull(classLoader)).loadClass(str));
        } catch (ClassNotFoundException e) {
            String str2 = "Unable to find dynamic class ";
            String valueOf = String.valueOf(str);
            throw new IllegalStateException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
    }

    private static <T> T zzd(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            String str = "Unable to instantiate the dynamic class ";
            String valueOf = String.valueOf(cls.getName());
            throw new IllegalStateException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        } catch (IllegalAccessException e2) {
            String str2 = "Unable to call the default constructor of ";
            String valueOf2 = String.valueOf(cls.getName());
            throw new IllegalStateException(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
        }
    }

    public static zze zzdt(Context context) throws GooglePlayServicesNotAvailableException {
        zze zzf;
        zzbq.checkNotNull(context);
        if (zziuc != null) {
            return zziuc;
        }
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        switch (isGooglePlayServicesAvailable) {
            case 0:
                Log.i(TAG, "Making Creator dynamically");
                IBinder iBinder = (IBinder) zza(zzdu(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl");
                if (iBinder == null) {
                    zzf = null;
                } else {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
                    zzf = queryLocalInterface instanceof zze ? (zze) queryLocalInterface : new zzf(iBinder);
                }
                zziuc = zzf;
                try {
                    zziuc.zzi(zzn.zzz(zzdu(context).getResources()), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
                    return zziuc;
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                }
            default:
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
        }
    }

    private static Context zzdu(Context context) {
        if (zziub != null) {
            return zziub;
        }
        Context zzdv = zzdv(context);
        zziub = zzdv;
        return zzdv;
    }

    private static Context zzdv(Context context) {
        try {
            return DynamiteModule.zza(context, DynamiteModule.zzgww, "com.google.android.gms.maps_dynamite").zzaqb();
        } catch (Throwable th) {
            Log.e(TAG, "Failed to load maps module, use legacy", th);
            return GooglePlayServicesUtil.getRemoteContext(context);
        }
    }
}
