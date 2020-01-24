package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.internal.zzbfn;
import java.util.List;

public final class zzg implements Creator<ListSubscriptionsResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Status status = null;
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        List list = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    list = zzbfn.zzc(parcel, readInt, Subscription.CREATOR);
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
        return new ListSubscriptionsResult(i, list, status);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new ListSubscriptionsResult[i];
    }
}
