package com.iwown.my_module.healthy.presenter;

import android.content.Context;
import com.google.gson.Gson;
import com.iwown.data_link.base.RetCode;
import com.iwown.my_module.healthy.contract.BbsAccountContract.BbsAccountView;
import com.iwown.my_module.healthy.data.DiscuzUser;
import com.iwown.my_module.healthy.network.BBsFactory;
import com.iwown.my_module.healthy.network.BBsService;
import com.iwown.my_module.healthy.network.request.BBSAccount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class BbsAccountPresenter implements com.iwown.my_module.healthy.contract.BbsAccountContract.BbsAccountPresenter {
    /* access modifiers changed from: private */
    public BbsAccountView accountView;
    private BBsFactory bbsFactory;
    private Context mContext;

    public BbsAccountPresenter(BbsAccountView accountView2) {
        this.accountView = accountView2;
        initNetWork();
    }

    private void initNetWork() {
        this.bbsFactory = new BBsFactory();
    }

    public void loginBBs(BBSAccount account) {
        this.bbsFactory.getBbsService().postBBSLogin(account).enqueue(new Callback<RetCode>() {
            public void onResponse(Call<RetCode> call, Response<RetCode> response) {
                if (response != null && response.body() != null) {
                    BbsAccountPresenter.this.accountView.loginResult(((RetCode) response.body()).getRetCode());
                } else if (response != null) {
                    BbsAccountPresenter.this.accountView.loginResult(response.code());
                } else {
                    BbsAccountPresenter.this.accountView.loginResult(-2);
                }
            }

            public void onFailure(Call<RetCode> call, Throwable t) {
                BbsAccountPresenter.this.accountView.loginResult(-1);
            }
        });
    }

    public void bindBBs(final BBSAccount account) {
        ((BBsService) new Builder().baseUrl("https://api1.iwown.com/venus/").addConverterFactory(GsonConverterFactory.create()).build().create(BBsService.class)).postBBSAccount(account).enqueue(new Callback<RetCode>() {
            public void onResponse(Call<RetCode> call, Response<RetCode> response) {
                if (response != null && response.body() != null) {
                    BbsAccountPresenter.this.accountView.bindAccountResult(((RetCode) response.body()).getRetCode(), new Gson().toJson((Object) account));
                } else if (response != null) {
                    BbsAccountPresenter.this.accountView.bindAccountResult(response.code(), null);
                } else {
                    BbsAccountPresenter.this.accountView.bindAccountResult(-2, null);
                }
            }

            public void onFailure(Call<RetCode> call, Throwable t) {
                BbsAccountPresenter.this.accountView.bindAccountResult(-1, null);
            }
        });
    }

    public void registerBBs(DiscuzUser user) {
        this.bbsFactory.getBbsService().postBBSRegister(user).enqueue(new Callback<RetCode>() {
            public void onResponse(Call<RetCode> call, Response<RetCode> response) {
                if (response != null && response.body() != null) {
                    BbsAccountPresenter.this.accountView.registerResult(((RetCode) response.body()).getRetCode());
                } else if (response != null) {
                    BbsAccountPresenter.this.accountView.registerResult(response.code());
                } else {
                    BbsAccountPresenter.this.accountView.registerResult(-2);
                }
            }

            public void onFailure(Call<RetCode> call, Throwable t) {
                BbsAccountPresenter.this.accountView.registerResult(-1);
            }
        });
    }
}
