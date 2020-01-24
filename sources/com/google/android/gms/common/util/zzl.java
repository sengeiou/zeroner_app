package com.google.android.gms.common.util;

import com.google.common.primitives.UnsignedBytes;

public final class zzl {
    public static String zza(byte[] bArr, int i, int i2, boolean z) {
        if (bArr == null || bArr.length == 0 || i2 <= 0 || i2 > bArr.length) {
            return null;
        }
        StringBuilder sb = new StringBuilder((((i2 + 16) - 1) / 16) * 57);
        int i3 = 0;
        int i4 = i2;
        int i5 = 0;
        while (i4 > 0) {
            if (i5 == 0) {
                if (i2 < 65536) {
                    sb.append(String.format("%04X:", new Object[]{Integer.valueOf(i3)}));
                } else {
                    sb.append(String.format("%08X:", new Object[]{Integer.valueOf(i3)}));
                }
            } else if (i5 == 8) {
                sb.append(" -");
            }
            sb.append(String.format(" %02X", new Object[]{Integer.valueOf(bArr[i3] & UnsignedBytes.MAX_VALUE)}));
            int i6 = i4 - 1;
            int i7 = i5 + 1;
            if (i7 == 16 || i6 == 0) {
                sb.append(10);
                i7 = 0;
            }
            i3++;
            i5 = i7;
            i4 = i6;
        }
        return sb.toString();
    }
}
