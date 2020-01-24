package cn.smssdk.net;

import android.content.Context;
import android.text.TextUtils;
import cn.smssdk.utils.SMSLog;
import cn.smssdk.utils.SPHelper;
import com.iwown.data_link.consts.UserConst;
import com.mob.commons.SMSSDK;
import com.mob.commons.eventrecoder.EventRecorder;
import com.mob.tools.utils.Hashon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class j {
    private static j a;
    private Context b;
    private Hashon c = new Hashon();
    private String d;
    private SPHelper e;
    private b f;

    public static j a(Context context) {
        if (a == null) {
            a = new j(context);
        }
        return a;
    }

    private j(Context context) {
        this.b = context.getApplicationContext();
        this.e = SPHelper.getInstance(context);
        this.f = b.a(context);
        EventRecorder.prepare(context);
    }

    public void a(String str, String str2) {
        this.d = str;
        SMSSDK.setAppKey(str);
        this.e.setAppKey(str);
        a(str2);
        i.a(this.b, str);
        this.f.a(str, str2);
    }

    public boolean a() {
        return this.f.a();
    }

    private void a(String str) {
        new HashMap().put("hashon", this.c);
    }

    public void a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2)) {
            throw new Throwable("{\"detail\":\"country code cant be empty\"}");
        } else if (TextUtils.isEmpty(str)) {
            throw new Throwable("{\"detail\":\"phone number cant be empty\"}");
        } else if (!this.f.a(str2)) {
            throw new Throwable("{\"status\":461}");
        } else {
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            if (!TextUtils.isEmpty(str3)) {
                hashMap2.put("extKey", str3);
            }
            hashMap.put(UserConst.PHONE, str);
            hashMap.put("zone", str2);
            hashMap.put("attr", hashMap2);
            hashMap.put("tempCode", "Nul2");
            this.f.a(10, hashMap);
        }
    }

    public void a(String str, String str2, ArrayList<HashMap<String, Object>> arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            HashMap hashMap = new HashMap();
            hashMap.put("zone", str);
            hashMap.put(UserConst.PHONE, str2);
            hashMap.put("contacts", arrayList);
            this.f.a(5, hashMap);
        }
    }

    public boolean a(String str, String str2, String str3, String str4) {
        if (TextUtils.isEmpty(str)) {
            throw new Throwable("{\"detail\":\"country code cant be empty\"}");
        } else if (TextUtils.isEmpty(str2)) {
            throw new Throwable("{\"detail\":\"phone number cant be empty\"}");
        } else if (!this.f.a(str)) {
            throw new Throwable("{\"status\":461}");
        } else {
            HashMap hashMap = new HashMap();
            hashMap.put(UserConst.PHONE, str2);
            hashMap.put("zone", str);
            if (!TextUtils.isEmpty(str3)) {
                HashMap hashMap2 = new HashMap();
                hashMap.put("attr", hashMap2);
                hashMap2.put("extKey", str3);
            }
            if (!TextUtils.isEmpty(str4)) {
                hashMap.put("tempCode", str4);
            }
            HashMap a2 = this.f.a(9, hashMap);
            String str5 = (String) a2.get("vCode");
            String str6 = (String) a2.get("smsId");
            Integer num = (Integer) a2.get("smart");
            this.e.setSMSID(str6);
            this.e.setVCodeHash(str5);
            if (num == null || num.intValue() != 1) {
                return false;
            }
            this.e.clearBuffer();
            try {
                this.e.setVerifyCountry(str);
                this.e.setVerifyPhone(str2);
            } catch (Throwable th) {
                SMSLog.getInstance().w(th);
            }
            return true;
        }
    }

    public HashMap<String, Object> b(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            throw new Throwable("{\"status\":\"466\"}");
        } else if (TextUtils.isEmpty(str2)) {
            throw new Throwable("{\"detail\":\"country code cant be empty\"}");
        } else if (TextUtils.isEmpty(str3)) {
            throw new Throwable("{\"detail\":\"phone number cant be empty\"}");
        } else {
            HashMap hashMap = new HashMap();
            hashMap.put(UserConst.PHONE, str3);
            hashMap.put("code", str);
            hashMap.put("zone", str2);
            this.f.a(11, hashMap);
            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap2.put("country", str2);
            hashMap2.put(UserConst.PHONE, str3);
            this.e.clearBuffer();
            try {
                this.e.setVerifyCountry(str2);
                this.e.setVerifyPhone(str3);
            } catch (Throwable th) {
                SMSLog.getInstance().w(th);
            }
            return hashMap2;
        }
    }

    public ArrayList<HashMap<String, Object>> b() {
        HashMap a2;
        long lastCountryListTime = this.e.getLastCountryListTime();
        String bufferedCountrylist = this.e.getBufferedCountrylist();
        if (System.currentTimeMillis() - lastCountryListTime > 86400000 || TextUtils.isEmpty(bufferedCountrylist) || a()) {
            a2 = this.f.a(2, null);
            this.e.setBufferedCountrylist(this.c.fromHashMap(a2));
            this.e.setLastCountryListTime();
            this.f.b();
        } else {
            a2 = this.c.fromJson(bufferedCountrylist);
        }
        return (ArrayList) ((HashMap) a2.get("result")).get("list");
    }

    public void a(String str, String str2, String str3, String str4, String str5) {
        HashMap hashMap = new HashMap();
        hashMap.put("avatar", str3);
        hashMap.put(UserConst.UID, str);
        hashMap.put("nickname", str2);
        hashMap.put(UserConst.PHONE, str5);
        hashMap.put("zone", str4);
        this.f.a(4, hashMap);
    }

    public ArrayList<HashMap<String, Object>> a(String[] strArr) {
        ArrayList arrayList;
        ArrayList arrayList2;
        HashMap hashMap = new HashMap();
        hashMap.put("contactphones", strArr);
        HashMap hashMap2 = (HashMap) this.f.a(6, hashMap).get("result");
        if (hashMap2 != null) {
            arrayList = (ArrayList) hashMap2.get("list");
        } else {
            arrayList = null;
        }
        if (arrayList == null) {
            arrayList2 = new ArrayList();
        } else {
            arrayList2 = arrayList;
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            HashMap hashMap3 = (HashMap) it.next();
            Object obj = hashMap3.get(UserConst.PHONE);
            if (obj != null) {
                hashMap3.put(UserConst.PHONE, this.f.c(String.valueOf(obj).toLowerCase(Locale.ENGLISH)));
            }
        }
        return arrayList2;
    }

    public void c() {
        this.f.c();
    }
}
