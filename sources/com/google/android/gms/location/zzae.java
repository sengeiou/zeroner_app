package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;

public final class zzae extends zzbfm {
    public static final Creator<zzae> CREATOR = new zzaf();
    private int zzikl;
    private int zzikm;
    private long zzikn;
    private long zziko;

    zzae(int i, int i2, long j, long j2) {
        this.zzikl = i;
        this.zzikm = i2;
        this.zzikn = j;
        this.zziko = j2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzae zzae = (zzae) obj;
        return this.zzikl == zzae.zzikl && this.zzikm == zzae.zzikm && this.zzikn == zzae.zzikn && this.zziko == zzae.zziko;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzikm), Integer.valueOf(this.zzikl), Long.valueOf(this.zziko), Long.valueOf(this.zzikn)});
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NetworkLocationStatus:");
        sb.append(" Wifi status: ").append(this.zzikl).append(" Cell status: ").append(this.zzikm).append(" elapsed time NS: ").append(this.zziko).append(" system time ms: ").append(this.zzikn);
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzikl);
        zzbfp.zzc(parcel, 2, this.zzikm);
        zzbfp.zza(parcel, 3, this.zzikn);
        zzbfp.zza(parcel, 4, this.zziko);
        zzbfp.zzai(parcel, zze);
    }
}
