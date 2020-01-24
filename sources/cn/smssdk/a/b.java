package cn.smssdk.a;

import android.os.Handler.Callback;
import android.os.Message;
import java.util.ArrayList;

class b implements Callback {
    final /* synthetic */ Callback a;
    final /* synthetic */ a b;

    b(a aVar, Callback callback) {
        this.b = aVar;
        this.a = callback;
    }

    public boolean handleMessage(Message message) {
        ArrayList a2;
        Message message2 = new Message();
        try {
            a2 = this.b.a(this.b.b.a((String[]) message.obj));
            this.b.c.setBufferedNewFriends(a2);
            this.b.c.setRequestNewFriendsTime();
        } catch (Throwable th) {
            message2.what = 0;
            message2.obj = th;
        }
        message2.what = 1;
        message2.obj = Integer.valueOf(a2.size());
        this.a.handleMessage(message2);
        return false;
    }
}
