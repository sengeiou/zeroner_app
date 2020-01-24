package coms.mediatek.ctrl.epo;

import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import coms.mediatek.wearable.Controller;
import coms.mediatek.wearable.WearableConfig;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;

public class EpoDownloadController extends Controller {
    private static EpoDownloadController a;
    private static HashSet<EpoDownloadChangeListener> h = new HashSet<>();
    /* access modifiers changed from: private */
    public String[] b;
    /* access modifiers changed from: private */
    public boolean c = false;
    /* access modifiers changed from: private */
    public long d;
    /* access modifiers changed from: private */
    public long e;
    /* access modifiers changed from: private */
    public int f;
    /* access modifiers changed from: private */
    public String g;

    private EpoDownloadController() {
        super("EpoDownloadController", 9);
        HashSet hashSet = new HashSet();
        hashSet.add("epo_update_data");
        super.setReceiverTags(hashSet);
        this.f = 0;
        this.d = 0;
        this.e = 0;
        this.g = WearableConfig.getEpoUrl();
        Log.d("EpoDownloadController", "[EpoDownloadController] URL: " + this.g);
    }

    /* access modifiers changed from: private */
    public String a(String str, String str2, String str3, int i, int i2) {
        return str + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str3 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + Integer.toString(i) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + Integer.toString(i2) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
    }

    /* access modifiers changed from: private */
    public void a(byte[] bArr) throws Exception {
        Log.d("EpoDownloadController", "[sendContent] send data content begin");
        int length = bArr.length / 512;
        for (int i = 0; i < length; i++) {
            byte[] bArr2 = new byte[512];
            System.arraycopy(bArr, i * 512, bArr2, 0, 512);
            send(a("epo_update_data", "epo_update_data", "1", 0, bArr2.length), bArr2, false, true, 0);
            this.d++;
        }
        int length2 = bArr.length % 512;
        if (length2 != 0) {
            byte[] bArr3 = new byte[length2];
            System.arraycopy(bArr, bArr.length - length2, bArr3, 0, length2);
            send(a("epo_update_data", "epo_update_data", "1", 0, bArr3.length), bArr3, false, true, 0);
            this.d++;
        }
        Log.d("EpoDownloadController", "[sendContent] send data content end");
    }

    public static void addListener(EpoDownloadChangeListener epoDownloadChangeListener) {
        h.add(epoDownloadChangeListener);
    }

    public static EpoDownloadController getInstance() {
        if (a != null) {
            return a;
        }
        a = new EpoDownloadController();
        return a;
    }

    public static HashSet<EpoDownloadChangeListener> getListeners() {
        return h;
    }

    public static void removeListener(EpoDownloadChangeListener epoDownloadChangeListener) {
        h.remove(epoDownloadChangeListener);
    }

    /* access modifiers changed from: protected */
    public void onConnectionStateChange(int i) {
        super.onConnectionStateChange(i);
        if (i == 5) {
            this.e = 0;
            this.d = 0;
        }
        Iterator it = getListeners().iterator();
        while (it.hasNext()) {
            ((EpoDownloadChangeListener) it.next()).notifyConnectionChanged(i);
        }
    }

    /* access modifiers changed from: protected */
    public void onProgress(float f2) {
        super.onProgress(f2);
        if (((int) (100.0f * f2)) == 100 && this.d != 0) {
            this.e++;
            Log.d("EpoDownloadController", "[onProgress] mTransferredCount : " + this.e);
            float f3 = ((float) this.e) / ((float) this.d);
            Log.d("EpoDownloadController", "[onProgress] pr : " + f3 + ", mTransferProgress : " + this.f);
            if (f3 != ((float) this.f)) {
                Iterator it = getListeners().iterator();
                while (it.hasNext()) {
                    ((EpoDownloadChangeListener) it.next()).notifyProgressChanged(f3);
                }
                if (this.e == this.d) {
                    this.d = 0;
                    this.e = 0;
                    this.f = 0;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onReceive(byte[] bArr) {
        super.onReceive(bArr);
        if (bArr == null) {
            Log.d("EpoDownloadController", "dataBuffer is null,return do nothing");
            return;
        }
        String str = new String(bArr);
        this.b = str.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        Log.d("EpoDownloadController", "onReceive, command=" + str + "command length=" + this.b.length);
        if (this.b.length == 5) {
            new Thread(new Runnable() {
                public void run() {
                    if (EpoDownloadController.this.b[0].equals("epo_update_data") && EpoDownloadController.this.b[1].equals("epo_update_data")) {
                        EpoDownloadController.this.c = Boolean.parseBoolean(EpoDownloadController.this.b[2]);
                        if (EpoDownloadController.this.b[4].equals("epo_download")) {
                            InputStream a2 = a.a(EpoDownloadController.this.g);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            byte[] bArr = new byte[100];
                            if (a2 != null) {
                                while (true) {
                                    try {
                                        int read = a2.read(bArr, 0, 100);
                                        if (read == -1) {
                                            break;
                                        }
                                        byteArrayOutputStream.write(bArr, 0, read);
                                    } catch (Exception e) {
                                        Log.d("EpoDownloadController", "[getInputStreamFromURL] IOException : " + e.getMessage());
                                        Iterator it = EpoDownloadController.getListeners().iterator();
                                        while (it.hasNext()) {
                                            ((EpoDownloadChangeListener) it.next()).notifyDownloadResult(0);
                                        }
                                        return;
                                    }
                                }
                                Iterator it2 = EpoDownloadController.getListeners().iterator();
                                while (it2.hasNext()) {
                                    ((EpoDownloadChangeListener) it2.next()).notifyDownloadResult(1);
                                }
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                try {
                                    EpoDownloadController.this.d = 0;
                                    EpoDownloadController.this.e = 0;
                                    EpoDownloadController.this.f = 0;
                                    long length = (long) (byteArray.length / 512);
                                    if (((long) (byteArray.length % 512)) != 0) {
                                        length++;
                                    }
                                    byte[] bytes = String.valueOf(length).getBytes();
                                    EpoDownloadController.this.send(EpoDownloadController.this.a("epo_update_data", "epo_update_data", "0", 0, bytes.length), bytes, false, false, 0);
                                    EpoDownloadController.this.a(byteArray);
                                    EpoDownloadController.this.send(EpoDownloadController.this.a("epo_update_data", "epo_update_data", "2", 0, 0), null, false, false, 0);
                                    String a3 = a.a();
                                    EpoDownloadController.this.send(EpoDownloadController.this.a("epo_update_md5", "epo_update_md5", "1", 0, a3.getBytes().length), a3.getBytes(), false, true, 0);
                                    EpoDownloadController.this.d = 1 + EpoDownloadController.this.d;
                                } catch (Exception e2) {
                                    ThrowableExtension.printStackTrace(e2);
                                }
                            } else {
                                Log.d("EpoDownloadController", "get Http File fail");
                                Iterator it3 = EpoDownloadController.getListeners().iterator();
                                while (it3.hasNext()) {
                                    ((EpoDownloadChangeListener) it3.next()).notifyDownloadResult(0);
                                }
                            }
                        }
                    }
                }
            }).start();
        } else {
            Log.d("EpoDownloadController", "mCommands length error!! so do nothing");
        }
    }
}
