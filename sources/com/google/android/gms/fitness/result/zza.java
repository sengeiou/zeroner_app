package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.internal.zzbfn;
import java.util.List;

public final class zza implements Creator<BleDevicesResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Status status = null;
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        List list = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    list = zzbfn.zzc(parcel, readInt, BleDevice.CREATOR);
                    break;
                case 2:
                    status = (Status) zzbfn.zza(parcel, readInt, Status.CREATOR);
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
        return new BleDevicesResult(i, list, status);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new BleDevicesResult[i];
    }
}
