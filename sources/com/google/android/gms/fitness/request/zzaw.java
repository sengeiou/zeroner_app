package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;

public final class zzaw implements Creator<SessionReadRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        long j = 0;
        long j2 = 0;
        ArrayList arrayList = null;
        ArrayList arrayList2 = null;
        boolean z = false;
        boolean z2 = false;
        ArrayList arrayList3 = null;
        IBinder iBinder = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzbfn.zzq(parcel, readInt);
                    break;
                case 2:
                    str2 = zzbfn.zzq(parcel, readInt);
                    break;
                case 3:
                    j = zzbfn.zzi(parcel, readInt);
                    break;
                case 4:
                    j2 = zzbfn.zzi(parcel, readInt);
                    break;
                case 5:
                    arrayList = zzbfn.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 6:
                    arrayList2 = zzbfn.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 7:
                    z = zzbfn.zzc(parcel, readInt);
                    break;
                case 8:
                    z2 = zzbfn.zzc(parcel, readInt);
                    break;
                case 9:
                    arrayList3 = zzbfn.zzac(parcel, readInt);
                    break;
                case 10:
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
        return new SessionReadRequest(i, str, str2, j, j2, arrayList, arrayList2, z, z2, arrayList3, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SessionReadRequest[i];
    }
}
