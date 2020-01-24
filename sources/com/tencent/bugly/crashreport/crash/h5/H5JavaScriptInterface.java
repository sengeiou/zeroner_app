package com.tencent.bugly.crashreport.crash.h5;

import android.webkit.JavascriptInterface;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.crashreport.CrashReport.WebViewInterface;
import com.tencent.bugly.crashreport.inner.InnerApi;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;
import org.litepal.util.Const.TableSchema;

/* compiled from: BUGLY */
public class H5JavaScriptInterface {
    private static HashSet<Integer> a = new HashSet<>();
    private String b = null;
    private Thread c = null;
    private String d = null;
    private Map<String, String> e = null;

    private H5JavaScriptInterface() {
    }

    public static H5JavaScriptInterface getInstance(WebViewInterface webView) {
        if (webView == null || a.contains(Integer.valueOf(webView.hashCode()))) {
            return null;
        }
        H5JavaScriptInterface h5JavaScriptInterface = new H5JavaScriptInterface();
        a.add(Integer.valueOf(webView.hashCode()));
        h5JavaScriptInterface.c = Thread.currentThread();
        h5JavaScriptInterface.d = a(h5JavaScriptInterface.c);
        h5JavaScriptInterface.e = a(webView);
        return h5JavaScriptInterface;
    }

    private static String a(Thread thread) {
        if (thread == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 2; i < thread.getStackTrace().length; i++) {
            StackTraceElement stackTraceElement = thread.getStackTrace()[i];
            if (!stackTraceElement.toString().contains("crashreport")) {
                sb.append(stackTraceElement.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    private static Map<String, String> a(WebViewInterface webViewInterface) {
        HashMap hashMap = new HashMap();
        hashMap.put("[WebView] ContentDescription", "" + webViewInterface.getContentDescription());
        return hashMap;
    }

    private a a(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            a aVar = new a();
            aVar.a = jSONObject.getString("projectRoot");
            if (aVar.a == null) {
                return null;
            }
            aVar.b = jSONObject.getString("context");
            if (aVar.b == null) {
                return null;
            }
            aVar.c = jSONObject.getString("url");
            if (aVar.c == null) {
                return null;
            }
            aVar.d = jSONObject.getString("userAgent");
            if (aVar.d == null) {
                return null;
            }
            aVar.e = jSONObject.getString("language");
            if (aVar.e == null) {
                return null;
            }
            aVar.f = jSONObject.getString(TableSchema.COLUMN_NAME);
            if (aVar.f == null || aVar.f.equals("null")) {
                return null;
            }
            String string = jSONObject.getString("stacktrace");
            if (string == null) {
                return null;
            }
            int indexOf = string.indexOf("\n");
            if (indexOf < 0) {
                an.d("H5 crash stack's format is wrong!", new Object[0]);
                return null;
            }
            aVar.h = string.substring(indexOf + 1);
            aVar.g = string.substring(0, indexOf);
            int indexOf2 = aVar.g.indexOf(":");
            if (indexOf2 > 0) {
                aVar.g = aVar.g.substring(indexOf2 + 1);
            }
            aVar.i = jSONObject.getString("file");
            if (aVar.f == null) {
                return null;
            }
            aVar.j = jSONObject.getLong("lineNumber");
            if (aVar.j < 0) {
                return null;
            }
            aVar.k = jSONObject.getLong("columnNumber");
            if (aVar.k < 0) {
                return null;
            }
            an.a("H5 crash information is following: ", new Object[0]);
            an.a("[projectRoot]: " + aVar.a, new Object[0]);
            an.a("[context]: " + aVar.b, new Object[0]);
            an.a("[url]: " + aVar.c, new Object[0]);
            an.a("[userAgent]: " + aVar.d, new Object[0]);
            an.a("[language]: " + aVar.e, new Object[0]);
            an.a("[name]: " + aVar.f, new Object[0]);
            an.a("[message]: " + aVar.g, new Object[0]);
            an.a("[stacktrace]: \n" + aVar.h, new Object[0]);
            an.a("[file]: " + aVar.i, new Object[0]);
            an.a("[lineNumber]: " + aVar.j, new Object[0]);
            an.a("[columnNumber]: " + aVar.k, new Object[0]);
            return aVar;
        } catch (Throwable th) {
            if (an.a(th)) {
                return null;
            }
            ThrowableExtension.printStackTrace(th);
            return null;
        }
    }

    private static void a(a aVar, Thread thread, Map<String, String> map) {
        if (aVar != null) {
            InnerApi.postH5CrashAsync(thread, aVar.f, aVar.g, aVar.h, map);
        }
    }

    @JavascriptInterface
    public void printLog(String log) {
        an.d("Log from js: %s", log);
    }

    @JavascriptInterface
    public void reportJSException(String payload) {
        if (payload == null) {
            an.d("Payload from JS is null.", new Object[0]);
            return;
        }
        String b2 = ap.b(payload.getBytes());
        if (this.b == null || !this.b.equals(b2)) {
            this.b = b2;
            an.d("Handling JS exception ...", new Object[0]);
            a a2 = a(payload);
            if (a2 == null) {
                an.d("Failed to parse payload.", new Object[0]);
                return;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.putAll(a2.a());
            linkedHashMap.putAll(this.e);
            linkedHashMap.put("Java Stack", this.d);
            a(a2, this.c, linkedHashMap);
            return;
        }
        an.d("Same payload from js. Please check whether you've injected bugly.js more than one times.", new Object[0]);
    }
}
