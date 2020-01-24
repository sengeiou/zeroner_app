package com.hiflying.smartlink.v7;

import com.hiflying.commons.log.HFLog;
import com.hiflying.smartlink.AbstractSmartLinker;
import com.hiflying.smartlink.v3.SnifferSmartLinkerSendAction;
import java.util.ArrayList;

public class MulticastSmartLinker extends AbstractSmartLinker {
    private boolean mMixSmartLink3 = true;

    private static final class MulticastSmartLinkerInner {
        /* access modifiers changed from: private */
        public static final MulticastSmartLinker MULTICAST_SMART_LINKER = new MulticastSmartLinker();

        private MulticastSmartLinkerInner() {
        }
    }

    public static MulticastSmartLinker getInstance() {
        return MulticastSmartLinkerInner.MULTICAST_SMART_LINKER;
    }

    @Deprecated
    public void setMaxRetryTimes(int maxRetryTimes) {
    }

    /* access modifiers changed from: protected */
    public Runnable[] setupSendAction(String password, String... ssid) throws Exception {
        if (ssid.length < 0) {
            throw new RuntimeException("It must set a ssid in setupSendAction");
        }
        ArrayList<Runnable> runnables = new ArrayList<>();
        runnables.add(new MulticastSmartLinkerSendAction(this.mContext, this, ssid[0], password));
        if (this.mMixSmartLink3) {
            HFLog.d((Object) this, "Mixed with smartlink3!");
            runnables.add(new SnifferSmartLinkerSendAction(this.mContext, this.mSmartConfigSocket, this, ssid[0], password));
        }
        return (Runnable[]) runnables.toArray(new Runnable[runnables.size()]);
    }

    public void setMixSmartLink3(boolean enabled) {
        this.mMixSmartLink3 = enabled;
    }

    public boolean isMixSmartLink3() {
        return this.mMixSmartLink3;
    }
}
