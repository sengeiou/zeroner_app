package com.tencent.wxop.stat;

import android.content.Context;
import com.google.common.net.HttpHeaders;
import com.tencent.a.a.a.a.g;
import com.tencent.a.a.a.a.h;
import com.tencent.stat.DeviceInfo;
import com.tencent.wxop.stat.common.StatLogger;
import com.tencent.wxop.stat.common.e;
import com.tencent.wxop.stat.common.f;
import com.tencent.wxop.stat.common.l;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

class i {
    private static StatLogger d = l.b();
    private static i e = null;
    private static Context f = null;
    DefaultHttpClient a = null;
    e b = null;
    StringBuilder c = new StringBuilder(4096);
    private long g = 0;

    private i(Context context) {
        try {
            f = context.getApplicationContext();
            this.g = System.currentTimeMillis() / 1000;
            this.b = new e();
            if (StatConfig.isDebugEnable()) {
                try {
                    Logger.getLogger("org.apache.http.wire").setLevel(Level.FINER);
                    Logger.getLogger("org.apache.http.headers").setLevel(Level.FINER);
                    System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
                    System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
                    System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
                    System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
                    System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");
                } catch (Throwable th) {
                }
            }
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, false);
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
            HttpConnectionParams.setSoTimeout(basicHttpParams, 10000);
            this.a = new DefaultHttpClient(basicHttpParams);
            this.a.setKeepAliveStrategy(new j(this));
        } catch (Throwable th2) {
            d.e(th2);
        }
    }

    static Context a() {
        return f;
    }

    static void a(Context context) {
        f = context.getApplicationContext();
    }

    private void a(JSONObject jSONObject) {
        try {
            String optString = jSONObject.optString(DeviceInfo.TAG_MID);
            if (h.c(optString)) {
                if (StatConfig.isDebugEnable()) {
                    d.i("update mid:" + optString);
                }
                g.C(f).a(optString);
            }
            if (!jSONObject.isNull("cfg")) {
                StatConfig.a(f, jSONObject.getJSONObject("cfg"));
            }
            if (!jSONObject.isNull("ncts")) {
                int i = jSONObject.getInt("ncts");
                int currentTimeMillis = (int) (((long) i) - (System.currentTimeMillis() / 1000));
                if (StatConfig.isDebugEnable()) {
                    d.i("server time:" + i + ", diff time:" + currentTimeMillis);
                }
                l.x(f);
                l.a(f, currentTimeMillis);
            }
        } catch (Throwable th) {
            d.w(th);
        }
    }

    static i b(Context context) {
        if (e == null) {
            synchronized (i.class) {
                if (e == null) {
                    e = new i(context);
                }
            }
        }
        return e;
    }

    /* access modifiers changed from: 0000 */
    public void a(com.tencent.wxop.stat.event.e eVar, h hVar) {
        b(Arrays.asList(new String[]{eVar.g()}), hVar);
    }

    /* access modifiers changed from: 0000 */
    public void a(List<?> list, h hVar) {
        boolean z = false;
        if (list != null && !list.isEmpty()) {
            int size = list.size();
            list.get(0);
            try {
                this.c.delete(0, this.c.length());
                this.c.append("[");
                String str = "rc4";
                for (int i = 0; i < size; i++) {
                    this.c.append(list.get(i).toString());
                    if (i != size - 1) {
                        this.c.append(",");
                    }
                }
                this.c.append("]");
                String sb = this.c.toString();
                int length = sb.length();
                String str2 = StatConfig.getStatReportUrl() + "/?index=" + this.g;
                this.g++;
                if (StatConfig.isDebugEnable()) {
                    d.i("[" + str2 + "]Send request(" + length + "bytes), content:" + sb);
                }
                HttpPost httpPost = new HttpPost(str2);
                httpPost.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");
                httpPost.setHeader(HttpHeaders.CONNECTION, "Keep-Alive");
                httpPost.removeHeaders(HttpHeaders.CACHE_CONTROL);
                HttpHost a2 = a.a(f).a();
                httpPost.addHeader(HttpHeaders.CONTENT_ENCODING, str);
                if (a2 == null) {
                    this.a.getParams().removeParameter("http.route.default-proxy");
                } else {
                    if (StatConfig.isDebugEnable()) {
                        d.d("proxy:" + a2.toHostString());
                    }
                    httpPost.addHeader("X-Content-Encoding", str);
                    this.a.getParams().setParameter("http.route.default-proxy", a2);
                    httpPost.addHeader("X-Online-Host", StatConfig.k);
                    httpPost.addHeader(HttpHeaders.ACCEPT, "*/*");
                    httpPost.addHeader("Content-Type", "json");
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(length);
                byte[] bytes = sb.getBytes("UTF-8");
                int length2 = bytes.length;
                if (length > StatConfig.o) {
                    z = true;
                }
                if (z) {
                    httpPost.removeHeaders(HttpHeaders.CONTENT_ENCODING);
                    String str3 = str + ",gzip";
                    httpPost.addHeader(HttpHeaders.CONTENT_ENCODING, str3);
                    if (a2 != null) {
                        httpPost.removeHeaders("X-Content-Encoding");
                        httpPost.addHeader("X-Content-Encoding", str3);
                    }
                    byteArrayOutputStream.write(new byte[4]);
                    GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                    gZIPOutputStream.write(bytes);
                    gZIPOutputStream.close();
                    bytes = byteArrayOutputStream.toByteArray();
                    ByteBuffer.wrap(bytes, 0, 4).putInt(length2);
                    if (StatConfig.isDebugEnable()) {
                        d.d("before Gzip:" + length2 + " bytes, after Gzip:" + bytes.length + " bytes");
                    }
                }
                httpPost.setEntity(new ByteArrayEntity(f.a(bytes)));
                HttpResponse execute = this.a.execute(httpPost);
                HttpEntity entity = execute.getEntity();
                int statusCode = execute.getStatusLine().getStatusCode();
                long contentLength = entity.getContentLength();
                if (StatConfig.isDebugEnable()) {
                    d.i("http recv response status code:" + statusCode + ", content length:" + contentLength);
                }
                if (contentLength <= 0) {
                    d.e((Object) "Server response no data.");
                    if (hVar != null) {
                        hVar.b();
                    }
                    EntityUtils.toString(entity);
                    return;
                }
                if (contentLength > 0) {
                    InputStream content = entity.getContent();
                    DataInputStream dataInputStream = new DataInputStream(content);
                    byte[] bArr = new byte[((int) entity.getContentLength())];
                    dataInputStream.readFully(bArr);
                    content.close();
                    dataInputStream.close();
                    Header firstHeader = execute.getFirstHeader(HttpHeaders.CONTENT_ENCODING);
                    if (firstHeader != null) {
                        if (firstHeader.getValue().equalsIgnoreCase("gzip,rc4")) {
                            bArr = f.b(l.a(bArr));
                        } else if (firstHeader.getValue().equalsIgnoreCase("rc4,gzip")) {
                            bArr = l.a(f.b(bArr));
                        } else if (firstHeader.getValue().equalsIgnoreCase("gzip")) {
                            bArr = l.a(bArr);
                        } else if (firstHeader.getValue().equalsIgnoreCase("rc4")) {
                            bArr = f.b(bArr);
                        }
                    }
                    String str4 = new String(bArr, "UTF-8");
                    if (StatConfig.isDebugEnable()) {
                        d.i("http get response data:" + str4);
                    }
                    JSONObject jSONObject = new JSONObject(str4);
                    if (statusCode == 200) {
                        a(jSONObject);
                        if (hVar != null) {
                            if (jSONObject.optInt("ret") == 0) {
                                hVar.a();
                            } else {
                                d.error((Object) "response error data.");
                                hVar.b();
                            }
                        }
                    } else {
                        d.error((Object) "Server response error code:" + statusCode + ", error:" + new String(bArr, "UTF-8"));
                        if (hVar != null) {
                            hVar.b();
                        }
                    }
                    content.close();
                } else {
                    EntityUtils.toString(entity);
                }
                byteArrayOutputStream.close();
                th = null;
                if (th != null) {
                    d.error(th);
                    if (hVar != null) {
                        try {
                            hVar.b();
                        } catch (Throwable th) {
                            d.e(th);
                        }
                    }
                    if (th instanceof OutOfMemoryError) {
                        System.gc();
                        this.c = null;
                        this.c = new StringBuilder(2048);
                    }
                    a.a(f).d();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(List<?> list, h hVar) {
        if (this.b != null) {
            this.b.a(new k(this, list, hVar));
        }
    }
}
