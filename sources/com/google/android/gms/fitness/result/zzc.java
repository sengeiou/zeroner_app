package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzc implements Creator<DataReadResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int i = 0;
        ArrayList arrayList = null;
        int zzd = zzbfn.zzd(parcel);
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        List list = null;
        Status status = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    zzbfn.zza(parcel, readInt, (List) arrayList2, getClass().getClassLoader());
                    break;
                case 2:
                    status = (Status) zzbfn.zza(parcel, readInt, Status.CREATOR);
                    break;
                case 3:
                    zzbfn.zza(parcel, readInt, (List) arrayList3, getClass().getClassLoader());
                    break;
                case 5:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 6:
                    list = zzbfn.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 7:
                    arrayList = zzbfn.zzc(parcel, readInt, DataType.CREATOR);
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
        return new DataReadResult(i2, arrayList2, status, arrayList3, i, list, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataReadResult[i];
    }
}
