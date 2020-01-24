package com.google.android.gms.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.SessionsApi;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.SessionReadResult;
import com.google.android.gms.fitness.result.SessionStopResult;
import java.util.concurrent.TimeUnit;

public final class zzbzt implements SessionsApi {
    public final PendingResult<Status> insertSession(GoogleApiClient googleApiClient, SessionInsertRequest sessionInsertRequest) {
        return googleApiClient.zzd(new zzbzw(this, googleApiClient, sessionInsertRequest));
    }

    public final PendingResult<SessionReadResult> readSession(GoogleApiClient googleApiClient, SessionReadRequest sessionReadRequest) {
        return googleApiClient.zzd(new zzbzx(this, googleApiClient, sessionReadRequest));
    }

    public final PendingResult<Status> registerForSessions(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return googleApiClient.zze(new zzbzy(this, googleApiClient, pendingIntent, 0));
    }

    public final PendingResult<Status> startSession(GoogleApiClient googleApiClient, Session session) {
        zzbq.checkNotNull(session, "Session cannot be null");
        zzbq.checkArgument(session.getEndTime(TimeUnit.MILLISECONDS) == 0, "Cannot start a session which has already ended");
        return googleApiClient.zze(new zzbzu(this, googleApiClient, session));
    }

    public final PendingResult<SessionStopResult> stopSession(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zze(new zzbzv(this, googleApiClient, null, str));
    }

    public final PendingResult<Status> unregisterForSessions(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return googleApiClient.zze(new zzbzz(this, googleApiClient, pendingIntent));
    }
}
