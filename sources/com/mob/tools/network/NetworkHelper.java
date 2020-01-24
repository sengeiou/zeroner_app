package com.mob.tools.network;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat;
import com.alibaba.android.arouter.utils.Consts;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.UrlEncodedParser;
import com.google.common.net.HttpHeaders;
import com.mob.tools.MobLog;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import com.mob.tools.utils.ReflectHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.text.Typography;
import no.nordicsemi.android.dfu.DfuBaseService;
import org.apache.commons.cli.HelpFormatter;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class NetworkHelper {
    public static int connectionTimeout;
    public static int readTimout;

    public static class NetworkTimeOut {
        public int connectionTimeout;
        public int readTimout;
    }

    public static final class SimpleX509TrustManager implements X509TrustManager {
        private X509TrustManager standardTrustManager;

        public SimpleX509TrustManager(KeyStore keystore) {
            try {
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
                tmf.init(keystore);
                TrustManager[] trustManagers = tmf.getTrustManagers();
                if (trustManagers == null || trustManagers.length == 0) {
                    throw new NoSuchAlgorithmException("no trust manager found.");
                }
                this.standardTrustManager = (X509TrustManager) trustManagers[0];
            } catch (Exception e) {
                MobLog.getInstance().d("failed to initialize the standard trust manager: " + e.getMessage(), new Object[0]);
                this.standardTrustManager = null;
            }
        }

        public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
            if (certificates == null) {
                throw new IllegalArgumentException("there were no certificates.");
            } else if (certificates.length == 1) {
                certificates[0].checkValidity();
            } else if (this.standardTrustManager != null) {
                this.standardTrustManager.checkServerTrusted(certificates, authType);
            } else {
                throw new CertificateException("there were one more certificates but no trust manager found.");
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public String httpGet(String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        long time = System.currentTimeMillis();
        MobLog.getInstance().i("httpGet: " + url, new Object[0]);
        if (values != null) {
            String param = kvPairsToUrl(values);
            if (param.length() > 0) {
                url = url + "?" + param;
            }
        }
        HttpURLConnection conn = getConnection(url, timeout);
        if (headers != null) {
            Iterator i$ = headers.iterator();
            while (i$.hasNext()) {
                KVPair<String> header = (KVPair) i$.next();
                conn.setRequestProperty(header.name, (String) header.value);
            }
        }
        conn.connect();
        int status = conn.getResponseCode();
        if (status == 200) {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));
            for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
                if (sb.length() > 0) {
                    sb.append(10);
                }
                sb.append(txt);
            }
            br.close();
            conn.disconnect();
            String resp = sb.toString();
            MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
            return resp;
        }
        StringBuilder sb2 = new StringBuilder();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8")));
        for (String txt2 = br2.readLine(); txt2 != null; txt2 = br2.readLine()) {
            if (sb2.length() > 0) {
                sb2.append(10);
            }
            sb2.append(txt2);
        }
        br2.close();
        conn.disconnect();
        HashMap<String, Object> errMap = new HashMap<>();
        errMap.put("error", sb2.toString());
        errMap.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(status));
        throw new Throwable(new Hashon().fromHashMap(errMap));
    }

    public String downloadCache(Context context, String url, String cacheFolder, boolean skipIfCached, NetworkTimeOut timeout) throws Throwable {
        String[] arr$;
        long time = System.currentTimeMillis();
        MobLog.getInstance().i("downloading: " + url, new Object[0]);
        if (skipIfCached) {
            File cache = new File(R.getCachePath(context, cacheFolder), Data.MD5(url));
            if (skipIfCached && cache.exists()) {
                MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
                return cache.getAbsolutePath();
            }
        }
        HttpURLConnection conn = getConnection(url, timeout);
        conn.connect();
        int status = conn.getResponseCode();
        if (status == 200) {
            String name = null;
            Map<String, List<String>> map = conn.getHeaderFields();
            if (map != null) {
                List<String> headers = (List) map.get(HttpHeaders.CONTENT_DISPOSITION);
                if (headers != null && headers.size() > 0) {
                    for (String part : ((String) headers.get(0)).split(";")) {
                        if (part.trim().startsWith("filename")) {
                            name = part.split("=")[1];
                            if (name.startsWith("\"") && name.endsWith("\"")) {
                                name = name.substring(1, name.length() - 1);
                            }
                        }
                    }
                }
            }
            if (name == null) {
                name = Data.MD5(url);
                if (map != null) {
                    List<String> headers2 = (List) map.get("Content-Type");
                    if (headers2 != null && headers2.size() > 0) {
                        String value = (String) headers2.get(0);
                        String value2 = value == null ? "" : value.trim();
                        if (value2.startsWith("image/")) {
                            String type = value2.substring("image/".length());
                            StringBuilder append = new StringBuilder().append(name).append(Consts.DOT);
                            if ("jpeg".equals(type)) {
                                type = "jpg";
                            }
                            name = append.append(type).toString();
                        } else {
                            int index = url.lastIndexOf(47);
                            String lastPart = null;
                            if (index > 0) {
                                lastPart = url.substring(index + 1);
                            }
                            if (lastPart != null && lastPart.length() > 0) {
                                int dot = lastPart.lastIndexOf(46);
                                if (dot > 0 && lastPart.length() - dot < 10) {
                                    name = name + lastPart.substring(dot);
                                }
                            }
                        }
                    }
                }
            }
            File cache2 = new File(R.getCachePath(context, cacheFolder), name);
            if (!skipIfCached || !cache2.exists()) {
                if (!cache2.getParentFile().exists()) {
                    cache2.getParentFile().mkdirs();
                }
                if (cache2.exists()) {
                    cache2.delete();
                }
                try {
                    InputStream is = conn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(cache2);
                    byte[] buf = new byte[1024];
                    for (int len = is.read(buf); len > 0; len = is.read(buf)) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    is.close();
                    fos.close();
                    conn.disconnect();
                    MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
                    return cache2.getAbsolutePath();
                } catch (Throwable t) {
                    if (cache2.exists()) {
                        cache2.delete();
                    }
                    throw t;
                }
            } else {
                conn.disconnect();
                MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
                return cache2.getAbsolutePath();
            }
        } else {
            StringBuilder sb = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
                if (sb.length() > 0) {
                    sb.append(10);
                }
                sb.append(txt);
            }
            br.close();
            conn.disconnect();
            HashMap<String, Object> errMap = new HashMap<>();
            errMap.put("error", sb.toString());
            errMap.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(status));
            throw new Throwable(new Hashon().fromHashMap(errMap));
        }
    }

    public void rawGet(String url, RawNetworkCallback callback, NetworkTimeOut timeout) throws Throwable {
        rawGet(url, null, callback, timeout);
    }

    public void rawGet(String url, ArrayList<KVPair<String>> headers, RawNetworkCallback callback, NetworkTimeOut timeout) throws Throwable {
        long time = System.currentTimeMillis();
        MobLog.getInstance().i("rawGet: " + url, new Object[0]);
        HttpURLConnection conn = getConnection(url, timeout);
        if (headers != null) {
            Iterator i$ = headers.iterator();
            while (i$.hasNext()) {
                KVPair<String> header = (KVPair) i$.next();
                conn.setRequestProperty(header.name, (String) header.value);
            }
        }
        conn.connect();
        int status = conn.getResponseCode();
        if (status == 200) {
            if (callback != null) {
                callback.onResponse(conn.getInputStream());
            }
            conn.disconnect();
            MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
            return;
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8")));
        for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
            if (sb.length() > 0) {
                sb.append(10);
            }
            sb.append(txt);
        }
        br.close();
        conn.disconnect();
        HashMap<String, Object> errMap = new HashMap<>();
        errMap.put("error", sb.toString());
        errMap.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(status));
        throw new Throwable(new Hashon().fromHashMap(errMap));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x005b, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x005c, code lost:
        r0.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x005f, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rawGet(java.lang.String r10, com.mob.tools.network.HttpResponseCallback r11, com.mob.tools.network.NetworkHelper.NetworkTimeOut r12) throws java.lang.Throwable {
        /*
            r9 = this;
            r8 = 0
            long r2 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r4 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "rawGet: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r10)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r8]
            r4.i(r5, r6)
            java.net.HttpURLConnection r0 = r9.getConnection(r10, r12)
            r0.connect()
            if (r11 == 0) goto L_0x0060
            com.mob.tools.network.HttpConnectionImpl23 r4 = new com.mob.tools.network.HttpConnectionImpl23     // Catch:{ Throwable -> 0x0059 }
            r4.<init>(r0)     // Catch:{ Throwable -> 0x0059 }
            r11.onResponse(r4)     // Catch:{ Throwable -> 0x0059 }
            r0.disconnect()
        L_0x0036:
            com.mob.tools.log.NLog r4 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "use time: "
            java.lang.StringBuilder r5 = r5.append(r6)
            long r6 = java.lang.System.currentTimeMillis()
            long r6 = r6 - r2
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r8]
            r4.i(r5, r6)
            return
        L_0x0059:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x005b }
        L_0x005b:
            r4 = move-exception
            r0.disconnect()
            throw r4
        L_0x0060:
            r0.disconnect()
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.rawGet(java.lang.String, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    public String jsonPost(String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        long time = System.currentTimeMillis();
        MobLog.getInstance().i("jsonPost: " + url, new Object[0]);
        HttpURLConnection conn = getConnection(url, timeout);
        conn.setDoOutput(true);
        conn.setChunkedStreamingMode(0);
        conn.setRequestProperty("content-type", "application/json");
        if (headers != null) {
            Iterator i$ = headers.iterator();
            while (i$.hasNext()) {
                KVPair<String> header = (KVPair) i$.next();
                conn.setRequestProperty(header.name, (String) header.value);
            }
        }
        StringPart sp = new StringPart();
        if (values != null) {
            HashMap<String, Object> errMap = new HashMap<>();
            Iterator i$2 = values.iterator();
            while (i$2.hasNext()) {
                KVPair<String> p = (KVPair) i$2.next();
                errMap.put(p.name, p.value);
            }
            sp.append(new Hashon().fromHashMap(errMap));
        }
        conn.connect();
        OutputStream os = conn.getOutputStream();
        InputStream is = sp.toInputStream();
        byte[] buf = new byte[65536];
        for (int len = is.read(buf); len > 0; len = is.read(buf)) {
            os.write(buf, 0, len);
        }
        os.flush();
        is.close();
        os.close();
        int status = conn.getResponseCode();
        if (status == 200 || status == 201) {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));
            for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
                if (sb.length() > 0) {
                    sb.append(10);
                }
                sb.append(txt);
            }
            br.close();
            conn.disconnect();
            String resp = sb.toString();
            MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
            return resp;
        }
        StringBuilder sb2 = new StringBuilder();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8")));
        for (String txt2 = br2.readLine(); txt2 != null; txt2 = br2.readLine()) {
            if (sb2.length() > 0) {
                sb2.append(10);
            }
            sb2.append(txt2);
        }
        br2.close();
        conn.disconnect();
        HashMap<String, Object> errMap2 = new HashMap<>();
        errMap2.put("error", sb2.toString());
        errMap2.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(status));
        throw new Throwable(new Hashon().fromHashMap(errMap2));
    }

    public String httpPost(String url, ArrayList<KVPair<String>> values, KVPair<String> file, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        return httpPost(url, values, file, headers, 0, timeout);
    }

    public String httpPost(String url, ArrayList<KVPair<String>> values, KVPair<String> file, ArrayList<KVPair<String>> headers, int chunkLength, NetworkTimeOut timeout) throws Throwable {
        ArrayList<KVPair<String>> files = new ArrayList<>();
        if (!(file == null || file.value == null || !new File((String) file.value).exists())) {
            files.add(file);
        }
        return httpPostFiles(url, values, files, headers, chunkLength, timeout);
    }

    public String httpPostFiles(String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> files, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        return httpPostFiles(url, values, files, headers, 0, timeout);
    }

    public String httpPostFiles(String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> files, ArrayList<KVPair<String>> headers, int chunkLength, NetworkTimeOut timeout) throws Throwable {
        final HashMap<String, String> tmpMap = new HashMap<>();
        httpPost(url, values, files, headers, chunkLength, new HttpResponseCallback() {
            public void onResponse(HttpConnection conn) throws Throwable {
                int status = conn.getResponseCode();
                if (status == 200 || status == 201) {
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));
                    for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
                        if (sb.length() > 0) {
                            sb.append(10);
                        }
                        sb.append(txt);
                    }
                    br.close();
                    tmpMap.put("resp", sb.toString());
                    return;
                }
                StringBuilder sb2 = new StringBuilder();
                BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8")));
                for (String txt2 = br2.readLine(); txt2 != null; txt2 = br2.readLine()) {
                    if (sb2.length() > 0) {
                        sb2.append(10);
                    }
                    sb2.append(txt2);
                }
                br2.close();
                HashMap<String, Object> errMap = new HashMap<>();
                errMap.put("error", sb2.toString());
                errMap.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(status));
                throw new Throwable(new Hashon().fromHashMap(errMap));
            }
        }, timeout);
        return (String) tmpMap.get("resp");
    }

    public void httpPost(String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> files, ArrayList<KVPair<String>> headers, HttpResponseCallback callback, NetworkTimeOut timeout) throws Throwable {
        httpPost(url, values, files, headers, 0, callback, timeout);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00f7, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00f8, code lost:
        r5.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00fb, code lost:
        throw r13;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void httpPost(java.lang.String r21, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r22, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r23, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r24, int r25, com.mob.tools.network.HttpResponseCallback r26, com.mob.tools.network.NetworkHelper.NetworkTimeOut r27) throws java.lang.Throwable {
        /*
            r20 = this;
            long r14 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r13 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            java.lang.String r17 = "httpPost: "
            java.lang.StringBuilder r16 = r16.append(r17)
            r0 = r16
            r1 = r21
            java.lang.StringBuilder r16 = r0.append(r1)
            java.lang.String r16 = r16.toString()
            r17 = 0
            r0 = r17
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r17 = r0
            r0 = r16
            r1 = r17
            r13.i(r0, r1)
            r0 = r20
            r1 = r21
            r2 = r27
            java.net.HttpURLConnection r5 = r0.getConnection(r1, r2)
            r13 = 1
            r5.setDoOutput(r13)
            if (r23 == 0) goto L_0x0078
            int r13 = r23.size()
            if (r13 <= 0) goto L_0x0078
            r0 = r20
            r1 = r21
            r2 = r22
            r3 = r23
            com.mob.tools.network.HTTPPart r11 = r0.getFilePostHTTPPart(r5, r1, r2, r3)
            if (r25 < 0) goto L_0x0058
            r0 = r25
            r5.setChunkedStreamingMode(r0)
        L_0x0058:
            if (r24 == 0) goto L_0x008d
            java.util.Iterator r7 = r24.iterator()
        L_0x005e:
            boolean r13 = r7.hasNext()
            if (r13 == 0) goto L_0x008d
            java.lang.Object r6 = r7.next()
            com.mob.tools.network.KVPair r6 = (com.mob.tools.network.KVPair) r6
            java.lang.String r0 = r6.name
            r16 = r0
            T r13 = r6.value
            java.lang.String r13 = (java.lang.String) r13
            r0 = r16
            r5.setRequestProperty(r0, r13)
            goto L_0x005e
        L_0x0078:
            r0 = r20
            r1 = r21
            r2 = r22
            com.mob.tools.network.HTTPPart r11 = r0.getTextPostHTTPPart(r5, r1, r2)
            long r16 = r11.length()
            r0 = r16
            int r13 = (int) r0
            r5.setFixedLengthStreamingMode(r13)
            goto L_0x0058
        L_0x008d:
            r5.connect()
            java.io.OutputStream r10 = r5.getOutputStream()
            java.io.InputStream r8 = r11.toInputStream()
            r13 = 65536(0x10000, float:9.18355E-41)
            byte[] r4 = new byte[r13]
            int r9 = r8.read(r4)
        L_0x00a0:
            if (r9 <= 0) goto L_0x00ab
            r13 = 0
            r10.write(r4, r13, r9)
            int r9 = r8.read(r4)
            goto L_0x00a0
        L_0x00ab:
            r10.flush()
            r8.close()
            r10.close()
            if (r26 == 0) goto L_0x00fc
            com.mob.tools.network.HttpConnectionImpl23 r13 = new com.mob.tools.network.HttpConnectionImpl23     // Catch:{ Throwable -> 0x00f5 }
            r13.<init>(r5)     // Catch:{ Throwable -> 0x00f5 }
            r0 = r26
            r0.onResponse(r13)     // Catch:{ Throwable -> 0x00f5 }
            r5.disconnect()
        L_0x00c3:
            com.mob.tools.log.NLog r13 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            java.lang.String r17 = "use time: "
            java.lang.StringBuilder r16 = r16.append(r17)
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r14
            r0 = r16
            r1 = r18
            java.lang.StringBuilder r16 = r0.append(r1)
            java.lang.String r16 = r16.toString()
            r17 = 0
            r0 = r17
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r17 = r0
            r0 = r16
            r1 = r17
            r13.i(r0, r1)
            return
        L_0x00f5:
            r12 = move-exception
            throw r12     // Catch:{ all -> 0x00f7 }
        L_0x00f7:
            r13 = move-exception
            r5.disconnect()
            throw r13
        L_0x00fc:
            r5.disconnect()
            goto L_0x00c3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.httpPost(java.lang.String, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList, int, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    private HTTPPart getFilePostHTTPPart(HttpURLConnection conn, String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> files) throws Throwable {
        String boundary = UUID.randomUUID().toString();
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        MultiPart mp = new MultiPart();
        StringPart sp = new StringPart();
        if (values != null) {
            Iterator i$ = values.iterator();
            while (i$.hasNext()) {
                KVPair<String> value = (KVPair) i$.next();
                sp.append(HelpFormatter.DEFAULT_LONG_OPT_PREFIX).append(boundary).append("\r\n");
                sp.append("Content-Disposition: form-data; name=\"").append(value.name).append("\"\r\n\r\n");
                sp.append((String) value.value).append("\r\n");
            }
        }
        mp.append(sp);
        Iterator i$2 = files.iterator();
        while (i$2.hasNext()) {
            KVPair<String> file = (KVPair) i$2.next();
            StringPart sp2 = new StringPart();
            File imageFile = new File((String) file.value);
            sp2.append(HelpFormatter.DEFAULT_LONG_OPT_PREFIX).append(boundary).append("\r\n");
            sp2.append("Content-Disposition: form-data; name=\"").append(file.name).append("\"; filename=\"").append(imageFile.getName()).append("\"\r\n");
            String mime = URLConnection.getFileNameMap().getContentTypeFor((String) file.value);
            if (mime == null || mime.length() <= 0) {
                if (((String) file.value).toLowerCase().endsWith("jpg") || ((String) file.value).toLowerCase().endsWith("jpeg")) {
                    mime = "image/jpeg";
                } else if (((String) file.value).toLowerCase().endsWith("png")) {
                    mime = "image/png";
                } else if (((String) file.value).toLowerCase().endsWith("gif")) {
                    mime = "image/gif";
                } else {
                    FileInputStream fis = new FileInputStream((String) file.value);
                    mime = URLConnection.guessContentTypeFromStream(fis);
                    fis.close();
                    if (mime == null || mime.length() <= 0) {
                        mime = DfuBaseService.MIME_TYPE_OCTET_STREAM;
                    }
                }
            }
            sp2.append("Content-Type: ").append(mime).append("\r\n\r\n");
            mp.append(sp2);
            FilePart fp = new FilePart();
            fp.setFile((String) file.value);
            mp.append(fp);
            StringPart sp3 = new StringPart();
            sp3.append("\r\n");
            mp.append(sp3);
        }
        StringPart sp4 = new StringPart();
        sp4.append(HelpFormatter.DEFAULT_LONG_OPT_PREFIX).append(boundary).append("--\r\n");
        mp.append(sp4);
        return mp;
    }

    private HTTPPart getTextPostHTTPPart(HttpURLConnection conn, String url, ArrayList<KVPair<String>> values) throws Throwable {
        conn.setRequestProperty("Content-Type", UrlEncodedParser.CONTENT_TYPE);
        StringPart sp = new StringPart();
        if (values != null) {
            sp.append(kvPairsToUrl(values));
        }
        return sp;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00e2, code lost:
        r21 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00e3, code lost:
        if (r10 != null) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r10.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00e8, code lost:
        r6.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00eb, code lost:
        throw r21;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rawPost(java.lang.String r27, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r28, com.mob.tools.network.HTTPPart r29, com.mob.tools.network.RawNetworkCallback r30, com.mob.tools.network.NetworkHelper.NetworkTimeOut r31) throws java.lang.Throwable {
        /*
            r26 = this;
            long r18 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r21 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r22 = new java.lang.StringBuilder
            r22.<init>()
            java.lang.String r23 = "rawpost: "
            java.lang.StringBuilder r22 = r22.append(r23)
            r0 = r22
            r1 = r27
            java.lang.StringBuilder r22 = r0.append(r1)
            java.lang.String r22 = r22.toString()
            r23 = 0
            r0 = r23
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r23 = r0
            r21.i(r22, r23)
            r0 = r26
            r1 = r27
            r2 = r31
            java.net.HttpURLConnection r6 = r0.getConnection(r1, r2)
            r21 = 1
            r0 = r21
            r6.setDoOutput(r0)
            r21 = 0
            r0 = r21
            r6.setChunkedStreamingMode(r0)
            if (r28 == 0) goto L_0x0067
            java.util.Iterator r9 = r28.iterator()
        L_0x0049:
            boolean r21 = r9.hasNext()
            if (r21 == 0) goto L_0x0067
            java.lang.Object r8 = r9.next()
            com.mob.tools.network.KVPair r8 = (com.mob.tools.network.KVPair) r8
            java.lang.String r0 = r8.name
            r22 = r0
            T r0 = r8.value
            r21 = r0
            java.lang.String r21 = (java.lang.String) r21
            r0 = r22
            r1 = r21
            r6.setRequestProperty(r0, r1)
            goto L_0x0049
        L_0x0067:
            r6.connect()
            java.io.OutputStream r14 = r6.getOutputStream()
            java.io.InputStream r11 = r29.toInputStream()
            r21 = 65536(0x10000, float:9.18355E-41)
            r0 = r21
            byte[] r5 = new byte[r0]
            int r13 = r11.read(r5)
        L_0x007c:
            if (r13 <= 0) goto L_0x008a
            r21 = 0
            r0 = r21
            r14.write(r5, r0, r13)
            int r13 = r11.read(r5)
            goto L_0x007c
        L_0x008a:
            r14.flush()
            r11.close()
            r14.close()
            int r16 = r6.getResponseCode()
            r21 = 200(0xc8, float:2.8E-43)
            r0 = r16
            r1 = r21
            if (r0 != r1) goto L_0x00f0
            if (r30 == 0) goto L_0x00ec
            java.io.InputStream r10 = r6.getInputStream()
            r0 = r30
            r0.onResponse(r10)     // Catch:{ Throwable -> 0x00e0 }
            if (r10 == 0) goto L_0x00af
            r10.close()     // Catch:{ Throwable -> 0x0163 }
        L_0x00af:
            r6.disconnect()
        L_0x00b2:
            com.mob.tools.log.NLog r21 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r22 = new java.lang.StringBuilder
            r22.<init>()
            java.lang.String r23 = "use time: "
            java.lang.StringBuilder r22 = r22.append(r23)
            long r24 = java.lang.System.currentTimeMillis()
            long r24 = r24 - r18
            r0 = r22
            r1 = r24
            java.lang.StringBuilder r22 = r0.append(r1)
            java.lang.String r22 = r22.toString()
            r23 = 0
            r0 = r23
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r23 = r0
            r21.i(r22, r23)
            return
        L_0x00e0:
            r17 = move-exception
            throw r17     // Catch:{ all -> 0x00e2 }
        L_0x00e2:
            r21 = move-exception
            if (r10 == 0) goto L_0x00e8
            r10.close()     // Catch:{ Throwable -> 0x0166 }
        L_0x00e8:
            r6.disconnect()
            throw r21
        L_0x00ec:
            r6.disconnect()
            goto L_0x00b2
        L_0x00f0:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.io.InputStreamReader r12 = new java.io.InputStreamReader
            java.io.InputStream r21 = r6.getErrorStream()
            java.lang.String r22 = "utf-8"
            java.nio.charset.Charset r22 = java.nio.charset.Charset.forName(r22)
            r0 = r21
            r1 = r22
            r12.<init>(r0, r1)
            java.io.BufferedReader r4 = new java.io.BufferedReader
            r4.<init>(r12)
            java.lang.String r20 = r4.readLine()
        L_0x0112:
            if (r20 == 0) goto L_0x012b
            int r21 = r15.length()
            if (r21 <= 0) goto L_0x0121
            r21 = 10
            r0 = r21
            r15.append(r0)
        L_0x0121:
            r0 = r20
            r15.append(r0)
            java.lang.String r20 = r4.readLine()
            goto L_0x0112
        L_0x012b:
            r4.close()
            r6.disconnect()
            java.util.HashMap r7 = new java.util.HashMap
            r7.<init>()
            java.lang.String r21 = "error"
            java.lang.String r22 = r15.toString()
            r0 = r21
            r1 = r22
            r7.put(r0, r1)
            java.lang.String r21 = "status"
            java.lang.Integer r22 = java.lang.Integer.valueOf(r16)
            r0 = r21
            r1 = r22
            r7.put(r0, r1)
            java.lang.Throwable r21 = new java.lang.Throwable
            com.mob.tools.utils.Hashon r22 = new com.mob.tools.utils.Hashon
            r22.<init>()
            r0 = r22
            java.lang.String r22 = r0.fromHashMap(r7)
            r21.<init>(r22)
            throw r21
        L_0x0163:
            r21 = move-exception
            goto L_0x00af
        L_0x0166:
            r22 = move-exception
            goto L_0x00e8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.rawPost(java.lang.String, java.util.ArrayList, com.mob.tools.network.HTTPPart, com.mob.tools.network.RawNetworkCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00b9, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00ba, code lost:
        r5.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00bd, code lost:
        throw r14;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rawPost(java.lang.String r19, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r20, com.mob.tools.network.HTTPPart r21, com.mob.tools.network.HttpResponseCallback r22, com.mob.tools.network.NetworkHelper.NetworkTimeOut r23) throws java.lang.Throwable {
        /*
            r18 = this;
            long r12 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r14 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r16 = "rawpost: "
            java.lang.StringBuilder r15 = r15.append(r16)
            r0 = r19
            java.lang.StringBuilder r15 = r15.append(r0)
            java.lang.String r15 = r15.toString()
            r16 = 0
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r14.i(r15, r16)
            r0 = r18
            r1 = r19
            r2 = r23
            java.net.HttpURLConnection r5 = r0.getConnection(r1, r2)
            r14 = 1
            r5.setDoOutput(r14)
            r14 = 0
            r5.setChunkedStreamingMode(r14)
            if (r20 == 0) goto L_0x0057
            java.util.Iterator r7 = r20.iterator()
        L_0x0041:
            boolean r14 = r7.hasNext()
            if (r14 == 0) goto L_0x0057
            java.lang.Object r6 = r7.next()
            com.mob.tools.network.KVPair r6 = (com.mob.tools.network.KVPair) r6
            java.lang.String r15 = r6.name
            T r14 = r6.value
            java.lang.String r14 = (java.lang.String) r14
            r5.setRequestProperty(r15, r14)
            goto L_0x0041
        L_0x0057:
            r5.connect()
            java.io.OutputStream r10 = r5.getOutputStream()
            java.io.InputStream r8 = r21.toInputStream()
            r14 = 65536(0x10000, float:9.18355E-41)
            byte[] r4 = new byte[r14]
            int r9 = r8.read(r4)
        L_0x006a:
            if (r9 <= 0) goto L_0x0075
            r14 = 0
            r10.write(r4, r14, r9)
            int r9 = r8.read(r4)
            goto L_0x006a
        L_0x0075:
            r10.flush()
            r8.close()
            r10.close()
            if (r22 == 0) goto L_0x00be
            com.mob.tools.network.HttpConnectionImpl23 r14 = new com.mob.tools.network.HttpConnectionImpl23     // Catch:{ Throwable -> 0x00b7 }
            r14.<init>(r5)     // Catch:{ Throwable -> 0x00b7 }
            r0 = r22
            r0.onResponse(r14)     // Catch:{ Throwable -> 0x00b7 }
            r5.disconnect()
        L_0x008d:
            com.mob.tools.log.NLog r14 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r16 = "use time: "
            java.lang.StringBuilder r15 = r15.append(r16)
            long r16 = java.lang.System.currentTimeMillis()
            long r16 = r16 - r12
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r15 = r15.toString()
            r16 = 0
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r14.i(r15, r16)
            return
        L_0x00b7:
            r11 = move-exception
            throw r11     // Catch:{ all -> 0x00b9 }
        L_0x00b9:
            r14 = move-exception
            r5.disconnect()
            throw r14
        L_0x00be:
            r5.disconnect()
            goto L_0x008d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.rawPost(java.lang.String, java.util.ArrayList, com.mob.tools.network.HTTPPart, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0112, code lost:
        r16 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0113, code lost:
        r5.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0116, code lost:
        throw r16;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x00be A[LOOP:1: B:14:0x00bc->B:15:0x00be, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00d5 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x007a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void getHttpPostResponse(java.lang.String r21, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r22, com.mob.tools.network.KVPair<java.lang.String> r23, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r24, com.mob.tools.network.HttpResponseCallback r25, com.mob.tools.network.NetworkHelper.NetworkTimeOut r26) throws java.lang.Throwable {
        /*
            r20 = this;
            long r14 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r16 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r17 = new java.lang.StringBuilder
            r17.<init>()
            java.lang.String r18 = "httpPost: "
            java.lang.StringBuilder r17 = r17.append(r18)
            r0 = r17
            r1 = r21
            java.lang.StringBuilder r17 = r0.append(r1)
            java.lang.String r17 = r17.toString()
            r18 = 0
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r18 = r0
            r16.i(r17, r18)
            r0 = r20
            r1 = r21
            r2 = r26
            java.net.HttpURLConnection r5 = r0.getConnection(r1, r2)
            r16 = 1
            r0 = r16
            r5.setDoOutput(r0)
            r16 = 0
            r0 = r16
            r5.setChunkedStreamingMode(r0)
            if (r23 == 0) goto L_0x009c
            r0 = r23
            T r0 = r0.value
            r16 = r0
            if (r16 == 0) goto L_0x009c
            java.io.File r17 = new java.io.File
            r0 = r23
            T r0 = r0.value
            r16 = r0
            java.lang.String r16 = (java.lang.String) r16
            r0 = r17
            r1 = r16
            r0.<init>(r1)
            boolean r16 = r17.exists()
            if (r16 == 0) goto L_0x009c
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r0 = r23
            r6.add(r0)
            r0 = r20
            r1 = r21
            r2 = r22
            com.mob.tools.network.HTTPPart r12 = r0.getFilePostHTTPPart(r5, r1, r2, r6)
        L_0x0078:
            if (r24 == 0) goto L_0x00a7
            java.util.Iterator r8 = r24.iterator()
        L_0x007e:
            boolean r16 = r8.hasNext()
            if (r16 == 0) goto L_0x00a7
            java.lang.Object r7 = r8.next()
            com.mob.tools.network.KVPair r7 = (com.mob.tools.network.KVPair) r7
            java.lang.String r0 = r7.name
            r17 = r0
            T r0 = r7.value
            r16 = r0
            java.lang.String r16 = (java.lang.String) r16
            r0 = r17
            r1 = r16
            r5.setRequestProperty(r0, r1)
            goto L_0x007e
        L_0x009c:
            r0 = r20
            r1 = r21
            r2 = r22
            com.mob.tools.network.HTTPPart r12 = r0.getTextPostHTTPPart(r5, r1, r2)
            goto L_0x0078
        L_0x00a7:
            r5.connect()
            java.io.OutputStream r11 = r5.getOutputStream()
            java.io.InputStream r9 = r12.toInputStream()
            r16 = 65536(0x10000, float:9.18355E-41)
            r0 = r16
            byte[] r4 = new byte[r0]
            int r10 = r9.read(r4)
        L_0x00bc:
            if (r10 <= 0) goto L_0x00ca
            r16 = 0
            r0 = r16
            r11.write(r4, r0, r10)
            int r10 = r9.read(r4)
            goto L_0x00bc
        L_0x00ca:
            r11.flush()
            r9.close()
            r11.close()
            if (r25 == 0) goto L_0x0117
            com.mob.tools.network.HttpConnectionImpl23 r16 = new com.mob.tools.network.HttpConnectionImpl23     // Catch:{ Throwable -> 0x0110 }
            r0 = r16
            r0.<init>(r5)     // Catch:{ Throwable -> 0x0110 }
            r0 = r25
            r1 = r16
            r0.onResponse(r1)     // Catch:{ Throwable -> 0x0110 }
            r5.disconnect()
        L_0x00e6:
            com.mob.tools.log.NLog r16 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r17 = new java.lang.StringBuilder
            r17.<init>()
            java.lang.String r18 = "use time: "
            java.lang.StringBuilder r17 = r17.append(r18)
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r14
            java.lang.StringBuilder r17 = r17.append(r18)
            java.lang.String r17 = r17.toString()
            r18 = 0
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r18 = r0
            r16.i(r17, r18)
            return
        L_0x0110:
            r13 = move-exception
            throw r13     // Catch:{ all -> 0x0112 }
        L_0x0112:
            r16 = move-exception
            r5.disconnect()
            throw r16
        L_0x0117:
            r5.disconnect()
            goto L_0x00e6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.getHttpPostResponse(java.lang.String, java.util.ArrayList, com.mob.tools.network.KVPair, java.util.ArrayList, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    public String httpPut(String url, ArrayList<KVPair<String>> values, KVPair<String> file, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        long time = System.currentTimeMillis();
        MobLog.getInstance().i("httpPut: " + url, new Object[0]);
        if (values != null) {
            String param = kvPairsToUrl(values);
            if (param.length() > 0) {
                url = url + "?" + param;
            }
        }
        HttpURLConnection conn = getConnection(url, timeout);
        conn.setDoOutput(true);
        conn.setChunkedStreamingMode(0);
        conn.setRequestMethod(HttpMethods.PUT);
        conn.setRequestProperty("Content-Type", DfuBaseService.MIME_TYPE_OCTET_STREAM);
        if (headers != null) {
            Iterator i$ = headers.iterator();
            while (i$.hasNext()) {
                KVPair<String> header = (KVPair) i$.next();
                conn.setRequestProperty(header.name, (String) header.value);
            }
        }
        conn.connect();
        OutputStream os = conn.getOutputStream();
        FilePart fp = new FilePart();
        fp.setFile((String) file.value);
        InputStream is = fp.toInputStream();
        byte[] buf = new byte[65536];
        for (int len = is.read(buf); len > 0; len = is.read(buf)) {
            os.write(buf, 0, len);
        }
        os.flush();
        is.close();
        os.close();
        int status = conn.getResponseCode();
        if (status == 200 || status == 201) {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));
            for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
                if (sb.length() > 0) {
                    sb.append(10);
                }
                sb.append(txt);
            }
            br.close();
            conn.disconnect();
            String resp = sb.toString();
            MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
            return resp;
        }
        StringBuilder sb2 = new StringBuilder();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8")));
        for (String txt2 = br2.readLine(); txt2 != null; txt2 = br2.readLine()) {
            if (sb2.length() > 0) {
                sb2.append(10);
            }
            sb2.append(txt2);
        }
        br2.close();
        HashMap<String, Object> errMap = new HashMap<>();
        errMap.put("error", sb2.toString());
        errMap.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(status));
        throw new Throwable(new Hashon().fromHashMap(errMap));
    }

    public ArrayList<KVPair<String[]>> httpHead(String url, ArrayList<KVPair<String>> values, KVPair<String> kVPair, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        long time = System.currentTimeMillis();
        MobLog.getInstance().i("httpHead: " + url, new Object[0]);
        if (values != null) {
            String param = kvPairsToUrl(values);
            if (param.length() > 0) {
                url = url + "?" + param;
            }
        }
        HttpURLConnection conn = getConnection(url, timeout);
        conn.setRequestMethod(HttpMethods.HEAD);
        if (headers != null) {
            Iterator i$ = headers.iterator();
            while (i$.hasNext()) {
                KVPair<String> header = (KVPair) i$.next();
                conn.setRequestProperty(header.name, (String) header.value);
            }
        }
        conn.connect();
        Map<String, List<String>> map = conn.getHeaderFields();
        ArrayList<KVPair<String[]>> list = new ArrayList<>();
        if (map != null) {
            for (Entry<String, List<String>> ent : map.entrySet()) {
                List<String> value = (List) ent.getValue();
                if (value == null) {
                    KVPair kVPair2 = new KVPair((String) ent.getKey(), new String[0]);
                    list.add(kVPair2);
                } else {
                    String[] hds = new String[value.size()];
                    for (int i = 0; i < hds.length; i++) {
                        hds[i] = (String) value.get(i);
                    }
                    KVPair kVPair3 = new KVPair((String) ent.getKey(), hds);
                    list.add(kVPair3);
                }
            }
        }
        conn.disconnect();
        MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
        return list;
    }

    public void httpPatch(String url, ArrayList<KVPair<String>> values, KVPair<String> file, long offset, ArrayList<KVPair<String>> headers, OnReadListener listener, HttpResponseCallback callback, NetworkTimeOut timeout) throws Throwable {
        if (VERSION.SDK_INT >= 23) {
            httpPatchImpl23(url, values, file, offset, headers, listener, callback, timeout);
        } else {
            httpPatchImpl(url, values, file, offset, headers, listener, callback, timeout);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x01d3, code lost:
        r28 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x01d4, code lost:
        r6.getConnectionManager().shutdown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x01db, code lost:
        throw r28;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void httpPatchImpl(java.lang.String r33, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r34, com.mob.tools.network.KVPair<java.lang.String> r35, long r36, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r38, com.mob.tools.network.OnReadListener r39, com.mob.tools.network.HttpResponseCallback r40, com.mob.tools.network.NetworkHelper.NetworkTimeOut r41) throws java.lang.Throwable {
        /*
            r32 = this;
            long r26 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r28 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r30 = "httpPatch: "
            java.lang.StringBuilder r29 = r29.append(r30)
            r0 = r29
            r1 = r33
            java.lang.StringBuilder r29 = r0.append(r1)
            java.lang.String r29 = r29.toString()
            r30 = 0
            r0 = r30
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r30 = r0
            r28.i(r29, r30)
            if (r34 == 0) goto L_0x005b
            r0 = r32
            r1 = r34
            java.lang.String r18 = r0.kvPairsToUrl(r1)
            int r28 = r18.length()
            if (r28 <= 0) goto L_0x005b
            java.lang.StringBuilder r28 = new java.lang.StringBuilder
            r28.<init>()
            r0 = r28
            r1 = r33
            java.lang.StringBuilder r28 = r0.append(r1)
            java.lang.String r29 = "?"
            java.lang.StringBuilder r28 = r28.append(r29)
            r0 = r28
            r1 = r18
            java.lang.StringBuilder r28 = r0.append(r1)
            java.lang.String r33 = r28.toString()
        L_0x005b:
            com.mob.tools.network.HttpPatch r20 = new com.mob.tools.network.HttpPatch
            r0 = r20
            r1 = r33
            r0.<init>(r1)
            if (r38 == 0) goto L_0x008a
            java.util.Iterator r14 = r38.iterator()
        L_0x006a:
            boolean r28 = r14.hasNext()
            if (r28 == 0) goto L_0x008a
            java.lang.Object r10 = r14.next()
            com.mob.tools.network.KVPair r10 = (com.mob.tools.network.KVPair) r10
            java.lang.String r0 = r10.name
            r29 = r0
            T r0 = r10.value
            r28 = r0
            java.lang.String r28 = (java.lang.String) r28
            r0 = r20
            r1 = r29
            r2 = r28
            r0.setHeader(r1, r2)
            goto L_0x006a
        L_0x008a:
            com.mob.tools.network.FilePart r9 = new com.mob.tools.network.FilePart
            r9.<init>()
            r0 = r39
            r9.setOnReadListener(r0)
            r0 = r35
            T r0 = r0.value
            r28 = r0
            java.lang.String r28 = (java.lang.String) r28
            r0 = r28
            r9.setFile(r0)
            r0 = r36
            r9.setOffset(r0)
            java.io.InputStream r15 = r9.toInputStream()
            long r28 = r9.length()
            long r16 = r28 - r36
            org.apache.http.entity.InputStreamEntity r8 = new org.apache.http.entity.InputStreamEntity
            r0 = r16
            r8.<init>(r15, r0)
            java.lang.String r28 = "application/offset+octet-stream"
            r0 = r28
            r8.setContentEncoding(r0)
            r0 = r20
            r0.setEntity(r8)
            org.apache.http.params.BasicHttpParams r11 = new org.apache.http.params.BasicHttpParams
            r11.<init>()
            if (r41 != 0) goto L_0x01bd
            int r7 = connectionTimeout
        L_0x00cd:
            if (r7 <= 0) goto L_0x00d2
            org.apache.http.params.HttpConnectionParams.setConnectionTimeout(r11, r7)
        L_0x00d2:
            if (r41 != 0) goto L_0x01c3
            int r21 = readTimout
        L_0x00d6:
            if (r21 <= 0) goto L_0x00dd
            r0 = r21
            org.apache.http.params.HttpConnectionParams.setSoTimeout(r11, r0)
        L_0x00dd:
            r0 = r20
            r0.setParams(r11)
            r6 = 0
            java.lang.String r28 = "https://"
            r0 = r33
            r1 = r28
            boolean r28 = r0.startsWith(r1)
            if (r28 == 0) goto L_0x01cb
            java.lang.String r28 = java.security.KeyStore.getDefaultType()
            java.security.KeyStore r25 = java.security.KeyStore.getInstance(r28)
            r28 = 0
            r29 = 0
            r0 = r25
            r1 = r28
            r2 = r29
            r0.load(r1, r2)
            com.mob.tools.network.SSLSocketFactoryEx r23 = new com.mob.tools.network.SSLSocketFactoryEx
            r0 = r23
            r1 = r25
            r0.<init>(r1)
            org.apache.http.conn.ssl.X509HostnameVerifier r28 = org.apache.http.conn.ssl.SSLSocketFactory.STRICT_HOSTNAME_VERIFIER
            r0 = r23
            r1 = r28
            r0.setHostnameVerifier(r1)
            org.apache.http.params.BasicHttpParams r19 = new org.apache.http.params.BasicHttpParams
            r19.<init>()
            org.apache.http.HttpVersion r4 = org.apache.http.HttpVersion.HTTP_1_1
            r0 = r19
            org.apache.http.params.HttpProtocolParams.setVersion(r0, r4)
            java.lang.String r28 = "UTF-8"
            r0 = r19
            r1 = r28
            org.apache.http.params.HttpProtocolParams.setContentCharset(r0, r1)
            org.apache.http.conn.scheme.SchemeRegistry r22 = new org.apache.http.conn.scheme.SchemeRegistry
            r22.<init>()
            org.apache.http.conn.scheme.PlainSocketFactory r13 = org.apache.http.conn.scheme.PlainSocketFactory.getSocketFactory()
            org.apache.http.conn.scheme.Scheme r28 = new org.apache.http.conn.scheme.Scheme
            java.lang.String r29 = "http"
            r30 = 80
            r0 = r28
            r1 = r29
            r2 = r30
            r0.<init>(r1, r13, r2)
            r0 = r22
            r1 = r28
            r0.register(r1)
            org.apache.http.conn.scheme.Scheme r28 = new org.apache.http.conn.scheme.Scheme
            java.lang.String r29 = "https"
            r30 = 443(0x1bb, float:6.21E-43)
            r0 = r28
            r1 = r29
            r2 = r23
            r3 = r30
            r0.<init>(r1, r2, r3)
            r0 = r22
            r1 = r28
            r0.register(r1)
            org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager r5 = new org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
            r0 = r19
            r1 = r22
            r5.<init>(r0, r1)
            org.apache.http.impl.client.DefaultHttpClient r6 = new org.apache.http.impl.client.DefaultHttpClient
            r0 = r19
            r6.<init>(r5, r0)
        L_0x0176:
            r0 = r20
            org.apache.http.HttpResponse r12 = r6.execute(r0)
            if (r40 == 0) goto L_0x01dc
            com.mob.tools.network.HttpConnectionImpl r28 = new com.mob.tools.network.HttpConnectionImpl     // Catch:{ Throwable -> 0x01d1 }
            r0 = r28
            r0.<init>(r12)     // Catch:{ Throwable -> 0x01d1 }
            r0 = r40
            r1 = r28
            r0.onResponse(r1)     // Catch:{ Throwable -> 0x01d1 }
            org.apache.http.conn.ClientConnectionManager r28 = r6.getConnectionManager()
            r28.shutdown()
        L_0x0193:
            com.mob.tools.log.NLog r28 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r29 = new java.lang.StringBuilder
            r29.<init>()
            java.lang.String r30 = "use time: "
            java.lang.StringBuilder r29 = r29.append(r30)
            long r30 = java.lang.System.currentTimeMillis()
            long r30 = r30 - r26
            java.lang.StringBuilder r29 = r29.append(r30)
            java.lang.String r29 = r29.toString()
            r30 = 0
            r0 = r30
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r30 = r0
            r28.i(r29, r30)
            return
        L_0x01bd:
            r0 = r41
            int r7 = r0.connectionTimeout
            goto L_0x00cd
        L_0x01c3:
            r0 = r41
            int r0 = r0.readTimout
            r21 = r0
            goto L_0x00d6
        L_0x01cb:
            org.apache.http.impl.client.DefaultHttpClient r6 = new org.apache.http.impl.client.DefaultHttpClient
            r6.<init>()
            goto L_0x0176
        L_0x01d1:
            r24 = move-exception
            throw r24     // Catch:{ all -> 0x01d3 }
        L_0x01d3:
            r28 = move-exception
            org.apache.http.conn.ClientConnectionManager r29 = r6.getConnectionManager()
            r29.shutdown()
            throw r28
        L_0x01dc:
            org.apache.http.conn.ClientConnectionManager r28 = r6.getConnectionManager()
            r28.shutdown()
            goto L_0x0193
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.httpPatchImpl(java.lang.String, java.util.ArrayList, com.mob.tools.network.KVPair, long, java.util.ArrayList, com.mob.tools.network.OnReadListener, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0131, code lost:
        r16 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0132, code lost:
        r5.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0135, code lost:
        throw r16;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void httpPatchImpl23(java.lang.String r21, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r22, com.mob.tools.network.KVPair<java.lang.String> r23, long r24, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r26, com.mob.tools.network.OnReadListener r27, com.mob.tools.network.HttpResponseCallback r28, com.mob.tools.network.NetworkHelper.NetworkTimeOut r29) throws java.lang.Throwable {
        /*
            r20 = this;
            long r14 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r16 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r17 = new java.lang.StringBuilder
            r17.<init>()
            java.lang.String r18 = "httpPatch: "
            java.lang.StringBuilder r17 = r17.append(r18)
            r0 = r17
            r1 = r21
            java.lang.StringBuilder r17 = r0.append(r1)
            java.lang.String r17 = r17.toString()
            r18 = 0
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r18 = r0
            r16.i(r17, r18)
            if (r22 == 0) goto L_0x0059
            r0 = r20
            r1 = r22
            java.lang.String r12 = r0.kvPairsToUrl(r1)
            int r16 = r12.length()
            if (r16 <= 0) goto L_0x0059
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            r0 = r16
            r1 = r21
            java.lang.StringBuilder r16 = r0.append(r1)
            java.lang.String r17 = "?"
            java.lang.StringBuilder r16 = r16.append(r17)
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r12)
            java.lang.String r21 = r16.toString()
        L_0x0059:
            r0 = r20
            r1 = r21
            r2 = r29
            java.net.HttpURLConnection r5 = r0.getConnection(r1, r2)
            r16 = 1
            r0 = r16
            r5.setDoOutput(r0)
            r16 = 0
            r0 = r16
            r5.setChunkedStreamingMode(r0)
            java.lang.String r16 = "PATCH"
            r0 = r16
            r5.setRequestMethod(r0)
            java.lang.String r16 = "Content-Type"
            java.lang.String r17 = "application/offset+octet-stream"
            r0 = r16
            r1 = r17
            r5.setRequestProperty(r0, r1)
            if (r26 == 0) goto L_0x00aa
            java.util.Iterator r8 = r26.iterator()
        L_0x008c:
            boolean r16 = r8.hasNext()
            if (r16 == 0) goto L_0x00aa
            java.lang.Object r7 = r8.next()
            com.mob.tools.network.KVPair r7 = (com.mob.tools.network.KVPair) r7
            java.lang.String r0 = r7.name
            r17 = r0
            T r0 = r7.value
            r16 = r0
            java.lang.String r16 = (java.lang.String) r16
            r0 = r17
            r1 = r16
            r5.setRequestProperty(r0, r1)
            goto L_0x008c
        L_0x00aa:
            r5.connect()
            java.io.OutputStream r11 = r5.getOutputStream()
            com.mob.tools.network.FilePart r6 = new com.mob.tools.network.FilePart
            r6.<init>()
            r0 = r27
            r6.setOnReadListener(r0)
            r0 = r23
            T r0 = r0.value
            r16 = r0
            java.lang.String r16 = (java.lang.String) r16
            r0 = r16
            r6.setFile(r0)
            r0 = r24
            r6.setOffset(r0)
            java.io.InputStream r9 = r6.toInputStream()
            r16 = 65536(0x10000, float:9.18355E-41)
            r0 = r16
            byte[] r4 = new byte[r0]
            int r10 = r9.read(r4)
        L_0x00db:
            if (r10 <= 0) goto L_0x00e9
            r16 = 0
            r0 = r16
            r11.write(r4, r0, r10)
            int r10 = r9.read(r4)
            goto L_0x00db
        L_0x00e9:
            r11.flush()
            r9.close()
            r11.close()
            if (r28 == 0) goto L_0x0136
            com.mob.tools.network.HttpConnectionImpl23 r16 = new com.mob.tools.network.HttpConnectionImpl23     // Catch:{ Throwable -> 0x012f }
            r0 = r16
            r0.<init>(r5)     // Catch:{ Throwable -> 0x012f }
            r0 = r28
            r1 = r16
            r0.onResponse(r1)     // Catch:{ Throwable -> 0x012f }
            r5.disconnect()
        L_0x0105:
            com.mob.tools.log.NLog r16 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r17 = new java.lang.StringBuilder
            r17.<init>()
            java.lang.String r18 = "use time: "
            java.lang.StringBuilder r17 = r17.append(r18)
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r14
            java.lang.StringBuilder r17 = r17.append(r18)
            java.lang.String r17 = r17.toString()
            r18 = 0
            r0 = r18
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r18 = r0
            r16.i(r17, r18)
            return
        L_0x012f:
            r13 = move-exception
            throw r13     // Catch:{ all -> 0x0131 }
        L_0x0131:
            r16 = move-exception
            r5.disconnect()
            throw r16
        L_0x0136:
            r5.disconnect()
            goto L_0x0105
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.httpPatchImpl23(java.lang.String, java.util.ArrayList, com.mob.tools.network.KVPair, long, java.util.ArrayList, com.mob.tools.network.OnReadListener, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    private String kvPairsToUrl(ArrayList<KVPair<String>> values) throws Throwable {
        StringBuilder sb = new StringBuilder();
        Iterator i$ = values.iterator();
        while (i$.hasNext()) {
            KVPair<String> value = (KVPair) i$.next();
            String encodedName = Data.urlEncode(value.name, "utf-8");
            String encodedValue = value.value != null ? Data.urlEncode((String) value.value, "utf-8") : "";
            if (sb.length() > 0) {
                sb.append(Typography.amp);
            }
            sb.append(encodedName).append('=').append(encodedValue);
        }
        return sb.toString();
    }

    private HttpURLConnection getConnection(String urlStr, NetworkTimeOut timeout) throws Throwable {
        Object obj;
        int readTimout2;
        Object methods;
        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        String filedName = "methodTokens";
        boolean staticType = false;
        Object methods2 = null;
        if (0 != 0) {
            try {
                methods2 = ReflectHelper.getStaticField("HttpURLConnection", filedName);
            } catch (Throwable th) {
            }
        } else {
            methods2 = ReflectHelper.getInstanceField(conn, filedName);
        }
        if (methods2 == null) {
            filedName = "PERMITTED_USER_METHODS";
            staticType = true;
            if (1 != 0) {
                try {
                    methods = ReflectHelper.getStaticField("HttpURLConnection", filedName);
                } catch (Throwable th2) {
                    obj = methods2;
                }
            } else {
                methods = ReflectHelper.getInstanceField(conn, filedName);
            }
            obj = methods;
        } else {
            obj = methods2;
        }
        if (obj != null) {
            String[] methodTokens = (String[]) obj;
            String[] myMethodTokens = new String[(methodTokens.length + 1)];
            System.arraycopy(methodTokens, 0, myMethodTokens, 0, methodTokens.length);
            myMethodTokens[methodTokens.length] = "PATCH";
            if (staticType) {
                ReflectHelper.setStaticField("HttpURLConnection", filedName, myMethodTokens);
            } else {
                ReflectHelper.setInstanceField(conn, filedName, myMethodTokens);
            }
        }
        if (VERSION.SDK_INT < 8) {
            System.setProperty("http.keepAlive", "false");
        }
        if (conn instanceof HttpsURLConnection) {
            HostnameVerifier hostnameVerifier = SSLSocketFactory.STRICT_HOSTNAME_VERIFIER;
            HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new SimpleX509TrustManager(null)}, new SecureRandom());
            httpsConn.setSSLSocketFactory(sc.getSocketFactory());
            httpsConn.setHostnameVerifier(hostnameVerifier);
        }
        int connectionTimeout2 = timeout == null ? connectionTimeout : timeout.connectionTimeout;
        if (connectionTimeout2 > 0) {
            conn.setConnectTimeout(connectionTimeout2);
        }
        if (timeout == null) {
            readTimout2 = readTimout;
        } else {
            readTimout2 = timeout.readTimout;
        }
        if (readTimout2 > 0) {
            conn.setReadTimeout(readTimout2);
        }
        return conn;
    }
}
