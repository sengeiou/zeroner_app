package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.location.zze;
import java.util.Collections;
import java.util.List;

public final class zzcdx extends zzbfm {
    public static final Creator<zzcdx> CREATOR = new zzcdy();
    static final List<zzcdv> zzikv = Collections.emptyList();
    static final zze zzikw = new zze();
    @Nullable
    private String mTag;
    private zze zzikx;
    private List<zzcdv> zziky;

    zzcdx(zze zze, List<zzcdv> list, String str) {
        this.zzikx = zze;
        this.zziky = list;
        this.mTag = str;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzcdx)) {
            return false;
        }
        zzcdx zzcdx = (zzcdx) obj;
        return zzbg.equal(this.zzikx, zzcdx.zzikx) && zzbg.equal(this.zziky, zzcdx.zziky) && zzbg.equal(this.mTag, zzcdx.mTag);
    }

    public final int hashCode() {
        return this.zzikx.hashCode();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzikx, i, false);
        zzbfp.zzc(parcel, 2, this.zziky, false);
        zzbfp.zza(parcel, 3, this.mTag, false);
        zzbfp.zzai(parcel, zze);
    }
}
