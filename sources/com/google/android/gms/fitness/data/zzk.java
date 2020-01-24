package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;

public final class zzk implements Creator<DataSource> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int i = 0;
        int[] iArr = null;
        int zzd = zzbfn.zzd(parcel);
        String str = null;
        zzb zzb = null;
        Device device = null;
        String str2 = null;
        DataType dataType = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    dataType = (DataType) zzbfn.zza(parcel, readInt, DataType.CREATOR);
                    break;
                case 2:
                    str2 = zzbfn.zzq(parcel, readInt);
                    break;
                case 3:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 4:
                    device = (Device) zzbfn.zza(parcel, readInt, Device.CREATOR);
                    break;
                case 5:
                    zzb = (zzb) zzbfn.zza(parcel, readInt, zzb.CREATOR);
                    break;
                case 6:
                    str = zzbfn.zzq(parcel, readInt);
                    break;
                case 8:
                    iArr = zzbfn.zzw(parcel, readInt);
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
        return new DataSource(i2, dataType, str2, i, device, zzb, str, iArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataSource[i];
    }
}
