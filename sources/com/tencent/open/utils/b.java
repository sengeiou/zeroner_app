package com.tencent.open.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.open.a.f;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* compiled from: ProGuard */
public class b {
    /* access modifiers changed from: private */
    public static String c;
    /* access modifiers changed from: private */
    public String a;
    /* access modifiers changed from: private */
    public c b;
    /* access modifiers changed from: private */
    public long d;
    /* access modifiers changed from: private */
    public Handler e;
    private Runnable f = new Runnable() {
        public void run() {
            boolean z;
            f.a("AsynLoadImg", "saveFileRunnable:");
            String str = "share_qq_" + j.f(b.this.a) + ".jpg";
            String str2 = b.c + str;
            File file = new File(str2);
            Message obtainMessage = b.this.e.obtainMessage();
            if (file.exists()) {
                obtainMessage.arg1 = 0;
                obtainMessage.obj = str2;
                f.a("AsynLoadImg", "file exists: time:" + (System.currentTimeMillis() - b.this.d));
            } else {
                Bitmap a2 = b.a(b.this.a);
                if (a2 != null) {
                    z = b.this.a(a2, str);
                } else {
                    f.a("AsynLoadImg", "saveFileRunnable:get bmp fail---");
                    z = false;
                }
                if (z) {
                    obtainMessage.arg1 = 0;
                    obtainMessage.obj = str2;
                } else {
                    obtainMessage.arg1 = 1;
                }
                f.a("AsynLoadImg", "file not exists: download time:" + (System.currentTimeMillis() - b.this.d));
            }
            b.this.e.sendMessage(obtainMessage);
        }
    };

    public b(Activity activity) {
        this.e = new Handler(activity.getMainLooper()) {
            public void handleMessage(Message message) {
                f.a("AsynLoadImg", "handleMessage:" + message.arg1);
                if (message.arg1 == 0) {
                    b.this.b.a(message.arg1, (String) message.obj);
                } else {
                    b.this.b.a(message.arg1, (String) null);
                }
            }
        };
    }

    public void a(String str, c cVar) {
        f.a("AsynLoadImg", "--save---");
        if (str == null || str.equals("")) {
            cVar.a(1, (String) null);
        } else if (!j.b()) {
            cVar.a(2, (String) null);
        } else {
            c = Environment.getExternalStorageDirectory() + "/tmp/";
            this.d = System.currentTimeMillis();
            this.a = str;
            this.b = cVar;
            new Thread(this.f).start();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0071 A[SYNTHETIC, Splitter:B:19:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x007d A[SYNTHETIC, Splitter:B:25:0x007d] */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(android.graphics.Bitmap r6, java.lang.String r7) {
        /*
            r5 = this;
            java.lang.String r0 = c
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ IOException -> 0x0061 }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0061 }
            boolean r3 = r2.exists()     // Catch:{ IOException -> 0x0061 }
            if (r3 != 0) goto L_0x0011
            r2.mkdir()     // Catch:{ IOException -> 0x0061 }
        L_0x0011:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0061 }
            r2.<init>()     // Catch:{ IOException -> 0x0061 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ IOException -> 0x0061 }
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ IOException -> 0x0061 }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x0061 }
            java.lang.String r2 = "AsynLoadImg"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0061 }
            r3.<init>()     // Catch:{ IOException -> 0x0061 }
            java.lang.String r4 = "saveFile:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x0061 }
            java.lang.StringBuilder r3 = r3.append(r7)     // Catch:{ IOException -> 0x0061 }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x0061 }
            com.tencent.open.a.f.a(r2, r3)     // Catch:{ IOException -> 0x0061 }
            java.io.File r3 = new java.io.File     // Catch:{ IOException -> 0x0061 }
            r3.<init>(r0)     // Catch:{ IOException -> 0x0061 }
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0061 }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0061 }
            r0.<init>(r3)     // Catch:{ IOException -> 0x0061 }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0061 }
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ IOException -> 0x0089, all -> 0x0086 }
            r1 = 80
            r6.compress(r0, r1, r2)     // Catch:{ IOException -> 0x0089, all -> 0x0086 }
            r2.flush()     // Catch:{ IOException -> 0x0089, all -> 0x0086 }
            if (r2 == 0) goto L_0x005a
            r2.close()     // Catch:{ IOException -> 0x005c }
        L_0x005a:
            r0 = 1
        L_0x005b:
            return r0
        L_0x005c:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x005a
        L_0x0061:
            r0 = move-exception
        L_0x0062:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x007a }
            java.lang.String r2 = "AsynLoadImg"
            java.lang.String r3 = "saveFile bmp fail---"
            com.tencent.open.a.f.b(r2, r3, r0)     // Catch:{ all -> 0x007a }
            r0 = 0
            if (r1 == 0) goto L_0x005b
            r1.close()     // Catch:{ IOException -> 0x0075 }
            goto L_0x005b
        L_0x0075:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x005b
        L_0x007a:
            r0 = move-exception
        L_0x007b:
            if (r1 == 0) goto L_0x0080
            r1.close()     // Catch:{ IOException -> 0x0081 }
        L_0x0080:
            throw r0
        L_0x0081:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0080
        L_0x0086:
            r0 = move-exception
            r1 = r2
            goto L_0x007b
        L_0x0089:
            r0 = move-exception
            r1 = r2
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.b.a(android.graphics.Bitmap, java.lang.String):boolean");
    }

    public static Bitmap a(String str) {
        f.a("AsynLoadImg", "getbitmap:" + str);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            f.a("AsynLoadImg", "image download finished." + str);
            return decodeStream;
        } catch (OutOfMemoryError e2) {
            ThrowableExtension.printStackTrace(e2);
            f.a("AsynLoadImg", "getbitmap bmp fail---");
            return null;
        } catch (IOException e3) {
            ThrowableExtension.printStackTrace(e3);
            f.a("AsynLoadImg", "getbitmap bmp fail---");
            return null;
        }
    }
}
