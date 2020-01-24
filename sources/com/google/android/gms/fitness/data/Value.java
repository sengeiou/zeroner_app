package com.google.android.gms.fitness.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzl;
import com.google.android.gms.fitness.zza;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public final class Value extends zzbfm {
    public static final Creator<Value> CREATOR = new zzai();
    private final int format;
    private float value;
    private final int versionCode;
    private String zzgcc;
    private boolean zzhea;
    private Map<String, MapValue> zzheb;
    private int[] zzhec;
    private float[] zzhed;
    private byte[] zzhee;

    public Value(int i) {
        this(3, i, false, 0.0f, null, null, null, null, null);
    }

    Value(int i, int i2, boolean z, float f, String str, Bundle bundle, int[] iArr, float[] fArr, byte[] bArr) {
        ArrayMap arrayMap;
        this.versionCode = i;
        this.format = i2;
        this.zzhea = z;
        this.value = f;
        this.zzgcc = str;
        if (bundle == null) {
            arrayMap = null;
        } else {
            bundle.setClassLoader(MapValue.class.getClassLoader());
            ArrayMap arrayMap2 = new ArrayMap(bundle.size());
            for (String str2 : bundle.keySet()) {
                arrayMap2.put(str2, (MapValue) bundle.getParcelable(str2));
            }
            arrayMap = arrayMap2;
        }
        this.zzheb = arrayMap;
        this.zzhec = iArr;
        this.zzhed = fArr;
        this.zzhee = bArr;
    }

    public final String asActivity() {
        return zza.getName(asInt());
    }

    public final float asFloat() {
        zzbq.zza(this.format == 2, (Object) "Value is not in float format");
        return this.value;
    }

    public final int asInt() {
        boolean z = true;
        if (this.format != 1) {
            z = false;
        }
        zzbq.zza(z, (Object) "Value is not in int format");
        return Float.floatToRawIntBits(this.value);
    }

    public final String asString() {
        zzbq.zza(this.format == 3, (Object) "Value is not in string format");
        return this.zzgcc;
    }

    public final void clearKey(String str) {
        zzbq.zza(this.format == 4, (Object) "Attempting to set a key's value to a field that is not in FLOAT_MAP format.  Please check the data type definition and use the right format.");
        if (this.zzheb != null) {
            this.zzheb.remove(str);
        }
    }

    public final boolean equals(Object obj) {
        boolean z;
        if (this != obj) {
            if (!(obj instanceof Value)) {
                return false;
            }
            Value value2 = (Value) obj;
            if (this.format == value2.format && this.zzhea == value2.zzhea) {
                switch (this.format) {
                    case 1:
                        if (asInt() != value2.asInt()) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    case 2:
                        if (this.value != value2.value) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    case 3:
                        z = zzbg.equal(this.zzgcc, value2.zzgcc);
                        break;
                    case 4:
                        z = zzbg.equal(this.zzheb, value2.zzheb);
                        break;
                    case 5:
                        z = Arrays.equals(this.zzhec, value2.zzhec);
                        break;
                    case 6:
                        z = Arrays.equals(this.zzhed, value2.zzhed);
                        break;
                    case 7:
                        z = Arrays.equals(this.zzhee, value2.zzhee);
                        break;
                    default:
                        if (this.value != value2.value) {
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

    public final int getFormat() {
        return this.format;
    }

    @Nullable
    public final Float getKeyValue(String str) {
        zzbq.zza(this.format == 4, (Object) "Value is not in float map format");
        if (this.zzheb == null || !this.zzheb.containsKey(str)) {
            return null;
        }
        return Float.valueOf(((MapValue) this.zzheb.get(str)).asFloat());
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.value), this.zzgcc, this.zzheb, this.zzhec, this.zzhed, this.zzhee});
    }

    public final boolean isSet() {
        return this.zzhea;
    }

    public final void setActivity(String str) {
        setInt(zza.zzhc(str));
    }

    public final void setFloat(float f) {
        zzbq.zza(this.format == 2, (Object) "Attempting to set an float value to a field that is not in FLOAT format.  Please check the data type definition and use the right format.");
        this.zzhea = true;
        this.value = f;
    }

    public final void setInt(int i) {
        zzbq.zza(this.format == 1, (Object) "Attempting to set an int value to a field that is not in INT32 format.  Please check the data type definition and use the right format.");
        this.zzhea = true;
        this.value = Float.intBitsToFloat(i);
    }

    public final void setKeyValue(String str, float f) {
        zzbq.zza(this.format == 4, (Object) "Attempting to set a key's value to a field that is not in FLOAT_MAP format.  Please check the data type definition and use the right format.");
        this.zzhea = true;
        if (this.zzheb == null) {
            this.zzheb = new HashMap();
        }
        this.zzheb.put(str, new MapValue(2, f));
    }

    public final void setString(String str) {
        zzbq.zza(this.format == 3, (Object) "Attempting to set a string value to a field that is not in STRING format.  Please check the data type definition and use the right format.");
        this.zzhea = true;
        this.zzgcc = str;
    }

    public final String toString() {
        if (!this.zzhea) {
            return "unset";
        }
        switch (this.format) {
            case 1:
                return Integer.toString(asInt());
            case 2:
                return Float.toString(this.value);
            case 3:
                return this.zzgcc;
            case 4:
                return new TreeMap(this.zzheb).toString();
            case 5:
                return Arrays.toString(this.zzhec);
            case 6:
                return Arrays.toString(this.zzhed);
            case 7:
                return zzl.zza(this.zzhee, 0, this.zzhee.length, false);
            default:
                return "unknown";
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        Bundle bundle;
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, getFormat());
        zzbfp.zza(parcel, 2, isSet());
        zzbfp.zza(parcel, 3, this.value);
        zzbfp.zza(parcel, 4, this.zzgcc, false);
        if (this.zzheb == null) {
            bundle = null;
        } else {
            Bundle bundle2 = new Bundle(this.zzheb.size());
            for (Entry entry : this.zzheb.entrySet()) {
                bundle2.putParcelable((String) entry.getKey(), (Parcelable) entry.getValue());
            }
            bundle = bundle2;
        }
        zzbfp.zza(parcel, 5, bundle, false);
        zzbfp.zza(parcel, 6, this.zzhec, false);
        zzbfp.zza(parcel, 7, this.zzhed, false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zza(parcel, 8, this.zzhee, false);
        zzbfp.zzai(parcel, zze);
    }
}
