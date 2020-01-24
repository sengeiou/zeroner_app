package com.google.android.gms.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.SensorsApi;
import com.google.android.gms.fitness.data.zzt;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.zzal;
import com.google.android.gms.fitness.request.zzan;
import com.google.android.gms.fitness.result.DataSourcesResult;

public final class zzbzp implements SensorsApi {
    private final PendingResult<Status> zza(GoogleApiClient googleApiClient, zzt zzt, PendingIntent pendingIntent) {
        return googleApiClient.zze(new zzbzs(this, googleApiClient, zzt, pendingIntent));
    }

    private final PendingResult<Status> zza(GoogleApiClient googleApiClient, SensorRequest sensorRequest, zzt zzt, PendingIntent pendingIntent) {
        return googleApiClient.zzd(new zzbzr(this, googleApiClient, sensorRequest, zzt, pendingIntent));
    }

    public final PendingResult<Status> add(GoogleApiClient googleApiClient, SensorRequest sensorRequest, PendingIntent pendingIntent) {
        return zza(googleApiClient, sensorRequest, null, pendingIntent);
    }

    public final PendingResult<Status> add(GoogleApiClient googleApiClient, SensorRequest sensorRequest, OnDataPointListener onDataPointListener) {
        return zza(googleApiClient, sensorRequest, zzan.zzaqv().zza(onDataPointListener), null);
    }

    public final PendingResult<DataSourcesResult> findDataSources(GoogleApiClient googleApiClient, DataSourcesRequest dataSourcesRequest) {
        return googleApiClient.zzd(new zzbzq(this, googleApiClient, dataSourcesRequest));
    }

    public final PendingResult<Status> remove(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return zza(googleApiClient, null, pendingIntent);
    }

    public final PendingResult<Status> remove(GoogleApiClient googleApiClient, OnDataPointListener onDataPointListener) {
        zzal zzb = zzan.zzaqv().zzb(onDataPointListener);
        return zzb == null ? PendingResults.zza(Status.zzfni, googleApiClient) : zza(googleApiClient, zzb, null);
    }
}
