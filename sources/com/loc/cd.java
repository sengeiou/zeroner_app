package com.loc;

/* compiled from: LogJsonDataStrategy */
public final class cd extends cg {
    private StringBuilder a = new StringBuilder();
    private boolean b = true;

    public cd() {
    }

    public cd(cg cgVar) {
        super(cgVar);
    }

    /* access modifiers changed from: protected */
    public final byte[] a(byte[] bArr) {
        byte[] a2 = w.a(this.a.toString());
        this.d = a2;
        this.b = true;
        this.a.delete(0, this.a.length());
        return a2;
    }

    public final void b(byte[] bArr) {
        String a2 = w.a(bArr);
        if (this.b) {
            this.b = false;
        } else {
            this.a.append(",");
        }
        this.a.append("{\"log\":\"").append(a2).append("\"}");
    }
}
