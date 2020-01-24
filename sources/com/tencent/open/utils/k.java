package com.tencent.open.utils;

/* compiled from: ProGuard */
public final class k implements Cloneable {
    private long a;

    public k(long j) {
        this.a = j;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof k) || this.a != ((k) obj).b()) {
            return false;
        }
        return true;
    }

    public byte[] a() {
        return new byte[]{(byte) ((int) (this.a & 255)), (byte) ((int) ((this.a & 65280) >> 8)), (byte) ((int) ((this.a & 16711680) >> 16)), (byte) ((int) ((this.a & 4278190080L) >> 24))};
    }

    public long b() {
        return this.a;
    }

    public int hashCode() {
        return (int) this.a;
    }
}
