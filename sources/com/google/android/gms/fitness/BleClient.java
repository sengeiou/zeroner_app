package com.google.android.gms.fitness;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.internal.zzbve;
import com.google.android.gms.internal.zzbyi;
import com.google.android.gms.internal.zzcaf;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.List;

public class BleClient extends GoogleApi<FitnessOptions> {
    private static final BleApi zzgyy = (zzq.zzamk() ? new zzbyi() : new zzcaf());

    BleClient(@NonNull Activity activity, @NonNull FitnessOptions fitnessOptions) {
        super(activity, zzbve.zzhen, fitnessOptions, zza.zzfmj);
    }

    BleClient(@NonNull Context context, @NonNull FitnessOptions fitnessOptions) {
        super(context, zzbve.zzhen, fitnessOptions, zza.zzfmj);
    }

    public Task<Void> claimBleDevice(BleDevice bleDevice) {
        return zzbj.zzb(zzgyy.claimBleDevice(zzago(), bleDevice));
    }

    public Task<Void> claimBleDevice(String str) {
        return zzbj.zzb(zzgyy.claimBleDevice(zzago(), str));
    }

    public Task<List<BleDevice>> listClaimedBleDevices() {
        return zzbj.zza(zzgyy.listClaimedBleDevices(zzago()), zzb.zzgnw);
    }

    @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
    public Task<Void> startBleScan(List<DataType> list, int i, BleScanCallback bleScanCallback) {
        if (!zzq.zzamk()) {
            return Tasks.forException(new ApiException(zzcaf.zzhfu));
        }
        zzci zza = zza(bleScanCallback, BleScanCallback.class.getSimpleName());
        return zza(new zzc(this, zza, zza, list, i), new zzd(this, zza.zzajo(), zza));
    }

    public Task<Boolean> stopBleScan(BleScanCallback bleScanCallback) {
        return !zzq.zzamk() ? Tasks.forException(new ApiException(zzcaf.zzhfu)) : zza(zzcm.zzb(bleScanCallback, BleScanCallback.class.getSimpleName()));
    }

    public Task<Void> unclaimBleDevice(BleDevice bleDevice) {
        return zzbj.zzb(zzgyy.unclaimBleDevice(zzago(), bleDevice));
    }

    public Task<Void> unclaimBleDevice(String str) {
        return zzbj.zzb(zzgyy.unclaimBleDevice(zzago(), str));
    }
}
