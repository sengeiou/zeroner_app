package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;

public final class zzaa implements Creator<RawDataSet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean z = false;
        int zzd = zzbfn.zzd(parcel);
        ArrayList arrayList = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i2 = zzbfn.zzg(parcel, readInt);
                    break;
                case 2:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 3:
                    arrayList = zzbfn.zzc(parcel, readInt, RawDataPoint.CREATOR);
                    break;
                case 4:
                    z = zzbfn.zzc(parcel, readInt);
                    break;
                case 1000:
                    i3 = zzbfn.zzg(parcel, readInt);
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        zzbfn.zzaf(parcel, zzd);
        return new RawDataSet(i3, i2, i, arrayList, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new RawDataSet[i];
    }
}
