package cn.smssdk.a;

import android.os.Handler.Callback;
import android.os.Message;
import com.iwown.data_link.consts.UserConst;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class c implements Callback {
    final /* synthetic */ Callback a;
    final /* synthetic */ a b;

    c(a aVar, Callback callback) {
        this.b = aVar;
        this.a = callback;
    }

    public boolean handleMessage(Message message) {
        ArrayList a2;
        ArrayList a3;
        Message message2 = new Message();
        try {
            a2 = this.b.b.a((String[]) message.obj);
            a3 = this.b.a(a2);
            this.b.c.setBufferedFriends(a2);
            this.b.c.setBufferedNewFriends(new ArrayList());
        } catch (Throwable th) {
            message2.what = 0;
            message2.obj = th;
        }
        Iterator it = a3.iterator();
        while (it.hasNext()) {
            Object obj = ((HashMap) it.next()).get(UserConst.PHONE);
            if (obj != null) {
                Iterator it2 = a2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    HashMap hashMap = (HashMap) it2.next();
                    if (obj.equals(hashMap.get(UserConst.PHONE))) {
                        hashMap.put("isnew", Boolean.valueOf(true));
                        break;
                    }
                }
            }
        }
        message2.what = 1;
        message2.obj = a2;
        this.a.handleMessage(message2);
        return false;
    }
}
