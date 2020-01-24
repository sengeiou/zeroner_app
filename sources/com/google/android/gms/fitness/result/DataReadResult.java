package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataSource.Builder;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.RawBucket;
import com.google.android.gms.fitness.data.RawDataSet;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DataReadResult extends zzbfm implements Result {
    public static final Creator<DataReadResult> CREATOR = new zzc();
    private final Status mStatus;
    private final int zzeck;
    private final List<DataSet> zzhab;
    private final List<Bucket> zzhii;
    private int zzhij;
    private final List<DataSource> zzhik;
    private final List<DataType> zzhil;

    DataReadResult(int i, List<RawDataSet> list, Status status, List<RawBucket> list2, int i2, List<DataSource> list3, List<DataType> list4) {
        this.zzeck = i;
        this.mStatus = status;
        this.zzhij = i2;
        this.zzhik = list3;
        this.zzhil = list4;
        this.zzhab = new ArrayList(list.size());
        for (RawDataSet dataSet : list) {
            this.zzhab.add(new DataSet(dataSet, list3));
        }
        this.zzhii = new ArrayList(list2.size());
        for (RawBucket bucket : list2) {
            this.zzhii.add(new Bucket(bucket, list3));
        }
    }

    private DataReadResult(List<DataSet> list, List<Bucket> list2, Status status) {
        this.zzeck = 5;
        this.zzhab = list;
        this.mStatus = status;
        this.zzhii = list2;
        this.zzhij = 1;
        this.zzhik = new ArrayList();
        this.zzhil = new ArrayList();
    }

    public static DataReadResult zza(Status status, List<DataType> list, List<DataSource> list2) {
        ArrayList arrayList = new ArrayList();
        for (DataSource create : list2) {
            arrayList.add(DataSet.create(create));
        }
        for (DataType dataType : list) {
            arrayList.add(DataSet.create(new Builder().setDataType(dataType).setType(1).setName("Default").build()));
        }
        return new DataReadResult(arrayList, Collections.emptyList(), status);
    }

    private static void zza(DataSet dataSet, List<DataSet> list) {
        for (DataSet dataSet2 : list) {
            if (dataSet2.getDataSource().equals(dataSet.getDataSource())) {
                dataSet2.zzb((Iterable<DataPoint>) dataSet.getDataPoints());
                return;
            }
        }
        list.add(dataSet);
    }

    private List<RawBucket> zzaqy() {
        ArrayList arrayList = new ArrayList(this.zzhii.size());
        for (Bucket rawBucket : this.zzhii) {
            arrayList.add(new RawBucket(rawBucket, this.zzhik, this.zzhil));
        }
        return arrayList;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof DataReadResult)) {
                return false;
            }
            DataReadResult dataReadResult = (DataReadResult) obj;
            if (!(this.mStatus.equals(dataReadResult.mStatus) && zzbg.equal(this.zzhab, dataReadResult.zzhab) && zzbg.equal(this.zzhii, dataReadResult.zzhii))) {
                return false;
            }
        }
        return true;
    }

    public List<Bucket> getBuckets() {
        return this.zzhii;
    }

    public DataSet getDataSet(DataSource dataSource) {
        for (DataSet dataSet : this.zzhab) {
            if (dataSource.equals(dataSet.getDataSource())) {
                return dataSet;
            }
        }
        return DataSet.create(dataSource);
    }

    public DataSet getDataSet(DataType dataType) {
        for (DataSet dataSet : this.zzhab) {
            if (dataType.equals(dataSet.getDataType())) {
                return dataSet;
            }
        }
        return DataSet.create(new Builder().setDataType(dataType).setType(1).build());
    }

    public List<DataSet> getDataSets() {
        return this.zzhab;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mStatus, this.zzhab, this.zzhii});
    }

    public String toString() {
        Object obj;
        Object obj2;
        zzbi zzg = zzbg.zzx(this).zzg(NotificationCompat.CATEGORY_STATUS, this.mStatus);
        String str = "dataSets";
        if (this.zzhab.size() > 5) {
            obj = this.zzhab.size() + " data sets";
        } else {
            obj = this.zzhab;
        }
        zzbi zzg2 = zzg.zzg(str, obj);
        String str2 = "buckets";
        if (this.zzhii.size() > 5) {
            obj2 = this.zzhii.size() + " buckets";
        } else {
            obj2 = this.zzhii;
        }
        return zzg2.zzg(str2, obj2).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        ArrayList arrayList = new ArrayList(this.zzhab.size());
        for (DataSet rawDataSet : this.zzhab) {
            arrayList.add(new RawDataSet(rawDataSet, this.zzhik, this.zzhil));
        }
        zzbfp.zzd(parcel, 1, arrayList, false);
        zzbfp.zza(parcel, 2, (Parcelable) getStatus(), i, false);
        zzbfp.zzd(parcel, 3, zzaqy(), false);
        zzbfp.zzc(parcel, 5, this.zzhij);
        zzbfp.zzc(parcel, 6, this.zzhik, false);
        zzbfp.zzc(parcel, 7, this.zzhil, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }

    public final int zzaqx() {
        return this.zzhij;
    }

    public final void zzb(DataReadResult dataReadResult) {
        for (DataSet zza : dataReadResult.getDataSets()) {
            zza(zza, this.zzhab);
        }
        for (Bucket bucket : dataReadResult.getBuckets()) {
            Iterator it = this.zzhii.iterator();
            while (true) {
                if (!it.hasNext()) {
                    this.zzhii.add(bucket);
                    break;
                }
                Bucket bucket2 = (Bucket) it.next();
                if (bucket2.zza(bucket)) {
                    for (DataSet zza2 : bucket.getDataSets()) {
                        zza(zza2, bucket2.getDataSets());
                    }
                }
            }
        }
    }
}
