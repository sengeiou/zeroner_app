package coms.mediatek.ctrl.fota.common;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import coms.mediatek.wearable.Controller;
import java.util.HashSet;

class a extends Controller {
    private C0014a a;

    /* renamed from: coms.mediatek.ctrl.fota.common.a$a reason: collision with other inner class name */
    interface C0014a {
        void a(float f);

        void a(int i);

        void a(byte[] bArr);
    }

    a(HashSet<String> hashSet, C0014a aVar) {
        super("[FOTA_UPDATE][FotaController]", 9);
        super.setReceiverTags(hashSet);
        this.a = aVar;
    }

    public void a(String str, byte[] bArr, boolean z) {
        try {
            super.send(str, bArr, false, z, 0);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void init() {
        super.init();
    }

    public void onConnectionStateChange(int i) {
        super.onConnectionStateChange(i);
        this.a.a(i);
    }

    /* access modifiers changed from: protected */
    public void onProgress(float f) {
        this.a.a(f);
    }

    public void onReceive(byte[] bArr) {
        super.onReceive(bArr);
        if (getReceiverTags().contains(new String(bArr).split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)[1])) {
            this.a.a(bArr);
        }
    }

    public void send(String str, byte[] bArr, boolean z, boolean z2, int i) {
        try {
            super.send(str, bArr, z, z2, i);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void tearDown() {
        super.tearDown();
    }
}
