package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;

public class AvailabilityException extends Exception {
    private final ArrayMap<zzh<?>, ConnectionResult> zzflw;

    public AvailabilityException(ArrayMap<zzh<?>, ConnectionResult> arrayMap) {
        this.zzflw = arrayMap;
    }

    public ConnectionResult getConnectionResult(GoogleApi<? extends ApiOptions> googleApi) {
        zzh zzagn = googleApi.zzagn();
        zzbq.checkArgument(this.zzflw.get(zzagn) != null, "The given API was not part of the availability request.");
        return (ConnectionResult) this.zzflw.get(zzagn);
    }

    public String getMessage() {
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        for (zzh zzh : this.zzflw.keySet()) {
            ConnectionResult connectionResult = (ConnectionResult) this.zzflw.get(zzh);
            if (connectionResult.isSuccess()) {
                z = false;
            }
            String zzagy = zzh.zzagy();
            String valueOf = String.valueOf(connectionResult);
            arrayList.add(new StringBuilder(String.valueOf(zzagy).length() + 2 + String.valueOf(valueOf).length()).append(zzagy).append(": ").append(valueOf).toString());
        }
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append("None of the queried APIs are available. ");
        } else {
            sb.append("Some of the queried APIs are unavailable. ");
        }
        sb.append(TextUtils.join("; ", arrayList));
        return sb.toString();
    }

    public final ArrayMap<zzh<?>, ConnectionResult> zzagj() {
        return this.zzflw;
    }
}
