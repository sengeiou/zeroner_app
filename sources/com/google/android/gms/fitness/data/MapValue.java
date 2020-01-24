package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public class MapValue extends zzbfm implements ReflectedParcelable {
    public static final Creator<MapValue> CREATOR = new zzw();
    private final int zzeck;
    private final int zzhdi;
    private final float zzhdj;

    public MapValue(int i, float f) {
        this(1, 2, f);
    }

    MapValue(int i, int i2, float f) {
        this.zzeck = i;
        this.zzhdi = i2;
        this.zzhdj = f;
    }

    public final float asFloat() {
        zzbq.zza(this.zzhdi == 2, (Object) "Value is not in float format");
        return this.zzhdj;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this != obj) {
            if (!(obj instanceof MapValue)) {
                return false;
            }
            MapValue mapValue = (MapValue) obj;
            if (this.zzhdi == mapValue.zzhdi) {
                switch (this.zzhdi) {
                    case 2:
                        if (asFloat() != mapValue.asFloat()) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    default:
                        if (this.zzhdj != mapValue.zzhdj) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                }
            } else {
                z = false;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return (int) this.zzhdj;
    }

    public String toString() {
        switch (this.zzhdi) {
            case 2:
                return Float.toString(asFloat());
            default:
                return "unknown";
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzhdi);
        zzbfp.zza(parcel, 2, this.zzhdj);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
