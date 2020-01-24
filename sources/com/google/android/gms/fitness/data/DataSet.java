package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbuy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class DataSet extends zzbfm implements ReflectedParcelable {
    public static final Creator<DataSet> CREATOR = new zzi();
    private final int versionCode;
    private final DataType zzgzg;
    private final DataSource zzgzh;
    private final List<DataPoint> zzham;
    private final List<DataSource> zzhan;
    private boolean zzhao;

    DataSet(int i, DataSource dataSource, DataType dataType, List<RawDataPoint> list, List<DataSource> list2, boolean z) {
        this.zzhao = false;
        this.versionCode = i;
        this.zzgzh = dataSource;
        this.zzgzg = dataSource.getDataType();
        this.zzhao = z;
        this.zzham = new ArrayList(list.size());
        if (i < 2) {
            list2 = Collections.singletonList(dataSource);
        }
        this.zzhan = list2;
        for (RawDataPoint dataPoint : list) {
            this.zzham.add(new DataPoint(this.zzhan, dataPoint));
        }
    }

    private DataSet(DataSource dataSource) {
        this.zzhao = false;
        this.versionCode = 3;
        this.zzgzh = (DataSource) zzbq.checkNotNull(dataSource);
        this.zzgzg = dataSource.getDataType();
        this.zzham = new ArrayList();
        this.zzhan = new ArrayList();
        this.zzhan.add(this.zzgzh);
    }

    public DataSet(RawDataSet rawDataSet, List<DataSource> list) {
        this.zzhao = false;
        this.versionCode = 3;
        int i = rawDataSet.zzhdo;
        this.zzgzh = (DataSource) ((i < 0 || i >= list.size()) ? null : list.get(i));
        this.zzgzg = this.zzgzh.getDataType();
        this.zzhan = list;
        this.zzhao = rawDataSet.zzhad;
        List<RawDataPoint> list2 = rawDataSet.zzhdt;
        this.zzham = new ArrayList(list2.size());
        for (RawDataPoint dataPoint : list2) {
            this.zzham.add(new DataPoint(this.zzhan, dataPoint));
        }
    }

    public static DataSet create(DataSource dataSource) {
        zzbq.checkNotNull(dataSource, "DataSource should be specified");
        return new DataSet(dataSource);
    }

    private final void zza(DataPoint dataPoint) {
        this.zzham.add(dataPoint);
        DataSource originalDataSource = dataPoint.getOriginalDataSource();
        if (originalDataSource != null && !this.zzhan.contains(originalDataSource)) {
            this.zzhan.add(originalDataSource);
        }
    }

    private List<RawDataPoint> zzaqk() {
        return zzab(this.zzhan);
    }

    public static void zzb(DataPoint dataPoint) throws IllegalArgumentException {
        String zza = zzbuy.zza(dataPoint, zzf.zzhae);
        if (zza != null) {
            String valueOf = String.valueOf(dataPoint);
            Log.w("Fitness", new StringBuilder(String.valueOf(valueOf).length() + 20).append("Invalid data point: ").append(valueOf).toString());
            throw new IllegalArgumentException(zza);
        }
    }

    public final void add(DataPoint dataPoint) {
        DataSource dataSource = dataPoint.getDataSource();
        zzbq.zzb(dataSource.getStreamIdentifier().equals(this.zzgzh.getStreamIdentifier()), "Conflicting data sources found %s vs %s", dataSource, this.zzgzh);
        dataPoint.zzaqj();
        zzb(dataPoint);
        zza(dataPoint);
    }

    public final void addAll(Iterable<DataPoint> iterable) {
        for (DataPoint add : iterable) {
            add(add);
        }
    }

    public final DataPoint createDataPoint() {
        return DataPoint.create(this.zzgzh);
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof DataSet)) {
                return false;
            }
            DataSet dataSet = (DataSet) obj;
            if (!(zzbg.equal(getDataType(), dataSet.getDataType()) && zzbg.equal(this.zzgzh, dataSet.zzgzh) && zzbg.equal(this.zzham, dataSet.zzham) && this.zzhao == dataSet.zzhao)) {
                return false;
            }
        }
        return true;
    }

    public final List<DataPoint> getDataPoints() {
        return Collections.unmodifiableList(this.zzham);
    }

    public final DataSource getDataSource() {
        return this.zzgzh;
    }

    public final DataType getDataType() {
        return this.zzgzh.getDataType();
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzgzh});
    }

    public final boolean isEmpty() {
        return this.zzham.isEmpty();
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String toString() {
        /*
            r8 = this;
            r5 = 2
            r7 = 1
            r6 = 0
            java.util.List r0 = r8.zzaqk()
            java.lang.String r1 = "DataSet{%s %s}"
            java.lang.Object[] r2 = new java.lang.Object[r5]
            com.google.android.gms.fitness.data.DataSource r3 = r8.zzgzh
            java.lang.String r3 = r3.toDebugString()
            r2[r6] = r3
            java.util.List<com.google.android.gms.fitness.data.DataPoint> r3 = r8.zzham
            int r3 = r3.size()
            r4 = 10
            if (r3 >= r4) goto L_0x0025
        L_0x001e:
            r2[r7] = r0
            java.lang.String r0 = java.lang.String.format(r1, r2)
            return r0
        L_0x0025:
            java.lang.String r3 = "%d data points, first 5: %s"
            java.lang.Object[] r4 = new java.lang.Object[r5]
            java.util.List<com.google.android.gms.fitness.data.DataPoint> r5 = r8.zzham
            int r5 = r5.size()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r6] = r5
            r5 = 5
            java.util.List r0 = r0.subList(r6, r5)
            r4[r7] = r0
            java.lang.String r0 = java.lang.String.format(r3, r4)
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.fitness.data.DataSet.toString():java.lang.String");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) getDataSource(), i, false);
        zzbfp.zza(parcel, 2, (Parcelable) getDataType(), i, false);
        zzbfp.zzd(parcel, 3, zzaqk(), false);
        zzbfp.zzc(parcel, 4, this.zzhan, false);
        zzbfp.zza(parcel, 5, this.zzhao);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, zze);
    }

    /* access modifiers changed from: 0000 */
    public final List<RawDataPoint> zzab(List<DataSource> list) {
        ArrayList arrayList = new ArrayList(this.zzham.size());
        for (DataPoint rawDataPoint : this.zzham) {
            arrayList.add(new RawDataPoint(rawDataPoint, list));
        }
        return arrayList;
    }

    public final boolean zzaqd() {
        return this.zzhao;
    }

    public final void zzb(Iterable<DataPoint> iterable) {
        for (DataPoint zza : iterable) {
            zza(zza);
        }
    }
}
