package com.tencent.stat.a;

import android.content.Context;
import com.tencent.stat.common.k;
import org.json.JSONObject;

public class j extends e {
    Long a = null;
    String l;
    String m;

    public j(Context context, String str, String str2, int i, Long l2) {
        super(context, i);
        this.m = str;
        this.l = str2;
        this.a = l2;
    }

    public f a() {
        return f.PAGE_VIEW;
    }

    public boolean a(JSONObject jSONObject) {
        k.a(jSONObject, "pi", this.l);
        k.a(jSONObject, "rf", this.m);
        if (this.a != null) {
            jSONObject.put("du", this.a);
        }
        return true;
    }
}
