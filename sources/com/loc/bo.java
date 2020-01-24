package com.loc;

/* compiled from: DownloadManager */
public final class bo {
    private bp a;
    private bq b;

    /* compiled from: DownloadManager */
    public interface a {
        void a(byte[] bArr, long j);

        void b();

        void c();

        void d();
    }

    public bo(bq bqVar) {
        this(bqVar, 0);
    }

    private bo(bq bqVar, byte b2) {
        this(bqVar, 0);
    }

    private bo(bq bqVar, char c) {
        this.b = bqVar;
        this.a = new bp(this.b.c, this.b.d, bqVar.e == null ? null : bqVar.e, false);
        this.a.b();
        this.a.a();
    }

    public final void a(a aVar) {
        this.a.a(this.b.c(), this.b.k(), this.b.j(), this.b.b(), this.b.b_(), this.b.d(), aVar);
    }
}
