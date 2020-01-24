package com.iwown.healthy.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import com.iwown.lib_common.network.interceptor.LogInterceptor;
import com.iwown.lib_common.network.utils.BaseUtils;
import com.iwown.lib_common.network.utils.GsonTypeAdapterUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.File;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClient {
    public static final String AUTHSERVICE = "/authservice";
    public static final String BaseServer = "https://api2.iwown.com/";
    public static final String BaseServer_Login = "http://api1.iwown.com/";
    public static final String BaseServer_Test_Baidu = "http://www.baidu.com";
    public static final String OLDBOYSERVICE = "/oldboyservice";
    public static final String USERSERVICE = "/userservice";
    public static final String Venus = "/mars";
    public static final String Venus_OldBoy = "/mars";
    public static final String WAWASERVICE = "/wawaservice";
    @SuppressLint({"StaticFieldLeak"})
    private static Context context;
    private static Retrofit mRetrofit_base;
    private static Retrofit mRetrofit_rergister_login;
    private static OkHttpClient okHttpClient;

    private AppClient() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(@NonNull Context mContext) {
        context = mContext.getApplicationContext();
        BaseUtils.init(context);
    }

    private static void initOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new Builder().addInterceptor(new LogInterceptor()).cache(new Cache(new File(context.getCacheDir(), "responses"), (long) 31457280)).build();
        }
    }

    public static Retrofit retrofitData() {
        initOkHttpClient();
        if (mRetrofit_base == null) {
            mRetrofit_base = new Retrofit.Builder().baseUrl("https://api2.iwown.com/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create(GsonTypeAdapterUtils.gsonWithDate2StringGson())).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        }
        return mRetrofit_base;
    }

    public static Retrofit retrofitRegisterLogin() {
        initOkHttpClient();
        if (mRetrofit_rergister_login == null) {
            mRetrofit_rergister_login = new Retrofit.Builder().baseUrl("http://api1.iwown.com/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create(GsonTypeAdapterUtils.gsonWithDate2StringGson())).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        }
        return mRetrofit_rergister_login;
    }

    public static Retrofit retrofitTest() {
        initOkHttpClient();
        if (mRetrofit_rergister_login == null) {
            mRetrofit_rergister_login = new Retrofit.Builder().baseUrl(BaseServer_Test_Baidu).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(GsonTypeAdapterUtils.gsonWithDate2StringGson())).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        }
        return mRetrofit_rergister_login;
    }
}
