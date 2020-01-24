package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public final class zzcfo extends zzbfm {
    public static final Creator<zzcfo> CREATOR = new zzcfp();
    static final List<zzcdv> zzikv = Collections.emptyList();
    @Nullable
    private String mTag;
    @Nullable
    private String zzelc;
    private LocationRequest zzhhp;
    private List<zzcdv> zziky;
    private boolean zzilw;
    private boolean zzilx;
    private boolean zzily;
    private boolean zzilz = true;

    zzcfo(LocationRequest locationRequest, List<zzcdv> list, @Nullable String str, boolean z, boolean z2, boolean z3, String str2) {
        this.zzhhp = locationRequest;
        this.zziky = list;
        this.mTag = str;
        this.zzilw = z;
        this.zzilx = z2;
        this.zzily = z3;
        this.zzelc = str2;
    }

    @Deprecated
    public static zzcfo zza(LocationRequest locationRequest) {
        return new zzcfo(locationRequest, zzikv, null, false, false, false, null);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzcfo)) {
            return false;
        }
        zzcfo zzcfo = (zzcfo) obj;
        return zzbg.equal(this.zzhhp, zzcfo.zzhhp) && zzbg.equal(this.zziky, zzcfo.zziky) && zzbg.equal(this.mTag, zzcfo.mTag) && this.zzilw == zzcfo.zzilw && this.zzilx == zzcfo.zzilx && this.zzily == zzcfo.zzily && zzbg.equal(this.zzelc, zzcfo.zzelc);
    }

    public final int hashCode() {
        return this.zzhhp.hashCode();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.zzhhp.toString());
        if (this.mTag != null) {
            sb.append(" tag=").append(this.mTag);
        }
        if (this.zzelc != null) {
            sb.append(" moduleId=").append(this.zzelc);
        }
        sb.append(" hideAppOps=").append(this.zzilw);
        sb.append(" clients=").append(this.zziky);
        sb.append(" forceCoarseLocation=").append(this.zzilx);
        if (this.zzily) {
            sb.append(" exemptFromBackgroundThrottle");
        }
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzhhp, i, false);
        zzbfp.zzc(parcel, 5, this.zziky, false);
        zzbfp.zza(parcel, 6, this.mTag, false);
        zzbfp.zza(parcel, 7, this.zzilw);
        zzbfp.zza(parcel, 8, this.zzilx);
        zzbfp.zza(parcel, 9, this.zzily);
        zzbfp.zza(parcel, 10, this.zzelc, false);
        zzbfp.zzai(parcel, zze);
    }
}
