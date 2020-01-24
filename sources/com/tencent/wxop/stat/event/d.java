package com.tencent.wxop.stat.event;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.wxop.stat.StatConfig;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.b;
import com.tencent.wxop.stat.common.r;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.json.JSONObject;

public class d extends e {
    private String a;
    private int m;
    private int n = 100;
    private Thread o = null;

    public d(Context context, int i, int i2, Throwable th, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        a(i2, th);
    }

    public d(Context context, int i, int i2, Throwable th, Thread thread, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        a(i2, th);
        this.o = thread;
    }

    public d(Context context, int i, String str, int i2, int i3, Thread thread, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        if (str != null) {
            if (i3 <= 0) {
                i3 = StatConfig.getMaxReportEventLength();
            }
            if (str.length() <= i3) {
                this.a = str;
            } else {
                this.a = str.substring(0, i3);
            }
        }
        this.o = thread;
        this.m = i2;
    }

    private void a(int i, Throwable th) {
        if (th != null) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            ThrowableExtension.printStackTrace(th, printWriter);
            this.a = stringWriter.toString();
            this.m = i;
            printWriter.close();
        }
    }

    public EventType a() {
        return EventType.ERROR;
    }

    public boolean a(JSONObject jSONObject) {
        r.a(jSONObject, "er", this.a);
        jSONObject.put("ea", this.m);
        if (this.m == 2 || this.m == 3) {
            new b(this.l).a(jSONObject, this.o);
        }
        return true;
    }
}
