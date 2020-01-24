package cn.smssdk.net;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import cn.smssdk.utils.SMSLog;
import cn.smssdk.utils.SPHelper;
import com.mob.commons.MobProduct;
import com.mob.commons.SMSSDK;
import com.mob.commons.authorize.DeviceAuthorizer;
import com.mob.commons.eventrecoder.EventRecorder;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class b {
    private static b D;
    private static final ArrayList<String> a = new ArrayList<>(3);
    private String A;
    private String B;
    private String C;
    /* access modifiers changed from: private */
    public String b;
    /* access modifiers changed from: private */
    public String c;
    /* access modifiers changed from: private */
    public Context d;
    /* access modifiers changed from: private */
    public SPHelper e;
    /* access modifiers changed from: private */
    public Hashon f;
    private SparseArray<k> g;
    /* access modifiers changed from: private */
    public final h h = new h();
    private ReentrantLock i;
    /* access modifiers changed from: private */
    public ReentrantReadWriteLock j;
    /* access modifiers changed from: private */
    public Condition k;
    private a l;
    /* access modifiers changed from: private */
    public boolean m;
    private boolean n;
    private boolean o;
    /* access modifiers changed from: private */
    public int p;
    private long q;
    private int r = 1;
    private int s;
    private int t = 1;
    private int u = 1;
    private int v = 1;
    private int w = 1;
    private String x;
    private String y;
    private int z;

    class a extends Thread {
        private boolean b = false;
        private boolean c = true;
        private boolean d = true;
        private long e;

        a() {
        }

        public void a() {
            this.e = (long) (b.this.p * 1000);
        }

        public void a(boolean z) {
            this.b = z;
        }

        public boolean b() {
            return this.d;
        }

        public void c() {
            this.d = false;
        }

        public void b(boolean z) {
            this.c = z;
        }

        public void run() {
            while (true) {
                if (this.c || this.e <= 0) {
                    try {
                        d();
                    } catch (Throwable th) {
                        this.d = true;
                        if (b.this.j.writeLock().tryLock()) {
                            this.c = false;
                            b.this.m = false;
                            b.this.k.signalAll();
                            b.this.j.writeLock().unlock();
                        }
                    }
                }
                try {
                    sleep(100);
                    this.e -= 100;
                } catch (InterruptedException e2) {
                }
            }
        }

        private void d() {
            EventRecorder.addBegin("SMSSDK", "getConfig");
            String b2 = b.this.a(false);
            if (TextUtils.isEmpty(b2)) {
                throw new Throwable("duid is empty!");
            }
            if (this.b) {
                g.b();
            }
            HashMap hashMap = new HashMap();
            hashMap.put("appkey", b.this.b);
            hashMap.put("appsecret", b.this.c);
            hashMap.put("duid", b2);
            hashMap.put("sdkver", "2.1.2");
            hashMap.put("plat", Integer.valueOf(1));
            HashMap a2 = b.this.a(b.this.h, hashMap, false, false, 1);
            if (a2 == null) {
                throw new Throwable("response is empty");
            }
            try {
                b.this.j.writeLock().lock();
                b.this.a(a2);
                this.d = false;
            } catch (Throwable th) {
                b.this.j.writeLock().unlock();
                throw th;
            }
            if (!this.d) {
                b.this.e.setConfig(b.this.f.fromHashMap(a2));
            }
            this.c = false;
            b.this.m = false;
            if (this.b) {
                this.b = false;
                b.this.k.signalAll();
            }
            b.this.j.writeLock().unlock();
            EventRecorder.addEnd("SMSSDK", "getConfig");
        }
    }

    public static b a(Context context) {
        if (D == null) {
            synchronized (b.class) {
                D = new b(context);
            }
        }
        return D;
    }

    private b(Context context) {
        this.d = context;
        this.n = false;
        this.e = SPHelper.getInstance(context);
        this.f = new Hashon();
        a.add("852");
        a.add("853");
        a.add("886");
    }

    public void a(String str, String str2) {
        this.b = str;
        this.c = str2;
    }

    public boolean a(String str) {
        d();
        if (this.t == 0 || ((this.u == 0 && str.equals("86")) || ((this.v == 0 && a.contains(str)) || (this.w == 0 && !a.contains(str) && !str.equals("86"))))) {
            return false;
        }
        return true;
    }

    public boolean a() {
        return this.o;
    }

    public void b() {
        this.o = false;
    }

    /* access modifiers changed from: private */
    public void a(HashMap<String, Object> hashMap) {
        int i2;
        int i3;
        int i4 = 1;
        long longValue = ((Long) hashMap.get("update")).longValue();
        if (longValue != this.q) {
            this.q = longValue;
            this.p = ((Integer) hashMap.get("expire_at")).intValue();
            if (this.p > 0) {
                this.l.a();
            }
            int intValue = ((Integer) hashMap.get("zonelist_update")).intValue();
            if (intValue > this.s) {
                this.s = intValue;
                this.o = true;
            }
            this.r = ((Integer) hashMap.get(SocialConstants.TYPE_REQUEST)).intValue();
            Integer num = (Integer) hashMap.get("sms_toggle");
            this.t = num != null ? num.intValue() : 1;
            Integer num2 = (Integer) hashMap.get("sms_home");
            if (num2 != null) {
                i2 = num2.intValue();
            } else {
                i2 = 1;
            }
            this.u = i2;
            Integer num3 = (Integer) hashMap.get("sms_sp_region");
            if (num3 != null) {
                i3 = num3.intValue();
            } else {
                i3 = 1;
            }
            this.v = i3;
            Integer num4 = (Integer) hashMap.get("sms_foreign");
            if (num4 != null) {
                i4 = num4.intValue();
            }
            this.w = i4;
            this.x = (String) hashMap.get("public_key");
            this.y = (String) hashMap.get("modulus");
            Integer num5 = (Integer) hashMap.get("size");
            this.z = num5 != null ? num5.intValue() : 0;
            if (!TextUtils.isEmpty(this.x) && !TextUtils.isEmpty(this.y) && this.z > 0) {
                g.a(this.x, this.y, this.z);
            }
            ArrayList arrayList = (ArrayList) ((HashMap) hashMap.get("result")).get("urls");
            if (this.g == null) {
                this.g = new SparseArray<>();
            } else if (this.g != null && this.g.size() > 0) {
                this.g.clear();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                HashMap hashMap2 = (HashMap) it.next();
                k kVar = new k(this.d);
                kVar.a(hashMap2);
                kVar.a(this.j);
                this.g.put(kVar.a(), kVar);
            }
        }
    }

    private k a(int i2) {
        String str;
        if (this.b != null && this.b.equalsIgnoreCase("f3fc6baa9ac4")) {
            if ("zh".equals(DeviceHelper.getInstance(this.d).getOSLanguage())) {
                str = String.valueOf(new char[]{25152, 22635, 20889, 'A', 'P', 'P', 'K', 'E', 'Y', 20165, 20379, 27979, 35797, 20351, 29992, 65292, 19988, 19981, 23450, 26399, 22833, 25928, 65292, 35831, 21040, 'm', 'o', 'b', '.', 'c', 'o', 'm', 21518, 21488, 30003, 35831, 27491, 24335, 'A', 'P', 'P', 'K', 'E', 'Y'});
            } else {
                str = "This appkey only for demo!Please request a new one for your own App";
            }
            Log.e("SMSSDK WARNING", str);
        }
        d();
        if (this.r != 0) {
            return (k) this.g.get(i2);
        }
        throw new Throwable("{\"status\":604,\"detail\":\"" + this.d.getResources().getString(R.getStringRes(this.d, "smssdk_error_desc_604")) + "\"}");
    }

    private void d() {
        if (!this.n) {
            String checkRecord = EventRecorder.checkRecord("SMSSDK");
            if (checkRecord != null) {
                EventRecorder.clear();
            }
            String config = this.e.getConfig();
            this.l = new a();
            this.i = new ReentrantLock();
            this.j = new ReentrantReadWriteLock();
            this.k = this.j.writeLock().newCondition();
            EventRecorder.addBegin("SMSSDK", "parseConfig");
            if (!TextUtils.isEmpty(checkRecord) || TextUtils.isEmpty(config)) {
                a(this.f.fromJson("{\"status\":200,\"expire_at\":86400,\"update\":1466077916207,\"zonelist_update\":20151129,\"request\":1,\"sms_toggle\":1,\"sms_home\":1,\"sms_sp_region\":1,\"sms_foreign\":1,\"result\":{\"urls\":[{\"name\":\"sendTextSMS\",\"url\":\"http://code.sms.mob.com/verify/code\",\"params\":[\"appkey\",\"duid\",\"zone\",\"phone\",\"simserial\",\"my_phone\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"},{\"name\":\"submitUser\",\"url\":\"http://sdkapi.sms.mob.com/app/submituserinfo\",\"params\":[],\"params_chunk\":\"user_info_001\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"},{\"name\":\"logCollect\",\"url\":\"http://log.sms.mob.com/log/collect\",\"params\":[],\"params_chunk\":\"collect_001\",\"encode\":\"AES\",\"zip\":1,\"request\":1,\"frequency\":\"1:2:m\"},{\"name\":\"verifyCode\",\"url\":\"http://code.sms.mob.com/client/verification\",\"params\":[\"zone\",\"phone\",\"code\",\"appkey\",\"duid\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"},{\"name\":\"sendVoiceSMS\",\"url\":\"http://code.sms.mob.com/voice/verify/code\",\"params\":[\"zone\",\"phone\",\"appkey\",\"duid\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"},{\"name\":\"getFriend\",\"url\":\"http://addrlist.sms.mob.com/relat/fm\",\"params\":[\"appkey\",\"duid\",\"contactphones\",\"plat\",\"sdkver\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":1,\"request\":1,\"frequency\":\"\"},{\"name\":\"uploadContacts\",\"url\":\"http://addrlist.sms.mob.com/relat/apply\",\"params\":[],\"params_chunk\":\"contacts_002\",\"encode\":\"RSA\",\"zip\":1,\"request\":1,\"frequency\":\"\"},{\"name\":\"getZoneList\",\"url\":\"http://sdkapi.sms.mob.com/utils/zonelist\",\"params\":[\"plat\",\"sdkver\",\"token\",\"appkey\",\"duid\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"},{\"name\":\"logInstall\",\"url\":\"http://log.sms.mob.com/log/install\",\"params\":[],\"params_chunk\":\"install_002\",\"encode\":\"AES\",\"zip\":1,\"request\":1,\"frequency\":\"\"},{\"name\":\"getToken\",\"url\":\"http://sdkapi.sms.mob.com/token/get\",\"params\":[\"appkey\",\"duid\",\"sdkver\",\"plat\",\"aesKey\",\"sign\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"}]}}"));
            } else {
                try {
                    a(this.f.fromJson(config));
                } catch (Throwable th) {
                    this.e.setConfig("");
                    a(this.f.fromJson("{\"status\":200,\"expire_at\":86400,\"update\":1466077916207,\"zonelist_update\":20151129,\"request\":1,\"sms_toggle\":1,\"sms_home\":1,\"sms_sp_region\":1,\"sms_foreign\":1,\"result\":{\"urls\":[{\"name\":\"sendTextSMS\",\"url\":\"http://code.sms.mob.com/verify/code\",\"params\":[\"appkey\",\"duid\",\"zone\",\"phone\",\"simserial\",\"my_phone\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"},{\"name\":\"submitUser\",\"url\":\"http://sdkapi.sms.mob.com/app/submituserinfo\",\"params\":[],\"params_chunk\":\"user_info_001\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"},{\"name\":\"logCollect\",\"url\":\"http://log.sms.mob.com/log/collect\",\"params\":[],\"params_chunk\":\"collect_001\",\"encode\":\"AES\",\"zip\":1,\"request\":1,\"frequency\":\"1:2:m\"},{\"name\":\"verifyCode\",\"url\":\"http://code.sms.mob.com/client/verification\",\"params\":[\"zone\",\"phone\",\"code\",\"appkey\",\"duid\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"},{\"name\":\"sendVoiceSMS\",\"url\":\"http://code.sms.mob.com/voice/verify/code\",\"params\":[\"zone\",\"phone\",\"appkey\",\"duid\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"},{\"name\":\"getFriend\",\"url\":\"http://addrlist.sms.mob.com/relat/fm\",\"params\":[\"appkey\",\"duid\",\"contactphones\",\"plat\",\"sdkver\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":1,\"request\":1,\"frequency\":\"\"},{\"name\":\"uploadContacts\",\"url\":\"http://addrlist.sms.mob.com/relat/apply\",\"params\":[],\"params_chunk\":\"contacts_002\",\"encode\":\"RSA\",\"zip\":1,\"request\":1,\"frequency\":\"\"},{\"name\":\"getZoneList\",\"url\":\"http://sdkapi.sms.mob.com/utils/zonelist\",\"params\":[\"plat\",\"sdkver\",\"token\",\"appkey\",\"duid\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"},{\"name\":\"logInstall\",\"url\":\"http://log.sms.mob.com/log/install\",\"params\":[],\"params_chunk\":\"install_002\",\"encode\":\"AES\",\"zip\":1,\"request\":1,\"frequency\":\"\"},{\"name\":\"getToken\",\"url\":\"http://sdkapi.sms.mob.com/token/get\",\"params\":[\"appkey\",\"duid\",\"sdkver\",\"plat\",\"aesKey\",\"sign\"],\"params_chunk\":\"\",\"encode\":\"RSA\",\"zip\":0,\"request\":1,\"frequency\":\"\"}]}}"));
                }
            }
            String aeskey = this.e.getAeskey();
            if (!TextUtils.isEmpty(aeskey)) {
                g.a(aeskey);
            }
            this.n = true;
            this.l.start();
            EventRecorder.addEnd("SMSSDK", "parseConfig");
        }
    }

    /* access modifiers changed from: private */
    public String a(boolean z2) {
        if (z2 || TextUtils.isEmpty(this.A)) {
            try {
                this.i.lock();
                if (!TextUtils.isEmpty(this.A)) {
                    return this.A;
                }
                this.A = DeviceAuthorizer.authorize(this.d, (MobProduct) new SMSSDK());
                this.i.unlock();
            } finally {
                this.i.unlock();
            }
        }
        return this.A;
    }

    private synchronized String b(boolean z2) {
        String str;
        this.B = this.e.getToken();
        if (z2 || TextUtils.isEmpty(this.B) || !g.a()) {
            HashMap hashMap = new HashMap();
            hashMap.put("aesKey", e(a(false)));
            hashMap.put("sign", e());
            this.B = (String) ((HashMap) a(3, hashMap).get("result")).get("token");
            if (TextUtils.isEmpty(this.B)) {
                throw new Throwable("get token error!");
            }
            this.e.setToken(this.B);
            str = this.B;
        } else {
            str = this.B;
        }
        return str;
    }

    public HashMap<String, Object> a(int i2, HashMap<String, Object> hashMap) {
        k a2 = a(i2);
        HashMap<String, Object> a3 = a(a2, hashMap, false, false, 1);
        if (a2.a() != 9 || a3 == null) {
            if (a3 != null) {
                a2.c();
            }
        } else if (((Integer) a3.get("smart")) == null) {
            a2.c();
        }
        return a3;
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.util.HashMap] */
    /* JADX WARNING: type inference failed for: r0v14, types: [java.util.HashMap<java.lang.String, java.lang.Object>] */
    /* JADX WARNING: type inference failed for: r1v4, types: [java.util.HashMap] */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.HashMap<java.lang.String, java.lang.Object> a(cn.smssdk.net.a r5, java.util.HashMap<java.lang.String, java.lang.Object> r6, boolean r7, boolean r8, int r9) {
        /*
            r4 = this;
            r1 = 0
            r0 = 5
            if (r9 <= r0) goto L_0x002f
            java.lang.String r0 = "Server is busy!"
            android.content.Context r1 = r4.d
            java.lang.String r2 = "smssdk_error_desc_server_busy"
            int r1 = com.mob.tools.utils.R.getStringRes(r1, r2)
            if (r1 <= 0) goto L_0x0018
            android.content.Context r0 = r4.d
            java.lang.String r0 = r0.getString(r1)
        L_0x0018:
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            java.lang.String r2 = "description"
            r1.put(r2, r0)
            java.lang.Throwable r0 = new java.lang.Throwable
            com.mob.tools.utils.Hashon r2 = r4.f
            java.lang.String r1 = r2.fromHashMap(r1)
            r0.<init>(r1)
            throw r0
        L_0x002f:
            java.lang.String r2 = r4.a(r7)
            boolean r0 = r5 instanceof cn.smssdk.net.k
            if (r0 == 0) goto L_0x0090
            int r0 = r5.a()
            r3 = 3
            if (r0 == r3) goto L_0x0090
            java.lang.String r0 = r4.b(r8)
        L_0x0042:
            java.lang.String r2 = r5.b(r2, r0, r6)     // Catch:{ Throwable -> 0x005d }
            com.mob.tools.utils.Hashon r0 = r4.f     // Catch:{ Throwable -> 0x0063 }
            java.util.HashMap r1 = r0.fromJson(r2)     // Catch:{ Throwable -> 0x0063 }
        L_0x004c:
            if (r1 == 0) goto L_0x0054
            int r0 = r1.size()
            if (r0 > 0) goto L_0x006c
        L_0x0054:
            java.lang.Throwable r0 = new java.lang.Throwable
            java.lang.String r1 = "[hashon]Response is empty"
            r0.<init>(r1)
            throw r0
        L_0x005d:
            r0 = move-exception
            java.util.HashMap r0 = r4.a(r0, r5, r6, r9)
        L_0x0062:
            return r0
        L_0x0063:
            r0 = move-exception
            com.mob.tools.log.NLog r3 = cn.smssdk.utils.SMSLog.getInstance()
            r3.e(r0)
            goto L_0x004c
        L_0x006c:
            java.lang.String r0 = "status"
            java.lang.Object r0 = r1.get(r0)
            if (r0 == 0) goto L_0x0079
            boolean r3 = r0 instanceof java.lang.Integer
            if (r3 != 0) goto L_0x007f
        L_0x0079:
            java.lang.Throwable r0 = new java.lang.Throwable
            r0.<init>(r2)
            throw r0
        L_0x007f:
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            r2 = 200(0xc8, float:2.8E-43)
            if (r0 == r2) goto L_0x008e
            java.util.HashMap r0 = r4.a(r0, r5, r6, r9)
            goto L_0x0062
        L_0x008e:
            r0 = r1
            goto L_0x0062
        L_0x0090:
            r0 = r1
            goto L_0x0042
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.smssdk.net.b.a(cn.smssdk.net.a, java.util.HashMap, boolean, boolean, int):java.util.HashMap");
    }

    private HashMap<String, Object> a(Throwable th, a aVar, HashMap<String, Object> hashMap, int i2) {
        HashMap fromJson = this.f.fromJson(th.getMessage());
        Integer num = (Integer) fromJson.get(NotificationCompat.CATEGORY_STATUS);
        if (num == null || num.intValue() == 0) {
            throw th;
        }
        HashMap<String, Object> a2 = a(num.intValue(), aVar, hashMap, i2);
        if (a2 != null) {
            return a2;
        }
        fromJson.put(SocialConstants.PARAM_COMMENT, b(num.intValue()));
        fromJson.put("detail", c(num.intValue()));
        throw new Throwable(this.f.fromHashMap(fromJson));
    }

    private HashMap<String, Object> a(int i2, a aVar, HashMap<String, Object> hashMap, int i3) {
        a aVar2;
        int i4 = i3 + 1;
        if (i2 == 453) {
            if (aVar instanceof k) {
                int a2 = aVar.a();
                try {
                    this.j.writeLock().lock();
                    this.l.b(true);
                    this.l.a(true);
                    this.m = true;
                    while (this.m) {
                        this.k.await();
                    }
                    this.j.writeLock().unlock();
                    if (this.l.b()) {
                        this.l.a(false);
                        g.b();
                        this.l.c();
                        return a(aVar, hashMap, false, false, i4);
                    }
                    if (a2 > 0) {
                        aVar = a(a2);
                    }
                    aVar2 = aVar;
                } catch (Throwable th) {
                    this.l.a(false);
                    g.b();
                    return a(aVar, hashMap, false, false, i4);
                }
            } else {
                g.b();
                aVar2 = aVar;
            }
            return a(aVar2, hashMap, false, false, i4);
        } else if (i2 == 419 || i2 == 420) {
            this.e.setToken("");
            return a(aVar, hashMap, true, true, i4);
        } else if (i2 == 401 || i2 == 402) {
            this.e.setToken("");
            return a(aVar, hashMap, false, true, i4);
        } else if (i2 == 403 || i2 == 404 || i2 == 454) {
            return a(aVar, hashMap, false, false, i4);
        } else {
            if (i2 == 480) {
                hashMap.put("aesKey", e(a(false)));
                return a(aVar, hashMap, false, true, i4);
            }
            throw new Throwable("{status:'" + i2 + "'}");
        }
    }

    private String b(int i2) {
        try {
            int stringRes = R.getStringRes(this.d, "smssdk_error_desc_" + i2);
            if (stringRes > 0) {
                return this.d.getString(stringRes);
            }
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
        }
        return null;
    }

    private String c(int i2) {
        try {
            int stringRes = R.getStringRes(this.d, "smssdk_error_detail_" + i2);
            if (stringRes > 0) {
                return this.d.getString(stringRes);
            }
        } catch (Throwable th) {
            SMSLog.getInstance().w(th);
        }
        return null;
    }

    private String e(String str) {
        String MD5 = Data.MD5(str + System.currentTimeMillis());
        g.a(MD5);
        this.e.setAeskey(MD5);
        return MD5;
    }

    private String e() {
        if (!TextUtils.isEmpty(this.C)) {
            return this.C;
        }
        try {
            this.C = Data.MD5(this.d.getPackageManager().getPackageInfo(this.d.getPackageName(), 64).signatures[0].toByteArray());
            return this.C;
        } catch (NameNotFoundException e2) {
            return null;
        }
    }

    public String b(String str) {
        if (!g.a()) {
            try {
                b(true);
            } catch (Throwable th) {
                return null;
            }
        }
        return Data.byteToHex(g.b(str));
    }

    public String c(String str) {
        String a2 = i.a().a(str);
        if (!TextUtils.isEmpty(a2)) {
            return a2;
        }
        if (!g.a()) {
            try {
                b(true);
            } catch (Throwable th) {
                return null;
            }
        }
        return new String(g.a(d(str)));
    }

    public void c() {
        a(false);
        new Thread(new c(this)).start();
        new Thread(new d(this)).start();
        new Thread(new e(this)).start();
        new Thread(new f(this)).start();
    }

    public static byte[] d(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length % 2 == 1) {
            return null;
        }
        int i2 = length / 2;
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            try {
                bArr[i3] = (byte) Integer.parseInt(str.substring(i3 * 2, (i3 * 2) + 2), 16);
                i3++;
            } catch (Throwable th) {
                return null;
            }
        }
        return bArr;
    }
}
