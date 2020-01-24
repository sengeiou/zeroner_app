package cn.smssdk.contact;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import cn.smssdk.net.j;
import cn.smssdk.utils.SMSLog;
import cn.smssdk.utils.SPHelper;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

public class m implements Callback {
    private Handler a = new Handler(this);
    /* access modifiers changed from: private */
    public d b;
    /* access modifiers changed from: private */
    public SPHelper c;
    /* access modifiers changed from: private */
    public j d;
    private String e;

    public m(Context context, d dVar) {
        this.b = dVar;
        this.c = SPHelper.getInstance(context);
        this.d = j.a(context);
        this.e = R.getCacheRoot(context) + ".slock";
    }

    public void a() {
        if (!b()) {
            this.a.removeMessages(1);
            this.a.sendEmptyMessageDelayed(1, 180000);
        }
    }

    public boolean handleMessage(Message message) {
        new n(this).start();
        return false;
    }

    /* access modifiers changed from: private */
    public String a(Object obj) {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        objectOutputStream.close();
        return Data.CRC32(byteArrayOutputStream.toByteArray());
    }

    private boolean b() {
        try {
            File file = new File(this.e);
            if (file.exists()) {
                if (System.currentTimeMillis() - file.lastModified() < 86400000) {
                    return true;
                }
                file.delete();
                file.createNewFile();
                return false;
            }
            file.createNewFile();
            return false;
        } catch (Exception e2) {
            SMSLog.getInstance().w(e2);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        try {
            File file = new File(this.e);
            if (file.exists()) {
                Thread.sleep(BootloaderScanner.TIMEOUT);
                file.delete();
            }
        } catch (Exception e2) {
            SMSLog.getInstance().w(e2);
        }
    }
}
