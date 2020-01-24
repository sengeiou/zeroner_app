package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.RecordingApi;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.data.Subscription.zza;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

public final class zzbzi implements RecordingApi {
    private final PendingResult<Status> zza(GoogleApiClient googleApiClient, Subscription subscription) {
        return googleApiClient.zzd(new zzbzl(this, googleApiClient, subscription));
    }

    public final PendingResult<ListSubscriptionsResult> listSubscriptions(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzbzj(this, googleApiClient));
    }

    public final PendingResult<ListSubscriptionsResult> listSubscriptions(GoogleApiClient googleApiClient, DataType dataType) {
        return googleApiClient.zzd(new zzbzk(this, googleApiClient, dataType));
    }

    public final PendingResult<Status> subscribe(GoogleApiClient googleApiClient, DataSource dataSource) {
        return zza(googleApiClient, new zza().zza(dataSource).zzaqr());
    }

    public final PendingResult<Status> subscribe(GoogleApiClient googleApiClient, DataType dataType) {
        return zza(googleApiClient, new zza().zza(dataType).zzaqr());
    }

    public final PendingResult<Status> unsubscribe(GoogleApiClient googleApiClient, DataSource dataSource) {
        return googleApiClient.zze(new zzbzn(this, googleApiClient, dataSource));
    }

    public final PendingResult<Status> unsubscribe(GoogleApiClient googleApiClient, DataType dataType) {
        return googleApiClient.zze(new zzbzm(this, googleApiClient, dataType));
    }

    public final PendingResult<Status> unsubscribe(GoogleApiClient googleApiClient, Subscription subscription) {
        return subscription.getDataType() == null ? unsubscribe(googleApiClient, subscription.getDataSource()) : unsubscribe(googleApiClient, subscription.getDataType());
    }
}
