package com.loc;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.iwown.my_module.utility.Constants;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AuthConfigManager */
public final class m {
    public static int a = -1;
    public static String b = "";

    /* compiled from: AuthConfigManager */
    public static class a {
        @Deprecated
        public c A;
        public c B;
        public b C;
        public b D;
        public b E;
        public b F;
        public e G;
        /* access modifiers changed from: private */
        public boolean H;
        public String a;
        public int b = -1;
        @Deprecated
        public JSONObject c;
        @Deprecated
        public JSONObject d;
        @Deprecated
        public JSONObject e;
        @Deprecated
        public JSONObject f;
        @Deprecated
        public JSONObject g;
        @Deprecated
        public JSONObject h;
        @Deprecated
        public JSONObject i;
        @Deprecated
        public JSONObject j;
        @Deprecated
        public JSONObject k;
        @Deprecated
        public JSONObject l;
        @Deprecated
        public JSONObject m;
        @Deprecated
        public JSONObject n;
        @Deprecated
        public JSONObject o;
        @Deprecated
        public JSONObject p;
        @Deprecated
        public JSONObject q;
        @Deprecated
        public JSONObject r;
        @Deprecated
        public JSONObject s;
        @Deprecated
        public JSONObject t;
        @Deprecated
        public JSONObject u;
        @Deprecated
        public JSONObject v;
        public JSONObject w;
        public C0003a x;
        public d y;
        public f z;

        /* renamed from: com.loc.m$a$a reason: collision with other inner class name */
        /* compiled from: AuthConfigManager */
        public static class C0003a {
            public boolean a;
            public boolean b;
            public JSONObject c;
        }

        /* compiled from: AuthConfigManager */
        public static class b {
            public boolean a;
            public String b;
            public String c;
            public String d;
            public boolean e;
        }

        /* compiled from: AuthConfigManager */
        public static class c {
            public String a;
            public String b;
        }

        /* compiled from: AuthConfigManager */
        public static class d {
            public String a;
            public String b;
            public String c;
        }

        /* compiled from: AuthConfigManager */
        public static class e {
            public boolean a;
            public boolean b;
            public boolean c;
            public String d;
            public String e;
            public String f;
        }

        /* compiled from: AuthConfigManager */
        public static class f {
            public boolean a;
        }

        public final boolean a() {
            return this.H;
        }
    }

    /* compiled from: AuthConfigManager */
    static class b extends bn {
        private String f;
        private Map<String, String> g = null;
        private boolean h;

        b(Context context, v vVar, String str) {
            super(context, vVar);
            this.f = str;
            this.h = VERSION.SDK_INT != 19;
        }

        public final boolean a() {
            return this.h;
        }

        public final byte[] a_() {
            return null;
        }

        public final Map<String, String> b() {
            return null;
        }

        public final String c() {
            return this.h ? "https://restapi.amap.com/v3/iasdkauth" : "http://restapi.amap.com/v3/iasdkauth";
        }

        public final byte[] e() {
            String v = p.v(this.a);
            if (TextUtils.isEmpty(v)) {
                v = p.i(this.a);
            }
            if (!TextUtils.isEmpty(v)) {
                v = s.b(new StringBuilder(v).reverse().toString());
            }
            HashMap hashMap = new HashMap();
            hashMap.put("authkey", this.f);
            hashMap.put("plattype", Constants.APPSYSTEM);
            hashMap.put("product", this.b.a());
            hashMap.put("version", this.b.b());
            hashMap.put("output", "json");
            hashMap.put("androidversion", VERSION.SDK_INT);
            hashMap.put("deviceId", v);
            hashMap.put("manufacture", Build.MANUFACTURER);
            if (this.g != null && !this.g.isEmpty()) {
                hashMap.putAll(this.g);
            }
            hashMap.put("abitype", w.a(this.a));
            hashMap.put("ext", this.b.d());
            return w.a(w.a((Map<String, String>) hashMap));
        }

