package com.iwown.ble_module.proto.task;

import android.content.Context;
import com.iwown.ble_module.utils.Util;
import com.iwown.device_module.common.BaseActionUtils;
import com.iwown.lib_common.log.L;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BackgroundThreadManager implements ITaskManager {
    private static final int THREAD_COUNTS = 1;
    private static BackgroundThreadManager instance;
    private boolean isWriteUnbind;
    private BackgroundThread mBackgroundThread = new BackgroundThread("Thread - 1", this.queue, this);
    private int mtu = 244;
    private Queue<ITask> queue = new Queue<>();

    private BackgroundThreadManager() {
        this.mBackgroundThread.start();
    }

    public static BackgroundThreadManager getInstance() {
        if (instance == null) {
            synchronized (BackgroundThreadManager.class) {
                if (instance == null) {
                    instance = new BackgroundThreadManager();
                }
            }
        }
        return instance;
    }

    public void addWriteData(Context context, byte[] data) {
        this.mtu = getMaxMtu(context);
        for (byte[] bytes1 : multipePackage(data, this.mtu)) {
            addTask(new BleWriteDataTask(context, bytes1));
        }
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
            L.file("移除WaitTask数量：" + cnt, 4);
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

    public int removeAllWaitTask() {
        int remove_cnt = 0;
        LinkedList<ITask> tasks = this.queue.getAllTaskNow();
        if (!(tasks == null || tasks.size() == 0)) {
            synchronized (tasks) {
                if (tasks != null) {
                    if (tasks.size() != 0) {
                        LinkedList<ITask> task2Remove = new LinkedList<>();
                        Iterator it = tasks.iterator();
                        while (it.hasNext()) {
                            ITask task = (ITask) it.next();
                            if (task instanceof WaitTask) {
                                task2Remove.add(task);
                                remove_cnt++;
                            }
                        }
                        Iterator it2 = task2Remove.iterator();
                        while (it2.hasNext()) {
                            this.queue.remove((ITask) it2.next());
                        }
                    }
                }
                return 0;
            }
        }
        return remove_cnt;
    }

    public synchronized void removeAllTaskExceptMsg() {
        LinkedList<ITask> tasks = this.queue.getAllTaskNow();
        if (tasks != null) {
            Iterator<ITask> taskIterator = tasks.iterator();
            while (taskIterator.hasNext()) {
                ITask task = (ITask) taskIterator.next();
                if (!(task instanceof MessageTask)) {
                    if (!(task instanceof BleWriteDataTask)) {
                        taskIterator.remove();
                    } else if (!((BleWriteDataTask) task).isBelongMsgTask()) {
                        L.file("刚连接时，移除队列多余任务：" + Util.bytesToString(((BleWriteDataTask) task).getDatas()), 4);
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
        if (!noTasks()) {
            this.queue.addAllSecond(list);
        } else {
            this.queue.addAllTail(list);
        }
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

    public boolean noTasks() {
        return this.queue.isEmpty();
    }

    public boolean isWriteUnbind() {
        return this.isWriteUnbind;
    }

    public void setWriteUnbind(boolean isWriteUnbind2) {
        this.isWriteUnbind = isWriteUnbind2;
    }

    private List<byte[]> multipePackage(byte[] bytes, int mtu2) {
        List<byte[]> packageList = new LinkedList<>();
        if (mtu2 > 0) {
            if (bytes.length > mtu2) {
                int i = 0;
                while (i < bytes.length) {
                    int to = i + mtu2;
                    if (to > bytes.length) {
                        to = bytes.length;
                    }
                    packageList.add(Arrays.copyOfRange(bytes, i, to));
                    i += mtu2;
                }
            } else {
                packageList.add(Arrays.copyOfRange(bytes, 0, bytes.length));
            }
        }
        return packageList;
    }

    private int getMaxMtu(Context context) {
        return context.getSharedPreferences("COM.ZERONER_WRISTBAND_SHAREDPREFERENCES", 0).getInt(BaseActionUtils.PROTOBUF_MTU_INFO, 244);
    }
}
