package com.hiflying.smartlink.v3;

import com.hiflying.commons.log.HFLog;
import com.hiflying.smartlink.AbstractSmartLinker;
import java.util.Arrays;

public class SnifferSmartLinker extends AbstractSmartLinker {

    private static class SnifferSmartLinkerInner {
        /* access modifiers changed from: private */
        public static final SnifferSmartLinker SNIFFER_SMART_LINKER = new SnifferSmartLinker(null);

        private SnifferSmartLinkerInner() {
        }
    }

    private SnifferSmartLinker() {
    }

    /* synthetic */ SnifferSmartLinker(SnifferSmartLinker snifferSmartLinker) {
        this();
    }

    public static SnifferSmartLinker getInstence() {
        return getInstance();
    }

    public static SnifferSmartLinker getInstance() {
        return SnifferSmartLinkerInner.SNIFFER_SMART_LINKER;
    }

    /* access modifiers changed from: protected */
    public Runnable[] setupSendAction(String password, String... ssid) throws Exception {
        HFLog.d((Object) this, String.format("setupSendAction: password-%s ssid-%s", new Object[]{password, Arrays.toString(ssid)}));
        Runnable[] runnableArr = new Runnable[1];
        runnableArr[0] = new SnifferSmartLinkerSendAction(this.mContext, this.mSmartConfigSocket, this, ssid.length > 0 ? ssid[0] : null, password);
        return runnableArr;
    }
}
