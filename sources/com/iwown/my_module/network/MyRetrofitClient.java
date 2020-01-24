package com.iwown.my_module.network;

import com.iwown.data_link.consts.ApiConst;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.my_module.utility.HttpLogUtils;
import com.iwown.my_module.utility.HttpLogUtils.Level;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofitClient {
    public static Retrofit getAPIRetrofit() {
        OkHttpClient okHttpClient = getCommonHttpClient();
        String baseUrl = ApiConst.AMAZON_API_ADDRESS_PROD;
        if (AppConfigUtil.isHealthy()) {
            baseUrl = "https://api1.iwown.com/venus/";
        }
        return new Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
    }

    public static Retrofit getAliyunAPI3Retrofit() {
        return new Builder().baseUrl("https://api3.iwown.com/venus/").addConverterFactory(GsonConverterFactory.create()).client(getCommonHttpClient()).build();
    }

    private static OkHttpClient getCommonHttpClient() {
        HttpLogUtils httpLogUtils = new HttpLogUtils();
        httpLogUtils.setLevel(Level.CUSTOM);
        return new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).addInterceptor(httpLogUtils).build();
    }

    public static Retrofit getFeedbackAPIRetrofit() {
        OkHttpClient okHttpClient = getCommonHttpClient();
        String baseUrl = ApiConst.AMAZON_API_ADDRESS_PROD + "wawaservice/new/";
        if (AppConfigUtil.isHealthy()) {
            baseUrl = "https://api2.iwown.com/venus/ai_access/";
        }
        return new Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
    }

    public static Retrofit getImgVideoApiRetrofit() {
        OkHttpClient okHttpClient = getCommonHttpClient();
        String baseUrl = ApiConst.AMAZON_UP_VEDIO;
        if (AppConfigUtil.isHealthy()) {
            baseUrl = "http://api2.iwown.com:7790/";
        }
        return new Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
    }
}
