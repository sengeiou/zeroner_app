package com.iwown.ble_module.zg_ble.task;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.socks.library.KLog;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskProducer {
    private static final int SendIng = 1;
    private static final int SendOver = 2;
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static TaskProducer instance;
    private String TAG = TaskProducer.class.getName();
    private LinkedList<BleMessage> cacheBleMessageLists = new LinkedList<>();
    private boolean isSend = false;
    private BlockingQueue<BleMessage> queue;
    private Integer sendStatus = Integer.valueOf(2);

    public static TaskProducer getInstance(BlockingQueue<BleMessage> queue2) {
        if (instance == null) {
            instance = new TaskProducer(queue2);
        }
        return instance;
    }

    private TaskProducer() {
    }

    private TaskProducer(BlockingQueue<BleMessage> queue2) {
        this.queue = queue2;
        this.cacheBleMessageLists = new LinkedList<>();
    }

    public void addMessageFirstImmediately(BleMessage message) {
        this.cacheBleMessageLists.add(0, message);
        sendMessage(true, true);
    }

    public void addTaskMessage(BleMessage message) {
        boolean isCanSend = true;
        if (this.cacheBleMessageLists != null && this.cacheBleMessageLists.size() > 0) {
            isCanSend = this.cacheBleMessageLists.getLast() instanceof WaitBleMessage;
        }
        this.cacheBleMessageLists.add(message);
        sendMessage(false, isCanSend);
    }

    public void sendMessage(boolean reshStatue, boolean isCanSend) {
        synchronized (this.sendStatus) {
            if (reshStatue) {
                setSendStatusOver();
            } else if (!isCanSend || this.sendStatus.intValue() != 2) {
                KLog.e("no2855=这里不可发送数据");
            } else {
                KLog.e("no2855=这里也可以??可以发送下一个");
                executorSendLastTask();
            }
        }
    }

    public void executorSendLastTask() {
        if (this.cacheBleMessageLists != null && this.cacheBleMessageLists.size() != 0) {
            try {
                BleMessage message = (BleMessage) this.cacheBleMessageLists.getFirst();
                KLog.e("queue size:" + this.queue.size());
                if (message == null) {
                    return;
                }
                if (!this.queue.offer(message)) {
                    KLog.e("列队满了 不能插入 ");
                    TaskHandler.getInstance().getTaskConsumer().wakeUp();
                } else if (this.cacheBleMessageLists.size() != 0) {
                    try {
                        this.cacheBleMessageLists.removeFirst();
                        KLog.e("发送队列空闲 插入成功 cache.size " + this.cacheBleMessageLists.size());
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            } catch (Exception e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
    }

    private void setSendStatusOver() {
        this.sendStatus = Integer.valueOf(2);
        KLog.e("no2855=这里发送的数据哦====");
        executorSendLastTask();
    }

    public boolean isSendOver() {
        return this.sendStatus.intValue() == 2;
    }

    public void removeWaitTask() {
        try {
            Iterator<BleMessage> it = this.cacheBleMessageLists.iterator();
            while (it.hasNext()) {
                if (((BleMessage) it.next()) instanceof WaitBleMessage) {
                    it.remove();
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void setSendStatusIng() {
        this.sendStatus = Integer.valueOf(1);
    }

    public int getSendStatus() {
        return this.sendStatus.intValue();
    }

    public void clear() {
        this.sendStatus = Integer.valueOf(2);
    }

    public void clearCacheList() {
        this.sendStatus = Integer.valueOf(2);
        this.cacheBleMessageLists.clear();
    }

    private static LinkedList<BleMessage> removeDuplicatedElements(LinkedList<BleMessage> list) {
        try {
            HashSet set = new HashSet();
            Iterator iter = list.listIterator();
            while (iter.hasNext()) {
                BleMessage bleMessage = (BleMessage) iter.next();
                if (!set.contains(bleMessage)) {
                    set.add(bleMessage);
                } else {
                    iter.remove();
                    KLog.e("no2855remove---->:bleMessage" + bleMessage.toString());
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return list;
    }

    public BleMessage getLastMessage() {
        if (this.cacheBleMessageLists == null || this.cacheBleMessageLists.size() <= 0) {
            return null;
        }
        return (BleMessage) this.cacheBleMessageLists.getLast();
    }
}
