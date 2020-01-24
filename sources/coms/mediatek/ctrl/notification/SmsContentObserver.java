package coms.mediatek.ctrl.notification;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;
import java.util.HashMap;
import java.util.Map.Entry;

class SmsContentObserver extends ContentObserver {
    /* access modifiers changed from: private */
    public final Context a;
    /* access modifiers changed from: private */
    public HashMap<Long, Integer> b;
    private final String c = "telecom/msg/";

    public class DatabaseMonitor extends Thread {
        public static final int MONITER_TYPE_ONLY_QUERY = 0;
        public static final int MONITER_TYPE_QUERY_AND_NOTIFY = 1;
        private int b = 0;

        public DatabaseMonitor(int i) {
            this.b = i;
        }

        private String a(int i) {
            switch (i) {
                case 1:
                    return "inbox";
                case 2:
                    return "sent";
                case 3:
                    return "draft";
                case 4:
                    return "outbox";
                default:
                    return "deleted";
            }
        }

        private synchronized void a() {
            a(SmsContentObserver.this.b);
            Log.d("MessageObserver", "query: size->" + SmsContentObserver.this.b.size());
        }

        /* JADX WARNING: Removed duplicated region for block: B:18:0x005b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void a(java.util.HashMap<java.lang.Long, java.lang.Integer> r8) {
            /*
                r7 = this;
                r6 = 0
                coms.mediatek.ctrl.notification.SmsContentObserver r0 = coms.mediatek.ctrl.notification.SmsContentObserver.this     // Catch:{ Exception -> 0x0063, all -> 0x0058 }
                android.content.Context r0 = r0.a     // Catch:{ Exception -> 0x0063, all -> 0x0058 }
                android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ Exception -> 0x0063, all -> 0x0058 }
                java.lang.String r1 = "content://sms/"
                android.net.Uri r1 = android.net.Uri.parse(r1)     // Catch:{ Exception -> 0x0063, all -> 0x0058 }
                r2 = 2
                java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0063, all -> 0x0058 }
                r3 = 0
                java.lang.String r4 = "_id"
                r2[r3] = r4     // Catch:{ Exception -> 0x0063, all -> 0x0058 }
                r3 = 1
                java.lang.String r4 = "type"
                r2[r3] = r4     // Catch:{ Exception -> 0x0063, all -> 0x0058 }
                r3 = 0
                r4 = 0
                r5 = 0
                android.database.Cursor r0 = r0.query(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0063, all -> 0x0058 }
                if (r0 == 0) goto L_0x0052
            L_0x002a:
                boolean r1 = r0.moveToNext()     // Catch:{ Exception -> 0x0046 }
                if (r1 == 0) goto L_0x0052
                r1 = 0
                long r2 = r0.getLong(r1)     // Catch:{ Exception -> 0x0046 }
                java.lang.Long r1 = java.lang.Long.valueOf(r2)     // Catch:{ Exception -> 0x0046 }
                r2 = 1
                int r2 = r0.getInt(r2)     // Catch:{ Exception -> 0x0046 }
                java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x0046 }
                r8.put(r1, r2)     // Catch:{ Exception -> 0x0046 }
                goto L_0x002a
            L_0x0046:
                r1 = move-exception
            L_0x0047:
                if (r0 == 0) goto L_0x004c
                r0.close()     // Catch:{ all -> 0x005f }
            L_0x004c:
                if (r0 == 0) goto L_0x0051
                r0.close()
            L_0x0051:
                return
            L_0x0052:
                if (r0 == 0) goto L_0x0051
                r0.close()
                goto L_0x0051
            L_0x0058:
                r0 = move-exception
            L_0x0059:
                if (r6 == 0) goto L_0x005e
                r6.close()
            L_0x005e:
                throw r0
            L_0x005f:
                r1 = move-exception
                r6 = r0
                r0 = r1
                goto L_0x0059
            L_0x0063:
                r0 = move-exception
                r0 = r6
                goto L_0x0047
            */
            throw new UnsupportedOperationException("Method not decompiled: coms.mediatek.ctrl.notification.SmsContentObserver.DatabaseMonitor.a(java.util.HashMap):void");
        }

        private synchronized void b() {
            HashMap hashMap = new HashMap();
            a(hashMap);
            Log.d("MessageObserver", "database has been changed, mType is  previous size is " + SmsContentObserver.this.b.size() + "current size is " + hashMap.size());
            if (SmsContentObserver.this.b.size() < hashMap.size()) {
                for (Entry key : hashMap.entrySet()) {
                    Long l = (Long) key.getKey();
                    String a2 = a(((Integer) hashMap.get(l)).intValue());
                    if (!SmsContentObserver.this.b.containsKey(l) && a2 != null && a2.equals("inbox")) {
                        Intent intent = new Intent();
                        intent.setAction("com.mtk.btnotification.SMS_RECEIVED");
                        SmsContentObserver.this.a.sendBroadcast(intent);
                    }
                }
            } else {
                for (Entry entry : SmsContentObserver.this.b.entrySet()) {
                    Long l2 = (Long) entry.getKey();
                    if (!hashMap.containsKey(l2)) {
                        try {
                            a(((Integer) SmsContentObserver.this.b.get(l2)).intValue());
                        } catch (Exception e) {
                            String exc = e.toString();
                            if (exc == null) {
                                exc = "querry error";
                            }
                            Log.w("MessageObserver", exc);
                        }
                    } else {
                        String a3 = a(((Integer) entry.getValue()).intValue());
                        String a4 = a(((Integer) hashMap.get(l2)).intValue());
                        if (a4 != null && a3 != null && !a3.equals(a4) && a4.equals("deleted")) {
                        }
                    }
                }
            }
            SmsContentObserver.this.b = hashMap;
        }

        public void run() {
            if (this.b == 0) {
                a();
            } else if (1 == this.b) {
                b();
            } else {
                Log.d("MessageObserver", "invalid monitor type:" + this.b);
            }
        }
    }

    public SmsContentObserver(Context context) {
        super(new Handler());
        this.a = context;
        this.b = new HashMap<>();
        new DatabaseMonitor(0).start();
    }

    public void onChange(boolean z) {
        super.onChange(z);
        Log.d("MessageObserver", "DataBase State Changed");
        new DatabaseMonitor(1).start();
    }
}
