package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbfn;
import java.util.List;

public final class zzj implements Creator<DataDeleteRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long j = 0;
        boolean z = false;
        IBinder iBinder = null;
        int zzd = zzbfn.zzd(parcel);
        boolean z2 = false;
        List list = null;
        List list2 = null;
        List list3 = null;
        long j2 = 0;
        int i = 0;
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
                    list3 = zzbfn.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 4:
                    list2 = zzbfn.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 5:
                    list = zzbfn.zzc(parcel, readInt, Session.CREATOR);
                    break;
                case 6:
                    z2 = zzbfn.zzc(parcel, readInt);
                    break;
                case 7:
                    z = zzbfn.zzc(parcel, readInt);
                    break;
                case 8:
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
        return new DataDeleteRequest(i, j2, j, list3, list2, list, z2, z, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataDeleteRequest[i];
    }
}
