package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.os.Binder;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;

public final class zzw extends zzr {
    private final Context mContext;

    public zzw(Context context) {
        this.mContext = context;
    }

    private final void zzabq() {
        if (!GooglePlayServicesUtil.zzf(this.mContext, Binder.getCallingUid())) {
            throw new SecurityException("Calling UID " + Binder.getCallingUid() + " is not Google Play services.");
        }
    }

    public final void zzabo() {
        zzabq();
        zzz zzbt = zzz.zzbt(this.mContext);
        GoogleSignInAccount zzabt = zzbt.zzabt();
        GoogleSignInOptions googleSignInOptions = GoogleSignInOptions.DEFAULT_SIGN_IN;
        if (zzabt != null) {
            googleSignInOptions = zzbt.zzabu();
        }
        GoogleApiClient build = new Builder(this.mContext).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
        try {
            if (build.blockingConnect().isSuccess()) {
                if (zzabt != null) {
                    Auth.GoogleSignInApi.revokeAccess(build);
                } else {
                    build.clearDefaultAccountAndReconnect();
                }
            }
        } finally {
            build.disconnect();
        }
    }

    public final void zzabp() {
        zzabq();
        zzo.zzbr(this.mContext).clear();
    }
}
