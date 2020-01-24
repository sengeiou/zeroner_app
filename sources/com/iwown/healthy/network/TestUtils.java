package com.iwown.healthy.network;

import com.iwown.healthy.network.api.TestApi;
import com.iwown.lib_common.network.RetryWithDelay;
import com.socks.library.KLog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class TestUtils {
    public static void testBaiduNetWork() {
        ((TestApi) AppClient.retrofitTest().create(TestApi.class)).getBaidu().retryWhen(new RetryWithDelay(2)).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            public void accept(Disposable disposable) throws Exception {
                KLog.e("拦截 弹框之类的");
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe((Observer<? super T>) new CallbackWrapper<ResponseBody>() {
            /* access modifiers changed from: protected */
            public void onSuccess(ResponseBody responseBody) throws Exception {
                KLog.e(responseBody.string());
            }
        });
    }
}
