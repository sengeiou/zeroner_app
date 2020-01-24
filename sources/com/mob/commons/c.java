package com.mob.commons;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import com.mob.commons.authorize.DeviceAuthorizer;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.MobLog;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import com.mob.tools.utils.SQLiteHelper;
import com.mob.tools.utils.SQLiteHelper.SingleTableDB;
import com.tencent.bugly.BuglyStrategy.a;
import io.reactivex.annotations.SchedulerSupport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.GZIPOutputStream;

/* compiled from: DataHeap */
public class c implements Callback {
    private static c a;
    /* access modifiers changed from: private */
    public Context b;
    private Handler c;
    /* access modifiers changed from: private */
    public SingleTableDB d;
    /* access modifiers changed from: private */
    public Hashon e = new Hashon();
    private DeviceHelper f;

    public static synchronized c a(Context context) {
        c cVar;
        synchronized (c.class) {
            if (a == null) {
                a = new c(context);
            }
            cVar = a;
        }
        return cVar;
    }

    private c(Context context) {
        this.b = context.getApplicationContext();
        this.f = DeviceHelper.getInstance(context);
        MobHandlerThread mobHandlerThread = new MobHandlerThread();
        mobHandlerThread.start();
        this.c = new Handler(mobHandlerThread.getLooper(), this);
        File file = new File(R.getCacheRoot(context), "comm/dbs/.dh");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        this.d = SQLiteHelper.getDatabase(file.getAbsolutePath(), "DataHeap_1");
        this.d.addField("time", "text", true);
        this.d.addField("data", "text", true);
        this.c.sendEmptyMessage(1);
    }

    public synchronized void a(HashMap<String, Object> hashMap) {
        Message message = new Message();
        message.what = 2;
        message.obj = hashMap;
        this.c.sendMessage(message);
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1:
                a();
                this.c.sendEmptyMessageDelayed(1, 10000);
                break;
            case 2:
                b((HashMap) message.obj);
                break;
        }
        return false;
    }

    private void b(final HashMap<String, Object> hashMap) {
        e.a(new File(R.getCacheRoot(this.b), "comm/locks/.dhlock"), true, new Runnable() {
            public void run() {
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("time", String.valueOf(a.a(c.this.b)));
                    contentValues.put("data", c.this.e.fromHashMap(hashMap));
                    SQLiteHelper.insert(c.this.d, contentValues);
                } catch (Throwable th) {
                    MobLog.getInstance().w(th);
                }
            }
        });
    }

    private void a() {
        String networkType = this.f.getNetworkType();
        if (networkType != null && !SchedulerSupport.NONE.equals(networkType)) {
            e.a(new File(R.getCacheRoot(this.b), "comm/locks/.dhlock"), true, new Runnable() {
                public void run() {
                    ArrayList d = c.this.b();
                    if (d.size() > 0 && c.this.a(d)) {
                        c.this.b(d);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public ArrayList<String[]> b() {
        ArrayList<String[]> arrayList = new ArrayList<>();
        try {
            Cursor query = SQLiteHelper.query(this.d, new String[]{"time", "data"}, null, null, null);
            if (query != null) {
                if (query.moveToFirst()) {
                    arrayList.add(new String[]{query.getString(0), query.getString(1)});
                    while (query.moveToNext()) {
                        arrayList.add(new String[]{query.getString(0), query.getString(1)});
                    }
                }
                query.close();
            }
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public boolean a(ArrayList<String[]> arrayList) {
        try {
            b a2 = b.a(this.b);
            ArrayList a3 = a2.a();
            if (a3.isEmpty()) {
                return false;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("plat", Integer.valueOf(this.f.getPlatformCode()));
            hashMap.put("device", this.f.getDeviceKey());
            hashMap.put("mac", this.f.getMacAddress());
            hashMap.put("model", this.f.getModel());
            hashMap.put("duid", DeviceAuthorizer.authorize(this.b, (MobProduct) null));
            hashMap.put("imei", this.f.getIMEI());
            hashMap.put("serialno", this.f.getSerialno());
            hashMap.put("networktype", this.f.getDetailNetworkTypeForStatic());
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(this.e.fromJson(((String[]) it.next())[1]));
            }
            hashMap.put("datas", arrayList2);
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add(new KVPair("appkey", ((MobProduct) a3.get(0)).getProductAppkey()));
            arrayList3.add(new KVPair("m", a(this.e.fromHashMap(hashMap))));
            ArrayList arrayList4 = new ArrayList();
            arrayList4.add(new KVPair("User-Identity", a2.a(a3)));
            NetworkTimeOut networkTimeOut = new NetworkTimeOut();
            networkTimeOut.readTimout = a.MAX_USERDATA_VALUE_LENGTH;
            networkTimeOut.connectionTimeout = a.MAX_USERDATA_VALUE_LENGTH;
            return "200".equals(String.valueOf(this.e.fromJson(a2.httpPost("http://c.data.mob.com/cdata", arrayList3, null, arrayList4, networkTimeOut)).get(NotificationCompat.CATEGORY_STATUS)));
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
            return false;
        }
    }

    private String a(String str) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(str.getBytes("utf-8"));
        gZIPOutputStream.flush();
        gZIPOutputStream.close();
        return Base64.encodeToString(Data.AES128Encode("sdk.commonca.sdk".getBytes("UTF-8"), byteArrayOutputStream.toByteArray()), 2);
    }

    /* access modifiers changed from: private */
    public void b(ArrayList<String[]> arrayList) {
        try {
            StringBuilder sb = new StringBuilder();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String[] strArr = (String[]) it.next();
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append('\'').append(strArr[0]).append('\'');
            }
            SQLiteHelper.delete(this.d, "time in (" + sb.toString() + ")", null);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }
}
