package com.alibaba.android.arouter.thread;

import android.support.annotation.NonNull;
import com.alibaba.android.arouter.launcher.ARouter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    public DefaultThreadFactory() {
        ThreadGroup threadGroup;
        SecurityManager s = System.getSecurityManager();
        if (s != null) {
            threadGroup = s.getThreadGroup();
        } else {
            threadGroup = Thread.currentThread().getThreadGroup();
        }
        this.group = threadGroup;
        this.namePrefix = "ARouter task pool No." + poolNumber.getAndIncrement() + ", thread No.";
    }

    public Thread newThread(@NonNull Runnable runnable) {
        String threadName = this.namePrefix + this.threadNumber.getAndIncrement();
        ARouter.logger.info("ARouter::", "Thread production, name is [" + threadName + "]");
        Thread thread = new Thread(this.group, runnable, threadName, 0);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }
        thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                ARouter.logger.info("ARouter::", "Running task appeared exception! Thread [" + thread.getName() + "], because [" + ex.getMessage() + "]");
            }
        });
        return thread;
    }
}
