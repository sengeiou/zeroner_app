package cn.smssdk.contact;

import cn.smssdk.utils.SPHelper;
import com.mob.tools.FakeActivity;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class g extends FakeActivity {
    final /* synthetic */ d a;

    g(d dVar) {
        this.a = dVar;
    }

    public void onResult(HashMap<String, Object> hashMap) {
        if ("true".equals(String.valueOf(hashMap.get(ShareConstants.RES_PATH)))) {
            SPHelper.getInstance(this.a.c).setAllowReadContact();
            Iterator it = ((ArrayList) hashMap.get("okActions")).iterator();
            while (it.hasNext()) {
                Runnable runnable = (Runnable) it.next();
                if (runnable != null) {
                    runnable.run();
                }
            }
            return;
        }
        Iterator it2 = ((ArrayList) hashMap.get("cancelActions")).iterator();
        while (it2.hasNext()) {
            Runnable runnable2 = (Runnable) it2.next();
            if (runnable2 != null) {
                runnable2.run();
            }
        }
    }
}
