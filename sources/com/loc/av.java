package com.loc;

/* compiled from: DexDownloadItem */
public final class av {
    protected String a;
    String b;
    String c;
    String d;
    String e;
    int f;
    int g;
    private String h;
    private boolean i;
    private boolean j;
    private boolean k;

    public av(String str, String str2, boolean z) {
        this(str, str2, z, 0);
    }

    private av(String str, String str2, boolean z, byte b2) {
        this.i = false;
        this.j = false;
        this.k = true;
        this.a = str;
        this.h = str2;
        this.i = false;
        this.k = z;
        try {
            String[] split = str.split("/");
            int length = split.length;
            if (length > 1) {
                this.b = split[length - 1];
                String[] split2 = this.b.split("_");
                this.c = split2[0];
                this.d = split2[2];
                this.e = split2[1];
                this.f = Integer.parseInt(split2[3]);
                this.g = Integer.parseInt(split2[4].split("\\.")[0]);
            }
        } catch (Throwable th) {
            ag.a(th, "DexDownloadItem", "DexDownloadItem");
        }
    }

    /* access modifiers changed from: 0000 */
    public final String a() {
        return this.a;
    }

    public final void a(boolean z) {
        this.j = z;
    }

    public final String b() {
        return this.h;
    }

    public final boolean c() {
        return this.i;
    }

    public final boolean d() {
        return this.j;
    }

    public final boolean e() {
        return this.k;
    }
}
