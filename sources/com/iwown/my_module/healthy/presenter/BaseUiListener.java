package com.iwown.my_module.healthy.presenter;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.my_module.healthy.HealthySharedUtil;
import com.kunekt.healthy.wxapi.WxQqUpload;
import com.socks.library.KLog;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseUiListener implements IUiListener {
    Context context;
    CallBack mCallBack;
    int type;

    interface CallBack {
        void doComplete(JSONObject jSONObject);
    }

    public BaseUiListener(Context context2) {
        this.context = context2;
    }

    public void setCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    public void removeCallBack() {
        this.mCallBack = null;
    }

    public void onComplete(Object response) {
        if (response != null) {
            JSONObject jsonResponse = (JSONObject) response;
            try {
                String openId = jsonResponse.getString("openid");
                String qqToken = jsonResponse.getString(Constants.PARAM_ACCESS_TOKEN);
                HealthySharedUtil sharedUtil = new HealthySharedUtil(this.context);
                sharedUtil.setQQOpenId(openId);
                sharedUtil.setQQToken(qqToken);
                KLog.e("no2855QQ准备开始同步数据: ");
                WxQqUpload.uploadStepQQ(this.context, true, true);
                WxQqUpload.responseSleepTime(this.context);
            } catch (JSONException e) {
                ThrowableExtension.printStackTrace(e);
            }
            if (jsonResponse == null || jsonResponse.length() != 0) {
                if (this.mCallBack != null) {
                    this.mCallBack.doComplete((JSONObject) response);
                }
                doComplete((JSONObject) response);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doComplete(JSONObject values) {
    }

    public void onError(UiError e) {
        KLog.e(e.errorMessage);
    }

    public void onCancel() {
        KLog.e("QQ 授权onCancel");
    }
}
