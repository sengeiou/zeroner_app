package com.google.android.gms.internal;

import com.google.android.gms.internal.zzffs;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzffq<FieldDescriptorType extends zzffs<FieldDescriptorType>> {
    private static final zzffq zzpgm = new zzffq(true);
    private boolean zzktj;
    private final zzfhy<FieldDescriptorType, Object> zzpgk = zzfhy.zzma(16);
    private boolean zzpgl = false;

    private zzffq() {
    }

    private zzffq(boolean z) {
        if (!this.zzktj) {
            this.zzpgk.zzbiy();
            this.zzktj = true;
        }
    }

    static int zza(zzfiy zzfiy, int i, Object obj) {
        int i2;
        int zzlg = zzffg.zzlg(i);
        if (zzfiy == zzfiy.GROUP) {
            zzffz.zzh((zzfhe) obj);
            i2 = zzlg << 1;
        } else {
            i2 = zzlg;
        }
        return i2 + zzb(zzfiy, obj);
    }

    public static Object zza(zzffb zzffb, zzfiy zzfiy, boolean z) throws IOException {
        zzfje zzfje = zzfje.STRICT;
        switch (zzfix.zzpgo[zzfiy.ordinal()]) {
            case 1:
                return Double.valueOf(zzffb.readDouble());
            case 2:
                return Float.valueOf(zzffb.readFloat());
            case 3:
                return Long.valueOf(zzffb.zzcvv());
            case 4:
                return Long.valueOf(zzffb.zzcvu());
            case 5:
                return Integer.valueOf(zzffb.zzcvw());
            case 6:
                return Long.valueOf(zzffb.zzcvx());
            case 7:
                return Integer.valueOf(zzffb.zzcvy());
            case 8:
                return Boolean.valueOf(zzffb.zzcvz());
            case 9:
                return zzffb.zzcwb();
            case 10:
                return Integer.valueOf(zzffb.zzcwc());
            case 11:
                return Integer.valueOf(zzffb.zzcwe());
            case 12:
                return Long.valueOf(zzffb.zzcwf());
            case 13:
                return Integer.valueOf(zzffb.zzcwg());
            case 14:
                return Long.valueOf(zzffb.zzcwh());
            case 15:
                return zzfje.zza(zzffb);
            case 16:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
            case 17:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
            case 18:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    static void zza(zzffg zzffg, zzfiy zzfiy, int i, Object obj) throws IOException {
        if (zzfiy == zzfiy.GROUP) {
            zzffz.zzh((zzfhe) obj);
            zzffg.zze(i, (zzfhe) obj);
            return;
        }
        zzffg.zzz(i, zzfiy.zzdae());
        switch (zzfiy) {
            case DOUBLE:
                zzffg.zzn(((Double) obj).doubleValue());
                return;
            case FLOAT:
                zzffg.zzf(((Float) obj).floatValue());
                return;
            case INT64:
                zzffg.zzct(((Long) obj).longValue());
                return;
            case UINT64:
                zzffg.zzct(((Long) obj).longValue());
                return;
            case INT32:
                zzffg.zzlc(((Integer) obj).intValue());
                return;
            case FIXED64:
                zzffg.zzcv(((Long) obj).longValue());
                return;
            case FIXED32:
                zzffg.zzlf(((Integer) obj).intValue());
                return;
            case BOOL:
                zzffg.zzdd(((Boolean) obj).booleanValue());
                return;
            case GROUP:
                ((zzfhe) obj).zza(zzffg);
                return;
            case MESSAGE:
                zzffg.zze((zzfhe) obj);
                return;
            case STRING:
                if (obj instanceof zzfes) {
                    zzffg.zzay((zzfes) obj);
                    return;
                } else {
                    zzffg.zzts((String) obj);
                    return;
                }
            case BYTES:
                if (obj instanceof zzfes) {
                    zzffg.zzay((zzfes) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzffg.zzi(bArr, 0, bArr.length);
                return;
            case UINT32:
                zzffg.zzld(((Integer) obj).intValue());
                return;
            case SFIXED32:
                zzffg.zzlf(((Integer) obj).intValue());
                return;
            case SFIXED64:
                zzffg.zzcv(((Long) obj).longValue());
                return;
            case SINT32:
                zzffg.zzle(((Integer) obj).intValue());
                return;
            case SINT64:
                zzffg.zzcu(((Long) obj).longValue());
                return;
            case ENUM:
                if (obj instanceof zzfga) {
                    zzffg.zzlc(((zzfga) obj).zzhq());
                    return;
                } else {
                    zzffg.zzlc(((Integer) obj).intValue());
                    return;
                }
            default:
                return;
        }
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zza(FieldDescriptorType r7, java.lang.Object r8) {
        /*
            r6 = this;
            boolean r0 = r7.zzcxj()
            if (r0 == 0) goto L_0x0035
            boolean r0 = r8 instanceof java.util.List
            if (r0 != 0) goto L_0x0013
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Wrong object type used with protocol message reflection."
            r0.<init>(r1)
            throw r0
        L_0x0013:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.List r8 = (java.util.List) r8
            r1.addAll(r8)
            r0 = r1
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            int r3 = r0.size()
            r2 = 0
        L_0x0025:
            if (r2 >= r3) goto L_0x003d
            java.lang.Object r4 = r0.get(r2)
            int r2 = r2 + 1
            com.google.android.gms.internal.zzfiy r5 = r7.zzcxh()
            zza(r5, r4)
            goto L_0x0025
        L_0x0035:
            com.google.android.gms.internal.zzfiy r0 = r7.zzcxh()
            zza(r0, r8)
            r1 = r8
        L_0x003d:
            boolean r0 = r1 instanceof com.google.android.gms.internal.zzfgg
            if (r0 == 0) goto L_0x0044
            r0 = 1
            r6.zzpgl = r0
        L_0x0044:
            com.google.android.gms.internal.zzfhy<FieldDescriptorType, java.lang.Object> r0 = r6.zzpgk
            r0.put(r7, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzffq.zza(com.google.android.gms.internal.zzffs, java.lang.Object):void");
    }

    private static void zza(zzfiy zzfiy, Object obj) {
        boolean z = false;
        zzffz.checkNotNull(obj);
        switch (zzfiy.zzdad()) {
            case INT:
                z = obj instanceof Integer;
                break;
            case LONG:
                z = obj instanceof Long;
                break;
            case FLOAT:
                z = obj instanceof Float;
                break;
            case DOUBLE:
                z = obj instanceof Double;
                break;
            case BOOLEAN:
                z = obj instanceof Boolean;
                break;
            case STRING:
                z = obj instanceof String;
                break;
            case BYTE_STRING:
                if ((obj instanceof zzfes) || (obj instanceof byte[])) {
                    z = true;
                    break;
                }
            case ENUM:
                if ((obj instanceof Integer) || (obj instanceof zzfga)) {
                    z = true;
                    break;
                }
            case MESSAGE:
                if ((obj instanceof zzfhe) || (obj instanceof zzfgg)) {
                    z = true;
                    break;
                }
        }
        if (!z) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    private static int zzb(zzffs<?> zzffs, Object obj) {
        int i = 0;
        zzfiy zzcxh = zzffs.zzcxh();
        int zzhq = zzffs.zzhq();
        if (!zzffs.zzcxj()) {
            return zza(zzcxh, zzhq, obj);
        }
        if (zzffs.zzcxk()) {
            for (Object zzb : (List) obj) {
                i += zzb(zzcxh, zzb);
            }
            return zzffg.zzlp(i) + zzffg.zzlg(zzhq) + i;
        }
        for (Object zza : (List) obj) {
            i += zza(zzcxh, zzhq, zza);
        }
        return i;
    }

    private static int zzb(zzfiy zzfiy, Object obj) {
        switch (zzfiy) {
            case DOUBLE:
                return zzffg.zzo(((Double) obj).doubleValue());
            case FLOAT:
                return zzffg.zzg(((Float) obj).floatValue());
            case INT64:
                return zzffg.zzcw(((Long) obj).longValue());
            case UINT64:
                return zzffg.zzcx(((Long) obj).longValue());
            case INT32:
                return zzffg.zzlh(((Integer) obj).intValue());
            case FIXED64:
                return zzffg.zzcz(((Long) obj).longValue());
            case FIXED32:
                return zzffg.zzlk(((Integer) obj).intValue());
            case BOOL:
                return zzffg.zzde(((Boolean) obj).booleanValue());
            case GROUP:
                return zzffg.zzg((zzfhe) obj);
            case MESSAGE:
                return obj instanceof zzfgg ? zzffg.zza((zzfgg) obj) : zzffg.zzf((zzfhe) obj);
            case STRING:
                return obj instanceof zzfes ? zzffg.zzaz((zzfes) obj) : zzffg.zztt((String) obj);
            case BYTES:
                return obj instanceof zzfes ? zzffg.zzaz((zzfes) obj) : zzffg.zzbd((byte[]) obj);
            case UINT32:
                return zzffg.zzli(((Integer) obj).intValue());
            case SFIXED32:
                return zzffg.zzll(((Integer) obj).intValue());
            case SFIXED64:
                return zzffg.zzda(((Long) obj).longValue());
            case SINT32:
                return zzffg.zzlj(((Integer) obj).intValue());
            case SINT64:
                return zzffg.zzcy(((Long) obj).longValue());
            case ENUM:
                return obj instanceof zzfga ? zzffg.zzlm(((zzfga) obj).zzhq()) : zzffg.zzlm(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    private static int zzb(Entry<FieldDescriptorType, Object> entry) {
        zzffs zzffs = (zzffs) entry.getKey();
        Object value = entry.getValue();
        return (zzffs.zzcxi() != zzfjd.MESSAGE || zzffs.zzcxj() || zzffs.zzcxk()) ? zzb(zzffs, value) : value instanceof zzfgg ? zzffg.zzb(((zzffs) entry.getKey()).zzhq(), (zzfgk) (zzfgg) value) : zzffg.zzd(((zzffs) entry.getKey()).zzhq(), (zzfhe) value);
    }

    public static <T extends zzffs<T>> zzffq<T> zzcxf() {
        return new zzffq<>();
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzffq zzffq = new zzffq();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.zzpgk.zzczj()) {
                break;
            }
            Entry zzmb = this.zzpgk.zzmb(i2);
            zzffq.zza((FieldDescriptorType) (zzffs) zzmb.getKey(), zzmb.getValue());
            i = i2 + 1;
        }
        for (Entry entry : this.zzpgk.zzczk()) {
            zzffq.zza((FieldDescriptorType) (zzffs) entry.getKey(), entry.getValue());
        }
        zzffq.zzpgl = this.zzpgl;
        return zzffq;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzffq)) {
            return false;
        }
        return this.zzpgk.equals(((zzffq) obj).zzpgk);
    }

    public final int hashCode() {
        return this.zzpgk.hashCode();
    }

    public final Iterator<Entry<FieldDescriptorType, Object>> iterator() {
        return this.zzpgl ? new zzfgj(this.zzpgk.entrySet().iterator()) : this.zzpgk.entrySet().iterator();
    }

    public final int zzcxg() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzpgk.zzczj(); i2++) {
            i += zzb(this.zzpgk.zzmb(i2));
        }
        for (Entry zzb : this.zzpgk.zzczk()) {
            i += zzb(zzb);
        }
        return i;
    }
}
