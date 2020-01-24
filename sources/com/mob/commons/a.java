package com.mob.commons;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.iwown.sport_module.ui.repository.DataSource;
import com.mob.tools.MobLog;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: CommonConfig */
public class a {
    private static HashMap<String, Object> a;
    /* access modifiers changed from: private */
    public static long b;
    private static long c;
    /* access modifiers changed from: private */
    public static boolean d;

    public static long a(Context context) {
        long j;
        q(context);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            j = Long.valueOf(String.valueOf(a.get("deviceTime"))).longValue();
        } catch (Throwable th) {
            j = 0;
        }
        return ((Long) R.forceCast(a.get("serverTime"), Long.valueOf(0))).longValue() + (elapsedRealtime - j);
    }

    public static boolean b(Context context) {
        q(context);
        return 1 == ((Integer) R.forceCast(a.get("rt"), Integer.valueOf(1))).intValue();
    }

    public static int c(Context context) {
        q(context);
        return ((Integer) R.forceCast(a.get("rtsr"), Integer.valueOf(DataSource.NetDataMaxTimeMs))).intValue();
    }

    public static boolean d(Context context) {
        q(context);
        return 1 == ((Integer) R.forceCast(a.get("all"), Integer.valueOf(1))).intValue();
    }

    public static long e(Context context) {
        q(context);
        return ((Long) R.forceCast(a.get("aspa"), Long.valueOf(2592000))).longValue();
    }

    public static boolean f(Context context) {
        q(context);
        return 1 == ((Integer) R.forceCast(a.get("di"), Integer.valueOf(1))).intValue();
    }

    public static boolean g(Context context) {
        q(context);
        return 1 == ((Integer) R.forceCast(a.get("ext"), Integer.valueOf(1))).intValue();
    }

    public static boolean h(Context context) {
        q(context);
        return 1 == ((Integer) R.forceCast(a.get("bs"), Integer.valueOf(1))).intValue();
    }

    public static int i(Context context) {
        q(context);
        return ((Integer) R.forceCast(a.get("bsgap"), Integer.valueOf(86400))).intValue();
    }

    public static boolean j(Context context) {
        q(context);
        return 1 == ((Integer) R.forceCast(a.get("l"), Integer.valueOf(0))).intValue();
    }

    public static int k(Context context) {
        q(context);
        return ((Integer) R.forceCast(a.get("lgap"), Integer.valueOf(86400))).intValue();
    }

    public static boolean l(Context context) {
        q(context);
        return 1 == ((Integer) R.forceCast(a.get("wi"), Integer.valueOf(1))).intValue();
    }

    private static synchronized void q(final Context context) {
        synchronized (a.class) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            if (a == null) {
                b(context, true, new Runnable() {
                    public void run() {
                        if (a.r(context)) {
                            a.b = elapsedRealtime;
                        }
                    }
                });
            } else if (elapsedRealtime - b >= 60000) {
                b(context, true, new Runnable() {
                    public void run() {
                        if (a.s(context)) {
                            a.b = elapsedRealtime;
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(Context context, boolean z, Runnable runnable) {
        e.a(new File(R.getCacheRoot(context), "comm/locks/.ccLock"), z, runnable);
    }

    /* access modifiers changed from: private */
    public static boolean r(Context context) {
        String v = v(context);
        if (TextUtils.isEmpty(v)) {
            a();
            return false;
        }
        b(v);
        w(context);
        return true;
    }

    /* access modifiers changed from: private */
    public static boolean s(Context context) {
        String u = u(context);
        if (TextUtils.isEmpty(u)) {
            return r(context);
        }
        b(u);
        if (((Long) R.forceCast(a.get("timestamp"), Long.valueOf(0))).longValue() - c >= 86400000) {
            x(context);
        }
        return true;
    }

    private static File t(Context context) {
        File file = new File(R.getCacheRoot(context), "comm/dbs/.ccc");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    private static String u(Context context) {
        String str;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            File t = t(context);
            if (!t.getParentFile().exists()) {
                t.getParentFile().mkdirs();
            }
            if (!t.exists()) {
                return null;
            }
            FileInputStream fileInputStream = new FileInputStream(t);
            byte[] bArr = new byte[1024];
            for (int read = fileInputStream.read(bArr); read > 0; read = fileInputStream.read(bArr)) {
                byteArrayOutputStream.write(bArr, 0, read);
            }
            byteArrayOutputStream.close();
            fileInputStream.close();
            str = new String(byteArrayOutputStream.toByteArray(), "utf-8");
            return str;
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
            str = null;
        }
    }

    /* access modifiers changed from: private */
    public static String v(Context context) {
        try {
            b a2 = b.a(context);
            ArrayList a3 = a2.a();
            if (a3.isEmpty()) {
                return null;
            }
            DeviceHelper instance = DeviceHelper.getInstance(context);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new KVPair("appkey", ((MobProduct) a3.get(0)).getProductAppkey()));
            arrayList.add(new KVPair("plat", String.valueOf(instance.getPlatformCode())));
            arrayList.add(new KVPair("apppkg", instance.getPackageName()));
            arrayList.add(new KVPair("appver", instance.getAppVersionName()));
            arrayList.add(new KVPair("networktype", instance.getDetailNetworkTypeForStatic()));
            NetworkTimeOut networkTimeOut = new NetworkTimeOut();
            networkTimeOut.readTimout = com.tencent.bugly.BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
            networkTimeOut.connectionTimeout = com.tencent.bugly.BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new KVPair("User-Identity", a2.a(a3)));
            String httpGet = a2.httpGet("http://m.data.mob.com/cconf", arrayList, arrayList2, networkTimeOut);
            Hashon hashon = new Hashon();
            HashMap fromJson = hashon.fromJson(httpGet);
            if (fromJson == null) {
                return null;
            }
            if (!"200".equals(String.valueOf(fromJson.get(NotificationCompat.CATEGORY_STATUS)))) {
                throw new Throwable("response is illegal: " + httpGet);
            }
            HashMap hashMap = (HashMap) R.forceCast(fromJson.get("switchs"));
            if (hashMap == null) {
                throw new Throwable("response is illegal: " + httpGet);
            }
            long longValue = ((Long) R.forceCast(fromJson.get("timestamp"), Long.valueOf(0))).longValue();
            hashMap.put("deviceTime", Long.valueOf(SystemClock.elapsedRealtime()));
            hashMap.put("serverTime", Long.valueOf(longValue));
            return hashon.fromHashMap(hashMap);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
            return null;
        }
    }

    private static void a() {
        a = new HashMap<>();
        a.put("in", Integer.valueOf(1));
        a.put("all", Integer.valueOf(1));
        a.put("aspa", Long.valueOf(2592000));
        a.put("un", Integer.valueOf(1));
        a.put("rt", Integer.valueOf(1));
        a.put("rtsr", Integer.valueOf(DataSource.NetDataMaxTimeMs));
        a.put("mi", Integer.valueOf(1));
        a.put("ext", Integer.valueOf(1));
        a.put("bs", Integer.valueOf(1));
        a.put("bsgap", Integer.valueOf(86400));
        a.put("di", Integer.valueOf(1));
        a.put("l", Integer.valueOf(0));
        a.put("lgap", Integer.valueOf(86400));
        a.put("wi", Integer.valueOf(1));
    }

    /* access modifiers changed from: private */
    public static void b(String str) {
        try {
            a = new Hashon().fromJson(str);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    /* access modifiers changed from: private */
    public static void w(Context context) {
        try {
            String fromHashMap = new Hashon().fromHashMap(a);
            FileOutputStream fileOutputStream = new FileOutputStream(t(context));
            fileOutputStream.write(fromHashMap.getBytes("utf-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    private static void x(final Context context) {
        if (!d) {
            d = true;
            new Thread() {
                public void run() {
                    a.b(context, false, new Runnable() {
                        public void run() {
                            String o = a.v(context);
                            if (!TextUtils.isEmpty(o)) {
                                a.b(o);
                                a.w(context);
                            }
                        }
                    });
                    a.d = false;
                }
            }.start();
        }
    }
}
