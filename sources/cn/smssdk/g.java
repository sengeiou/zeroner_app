package cn.smssdk;

import android.os.Handler.Callback;
import android.os.Message;

class g implements Callback {
    final /* synthetic */ f a;

    g(f fVar) {
        this.a = fVar;
    }

    public boolean handleMessage(Message message) {
        if (message.arg1 > 0) {
            this.a.a.a(7, -1, (Object) Integer.valueOf(message.arg1));
        }
        return false;
    }
}
