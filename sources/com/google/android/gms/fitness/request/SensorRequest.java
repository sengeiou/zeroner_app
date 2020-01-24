package com.google.android.gms.fitness.request;

import android.os.SystemClock;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.location.LocationRequest;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.LongCompanionObject;

public class SensorRequest {
    public static final int ACCURACY_MODE_DEFAULT = 2;
    public static final int ACCURACY_MODE_HIGH = 3;
    public static final int ACCURACY_MODE_LOW = 1;
    private final DataSource zzhbr;
    private final DataType zzhbs;
    private final long zzhdy;
    private final int zzhdz;
    private final long zzhhk;
    private final long zzhhl;
    private final LocationRequest zzhhp;
    private final long zzhhq;

    public static class Builder {
        /* access modifiers changed from: private */
        public DataSource zzhbr;
        /* access modifiers changed from: private */
        public DataType zzhbs;
        /* access modifiers changed from: private */
        public long zzhdy = -1;
        /* access modifiers changed from: private */
        public int zzhdz = 2;
        /* access modifiers changed from: private */
        public long zzhhk = 0;
        /* access modifiers changed from: private */
        public long zzhhl = 0;
        /* access modifiers changed from: private */
        public long zzhhq = LongCompanionObject.MAX_VALUE;
        private boolean zzhhr = false;

        public SensorRequest build() {
            boolean z = false;
            zzbq.zza((this.zzhbr == null && this.zzhbs == null) ? false : true, (Object) "Must call setDataSource() or setDataType()");
            if (this.zzhbs == null || this.zzhbr == null || this.zzhbs.equals(this.zzhbr.getDataType())) {
                z = true;
            }
            zzbq.zza(z, (Object) "Specified data type is incompatible with specified data source");
            return new SensorRequest(this);
        }

        public Builder setAccuracyMode(int i) {
            switch (i) {
                case 1:
                case 3:
                    break;
                default:
                    i = 2;
                    break;
            }
            this.zzhdz = i;
            return this;
        }

        public Builder setDataSource(DataSource dataSource) {
            this.zzhbr = dataSource;
            return this;
        }

        public Builder setDataType(DataType dataType) {
            this.zzhbs = dataType;
            return this;
        }

        public Builder setFastestRate(int i, TimeUnit timeUnit) {
            zzbq.checkArgument(i >= 0, "Cannot use a negative interval");
            this.zzhhr = true;
            this.zzhhl = timeUnit.toMicros((long) i);
            return this;
        }

        public Builder setMaxDeliveryLatency(int i, TimeUnit timeUnit) {
            zzbq.checkArgument(i >= 0, "Cannot use a negative delivery interval");
            this.zzhhk = timeUnit.toMicros((long) i);
            return this;
        }

        public Builder setSamplingRate(long j, TimeUnit timeUnit) {
            zzbq.checkArgument(j >= 0, "Cannot use a negative sampling interval");
            this.zzhdy = timeUnit.toMicros(j);
            if (!this.zzhhr) {
                this.zzhhl = this.zzhdy / 2;
            }
            return this;
        }

        public Builder setTimeout(long j, TimeUnit timeUnit) {
            boolean z = true;
            zzbq.zzb(j > 0, "Invalid time out value specified: %d", Long.valueOf(j));
            if (timeUnit == null) {
                z = false;
            }
            zzbq.checkArgument(z, "Invalid time unit specified");
            this.zzhhq = timeUnit.toMicros(j);
            return this;
        }
    }

    private SensorRequest(DataSource dataSource, LocationRequest locationRequest) {
        int i;
        this.zzhhp = locationRequest;
        this.zzhdy = TimeUnit.MILLISECONDS.toMicros(locationRequest.getInterval());
        this.zzhhl = TimeUnit.MILLISECONDS.toMicros(locationRequest.getFastestInterval());
        this.zzhhk = this.zzhdy;
        this.zzhbs = dataSource.getDataType();
        switch (locationRequest.getPriority()) {
            case 100:
                i = 3;
                break;
            case 104:
                i = 1;
                break;
            default:
                i = 2;
                break;
        }
        this.zzhdz = i;
        this.zzhbr = dataSource;
        long expirationTime = locationRequest.getExpirationTime();
        if (expirationTime == LongCompanionObject.MAX_VALUE) {
            this.zzhhq = LongCompanionObject.MAX_VALUE;
        } else {
            this.zzhhq = TimeUnit.MILLISECONDS.toMicros(expirationTime - SystemClock.elapsedRealtime());
        }
    }

    private SensorRequest(Builder builder) {
        this.zzhbr = builder.zzhbr;
        this.zzhbs = builder.zzhbs;
        this.zzhdy = builder.zzhdy;
        this.zzhhl = builder.zzhhl;
        this.zzhhk = builder.zzhhk;
        this.zzhdz = builder.zzhdz;
        this.zzhhp = null;
        this.zzhhq = builder.zzhhq;
    }

    public static SensorRequest fromLocationRequest(DataSource dataSource, LocationRequest locationRequest) {
        return new SensorRequest(dataSource, locationRequest);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof SensorRequest)) {
                return false;
            }
            SensorRequest sensorRequest = (SensorRequest) obj;
            if (!(zzbg.equal(this.zzhbr, sensorRequest.zzhbr) && zzbg.equal(this.zzhbs, sensorRequest.zzhbs) && this.zzhdy == sensorRequest.zzhdy && this.zzhhl == sensorRequest.zzhhl && this.zzhhk == sensorRequest.zzhhk && this.zzhdz == sensorRequest.zzhdz && zzbg.equal(this.zzhhp, sensorRequest.zzhhp) && this.zzhhq == sensorRequest.zzhhq)) {
                return false;
            }
        }
        return true;
    }

    public int getAccuracyMode() {
        return this.zzhdz;
    }

    public DataSource getDataSource() {
        return this.zzhbr;
    }

    public DataType getDataType() {
        return this.zzhbs;
    }

    public long getFastestRate(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhhl, TimeUnit.MICROSECONDS);
    }

    public long getMaxDeliveryLatency(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhhk, TimeUnit.MICROSECONDS);
    }

    public long getSamplingRate(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhdy, TimeUnit.MICROSECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhbr, this.zzhbs, Long.valueOf(this.zzhdy), Long.valueOf(this.zzhhl), Long.valueOf(this.zzhhk), Integer.valueOf(this.zzhdz), this.zzhhp, Long.valueOf(this.zzhhq)});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("dataSource", this.zzhbr).zzg("dataType", this.zzhbs).zzg("samplingRateMicros", Long.valueOf(this.zzhdy)).zzg("deliveryLatencyMicros", Long.valueOf(this.zzhhk)).zzg("timeOutMicros", Long.valueOf(this.zzhhq)).toString();
    }

    public final long zzaqw() {
        return this.zzhhq;
    }
}
