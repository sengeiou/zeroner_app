package com.google.android.gms.fitness.data;

import com.google.android.gms.internal.zzbuw;

final class zzg implements zzbuw<DataType> {
    public static final zzg zzhaf = new zzg();

    private zzg() {
    }

    private static Field zza(DataType dataType, int i) {
        return (Field) dataType.getFields().get(i);
    }

    public final /* synthetic */ int zzac(Object obj) {
        return ((DataType) obj).getFields().size();
    }

    public final /* synthetic */ String zzad(Object obj) {
        return ((DataType) obj).getName();
    }

    public final /* synthetic */ int zze(Object obj, int i) {
        return zza((DataType) obj, i).getFormat();
    }

    public final /* synthetic */ boolean zzf(Object obj, int i) {
        return Boolean.TRUE.equals(zza((DataType) obj, i).isOptional());
    }

    public final /* synthetic */ String zzg(Object obj, int i) {
        return zza((DataType) obj, i).getName();
    }

    public final boolean zzhe(String str) {
        return zzm.zzhf(str) != null;
    }
}
