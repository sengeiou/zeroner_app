package cn.smssdk.net;

import com.mob.tools.network.HttpResponseCallback;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

public class HttpResponseCallbackImp implements HttpResponseCallback {
    private HashMap<String, Object> a;

    public HttpResponseCallbackImp(HashMap<String, Object> hashMap) {
        this.a = hashMap;
    }

    public void handleInput(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[65536];
        int read = inputStream.read(bArr);
        while (read > 0) {
            byteArrayOutputStream.write(bArr, 0, read);
            read = inputStream.read(bArr);
        }
        byteArrayOutputStream.flush();
        this.a.put("bResp", byteArrayOutputStream.toByteArray());
        byteArrayOutputStream.close();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0030, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0031, code lost:
        if (r1 != null) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0036, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onResponse(com.mob.tools.network.HttpConnection r6) {
        /*
            r5 = this;
            r4 = 600(0x258, float:8.41E-43)
            int r1 = r6.getResponseCode()
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r5.a
            java.lang.String r2 = "httpStatus"
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)
            r0.put(r2, r3)
            r0 = 200(0xc8, float:2.8E-43)
            if (r1 == r0) goto L_0x0018
            if (r1 != r4) goto L_0x0037
        L_0x0018:
            if (r1 != r4) goto L_0x0028
            java.io.InputStream r0 = r6.getErrorStream()
            r1 = r0
        L_0x001f:
            r5.handleInput(r1)     // Catch:{ Throwable -> 0x002e }
            if (r1 == 0) goto L_0x0027
            r1.close()     // Catch:{ Throwable -> 0x0095 }
        L_0x0027:
            return
        L_0x0028:
            java.io.InputStream r0 = r6.getInputStream()
            r1 = r0
            goto L_0x001f
        L_0x002e:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0030 }
        L_0x0030:
            r0 = move-exception
            if (r1 == 0) goto L_0x0036
            r1.close()     // Catch:{ Throwable -> 0x0097 }
        L_0x0036:
            throw r0
        L_0x0037:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.io.InputStreamReader r0 = new java.io.InputStreamReader
            java.io.InputStream r3 = r6.getErrorStream()
            java.lang.String r4 = "utf-8"
            java.nio.charset.Charset r4 = java.nio.charset.Charset.forName(r4)
            r0.<init>(r3, r4)
            java.io.BufferedReader r3 = new java.io.BufferedReader
            r3.<init>(r0)
            java.lang.String r0 = r3.readLine()
        L_0x0055:
            if (r0 == 0) goto L_0x006a
            int r4 = r2.length()
            if (r4 <= 0) goto L_0x0062
            r4 = 10
            r2.append(r4)
        L_0x0062:
            r2.append(r0)
            java.lang.String r0 = r3.readLine()
            goto L_0x0055
        L_0x006a:
            r3.close()
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.lang.String r3 = "error"
            java.lang.String r2 = r2.toString()
            r0.put(r3, r2)
            java.lang.String r2 = "status"
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r0.put(r2, r1)
            java.lang.Throwable r1 = new java.lang.Throwable
            com.mob.tools.utils.Hashon r2 = new com.mob.tools.utils.Hashon
            r2.<init>()
            java.lang.String r0 = r2.fromHashMap(r0)
            r1.<init>(r0)
            throw r1
        L_0x0095:
            r0 = move-exception
            goto L_0x0027
        L_0x0097:
            r1 = move-exception
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.smssdk.net.HttpResponseCallbackImp.onResponse(com.mob.tools.network.HttpConnection):void");
    }
}
