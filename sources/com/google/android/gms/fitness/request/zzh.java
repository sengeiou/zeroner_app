package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfn;

public final class zzh implements Creator<zzg> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        DataType dataType = null;
        IBinder iBinder = null;
        int i = 0;
        boolean z = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    iBinder = zzbfn.zzr(parcel, readInt);
                    break;
                case 2:
                    dataType = (DataType) zzbfn.zza(parcel, readInt, DataType.CREATOR);
                    break;
                case 4:
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
        return new zzg(i, iBinder, dataType, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzg[i];
    }
}
