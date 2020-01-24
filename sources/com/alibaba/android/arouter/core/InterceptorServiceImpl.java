package com.alibaba.android.arouter.core;

import android.content.Context;
import com.alibaba.android.arouter.exception.HandlerException;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.service.InterceptorService;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.thread.CancelableCountDownLatch;
import com.alibaba.android.arouter.utils.MapUtils;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Route(path = "/arouter/service/interceptor")
public class InterceptorServiceImpl implements InterceptorService {
    /* access modifiers changed from: private */
    public static boolean interceptorHasInit;
    /* access modifiers changed from: private */
    public static final Object interceptorInitLock = new Object();

    public void doInterceptions(final Postcard postcard, final InterceptorCallback callback) {
        if (Warehouse.interceptors == null || Warehouse.interceptors.size() <= 0) {
            callback.onContinue(postcard);
            return;
        }
        checkInterceptorsInitStatus();
        if (!interceptorHasInit) {
            callback.onInterrupt(new HandlerException("Interceptors initialization takes too much time."));
        } else {
            LogisticsCenter.executor.execute(new Runnable() {
                public void run() {
                    CancelableCountDownLatch interceptorCounter = new CancelableCountDownLatch(Warehouse.interceptors.size());
                    try {
                        InterceptorServiceImpl._excute(0, interceptorCounter, postcard);
                        interceptorCounter.await((long) postcard.getTimeout(), TimeUnit.SECONDS);
                        if (interceptorCounter.getCount() > 0) {
                            callback.onInterrupt(new HandlerException("The interceptor processing timed out."));
                        } else if (postcard.getTag() != null) {
                            callback.onInterrupt(new HandlerException(postcard.getTag().toString()));
                        } else {
                            callback.onContinue(postcard);
                        }
                    } catch (Exception e) {
                        callback.onInterrupt(e);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void _excute(final int index, final CancelableCountDownLatch counter, final Postcard postcard) {
        if (index < Warehouse.interceptors.size()) {
            ((IInterceptor) Warehouse.interceptors.get(index)).process(postcard, new InterceptorCallback() {
                public void onContinue(Postcard postcard) {
                    counter.countDown();
                    InterceptorServiceImpl._excute(index + 1, counter, postcard);
                }

                public void onInterrupt(Throwable exception) {
                    postcard.setTag(exception == null ? new HandlerException("No message.") : exception.getMessage());
                    counter.cancel();
                }
            });
        }
    }

    public void init(final Context context) {
        LogisticsCenter.executor.execute(new Runnable() {
            public void run() {
                if (MapUtils.isNotEmpty(Warehouse.interceptorsIndex)) {
                    for (Entry<Integer, Class<? extends IInterceptor>> entry : Warehouse.interceptorsIndex.entrySet()) {
                        Class<? extends IInterceptor> interceptorClass = (Class) entry.getValue();
                        try {
                            IInterceptor iInterceptor = (IInterceptor) interceptorClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                            iInterceptor.init(context);
                            Warehouse.interceptors.add(iInterceptor);
                        } catch (Exception ex) {
                            throw new HandlerException("ARouter::ARouter init interceptor error! name = [" + interceptorClass.getName() + "], reason = [" + ex.getMessage() + "]");
                        }
                    }
                    InterceptorServiceImpl.interceptorHasInit = true;
                    ARouter.logger.info("ARouter::", "ARouter interceptors init over.");
                    synchronized (InterceptorServiceImpl.interceptorInitLock) {
                        InterceptorServiceImpl.interceptorInitLock.notifyAll();
                    }
                }
            }
        });
    }

    private static void checkInterceptorsInitStatus() {
        synchronized (interceptorInitLock) {
            while (!interceptorHasInit) {
                try {
                    interceptorInitLock.wait(10000);
                } catch (InterruptedException e) {
                    throw new HandlerException("ARouter::Interceptor init cost too much time error! reason = [" + e.getMessage() + "]");
                }
            }
        }
    }
}
