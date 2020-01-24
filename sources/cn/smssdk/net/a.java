package cn.smssdk.net;

import cn.smssdk.utils.SMSLog;
import com.iwown.device_module.device_firmware_upgrade.bean.FwUpdateInfo;
import com.mob.tools.network.ByteArrayPart;
import com.mob.tools.network.HTTPPart;
import com.mob.tools.network.HttpResponseCallback;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class a {
    private static NetworkHelper i;
    private static NetworkTimeOut j;
    protected int a;
    protected String b;
    protected String c;
    protected int d;
    protected boolean e;
    protected boolean f;
    protected boolean g = false;
    protected ReentrantReadWriteLock h;

    /* access modifiers changed from: protected */
    public abstract HashMap<String, Object> a(String str, String str2, HashMap<String, Object> hashMap);

    public abstract boolean b();

    public int a() {
        return this.a;
    }

    public void a(ReentrantReadWriteLock reentrantReadWriteLock) {
        this.h = reentrantReadWriteLock;
    }

    /* JADX INFO: finally extract failed */
    public String b(String str, String str2, HashMap<String, Object> hashMap) {
        if (b()) {
            throw new Throwable("{\"status\":464}");
        }
        try {
            if (this.h != null) {
                this.h.readLock().lock();
            }
            String a2 = a(this.c, a(a(str, str2, hashMap), this.e, this.d), str2, this.d);
            if (this.h != null) {
                this.h.readLock().unlock();
            }
            return a2;
        } catch (Throwable th) {
            if (this.h != null) {
                this.h.readLock().unlock();
            }
            throw th;
        }
    }

    private static String a(String str, byte[] bArr, String str2, int i2) {
        int i3;
        String a2;
        if (i == null || j == null) {
            j = new NetworkTimeOut();
            j.connectionTimeout = FwUpdateInfo.UPDATE_TIME_OUT;
            j.readTimout = FwUpdateInfo.UPDATE_TIME_OUT;
            i = new NetworkHelper();
        }
        ArrayList a3 = i.a().a(bArr, str2);
        ByteArrayPart byteArrayPart = new ByteArrayPart();
        byteArrayPart.append(bArr);
        HashMap hashMap = new HashMap();
        i.rawPost(str, a3, (HTTPPart) byteArrayPart, (HttpResponseCallback) new HttpResponseCallbackImp(hashMap), j);
        if (hashMap == null || hashMap.size() <= 0) {
            throw new Throwable("[map]Response is empty");
        }
        Object obj = hashMap.get("httpStatus");
        if (obj != null) {
            i3 = ((Integer) obj).intValue();
        } else {
            i3 = 0;
        }
        byte[] bArr2 = (byte[]) hashMap.get("bResp");
        if (bArr2 == null || bArr2.length <= 0) {
            throw new Throwable("[resp]Response is empty");
        }
        if (i3 == 600) {
            a2 = new String(bArr2, "utf-8");
        } else {
            a2 = a(bArr2, i2);
        }
        SMSLog.getInstance().d("resp: " + a2, new Object[0]);
        return a2;
    }

    private static byte[] a(HashMap<String, Object> hashMap, boolean z, int i2) {
        return g.a(hashMap, z, i2);
    }

    private static String a(byte[] bArr, int i2) {
        return g.a(bArr, i2);
    }
}
