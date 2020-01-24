package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;

public final class zzae extends zzbfm {
    public static final Creator<zzae> CREATOR = new zzaf();
    private int zzeck;
    private final Session zzgzp;
    private final DataSet zzhdx;

    zzae(int i, Session session, DataSet dataSet) {
        this.zzeck = i;
        this.zzgzp = session;
        this.zzhdx = dataSet;
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof zzae)) {
                return false;
            }
            zzae zzae = (zzae) obj;
            if (!(zzbg.equal(this.zzgzp, zzae.zzgzp) && zzbg.equal(this.zzhdx, zzae.zzhdx))) {
                return false;
            }
        }
        return true;
    }

    public final DataSet getDataSet() {
        return this.zzhdx;
    }

    public final Session getSession() {
        return this.zzgzp;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzgzp, this.zzhdx});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("session", this.zzgzp).zzg("dataSet", this.zzhdx).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzgzp, i, false);
        zzbfp.zza(parcel, 2, (Parcelable) this.zzhdx, i, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
