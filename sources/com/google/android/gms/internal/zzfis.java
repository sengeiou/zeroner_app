package com.google.android.gms.internal;

final class zzfis {
    private static final zzfit zzplo = (zzfiq.zzczx() && zzfiq.zzczy() ? new zzfiw() : new zzfiu());

    static int zza(CharSequence charSequence, byte[] bArr, int i, int i2) {
        return zzplo.zzb(charSequence, bArr, i, i2);
    }

    /* access modifiers changed from: private */
    public static int zzaj(int i, int i2) {
        if (i > -12 || i2 > -65) {
            return -1;
        }
        return (i2 << 8) ^ i;
    }

    static int zzd(CharSequence charSequence) {
        int i;
        int i2 = 0;
        int length = charSequence.length();
        int i3 = 0;
        while (i3 < length && charSequence.charAt(i3) < 128) {
            i3++;
        }
        int i4 = length;
        while (true) {
            if (i3 >= length) {
                i = i4;
                break;
            }
            char charAt = charSequence.charAt(i3);
            if (charAt < 2048) {
                i4 += (127 - charAt) >>> 31;
                i3++;
            } else {
                int length2 = charSequence.length();
                while (i3 < length2) {
                    char charAt2 = charSequence.charAt(i3);
                    if (charAt2 < 2048) {
                        i2 += (127 - charAt2) >>> 31;
                    } else {
                        i2 += 2;
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i3) < 65536) {
                                throw new zzfiv(i3, length2);
                            }
                            i3++;
                        }
                    }
                    i3++;
                }
                i = i4 + i2;
            }
        }
        if (i >= length) {
            return i;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (((long) i) + 4294967296L));
    }

    /* access modifiers changed from: private */
    public static int zzi(int i, int i2, int i3) {
        if (i > -12 || i2 > -65 || i3 > -65) {
            return -1;
        }
        return ((i2 << 8) ^ i) ^ (i3 << 16);
    }

    public static boolean zzk(byte[] bArr, int i, int i2) {
        return zzplo.zzb(0, bArr, i, i2) == 0;
    }

    /* access modifiers changed from: private */
    public static int zzl(byte[] bArr, int i, int i2) {
        byte b = bArr[i - 1];
        switch (i2 - i) {
            case 0:
                return zzme(b);
            case 1:
                return zzaj(b, bArr[i]);
            case 2:
                return zzi(b, bArr[i], bArr[i + 1]);
            default:
                throw new AssertionError();
        }
    }

    /* access modifiers changed from: private */
    public static int zzme(int i) {
        if (i > -12) {
            return -1;
        }
        return i;
    }
}
