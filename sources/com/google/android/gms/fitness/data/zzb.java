package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;

public final class zzb extends zzbfm {
    public static final Creator<zzb> CREATOR = new zzc();
    public static final zzb zzgzu = new zzb("com.google.android.gms", null, null);
    private final String packageName;
    private final String version;
    private final int versionCode;
    private final String zzgzv;

    zzb(int i, String str, String str2, String str3) {
        this.versionCode = i;
        this.packageName = (String) zzbq.checkNotNull(str);
        this.version = "";
        this.zzgzv = str3;
    }

    private zzb(String str, String str2, String str3) {
        this(1, str, "", null);
    }

    public static zzb zzhd(String str) {
        return "com.google.android.gms".equals(str) ? zzgzu : new zzb(str, null, null);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof zzb)) {
                return false;
            }
            zzb zzb = (zzb) obj;
            if (!(this.packageName.equals(zzb.packageName) && zzbg.equal(this.version, zzb.version) && zzbg.equal(this.zzgzv, zzb.zzgzv))) {
                return false;
            }
        }
        return true;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.packageName, this.version, this.zzgzv});
    }

    public final String toString() {
        return String.format("Application{%s:%s:%s}", new Object[]{this.packageName, this.version, this.zzgzv});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.packageName, false);
        zzbfp.zza(parcel, 2, this.version, false);
        zzbfp.zza(parcel, 3, this.zzgzv, false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, zze);
    }
}
