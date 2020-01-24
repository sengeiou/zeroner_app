package com.iwown.my_module.healthy.network;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class BBsFactory {
    private BBsService bbsService = ((BBsService) this.retrofit.create(BBsService.class));
    private Retrofit retrofit = new Builder().baseUrl("http://iwown.com:9090/").addConverterFactory(GsonConverterFactory.create()).build();

    public BBsService getBbsService() {
        return this.bbsService;
    }

    public void setBbsService(BBsService bbsService2) {
        this.bbsService = bbsService2;
    }
}
