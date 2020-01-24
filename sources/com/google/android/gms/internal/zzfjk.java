package com.google.android.gms.internal;

import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

public final class zzfjk {
    private final ByteBuffer buffer;

    private zzfjk(ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzfjk(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    private static int zza(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int i3;
        int length = charSequence.length();
        int i4 = 0;
        int i5 = i + i2;
        while (i4 < length && i4 + i < i5) {
            char charAt = charSequence.charAt(i4);
            if (charAt >= 128) {
                break;
            }
            bArr[i + i4] = (byte) charAt;
            i4++;
        }
        if (i4 == length) {
            return i + length;
        }
        int i6 = i + i4;
        while (i4 < length) {
            char charAt2 = charSequence.charAt(i4);
            if (charAt2 < 128 && i6 < i5) {
                i3 = i6 + 1;
                bArr[i6] = (byte) charAt2;
            } else if (charAt2 < 2048 && i6 <= i5 - 2) {
                int i7 = i6 + 1;
                bArr[i6] = (byte) ((charAt2 >>> 6) | 960);
                i3 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 & '?') | 128);
            } else if ((charAt2 < 55296 || 57343 < charAt2) && i6 <= i5 - 3) {
                int i8 = i6 + 1;
                bArr[i6] = (byte) ((charAt2 >>> 12) | 480);
                int i9 = i8 + 1;
                bArr[i8] = (byte) (((charAt2 >>> 6) & 63) | 128);
                i3 = i9 + 1;
                bArr[i9] = (byte) ((charAt2 & '?') | 128);
            } else if (i6 <= i5 - 4) {
                if (i4 + 1 != charSequence.length()) {
                    i4++;
                    char charAt3 = charSequence.charAt(i4);
                    if (Character.isSurrogatePair(charAt2, charAt3)) {
                        int codePoint = Character.toCodePoint(charAt2, charAt3);
                        int i10 = i6 + 1;
                        bArr[i6] = (byte) ((codePoint >>> 18) | 240);
                        int i11 = i10 + 1;
                        bArr[i10] = (byte) (((codePoint >>> 12) & 63) | 128);
                        int i12 = i11 + 1;
                        bArr[i11] = (byte) (((codePoint >>> 6) & 63) | 128);
                        i3 = i12 + 1;
                        bArr[i12] = (byte) ((codePoint & 63) | 128);
                    }
                }
                throw new IllegalArgumentException("Unpaired surrogate at index " + (i4 - 1));
            } else {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + i6);
            }
            i4++;
            i6 = i3;
        }
        return i6;
    }

    private static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(zza(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
            } catch (ArrayIndexOutOfBoundsException e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        } else {
            zzb(charSequence, byteBuffer);
        }
    }

    public static int zzad(int i, int i2) {
        return zzlg(i) + zzlh(i2);
    }

    public static int zzb(int i, zzfjs zzfjs) {
        int zzlg = zzlg(i);
        int zzho = zzfjs.zzho();
        return zzlg + zzho + zzlp(zzho);
    }

    private static void zzb(CharSequence charSequence, ByteBuffer byteBuffer) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 128) {
                byteBuffer.put((byte) charAt);
            } else if (charAt < 2048) {
                byteBuffer.put((byte) ((charAt >>> 6) | 960));
                byteBuffer.put((byte) ((charAt & '?') | 128));
            } else if (charAt < 55296 || 57343 < charAt) {
                byteBuffer.put((byte) ((charAt >>> 12) | 480));
                byteBuffer.put((byte) (((charAt >>> 6) & 63) | 128));
                byteBuffer.put((byte) ((charAt & '?') | 128));
            } else {
                if (i + 1 != charSequence.length()) {
                    i++;
                    char charAt2 = charSequence.charAt(i);
                    if (Character.isSurrogatePair(charAt, charAt2)) {
                        int codePoint = Character.toCodePoint(charAt, charAt2);
                        byteBuffer.put((byte) ((codePoint >>> 18) | 240));
                        byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                        byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                        byteBuffer.put((byte) ((codePoint & 63) | 128));
                    }
                }
                throw new IllegalArgumentException("Unpaired surrogate at index " + (i - 1));
            }
            i++;
        }
    }

    public static zzfjk zzbf(byte[] bArr) {
        return zzo(bArr, 0, bArr.length);
    }

    public static int zzbg(byte[] bArr) {
        return zzlp(bArr.length) + bArr.length;
    }

    public static int zzc(int i, long j) {
        return zzlg(i) + zzdi(j);
    }

    public static int zzd(int i, byte[] bArr) {
        return zzlg(i) + zzbg(bArr);
    }

    private static int zzd(CharSequence charSequence) {
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
                                throw new IllegalArgumentException("Unpaired surrogate at index " + i3);
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

    private static long zzdb(long j) {
        return (j << 1) ^ (j >> 63);
    }

    private final void zzdh(long j) throws IOException {
        while ((-128 & j) != 0) {
            zzmh((((int) j) & Opcodes.NEG_FLOAT) | 128);
            j >>>= 7;
        }
        zzmh((int) j);
    }

    public static int zzdi(long j) {
        if ((-128 & j) == 0) {
            return 1;
        }
        if ((-16384 & j) == 0) {
            return 2;
        }
        if ((-2097152 & j) == 0) {
            return 3;
        }
        if ((-268435456 & j) == 0) {
            return 4;
        }
        if ((-34359738368L & j) == 0) {
            return 5;
        }
        if ((-4398046511104L & j) == 0) {
            return 6;
        }
        if ((-562949953421312L & j) == 0) {
            return 7;
        }
        if ((-72057594037927936L & j) == 0) {
            return 8;
        }
        return (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    private final void zzdj(long j) throws IOException {
        if (this.buffer.remaining() < 8) {
            throw new zzfjl(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.putLong(j);
    }

    public static int zzh(int i, long j) {
        return zzlg(i) + zzdi(zzdb(j));
    }

    public static int zzlg(int i) {
        return zzlp(i << 3);
    }

    public static int zzlh(int i) {
        if (i >= 0) {
            return zzlp(i);
        }
        return 10;
    }

    public static int zzlo(int i) {
        return (i << 1) ^ (i >> 31);
    }

    public static int zzlp(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (-268435456 & i) == 0 ? 4 : 5;
    }

    private final void zzmh(int i) throws IOException {
        byte b = (byte) i;
        if (!this.buffer.hasRemaining()) {
            throw new zzfjl(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.put(b);
    }

    public static int zzo(int i, String str) {
        return zzlg(i) + zztt(str);
    }

    public static zzfjk zzo(byte[] bArr, int i, int i2) {
        return new zzfjk(bArr, 0, i2);
    }

    public static int zztt(String str) {
        int zzd = zzd(str);
        return zzd + zzlp(zzd);
    }

    public final void zza(int i, double d) throws IOException {
        zzz(i, 1);
        zzdj(Double.doubleToLongBits(d));
    }

    public final void zza(int i, long j) throws IOException {
        zzz(i, 0);
        zzdh(j);
    }

    public final void zza(int i, zzfjs zzfjs) throws IOException {
        zzz(i, 2);
        zzb(zzfjs);
    }

    public final void zzaa(int i, int i2) throws IOException {
        zzz(i, 0);
        if (i2 >= 0) {
            zzmi(i2);
        } else {
            zzdh((long) i2);
        }
    }

    public final void zzb(int i, long j) throws IOException {
        zzz(i, 1);
        zzdj(j);
    }

    public final void zzb(zzfjs zzfjs) throws IOException {
        zzmi(zzfjs.zzdam());
        zzfjs.zza(this);
    }

    public final void zzbh(byte[] bArr) throws IOException {
        int length = bArr.length;
        if (this.buffer.remaining() >= length) {
            this.buffer.put(bArr, 0, length);
            return;
        }
        throw new zzfjl(this.buffer.position(), this.buffer.limit());
    }

    public final void zzc(int i, float f) throws IOException {
        zzz(i, 5);
        int floatToIntBits = Float.floatToIntBits(f);
        if (this.buffer.remaining() < 4) {
            throw new zzfjl(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.putInt(floatToIntBits);
    }

    public final void zzc(int i, byte[] bArr) throws IOException {
        zzz(i, 2);
        zzmi(bArr.length);
        zzbh(bArr);
    }

    public final void zzcwt() {
        if (this.buffer.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", new Object[]{Integer.valueOf(this.buffer.remaining())}));
        }
    }

    public final void zzf(int i, long j) throws IOException {
        zzz(i, 0);
        zzdh(j);
    }

    public final void zzg(int i, long j) throws IOException {
        zzz(i, 0);
        zzdh(zzdb(j));
    }

    public final void zzl(int i, boolean z) throws IOException {
        int i2 = 0;
        zzz(i, 0);
        if (z) {
            i2 = 1;
        }
        byte b = (byte) i2;
        if (!this.buffer.hasRemaining()) {
            throw new zzfjl(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.put(b);
    }

    public final void zzmi(int i) throws IOException {
        while ((i & -128) != 0) {
            zzmh((i & Opcodes.NEG_FLOAT) | 128);
            i >>>= 7;
        }
        zzmh(i);
    }

    public final void zzn(int i, String str) throws IOException {
        zzz(i, 2);
        try {
            int zzlp = zzlp(str.length());
            if (zzlp == zzlp(str.length() * 3)) {
                int position = this.buffer.position();
                if (this.buffer.remaining() < zzlp) {
                    throw new zzfjl(zzlp + position, this.buffer.limit());
                }
                this.buffer.position(position + zzlp);
                zza((CharSequence) str, this.buffer);
                int position2 = this.buffer.position();
                this.buffer.position(position);
                zzmi((position2 - position) - zzlp);
                this.buffer.position(position2);
                return;
            }
            zzmi(zzd(str));
            zza((CharSequence) str, this.buffer);
        } catch (BufferOverflowException e) {
            zzfjl zzfjl = new zzfjl(this.buffer.position(), this.buffer.limit());
            zzfjl.initCause(e);
            throw zzfjl;
        }
    }

    public final void zzz(int i, int i2) throws IOException {
        zzmi((i << 3) | i2);
    }
}
