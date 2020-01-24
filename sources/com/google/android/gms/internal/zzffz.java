package com.google.android.gms.internal;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.apache.commons.codec.CharEncoding;

public final class zzffz {
    public static final byte[] EMPTY_BYTE_ARRAY;
    private static Charset ISO_8859_1 = Charset.forName(CharEncoding.ISO_8859_1);
    static final Charset UTF_8 = Charset.forName("UTF-8");
    private static ByteBuffer zzphu;
    private static zzffb zzphv = zzffb.zzbb(EMPTY_BYTE_ARRAY);

    static {
        byte[] bArr = new byte[0];
        EMPTY_BYTE_ARRAY = bArr;
        zzphu = ByteBuffer.wrap(bArr);
    }

    static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static int hashCode(byte[] bArr) {
        int length = bArr.length;
        int zza = zza(length, bArr, 0, length);
        if (zza == 0) {
            return 1;
        }
        return zza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    static <T> T zzc(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static int zzde(long j) {
        return (int) ((j >>> 32) ^ j);
    }

    public static int zzdg(boolean z) {
        return z ? 1231 : 1237;
    }

    static boolean zzh(zzfhe zzfhe) {
        return false;
    }
}
