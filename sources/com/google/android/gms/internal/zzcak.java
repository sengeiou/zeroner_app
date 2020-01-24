package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.DataType;
import java.util.Collections;
import java.util.List;

public final class zzcak extends zzbfm {
    public static final Creator<zzcak> CREATOR = new zzcal();
    private final int zzeck;
    private final List<DataType> zzgzy;

    zzcak(int i, List<DataType> list) {
        this.zzeck = i;
        this.zzgzy = list;
    }

    public final List<DataType> getDataTypes() {
        return Collections.unmodifiableList(this.zzgzy);
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("dataTypes", this.zzgzy).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, Collections.unmodifiableList(this.zzgzy), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
