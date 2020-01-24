package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.internal.zzbfn;

public final class zzbk implements Creator<zzbj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        boolean z = false;
        Subscription subscription = null;
        int i = 0;
        IBinder iBinder = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    subscription = (Subscription) zzbfn.zza(parcel, readInt, Subscription.CREATOR);
                    break;
                case 2:
                    z = zzbfn.zzc(parcel, readInt);
                    break;
                case 3:
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
        return new zzbj(i, subscription, z, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbj[i];
    }
}
