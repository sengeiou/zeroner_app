package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.Goal;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.List;

public class GoalsResult extends zzbfm implements Result {
    public static final Creator<GoalsResult> CREATOR = new zzf();
    private final int versionCode;
    private final Status zzead;
    private final List<Goal> zzhin;

    GoalsResult(int i, Status status, List<Goal> list) {
        this.versionCode = i;
        this.zzead = status;
        this.zzhin = list;
    }

    public GoalsResult(Status status, List<Goal> list) {
        this(1, status, list);
    }

    public List<Goal> getGoals() {
        return this.zzhin;
    }

    public Status getStatus() {
        return this.zzead;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) getStatus(), i, false);
        zzbfp.zzc(parcel, 2, getGoals(), false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, zze);
    }
}
