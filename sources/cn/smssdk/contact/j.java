package cn.smssdk.contact;

class j implements Runnable {
    final /* synthetic */ Runnable a;
    final /* synthetic */ d b;

    j(d dVar, Runnable runnable) {
        this.b = dVar;
        this.a = runnable;
    }

    public void run() {
        if (this.a != null) {
            this.a.run();
        }
    }
}
