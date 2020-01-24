package com.tencent.mm.opensdk.channel.a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.d;

public final class a {

    /* renamed from: com.tencent.mm.opensdk.channel.a.a$a reason: collision with other inner class name */
    public static class C0006a {
        public String W;
        public long X;
        public String action;
        public Bundle bundle;
        public String content;
    }

    public static boolean a(Context context, C0006a aVar) {
        if (context == null) {
            Log.e("MicroMsg.SDK.MMessage", "send fail, invalid argument");
            return false;
        } else if (d.i(aVar.action)) {
            Log.e("MicroMsg.SDK.MMessage", "send fail, action is null");
            return false;
        } else {
            String str = null;
            if (!d.i(aVar.W)) {
                str = aVar.W + ".permission.MM_MESSAGE";
            }
            Intent intent = new Intent(aVar.action);
            if (aVar.bundle != null) {
                intent.putExtras(aVar.bundle);
            }
            String packageName = context.getPackageName();
            intent.putExtra(ConstantsAPI.SDK_VERSION, Build.SDK_INT);
            intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
            intent.putExtra(ConstantsAPI.CONTENT, aVar.content);
            intent.putExtra(ConstantsAPI.APP_SUPORT_CONTENT_TYPE, aVar.X);
            intent.putExtra(ConstantsAPI.CHECK_SUM, b.a(aVar.content, Build.SDK_INT, packageName));
            context.sendBroadcast(intent, str);
            Log.d("MicroMsg.SDK.MMessage", "send mm message, intent=" + intent + ", perm=" + str);
            return true;
        }
    }
}
