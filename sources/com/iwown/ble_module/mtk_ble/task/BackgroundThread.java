package com.iwown.ble_module.mtk_ble.task;

import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BackgroundThread extends Thread {
    private static final String LOG_TAG = BackgroundThread.class.getSimpleName();
    private byte[] datas = new byte[0];
    private ITask lastTask;
    private final Condition mCondition;
    private Lock mLock;
    private final ITaskManager mTaskManager;
    private Queue<ITask> queue;
    private long timeContinue;
    private long timeTask;

    public BackgroundThread(String threadName, Queue<ITask> queue2, ITaskManager manager) {
        super(threadName);
        this.queue = queue2;
        this.mLock = new ReentrantLock();
        this.mCondition = this.mLock.newCondition();
        this.mTaskManager = manager;
    }

    public void run() {
        while (true) {
            if (!Thread.interrupted()) {
                ITask itask = (ITask) this.queue.getNew();
                if (itask instanceof BleWriteDataTask) {
                    BleWriteDataTask task = (BleWriteDataTask) itask;
                    if (this.lastTask == null || this.lastTask != task || (task.isNeedRetry() && task.getRetryCount() <= 5)) {
                        if (task.isNeedRetry()) {
                            needWait(1);
                        }
                        if (!task.isUnbind() || this.queue.size() <= 1) {
                            Log.d(LOG_TAG, "id = " + getId() + "  " + getName() + ": task.task()queue size：" + this.queue.size());
                            this.datas = task.getDatas();
                            task.task();
                            this.lastTask = task;
                            this.timeTask = System.currentTimeMillis();
                            if (task.isUnbind()) {
                                this.queue.remove();
                            }
                        } else {
                            task.task();
                            this.queue.clear();
                        }
                        Log.e(LOG_TAG, getName() + ": task is null.");
                    } else {
                        this.timeContinue = System.currentTimeMillis();
                        needWait(1);
                        if (this.timeContinue < this.timeTask) {
                            this.timeTask = this.timeContinue;
                        }
                        if ((this.timeContinue - this.timeTask >= 15000 || task.getRetryCount() > 5) && this.mTaskManager != null) {
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
