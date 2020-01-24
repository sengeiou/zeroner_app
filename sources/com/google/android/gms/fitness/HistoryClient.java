package com.google.android.gms.fitness;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.internal.zzbvv;
import com.google.android.gms.internal.zzbyy;
import com.google.android.gms.tasks.Task;

public class HistoryClient extends GoogleApi<FitnessOptions> {
    private static final HistoryApi zzgzl = new zzbyy();

    HistoryClient(@NonNull Activity activity, @NonNull FitnessOptions fitnessOptions) {
        super(activity, zzbvv.zzhen, fitnessOptions, zza.zzfmj);
    }

    HistoryClient(@NonNull Context context, @NonNull FitnessOptions fitnessOptions) {
        super(context, zzbvv.zzhen, fitnessOptions, zza.zzfmj);
    }

    public Task<Void> deleteData(DataDeleteRequest dataDeleteRequest) {
        return zzbj.zzb(zzgzl.deleteData(zzago(), dataDeleteRequest));
    }

    public Task<Void> insertData(DataSet dataSet) {
        return zzbj.zzb(zzgzl.insertData(zzago(), dataSet));
    }

    public Task<DataSet> readDailyTotal(DataType dataType) {
        return zzbj.zza(zzgzl.readDailyTotal(zzago(), dataType), zzj.zzgnw);
    }

    public Task<DataSet> readDailyTotalFromLocalDevice(DataType dataType) {
        return zzbj.zza(zzgzl.readDailyTotalFromLocalDevice(zzago(), dataType), zzk.zzgnw);
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    public Task<DataReadResponse> readData(DataReadRequest dataReadRequest) {
        return zzbj.zza(zzgzl.readData(zzago(), dataReadRequest), new DataReadResponse());
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    public Task<Void> registerDataUpdateListener(DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest) {
        return zzbj.zzb(zzgzl.registerDataUpdateListener(zzago(), dataUpdateListenerRegistrationRequest));
    }

    public Task<Void> unregisterDataUpdateListener(PendingIntent pendingIntent) {
        return zzbj.zzb(zzgzl.unregisterDataUpdateListener(zzago(), pendingIntent));
    }

    public Task<Void> updateData(DataUpdateRequest dataUpdateRequest) {
        return zzbj.zzb(zzgzl.updateData(zzago(), dataUpdateRequest));
    }
}
