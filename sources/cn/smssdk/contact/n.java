package cn.smssdk.contact;

import cn.smssdk.utils.SMSLog;
import java.util.ArrayList;

class n extends Thread {
    final /* synthetic */ m a;

    n(m mVar) {
        this.a = mVar;
    }

    public void run() {
        String str;
        String str2 = null;
        try {
            String verifyCountry = this.a.c.getVerifyCountry();
            str = this.a.c.getVerifyPhone();
            str2 = verifyCountry;
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
        }
        ArrayList a2 = this.a.b.a(false);
        String a3 = this.a.a((Object) a2);
        String bufferedContactsSignature = this.a.c.getBufferedContactsSignature();
        if (a2 != null && !a2.isEmpty() && a3 != null && !a3.equals(bufferedContactsSignature)) {
            this.a.d.a(str2, str, a2);
        }
        this.a.c.setBufferedContactsSignature(a3);
        this.a.c();
    }
}
