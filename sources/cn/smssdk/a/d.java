package cn.smssdk.a;

import android.os.Handler.Callback;
import android.os.Message;

class d implements Runnable {
    final /* synthetic */ Message a;
    final /* synthetic */ Callback b;
    final /* synthetic */ a c;

    d(a aVar, Message message, Callback callback) {
        this.c = aVar;
        this.a = message;
        this.b = callback;
    }

    public void run() {
        new e(this).start();
    }
}
