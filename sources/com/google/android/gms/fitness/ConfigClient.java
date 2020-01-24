package com.google.android.gms.fitness;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataTypeCreateRequest;
import com.google.android.gms.internal.zzbvk;
import com.google.android.gms.internal.zzbyq;
import com.google.android.gms.tasks.Task;

public class ConfigClient extends GoogleApi<FitnessOptions> {
    private static final ConfigApi zzgzb = new zzbyq();

    ConfigClient(@NonNull Activity activity, @NonNull FitnessOptions fitnessOptions) {
        super(activity, zzbvk.zzhen, fitnessOptions, zza.zzfmj);
    }

    ConfigClient(@NonNull Context context, @NonNull FitnessOptions fitnessOptions) {
        super(context, zzbvk.zzhen, fitnessOptions, zza.zzfmj);
    }

    public Task<DataType> createCustomDataType(DataTypeCreateRequest dataTypeCreateRequest) {
        return zzbj.zza(zzgzb.createCustomDataType(zzago(), dataTypeCreateRequest), zze.zzgnw);
    }

    public Task<Void> disableFit() {
        return zzbj.zzb(zzgzb.disableFit(zzago()));
    }

    public Task<DataType> readDataType(String str) {
        return zzbj.zza(zzgzb.readDataType(zzago(), str), zzf.zzgnw);
    }
}
