package cn.smssdk.net;

import android.content.Context;
import android.text.TextUtils;
import cn.smssdk.SMSSDK;
import com.google.common.net.HttpHeaders;
import com.iwown.data_link.consts.UserConst;
import com.mob.tools.network.KVPair;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class i {
    private static String a;
    private static boolean b;
    private static DeviceHelper c;
    private static HashMap<String, Object> d;
    private static b e;
    private static boolean h;
    private static boolean i;
    private static i j;
    private Hashon f = new Hashon();
    private HashMap<String, String> g;

    public static i a() {
        if (j == null) {
            synchronized (i.class) {
                j = new i();
            }
        }
        return j;
    }

    private i() {
    }

    public static void a(Context context, String str) {
        String str2;
        String str3 = null;
        a = str;
        c = DeviceHelper.getInstance(context);
        e = b.a(context);
        d = new HashMap<>();
        d.put("plat", Integer.valueOf(c.getPlatformCode()));
        d.put("sdkver", "2.1.2");
        try {
            h = c.checkPermission("android.permission.READ_PHONE_STATE");
            i = c.checkPermission("android.permission.READ_SMS");
            if (h) {
                str2 = c.getSimSerialNumber();
            } else {
                str2 = null;
            }
            try {
                if (h && i) {
                    str3 = c.getLine1Number();
                }
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
            str2 = null;
        }
        String carrier = c.getCarrier();
        if (carrier != null && !carrier.equals("-1")) {
            d.put("operator", carrier);
        }
        if (str2 != null && !str2.equals("-1")) {
            d.put("simserial", str2);
        }
        if (str3 != null && !str3.equals("-1")) {
            d.put("my_phone", str3);
        }
        b = true;
    }

    public HashMap<String, Object> a(String str, String str2, String str3, HashMap<String, Object> hashMap) {
        if (!b) {
            throw new Throwable("ParamsBuilder need prepare before use");
        } else if (str.equals("user_info_001")) {
            return a(str2, hashMap);
        } else {
            if (str.equals("device_001")) {
                return c();
            }
            if (str.equals("install_002")) {
                return b(str2, hashMap);
            }
            if (str.equals("collect_001")) {
                return a(hashMap);
            }
            if (str.equals("contacts_001")) {
                return c(str2, hashMap);
            }
            if (str.equals("contacts_002")) {
                return d(str2, hashMap);
            }
            return null;
        }
    }

    public HashMap<String, Object> a(ArrayList<String> arrayList, String str, String str2, HashMap<String, Object> hashMap) {
        if (!b) {
            throw new Throwable("ParamsBuilder need prepare before use");
        }
        HashMap<String, Object> hashMap2 = new HashMap<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str3 = (String) it.next();
            if (str3.equals("appkey")) {
                hashMap2.put("appkey", a);
            } else if (str3.equals("token")) {
                hashMap2.put("token", str2);
            } else if (str3.equals("duid")) {
                hashMap2.put("duid", str);
            } else if (str3.equals("contactphones")) {
                hashMap2.put("contactphones", a((String[]) hashMap.get("contactphones")));
            } else {
                Object obj = d.get(str3);
                if (obj == null) {
                    obj = hashMap.get(str3);
                }
                hashMap2.put(str3, obj);
            }
        }
        return hashMap2;
    }

    public ArrayList<KVPair<String>> a(byte[] bArr, String str) {
        ArrayList<KVPair<String>> arrayList = new ArrayList<>();
        arrayList.add(new KVPair("appkey", a));
        String str2 = "token";
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        arrayList.add(new KVPair(str2, str));
        arrayList.add(new KVPair("hash", Data.CRC32(bArr)));
        arrayList.add(new KVPair(HttpHeaders.USER_AGENT, b()));
        return arrayList;
    }

    private String b() {
        StringBuilder sb = new StringBuilder();
        sb.append("SMSSDK/").append("2.1.2").append(' ');
        sb.append("(Android; ").append(Data.urlEncode(c.getOSVersionName())).append('/').append(c.getOSVersionInt()).append(") ");
        sb.append(Data.urlEncode(c.getManufacturer())).append('/').append(Data.urlEncode(c.getModel())).append(' ');
        sb.append(Data.urlEncode(c.getAppName())).append('/').append(c.getPackageName()).append('/').append(Data.urlEncode(c.getAppVersionName()));
        return sb.toString();
    }

    private HashMap<String, Object> a(String str, HashMap<String, Object> hashMap) {
        String str2 = (String) hashMap.get(UserConst.UID);
        String str3 = (String) hashMap.get("nickname");
        String str4 = (String) hashMap.get("avatar");
        String str5 = (String) hashMap.get("zone");
        String str6 = (String) hashMap.get(UserConst.PHONE);
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("avatar", str4);
        hashMap2.put(UserConst.UID, str2);
        hashMap2.put("nickname", str3);
        hashMap2.put("appkey", a);
        hashMap2.put(UserConst.PHONE, str6);
        hashMap2.put("zone", str5);
        hashMap2.put("duid", str);
        return hashMap2;
    }

    private HashMap<String, Object> c() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("appkey", a);
        hashMap.put("apppkg", c.getPackageName());
        hashMap.put("appver", c.getAppVersionName());
        hashMap.put("plat", Integer.valueOf(c.getPlatformCode()));
        hashMap.put("network", c.getNetworkTypeForStatic());
        hashMap.put("deviceinfo", d());
        return hashMap;
    }

    private HashMap<String, Object> b(String str, HashMap<String, Object> hashMap) {
        String str2 = (String) hashMap.get("type");
        Object obj = hashMap.get("list");
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("type", str2);
        hashMap2.put("plat", Integer.valueOf(c.getPlatformCode()));
        hashMap2.put("device", str);
        hashMap2.put("list", obj);
        return hashMap2;
    }

    private HashMap<String, Object> a(HashMap<String, Object> hashMap) {
        Object obj = hashMap.get("logs");
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("logs", obj);
        hashMap2.put("deviceinfo", d());
        return hashMap2;
    }

    private HashMap<String, Object> c(String str, HashMap<String, Object> hashMap) {
        Object obj;
        if (h) {
            obj = c.getSimSerialNumber();
        } else {
            obj = null;
        }
        ArrayList arrayList = (ArrayList) hashMap.get("contacts");
        a(arrayList);
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("appkey", a);
        hashMap2.put("duid", str);
        hashMap2.put("simserial", obj);
        hashMap2.put("contacts", b(arrayList));
        return hashMap2;
    }

    private HashMap<String, Object> d(String str, HashMap<String, Object> hashMap) {
        Object obj;
        String str2;
        String str3 = null;
        if (h) {
            obj = c.getSimSerialNumber();
        } else {
            obj = null;
        }
        String str4 = (String) hashMap.get("zone");
        String[] countryByMCC = SMSSDK.getCountryByMCC(c.getMCC());
        if (countryByMCC != null) {
            str2 = countryByMCC[1];
        } else {
            str2 = str4;
        }
        if (h && i) {
            str3 = c.getLine1Number();
        }
        ArrayList arrayList = (ArrayList) hashMap.get("contacts");
        a(arrayList);
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("appkey", a);
        hashMap2.put("duid", str);
        hashMap2.put("my_phone", str3);
        hashMap2.put("zone", str2);
        hashMap2.put("simserial", obj);
        String carrier = c.getCarrier();
        if (carrier != null && !carrier.equals("-1")) {
            hashMap2.put("operator", carrier);
        }
        hashMap2.put("contacts", b(arrayList));
        return hashMap2;
    }

    private HashMap<String, Object> d() {
        HashMap<String, Object> hashMap = new HashMap<>();
        String imei = c.getIMEI();
        hashMap.put("adsid", c.getAdvertisingID());
        String str = "imei";
        if (imei == null) {
            imei = "";
        }
        hashMap.put(str, imei);
        String serialno = c.getSerialno();
        String str2 = "serialno";
        if (serialno == null) {
            serialno = "";
        }
        hashMap.put(str2, serialno);
        String macAddress = c.getMacAddress();
        String str3 = "mac";
        if (macAddress == null) {
            macAddress = "";
        }
        hashMap.put(str3, macAddress);
        String model = c.getModel();
        String str4 = "model";
        if (model == null) {
            model = "";
        }
        hashMap.put(str4, model);
        String manufacturer = c.getManufacturer();
        String str5 = "factory";
        if (manufacturer == null) {
            manufacturer = "";
        }
        hashMap.put(str5, manufacturer);
        String carrier = c.getCarrier();
        String str6 = "carrier";
        if (carrier == null) {
            carrier = "";
        }
        hashMap.put(str6, carrier);
        String screenSize = c.getScreenSize();
        String str7 = "screensize";
        if (screenSize == null) {
            screenSize = "";
        }
        hashMap.put(str7, screenSize);
        String oSVersionName = c.getOSVersionName();
        String str8 = "sysver";
        if (oSVersionName == null) {
            oSVersionName = "";
        }
        hashMap.put(str8, oSVersionName);
        hashMap.put("androidid", c.getAndroidID());
        return hashMap;
    }

    private void a(ArrayList<HashMap<String, Object>> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                HashMap hashMap = (HashMap) it.next();
                if (hashMap != null) {
                    Object obj = hashMap.get("phones");
                    if (obj != null) {
                        Iterator it2 = ((ArrayList) obj).iterator();
                        while (it2.hasNext()) {
                            HashMap hashMap2 = (HashMap) it2.next();
                            Object obj2 = hashMap2.get(UserConst.PHONE);
                            if (obj2 != null) {
                                hashMap2.put(UserConst.PHONE, e.b((String) obj2));
                            }
                        }
                    }
                }
            }
        }
    }

    private String b(ArrayList<HashMap<String, Object>> arrayList) {
        HashMap hashMap = new HashMap();
        hashMap.put("list", arrayList);
        String fromHashMap = this.f.fromHashMap(hashMap);
        return fromHashMap.substring(8, fromHashMap.length() - 1);
    }

    private String a(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        if (this.g == null) {
            this.g = new HashMap<>();
        }
        for (String str : strArr) {
            this.g.put(e.b(str), str);
        }
        if (this.g == null || this.g.size() <= 0) {
            return null;
        }
        return TextUtils.join(",", this.g.keySet());
    }

    public String a(String str) {
        if (this.g == null || this.g.size() == 0) {
            return null;
        }
        return (String) this.g.get(str);
    }
}
