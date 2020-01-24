package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbfn;
import java.util.List;

public final class zzi implements Creator<SessionStopResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        Status status = null;
        int i = 0;
        List list = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    status = (Status) zzbfn.zza(parcel, readInt, Status.CREATOR);
                    break;
                case 3:
                    list = zzbfn.zzc(parcel, readInt, Session.CREATOR);
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
        return new SessionStopResult(i, status, list);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SessionStopResult[i];
    }
}
