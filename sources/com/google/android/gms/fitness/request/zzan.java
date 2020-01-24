package com.google.android.gms.fitness.request;

import android.os.Looper;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzcm;
import java.util.HashMap;
import java.util.Map;

public final class zzan {
    private static final zzan zzhhh = new zzan();
    private final Map<zzck<OnDataPointListener>, zzal> zzhhi = new HashMap();

    private zzan() {
    }

    public static zzan zzaqv() {
        return zzhhh;
    }

    private static zzci<OnDataPointListener> zzc(OnDataPointListener onDataPointListener) {
        return zzcm.zzb(onDataPointListener, Looper.getMainLooper(), OnDataPointListener.class.getSimpleName());
    }

    public final zzal zza(OnDataPointListener onDataPointListener) {
        return zzc(zzc(onDataPointListener));
    }

    public final zzal zzb(OnDataPointListener onDataPointListener) {
        return zzd(zzc(onDataPointListener));
    }

    public final zzal zzc(zzci<OnDataPointListener> zzci) {
        zzal zzal;
        synchronized (this.zzhhi) {
            zzal = (zzal) this.zzhhi.get(zzci.zzajo());
            if (zzal == null) {
                zzal = new zzal(zzci, null);
                this.zzhhi.put(zzci.zzajo(), zzal);
            }
        }
        return zzal;
    }

    public final zzal zzd(zzci<OnDataPointListener> zzci) {
        zzal zzal;
        synchronized (this.zzhhi) {
            zzal = (zzal) this.zzhhi.remove(zzci.zzajo());
            if (zzal != null) {
                zzal.release();
            }
        }
        return zzal;
    }
}
