package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.open.TDialog;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.c;
import com.tencent.open.utils.h;
import com.tencent.open.utils.j;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class QzoneShare extends BaseApi {
    public static final String SHARE_TO_QQ_APP_NAME = "appName";
    public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
    public static final String SHARE_TO_QQ_EXT_INT = "cflag";
    public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
    public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
    public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
    public static final String SHARE_TO_QQ_SITE = "site";
    public static final String SHARE_TO_QQ_SUMMARY = "summary";
    public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
    public static final String SHARE_TO_QQ_TITLE = "title";
    public static final String SHARE_TO_QZONE_EXTMAP = "extMap";
    public static final String SHARE_TO_QZONE_KEY_TYPE = "req_type";
    public static final int SHARE_TO_QZONE_TYPE_APP = 6;
    public static final int SHARE_TO_QZONE_TYPE_IMAGE = 5;
    public static final int SHARE_TO_QZONE_TYPE_IMAGE_TEXT = 1;
    public static final int SHARE_TO_QZONE_TYPE_NO_TYPE = 0;
    private boolean c = true;
    private boolean d = false;
    private boolean e = false;
    private boolean f = false;
    public String mViaShareQzoneType = "";

    public QzoneShare(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void shareToQzone(final Activity activity, final Bundle bundle, final IUiListener iUiListener) {
        String str;
        String str2;
        f.c("openSDK_LOG.QzoneShare", "shareToQzone() -- start");
        if (bundle == null) {
            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_NULL_ERROR, null));
            f.e("openSDK_LOG.QzoneShare", "shareToQzone() params is null");
            d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_NULL_ERROR);
            return;
        }
        String string = bundle.getString("title");
        String string2 = bundle.getString("summary");
        String string3 = bundle.getString("targetUrl");
        ArrayList stringArrayList = bundle.getStringArrayList("imageUrl");
        String a = j.a((Context) activity);
        if (a == null) {
            a = bundle.getString("appName");
        } else if (a.length() > 20) {
            a = a.substring(0, 20) + "...";
        }
        int i = bundle.getInt("req_type");
        switch (i) {
            case 1:
                this.mViaShareQzoneType = "1";
                break;
            case 5:
                this.mViaShareQzoneType = "2";
                break;
            case 6:
                this.mViaShareQzoneType = "4";
                break;
            default:
                this.mViaShareQzoneType = "1";
                break;
        }
        switch (i) {
            case 1:
                this.c = true;
                this.d = false;
                this.e = true;
                this.f = false;
                String str3 = string3;
                str = string;
                str2 = str3;
                break;
            case 5:
                iUiListener.onError(new UiError(-5, Constants.MSG_SHARE_TYPE_ERROR, null));
                f.e("openSDK_LOG.QzoneShare", "shareToQzone() error--end请选择支持的分享类型");
                d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() 请选择支持的分享类型");
                return;
            case 6:
                if (!j.g(activity, "5.0.0")) {
                    String format = String.format("http://fusion.qq.com/cgi-bin/qzapps/unified_jump?appid=%1$s&from=%2$s&isOpenAppID=1", new Object[]{this.b.getAppId(), "mqq"});
                    bundle.putString("targetUrl", format);
                    String str4 = format;
                    str = string;
                    str2 = str4;
                    break;
                } else {
                    iUiListener.onError(new UiError(-15, Constants.MSG_PARAM_APPSHARE_TOO_LOW, null));
                    f.e("openSDK_LOG.QzoneShare", "-->shareToQzone, app share is not support below qq5.0.");
                    d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone, app share is not support below qq5.0.");
                    return;
                }
            default:
                if (!j.e(string) || !j.e(string2)) {
                    this.c = true;
                } else if (stringArrayList == null || stringArrayList.size() == 0) {
                    string = "来自" + a + "的分享";
                    this.c = true;
                } else {
                    this.c = false;
                }
                this.d = false;
                this.e = true;
                this.f = false;
                String str5 = string3;
                str = string;
                str2 = str5;
                break;
        }
        if (j.b() || !j.g(activity, "4.5.0")) {
            if (this.c) {
                if (TextUtils.isEmpty(str2)) {
                    iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_TARGETURL_NULL_ERROR, null));
                    f.e("openSDK_LOG.QzoneShare", "shareToQzone() targetUrl null error--end");
                    d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_TARGETURL_NULL_ERROR);
                    return;
                } else if (!j.g(str2)) {
                    iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_TARGETURL_ERROR, null));
                    f.e("openSDK_LOG.QzoneShare", "shareToQzone() targetUrl error--end");
                    d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_TARGETURL_ERROR);
                    return;
                }
            }
            if (this.d) {
                bundle.putString("title", "");
                bundle.putString("summary", "");
            } else if (!this.e || !j.e(str)) {
                if (!j.e(str) && str.length() > 200) {
                    bundle.putString("title", j.a(str, 200, (String) null, (String) null));
                }
                if (!j.e(string2) && string2.length() > 600) {
                    bundle.putString("summary", j.a(string2, (int) ServiceErrorCode.YOU_AND_ME_IS_FRIEND, (String) null, (String) null));
                }
            } else {
                iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_TITLE_NULL_ERROR, null));
                f.e("openSDK_LOG.QzoneShare", "shareToQzone() title is null--end");
                d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() title is null");
                return;
            }
            if (!TextUtils.isEmpty(a)) {
                bundle.putString("appName", a);
            }
            if (stringArrayList != null && (stringArrayList == null || stringArrayList.size() != 0)) {
                int i2 = 0;
                while (true) {
                    int i3 = i2;
                    if (i3 < stringArrayList.size()) {
                        String str6 = (String) stringArrayList.get(i3);
                        if (!j.g(str6) && !j.h(str6)) {
                            stringArrayList.remove(i3);
                            i3--;
                        }
                        i2 = i3 + 1;
                    } else if (stringArrayList.size() == 0) {
                        iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
                        f.e("openSDK_LOG.QzoneShare", "shareToQzone() MSG_PARAM_IMAGE_URL_FORMAT_ERROR--end");
                        d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() 非法的图片地址!");
                        return;
                    } else {
                        bundle.putStringArrayList("imageUrl", stringArrayList);
                    }
                }
            } else if (this.f) {
                iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_ERROR, null));
                f.e("openSDK_LOG.QzoneShare", "shareToQzone() imageUrl is null -- end");
                d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() imageUrl is null");
                return;
            }
            if (!j.g(activity, "4.6.0")) {
                f.c("openSDK_LOG.QzoneShare", "shareToQzone() qqver greater than 4.6.0");
                a.a((Context) activity, stringArrayList, (c) new c() {
                    public void a(int i, String str) {
                        iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
                    }

                    public void a(int i, ArrayList<String> arrayList) {
                        if (i == 0) {
                            bundle.putStringArrayList("imageUrl", arrayList);
                        }
                        QzoneShare.this.b(activity, bundle, iUiListener);
                    }
                });
            } else if (h.c(activity, "4.2.0") < 0 || h.c(activity, "4.6.0") >= 0) {
                f.d("openSDK_LOG.QzoneShare", "shareToQzone() qqver below 4.2.0, will show download dialog");
                new TDialog(activity, "", a(""), null, this.b).show();
            } else {
                f.d("openSDK_LOG.QzoneShare", "shareToQzone() qqver between 4.2.0 and 4.6.0, will use qqshare");
                QQShare qQShare = new QQShare(activity, this.b);
                if (stringArrayList != null && stringArrayList.size() > 0) {
                    String str7 = (String) stringArrayList.get(0);
                    if (i != 5 || j.h(str7)) {
                        bundle.putString("imageLocalUrl", str7);
                    } else {
                        iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_MUST_BE_LOCAL, null));
                        f.e("openSDK_LOG.QzoneShare", "shareToQzone()手Q版本过低，纯图分享不支持网路图片");
                        d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone()手Q版本过低，纯图分享不支持网路图片");
                        return;
                    }
                }
                if (!j.g(activity, "4.5.0")) {
                    bundle.putInt("cflag", 1);
                }
                qQShare.shareToQQ(activity, bundle, iUiListener);
            }
            f.c("openSDK_LOG.QzoneShare", "shareToQzone() --end");
            return;
        }
        iUiListener.onError(new UiError(-6, Constants.MSG_SHARE_NOSD_ERROR, null));
        f.e("openSDK_LOG.QzoneShare", "shareToQzone() sdcard is null--end");
        d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_NOSD_ERROR);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x013e  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0164  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x018a  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x01a9  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x01cf  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x01f5  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x021b  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0265  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x028b  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x032c  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0359  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x03bb  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x03f0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(android.app.Activity r20, android.os.Bundle r21, com.tencent.tauth.IUiListener r22) {
        /*
            r19 = this;
            java.lang.String r2 = "openSDK_LOG.QzoneShare"
            java.lang.String r3 = "doshareToQzone() --start"
            com.tencent.open.a.f.c(r2, r3)
            java.lang.StringBuffer r6 = new java.lang.StringBuffer
            java.lang.String r2 = "mqqapi://share/to_qzone?src_type=app&version=1&file_type=news"
            r6.<init>(r2)
            java.lang.String r2 = "imageUrl"
            r0 = r21
            java.util.ArrayList r7 = r0.getStringArrayList(r2)
            java.lang.String r2 = "title"
            r0 = r21
            java.lang.String r8 = r0.getString(r2)
            java.lang.String r2 = "summary"
            r0 = r21
            java.lang.String r9 = r0.getString(r2)
            java.lang.String r2 = "targetUrl"
            r0 = r21
            java.lang.String r10 = r0.getString(r2)
            java.lang.String r2 = "audio_url"
            r0 = r21
            java.lang.String r11 = r0.getString(r2)
            java.lang.String r2 = "req_type"
            r3 = 1
            r0 = r21
            int r12 = r0.getInt(r2, r3)
            java.lang.String r2 = "appName"
            r0 = r21
            java.lang.String r13 = r0.getString(r2)
            java.lang.String r2 = "cflag"
            r3 = 0
            r0 = r21
            int r14 = r0.getInt(r2, r3)
            java.lang.String r2 = "share_qq_ext_str"
            r0 = r21
            java.lang.String r15 = r0.getString(r2)
            java.lang.String r3 = ""
            java.lang.String r2 = "extMap"
            r0 = r21
            android.os.Bundle r4 = r0.getBundle(r2)     // Catch:{ Exception -> 0x0097 }
            if (r4 == 0) goto L_0x044a
            java.util.Set r5 = r4.keySet()     // Catch:{ Exception -> 0x0097 }
            org.json.JSONObject r16 = new org.json.JSONObject     // Catch:{ Exception -> 0x0097 }
            r16.<init>()     // Catch:{ Exception -> 0x0097 }
            java.util.Iterator r17 = r5.iterator()     // Catch:{ Exception -> 0x0097 }
        L_0x007f:
            boolean r2 = r17.hasNext()     // Catch:{ Exception -> 0x0097 }
            if (r2 == 0) goto L_0x0102
            java.lang.Object r2 = r17.next()     // Catch:{ Exception -> 0x0097 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x0097 }
            java.lang.Object r18 = r4.get(r2)     // Catch:{ Exception -> 0x0097 }
            r0 = r16
            r1 = r18
            r0.put(r2, r1)     // Catch:{ Exception -> 0x0097 }
            goto L_0x007f
        L_0x0097:
            r2 = move-exception
            java.lang.String r4 = "openSDK_LOG.QzoneShare"
            java.lang.String r5 = "ShareToQzone()  --error parse extmap"
            com.tencent.open.a.f.b(r4, r5, r2)
        L_0x00a1:
            r0 = r19
            com.tencent.connect.auth.QQToken r2 = r0.b
            java.lang.String r16 = r2.getAppId()
            r0 = r19
            com.tencent.connect.auth.QQToken r2 = r0.b
            java.lang.String r17 = r2.getOpenId()
            java.lang.String r2 = "openSDK_LOG.QzoneShare"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "openId:"
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r17
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r4 = r4.toString()
            com.tencent.open.a.f.a(r2, r4)
            if (r7 == 0) goto L_0x0138
            java.lang.StringBuffer r18 = new java.lang.StringBuffer
            r18.<init>()
            int r2 = r7.size()
            r4 = 9
            if (r2 <= r4) goto L_0x010e
            r2 = 9
            r4 = r2
        L_0x00df:
            r2 = 0
            r5 = r2
        L_0x00e1:
            if (r5 >= r4) goto L_0x0114
            java.lang.Object r2 = r7.get(r5)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r2 = java.net.URLEncoder.encode(r2)
            r0 = r18
            r0.append(r2)
            int r2 = r4 + -1
            if (r5 == r2) goto L_0x00fe
            java.lang.String r2 = ";"
            r0 = r18
            r0.append(r2)
        L_0x00fe:
            int r2 = r5 + 1
            r5 = r2
            goto L_0x00e1
        L_0x0102:
            int r2 = r5.size()     // Catch:{ Exception -> 0x0097 }
            if (r2 <= 0) goto L_0x044a
            java.lang.String r2 = r16.toString()     // Catch:{ Exception -> 0x0097 }
        L_0x010c:
            r3 = r2
            goto L_0x00a1
        L_0x010e:
            int r2 = r7.size()
            r4 = r2
            goto L_0x00df
        L_0x0114:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&image_url="
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r4 = r18.toString()
            byte[] r4 = com.tencent.open.utils.j.i(r4)
            r5 = 2
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x0138:
            boolean r2 = android.text.TextUtils.isEmpty(r8)
            if (r2 != 0) goto L_0x015e
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&title="
            java.lang.StringBuilder r2 = r2.append(r4)
            byte[] r4 = com.tencent.open.utils.j.i(r8)
            r5 = 2
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x015e:
            boolean r2 = android.text.TextUtils.isEmpty(r9)
            if (r2 != 0) goto L_0x0184
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&description="
            java.lang.StringBuilder r2 = r2.append(r4)
            byte[] r4 = com.tencent.open.utils.j.i(r9)
            r5 = 2
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x0184:
            boolean r2 = android.text.TextUtils.isEmpty(r16)
            if (r2 != 0) goto L_0x01a3
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&share_id="
            java.lang.StringBuilder r2 = r2.append(r4)
            r0 = r16
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x01a3:
            boolean r2 = android.text.TextUtils.isEmpty(r10)
            if (r2 != 0) goto L_0x01c9
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&url="
            java.lang.StringBuilder r2 = r2.append(r4)
            byte[] r4 = com.tencent.open.utils.j.i(r10)
            r5 = 2
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x01c9:
            boolean r2 = android.text.TextUtils.isEmpty(r13)
            if (r2 != 0) goto L_0x01ef
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&app_name="
            java.lang.StringBuilder r2 = r2.append(r4)
            byte[] r4 = com.tencent.open.utils.j.i(r13)
            r5 = 2
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x01ef:
            boolean r2 = com.tencent.open.utils.j.e(r17)
            if (r2 != 0) goto L_0x0215
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&open_id="
            java.lang.StringBuilder r2 = r2.append(r4)
            byte[] r4 = com.tencent.open.utils.j.i(r17)
            r5 = 2
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x0215:
            boolean r2 = com.tencent.open.utils.j.e(r11)
            if (r2 != 0) goto L_0x023b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&audioUrl="
            java.lang.StringBuilder r2 = r2.append(r4)
            byte[] r4 = com.tencent.open.utils.j.i(r11)
            r5 = 2
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x023b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&req_type="
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r4 = java.lang.String.valueOf(r12)
            byte[] r4 = com.tencent.open.utils.j.i(r4)
            r5 = 2
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
            boolean r2 = com.tencent.open.utils.j.e(r15)
            if (r2 != 0) goto L_0x0285
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&share_qq_ext_str="
            java.lang.StringBuilder r2 = r2.append(r4)
            byte[] r4 = com.tencent.open.utils.j.i(r15)
            r5 = 2
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x0285:
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x02ab
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&share_qzone_ext_str="
            java.lang.StringBuilder r2 = r2.append(r4)
            byte[] r3 = com.tencent.open.utils.j.i(r3)
            r4 = 2
            java.lang.String r3 = android.util.Base64.encodeToString(r3, r4)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x02ab:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "&cflag="
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = java.lang.String.valueOf(r14)
            byte[] r3 = com.tencent.open.utils.j.i(r3)
            r4 = 2
            java.lang.String r3 = android.util.Base64.encodeToString(r3, r4)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
            java.lang.String r2 = "openSDK_LOG.QzoneShare"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "doshareToQzone, url: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r6.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.tencent.open.a.f.a(r2, r3)
            android.content.Context r2 = com.tencent.open.utils.e.a()
            r0 = r19
            com.tencent.connect.auth.QQToken r3 = r0.b
            java.lang.String r4 = "requireApi"
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]
            r7 = 0
            java.lang.String r8 = "shareToNativeQQ"
            r5[r7] = r8
            com.tencent.connect.a.a.a(r2, r3, r4, r5)
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r3 = "android.intent.action.VIEW"
            r2.<init>(r3)
            java.lang.String r3 = r6.toString()
            android.net.Uri r3 = android.net.Uri.parse(r3)
            r2.setData(r3)
            java.lang.String r3 = "pkg_name"
            java.lang.String r4 = r20.getPackageName()
            r2.putExtra(r3, r4)
            java.lang.String r3 = "4.6.0"
            r0 = r20
            boolean r3 = com.tencent.open.utils.j.g(r0, r3)
            if (r3 == 0) goto L_0x03bb
            r0 = r19
            boolean r3 = r0.a(r2)
            if (r3 == 0) goto L_0x0348
            com.tencent.connect.common.UIListenerManager r3 = com.tencent.connect.common.UIListenerManager.getInstance()
            r4 = 11104(0x2b60, float:1.556E-41)
            r0 = r22
            r3.setListenerWithRequestcode(r4, r0)
            r3 = 11104(0x2b60, float:1.556E-41)
            r0 = r19
            r1 = r20
            r0.a(r1, r2, r3)
        L_0x0348:
            java.lang.String r3 = "openSDK_LOG.QzoneShare"
            java.lang.String r4 = "doShareToQzone() -- QQ Version is < 4.6.0"
            com.tencent.open.a.f.c(r3, r4)
        L_0x0351:
            r0 = r19
            boolean r2 = r0.a(r2)
            if (r2 == 0) goto L_0x03f0
            com.tencent.open.b.d r2 = com.tencent.open.b.d.a()
            r0 = r19
            com.tencent.connect.auth.QQToken r3 = r0.b
            java.lang.String r3 = r3.getOpenId()
            r0 = r19
            com.tencent.connect.auth.QQToken r4 = r0.b
            java.lang.String r4 = r4.getAppId()
            java.lang.String r5 = "ANDROIDQQ.SHARETOQZ.XX"
            java.lang.String r6 = "11"
            java.lang.String r7 = "3"
            java.lang.String r8 = "0"
            r0 = r19
            java.lang.String r9 = r0.mViaShareQzoneType
            java.lang.String r10 = "0"
            java.lang.String r11 = "1"
            java.lang.String r12 = "0"
            r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            com.tencent.open.b.d r2 = com.tencent.open.b.d.a()
            r3 = 0
            java.lang.String r4 = "SHARE_CHECK_SDK"
            java.lang.String r5 = "1000"
            r0 = r19
            com.tencent.connect.auth.QQToken r6 = r0.b
            java.lang.String r6 = r6.getAppId()
            r7 = 4
            java.lang.String r7 = java.lang.String.valueOf(r7)
            long r8 = android.os.SystemClock.elapsedRealtime()
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r9 = 0
            r10 = 1
            java.lang.String r11 = ""
            r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11)
        L_0x03b1:
            java.lang.String r2 = "openSDK_LOG"
            java.lang.String r3 = "doShareToQzone() --end"
            com.tencent.open.a.f.c(r2, r3)
            return
        L_0x03bb:
            java.lang.String r3 = "openSDK_LOG.QzoneShare"
            java.lang.String r4 = "doShareToQzone() -- QQ Version is > 4.6.0"
            com.tencent.open.a.f.c(r3, r4)
            com.tencent.connect.common.UIListenerManager r3 = com.tencent.connect.common.UIListenerManager.getInstance()
            java.lang.String r4 = "shareToQzone"
            r0 = r22
            java.lang.Object r3 = r3.setListnerWithAction(r4, r0)
            if (r3 == 0) goto L_0x03dc
            java.lang.String r3 = "openSDK_LOG.QzoneShare"
            java.lang.String r4 = "doShareToQzone() -- do listener onCancel()"
            com.tencent.open.a.f.c(r3, r4)
        L_0x03dc:
            r0 = r19
            boolean r3 = r0.a(r2)
            if (r3 == 0) goto L_0x0351
            r3 = 10104(0x2778, float:1.4159E-41)
            r4 = 0
            r0 = r19
            r1 = r20
            r0.a(r1, r3, r2, r4)
            goto L_0x0351
        L_0x03f0:
            com.tencent.open.b.d r2 = com.tencent.open.b.d.a()
            r0 = r19
            com.tencent.connect.auth.QQToken r3 = r0.b
            java.lang.String r3 = r3.getOpenId()
            r0 = r19
            com.tencent.connect.auth.QQToken r4 = r0.b
            java.lang.String r4 = r4.getAppId()
            java.lang.String r5 = "ANDROIDQQ.SHARETOQZ.XX"
            java.lang.String r6 = "11"
            java.lang.String r7 = "3"
            java.lang.String r8 = "1"
            r0 = r19
            java.lang.String r9 = r0.mViaShareQzoneType
            java.lang.String r10 = "0"
            java.lang.String r11 = "1"
            java.lang.String r12 = "0"
            r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            com.tencent.open.b.d r2 = com.tencent.open.b.d.a()
            r3 = 1
            java.lang.String r4 = "SHARE_CHECK_SDK"
            java.lang.String r5 = "1000"
            r0 = r19
            com.tencent.connect.auth.QQToken r6 = r0.b
            java.lang.String r6 = r6.getAppId()
            r7 = 4
            java.lang.String r7 = java.lang.String.valueOf(r7)
            long r8 = android.os.SystemClock.elapsedRealtime()
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r9 = 0
            r10 = 1
            java.lang.String r11 = "hasActivityForIntent fail"
            r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            goto L_0x03b1
        L_0x044a:
            r2 = r3
            goto L_0x010c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.connect.share.QzoneShare.b(android.app.Activity, android.os.Bundle, com.tencent.tauth.IUiListener):void");
    }

    public void releaseResource() {
    }
}
