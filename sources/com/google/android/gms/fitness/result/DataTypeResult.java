package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;

public class DataTypeResult extends zzbfm implements Result {
    public static final Creator<DataTypeResult> CREATOR = new zze();
    private final Status mStatus;
    private final int zzeck;
    private final DataType zzhbs;

    DataTypeResult(int i, Status status, DataType dataType) {
        this.zzeck = i;
        this.mStatus = status;
        this.zzhbs = dataType;
    }

    private DataTypeResult(Status status, DataType dataType) {
        this.zzeck = 2;
        this.mStatus = status;
        this.zzhbs = null;
    }

    public static DataTypeResult zzad(Status status) {
        return new DataTypeResult(status, null);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof DataTypeResult)) {
                return false;
            }
            DataTypeResult dataTypeResult = (DataTypeResult) obj;
            if (!(this.mStatus.equals(dataTypeResult.mStatus) && zzbg.equal(this.zzhbs, dataTypeResult.zzhbs))) {
                return false;
            }
        }
        return true;
    }

    public DataType getDataType() {
        return this.zzhbs;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mStatus, this.zzhbs});
    }

    public String toString() {
        return zzbg.zzx(this).zzg(NotificationCompat.CATEGORY_STATUS, this.mStatus).zzg("dataType", this.zzhbs).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) getStatus(), i, false);
        zzbfp.zza(parcel, 3, (Parcelable) getDataType(), i, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
