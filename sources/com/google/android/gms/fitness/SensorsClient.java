package com.google.android.gms.fitness;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.internal.zzbwh;
import com.google.android.gms.internal.zzbzp;
import com.google.android.gms.tasks.Task;
import java.util.List;

public class SensorsClient extends GoogleApi<FitnessOptions> {
    private static final SensorsApi zzgzn = new zzbzp();

    SensorsClient(@NonNull Activity activity, @NonNull FitnessOptions fitnessOptions) {
        super(activity, zzbwh.zzhen, fitnessOptions, zza.zzfmj);
    }

    SensorsClient(@NonNull Context context, @NonNull FitnessOptions fitnessOptions) {
        super(context, zzbwh.zzhen, fitnessOptions, zza.zzfmj);
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    public Task<Void> add(SensorRequest sensorRequest, PendingIntent pendingIntent) {
        return zzbj.zzb(zzgzn.add(zzago(), sensorRequest, pendingIntent));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    public Task<Void> add(SensorRequest sensorRequest, OnDataPointListener onDataPointListener) {
        zzci zza = zza(onDataPointListener, OnDataPointListener.class.getSimpleName());
        return zza(new zzo(this, zza, zza, sensorRequest), new zzp(this, zza.zzajo(), zza));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    public Task<List<DataSource>> findDataSources(DataSourcesRequest dataSourcesRequest) {
        return zzbj.zza(zzgzn.findDataSources(zzago(), dataSourcesRequest), zzn.zzgnw);
    }

    public Task<Void> remove(PendingIntent pendingIntent) {
        return zzbj.zzb(zzgzn.remove(zzago(), pendingIntent));
    }

    public Task<Boolean> remove(OnDataPointListener onDataPointListener) {
        return zza(zzcm.zzb(onDataPointListener, OnDataPointListener.class.getSimpleName()));
    }
}
