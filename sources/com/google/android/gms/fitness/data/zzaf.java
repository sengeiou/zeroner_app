package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;

public final class zzaf implements Creator<zzae> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        Session session = null;
        int i = 0;
        DataSet dataSet = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    session = (Session) zzbfn.zza(parcel, readInt, Session.CREATOR);
                    break;
                case 2:
                    dataSet = (DataSet) zzbfn.zza(parcel, readInt, DataSet.CREATOR);
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
        return new zzae(i, session, dataSet);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzae[i];
    }
}
