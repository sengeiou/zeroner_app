package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadFactoryBuilder {
    private ThreadFactory backingThreadFactory = null;
    private Boolean daemon = null;
    private String nameFormat = null;
    private Integer priority = null;
    private UncaughtExceptionHandler uncaughtExceptionHandler = null;

    public ThreadFactoryBuilder setNameFormat(String nameFormat2) {
        String.format(nameFormat2, new Object[]{Integer.valueOf(0)});
        this.nameFormat = nameFormat2;
        return this;
    }

    public ThreadFactoryBuilder setDaemon(boolean daemon2) {
        this.daemon = Boolean.valueOf(daemon2);
        return this;
    }

    public ThreadFactoryBuilder setPriority(int priority2) {
        boolean z;
        Preconditions.checkArgument(priority2 >= 1, "Thread priority (%s) must be >= %s", Integer.valueOf(priority2), Integer.valueOf(1));
        if (priority2 <= 10) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Thread priority (%s) must be <= %s", Integer.valueOf(priority2), Integer.valueOf(10));
        this.priority = Integer.valueOf(priority2);
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler2) {
        this.uncaughtExceptionHandler = (UncaughtExceptionHandler) Preconditions.checkNotNull(uncaughtExceptionHandler2);
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory backingThreadFactory2) {
        this.backingThreadFactory = (ThreadFactory) Preconditions.checkNotNull(backingThreadFactory2);
        return this;
    }

    public ThreadFactory build() {
        return build(this);
    }

    private static ThreadFactory build(ThreadFactoryBuilder builder) {
        final String nameFormat2 = builder.nameFormat;
        final Boolean daemon2 = builder.daemon;
        final Integer priority2 = builder.priority;
        final UncaughtExceptionHandler uncaughtExceptionHandler2 = builder.uncaughtExceptionHandler;
        final ThreadFactory backingThreadFactory2 = builder.backingThreadFactory != null ? builder.backingThreadFactory : Executors.defaultThreadFactory();
        final AtomicLong count = nameFormat2 != null ? new AtomicLong(0) : null;
        return new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                Thread thread = backingThreadFactory2.newThread(runnable);
                if (nameFormat2 != null) {
                    thread.setName(String.format(nameFormat2, new Object[]{Long.valueOf(count.getAndIncrement())}));
                }
                if (daemon2 != null) {
                    thread.setDaemon(daemon2.booleanValue());
                }
                if (priority2 != null) {
                    thread.setPriority(priority2.intValue());
                }
                if (uncaughtExceptionHandler2 != null) {
                    thread.setUncaughtExceptionHandler(uncaughtExceptionHandler2);
                }
                return thread;
            }
        };
    }
}
