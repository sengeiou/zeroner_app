package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule.zzc;
import com.google.android.gms.dynamite.DynamiteModule.zzd;

final class zze implements zzd {
    zze() {
    }

    public final zzj zza(Context context, String str, zzi zzi) throws zzc {
        zzj zzj = new zzj();
        zzj.zzgxg = zzi.zzab(context, str);
        if (zzj.zzgxg != 0) {
            zzj.zzgxh = zzi.zzc(context, str, false);
        } else {
            zzj.zzgxh = zzi.zzc(context, str, true);
        }
        if (zzj.zzgxg == 0 && zzj.zzgxh == 0) {
            zzj.zzgxi = 0;
        } else if (zzj.zzgxg >= zzj.zzgxh) {
            zzj.zzgxi = -1;
        } else {
            zzj.zzgxi = 1;
        }
        return zzj;
    }
}
