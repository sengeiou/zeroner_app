package com.tencent.stat;

class v implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ n b;

    v(n nVar, int i) {
        this.b = nVar;
        this.a = i;
    }

    public void run() {
        int a2 = StatConfig.a();
        int i = this.a == -1 ? this.b.b : this.a;
        int i2 = i / a2;
        int i3 = i % a2;
        for (int i4 = 0; i4 < i2 + 1; i4++) {
            this.b.b(a2);
        }
        if (i3 > 0) {
            this.b.b(i3);
        }
    }
}
