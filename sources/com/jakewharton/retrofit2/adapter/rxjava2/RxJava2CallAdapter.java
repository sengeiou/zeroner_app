package com.jakewharton.retrofit2.adapter.rxjava2;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;

final class RxJava2CallAdapter implements CallAdapter<Object> {
    private final boolean isBody;
    private final boolean isCompletable;
    private final boolean isFlowable;
    private final boolean isMaybe;
    private final boolean isResult;
    private final boolean isSingle;
    private final Type responseType;
    private final Scheduler scheduler;

    RxJava2CallAdapter(Type responseType2, Scheduler scheduler2, boolean isResult2, boolean isBody2, boolean isFlowable2, boolean isSingle2, boolean isMaybe2, boolean isCompletable2) {
        this.responseType = responseType2;
        this.scheduler = scheduler2;
        this.isResult = isResult2;
        this.isBody = isBody2;
        this.isFlowable = isFlowable2;
        this.isSingle = isSingle2;
        this.isMaybe = isMaybe2;
        this.isCompletable = isCompletable2;
    }

    public Type responseType() {
        return this.responseType;
    }

    public <R> Object adapt(Call<R> call) {
        Observable observable;
        Observable<Response<R>> responseObservable = new CallObservable<>(call);
        if (this.isResult) {
            observable = new ResultObservable(responseObservable);
        } else if (this.isBody) {
            observable = new BodyObservable(responseObservable);
        } else {
            observable = responseObservable;
        }
        if (this.scheduler != null) {
            observable = observable.subscribeOn(this.scheduler);
        }
        if (this.isFlowable) {
            return observable.toFlowable(BackpressureStrategy.LATEST);
        }
        if (this.isSingle) {
            return observable.singleOrError();
        }
        if (this.isMaybe) {
            return observable.singleElement();
        }
        if (this.isCompletable) {
            return observable.ignoreElements();
        }
        return observable;
    }
}
