package com.iwown.device_module.device_firmware_upgrade.dwonloadBiz;

import android.os.Environment;
import android.os.Message;
import com.google.common.net.HttpHeaders;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.lib_common.file.FileUtils;
import com.socks.library.KLog;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;

public class DownloadServiceBiz {
    public static final int DOWNLOAD_FIRMWARE = 1;
    public static final int DOWNLOAD_PICTURE = 2;
    private static DownloadServiceBiz downloadServiceBiz;
    OkHttpClient https = new OkHttpClient();
    /* access modifiers changed from: private */
    public CallbackHandler mHandler;

    public static DownloadServiceBiz getInstance() {
        if (downloadServiceBiz == null) {
            downloadServiceBiz = new DownloadServiceBiz();
        }
        return downloadServiceBiz;
    }

    public void downloadFirmware(String path, String fileName) {
        final File fir = new File(Environment.getExternalStorageDirectory(), LogPath.ZERONER + fileName);
        KLog.e(fir.getPath());
        boolean flag = false;
        try {
            if (fir.exists()) {
                boolean flag2 = FileUtils.deleteFile(LogPath.ZERONER + fileName);
                File fir1 = new File(Environment.getExternalStorageDirectory(), LogPath.ZERONER);
                if (fir1.exists()) {
                    fir1.mkdirs();
                }
                fir.createNewFile();
                flag = true;
            } else {
                File fir12 = new File(Environment.getExternalStorageDirectory(), LogPath.ZERONER);
                if (fir12.exists()) {
                    fir12.mkdirs();
                }
                fir.createNewFile();
                flag = true;
            }
        } catch (IOException e) {
            KLog.e("==========================");
            ThrowableExtension.printStackTrace(e);
            sendErrorMessage("失败 error " + e.getMessage());
        }
        if (flag) {
            this.https.newCall(new Builder().url(path).addHeader(HttpHeaders.ACCEPT_ENCODING, "identity").build()).enqueue(new Callback() {
                /* JADX WARNING: Removed duplicated region for block: B:20:0x00e5 A[SYNTHETIC, Splitter:B:20:0x00e5] */
                /* JADX WARNING: Removed duplicated region for block: B:23:0x00ea A[SYNTHETIC, Splitter:B:23:0x00ea] */
                /* JADX WARNING: Removed duplicated region for block: B:42:0x012a A[SYNTHETIC, Splitter:B:42:0x012a] */
                /* JADX WARNING: Removed duplicated region for block: B:45:0x012f A[SYNTHETIC, Splitter:B:45:0x012f] */
                /* JADX WARNING: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onResponse(okhttp3.Call r16, okhttp3.Response r17) throws java.io.IOException {
                    /*
                        r15 = this;
                        if (r17 != 0) goto L_0x0012
                        java.lang.String r7 = "下载失败 : response = = null"
                        r12 = 6
                        com.iwown.lib_common.log.L.file(r7, r12)
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz r7 = com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz.this
                        java.lang.String r12 = "失败 error :response == null "
                        r7.sendErrorMessage(r12)
                    L_0x0011:
                        return
                    L_0x0012:
                        boolean r7 = r17.isSuccessful()
                        if (r7 != 0) goto L_0x0056
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder
                        r7.<init>()
                        java.lang.String r12 = "下载失败 : "
                        java.lang.StringBuilder r7 = r7.append(r12)
                        int r12 = r17.code()
                        java.lang.StringBuilder r7 = r7.append(r12)
                        java.lang.String r7 = r7.toString()
                        r12 = 6
                        com.iwown.lib_common.log.L.file(r7, r12)
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz r7 = com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz.this
                        java.lang.StringBuilder r12 = new java.lang.StringBuilder
                        r12.<init>()
                        java.lang.String r13 = "失败 error : "
                        java.lang.StringBuilder r12 = r12.append(r13)
                        int r13 = r17.code()
                        java.lang.StringBuilder r12 = r12.append(r13)
                        java.lang.String r12 = r12.toString()
                        int r13 = r17.code()
                        r7.sendErrorMessage(r12, r13)
                        goto L_0x0011
                    L_0x0056:
                        r4 = 0
                        r7 = 1024(0x400, float:1.435E-42)
                        byte[] r0 = new byte[r7]
                        r5 = 0
                        r2 = 0
                        okhttp3.ResponseBody r7 = r17.body()     // Catch:{ Exception -> 0x013e }
                        java.io.InputStream r4 = r7.byteStream()     // Catch:{ Exception -> 0x013e }
                        okhttp3.ResponseBody r7 = r17.body()     // Catch:{ Exception -> 0x013e }
                        long r10 = r7.contentLength()     // Catch:{ Exception -> 0x013e }
                        java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x013e }
                        java.io.File r7 = r1     // Catch:{ Exception -> 0x013e }
                        r3.<init>(r7)     // Catch:{ Exception -> 0x013e }
                        r8 = 0
                    L_0x0076:
                        int r5 = r4.read(r0)     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        r7 = -1
                        if (r5 == r7) goto L_0x00f2
                        r7 = 0
                        r3.write(r0, r7, r5)     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        long r12 = (long) r5     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        long r8 = r8 + r12
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz r7 = com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz.this     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.CallbackHandler r7 = r7.mHandler     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        if (r7 == 0) goto L_0x0076
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz r7 = com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz.this     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.CallbackHandler r7 = r7.mHandler     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        android.os.Message r6 = r7.obtainMessage()     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        r7 = 2
                        java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        r12 = 0
                        java.lang.Long r13 = java.lang.Long.valueOf(r8)     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        r7[r12] = r13     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        r12 = 1
                        java.lang.Long r13 = java.lang.Long.valueOf(r10)     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        r7[r12] = r13     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        r6.obj = r7     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        r7 = 3
                        r6.what = r7     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz r7 = com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz.this     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.CallbackHandler r7 = r7.mHandler     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        r7.sendMessage(r6)     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        goto L_0x0076
                    L_0x00b5:
                        r1 = move-exception
                        r2 = r3
                    L_0x00b7:
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz r7 = com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz.this     // Catch:{ all -> 0x0127 }
                        java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0127 }
                        r12.<init>()     // Catch:{ all -> 0x0127 }
                        java.lang.String r13 = "失败  error = "
                        java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0127 }
                        java.lang.String r13 = r1.getMessage()     // Catch:{ all -> 0x0127 }
                        java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x0127 }
                        java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0127 }
                        r7.sendErrorMessage(r12)     // Catch:{ all -> 0x0127 }
                        java.lang.String r7 = "DownloadServiceBiz"
                        r12 = 1
                        java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ all -> 0x0127 }
                        r13 = 0
                        java.lang.String r14 = "文件下载失败"
                        r12[r13] = r14     // Catch:{ all -> 0x0127 }
                        com.socks.library.KLog.d(r7, r12)     // Catch:{ all -> 0x0127 }
                        if (r4 == 0) goto L_0x00e8
                        r4.close()     // Catch:{ IOException -> 0x0135 }
                    L_0x00e8:
                        if (r2 == 0) goto L_0x0011
                        r2.close()     // Catch:{ IOException -> 0x00ef }
                        goto L_0x0011
                    L_0x00ef:
                        r7 = move-exception
                        goto L_0x0011
                    L_0x00f2:
                        r3.flush()     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz r7 = com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz.this     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.CallbackHandler r7 = r7.mHandler     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        if (r7 == 0) goto L_0x0107
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz r7 = com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz.this     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.CallbackHandler r7 = r7.mHandler     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        r12 = 4
                        r7.sendEmptyMessage(r12)     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                    L_0x0107:
                        java.lang.String r7 = "DownloadServiceBiz"
                        r12 = 1
                        java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        r13 = 0
                        java.lang.String r14 = "文件下载成功"
                        r12[r13] = r14     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        com.socks.library.KLog.d(r7, r12)     // Catch:{ Exception -> 0x00b5, all -> 0x013b }
                        if (r4 == 0) goto L_0x011b
                        r4.close()     // Catch:{ IOException -> 0x0133 }
                    L_0x011b:
                        if (r3 == 0) goto L_0x0120
                        r3.close()     // Catch:{ IOException -> 0x0123 }
                    L_0x0120:
                        r2 = r3
                        goto L_0x0011
                    L_0x0123:
                        r7 = move-exception
                        r2 = r3
                        goto L_0x0011
                    L_0x0127:
                        r7 = move-exception
                    L_0x0128:
                        if (r4 == 0) goto L_0x012d
                        r4.close()     // Catch:{ IOException -> 0x0137 }
                    L_0x012d:
                        if (r2 == 0) goto L_0x0132
                        r2.close()     // Catch:{ IOException -> 0x0139 }
                    L_0x0132:
                        throw r7
                    L_0x0133:
                        r7 = move-exception
                        goto L_0x011b
                    L_0x0135:
                        r7 = move-exception
                        goto L_0x00e8
                    L_0x0137:
                        r12 = move-exception
                        goto L_0x012d
                    L_0x0139:
                        r12 = move-exception
                        goto L_0x0132
                    L_0x013b:
                        r7 = move-exception
                        r2 = r3
                        goto L_0x0128
                    L_0x013e:
                        r1 = move-exception
                        goto L_0x00b7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz.AnonymousClass1.onResponse(okhttp3.Call, okhttp3.Response):void");
                }

                public void onFailure(Call call, IOException e) {
                    DownloadServiceBiz.this.sendErrorMessage("失败 error " + e.getMessage());
                }
            });
        }
    }

    public void sendErrorMessage(String message) {
        if (this.mHandler != null) {
            Message msg = Message.obtain();
            msg.obj = new Object[]{message};
            msg.what = 5;
            this.mHandler.sendMessage(msg);
        }
    }

    /* access modifiers changed from: private */
    public void sendErrorMessage(String message, int errorCode) {
        if (this.mHandler != null) {
            Message msg = Message.obtain();
            msg.obj = new Object[]{message, Integer.valueOf(errorCode)};
            msg.what = 5;
            this.mHandler.sendMessage(msg);
        }
    }

    public CallbackHandler getmHandler() {
        return this.mHandler;
    }

    public void setmHandler(CallbackHandler mHandler2) {
        this.mHandler = mHandler2;
    }
}
