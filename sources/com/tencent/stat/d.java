package com.tencent.stat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.stat.a.e;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.common.k;
import java.util.Arrays;
import java.util.List;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

class d {
    /* access modifiers changed from: private */
    public static StatLogger c = k.b();
    private static long d = -1;
    private static d e = null;
    private static Context f = null;
    DefaultHttpClient a = null;
    Handler b = null;

    private d() {
        try {
            HandlerThread handlerThread = new HandlerThread("StatDispatcher");
            handlerThread.start();
            d = handlerThread.getId();
            this.b = new Handler(handlerThread.getLooper());
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
            HttpConnectionParams.setSoTimeout(basicHttpParams, 10000);
            this.a = new DefaultHttpClient(basicHttpParams);
            this.a.setKeepAliveStrategy(new e(this));
            if (StatConfig.b() != null) {
                this.a.getParams().setParameter("http.route.default-proxy", StatConfig.b());
            }
        } catch (Throwable th) {
            c.e((Object) th);
        }
    }

    static Context a() {
        return f;
    }

    static void a(Context context) {
        f = context.getApplicationContext();
    }

    static synchronized d b() {
        d dVar;
        synchronized (d.class) {
            if (e == null) {
                e = new d();
            }
            dVar = e;
        }
        return dVar;
    }

    /* access modifiers changed from: 0000 */
    public void a(e eVar, c cVar) {
        b(Arrays.asList(new String[]{eVar.d()}), cVar);
    }

