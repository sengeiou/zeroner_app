package com.google.android.gms.fitness.request;

import android.os.Looper;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzcm;
import java.util.HashMap;
import java.util.Map;

public final class zzd {
    private static final zzd zzhfy = new zzd();
    private final Map<zzck<BleScanCallback>, zza> zzhfz = new HashMap();

    private zzd() {
    }

    public static zzd zzaqt() {
        return zzhfy;
    }

    private static zzci<BleScanCallback> zzc(BleScanCallback bleScanCallback) {
        return zzcm.zzb(bleScanCallback, Looper.getMainLooper(), BleScanCallback.class.getSimpleName());
    }

    public final zza zza(zzci<BleScanCallback> zzci) {
        zza zza;
        synchronized (this.zzhfz) {
            zza = (zza) this.zzhfz.get(zzci.zzajo());
            if (zza == null) {
                zza = new zza(zzci, null);
                this.zzhfz.put(zzci.zzajo(), zza);
            }
        }
        return zza;
    }

    public final zza zza(BleScanCallback bleScanCallback) {
        return zza(zzc(bleScanCallback));
    }

    public final zza zzb(zzci<BleScanCallback> zzci) {
        zza zza;
        synchronized (this.zzhfz) {
            zza = (zza) this.zzhfz.get(zzci.zzajo());
            if (zza != null) {
                zza.release();
            }
        }
        return zza;
    }

    public final zza zzb(BleScanCallback bleScanCallback) {
        return zzb(zzc(bleScanCallback));
    }
}
