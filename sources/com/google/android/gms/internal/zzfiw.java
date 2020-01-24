package com.google.android.gms.internal;

final class zzfiw extends zzfit {
    zzfiw() {
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        switch (i2) {
            case 0:
                return zzfis.zzme(i);
            case 1:
                return zzfis.zzaj(i, zzfiq.zzb(bArr, j));
            case 2:
                return zzfis.zzi(i, zzfiq.zzb(bArr, j), zzfiq.zzb(bArr, 1 + j));
            default:
                throw new AssertionError();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:81:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
        return -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int zzb(byte[] r9, long r10, int r12) {
        /*
            r0 = 16
            if (r12 >= r0) goto L_0x001b
            r0 = 0
        L_0x0005:
            int r1 = r12 - r0
            long r2 = (long) r0
            long r2 = r2 + r10
            r0 = r1
        L_0x000a:
            r1 = 0
            r4 = r2
        L_0x000c:
            if (r0 <= 0) goto L_0x002f
            r2 = 1
            long r2 = r2 + r4
            byte r1 = com.google.android.gms.internal.zzfiq.zzb(r9, r4)
            if (r1 < 0) goto L_0x002e
            int r0 = r0 + -1
            r4 = r2
            goto L_0x000c
        L_0x001b:
            r0 = 0
            r2 = r10
        L_0x001d:
            if (r0 >= r12) goto L_0x002c
            r4 = 1
            long r4 = r4 + r2
            byte r1 = com.google.android.gms.internal.zzfiq.zzb(r9, r2)
            if (r1 < 0) goto L_0x0005
            int r0 = r0 + 1
            r2 = r4
            goto L_0x001d
        L_0x002c:
            r0 = r12
            goto L_0x0005
        L_0x002e:
            r4 = r2
        L_0x002f:
            if (r0 != 0) goto L_0x0033
            r0 = 0
        L_0x0032:
            return r0
        L_0x0033:
            int r0 = r0 + -1
            r2 = -32
            if (r1 >= r2) goto L_0x0050
            if (r0 != 0) goto L_0x003d
            r0 = r1
            goto L_0x0032
        L_0x003d:
            int r0 = r0 + -1
            r2 = -62
            if (r1 < r2) goto L_0x004e
            r2 = 1
            long r2 = r2 + r4
            byte r1 = com.google.android.gms.internal.zzfiq.zzb(r9, r4)
            r4 = -65
            if (r1 <= r4) goto L_0x000a
        L_0x004e:
            r0 = -1
            goto L_0x0032
        L_0x0050:
            r2 = -16
            if (r1 >= r2) goto L_0x0087
            r2 = 2
            if (r0 >= r2) goto L_0x005c
            int r0 = zza(r9, r1, r4, r0)
            goto L_0x0032
        L_0x005c:
            int r0 = r0 + -2
            r2 = 1
            long r6 = r4 + r2
            byte r2 = com.google.android.gms.internal.zzfiq.zzb(r9, r4)
            r3 = -65
            if (r2 > r3) goto L_0x0085
            r3 = -32
            if (r1 != r3) goto L_0x0072
            r3 = -96
            if (r2 < r3) goto L_0x0085
        L_0x0072:
            r3 = -19
            if (r1 != r3) goto L_0x007a
            r1 = -96
            if (r2 >= r1) goto L_0x0085
        L_0x007a:
            r2 = 1
            long r2 = r2 + r6
            byte r1 = com.google.android.gms.internal.zzfiq.zzb(r9, r6)
            r4 = -65
            if (r1 <= r4) goto L_0x000a
        L_0x0085:
            r0 = -1
            goto L_0x0032
        L_0x0087:
            r2 = 3
            if (r0 >= r2) goto L_0x008f
            int r0 = zza(r9, r1, r4, r0)
            goto L_0x0032
        L_0x008f:
            int r0 = r0 + -3
            r2 = 1
            long r2 = r2 + r4
            byte r4 = com.google.android.gms.internal.zzfiq.zzb(r9, r4)
            r5 = -65
            if (r4 > r5) goto L_0x00bb
            int r1 = r1 << 28
            int r4 = r4 + 112
            int r1 = r1 + r4
            int r1 = r1 >> 30
            if (r1 != 0) goto L_0x00bb
            r4 = 1
            long r4 = r4 + r2
            byte r1 = com.google.android.gms.internal.zzfiq.zzb(r9, r2)
            r2 = -65
            if (r1 > r2) goto L_0x00bb
            r2 = 1
            long r2 = r2 + r4
            byte r1 = com.google.android.gms.internal.zzfiq.zzb(r9, r4)
            r4 = -65
            if (r1 <= r4) goto L_0x000a
        L_0x00bb:
            r0 = -1
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfiw.zzb(byte[], long, int):int");
    }

    /* access modifiers changed from: 0000 */
    public final int zzb(int i, byte[] bArr, int i2, int i3) {
        if ((i2 | i3 | (bArr.length - i3)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)}));
        }
        long j = (long) i2;
        return zzb(bArr, j, (int) (((long) i3) - j));
    }

