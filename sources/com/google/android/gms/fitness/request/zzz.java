package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.internal.zzbfn;

public final class zzz implements Creator<DataUpdateRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long j = 0;
        IBinder iBinder = null;
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        DataSet dataSet = null;
        long j2 = 0;
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
                    dataSet = (DataSet) zzbfn.zza(parcel, readInt, DataSet.CREATOR);
                    break;
                case 4:
                    iBinder = zzbfn.zzr(parcel, readInt);
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
        return new DataUpdateRequest(i, j2, j, dataSet, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataUpdateRequest[i];
    }
}
