package com.iwown.my_module.healthy.network;

import com.iwown.my_module.network.MyRetrofitClient;
import retrofit2.Retrofit;

public class BindFactory {
    private BindService bindService = ((BindService) this.retrofit.create(BindService.class));
    private Retrofit retrofit = MyRetrofitClient.getAliyunAPI3Retrofit();

    public BindService getBindService() {
        return this.bindService;
    }

    public void setBindService(BindService bindService2) {
        this.bindService = bindService2;
    }
}
