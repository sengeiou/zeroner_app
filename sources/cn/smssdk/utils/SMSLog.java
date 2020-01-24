package cn.smssdk.utils;

import android.content.Context;
import com.mob.tools.log.NLog;

public class SMSLog extends NLog {
    private SMSLog(Context context, int i, String str) {
        setCollector("SMSSDK", new b(this, context, i, str));
    }

    /* access modifiers changed from: protected */
    public String getSDKTag() {
        return "SMSSDK";
    }

    public static NLog prepare(Context context, int i, String str) {
        return new SMSLog(context, i, str);
    }

    public static NLog getInstance() {
        return getInstanceForSDK("SMSSDK", true);
    }
}
