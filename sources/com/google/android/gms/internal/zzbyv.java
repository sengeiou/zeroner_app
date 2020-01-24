package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.fitness.GoalsApi;
import com.google.android.gms.fitness.request.GoalsReadRequest;
import com.google.android.gms.fitness.result.GoalsResult;

public final class zzbyv implements GoalsApi {
    public final PendingResult<GoalsResult> readCurrentGoals(GoogleApiClient googleApiClient, GoalsReadRequest goalsReadRequest) {
        return googleApiClient.zzd(new zzbyw(this, googleApiClient, goalsReadRequest));
    }
}
