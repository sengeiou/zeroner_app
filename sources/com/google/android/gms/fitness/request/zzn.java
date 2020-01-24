package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;

public final class zzn implements Creator<DataReadRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        ArrayList arrayList = null;
        ArrayList arrayList2 = null;
        long j = 0;
        long j2 = 0;
        ArrayList arrayList3 = null;
        ArrayList arrayList4 = null;
        int i2 = 0;
        long j3 = 0;
        DataSource dataSource = null;
        int i3 = 0;
        boolean z = false;
        boolean z2 = false;
        IBinder iBinder = null;
        ArrayList arrayList5 = null;
        ArrayList arrayList6 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    arrayList = zzbfn.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 2:
                    arrayList2 = zzbfn.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 3:
                    j = zzbfn.zzi(parcel, readInt);
                    break;
                case 4:
                    j2 = zzbfn.zzi(parcel, readInt);
                    break;
                case 5:
                    arrayList3 = zzbfn.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 6:
                    arrayList4 = zzbfn.zzc(parcel, readInt, DataSource.CREATOR);
                    break;
                case 7:
                    i2 = zzbfn.zzg(parcel, readInt);
                    break;
                case 8:
                    j3 = zzbfn.zzi(parcel, readInt);
                    break;
                case 9:
                    dataSource = (DataSource) zzbfn.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 10:
                    i3 = zzbfn.zzg(parcel, readInt);
                    break;
                case 12:
                    z = zzbfn.zzc(parcel, readInt);
                    break;
                case 13:
                    z2 = zzbfn.zzc(parcel, readInt);
                    break;
                case 14:
                    iBinder = zzbfn.zzr(parcel, readInt);
                    break;
                case 16:
                    arrayList5 = zzbfn.zzc(parcel, readInt, Device.CREATOR);
                    break;
                case 17:
                    arrayList6 = zzbfn.zzab(parcel, readInt);
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
        return new DataReadRequest(i, arrayList, arrayList2, j, j2, arrayList3, arrayList4, i2, j3, dataSource, i3, z, z2, iBinder, arrayList5, arrayList6);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataReadRequest[i];
    }
}
