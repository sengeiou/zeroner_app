package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.Goal.DurationObjective;
import com.google.android.gms.fitness.data.Goal.FrequencyObjective;
import com.google.android.gms.fitness.data.Goal.MetricObjective;
import com.google.android.gms.fitness.data.Goal.Recurrence;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzs implements Creator<Goal> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long j = 0;
        int i = 0;
        FrequencyObjective frequencyObjective = null;
        int zzd = zzbfn.zzd(parcel);
        ArrayList arrayList = new ArrayList();
        DurationObjective durationObjective = null;
        MetricObjective metricObjective = null;
        Recurrence recurrence = null;
        long j2 = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    j2 = zzbfn.zzi(parcel, readInt);
                    break;
                case 2:
                    j = zzbfn.zzi(parcel, readInt);
                    break;
                case 3:
                    zzbfn.zza(parcel, readInt, (List) arrayList, getClass().getClassLoader());
                    break;
                case 4:
                    recurrence = (Recurrence) zzbfn.zza(parcel, readInt, Recurrence.CREATOR);
                    break;
                case 5:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 6:
                    metricObjective = (MetricObjective) zzbfn.zza(parcel, readInt, MetricObjective.CREATOR);
                    break;
                case 7:
                    durationObjective = (DurationObjective) zzbfn.zza(parcel, readInt, DurationObjective.CREATOR);
                    break;
                case 8:
                    frequencyObjective = (FrequencyObjective) zzbfn.zza(parcel, readInt, FrequencyObjective.CREATOR);
                    break;
                case 1000:
                    i2 = zzbfn.zzg(parcel, readInt);
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        zzbfn.zzaf(parcel, zzd);
        return new Goal(i2, j2, j, arrayList, recurrence, i, metricObjective, durationObjective, frequencyObjective);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Goal[i];
    }
}
