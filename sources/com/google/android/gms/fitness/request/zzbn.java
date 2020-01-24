package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import java.util.Arrays;

public final class zzbn extends zzbfm {
    public static final Creator<zzbn> CREATOR = new zzbo();
    private final int zzeck;
    private final DataSource zzhbr;
    private final DataType zzhbs;
    private final zzbyf zzhgc;

    zzbn(int i, DataType dataType, DataSource dataSource, IBinder iBinder) {
        this.zzeck = i;
        this.zzhbs = dataType;
        this.zzhbr = dataSource;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    public zzbn(DataType dataType, DataSource dataSource, zzbyf zzbyf) {
        this.zzeck = 3;
        this.zzhbs = dataType;
        this.zzhbr = dataSource;
        this.zzhgc = zzbyf;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof zzbn)) {
                return false;
            }
            zzbn zzbn = (zzbn) obj;
            if (!(zzbg.equal(this.zzhbr, zzbn.zzhbr) && zzbg.equal(this.zzhbs, zzbn.zzhbs))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhbr, this.zzhbs});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzhbs, i, false);
        zzbfp.zza(parcel, 2, (Parcelable) this.zzhbr, i, false);
        zzbfp.zza(parcel, 3, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
