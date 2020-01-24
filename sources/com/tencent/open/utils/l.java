package com.tencent.open.utils;

import com.google.common.primitives.UnsignedBytes;

/* compiled from: ProGuard */
public final class l implements Cloneable {
    private int a;

    public l(byte[] bArr) {
        this(bArr, 0);
    }

    public l(byte[] bArr, int i) {
        this.a = (bArr[i + 1] << 8) & 65280;
        this.a += bArr[i] & UnsignedBytes.MAX_VALUE;
    }

    public l(int i) {
        this.a = i;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof l) || this.a != ((l) obj).b()) {
            return false;
        }
        return true;
    }

    public byte[] a() {
        return new byte[]{(byte) (this.a & 255), (byte) ((this.a & 65280) >> 8)};
    }

    public int b() {
        return this.a;
    }

    public int hashCode() {
        return this.a;
    }
}
