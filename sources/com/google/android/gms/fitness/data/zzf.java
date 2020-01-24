package com.google.android.gms.fitness.data;

import com.google.android.gms.internal.zzbuv;
import com.google.android.gms.internal.zzbuw;
import java.util.concurrent.TimeUnit;

final class zzf implements zzbuv<DataPoint, DataType> {
    public static final zzf zzhae = new zzf();

    private zzf() {
    }

    public final /* synthetic */ long zza(Object obj, TimeUnit timeUnit) {
        DataPoint dataPoint = (DataPoint) obj;
        return dataPoint.getEndTime(timeUnit) - dataPoint.getStartTime(timeUnit);
    }

    public final /* synthetic */ Object zzaa(Object obj) {
        return ((DataPoint) obj).getDataType();
    }

    public final /* synthetic */ String zzab(Object obj) {
        return ((DataPoint) obj).getDataType().getName();
    }

    public final zzbuw<DataType> zzaqe() {
        return zzg.zzhaf;
    }

    public final /* synthetic */ double zzb(Object obj, int i) {
        return (double) ((DataPoint) obj).zzdb(i).asFloat();
    }

    public final /* synthetic */ int zzc(Object obj, int i) {
        return ((DataPoint) obj).zzdb(i).asInt();
    }

    public final /* synthetic */ boolean zzd(Object obj, int i) {
        return ((DataPoint) obj).zzdb(i).isSet();
    }
}
