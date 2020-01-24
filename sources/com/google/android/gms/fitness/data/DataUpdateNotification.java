package com.google.android.gms.fitness.data;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbfr;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class DataUpdateNotification extends zzbfm {
    public static final String ACTION = "com.google.android.gms.fitness.DATA_UPDATE_NOTIFICATION";
    public static final Creator<DataUpdateNotification> CREATOR = new zzn();
    public static final String EXTRA_DATA_UPDATE_NOTIFICATION = "vnd.google.fitness.data_udpate_notification";
    public static final int OPERATION_DELETE = 2;
    public static final int OPERATION_INSERT = 1;
    public static final int OPERATION_UPDATE = 3;
    private int zzeck;
    private final long zzhbo;
    private final long zzhbp;
    private final int zzhbq;
    private final DataSource zzhbr;
    private final DataType zzhbs;

    DataUpdateNotification(int i, long j, long j2, int i2, DataSource dataSource, DataType dataType) {
        this.zzeck = i;
        this.zzhbo = j;
        this.zzhbp = j2;
        this.zzhbq = i2;
        this.zzhbr = dataSource;
        this.zzhbs = dataType;
    }

    public static DataUpdateNotification getDataUpdateNotification(Intent intent) {
        return (DataUpdateNotification) zzbfr.zza(intent, EXTRA_DATA_UPDATE_NOTIFICATION, CREATOR);
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof DataUpdateNotification)) {
                return false;
            }
            DataUpdateNotification dataUpdateNotification = (DataUpdateNotification) obj;
            if (!(this.zzhbo == dataUpdateNotification.zzhbo && this.zzhbp == dataUpdateNotification.zzhbp && this.zzhbq == dataUpdateNotification.zzhbq && zzbg.equal(this.zzhbr, dataUpdateNotification.zzhbr) && zzbg.equal(this.zzhbs, dataUpdateNotification.zzhbs))) {
                return false;
            }
        }
        return true;
    }

    public DataSource getDataSource() {
        return this.zzhbr;
    }

    public DataType getDataType() {
        return this.zzhbs;
    }

    public int getOperationType() {
        return this.zzhbq;
    }

    public long getUpdateEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhbp, TimeUnit.NANOSECONDS);
    }

    public long getUpdateStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhbo, TimeUnit.NANOSECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzhbo), Long.valueOf(this.zzhbp), Integer.valueOf(this.zzhbq), this.zzhbr, this.zzhbs});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("updateStartTimeNanos", Long.valueOf(this.zzhbo)).zzg("updateEndTimeNanos", Long.valueOf(this.zzhbp)).zzg("operationType", Integer.valueOf(this.zzhbq)).zzg("dataSource", this.zzhbr).zzg("dataType", this.zzhbs).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhbo);
        zzbfp.zza(parcel, 2, this.zzhbp);
        zzbfp.zzc(parcel, 3, getOperationType());
        zzbfp.zza(parcel, 4, (Parcelable) getDataSource(), i, false);
        zzbfp.zza(parcel, 5, (Parcelable) getDataType(), i, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
