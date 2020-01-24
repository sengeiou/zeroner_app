package com.iwown.my_module.healthy.network;

import com.iwown.my_module.network.MyRetrofitClient;
import retrofit2.Retrofit;

public class LoginFactory {
    private LoginService loginService = ((LoginService) this.retrofit.create(LoginService.class));
    private Retrofit retrofit = MyRetrofitClient.getAPIRetrofit();

    public LoginService getLoginService() {
        return this.loginService;
    }

    public void setLoginService(LoginService loginService2) {
        this.loginService = loginService2;
    }
}
