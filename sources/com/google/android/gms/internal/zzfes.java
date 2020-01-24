package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public abstract class zzfes implements Serializable, Iterable<Byte> {
    public static final zzfes zzpfg = new zzfez(zzffz.EMPTY_BYTE_ARRAY);
    private static final zzfew zzpfh;
    private int zzmal = 0;

    static {
        boolean z = true;
        try {
            Class.forName("android.content.Context");
        } catch (ClassNotFoundException e) {
            z = false;
        }
        zzpfh = z ? new zzffa(null) : new zzfeu(null);
    }

    zzfes() {
    }

    private static zzfes zza(Iterator<zzfes> it, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException(String.format("length (%s) must be >= 1", new Object[]{Integer.valueOf(i)}));
        } else if (i == 1) {
            return (zzfes) it.next();
        } else {
            int i2 = i >>> 1;
            zzfes zza = zza(it, i2);
            zzfes zza2 = zza(it, i - i2);
            if (Integer.MAX_VALUE - zza.size() >= zza2.size()) {
                return zzfhq.zza(zza, zza2);
            }
            int size = zza.size();
            throw new IllegalArgumentException("ByteString would be too long: " + size + "+" + zza2.size());
        }
    }

    public static zzfes zzaz(byte[] bArr) {
        return zze(bArr, 0, bArr.length);
    }

    static zzfes zzba(byte[] bArr) {
        return new zzfez(bArr);
    }

    public static zzfes zze(byte[] bArr, int i, int i2) {
        return new zzfez(zzpfh.zzf(bArr, i, i2));
    }

    public static zzfes zzf(Iterable<zzfes> iterable) {
        int size = ((Collection) iterable).size();
        return size == 0 ? zzpfg : zza(iterable.iterator(), size);
    }

    static int zzh(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException("Beginning index: " + i + " < 0");
        } else if (i2 < i) {
            throw new IndexOutOfBoundsException("Beginning index larger than ending index: " + i + ", " + i2);
        } else {
            throw new IndexOutOfBoundsException("End index: " + i2 + " >= " + i3);
        }
    }

    static zzfex zzko(int i) {
        return new zzfex(i, null);
    }

    public static zzfes zztr(String str) {
        return new zzfez(str.getBytes(zzffz.UTF_8));
    }

    static void zzy(int i, int i2) {
        if (((i2 - (i + 1)) | i) >= 0) {
            return;
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("Index < 0: " + i);
        }
        throw new ArrayIndexOutOfBoundsException("Index > length: " + i + ", " + i2);
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int i = this.zzmal;
        if (i == 0) {
            int size = size();
            i = zzg(size, 0, size);
            if (i == 0) {
                i = 1;
            }
            this.zzmal = i;
        }
        return i;
    }

    public final boolean isEmpty() {
        return size() == 0;
    }

    public /* synthetic */ Iterator iterator() {
        return new zzfet(this);
    }

    public abstract int size();

    public final byte[] toByteArray() {
        int size = size();
        if (size == 0) {
            return zzffz.EMPTY_BYTE_ARRAY;
        }
        byte[] bArr = new byte[size];
        zzb(bArr, 0, 0, size);
        return bArr;
    }

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())});
    }

    /* access modifiers changed from: 0000 */
    public abstract void zza(zzfer zzfer) throws IOException;

    public final void zza(byte[] bArr, int i, int i2, int i3) {
        zzh(i, i + i3, size());
        zzh(i2, i2 + i3, bArr.length);
        if (i3 > 0) {
            zzb(bArr, i, i2, i3);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zzb(byte[] bArr, int i, int i2, int i3);

    public abstract zzffb zzcvm();

    /* access modifiers changed from: protected */
    public abstract int zzcvn();

    /* access modifiers changed from: protected */
    public abstract boolean zzcvo();

    /* access modifiers changed from: protected */
    public final int zzcvp() {
        return this.zzmal;
    }

    /* access modifiers changed from: protected */
    public abstract int zzg(int i, int i2, int i3);

    public abstract byte zzkn(int i);

    public abstract zzfes zzx(int i, int i2);
}
