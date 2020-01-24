package com.mob.commons.authorize;

import android.content.Context;
import com.mob.commons.MobProduct;
import com.mob.commons.e;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.R;
import java.io.File;
import java.util.HashMap;

/* compiled from: Authorizer */
public final class a {
    public final String a(final Context context, final MobProduct mobProduct) {
        final String[] strArr = new String[1];
        e.a(new File(R.getCacheRoot(context), "comm/locks/.globalLock"), true, new Runnable() {
            public void run() {
                strArr[0] = a.this.b(context, mobProduct);
            }
        });
        return strArr[0];
    }

    private final File b(Context context) {
        File file = new File(R.getCacheRoot(context), "comm/dbs/.duid");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004e, code lost:
        if (r1 != null) goto L_0x0050;
     */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a5 A[SYNTHETIC, Splitter:B:43:0x00a5] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b3 A[SYNTHETIC, Splitter:B:51:0x00b3] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00e0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String b(android.content.Context r7, com.mob.commons.MobProduct r8) {
        /*
            r6 = this;
            r1 = 0
            java.io.File r0 = r6.b(r7)     // Catch:{ Throwable -> 0x00b7 }
            boolean r2 = r0.exists()     // Catch:{ Throwable -> 0x00b7 }
            if (r2 == 0) goto L_0x00bf
            boolean r2 = r0.isFile()     // Catch:{ Throwable -> 0x00b7 }
            if (r2 == 0) goto L_0x00bf
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x009a, all -> 0x00af }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x009a, all -> 0x00af }
            java.io.ObjectInputStream r2 = new java.io.ObjectInputStream     // Catch:{ Throwable -> 0x009a, all -> 0x00af }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x009a, all -> 0x00af }
            java.lang.Object r0 = r2.readObject()     // Catch:{ Throwable -> 0x00d7 }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x00d7 }
            if (r2 == 0) goto L_0x0026
            r2.close()     // Catch:{ Throwable -> 0x00cb }
        L_0x0026:
            if (r0 != 0) goto L_0x00e0
            java.util.HashMap r0 = r6.c(r7, r8)
            r3 = r0
        L_0x002d:
            if (r3 == 0) goto L_0x00de
            java.lang.String r0 = "duid"
            java.lang.Object r0 = r3.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x0099
            java.lang.String r1 = "deviceInfo"
            java.lang.Object r1 = r3.get(r1)     // Catch:{ Throwable -> 0x00c2 }
            java.util.HashMap r1 = (java.util.HashMap) r1     // Catch:{ Throwable -> 0x00c2 }
            boolean r1 = r6.a(r7, r1)     // Catch:{ Throwable -> 0x00c2 }
            if (r1 == 0) goto L_0x00db
            r1 = 1
            java.lang.String r1 = r6.a(r7, r8, r3, r1)     // Catch:{ Throwable -> 0x00c2 }
            if (r1 == 0) goto L_0x00db
        L_0x0050:
            java.lang.String r0 = "appInfo"
            java.lang.Object r0 = r3.get(r0)     // Catch:{ Throwable -> 0x00d0 }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x00d0 }
            if (r0 != 0) goto L_0x00d9
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Throwable -> 0x00d0 }
            r0.<init>()     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r2 = "appInfo"
            r3.put(r2, r0)     // Catch:{ Throwable -> 0x00d0 }
            r2 = r0
        L_0x0067:
            com.mob.tools.utils.DeviceHelper r0 = com.mob.tools.utils.DeviceHelper.getInstance(r7)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r4 = r0.getPackageName()     // Catch:{ Throwable -> 0x00d0 }
            java.lang.Object r0 = r2.get(r4)     // Catch:{ Throwable -> 0x00d0 }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x00d0 }
            if (r0 != 0) goto L_0x007f
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Throwable -> 0x00d0 }
            r0.<init>()     // Catch:{ Throwable -> 0x00d0 }
            r2.put(r4, r0)     // Catch:{ Throwable -> 0x00d0 }
        L_0x007f:
            java.lang.String r2 = r8.getProductTag()     // Catch:{ Throwable -> 0x00d0 }
            java.lang.Object r0 = r0.get(r2)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x00d0 }
            if (r0 == 0) goto L_0x0095
            java.lang.String r2 = r8.getProductAppkey()     // Catch:{ Throwable -> 0x00d0 }
            boolean r0 = r0.equals(r2)     // Catch:{ Throwable -> 0x00d0 }
            if (r0 != 0) goto L_0x0098
        L_0x0095:
            r6.a(r7, r8, r3)     // Catch:{ Throwable -> 0x00d0 }
        L_0x0098:
            r0 = r1
        L_0x0099:
            return r0
        L_0x009a:
            r0 = move-exception
            r2 = r1
        L_0x009c:
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x00d5 }
            r3.w(r0)     // Catch:{ all -> 0x00d5 }
            if (r2 == 0) goto L_0x00bf
            r2.close()     // Catch:{ Throwable -> 0x00ab }
            r0 = r1
            goto L_0x0026
        L_0x00ab:
            r0 = move-exception
            r0 = r1
            goto L_0x0026
        L_0x00af:
            r0 = move-exception
            r2 = r1
        L_0x00b1:
            if (r2 == 0) goto L_0x00b6
            r2.close()     // Catch:{ Throwable -> 0x00ce }
        L_0x00b6:
            throw r0     // Catch:{ Throwable -> 0x00b7 }
        L_0x00b7:
            r0 = move-exception
            com.mob.tools.log.NLog r2 = com.mob.tools.MobLog.getInstance()
            r2.w(r0)
        L_0x00bf:
            r0 = r1
            goto L_0x0026
        L_0x00c2:
            r1 = move-exception
        L_0x00c3:
            com.mob.tools.log.NLog r2 = com.mob.tools.MobLog.getInstance()
            r2.w(r1)
            goto L_0x0099
        L_0x00cb:
            r2 = move-exception
            goto L_0x0026
        L_0x00ce:
            r2 = move-exception
            goto L_0x00b6
        L_0x00d0:
            r0 = move-exception
            r5 = r0
            r0 = r1
            r1 = r5
            goto L_0x00c3
        L_0x00d5:
            r0 = move-exception
            goto L_0x00b1
        L_0x00d7:
            r0 = move-exception
            goto L_0x009c
        L_0x00d9:
            r2 = r0
            goto L_0x0067
        L_0x00db:
            r1 = r0
            goto L_0x0050
        L_0x00de:
            r0 = r1
            goto L_0x0099
        L_0x00e0:
            r3 = r0
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.b(android.content.Context, com.mob.commons.MobProduct):java.lang.String");
    }

    private final boolean a(Context context, HashMap<String, String> hashMap) {
        if (hashMap == null) {
            return true;
        }
        DeviceHelper instance = DeviceHelper.getInstance(context);
        String advertisingID = instance.getAdvertisingID();
        Object obj = hashMap.get("adsid");
        if (advertisingID != null) {
            if (obj == null && advertisingID != null) {
                return true;
            }
            if (obj != null && !obj.equals(advertisingID)) {
                return true;
            }
        }
        Object obj2 = hashMap.get("imei");
        if (obj2 == null || !obj2.equals(instance.getIMEI())) {
            return true;
        }
        Object obj3 = hashMap.get("serialno");
        if (obj3 == null || !obj3.equals(instance.getSerialno())) {
            return true;
        }
        Object obj4 = hashMap.get("mac");
        if (obj4 == null || !obj4.equals(instance.getMacAddress())) {
            return true;
        }
        Object obj5 = hashMap.get("model");
        if (obj5 == null || !obj5.equals(instance.getModel())) {
            return true;
        }
        Object obj6 = hashMap.get("factory");
        if (obj6 == null || !obj6.equals(instance.getManufacturer())) {
            return true;
        }
        Object obj7 = hashMap.get("androidid");
        if (obj7 == null || !obj7.equals(instance.getAndroidID())) {
            return true;
        }
        Object obj8 = hashMap.get("sysver");
        if (obj8 == null || !obj8.equals(instance.getOSVersionName())) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0123 A[SYNTHETIC, Splitter:B:33:0x0123] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0130 A[SYNTHETIC, Splitter:B:39:0x0130] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.HashMap<java.lang.String, java.lang.Object> c(android.content.Context r11, com.mob.commons.MobProduct r12) {
        /*
            r10 = this;
            r6 = 0
            com.mob.tools.utils.DeviceHelper r1 = com.mob.tools.utils.DeviceHelper.getInstance(r11)     // Catch:{ Throwable -> 0x0137 }
            r0 = -1
            java.lang.String r2 = r1.getCarrier()     // Catch:{ Throwable -> 0x00cb }
            int r0 = com.mob.tools.utils.R.parseInt(r2)     // Catch:{ Throwable -> 0x00cb }
            r7 = r0
        L_0x000f:
            java.util.HashMap r8 = new java.util.HashMap     // Catch:{ Throwable -> 0x0137 }
            r8.<init>()     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = "adsid"
            java.lang.String r2 = r1.getAdvertisingID()     // Catch:{ Throwable -> 0x0137 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = "imei"
            java.lang.String r2 = r1.getIMEI()     // Catch:{ Throwable -> 0x0137 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = "serialno"
            java.lang.String r2 = r1.getSerialno()     // Catch:{ Throwable -> 0x0137 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = "androidid"
            java.lang.String r2 = r1.getAndroidID()     // Catch:{ Throwable -> 0x0137 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = "mac"
            java.lang.String r2 = r1.getMacAddress()     // Catch:{ Throwable -> 0x0137 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = "model"
            java.lang.String r2 = r1.getModel()     // Catch:{ Throwable -> 0x0137 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = "factory"
            java.lang.String r2 = r1.getManufacturer()     // Catch:{ Throwable -> 0x0137 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = "carrier"
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x0137 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = "screensize"
            java.lang.String r2 = r1.getScreenSize()     // Catch:{ Throwable -> 0x0137 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = "sysver"
            java.lang.String r1 = r1.getOSVersionName()     // Catch:{ Throwable -> 0x0137 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = "plat"
            r1 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Throwable -> 0x0137 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0137 }
            com.mob.tools.utils.Hashon r9 = new com.mob.tools.utils.Hashon     // Catch:{ Throwable -> 0x0137 }
            r9.<init>()     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = r9.fromHashMap(r8)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r1 = "sdk.commonap.sdk"
            byte[] r0 = com.mob.tools.utils.Data.AES128Encode(r1, r0)     // Catch:{ Throwable -> 0x0137 }
            r1 = 2
            java.lang.String r0 = android.util.Base64.encodeToString(r0, r1)     // Catch:{ Throwable -> 0x0137 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0137 }
            r2.<init>()     // Catch:{ Throwable -> 0x0137 }
            com.mob.tools.network.KVPair r1 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r3 = "m"
            r1.<init>(r3, r0)     // Catch:{ Throwable -> 0x0137 }
            r2.add(r1)     // Catch:{ Throwable -> 0x0137 }
            com.mob.tools.network.NetworkHelper$NetworkTimeOut r5 = new com.mob.tools.network.NetworkHelper$NetworkTimeOut     // Catch:{ Throwable -> 0x0137 }
            r5.<init>()     // Catch:{ Throwable -> 0x0137 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.readTimout = r0     // Catch:{ Throwable -> 0x0137 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.connectionTimeout = r0     // Catch:{ Throwable -> 0x0137 }
            com.mob.tools.network.NetworkHelper r0 = new com.mob.tools.network.NetworkHelper     // Catch:{ Throwable -> 0x0137 }
            r0.<init>()     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r1 = "http://devs.data.mob.com:80/dinfo"
            r3 = 0
            r4 = 0
            java.lang.String r0 = r0.httpPost(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0137 }
            java.util.HashMap r0 = r9.fromJson(r0)     // Catch:{ Throwable -> 0x0137 }
            if (r0 != 0) goto L_0x00cf
            r0 = r6
        L_0x00ca:
            return r0
        L_0x00cb:
            r2 = move-exception
            r7 = r0
            goto L_0x000f
        L_0x00cf:
            java.lang.String r1 = "duid"
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0137 }
            if (r0 == 0) goto L_0x00e0
            int r1 = r0.length()     // Catch:{ Throwable -> 0x0137 }
            if (r1 > 0) goto L_0x00e2
        L_0x00e0:
            r0 = r6
            goto L_0x00ca
        L_0x00e2:
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ Throwable -> 0x0137 }
            r1.<init>()     // Catch:{ Throwable -> 0x0137 }
            java.lang.String r2 = "duid"
            r1.put(r2, r0)     // Catch:{ Throwable -> 0x0118, all -> 0x012c }
            java.lang.String r0 = "carrier"
            java.lang.String r2 = java.lang.String.valueOf(r7)     // Catch:{ Throwable -> 0x0118, all -> 0x012c }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0118, all -> 0x012c }
            java.lang.String r0 = "deviceInfo"
            r1.put(r0, r8)     // Catch:{ Throwable -> 0x0118, all -> 0x012c }
            java.io.File r0 = r10.b(r11)     // Catch:{ Throwable -> 0x0118, all -> 0x012c }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0118, all -> 0x012c }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x0118, all -> 0x012c }
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ Throwable -> 0x0118, all -> 0x012c }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0118, all -> 0x012c }
            r2.writeObject(r1)     // Catch:{ Throwable -> 0x0145 }
            if (r2 == 0) goto L_0x0116
            r2.flush()     // Catch:{ Throwable -> 0x0147 }
            r2.close()     // Catch:{ Throwable -> 0x0147 }
        L_0x0116:
            r0 = r1
            goto L_0x00ca
        L_0x0118:
            r0 = move-exception
            r2 = r6
        L_0x011a:
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x0143 }
            r3.w(r0)     // Catch:{ all -> 0x0143 }
            if (r2 == 0) goto L_0x0116
            r2.flush()     // Catch:{ Throwable -> 0x012a }
            r2.close()     // Catch:{ Throwable -> 0x012a }
            goto L_0x0116
        L_0x012a:
            r0 = move-exception
            goto L_0x0116
        L_0x012c:
            r0 = move-exception
            r2 = r6
        L_0x012e:
            if (r2 == 0) goto L_0x0136
            r2.flush()     // Catch:{ Throwable -> 0x0141 }
            r2.close()     // Catch:{ Throwable -> 0x0141 }
        L_0x0136:
            throw r0     // Catch:{ Throwable -> 0x0137 }
        L_0x0137:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            r0 = r6
            goto L_0x00ca
        L_0x0141:
            r1 = move-exception
            goto L_0x0136
        L_0x0143:
            r0 = move-exception
            goto L_0x012e
        L_0x0145:
            r0 = move-exception
            goto L_0x011a
        L_0x0147:
            r0 = move-exception
            goto L_0x0116
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.c(android.content.Context, com.mob.commons.MobProduct):java.util.HashMap");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0121 A[SYNTHETIC, Splitter:B:33:0x0121] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x012e A[SYNTHETIC, Splitter:B:39:0x012e] */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A[Catch:{  }, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.String a(android.content.Context r11, com.mob.commons.MobProduct r12, java.util.HashMap<java.lang.String, java.lang.Object> r13, boolean r14) {
        /*
            r10 = this;
            r6 = 0
            com.mob.tools.utils.DeviceHelper r1 = com.mob.tools.utils.DeviceHelper.getInstance(r11)     // Catch:{ Throwable -> 0x0135 }
            r0 = -1
            java.lang.String r2 = r1.getCarrier()     // Catch:{ Throwable -> 0x00cb }
            int r0 = com.mob.tools.utils.R.parseInt(r2)     // Catch:{ Throwable -> 0x00cb }
            r7 = r0
        L_0x000f:
            java.util.HashMap r8 = new java.util.HashMap     // Catch:{ Throwable -> 0x0135 }
            r8.<init>()     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = "adsid"
            java.lang.String r2 = r1.getAdvertisingID()     // Catch:{ Throwable -> 0x0135 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = "imei"
            java.lang.String r2 = r1.getIMEI()     // Catch:{ Throwable -> 0x0135 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = "serialno"
            java.lang.String r2 = r1.getSerialno()     // Catch:{ Throwable -> 0x0135 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = "androidid"
            java.lang.String r2 = r1.getAndroidID()     // Catch:{ Throwable -> 0x0135 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = "mac"
            java.lang.String r2 = r1.getMacAddress()     // Catch:{ Throwable -> 0x0135 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = "model"
            java.lang.String r2 = r1.getModel()     // Catch:{ Throwable -> 0x0135 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = "factory"
            java.lang.String r2 = r1.getManufacturer()     // Catch:{ Throwable -> 0x0135 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = "carrier"
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x0135 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = "screensize"
            java.lang.String r2 = r1.getScreenSize()     // Catch:{ Throwable -> 0x0135 }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = "sysver"
            java.lang.String r1 = r1.getOSVersionName()     // Catch:{ Throwable -> 0x0135 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = "plat"
            r1 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Throwable -> 0x0135 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0135 }
            com.mob.tools.utils.Hashon r9 = new com.mob.tools.utils.Hashon     // Catch:{ Throwable -> 0x0135 }
            r9.<init>()     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = r9.fromHashMap(r8)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r1 = "sdk.commonap.sdk"
            byte[] r0 = com.mob.tools.utils.Data.AES128Encode(r1, r0)     // Catch:{ Throwable -> 0x0135 }
            r1 = 2
            java.lang.String r0 = android.util.Base64.encodeToString(r0, r1)     // Catch:{ Throwable -> 0x0135 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0135 }
            r2.<init>()     // Catch:{ Throwable -> 0x0135 }
            com.mob.tools.network.KVPair r1 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r3 = "m"
            r1.<init>(r3, r0)     // Catch:{ Throwable -> 0x0135 }
            r2.add(r1)     // Catch:{ Throwable -> 0x0135 }
            com.mob.tools.network.NetworkHelper$NetworkTimeOut r5 = new com.mob.tools.network.NetworkHelper$NetworkTimeOut     // Catch:{ Throwable -> 0x0135 }
            r5.<init>()     // Catch:{ Throwable -> 0x0135 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.readTimout = r0     // Catch:{ Throwable -> 0x0135 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.connectionTimeout = r0     // Catch:{ Throwable -> 0x0135 }
            com.mob.tools.network.NetworkHelper r0 = new com.mob.tools.network.NetworkHelper     // Catch:{ Throwable -> 0x0135 }
            r0.<init>()     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r1 = "http://devs.data.mob.com:80/dinfo"
            r3 = 0
            r4 = 0
            java.lang.String r0 = r0.httpPost(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0135 }
            java.util.HashMap r0 = r9.fromJson(r0)     // Catch:{ Throwable -> 0x0135 }
            if (r0 != 0) goto L_0x00cf
            r0 = r6
        L_0x00ca:
            return r0
        L_0x00cb:
            r2 = move-exception
            r7 = r0
            goto L_0x000f
        L_0x00cf:
            java.lang.String r1 = "duid"
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0135 }
            if (r0 == 0) goto L_0x00e0
            int r1 = r0.length()     // Catch:{ Throwable -> 0x0135 }
            if (r1 > 0) goto L_0x00e2
        L_0x00e0:
            r0 = r6
            goto L_0x00ca
        L_0x00e2:
            java.lang.String r1 = "duid"
            r13.put(r1, r0)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r1 = "carrier"
            java.lang.String r2 = java.lang.String.valueOf(r7)     // Catch:{ Throwable -> 0x0135 }
            r8.put(r1, r2)     // Catch:{ Throwable -> 0x0135 }
            java.lang.String r1 = "deviceInfo"
            r13.put(r1, r8)     // Catch:{ Throwable -> 0x0135 }
            if (r14 == 0) goto L_0x00ca
            java.io.File r1 = r10.b(r11)     // Catch:{ Throwable -> 0x0116, all -> 0x012a }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0116, all -> 0x012a }
            r3.<init>(r1)     // Catch:{ Throwable -> 0x0116, all -> 0x012a }
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ Throwable -> 0x0116, all -> 0x012a }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0116, all -> 0x012a }
            r2.writeObject(r13)     // Catch:{ Throwable -> 0x0143 }
            if (r2 == 0) goto L_0x00ca
            r2.flush()     // Catch:{ Throwable -> 0x0114 }
            r2.close()     // Catch:{ Throwable -> 0x0114 }
            goto L_0x00ca
        L_0x0114:
            r1 = move-exception
            goto L_0x00ca
        L_0x0116:
            r1 = move-exception
            r2 = r6
        L_0x0118:
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x0141 }
            r3.w(r1)     // Catch:{ all -> 0x0141 }
            if (r2 == 0) goto L_0x00ca
            r2.flush()     // Catch:{ Throwable -> 0x0128 }
            r2.close()     // Catch:{ Throwable -> 0x0128 }
            goto L_0x00ca
        L_0x0128:
            r1 = move-exception
            goto L_0x00ca
        L_0x012a:
            r0 = move-exception
            r2 = r6
        L_0x012c:
            if (r2 == 0) goto L_0x0134
            r2.flush()     // Catch:{ Throwable -> 0x013f }
            r2.close()     // Catch:{ Throwable -> 0x013f }
        L_0x0134:
            throw r0     // Catch:{ Throwable -> 0x0135 }
        L_0x0135:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            r0 = r6
            goto L_0x00ca
        L_0x013f:
            r1 = move-exception
            goto L_0x0134
        L_0x0141:
            r0 = move-exception
            goto L_0x012c
        L_0x0143:
            r1 = move-exception
            goto L_0x0118
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.a(android.content.Context, com.mob.commons.MobProduct, java.util.HashMap, boolean):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0116 A[SYNTHETIC, Splitter:B:21:0x0116] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0122 A[SYNTHETIC, Splitter:B:26:0x0122] */
    /* JADX WARNING: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void a(android.content.Context r9, com.mob.commons.MobProduct r10, java.util.HashMap<java.lang.String, java.lang.Object> r11) {
        /*
            r8 = this;
            r6 = 0
            java.lang.String r0 = "duid"
            java.lang.Object r0 = r11.get(r0)     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0129 }
            com.mob.tools.utils.DeviceHelper r7 = com.mob.tools.utils.DeviceHelper.getInstance(r9)     // Catch:{ Throwable -> 0x0129 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0129 }
            r2.<init>()     // Catch:{ Throwable -> 0x0129 }
            com.mob.tools.network.KVPair r1 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r3 = "product"
            java.lang.String r4 = r10.getProductTag()     // Catch:{ Throwable -> 0x0129 }
            r1.<init>(r3, r4)     // Catch:{ Throwable -> 0x0129 }
            r2.add(r1)     // Catch:{ Throwable -> 0x0129 }
            com.mob.tools.network.KVPair r1 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r3 = "appkey"
            java.lang.String r4 = r10.getProductAppkey()     // Catch:{ Throwable -> 0x0129 }
            r1.<init>(r3, r4)     // Catch:{ Throwable -> 0x0129 }
            r2.add(r1)     // Catch:{ Throwable -> 0x0129 }
            com.mob.tools.network.KVPair r1 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r3 = "duid"
            r1.<init>(r3, r0)     // Catch:{ Throwable -> 0x0129 }
            r2.add(r1)     // Catch:{ Throwable -> 0x0129 }
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r1 = "apppkg"
            java.lang.String r3 = r7.getPackageName()     // Catch:{ Throwable -> 0x0129 }
            r0.<init>(r1, r3)     // Catch:{ Throwable -> 0x0129 }
            r2.add(r0)     // Catch:{ Throwable -> 0x0129 }
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r1 = "appver"
            int r3 = r7.getAppVersion()     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0129 }
            r0.<init>(r1, r3)     // Catch:{ Throwable -> 0x0129 }
            r2.add(r0)     // Catch:{ Throwable -> 0x0129 }
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r1 = "sdkver"
            int r3 = r10.getSdkver()     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0129 }
            r0.<init>(r1, r3)     // Catch:{ Throwable -> 0x0129 }
            r2.add(r0)     // Catch:{ Throwable -> 0x0129 }
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r1 = "network"
            java.lang.String r3 = r7.getDetailNetworkTypeForStatic()     // Catch:{ Throwable -> 0x0129 }
            r0.<init>(r1, r3)     // Catch:{ Throwable -> 0x0129 }
            r2.add(r0)     // Catch:{ Throwable -> 0x0129 }
            com.mob.tools.network.NetworkHelper$NetworkTimeOut r5 = new com.mob.tools.network.NetworkHelper$NetworkTimeOut     // Catch:{ Throwable -> 0x0129 }
            r5.<init>()     // Catch:{ Throwable -> 0x0129 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.readTimout = r0     // Catch:{ Throwable -> 0x0129 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.connectionTimeout = r0     // Catch:{ Throwable -> 0x0129 }
            com.mob.tools.network.NetworkHelper r0 = new com.mob.tools.network.NetworkHelper     // Catch:{ Throwable -> 0x0129 }
            r0.<init>()     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r1 = "http://devs.data.mob.com:80/dsign"
            r3 = 0
            r4 = 0
            java.lang.String r0 = r0.httpPost(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0129 }
            com.mob.tools.utils.Hashon r1 = new com.mob.tools.utils.Hashon     // Catch:{ Throwable -> 0x0129 }
            r1.<init>()     // Catch:{ Throwable -> 0x0129 }
            java.util.HashMap r0 = r1.fromJson(r0)     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r1 = "true"
            java.lang.String r2 = "reup"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0129 }
            boolean r1 = r1.equals(r2)     // Catch:{ Throwable -> 0x0129 }
            if (r1 == 0) goto L_0x00bf
            r1 = 0
            java.lang.String r1 = r8.a(r9, r10, r11, r1)     // Catch:{ Throwable -> 0x0129 }
            if (r1 == 0) goto L_0x00bf
        L_0x00bf:
            java.lang.String r1 = "200"
            java.lang.String r2 = "status"
            java.lang.Object r0 = r0.get(r2)     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x0129 }
            boolean r0 = r1.equals(r0)     // Catch:{ Throwable -> 0x0129 }
            if (r0 == 0) goto L_0x010a
            java.lang.String r0 = "appInfo"
            java.lang.Object r0 = r11.get(r0)     // Catch:{ Throwable -> 0x0129 }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r1 = r7.getPackageName()     // Catch:{ Throwable -> 0x0129 }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Throwable -> 0x0129 }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r1 = r10.getProductTag()     // Catch:{ Throwable -> 0x0129 }
            java.lang.String r2 = r10.getProductAppkey()     // Catch:{ Throwable -> 0x0129 }
            r0.put(r1, r2)     // Catch:{ Throwable -> 0x0129 }
            java.io.File r0 = r8.b(r9)     // Catch:{ Throwable -> 0x010b, all -> 0x011f }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x010b, all -> 0x011f }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x010b, all -> 0x011f }
            java.io.ObjectOutputStream r1 = new java.io.ObjectOutputStream     // Catch:{ Throwable -> 0x010b, all -> 0x011f }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x010b, all -> 0x011f }
            r1.writeObject(r11)     // Catch:{ Throwable -> 0x0137 }
            if (r1 == 0) goto L_0x010a
            r1.flush()     // Catch:{ Throwable -> 0x0139 }
            r1.close()     // Catch:{ Throwable -> 0x0139 }
        L_0x010a:
            return
        L_0x010b:
            r0 = move-exception
            r1 = r6
        L_0x010d:
            com.mob.tools.log.NLog r2 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x0134 }
            r2.w(r0)     // Catch:{ all -> 0x0134 }
            if (r1 == 0) goto L_0x010a
            r1.flush()     // Catch:{ Throwable -> 0x011d }
            r1.close()     // Catch:{ Throwable -> 0x011d }
            goto L_0x010a
        L_0x011d:
            r0 = move-exception
            goto L_0x010a
        L_0x011f:
            r0 = move-exception
        L_0x0120:
            if (r6 == 0) goto L_0x0128
            r6.flush()     // Catch:{ Throwable -> 0x0132 }
            r6.close()     // Catch:{ Throwable -> 0x0132 }
        L_0x0128:
            throw r0     // Catch:{ Throwable -> 0x0129 }
        L_0x0129:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            goto L_0x010a
        L_0x0132:
            r1 = move-exception
            goto L_0x0128
        L_0x0134:
            r0 = move-exception
            r6 = r1
            goto L_0x0120
        L_0x0137:
            r0 = move-exception
            goto L_0x010d
        L_0x0139:
            r0 = move-exception
            goto L_0x010a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.a(android.content.Context, com.mob.commons.MobProduct, java.util.HashMap):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0041 A[SYNTHETIC, Splitter:B:22:0x0041] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x004d A[SYNTHETIC, Splitter:B:30:0x004d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String a(android.content.Context r5) {
        /*
            r4 = this;
            r1 = 0
            java.io.File r0 = r4.b(r5)     // Catch:{ Throwable -> 0x0051 }
            boolean r2 = r0.exists()     // Catch:{ Throwable -> 0x0051 }
            if (r2 == 0) goto L_0x0059
            boolean r2 = r0.isFile()     // Catch:{ Throwable -> 0x0051 }
            if (r2 == 0) goto L_0x0059
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0036, all -> 0x0049 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x0036, all -> 0x0049 }
            java.io.ObjectInputStream r2 = new java.io.ObjectInputStream     // Catch:{ Throwable -> 0x0036, all -> 0x0049 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0036, all -> 0x0049 }
            java.lang.Object r0 = r2.readObject()     // Catch:{ Throwable -> 0x0061 }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x0061 }
            if (r2 == 0) goto L_0x0026
            r2.close()     // Catch:{ Throwable -> 0x005b }
        L_0x0026:
            if (r0 != 0) goto L_0x002c
            java.util.HashMap r0 = r4.c(r5)
        L_0x002c:
            java.lang.String r1 = "duid"
            java.lang.Object r0 = r0.get(r1)
            java.lang.String r0 = (java.lang.String) r0
            return r0
        L_0x0036:
            r0 = move-exception
            r2 = r1
        L_0x0038:
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x005f }
            r3.w(r0)     // Catch:{ all -> 0x005f }
            if (r2 == 0) goto L_0x0059
            r2.close()     // Catch:{ Throwable -> 0x0046 }
            r0 = r1
            goto L_0x0026
        L_0x0046:
            r0 = move-exception
            r0 = r1
            goto L_0x0026
        L_0x0049:
            r0 = move-exception
            r2 = r1
        L_0x004b:
            if (r2 == 0) goto L_0x0050
            r2.close()     // Catch:{ Throwable -> 0x005d }
        L_0x0050:
            throw r0     // Catch:{ Throwable -> 0x0051 }
        L_0x0051:
            r0 = move-exception
            com.mob.tools.log.NLog r2 = com.mob.tools.MobLog.getInstance()
            r2.w(r0)
        L_0x0059:
            r0 = r1
            goto L_0x0026
        L_0x005b:
            r1 = move-exception
            goto L_0x0026
        L_0x005d:
            r2 = move-exception
            goto L_0x0050
        L_0x005f:
            r0 = move-exception
            goto L_0x004b
        L_0x0061:
            r0 = move-exception
            goto L_0x0038
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.a(android.content.Context):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0114 A[SYNTHETIC, Splitter:B:28:0x0114] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0120 A[SYNTHETIC, Splitter:B:33:0x0120] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.HashMap<java.lang.String, java.lang.Object> c(android.content.Context r9) {
        /*
            r8 = this;
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            r1 = 0
            com.mob.tools.utils.DeviceHelper r6 = com.mob.tools.utils.DeviceHelper.getInstance(r9)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r0 = r6.getModel()     // Catch:{ Throwable -> 0x010a }
            if (r0 != 0) goto L_0x0101
            java.lang.String r0 = ""
            r4 = r0
        L_0x0014:
            java.lang.String r0 = r6.getIMEI()     // Catch:{ Throwable -> 0x010a }
            if (r0 != 0) goto L_0x0104
            java.lang.String r0 = ""
            r3 = r0
        L_0x001e:
            java.lang.String r0 = r6.getMacAddress()     // Catch:{ Throwable -> 0x010a }
            if (r0 != 0) goto L_0x0107
            java.lang.String r0 = ""
            r2 = r0
        L_0x0028:
            java.lang.String r0 = r6.getSerialno()     // Catch:{ Throwable -> 0x010a }
            if (r0 != 0) goto L_0x0031
            java.lang.String r0 = ""
        L_0x0031:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x010a }
            r7.<init>()     // Catch:{ Throwable -> 0x010a }
            java.lang.StringBuilder r4 = r7.append(r4)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r7 = ":"
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ Throwable -> 0x010a }
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r4 = ":"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x010a }
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r3 = ":"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x010a }
            byte[] r0 = com.mob.tools.utils.Data.SHA1(r0)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "duid"
            java.lang.String r0 = com.mob.tools.utils.Data.byteToHex(r0)     // Catch:{ Throwable -> 0x010a }
            r5.put(r2, r0)     // Catch:{ Throwable -> 0x010a }
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Throwable -> 0x010a }
            r0.<init>()     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "adsid"
            java.lang.String r3 = r6.getAdvertisingID()     // Catch:{ Throwable -> 0x010a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "imei"
            java.lang.String r3 = r6.getIMEI()     // Catch:{ Throwable -> 0x010a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "serialno"
            java.lang.String r3 = r6.getSerialno()     // Catch:{ Throwable -> 0x010a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "androidid"
            java.lang.String r3 = r6.getAndroidID()     // Catch:{ Throwable -> 0x010a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "mac"
            java.lang.String r3 = r6.getMacAddress()     // Catch:{ Throwable -> 0x010a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "model"
            java.lang.String r3 = r6.getModel()     // Catch:{ Throwable -> 0x010a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "factory"
            java.lang.String r3 = r6.getManufacturer()     // Catch:{ Throwable -> 0x010a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "carrier"
            java.lang.String r3 = r6.getCarrier()     // Catch:{ Throwable -> 0x010a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "screensize"
            java.lang.String r3 = r6.getScreenSize()     // Catch:{ Throwable -> 0x010a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "sysver"
            java.lang.String r3 = r6.getOSVersionName()     // Catch:{ Throwable -> 0x010a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "plat"
            r3 = 1
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x010a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r2 = "deviceInfo"
            r5.put(r2, r0)     // Catch:{ Throwable -> 0x010a }
            java.io.File r0 = r8.b(r9)     // Catch:{ Throwable -> 0x010a }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x010a }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x010a }
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ Throwable -> 0x010a }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x010a }
            r2.writeObject(r5)     // Catch:{ Throwable -> 0x012c, all -> 0x0129 }
            if (r2 == 0) goto L_0x0100
            r2.flush()     // Catch:{ Throwable -> 0x012f }
            r2.close()     // Catch:{ Throwable -> 0x012f }
        L_0x0100:
            return r5
        L_0x0101:
            r4 = r0
            goto L_0x0014
        L_0x0104:
            r3 = r0
            goto L_0x001e
        L_0x0107:
            r2 = r0
            goto L_0x0028
        L_0x010a:
            r0 = move-exception
        L_0x010b:
            com.mob.tools.log.NLog r2 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x011d }
            r2.w(r0)     // Catch:{ all -> 0x011d }
            if (r1 == 0) goto L_0x0100
            r1.flush()     // Catch:{ Throwable -> 0x011b }
            r1.close()     // Catch:{ Throwable -> 0x011b }
            goto L_0x0100
        L_0x011b:
            r0 = move-exception
            goto L_0x0100
        L_0x011d:
            r0 = move-exception
        L_0x011e:
            if (r1 == 0) goto L_0x0126
            r1.flush()     // Catch:{ Throwable -> 0x0127 }
            r1.close()     // Catch:{ Throwable -> 0x0127 }
        L_0x0126:
            throw r0
        L_0x0127:
            r1 = move-exception
            goto L_0x0126
        L_0x0129:
            r0 = move-exception
            r1 = r2
            goto L_0x011e
        L_0x012c:
            r0 = move-exception
            r1 = r2
            goto L_0x010b
        L_0x012f:
            r0 = move-exception
            goto L_0x0100
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.c(android.content.Context):java.util.HashMap");
    }
}
