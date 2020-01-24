package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzat;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzl;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.apache.commons.codec.CharEncoding;

abstract class zzh extends zzau {
    private int zzflb;

    protected zzh(byte[] bArr) {
        boolean z = false;
        if (bArr.length != 25) {
            int length = bArr.length;
            String zza = zzl.zza(bArr, 0, bArr.length, false);
            Log.wtf("GoogleCertificates", new StringBuilder(String.valueOf(zza).length() + 51).append("Cert hash data has incorrect length (").append(length).append("):\n").append(zza).toString(), new Exception());
            bArr = Arrays.copyOfRange(bArr, 0, 25);
            if (bArr.length == 25) {
                z = true;
            }
            zzbq.checkArgument(z, "cert hash data has incorrect length. length=" + bArr.length);
        }
        this.zzflb = Arrays.hashCode(bArr);
    }

    protected static byte[] zzfx(String str) {
        try {
            return str.getBytes(CharEncoding.ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof zzat)) {
            return false;
        }
        try {
            zzat zzat = (zzat) obj;
            if (zzat.zzagb() != hashCode()) {
                return false;
            }
            IObjectWrapper zzaga = zzat.zzaga();
            if (zzaga == null) {
                return false;
            }
            return Arrays.equals(getBytes(), (byte[]) zzn.zzx(zzaga));
        } catch (RemoteException e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract byte[] getBytes();

    public int hashCode() {
        return this.zzflb;
    }

    public final IObjectWrapper zzaga() {
        return zzn.zzz(getBytes());
    }

    public final int zzagb() {
        return hashCode();
    }
}
