package com.iwown.ble_module.zg_ble.task;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class WaitBleMessage extends BleMessage {
    private Condition mCondition;
    private Lock mLock;

    public Lock getmLock() {
        return this.mLock;
    }

    public void setmLock(Lock mLock2) {
        this.mLock = mLock2;
    }

    public Condition getmCondition() {
        return this.mCondition;
    }

    public void setmCondition(Condition mCondition2) {
        this.mCondition = mCondition2;
    }

    public void waitTask() {
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
