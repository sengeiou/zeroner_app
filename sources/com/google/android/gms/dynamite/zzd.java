package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule.zzc;

final class zzd implements com.google.android.gms.dynamite.DynamiteModule.zzd {
    zzd() {
    }

    public final zzj zza(Context context, String str, zzi zzi) throws zzc {
        zzj zzj = new zzj();
        zzj.zzgxg = zzi.zzab(context, str);
        zzj.zzgxh = zzi.zzc(context, str, true);
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
