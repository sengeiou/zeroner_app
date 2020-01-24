package com.google.android.gms.internal;

import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class zzffg extends zzfer {
    private static final Logger logger = Logger.getLogger(zzffg.class.getName());
    /* access modifiers changed from: private */
    public static final boolean zzpfz = zzfiq.zzczx();
    zzffi zzpga = this;

    static abstract class zza extends zzffg {
        final byte[] buffer;
        final int limit;
        int position;
        int zzpgb;

        zza(int i) {
            super();
            if (i < 0) {
                throw new IllegalArgumentException("bufferSize must be >= 0");
            }
            this.buffer = new byte[Math.max(i, 20)];
            this.limit = this.buffer.length;
        }

        /* access modifiers changed from: 0000 */
        public final void zzah(int i, int i2) {
            zzlq((i << 3) | i2);
        }

        /* access modifiers changed from: 0000 */
        public final void zzc(byte b) {
            byte[] bArr = this.buffer;
            int i = this.position;
            this.position = i + 1;
            bArr[i] = b;
            this.zzpgb++;
        }

        public final int zzcws() {
            throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array or ByteBuffer.");
        }

        /* access modifiers changed from: 0000 */
        public final void zzdc(long j) {
            if (zzffg.zzpfz) {
                long j2 = (long) this.position;
                while ((j & -128) != 0) {
                    byte[] bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    zzfiq.zza(bArr, (long) i, (byte) ((((int) j) & Opcodes.NEG_FLOAT) | 128));
                    j >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                zzfiq.zza(bArr2, (long) i2, (byte) ((int) j));
                this.zzpgb = ((int) (((long) this.position) - j2)) + this.zzpgb;
                return;
            }
            while ((j & -128) != 0) {
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr3[i3] = (byte) ((((int) j) & Opcodes.NEG_FLOAT) | 128);
                this.zzpgb++;
                j >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr4[i4] = (byte) ((int) j);
            this.zzpgb++;
        }

        /* access modifiers changed from: 0000 */
        public final void zzdd(long j) {
            byte[] bArr = this.buffer;
            int i = this.position;
            this.position = i + 1;
            bArr[i] = (byte) ((int) (j & 255));
            byte[] bArr2 = this.buffer;
            int i2 = this.position;
            this.position = i2 + 1;
            bArr2[i2] = (byte) ((int) ((j >> 8) & 255));
            byte[] bArr3 = this.buffer;
            int i3 = this.position;
            this.position = i3 + 1;
            bArr3[i3] = (byte) ((int) ((j >> 16) & 255));
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr4[i4] = (byte) ((int) ((j >> 24) & 255));
            byte[] bArr5 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            bArr5[i5] = (byte) ((int) (j >> 32));
            byte[] bArr6 = this.buffer;
            int i6 = this.position;
            this.position = i6 + 1;
            bArr6[i6] = (byte) ((int) (j >> 40));
            byte[] bArr7 = this.buffer;
            int i7 = this.position;
            this.position = i7 + 1;
            bArr7[i7] = (byte) ((int) (j >> 48));
            byte[] bArr8 = this.buffer;
            int i8 = this.position;
            this.position = i8 + 1;
            bArr8[i8] = (byte) ((int) (j >> 56));
            this.zzpgb += 8;
        }

        /* access modifiers changed from: 0000 */
        public final void zzlq(int i) {
            if (zzffg.zzpfz) {
                long j = (long) this.position;
                while ((i & -128) != 0) {
                    byte[] bArr = this.buffer;
                    int i2 = this.position;
                    this.position = i2 + 1;
                    zzfiq.zza(bArr, (long) i2, (byte) ((i & Opcodes.NEG_FLOAT) | 128));
                    i >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                zzfiq.zza(bArr2, (long) i3, (byte) i);
                this.zzpgb = ((int) (((long) this.position) - j)) + this.zzpgb;
                return;
            }
            while ((i & -128) != 0) {
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr3[i4] = (byte) ((i & Opcodes.NEG_FLOAT) | 128);
                this.zzpgb++;
                i >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            bArr4[i5] = (byte) i;
            this.zzpgb++;
        }

        /* JADX WARNING: type inference failed for: r0v3, types: [byte[]] */
        /* JADX WARNING: type inference failed for: r2v9, types: [int, byte] */
        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Incorrect type for immutable var: ssa=byte[], code=null, for r0v3, types: [byte[]] */
        /* JADX WARNING: Incorrect type for immutable var: ssa=int, code=null, for r2v9, types: [int, byte] */
        /* JADX WARNING: Unknown variable types count: 2 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void zzlr(int r4) {
            /*
                r3 = this;
                byte[] r0 = r3.buffer
                int r1 = r3.position
                int r2 = r1 + 1
                r3.position = r2
                byte r2 = (byte) r4
                r0[r1] = r2
                byte[] r0 = r3.buffer
                int r1 = r3.position
                int r2 = r1 + 1
                r3.position = r2
                int r2 = r4 >> 8
                byte r2 = (byte) r2
                r0[r1] = r2
                byte[] r0 = r3.buffer
                int r1 = r3.position
                int r2 = r1 + 1
                r3.position = r2
                int r2 = r4 >> 16
                byte r2 = (byte) r2
                r0[r1] = r2
                byte[] r0 = r3.buffer
                int r1 = r3.position
                int r2 = r1 + 1
                r3.position = r2
                int r2 = r4 >> 24
                r0[r1] = r2
                int r0 = r3.zzpgb
                int r0 = r0 + 4
                r3.zzpgb = r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzffg.zza.zzlr(int):void");
        }
    }

    static class zzb extends zzffg {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zzb(byte[] bArr, int i, int i2) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            } else if ((i | i2 | (bArr.length - (i + i2))) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)}));
            } else {
                this.buffer = bArr;
                this.offset = i;
                this.position = i;
                this.limit = i + i2;
            }
        }

        public void flush() {
        }

        public final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i2)}), e);
            }
        }

        public final void zza(int i, long j) throws IOException {
            zzz(i, 0);
            zzct(j);
        }

        public final void zza(int i, zzfes zzfes) throws IOException {
            zzz(i, 2);
            zzay(zzfes);
        }

        public final void zza(int i, zzfhe zzfhe) throws IOException {
            zzz(i, 2);
            zze(zzfhe);
        }

        public final void zzaa(int i, int i2) throws IOException {
            zzz(i, 0);
            zzlc(i2);
        }

        public final void zzab(int i, int i2) throws IOException {
            zzz(i, 0);
            zzld(i2);
        }

        public final void zzac(int i, int i2) throws IOException {
            zzz(i, 5);
            zzlf(i2);
        }

        public final void zzay(zzfes zzfes) throws IOException {
            zzld(zzfes.size());
            zzfes.zza(this);
        }

        public final void zzb(byte b) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = b;
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        public final void zzb(int i, long j) throws IOException {
            zzz(i, 1);
            zzcv(j);
        }

        public final void zzb(int i, zzfes zzfes) throws IOException {
            zzz(1, 3);
            zzab(2, i);
            zza(3, zzfes);
            zzz(1, 4);
        }

        public final void zzb(int i, zzfhe zzfhe) throws IOException {
            zzz(1, 3);
            zzab(2, i);
            zza(3, zzfhe);
            zzz(1, 4);
        }

        public final void zzct(long j) throws IOException {
            if (!zzffg.zzpfz || zzcws() < 10) {
                while ((j & -128) != 0) {
                    try {
                        byte[] bArr = this.buffer;
                        int i = this.position;
                        this.position = i + 1;
                        bArr[i] = (byte) ((((int) j) & Opcodes.NEG_FLOAT) | 128);
                        j >>>= 7;
                    } catch (IndexOutOfBoundsException e) {
                        throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
                    }
                }
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr2[i2] = (byte) ((int) j);
                return;
            }
            while ((j & -128) != 0) {
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                zzfiq.zza(bArr3, (long) i3, (byte) ((((int) j) & Opcodes.NEG_FLOAT) | 128));
                j >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            zzfiq.zza(bArr4, (long) i4, (byte) ((int) j));
        }

        public final void zzcv(long j) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) j);
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr2[i2] = (byte) ((int) (j >> 8));
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr3[i3] = (byte) ((int) (j >> 16));
                byte[] bArr4 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr4[i4] = (byte) ((int) (j >> 24));
                byte[] bArr5 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                bArr5[i5] = (byte) ((int) (j >> 32));
                byte[] bArr6 = this.buffer;
                int i6 = this.position;
                this.position = i6 + 1;
                bArr6[i6] = (byte) ((int) (j >> 40));
                byte[] bArr7 = this.buffer;
                int i7 = this.position;
                this.position = i7 + 1;
                bArr7[i7] = (byte) ((int) (j >> 48));
                byte[] bArr8 = this.buffer;
                int i8 = this.position;
                this.position = i8 + 1;
                bArr8[i8] = (byte) ((int) (j >> 56));
            } catch (IndexOutOfBoundsException e) {
                throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        public final int zzcws() {
            return this.limit - this.position;
        }

        public final void zzd(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        public final void zze(zzfhe zzfhe) throws IOException {
            zzld(zzfhe.zzho());
            zzfhe.zza(this);
        }

        public final void zzi(byte[] bArr, int i, int i2) throws IOException {
            zzld(i2);
            write(bArr, 0, i2);
        }

        public final void zzl(int i, boolean z) throws IOException {
            int i2 = 0;
            zzz(i, 0);
            if (z) {
                i2 = 1;
            }
            zzb((byte) i2);
        }

        public final void zzlc(int i) throws IOException {
            if (i >= 0) {
                zzld(i);
            } else {
                zzct((long) i);
            }
        }

        public final void zzld(int i) throws IOException {
            if (!zzffg.zzpfz || zzcws() < 10) {
                while ((i & -128) != 0) {
                    try {
                        byte[] bArr = this.buffer;
                        int i2 = this.position;
                        this.position = i2 + 1;
                        bArr[i2] = (byte) ((i & Opcodes.NEG_FLOAT) | 128);
                        i >>>= 7;
                    } catch (IndexOutOfBoundsException e) {
                        throw new zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
                    }
                }
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr2[i3] = (byte) i;
                return;
            }
            while ((i & -128) != 0) {
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                zzfiq.zza(bArr3, (long) i4, (byte) ((i & Opcodes.NEG_FLOAT) | 128));
                i >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            zzfiq.zza(bArr4, (long) i5, (byte) i);
        }

        /* JADX WARNING: type inference failed for: r0v4, types: [byte[]] */
        /* JADX WARNING: type inference failed for: r2v11, types: [int, byte] */
        /* JADX WARNING: Incorrect type for immutable var: ssa=byte[], code=null, for r0v4, types: [byte[]] */
        /* JADX WARNING: Incorrect type for immutable var: ssa=int, code=null, for r2v11, types: [int, byte] */
        /* JADX WARNING: Unknown variable types count: 2 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void zzlf(int r8) throws java.io.IOException {
            /*
                r7 = this;
                r6 = 1
                byte[] r0 = r7.buffer     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                int r1 = r7.position     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                int r2 = r1 + 1
                r7.position = r2     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                byte r2 = (byte) r8     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                r0[r1] = r2     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                byte[] r0 = r7.buffer     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                int r1 = r7.position     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                int r2 = r1 + 1
                r7.position = r2     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                int r2 = r8 >> 8
                byte r2 = (byte) r2     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                r0[r1] = r2     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                byte[] r0 = r7.buffer     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                int r1 = r7.position     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                int r2 = r1 + 1
                r7.position = r2     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                int r2 = r8 >> 16
                byte r2 = (byte) r2     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                r0[r1] = r2     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                byte[] r0 = r7.buffer     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                int r1 = r7.position     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                int r2 = r1 + 1
                r7.position = r2     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                int r2 = r8 >> 24
                r0[r1] = r2     // Catch:{ IndexOutOfBoundsException -> 0x0033 }
                return
            L_0x0033:
                r0 = move-exception
                com.google.android.gms.internal.zzffg$zzc r1 = new com.google.android.gms.internal.zzffg$zzc
                java.lang.String r2 = "Pos: %d, limit: %d, len: %d"
                r3 = 3
                java.lang.Object[] r3 = new java.lang.Object[r3]
                r4 = 0
                int r5 = r7.position
                java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
                r3[r4] = r5
                int r4 = r7.limit
                java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
                r3[r6] = r4
                r4 = 2
                java.lang.Integer r5 = java.lang.Integer.valueOf(r6)
                r3[r4] = r5
                java.lang.String r2 = java.lang.String.format(r2, r3)
                r1.<init>(r2, r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzffg.zzb.zzlf(int):void");
        }

        public final void zzn(int i, String str) throws IOException {
            zzz(i, 2);
            zzts(str);
        }

        public final void zzts(String str) throws IOException {
            int i = this.position;
            try {
                int zzli = zzli(str.length() * 3);
                int zzli2 = zzli(str.length());
                if (zzli2 == zzli) {
                    this.position = i + zzli2;
                    int zza = zzfis.zza(str, this.buffer, this.position, zzcws());
                    this.position = i;
                    zzld((zza - i) - zzli2);
                    this.position = zza;
                    return;
                }
                zzld(zzfis.zzd(str));
                this.position = zzfis.zza(str, this.buffer, this.position, zzcws());
            } catch (zzfiv e) {
                this.position = i;
                zza(str, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new zzc(e2);
            }
        }

        public final void zzz(int i, int i2) throws IOException {
            zzld((i << 3) | i2);
        }
    }

    public static class zzc extends IOException {
        zzc() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        zzc(String str, Throwable th) {
            String valueOf = String.valueOf("CodedOutputStream was writing to a flat byte array and ran out of space.: ");
            String valueOf2 = String.valueOf(str);
            super(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), th);
        }

        zzc(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }
    }

    static final class zzd extends zza {
        private final OutputStream out;

        zzd(OutputStream outputStream, int i) {
            super(i);
            if (outputStream == null) {
                throw new NullPointerException("out");
            }
            this.out = outputStream;
        }

        private final void doFlush() throws IOException {
            this.out.write(this.buffer, 0, this.position);
            this.position = 0;
        }

        private final void zzls(int i) throws IOException {
            if (this.limit - this.position < i) {
                doFlush();
            }
        }

        public final void flush() throws IOException {
            if (this.position > 0) {
                doFlush();
            }
        }

        public final void write(byte[] bArr, int i, int i2) throws IOException {
            if (this.limit - this.position >= i2) {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } else {
                int i3 = this.limit - this.position;
                System.arraycopy(bArr, i, this.buffer, this.position, i3);
                int i4 = i + i3;
                i2 -= i3;
                this.position = this.limit;
                this.zzpgb = i3 + this.zzpgb;
                doFlush();
                if (i2 <= this.limit) {
                    System.arraycopy(bArr, i4, this.buffer, 0, i2);
                    this.position = i2;
                } else {
                    this.out.write(bArr, i4, i2);
                }
            }
            this.zzpgb += i2;
        }

        public final void zza(int i, long j) throws IOException {
            zzls(20);
            zzah(i, 0);
            zzdc(j);
        }

        public final void zza(int i, zzfes zzfes) throws IOException {
            zzz(i, 2);
            zzay(zzfes);
        }

        public final void zza(int i, zzfhe zzfhe) throws IOException {
            zzz(i, 2);
            zze(zzfhe);
        }

        public final void zzaa(int i, int i2) throws IOException {
            zzls(20);
            zzah(i, 0);
            if (i2 >= 0) {
                zzlq(i2);
            } else {
                zzdc((long) i2);
            }
        }

        public final void zzab(int i, int i2) throws IOException {
            zzls(20);
            zzah(i, 0);
            zzlq(i2);
        }

        public final void zzac(int i, int i2) throws IOException {
            zzls(14);
            zzah(i, 5);
            zzlr(i2);
        }

        public final void zzay(zzfes zzfes) throws IOException {
            zzld(zzfes.size());
            zzfes.zza(this);
        }

        public final void zzb(byte b) throws IOException {
            if (this.position == this.limit) {
                doFlush();
            }
            zzc(b);
        }

        public final void zzb(int i, long j) throws IOException {
            zzls(18);
            zzah(i, 1);
            zzdd(j);
        }

        public final void zzb(int i, zzfes zzfes) throws IOException {
            zzz(1, 3);
            zzab(2, i);
            zza(3, zzfes);
            zzz(1, 4);
        }

        public final void zzb(int i, zzfhe zzfhe) throws IOException {
            zzz(1, 3);
            zzab(2, i);
            zza(3, zzfhe);
            zzz(1, 4);
        }

        public final void zzct(long j) throws IOException {
            zzls(10);
            zzdc(j);
        }

        public final void zzcv(long j) throws IOException {
            zzls(8);
            zzdd(j);
        }

        public final void zzd(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        public final void zze(zzfhe zzfhe) throws IOException {
            zzld(zzfhe.zzho());
            zzfhe.zza(this);
        }

        public final void zzi(byte[] bArr, int i, int i2) throws IOException {
            zzld(i2);
            write(bArr, 0, i2);
        }

        public final void zzl(int i, boolean z) throws IOException {
            int i2 = 0;
            zzls(11);
            zzah(i, 0);
            if (z) {
                i2 = 1;
            }
            zzc((byte) i2);
        }

        public final void zzlc(int i) throws IOException {
            if (i >= 0) {
                zzld(i);
            } else {
                zzct((long) i);
            }
        }

        public final void zzld(int i) throws IOException {
            zzls(10);
            zzlq(i);
        }

        public final void zzlf(int i) throws IOException {
            zzls(4);
            zzlr(i);
        }

        public final void zzn(int i, String str) throws IOException {
            zzz(i, 2);
            zzts(str);
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void zzts(java.lang.String r7) throws java.io.IOException {
            /*
                r6 = this;
                int r0 = r7.length()     // Catch:{ zzfiv -> 0x0064 }
                int r0 = r0 * 3
                int r1 = zzli(r0)     // Catch:{ zzfiv -> 0x0064 }
                int r2 = r1 + r0
                int r3 = r6.limit     // Catch:{ zzfiv -> 0x0064 }
                if (r2 <= r3) goto L_0x001f
                byte[] r1 = new byte[r0]     // Catch:{ zzfiv -> 0x0064 }
                r2 = 0
                int r0 = com.google.android.gms.internal.zzfis.zza(r7, r1, r2, r0)     // Catch:{ zzfiv -> 0x0064 }
                r6.zzld(r0)     // Catch:{ zzfiv -> 0x0064 }
                r2 = 0
                r6.zzd(r1, r2, r0)     // Catch:{ zzfiv -> 0x0064 }
            L_0x001e:
                return
            L_0x001f:
                int r0 = r0 + r1
                int r2 = r6.limit     // Catch:{ zzfiv -> 0x0064 }
                int r3 = r6.position     // Catch:{ zzfiv -> 0x0064 }
                int r2 = r2 - r3
                if (r0 <= r2) goto L_0x002a
                r6.doFlush()     // Catch:{ zzfiv -> 0x0064 }
            L_0x002a:
                int r0 = r7.length()     // Catch:{ zzfiv -> 0x0064 }
                int r0 = zzli(r0)     // Catch:{ zzfiv -> 0x0064 }
                int r2 = r6.position     // Catch:{ zzfiv -> 0x0064 }
                if (r0 != r1) goto L_0x0069
                int r1 = r2 + r0
                r6.position = r1     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                byte[] r1 = r6.buffer     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                int r3 = r6.position     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                int r4 = r6.limit     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                int r5 = r6.position     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                int r4 = r4 - r5
                int r1 = com.google.android.gms.internal.zzfis.zza(r7, r1, r3, r4)     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                r6.position = r2     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                int r3 = r1 - r2
                int r0 = r3 - r0
                r6.zzlq(r0)     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                r6.position = r1     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
            L_0x0052:
                int r1 = r6.zzpgb     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                int r0 = r0 + r1
                r6.zzpgb = r0     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                goto L_0x001e
            L_0x0058:
                r0 = move-exception
                int r1 = r6.zzpgb     // Catch:{ zzfiv -> 0x0064 }
                int r3 = r6.position     // Catch:{ zzfiv -> 0x0064 }
                int r3 = r3 - r2
                int r1 = r1 - r3
                r6.zzpgb = r1     // Catch:{ zzfiv -> 0x0064 }
                r6.position = r2     // Catch:{ zzfiv -> 0x0064 }
                throw r0     // Catch:{ zzfiv -> 0x0064 }
            L_0x0064:
                r0 = move-exception
                r6.zza(r7, r0)
                goto L_0x001e
            L_0x0069:
                int r0 = com.google.android.gms.internal.zzfis.zzd(r7)     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                r6.zzlq(r0)     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                byte[] r1 = r6.buffer     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                int r3 = r6.position     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                int r1 = com.google.android.gms.internal.zzfis.zza(r7, r1, r3, r0)     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                r6.position = r1     // Catch:{ zzfiv -> 0x0058, ArrayIndexOutOfBoundsException -> 0x007b }
                goto L_0x0052
            L_0x007b:
                r0 = move-exception
                com.google.android.gms.internal.zzffg$zzc r1 = new com.google.android.gms.internal.zzffg$zzc     // Catch:{ zzfiv -> 0x0064 }
                r1.<init>(r0)     // Catch:{ zzfiv -> 0x0064 }
                throw r1     // Catch:{ zzfiv -> 0x0064 }
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzffg.zzd.zzts(java.lang.String):void");
        }

        public final void zzz(int i, int i2) throws IOException {
            zzld((i << 3) | i2);
        }
    }

    private zzffg() {
    }

    public static int zza(int i, zzfgk zzfgk) {
        int zzlg = zzlg(i);
        int zzho = zzfgk.zzho();
        return zzlg + zzho + zzli(zzho);
    }

    public static int zza(zzfgk zzfgk) {
        int zzho = zzfgk.zzho();
        return zzho + zzli(zzho);
    }

    public static int zzad(int i, int i2) {
        return zzlg(i) + zzlh(i2);
    }

    public static int zzae(int i, int i2) {
        return zzlg(i) + zzli(i2);
    }

    public static int zzaf(int i, int i2) {
        return zzlg(i) + 4;
    }

    public static int zzag(int i, int i2) {
        return zzlg(i) + zzlh(i2);
    }

    public static int zzaz(zzfes zzfes) {
        int size = zzfes.size();
        return size + zzli(size);
    }

    public static int zzb(int i, double d) {
        return zzlg(i) + 8;
    }

    public static int zzb(int i, zzfgk zzfgk) {
        return (zzlg(1) << 1) + zzae(2, i) + zza(3, zzfgk);
    }

    public static zzffg zzb(OutputStream outputStream, int i) {
        return new zzd(outputStream, i);
    }

    public static zzffg zzbc(byte[] bArr) {
        return zzh(bArr, 0, bArr.length);
    }

    public static int zzbd(byte[] bArr) {
        int length = bArr.length;
        return length + zzli(length);
    }

    public static int zzc(int i, long j) {
        return zzlg(i) + zzcx(j);
    }

    public static int zzc(int i, zzfes zzfes) {
        int zzlg = zzlg(i);
        int size = zzfes.size();
        return zzlg + size + zzli(size);
    }

    public static int zzc(int i, zzfhe zzfhe) {
        return zzlg(i) + zzf(zzfhe);
    }

    public static int zzcw(long j) {
        return zzcx(j);
    }

    public static int zzcx(long j) {
        long j2;
        if ((-128 & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        int i = 2;
        if ((-34359738368L & j) != 0) {
            i = 6;
            j2 = j >>> 28;
        } else {
            j2 = j;
        }
        if ((-2097152 & j2) != 0) {
            i += 2;
            j2 >>>= 14;
        }
        return (j2 & -16384) != 0 ? i + 1 : i;
    }

    public static int zzcy(long j) {
        return zzcx(zzdb(j));
    }

    public static int zzcz(long j) {
        return 8;
    }

    public static int zzd(int i, long j) {
        return zzlg(i) + zzcx(j);
    }

    public static int zzd(int i, zzfes zzfes) {
        return (zzlg(1) << 1) + zzae(2, i) + zzc(3, zzfes);
    }

    public static int zzd(int i, zzfhe zzfhe) {
        return (zzlg(1) << 1) + zzae(2, i) + zzc(3, zzfhe);
    }

    public static int zzda(long j) {
        return 8;
    }

    private static long zzdb(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public static int zzde(boolean z) {
        return 1;
    }

    public static int zze(int i, long j) {
        return zzlg(i) + 8;
    }

    public static int zzf(zzfhe zzfhe) {
        int zzho = zzfhe.zzho();
        return zzho + zzli(zzho);
    }

    public static int zzg(float f) {
        return 4;
    }

    @Deprecated
    public static int zzg(zzfhe zzfhe) {
        return zzfhe.zzho();
    }

    public static zzffg zzh(byte[] bArr, int i, int i2) {
        return new zzb(bArr, i, i2);
    }

    static int zzlb(int i) {
        if (i > 4096) {
            return 4096;
        }
        return i;
    }

    public static int zzlg(int i) {
        return zzli(i << 3);
    }

    public static int zzlh(int i) {
        if (i >= 0) {
            return zzli(i);
        }
        return 10;
    }

    public static int zzli(int i) {
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

    public static int zzlj(int i) {
        return zzli(zzlo(i));
    }

    public static int zzlk(int i) {
        return 4;
    }

    public static int zzll(int i) {
        return 4;
    }

    public static int zzlm(int i) {
        return zzlh(i);
    }

    static int zzln(int i) {
        return zzli(i) + i;
    }

    private static int zzlo(int i) {
        return (i << 1) ^ (i >> 31);
    }

    @Deprecated
    public static int zzlp(int i) {
        return zzli(i);
    }

    public static int zzm(int i, boolean z) {
        return zzlg(i) + 1;
    }

    public static int zzo(double d) {
        return 8;
    }

    public static int zzo(int i, String str) {
        return zzlg(i) + zztt(str);
    }

    public static int zztt(String str) {
        int length;
        try {
            length = zzfis.zzd(str);
        } catch (zzfiv e) {
            length = str.getBytes(zzffz.UTF_8).length;
        }
        return length + zzli(length);
    }

    public abstract void flush() throws IOException;

    public abstract void write(byte[] bArr, int i, int i2) throws IOException;

    public final void zza(int i, double d) throws IOException {
        zzb(i, Double.doubleToRawLongBits(d));
    }

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzfes zzfes) throws IOException;

    public abstract void zza(int i, zzfhe zzfhe) throws IOException;

    /* access modifiers changed from: 0000 */
    public final void zza(String str, zzfiv zzfiv) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", zzfiv);
        byte[] bytes = str.getBytes(zzffz.UTF_8);
        try {
            zzld(bytes.length);
            zzd(bytes, 0, bytes.length);
        } catch (IndexOutOfBoundsException e) {
            throw new zzc(e);
        } catch (zzc e2) {
            throw e2;
        }
    }

    public abstract void zzaa(int i, int i2) throws IOException;

    public abstract void zzab(int i, int i2) throws IOException;

    public abstract void zzac(int i, int i2) throws IOException;

    public abstract void zzay(zzfes zzfes) throws IOException;

    public abstract void zzb(byte b) throws IOException;

    public abstract void zzb(int i, long j) throws IOException;

    public abstract void zzb(int i, zzfes zzfes) throws IOException;

    public abstract void zzb(int i, zzfhe zzfhe) throws IOException;

    public abstract void zzct(long j) throws IOException;

    public final void zzcu(long j) throws IOException {
        zzct(zzdb(j));
    }

    public abstract void zzcv(long j) throws IOException;

    public abstract int zzcws();

    public final void zzcwt() {
        if (zzcws() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public final void zzdd(boolean z) throws IOException {
        zzb((byte) (z ? 1 : 0));
    }

    @Deprecated
    public final void zze(int i, zzfhe zzfhe) throws IOException {
        zzz(i, 3);
        zzfhe.zza(this);
        zzz(i, 4);
    }

    public abstract void zze(zzfhe zzfhe) throws IOException;

    public final void zzf(float f) throws IOException {
        zzlf(Float.floatToRawIntBits(f));
    }

    /* access modifiers changed from: 0000 */
    public abstract void zzi(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zzl(int i, boolean z) throws IOException;

    public abstract void zzlc(int i) throws IOException;

    public abstract void zzld(int i) throws IOException;

    public final void zzle(int i) throws IOException {
        zzld(zzlo(i));
    }

    public abstract void zzlf(int i) throws IOException;

    public final void zzn(double d) throws IOException {
        zzcv(Double.doubleToRawLongBits(d));
    }

    public abstract void zzn(int i, String str) throws IOException;

    public abstract void zzts(String str) throws IOException;

    public abstract void zzz(int i, int i2) throws IOException;
}
