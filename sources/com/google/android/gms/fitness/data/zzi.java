package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzi implements Creator<DataSet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean z = false;
        ArrayList arrayList = null;
        int zzd = zzbfn.zzd(parcel);
        ArrayList arrayList2 = new ArrayList();
        DataType dataType = null;
        DataSource dataSource = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    dataSource = (DataSource) zzbfn.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 2:
                    dataType = (DataType) zzbfn.zza(parcel, readInt, DataType.CREATOR);
                    break;
                case 3:
                    zzbfn.zza(parcel, readInt, (List) arrayList2, getClass().getClassLoader());
                    break;
                case 4:
                    arrayList = zzbfn.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 5:
                    z = zzbfn.zzc(parcel, readInt);
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
        return new DataSet(i, dataSource, dataType, arrayList2, arrayList, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataSet[i];
    }
}
