package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Base64;
import com.iwown.sport_module.zxing.activity.CaptureActivity;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;
import com.tencent.mm.opensdk.utils.Log;
import org.json.JSONObject;

public final class d extends AsyncTask<Void, Void, a> {
    private static final String ad = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/tencent/MicroMsg/oauth_qrcode.png");
    private static String ae;
    private String af;
    private String ag;
    private OAuthListener ah;
    private f ai;
    private String appId;
    private String scope;
    private String signature;

    static class a {
        public OAuthErrCode aj;
        public String ak;
        public String al;
        public String am;
        public int an;
        public String ao;
        public byte[] ap;

        private a() {
        }

        public static a c(byte[] bArr) {
            a aVar = new a();
            if (bArr == null || bArr.length == 0) {
                Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, buf is null");
                aVar.aj = OAuthErrCode.WechatAuth_Err_NetworkErr;
            } else {
                try {
                    try {
                        JSONObject jSONObject = new JSONObject(new String(bArr, "utf-8"));
                        int i = jSONObject.getInt("errcode");
                        if (i != 0) {
                            Log.e("MicroMsg.SDK.GetQRCodeResult", String.format("resp errcode = %d", new Object[]{Integer.valueOf(i)}));
                            aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                            aVar.an = i;
                            aVar.ao = jSONObject.optString("errmsg");
                        } else {
                            String string = jSONObject.getJSONObject(CaptureActivity.Intent_Type_QrCodeMode).getString("qrcodebase64");
                            if (string == null || string.length() == 0) {
                                Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, qrcodeBase64 is null");
                                aVar.aj = OAuthErrCode.WechatAuth_Err_JsonDecodeErr;
                            } else {
                                byte[] decode = Base64.decode(string, 0);
                                if (decode == null || decode.length == 0) {
                                    Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, qrcodeBuf is null");
                                    aVar.aj = OAuthErrCode.WechatAuth_Err_JsonDecodeErr;
                                } else {
                                    aVar.aj = OAuthErrCode.WechatAuth_Err_OK;
                                    aVar.ap = decode;
                                    aVar.ak = jSONObject.getString("uuid");
                                    aVar.al = jSONObject.getString("appname");
                                    Log.d("MicroMsg.SDK.GetQRCodeResult", String.format("parse succ, save in memory, uuid = %s, appname = %s, imgBufLength = %d", new Object[]{aVar.ak, aVar.al, Integer.valueOf(aVar.ap.length)}));
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("MicroMsg.SDK.GetQRCodeResult", String.format("parse json fail, ex = %s", new Object[]{e.getMessage()}));
                        aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                    }
                } catch (Exception e2) {
                    Log.e("MicroMsg.SDK.GetQRCodeResult", String.format("parse fail, build String fail, ex = %s", new Object[]{e2.getMessage()}));
                    aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                }
            }
            return aVar;
        }
    }

    static {
        ae = null;
        ae = "https://open.weixin.qq.com/connect/sdk/qrconnect?appid=%s&noncestr=%s&timestamp=%s&scope=%s&signature=%s";
    }

    public d(String str, String str2, String str3, String str4, String str5, OAuthListener oAuthListener) {
        this.appId = str;
        this.scope = str2;
        this.af = str3;
        this.ag = str4;
        this.signature = str5;
        this.ah = oAuthListener;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object doInBackground(Object[] objArr) {
        Log.i("MicroMsg.SDK.GetQRCodeTask", "external storage available = false");
        String format = String.format(ae, new Object[]{this.appId, this.af, this.ag, this.scope, this.signature});
        long currentTimeMillis = System.currentTimeMillis();
        byte[] h = e.h(format);
        Log.d("MicroMsg.SDK.GetQRCodeTask", String.format("doInBackground, url = %s, time consumed = %d(ms)", new Object[]{format, Long.valueOf(System.currentTimeMillis() - currentTimeMillis)}));
        return a.c(h);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        a aVar = (a) obj;
        if (aVar.aj == OAuthErrCode.WechatAuth_Err_OK) {
            Log.d("MicroMsg.SDK.GetQRCodeTask", "onPostExecute, get qrcode success");
            this.ah.onAuthGotQrcode(aVar.am, aVar.ap);
            this.ai = new f(aVar.ak, this.ah);
            f fVar = this.ai;
            if (VERSION.SDK_INT >= 11) {
                fVar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            } else {
                fVar.execute(new Void[0]);
            }
        } else {
            Log.e("MicroMsg.SDK.GetQRCodeTask", String.format("onPostExecute, get qrcode fail, OAuthErrCode = %s", new Object[]{aVar.aj}));
            this.ah.onAuthFinish(aVar.aj, null);
        }
    }

    public final boolean q() {
        Log.i("MicroMsg.SDK.GetQRCodeTask", "cancelTask");
        return this.ai == null ? cancel(true) : this.ai.cancel(true);
    }
}