    /* access modifiers changed from: 0000 */
    public final int zzb(CharSequence charSequence, byte[] bArr, int i, int i2) {
        long j;
        long j2 = (long) i;
        long j3 = j2 + ((long) i2);
        int length = charSequence.length();
        if (length > i2 || bArr.length - i2 < i) {
            throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(length - 1) + " at index " + (i + i2));
        }
        int i3 = 0;
        while (i3 < length) {
            char charAt = charSequence.charAt(i3);
            if (charAt >= 128) {
                break;
            }
            long j4 = 1 + j2;
            zzfiq.zza(bArr, j2, (byte) charAt);
            i3++;
            j2 = j4;
        }
        if (i3 == length) {
            return (int) j2;
        }
        long j5 = j2;
        while (i3 < length) {
            char charAt2 = charSequence.charAt(i3);
            if (charAt2 < 128 && j5 < j3) {
                j = 1 + j5;
                zzfiq.zza(bArr, j5, (byte) charAt2);
            } else if (charAt2 < 2048 && j5 <= j3 - 2) {
                long j6 = j5 + 1;
                zzfiq.zza(bArr, j5, (byte) ((charAt2 >>> 6) | 960));
                j = 1 + j6;
                zzfiq.zza(bArr, j6, (byte) ((charAt2 & '?') | 128));
            } else if ((charAt2 < 55296 || 57343 < charAt2) && j5 <= j3 - 3) {
                long j7 = 1 + j5;
                zzfiq.zza(bArr, j5, (byte) ((charAt2 >>> 12) | 480));
                long j8 = 1 + j7;
                zzfiq.zza(bArr, j7, (byte) (((charAt2 >>> 6) & 63) | 128));
                j = 1 + j8;
                zzfiq.zza(bArr, j8, (byte) ((charAt2 & '?') | 128));
            } else if (j5 <= j3 - 4) {
                if (i3 + 1 != length) {
                    i3++;
                    char charAt3 = charSequence.charAt(i3);
                    if (Character.isSurrogatePair(charAt2, charAt3)) {
                        int codePoint = Character.toCodePoint(charAt2, charAt3);
                        long j9 = 1 + j5;
                        zzfiq.zza(bArr, j5, (byte) ((codePoint >>> 18) | 240));
                        long j10 = 1 + j9;
                        zzfiq.zza(bArr, j9, (byte) (((codePoint >>> 12) & 63) | 128));
                        long j11 = j10 + 1;
                        zzfiq.zza(bArr, j10, (byte) (((codePoint >>> 6) & 63) | 128));
                        j = 1 + j11;
                        zzfiq.zza(bArr, j11, (byte) ((codePoint & 63) | 128));
                    }
                }
                throw new zzfiv(i3 - 1, length);
            } else if (55296 > charAt2 || charAt2 > 57343 || (i3 + 1 != length && Character.isSurrogatePair(charAt2, charSequence.charAt(i3 + 1)))) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + j5);
            } else {
                throw new zzfiv(i3, length);
            }
            i3++;
            j5 = j;
        }
        return (int) j5;
    }
}
