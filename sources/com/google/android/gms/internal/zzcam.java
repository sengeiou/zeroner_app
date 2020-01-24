package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataSource;

public final class zzcam extends zzbfm {
    public static final Creator<zzcam> CREATOR = new zzcan();
    private final int zzeck;
    private final DataSource zzhbr;

    zzcam(int i, DataSource dataSource) {
        this.zzeck = i;
        this.zzhbr = dataSource;
    }

    public final DataSource getDataSource() {
        return this.zzhbr;
    }

    public final String toString() {
        return String.format("ApplicationUnregistrationRequest{%s}", new Object[]{this.zzhbr});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzhbr, i, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
