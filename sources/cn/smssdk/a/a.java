package cn.smssdk.a;

import android.content.Context;
import android.os.Handler.Callback;
import android.os.Message;
import cn.smssdk.contact.d;
import cn.smssdk.net.j;
import cn.smssdk.utils.SMSLog;
import cn.smssdk.utils.SPHelper;
import com.iwown.data_link.consts.UserConst;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public final class a {
    private static a a;
    /* access modifiers changed from: private */
    public j b;
    /* access modifiers changed from: private */
    public SPHelper c;
    private h d;
    /* access modifiers changed from: private */
    public d e;

    public static a a(Context context) {
        if (a == null) {
            a = new a(context);
        }
        return a;
    }

    private a(Context context) {
        this.b = j.a(context);
        this.c = SPHelper.getInstance(context);
        this.d = new h(context, this);
        this.e = d.a(context);
    }

    public void a(String str, String str2, String str3, String str4, String str5) {
        this.b.a(str, str2, str3, str4, str5);
    }

    public void a(Callback callback) {
        if (a()) {
            c((Callback) new b(this, callback));
            return;
        }
        Message message = new Message();
        message.what = 1;
        try {
            message.obj = Integer.valueOf(this.c.getBufferedNewFriends().size());
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
            message.obj = Integer.valueOf(0);
        }
        callback.handleMessage(message);
    }

    private boolean a() {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        int i = instance.get(1);
        int i2 = instance.get(2);
        int i3 = instance.get(5);
        instance.setTimeInMillis(this.c.getLastRequestNewFriendsTime());
        int i4 = instance.get(1);
        int i5 = instance.get(2);
        int i6 = instance.get(5);
        if (i == i4 && i2 == i5 && i3 == i6) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public ArrayList<HashMap<String, Object>> a(ArrayList<HashMap<String, Object>> arrayList) {
        ArrayList arrayList2;
        ArrayList arrayList3;
        boolean z;
        try {
            arrayList2 = this.c.getBufferedFriends();
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
            arrayList2 = new ArrayList();
        }
        HashMap hashMap = new HashMap();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            HashMap hashMap2 = (HashMap) it.next();
            Object obj = hashMap2.get(UserConst.PHONE);
            if (obj != null) {
                Iterator it2 = arrayList2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (obj.equals(((HashMap) it2.next()).get(UserConst.PHONE))) {
                            z = false;
                            break;
                        }
                    } else {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    hashMap.put(obj, hashMap2);
                }
            }
        }
        try {
            arrayList3 = this.c.getBufferedNewFriends();
        } catch (Throwable th2) {
            SMSLog.getInstance().w(th2);
            arrayList3 = new ArrayList();
        }
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            HashMap hashMap3 = (HashMap) it3.next();
            Object obj2 = hashMap3.get(UserConst.PHONE);
            if (obj2 != null) {
                hashMap.put(obj2, hashMap3);
            }
        }
        ArrayList<HashMap<String, Object>> arrayList4 = new ArrayList<>();
        for (Entry value : hashMap.entrySet()) {
            arrayList4.add(value.getValue());
        }
        return arrayList4;
    }

    public void b(Callback callback) {
        c((Callback) new c(this, callback));
    }

    public void a(int i, Callback callback) {
        this.d.a(i, callback);
    }

    private void c(Callback callback) {
        String[] strArr;
        Message message = new Message();
        try {
            strArr = this.c.getBufferedContactPhones();
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
            strArr = null;
        }
        if (strArr == null || strArr.length <= 0) {
            this.e.a((Runnable) new d(this, message, callback), (Runnable) new f(this, callback, message));
            return;
        }
        message.obj = strArr;
        callback.handleMessage(message);
    }
}
