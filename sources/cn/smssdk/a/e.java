package cn.smssdk.a;

import cn.smssdk.utils.SMSLog;

class e extends Thread {
    final /* synthetic */ d a;

    e(d dVar) {
        this.a = dVar;
    }

    public void run() {
        String[] b = this.a.c.e.b();
        try {
            this.a.c.c.setBufferedContactPhones(b);
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
        }
        this.a.a.obj = b;
        this.a.b.handleMessage(this.a.a);
    }
}
