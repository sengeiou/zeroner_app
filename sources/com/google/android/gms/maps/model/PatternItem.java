package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatternItem extends zzbfm {
    public static final Creator<PatternItem> CREATOR = new zzi();
    private static final String TAG = PatternItem.class.getSimpleName();
    private final int type;
    @Nullable
    private final Float zzivm;

    public PatternItem(int i, @Nullable Float f) {
        boolean z = true;
        if (i != 1 && (f == null || f.floatValue() < 0.0f)) {
            z = false;
        }
        String valueOf = String.valueOf(f);
        zzbq.checkArgument(z, new StringBuilder(String.valueOf(valueOf).length() + 45).append("Invalid PatternItem: type=").append(i).append(" length=").append(valueOf).toString());
        this.type = i;
        this.zzivm = f;
    }

    @Nullable
    static List<PatternItem> zzag(@Nullable List<PatternItem> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (PatternItem patternItem : list) {
            if (patternItem != null) {
                switch (patternItem.type) {
                    case 0:
                        patternItem = new Dash(patternItem.zzivm.floatValue());
                        break;
                    case 1:
                        patternItem = new Dot();
                        break;
                    case 2:
                        patternItem = new Gap(patternItem.zzivm.floatValue());
                        break;
                    default:
                        Log.w(TAG, "Unknown PatternItem type: " + patternItem.type);
                        break;
                }
            } else {
                patternItem = null;
            }
            arrayList.add(patternItem);
        }
        return arrayList;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PatternItem)) {
            return false;
        }
        PatternItem patternItem = (PatternItem) obj;
        return this.type == patternItem.type && zzbg.equal(this.zzivm, patternItem.zzivm);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.type), this.zzivm});
    }

    public String toString() {
        int i = this.type;
        String valueOf = String.valueOf(this.zzivm);
        return new StringBuilder(String.valueOf(valueOf).length() + 39).append("[PatternItem: type=").append(i).append(" length=").append(valueOf).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.type);
        zzbfp.zza(parcel, 3, this.zzivm, false);
        zzbfp.zzai(parcel, zze);
    }
}
