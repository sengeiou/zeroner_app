package com.iwown.device_module.device_firmware_upgrade.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorProxy {
    private int mCorePoolSize;
    ThreadPoolExecutor mExecutor;
    private long mKeepAliveTime;
    private int mMaximumPoolSize;

    public ThreadPoolExecutorProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.mMaximumPoolSize = maximumPoolSize;
        this.mCorePoolSize = corePoolSize;
        this.mKeepAliveTime = keepAliveTime;
    }

    private void initThreadPool() {
        if (this.mExecutor == null || this.mExecutor.isShutdown() || this.mExecutor.isTerminating()) {
            TimeUnit unit = TimeUnit.MILLISECONDS;
            LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
            ThreadFactory threadFactory = Executors.defaultThreadFactory();
            RejectedExecutionHandler handler = new DiscardPolicy();
            synchronized (ThreadPoolExecutorProxy.class) {
                if (this.mExecutor == null || this.mExecutor.isShutdown() || this.mExecutor.isTerminating()) {
                    this.mExecutor = new ThreadPoolExecutor(this.mCorePoolSize, this.mMaximumPoolSize, this.mKeepAliveTime, unit, workQueue, threadFactory, handler);
                }
            }
        }
    }

    public Future<?> submit(Runnable task) {
        initThreadPool();
        return this.mExecutor.submit(task);
    }

    public void execute(Runnable task) {
        initThreadPool();
        this.mExecutor.execute(task);
    }

    public void remove(Runnable task) {
        initThreadPool();
        this.mExecutor.remove(task);
    }
}
