package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzd implements Creator<BleDevice> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        ArrayList arrayList = null;
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        List list = null;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str2 = zzbfn.zzq(parcel, readInt);
                    break;
                case 2:
                    str = zzbfn.zzq(parcel, readInt);
                    break;
                case 3:
                    list = zzbfn.zzac(parcel, readInt);
                    break;
                case 4:
                    arrayList = zzbfn.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 1000:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        zzbfn.zzaf(parcel, zzd);
        return new BleDevice(i, str2, str, list, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new BleDevice[i];
    }
}
