package com.iwown.ble_module.proto.task;

import android.content.Context;
import com.socks.library.KLog;

public class MessageTask implements ITask {
    public static final long TIME_OUT_LONG = 10800000;
    public static final long TIME_OUT_SHORT = 1800000;
    private static MessageTask instance;
    private Context mContext;
    private Queue<Message> mQueue = new Queue<>();

    private static class Message {
        String msg;
        long timeout;
        int type;

        private Message() {
        }
    }

    public static MessageTask getInstance(Context context) {
        if (instance == null) {
            synchronized (MessageTask.class) {
                if (instance == null) {
                    instance = new MessageTask(context);
                }
            }
        }
        return instance;
    }

    private MessageTask(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void addMessage(int type, String msg, long time) {
        Message message = new Message();
        message.type = type;
        message.msg = msg;
        message.timeout = time;
        addMessage(message);
    }

    private void addMessage(Message message) {
        if (this.mQueue.size() > 8) {
            this.mQueue.remove();
        }
        this.mQueue.addTail(message);
        addTask();
    }

    public void task() {
        KLog.d("执行消息推送");
        BackgroundThreadManager.getInstance().removeTask(this);
    }

    private void addTask() {
        if (!BackgroundThreadManager.getInstance().containsTask(this)) {
            BackgroundThreadManager.getInstance().addTask(this);
        }
    }
}
