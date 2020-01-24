package coms.mediatek.ctrl.fota.common;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.device_firmware_upgrade.FotaUtils;
import coms.mediatek.wearable.WearableManager;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class FotaOperator {
    public static final int TYPE_FIRMWARE_FULL_BIN = 5;
    public static final int TYPE_FIRMWARE_GNSS_FOTA = 6;
    private static final byte[] a = "BTpush".getBytes();
    private static FotaOperator b;
    private a c;
    /* access modifiers changed from: private */
    public CopyOnWriteArrayList<IFotaOperatorCallback> d = new CopyOnWriteArrayList<>();
    /* access modifiers changed from: private */
    public long e;
    /* access modifiers changed from: private */
    public long f;
    private int g;
    private Context h;
    private boolean i = false;
    private C0014a j = new C0014a() {
        public void a(float f) {
            Log.d("[FOTA_UPDATE][FotaOperator]", "[onProgress] " + f);
            FotaOperator.this.a(f);
        }

        public void a(int i) {
            if (i == 5) {
                FotaOperator.this.f = 0;
                FotaOperator.this.e = 0;
            }
            Iterator it = FotaOperator.this.d.iterator();
            while (it.hasNext()) {
                ((IFotaOperatorCallback) it.next()).onConnectionStateChange(i);
            }
        }

        public void a(byte[] bArr) {
            if (bArr == null || bArr.length == 0) {
                Log.e("[FOTA_UPDATE][FotaOperator]", "[onReceived] bytes is WRONG");
                return;
            }
            String str = new String(bArr);
            Log.d("[FOTA_UPDATE][FotaOperator]", "[onReceived] received string : " + str);
            String[] split = str.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            if (split == null || split.length < 2) {
                Log.e("[FOTA_UPDATE][FotaOperator]", "[onReceived] strs is wrong");
            } else if ("fota_bt_ver".equals(split[1])) {
                Log.d("[FOTA_UPDATE][FotaOperator]", "[onReceived] version received");
                FotaOperator.this.a(str);
            } else if ("fota_cust_cmd".equals(split[1])) {
                Log.d("[FOTA_UPDATE][FotaOperator]", "[onReceived] customer information received");
                FotaOperator.this.b(str);
            } else {
                Log.d("[FOTA_UPDATE][FotaOperator]", "[onReceived] other information received");
                FotaOperator.this.c(str);
            }
        }
    };

    private FotaOperator(Context context) {
        HashSet hashSet = new HashSet();
        hashSet.add("fota_bt_ver");
        hashSet.add("fota_fbin");
        hashSet.add("fota_cust_cmd");
        this.h = context;
        this.g = 0;
        this.e = 0;
        this.f = 0;
        this.c = new a(hashSet, this.j);
        WearableManager.getInstance().addController(this.c);
    }

    private String a(String str, String str2, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(str + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        sb.append(str2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        sb.append(i2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        sb.append("0 ");
        sb.append(i3 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        Log.d("[FOTA_UPDATE][FotaOperator]", "[buildSendData] send cmd : " + sb.toString());
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public void a(float f2) {
        if (((double) f2) == 1.0d && this.e != 0) {
            this.f++;
            Log.d("[FOTA_UPDATE][FotaOperator]", "[handleProgressChange] mTransferredCount : " + this.f);
            int i2 = (int) ((this.f * 100) / this.e);
            Log.d("[FOTA_UPDATE][FotaOperator]", "[handleProgressChange] pr : " + i2 + ", mTransferProgress : " + this.g);
            if (i2 == this.g) {
                Log.d("[FOTA_UPDATE][FotaOperator]", "[handleProgressChange] Progress Return");
                return;
            }
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                IFotaOperatorCallback iFotaOperatorCallback = (IFotaOperatorCallback) it.next();
                Log.d("[FOTA_UPDATE][FotaOperator]", "[handleProgressChange] Callback : " + iFotaOperatorCallback);
                iFotaOperatorCallback.onProgress(i2);
            }
            if (this.f == this.e) {
                this.e = 0;
                this.f = 0;
                this.g = 0;
            }
        }
    }

    private void a(int i2) {
        String str;
        String str2;
        Log.d("[FOTA_UPDATE][FotaOperator]", "[sendDataTransEndCommand] which : " + i2);
        switch (i2) {
            case 5:
                str = "fota_fbin";
                str2 = "fota_fbin";
                break;
            case 6:
                str = "gnss_update";
                str2 = "gnss_update";
                break;
            default:
                Log.e("[FOTA_UPDATE][FotaOperator]", "[sendDataTransEndCommand] unknow type");
                return;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[sendDataTransEndCommand] sender or receiver is WRONG");
            return;
        }
        Log.d("[FOTA_UPDATE][FotaOperator]", "[sendDataTransEndCommand] send the data tranfer end command");
        String a2 = a(str, str2, 2, a.length);
        this.c.a(a2, a, false);
        Log.d("[FOTA_UPDATE][FotaOperator]", "[sendBeginCommand] End command data length " + (a2.length() + a.length));
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[handleReceivedVersion] string is WRONG");
            return;
        }
        Log.d("[FOTA_UPDATE][FotaOperator]", "[handleReceivedVersion] string : " + str);
        String[] split = str.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        if (split != null && split.length >= 5) {
            if (!"fota_bt_ver".equals(split[1])) {
                Log.e("[FOTA_UPDATE][FotaOperator]", "[handleReceivedVersion] NOT version receiver");
                return;
            }
            String substring = str.substring(str.length() - Integer.valueOf(split[3]).intValue(), str.length());
            if (TextUtils.isEmpty(substring)) {
                Log.e("[FOTA_UPDATE][FotaOperator]", "[handleReceivedVersion] versionString is WRONG");
                Iterator it = this.d.iterator();
                while (it.hasNext()) {
                    ((IFotaOperatorCallback) it.next()).onFotaVersionReceived(null);
                }
            } else if (FotaUtils.FOTA_VERSION_GET_FAILED.equals(substring)) {
                Log.e("[FOTA_UPDATE][FotaOperator]", "[handleReceivedVersion] versionString is get version failed");
                Iterator it2 = this.d.iterator();
                while (it2.hasNext()) {
                    ((IFotaOperatorCallback) it2.next()).onFotaVersionReceived(null);
                }
            } else {
                String[] split2 = substring.split(";");
                if (split2 == null || split2.length == 0) {
                    Log.e("[FOTA_UPDATE][FotaOperator]", "[handleReceivedVersion] versionString is NOT able to split with ;");
                    Iterator it3 = this.d.iterator();
                    while (it3.hasNext()) {
                        ((IFotaOperatorCallback) it3.next()).onFotaVersionReceived(null);
                    }
                    return;
                }
                FotaVersion fotaVersion = new FotaVersion();
                for (String d2 : split2) {
                    ArrayList d3 = d(d2);
                    if (d3.size() == 1) {
                        Log.d("[FOTA_UPDATE][FotaOperator]", "[handleReceivedVersion] keyValue size is 1");
                    } else {
                        if (((String) d3.get(0)).equals("verno")) {
                            fotaVersion.mVersionString = (String) d3.get(1);
                        }
                        if (((String) d3.get(0)).equals("releaseDate")) {
                            fotaVersion.mReleaseDateString = (String) d3.get(1);
                        }
                        if (((String) d3.get(0)).equals("platform")) {
                            fotaVersion.mPlatformString = (String) d3.get(1);
                        }
                        if (((String) d3.get(0)).equals("model")) {
                            fotaVersion.mModuleString = (String) d3.get(1);
                        }
                        if (((String) d3.get(0)).equals("dev_id")) {
                            fotaVersion.mDeviceIdString = (String) d3.get(1);
                        }
                        if (((String) d3.get(0)).equals("battery") && ((String) d3.get(1)).equals("low")) {
                            fotaVersion.mIsFeaturePhoneLowPower = true;
                        }
                        if (((String) d3.get(0)).equals("brand")) {
                            fotaVersion.mBrandString = (String) d3.get(1);
                        }
                        if (((String) d3.get(0)).equals("domain")) {
                            fotaVersion.mDomainString = (String) d3.get(1);
                        }
                        if (((String) d3.get(0)).equals("dl_key")) {
                            fotaVersion.mDownloadKeyString = (String) d3.get(1);
                        }
                        if (((String) d3.get(0)).equals("pin_code")) {
                            fotaVersion.mPinCodeString = (String) d3.get(1);
                        }
                    }
                }
                Log.d("[FOTA_UPDATE][FotaOperator]", "[handleReceivedVersion] mFotaCallbacks size : " + this.d.size());
                Iterator it4 = this.d.iterator();
                while (it4.hasNext()) {
                    ((IFotaOperatorCallback) it4.next()).onFotaVersionReceived(fotaVersion);
                }
            }
        }
    }

    private boolean a(int i2, Uri uri) throws IllegalArgumentException {
        if (i2 != 5 && i2 != 6) {
            throw new IllegalArgumentException("unknow fota type");
        } else if (uri == null) {
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                ((IFotaOperatorCallback) it.next()).onStatusReceived(-100);
            }
            return false;
        } else {
            try {
                return a(i2, this.h.getContentResolver().openInputStream(uri));
            } catch (FileNotFoundException e2) {
                ThrowableExtension.printStackTrace(e2);
                Iterator it2 = this.d.iterator();
                while (it2.hasNext()) {
                    ((IFotaOperatorCallback) it2.next()).onStatusReceived(-100);
                }
                return false;
            }
        }
    }

    private boolean a(int i2, InputStream inputStream) throws IllegalArgumentException {
        String str;
        String str2;
        if (inputStream == null) {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[sendBeginCommand] ins is null");
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                ((IFotaOperatorCallback) it.next()).onStatusReceived(-100);
            }
            return false;
        }
        if (this.e != 0) {
            this.e = 0;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } catch (IOException e2) {
                ThrowableExtension.printStackTrace(e2);
                Iterator it2 = this.d.iterator();
                while (it2.hasNext()) {
                    ((IFotaOperatorCallback) it2.next()).onStatusReceived(FotaUtils.READ_FILE_FAILED);
                }
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e3) {
                        ThrowableExtension.printStackTrace(e3);
                        return false;
                    }
                }
                inputStream.close();
            } catch (Exception e4) {
                ThrowableExtension.printStackTrace(e4);
                Iterator it3 = this.d.iterator();
                while (it3.hasNext()) {
                    ((IFotaOperatorCallback) it3.next()).onStatusReceived(FotaUtils.READ_FILE_FAILED);
                }
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e5) {
                        ThrowableExtension.printStackTrace(e5);
                        return false;
                    }
                }
                inputStream.close();
            } catch (Throwable th) {
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e6) {
                        ThrowableExtension.printStackTrace(e6);
                        throw th;
                    }
                }
                inputStream.close();
                throw th;
            }
        }
        byteArrayOutputStream.flush();
        long size = (long) (byteArrayOutputStream.size() % 1900);
        long size2 = (long) (byteArrayOutputStream.size() / 1900);
        if (size != 0) {
            size2++;
        }
        Log.d("[FOTA_UPDATE][FotaOperator]", "[sendBeginCommand] cu : " + size);
        this.e = size2;
        Log.d("[FOTA_UPDATE][FotaOperator]", "[sendBeginCommand] mMaxTransferCount : " + this.e);
        byte[] bytes = String.valueOf(size2).getBytes();
        switch (i2) {
            case 5:
                str = "fota_fbin";
                str2 = "fota_fbin";
                break;
            case 6:
                str = "gnss_update";
                str2 = "gnss_update";
                break;
            default:
                Log.e("[FOTA_UPDATE][FotaOperator]", "[sendBeginCommand] unknow type");
                throw new IllegalArgumentException("unknow type");
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[sendBeginCommand] sender or receiver is null");
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e7) {
                    ThrowableExtension.printStackTrace(e7);
                }
            }
            inputStream.close();
            return false;
        }
        Log.d("[FOTA_UPDATE][FotaOperator]", "[sendBeginCommand] send the data tranfer begin command");
        String a2 = a(str, str2, 0, bytes.length);
        this.c.a(a2, bytes, false);
        Log.d("[FOTA_UPDATE][FotaOperator]", "[sendBeginCommand] Begin command data length " + (a2.length() + bytes.length));
        if (byteArrayOutputStream != null) {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e8) {
                ThrowableExtension.printStackTrace(e8);
                return true;
            }
        }
        inputStream.close();
        return true;
    }

    private boolean a(int i2, String str) throws IllegalArgumentException {
        if (i2 != 5 && i2 != 6) {
            throw new IllegalArgumentException("unknow fota type");
        } else if (TextUtils.isEmpty(str)) {
            return false;
        } else {
            try {
                return a(i2, (InputStream) new FileInputStream(str));
            } catch (FileNotFoundException e2) {
                ThrowableExtension.printStackTrace(e2);
                Iterator it = this.d.iterator();
                while (it.hasNext()) {
                    ((IFotaOperatorCallback) it.next()).onStatusReceived(-100);
                }
                return false;
            }
        }
    }

    private void b(int i2, Uri uri) {
        Log.d("[FOTA_UPDATE][FotaOperator]", "[sendDataContent] which : " + i2 + ", fileUri : " + uri);
        try {
            b(i2, this.h.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e2) {
            ThrowableExtension.printStackTrace(e2);
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                ((IFotaOperatorCallback) it.next()).onStatusReceived(-100);
            }
        }
    }

    /* JADX WARNING: Incorrect condition in loop: B:37:0x00ca */
    /* JADX WARNING: Incorrect condition in loop: B:53:0x0103 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.io.InputStream, code=boolean, for r10v0, types: [java.io.InputStream] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r9, boolean r10) {
        /*
            r8 = this;
            r4 = 0
            r3 = -100
            r2 = 0
            if (r10 != 0) goto L_0x0026
            java.lang.String r0 = "[FOTA_UPDATE][FotaOperator]"
            java.lang.String r1 = "[sendContent] ins is null"
            android.util.Log.e(r0, r1)
            java.util.concurrent.CopyOnWriteArrayList<coms.mediatek.ctrl.fota.common.IFotaOperatorCallback> r0 = r8.d
            java.util.Iterator r1 = r0.iterator()
        L_0x0016:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x003a
            java.lang.Object r0 = r1.next()
            coms.mediatek.ctrl.fota.common.IFotaOperatorCallback r0 = (coms.mediatek.ctrl.fota.common.IFotaOperatorCallback) r0
            r0.onStatusReceived(r3)
            goto L_0x0016
        L_0x0026:
            long r0 = r8.f
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x002e
            r8.f = r4
        L_0x002e:
            switch(r9) {
                case 5: goto L_0x003b;
                case 6: goto L_0x0057;
                default: goto L_0x0031;
            }
        L_0x0031:
            java.lang.String r0 = "[FOTA_UPDATE][FotaOperator]"
            java.lang.String r1 = "[sendContent] unknow type"
            android.util.Log.e(r0, r1)
        L_0x003a:
            return
        L_0x003b:
            java.lang.String r1 = "fota_fbin"
            java.lang.String r0 = "fota_fbin"
        L_0x0041:
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 != 0) goto L_0x004d
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 == 0) goto L_0x005e
        L_0x004d:
            java.lang.String r0 = "[FOTA_UPDATE][FotaOperator]"
            java.lang.String r1 = "[sendContent] sender or receiver is WRONG"
            android.util.Log.e(r0, r1)
            goto L_0x003a
        L_0x0057:
            java.lang.String r1 = "gnss_update"
            java.lang.String r0 = "gnss_update"
            goto L_0x0041
        L_0x005e:
            java.lang.String r3 = "[FOTA_UPDATE][FotaOperator]"
            java.lang.String r4 = "[sendContent] send data content begin"
            android.util.Log.d(r3, r4)     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            r3 = 1900(0x76c, float:2.662E-42)
            byte[] r3 = new byte[r3]     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
        L_0x006b:
            int r4 = r10.read(r3)     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            if (r4 <= 0) goto L_0x009f
            coms.mediatek.wearable.WearableManager r5 = coms.mediatek.wearable.WearableManager.getInstance()     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            boolean r5 = r5.isAvailable()     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            if (r5 == 0) goto L_0x0096
            byte[] r5 = new byte[r4]     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            r6 = 0
            r7 = 0
            java.lang.System.arraycopy(r3, r6, r5, r7, r4)     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            r4 = 1
            int r6 = r5.length     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            java.lang.String r4 = r8.a(r1, r0, r4, r6)     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            coms.mediatek.ctrl.fota.common.a r6 = r8.c     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            r7 = 1
            r6.a(r4, r5, r7)     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            int r4 = r4.length()     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            int r5 = r5.length     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            int r4 = r4 + r5
            int r2 = r2 + r4
            goto L_0x006b
        L_0x0096:
            java.lang.String r0 = "[FOTA_UPDATE][FotaOperator]"
            java.lang.String r1 = "[sendContent] connection is lost"
            android.util.Log.e(r0, r1)     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
        L_0x009f:
            java.lang.String r0 = "[FOTA_UPDATE][FotaOperator]"
            java.lang.String r1 = "[sendContent] send data content end"
            android.util.Log.d(r0, r1)     // Catch:{ FileNotFoundException -> 0x00b3, IOException -> 0x00ec }
            if (r10 == 0) goto L_0x003a
            r10.close()     // Catch:{ IOException -> 0x00ae }
            goto L_0x003a
        L_0x00ae:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x003a
        L_0x00b3:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x00d8 }
            java.lang.String r0 = "[FOTA_UPDATE][FotaOperator]"
            java.lang.String r1 = "[sendContent] file not found"
            android.util.Log.e(r0, r1)     // Catch:{ all -> 0x00d8 }
            java.util.concurrent.CopyOnWriteArrayList<coms.mediatek.ctrl.fota.common.IFotaOperatorCallback> r0 = r8.d     // Catch:{ all -> 0x00d8 }
            java.util.Iterator r1 = r0.iterator()     // Catch:{ all -> 0x00d8 }
        L_0x00c6:
            boolean r0 = r1.hasNext()     // Catch:{ all -> 0x00d8 }
            if (r0 == 0) goto L_0x00df
            java.lang.Object r0 = r1.next()     // Catch:{ all -> 0x00d8 }
            coms.mediatek.ctrl.fota.common.IFotaOperatorCallback r0 = (coms.mediatek.ctrl.fota.common.IFotaOperatorCallback) r0     // Catch:{ all -> 0x00d8 }
            r2 = -100
            r0.onStatusReceived(r2)     // Catch:{ all -> 0x00d8 }
            goto L_0x00c6
        L_0x00d8:
            r0 = move-exception
            if (r10 == 0) goto L_0x00de
            r10.close()     // Catch:{ IOException -> 0x011e }
        L_0x00de:
            throw r0
        L_0x00df:
            if (r10 == 0) goto L_0x003a
            r10.close()     // Catch:{ IOException -> 0x00e6 }
            goto L_0x003a
        L_0x00e6:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x003a
        L_0x00ec:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x00d8 }
            java.lang.String r0 = "[FOTA_UPDATE][FotaOperator]"
            java.lang.String r1 = "[sendContent] read file failed"
            android.util.Log.e(r0, r1)     // Catch:{ all -> 0x00d8 }
            java.util.concurrent.CopyOnWriteArrayList<coms.mediatek.ctrl.fota.common.IFotaOperatorCallback> r0 = r8.d     // Catch:{ all -> 0x00d8 }
            java.util.Iterator r1 = r0.iterator()     // Catch:{ all -> 0x00d8 }
        L_0x00ff:
            boolean r0 = r1.hasNext()     // Catch:{ all -> 0x00d8 }
            if (r0 == 0) goto L_0x0111
            java.lang.Object r0 = r1.next()     // Catch:{ all -> 0x00d8 }
            coms.mediatek.ctrl.fota.common.IFotaOperatorCallback r0 = (coms.mediatek.ctrl.fota.common.IFotaOperatorCallback) r0     // Catch:{ all -> 0x00d8 }
            r2 = -101(0xffffffffffffff9b, float:NaN)
            r0.onStatusReceived(r2)     // Catch:{ all -> 0x00d8 }
            goto L_0x00ff
        L_0x0111:
            if (r10 == 0) goto L_0x003a
            r10.close()     // Catch:{ IOException -> 0x0118 }
            goto L_0x003a
        L_0x0118:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x003a
        L_0x011e:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00de
        */
        throw new UnsupportedOperationException("Method not decompiled: coms.mediatek.ctrl.fota.common.FotaOperator.b(int, java.io.InputStream):void");
    }

    private void b(int i2, String str) {
        Log.d("[FOTA_UPDATE][FotaOperator]", "[sendDataContent] which : " + i2 + ", filePath : " + str);
        try {
            b(i2, (InputStream) new FileInputStream(str));
        } catch (FileNotFoundException e2) {
            ThrowableExtension.printStackTrace(e2);
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                ((IFotaOperatorCallback) it.next()).onStatusReceived(-100);
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[handleReceivedCustomerInfo] string is WRONG");
            return;
        }
        Log.d("[FOTA_UPDATE][FotaOperator]", "[handleReceivedCustomerInfo] string : " + str);
        String[] split = str.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        if (split != null && split.length >= 5) {
            if (!"fota_cust_cmd".equals(split[1])) {
                Log.e("[FOTA_UPDATE][FotaOperator]", "[handleReceivedCustomerInfo] NOT customer information receiver");
                return;
            }
            String str2 = split[4];
            if (TextUtils.isEmpty(str2)) {
                Iterator it = this.d.iterator();
                while (it.hasNext()) {
                    ((IFotaOperatorCallback) it.next()).onCustomerInfoReceived(null);
                }
            } else if (str2.equals("-9")) {
                Iterator it2 = this.d.iterator();
                while (it2.hasNext()) {
                    ((IFotaOperatorCallback) it2.next()).onCustomerInfoReceived(null);
                }
            } else {
                Iterator it3 = this.d.iterator();
                while (it3.hasNext()) {
                    ((IFotaOperatorCallback) it3.next()).onCustomerInfoReceived(str2);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void c(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[handleReceivedOtherInfo] string is WRONG");
            return;
        }
        Log.d("[FOTA_UPDATE][FotaOperator]", "[handleReceivedOtherInfo] string : " + str);
        String[] split = str.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        if (split != null && split.length >= 5) {
            if (!"fota_fbin".equals(split[1])) {
                Log.e("[FOTA_UPDATE][FotaOperator]", "[handleReceivedOtherInfo] NOT status receiver");
            } else if (TextUtils.isEmpty(split[4])) {
                Log.e("[FOTA_UPDATE][FotaOperator]", "[handleReceivedOtherInfo] strs[4] is empty");
            } else {
                int intValue = Integer.valueOf(split[4]).intValue();
                Log.d("[FOTA_UPDATE][FotaOperator]", "[handleReceivedOtherInfo] status : " + intValue);
                Iterator it = this.d.iterator();
                while (it.hasNext()) {
                    ((IFotaOperatorCallback) it.next()).onStatusReceived(intValue);
                }
            }
        }
    }

    private ArrayList<String> d(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] split = str.split("=");
        arrayList.add(split[0]);
        if (split.length == 2) {
            arrayList.add(split[1]);
        }
        return arrayList;
    }

    public static final FotaOperator getInstance(Context context) {
        if (b == null) {
            b = new FotaOperator(context);
        }
        return b;
    }

    public void close() {
        Log.d("[FOTA_UPDATE][FotaOperator]", "[close]");
    }

    public boolean registerFotaCallback(IFotaOperatorCallback iFotaOperatorCallback) {
        if (iFotaOperatorCallback == null) {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[registerFotaCallback] callback is null");
            return false;
        } else if (this.d.contains(iFotaOperatorCallback)) {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[registerFotaCallback] callback has been registered");
            return false;
        } else {
            Log.d("[FOTA_UPDATE][FotaOperator]", "[handleProgressChange] Callback To Add: " + iFotaOperatorCallback);
            return this.d.add(iFotaOperatorCallback);
        }
    }

    public void sendFotaCustomerInfoGetCommand() {
        Log.d("[FOTA_UPDATE][FotaOperator]", "[sendFotaCustomerInfoGetCommand] enter");
        this.c.a(a("fota_cust_cmd", "fota_cust_cmd", 0, "getCustomerInfo".length()), "getCustomerInfo".getBytes(), false);
    }

    public void sendFotaFirmwareData(int i2, Uri uri) throws NullPointerException, IllegalArgumentException {
        if (uri == null) {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[sendFotaFirmwareData] filePath is WRONG");
            throw new NullPointerException("File Path is Empty");
        } else if (i2 == 5 || i2 == 6) {
            Log.d("[FOTA_UPDATE][FotaOperator]", "[sendFotaFirmwareData] which : " + i2 + ", filePath : " + uri);
            this.g = 0;
            this.e = 0;
            this.f = 0;
            if (a(i2, uri)) {
                Log.d("[FOTA_UPDATE][FotaOperator]", "[sendFotaFirmwareData] send begin command success");
                b(i2, uri);
                a(i2);
                return;
            }
            Log.d("[FOTA_UPDATE][FotaOperator]", "[sendFotaFirmwareData] send begin command failed");
        } else {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[sendFotaFirmwareData] unknown type");
            throw new IllegalArgumentException("Type is Wrong");
        }
    }

    public void sendFotaFirmwareData(int i2, String str) throws NullPointerException, IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[sendFotaFirmwareData] filePath is WRONG");
            throw new NullPointerException("File Path is Empty");
        } else if (i2 == 5 || i2 == 6) {
            Log.d("[FOTA_UPDATE][FotaOperator]", "[sendFotaFirmwareData] which : " + i2 + ", filePath : " + str);
            this.g = 0;
            this.e = 0;
            this.f = 0;
            if (a(i2, str)) {
                Log.d("[FOTA_UPDATE][FotaOperator]", "[sendFotaFirmwareData] send begin command success");
                b(i2, str);
                a(i2);
                return;
            }
            Log.d("[FOTA_UPDATE][FotaOperator]", "[sendFotaFirmwareData] send begin command failed");
        } else {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[sendFotaFirmwareData] unknown type");
            throw new IllegalArgumentException("Type is Wrong");
        }
    }

    public boolean sendFotaVersionGetCommand(int i2) throws IllegalArgumentException {
        int i3;
        if (i2 == 5 || i2 == 6) {
            Log.d("[FOTA_UPDATE][FotaOperator]", "[sendFotaVersionGetCommand] which : " + i2 + ", mFotaTypeExist : " + this.i);
            byte[] bArr = null;
            switch (i2) {
                case 5:
                    i3 = "getFBINVersion".length();
                    bArr = "getFBINVersion".getBytes();
                    break;
                case 6:
                    i3 = -1;
                    break;
                default:
                    Log.e("[FOTA_UPDATE][FotaOperator]", "[sendFotaVersionGetCommand] default enter");
                    return false;
            }
            if (i3 == -1 || bArr == null) {
                Log.e("[FOTA_UPDATE][FotaOperator]", "[sendFotaVersionGetCommand] data is WRONG");
                return false;
            }
            this.c.a(a("fota_bt_ver", "fota_bt_ver", 0, i3), bArr, false);
            return true;
        }
        throw new IllegalArgumentException("unknown type to get version");
    }

    public boolean unregisterFotaCallback(IFotaOperatorCallback iFotaOperatorCallback) {
        if (iFotaOperatorCallback == null) {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[unregisterFotaCallback] callback is null");
            return false;
        } else if (this.d.contains(iFotaOperatorCallback)) {
            return this.d.remove(iFotaOperatorCallback);
        } else {
            Log.e("[FOTA_UPDATE][FotaOperator]", "[unregisterFotaCallback] callback has NOT been registered");
            return false;
        }
    }
}
