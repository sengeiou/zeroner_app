package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfn;
import java.util.List;

public final class zzp implements Creator<DataSourcesRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean z = false;
        IBinder iBinder = null;
        int zzd = zzbfn.zzd(parcel);
        List list = null;
        List list2 = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    list2 = zzbfn.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 2:
                    list = zzbfn.zzab(parcel, readInt);
                    break;
                case 3:
                    z = zzbfn.zzc(parcel, readInt);
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
        return new DataSourcesRequest(i, list2, list, z, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataSourcesRequest[i];
    }
}
