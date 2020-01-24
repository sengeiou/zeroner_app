package com.google.android.gms.internal;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class zzffe extends zzffb {
    private final byte[] buffer;
    private int pos;
    private int zzpfr;
    private int zzpft;
    private int zzpfu;
    private final InputStream zzpfv;
    private int zzpfw;
    private int zzpfx;
    private zzfff zzpfy;

    private zzffe(InputStream inputStream, int i) {
        super();
        this.zzpfu = Integer.MAX_VALUE;
        this.zzpfy = null;
        zzffz.zzc(inputStream, "input");
        this.zzpfv = inputStream;
        this.buffer = new byte[i];
        this.zzpfw = 0;
        this.pos = 0;
        this.zzpfx = 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b2, code lost:
        if (((long) r4[r3]) < 0) goto L_0x00b4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final long zzcwn() throws java.io.IOException {
        /*
            r10 = this;
            r8 = 0
            int r0 = r10.pos
            int r1 = r10.zzpfw
            if (r1 == r0) goto L_0x00b4
            byte[] r4 = r10.buffer
            int r1 = r0 + 1
            byte r0 = r4[r0]
            if (r0 < 0) goto L_0x0014
            r10.pos = r1
            long r0 = (long) r0
        L_0x0013:
            return r0
        L_0x0014:
            int r2 = r10.zzpfw
            int r2 = r2 - r1
            r3 = 9
            if (r2 < r3) goto L_0x00b4
            int r2 = r1 + 1
            byte r1 = r4[r1]
            int r1 = r1 << 7
            r0 = r0 ^ r1
            if (r0 >= 0) goto L_0x002a
            r0 = r0 ^ -128(0xffffffffffffff80, float:NaN)
            long r0 = (long) r0
        L_0x0027:
            r10.pos = r2
            goto L_0x0013
        L_0x002a:
            int r3 = r2 + 1
            byte r1 = r4[r2]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L_0x0038
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            long r0 = (long) r0
            r2 = r3
            goto L_0x0027
        L_0x0038:
            int r2 = r3 + 1
            byte r1 = r4[r3]
            int r1 = r1 << 21
            r0 = r0 ^ r1
            if (r0 >= 0) goto L_0x0047
            r1 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r1
            long r0 = (long) r0
            goto L_0x0027
        L_0x0047:
            long r0 = (long) r0
            int r3 = r2 + 1
            byte r2 = r4[r2]
            long r6 = (long) r2
            r2 = 28
            long r6 = r6 << r2
            long r0 = r0 ^ r6
            int r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r2 < 0) goto L_0x005b
            r4 = 266354560(0xfe03f80, double:1.315966377E-315)
            long r0 = r0 ^ r4
            r2 = r3
            goto L_0x0027
        L_0x005b:
            int r2 = r3 + 1
            byte r3 = r4[r3]
            long r6 = (long) r3
            r3 = 35
            long r6 = r6 << r3
            long r0 = r0 ^ r6
            int r3 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r3 >= 0) goto L_0x006f
            r4 = -34093383808(0xfffffff80fe03f80, double:NaN)
            long r0 = r0 ^ r4
            goto L_0x0027
        L_0x006f:
            int r3 = r2 + 1
            byte r2 = r4[r2]
            long r6 = (long) r2
            r2 = 42
            long r6 = r6 << r2
            long r0 = r0 ^ r6
            int r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r2 < 0) goto L_0x0084
            r4 = 4363953127296(0x3f80fe03f80, double:2.1560793202584E-311)
            long r0 = r0 ^ r4
            r2 = r3
            goto L_0x0027
        L_0x0084:
            int r2 = r3 + 1
            byte r3 = r4[r3]
            long r6 = (long) r3
            r3 = 49
            long r6 = r6 << r3
            long r0 = r0 ^ r6
            int r3 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r3 >= 0) goto L_0x0098
            r4 = -558586000294016(0xfffe03f80fe03f80, double:NaN)
            long r0 = r0 ^ r4
            goto L_0x0027
        L_0x0098:
            int r3 = r2 + 1
            byte r2 = r4[r2]
            long r6 = (long) r2
            r2 = 56
            long r6 = r6 << r2
            long r0 = r0 ^ r6
            r6 = 71499008037633920(0xfe03f80fe03f80, double:6.838959413692434E-304)
            long r0 = r0 ^ r6
            int r2 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r2 >= 0) goto L_0x00ba
            int r2 = r3 + 1
            byte r3 = r4[r3]
            long r4 = (long) r3
            int r3 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r3 >= 0) goto L_0x0027
        L_0x00b4:
            long r0 = r10.zzcwj()
            goto L_0x0013
        L_0x00ba:
            r2 = r3
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzffe.zzcwn():long");
    }

    private final int zzcwo() throws IOException {
        int i = this.pos;
        if (this.zzpfw - i < 4) {
            zzkw(4);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i] & UnsignedBytes.MAX_VALUE) | ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << 16);
    }

    private final long zzcwp() throws IOException {
        int i = this.pos;
        if (this.zzpfw - i < 8) {
            zzkw(8);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    private final void zzcwq() {
        this.zzpfw += this.zzpfr;
        int i = this.zzpfx + this.zzpfw;
        if (i > this.zzpfu) {
            this.zzpfr = i - this.zzpfu;
            this.zzpfw -= this.zzpfr;
            return;
        }
        this.zzpfr = 0;
    }

    private final byte zzcwr() throws IOException {
        if (this.pos == this.zzpfw) {
            zzkw(1);
        }
        byte[] bArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i];
    }

    private final void zzkw(int i) throws IOException {
        if (zzkx(i)) {
            return;
        }
        if (i > (this.zzpfo - this.zzpfx) - this.pos) {
            throw zzfge.zzcyh();
        }
        throw zzfge.zzcya();
    }

    private final boolean zzkx(int i) throws IOException {
        while (this.pos + i > this.zzpfw) {
            if (i > (this.zzpfo - this.zzpfx) - this.pos || this.zzpfx + this.pos + i > this.zzpfu) {
                return false;
            }
            int i2 = this.pos;
            if (i2 > 0) {
                if (this.zzpfw > i2) {
                    System.arraycopy(this.buffer, i2, this.buffer, 0, this.zzpfw - i2);
                }
                this.zzpfx += i2;
                this.zzpfw -= i2;
                this.pos = 0;
            }
            int read = this.zzpfv.read(this.buffer, this.zzpfw, Math.min(this.buffer.length - this.zzpfw, (this.zzpfo - this.zzpfx) - this.zzpfw));
            if (read == 0 || read < -1 || read > this.buffer.length) {
                throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + read + "\nThe InputStream implementation is buggy.");
            } else if (read <= 0) {
                return false;
            } else {
                this.zzpfw = read + this.zzpfw;
                zzcwq();
                if (this.zzpfw >= i) {
                    return true;
                }
            }
        }
        throw new IllegalStateException("refillBuffer() called when " + i + " bytes were already available in buffer");
    }

    private final byte[] zzky(int i) throws IOException {
        byte[] zzkz = zzkz(i);
        if (zzkz != null) {
            return zzkz;
        }
        int i2 = this.pos;
        int i3 = this.zzpfw - this.pos;
        this.zzpfx += this.zzpfw;
        this.pos = 0;
        this.zzpfw = 0;
        List zzla = zzla(i - i3);
        byte[] bArr = new byte[i];
        System.arraycopy(this.buffer, i2, bArr, 0, i3);
        Iterator it = zzla.iterator();
        while (true) {
            int i4 = i3;
            if (!it.hasNext()) {
                return bArr;
            }
            byte[] bArr2 = (byte[]) it.next();
            System.arraycopy(bArr2, 0, bArr, i4, bArr2.length);
            i3 = bArr2.length + i4;
        }
    }

    private final byte[] zzkz(int i) throws IOException {
        if (i == 0) {
            return zzffz.EMPTY_BYTE_ARRAY;
        }
        if (i < 0) {
            throw zzfge.zzcyb();
        }
        int i2 = this.zzpfx + this.pos + i;
        if (i2 - this.zzpfo > 0) {
            throw zzfge.zzcyh();
        } else if (i2 > this.zzpfu) {
            zzku((this.zzpfu - this.zzpfx) - this.pos);
            throw zzfge.zzcya();
        } else {
            int i3 = this.zzpfw - this.pos;
            int i4 = i - i3;
            if (i4 >= 4096 && i4 > this.zzpfv.available()) {
                return null;
            }
            byte[] bArr = new byte[i];
            System.arraycopy(this.buffer, this.pos, bArr, 0, i3);
            this.zzpfx += this.zzpfw;
            this.pos = 0;
            this.zzpfw = 0;
            while (i3 < bArr.length) {
                int read = this.zzpfv.read(bArr, i3, i - i3);
                if (read == -1) {
                    throw zzfge.zzcya();
                }
                this.zzpfx += read;
                i3 += read;
            }
            return bArr;
        }
    }

    private final List<byte[]> zzla(int i) throws IOException {
        ArrayList arrayList = new ArrayList();
        while (i > 0) {
            byte[] bArr = new byte[Math.min(i, 4096)];
            int i2 = 0;
            while (i2 < bArr.length) {
                int read = this.zzpfv.read(bArr, i2, bArr.length - i2);
                if (read == -1) {
                    throw zzfge.zzcya();
                }
                this.zzpfx += read;
                i2 += read;
            }
            i -= bArr.length;
            arrayList.add(bArr);
        }
        return arrayList;
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zzcwp());
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zzcwo());
    }

    public final String readString() throws IOException {
        int zzcwi = zzcwi();
        if (zzcwi > 0 && zzcwi <= this.zzpfw - this.pos) {
            String str = new String(this.buffer, this.pos, zzcwi, zzffz.UTF_8);
            this.pos = zzcwi + this.pos;
            return str;
        } else if (zzcwi == 0) {
            return "";
        } else {
            if (zzcwi > this.zzpfw) {
                return new String(zzky(zzcwi), zzffz.UTF_8);
            }
            zzkw(zzcwi);
            String str2 = new String(this.buffer, this.pos, zzcwi, zzffz.UTF_8);
            this.pos = zzcwi + this.pos;
            return str2;
        }
    }

    public final <T extends zzffu<T, ?>> T zza(T t, zzffm zzffm) throws IOException {
        int zzcwi = zzcwi();
        if (this.zzpfm >= this.zzpfn) {
            throw zzfge.zzcyg();
        }
        int zzks = zzks(zzcwi);
        this.zzpfm++;
        T zza = zzffu.zza(t, (zzffb) this, zzffm);
        zzkp(0);
        this.zzpfm--;
        zzkt(zzks);
        return zza;
    }

    public final void zza(zzfhf zzfhf, zzffm zzffm) throws IOException {
        int zzcwi = zzcwi();
        if (this.zzpfm >= this.zzpfn) {
            throw zzfge.zzcyg();
        }
        int zzks = zzks(zzcwi);
        this.zzpfm++;
        zzfhf.zzb(this, zzffm);
        zzkp(0);
        this.zzpfm--;
        zzkt(zzks);
    }

    public final int zzcvt() throws IOException {
        if (zzcwl()) {
            this.zzpft = 0;
            return 0;
        }
        this.zzpft = zzcwi();
        if ((this.zzpft >>> 3) != 0) {
            return this.zzpft;
        }
        throw zzfge.zzcyd();
    }

    public final long zzcvu() throws IOException {
        return zzcwn();
    }

    public final long zzcvv() throws IOException {
        return zzcwn();
    }

    public final int zzcvw() throws IOException {
        return zzcwi();
    }

    public final long zzcvx() throws IOException {
        return zzcwp();
    }

    public final int zzcvy() throws IOException {
        return zzcwo();
    }

    public final boolean zzcvz() throws IOException {
        return zzcwn() != 0;
    }

    public final String zzcwa() throws IOException {
        byte[] zzky;
        int zzcwi = zzcwi();
        int i = this.pos;
        if (zzcwi <= this.zzpfw - i && zzcwi > 0) {
            zzky = this.buffer;
            this.pos = i + zzcwi;
        } else if (zzcwi == 0) {
            return "";
        } else {
            if (zzcwi <= this.zzpfw) {
                zzkw(zzcwi);
                byte[] bArr = this.buffer;
                this.pos = zzcwi;
                zzky = bArr;
                i = 0;
            } else {
                zzky = zzky(zzcwi);
                i = 0;
            }
        }
        if (zzfis.zzk(zzky, i, i + zzcwi)) {
            return new String(zzky, i, zzcwi, zzffz.UTF_8);
        }
        throw zzfge.zzcyi();
    }

    public final zzfes zzcwb() throws IOException {
        int zzcwi = zzcwi();
        if (zzcwi <= this.zzpfw - this.pos && zzcwi > 0) {
            zzfes zze = zzfes.zze(this.buffer, this.pos, zzcwi);
            this.pos = zzcwi + this.pos;
            return zze;
        } else if (zzcwi == 0) {
            return zzfes.zzpfg;
        } else {
            byte[] zzkz = zzkz(zzcwi);
            if (zzkz != null) {
                return zzfes.zzba(zzkz);
            }
            int i = this.pos;
            int i2 = this.zzpfw - this.pos;
            this.zzpfx += this.zzpfw;
            this.pos = 0;
            this.zzpfw = 0;
            List<byte[]> zzla = zzla(zzcwi - i2);
            ArrayList arrayList = new ArrayList(zzla.size() + 1);
            arrayList.add(zzfes.zze(this.buffer, i, i2));
            for (byte[] zzba : zzla) {
                arrayList.add(zzfes.zzba(zzba));
            }
            return zzfes.zzf(arrayList);
        }
    }

    public final int zzcwc() throws IOException {
        return zzcwi();
    }

    public final int zzcwd() throws IOException {
        return zzcwi();
    }

    public final int zzcwe() throws IOException {
        return zzcwo();
    }

    public final long zzcwf() throws IOException {
        return zzcwp();
    }

    public final int zzcwg() throws IOException {
        return zzkv(zzcwi());
    }

    public final long zzcwh() throws IOException {
        return zzcs(zzcwn());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x006a, code lost:
        if (r3[r2] < 0) goto L_0x006c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zzcwi() throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.pos
            int r1 = r5.zzpfw
            if (r1 == r0) goto L_0x006c
            byte[] r3 = r5.buffer
            int r2 = r0 + 1
            byte r0 = r3[r0]
            if (r0 < 0) goto L_0x0011
            r5.pos = r2
        L_0x0010:
            return r0
        L_0x0011:
            int r1 = r5.zzpfw
            int r1 = r1 - r2
            r4 = 9
            if (r1 < r4) goto L_0x006c
            int r1 = r2 + 1
            byte r2 = r3[r2]
            int r2 = r2 << 7
            r0 = r0 ^ r2
            if (r0 >= 0) goto L_0x0026
            r0 = r0 ^ -128(0xffffffffffffff80, float:NaN)
        L_0x0023:
            r5.pos = r1
            goto L_0x0010
        L_0x0026:
            int r2 = r1 + 1
            byte r1 = r3[r1]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L_0x0033
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            r1 = r2
            goto L_0x0023
        L_0x0033:
            int r1 = r2 + 1
            byte r2 = r3[r2]
            int r2 = r2 << 21
            r0 = r0 ^ r2
            if (r0 >= 0) goto L_0x0041
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            goto L_0x0023
        L_0x0041:
            int r2 = r1 + 1
            byte r1 = r3[r1]
            int r4 = r1 << 28
            r0 = r0 ^ r4
            r4 = 266354560(0xfe03f80, float:2.2112565E-29)
            r0 = r0 ^ r4
            if (r1 >= 0) goto L_0x0072
            int r1 = r2 + 1
            byte r2 = r3[r2]
            if (r2 >= 0) goto L_0x0023
            int r2 = r1 + 1
            byte r1 = r3[r1]
            if (r1 >= 0) goto L_0x0072
            int r1 = r2 + 1
            byte r2 = r3[r2]
            if (r2 >= 0) goto L_0x0023
            int r2 = r1 + 1
            byte r1 = r3[r1]
            if (r1 >= 0) goto L_0x0072
            int r1 = r2 + 1
            byte r2 = r3[r2]
            if (r2 >= 0) goto L_0x0023
        L_0x006c:
            long r0 = r5.zzcwj()
            int r0 = (int) r0
            goto L_0x0010
        L_0x0072:
            r1 = r2
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzffe.zzcwi():int");
    }

    /* access modifiers changed from: 0000 */
    public final long zzcwj() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzcwr = zzcwr();
            j |= ((long) (zzcwr & Byte.MAX_VALUE)) << i;
            if ((zzcwr & 128) == 0) {
                return j;
            }
        }
        throw zzfge.zzcyc();
    }

    public final int zzcwk() {
        if (this.zzpfu == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzpfu - (this.zzpfx + this.pos);
    }

    public final boolean zzcwl() throws IOException {
        return this.pos == this.zzpfw && !zzkx(1);
    }

    public final int zzcwm() {
        return this.zzpfx + this.pos;
    }

    public final void zzkp(int i) throws zzfge {
        if (this.zzpft != i) {
            throw zzfge.zzcye();
        }
    }

    public final boolean zzkq(int i) throws IOException {
        int zzcvt;
        int i2 = 0;
        switch (i & 7) {
            case 0:
                if (this.zzpfw - this.pos >= 10) {
                    while (i2 < 10) {
                        byte[] bArr = this.buffer;
                        int i3 = this.pos;
                        this.pos = i3 + 1;
                        if (bArr[i3] >= 0) {
                            return true;
                        }
                        i2++;
                    }
                    throw zzfge.zzcyc();
                }
                while (i2 < 10) {
                    if (zzcwr() >= 0) {
                        return true;
                    }
                    i2++;
                }
                throw zzfge.zzcyc();
            case 1:
                zzku(8);
                return true;
            case 2:
                zzku(zzcwi());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzku(4);
                return true;
            default:
                throw zzfge.zzcyf();
        }
        do {
            zzcvt = zzcvt();
            if (zzcvt != 0) {
            }
            zzkp(((i >>> 3) << 3) | 4);
            return true;
        } while (zzkq(zzcvt));
        zzkp(((i >>> 3) << 3) | 4);
        return true;
    }

    public final int zzks(int i) throws zzfge {
        if (i < 0) {
            throw zzfge.zzcyb();
        }
        int i2 = this.zzpfx + this.pos + i;
        int i3 = this.zzpfu;
        if (i2 > i3) {
            throw zzfge.zzcya();
        }
        this.zzpfu = i2;
        zzcwq();
        return i3;
    }

    public final void zzkt(int i) {
        this.zzpfu = i;
        zzcwq();
    }

    public final void zzku(int i) throws IOException {
        if (i <= this.zzpfw - this.pos && i >= 0) {
            this.pos += i;
        } else if (i < 0) {
            throw zzfge.zzcyb();
        } else if (this.zzpfx + this.pos + i > this.zzpfu) {
            zzku((this.zzpfu - this.zzpfx) - this.pos);
            throw zzfge.zzcya();
        } else {
            int i2 = this.zzpfw - this.pos;
            this.pos = this.zzpfw;
            zzkw(1);
            while (i - i2 > this.zzpfw) {
                i2 += this.zzpfw;
                this.pos = this.zzpfw;
                zzkw(1);
            }
            this.pos = i - i2;
        }
    }
}
