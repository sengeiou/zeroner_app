package com.iwown.ble_module.zg_ble.task;

import android.os.Handler;
import android.os.Looper;
import com.socks.library.KLog;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TaskHandler {
    private static TaskHandler instance;
    /* access modifiers changed from: private */
    public BleMessage lastBleMessage;
    /* access modifiers changed from: private */
    public long lastTime = 0;
    public Handler mHandler = new Handler(Looper.myLooper());
    private BlockingQueue myQueue = new ArrayBlockingQueue(1);
    private Runnable status = new Runnable() {
        public void run() {
            TaskHandler.this.setSendStatusOver();
        }
    };
    private TaskConsumer taskConsumer = TaskConsumer.getInstance(this.myQueue);
    /* access modifiers changed from: private */
    public TaskProducer taskProducer = TaskProducer.getInstance(this.myQueue);
    private Runnable timeOut = new Runnable() {
        public void run() {
            TaskHandler.this.lastTime = System.currentTimeMillis();
            if (TaskHandler.this.lastBleMessage == TaskHandler.this.taskProducer.getLastMessage() && TaskHandler.this.taskProducer.getLastMessage() != null) {
                TaskHandler.this.setSendStatusOver();
            }
        }
    };

    public void setSendStatusIng() {
        this.taskProducer.setSendStatusIng();
        this.mHandler.postDelayed(this.status, 15000);
    }

    public int getSendStatus() {
        return this.taskProducer.getSendStatus();
    }

    private TaskHandler() {
        initTaskConsumerProducer();
    }

    public void init() {
    }

    public TaskConsumer getTaskConsumer() {
        return TaskConsumer.getInstance(this.myQueue);
    }

    public TaskProducer getTaskProducer() {
        return TaskProducer.getInstance(this.myQueue);
    }

    public static TaskHandler getInstance() {
        if (instance == null) {
            synchronized (TaskHandler.class) {
                if (instance == null) {
                    instance = new TaskHandler();
                }
            }
        }
        return instance;
    }

    public void addMessageFirstImmediately(BleMessage bleMessage) {
        this.taskProducer.addMessageFirstImmediately(bleMessage);
    }

    public void addTaskMessage(BleMessage message) {
        this.taskProducer.addTaskMessage(message);
        if (!this.taskProducer.isSendOver() && System.currentTimeMillis() - this.lastTime > 30000 && !(message instanceof WaitBleMessage)) {
            this.lastBleMessage = message;
            this.mHandler.postDelayed(this.timeOut, 10000);
        }
    }

    public void setSendStatusOver() {
        KLog.e("no2855=setSendStatusOver :下一条");
        this.taskProducer.sendMessage(true, true);
        this.mHandler.removeCallbacks(this.status);
    }

    public void setSendStatusNotOver() {
        this.mHandler.removeCallbacks(this.timeOut);
        this.mHandler.removeCallbacks(this.status);
        this.mHandler.postDelayed(this.status, 15000);
    }

    public boolean isSendOver() {
        return this.taskProducer.isSendOver();
    }

    public void initTaskConsumerProducer() {
        KLog.e("=====================initTaskConsumerProducer======================");
        if (this.myQueue != null) {
            this.taskConsumer.start();
            this.taskProducer.clear();
        }
    }

    public void initStatus() {
        this.taskProducer = TaskProducer.getInstance(this.myQueue);
        this.taskProducer.clear();
    }

    public void clearTask() {
        if (this.taskProducer != null) {
            this.taskProducer.clearCacheList();
        }
    }
}
