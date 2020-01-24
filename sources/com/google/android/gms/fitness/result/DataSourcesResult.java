package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataSourcesResult extends zzbfm implements Result {
    public static final Creator<DataSourcesResult> CREATOR = new zzd();
    private final int versionCode;
    private final Status zzead;
    private final List<DataSource> zzhim;

    DataSourcesResult(int i, List<DataSource> list, Status status) {
        this.versionCode = i;
        this.zzhim = Collections.unmodifiableList(list);
        this.zzead = status;
    }

    public DataSourcesResult(List<DataSource> list, Status status) {
        this.versionCode = 3;
        this.zzhim = Collections.unmodifiableList(list);
        this.zzead = status;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof DataSourcesResult)) {
                return false;
            }
            DataSourcesResult dataSourcesResult = (DataSourcesResult) obj;
            if (!(this.zzead.equals(dataSourcesResult.zzead) && zzbg.equal(this.zzhim, dataSourcesResult.zzhim))) {
                return false;
            }
        }
        return true;
    }

    public List<DataSource> getDataSources() {
        return this.zzhim;
    }

    public List<DataSource> getDataSources(DataType dataType) {
        ArrayList arrayList = new ArrayList();
        for (DataSource dataSource : this.zzhim) {
            if (dataSource.getDataType().equals(dataType)) {
                arrayList.add(dataSource);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Status getStatus() {
        return this.zzead;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzead, this.zzhim});
    }

    public String toString() {
        return zzbg.zzx(this).zzg(NotificationCompat.CATEGORY_STATUS, this.zzead).zzg("dataSources", this.zzhim).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, getDataSources(), false);
        zzbfp.zza(parcel, 2, (Parcelable) getStatus(), i, false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, zze);
    }
}
