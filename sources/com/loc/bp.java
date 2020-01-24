package com.loc;

import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.Vector;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import org.apache.commons.cli.HelpFormatter;

/* compiled from: HttpUrlUtil */
public final class bp {
    private int a;
    private int b;
    private boolean c;
    private SSLContext d;
    private Proxy e;
    private volatile boolean f;
    private long g;
    private long h;
    private String i;
    private a j;
    private com.loc.bm.a k;

    /* compiled from: HttpUrlUtil */
    private static class a {
        private Vector<b> a;
        private volatile b b;

        private a() {
            this.a = new Vector<>();
            this.b = new b(0);
        }

        /* synthetic */ a(byte b2) {
            this();
        }

        public final b a() {
            return this.b;
        }

        public final b a(String str) {
            if (TextUtils.isEmpty(str)) {
                return this.b;
            }
            for (int i = 0; i < this.a.size(); i++) {
                b bVar = (b) this.a.get(i);
                if (bVar != null && bVar.a().equals(str)) {
                    return bVar;
                }
            }
            b bVar2 = new b(0);
            bVar2.b(str);
            this.a.add(bVar2);
            return bVar2;
        }

        public final void b(String str) {
            if (!TextUtils.isEmpty(str)) {
                this.b.a(str);
            }
        }
    }

    /* compiled from: HttpUrlUtil */
    private static class b implements HostnameVerifier {
        private String a;
        private String b;

        private b() {
        }

        /* synthetic */ b(byte b2) {
            this();
        }

        public final String a() {
            return this.b;
        }

        public final void a(String str) {
            this.a = str;
        }

        public final void b(String str) {
            this.b = str;
        }

        public final boolean verify(String str, SSLSession sSLSession) {
            HostnameVerifier defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
            return !TextUtils.isEmpty(this.a) ? this.a.equals(str) : !TextUtils.isEmpty(this.b) ? defaultHostnameVerifier.verify(this.b, sSLSession) : defaultHostnameVerifier.verify(str, sSLSession);
        }
    }

    bp(int i2, int i3, Proxy proxy, boolean z) {
        this(i2, i3, proxy, z, 0);
    }

