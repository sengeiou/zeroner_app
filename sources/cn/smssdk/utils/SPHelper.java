package cn.smssdk.utils;

import android.content.Context;
import android.text.TextUtils;
import cn.smssdk.net.g;
import com.mob.tools.utils.SharePrefrenceHelper;
import java.util.ArrayList;
import java.util.HashMap;

public class SPHelper {
    private static SPHelper a;
    private SharePrefrenceHelper b;
    private SharePrefrenceHelper c;

    public static SPHelper getInstance(Context context) {
        if (a == null) {
            a = new SPHelper(context);
        }
        return a;
    }

    private SPHelper(Context context) {
        this.b = new SharePrefrenceHelper(context);
        this.b.open("SMSSDK", 2);
        this.c = new SharePrefrenceHelper(context);
        this.c.open("SMSSDK_VCODE", 1);
    }

    public String getConfig() {
        String d = g.d(this.b.getString("config"));
        if (d != null) {
            return d;
        }
        return null;
    }

    public void setConfig(String str) {
        this.b.putString("config", g.c(str));
    }

    public String getAeskey() {
        String d = g.d(this.b.getString("aeskey"));
        if (d != null) {
            return d;
        }
        return null;
    }

    public void setAeskey(String str) {
        this.b.putString("aeskey", g.c(str));
    }

    public String getLimit(String str) {
        return this.b.getString(str);
    }

    public void setLimit(String str, String str2) {
        this.b.putString(str, str2);
    }

    public boolean isAllowReadContact() {
        return this.b.getBoolean("read_contact");
    }

    public void setAllowReadContact() {
        this.b.putBoolean("read_contact", Boolean.valueOf(true));
    }

    public boolean isWarnWhenReadContact() {
        return this.b.getBoolean("read_contact_warn");
    }

    public void setWarnWhenReadContact(boolean z) {
        this.b.putBoolean("read_contact_warn", Boolean.valueOf(z));
    }

    public String getVerifyCountry() {
        return (String) g.a(getAppKey(), this.b.getString("verify_country"));
    }

    public void setVerifyCountry(String str) {
        this.b.putString("verify_country", g.a(getAppKey(), (Object) str));
    }

    public String getVerifyPhone() {
        return (String) g.a(getAppKey(), this.b.getString("verify_phone"));
    }

    public void setVerifyPhone(String str) {
        this.b.putString("verify_phone", g.a(getAppKey(), (Object) str));
    }

    public void clearBuffer() {
        this.b.remove("bufferedNewFriends");
        this.b.remove("bufferedFriends");
        this.b.remove("lastRequestNewFriendsTime");
        this.b.remove("bufferedContactPhones");
    }

    public String getBufferedCountrylist() {
        return this.b.getString("bufferedCountryList");
    }

    public void setBufferedCountrylist(String str) {
        this.b.putString("bufferedCountryList", str);
    }

    public long getLastCountryListTime() {
        return this.b.getLong("lastCountryListTime");
    }

    public void setLastCountryListTime() {
        this.b.putLong("lastCountryListTime", Long.valueOf(System.currentTimeMillis()));
    }

    public String getBufferedContactsSignature() {
        return this.b.getString("bufferedContactsSignature");
    }

    public void setBufferedContactsSignature(String str) {
        this.b.putString("bufferedContactsSignature", str);
    }

    public ArrayList<HashMap<String, Object>> getBufferedContacts() {
        Object obj = this.b.get("bufferedContacts");
        if (obj != null) {
            return (ArrayList) obj;
        }
        return new ArrayList<>();
    }

    public void setBufferedContacts(ArrayList<HashMap<String, Object>> arrayList) {
        this.b.put("bufferedContacts", arrayList);
    }

    public ArrayList<HashMap<String, Object>> getBufferedFriends() {
        ArrayList<HashMap<String, Object>> arrayList;
        synchronized ("bufferedFriends") {
            Object obj = this.b.get("bufferedFriends");
            if (obj != null) {
                arrayList = (ArrayList) obj;
            } else {
                arrayList = new ArrayList<>();
            }
        }
        return arrayList;
    }

    public void setBufferedFriends(ArrayList<HashMap<String, Object>> arrayList) {
        synchronized ("bufferedFriends") {
            this.b.put("bufferedFriends", arrayList);
        }
    }

    public ArrayList<HashMap<String, Object>> getBufferedNewFriends() {
        Object obj = this.b.get("bufferedNewFriends");
        if (obj != null) {
            return (ArrayList) obj;
        }
        return new ArrayList<>();
    }

    public void setBufferedNewFriends(ArrayList<HashMap<String, Object>> arrayList) {
        this.b.put("bufferedNewFriends", arrayList);
    }

    public long getLastRequestNewFriendsTime() {
        return this.b.getLong("lastRequestNewFriendsTime");
    }

    public void setRequestNewFriendsTime() {
        this.b.putLong("lastRequestNewFriendsTime", Long.valueOf(System.currentTimeMillis()));
    }

    public void setBufferedContactPhones(String[] strArr) {
        this.b.put("bufferedContactPhones", strArr);
    }

    public String[] getBufferedContactPhones() {
        Object obj = this.b.get("bufferedContactPhones");
        if (obj != null) {
            return (String[]) obj;
        }
        return new String[0];
    }

    public String getVCodeHash() {
        return this.c.getString("KEY_VCODE_HASH");
    }

    public void setVCodeHash(String str) {
        this.c.putString("KEY_VCODE_HASH", str);
    }

    public String getSMSID() {
        return this.c.getString("KEY_SMSID");
    }

    public void setSMSID(String str) {
        this.c.putString("KEY_SMSID", str);
    }

    public String getAppKey() {
        return this.c.getString("KEY_APPKEY");
    }

    public void setAppKey(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        this.c.putString("KEY_APPKEY", str);
    }

    public void setLog(String str) {
        synchronized ("KEY_LOG") {
            String log = getLog();
            if (!TextUtils.isEmpty(log)) {
                str = log + "\r\n" + str;
            }
            this.c.putString("KEY_LOG", str);
        }
    }

    public String getLog() {
        return this.c.getString("KEY_LOG");
    }

    public void clearLog() {
        synchronized ("KEY_LOG") {
            this.c.remove("KEY_LOG");
        }
    }

    public String getToken() {
        return this.b.getString("token");
    }

    public void setToken(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.b.putString("token", str);
        }
    }
}
