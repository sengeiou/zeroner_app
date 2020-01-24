package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;

public final class zzh implements Creator<DataPoint> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        DataSource dataSource = null;
        long j = 0;
        long j2 = 0;
        Value[] valueArr = null;
        DataSource dataSource2 = null;
        long j3 = 0;
        long j4 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    dataSource = (DataSource) zzbfn.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 3:
                    j = zzbfn.zzi(parcel, readInt);
                    break;
                case 4:
                    j2 = zzbfn.zzi(parcel, readInt);
                    break;
                case 5:
                    valueArr = (Value[]) zzbfn.zzb(parcel, readInt, Value.CREATOR);
                    break;
                case 6:
                    dataSource2 = (DataSource) zzbfn.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 7:
                    j3 = zzbfn.zzi(parcel, readInt);
                    break;
                case 8:
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
        return new DataPoint(i, dataSource, j, j2, valueArr, dataSource2, j3, j4);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataPoint[i];
    }
}
