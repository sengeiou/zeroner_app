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
import java.util.List;

public class GeofencingClient extends GoogleApi<NoOptions> {
    public GeofencingClient(@NonNull Activity activity) {
        super(activity, LocationServices.API, null, (zzcz) new zzg());
    }

    public GeofencingClient(@NonNull Context context) {
        super(context, LocationServices.API, null, (zzcz) new zzg());
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public Task<Void> addGeofences(GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        return zzbj.zzb(LocationServices.GeofencingApi.addGeofences(zzago(), geofencingRequest, pendingIntent));
    }

    public Task<Void> removeGeofences(PendingIntent pendingIntent) {
        return zzbj.zzb(LocationServices.GeofencingApi.removeGeofences(zzago(), pendingIntent));
    }

    public Task<Void> removeGeofences(List<String> list) {
        return zzbj.zzb(LocationServices.GeofencingApi.removeGeofences(zzago(), list));
    }
}
