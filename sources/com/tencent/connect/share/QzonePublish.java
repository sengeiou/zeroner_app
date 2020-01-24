package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.open.TDialog;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.j;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.File;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class QzonePublish extends BaseApi {
    public static final String HULIAN_CALL_BACK = "hulian_call_back";
    public static final String HULIAN_EXTRA_SCENE = "hulian_extra_scene";
    public static final String PUBLISH_TO_QZONE_APP_NAME = "appName";
    public static final String PUBLISH_TO_QZONE_EXTMAP = "extMap";
    public static final String PUBLISH_TO_QZONE_IMAGE_URL = "imageUrl";
    public static final String PUBLISH_TO_QZONE_KEY_TYPE = "req_type";
    public static final String PUBLISH_TO_QZONE_SUMMARY = "summary";
    public static final int PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD = 3;
    public static final int PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO = 4;
    public static final String PUBLISH_TO_QZONE_VIDEO_DURATION = "videoDuration";
    public static final String PUBLISH_TO_QZONE_VIDEO_PATH = "videoPath";
    public static final String PUBLISH_TO_QZONE_VIDEO_SIZE = "videoSize";

    public QzonePublish(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void publishToQzone(Activity activity, Bundle bundle, final IUiListener iUiListener) {
        int i = 0;
        f.c("openSDK_LOG.QzonePublish", "publishToQzone() -- start");
        if (bundle == null) {
            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_NULL_ERROR, null));
            f.e("openSDK_LOG.QzonePublish", "-->publishToQzone, params is null");
            d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_NULL_ERROR);
        } else if (!j.e((Context) activity)) {
            iUiListener.onError(new UiError(-15, Constants.MSG_PARAM_VERSION_TOO_LOW, null));
            f.e("openSDK_LOG.QzonePublish", "-->publishToQzone, this is not support below qq 5.9.5");
            d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "publicToQzone, this is not support below qq 5.9.5");
            new TDialog(activity, "", a(""), null, this.b).show();
        } else {
            String a = j.a((Context) activity);
            if (a == null) {
                a = bundle.getString("appName");
            } else if (a.length() > 20) {
                a = a.substring(0, 20) + "...";
            }
            if (!TextUtils.isEmpty(a)) {
                bundle.putString("appName", a);
            }
            int i2 = bundle.getInt("req_type");
            if (i2 == 3) {
                ArrayList stringArrayList = bundle.getStringArrayList("imageUrl");
                if (stringArrayList != null && stringArrayList.size() > 0) {
                    while (i < stringArrayList.size()) {
                        if (!j.h((String) stringArrayList.get(i))) {
                            stringArrayList.remove(i);
                            i--;
                        }
                        i++;
                    }
                    bundle.putStringArrayList("imageUrl", stringArrayList);
                }
                b(activity, bundle, iUiListener);
                f.c("openSDK_LOG.QzonePublish", "publishToQzone() --end");
            } else if (i2 == 4) {
                final String string = bundle.getString(PUBLISH_TO_QZONE_VIDEO_PATH);
                if (!j.h(string)) {
                    f.e("openSDK_LOG.QzonePublish", "publishToQzone() video url invalid");
                    iUiListener.onError(new UiError(-5, Constants.MSG_PUBLISH_VIDEO_ERROR, null));
                    return;
                }
                MediaPlayer mediaPlayer = new MediaPlayer();
                final Bundle bundle2 = bundle;
                final Activity activity2 = activity;
                final IUiListener iUiListener2 = iUiListener;
                mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        long length = new File(string).length();
                        int duration = mediaPlayer.getDuration();
                        bundle2.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, string);
                        bundle2.putInt(QzonePublish.PUBLISH_TO_QZONE_VIDEO_DURATION, duration);
                        bundle2.putLong(QzonePublish.PUBLISH_TO_QZONE_VIDEO_SIZE, length);
                        QzonePublish.this.b(activity2, bundle2, iUiListener2);
                        f.c("openSDK_LOG.QzonePublish", "publishToQzone() --end");
                    }
                });
                mediaPlayer.setOnErrorListener(new OnErrorListener() {
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                        f.e("openSDK_LOG.QzonePublish", "publishToQzone() mediaplayer onError()");
                        iUiListener.onError(new UiError(-5, Constants.MSG_PUBLISH_VIDEO_ERROR, null));
                        return false;
                    }
                });
                try {
                    mediaPlayer.setDataSource(string);
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    f.e("openSDK_LOG.QzonePublish", "publishToQzone() exception(s) occurred when preparing mediaplayer");
                    iUiListener.onError(new UiError(-5, Constants.MSG_PUBLISH_VIDEO_ERROR, null));
                }
            } else {
                iUiListener.onError(new UiError(-5, Constants.MSG_SHARE_TYPE_ERROR, null));
                f.e("openSDK_LOG.QzonePublish", "publishToQzone() error--end请选择支持的分享类型");
                d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "publishToQzone() 请选择支持的分享类型");
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x012b  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x019d  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x01e2  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0208  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x022e  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x02cc  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0335  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0103 A[EDGE_INSN: B:56:0x0103->B:27:0x0103 ?: BREAK  
    EDGE_INSN: B:56:0x0103->B:27:0x0103 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(android.app.Activity r20, android.os.Bundle r21, com.tencent.tauth.IUiListener r22) {
        /*
            r19 = this;
            java.lang.String r2 = "openSDK_LOG.QzonePublish"
            java.lang.String r3 = "doPublishToQzone() --start"
            com.tencent.open.a.f.c(r2, r3)
            java.lang.StringBuffer r6 = new java.lang.StringBuffer
            java.lang.String r2 = "mqqapi://qzone/publish?src_type=app&version=1&file_type=news"
            r6.<init>(r2)
            java.lang.String r2 = "imageUrl"
            r0 = r21
            java.util.ArrayList r7 = r0.getStringArrayList(r2)
            java.lang.String r2 = "summary"
            r0 = r21
            java.lang.String r8 = r0.getString(r2)
            java.lang.String r2 = "req_type"
            r3 = 3
            r0 = r21
            int r9 = r0.getInt(r2, r3)
            java.lang.String r2 = "appName"
            r0 = r21
            java.lang.String r10 = r0.getString(r2)
            java.lang.String r2 = "videoPath"
            r0 = r21
            java.lang.String r11 = r0.getString(r2)
            java.lang.String r2 = "videoDuration"
            r0 = r21
            int r12 = r0.getInt(r2)
            java.lang.String r2 = "videoSize"
            r0 = r21
            long r14 = r0.getLong(r2)
            java.lang.String r3 = ""
            java.lang.String r2 = "extMap"
            r0 = r21
            android.os.Bundle r4 = r0.getBundle(r2)     // Catch:{ Exception -> 0x008c }
            if (r4 == 0) goto L_0x0394
            java.util.Set r2 = r4.keySet()     // Catch:{ Exception -> 0x008c }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x008c }
            r5.<init>()     // Catch:{ Exception -> 0x008c }
            java.util.Iterator r13 = r2.iterator()     // Catch:{ Exception -> 0x008c }
        L_0x006c:
            boolean r2 = r13.hasNext()     // Catch:{ Exception -> 0x008c }
            if (r2 == 0) goto L_0x00f7
            java.lang.Object r2 = r13.next()     // Catch:{ Exception -> 0x008c }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x008c }
            java.lang.String r16 = r4.getString(r2)     // Catch:{ Exception -> 0x008c }
            boolean r16 = android.text.TextUtils.isEmpty(r16)     // Catch:{ Exception -> 0x008c }
            if (r16 != 0) goto L_0x006c
            java.lang.String r16 = r4.getString(r2)     // Catch:{ Exception -> 0x008c }
            r0 = r16
            r5.put(r2, r0)     // Catch:{ Exception -> 0x008c }
            goto L_0x006c
        L_0x008c:
            r2 = move-exception
            java.lang.String r4 = "openSDK_LOG.QzonePublish"
            java.lang.String r5 = "publishToQzone()  --error parse extmap"
            com.tencent.open.a.f.b(r4, r5, r2)
        L_0x0096:
            r0 = r19
            com.tencent.connect.auth.QQToken r2 = r0.b
            java.lang.String r16 = r2.getAppId()
            r0 = r19
            com.tencent.connect.auth.QQToken r2 = r0.b
            java.lang.String r17 = r2.getOpenId()
            java.lang.String r2 = "openSDK_LOG.QzonePublish"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "openId:"
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r17
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r4 = r4.toString()
            com.tencent.open.a.f.a(r2, r4)
            java.lang.String r2 = ""
            r4 = 3
            if (r4 != r9) goto L_0x0128
            if (r7 == 0) goto L_0x0128
            java.lang.String r4 = "7"
            java.lang.StringBuffer r13 = new java.lang.StringBuffer
            r13.<init>()
            int r18 = r7.size()
            r2 = 0
            r5 = r2
        L_0x00d8:
            r0 = r18
            if (r5 >= r0) goto L_0x0103
            java.lang.Object r2 = r7.get(r5)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r2 = java.net.URLEncoder.encode(r2)
            r13.append(r2)
            int r2 = r18 + -1
            if (r5 == r2) goto L_0x00f3
            java.lang.String r2 = ";"
            r13.append(r2)
        L_0x00f3:
            int r2 = r5 + 1
            r5 = r2
            goto L_0x00d8
        L_0x00f7:
            int r2 = r5.length()     // Catch:{ Exception -> 0x008c }
            if (r2 <= 0) goto L_0x0394
            java.lang.String r2 = r5.toString()     // Catch:{ Exception -> 0x008c }
        L_0x0101:
            r3 = r2
            goto L_0x0096
        L_0x0103:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "&image_url="
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r5 = r13.toString()
            byte[] r5 = com.tencent.open.utils.j.i(r5)
            r7 = 2
            java.lang.String r5 = android.util.Base64.encodeToString(r5, r7)
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
            r2 = r4
        L_0x0128:
            r4 = 4
            if (r4 != r9) goto L_0x0196
            java.lang.String r2 = "8"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "&videoPath="
            java.lang.StringBuilder r4 = r4.append(r5)
            byte[] r5 = com.tencent.open.utils.j.i(r11)
            r7 = 2
            java.lang.String r5 = android.util.Base64.encodeToString(r5, r7)
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r6.append(r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "&videoDuration="
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = java.lang.String.valueOf(r12)
            byte[] r5 = com.tencent.open.utils.j.i(r5)
            r7 = 2
            java.lang.String r5 = android.util.Base64.encodeToString(r5, r7)
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r6.append(r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "&videoSize="
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = java.lang.String.valueOf(r14)
            byte[] r5 = com.tencent.open.utils.j.i(r5)
            r7 = 2
            java.lang.String r5 = android.util.Base64.encodeToString(r5, r7)
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r6.append(r4)
        L_0x0196:
            r13 = r2
            boolean r2 = android.text.TextUtils.isEmpty(r8)
            if (r2 != 0) goto L_0x01bd
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&description="
            java.lang.StringBuilder r2 = r2.append(r4)
            byte[] r4 = com.tencent.open.utils.j.i(r8)
            r5 = 2
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x01bd:
            boolean r2 = android.text.TextUtils.isEmpty(r16)
            if (r2 != 0) goto L_0x01dc
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&share_id="
            java.lang.StringBuilder r2 = r2.append(r4)
            r0 = r16
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x01dc:
            boolean r2 = android.text.TextUtils.isEmpty(r10)
            if (r2 != 0) goto L_0x0202
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "&app_name="
            java.lang.StringBuilder r2 = r2.append(r4)
            byte[] r4 = com.tencent.open.utils.j.i(r10)
            r5 = 2
            java.lang.String r4 = android.util.Base64.encodeToString(r4, r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
        L_0x0202:
            boolean r2 = com.tencent.open.utils.j.e(r17)
            if (r2 != 0) goto L_0x0228
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
        L_0x0228:
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x024e
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
        L_0x024e:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "&req_type="
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = java.lang.String.valueOf(r9)
            byte[] r3 = com.tencent.open.utils.j.i(r3)
            r4 = 2
            java.lang.String r3 = android.util.Base64.encodeToString(r3, r4)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r6.append(r2)
            java.lang.String r2 = "openSDK_LOG.QzonePublish"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "doPublishToQzone, url: "
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
            r0 = r19
            boolean r3 = r0.a(r2)
            if (r3 == 0) goto L_0x0335
            r3 = 10104(0x2778, float:1.4159E-41)
            r4 = 0
            r0 = r19
            r1 = r20
            r0.a(r1, r3, r2, r4)
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
            java.lang.String r11 = "hasActivityForIntent success"
            r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11)
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
            java.lang.String r10 = "0"
            java.lang.String r11 = "1"
            java.lang.String r12 = "0"
            r9 = r13
            r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
        L_0x032b:
            java.lang.String r2 = "openSDK_LOG"
            java.lang.String r3 = "doPublishToQzone() --end"
            com.tencent.open.a.f.c(r2, r3)
            return
        L_0x0335:
            java.lang.String r2 = "openSDK_LOG.QzonePublish"
            java.lang.String r3 = "doPublishToQzone() target activity not found"
            com.tencent.open.a.f.e(r2, r3)
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
            java.lang.String r10 = "0"
            java.lang.String r11 = "1"
            java.lang.String r12 = "0"
            r9 = r13
            r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            goto L_0x032b
        L_0x0394:
            r2 = r3
            goto L_0x0101
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.connect.share.QzonePublish.b(android.app.Activity, android.os.Bundle, com.tencent.tauth.IUiListener):void");
    }
}
