package com.safframework.aop;

import android.os.Looper;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AsyncAspect {
    @Around("execution(!synthetic * *(..)) && onAsyncMethod()")
    public void doAsyncMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        asyncMethod(joinPoint);
    }

    @Pointcut("@within(com.safframework.aop.annotation.Async)||@annotation(com.safframework.aop.annotation.Async)")
    public void onAsyncMethod() {
    }

    private void asyncMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        Flowable.create(new FlowableOnSubscribe<Object>() {
            public void subscribe(FlowableEmitter<Object> flowableEmitter) throws Exception {
                Looper.prepare();
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    ThrowableExtension.printStackTrace(throwable);
                }
                Looper.loop();
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }
}
