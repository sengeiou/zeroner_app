package com.alibaba.android.arouter.thread;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DefaultPoolExecutor extends ThreadPoolExecutor {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int INIT_THREAD_COUNT = (CPU_COUNT + 1);
    private static final int MAX_THREAD_COUNT = INIT_THREAD_COUNT;
    private static final long SURPLUS_THREAD_LIFE = 30;
    private static DefaultPoolExecutor instance;

    public static DefaultPoolExecutor getInstance() {
        if (instance == null) {
            synchronized (DefaultPoolExecutor.class) {
                if (instance == null) {
                    instance = new DefaultPoolExecutor(INIT_THREAD_COUNT, MAX_THREAD_COUNT, SURPLUS_THREAD_LIFE, TimeUnit.SECONDS, new ArrayBlockingQueue(64), new DefaultThreadFactory());
                }
            }
        }
        return instance;
    }

    private DefaultPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, new RejectedExecutionHandler() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                ARouter.logger.error("ARouter::", "Task rejected, too many task!");
            }
        });
    }

    /* access modifiers changed from: protected */
    public void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && (r instanceof Future)) {
            try {
                ((Future) r).get();
            } catch (CancellationException e) {
                t = e;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            ARouter.logger.warning("ARouter::", "Running task appeared exception! Thread [" + Thread.currentThread().getName() + "], because [" + t.getMessage() + "]\n" + TextUtils.formatStackTrace(t.getStackTrace()));
        }
    }
}
