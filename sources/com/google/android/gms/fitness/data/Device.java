package com.google.android.gms.fitness.data;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.provider.Settings.Secure;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbux;
import com.google.android.gms.internal.zzcaj;
import java.util.Arrays;

public final class Device extends zzbfm {
    public static final Creator<Device> CREATOR = new zzo();
    public static final int TYPE_CHEST_STRAP = 4;
    public static final int TYPE_HEAD_MOUNTED = 6;
    public static final int TYPE_PHONE = 1;
    public static final int TYPE_SCALE = 5;
    public static final int TYPE_TABLET = 2;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WATCH = 3;
    private final int type;
    private final String version;
    private final int versionCode;
    private final String zzhbt;
    private final String zzhbu;
    private final String zzhbv;
    private final int zzhbw;

    Device(int i, String str, String str2, String str3, String str4, int i2, int i3) {
        this.versionCode = i;
        this.zzhbt = (String) zzbq.checkNotNull(str);
        this.zzhbu = (String) zzbq.checkNotNull(str2);
        this.version = "";
        if (str4 != null) {
            this.zzhbv = str4;
            this.type = i2;
            this.zzhbw = i3;
            return;
        }
        throw new IllegalStateException("Device UID is null.");
    }

    public Device(String str, String str2, String str3, int i) {
        this(str, str2, "", str3, i, 0);
    }

    private Device(String str, String str2, String str3, String str4, int i, int i2) {
        this(1, str, str2, "", str4, i, i2);
    }

    public static Device getLocalDevice(Context context) {
        int zzdg = zzbux.zzdg(context);
        return new Device(Build.MANUFACTURER, Build.MODEL, VERSION.RELEASE, Secure.getString(context.getContentResolver(), "android_id"), zzdg, 2);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof Device)) {
                return false;
            }
            Device device = (Device) obj;
            if (!(zzbg.equal(this.zzhbt, device.zzhbt) && zzbg.equal(this.zzhbu, device.zzhbu) && zzbg.equal(this.version, device.version) && zzbg.equal(this.zzhbv, device.zzhbv) && this.type == device.type && this.zzhbw == device.zzhbw)) {
                return false;
            }
        }
        return true;
    }

    public final String getManufacturer() {
        return this.zzhbt;
    }

    public final String getModel() {
        return this.zzhbu;
    }

    /* access modifiers changed from: 0000 */
    public final String getStreamIdentifier() {
        return String.format("%s:%s:%s", new Object[]{this.zzhbt, this.zzhbu, this.zzhbv});
    }

    public final int getType() {
        return this.type;
    }

    public final String getUid() {
        return this.zzhbv;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhbt, this.zzhbu, this.version, this.zzhbv, Integer.valueOf(this.type)});
    }

    public final String toString() {
        return String.format("Device{%s:%s:%s:%s}", new Object[]{getStreamIdentifier(), this.version, Integer.valueOf(this.type), Integer.valueOf(this.zzhbw)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, getManufacturer(), false);
        zzbfp.zza(parcel, 2, getModel(), false);
        zzbfp.zza(parcel, 3, this.version, false);
        zzbfp.zza(parcel, 4, this.zzhbw == 1 ? this.zzhbv : zzcaj.zzho(this.zzhbv), false);
        zzbfp.zzc(parcel, 5, getType());
        zzbfp.zzc(parcel, 6, this.zzhbw);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, zze);
    }
}
