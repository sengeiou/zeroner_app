package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzcea implements Creator<zzcdz> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        IBinder iBinder = null;
        zzcdx zzcdx = null;
        int i = 1;
        IBinder iBinder2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 2:
                    zzcdx = (zzcdx) zzbfn.zza(parcel, readInt, zzcdx.CREATOR);
                    break;
                case 3:
                    iBinder = zzbfn.zzr(parcel, readInt);
                    break;
                case 4:
                    iBinder2 = zzbfn.zzr(parcel, readInt);
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        zzbfn.zzaf(parcel, zzd);
        return new zzcdz(i, zzcdx, iBinder, iBinder2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcdz[i];
    }
}
