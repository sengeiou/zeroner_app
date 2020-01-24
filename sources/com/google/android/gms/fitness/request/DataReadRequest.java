package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbww;
import com.google.android.gms.internal.zzbwx;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataReadRequest extends zzbfm {
    public static final Creator<DataReadRequest> CREATOR = new zzn();
    public static final int NO_LIMIT = 0;
    private final long zzdvq;
    private final int zzeck;
    private final List<DataType> zzgzy;
    private final long zzgzz;
    private final int zzhac;
    private final List<DataSource> zzhgf;
    private final List<DataType> zzhgk;
    private final List<DataSource> zzhgl;
    private final long zzhgm;
    private final DataSource zzhgn;
    private final int zzhgo;
    private final boolean zzhgp;
    private final boolean zzhgq;
    private final zzbww zzhgr;
    private final List<Device> zzhgs;
    private final List<Integer> zzhgt;

    public static class Builder {
        /* access modifiers changed from: private */
        public long zzdvq;
        /* access modifiers changed from: private */
        public List<DataType> zzgzy = new ArrayList();
        /* access modifiers changed from: private */
        public long zzgzz;
        /* access modifiers changed from: private */
        public int zzhac = 0;
        /* access modifiers changed from: private */
        public List<DataSource> zzhgf = new ArrayList();
        /* access modifiers changed from: private */
        public List<DataType> zzhgk = new ArrayList();
        /* access modifiers changed from: private */
        public List<DataSource> zzhgl = new ArrayList();
        /* access modifiers changed from: private */
        public long zzhgm = 0;
        /* access modifiers changed from: private */
        public DataSource zzhgn;
        /* access modifiers changed from: private */
        public int zzhgo = 0;
        private boolean zzhgp = false;
        /* access modifiers changed from: private */
        public boolean zzhgq = false;
        /* access modifiers changed from: private */
        public final List<Device> zzhgs = new ArrayList();
        /* access modifiers changed from: private */
        public final List<Integer> zzhgt = new ArrayList();

        public Builder addFilteredDataQualityStandard(int i) {
            zzbq.checkArgument(this.zzhgs.isEmpty(), "Cannot add data quality standard filter when filtering by device.");
            this.zzhgt.add(Integer.valueOf(i));
            return this;
        }

        public Builder aggregate(DataSource dataSource, DataType dataType) {
            zzbq.checkNotNull(dataSource, "Attempting to add a null data source");
            zzbq.zza(!this.zzhgf.contains(dataSource), (Object) "Cannot add the same data source for aggregated and detailed");
            DataType dataType2 = dataSource.getDataType();
            List aggregatesForInput = DataType.getAggregatesForInput(dataType2);
            zzbq.zzb(!aggregatesForInput.isEmpty(), "Unsupported input data type specified for aggregation: %s", dataType2);
            zzbq.zzb(aggregatesForInput.contains(dataType), "Invalid output aggregate data type specified: %s -> %s", dataType2, dataType);
            if (!this.zzhgl.contains(dataSource)) {
                this.zzhgl.add(dataSource);
            }
            return this;
        }

        public Builder aggregate(DataType dataType, DataType dataType2) {
            zzbq.checkNotNull(dataType, "Attempting to use a null data type");
            zzbq.zza(!this.zzgzy.contains(dataType), (Object) "Cannot add the same data type as aggregated and detailed");
            List aggregatesForInput = DataType.getAggregatesForInput(dataType);
            zzbq.zzb(!aggregatesForInput.isEmpty(), "Unsupported input data type specified for aggregation: %s", dataType);
            zzbq.zzb(aggregatesForInput.contains(dataType2), "Invalid output aggregate data type specified: %s -> %s", dataType, dataType2);
            if (!this.zzhgk.contains(dataType)) {
                this.zzhgk.add(dataType);
            }
            return this;
        }

        public Builder bucketByActivitySegment(int i, TimeUnit timeUnit) {
            zzbq.zzb(this.zzhac == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzhac));
            zzbq.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzhac = 4;
            this.zzhgm = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByActivitySegment(int i, TimeUnit timeUnit, DataSource dataSource) {
            zzbq.zzb(this.zzhac == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzhac));
            zzbq.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            zzbq.checkArgument(dataSource != null, "Invalid activity data source specified");
            zzbq.zzb(dataSource.getDataType().equals(DataType.TYPE_ACTIVITY_SEGMENT), "Invalid activity data source specified: %s", dataSource);
            this.zzhgn = dataSource;
            this.zzhac = 4;
            this.zzhgm = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByActivityType(int i, TimeUnit timeUnit) {
            zzbq.zzb(this.zzhac == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzhac));
            zzbq.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzhac = 3;
            this.zzhgm = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByActivityType(int i, TimeUnit timeUnit, DataSource dataSource) {
            zzbq.zzb(this.zzhac == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzhac));
            zzbq.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            zzbq.checkArgument(dataSource != null, "Invalid activity data source specified");
            zzbq.zzb(dataSource.getDataType().equals(DataType.TYPE_ACTIVITY_SEGMENT), "Invalid activity data source specified: %s", dataSource);
            this.zzhgn = dataSource;
            this.zzhac = 3;
            this.zzhgm = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketBySession(int i, TimeUnit timeUnit) {
            zzbq.zzb(this.zzhac == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzhac));
            zzbq.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzhac = 2;
            this.zzhgm = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByTime(int i, TimeUnit timeUnit) {
            zzbq.zzb(this.zzhac == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzhac));
            zzbq.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzhac = 1;
            this.zzhgm = timeUnit.toMillis((long) i);
            return this;
        }

        public DataReadRequest build() {
            boolean z = true;
            zzbq.zza(!this.zzhgf.isEmpty() || !this.zzgzy.isEmpty() || !this.zzhgl.isEmpty() || !this.zzhgk.isEmpty(), (Object) "Must add at least one data source (aggregated or detailed)");
            zzbq.zza(this.zzdvq > 0, "Invalid start time: %s", Long.valueOf(this.zzdvq));
            zzbq.zza(this.zzgzz > 0 && this.zzgzz > this.zzdvq, "Invalid end time: %s", Long.valueOf(this.zzgzz));
            boolean z2 = this.zzhgl.isEmpty() && this.zzhgk.isEmpty();
            if ((!z2 || this.zzhac != 0) && (z2 || this.zzhac == 0)) {
                z = false;
            }
            zzbq.zza(z, (Object) "Must specify a valid bucketing strategy while requesting aggregation");
            return new DataReadRequest(this);
        }

        public Builder enableServerQueries() {
            this.zzhgq = true;
            return this;
        }

        public Builder read(DataSource dataSource) {
            zzbq.checkNotNull(dataSource, "Attempting to add a null data source");
            zzbq.checkArgument(!this.zzhgl.contains(dataSource), "Cannot add the same data source as aggregated and detailed");
            if (!this.zzhgf.contains(dataSource)) {
                this.zzhgf.add(dataSource);
            }
            return this;
        }

        public Builder read(DataType dataType) {
            zzbq.checkNotNull(dataType, "Attempting to use a null data type");
            zzbq.zza(!this.zzhgk.contains(dataType), (Object) "Cannot add the same data type as aggregated and detailed");
            if (!this.zzgzy.contains(dataType)) {
                this.zzgzy.add(dataType);
            }
            return this;
        }

        public Builder setLimit(int i) {
            zzbq.zzb(i > 0, "Invalid limit %d is specified", Integer.valueOf(i));
            this.zzhgo = i;
            return this;
        }

        public Builder setTimeRange(long j, long j2, TimeUnit timeUnit) {
            this.zzdvq = timeUnit.toMillis(j);
            this.zzgzz = timeUnit.toMillis(j2);
            return this;
        }
    }

    DataReadRequest(int i, List<DataType> list, List<DataSource> list2, long j, long j2, List<DataType> list3, List<DataSource> list4, int i2, long j3, DataSource dataSource, int i3, boolean z, boolean z2, IBinder iBinder, List<Device> list5, List<Integer> list6) {
        this.zzeck = i;
        this.zzgzy = list;
        this.zzhgf = list2;
        this.zzdvq = j;
        this.zzgzz = j2;
        this.zzhgk = list3;
        this.zzhgl = list4;
        this.zzhac = i2;
        this.zzhgm = j3;
        this.zzhgn = dataSource;
        this.zzhgo = i3;
        this.zzhgp = z;
        this.zzhgq = z2;
        this.zzhgr = iBinder == null ? null : zzbwx.zzat(iBinder);
        if (list5 == null) {
            list5 = Collections.emptyList();
        }
        this.zzhgs = list5;
        if (list6 == null) {
            list6 = Collections.emptyList();
        }
        this.zzhgt = list6;
    }

    private DataReadRequest(Builder builder) {
        this(builder.zzgzy, builder.zzhgf, builder.zzdvq, builder.zzgzz, builder.zzhgk, builder.zzhgl, builder.zzhac, builder.zzhgm, builder.zzhgn, builder.zzhgo, false, builder.zzhgq, null, builder.zzhgs, builder.zzhgt);
    }

    public DataReadRequest(DataReadRequest dataReadRequest, zzbww zzbww) {
        this(dataReadRequest.zzgzy, dataReadRequest.zzhgf, dataReadRequest.zzdvq, dataReadRequest.zzgzz, dataReadRequest.zzhgk, dataReadRequest.zzhgl, dataReadRequest.zzhac, dataReadRequest.zzhgm, dataReadRequest.zzhgn, dataReadRequest.zzhgo, dataReadRequest.zzhgp, dataReadRequest.zzhgq, zzbww, dataReadRequest.zzhgs, dataReadRequest.zzhgt);
    }

    private DataReadRequest(List<DataType> list, List<DataSource> list2, long j, long j2, List<DataType> list3, List<DataSource> list4, int i, long j3, DataSource dataSource, int i2, boolean z, boolean z2, zzbww zzbww, List<Device> list5, List<Integer> list6) {
        this(6, list, list2, j, j2, list3, list4, i, j3, dataSource, i2, z, z2, zzbww == null ? null : zzbww.asBinder(), list5, list6);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof DataReadRequest)) {
                return false;
            }
            DataReadRequest dataReadRequest = (DataReadRequest) obj;
            if (!(this.zzgzy.equals(dataReadRequest.zzgzy) && this.zzhgf.equals(dataReadRequest.zzhgf) && this.zzdvq == dataReadRequest.zzdvq && this.zzgzz == dataReadRequest.zzgzz && this.zzhac == dataReadRequest.zzhac && this.zzhgl.equals(dataReadRequest.zzhgl) && this.zzhgk.equals(dataReadRequest.zzhgk) && zzbg.equal(this.zzhgn, dataReadRequest.zzhgn) && this.zzhgm == dataReadRequest.zzhgm && this.zzhgq == dataReadRequest.zzhgq && this.zzhgo == dataReadRequest.zzhgo && this.zzhgp == dataReadRequest.zzhgp && zzbg.equal(this.zzhgr, dataReadRequest.zzhgr) && zzbg.equal(this.zzhgs, dataReadRequest.zzhgs) && zzbg.equal(this.zzhgt, dataReadRequest.zzhgt))) {
                return false;
            }
        }
        return true;
    }

    public DataSource getActivityDataSource() {
        return this.zzhgn;
    }

    public List<DataSource> getAggregatedDataSources() {
        return this.zzhgl;
    }

    public List<DataType> getAggregatedDataTypes() {
        return this.zzhgk;
    }

    public long getBucketDuration(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhgm, TimeUnit.MILLISECONDS);
    }

    public int getBucketType() {
        return this.zzhac;
    }

    public List<DataSource> getDataSources() {
        return this.zzhgf;
    }

    public List<DataType> getDataTypes() {
        return this.zzgzy;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzgzz, TimeUnit.MILLISECONDS);
    }

    public List<Integer> getFilteredDataQualityStandards() {
        return this.zzhgt;
    }

    public int getLimit() {
        return this.zzhgo;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzdvq, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzhac), Long.valueOf(this.zzdvq), Long.valueOf(this.zzgzz)});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DataReadRequest{");
        if (!this.zzgzy.isEmpty()) {
            for (DataType zzaqp : this.zzgzy) {
                sb.append(zzaqp.zzaqp()).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
        }
        if (!this.zzhgf.isEmpty()) {
            for (DataSource debugString : this.zzhgf) {
                sb.append(debugString.toDebugString()).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
        }
        if (this.zzhac != 0) {
            sb.append("bucket by ").append(Bucket.zzda(this.zzhac));
            if (this.zzhgm > 0) {
                sb.append(" >").append(this.zzhgm).append("ms");
            }
            sb.append(": ");
        }
        if (!this.zzhgk.isEmpty()) {
            for (DataType zzaqp2 : this.zzhgk) {
                sb.append(zzaqp2.zzaqp()).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
        }
        if (!this.zzhgl.isEmpty()) {
            for (DataSource debugString2 : this.zzhgl) {
                sb.append(debugString2.toDebugString()).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
        }
        sb.append(String.format("(%tF %tT - %tF %tT)", new Object[]{Long.valueOf(this.zzdvq), Long.valueOf(this.zzdvq), Long.valueOf(this.zzgzz), Long.valueOf(this.zzgzz)}));
        if (this.zzhgn != null) {
            sb.append("activities: ").append(this.zzhgn.toDebugString());
        }
        if (!this.zzhgt.isEmpty()) {
            sb.append("quality: ");
            for (Integer intValue : this.zzhgt) {
                sb.append(DataSource.zzdd(intValue.intValue())).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
        }
        if (this.zzhgq) {
            sb.append(" +server");
        }
        sb.append("}");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, getDataTypes(), false);
        zzbfp.zzc(parcel, 2, getDataSources(), false);
        zzbfp.zza(parcel, 3, this.zzdvq);
        zzbfp.zza(parcel, 4, this.zzgzz);
        zzbfp.zzc(parcel, 5, getAggregatedDataTypes(), false);
        zzbfp.zzc(parcel, 6, getAggregatedDataSources(), false);
        zzbfp.zzc(parcel, 7, getBucketType());
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zza(parcel, 8, this.zzhgm);
        zzbfp.zza(parcel, 9, (Parcelable) getActivityDataSource(), i, false);
        zzbfp.zzc(parcel, 10, getLimit());
        zzbfp.zza(parcel, 12, this.zzhgp);
        zzbfp.zza(parcel, 13, this.zzhgq);
        zzbfp.zza(parcel, 14, this.zzhgr == null ? null : this.zzhgr.asBinder(), false);
        zzbfp.zzc(parcel, 16, this.zzhgs, false);
        zzbfp.zza(parcel, 17, getFilteredDataQualityStandards(), false);
        zzbfp.zzai(parcel, zze);
    }
}
