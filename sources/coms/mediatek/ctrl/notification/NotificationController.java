package coms.mediatek.ctrl.notification;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.CallLog.Calls;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import coms.mediatek.ctrl.notification.NotificationMessageBody.Action;
import coms.mediatek.ctrl.notification.NotificationMessageBody.Page;
import coms.mediatek.wearable.Controller;
import coms.mediatek.wearable.WearableManager;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;

public class NotificationController extends Controller {
    private static NotificationController a;
    private static NotificationEventListener c;
    private final Context b;

    private NotificationController(Context context) {
        super("NotificationController", 1);
        this.b = context;
        SmsContentObserver smsContentObserver = new SmsContentObserver(this.b);
        this.b.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), false, smsContentObserver);
        this.b.getContentResolver().registerContentObserver(Uri.parse("content://mms-sms/conversataions"), false, smsContentObserver);
    }

    private NotificationMessageBody a(NotificationData notificationData) {
        Log.d("App/Noti/Controller", "createNotificationBody(NotificationData notificationData)");
        ApplicationInfo a2 = g.a(this.b, (CharSequence) notificationData.getPackageName());
        String a3 = g.a(this.b, a2);
        Bitmap b2 = g.b(this.b, a2);
        int a4 = System.currentTimeMillis() - notificationData.getWhen() > 3600000 ? g.a(System.currentTimeMillis()) : g.a(notificationData.getWhen());
        String str = "";
        String str2 = "";
        for (int i = 0; i < notificationData.getTextList().length; i++) {
            if (i == 0) {
                str = notificationData.getTextList()[i];
            } else {
                str2 = str2 + notificationData.getTextList()[i] + "\n";
            }
        }
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        if (str.length() > 128) {
            str = str.substring(0, 128) + "...";
        }
        if (str2.length() > 256) {
            str2 = str2.substring(0, 256) + "...";
        }
        String str3 = "";
        if (notificationData.getTickerText() != null && str2.length() == 0) {
            str3 = notificationData.getTickerText();
        }
        if (str3.length() > 128) {
            str3 = str3.substring(0, 128) + "...";
        }
        if (str3.length() > 0) {
            str3 = "[".concat(str3).concat("]");
        }
        NotificationMessageBody notificationMessageBody = new NotificationMessageBody();
        notificationMessageBody.b(a3);
        notificationMessageBody.g(notificationData.getAppID());
        if (WearableManager.getInstance().getRemoteDeviceVersion() < 340 || notificationData.getTextList() == null || notificationData.getTextList().length <= 0) {
            Log.d("App/Noti/Controller", "createNotificationBody,add title and content");
            notificationMessageBody.e(str);
            notificationMessageBody.c(str2);
        } else {
            Log.d("App/Noti/Controller", "createNotificationBody,add page");
            int length = notificationData.getTextList().length / 2;
            for (int i2 = 0; i2 < length * 2; i2 += 2) {
                Page page = new Page(notificationData.getTextList()[i2], notificationData.getTextList()[i2 + 1]);
                notificationMessageBody.a(page);
                if (i2 == 0) {
                    notificationMessageBody.e(page.getTitle());
                    notificationMessageBody.c(page.getContent());
                }
            }
            Log.d("App/Noti/Controller", "createNotificationBody,page size is " + notificationMessageBody.e().size());
        }
        notificationMessageBody.f(str3);
        notificationMessageBody.b(a4);
        notificationMessageBody.a(b2);
        if (notificationData.getActionsList() == null || notificationData.getActionsList().size() <= 0) {
            Log.d("App/Noti/Controller", "createNotificationBody,add action fail");
        } else {
            Log.d("App/Noti/Controller", "createNotificationBody,add action");
            Iterator it = notificationData.getActionsList().iterator();
            while (it.hasNext()) {
                NotificationActions notificationActions = (NotificationActions) it.next();
                notificationMessageBody.a(new Action(notificationActions.getActionId(), notificationActions.getActionTitle()));
            }
            Log.d("App/Noti/Controller", "createNotificationBody,action size is " + notificationMessageBody.d().size());
        }
        if (!TextUtils.isEmpty(notificationData.getGroupKey())) {
            Log.d("App/Noti/Controller", "createNotificationBody,add groupkey");
            notificationMessageBody.a(notificationData.getGroupKey());
        } else {
            notificationMessageBody.a("");
        }
        return notificationMessageBody;
    }

    private NotificationMessageBody a(String str, CharSequence charSequence, CharSequence charSequence2, long j, String[] strArr) {
        ApplicationInfo a2 = g.a(this.b, charSequence);
        String a3 = g.a(this.b, a2);
        Bitmap b2 = g.b(this.b, a2);
        int a4 = System.currentTimeMillis() - j > 3600000 ? g.a(System.currentTimeMillis()) : g.a(j);
        String str2 = "";
        String str3 = "";
        for (int i = 0; i < strArr.length; i++) {
            if (i == 0) {
                str3 = strArr[i];
            } else {
                str2 = str2 + strArr[i] + "\n";
            }
        }
        if (TextUtils.isEmpty(str3)) {
            str3 = "";
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        if (str3.length() > 128) {
            str3 = str3.substring(0, 128) + "...";
        }
        if (str2.length() > 256) {
            str2 = str2.substring(0, 256) + "...";
        }
        String str4 = "";
        if (charSequence2 != null && str2.length() == 0) {
            str4 = charSequence2.toString();
        }
        if (str4.length() > 128) {
            str4 = str4.substring(0, 128) + "...";
        }
        if (str4.length() > 0) {
            str4 = "[".concat(str4).concat("]");
        }
        NotificationMessageBody notificationMessageBody = new NotificationMessageBody();
        notificationMessageBody.b(a3);
        notificationMessageBody.g(str);
        notificationMessageBody.e(str3);
        notificationMessageBody.c(str2);
        notificationMessageBody.f(str4);
        notificationMessageBody.b(a4);
        notificationMessageBody.a(b2);
        Log.d("App/Noti/Controller", "createNotificationBody(), body=" + notificationMessageBody.toString().substring(0, 20));
        return notificationMessageBody;
    }

    private NotificationMessageBody a(String str, String str2, String str3) {
        ApplicationInfo applicationInfo = this.b.getApplicationInfo();
        String a2 = g.a(this.b, applicationInfo);
        Bitmap b2 = g.b(this.b, applicationInfo);
        int a3 = g.a(System.currentTimeMillis());
        String str4 = "";
        NotificationMessageBody notificationMessageBody = new NotificationMessageBody();
        notificationMessageBody.g(str3);
        notificationMessageBody.b(a2);
        if (WearableManager.getInstance().getRemoteDeviceVersion() >= 340) {
            notificationMessageBody.a(new Page(str, str2));
        } else {
            notificationMessageBody.e(str);
            notificationMessageBody.c(str2);
        }
        notificationMessageBody.f(str4);
        notificationMessageBody.b(a3);
        notificationMessageBody.a(b2);
        Log.d("App/Noti/Controller", "createLowBatteryBody(), body=" + notificationMessageBody.toString().substring(0, 20));
        return notificationMessageBody;
    }

    private a a(String str, String str2, String str3, int i) {
        int a2 = g.a(System.currentTimeMillis());
        a aVar = new a();
        aVar.b(str2);
        aVar.a(str);
        aVar.c(str3);
        aVar.a(i);
        aVar.b(a2);
        Log.d("App/Noti/Controller", "createCallBody(), body=" + aVar);
        return aVar;
    }

    private c a() {
        c cVar = new c();
        cVar.a("notification");
        cVar.b("text");
        cVar.a(g.a());
        cVar.c("add");
        Log.d("App/Noti/Controller", "createNotificationHeader, header=" + cVar);
        return cVar;
    }

    private c a(int i, String str, String str2) {
        c cVar = new c();
        cVar.a("notification");
        cVar.b(str);
        cVar.a(i);
        cVar.c(str2);
        Log.d("App/Noti/Controller", "createNotificationHeader, header=" + cVar);
        return cVar;
    }

    private f a(String str, String str2) {
        String a2 = g.a(this.b, str);
        if (str2.length() > 256) {
            str2 = str2.substring(0, 256) + "...";
        }
        int a3 = g.a(System.currentTimeMillis());
        f fVar = new f();
        fVar.b(a2);
        fVar.a(str);
        fVar.c(str2);
        fVar.b(a3);
        Log.d("App/Noti/Controller", "createSmsBody(), body=" + fVar.toString());
        return fVar;
    }

    private void a(d dVar) {
        String j = ((NotificationMessageBody) dVar.b()).j();
        if (c != null) {
            c.notifyBlockListChanged(j);
        }
    }

    private void a(byte[] bArr) {
        try {
            super.send(String.valueOf(bArr.length), bArr, false, false, 0);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private c b() {
        c cVar = new c();
        cVar.a("notification");
        cVar.b("sms");
        cVar.a(g.a());
        cVar.c("add");
        Log.d("App/Noti/Controller", "createSmsHeader(), header=" + cVar);
        return cVar;
    }

    private void b(d dVar) {
        String valueOf = String.valueOf(dVar.a().c());
        if (c != null) {
            c.notifyNotificationDeleted(valueOf);
        }
    }

    private void b(byte[] bArr) throws IOException {
        d dVar = new d();
        new c();
        try {
            d a2 = dVar.a(bArr);
            c a3 = a2.a();
            String b2 = a3.b();
            if (a3 != null) {
                Log.d("App/Noti/Controller", "parseReadBuffer(),  mIncomingMessageHeader is " + a3.toString());
            }
            if (a2.b() != null) {
                Log.d("App/Noti/Controller", "parseReadBuffer(),  mIncomingMessage is " + a2.b().toString());
            }
            if (b2.equals("block_sender")) {
                a(a2);
            } else if (b2.equals("sms")) {
                d(a2);
            } else if (b2.equals("missed_call")) {
                d();
            } else if (b2.equals("notification_delete")) {
                if (WearableManager.getInstance().getRemoteDeviceVersion() < 340 || VERSION.SDK_INT < 18) {
                    Log.d("App/Noti/Controller", "Android platform is lower than android 4.3(API 18) and does not support sync delete notification opeartion.");
                } else {
                    b(a2);
                }
            } else if (!b2.equals("action_operate")) {
            } else {
                if (WearableManager.getInstance().getRemoteDeviceVersion() < 340 || VERSION.SDK_INT < 18) {
                    Log.d("App/Noti/Controller", "Android platform is lower than android 4.3(API 18) and does not support handle notification opeartion.");
                } else {
                    c(a2);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private c c() {
        c cVar = new c();
        cVar.a(NotificationCompat.CATEGORY_CALL);
        cVar.b("missed_call");
        cVar.a(g.a());
        cVar.c("add");
        Log.d("App/Noti/Controller", "createCallHeader(), header=" + cVar);
        return cVar;
    }

    private void c(d dVar) {
        c a2 = dVar.a();
        String str = "";
        if (a2 != null) {
            String valueOf = String.valueOf(a2.c());
            NotificationMessageBody notificationMessageBody = (NotificationMessageBody) dVar.b();
            if (notificationMessageBody != null) {
                String str2 = (notificationMessageBody.d() == null || notificationMessageBody.d().size() == 0) ? "" : ((Action) notificationMessageBody.d().get(0)).getId();
                if (c != null) {
                    c.notifyNotificationActionOperate(valueOf, str2);
                    return;
                }
                return;
            }
            Log.d("App/Noti/Controller", "handleNotificaitonAction,notiBody is null");
            return;
        }
        Log.d("App/Noti/Controller", "handleNotificaitonAction,header is null");
    }

    private void d() {
        Log.d("App/Noti/Controller", "updateMissedCallCountToZero()");
        ContentValues contentValues = new ContentValues();
        contentValues.put("new", Integer.valueOf(0));
        if (VERSION.SDK_INT >= 14) {
            contentValues.put("is_read", Integer.valueOf(1));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("new");
        sb.append(" = 1 AND ");
        sb.append("type");
        sb.append(" = ?");
        this.b.getContentResolver().update(Calls.CONTENT_URI, contentValues, sb.toString(), new String[]{Integer.toString(3)});
    }

    private void d(d dVar) {
        Log.d("App/Noti/Controller", "sendSMS(),  notiMessageId=" + dVar.a().c());
        String c2 = dVar.b().c();
        if (c2 == null) {
            c2 = "\n";
        }
        if (c2.equals("")) {
            String str = "\n";
        }
    }

    public static int genMessageId() {
        Log.d("App/Noti/Controller", "genMessageId(), messageId=" + g.a());
        return g.a();
    }

    public static NotificationController getInstance(Context context) {
        if (a != null) {
            return a;
        }
        a = new NotificationController(context);
        return a;
    }

    public static NotificationEventListener getListener() {
        return c;
    }

    public static void setListener(NotificationEventListener notificationEventListener) {
        c = notificationEventListener;
    }

    /* access modifiers changed from: protected */
    public void onConnectionStateChange(int i) {
        super.onConnectionStateChange(i);
        if (i == 5 && c != null) {
            c.clearAllNotificationData();
        }
    }

    /* access modifiers changed from: protected */
    public void onReceive(byte[] bArr) {
        Log.d("App/Noti/Controller", "onReceive(), command :" + new String(bArr));
        super.onReceive(bArr);
        try {
            b(bArr);
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void sendActionOperateResult(int i, boolean z) {
        try {
            Log.d("App/Noti/Controller", "sendOpenErrorNotfications " + i);
            d dVar = new d();
            dVar.a(a(i, "action_operate_result", "update"));
            NotificationMessageBody notificationMessageBody = new NotificationMessageBody();
            if (z) {
                notificationMessageBody.d("true");
            } else {
                notificationMessageBody.d("false");
            }
            dVar.a((b) notificationMessageBody);
            a(dVar.c());
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            Log.e("Exception during write ", e.getMessage());
        }
    }

    public void sendCallMessage(String str, String str2, String str3, int i) {
        d dVar = new d();
        dVar.a(c());
        dVar.a((b) a(str, str2, str3, i));
        getInstance(this.b).a(dVar.c());
    }

    public void sendDelNotfications(int i) {
        try {
            Log.d("App/Noti/Controller", "sendDelNotfications " + i);
            d dVar = new d();
            dVar.a(a(i, "notification_delete", "delete"));
            dVar.a((b) null);
            a(dVar.c());
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            Log.e("Exception during write ", e.getMessage());
        }
    }

    public void sendLowBatteryMessage(String str, String str2, String str3, String str4) {
        String str5 = str2 + ":" + str4 + "%";
        d dVar = new d();
        dVar.a(a());
        dVar.a((b) a(str, str5, str3));
        Log.d("App/Noti/Controller", "sendLowBatteryMessage(), lowBatteryData=" + dVar);
        a(dVar.c());
    }

    public void sendNotfications(NotificationData notificationData) {
        try {
            Log.d("App/Noti/Controller", "sendNotifiMessage(notificationData)");
            d dVar = new d();
            dVar.a(a(notificationData.getMsgId(), "text", "add"));
            NotificationMessageBody a2 = a(notificationData);
            dVar.a((b) a2);
            String str = "";
            if (TextUtils.isEmpty((WearableManager.getInstance().getRemoteDeviceVersion() < 340 || a2.e() == null || a2.e().size() <= 0) ? dVar.b().c() : ((Page) a2.e().get(0)).getContent())) {
                Log.e("App/Noti/Controller", "content is empty, return.");
                return;
            }
            byte[] c2 = dVar.c();
            if (c2 != null) {
                a(c2);
            } else {
                Log.e("App/Noti/Controller", "sendNotfications(),data is null and return.");
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            Log.e("Exception during write ", e.getMessage());
        }
    }

    public void sendNotfications(String str, CharSequence charSequence, CharSequence charSequence2, long j, String[] strArr) {
        try {
            Log.d("App/Noti/Controller", "sendNotifiMessage()");
            d dVar = new d();
            dVar.a(a());
            dVar.a((b) a(str, charSequence, charSequence2, j, strArr));
            if (TextUtils.isEmpty(dVar.b().c())) {
                Log.e("App/Noti/Controller", "content is empty, return.");
            } else {
                a(dVar.c());
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            Log.w("Exception during write", e);
        }
    }

    public void sendReadMissedCallData() {
        String str = "";
        String str2 = "";
        String str3 = "";
        int a2 = g.a(Calendar.getInstance().getTimeInMillis());
        a aVar = new a();
        aVar.b(str2);
        aVar.a(str);
        aVar.c(str3);
        aVar.a(0);
        aVar.b(a2);
        Log.d("App/Noti/Controller", "sendReadMissedCallData() sender:phoneNum:content" + str2 + str + str3);
        d dVar = new d();
        dVar.a(c());
        dVar.a((b) aVar);
        getInstance(this.b).a(dVar.c());
    }

    public void sendSmsMessage(String str, String str2) {
        Log.d("App/Noti/Controller", "sendSmsMessage(), body=" + str + " address=" + str2);
        d dVar = new d();
        dVar.a(b());
        dVar.a((b) a(str2, str));
        getInstance(this.b).a(dVar.c());
    }
}
