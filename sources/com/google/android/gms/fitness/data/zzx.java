package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.fitness.data.Goal.MetricObjective;
import com.google.android.gms.internal.zzbfn;

public final class zzx implements Creator<MetricObjective> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        double d = Utils.DOUBLE_EPSILON;
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        String str = null;
        double d2 = 0.0d;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzbfn.zzq(parcel, readInt);
                    break;
                case 2:
                    d2 = zzbfn.zzn(parcel, readInt);
                    break;
                case 3:
                    d = zzbfn.zzn(parcel, readInt);
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
        return new MetricObjective(i, str, d2, d);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MetricObjective[i];
    }
}
