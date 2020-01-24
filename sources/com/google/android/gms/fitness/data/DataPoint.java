package com.google.android.gms.fitness.data;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbfr;
import com.google.android.gms.internal.zzbut;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class DataPoint extends zzbfm implements ReflectedParcelable {
    public static final Creator<DataPoint> CREATOR = new zzh();
    private final int versionCode;
    private final DataSource zzgzh;
    private long zzhag;
    private long zzhah;
    private final Value[] zzhai;
    private DataSource zzhaj;
    private long zzhak;
    private long zzhal;

    DataPoint(int i, DataSource dataSource, long j, long j2, Value[] valueArr, DataSource dataSource2, long j3, long j4) {
        this.versionCode = i;
        this.zzgzh = dataSource;
        this.zzhaj = dataSource2;
        this.zzhag = j;
        this.zzhah = j2;
        this.zzhai = valueArr;
        this.zzhak = j3;
        this.zzhal = j4;
    }

    private DataPoint(DataSource dataSource) {
        this.versionCode = 4;
        this.zzgzh = (DataSource) zzbq.checkNotNull(dataSource, "Data source cannot be null");
        List fields = dataSource.getDataType().getFields();
        this.zzhai = new Value[fields.size()];
        int i = 0;
        Iterator it = fields.iterator();
        while (true) {
            int i2 = i;
            if (it.hasNext()) {
                this.zzhai[i2] = new Value(((Field) it.next()).getFormat());
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private DataPoint(DataSource dataSource, DataSource dataSource2, RawDataPoint rawDataPoint) {
        this(4, dataSource, zza(Long.valueOf(rawDataPoint.zzhdl), 0), zza(Long.valueOf(rawDataPoint.zzhdm), 0), rawDataPoint.zzhdn, dataSource2, zza(Long.valueOf(rawDataPoint.zzhdq), 0), zza(Long.valueOf(rawDataPoint.zzhdr), 0));
    }

    DataPoint(List<DataSource> list, RawDataPoint rawDataPoint) {
        this(zzc(list, rawDataPoint.zzhdo), zzc(list, rawDataPoint.zzhdp), rawDataPoint);
    }

    public static DataPoint create(DataSource dataSource) {
        return new DataPoint(dataSource);
    }

    public static DataPoint extract(Intent intent) {
        if (intent == null) {
            return null;
        }
        return (DataPoint) zzbfr.zza(intent, "com.google.android.gms.fitness.EXTRA_DATA_POINT", CREATOR);
    }

    private static long zza(Long l, long j) {
        if (l != null) {
            return l.longValue();
        }
        return 0;
    }

    private static DataSource zzc(List<DataSource> list, int i) {
        if (i < 0 || i >= list.size()) {
            return null;
        }
        return (DataSource) list.get(i);
    }

    private final void zzdc(int i) {
        List fields = getDataType().getFields();
        int size = fields.size();
        zzbq.zzb(i == size, "Attempting to insert %s values, but needed %s: %s", Integer.valueOf(i), Integer.valueOf(size), fields);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof DataPoint)) {
                return false;
            }
            DataPoint dataPoint = (DataPoint) obj;
            if (!(zzbg.equal(this.zzgzh, dataPoint.zzgzh) && this.zzhag == dataPoint.zzhag && this.zzhah == dataPoint.zzhah && Arrays.equals(this.zzhai, dataPoint.zzhai) && zzbg.equal(getOriginalDataSource(), dataPoint.getOriginalDataSource()))) {
                return false;
            }
        }
        return true;
    }

    public final DataSource getDataSource() {
        return this.zzgzh;
    }

    public final DataType getDataType() {
        return this.zzgzh.getDataType();
    }

    public final long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhag, TimeUnit.NANOSECONDS);
    }

    public final DataSource getOriginalDataSource() {
        return this.zzhaj != null ? this.zzhaj : this.zzgzh;
    }

    public final long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhah, TimeUnit.NANOSECONDS);
    }

    public final long getTimestamp(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhag, TimeUnit.NANOSECONDS);
    }

    public final Value getValue(Field field) {
        return this.zzhai[getDataType().indexOf(field)];
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzgzh, Long.valueOf(this.zzhag), Long.valueOf(this.zzhah)});
    }

    public final DataPoint setFloatValues(float... fArr) {
        zzdc(fArr.length);
        for (int i = 0; i < fArr.length; i++) {
            this.zzhai[i].setFloat(fArr[i]);
        }
        return this;
    }

    public final DataPoint setIntValues(int... iArr) {
        zzdc(iArr.length);
        for (int i = 0; i < iArr.length; i++) {
            this.zzhai[i].setInt(iArr[i]);
        }
        return this;
    }

    public final DataPoint setTimeInterval(long j, long j2, TimeUnit timeUnit) {
        this.zzhah = timeUnit.toNanos(j);
        this.zzhag = timeUnit.toNanos(j2);
        return this;
    }

    public final DataPoint setTimestamp(long j, TimeUnit timeUnit) {
        boolean z = true;
        this.zzhag = timeUnit.toNanos(j);
        if (getDataType() == DataType.TYPE_LOCATION_SAMPLE) {
            if (timeUnit.convert(1, TimeUnit.MILLISECONDS) <= 1) {
                z = false;
            }
            if (z) {
                Log.w("Fitness", "Storing location at more than millisecond granularity is not supported. Extra precision is lost as the value is converted to milliseconds.");
                this.zzhag = zzbut.zza(this.zzhag, TimeUnit.NANOSECONDS, TimeUnit.MILLISECONDS);
            }
        }
        return this;
    }

    public final String toString() {
        String str = "DataPoint{%s@[%s, %s,raw=%s,insert=%s](%s %s)}";
        Object[] objArr = new Object[7];
        objArr[0] = Arrays.toString(this.zzhai);
        objArr[1] = Long.valueOf(this.zzhah);
        objArr[2] = Long.valueOf(this.zzhag);
        objArr[3] = Long.valueOf(this.zzhak);
        objArr[4] = Long.valueOf(this.zzhal);
        objArr[5] = this.zzgzh.toDebugString();
        objArr[6] = this.zzhaj != null ? this.zzhaj.toDebugString() : "N/A";
        return String.format(str, objArr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) getDataSource(), i, false);
        zzbfp.zza(parcel, 3, this.zzhag);
        zzbfp.zza(parcel, 4, this.zzhah);
        zzbfp.zza(parcel, 5, (T[]) this.zzhai, i, false);
        zzbfp.zza(parcel, 6, (Parcelable) this.zzhaj, i, false);
        zzbfp.zza(parcel, 7, this.zzhak);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zza(parcel, 8, this.zzhal);
        zzbfp.zzai(parcel, zze);
    }

    public final Value[] zzaqf() {
        return this.zzhai;
    }

    public final DataSource zzaqg() {
        return this.zzhaj;
    }

    public final long zzaqh() {
        return this.zzhak;
    }

    public final long zzaqi() {
        return this.zzhal;
    }

    public final void zzaqj() {
        zzbq.zzb(getDataType().getName().equals(getDataSource().getDataType().getName()), "Conflicting data types found %s vs %s", getDataType(), getDataType());
        zzbq.zzb(this.zzhag > 0, "Data point does not have the timestamp set: %s", this);
        zzbq.zzb(this.zzhah <= this.zzhag, "Data point with start time greater than end time found: %s", this);
    }

    public final Value zzdb(int i) {
        DataType dataType = getDataType();
        if (i >= 0 && i < dataType.getFields().size()) {
            return this.zzhai[i];
        }
        throw new IllegalArgumentException(String.format("fieldIndex %s is out of range for %s", new Object[]{Integer.valueOf(i), dataType}));
    }
}
