package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;

public final class zzn implements Creator<DataUpdateNotification> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long j = 0;
        DataType dataType = null;
        int i = 0;
        int zzd = zzbfn.zzd(parcel);
        DataSource dataSource = null;
        long j2 = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    j2 = zzbfn.zzi(parcel, readInt);
                    break;
                case 2:
                    j = zzbfn.zzi(parcel, readInt);
                    break;
                case 3:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 4:
                    dataSource = (DataSource) zzbfn.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 5:
                    dataType = (DataType) zzbfn.zza(parcel, readInt, DataType.CREATOR);
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
        return new DataUpdateNotification(i2, j2, j, i, dataSource, dataType);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataUpdateNotification[i];
    }
}
