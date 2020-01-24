package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbfn;

public final class zzba implements Creator<zzaz> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        Session session = null;
        int i = 0;
        IBinder iBinder = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    session = (Session) zzbfn.zza(parcel, readInt, Session.CREATOR);
                    break;
                case 2:
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
        return new zzaz(i, session, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzaz[i];
    }
}