    /* access modifiers changed from: 0000 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.util.List<java.lang.String> r13, com.tencent.stat.c r14) {
        /*
            r12 = this;
            r10 = 0
            r9 = 200(0xc8, float:2.8E-43)
            r1 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x015f }
            r3.<init>()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = "["
            r3.append(r0)     // Catch:{ Throwable -> 0x015f }
            r2 = r1
        L_0x0011:
            int r0 = r13.size()     // Catch:{ Throwable -> 0x015f }
            if (r2 >= r0) goto L_0x0032
            java.lang.Object r0 = r13.get(r2)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x015f }
            r3.append(r0)     // Catch:{ Throwable -> 0x015f }
            int r0 = r13.size()     // Catch:{ Throwable -> 0x015f }
            int r0 = r0 + -1
            if (r2 == r0) goto L_0x002e
            java.lang.String r0 = ","
            r3.append(r0)     // Catch:{ Throwable -> 0x015f }
        L_0x002e:
            int r0 = r2 + 1
            r2 = r0
            goto L_0x0011
        L_0x0032:
            java.lang.String r0 = "]"
            r3.append(r0)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = com.tencent.stat.StatConfig.getStatReportUrl()     // Catch:{ Throwable -> 0x015f }
            com.tencent.stat.common.StatLogger r2 = c     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x015f }
            r4.<init>()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r5 = "["
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r5 = "]Send request("
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r5 = r3.toString()     // Catch:{ Throwable -> 0x015f }
            int r5 = r5.length()     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r5 = "bytes):"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r5 = r3.toString()     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x015f }
            r2.i(r4)     // Catch:{ Throwable -> 0x015f }
            org.apache.http.client.methods.HttpPost r2 = new org.apache.http.client.methods.HttpPost     // Catch:{ Throwable -> 0x015f }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = "Accept-Encoding"
            java.lang.String r4 = "gzip"
            r2.addHeader(r0, r4)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = "Connection"
            java.lang.String r4 = "Keep-Alive"
            r2.setHeader(r0, r4)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = "Cache-Control"
            r2.removeHeaders(r0)     // Catch:{ Throwable -> 0x015f }
            android.content.Context r0 = f     // Catch:{ Throwable -> 0x015f }
            org.apache.http.HttpHost r4 = com.tencent.stat.common.k.a(r0)     // Catch:{ Throwable -> 0x015f }
            if (r4 == 0) goto L_0x00cb
            org.apache.http.impl.client.DefaultHttpClient r0 = r12.a     // Catch:{ Throwable -> 0x015f }
            org.apache.http.params.HttpParams r0 = r0.getParams()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r1 = "http.route.default-proxy"
            android.content.Context r5 = f     // Catch:{ Throwable -> 0x015f }
            org.apache.http.HttpHost r5 = com.tencent.stat.common.k.a(r5)     // Catch:{ Throwable -> 0x015f }
            r0.setParameter(r1, r5)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = "X-Online-Host"
            java.lang.String r1 = "pingma.qq.com:80"
            r2.addHeader(r0, r1)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = "Accept"
            java.lang.String r1 = "*/*"
            r2.addHeader(r0, r1)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = "Content-Type"
            java.lang.String r1 = "json"
            r2.addHeader(r0, r1)     // Catch:{ Throwable -> 0x015f }
            r0 = 1
            r1 = r0
        L_0x00cb:
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x015f }
            r5.<init>()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r6 = "UTF-8"
            byte[] r0 = r0.getBytes(r6)     // Catch:{ Throwable -> 0x015f }
            int r6 = r0.length     // Catch:{ Throwable -> 0x015f }
            int r3 = r3.length()     // Catch:{ Throwable -> 0x015f }
            r7 = 256(0x100, float:3.59E-43)
            if (r3 >= r7) goto L_0x0174
            if (r4 != 0) goto L_0x0155
            java.lang.String r3 = "Content-Encoding"
            java.lang.String r4 = "rc4"
            r2.addHeader(r3, r4)     // Catch:{ Throwable -> 0x015f }
        L_0x00ef:
            byte[] r0 = com.tencent.stat.common.e.a(r0)     // Catch:{ Throwable -> 0x015f }
            org.apache.http.entity.ByteArrayEntity r3 = new org.apache.http.entity.ByteArrayEntity     // Catch:{ Throwable -> 0x015f }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x015f }
            r2.setEntity(r3)     // Catch:{ Throwable -> 0x015f }
            org.apache.http.impl.client.DefaultHttpClient r0 = r12.a     // Catch:{ Throwable -> 0x015f }
            org.apache.http.HttpResponse r2 = r0.execute(r2)     // Catch:{ Throwable -> 0x015f }
            if (r1 == 0) goto L_0x010f
            org.apache.http.impl.client.DefaultHttpClient r0 = r12.a     // Catch:{ Throwable -> 0x015f }
            org.apache.http.params.HttpParams r0 = r0.getParams()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r1 = "http.route.default-proxy"
            r0.removeParameter(r1)     // Catch:{ Throwable -> 0x015f }
        L_0x010f:
            org.apache.http.HttpEntity r0 = r2.getEntity()     // Catch:{ Throwable -> 0x015f }
            org.apache.http.StatusLine r1 = r2.getStatusLine()     // Catch:{ Throwable -> 0x015f }
            int r1 = r1.getStatusCode()     // Catch:{ Throwable -> 0x015f }
            long r6 = r0.getContentLength()     // Catch:{ Throwable -> 0x015f }
            com.tencent.stat.common.StatLogger r3 = c     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x015f }
            r4.<init>()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r8 = "recv response status code:"
            java.lang.StringBuilder r4 = r4.append(r8)     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r8 = ", content length:"
            java.lang.StringBuilder r4 = r4.append(r8)     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x015f }
            r3.i(r4)     // Catch:{ Throwable -> 0x015f }
            int r3 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r3 != 0) goto L_0x01f0
            org.apache.http.util.EntityUtils.toString(r0)     // Catch:{ Throwable -> 0x015f }
            if (r1 != r9) goto L_0x01d5
            if (r14 == 0) goto L_0x0151
            r14.a()     // Catch:{ Throwable -> 0x015f }
        L_0x0151:
            r5.close()     // Catch:{ Throwable -> 0x015f }
        L_0x0154:
            return
        L_0x0155:
            java.lang.String r3 = "X-Content-Encoding"
            java.lang.String r4 = "rc4"
            r2.addHeader(r3, r4)     // Catch:{ Throwable -> 0x015f }
            goto L_0x00ef
        L_0x015f:
            r0 = move-exception
            com.tencent.stat.common.StatLogger r1 = c     // Catch:{ all -> 0x0172 }
            r1.e(r0)     // Catch:{ all -> 0x0172 }
            if (r14 == 0) goto L_0x0154
            r14.b()     // Catch:{ Throwable -> 0x016b }
            goto L_0x0154
        L_0x016b:
            r0 = move-exception
            com.tencent.stat.common.StatLogger r1 = c     // Catch:{ all -> 0x0172 }
            r1.e(r0)     // Catch:{ all -> 0x0172 }
            goto L_0x0154
        L_0x0172:
            r0 = move-exception
            throw r0
        L_0x0174:
            if (r4 != 0) goto L_0x01cb
            java.lang.String r3 = "Content-Encoding"
            java.lang.String r4 = "rc4,gzip"
            r2.addHeader(r3, r4)     // Catch:{ Throwable -> 0x015f }
        L_0x017f:
            r3 = 4
            byte[] r3 = new byte[r3]     // Catch:{ Throwable -> 0x015f }
            r5.write(r3)     // Catch:{ Throwable -> 0x015f }
            java.util.zip.GZIPOutputStream r3 = new java.util.zip.GZIPOutputStream     // Catch:{ Throwable -> 0x015f }
            r3.<init>(r5)     // Catch:{ Throwable -> 0x015f }
            r3.write(r0)     // Catch:{ Throwable -> 0x015f }
            r3.close()     // Catch:{ Throwable -> 0x015f }
            byte[] r0 = r5.toByteArray()     // Catch:{ Throwable -> 0x015f }
            r3 = 0
            r4 = 4
            java.nio.ByteBuffer r3 = java.nio.ByteBuffer.wrap(r0, r3, r4)     // Catch:{ Throwable -> 0x015f }
            r3.putInt(r6)     // Catch:{ Throwable -> 0x015f }
            com.tencent.stat.common.StatLogger r3 = c     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x015f }
            r4.<init>()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r7 = "before Gzip:"
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r6 = " bytes, after Gzip:"
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x015f }
            int r6 = r0.length     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r6 = " bytes"
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x015f }
            r3.d(r4)     // Catch:{ Throwable -> 0x015f }
            goto L_0x00ef
        L_0x01cb:
            java.lang.String r3 = "X-Content-Encoding"
            java.lang.String r4 = "rc4,gzip"
            r2.addHeader(r3, r4)     // Catch:{ Throwable -> 0x015f }
            goto L_0x017f
        L_0x01d5:
            com.tencent.stat.common.StatLogger r0 = c     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x015f }
            r2.<init>()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r3 = "Server response error code:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x015f }
            r0.error(r1)     // Catch:{ Throwable -> 0x015f }
            goto L_0x0151
        L_0x01f0:
            int r3 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r3 <= 0) goto L_0x0345
            java.io.InputStream r3 = r0.getContent()     // Catch:{ Throwable -> 0x015f }
            java.io.DataInputStream r4 = new java.io.DataInputStream     // Catch:{ Throwable -> 0x015f }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x015f }
            long r6 = r0.getContentLength()     // Catch:{ Throwable -> 0x015f }
            int r0 = (int) r6     // Catch:{ Throwable -> 0x015f }
            byte[] r0 = new byte[r0]     // Catch:{ Throwable -> 0x015f }
            r4.readFully(r0)     // Catch:{ Throwable -> 0x015f }
            r3.close()     // Catch:{ Throwable -> 0x015f }
            r4.close()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r4 = "Content-Encoding"
            org.apache.http.Header r2 = r2.getFirstHeader(r4)     // Catch:{ Throwable -> 0x015f }
            if (r2 == 0) goto L_0x022b
            java.lang.String r4 = r2.getValue()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r6 = "gzip,rc4"
            boolean r4 = r4.equalsIgnoreCase(r6)     // Catch:{ Throwable -> 0x015f }
            if (r4 == 0) goto L_0x02b9
            byte[] r0 = com.tencent.stat.common.k.a(r0)     // Catch:{ Throwable -> 0x015f }
            byte[] r0 = com.tencent.stat.common.e.b(r0)     // Catch:{ Throwable -> 0x015f }
        L_0x022b:
            if (r1 != r9) goto L_0x0317
            java.lang.String r1 = new java.lang.String     // Catch:{ Throwable -> 0x030c }
            java.lang.String r2 = "UTF-8"
            r1.<init>(r0, r2)     // Catch:{ Throwable -> 0x030c }
            com.tencent.stat.common.StatLogger r0 = c     // Catch:{ Throwable -> 0x030c }
            r0.d(r1)     // Catch:{ Throwable -> 0x030c }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Throwable -> 0x030c }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x030c }
            java.lang.String r2 = "cfg"
            boolean r2 = r0.isNull(r2)     // Catch:{ Throwable -> 0x030c }
            if (r2 != 0) goto L_0x0252
            java.lang.String r2 = "cfg"
            org.json.JSONObject r2 = r0.getJSONObject(r2)     // Catch:{ Throwable -> 0x030c }
            com.tencent.stat.StatConfig.a(r2)     // Catch:{ Throwable -> 0x030c }
        L_0x0252:
            java.lang.String r2 = "et"
            boolean r2 = r0.isNull(r2)     // Catch:{ Throwable -> 0x030c }
            if (r2 != 0) goto L_0x02af
            java.lang.String r2 = "st"
            boolean r2 = r0.isNull(r2)     // Catch:{ Throwable -> 0x030c }
            if (r2 != 0) goto L_0x02af
            com.tencent.stat.common.StatLogger r2 = c     // Catch:{ Throwable -> 0x030c }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x030c }
            r4.<init>()     // Catch:{ Throwable -> 0x030c }
            java.lang.String r6 = "get mid respone:"
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x030c }
            java.lang.StringBuilder r1 = r4.append(r1)     // Catch:{ Throwable -> 0x030c }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x030c }
            r2.d(r1)     // Catch:{ Throwable -> 0x030c }
            java.lang.String r1 = "et"
            int r1 = r0.getInt(r1)     // Catch:{ Throwable -> 0x030c }
            com.tencent.stat.a.f r2 = com.tencent.stat.a.f.SESSION_ENV     // Catch:{ Throwable -> 0x030c }
            int r2 = r2.a()     // Catch:{ Throwable -> 0x030c }
            if (r1 != r2) goto L_0x02af
            java.lang.String r1 = "st"
            int r1 = r0.getInt(r1)     // Catch:{ Throwable -> 0x030c }
            switch(r1) {
                case -1: goto L_0x02f6;
                case 0: goto L_0x02f6;
                default: goto L_0x0296;
            }     // Catch:{ Throwable -> 0x030c }
        L_0x0296:
            com.tencent.stat.common.StatLogger r0 = c     // Catch:{ Throwable -> 0x030c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x030c }
            r2.<init>()     // Catch:{ Throwable -> 0x030c }
            java.lang.String r4 = "error type for st:"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x030c }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ Throwable -> 0x030c }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x030c }
            r0.e(r1)     // Catch:{ Throwable -> 0x030c }
        L_0x02af:
            if (r14 == 0) goto L_0x02b4
            r14.a()     // Catch:{ Throwable -> 0x015f }
        L_0x02b4:
            r3.close()     // Catch:{ Throwable -> 0x015f }
            goto L_0x0151
        L_0x02b9:
            java.lang.String r4 = r2.getValue()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r6 = "rc4,gzip"
            boolean r4 = r4.equalsIgnoreCase(r6)     // Catch:{ Throwable -> 0x015f }
            if (r4 == 0) goto L_0x02d0
            byte[] r0 = com.tencent.stat.common.e.b(r0)     // Catch:{ Throwable -> 0x015f }
            byte[] r0 = com.tencent.stat.common.k.a(r0)     // Catch:{ Throwable -> 0x015f }
            goto L_0x022b
        L_0x02d0:
            java.lang.String r4 = r2.getValue()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r6 = "gzip"
            boolean r4 = r4.equalsIgnoreCase(r6)     // Catch:{ Throwable -> 0x015f }
            if (r4 == 0) goto L_0x02e3
            byte[] r0 = com.tencent.stat.common.k.a(r0)     // Catch:{ Throwable -> 0x015f }
            goto L_0x022b
        L_0x02e3:
            java.lang.String r2 = r2.getValue()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r4 = "rc4"
            boolean r2 = r2.equalsIgnoreCase(r4)     // Catch:{ Throwable -> 0x015f }
            if (r2 == 0) goto L_0x022b
            byte[] r0 = com.tencent.stat.common.e.b(r0)     // Catch:{ Throwable -> 0x015f }
            goto L_0x022b
        L_0x02f6:
            java.lang.String r1 = "mid"
            boolean r1 = r0.isNull(r1)     // Catch:{ Throwable -> 0x030c }
            if (r1 != 0) goto L_0x02af
            android.content.Context r1 = f     // Catch:{ Throwable -> 0x030c }
            java.lang.String r2 = "mid"
            java.lang.String r0 = r0.getString(r2)     // Catch:{ Throwable -> 0x030c }
            com.tencent.stat.StatMid.updateDeviceInfo(r1, r0)     // Catch:{ Throwable -> 0x030c }
            goto L_0x02af
        L_0x030c:
            r0 = move-exception
            com.tencent.stat.common.StatLogger r1 = c     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x015f }
            r1.i(r0)     // Catch:{ Throwable -> 0x015f }
            goto L_0x02af
        L_0x0317:
            com.tencent.stat.common.StatLogger r2 = c     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x015f }
            r4.<init>()     // Catch:{ Throwable -> 0x015f }
            java.lang.String r6 = "Server response error code:"
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r1 = r4.append(r1)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r4 = ", error:"
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r4 = new java.lang.String     // Catch:{ Throwable -> 0x015f }
            java.lang.String r6 = "UTF-8"
            r4.<init>(r0, r6)     // Catch:{ Throwable -> 0x015f }
            java.lang.StringBuilder r0 = r1.append(r4)     // Catch:{ Throwable -> 0x015f }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x015f }
            r2.error(r0)     // Catch:{ Throwable -> 0x015f }
            goto L_0x02b4
        L_0x0345:
            org.apache.http.util.EntityUtils.toString(r0)     // Catch:{ Throwable -> 0x015f }
            goto L_0x0151
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.d.a(java.util.List, com.tencent.stat.c):void");
    }

    /* access modifiers changed from: 0000 */
    public void b(List<String> list, c cVar) {
        if (!list.isEmpty() && this.b != null) {
            this.b.post(new f(this, list, cVar));
        }
    }
}
