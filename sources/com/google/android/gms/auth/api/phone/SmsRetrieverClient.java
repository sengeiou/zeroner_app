package com.google.android.gms.auth.api.phone;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zzcz;
import com.google.android.gms.common.api.internal.zzg;
import com.google.android.gms.internal.zzawr;
import com.google.android.gms.tasks.Task;

public abstract class SmsRetrieverClient extends GoogleApi<NoOptions> implements SmsRetrieverApi {
    private static final Api<NoOptions> API = new Api<>("SmsRetriever.API", zzebg, zzebf);
    private static final zzf<zzawr> zzebf = new zzf<>();
    private static final zza<zzawr, NoOptions> zzebg = new zza();

    public SmsRetrieverClient(@NonNull Activity activity) {
        super(activity, API, null, (zzcz) new zzg());
    }

    public SmsRetrieverClient(@NonNull Context context) {
        super(context, API, null, (zzcz) new zzg());
    }

    public abstract Task<Void> startSmsRetriever();
}
