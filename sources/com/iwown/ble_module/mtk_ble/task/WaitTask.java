package com.iwown.ble_module.mtk_ble.task;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class WaitTask implements ITask {
    private Condition mCondition;
    private Lock mLock;

    public WaitTask(Lock lock, Condition condition) {
        this.mLock = lock;
        this.mCondition = condition;
    }

    public void task() {
        if (this.mLock != null && this.mCondition != null) {
            this.mLock.lock();
            try {
                this.mCondition.await();
            } catch (InterruptedException e) {
                ThrowableExtension.printStackTrace(e);
            } finally {
                this.mLock.unlock();
            }
        }
    }
}
