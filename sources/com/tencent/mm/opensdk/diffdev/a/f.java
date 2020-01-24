package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;
import com.tencent.mm.opensdk.utils.Log;

final class f extends AsyncTask<Void, Void, a> {
    private OAuthListener ah;
    private String ak;
    private int aq;
    private String url;

    static class a {
        public OAuthErrCode aj;
        public String ar;
        public int as;

        a() {
        }

        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static com.tencent.mm.opensdk.diffdev.a.f.a d(byte[] r9) {
            /*
                r8 = 1
                r7 = 0
                com.tencent.mm.opensdk.diffdev.a.f$a r0 = new com.tencent.mm.opensdk.diffdev.a.f$a
                r0.<init>()
                if (r9 == 0) goto L_0x000c
                int r1 = r9.length
                if (r1 != 0) goto L_0x001a
            L_0x000c:
                java.lang.String r1 = "MicroMsg.SDK.NoopingResult"
                java.lang.String r2 = "parse fail, buf is null"
                com.tencent.mm.opensdk.utils.Log.e(r1, r2)
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_NetworkErr
                r0.aj = r1
            L_0x0019:
                return r0
            L_0x001a:
                java.lang.String r1 = new java.lang.String     // Catch:{ Exception -> 0x006e }
                java.lang.String r2 = "utf-8"
                r1.<init>(r9, r2)     // Catch:{ Exception -> 0x006e }
                org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0053 }
                r2.<init>(r1)     // Catch:{ Exception -> 0x0053 }
                java.lang.String r1 = "wx_errcode"
                int r1 = r2.getInt(r1)     // Catch:{ Exception -> 0x0053 }
                r0.as = r1     // Catch:{ Exception -> 0x0053 }
                java.lang.String r1 = "MicroMsg.SDK.NoopingResult"
                java.lang.String r3 = "nooping uuidStatusCode = %d"
                r4 = 1
                java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0053 }
                r5 = 0
                int r6 = r0.as     // Catch:{ Exception -> 0x0053 }
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x0053 }
                r4[r5] = r6     // Catch:{ Exception -> 0x0053 }
                java.lang.String r3 = java.lang.String.format(r3, r4)     // Catch:{ Exception -> 0x0053 }
                com.tencent.mm.opensdk.utils.Log.d(r1, r3)     // Catch:{ Exception -> 0x0053 }
                int r1 = r0.as     // Catch:{ Exception -> 0x0053 }
                switch(r1) {
                    case 402: goto L_0x00a3;
                    case 403: goto L_0x00a9;
                    case 404: goto L_0x0097;
                    case 405: goto L_0x0089;
                    case 408: goto L_0x009d;
                    case 500: goto L_0x00af;
                    default: goto L_0x004e;
                }     // Catch:{ Exception -> 0x0053 }
            L_0x004e:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_NormalErr     // Catch:{ Exception -> 0x0053 }
                r0.aj = r1     // Catch:{ Exception -> 0x0053 }
                goto L_0x0019
            L_0x0053:
                r1 = move-exception
                java.lang.String r2 = "MicroMsg.SDK.NoopingResult"
                java.lang.String r3 = "parse json fail, ex = %s"
                java.lang.Object[] r4 = new java.lang.Object[r8]
                java.lang.String r1 = r1.getMessage()
                r4[r7] = r1
                java.lang.String r1 = java.lang.String.format(r3, r4)
                com.tencent.mm.opensdk.utils.Log.e(r2, r1)
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_NormalErr
                r0.aj = r1
                goto L_0x0019
            L_0x006e:
                r1 = move-exception
                java.lang.String r2 = "MicroMsg.SDK.NoopingResult"
                java.lang.String r3 = "parse fail, build String fail, ex = %s"
                java.lang.Object[] r4 = new java.lang.Object[r8]
                java.lang.String r1 = r1.getMessage()
                r4[r7] = r1
                java.lang.String r1 = java.lang.String.format(r3, r4)
                com.tencent.mm.opensdk.utils.Log.e(r2, r1)
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_NormalErr
                r0.aj = r1
                goto L_0x0019
            L_0x0089:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_OK     // Catch:{ Exception -> 0x0053 }
                r0.aj = r1     // Catch:{ Exception -> 0x0053 }
                java.lang.String r1 = "wx_code"
                java.lang.String r1 = r2.getString(r1)     // Catch:{ Exception -> 0x0053 }
                r0.ar = r1     // Catch:{ Exception -> 0x0053 }
                goto L_0x0019
            L_0x0097:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_OK     // Catch:{ Exception -> 0x0053 }
                r0.aj = r1     // Catch:{ Exception -> 0x0053 }
                goto L_0x0019
            L_0x009d:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_OK     // Catch:{ Exception -> 0x0053 }
                r0.aj = r1     // Catch:{ Exception -> 0x0053 }
                goto L_0x0019
            L_0x00a3:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_Timeout     // Catch:{ Exception -> 0x0053 }
                r0.aj = r1     // Catch:{ Exception -> 0x0053 }
                goto L_0x0019
            L_0x00a9:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_Cancel     // Catch:{ Exception -> 0x0053 }
                r0.aj = r1     // Catch:{ Exception -> 0x0053 }
                goto L_0x0019
            L_0x00af:
                com.tencent.mm.opensdk.diffdev.OAuthErrCode r1 = com.tencent.mm.opensdk.diffdev.OAuthErrCode.WechatAuth_Err_NormalErr     // Catch:{ Exception -> 0x0053 }
                r0.aj = r1     // Catch:{ Exception -> 0x0053 }
                goto L_0x0019
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.mm.opensdk.diffdev.a.f.a.d(byte[]):com.tencent.mm.opensdk.diffdev.a.f$a");
        }
    }

    public f(String str, OAuthListener oAuthListener) {
        this.ak = str;
        this.ah = oAuthListener;
        this.url = String.format("https://long.open.weixin.qq.com/connect/l/qrconnect?f=json&uuid=%s", new Object[]{str});
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object doInBackground(Object[] objArr) {
        if (this.ak == null || this.ak.length() == 0) {
            Log.e("MicroMsg.SDK.NoopingTask", "run fail, uuid is null");
            a aVar = new a();
            aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
            return aVar;
        }
        while (!isCancelled()) {
            String str = this.url + (this.aq == 0 ? "" : "&last=" + this.aq);
            long currentTimeMillis = System.currentTimeMillis();
            byte[] h = e.h(str);
            long currentTimeMillis2 = System.currentTimeMillis();
            a d = a.d(h);
            Log.d("MicroMsg.SDK.NoopingTask", String.format("nooping, url = %s, errCode = %s, uuidStatusCode = %d, time consumed = %d(ms)", new Object[]{str, d.aj.toString(), Integer.valueOf(d.as), Long.valueOf(currentTimeMillis2 - currentTimeMillis)}));
            if (d.aj == OAuthErrCode.WechatAuth_Err_OK) {
                this.aq = d.as;
                if (d.as == g.UUID_SCANED.getCode()) {
                    this.ah.onQrcodeScanned();
                } else if (d.as != g.UUID_KEEP_CONNECT.getCode() && d.as == g.UUID_CONFIRM.getCode()) {
                    if (d.ar != null && d.ar.length() != 0) {
                        return d;
                    }
                    Log.e("MicroMsg.SDK.NoopingTask", "nooping fail, confirm with an empty code!!!");
                    d.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                    return d;
                }
            } else {
                Log.e("MicroMsg.SDK.NoopingTask", String.format("nooping fail, errCode = %s, uuidStatusCode = %d", new Object[]{d.aj.toString(), Integer.valueOf(d.as)}));
                return d;
            }
        }
        Log.i("MicroMsg.SDK.NoopingTask", "IDiffDevOAuth.stopAuth / detach invoked");
        a aVar2 = new a();
        aVar2.aj = OAuthErrCode.WechatAuth_Err_Auth_Stopped;
        return aVar2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        a aVar = (a) obj;
        this.ah.onAuthFinish(aVar.aj, aVar.ar);
    }
}
