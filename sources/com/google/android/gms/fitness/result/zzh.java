package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.zzae;
import com.google.android.gms.internal.zzbfn;
import java.util.List;

public final class zzh implements Creator<SessionReadResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Status status = null;
        int zzd = zzbfn.zzd(parcel);
        List list = null;
        int i = 0;
        List list2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    list = zzbfn.zzc(parcel, readInt, Session.CREATOR);
                    break;
                case 2:
                    list2 = zzbfn.zzc(parcel, readInt, zzae.CREATOR);
                    break;
                case 3:
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
        return new SessionReadResult(i, list, list2, status);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SessionReadResult[i];
    }
}
