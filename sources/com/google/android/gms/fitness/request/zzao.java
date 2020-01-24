package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.zzt;
import com.google.android.gms.fitness.data.zzu;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import com.google.android.gms.internal.zzcdv;
import com.google.android.gms.location.LocationRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class zzao extends zzbfm {
    public static final Creator<zzao> CREATOR = new zzap();
    private final int zzeck;
    private final PendingIntent zzeeo;
    private DataSource zzhbr;
    private DataType zzhbs;
    private final long zzhdy;
    private final int zzhdz;
    private final zzbyf zzhgc;
    private zzt zzhhj;
    private final long zzhhk;
    private final long zzhhl;
    private final List<LocationRequest> zzhhm;
    private final long zzhhn;
    private final List<zzcdv> zzhho;

    zzao(int i, DataSource dataSource, DataType dataType, IBinder iBinder, int i2, int i3, long j, long j2, PendingIntent pendingIntent, long j3, int i4, List<LocationRequest> list, long j4, IBinder iBinder2) {
        this.zzeck = i;
        this.zzhbr = dataSource;
        this.zzhbs = dataType;
        this.zzhhj = iBinder == null ? null : zzu.zzar(iBinder);
        if (j == 0) {
            j = (long) i2;
        }
        this.zzhdy = j;
        this.zzhhl = j3;
        if (j2 == 0) {
            j2 = (long) i3;
        }
        this.zzhhk = j2;
        this.zzhhm = list;
        this.zzeeo = pendingIntent;
        this.zzhdz = i4;
        this.zzhho = Collections.emptyList();
        this.zzhhn = j4;
        this.zzhgc = zzbyg.zzba(iBinder2);
    }

    private zzao(DataSource dataSource, DataType dataType, zzt zzt, PendingIntent pendingIntent, long j, long j2, long j3, int i, List<LocationRequest> list, List<zzcdv> list2, long j4, zzbyf zzbyf) {
        this.zzeck = 6;
        this.zzhbr = dataSource;
        this.zzhbs = dataType;
        this.zzhhj = zzt;
        this.zzeeo = pendingIntent;
        this.zzhdy = j;
        this.zzhhl = j2;
        this.zzhhk = j3;
        this.zzhdz = i;
        this.zzhhm = null;
        this.zzhho = list2;
        this.zzhhn = j4;
        this.zzhgc = zzbyf;
    }

    public zzao(SensorRequest sensorRequest, zzt zzt, PendingIntent pendingIntent, zzbyf zzbyf) {
        zzt zzt2 = zzt;
        PendingIntent pendingIntent2 = pendingIntent;
        this(sensorRequest.getDataSource(), sensorRequest.getDataType(), zzt2, pendingIntent2, sensorRequest.getSamplingRate(TimeUnit.MICROSECONDS), sensorRequest.getFastestRate(TimeUnit.MICROSECONDS), sensorRequest.getMaxDeliveryLatency(TimeUnit.MICROSECONDS), sensorRequest.getAccuracyMode(), null, Collections.emptyList(), sensorRequest.zzaqw(), zzbyf);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof zzao)) {
                return false;
            }
            zzao zzao = (zzao) obj;
            if (!(zzbg.equal(this.zzhbr, zzao.zzhbr) && zzbg.equal(this.zzhbs, zzao.zzhbs) && this.zzhdy == zzao.zzhdy && this.zzhhl == zzao.zzhhl && this.zzhhk == zzao.zzhhk && this.zzhdz == zzao.zzhdz && zzbg.equal(this.zzhhm, zzao.zzhhm))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhbr, this.zzhbs, this.zzhhj, Long.valueOf(this.zzhdy), Long.valueOf(this.zzhhl), Long.valueOf(this.zzhhk), Integer.valueOf(this.zzhdz), this.zzhhm});
    }

    public final String toString() {
        return String.format("SensorRegistrationRequest{type %s source %s interval %s fastest %s latency %s}", new Object[]{this.zzhbs, this.zzhbr, Long.valueOf(this.zzhdy), Long.valueOf(this.zzhhl), Long.valueOf(this.zzhhk)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzhbr, i, false);
        zzbfp.zza(parcel, 2, (Parcelable) this.zzhbs, i, false);
        zzbfp.zza(parcel, 3, this.zzhhj == null ? null : this.zzhhj.asBinder(), false);
        zzbfp.zzc(parcel, 4, 0);
        zzbfp.zzc(parcel, 5, 0);
        zzbfp.zza(parcel, 6, this.zzhdy);
        zzbfp.zza(parcel, 7, this.zzhhk);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zza(parcel, 8, (Parcelable) this.zzeeo, i, false);
        zzbfp.zza(parcel, 9, this.zzhhl);
        zzbfp.zzc(parcel, 10, this.zzhdz);
        zzbfp.zzc(parcel, 11, this.zzhhm, false);
        zzbfp.zza(parcel, 12, this.zzhhn);
        if (this.zzhgc != null) {
            iBinder = this.zzhgc.asBinder();
        }
        zzbfp.zza(parcel, 13, iBinder, false);
        zzbfp.zzai(parcel, zze);
    }
}
