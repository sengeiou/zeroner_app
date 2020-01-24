package cn.smssdk;

import android.content.Context;
import android.os.Handler.Callback;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import cn.smssdk.SMSSDK.VerifyCodeReadListener;
import cn.smssdk.contact.OnContactChangeListener;
import cn.smssdk.contact.d;
import cn.smssdk.net.j;
import cn.smssdk.utils.SMSLog;
import cn.smssdk.utils.SPHelper;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class a implements OnContactChangeListener {
    private Context a;
    /* access modifiers changed from: private */
    public HashSet<EventHandler> b = new HashSet<>();
    /* access modifiers changed from: private */
    public j c;
    private d d;
    /* access modifiers changed from: private */
    public cn.smssdk.a.a e;
    private cn.smssdk.b.a f;
    private String g;
    private HashMap<Character, ArrayList<String[]>> h;
    private HashMap<String, String> i;
    private ArrayList<HashMap<String, Object>> j;
    private String k;

    public a(Context context, String str, String str2) {
        this.a = context.getApplicationContext();
        this.k = str;
        SMSLog.prepare(this.a, 25, str);
        this.c = j.a(this.a);
        this.c.a(str, str2);
        this.e = cn.smssdk.a.a.a(this.a);
        this.d = d.a(this.a);
        this.f = cn.smssdk.b.a.a(this.a, str);
    }

    public void a() {
        this.d.a((OnContactChangeListener) this);
        this.d.a();
        new Thread(new b(this));
    }

    public void a(EventHandler eventHandler) {
        synchronized (this.b) {
            if (eventHandler != null) {
                if (!this.b.contains(eventHandler)) {
                    this.b.add(eventHandler);
                    eventHandler.onRegister();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(cn.smssdk.EventHandler r3) {
        /*
            r2 = this;
            java.util.HashSet<cn.smssdk.EventHandler> r1 = r2.b
            monitor-enter(r1)
            if (r3 == 0) goto L_0x000d
            java.util.HashSet<cn.smssdk.EventHandler> r0 = r2.b     // Catch:{ all -> 0x0019 }
            boolean r0 = r0.contains(r3)     // Catch:{ all -> 0x0019 }
            if (r0 != 0) goto L_0x000f
        L_0x000d:
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
        L_0x000e:
            return
        L_0x000f:
            r3.onUnregister()     // Catch:{ all -> 0x0019 }
            java.util.HashSet<cn.smssdk.EventHandler> r0 = r2.b     // Catch:{ all -> 0x0019 }
            r0.remove(r3)     // Catch:{ all -> 0x0019 }
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            goto L_0x000e
        L_0x0019:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.smssdk.a.b(cn.smssdk.EventHandler):void");
    }

    public void b() {
        synchronized (this.b) {
            Iterator it = this.b.iterator();
            while (it.hasNext()) {
                ((EventHandler) it.next()).onUnregister();
            }
            this.b.clear();
        }
    }

    public void a(int i2, Object obj) {
        new c(this, i2, obj).start();
    }

    /* access modifiers changed from: private */
    public void b(int i2, Object obj) {
        switch (i2) {
            case 1:
                e();
                return;
            case 2:
                b(obj);
                return;
            case 3:
                c(obj);
                return;
            case 4:
                a(obj);
                return;
            case 5:
                d(obj);
                return;
            case 6:
                g();
                return;
            case 7:
                f();
                return;
            case 8:
                e(obj);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2, int i3, Object obj) {
        if (obj != null && (obj instanceof Throwable)) {
            Throwable th = (Throwable) obj;
            String message = th.getMessage();
            if (!TextUtils.isEmpty(message)) {
                try {
                    JSONObject jSONObject = new JSONObject(message);
                    int optInt = jSONObject.optInt(NotificationCompat.CATEGORY_STATUS);
                    if (TextUtils.isEmpty(jSONObject.optString("detail")) && ((optInt > 400 && optInt <= 500) || optInt > 600)) {
                        int stringRes = R.getStringRes(this.a, "smssdk_error_desc_" + optInt);
                        if (stringRes > 0) {
                            String string = this.a.getResources().getString(stringRes);
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put(NotificationCompat.CATEGORY_STATUS, optInt);
                            jSONObject2.put("detail", string);
                            obj = new Throwable(jSONObject2.toString(), th);
                        }
                    }
                } catch (Throwable th2) {
                    SMSLog.getInstance().w(th2);
                }
            }
            SMSLog.getInstance().w(th);
        }
        synchronized (this.b) {
            Iterator it = this.b.iterator();
            while (it.hasNext()) {
                ((EventHandler) it.next()).afterEvent(i2, i3, obj);
            }
        }
    }

    private void a(Object obj) {
        int i2 = 0;
        try {
            th = this.d.a(((Boolean) obj).booleanValue());
            i2 = -1;
        } catch (Throwable th) {
            th = th;
        }
        a(4, i2, th);
    }

    private void b(Object obj) {
        Object obj2;
        int i2;
        String str;
        try {
            if (this.i == null || this.i.size() <= 0) {
                h();
            }
            Object[] objArr = (Object[]) obj;
            String str2 = (String) objArr[0];
            String str3 = (String) objArr[1];
            String str4 = (String) objArr[2];
            String str5 = (String) objArr[3];
            if (str2.startsWith("+")) {
                str = str2.substring(1);
            } else {
                str = str2;
            }
            if (!a(str3, str)) {
                SMSLog.getInstance().d("phone num error", new Object[0]);
            }
            OnSendMessageHandler onSendMessageHandler = (OnSendMessageHandler) objArr[4];
            if (onSendMessageHandler == null || !onSendMessageHandler.onSendMessage(str, str3)) {
                obj2 = Boolean.valueOf(this.c.a(str, str3, str4, str5));
                i2 = -1;
                a(2, i2, obj2);
                return;
            }
            throw new UserInterruptException();
        } catch (Throwable th) {
            obj2 = th;
            i2 = 0;
        }
    }

    private void c(Object obj) {
        int i2 = 0;
        try {
            if (this.i == null || this.i.size() <= 0) {
                h();
            }
            String[] strArr = (String[]) obj;
            String str = strArr[0];
            String str2 = strArr[1];
            String str3 = strArr[2];
            if (str.startsWith("+")) {
                str = str.substring(1);
            }
            if (!a(str2, str)) {
                throw new Throwable("phone num error");
            }
            th = this.c.b(str3, str, str2);
            i2 = -1;
            a(3, i2, th);
        } catch (Throwable th) {
            th = th;
        }
    }

    private void e() {
        int i2 = 0;
        try {
            th = h();
            i2 = -1;
        } catch (Throwable th) {
            th = th;
        }
        a(1, i2, th);
    }

    private void d(Object obj) {
        Throwable th;
        int i2;
        try {
            String[] strArr = (String[]) obj;
            this.e.a(strArr[0], strArr[1], strArr[2], strArr[3], strArr[4]);
            i2 = -1;
            th = null;
        } catch (Throwable th2) {
            th = th2;
            i2 = 0;
        }
        a(5, i2, (Object) th);
    }

    private void f() {
        this.e.a((Callback) new d(this));
    }

    private void g() {
        this.e.b((Callback) new e(this));
    }

    public void onContactChange(boolean z) {
        this.d.b(new f(this), null);
    }

    public HashMap<Character, ArrayList<String[]>> c() {
        ArrayList arrayList;
        String appLanguage = DeviceHelper.getInstance(this.a).getAppLanguage();
        SMSLog.getInstance().d("appLanguage:" + appLanguage, new Object[0]);
        if (appLanguage != null && !appLanguage.equals(this.g)) {
            this.g = appLanguage;
            this.h = null;
        }
        if (this.h != null && this.h.size() > 0) {
            return this.h;
        }
        LinkedHashMap linkedHashMap = null;
        for (char c2 = 'A'; c2 <= 'Z'; c2 = (char) (c2 + 1)) {
            int stringArrayRes = R.getStringArrayRes(this.a, "smssdk_country_group_" + Character.toLowerCase(c2));
            if (stringArrayRes > 0) {
                String[] stringArray = this.a.getResources().getStringArray(stringArrayRes);
                if (stringArray != null) {
                    arrayList = null;
                    for (String split : stringArray) {
                        String[] split2 = split.split(",");
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(split2);
                    }
                } else {
                    arrayList = null;
                }
                if (arrayList != null) {
                    if (linkedHashMap == null) {
                        linkedHashMap = new LinkedHashMap();
                    }
                    linkedHashMap.put(Character.valueOf(c2), arrayList);
                }
            }
        }
        this.h = linkedHashMap;
        return this.h;
    }

    public String[] a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        for (Entry value : c().entrySet()) {
            ArrayList arrayList = (ArrayList) value.getValue();
            int size = arrayList == null ? 0 : arrayList.size();
            int i2 = 0;
            while (true) {
                if (i2 < size) {
                    String[] strArr = (String[]) arrayList.get(i2);
                    if (strArr != null && strArr.length > 2 && str.equals(strArr[2])) {
                        return strArr;
                    }
                    i2++;
                }
            }
        }
        return null;
    }

    public String[] b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        for (Entry value : c().entrySet()) {
            ArrayList arrayList = (ArrayList) value.getValue();
            int size = arrayList == null ? 0 : arrayList.size();
            int i2 = 0;
            while (true) {
                if (i2 < size) {
                    String[] strArr = (String[]) arrayList.get(i2);
                    if (strArr.length < 4) {
                        SMSLog.getInstance().d("MCC not found in the country: " + strArr[0], new Object[0]);
                    } else {
                        String str2 = strArr[3];
                        if (str2.indexOf("|") >= 0) {
                            for (String equals : str2.split("\\|")) {
                                if (str.equals(equals)) {
                                    return strArr;
                                }
                            }
                        } else if (str.equals(str2)) {
                            return strArr;
                        }
                    }
                    i2++;
                }
            }
        }
        return null;
    }

    public void a(SmsMessage smsMessage, VerifyCodeReadListener verifyCodeReadListener) {
        this.f.a(verifyCodeReadListener);
        this.f.a(smsMessage);
    }

    private void e(Object obj) {
        Throwable th;
        int i2;
        Object[] objArr = (Object[]) obj;
        String str = (String) objArr[0];
        String str2 = (String) objArr[1];
        String str3 = (String) objArr[2];
        if (str2.startsWith("+")) {
            str2 = str2.substring(1);
        }
        try {
            if (this.i == null || this.i.size() <= 0) {
                h();
            }
            if (!a(str, str2)) {
                SMSLog.getInstance().d("phone num error", new Object[0]);
            }
            this.c.a(str, str2, str3);
            i2 = -1;
            th = null;
        } catch (Throwable th2) {
            th = th2;
            i2 = 0;
        }
        a(8, i2, (Object) th);
    }

    public void d() {
        SPHelper.getInstance(this.a).setWarnWhenReadContact(true);
    }

    private boolean a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            int stringRes = R.getStringRes(this.a, "smssdk_error_desc_603");
            if (stringRes <= 0) {
                return false;
            }
            throw new Throwable("{\"status\":603,\"detail\":\"" + this.a.getResources().getString(stringRes) + "\"}");
        } else if (this.i == null || this.i.size() <= 0) {
            if (str2 != "86") {
                int stringRes2 = R.getStringRes(this.a, "smssdk_error_desc_604");
                if (stringRes2 > 0) {
                    throw new Throwable("{\"status\":604,\"detail\":\"" + this.a.getResources().getString(stringRes2) + "\"}");
                }
            } else if (str.length() != 11) {
                int stringRes3 = R.getStringRes(this.a, "smssdk_error_desc_603");
                if (stringRes3 > 0) {
                    throw new Throwable("{\"status\":603,\"detail\":\"" + this.a.getResources().getString(stringRes3) + "\"}");
                }
            }
            return false;
        } else {
            String str3 = (String) this.i.get(str2);
            if (TextUtils.isEmpty(str3)) {
                int stringRes4 = R.getStringRes(this.a, "smssdk_error_desc_604");
                if (stringRes4 > 0) {
                    throw new Throwable("{\"status\":604,\"detail\":\"" + this.a.getResources().getString(stringRes4) + "\"}");
                }
            }
            if (Pattern.compile(str3).matcher(str).matches()) {
                return true;
            }
            int stringRes5 = R.getStringRes(this.a, "smssdk_error_desc_603");
            if (stringRes5 <= 0) {
                return false;
            }
            throw new Throwable("{\"status\":603,\"detail\":\"" + this.a.getResources().getString(stringRes5) + "\"}");
        }
    }

    private ArrayList<HashMap<String, Object>> h() {
        if (this.j == null || this.c.a()) {
            this.j = this.c.b();
        }
        Iterator it = this.j.iterator();
        while (it.hasNext()) {
            HashMap hashMap = (HashMap) it.next();
            String str = (String) hashMap.get("zone");
            String str2 = (String) hashMap.get("rule");
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                if (this.i == null) {
                    this.i = new HashMap<>();
                }
                this.i.put(str, str2);
            }
        }
        return this.j;
    }
}
