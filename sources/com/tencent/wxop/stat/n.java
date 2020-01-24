package com.tencent.wxop.stat;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.wxop.stat.common.l;

final class n implements Runnable {
    final /* synthetic */ Context a;

    n(Context context) {
        this.a = context;
    }

    public final void run() {
        if (this.a == null) {
            StatServiceImpl.q.error((Object) "The Context of StatService.onStop() can not be null!");
            return;
        }
        StatServiceImpl.flushDataToDB(this.a);
        if (!StatServiceImpl.a()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                ThrowableExtension.printStackTrace(e);
            }
            if (l.z(this.a)) {
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.q.i("onStop isBackgroundRunning flushDataToDB");
                }
                StatServiceImpl.commitEvents(this.a, -1);
            }
        }
    }
}
