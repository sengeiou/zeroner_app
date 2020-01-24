package com.iwown.lib_common.network;

import com.socks.library.KLog;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

public class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {
    /* access modifiers changed from: private */
    public final int maxRetries;
    /* access modifiers changed from: private */
    public int retryCount;
    /* access modifiers changed from: private */
    public int retryDelayMillis = 2500;

    static /* synthetic */ int access$004(RetryWithDelay x0) {
        int i = x0.retryCount + 1;
        x0.retryCount = i;
        return i;
    }

    public RetryWithDelay(int maxRetries2, int retryDelayMillis2) {
        this.maxRetries = maxRetries2;
        this.retryDelayMillis = retryDelayMillis2;
    }

    public RetryWithDelay(int maxRetries2) {
        this.maxRetries = maxRetries2;
    }

    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                KLog.e("RetryWithDelay apply apply " + throwable.getMessage());
                if (RetryWithDelay.access$004(RetryWithDelay.this) > RetryWithDelay.this.maxRetries || (!(throwable instanceof ConnectException) && !(throwable instanceof SocketTimeoutException) && !(throwable instanceof SocketException))) {
                    return Observable.error(throwable);
                }
                KLog.e("get error, it will try after " + RetryWithDelay.this.retryDelayMillis + " millisecond, retry count " + RetryWithDelay.this.retryCount);
                return Observable.timer((long) RetryWithDelay.this.retryDelayMillis, TimeUnit.MILLISECONDS);
            }
        });
    }
}
