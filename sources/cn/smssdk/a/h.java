package cn.smssdk.a;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import cn.smssdk.contact.d;
import cn.smssdk.net.j;
import cn.smssdk.utils.SMSLog;
import cn.smssdk.utils.SPHelper;
import java.util.ArrayList;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

public class h implements Callback {
    private Handler a = new Handler(this);
    private d b;
    private SPHelper c;
    private j d;
    private a e;
    /* access modifiers changed from: private */
    public int f;
    /* access modifiers changed from: private */
    public Callback g;

    public h(Context context, a aVar) {
        this.b = d.a(context);
        this.c = SPHelper.getInstance(context);
        this.d = j.a(context);
        this.e = aVar;
    }

    public void a(int i, Callback callback) {
        this.a.removeMessages(1);
        this.f = i;
        this.g = callback;
        this.a.sendEmptyMessageDelayed(1, BootloaderScanner.TIMEOUT);
    }

    public boolean handleMessage(Message message) {
        new i(this).start();
        return false;
    }

    public int a() {
        String[] strArr;
        String[] b2 = this.b.b();
        try {
            strArr = this.c.getBufferedContactPhones();
            this.c.setBufferedContactPhones(b2);
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
            strArr = new String[0];
        }
        ArrayList arrayList = new ArrayList();
        for (String str : b2) {
            if (str != null) {
                boolean z = true;
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    } else if (str.equals(strArr[i])) {
                        z = false;
                        break;
                    } else {
                        i++;
                    }
                }
                if (z) {
                    arrayList.add(str);
                }
            }
        }
        if (arrayList.size() <= 0) {
            return 0;
        }
        String[] strArr2 = new String[arrayList.size()];
        for (int i2 = 0; i2 < strArr2.length; i2++) {
            strArr2[i2] = (String) arrayList.get(i2);
        }
        ArrayList a2 = this.e.a(this.d.a(strArr2));
        try {
            this.c.setBufferedNewFriends(a2);
            this.c.setRequestNewFriendsTime();
        } catch (Throwable th2) {
            SMSLog.getInstance().w(th2);
        }
        return a2.size();
    }
}
