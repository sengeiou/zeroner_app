package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

public final class zzfkq extends zzfjm<zzfkq> implements Cloneable {
    private String tag;
    private int zzala;
    private boolean zzmsn;
    private zzfks zzofi;
    public long zzpql;
    public long zzpqm;
    private long zzpqn;
    private int zzpqo;
    private zzfkr[] zzpqp;
    private byte[] zzpqq;
    private zzfko zzpqr;
    public byte[] zzpqs;
    private String zzpqt;
    private String zzpqu;
    private zzfkn zzpqv;
    private String zzpqw;
    public long zzpqx;
    private zzfkp zzpqy;
    public byte[] zzpqz;
    private String zzpra;
    private int zzprb;
    private int[] zzprc;
    private long zzprd;

    public zzfkq() {
        this.zzpql = 0;
        this.zzpqm = 0;
        this.zzpqn = 0;
        this.tag = "";
        this.zzpqo = 0;
        this.zzala = 0;
        this.zzmsn = false;
        this.zzpqp = zzfkr.zzdba();
        this.zzpqq = zzfjv.zzpnv;
        this.zzpqr = null;
        this.zzpqs = zzfjv.zzpnv;
        this.zzpqt = "";
        this.zzpqu = "";
        this.zzpqv = null;
        this.zzpqw = "";
        this.zzpqx = 180000;
        this.zzpqy = null;
        this.zzpqz = zzfjv.zzpnv;
        this.zzpra = "";
        this.zzprb = 0;
        this.zzprc = zzfjv.zzpnp;
        this.zzprd = 0;
        this.zzofi = null;
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzar */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzfkq zza(com.google.android.gms.internal.zzfjj r8) throws java.io.IOException {
        /*
            r7 = this;
            r1 = 0
        L_0x0001:
            int r0 = r8.zzcvt()
            switch(r0) {
                case 0: goto L_0x000e;
                case 8: goto L_0x000f;
                case 18: goto L_0x0016;
                case 26: goto L_0x001d;
                case 34: goto L_0x005c;
                case 50: goto L_0x0063;
                case 58: goto L_0x006a;
                case 66: goto L_0x007b;
                case 74: goto L_0x0082;
                case 80: goto L_0x0094;
                case 88: goto L_0x009c;
                case 96: goto L_0x00a4;
                case 106: goto L_0x00ac;
                case 114: goto L_0x00b4;
                case 120: goto L_0x00bc;
                case 130: goto L_0x00c4;
                case 136: goto L_0x00d6;
                case 146: goto L_0x00de;
                case 152: goto L_0x00e6;
                case 160: goto L_0x011a;
                case 162: goto L_0x014e;
                case 168: goto L_0x0190;
                case 176: goto L_0x0198;
                case 186: goto L_0x01a0;
                case 194: goto L_0x01b2;
                default: goto L_0x0008;
            }
        L_0x0008:
            boolean r0 = super.zza(r8, r0)
            if (r0 != 0) goto L_0x0001
        L_0x000e:
            return r7
        L_0x000f:
            long r2 = r8.zzcvv()
            r7.zzpql = r2
            goto L_0x0001
        L_0x0016:
            java.lang.String r0 = r8.readString()
            r7.tag = r0
            goto L_0x0001
        L_0x001d:
            r0 = 26
            int r2 = com.google.android.gms.internal.zzfjv.zzb(r8, r0)
            com.google.android.gms.internal.zzfkr[] r0 = r7.zzpqp
            if (r0 != 0) goto L_0x0049
            r0 = r1
        L_0x0028:
            int r2 = r2 + r0
            com.google.android.gms.internal.zzfkr[] r2 = new com.google.android.gms.internal.zzfkr[r2]
            if (r0 == 0) goto L_0x0032
            com.google.android.gms.internal.zzfkr[] r3 = r7.zzpqp
            java.lang.System.arraycopy(r3, r1, r2, r1, r0)
        L_0x0032:
            int r3 = r2.length
            int r3 = r3 + -1
            if (r0 >= r3) goto L_0x004d
            com.google.android.gms.internal.zzfkr r3 = new com.google.android.gms.internal.zzfkr
            r3.<init>()
            r2[r0] = r3
            r3 = r2[r0]
            r8.zza(r3)
            r8.zzcvt()
            int r0 = r0 + 1
            goto L_0x0032
        L_0x0049:
            com.google.android.gms.internal.zzfkr[] r0 = r7.zzpqp
            int r0 = r0.length
            goto L_0x0028
        L_0x004d:
            com.google.android.gms.internal.zzfkr r3 = new com.google.android.gms.internal.zzfkr
            r3.<init>()
            r2[r0] = r3
            r0 = r2[r0]
            r8.zza(r0)
            r7.zzpqp = r2
            goto L_0x0001
        L_0x005c:
            byte[] r0 = r8.readBytes()
            r7.zzpqq = r0
            goto L_0x0001
        L_0x0063:
            byte[] r0 = r8.readBytes()
            r7.zzpqs = r0
            goto L_0x0001
        L_0x006a:
            com.google.android.gms.internal.zzfkn r0 = r7.zzpqv
            if (r0 != 0) goto L_0x0075
            com.google.android.gms.internal.zzfkn r0 = new com.google.android.gms.internal.zzfkn
            r0.<init>()
            r7.zzpqv = r0
        L_0x0075:
            com.google.android.gms.internal.zzfkn r0 = r7.zzpqv
            r8.zza(r0)
            goto L_0x0001
        L_0x007b:
            java.lang.String r0 = r8.readString()
            r7.zzpqt = r0
            goto L_0x0001
        L_0x0082:
            com.google.android.gms.internal.zzfko r0 = r7.zzpqr
            if (r0 != 0) goto L_0x008d
            com.google.android.gms.internal.zzfko r0 = new com.google.android.gms.internal.zzfko
            r0.<init>()
            r7.zzpqr = r0
        L_0x008d:
            com.google.android.gms.internal.zzfko r0 = r7.zzpqr
            r8.zza(r0)
            goto L_0x0001
        L_0x0094:
            boolean r0 = r8.zzcvz()
            r7.zzmsn = r0
            goto L_0x0001
        L_0x009c:
            int r0 = r8.zzcvw()
            r7.zzpqo = r0
            goto L_0x0001
        L_0x00a4:
            int r0 = r8.zzcvw()
            r7.zzala = r0
            goto L_0x0001
        L_0x00ac:
            java.lang.String r0 = r8.readString()
            r7.zzpqu = r0
            goto L_0x0001
        L_0x00b4:
            java.lang.String r0 = r8.readString()
            r7.zzpqw = r0
            goto L_0x0001
        L_0x00bc:
            long r2 = r8.zzcwh()
            r7.zzpqx = r2
            goto L_0x0001
        L_0x00c4:
            com.google.android.gms.internal.zzfkp r0 = r7.zzpqy
            if (r0 != 0) goto L_0x00cf
            com.google.android.gms.internal.zzfkp r0 = new com.google.android.gms.internal.zzfkp
            r0.<init>()
            r7.zzpqy = r0
        L_0x00cf:
            com.google.android.gms.internal.zzfkp r0 = r7.zzpqy
            r8.zza(r0)
            goto L_0x0001
        L_0x00d6:
            long r2 = r8.zzcvv()
            r7.zzpqm = r2
            goto L_0x0001
        L_0x00de:
            byte[] r0 = r8.readBytes()
            r7.zzpqz = r0
            goto L_0x0001
        L_0x00e6:
            int r2 = r8.getPosition()
            int r3 = r8.zzcvw()     // Catch:{ IllegalArgumentException -> 0x010d }
            switch(r3) {
                case 0: goto L_0x0116;
                case 1: goto L_0x0116;
                case 2: goto L_0x0116;
                default: goto L_0x00f1;
            }     // Catch:{ IllegalArgumentException -> 0x010d }
        L_0x00f1:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch:{ IllegalArgumentException -> 0x010d }
            r5 = 45
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x010d }
            r6.<init>(r5)     // Catch:{ IllegalArgumentException -> 0x010d }
            java.lang.StringBuilder r3 = r6.append(r3)     // Catch:{ IllegalArgumentException -> 0x010d }
            java.lang.String r5 = " is not a valid enum InternalEvent"
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ IllegalArgumentException -> 0x010d }
            java.lang.String r3 = r3.toString()     // Catch:{ IllegalArgumentException -> 0x010d }
            r4.<init>(r3)     // Catch:{ IllegalArgumentException -> 0x010d }
            throw r4     // Catch:{ IllegalArgumentException -> 0x010d }
        L_0x010d:
            r3 = move-exception
            r8.zzmg(r2)
            r7.zza(r8, r0)
            goto L_0x0001
        L_0x0116:
            r7.zzprb = r3     // Catch:{ IllegalArgumentException -> 0x010d }
            goto L_0x0001
        L_0x011a:
            r0 = 160(0xa0, float:2.24E-43)
            int r2 = com.google.android.gms.internal.zzfjv.zzb(r8, r0)
            int[] r0 = r7.zzprc
            if (r0 != 0) goto L_0x0140
            r0 = r1
        L_0x0125:
            int r2 = r2 + r0
            int[] r2 = new int[r2]
            if (r0 == 0) goto L_0x012f
            int[] r3 = r7.zzprc
            java.lang.System.arraycopy(r3, r1, r2, r1, r0)
        L_0x012f:
            int r3 = r2.length
            int r3 = r3 + -1
            if (r0 >= r3) goto L_0x0144
            int r3 = r8.zzcvw()
            r2[r0] = r3
            r8.zzcvt()
            int r0 = r0 + 1
            goto L_0x012f
        L_0x0140:
            int[] r0 = r7.zzprc
            int r0 = r0.length
            goto L_0x0125
        L_0x0144:
            int r3 = r8.zzcvw()
            r2[r0] = r3
            r7.zzprc = r2
            goto L_0x0001
        L_0x014e:
            int r0 = r8.zzcwi()
            int r3 = r8.zzks(r0)
            int r2 = r8.getPosition()
            r0 = r1
        L_0x015b:
            int r4 = r8.zzcwk()
            if (r4 <= 0) goto L_0x0167
            r8.zzcvw()
            int r0 = r0 + 1
            goto L_0x015b
        L_0x0167:
            r8.zzmg(r2)
            int[] r2 = r7.zzprc
            if (r2 != 0) goto L_0x0185
            r2 = r1
        L_0x016f:
            int r0 = r0 + r2
            int[] r0 = new int[r0]
            if (r2 == 0) goto L_0x0179
            int[] r4 = r7.zzprc
            java.lang.System.arraycopy(r4, r1, r0, r1, r2)
        L_0x0179:
            int r4 = r0.length
            if (r2 >= r4) goto L_0x0189
            int r4 = r8.zzcvw()
            r0[r2] = r4
            int r2 = r2 + 1
            goto L_0x0179
        L_0x0185:
            int[] r2 = r7.zzprc
            int r2 = r2.length
            goto L_0x016f
        L_0x0189:
            r7.zzprc = r0
            r8.zzkt(r3)
            goto L_0x0001
        L_0x0190:
            long r2 = r8.zzcvv()
            r7.zzpqn = r2
            goto L_0x0001
        L_0x0198:
            long r2 = r8.zzcvv()
            r7.zzprd = r2
            goto L_0x0001
        L_0x01a0:
            com.google.android.gms.internal.zzfks r0 = r7.zzofi
            if (r0 != 0) goto L_0x01ab
            com.google.android.gms.internal.zzfks r0 = new com.google.android.gms.internal.zzfks
            r0.<init>()
            r7.zzofi = r0
        L_0x01ab:
            com.google.android.gms.internal.zzfks r0 = r7.zzofi
            r8.zza(r0)
            goto L_0x0001
        L_0x01b2:
            java.lang.String r0 = r8.readString()
            r7.zzpra = r0
            goto L_0x0001
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfkq.zza(com.google.android.gms.internal.zzfjj):com.google.android.gms.internal.zzfkq");
    }

    /* access modifiers changed from: private */
    /* renamed from: zzdaz */
    public final zzfkq clone() {
        try {
            zzfkq zzfkq = (zzfkq) super.clone();
            if (this.zzpqp != null && this.zzpqp.length > 0) {
                zzfkq.zzpqp = new zzfkr[this.zzpqp.length];
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= this.zzpqp.length) {
                        break;
                    }
                    if (this.zzpqp[i2] != null) {
                        zzfkq.zzpqp[i2] = (zzfkr) this.zzpqp[i2].clone();
                    }
                    i = i2 + 1;
                }
            }
            if (this.zzpqr != null) {
                zzfkq.zzpqr = (zzfko) this.zzpqr.clone();
            }
            if (this.zzpqv != null) {
                zzfkq.zzpqv = (zzfkn) this.zzpqv.clone();
            }
            if (this.zzpqy != null) {
                zzfkq.zzpqy = (zzfkp) this.zzpqy.clone();
            }
            if (this.zzprc != null && this.zzprc.length > 0) {
                zzfkq.zzprc = (int[]) this.zzprc.clone();
            }
            if (this.zzofi != null) {
                zzfkq.zzofi = (zzfks) this.zzofi.clone();
            }
            return zzfkq;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfkq)) {
            return false;
        }
        zzfkq zzfkq = (zzfkq) obj;
        if (this.zzpql != zzfkq.zzpql) {
            return false;
        }
        if (this.zzpqm != zzfkq.zzpqm) {
            return false;
        }
        if (this.zzpqn != zzfkq.zzpqn) {
            return false;
        }
        if (this.tag == null) {
            if (zzfkq.tag != null) {
                return false;
            }
        } else if (!this.tag.equals(zzfkq.tag)) {
            return false;
        }
        if (this.zzpqo != zzfkq.zzpqo) {
            return false;
        }
        if (this.zzala != zzfkq.zzala) {
            return false;
        }
        if (this.zzmsn != zzfkq.zzmsn) {
            return false;
        }
        if (!zzfjq.equals((Object[]) this.zzpqp, (Object[]) zzfkq.zzpqp)) {
            return false;
        }
        if (!Arrays.equals(this.zzpqq, zzfkq.zzpqq)) {
            return false;
        }
        if (this.zzpqr == null) {
            if (zzfkq.zzpqr != null) {
                return false;
            }
        } else if (!this.zzpqr.equals(zzfkq.zzpqr)) {
            return false;
        }
        if (!Arrays.equals(this.zzpqs, zzfkq.zzpqs)) {
            return false;
        }
        if (this.zzpqt == null) {
            if (zzfkq.zzpqt != null) {
                return false;
            }
        } else if (!this.zzpqt.equals(zzfkq.zzpqt)) {
            return false;
        }
        if (this.zzpqu == null) {
            if (zzfkq.zzpqu != null) {
                return false;
            }
        } else if (!this.zzpqu.equals(zzfkq.zzpqu)) {
            return false;
        }
        if (this.zzpqv == null) {
            if (zzfkq.zzpqv != null) {
                return false;
            }
        } else if (!this.zzpqv.equals(zzfkq.zzpqv)) {
            return false;
        }
        if (this.zzpqw == null) {
            if (zzfkq.zzpqw != null) {
                return false;
            }
        } else if (!this.zzpqw.equals(zzfkq.zzpqw)) {
            return false;
        }
        if (this.zzpqx != zzfkq.zzpqx) {
            return false;
        }
        if (this.zzpqy == null) {
            if (zzfkq.zzpqy != null) {
                return false;
            }
        } else if (!this.zzpqy.equals(zzfkq.zzpqy)) {
            return false;
        }
        if (!Arrays.equals(this.zzpqz, zzfkq.zzpqz)) {
            return false;
        }
        if (this.zzpra == null) {
            if (zzfkq.zzpra != null) {
                return false;
            }
        } else if (!this.zzpra.equals(zzfkq.zzpra)) {
            return false;
        }
        if (this.zzprb != zzfkq.zzprb) {
            return false;
        }
        if (!zzfjq.equals(this.zzprc, zzfkq.zzprc)) {
            return false;
        }
        if (this.zzprd != zzfkq.zzprd) {
            return false;
        }
        if (this.zzofi == null) {
            if (zzfkq.zzofi != null) {
                return false;
            }
        } else if (!this.zzofi.equals(zzfkq.zzofi)) {
            return false;
        }
        return (this.zzpnc == null || this.zzpnc.isEmpty()) ? zzfkq.zzpnc == null || zzfkq.zzpnc.isEmpty() : this.zzpnc.equals(zzfkq.zzpnc);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((this.zzmsn ? 1231 : 1237) + (((((((this.tag == null ? 0 : this.tag.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.zzpql ^ (this.zzpql >>> 32)))) * 31) + ((int) (this.zzpqm ^ (this.zzpqm >>> 32)))) * 31) + ((int) (this.zzpqn ^ (this.zzpqn >>> 32)))) * 31)) * 31) + this.zzpqo) * 31) + this.zzala) * 31)) * 31) + zzfjq.hashCode((Object[]) this.zzpqp)) * 31) + Arrays.hashCode(this.zzpqq);
        zzfko zzfko = this.zzpqr;
        int hashCode2 = (this.zzpqu == null ? 0 : this.zzpqu.hashCode()) + (((this.zzpqt == null ? 0 : this.zzpqt.hashCode()) + (((((zzfko == null ? 0 : zzfko.hashCode()) + (hashCode * 31)) * 31) + Arrays.hashCode(this.zzpqs)) * 31)) * 31);
        zzfkn zzfkn = this.zzpqv;
        int hashCode3 = (((this.zzpqw == null ? 0 : this.zzpqw.hashCode()) + (((zzfkn == null ? 0 : zzfkn.hashCode()) + (hashCode2 * 31)) * 31)) * 31) + ((int) (this.zzpqx ^ (this.zzpqx >>> 32)));
        zzfkp zzfkp = this.zzpqy;
        int hashCode4 = (((((((this.zzpra == null ? 0 : this.zzpra.hashCode()) + (((((zzfkp == null ? 0 : zzfkp.hashCode()) + (hashCode3 * 31)) * 31) + Arrays.hashCode(this.zzpqz)) * 31)) * 31) + this.zzprb) * 31) + zzfjq.hashCode(this.zzprc)) * 31) + ((int) (this.zzprd ^ (this.zzprd >>> 32)));
        zzfks zzfks = this.zzofi;
        int hashCode5 = ((zzfks == null ? 0 : zzfks.hashCode()) + (hashCode4 * 31)) * 31;
        if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
            i = this.zzpnc.hashCode();
        }
        return hashCode5 + i;
    }

    public final void zza(zzfjk zzfjk) throws IOException {
        if (this.zzpql != 0) {
            zzfjk.zzf(1, this.zzpql);
        }
        if (this.tag != null && !this.tag.equals("")) {
            zzfjk.zzn(2, this.tag);
        }
        if (this.zzpqp != null && this.zzpqp.length > 0) {
            for (zzfkr zzfkr : this.zzpqp) {
                if (zzfkr != null) {
                    zzfjk.zza(3, (zzfjs) zzfkr);
                }
            }
        }
        if (!Arrays.equals(this.zzpqq, zzfjv.zzpnv)) {
            zzfjk.zzc(4, this.zzpqq);
        }
        if (!Arrays.equals(this.zzpqs, zzfjv.zzpnv)) {
            zzfjk.zzc(6, this.zzpqs);
        }
        if (this.zzpqv != null) {
            zzfjk.zza(7, (zzfjs) this.zzpqv);
        }
        if (this.zzpqt != null && !this.zzpqt.equals("")) {
            zzfjk.zzn(8, this.zzpqt);
        }
        if (this.zzpqr != null) {
            zzfjk.zza(9, (zzfjs) this.zzpqr);
        }
        if (this.zzmsn) {
            zzfjk.zzl(10, this.zzmsn);
        }
        if (this.zzpqo != 0) {
            zzfjk.zzaa(11, this.zzpqo);
        }
        if (this.zzala != 0) {
            zzfjk.zzaa(12, this.zzala);
        }
        if (this.zzpqu != null && !this.zzpqu.equals("")) {
            zzfjk.zzn(13, this.zzpqu);
        }
        if (this.zzpqw != null && !this.zzpqw.equals("")) {
            zzfjk.zzn(14, this.zzpqw);
        }
        if (this.zzpqx != 180000) {
            zzfjk.zzg(15, this.zzpqx);
        }
        if (this.zzpqy != null) {
            zzfjk.zza(16, (zzfjs) this.zzpqy);
        }
        if (this.zzpqm != 0) {
            zzfjk.zzf(17, this.zzpqm);
        }
        if (!Arrays.equals(this.zzpqz, zzfjv.zzpnv)) {
            zzfjk.zzc(18, this.zzpqz);
        }
        if (this.zzprb != 0) {
            zzfjk.zzaa(19, this.zzprb);
        }
        if (this.zzprc != null && this.zzprc.length > 0) {
            for (int zzaa : this.zzprc) {
                zzfjk.zzaa(20, zzaa);
            }
        }
        if (this.zzpqn != 0) {
            zzfjk.zzf(21, this.zzpqn);
        }
        if (this.zzprd != 0) {
            zzfjk.zzf(22, this.zzprd);
        }
        if (this.zzofi != null) {
            zzfjk.zza(23, (zzfjs) this.zzofi);
        }
        if (this.zzpra != null && !this.zzpra.equals("")) {
            zzfjk.zzn(24, this.zzpra);
        }
        super.zza(zzfjk);
    }

    public final /* synthetic */ zzfjm zzdaf() throws CloneNotSupportedException {
        return (zzfkq) clone();
    }

    public final /* synthetic */ zzfjs zzdag() throws CloneNotSupportedException {
        return (zzfkq) clone();
    }

    /* access modifiers changed from: protected */
    public final int zzq() {
        int zzq = super.zzq();
        if (this.zzpql != 0) {
            zzq += zzfjk.zzc(1, this.zzpql);
        }
        if (this.tag != null && !this.tag.equals("")) {
            zzq += zzfjk.zzo(2, this.tag);
        }
        if (this.zzpqp != null && this.zzpqp.length > 0) {
            int i = zzq;
            for (zzfkr zzfkr : this.zzpqp) {
                if (zzfkr != null) {
                    i += zzfjk.zzb(3, (zzfjs) zzfkr);
                }
            }
            zzq = i;
        }
        if (!Arrays.equals(this.zzpqq, zzfjv.zzpnv)) {
            zzq += zzfjk.zzd(4, this.zzpqq);
        }
        if (!Arrays.equals(this.zzpqs, zzfjv.zzpnv)) {
            zzq += zzfjk.zzd(6, this.zzpqs);
        }
        if (this.zzpqv != null) {
            zzq += zzfjk.zzb(7, (zzfjs) this.zzpqv);
        }
        if (this.zzpqt != null && !this.zzpqt.equals("")) {
            zzq += zzfjk.zzo(8, this.zzpqt);
        }
        if (this.zzpqr != null) {
            zzq += zzfjk.zzb(9, (zzfjs) this.zzpqr);
        }
        if (this.zzmsn) {
            zzq += zzfjk.zzlg(10) + 1;
        }
        if (this.zzpqo != 0) {
            zzq += zzfjk.zzad(11, this.zzpqo);
        }
        if (this.zzala != 0) {
            zzq += zzfjk.zzad(12, this.zzala);
        }
        if (this.zzpqu != null && !this.zzpqu.equals("")) {
            zzq += zzfjk.zzo(13, this.zzpqu);
        }
        if (this.zzpqw != null && !this.zzpqw.equals("")) {
            zzq += zzfjk.zzo(14, this.zzpqw);
        }
        if (this.zzpqx != 180000) {
            zzq += zzfjk.zzh(15, this.zzpqx);
        }
        if (this.zzpqy != null) {
            zzq += zzfjk.zzb(16, (zzfjs) this.zzpqy);
        }
        if (this.zzpqm != 0) {
            zzq += zzfjk.zzc(17, this.zzpqm);
        }
        if (!Arrays.equals(this.zzpqz, zzfjv.zzpnv)) {
            zzq += zzfjk.zzd(18, this.zzpqz);
        }
        if (this.zzprb != 0) {
            zzq += zzfjk.zzad(19, this.zzprb);
        }
        if (this.zzprc != null && this.zzprc.length > 0) {
            int i2 = 0;
            for (int zzlh : this.zzprc) {
                i2 += zzfjk.zzlh(zzlh);
            }
            zzq = zzq + i2 + (this.zzprc.length * 2);
        }
        if (this.zzpqn != 0) {
            zzq += zzfjk.zzc(21, this.zzpqn);
        }
        if (this.zzprd != 0) {
            zzq += zzfjk.zzc(22, this.zzprd);
        }
        if (this.zzofi != null) {
            zzq += zzfjk.zzb(23, (zzfjs) this.zzofi);
        }
        return (this.zzpra == null || this.zzpra.equals("")) ? zzq : zzq + zzfjk.zzo(24, this.zzpra);
    }
}
