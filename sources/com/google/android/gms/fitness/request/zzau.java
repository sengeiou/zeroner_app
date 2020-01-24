package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbfn;
import java.util.List;

public final class zzau implements Creator<SessionInsertRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        List list = null;
        List list2 = null;
        Session session = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    session = (Session) zzbfn.zza(parcel, readInt, Session.CREATOR);
                    break;
                case 2:
                    list2 = zzbfn.zzc(parcel, readInt, DataSet.CREATOR);
                    break;
                case 3:
                    list = zzbfn.zzc(parcel, readInt, DataPoint.CREATOR);
                    break;
                case 4:
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
        return new SessionInsertRequest(i, session, list2, list, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SessionInsertRequest[i];
    }
}
