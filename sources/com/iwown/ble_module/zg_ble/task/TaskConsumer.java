package com.iwown.ble_module.zg_ble.task;

import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskConsumer extends Thread {
    private static TaskConsumer instance;
    private String TAG = TaskConsumer.class.getName();
    private Condition mCondition;
    private Lock mLock;
    private BlockingQueue<BleMessage> queue;

    public static TaskConsumer getInstance(BlockingQueue<BleMessage> queue2) {
        if (instance == null) {
            instance = new TaskConsumer(queue2);
        }
        return instance;
    }

    private TaskConsumer(BlockingQueue<BleMessage> queue2) {
        this.queue = queue2;
        this.mLock = new ReentrantLock();
        this.mCondition = this.mLock.newCondition();
    }

    public void run() {
        while (true) {
            try {
                if (TaskHandler.getInstance().isSendOver()) {
                    BleMessage take = (BleMessage) this.queue.take();
                    if (take.bytes != null) {
                        KLog.e("TaskConsumer: " + this.queue.size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + take.bytes.size());
                    }
                    if (take instanceof WaitBleMessage) {
                        KLog.e(" ============waitTask=================");
                        ((WaitBleMessage) take).waitTask();
                    } else if (take instanceof AgpsBleMessage) {
                        KLog.e("no2855AgpsBleMessage queue size:" + this.queue.size() + " TaskConsumer send cmd success:" + take.toString());
                        BleHandler.getInstance().wirteMessege2Ble(take, 30);
                    } else {
                        KLog.e("queue size:" + this.queue.size() + " TaskConsumer send cmd success:" + take.toString());
                        BleHandler.getInstance().wirteMessege2Ble(take, 240);
                    }
                }
            } catch (Exception e) {
                Log.e(this.TAG, " TaskConsumer InterruptedException " + e.getLocalizedMessage());
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
        TaskHandler.getInstance().getTaskProducer().removeWaitTask();
        int cnt = removeAllWaitTask();
        if (cnt > 0) {
            L.file("移除WaitTask数量：" + cnt, 3);
            KLog.e("移除WaitTask数量：" + cnt);
        }
        this.mLock.lock();
        this.mCondition.signalAll();
        this.mLock.unlock();
    }

    public synchronized int removeAllWaitTask() {
        int remove_cnt;
        remove_cnt = 0;
        try {
            Iterator taskIterator = this.queue.iterator();
            while (taskIterator.hasNext()) {
                if (((BleMessage) taskIterator.next()) instanceof WaitBleMessage) {
                    taskIterator.remove();
                    remove_cnt++;
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return remove_cnt;
    }

    public void needWait() {
        if (this.queue.size() > 0) {
            BleMessage message = (BleMessage) this.queue.peek();
            if (message != null && !(message instanceof WaitBleMessage)) {
                WaitBleMessage waitTask = new WaitBleMessage();
                waitTask.setmLock(this.mLock);
                waitTask.setmCondition(this.mCondition);
                this.queue.offer(waitTask);
                return;
            }
            return;
        }
        WaitBleMessage waitTask2 = new WaitBleMessage();
        waitTask2.setmLock(this.mLock);
        waitTask2.setmCondition(this.mCondition);
        this.queue.offer(waitTask2);
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
