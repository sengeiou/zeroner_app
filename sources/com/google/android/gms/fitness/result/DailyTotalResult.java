package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource.Builder;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;

public class DailyTotalResult extends zzbfm implements Result {
    public static final Creator<DailyTotalResult> CREATOR = new zzb();
    private final Status mStatus;
    private final int zzeck;
    private final DataSet zzhdx;

    DailyTotalResult(int i, Status status, DataSet dataSet) {
        this.zzeck = i;
        this.mStatus = status;
        this.zzhdx = dataSet;
    }

    private DailyTotalResult(DataSet dataSet, Status status) {
        this.zzeck = 1;
        this.mStatus = status;
        this.zzhdx = dataSet;
    }

    public static DailyTotalResult zza(Status status, DataType dataType) {
        return new DailyTotalResult(DataSet.create(new Builder().setDataType(dataType).setType(1).build()), status);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof DailyTotalResult)) {
                return false;
            }
            DailyTotalResult dailyTotalResult = (DailyTotalResult) obj;
            if (!(this.mStatus.equals(dailyTotalResult.mStatus) && zzbg.equal(this.zzhdx, dailyTotalResult.zzhdx))) {
                return false;
            }
        }
        return true;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    @Nullable
    public DataSet getTotal() {
        return this.zzhdx;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mStatus, this.zzhdx});
    }

    public String toString() {
        return zzbg.zzx(this).zzg(NotificationCompat.CATEGORY_STATUS, this.mStatus).zzg("dataPoint", this.zzhdx).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) getStatus(), i, false);
        zzbfp.zza(parcel, 2, (Parcelable) getTotal(), i, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
