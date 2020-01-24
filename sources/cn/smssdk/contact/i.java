package cn.smssdk.contact;

import cn.smssdk.utils.SMSLog;

class i extends Thread {
    final /* synthetic */ h a;

    i(h hVar) {
        this.a = hVar;
    }

    public void run() {
        try {
            this.a.b.b(true);
            if (this.a.a != null) {
                this.a.a.run();
            }
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
        }
    }
}
