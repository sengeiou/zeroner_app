package com.tencent.bugly.proguard;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.BuglyStrategy.a;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: BUGLY */
public class ai {
    private static ai c;
    protected Context a;
    public Map<String, String> b = null;

    private ai(Context context) {
        this.a = context;
    }

    public static ai a(Context context) {
        if (c == null) {
            c = new ai(context);
        }
        return c;
    }

    /* JADX WARNING: Removed duplicated region for block: B:75:0x0185 A[Catch:{ all -> 0x0197, Throwable -> 0x019c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] a(java.lang.String r19, byte[] r20, com.tencent.bugly.proguard.al r21, java.util.Map<java.lang.String, java.lang.String> r22) {
        /*
            r18 = this;
            if (r19 != 0) goto L_0x000d
            java.lang.String r4 = "Failed for no URL."
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]
            com.tencent.bugly.proguard.an.e(r4, r5)
            r4 = 0
        L_0x000c:
            return r4
        L_0x000d:
            r7 = 0
            r8 = 0
            if (r20 != 0) goto L_0x005c
            r4 = 0
        L_0x0013:
            java.lang.String r6 = "request: %s, send: %d (pid=%d | tid=%d)"
            r9 = 4
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r10 = 0
            r9[r10] = r19
            r10 = 1
            java.lang.Long r11 = java.lang.Long.valueOf(r4)
            r9[r10] = r11
            r10 = 2
            int r11 = android.os.Process.myPid()
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            r9[r10] = r11
            r10 = 3
            int r11 = android.os.Process.myTid()
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            r9[r10] = r11
            com.tencent.bugly.proguard.an.c(r6, r9)
            r6 = 0
            r9 = r19
        L_0x003f:
            r10 = 1
            if (r7 >= r10) goto L_0x01bd
            r10 = 1
            if (r8 >= r10) goto L_0x01bd
            if (r6 == 0) goto L_0x0061
            r6 = 0
        L_0x0048:
            r0 = r18
            android.content.Context r10 = r0.a
            java.lang.String r10 = com.tencent.bugly.crashreport.common.info.b.f(r10)
            if (r10 != 0) goto L_0x0097
            java.lang.String r10 = "Failed to request for network not avail"
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]
            com.tencent.bugly.proguard.an.d(r10, r11)
            goto L_0x003f
        L_0x005c:
            r0 = r20
            int r4 = r0.length
            long r4 = (long) r4
            goto L_0x0013
        L_0x0061:
            int r7 = r7 + 1
            r10 = 1
            if (r7 <= r10) goto L_0x0048
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "try time: "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r7)
            java.lang.String r10 = r10.toString()
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]
            com.tencent.bugly.proguard.an.c(r10, r11)
            java.util.Random r10 = new java.util.Random
            long r12 = java.lang.System.currentTimeMillis()
            r10.<init>(r12)
            r11 = 10000(0x2710, float:1.4013E-41)
            int r10 = r10.nextInt(r11)
            long r10 = (long) r10
            r12 = 10000(0x2710, double:4.9407E-320)
            long r10 = r10 + r12
            android.os.SystemClock.sleep(r10)
            goto L_0x0048
        L_0x0097:
            r0 = r21
            r0.a(r9, r4, r10)
            r0 = r18
            r1 = r20
            r2 = r22
            java.net.HttpURLConnection r14 = r0.a(r9, r1, r10, r2)
            if (r14 == 0) goto L_0x01a7
            int r12 = r14.getResponseCode()     // Catch:{ IOException -> 0x0179 }
            r10 = 200(0xc8, float:2.8E-43)
            if (r12 != r10) goto L_0x00dd
            r0 = r18
            java.util.Map r10 = r0.b(r14)     // Catch:{ IOException -> 0x0179 }
            r0 = r18
            r0.b = r10     // Catch:{ IOException -> 0x0179 }
            r0 = r18
            byte[] r10 = r0.a(r14)     // Catch:{ IOException -> 0x0179 }
            if (r10 != 0) goto L_0x00cf
            r12 = 0
        L_0x00c4:
            r0 = r21
            r0.a(r12)     // Catch:{ IOException -> 0x0179 }
            r14.disconnect()     // Catch:{ Throwable -> 0x00d2 }
        L_0x00cc:
            r4 = r10
            goto L_0x000c
        L_0x00cf:
            int r11 = r10.length     // Catch:{ IOException -> 0x0179 }
            long r12 = (long) r11
            goto L_0x00c4
        L_0x00d2:
            r4 = move-exception
            boolean r5 = com.tencent.bugly.proguard.an.a(r4)
            if (r5 != 0) goto L_0x00cc
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r4)
            goto L_0x00cc
        L_0x00dd:
            r0 = r18
            boolean r10 = r0.a(r12)     // Catch:{ IOException -> 0x0179 }
            if (r10 == 0) goto L_0x01db
            r10 = 1
            java.lang.String r6 = "Location"
            java.lang.String r11 = r14.getHeaderField(r6)     // Catch:{ IOException -> 0x01c0 }
            if (r11 != 0) goto L_0x011b
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01c0 }
            r6.<init>()     // Catch:{ IOException -> 0x01c0 }
            java.lang.String r11 = "Failed to redirect: %d"
            java.lang.StringBuilder r6 = r6.append(r11)     // Catch:{ IOException -> 0x01c0 }
            java.lang.StringBuilder r6 = r6.append(r12)     // Catch:{ IOException -> 0x01c0 }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x01c0 }
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ IOException -> 0x01c0 }
            com.tencent.bugly.proguard.an.e(r6, r11)     // Catch:{ IOException -> 0x01c0 }
            r4 = 0
            r14.disconnect()     // Catch:{ Throwable -> 0x010f }
            goto L_0x000c
        L_0x010f:
            r5 = move-exception
            boolean r6 = com.tencent.bugly.proguard.an.a(r5)
            if (r6 != 0) goto L_0x000c
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r5)
            goto L_0x000c
        L_0x011b:
            int r8 = r8 + 1
            r7 = 0
            java.lang.String r6 = "redirect code: %d ,to:%s"
            r9 = 2
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ IOException -> 0x01cc }
            r13 = 0
            java.lang.Integer r15 = java.lang.Integer.valueOf(r12)     // Catch:{ IOException -> 0x01cc }
            r9[r13] = r15     // Catch:{ IOException -> 0x01cc }
            r13 = 1
            r9[r13] = r11     // Catch:{ IOException -> 0x01cc }
            com.tencent.bugly.proguard.an.c(r6, r9)     // Catch:{ IOException -> 0x01cc }
            r6 = r10
            r9 = r11
            r16 = r7
            r7 = r8
            r8 = r16
        L_0x0138:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01d9 }
            r10.<init>()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r11 = "response code "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ IOException -> 0x01d9 }
            java.lang.StringBuilder r10 = r10.append(r12)     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r10 = r10.toString()     // Catch:{ IOException -> 0x01d9 }
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ IOException -> 0x01d9 }
            com.tencent.bugly.proguard.an.d(r10, r11)     // Catch:{ IOException -> 0x01d9 }
            int r10 = r14.getContentLength()     // Catch:{ IOException -> 0x01d9 }
            long r10 = (long) r10     // Catch:{ IOException -> 0x01d9 }
            r12 = 0
            int r12 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r12 >= 0) goto L_0x015f
            r10 = 0
        L_0x015f:
            r0 = r21
            r0.a(r10)     // Catch:{ IOException -> 0x01d9 }
            r14.disconnect()     // Catch:{ Throwable -> 0x016e }
        L_0x0167:
            r16 = r7
            r7 = r8
            r8 = r16
            goto L_0x003f
        L_0x016e:
            r10 = move-exception
            boolean r11 = com.tencent.bugly.proguard.an.a(r10)
            if (r11 != 0) goto L_0x0167
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r10)
            goto L_0x0167
        L_0x0179:
            r10 = move-exception
            r16 = r8
            r8 = r7
            r7 = r16
        L_0x017f:
            boolean r11 = com.tencent.bugly.proguard.an.a(r10)     // Catch:{ all -> 0x0197 }
            if (r11 != 0) goto L_0x0188
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r10)     // Catch:{ all -> 0x0197 }
        L_0x0188:
            r14.disconnect()     // Catch:{ Throwable -> 0x018c }
            goto L_0x0167
        L_0x018c:
            r10 = move-exception
            boolean r11 = com.tencent.bugly.proguard.an.a(r10)
            if (r11 != 0) goto L_0x0167
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r10)
            goto L_0x0167
        L_0x0197:
            r4 = move-exception
            r14.disconnect()     // Catch:{ Throwable -> 0x019c }
        L_0x019b:
            throw r4
        L_0x019c:
            r5 = move-exception
            boolean r6 = com.tencent.bugly.proguard.an.a(r5)
            if (r6 != 0) goto L_0x019b
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r5)
            goto L_0x019b
        L_0x01a7:
            java.lang.String r10 = "Failed to execute post."
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]
            com.tencent.bugly.proguard.an.c(r10, r11)
            r10 = 0
            r0 = r21
            r0.a(r10)
            r16 = r8
            r8 = r7
            r7 = r16
            goto L_0x0167
        L_0x01bd:
            r4 = 0
            goto L_0x000c
        L_0x01c0:
            r6 = move-exception
            r16 = r6
            r6 = r10
            r10 = r16
            r17 = r8
            r8 = r7
            r7 = r17
            goto L_0x017f
        L_0x01cc:
            r6 = move-exception
            r9 = r11
            r16 = r10
            r10 = r6
            r6 = r16
            r17 = r7
            r7 = r8
            r8 = r17
            goto L_0x017f
        L_0x01d9:
            r10 = move-exception
            goto L_0x017f
        L_0x01db:
            r16 = r8
            r8 = r7
            r7 = r16
            goto L_0x0138
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ai.a(java.lang.String, byte[], com.tencent.bugly.proguard.al, java.util.Map):byte[]");
    }

    private Map<String, String> b(HttpURLConnection httpURLConnection) {
        HashMap hashMap = new HashMap();
        Map headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null || headerFields.size() == 0) {
            return null;
        }
        for (String str : headerFields.keySet()) {
            List list = (List) headerFields.get(str);
            if (list.size() >= 1) {
                hashMap.put(str, list.get(0));
            }
        }
        return hashMap;
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.io.BufferedInputStream] */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r2v3, types: [java.io.BufferedInputStream] */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x004d A[SYNTHETIC, Splitter:B:30:0x004d] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] a(java.net.HttpURLConnection r7) {
        /*
            r6 = this;
            r0 = 0
            if (r7 != 0) goto L_0x0004
        L_0x0003:
            return r0
        L_0x0004:
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0058, all -> 0x0048 }
            java.io.InputStream r1 = r7.getInputStream()     // Catch:{ Throwable -> 0x0058, all -> 0x0048 }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0058, all -> 0x0048 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0021 }
            r1.<init>()     // Catch:{ Throwable -> 0x0021 }
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]     // Catch:{ Throwable -> 0x0021 }
        L_0x0016:
            int r4 = r2.read(r3)     // Catch:{ Throwable -> 0x0021 }
            if (r4 <= 0) goto L_0x0036
            r5 = 0
            r1.write(r3, r5, r4)     // Catch:{ Throwable -> 0x0021 }
            goto L_0x0016
        L_0x0021:
            r1 = move-exception
        L_0x0022:
            boolean r3 = com.tencent.bugly.proguard.an.a(r1)     // Catch:{ all -> 0x0056 }
            if (r3 != 0) goto L_0x002b
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x0056 }
        L_0x002b:
            if (r2 == 0) goto L_0x0003
            r2.close()     // Catch:{ Throwable -> 0x0031 }
            goto L_0x0003
        L_0x0031:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0003
        L_0x0036:
            r1.flush()     // Catch:{ Throwable -> 0x0021 }
            byte[] r0 = r1.toByteArray()     // Catch:{ Throwable -> 0x0021 }
            if (r2 == 0) goto L_0x0003
            r2.close()     // Catch:{ Throwable -> 0x0043 }
            goto L_0x0003
        L_0x0043:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0003
        L_0x0048:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x004b:
            if (r2 == 0) goto L_0x0050
            r2.close()     // Catch:{ Throwable -> 0x0051 }
        L_0x0050:
            throw r0
        L_0x0051:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0050
        L_0x0056:
            r0 = move-exception
            goto L_0x004b
        L_0x0058:
            r1 = move-exception
            r2 = r0
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ai.a(java.net.HttpURLConnection):byte[]");
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection a(String str, byte[] bArr, String str2, Map<String, String> map) {
        if (str == null) {
            an.e("destUrl is null.", new Object[0]);
            return null;
        }
        HttpURLConnection a2 = a(str2, str);
        if (a2 == null) {
            an.e("Failed to get HttpURLConnection object.", new Object[0]);
            return null;
        }
        try {
            a2.setRequestProperty("wup_version", "3.0");
            if (map != null && map.size() > 0) {
                for (Entry entry : map.entrySet()) {
                    a2.setRequestProperty((String) entry.getKey(), URLEncoder.encode((String) entry.getValue(), "utf-8"));
                }
            }
            a2.setRequestProperty("A37", URLEncoder.encode(str2, "utf-8"));
            a2.setRequestProperty("A38", URLEncoder.encode(str2, "utf-8"));
            OutputStream outputStream = a2.getOutputStream();
            if (bArr == null) {
                outputStream.write(0);
            } else {
                outputStream.write(bArr);
            }
            return a2;
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            an.e("Failed to upload, please check your network.", new Object[0]);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection a(String str, String str2) {
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(str2);
            if (str == null || !str.toLowerCase(Locale.US).contains("wap")) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection(new Proxy(Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.parseInt(System.getProperty("http.proxyPort")))));
            }
            httpURLConnection.setConnectTimeout(a.MAX_USERDATA_VALUE_LENGTH);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(false);
            return httpURLConnection;
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i) {
        return i == 301 || i == 302 || i == 303 || i == 307;
    }
}
