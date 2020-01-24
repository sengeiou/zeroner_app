package com.kunekt.healthy.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.data_link.base.RetCode;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.device.DeviceSetting;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.walk_29_data.ModuleRouteWalkService;
import com.iwown.data_link.walk_29_data.V3_walk;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.log.L;
import com.iwown.my_module.MyInitUtils;
import com.iwown.my_module.R;
import com.iwown.my_module.healthy.HealthySharedUtil;
import com.iwown.my_module.healthy.data.WxStep;
import com.iwown.my_module.healthy.event.LoginEvent;
import com.iwown.my_module.healthy.event.WxBindEvent;
import com.iwown.my_module.healthy.network.BindFactory;
import com.iwown.my_module.healthy.network.request.UploadSprotSteps;
import com.iwown.my_module.healthy.network.request.WxBindDeviceSend;
import com.iwown.my_module.healthy.network.request.WxBindSend;
import com.iwown.my_module.healthy.network.response.WxBindReturnCode;
import com.socks.library.KLog;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    /* access modifiers changed from: private */
    public Context mContext;
    private Handler mHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WxQqAPI.getIwxapi(this).handleIntent(getIntent(), this);
        this.mContext = this;
        setContentView(R.layout.my_module_wx_main);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WxQqAPI.getIwxapi(this).handleIntent(intent, this);
    }

    public void onReq(BaseReq baseReq) {
        KLog.e("no2855微信消息11: " + new Gson().toJson((Object) baseReq));
    }

    public void onResp(BaseResp baseResp) {
        try {
            KLog.e("no2855: baseResp.errCode " + baseResp.errCode);
            if (baseResp.errCode == 0) {
                HealthySharedUtil sharedUtil = new HealthySharedUtil(this);
                KLog.e("no2855: bindType " + sharedUtil.getWxLoginOrBind());
                if (sharedUtil.getWxLoginOrBind() == 0) {
                    finish();
                    return;
                }
                String msg = new Gson().toJson((Object) baseResp);
                KLog.e("no2855: baseResp.errCode " + msg);
                JSONObject jb = new JSONObject(msg);
                if (jb.has("code")) {
                    getAccess_token(jb.getString("code"));
                } else {
                    finish();
                }
            } else {
                finish();
            }
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private void getAccess_token(String code) {
        new OkHttpClient().newBuilder().build().newCall(new Builder().post(new FormBody.Builder().add("appid", "wx695ef7ad14cc332e").add("secret", "5d90948461cdcfc53f120b672bab9590").add("code", code).add("grant_type", "authorization_code").build()).url("https://api.weixin.qq.com/sns/oauth2/access_token?").build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                KLog.e("no2855 access_token: onFailure");
                HealthySharedUtil sharedUtil = new HealthySharedUtil(WXEntryActivity.this.mContext);
                if (sharedUtil.getWxLoginOrBind() == 2) {
                    EventBus.getDefault().post(new WxQqEvent(-1, WXEntryActivity.this.getString(R.string.wechat_auth_error)));
                } else {
                    EventBus.getDefault().post(new LoginEvent(3, null, null));
                }
                sharedUtil.setWxLoginOrBind(0);
                WXEntryActivity.this.finish();
            }

            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jb2 = new JSONObject(response.body().string());
                    String access_token = jb2.getString(Constants.PARAM_ACCESS_TOKEN);
                    String openId = jb2.getString("openid");
                    KLog.e("no2855 access_token: " + access_token + " --open: " + openId);
                    HealthySharedUtil sharedUtil = new HealthySharedUtil(WXEntryActivity.this.mContext);
                    sharedUtil.setWxOpenId(jb2.getString("openid"));
                    if (sharedUtil.getWxLoginOrBind() == 2) {
                        sharedUtil.setWxLoginOrBind(0);
                        if (TextUtils.isEmpty(access_token) || TextUtils.isEmpty(openId)) {
                            EventBus.getDefault().post(new WxQqEvent(-1, WXEntryActivity.this.getString(R.string.wechat_auth_error)));
                            WXEntryActivity.this.finish();
                        } else if (TextUtils.isEmpty(ModuleRouteDeviceInfoService.getInstance().getDevicemodel())) {
                            EventBus.getDefault().post(new WxQqEvent(-1, WXEntryActivity.this.getString(R.string.connect_wrist)));
                            WXEntryActivity.this.finish();
                        } else {
                            WXEntryActivity.this.bindWx(access_token, openId);
                        }
                    } else {
                        sharedUtil.setWxLoginOrBind(0);
                        EventBus.getDefault().post(new LoginEvent(3, jb2.getString(Constants.PARAM_ACCESS_TOKEN), jb2.getString("openid")));
                        WXEntryActivity.this.finish();
                    }
                } catch (JSONException e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        });
    }

    private void getWxUserInfo(String access_token, String openid) {
        Request request = new Builder().post(new FormBody.Builder().add(Constants.PARAM_ACCESS_TOKEN, access_token).add("openid", openid).build()).url("https://api.weixin.qq.com/sns/userinfo?").build();
        KLog.e("no2855微信消息:请求用户数据 ");
        new OkHttpClient().newBuilder().build().newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                KLog.e("no2855 access_token: onFailure");
            }

            public void onResponse(Call call, Response response) throws IOException {
                KLog.e("no2855 usrer: " + response.body().string());
                WXEntryActivity.this.finish();
            }
        });
    }

    private void saveWxInfoToTb(String userInfo) {
    }

    /* access modifiers changed from: private */
    public void bindWx(String token, String openId) {
        BindFactory bindFactory = new BindFactory();
        WxBindSend request = new WxBindSend();
        request.setOpenid(openId);
        request.setToken(token);
        request.setUid(GlobalUserDataFetcher.getCurrentUid(this.mContext).longValue());
        bindFactory.getBindService().postBindWx(request).enqueue(new retrofit2.Callback<RetCode>() {
            public void onResponse(retrofit2.Call<RetCode> call, retrofit2.Response<RetCode> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        try {
                            int code = ((RetCode) response.body()).getRetCode();
                            if (code == 0) {
                                WXEntryActivity.this.bindWxDevice();
                                return;
                            }
                            call.cancel();
                            EventBus.getDefault().post(new WxQqEvent(-1, "错误码+" + code + "微信接入失败"));
                            WXEntryActivity.this.finish();
                        } catch (NumberFormatException e) {
                            ThrowableExtension.printStackTrace(e);
                            EventBus.getDefault().post(new WxQqEvent(-1, "微信接入失败exception"));
                            WXEntryActivity.this.finish();
                        }
                    } else {
                        EventBus.getDefault().post(new WxQqEvent(-1, "错误码+" + response.code() + "微信接入失败"));
                        WXEntryActivity.this.finish();
                    }
                }
            }

            public void onFailure(retrofit2.Call<RetCode> call, Throwable t) {
                EventBus.getDefault().post(new WxQqEvent(-1, "微信接入失败 failure"));
                WXEntryActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: private */
    public void bindWxDevice() {
        BindFactory bindFactory = new BindFactory();
        WxBindDeviceSend request = new WxBindDeviceSend();
        String address = ModuleRouteDeviceInfoService.getInstance().getDeviceAddress();
        String model = ModuleRouteDeviceInfoService.getInstance().getDevicemodel();
        request.setMacaddr(address.replaceAll(":", ""));
        int toWxServer = 0;
        DeviceSetting setting = ModuleRouteDeviceInfoService.getInstance().getDeviceSetting(model);
        if (setting != null) {
            toWxServer = setting.getModel_wechat();
        }
        KLog.e("no2855微信: " + toWxServer + "  model: " + model + " ac: " + address);
        request.setDevice_model(toWxServer);
        bindFactory.getBindService().postBindWxDevice(request).enqueue(new retrofit2.Callback<WxBindReturnCode>() {
            public void onResponse(retrofit2.Call<WxBindReturnCode> call, retrofit2.Response<WxBindReturnCode> response) {
                if (response == null) {
                    WXEntryActivity.this.finish();
                    return;
                }
                if (response.isSuccessful()) {
                    try {
                        int code = ((WxBindReturnCode) response.body()).getRetCode();
                        if (code == 0) {
                            EventBus.getDefault().post(new WxBindEvent(((WxBindReturnCode) response.body()).getQrcode()));
                            WxQqUpload.uploadStepWX(MyInitUtils.getInstance().getMyApplication(), true, true);
                            KLog.d("no2855微信收到数据:这里走了啊啊啊啊啊 ");
                            WXEntryActivity.this.finish();
                        } else {
                            KLog.d("no2855绑定设备失败");
                            EventBus.getDefault().post(new WxQqEvent(-1, "错误码+" + code + "微信接入失败"));
                            call.cancel();
                            WXEntryActivity.this.finish();
                        }
                    } catch (NumberFormatException e) {
                    }
                } else {
                    EventBus.getDefault().post(new WxQqEvent(-1, "错误码+" + ((WxBindReturnCode) response.body()).getRetCode() + "微信接入失败"));
                }
                WXEntryActivity.this.finish();
            }

            public void onFailure(retrofit2.Call<WxBindReturnCode> call, Throwable t) {
                EventBus.getDefault().post(new WxQqEvent(-1, "微信接入失败failure"));
                WXEntryActivity.this.finish();
            }
        });
    }

    public void uploadStepsToService(Context mContext2, boolean mustUpload) {
        KLog.d("no2855微信开始同步步数");
        DateUtil dateUtil = new DateUtil();
        long uid = new HealthySharedUtil(mContext2).getUid();
        final String record_date = dateUtil.getSyyyyMMddDate();
        V3_walk tb_step = ModuleRouteWalkService.getInstance().getWalk(uid, record_date, ModuleRouteDeviceInfoService.getInstance().getDeviceInfo().mac);
        if (tb_step != null) {
            final int steps = tb_step.getStep();
            if (steps == 0) {
                KLog.e("微信上传步数 : 0");
                return;
            }
            HealthySharedUtil sharedUtil = new HealthySharedUtil(mContext2);
            UploadSprotSteps request = new UploadSprotSteps();
            request.setOpenid(sharedUtil.getWxOpenId());
            request.setStep(steps);
            request.setRecord_date(record_date);
            WxStep wxStep = (WxStep) new Gson().fromJson(sharedUtil.getWxStepMsg(), WxStep.class);
            if (mustUpload || wxStep == null || !TextUtils.equals(record_date, wxStep.getDate()) || wxStep.getStep() < steps) {
                new BindFactory().getBindService().postBindWxStep(request).enqueue(new retrofit2.Callback<RetCode>() {
                    public void onResponse(retrofit2.Call<RetCode> call, retrofit2.Response<RetCode> response) {
                        if (response == null || response.body() == null) {
                            L.file("no2855微信上传步数 : 失败" + (response != null ? response.errorBody().toString() : " null"), 3);
                            EventBus.getDefault().post(new WxQqEvent(-1, "微信上传步数 : 失败" + (response != null ? response.errorBody().toString() : " null")));
                        } else if (((RetCode) response.body()).getRetCode() == 0) {
                            WxStep wxStep1 = new WxStep();
                            wxStep1.setDate(record_date);
                            wxStep1.setStep(steps);
                            L.file("no2855微信上传步数 : 成功" + steps, 3);
                            EventBus.getDefault().post(new WxQqEvent(2, "微信上传步数 : 成功"));
                        } else {
                            L.file("微信上传步数 : 失败" + ((RetCode) response.body()).getRetCode(), 3);
                            EventBus.getDefault().post(new WxQqEvent(-1, "微信上传步数 : 失败" + ((RetCode) response.body()).getRetCode()));
                        }
                        WXEntryActivity.this.finish();
                    }

                    public void onFailure(retrofit2.Call<RetCode> call, Throwable t) {
                        KLog.e("no2855微信上传步数 : 失败     " + t.toString());
                        L.file("微信上传步数 : 失败     " + t.toString(), 3);
                        call.cancel();
                        EventBus.getDefault().post(new WxQqEvent(-1, "微信上传步数 : 失败     " + t.toString()));
                        WXEntryActivity.this.finish();
                    }
                });
            } else {
                KLog.e("no2855 微信上传步数没有变化");
            }
        }
    }
}
