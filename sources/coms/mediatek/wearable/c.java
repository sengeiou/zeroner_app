package coms.mediatek.wearable;

import android.os.Message;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

class c {
    protected static final HashSet<Controller> a = new HashSet<>();
    private static c b;

    c() {
    }

    protected static c a() {
        if (b == null) {
            b = new c();
        }
        return b;
    }

    private void b(int i, byte[] bArr) {
        String str = new String(bArr);
        String[] split = str.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        Log.d("[wearable]ControllerManager", "handleReceiveData, cmdType=" + i + " command=" + str + ", dataBuffer=" + Arrays.toString(bArr));
        Iterator it = a.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Controller controller = (Controller) it.next();
            if (i == 1 && controller.getCmdType() == i) {
                Message obtain = Message.obtain(controller.mHandler, 9002);
                obtain.obj = bArr;
                obtain.sendToTarget();
                i2++;
            } else if ((i == 5 || i == 6) && (controller.getCmdType() == 5 || controller.getCmdType() == 6)) {
                Message obtain2 = Message.obtain(controller.mHandler, 9002);
                obtain2.obj = bArr;
                obtain2.sendToTarget();
                i2++;
            } else if (i == 8 && controller.getCmdType() == i) {
                HashSet receiverTags = controller.getReceiverTags();
                if ((receiverTags != null && receiverTags.size() > 0 && receiverTags.contains(split[0])) || receiverTags == null || receiverTags.size() == 0) {
                    Message obtain3 = Message.obtain(controller.mHandler, 9002);
                    obtain3.obj = bArr;
                    obtain3.sendToTarget();
                    i2++;
                }
            } else if (controller.getCmdType() == i) {
                HashSet receiverTags2 = controller.getReceiverTags();
                if ((receiverTags2 != null && receiverTags2.size() > 0 && receiverTags2.contains(split[1])) || receiverTags2 == null || receiverTags2.size() == 0) {
                    Message obtain4 = Message.obtain(controller.mHandler, 9002);
                    obtain4.obj = bArr;
                    obtain4.sendToTarget();
                    i2++;
                }
            }
            i2 = i2;
        }
        if (i2 == 0) {
            try {
                throw new Exception("didn't find controller for distributing received command. command=" + str);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(float f, String str) {
        if (str != null) {
            Iterator it = a.iterator();
            while (it.hasNext()) {
                Controller controller = (Controller) it.next();
                if (str.equals(controller.getControllerTag())) {
                    controller.onProgress(f);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(int i) {
        Iterator it = a.iterator();
        while (it.hasNext()) {
            ((Controller) it.next()).onConnectionStateChange(i);
        }
    }

    /* access modifiers changed from: protected */
    public void a(int i, byte[] bArr) {
        b(i, bArr);
    }
}
