package com.iwown.my_module.healthy.contract;

import com.iwown.data_link.base.RetCode;
import com.iwown.my_module.healthy.network.LoginService;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.utility.Constants;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CheckUserExist {
    private CheckListener checkListener;
    private LoginService loginService;
    private Retrofit retrofit;

    public interface CheckListener {
        void onCheckUserEnd(int i);

        void onSendEmail(int i);
    }

    public CheckUserExist() {
        initNetWork();
    }

    public CheckUserExist(CheckListener checkListener2) {
        this.checkListener = checkListener2;
        initNetWork();
    }

    private void initNetWork() {
        this.retrofit = MyRetrofitClient.getAPIRetrofit();
        this.loginService = (LoginService) this.retrofit.create(LoginService.class);
    }

    public void setCheckListener(CheckListener checkListener2) {
        this.checkListener = checkListener2;
    }

    /* access modifiers changed from: private */
    public void onEndListener(int type) {
        if (this.checkListener != null) {
            this.checkListener.onCheckUserEnd(type);
        }
    }

    /* access modifiers changed from: private */
    public void onSendEndListener(int type) {
        if (this.checkListener != null) {
            this.checkListener.onSendEmail(type);
        }
    }

    public void checkPhoneExist(String phone) {
        long phoneNum = Long.parseLong(phone);
        new HashMap<>().put(Constants.NEW_MAP_KEY, Long.valueOf(phoneNum));
        this.loginService.phoneExists(phoneNum).enqueue(new Callback<RetCode>() {
            public void onResponse(Call<RetCode> call, Response<RetCode> response) {
                if (response == null || response.body() == null) {
                    CheckUserExist.this.onEndListener(-2);
                } else {
                    CheckUserExist.this.onEndListener(((RetCode) response.body()).getRetCode());
                }
            }

            public void onFailure(Call<RetCode> call, Throwable t) {
                CheckUserExist.this.onEndListener(-1);
            }
        });
    }

    public void checkEmailExist(String email) {
        this.loginService.emailExists(email).enqueue(new Callback<RetCode>() {
            public void onResponse(Call<RetCode> call, Response<RetCode> response) {
                if (response == null || response.body() == null) {
                    CheckUserExist.this.onEndListener(-2);
                } else {
                    CheckUserExist.this.onEndListener(((RetCode) response.body()).getRetCode());
                }
            }

            public void onFailure(Call<RetCode> call, Throwable t) {
                CheckUserExist.this.onEndListener(-1);
            }
        });
    }

    public void sendUserEmail(String userEmail) {
        new HashMap<>().put("path", userEmail);
        this.loginService.postEmailFindPassword(userEmail).enqueue(new Callback<RetCode>() {
            public void onResponse(Call<RetCode> call, Response<RetCode> response) {
                if (response == null || response.body() == null) {
                    CheckUserExist.this.onSendEndListener(-2);
                } else {
                    CheckUserExist.this.onSendEndListener(((RetCode) response.body()).getRetCode());
                }
            }

            public void onFailure(Call<RetCode> call, Throwable t) {
                CheckUserExist.this.onSendEndListener(-1);
            }
        });
    }

    public void destoryClear() {
        this.loginService = null;
        this.retrofit = null;
    }
}
