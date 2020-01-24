package com.iwown.my_module.healthy.presenter;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.user_pre.UserInfo;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.my_module.data.HealthGoalEntity;
import com.iwown.my_module.data.UserInfoEntity;
import com.iwown.my_module.healthy.HealthySharedUtil;
import com.iwown.my_module.healthy.HealthyUtil;
import com.iwown.my_module.healthy.contract.LoginContract.LoginView;
import com.iwown.my_module.healthy.network.LoginService;
import com.iwown.my_module.healthy.network.request.GoalSend;
import com.iwown.my_module.healthy.network.request.PhoneSend;
import com.iwown.my_module.healthy.network.request.QQWxSend;
import com.iwown.my_module.healthy.network.response.AllInfoData;
import com.iwown.my_module.healthy.network.response.AllUserInfoRetCode;
import com.iwown.my_module.healthy.network.response.HealthyGoalCode;
import com.iwown.my_module.healthy.network.response.LoginCode;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.utility.TextValidator;
import com.kunekt.healthy.wxapi.WxQqAPI;
import com.socks.library.KLog;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI.Token;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPresenter implements com.iwown.my_module.healthy.contract.LoginContract.LoginPresenter {
    private LoginService loginService;
    /* access modifiers changed from: private */
    public LoginView loginView;
    /* access modifiers changed from: private */
    public Context mContext;
    private Retrofit retrofit;

    private class BaseUiListener implements IUiListener {
        long myUid;

        public BaseUiListener(long uid) {
            this.myUid = uid;
            KLog.e("no2855 QQ用户信息uid: " + this.myUid);
        }

        public void onComplete(Object response) {
            if (response != null) {
                JSONObject jsonResponse = (JSONObject) response;
                try {
                    HealthyUtil.saveWxQqToTb(jsonResponse.toString(), 4, this.myUid);
                    KLog.e("no2855 QQ用户信息: " + jsonResponse.toString());
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
                if (jsonResponse == null || jsonResponse.length() != 0) {
                    doComplete((JSONObject) response);
                }
            }
        }

        /* access modifiers changed from: protected */
        public void doComplete(JSONObject values) {
        }

        public void onError(UiError e) {
        }

        public void onCancel() {
        }
    }

    public LoginPresenter(LoginView loginView2, Context context) {
        this.loginView = loginView2;
        this.mContext = context;
        initNetWork();
    }

    private void initNetWork() {
        this.retrofit = MyRetrofitClient.getAPIRetrofit();
        this.loginService = (LoginService) this.retrofit.create(LoginService.class);
    }

    public void getQqOrWxUid(final int type, final String token, final String openid) {
        Call<LoginCode> call = null;
        if (type == 3) {
            call = this.loginService.postLoginQQWx(Token.WX_TOKEN_PLATFORMID_VALUE, new QQWxSend(token, openid));
        } else if (type == 4) {
            call = this.loginService.postLoginQQWx("qq", new QQWxSend(token, openid));
        }
        if (call != null) {
            call.enqueue(new Callback<LoginCode>() {
                public void onResponse(Call<LoginCode> call, Response<LoginCode> response) {
                    if (response == null || response.body() == null) {
                        if (response != null) {
                            LoginPresenter.this.loginView.netError(1, response.code());
                        } else {
                            LoginPresenter.this.loginView.netError(1, -2);
                        }
                    } else if (((LoginCode) response.body()).getRetCode() == 0) {
                        long uid = ((LoginCode) response.body()).getUid();
                        KLog.d("no2855获取到的QQ微信: " + ((LoginCode) response.body()).getNewbie() + " -- " + ((LoginCode) response.body()).getUid() + " --  -- " + ((LoginCode) response.body()).getRegister_date());
                        HealthySharedUtil sharedUtil = new HealthySharedUtil(LoginPresenter.this.mContext);
                        sharedUtil.setUid(uid);
                        sharedUtil.setLoginType(type);
                        GlobalDataUpdater.setRegisterTime(LoginPresenter.this.mContext, DateUtil.dateY_M_D2Stamp(((LoginCode) response.body()).getRegister_date()));
                        if (type == 3) {
                            LoginPresenter.this.getWxUserInfo(token, openid, uid);
                        } else if (type == 4) {
                            LoginPresenter.this.getQQUserInfo(uid);
                        }
                        if (((LoginCode) response.body()).getNewbie() == 2) {
                            sharedUtil.setUserExist(true);
                            GlobalDataUpdater.setLoginStatus(LoginPresenter.this.mContext, 1);
                            LoginPresenter.this.loginView.loginOk(1);
                            return;
                        }
                        sharedUtil.setUserExist(false);
                        LoginPresenter.this.getUserInfo(((LoginCode) response.body()).getUid());
                    } else {
                        LoginPresenter.this.loginView.netError(1, ((LoginCode) response.body()).getRetCode());
                    }
                }

                public void onFailure(Call<LoginCode> call, Throwable t) {
                    LoginPresenter.this.loginView.netError(1, -1);
                }
            });
        } else {
            KLog.e("QQ微信登录type值传错");
        }
    }

    public void getPhoneUid(String userName, String password) {
        int type;
        if (TextValidator.isPhoneNumber(userName)) {
            type = 1;
        } else {
            type = 2;
        }
        final int loginType = type;
        this.loginService.postLogin(new PhoneSend(userName, type, password)).enqueue(new Callback<LoginCode>() {
            public void onResponse(Call<LoginCode> call, Response<LoginCode> response) {
                if (response == null || response.body() == null) {
                    if (response != null) {
                        LoginPresenter.this.loginView.netError(1, response.code());
                    } else {
                        LoginPresenter.this.loginView.netError(1, -2);
                    }
                } else if (((LoginCode) response.body()).getRetCode() == 0) {
                    KLog.d("no2855获取到的手机UID: " + ((LoginCode) response.body()).getNewbie() + " -- " + ((LoginCode) response.body()).getUid() + " -- " + ((LoginCode) response.body()).getRegister_date());
                    HealthySharedUtil sharedUtil = new HealthySharedUtil(LoginPresenter.this.mContext);
                    sharedUtil.setUid(((LoginCode) response.body()).getUid());
                    sharedUtil.setLoginType(loginType);
                    GlobalDataUpdater.setRegisterTime(LoginPresenter.this.mContext, DateUtil.dateY_M_D2Stamp(((LoginCode) response.body()).getRegister_date()));
                    if (((LoginCode) response.body()).getNewbie() == 2) {
                        sharedUtil.setUserExist(true);
                        LoginPresenter.this.loginView.loginOk(1);
                        GlobalDataUpdater.setLoginStatus(LoginPresenter.this.mContext, 1);
                        return;
                    }
                    sharedUtil.setUserExist(false);
                    LoginPresenter.this.getUserInfo(((LoginCode) response.body()).getUid());
                } else {
                    LoginPresenter.this.loginView.netError(1, ((LoginCode) response.body()).getRetCode());
                }
            }

            public void onFailure(Call<LoginCode> call, Throwable t) {
                LoginPresenter.this.loginView.netError(1, -1);
            }
        });
    }

    public void getUserInfo(final long uid) {
        this.loginService.getAllUserInfo(uid).enqueue(new Callback<AllUserInfoRetCode>() {
            public void onResponse(Call<AllUserInfoRetCode> call, Response<AllUserInfoRetCode> response) {
                if (response == null || response.body() == null) {
                    if (response != null) {
                        LoginPresenter.this.loginView.netError(1, response.code());
                    } else {
                        LoginPresenter.this.loginView.netError(1, -2);
                    }
                } else if (((AllUserInfoRetCode) response.body()).getRetCode() == 0) {
                    try {
                        if (((AllUserInfoRetCode) response.body()).getData() == null) {
                            KLog.e("no2855获取个人信息为空");
                            LoginPresenter.this.loginView.loginOk(1);
                            GlobalDataUpdater.setLoginStatus(LoginPresenter.this.mContext, 1);
                            return;
                        }
                        KLog.e("no2855获取个人信息不为空");
                        if (((AllUserInfoRetCode) response.body()).getData().getHealth() == null || ((AllUserInfoRetCode) response.body()).getData().getWeight() == null) {
                            KLog.e("no2855获取个人信息真的是空的");
                            LoginPresenter.this.loginView.loginOk(1);
                            GlobalDataUpdater.setLoginStatus(LoginPresenter.this.mContext, 1);
                            return;
                        }
                        KLog.e("no2855保存个人信心");
                        LoginPresenter.this.saveUserTB(((AllUserInfoRetCode) response.body()).getData(), uid);
                        GlobalDataUpdater.setLoginStatus(LoginPresenter.this.mContext, 3);
                        LoginPresenter.this.getGoalData(uid, 0);
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                        KLog.e("no2855获取个人信息有误，需要重新设置");
                        LoginPresenter.this.loginView.loginOk(1);
                        GlobalDataUpdater.setLoginStatus(LoginPresenter.this.mContext, 1);
                    }
                } else if (((AllUserInfoRetCode) response.body()).getRetCode() == 10404) {
                    LoginPresenter.this.loginView.loginOk(1);
                    GlobalDataUpdater.setLoginStatus(LoginPresenter.this.mContext, 1);
                } else {
                    LoginPresenter.this.loginView.netError(2, ((AllUserInfoRetCode) response.body()).getRetCode());
                }
            }

            public void onFailure(Call<AllUserInfoRetCode> call, Throwable t) {
                LoginPresenter.this.loginView.netError(2, -1);
            }
        });
    }

    /* access modifiers changed from: private */
    public void saveUserTB(AllInfoData infoData, long uid) {
        UserInfoEntity infoEntity = new UserInfoEntity();
        infoEntity.setUid(uid);
        if (infoData.getAccount() != null) {
            infoEntity.setNickname(infoData.getAccount().getNickname());
            infoEntity.setPortrait_url(infoData.getAccount().getPortrait_url());
        }
        if (infoData.getHealth() != null) {
            infoEntity.setGender(infoData.getHealth().getGender());
            infoEntity.setBirthday(infoData.getHealth().getBirthday());
            infoEntity.setHeight(infoData.getHealth().getHeight());
        }
        if (infoData.getWeight() != null) {
            infoEntity.setWeight(infoData.getWeight().getWeight());
        }
        infoEntity.saveOrUpdate("uid=?", uid + "");
    }

    /* access modifiers changed from: private */
    public void getGoalData(final long uid, final int sendType) {
        if (sendType >= 1) {
            this.loginView.loginOk(3);
        } else {
            this.loginService.getHealthyGoal(uid).enqueue(new Callback<HealthyGoalCode>() {
                public void onResponse(Call<HealthyGoalCode> call, Response<HealthyGoalCode> response) {
                    if (response == null || response.body() == null) {
                        LoginPresenter.this.getGoalData(uid, sendType + 1);
                    } else if (((HealthyGoalCode) response.body()).getRetCode() == 0) {
                        HealthGoalEntity mGoal = new HealthGoalEntity();
                        GoalSend goal_server = ((HealthyGoalCode) response.body()).getData();
                        if (goal_server == null) {
                            GlobalDataUpdater.setLoginStatus(LoginPresenter.this.mContext, 2);
                            LoginPresenter.this.loginView.loginOk(2);
                            return;
                        }
                        mGoal.setUid(goal_server.getUid());
                        mGoal.setTarget_step(goal_server.getTarget_step());
                        mGoal.setTarget_weight(goal_server.getTarget_weight());
                        mGoal.saveOrUpdate("uid=?", String.valueOf(goal_server.getUid()));
                        GlobalDataUpdater.setLoginStatus(LoginPresenter.this.mContext, 3);
                        try {
                            ModuleRouteDeviceInfoService.getInstance().updateTargetStep(mGoal.getTarget_step(), new UserInfo().getGoalCaloria());
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                        LoginPresenter.this.loginView.loginOk(3);
                    } else if (((HealthyGoalCode) response.body()).getRetCode() == 10404) {
                        GlobalDataUpdater.setLoginStatus(LoginPresenter.this.mContext, 2);
                        LoginPresenter.this.loginView.loginOk(2);
                    } else {
                        LoginPresenter.this.getGoalData(uid, sendType + 1);
                    }
                }

                public void onFailure(Call<HealthyGoalCode> call, Throwable t) {
                    LoginPresenter.this.getGoalData(uid, sendType + 1);
                }
            });
        }
    }

    public void getWxUserInfo(String access_token, String openid, final long uid) {
        Request request = new Builder().post(new FormBody.Builder().add(Constants.PARAM_ACCESS_TOKEN, access_token).add("openid", openid).build()).url("https://api.weixin.qq.com/sns/userinfo?").build();
        KLog.e("no2855微信消息:请求用户数据 ");
        new OkHttpClient().newBuilder().build().newCall(request).enqueue(new okhttp3.Callback() {
            public void onFailure(okhttp3.Call call, IOException e) {
                KLog.e("no2855 access_token: onFailure");
            }

            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String userInfo = response.body().string();
                KLog.e("no2855 usrer: " + userInfo);
                HealthyUtil.saveWxQqToTb(userInfo, 3, uid);
            }
        });
    }

    public void getQQUserInfo(long uid) {
        new com.tencent.connect.UserInfo(this.mContext, WxQqAPI.getTencent(this.mContext).getQQToken()).getUserInfo(new BaseUiListener(uid));
    }
}
