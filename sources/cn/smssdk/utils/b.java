package cn.smssdk.utils;

import android.content.Context;
import com.mob.commons.logcollector.LogsCollector;

class b extends LogsCollector {
    final /* synthetic */ int a;
    final /* synthetic */ String b;
    final /* synthetic */ SMSLog c;

    b(SMSLog sMSLog, Context context, int i, String str) {
        this.c = sMSLog;
        this.a = i;
        this.b = str;
        super(context);
    }

    /* access modifiers changed from: protected */
    public int getSDKVersion() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public String getSDKTag() {
        return "SMSSDK";
    }

    /* access modifiers changed from: protected */
    public String getAppkey() {
        return this.b;
    }
}
