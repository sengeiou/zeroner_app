package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;

public final class zzz implements Creator<RawDataPoint> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        long j = 0;
        long j2 = 0;
        Value[] valueArr = null;
        int i2 = 0;
        int i3 = 0;
        long j3 = 0;
        long j4 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    j = zzbfn.zzi(parcel, readInt);
                    break;
                case 2:
                    j2 = zzbfn.zzi(parcel, readInt);
                    break;
                case 3:
                    valueArr = (Value[]) zzbfn.zzb(parcel, readInt, Value.CREATOR);
                    break;
                case 4:
                    i2 = zzbfn.zzg(parcel, readInt);
                    break;
                case 5:
                    i3 = zzbfn.zzg(parcel, readInt);
                    break;
                case 6:
                    j3 = zzbfn.zzi(parcel, readInt);
                    break;
                case 7:
                    j4 = zzbfn.zzi(parcel, readInt);
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
        return new RawDataPoint(i, j, j2, valueArr, i2, i3, j3, j4);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new RawDataPoint[i];
    }
}
