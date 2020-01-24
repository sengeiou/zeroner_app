package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.fitness.result.DataReadResult;

final class zzbzh extends zzbwx {
    private final zzn<DataReadResult> zzgbw;
    private int zzhfg;
    private DataReadResult zzhfh;

    private zzbzh(zzn<DataReadResult> zzn) {
        this.zzhfg = 0;
        this.zzhfh = null;
        this.zzgbw = zzn;
    }

    /* synthetic */ zzbzh(zzn zzn, zzbyz zzbyz) {
        this(zzn);
    }

    public final void zza(DataReadResult dataReadResult) {
        synchronized (this) {
            if (Log.isLoggable("Fitness", 2)) {
                Log.v("Fitness", "Received batch result " + this.zzhfg);
            }
            if (this.zzhfh == null) {
                this.zzhfh = dataReadResult;
            } else {
                this.zzhfh.zzb(dataReadResult);
            }
            this.zzhfg++;
            if (this.zzhfg == this.zzhfh.zzaqx()) {
                this.zzgbw.setResult(this.zzhfh);
            }
        }
    }
}
