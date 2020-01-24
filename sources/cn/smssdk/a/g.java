package cn.smssdk.a;

class g extends Thread {
    final /* synthetic */ f a;

    g(f fVar) {
        this.a = fVar;
    }

    public void run() {
        this.a.a.handleMessage(this.a.b);
    }
}
