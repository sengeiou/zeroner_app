package com.google.android.gms.fitness;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.internal.zzbwb;
import com.google.android.gms.internal.zzbzi;
import com.google.android.gms.tasks.Task;
import java.util.List;

public class RecordingClient extends GoogleApi<FitnessOptions> {
    private static final RecordingApi zzgzm = new zzbzi();

    RecordingClient(@NonNull Activity activity, @NonNull FitnessOptions fitnessOptions) {
        super(activity, zzbwb.zzhen, fitnessOptions, zza.zzfmj);
    }

    RecordingClient(@NonNull Context context, @NonNull FitnessOptions fitnessOptions) {
        super(context, zzbwb.zzhen, fitnessOptions, zza.zzfmj);
    }

    public Task<List<Subscription>> listSubscriptions() {
        return zzbj.zza(zzgzm.listSubscriptions(zzago()), zzl.zzgnw);
    }

    public Task<List<Subscription>> listSubscriptions(DataType dataType) {
        return zzbj.zza(zzgzm.listSubscriptions(zzago(), dataType), zzm.zzgnw);
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    public Task<Void> subscribe(DataSource dataSource) {
        return zzbj.zzb(zzgzm.subscribe(zzago(), dataSource));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    public Task<Void> subscribe(DataType dataType) {
        return zzbj.zzb(zzgzm.subscribe(zzago(), dataType));
    }

    public Task<Void> unsubscribe(DataSource dataSource) {
        return zzbj.zzb(zzgzm.unsubscribe(zzago(), dataSource));
    }

    public Task<Void> unsubscribe(DataType dataType) {
        return zzbj.zzb(zzgzm.unsubscribe(zzago(), dataType));
    }

    public Task<Void> unsubscribe(Subscription subscription) {
        return zzbj.zzb(zzgzm.unsubscribe(zzago(), subscription));
    }
}
