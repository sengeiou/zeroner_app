package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zza;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbwz;
import com.google.android.gms.internal.zzbxa;
import java.util.Arrays;
import java.util.List;

public class DataSourcesRequest extends zzbfm {
    public static final Creator<DataSourcesRequest> CREATOR = new zzp();
    private final int zzeck;
    private final List<DataType> zzgzy;
    private final List<Integer> zzhgu;
    private final boolean zzhgv;
    private final zzbwz zzhgw;

    public static class Builder {
        private boolean zzhgv = false;
        /* access modifiers changed from: private */
        public DataType[] zzhgx = new DataType[0];
        /* access modifiers changed from: private */
        public int[] zzhgy = {0, 1};

        public DataSourcesRequest build() {
            boolean z = true;
            zzbq.zza(this.zzhgx.length > 0, (Object) "Must add at least one data type");
            if (this.zzhgy.length <= 0) {
                z = false;
            }
            zzbq.zza(z, (Object) "Must add at least one data source type");
            return new DataSourcesRequest(this);
        }

        public Builder setDataSourceTypes(int... iArr) {
            this.zzhgy = iArr;
            return this;
        }

        public Builder setDataTypes(DataType... dataTypeArr) {
            this.zzhgx = dataTypeArr;
            return this;
        }
    }

    DataSourcesRequest(int i, List<DataType> list, List<Integer> list2, boolean z, IBinder iBinder) {
        this.zzeck = i;
        this.zzgzy = list;
        this.zzhgu = list2;
        this.zzhgv = z;
        this.zzhgw = zzbxa.zzau(iBinder);
    }

    private DataSourcesRequest(Builder builder) {
        this(zza.zza((T[]) builder.zzhgx), Arrays.asList(zza.zzb(builder.zzhgy)), false, null);
    }

    public DataSourcesRequest(DataSourcesRequest dataSourcesRequest, zzbwz zzbwz) {
        this(dataSourcesRequest.zzgzy, dataSourcesRequest.zzhgu, dataSourcesRequest.zzhgv, zzbwz);
    }

    private DataSourcesRequest(List<DataType> list, List<Integer> list2, boolean z, zzbwz zzbwz) {
        this.zzeck = 4;
        this.zzgzy = list;
        this.zzhgu = list2;
        this.zzhgv = z;
        this.zzhgw = zzbwz;
    }

    public List<DataType> getDataTypes() {
        return this.zzgzy;
    }

    public String toString() {
        zzbi zzg = zzbg.zzx(this).zzg("dataTypes", this.zzgzy).zzg("sourceTypes", this.zzhgu);
        if (this.zzhgv) {
            zzg.zzg("includeDbOnlySources", "true");
        }
        return zzg.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, getDataTypes(), false);
        zzbfp.zza(parcel, 2, this.zzhgu, false);
        zzbfp.zza(parcel, 3, this.zzhgv);
        zzbfp.zza(parcel, 4, this.zzhgw == null ? null : this.zzhgw.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
