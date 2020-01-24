package com.iwown.ble_module.mtk_ble.task;

import android.content.Context;
import android.util.Log;
import com.iwown.ble_module.utils.Util;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MtkBackgroundThreadManager implements ITaskManager {
    private static final int THREAD_COUNTS = 1;
    private static MtkBackgroundThreadManager instance;
    private String TAG = getClass().getSimpleName();
    private boolean isWriteUnbind;
    private BackgroundThread mBackgroundThread = new BackgroundThread("Thread - 1", this.queue, this);
    private Queue<ITask> queue = new Queue<>();

    private MtkBackgroundThreadManager() {
        this.mBackgroundThread.start();
    }

    public static MtkBackgroundThreadManager getInstance() {
        if (instance == null) {
            synchronized (MtkBackgroundThreadManager.class) {
                if (instance == null) {
                    instance = new MtkBackgroundThreadManager();
                }
            }
        }
        return instance;
    }

    public void addWriteData(Context context, byte[] data) {
        addTask(new BleWriteDataTask(context, data));
    }

    public void addWriteDataAsMsg(Context context, byte[] data) {
        addTask(new BleWriteDataTask(context, data, 4));
    }

    public void addTask(ITask task) {
        if (task instanceof BleWriteDataTask) {
            BleWriteDataTask bleWriteDataTask = (BleWriteDataTask) task;
        }
        this.queue.addTail(task);
    }

    public void needWait() {
        if (this.queue.size() <= 0) {
            this.queue.addFirst(new WaitTask(this.mBackgroundThread.getLock(), this.mBackgroundThread.getCondition()));
        } else if (!(((ITask) this.queue.getNew()) instanceof WaitTask)) {
            this.queue.addFirst(new WaitTask(this.mBackgroundThread.getLock(), this.mBackgroundThread.getCondition()));
        }
    }

    public void wakeUp() {
        int cnt = removeAllWaitTask();
        if (cnt > 0) {
            Log.i(this.TAG, "移除WaitTask数量：" + cnt);
        }
        this.mBackgroundThread.wakeUp();
    }

    public void removeUnbindTask() {
        while (this.queue.size() > 0) {
            ITask lastTask = getLastTask();
            if ((lastTask instanceof BleWriteDataTask) && ((BleWriteDataTask) lastTask).isUnbind()) {
                removeTask(lastTask);
            } else {
                return;
            }
        }
    }

    public synchronized int removeAllWaitTask() {
        int i;
        int remove_cnt = 0;
        LinkedList<ITask> tasks = this.queue.getAllTaskNow();
        if (tasks == null) {
            i = 0;
        } else {
            Iterator<ITask> taskIterator = tasks.iterator();
            while (taskIterator.hasNext()) {
                if (((ITask) taskIterator.next()) instanceof WaitTask) {
                    taskIterator.remove();
                    remove_cnt++;
                }
            }
            i = remove_cnt;
        }
        return i;
    }

    public synchronized void removeAllTaskExceptMsg() {
        LinkedList<ITask> tasks = this.queue.getAllTaskNow();
        if (tasks != null) {
            Iterator<ITask> taskIterator = tasks.iterator();
            while (taskIterator.hasNext()) {
                ITask task = (ITask) taskIterator.next();
                if (!(task instanceof MtkMessageTask)) {
                    if (!(task instanceof BleWriteDataTask)) {
                        taskIterator.remove();
                    } else if (!((BleWriteDataTask) task).isBelongMsgTask()) {
                        Log.i(this.TAG, "刚连接时，移除队列多余任务：" + Util.bytesToString(((BleWriteDataTask) task).getDatas()));
                        taskIterator.remove();
                    }
                }
            }
        }
    }

    public void addAllTask(List<ITask> list) {
        this.queue.addAllTail(list);
    }

    public void addAllTaskSecond(List<ITask> list) {
        this.queue.addAllSecond(list);
    }

    public void removeTask() {
        this.queue.remove();
        this.mBackgroundThread.wakeUp();
    }

    public void removeTask(ITask task) {
        this.queue.remove(task);
    }

    public boolean containsTask(ITask task) {
        return this.queue.contains(task);
    }

    public int getQueueSize() {
        return this.queue.size();
    }

    public ITask getLastTask() {
        return (ITask) this.queue.getLast();
    }

    public ITask getNowTask() {
        return (ITask) this.queue.getNew();
    }

    public ITask getNowTaskNotWait() {
        return (ITask) this.queue.getNewNotWait();
    }

    public void clearQueue() {
        this.queue.clear();
    }

    public boolean isWriteUnbind() {
        return this.isWriteUnbind;
    }

    public void setWriteUnbind(boolean isWriteUnbind2) {
        this.isWriteUnbind = isWriteUnbind2;
    }
}