    private bp(int i2, int i3, Proxy proxy, boolean z, byte b2) {
        this.f = false;
        this.g = -1;
        this.h = 0;
        this.a = i2;
        this.b = i3;
        this.e = proxy;
        this.c = r.a().b(z);
        if (r.b()) {
            this.c = false;
        }
        this.k = null;
        try {
            this.i = UUID.randomUUID().toString().replaceAll(HelpFormatter.DEFAULT_OPT_PREFIX, "").toLowerCase();
        } catch (Throwable th) {
            ag.a(th, "ht", "ic");
        }
        if (this.c) {
            try {
                SSLContext instance = SSLContext.getInstance("TLS");
                instance.init(null, null, null);
                this.d = instance;
            } catch (Throwable th2) {
                ag.a(th2, "ht", "ne");
            }
        }
        this.j = new a(0);
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r9v0 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r9v1 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r2v8, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r5v11, types: [java.util.zip.GZIPInputStream] */
    /* JADX WARNING: type inference failed for: r1v19 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r1v21 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x015a, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x015b, code lost:
        r1 = 0;
        r3 = null;
        r4 = null;
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0170, code lost:
        r0 = r1;
        r3 = null;
        r4 = null;
        r1 = 0;
        r2 = r2;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x002d A[SYNTHETIC, Splitter:B:11:0x002d] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0080 A[SYNTHETIC, Splitter:B:22:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0085 A[SYNTHETIC, Splitter:B:25:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x008a A[SYNTHETIC, Splitter:B:28:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x008f A[SYNTHETIC, Splitter:B:31:0x008f] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0093 A[SYNTHETIC, Splitter:B:34:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x015a A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x0004] */
    /* JADX WARNING: Unknown variable types count: 8 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.loc.br a(java.net.HttpURLConnection r11) throws com.loc.k, java.io.IOException {
        /*
            r10 = this;
            r2 = 0
            java.lang.String r1 = ""
            r11.connect()     // Catch:{ IOException -> 0x016f, all -> 0x015a }
            java.util.Map r6 = r11.getHeaderFields()     // Catch:{ IOException -> 0x016f, all -> 0x015a }
            int r3 = r11.getResponseCode()     // Catch:{ IOException -> 0x016f, all -> 0x015a }
            if (r6 == 0) goto L_0x017f
            java.lang.String r0 = "gsid"
            java.lang.Object r0 = r6.get(r0)     // Catch:{ IOException -> 0x016f, all -> 0x015a }
            java.util.List r0 = (java.util.List) r0     // Catch:{ IOException -> 0x016f, all -> 0x015a }
            if (r0 == 0) goto L_0x017f
            int r4 = r0.size()     // Catch:{ IOException -> 0x016f, all -> 0x015a }
            if (r4 <= 0) goto L_0x017f
            r4 = 0
            java.lang.Object r0 = r0.get(r4)     // Catch:{ IOException -> 0x016f, all -> 0x015a }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ IOException -> 0x016f, all -> 0x015a }
        L_0x0029:
            r1 = 200(0xc8, float:2.8E-43)
            if (r3 == r1) goto L_0x0093
            com.loc.k r1 = new com.loc.k     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.String r5 = "网络异常原因："
            r4.<init>(r5)     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.String r5 = r11.getResponseMessage()     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.String r5 = " 网络异常状态码："
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.StringBuilder r4 = r4.append(r3)     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.String r5 = "  "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.String r5 = " "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.String r5 = r10.i     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            r1.<init>(r4, r0)     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            r1.a(r3)     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            throw r1     // Catch:{ IOException -> 0x006d, all -> 0x015a }
        L_0x006d:
            r1 = move-exception
            r1 = r2
            r3 = r2
            r4 = r2
        L_0x0071:
            com.loc.k r5 = new com.loc.k     // Catch:{ all -> 0x007a }
            java.lang.String r6 = "IO 操作异常 - IOException"
            r5.<init>(r6, r0)     // Catch:{ all -> 0x007a }
            throw r5     // Catch:{ all -> 0x007a }
        L_0x007a:
            r0 = move-exception
            r9 = r1
            r1 = r2
            r2 = r9
        L_0x007e:
            if (r4 == 0) goto L_0x0083
            r4.close()     // Catch:{ Throwable -> 0x00fe }
        L_0x0083:
            if (r3 == 0) goto L_0x0088
            r3.close()     // Catch:{ Throwable -> 0x010a }
        L_0x0088:
            if (r2 == 0) goto L_0x008d
            r2.close()     // Catch:{ Throwable -> 0x0116 }
        L_0x008d:
            if (r1 == 0) goto L_0x0092
            r1.close()     // Catch:{ Throwable -> 0x0122 }
        L_0x0092:
            throw r0
        L_0x0093:
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            r4.<init>()     // Catch:{ IOException -> 0x006d, all -> 0x015a }
            java.io.InputStream r3 = r11.getInputStream()     // Catch:{ IOException -> 0x0176, all -> 0x0160 }
            java.io.PushbackInputStream r1 = new java.io.PushbackInputStream     // Catch:{ IOException -> 0x017b, all -> 0x0165 }
            r5 = 2
            r1.<init>(r3, r5)     // Catch:{ IOException -> 0x017b, all -> 0x0165 }
            r5 = 2
            byte[] r5 = new byte[r5]     // Catch:{ all -> 0x0169 }
            r1.read(r5)     // Catch:{ all -> 0x0169 }
            r1.unread(r5)     // Catch:{ all -> 0x0169 }
            r7 = 0
            byte r7 = r5[r7]     // Catch:{ all -> 0x0169 }
            r8 = 31
            if (r7 != r8) goto L_0x00d1
            r7 = 1
            byte r5 = r5[r7]     // Catch:{ all -> 0x0169 }
            r7 = -117(0xffffffffffffff8b, float:NaN)
            if (r5 != r7) goto L_0x00d1
            java.util.zip.GZIPInputStream r5 = new java.util.zip.GZIPInputStream     // Catch:{ all -> 0x0169 }
            r5.<init>(r1)     // Catch:{ all -> 0x0169 }
            r2 = r5
        L_0x00bf:
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch:{ IOException -> 0x00cf }
        L_0x00c3:
            int r7 = r2.read(r5)     // Catch:{ IOException -> 0x00cf }
            r8 = -1
            if (r7 == r8) goto L_0x00d3
            r8 = 0
            r4.write(r5, r8, r7)     // Catch:{ IOException -> 0x00cf }
            goto L_0x00c3
        L_0x00cf:
            r5 = move-exception
            goto L_0x0071
        L_0x00d1:
            r2 = r1
            goto L_0x00bf
        L_0x00d3:
            com.loc.aj.c()     // Catch:{ IOException -> 0x00cf }
            com.loc.br r5 = new com.loc.br     // Catch:{ IOException -> 0x00cf }
            r5.<init>()     // Catch:{ IOException -> 0x00cf }
            byte[] r7 = r4.toByteArray()     // Catch:{ IOException -> 0x00cf }
            r5.a = r7     // Catch:{ IOException -> 0x00cf }
            r5.b = r6     // Catch:{ IOException -> 0x00cf }
            java.lang.String r6 = r10.i     // Catch:{ IOException -> 0x00cf }
            r5.c = r6     // Catch:{ IOException -> 0x00cf }
            r5.d = r0     // Catch:{ IOException -> 0x00cf }
            if (r4 == 0) goto L_0x00ee
            r4.close()     // Catch:{ Throwable -> 0x012e }
        L_0x00ee:
            if (r3 == 0) goto L_0x00f3
            r3.close()     // Catch:{ Throwable -> 0x0139 }
        L_0x00f3:
            if (r1 == 0) goto L_0x00f8
            r1.close()     // Catch:{ Throwable -> 0x0144 }
        L_0x00f8:
            if (r2 == 0) goto L_0x00fd
            r2.close()     // Catch:{ Throwable -> 0x014f }
        L_0x00fd:
            return r5
        L_0x00fe:
            r4 = move-exception
            java.lang.String r5 = "ht"
            java.lang.String r6 = "par"
            com.loc.ag.a(r4, r5, r6)
            goto L_0x0083
        L_0x010a:
            r3 = move-exception
            java.lang.String r4 = "ht"
            java.lang.String r5 = "par"
            com.loc.ag.a(r3, r4, r5)
            goto L_0x0088
        L_0x0116:
            r2 = move-exception
            java.lang.String r3 = "ht"
            java.lang.String r4 = "par"
            com.loc.ag.a(r2, r3, r4)
            goto L_0x008d
        L_0x0122:
            r1 = move-exception
            java.lang.String r2 = "ht"
            java.lang.String r3 = "par"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x0092
        L_0x012e:
            r0 = move-exception
            java.lang.String r4 = "ht"
            java.lang.String r6 = "par"
            com.loc.ag.a(r0, r4, r6)
            goto L_0x00ee
        L_0x0139:
            r0 = move-exception
            java.lang.String r3 = "ht"
            java.lang.String r4 = "par"
            com.loc.ag.a(r0, r3, r4)
            goto L_0x00f3
        L_0x0144:
            r0 = move-exception
            java.lang.String r1 = "ht"
            java.lang.String r3 = "par"
            com.loc.ag.a(r0, r1, r3)
            goto L_0x00f8
        L_0x014f:
            r0 = move-exception
            java.lang.String r1 = "ht"
            java.lang.String r2 = "par"
            com.loc.ag.a(r0, r1, r2)
            goto L_0x00fd
        L_0x015a:
            r0 = move-exception
            r1 = r2
            r3 = r2
            r4 = r2
            goto L_0x007e
        L_0x0160:
            r0 = move-exception
            r1 = r2
            r3 = r2
            goto L_0x007e
        L_0x0165:
            r0 = move-exception
            r1 = r2
            goto L_0x007e
        L_0x0169:
            r0 = move-exception
            r9 = r1
            r1 = r2
            r2 = r9
            goto L_0x007e
        L_0x016f:
            r0 = move-exception
            r0 = r1
            r3 = r2
            r4 = r2
            r1 = r2
            goto L_0x0071
        L_0x0176:
            r1 = move-exception
            r1 = r2
            r3 = r2
            goto L_0x0071
        L_0x017b:
            r1 = move-exception
            r1 = r2
            goto L_0x0071
        L_0x017f:
            r0 = r1
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bp.a(java.net.HttpURLConnection):com.loc.br");
    }

    static String a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (str2 == null) {
                str2 = "";
            }
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(str));
            sb.append("=");
            sb.append(URLEncoder.encode(str2));
        }
        return sb.toString();
    }

    private HttpURLConnection a(String str, boolean z, String str2, Map<String, String> map, boolean z2) throws IOException {
        URLConnection uRLConnection;
        HttpURLConnection httpURLConnection;
        p.b();
        if (map == null) {
            map = new HashMap<>();
        }
        b bVar = (!z || TextUtils.isEmpty(str2)) ? this.j.a() : this.j.a(str2);
        String str3 = "";
        switch (bm.a) {
            case 1:
                str3 = bm.b;
                break;
        }
        if (!TextUtils.isEmpty(str3)) {
            Uri parse = Uri.parse(str);
            String host = parse.getHost();
            str = parse.buildUpon().encodedAuthority(str3).build().toString();
            if (map != null) {
                map.put("targetHost", host);
            }
            if (this.c) {
                this.j.b(str3);
            }
        }
        if (this.c) {
            str = r.a(str);
        }
        URL url = new URL(str);
        if (this.k != null) {
            com.loc.bm.a aVar = this.k;
            Proxy proxy = this.e;
            uRLConnection = aVar.a();
        } else {
            uRLConnection = null;
        }
        if (uRLConnection == null) {
            uRLConnection = this.e != null ? url.openConnection(this.e) : url.openConnection();
        }
        if (this.c) {
            httpURLConnection = (HttpsURLConnection) uRLConnection;
            ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(this.d.getSocketFactory());
            ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(bVar);
        } else {
            httpURLConnection = (HttpURLConnection) uRLConnection;
        }
        if (VERSION.SDK != null && VERSION.SDK_INT > 13) {
            httpURLConnection.setRequestProperty(HttpHeaders.CONNECTION, "close");
        }
        a(map, httpURLConnection);
        if (z2) {
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
        } else {
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
        }
        return httpURLConnection;
    }

    private void a(Map<String, String> map, HttpURLConnection httpURLConnection) {
        if (map != null) {
            for (String str : map.keySet()) {
                httpURLConnection.addRequestProperty(str, (String) map.get(str));
            }
        }
        try {
            httpURLConnection.addRequestProperty("csid", this.i);
        } catch (Throwable th) {
            ag.a(th, "ht", "adh");
        }
        httpURLConnection.setConnectTimeout(this.a);
        httpURLConnection.setReadTimeout(this.b);
    }

    /* JADX WARNING: type inference failed for: r8v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: type inference failed for: r1v16, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r1v18 */
    /* JADX WARNING: type inference failed for: r1v19 */
    /* JADX WARNING: type inference failed for: r1v20 */
    /* JADX WARNING: type inference failed for: r1v21 */
    /* JADX WARNING: type inference failed for: r1v22 */
    /* JADX WARNING: type inference failed for: r1v23 */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.String, code=null, for r8v0, types: [java.lang.String] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v9
      assigns: []
      uses: []
      mth insns count: 94
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0045 A[SYNTHETIC, Splitter:B:22:0x0045] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:17:0x0036=Splitter:B:17:0x0036, B:27:0x004b=Splitter:B:27:0x004b} */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.loc.br a(java.lang.String r8, boolean r9, java.lang.String r10, java.util.Map<java.lang.String, java.lang.String> r11, byte[] r12) throws com.loc.k {
        /*
            r7 = this;
            r6 = 0
            r5 = 1
            r0 = r7
            r1 = r8
            r2 = r9
            r3 = r10
            r4 = r11
            java.net.HttpURLConnection r1 = r0.a(r1, r2, r3, r4, r5)     // Catch:{ ConnectException -> 0x0034, MalformedURLException -> 0x0049, UnknownHostException -> 0x0057, SocketException -> 0x0065, SocketTimeoutException -> 0x0073, InterruptedIOException -> 0x0081, IOException -> 0x008c, k -> 0x009a, Throwable -> 0x00a6, all -> 0x00c5 }
            if (r12 == 0) goto L_0x001f
            int r0 = r12.length     // Catch:{ ConnectException -> 0x00da, MalformedURLException -> 0x00d7, UnknownHostException -> 0x00d5, SocketException -> 0x00d3, SocketTimeoutException -> 0x00d1, InterruptedIOException -> 0x00cf, IOException -> 0x00cd, k -> 0x00cb, Throwable -> 0x00c9 }
            if (r0 <= 0) goto L_0x001f
            java.io.DataOutputStream r0 = new java.io.DataOutputStream     // Catch:{ ConnectException -> 0x00da, MalformedURLException -> 0x00d7, UnknownHostException -> 0x00d5, SocketException -> 0x00d3, SocketTimeoutException -> 0x00d1, InterruptedIOException -> 0x00cf, IOException -> 0x00cd, k -> 0x00cb, Throwable -> 0x00c9 }
            java.io.OutputStream r2 = r1.getOutputStream()     // Catch:{ ConnectException -> 0x00da, MalformedURLException -> 0x00d7, UnknownHostException -> 0x00d5, SocketException -> 0x00d3, SocketTimeoutException -> 0x00d1, InterruptedIOException -> 0x00cf, IOException -> 0x00cd, k -> 0x00cb, Throwable -> 0x00c9 }
            r0.<init>(r2)     // Catch:{ ConnectException -> 0x00da, MalformedURLException -> 0x00d7, UnknownHostException -> 0x00d5, SocketException -> 0x00d3, SocketTimeoutException -> 0x00d1, InterruptedIOException -> 0x00cf, IOException -> 0x00cd, k -> 0x00cb, Throwable -> 0x00c9 }
            r0.write(r12)     // Catch:{ ConnectException -> 0x00da, MalformedURLException -> 0x00d7, UnknownHostException -> 0x00d5, SocketException -> 0x00d3, SocketTimeoutException -> 0x00d1, InterruptedIOException -> 0x00cf, IOException -> 0x00cd, k -> 0x00cb, Throwable -> 0x00c9 }
            r0.close()     // Catch:{ ConnectException -> 0x00da, MalformedURLException -> 0x00d7, UnknownHostException -> 0x00d5, SocketException -> 0x00d3, SocketTimeoutException -> 0x00d1, InterruptedIOException -> 0x00cf, IOException -> 0x00cd, k -> 0x00cb, Throwable -> 0x00c9 }
        L_0x001f:
            com.loc.br r0 = r7.a(r1)     // Catch:{ ConnectException -> 0x00da, MalformedURLException -> 0x00d7, UnknownHostException -> 0x00d5, SocketException -> 0x00d3, SocketTimeoutException -> 0x00d1, InterruptedIOException -> 0x00cf, IOException -> 0x00cd, k -> 0x00cb, Throwable -> 0x00c9 }
            if (r1 == 0) goto L_0x0028
            r1.disconnect()     // Catch:{ Throwable -> 0x0029 }
        L_0x0028:
            return r0
        L_0x0029:
            r1 = move-exception
            java.lang.String r2 = "ht"
            java.lang.String r3 = "mPt"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x0028
        L_0x0034:
            r0 = move-exception
            r1 = r6
        L_0x0036:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0042 }
            com.loc.k r0 = new com.loc.k     // Catch:{ all -> 0x0042 }
            java.lang.String r2 = "http连接失败 - ConnectionException"
            r0.<init>(r2)     // Catch:{ all -> 0x0042 }
            throw r0     // Catch:{ all -> 0x0042 }
        L_0x0042:
            r0 = move-exception
        L_0x0043:
            if (r1 == 0) goto L_0x0048
            r1.disconnect()     // Catch:{ Throwable -> 0x00ba }
        L_0x0048:
            throw r0
        L_0x0049:
            r0 = move-exception
            r1 = r6
        L_0x004b:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0042 }
            com.loc.k r0 = new com.loc.k     // Catch:{ all -> 0x0042 }
            java.lang.String r2 = "url异常 - MalformedURLException"
            r0.<init>(r2)     // Catch:{ all -> 0x0042 }
            throw r0     // Catch:{ all -> 0x0042 }
        L_0x0057:
            r0 = move-exception
            r1 = r6
        L_0x0059:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0042 }
            com.loc.k r0 = new com.loc.k     // Catch:{ all -> 0x0042 }
            java.lang.String r2 = "未知主机 - UnKnowHostException"
            r0.<init>(r2)     // Catch:{ all -> 0x0042 }
            throw r0     // Catch:{ all -> 0x0042 }
        L_0x0065:
            r0 = move-exception
            r1 = r6
        L_0x0067:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0042 }
            com.loc.k r0 = new com.loc.k     // Catch:{ all -> 0x0042 }
            java.lang.String r2 = "socket 连接异常 - SocketException"
            r0.<init>(r2)     // Catch:{ all -> 0x0042 }
            throw r0     // Catch:{ all -> 0x0042 }
        L_0x0073:
            r0 = move-exception
            r1 = r6
        L_0x0075:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0042 }
            com.loc.k r0 = new com.loc.k     // Catch:{ all -> 0x0042 }
            java.lang.String r2 = "socket 连接超时 - SocketTimeoutException"
            r0.<init>(r2)     // Catch:{ all -> 0x0042 }
            throw r0     // Catch:{ all -> 0x0042 }
        L_0x0081:
            r0 = move-exception
            r1 = r6
        L_0x0083:
            com.loc.k r0 = new com.loc.k     // Catch:{ all -> 0x0042 }
            java.lang.String r2 = "未知的错误"
            r0.<init>(r2)     // Catch:{ all -> 0x0042 }
            throw r0     // Catch:{ all -> 0x0042 }
        L_0x008c:
            r0 = move-exception
            r1 = r6
        L_0x008e:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0042 }
            com.loc.k r0 = new com.loc.k     // Catch:{ all -> 0x0042 }
            java.lang.String r2 = "IO 操作异常 - IOException"
            r0.<init>(r2)     // Catch:{ all -> 0x0042 }
            throw r0     // Catch:{ all -> 0x0042 }
        L_0x009a:
            r0 = move-exception
            r1 = r6
        L_0x009c:
            java.lang.String r2 = "ht"
            java.lang.String r3 = "mPt"
            com.loc.ag.a(r0, r2, r3)     // Catch:{ all -> 0x0042 }
            throw r0     // Catch:{ all -> 0x0042 }
        L_0x00a6:
            r0 = move-exception
            r1 = r6
        L_0x00a8:
            java.lang.String r2 = "ht"
            java.lang.String r3 = "mPt"
            com.loc.ag.a(r0, r2, r3)     // Catch:{ all -> 0x0042 }
            com.loc.k r0 = new com.loc.k     // Catch:{ all -> 0x0042 }
            java.lang.String r2 = "未知的错误"
            r0.<init>(r2)     // Catch:{ all -> 0x0042 }
            throw r0     // Catch:{ all -> 0x0042 }
        L_0x00ba:
            r1 = move-exception
            java.lang.String r2 = "ht"
            java.lang.String r3 = "mPt"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x0048
        L_0x00c5:
            r0 = move-exception
            r1 = r6
            goto L_0x0043
        L_0x00c9:
            r0 = move-exception
            goto L_0x00a8
        L_0x00cb:
            r0 = move-exception
            goto L_0x009c
        L_0x00cd:
            r0 = move-exception
            goto L_0x008e
        L_0x00cf:
            r0 = move-exception
            goto L_0x0083
        L_0x00d1:
            r0 = move-exception
            goto L_0x0075
        L_0x00d3:
            r0 = move-exception
            goto L_0x0067
        L_0x00d5:
            r0 = move-exception
            goto L_0x0059
        L_0x00d7:
            r0 = move-exception
            goto L_0x004b
        L_0x00da:
            r0 = move-exception
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bp.a(java.lang.String, boolean, java.lang.String, java.util.Map, byte[]):com.loc.br");
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        this.h = 0;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00dc A[SYNTHETIC, Splitter:B:46:0x00dc] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00e1 A[SYNTHETIC, Splitter:B:49:0x00e1] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x010f A[SYNTHETIC, Splitter:B:61:0x010f] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0114 A[SYNTHETIC, Splitter:B:64:0x0114] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.String r9, boolean r10, java.lang.String r11, java.util.Map<java.lang.String, java.lang.String> r12, java.util.Map<java.lang.String, java.lang.String> r13, byte[] r14, com.loc.bo.a r15) {
        /*
            r8 = this;
            r7 = 0
            r6 = 0
            if (r15 != 0) goto L_0x0005
        L_0x0004:
            return
        L_0x0005:
            java.lang.String r0 = a(r13)     // Catch:{ Throwable -> 0x0190, all -> 0x0189 }
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ Throwable -> 0x0190, all -> 0x0189 }
            r1.<init>()     // Catch:{ Throwable -> 0x0190, all -> 0x0189 }
            r1.append(r9)     // Catch:{ Throwable -> 0x0190, all -> 0x0189 }
            if (r0 == 0) goto L_0x001d
            java.lang.String r2 = "?"
            java.lang.StringBuffer r2 = r1.append(r2)     // Catch:{ Throwable -> 0x0190, all -> 0x0189 }
            r2.append(r0)     // Catch:{ Throwable -> 0x0190, all -> 0x0189 }
        L_0x001d:
            if (r14 == 0) goto L_0x00f2
            int r0 = r14.length     // Catch:{ Throwable -> 0x0190, all -> 0x0189 }
            if (r0 <= 0) goto L_0x00f2
            r5 = 1
        L_0x0023:
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0190, all -> 0x0189 }
            r0 = r8
            r2 = r10
            r3 = r11
            r4 = r12
            java.net.HttpURLConnection r0 = r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0190, all -> 0x0189 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.String r2 = "bytes="
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            long r2 = r8.h     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.String r2 = "-"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.String r2 = "RANGE"
            r0.setRequestProperty(r2, r1)     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            if (r5 == 0) goto L_0x005f
            java.io.DataOutputStream r1 = new java.io.DataOutputStream     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.io.OutputStream r2 = r0.getOutputStream()     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            r1.write(r14)     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            r1.close()     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
        L_0x005f:
            r0.connect()     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            int r3 = r0.getResponseCode()     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            r1 = 200(0xc8, float:2.8E-43)
            if (r3 == r1) goto L_0x00f5
            r1 = 1
            r2 = r1
        L_0x006c:
            r1 = 206(0xce, float:2.89E-43)
            if (r3 == r1) goto L_0x00f9
            r1 = 1
        L_0x0071:
            r1 = r1 & r2
            if (r1 == 0) goto L_0x009b
            com.loc.k r1 = new com.loc.k     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.String r4 = "网络异常原因："
            r2.<init>(r4)     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.String r4 = r0.getResponseMessage()     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.String r4 = " 网络异常状态码："
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            r15.d()     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
        L_0x009b:
            java.io.InputStream r1 = r0.getInputStream()     // Catch:{ Throwable -> 0x0195, all -> 0x018b }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ Throwable -> 0x00d6 }
        L_0x00a3:
            boolean r3 = java.lang.Thread.interrupted()     // Catch:{ Throwable -> 0x00d6 }
            if (r3 != 0) goto L_0x0118
            boolean r3 = r8.f     // Catch:{ Throwable -> 0x00d6 }
            if (r3 != 0) goto L_0x0118
            r3 = 0
            r4 = 1024(0x400, float:1.435E-42)
            int r3 = r1.read(r2, r3, r4)     // Catch:{ Throwable -> 0x00d6 }
            if (r3 <= 0) goto L_0x0118
            long r4 = r8.g     // Catch:{ Throwable -> 0x00d6 }
            r6 = -1
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 == 0) goto L_0x00c6
            long r4 = r8.h     // Catch:{ Throwable -> 0x00d6 }
            long r6 = r8.g     // Catch:{ Throwable -> 0x00d6 }
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 >= 0) goto L_0x0118
        L_0x00c6:
            r4 = 1024(0x400, float:1.435E-42)
            if (r3 != r4) goto L_0x00fc
            long r4 = r8.h     // Catch:{ Throwable -> 0x00d6 }
            r15.a(r2, r4)     // Catch:{ Throwable -> 0x00d6 }
        L_0x00cf:
            long r4 = r8.h     // Catch:{ Throwable -> 0x00d6 }
            long r6 = (long) r3     // Catch:{ Throwable -> 0x00d6 }
            long r4 = r4 + r6
            r8.h = r4     // Catch:{ Throwable -> 0x00d6 }
            goto L_0x00a3
        L_0x00d6:
            r2 = move-exception
        L_0x00d7:
            r15.d()     // Catch:{ all -> 0x0109 }
            if (r1 == 0) goto L_0x00df
            r1.close()     // Catch:{ IOException -> 0x0151, Throwable -> 0x015c }
        L_0x00df:
            if (r0 == 0) goto L_0x0004
            r0.disconnect()     // Catch:{ Throwable -> 0x00e6 }
            goto L_0x0004
        L_0x00e6:
            r0 = move-exception
            java.lang.String r1 = "ht"
            java.lang.String r2 = "mdr"
            com.loc.ag.a(r0, r1, r2)
            goto L_0x0004
        L_0x00f2:
            r5 = 0
            goto L_0x0023
        L_0x00f5:
            r1 = 0
            r2 = r1
            goto L_0x006c
        L_0x00f9:
            r1 = 0
            goto L_0x0071
        L_0x00fc:
            byte[] r4 = new byte[r3]     // Catch:{ Throwable -> 0x00d6 }
            r5 = 0
            r6 = 0
            java.lang.System.arraycopy(r2, r5, r4, r6, r3)     // Catch:{ Throwable -> 0x00d6 }
            long r6 = r8.h     // Catch:{ Throwable -> 0x00d6 }
            r15.a(r4, r6)     // Catch:{ Throwable -> 0x00d6 }
            goto L_0x00cf
        L_0x0109:
            r2 = move-exception
            r6 = r0
            r7 = r1
            r0 = r2
        L_0x010d:
            if (r7 == 0) goto L_0x0112
            r7.close()     // Catch:{ IOException -> 0x0168, Throwable -> 0x0173 }
        L_0x0112:
            if (r6 == 0) goto L_0x0117
            r6.disconnect()     // Catch:{ Throwable -> 0x017e }
        L_0x0117:
            throw r0
        L_0x0118:
            boolean r2 = r8.f     // Catch:{ Throwable -> 0x00d6 }
            if (r2 == 0) goto L_0x0137
            r15.c()     // Catch:{ Throwable -> 0x00d6 }
        L_0x011f:
            if (r1 == 0) goto L_0x0124
            r1.close()     // Catch:{ IOException -> 0x013b, Throwable -> 0x0146 }
        L_0x0124:
            if (r0 == 0) goto L_0x0004
            r0.disconnect()     // Catch:{ Throwable -> 0x012b }
            goto L_0x0004
        L_0x012b:
            r0 = move-exception
            java.lang.String r1 = "ht"
            java.lang.String r2 = "mdr"
            com.loc.ag.a(r0, r1, r2)
            goto L_0x0004
        L_0x0137:
            r15.b()     // Catch:{ Throwable -> 0x00d6 }
            goto L_0x011f
        L_0x013b:
            r1 = move-exception
            java.lang.String r2 = "ht"
            java.lang.String r3 = "mdr"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x0124
        L_0x0146:
            r1 = move-exception
            java.lang.String r2 = "ht"
            java.lang.String r3 = "mdr"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x0124
        L_0x0151:
            r1 = move-exception
            java.lang.String r2 = "ht"
            java.lang.String r3 = "mdr"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x00df
        L_0x015c:
            r1 = move-exception
            java.lang.String r2 = "ht"
            java.lang.String r3 = "mdr"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x00df
        L_0x0168:
            r1 = move-exception
            java.lang.String r2 = "ht"
            java.lang.String r3 = "mdr"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x0112
        L_0x0173:
            r1 = move-exception
            java.lang.String r2 = "ht"
            java.lang.String r3 = "mdr"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x0112
        L_0x017e:
            r1 = move-exception
            java.lang.String r2 = "ht"
            java.lang.String r3 = "mdr"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x0117
        L_0x0189:
            r0 = move-exception
            goto L_0x010d
        L_0x018b:
            r1 = move-exception
            r6 = r0
            r0 = r1
            goto L_0x010d
        L_0x0190:
            r0 = move-exception
            r0 = r6
            r1 = r7
            goto L_0x00d7
        L_0x0195:
            r1 = move-exception
            r1 = r7
            goto L_0x00d7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bp.a(java.lang.String, boolean, java.lang.String, java.util.Map, java.util.Map, byte[], com.loc.bo$a):void");
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        this.g = -1;
    }
}
