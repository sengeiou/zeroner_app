package com.tencent.bugly.proguard;

/* compiled from: BUGLY */
public final class ba extends m implements Cloneable {
    static byte[] d;
    public byte a = 0;
    public String b = "";
    public byte[] c = null;

    public ba() {
    }

    public ba(byte b2, String str, byte[] bArr) {
        this.a = b2;
        this.b = str;
        this.c = bArr;
    }

    public void a(l lVar) {
        lVar.b(this.a, 0);
        lVar.a(this.b, 1);
        if (this.c != null) {
            lVar.a(this.c, 2);
        }
    }

    public void a(k kVar) {
        this.a = kVar.a(this.a, 0, true);
        this.b = kVar.a(1, true);
        if (d == null) {
            d = new byte[1];
            d[0] = 0;
        }
        this.c = kVar.a(d, 2, false);
    }

    public void a(StringBuilder sb, int i) {
    }
}