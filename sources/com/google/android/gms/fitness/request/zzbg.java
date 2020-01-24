package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfn;
import java.util.List;

public final class zzbg implements Creator<StartBleScanRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int i = 0;
        IBinder iBinder = null;
        int zzd = zzbfn.zzd(parcel);
        IBinder iBinder2 = null;
        List list = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    list = zzbfn.zzc(parcel, readInt, DataType.CREATOR);
                    break;
                case 2:
                    iBinder2 = zzbfn.zzr(parcel, readInt);
                    break;
                case 3:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 4:
                    iBinder = zzbfn.zzr(parcel, readInt);
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
        return new StartBleScanRequest(i2, list, iBinder2, i, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new StartBleScanRequest[i];
    }
}
