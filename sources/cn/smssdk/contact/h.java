package cn.smssdk.contact;

class h implements Runnable {
    final /* synthetic */ Runnable a;
    final /* synthetic */ d b;

    h(d dVar, Runnable runnable) {
        this.b = dVar;
        this.a = runnable;
    }

    public void run() {
        new i(this).start();
    }
}
