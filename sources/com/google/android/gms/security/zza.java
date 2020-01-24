package com.google.android.gms.security;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.zzf;
import com.google.android.gms.security.ProviderInstaller.ProviderInstallListener;

final class zza extends AsyncTask<Void, Void, Integer> {
    private /* synthetic */ Context val$context;
    private /* synthetic */ ProviderInstallListener zzkbp;

    zza(Context context, ProviderInstallListener providerInstallListener) {
        this.val$context = context;
        this.zzkbp = providerInstallListener;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final Integer doInBackground(Void... voidArr) {
        try {
            ProviderInstaller.installIfNeeded(this.val$context);
            return Integer.valueOf(0);
        } catch (GooglePlayServicesRepairableException e) {
            return Integer.valueOf(e.getConnectionStatusCode());
        } catch (GooglePlayServicesNotAvailableException e2) {
            return Integer.valueOf(e2.errorCode);
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        Integer num = (Integer) obj;
        if (num.intValue() == 0) {
            this.zzkbp.onProviderInstalled();
            return;
        }
        ProviderInstaller.zzkbn;
        this.zzkbp.onProviderInstallFailed(num.intValue(), zzf.zza(this.val$context, num.intValue(), "pi"));
    }
}
