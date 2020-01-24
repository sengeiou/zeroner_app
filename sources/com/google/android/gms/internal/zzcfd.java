package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.zzp;
import com.google.android.gms.location.zzs;
import java.util.HashMap;
import java.util.Map;

public final class zzcfd {
    private final Context mContext;
    private final zzcfu<zzcez> zzikt;
    private ContentProviderClient zzilm = null;
    private boolean zziln = false;
    private final Map<zzck<LocationListener>, zzcfi> zzilo = new HashMap();
    private final Map<zzck<Object>, zzcfh> zzilp = new HashMap();
    private final Map<zzck<LocationCallback>, zzcfe> zzilq = new HashMap();

    public zzcfd(Context context, zzcfu<zzcez> zzcfu) {
        this.mContext = context;
        this.zzikt = zzcfu;
    }

    private final zzcfi zzm(zzci<LocationListener> zzci) {
        zzcfi zzcfi;
        synchronized (this.zzilo) {
            zzcfi = (zzcfi) this.zzilo.get(zzci.zzajo());
            if (zzcfi == null) {
                zzcfi = new zzcfi(zzci);
            }
            this.zzilo.put(zzci.zzajo(), zzcfi);
        }
        return zzcfi;
    }

    private final zzcfe zzn(zzci<LocationCallback> zzci) {
        zzcfe zzcfe;
        synchronized (this.zzilq) {
            zzcfe = (zzcfe) this.zzilq.get(zzci.zzajo());
            if (zzcfe == null) {
                zzcfe = new zzcfe(zzci);
            }
            this.zzilq.put(zzci.zzajo(), zzcfe);
        }
        return zzcfe;
    }

    public final Location getLastLocation() throws RemoteException {
        this.zzikt.zzakm();
        return ((zzcez) this.zzikt.zzakn()).zzif(this.mContext.getPackageName());
    }

    public final void removeAllListeners() throws RemoteException {
        synchronized (this.zzilo) {
            for (zzcfi zzcfi : this.zzilo.values()) {
                if (zzcfi != null) {
                    ((zzcez) this.zzikt.zzakn()).zza(zzcfq.zza((zzs) zzcfi, (zzceu) null));
                }
            }
            this.zzilo.clear();
        }
        synchronized (this.zzilq) {
            for (zzcfe zzcfe : this.zzilq.values()) {
                if (zzcfe != null) {
                    ((zzcez) this.zzikt.zzakn()).zza(zzcfq.zza((zzp) zzcfe, (zzceu) null));
                }
            }
            this.zzilq.clear();
        }
        synchronized (this.zzilp) {
            for (zzcfh zzcfh : this.zzilp.values()) {
                if (zzcfh != null) {
                    ((zzcez) this.zzikt.zzakn()).zza(new zzcdz(2, null, zzcfh.asBinder(), null));
                }
            }
            this.zzilp.clear();
        }
    }

    public final void zza(PendingIntent pendingIntent, zzceu zzceu) throws RemoteException {
        this.zzikt.zzakm();
        ((zzcez) this.zzikt.zzakn()).zza(new zzcfq(2, null, null, pendingIntent, null, zzceu != null ? zzceu.asBinder() : null));
    }

    public final void zza(zzck<LocationListener> zzck, zzceu zzceu) throws RemoteException {
        this.zzikt.zzakm();
        zzbq.checkNotNull(zzck, "Invalid null listener key");
        synchronized (this.zzilo) {
            zzcfi zzcfi = (zzcfi) this.zzilo.remove(zzck);
            if (zzcfi != null) {
                zzcfi.release();
                ((zzcez) this.zzikt.zzakn()).zza(zzcfq.zza((zzs) zzcfi, zzceu));
            }
        }
    }

    public final void zza(zzceu zzceu) throws RemoteException {
        this.zzikt.zzakm();
        ((zzcez) this.zzikt.zzakn()).zza(zzceu);
    }

    public final void zza(zzcfo zzcfo, zzci<LocationCallback> zzci, zzceu zzceu) throws RemoteException {
        this.zzikt.zzakm();
        ((zzcez) this.zzikt.zzakn()).zza(new zzcfq(1, zzcfo, null, null, zzn(zzci).asBinder(), zzceu != null ? zzceu.asBinder() : null));
    }

    public final void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzceu zzceu) throws RemoteException {
        this.zzikt.zzakm();
        ((zzcez) this.zzikt.zzakn()).zza(new zzcfq(1, zzcfo.zza(locationRequest), null, pendingIntent, null, zzceu != null ? zzceu.asBinder() : null));
    }

    public final void zza(LocationRequest locationRequest, zzci<LocationListener> zzci, zzceu zzceu) throws RemoteException {
        this.zzikt.zzakm();
        ((zzcez) this.zzikt.zzakn()).zza(new zzcfq(1, zzcfo.zza(locationRequest), zzm(zzci).asBinder(), null, null, zzceu != null ? zzceu.asBinder() : null));
    }

    public final LocationAvailability zzavk() throws RemoteException {
        this.zzikt.zzakm();
        return ((zzcez) this.zzikt.zzakn()).zzig(this.mContext.getPackageName());
    }

    public final void zzavl() throws RemoteException {
        if (this.zziln) {
            zzbj(false);
        }
    }

    public final void zzb(zzck<LocationCallback> zzck, zzceu zzceu) throws RemoteException {
        this.zzikt.zzakm();
        zzbq.checkNotNull(zzck, "Invalid null listener key");
        synchronized (this.zzilq) {
            zzcfe zzcfe = (zzcfe) this.zzilq.remove(zzck);
            if (zzcfe != null) {
                zzcfe.release();
                ((zzcez) this.zzikt.zzakn()).zza(zzcfq.zza((zzp) zzcfe, zzceu));
            }
        }
    }

    public final void zzbj(boolean z) throws RemoteException {
        this.zzikt.zzakm();
        ((zzcez) this.zzikt.zzakn()).zzbj(z);
        this.zziln = z;
    }

    public final void zzc(Location location) throws RemoteException {
        this.zzikt.zzakm();
        ((zzcez) this.zzikt.zzakn()).zzc(location);
    }
}
