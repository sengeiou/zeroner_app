package cn.smssdk.a;

import android.os.Handler.Callback;
import android.os.Message;

class f implements Runnable {
    final /* synthetic */ Callback a;
    final /* synthetic */ Message b;
    final /* synthetic */ a c;

    f(a aVar, Callback callback, Message message) {
        this.c = aVar;
        this.a = callback;
        this.b = message;
    }

    public void run() {
        new g(this).start();
    }
}
