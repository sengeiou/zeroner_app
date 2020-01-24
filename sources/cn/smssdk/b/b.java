package cn.smssdk.b;

import cn.smssdk.utils.SMSLog;

class b implements Runnable {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void run() {
        try {
            this.a.a();
        } catch (Throwable th) {
            SMSLog.getInstance().d(th);
        }
    }
}
