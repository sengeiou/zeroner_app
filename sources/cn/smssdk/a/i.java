package cn.smssdk.a;

import android.os.Message;
import cn.smssdk.utils.SMSLog;

class i extends Thread {
    final /* synthetic */ h a;

    i(h hVar) {
        this.a = hVar;
    }

    public void run() {
        try {
            int a2 = this.a.a();
            if (this.a.g != null) {
                Message message = new Message();
                message.what = this.a.f;
                message.arg1 = a2;
                this.a.g.handleMessage(message);
            }
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
        }
    }
}
