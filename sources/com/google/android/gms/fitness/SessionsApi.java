package com.google.android.gms.fitness;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.SessionReadResult;
import com.google.android.gms.fitness.result.SessionStopResult;
import com.google.android.gms.internal.zzbfr;

public interface SessionsApi {

    public static class ViewIntentBuilder {
        private final Context mContext;
        private Session zzgzp;
        private String zzgzq;
        private boolean zzgzr = false;

        public ViewIntentBuilder(Context context) {
            this.mContext = context;
        }

        public Intent build() {
            zzbq.zza(this.zzgzp != null, (Object) "Session must be set");
            Intent intent = new Intent(Fitness.ACTION_VIEW);
            intent.setType(Session.getMimeType(this.zzgzp.getActivity()));
            zzbfr.zza(this.zzgzp, intent, Session.EXTRA_SESSION);
            if (!this.zzgzr) {
                this.zzgzq = this.zzgzp.getAppPackageName();
            }
            if (this.zzgzq != null) {
                Intent intent2 = new Intent(intent).setPackage(this.zzgzq);
                ResolveInfo resolveActivity = this.mContext.getPackageManager().resolveActivity(intent2, 0);
                if (resolveActivity != null) {
                    intent2.setComponent(new ComponentName(this.zzgzq, resolveActivity.activityInfo.name));
                    return intent2;
                }
            }
            return intent;
        }

        public ViewIntentBuilder setPreferredApplication(String str) {
            this.zzgzq = str;
            this.zzgzr = true;
            return this;
        }

        public ViewIntentBuilder setSession(Session session) {
            this.zzgzp = session;
            return this;
        }
    }

    PendingResult<Status> insertSession(GoogleApiClient googleApiClient, SessionInsertRequest sessionInsertRequest);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    PendingResult<SessionReadResult> readSession(GoogleApiClient googleApiClient, SessionReadRequest sessionReadRequest);

    PendingResult<Status> registerForSessions(GoogleApiClient googleApiClient, PendingIntent pendingIntent);

    PendingResult<Status> startSession(GoogleApiClient googleApiClient, Session session);

    PendingResult<SessionStopResult> stopSession(GoogleApiClient googleApiClient, String str);

    PendingResult<Status> unregisterForSessions(GoogleApiClient googleApiClient, PendingIntent pendingIntent);
}
