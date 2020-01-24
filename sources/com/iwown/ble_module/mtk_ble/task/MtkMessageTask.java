package com.iwown.ble_module.mtk_ble.task;

import android.content.Context;
import android.util.Log;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;

public class MtkMessageTask implements ITask {
    public static final long TIME_OUT_LONG = 10800000;
    public static final long TIME_OUT_SHORT = 1800000;
    private static MtkMessageTask instance;
    private String TAG = getClass().getSimpleName();
    private Context mContext;
    private Queue<Message> mQueue = new Queue<>();

    private static class Message {
        String msg;
        long timeout;
        int type;

        private Message() {
        }
    }

    public static MtkMessageTask getInstance(Context context) {
        if (instance == null) {
            synchronized (MtkMessageTask.class) {
                if (instance == null) {
                    instance = new MtkMessageTask(context);
                }
            }
        }
        return instance;
    }

    private MtkMessageTask(Context context) {
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
        Log.d(this.TAG, "执行消息推送");
        if (MTKBle.getInstance() != null) {
            while (this.mQueue.size() > 0) {
                Message message = (Message) this.mQueue.get();
                if (message.timeout > System.currentTimeMillis()) {
                    MtkCmdAssembler.getInstance().writeAlertFontLibrary(this.mContext, message.type, message.msg);
                }
            }
            MtkBackgroundThreadManager.getInstance().removeTask(this);
        }
    }

    private void addTask() {
        if (!MtkBackgroundThreadManager.getInstance().containsTask(this)) {
            MtkBackgroundThreadManager.getInstance().addTask(this);
        }
    }
}
