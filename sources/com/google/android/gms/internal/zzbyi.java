package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.BleApi;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.result.BleDevicesResult;

public final class zzbyi implements BleApi {
    public final PendingResult<Status> claimBleDevice(GoogleApiClient googleApiClient, BleDevice bleDevice) {
        return googleApiClient.zze(new zzbym(this, googleApiClient, bleDevice));
    }

    public final PendingResult<Status> claimBleDevice(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zze(new zzbyl(this, googleApiClient, str));
    }

    public final PendingResult<BleDevicesResult> listClaimedBleDevices(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzbyo(this, googleApiClient));
    }

    public final PendingResult<Status> startBleScan(GoogleApiClient googleApiClient, StartBleScanRequest startBleScanRequest) {
        return googleApiClient.zzd(new zzbyj(this, googleApiClient, startBleScanRequest));
    }

    public final PendingResult<Status> stopBleScan(GoogleApiClient googleApiClient, BleScanCallback bleScanCallback) {
        return googleApiClient.zzd(new zzbyk(this, googleApiClient, bleScanCallback));
    }

    public final PendingResult<Status> unclaimBleDevice(GoogleApiClient googleApiClient, BleDevice bleDevice) {
        return unclaimBleDevice(googleApiClient, bleDevice.getAddress());
    }

    public final PendingResult<Status> unclaimBleDevice(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zze(new zzbyn(this, googleApiClient, str));
    }
}
