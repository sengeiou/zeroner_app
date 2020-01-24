package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.internal.zzdd;
import com.google.android.gms.tasks.Task;

public final class zzaws extends SmsRetrieverClient {
    public zzaws(@NonNull Activity activity) {
        super(activity);
    }

    public zzaws(@NonNull Context context) {
        super(context);
    }

    public final Task<Void> startSmsRetriever() {
        return zzb((zzdd<A, TResult>) new zzawt<A,TResult>(this));
    }
}
