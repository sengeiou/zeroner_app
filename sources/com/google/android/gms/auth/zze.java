package com.google.android.gms.auth;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.internal.zzaxb;
import com.google.android.gms.internal.zzey;
import java.io.IOException;

final class zze implements zzj<TokenData> {
    private /* synthetic */ Bundle val$options;
    private /* synthetic */ Account zzecd;
    private /* synthetic */ String zzece;

    zze(Account account, String str, Bundle bundle) {
        this.zzecd = account;
        this.zzece = str;
        this.val$options = bundle;
    }

    public final /* synthetic */ Object zzac(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
        Bundle bundle = (Bundle) zzd.zzp(zzey.zza(iBinder).zza(this.zzecd, this.zzece, this.val$options));
        TokenData zzd = TokenData.zzd(bundle, "tokenDetails");
        if (zzd != null) {
            return zzd;
        }
        String string = bundle.getString("Error");
        Intent intent = (Intent) bundle.getParcelable("userRecoveryIntent");
        zzaxb zzfb = zzaxb.zzfb(string);
        if (zzaxb.zza(zzfb)) {
            String valueOf = String.valueOf(zzfb);
            zzd.zzecc.zzf("GoogleAuthUtil", new StringBuilder(String.valueOf(valueOf).length() + 31).append("isUserRecoverableError status: ").append(valueOf).toString());
            throw new UserRecoverableAuthException(string, intent);
        }
        if (zzaxb.NETWORK_ERROR.equals(zzfb) || zzaxb.SERVICE_UNAVAILABLE.equals(zzfb)) {
            throw new IOException(string);
        }
        throw new GoogleAuthException(string);
    }
}
