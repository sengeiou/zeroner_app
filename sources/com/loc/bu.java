package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import java.lang.ref.WeakReference;

/* compiled from: MarkInfoManager */
public class bu {
    static WeakReference<bs> a;

    public static void a(final Context context) {
        aj.d().submit(new Runnable() {
            public final void run() {
                synchronized (bu.class) {
                    bs a2 = bz.a(bu.a);
                    bz.a(context, a2, ah.j, 50, ShareConstants.MD5_FILE_BUF_LENGTH, Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
                    if (a2.g == null) {
                        a2.g = new cd(new cc(context, new ch(), new z(new ad(new ab())), "WImFwcG5hbWUiOiIlcyIsInBrZyI6IiVzIiwiZGl1IjoiJXMi", l.b(context), l.c(context), bu.b(context)));
                    }
                    a2.h = 14400000;
                    if (TextUtils.isEmpty(a2.i)) {
                        a2.i = "eKey";
                    }
                    if (a2.f == null) {
                        a2.f = new cl(context, a2.h, a2.i, new ci(5, a2.a, new cn(context)));
                    }
                    bt.a(a2);
                }
            }
        });
    }

    static /* synthetic */ String b(Context context) {
        String v = p.v(context);
        if (!TextUtils.isEmpty(v)) {
            return v;
        }
        String h = p.h(context);
        if (!TextUtils.isEmpty(h)) {
            return h;
        }
        String m = p.m(context);
        return TextUtils.isEmpty(m) ? p.b(context) : m;
    }
}
