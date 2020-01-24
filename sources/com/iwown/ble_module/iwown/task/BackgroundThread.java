package com.iwown.ble_module.iwown.task;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.socks.library.KLog;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BackgroundThread extends Thread {
    private static final String LOG_TAG = BackgroundThread.class.getSimpleName();
    private ZeronerBleWriteDataTask lastTask;
    private final Condition mCondition = this.mLock.newCondition();
    private Lock mLock = new ReentrantLock();
    private final ITaskManager mTaskManager;
    private Queue<ITask> queue;
    private long timeContinue;
    private long timeTask;

    public BackgroundThread(String threadName, Queue<ITask> queue2, ITaskManager manager) {
        super(threadName);
        this.queue = queue2;
        this.mTaskManager = manager;
    }

    public void run() {
        while (true) {
            if (!Thread.interrupted()) {
                ITask itask = (ITask) this.queue.getNew();
                if (itask instanceof ZeronerBleWriteDataTask) {
                    ZeronerBleWriteDataTask task = (ZeronerBleWriteDataTask) itask;
                    DataBean dataBean = task.getBean();
                    if (this.lastTask == null || this.lastTask != task || (dataBean.isNeedRetry() && dataBean.getRetryCount() <= 5)) {
                        if (dataBean.isNeedRetry()) {
                            needWait(5);
                        }
                        if (this.lastTask != task || dataBean.isNeedRetry()) {
                            if (!dataBean.isUnbind() || this.queue.size() <= 1) {
                                KLog.d(LOG_TAG, "id = " + getId() + "  " + getName() + ": task.task()queue size：" + this.queue.size());
                                task.task();
                                this.lastTask = task;
                                this.timeTask = System.currentTimeMillis();
                                if (dataBean.isUnbind()) {
                                    this.queue.remove();
                                }
                            } else {
                                KLog.i("!task.isUnbind() || queue.size() <= 1");
                                task.task();
                                this.queue.clear();
                            }
                            KLog.w(LOG_TAG, getName() + ": task is null.");
                        } else {
                            KLog.e("-----lastTask same---------");
                        }
                    } else {
                        this.timeContinue = System.currentTimeMillis();
                        needWait(1);
                        if (this.timeContinue < this.timeTask) {
                            this.timeTask = this.timeContinue;
                        }
                        if ((this.timeContinue - this.timeTask >= 15000 || dataBean.getRetryCount() > 5) && this.mTaskManager != null) {
                            this.mTaskManager.removeTask();
                        }
                    }
                } else if (itask != null) {
                    itask.task();
                }
            }
        }
    }

    private void needWait(long seconds) {
        if (seconds > 0) {
            this.mLock.lock();
            try {
                this.mCondition.await(seconds, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                ThrowableExtension.printStackTrace(e);
            } finally {
                this.mLock.unlock();
            }
        }
    }

    public Lock getLock() {
        return this.mLock;
    }

    public void setLock(Lock lock) {
        this.mLock = lock;
    }

    public Condition getCondition() {
        return this.mCondition;
    }

    public void wakeUp() {
        this.mLock.lock();
        this.mCondition.signalAll();
        this.mLock.unlock();
    }
}
