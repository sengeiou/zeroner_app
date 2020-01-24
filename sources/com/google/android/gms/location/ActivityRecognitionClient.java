package com.google.android.gms.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zzcz;
import com.google.android.gms.common.api.internal.zzg;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.tasks.Task;

public class ActivityRecognitionClient extends GoogleApi<NoOptions> {
    public ActivityRecognitionClient(@NonNull Activity activity) {
        super(activity, LocationServices.API, null, (zzcz) new zzg());
    }

    public ActivityRecognitionClient(@NonNull Context context) {
        super(context, LocationServices.API, null, (zzcz) new zzg());
    }

    @RequiresPermission("com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    public Task<Void> removeActivityUpdates(PendingIntent pendingIntent) {
        return zzbj.zzb(ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(zzago(), pendingIntent));
    }

    @RequiresPermission("com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    public Task<Void> requestActivityUpdates(long j, PendingIntent pendingIntent) {
        return zzbj.zzb(ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(zzago(), j, pendingIntent));
    }
}
