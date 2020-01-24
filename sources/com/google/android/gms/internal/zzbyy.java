package com.google.android.gms.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.HistoryApi;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataReadResult;

public final class zzbyy implements HistoryApi {
    private final PendingResult<DailyTotalResult> zza(GoogleApiClient googleApiClient, DataType dataType, boolean z) {
        return googleApiClient.zzd(new zzbzf(this, googleApiClient, dataType, z));
    }

    public final PendingResult<Status> deleteData(GoogleApiClient googleApiClient, DataDeleteRequest dataDeleteRequest) {
        return googleApiClient.zzd(new zzbza(this, googleApiClient, dataDeleteRequest));
    }

    public final PendingResult<Status> insertData(GoogleApiClient googleApiClient, DataSet dataSet) {
        zzbq.checkNotNull(dataSet, "Must set the data set");
        zzbq.zza(!dataSet.getDataPoints().isEmpty(), (Object) "Cannot use an empty data set");
        zzbq.checkNotNull(dataSet.getDataSource().zzaql(), "Must set the app package name for the data source");
        return googleApiClient.zzd(new zzbyz(this, googleApiClient, dataSet, false));
    }

    public final PendingResult<DailyTotalResult> readDailyTotal(GoogleApiClient googleApiClient, DataType dataType) {
        return zza(googleApiClient, dataType, false);
    }

    public final PendingResult<DailyTotalResult> readDailyTotalFromLocalDevice(GoogleApiClient googleApiClient, DataType dataType) {
        return zza(googleApiClient, dataType, true);
    }

    public final PendingResult<DataReadResult> readData(GoogleApiClient googleApiClient, DataReadRequest dataReadRequest) {
        return googleApiClient.zzd(new zzbze(this, googleApiClient, dataReadRequest));
    }

    public final PendingResult<Status> registerDataUpdateListener(GoogleApiClient googleApiClient, DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest) {
        return googleApiClient.zzd(new zzbzc(this, googleApiClient, dataUpdateListenerRegistrationRequest));
    }

    public final PendingResult<Status> unregisterDataUpdateListener(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return googleApiClient.zze(new zzbzd(this, googleApiClient, pendingIntent));
    }

    public final PendingResult<Status> updateData(GoogleApiClient googleApiClient, DataUpdateRequest dataUpdateRequest) {
        zzbq.checkNotNull(dataUpdateRequest.getDataSet(), "Must set the data set");
        zzbq.zza(dataUpdateRequest.zzaae(), (Object) "Must set a non-zero value for startTimeMillis/startTime");
        zzbq.zza(dataUpdateRequest.zzaqu(), (Object) "Must set a non-zero value for endTimeMillis/endTime");
        return googleApiClient.zzd(new zzbzb(this, googleApiClient, dataUpdateRequest));
    }
}
