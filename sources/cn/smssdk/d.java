package cn.smssdk;

import android.os.Handler.Callback;
import android.os.Message;

class d implements Callback {
    final /* synthetic */ a a;

    d(a aVar) {
        this.a = aVar;
    }

    public boolean handleMessage(Message message) {
        int i;
        if (message.what == 1) {
            i = -1;
        } else {
            i = 0;
        }
        this.a.a(7, i, message.obj);
        return false;
    }
}
