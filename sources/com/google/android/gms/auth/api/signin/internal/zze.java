package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.internal.zzbgg;

public final class zze {
    private static zzbgg zzehz = new zzbgg("GoogleSignInCommon", new String[0]);

    @Nullable
    public static GoogleSignInResult getSignInResultFromIntent(Intent intent) {
        if (intent == null || (!intent.hasExtra("googleSignInStatus") && !intent.hasExtra("googleSignInAccount"))) {
            return null;
        }
        GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) intent.getParcelableExtra("googleSignInAccount");
        Status status = (Status) intent.getParcelableExtra("googleSignInStatus");
        if (googleSignInAccount != null) {
            status = Status.zzfni;
        }
        return new GoogleSignInResult(googleSignInAccount, status);
    }

    public static Intent zza(Context context, GoogleSignInOptions googleSignInOptions) {
        zzehz.zzb("getSignInIntent()", new Object[0]);
        SignInConfiguration signInConfiguration = new SignInConfiguration(context.getPackageName(), googleSignInOptions);
        Intent intent = new Intent("com.google.android.gms.auth.GOOGLE_SIGN_IN");
        intent.setPackage(context.getPackageName());
        intent.setClass(context, SignInHubActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("config", signInConfiguration);
        intent.putExtra("config", bundle);
        return intent;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x009b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.common.api.OptionalPendingResult<com.google.android.gms.auth.api.signin.GoogleSignInResult> zza(com.google.android.gms.common.api.GoogleApiClient r5, android.content.Context r6, com.google.android.gms.auth.api.signin.GoogleSignInOptions r7, boolean r8) {
        /*
            r1 = 0
            r2 = 0
            com.google.android.gms.internal.zzbgg r0 = zzehz
            java.lang.String r3 = "silentSignIn()"
            java.lang.Object[] r4 = new java.lang.Object[r2]
            r0.zzb(r3, r4)
            com.google.android.gms.internal.zzbgg r0 = zzehz
            java.lang.String r3 = "getEligibleSavedSignInResult()"
            java.lang.Object[] r4 = new java.lang.Object[r2]
            r0.zzb(r3, r4)
            com.google.android.gms.common.internal.zzbq.checkNotNull(r7)
            com.google.android.gms.auth.api.signin.internal.zzo r0 = com.google.android.gms.auth.api.signin.internal.zzo.zzbr(r6)
            com.google.android.gms.auth.api.signin.GoogleSignInOptions r3 = r0.zzabm()
            if (r3 == 0) goto L_0x0099
            android.accounts.Account r0 = r3.getAccount()
            android.accounts.Account r4 = r7.getAccount()
            if (r0 != 0) goto L_0x0094
            if (r4 != 0) goto L_0x0092
            r0 = 1
        L_0x0030:
            if (r0 == 0) goto L_0x0099
            boolean r0 = r7.zzabf()
            if (r0 != 0) goto L_0x0099
            boolean r0 = r7.isIdTokenRequested()
            if (r0 == 0) goto L_0x0052
            boolean r0 = r3.isIdTokenRequested()
            if (r0 == 0) goto L_0x0099
            java.lang.String r0 = r7.getServerClientId()
            java.lang.String r4 = r3.getServerClientId()
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0099
        L_0x0052:
            java.util.HashSet r0 = new java.util.HashSet
            java.util.ArrayList r3 = r3.zzabe()
            r0.<init>(r3)
            java.util.HashSet r3 = new java.util.HashSet
            java.util.ArrayList r4 = r7.zzabe()
            r3.<init>(r4)
            boolean r0 = r0.containsAll(r3)
            if (r0 == 0) goto L_0x0099
            com.google.android.gms.auth.api.signin.internal.zzo r0 = com.google.android.gms.auth.api.signin.internal.zzo.zzbr(r6)
            com.google.android.gms.auth.api.signin.GoogleSignInAccount r3 = r0.zzabl()
            if (r3 == 0) goto L_0x0099
            boolean r0 = r3.zza()
            if (r0 != 0) goto L_0x0099
            com.google.android.gms.auth.api.signin.GoogleSignInResult r0 = new com.google.android.gms.auth.api.signin.GoogleSignInResult
            com.google.android.gms.common.api.Status r4 = com.google.android.gms.common.api.Status.zzfni
            r0.<init>(r3, r4)
        L_0x0081:
            if (r0 == 0) goto L_0x009b
            com.google.android.gms.internal.zzbgg r1 = zzehz
            java.lang.String r3 = "Eligible saved sign in result found"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r1.zzb(r3, r2)
            com.google.android.gms.common.api.OptionalPendingResult r0 = com.google.android.gms.common.api.PendingResults.zzb(r0, r5)
        L_0x0091:
            return r0
        L_0x0092:
            r0 = r2
            goto L_0x0030
        L_0x0094:
            boolean r0 = r0.equals(r4)
            goto L_0x0030
        L_0x0099:
            r0 = r1
            goto L_0x0081
        L_0x009b:
            if (r8 == 0) goto L_0x00ad
            com.google.android.gms.auth.api.signin.GoogleSignInResult r0 = new com.google.android.gms.auth.api.signin.GoogleSignInResult
            com.google.android.gms.common.api.Status r2 = new com.google.android.gms.common.api.Status
            r3 = 4
            r2.<init>(r3)
            r0.<init>(r1, r2)
            com.google.android.gms.common.api.OptionalPendingResult r0 = com.google.android.gms.common.api.PendingResults.zzb(r0, r5)
            goto L_0x0091
        L_0x00ad:
            com.google.android.gms.internal.zzbgg r0 = zzehz
            java.lang.String r1 = "trySilentSignIn()"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r0.zzb(r1, r2)
            com.google.android.gms.auth.api.signin.internal.zzf r0 = new com.google.android.gms.auth.api.signin.internal.zzf
            r0.<init>(r5, r6, r7)
            com.google.android.gms.common.api.internal.zzm r1 = r5.zzd(r0)
            com.google.android.gms.common.api.internal.zzco r0 = new com.google.android.gms.common.api.internal.zzco
            r0.<init>(r1)
            goto L_0x0091
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.internal.zze.zza(com.google.android.gms.common.api.GoogleApiClient, android.content.Context, com.google.android.gms.auth.api.signin.GoogleSignInOptions, boolean):com.google.android.gms.common.api.OptionalPendingResult");
    }

    public static PendingResult<Status> zza(GoogleApiClient googleApiClient, Context context, boolean z) {
        zzehz.zzb("Signing out", new Object[0]);
        zzbq(context);
        return z ? PendingResults.zza(Status.zzfni, googleApiClient) : googleApiClient.zze(new zzh(googleApiClient));
    }

    public static Intent zzb(Context context, GoogleSignInOptions googleSignInOptions) {
        zzehz.zzb("getFallbackSignInIntent()", new Object[0]);
        Intent zza = zza(context, googleSignInOptions);
        zza.setAction("com.google.android.gms.auth.APPAUTH_SIGN_IN");
        return zza;
    }

    public static PendingResult<Status> zzb(GoogleApiClient googleApiClient, Context context, boolean z) {
        zzehz.zzb("Revoking access", new Object[0]);
        zzbq(context);
        return z ? PendingResults.zza(Status.zzfnn, googleApiClient) : googleApiClient.zze(new zzj(googleApiClient));
    }

    private static void zzbq(Context context) {
        zzo.zzbr(context).clear();
        for (GoogleApiClient zzags : GoogleApiClient.zzagr()) {
            zzags.zzags();
        }
        zzbm.zzair();
    }

    public static Intent zzc(Context context, GoogleSignInOptions googleSignInOptions) {
        zzehz.zzb("getNoImplementationSignInIntent()", new Object[0]);
        Intent zza = zza(context, googleSignInOptions);
        zza.setAction("com.google.android.gms.auth.NO_IMPL");
        return zza;
    }
}