        /* access modifiers changed from: protected */
        public final String f() {
            return "3.0";
        }
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r4v6, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r3v7, types: [com.loc.br] */
    /* JADX WARNING: type inference failed for: r1v10, types: [java.lang.CharSequence] */
    /* JADX WARNING: type inference failed for: r1v11, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v102, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v28, types: [com.loc.br] */
    /* JADX WARNING: type inference failed for: r4v39 */
    /* JADX WARNING: type inference failed for: r4v40, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r3v20, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v119 */
    /* JADX WARNING: type inference failed for: r3v21 */
    /* JADX WARNING: type inference failed for: r4v41 */
    /* JADX WARNING: type inference failed for: r4v42 */
    /* JADX WARNING: type inference failed for: r4v43 */
    /* JADX WARNING: type inference failed for: r4v44, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r4v45 */
    /* JADX WARNING: type inference failed for: r4v46 */
    /* JADX WARNING: type inference failed for: r4v47 */
    /* JADX WARNING: type inference failed for: r1v123 */
    /* JADX WARNING: type inference failed for: r2v29 */
    /* JADX WARNING: type inference failed for: r2v30 */
    /* JADX WARNING: type inference failed for: r2v31 */
    /* JADX WARNING: type inference failed for: r2v32 */
    /* JADX WARNING: type inference failed for: r2v33 */
    /* JADX WARNING: type inference failed for: r2v34 */
    /* JADX WARNING: type inference failed for: r4v48 */
    /* JADX WARNING: type inference failed for: r4v49 */
    /* JADX WARNING: type inference failed for: r4v50 */
    /* JADX WARNING: type inference failed for: r4v51 */
    /* JADX WARNING: type inference failed for: r4v52 */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x009f, code lost:
        r2 = r3;
        r4 = r3;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r4v0
      assigns: []
      uses: []
      mth insns count: 336
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
    /* JADX WARNING: Removed duplicated region for block: B:24:0x009e A[ExcHandler: IllegalBlockSizeException (e javax.crypto.IllegalBlockSizeException), PHI: r13 
      PHI: (r13v4 java.lang.String) = (r13v0 java.lang.String), (r13v0 java.lang.String), (r13v0 java.lang.String), (r13v8 java.lang.String), (r13v9 java.lang.String), (r13v9 java.lang.String), (r13v10 java.lang.String) binds: [B:1:0x0014, B:2:?, B:3:0x0019, B:21:0x0095, B:15:0x0081, B:16:?, B:4:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:1:0x0014] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b3  */
    /* JADX WARNING: Unknown variable types count: 15 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.loc.m.a a(android.content.Context r11, com.loc.v r12, java.lang.String r13, boolean r14) {
        /*
            r10 = 1
            r3 = 0
            r5 = 0
            com.loc.m$a r0 = new com.loc.m$a
            r0.<init>()
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            r0.w = r1
            com.loc.r r1 = com.loc.r.a.a
            r1.a(r11)
            com.loc.bm r1 = new com.loc.bm     // Catch:{ k -> 0x0082, IllegalBlockSizeException -> 0x009e, Throwable -> 0x00a4 }
            r1.<init>()     // Catch:{ k -> 0x0082, IllegalBlockSizeException -> 0x009e, Throwable -> 0x00a4 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ k -> 0x0080, Throwable -> 0x0094, IllegalBlockSizeException -> 0x009e }
            r1.<init>()     // Catch:{ k -> 0x0080, Throwable -> 0x0094, IllegalBlockSizeException -> 0x009e }
            java.lang.StringBuilder r1 = r1.append(r13)     // Catch:{ k -> 0x0080, Throwable -> 0x0094, IllegalBlockSizeException -> 0x009e }
            java.lang.String r2 = ";14N;15K;16H"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ k -> 0x0080, Throwable -> 0x0094, IllegalBlockSizeException -> 0x009e }
            java.lang.String r13 = r1.toString()     // Catch:{ k -> 0x0080, Throwable -> 0x0094, IllegalBlockSizeException -> 0x009e }
            com.loc.m$b r1 = new com.loc.m$b     // Catch:{ k -> 0x0080, Throwable -> 0x0094, IllegalBlockSizeException -> 0x009e }
            r1.<init>(r11, r12, r13)     // Catch:{ k -> 0x0080, Throwable -> 0x0094, IllegalBlockSizeException -> 0x009e }
            boolean r2 = r1.a()     // Catch:{ k -> 0x0080, Throwable -> 0x0094, IllegalBlockSizeException -> 0x009e }
            com.loc.br r2 = com.loc.bm.a(r1, r2)     // Catch:{ k -> 0x0080, Throwable -> 0x0094, IllegalBlockSizeException -> 0x009e }
            if (r2 == 0) goto L_0x03b7
            byte[] r4 = r2.a     // Catch:{ k -> 0x03b0, IllegalBlockSizeException -> 0x03a9, Throwable -> 0x03a2 }
        L_0x003e:
            r1 = 16
            byte[] r1 = new byte[r1]     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            int r6 = r4.length     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            int r6 = r6 + -16
            byte[] r6 = new byte[r6]     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            r7 = 0
            r8 = 0
            r9 = 16
            java.lang.System.arraycopy(r4, r7, r1, r8, r9)     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            r7 = 16
            r8 = 0
            int r9 = r4.length     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            int r9 = r9 + -16
            java.lang.System.arraycopy(r4, r7, r6, r8, r9)     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            javax.crypto.spec.SecretKeySpec r7 = new javax.crypto.spec.SecretKeySpec     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            java.lang.String r8 = "AES"
            r7.<init>(r1, r8)     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            java.lang.String r1 = "AES/CBC/PKCS5Padding"
            javax.crypto.Cipher r1 = javax.crypto.Cipher.getInstance(r1)     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            javax.crypto.spec.IvParameterSpec r8 = new javax.crypto.spec.IvParameterSpec     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            byte[] r9 = com.loc.w.c()     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            r8.<init>(r9)     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            r9 = 2
            r1.init(r9, r7, r8)     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            byte[] r1 = r1.doFinal(r6)     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            java.lang.String r3 = com.loc.w.a(r1)     // Catch:{ k -> 0x03b4, IllegalBlockSizeException -> 0x03ad, Throwable -> 0x03a6 }
            r1 = r3
            r3 = r2
        L_0x007d:
            if (r4 != 0) goto L_0x00b3
        L_0x007f:
            return r0
        L_0x0080:
            r1 = move-exception
            throw r1     // Catch:{ k -> 0x0082, IllegalBlockSizeException -> 0x009e, Throwable -> 0x00a4 }
        L_0x0082:
            r1 = move-exception
            r2 = r3
            r4 = r3
        L_0x0085:
            java.lang.String r6 = r1.a()
            r0.a = r6
            java.lang.String r6 = "/v3/iasdkauth"
            com.loc.aj.a(r12, r6, r1)
            r1 = r3
            r3 = r2
            goto L_0x007d
        L_0x0094:
            r1 = move-exception
            com.loc.k r1 = new com.loc.k     // Catch:{ k -> 0x0082, IllegalBlockSizeException -> 0x009e, Throwable -> 0x00a4 }
            java.lang.String r2 = "未知的错误"
            r1.<init>(r2)     // Catch:{ k -> 0x0082, IllegalBlockSizeException -> 0x009e, Throwable -> 0x00a4 }
            throw r1     // Catch:{ k -> 0x0082, IllegalBlockSizeException -> 0x009e, Throwable -> 0x00a4 }
        L_0x009e:
            r1 = move-exception
            r2 = r3
            r4 = r3
        L_0x00a1:
            r1 = r3
            r3 = r2
            goto L_0x007d
        L_0x00a4:
            r1 = move-exception
            r2 = r3
            r4 = r3
        L_0x00a7:
            java.lang.String r6 = "at"
            java.lang.String r7 = "lc"
            com.loc.aj.b(r1, r6, r7)
            r1 = r3
            r3 = r2
            goto L_0x007d
        L_0x00b3:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto L_0x00bd
            java.lang.String r1 = com.loc.w.a(r4)
        L_0x00bd:
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0175 }
            r4.<init>(r1)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r1 = "status"
            boolean r1 = r4.has(r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x007f
            java.lang.String r1 = "status"
            int r1 = r4.getInt(r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 != r10) goto L_0x012a
            r1 = 1
            a = r1     // Catch:{ Throwable -> 0x0175 }
        L_0x00d7:
            java.lang.String r1 = "ver"
            boolean r1 = r4.has(r1)     // Catch:{ Throwable -> 0x0181 }
            if (r1 == 0) goto L_0x00e9
            java.lang.String r1 = "ver"
            int r1 = r4.getInt(r1)     // Catch:{ Throwable -> 0x0181 }
            r0.b = r1     // Catch:{ Throwable -> 0x0181 }
        L_0x00e9:
            java.lang.String r1 = "result"
            boolean r1 = com.loc.w.a(r4, r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x007f
            com.loc.m$a$a r2 = new com.loc.m$a$a     // Catch:{ Throwable -> 0x0175 }
            r2.<init>()     // Catch:{ Throwable -> 0x0175 }
            r1 = 0
            r2.a = r1     // Catch:{ Throwable -> 0x0175 }
            r1 = 0
            r2.b = r1     // Catch:{ Throwable -> 0x0175 }
            r0.x = r2     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r1 = "result"
            org.json.JSONObject r3 = r4.getJSONObject(r1)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r1 = ";"
            java.lang.String[] r4 = r13.split(r1)     // Catch:{ Throwable -> 0x018d }
            if (r4 == 0) goto L_0x0197
            int r1 = r4.length     // Catch:{ Throwable -> 0x018d }
            if (r1 <= 0) goto L_0x0197
            int r6 = r4.length     // Catch:{ Throwable -> 0x018d }
            r1 = r5
        L_0x0114:
            if (r1 >= r6) goto L_0x0197
            r5 = r4[r1]     // Catch:{ Throwable -> 0x018d }
            boolean r7 = r3.has(r5)     // Catch:{ Throwable -> 0x018d }
            if (r7 == 0) goto L_0x0127
            org.json.JSONObject r7 = r0.w     // Catch:{ Throwable -> 0x018d }
            java.lang.Object r8 = r3.get(r5)     // Catch:{ Throwable -> 0x018d }
            r7.putOpt(r5, r8)     // Catch:{ Throwable -> 0x018d }
        L_0x0127:
            int r1 = r1 + 1
            goto L_0x0114
        L_0x012a:
            if (r1 != 0) goto L_0x00d7
            java.lang.String r1 = "authcsid"
            java.lang.String r2 = "authgsid"
            if (r3 == 0) goto L_0x0138
            java.lang.String r1 = r3.c     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r2 = r3.d     // Catch:{ Throwable -> 0x0175 }
        L_0x0138:
            com.loc.w.a(r11, r1, r2, r4)     // Catch:{ Throwable -> 0x0175 }
            r1 = 0
            a = r1     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r1 = "info"
            boolean r1 = r4.has(r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x0150
            java.lang.String r1 = "info"
            java.lang.String r1 = r4.getString(r1)     // Catch:{ Throwable -> 0x0175 }
            b = r1     // Catch:{ Throwable -> 0x0175 }
        L_0x0150:
            java.lang.String r1 = ""
            java.lang.String r3 = "infocode"
            boolean r3 = r4.has(r3)     // Catch:{ Throwable -> 0x0175 }
            if (r3 == 0) goto L_0x0163
            java.lang.String r1 = "infocode"
            java.lang.String r1 = r4.getString(r1)     // Catch:{ Throwable -> 0x0175 }
        L_0x0163:
            java.lang.String r3 = "/v3/iasdkauth"
            java.lang.String r6 = b     // Catch:{ Throwable -> 0x0175 }
            com.loc.aj.a(r12, r3, r6, r2, r1)     // Catch:{ Throwable -> 0x0175 }
            int r1 = a     // Catch:{ Throwable -> 0x0175 }
            if (r1 != 0) goto L_0x00d7
            java.lang.String r1 = b     // Catch:{ Throwable -> 0x0175 }
            r0.a = r1     // Catch:{ Throwable -> 0x0175 }
            goto L_0x007f
        L_0x0175:
            r1 = move-exception
            java.lang.String r2 = "at"
            java.lang.String r3 = "lc"
            com.loc.ag.a(r1, r2, r3)
            goto L_0x007f
        L_0x0181:
            r1 = move-exception
            java.lang.String r2 = "at"
            java.lang.String r3 = "lc"
            com.loc.ag.a(r1, r2, r3)     // Catch:{ Throwable -> 0x0175 }
            goto L_0x00e9
        L_0x018d:
            r1 = move-exception
            java.lang.String r4 = "at"
            java.lang.String r5 = "co"
            com.loc.ag.a(r1, r4, r5)     // Catch:{ Throwable -> 0x0175 }
        L_0x0197:
            java.lang.String r1 = "16H"
            boolean r1 = com.loc.w.a(r3, r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x01b6
            java.lang.String r1 = "16H"
            org.json.JSONObject r1 = r3.getJSONObject(r1)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r4 = "able"
            java.lang.String r1 = r1.optString(r4)     // Catch:{ Throwable -> 0x0175 }
            r4 = 0
            boolean r1 = a(r1, r4)     // Catch:{ Throwable -> 0x0175 }
            r0.H = r1     // Catch:{ Throwable -> 0x0175 }
        L_0x01b6:
            java.lang.String r1 = "11K"
            boolean r1 = com.loc.w.a(r3, r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x01e6
            java.lang.String r1 = "11K"
            org.json.JSONObject r1 = r3.getJSONObject(r1)     // Catch:{ Throwable -> 0x0368 }
            java.lang.String r4 = "able"
            java.lang.String r4 = r1.getString(r4)     // Catch:{ Throwable -> 0x0368 }
            r5 = 0
            boolean r4 = a(r4, r5)     // Catch:{ Throwable -> 0x0368 }
            r2.a = r4     // Catch:{ Throwable -> 0x0368 }
            java.lang.String r4 = "off"
            boolean r4 = r1.has(r4)     // Catch:{ Throwable -> 0x0368 }
            if (r4 == 0) goto L_0x01e6
            java.lang.String r4 = "off"
            org.json.JSONObject r1 = r1.getJSONObject(r4)     // Catch:{ Throwable -> 0x0368 }
            r2.c = r1     // Catch:{ Throwable -> 0x0368 }
        L_0x01e6:
            java.lang.String r1 = "001"
            boolean r1 = com.loc.w.a(r3, r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x0226
            java.lang.String r1 = "001"
            org.json.JSONObject r1 = r3.getJSONObject(r1)     // Catch:{ Throwable -> 0x0175 }
            com.loc.m$a$d r2 = new com.loc.m$a$d     // Catch:{ Throwable -> 0x0175 }
            r2.<init>()     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x0224
            java.lang.String r4 = "md5"
            java.lang.String r4 = a(r1, r4)     // Catch:{ Throwable -> 0x037c }
            java.lang.String r5 = "url"
            java.lang.String r5 = a(r1, r5)     // Catch:{ Throwable -> 0x037c }
            java.lang.String r6 = "sdkversion"
            java.lang.String r1 = a(r1, r6)     // Catch:{ Throwable -> 0x037c }
            boolean r6 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x037c }
            if (r6 != 0) goto L_0x0224
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x037c }
            if (r6 != 0) goto L_0x0224
            boolean r6 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x037c }
            if (r6 == 0) goto L_0x0374
        L_0x0224:
            r0.y = r2     // Catch:{ Throwable -> 0x0175 }
        L_0x0226:
            java.lang.String r1 = "002"
            boolean r1 = com.loc.w.a(r3, r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x0240
            java.lang.String r1 = "002"
            org.json.JSONObject r1 = r3.getJSONObject(r1)     // Catch:{ Throwable -> 0x0175 }
            com.loc.m$a$c r2 = new com.loc.m$a$c     // Catch:{ Throwable -> 0x0175 }
            r2.<init>()     // Catch:{ Throwable -> 0x0175 }
            a(r1, r2)     // Catch:{ Throwable -> 0x0175 }
            r0.A = r2     // Catch:{ Throwable -> 0x0175 }
        L_0x0240:
            java.lang.String r1 = "14S"
            boolean r1 = com.loc.w.a(r3, r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x025a
            java.lang.String r1 = "14S"
            org.json.JSONObject r1 = r3.getJSONObject(r1)     // Catch:{ Throwable -> 0x0175 }
            com.loc.m$a$c r2 = new com.loc.m$a$c     // Catch:{ Throwable -> 0x0175 }
            r2.<init>()     // Catch:{ Throwable -> 0x0175 }
            a(r1, r2)     // Catch:{ Throwable -> 0x0175 }
            r0.B = r2     // Catch:{ Throwable -> 0x0175 }
        L_0x025a:
            a(r0, r3)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r1 = "14Z"
            boolean r1 = com.loc.w.a(r3, r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x02b9
            java.lang.String r1 = "14Z"
            org.json.JSONObject r1 = r3.getJSONObject(r1)     // Catch:{ Throwable -> 0x0175 }
            com.loc.m$a$e r2 = new com.loc.m$a$e     // Catch:{ Throwable -> 0x0175 }
            r2.<init>()     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r4 = "md5"
            java.lang.String r4 = a(r1, r4)     // Catch:{ Throwable -> 0x0388 }
            java.lang.String r5 = "md5info"
            java.lang.String r5 = a(r1, r5)     // Catch:{ Throwable -> 0x0388 }
            java.lang.String r6 = "url"
            java.lang.String r6 = a(r1, r6)     // Catch:{ Throwable -> 0x0388 }
            java.lang.String r7 = "able"
            java.lang.String r7 = a(r1, r7)     // Catch:{ Throwable -> 0x0388 }
            java.lang.String r8 = "on"
            java.lang.String r8 = a(r1, r8)     // Catch:{ Throwable -> 0x0388 }
            java.lang.String r9 = "mobileable"
            java.lang.String r1 = a(r1, r9)     // Catch:{ Throwable -> 0x0388 }
            r2.e = r4     // Catch:{ Throwable -> 0x0388 }
            r2.f = r5     // Catch:{ Throwable -> 0x0388 }
            r2.d = r6     // Catch:{ Throwable -> 0x0388 }
            r4 = 0
            boolean r4 = a(r7, r4)     // Catch:{ Throwable -> 0x0388 }
            r2.a = r4     // Catch:{ Throwable -> 0x0388 }
            r4 = 0
            boolean r4 = a(r8, r4)     // Catch:{ Throwable -> 0x0388 }
            r2.b = r4     // Catch:{ Throwable -> 0x0388 }
            r4 = 0
            boolean r1 = a(r1, r4)     // Catch:{ Throwable -> 0x0388 }
            r2.c = r1     // Catch:{ Throwable -> 0x0388 }
        L_0x02b7:
            r0.G = r2     // Catch:{ Throwable -> 0x0175 }
        L_0x02b9:
            java.lang.String r1 = "151"
            boolean r1 = com.loc.w.a(r3, r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x02e0
            java.lang.String r1 = "151"
            org.json.JSONObject r1 = r3.getJSONObject(r1)     // Catch:{ Throwable -> 0x0175 }
            com.loc.m$a$f r2 = new com.loc.m$a$f     // Catch:{ Throwable -> 0x0175 }
            r2.<init>()     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x02de
            java.lang.String r4 = "able"
            java.lang.String r1 = r1.optString(r4)     // Catch:{ Throwable -> 0x0175 }
            r4 = 0
            boolean r1 = a(r1, r4)     // Catch:{ Throwable -> 0x0175 }
            r2.a = r1     // Catch:{ Throwable -> 0x0175 }
        L_0x02de:
            r0.z = r2     // Catch:{ Throwable -> 0x0175 }
        L_0x02e0:
            a(r0, r3)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r1 = "14N"
            boolean r1 = com.loc.w.a(r3, r1)     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x033a
            java.lang.String r1 = "14N"
            org.json.JSONObject r1 = r3.getJSONObject(r1)     // Catch:{ Throwable -> 0x0175 }
            com.loc.m$a$b r2 = new com.loc.m$a$b     // Catch:{ Throwable -> 0x0175 }
            r2.<init>()     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r4 = "able"
            java.lang.String r4 = r1.optString(r4)     // Catch:{ Throwable -> 0x0175 }
            r5 = 0
            boolean r4 = a(r4, r5)     // Catch:{ Throwable -> 0x0175 }
            r2.a = r4     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r4 = "url"
            java.lang.String r4 = r1.optString(r4)     // Catch:{ Throwable -> 0x0175 }
            r2.b = r4     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r4 = "md5"
            java.lang.String r1 = r1.optString(r4)     // Catch:{ Throwable -> 0x0175 }
            r2.c = r1     // Catch:{ Throwable -> 0x0175 }
            boolean r1 = r2.a     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x0394
            com.loc.v r1 = com.loc.ae.a()     // Catch:{ Throwable -> 0x0175 }
            if (r1 == 0) goto L_0x033a
            com.loc.av r4 = new com.loc.av     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r5 = r2.b     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r2 = r2.c     // Catch:{ Throwable -> 0x0175 }
            boolean r6 = r0.H     // Catch:{ Throwable -> 0x0175 }
            r4.<init>(r5, r2, r6)     // Catch:{ Throwable -> 0x0175 }
            r4.a(r14)     // Catch:{ Throwable -> 0x0175 }
            com.loc.au r2 = new com.loc.au     // Catch:{ Throwable -> 0x0175 }
            r2.<init>(r11, r4, r1)     // Catch:{ Throwable -> 0x0175 }
            r2.a()     // Catch:{ Throwable -> 0x0175 }
        L_0x033a:
            java.lang.String r1 = "15K"
            org.json.JSONObject r1 = r3.getJSONObject(r1)     // Catch:{ Throwable -> 0x0362 }
            java.lang.String r2 = "isTargetAble"
            java.lang.String r2 = r1.optString(r2)     // Catch:{ Throwable -> 0x0362 }
            r3 = 0
            boolean r2 = a(r2, r3)     // Catch:{ Throwable -> 0x0362 }
            java.lang.String r3 = "able"
            java.lang.String r1 = r1.optString(r3)     // Catch:{ Throwable -> 0x0362 }
            r3 = 0
            boolean r1 = a(r1, r3)     // Catch:{ Throwable -> 0x0362 }
            if (r1 != 0) goto L_0x039b
            com.loc.r r1 = com.loc.r.a.a     // Catch:{ Throwable -> 0x0362 }
            r1.b(r11)     // Catch:{ Throwable -> 0x0362 }
            goto L_0x007f
        L_0x0362:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ Throwable -> 0x0175 }
            goto L_0x007f
        L_0x0368:
            r1 = move-exception
            java.lang.String r2 = "AuthConfigManager"
            java.lang.String r4 = "loadException"
            com.loc.ag.a(r1, r2, r4)     // Catch:{ Throwable -> 0x0175 }
            goto L_0x01e6
        L_0x0374:
            r2.a = r5     // Catch:{ Throwable -> 0x037c }
            r2.b = r4     // Catch:{ Throwable -> 0x037c }
            r2.c = r1     // Catch:{ Throwable -> 0x037c }
            goto L_0x0224
        L_0x037c:
            r1 = move-exception
            java.lang.String r4 = "at"
            java.lang.String r5 = "psu"
            com.loc.ag.a(r1, r4, r5)     // Catch:{ Throwable -> 0x0175 }
            goto L_0x0224
        L_0x0388:
            r1 = move-exception
            java.lang.String r4 = "at"
            java.lang.String r5 = "pes"
            com.loc.ag.a(r1, r4, r5)     // Catch:{ Throwable -> 0x0175 }
            goto L_0x02b7
        L_0x0394:
            java.lang.String r1 = "aiu"
            com.loc.ba.a(r11, r1)     // Catch:{ Throwable -> 0x0175 }
            goto L_0x033a
        L_0x039b:
            com.loc.r r1 = com.loc.r.a.a     // Catch:{ Throwable -> 0x0362 }
            r1.a(r11, r2)     // Catch:{ Throwable -> 0x0362 }
            goto L_0x007f
        L_0x03a2:
            r1 = move-exception
            r4 = r3
            goto L_0x00a7
        L_0x03a6:
            r1 = move-exception
            goto L_0x00a7
        L_0x03a9:
            r1 = move-exception
            r4 = r3
            goto L_0x00a1
        L_0x03ad:
            r1 = move-exception
            goto L_0x00a1
        L_0x03b0:
            r1 = move-exception
            r4 = r3
            goto L_0x0085
        L_0x03b4:
            r1 = move-exception
            goto L_0x0085
        L_0x03b7:
            r4 = r3
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.m.a(android.content.Context, com.loc.v, java.lang.String, boolean):com.loc.m$a");
    }

    private static String a(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject == null) {
            return "";
        }
        String str2 = "";
        return (!jSONObject.has(str) || jSONObject.getString(str).equals("[]")) ? str2 : jSONObject.optString(str);
    }

    public static void a(Context context, String str) {
        l.a(context, str);
    }

    private static void a(a aVar, JSONObject jSONObject) {
        try {
            if (w.a(jSONObject, "11B")) {
                aVar.h = jSONObject.getJSONObject("11B");
            }
            if (w.a(jSONObject, "11C")) {
                aVar.k = jSONObject.getJSONObject("11C");
            }
            if (w.a(jSONObject, "11I")) {
                aVar.l = jSONObject.getJSONObject("11I");
            }
            if (w.a(jSONObject, "11H")) {
                aVar.m = jSONObject.getJSONObject("11H");
            }
            if (w.a(jSONObject, "11E")) {
                aVar.n = jSONObject.getJSONObject("11E");
            }
            if (w.a(jSONObject, "11F")) {
                aVar.o = jSONObject.getJSONObject("11F");
            }
            if (w.a(jSONObject, "13A")) {
                aVar.q = jSONObject.getJSONObject("13A");
            }
            if (w.a(jSONObject, "13J")) {
                aVar.i = jSONObject.getJSONObject("13J");
            }
            if (w.a(jSONObject, "11G")) {
                aVar.p = jSONObject.getJSONObject("11G");
            }
            if (w.a(jSONObject, "006")) {
                aVar.r = jSONObject.getJSONObject("006");
            }
            if (w.a(jSONObject, "010")) {
                aVar.s = jSONObject.getJSONObject("010");
            }
            if (w.a(jSONObject, "11Z")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("11Z");
                b bVar = new b();
                a(jSONObject2, bVar);
                aVar.C = bVar;
            }
            if (w.a(jSONObject, "135")) {
                aVar.j = jSONObject.getJSONObject("135");
            }
            if (w.a(jSONObject, "13S")) {
                aVar.g = jSONObject.getJSONObject("13S");
            }
            if (w.a(jSONObject, "121")) {
                JSONObject jSONObject3 = jSONObject.getJSONObject("121");
                b bVar2 = new b();
                a(jSONObject3, bVar2);
                aVar.D = bVar2;
            }
            if (w.a(jSONObject, "122")) {
                JSONObject jSONObject4 = jSONObject.getJSONObject("122");
                b bVar3 = new b();
                a(jSONObject4, bVar3);
                aVar.E = bVar3;
            }
            if (w.a(jSONObject, "123")) {
                JSONObject jSONObject5 = jSONObject.getJSONObject("123");
                b bVar4 = new b();
                a(jSONObject5, bVar4);
                aVar.F = bVar4;
            }
            if (w.a(jSONObject, "011")) {
                aVar.c = jSONObject.getJSONObject("011");
            }
            if (w.a(jSONObject, "012")) {
                aVar.d = jSONObject.getJSONObject("012");
            }
            if (w.a(jSONObject, "013")) {
                aVar.e = jSONObject.getJSONObject("013");
            }
            if (w.a(jSONObject, "014")) {
                aVar.f = jSONObject.getJSONObject("014");
            }
            if (w.a(jSONObject, "145")) {
                aVar.t = jSONObject.getJSONObject("145");
            }
            if (w.a(jSONObject, "14B")) {
                aVar.u = jSONObject.getJSONObject("14B");
            }
            if (w.a(jSONObject, "14D")) {
                aVar.v = jSONObject.getJSONObject("14D");
            }
        } catch (Throwable th) {
            aj.b(th, "at", "pe");
        }
    }

    private static void a(JSONObject jSONObject, b bVar) {
        if (bVar != null) {
            try {
                String a2 = a(jSONObject, "m");
                String a3 = a(jSONObject, "u");
                String a4 = a(jSONObject, "v");
                String a5 = a(jSONObject, "able");
                String a6 = a(jSONObject, "on");
                bVar.c = a2;
                bVar.b = a3;
                bVar.d = a4;
                bVar.a = a(a5, false);
                bVar.e = a(a6, true);
            } catch (Throwable th) {
                ag.a(th, "at", "pe");
            }
        }
    }

    private static void a(JSONObject jSONObject, c cVar) {
        if (jSONObject != null) {
            try {
                String a2 = a(jSONObject, "md5");
                String a3 = a(jSONObject, "url");
                cVar.b = a2;
                cVar.a = a3;
            } catch (Throwable th) {
                ag.a(th, "at", "psc");
            }
        }
    }

    public static boolean a(String str, boolean z) {
        try {
            if (TextUtils.isEmpty(str)) {
                return z;
            }
            String[] split = URLDecoder.decode(str).split("/");
            return split[split.length + -1].charAt(4) % 2 == 1;
        } catch (Throwable th) {
            return z;
        }
    }
}
