package com.iwown.ble_module.iwown.task;

import android.content.Context;
import java.util.List;

public class ZeronerBackgroundThreadManager implements ITaskManager {
    private static final int THREAD_COUNTS = 1;
    private static ZeronerBackgroundThreadManager instance;
    private boolean isWriteUnbind;
    private BackgroundThread mBackgroundThread = new BackgroundThread("Thread - 1", this.queue, this);
    private Queue<ITask> queue = new Queue<>();

    private ZeronerBackgroundThreadManager() {
        this.mBackgroundThread.start();
    }

    public static ZeronerBackgroundThreadManager getInstance() {
        if (instance == null) {
            synchronized (ZeronerBackgroundThreadManager.class) {
                if (instance == null) {
                    instance = new ZeronerBackgroundThreadManager();
                }
            }
        }
        return instance;
    }

    public void addWriteData(Context context, DataBean bean) {
        addTask(new ZeronerBleWriteDataTask(context, bean));
    }

    public void addTask(ITask task) {
        if (task instanceof ZeronerBleWriteDataTask) {
            ZeronerBleWriteDataTask zeronerBleWriteDataTask = (ZeronerBleWriteDataTask) task;
        }
        this.queue.addTail(task);
    }

    public void addTaskFirst(ITask task) {
        this.queue.addFirst(task);
    }

    public void needWait() {
        if (this.queue.size() <= 0) {
            this.queue.addFirst(new WaitTask(this.mBackgroundThread.getLock(), this.mBackgroundThread.getCondition()));
        } else if (!(((ITask) this.queue.getNew()) instanceof WaitTask)) {
            this.queue.addFirst(new WaitTask(this.mBackgroundThread.getLock(), this.mBackgroundThread.getCondition()));
        }
    }

    public void wakeUp() {
        while (this.queue.size() > 0 && (((ITask) this.queue.getNew()) instanceof WaitTask)) {
            this.queue.remove();
        }
        this.mBackgroundThread.wakeUp();
    }

    public void removeUnbindTask() {
        while (this.queue.size() > 0) {
            ITask lastTask = getLastTask();
            if ((lastTask instanceof ZeronerBleWriteDataTask) && ((ZeronerBleWriteDataTask) lastTask).getBean().isUnbind()) {
                removeTask(lastTask);
            } else {
                return;
            }
        }
    }

    public void addAllTask(List<ITask> list) {
        this.queue.addAllTail(list);
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
