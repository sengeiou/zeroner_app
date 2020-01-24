package com.google.android.gms.internal;

import com.github.mikephil.charting.utils.Utils;
import java.util.concurrent.TimeUnit;

public final class zzbuy {
    public static <DP, DT> String zza(DP dp, zzbuv<DP, DT> zzbuv) {
        double zzb;
        zzbuw zzaqe = zzbuv.zzaqe();
        if (!zzaqe.zzhe(zzbuv.zzab(dp))) {
            return null;
        }
        Object zzaa = zzbuv.zzaa(dp);
        for (int i = 0; i < zzaqe.zzac(zzaa); i++) {
            String zzg = zzaqe.zzg(zzaa, i);
            if (zzbuv.zzd(dp, i)) {
                double zze = (double) zzaqe.zze(zzaa, i);
                if (zze == 1.0d) {
                    zzb = (double) zzbuv.zzc(dp, i);
                } else if (zze == 2.0d) {
                    zzb = zzbuv.zzb(dp, i);
                } else {
                    continue;
                }
                zzbvb zzhn = zzbuz.zzaqs().zzhn(zzg);
                if (zzhn != null && !zzhn.zzf(zzb)) {
                    return "Field out of range";
                }
                zzbvb zzz = zzbuz.zzaqs().zzz(zzaqe.zzad(zzaa), zzg);
                if (zzz != null) {
                    long zza = zzbuv.zza(dp, TimeUnit.NANOSECONDS);
                    if (zza == 0) {
                        if (zzb == Utils.DOUBLE_EPSILON) {
                            return null;
                        }
                        return "DataPoint out of range";
                    } else if (!zzz.zzf(zzb / ((double) zza))) {
                        return "DataPoint out of range";
                    }
                } else {
                    continue;
                }
            } else if (!zzaqe.zzf(zzaa, i) && !zzbuz.zzheg.contains(zzg)) {
                return String.valueOf(zzg).concat(" not set");
            }
        }
        return null;
    }
}
