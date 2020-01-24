package com.google.android.gms.fitness;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.SessionReadResponse;
import com.google.android.gms.internal.zzbwn;
import com.google.android.gms.internal.zzbzt;
import com.google.android.gms.tasks.Task;
import java.util.List;

public class SessionsClient extends GoogleApi<FitnessOptions> {
    private static final SessionsApi zzgzs = new zzbzt();

    SessionsClient(@NonNull Activity activity, @NonNull FitnessOptions fitnessOptions) {
        super(activity, zzbwn.zzhen, fitnessOptions, zza.zzfmj);
    }

    SessionsClient(@NonNull Context context, @NonNull FitnessOptions fitnessOptions) {
        super(context, zzbwn.zzhen, fitnessOptions, zza.zzfmj);
    }

    public Task<Void> insertSession(SessionInsertRequest sessionInsertRequest) {
        return zzbj.zzb(zzgzs.insertSession(zzago(), sessionInsertRequest));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    public Task<SessionReadResponse> readSession(SessionReadRequest sessionReadRequest) {
        return zzbj.zza(zzgzs.readSession(zzago(), sessionReadRequest), new SessionReadResponse());
    }

    public Task<Void> registerForSessions(PendingIntent pendingIntent) {
        return zzbj.zzb(zzgzs.registerForSessions(zzago(), pendingIntent));
    }

    public Task<Void> startSession(Session session) {
        return zzbj.zzb(zzgzs.startSession(zzago(), session));
    }

    public Task<List<Session>> stopSession(String str) {
        return zzbj.zza(zzgzs.stopSession(zzago(), str), zzq.zzgnw);
    }

    public Task<Void> unregisterForSessions(PendingIntent pendingIntent) {
        return zzbj.zzb(zzgzs.unregisterForSessions(zzago(), pendingIntent));
    }
}
