package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;
import kotlin.jvm.internal.LongCompanionObject;

public final class zze extends zzbfm {
    public static final Creator<zze> CREATOR = new zzf();
    private boolean zziir;
    private long zziis;
    private float zziit;
    private long zziiu;
    private int zziiv;

    public zze() {
        this(true, 50, 0.0f, LongCompanionObject.MAX_VALUE, Integer.MAX_VALUE);
    }

    zze(boolean z, long j, float f, long j2, int i) {
        this.zziir = z;
        this.zziis = j;
        this.zziit = f;
        this.zziiu = j2;
        this.zziiv = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zze)) {
            return false;
        }
        zze zze = (zze) obj;
        return this.zziir == zze.zziir && this.zziis == zze.zziis && Float.compare(this.zziit, zze.zziit) == 0 && this.zziiu == zze.zziiu && this.zziiv == zze.zziiv;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.zziir), Long.valueOf(this.zziis), Float.valueOf(this.zziit), Long.valueOf(this.zziiu), Integer.valueOf(this.zziiv)});
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DeviceOrientationRequest[mShouldUseMag=").append(this.zziir);
        sb.append(" mMinimumSamplingPeriodMs=").append(this.zziis);
        sb.append(" mSmallestAngleChangeRadians=").append(this.zziit);
        if (this.zziiu != LongCompanionObject.MAX_VALUE) {
            long elapsedRealtime = this.zziiu - SystemClock.elapsedRealtime();
            sb.append(" expireIn=");
            sb.append(elapsedRealtime).append("ms");
        }
        if (this.zziiv != Integer.MAX_VALUE) {
            sb.append(" num=").append(this.zziiv);
        }
        sb.append(']');
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zziir);
        zzbfp.zza(parcel, 2, this.zziis);
        zzbfp.zza(parcel, 3, this.zziit);
        zzbfp.zza(parcel, 4, this.zziiu);
        zzbfp.zzc(parcel, 5, this.zziiv);
        zzbfp.zzai(parcel, zze);
    }
}
