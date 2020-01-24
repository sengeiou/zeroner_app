package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;

public final class zzw implements Creator<MapValue> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int i = 0;
        int zzd = zzbfn.zzd(parcel);
        float f = 0.0f;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 2:
                    f = zzbfn.zzl(parcel, readInt);
                    break;
                case 1000:
                    i2 = zzbfn.zzg(parcel, readInt);
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        zzbfn.zzaf(parcel, zzd);
        return new MapValue(i2, i, f);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MapValue[i];
    }
}
