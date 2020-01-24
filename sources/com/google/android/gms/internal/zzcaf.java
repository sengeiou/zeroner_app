package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.BleApi;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.result.BleDevicesResult;

public final class zzcaf implements BleApi {
    public static final Status zzhfu = new Status(FitnessStatusCodes.UNSUPPORTED_PLATFORM);

    public final PendingResult<Status> claimBleDevice(GoogleApiClient googleApiClient, BleDevice bleDevice) {
        return PendingResults.zza(zzhfu, googleApiClient);
    }

    public final PendingResult<Status> claimBleDevice(GoogleApiClient googleApiClient, String str) {
        return PendingResults.zza(zzhfu, googleApiClient);
    }

    public final PendingResult<BleDevicesResult> listClaimedBleDevices(GoogleApiClient googleApiClient) {
        return PendingResults.zza(BleDevicesResult.zzac(zzhfu), googleApiClient);
    }

    public final PendingResult<Status> startBleScan(GoogleApiClient googleApiClient, StartBleScanRequest startBleScanRequest) {
        return PendingResults.zza(zzhfu, googleApiClient);
    }

    public final PendingResult<Status> stopBleScan(GoogleApiClient googleApiClient, BleScanCallback bleScanCallback) {
        return PendingResults.zza(zzhfu, googleApiClient);
    }

    public final PendingResult<Status> unclaimBleDevice(GoogleApiClient googleApiClient, BleDevice bleDevice) {
        return PendingResults.zza(zzhfu, googleApiClient);
    }

    public final PendingResult<Status> unclaimBleDevice(GoogleApiClient googleApiClient, String str) {
        return PendingResults.zza(zzhfu, googleApiClient);
    }
}
