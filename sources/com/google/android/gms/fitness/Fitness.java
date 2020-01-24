package com.google.android.gms.fitness;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzbve;
import com.google.android.gms.internal.zzbvk;
import com.google.android.gms.internal.zzbvq;
import com.google.android.gms.internal.zzbvv;
import com.google.android.gms.internal.zzbwb;
import com.google.android.gms.internal.zzbwh;
import com.google.android.gms.internal.zzbwn;
import com.google.android.gms.internal.zzbyi;
import com.google.android.gms.internal.zzbyq;
import com.google.android.gms.internal.zzbyv;
import com.google.android.gms.internal.zzbyy;
import com.google.android.gms.internal.zzbzi;
import com.google.android.gms.internal.zzbzp;
import com.google.android.gms.internal.zzbzt;
import com.google.android.gms.internal.zzcaf;
import java.util.concurrent.TimeUnit;

public class Fitness {
    public static final String ACTION_TRACK = "vnd.google.fitness.TRACK";
    public static final String ACTION_VIEW = "vnd.google.fitness.VIEW";
    public static final String ACTION_VIEW_GOAL = "vnd.google.fitness.VIEW_GOAL";
    @Deprecated
    public static final Void API = null;
    public static final Api<NoOptions> BLE_API = zzbve.API;
    public static final BleApi BleApi = (VERSION.SDK_INT >= 18 ? new zzbyi() : new zzcaf());
    public static final Api<NoOptions> CONFIG_API = zzbvk.API;
    public static final ConfigApi ConfigApi = new zzbyq();
    public static final String EXTRA_END_TIME = "vnd.google.fitness.end_time";
    public static final String EXTRA_START_TIME = "vnd.google.fitness.start_time";
    public static final Api<NoOptions> GOALS_API = zzbvq.API;
    public static final GoalsApi GoalsApi = new zzbyv();
    public static final Api<NoOptions> HISTORY_API = zzbvv.API;
    public static final HistoryApi HistoryApi = new zzbyy();
    public static final Api<NoOptions> RECORDING_API = zzbwb.API;
    public static final RecordingApi RecordingApi = new zzbzi();
    public static final Scope SCOPE_ACTIVITY_READ = new Scope(Scopes.FITNESS_ACTIVITY_READ);
    public static final Scope SCOPE_ACTIVITY_READ_WRITE = new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE);
    public static final Scope SCOPE_BODY_READ = new Scope(Scopes.FITNESS_BODY_READ);
    public static final Scope SCOPE_BODY_READ_WRITE = new Scope(Scopes.FITNESS_BODY_READ_WRITE);
    public static final Scope SCOPE_LOCATION_READ = new Scope(Scopes.FITNESS_LOCATION_READ);
    public static final Scope SCOPE_LOCATION_READ_WRITE = new Scope(Scopes.FITNESS_LOCATION_READ_WRITE);
    public static final Scope SCOPE_NUTRITION_READ = new Scope(Scopes.FITNESS_NUTRITION_READ);
    public static final Scope SCOPE_NUTRITION_READ_WRITE = new Scope(Scopes.FITNESS_NUTRITION_READ_WRITE);
    public static final Api<NoOptions> SENSORS_API = zzbwh.API;
    public static final Api<NoOptions> SESSIONS_API = zzbwn.API;
    public static final SensorsApi SensorsApi = new zzbzp();
    public static final SessionsApi SessionsApi = new zzbzt();

    private Fitness() {
    }

    public static BleClient getBleClient(@NonNull Activity activity, GoogleSignInAccount googleSignInAccount) {
        return new BleClient(activity, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static BleClient getBleClient(@NonNull Context context, GoogleSignInAccount googleSignInAccount) {
        return new BleClient(context, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static ConfigClient getConfigClient(@NonNull Activity activity, GoogleSignInAccount googleSignInAccount) {
        return new ConfigClient(activity, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static ConfigClient getConfigClient(@NonNull Context context, GoogleSignInAccount googleSignInAccount) {
        return new ConfigClient(context, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static long getEndTime(Intent intent, TimeUnit timeUnit) {
        long longExtra = intent.getLongExtra(EXTRA_END_TIME, -1);
        if (longExtra == -1) {
            return -1;
        }
        return timeUnit.convert(longExtra, TimeUnit.MILLISECONDS);
    }

    public static GoalsClient getGoalsClient(@NonNull Activity activity, GoogleSignInAccount googleSignInAccount) {
        return new GoalsClient(activity, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static GoalsClient getGoalsClient(@NonNull Context context, GoogleSignInAccount googleSignInAccount) {
        return new GoalsClient(context, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static HistoryClient getHistoryClient(@NonNull Activity activity, GoogleSignInAccount googleSignInAccount) {
        return new HistoryClient(activity, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static HistoryClient getHistoryClient(@NonNull Context context, GoogleSignInAccount googleSignInAccount) {
        return new HistoryClient(context, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static RecordingClient getRecordingClient(@NonNull Activity activity, GoogleSignInAccount googleSignInAccount) {
        return new RecordingClient(activity, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static RecordingClient getRecordingClient(@NonNull Context context, GoogleSignInAccount googleSignInAccount) {
        return new RecordingClient(context, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static SensorsClient getSensorsClient(@NonNull Activity activity, GoogleSignInAccount googleSignInAccount) {
        return new SensorsClient(activity, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static SensorsClient getSensorsClient(@NonNull Context context, GoogleSignInAccount googleSignInAccount) {
        return new SensorsClient(context, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static SessionsClient getSessionsClient(@NonNull Activity activity, GoogleSignInAccount googleSignInAccount) {
        return new SessionsClient(activity, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static SessionsClient getSessionsClient(@NonNull Context context, GoogleSignInAccount googleSignInAccount) {
        return new SessionsClient(context, FitnessOptions.zzb(googleSignInAccount).build());
    }

    public static long getStartTime(Intent intent, TimeUnit timeUnit) {
        long longExtra = intent.getLongExtra(EXTRA_START_TIME, -1);
        if (longExtra == -1) {
            return -1;
        }
        return timeUnit.convert(longExtra, TimeUnit.MILLISECONDS);
    }
}
