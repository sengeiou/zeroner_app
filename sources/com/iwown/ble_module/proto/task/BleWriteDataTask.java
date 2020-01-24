package com.iwown.ble_module.proto.task;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.proto.ble.ProtoBle;

public class BleWriteDataTask implements ITask {
    public static final int TYPE_MESSAGE = 4;
    public static final int TYPE_NORMAL = 2;
    public static final int TYPE_UNBIND = 3;
    public static final int TYPE_UPDATE = 1;
    private boolean belongMsgTask = false;
    private Context context;
    private byte[] datas;
    private boolean flag = true;
    private boolean isFmData;
    private boolean isNeedRetry = false;
    private boolean isUnbind = false;
    private int retryCount = 0;

    public boolean isBelongMsgTask() {
        return this.belongMsgTask;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public boolean isNeedRetry() {
        return this.isNeedRetry;
    }

    public void setNeedRetry(boolean needRetry) {
        this.isNeedRetry = needRetry;
    }

    public BleWriteDataTask() {
    }

    public BleWriteDataTask(Context context2, byte[] datas2) {
        this.context = context2.getApplicationContext();
        if (datas2 == null) {
            this.datas = new byte[0];
        } else {
            this.datas = datas2;
        }
    }

    public BleWriteDataTask(Context context2, byte[] datas2, int type) {
        this.context = context2.getApplicationContext();
        if (datas2 == null) {
            this.datas = new byte[0];
        } else {
            this.datas = datas2;
        }
        if (type == 3) {
            this.isUnbind = true;
        } else if (type == 1) {
            this.isFmData = true;
        } else if (type == 4) {
            this.belongMsgTask = true;
        }
    }

    public BleWriteDataTask(Context context2, byte[] datas2, boolean isUnbind2) {
        this.context = context2.getApplicationContext();
        this.isUnbind = isUnbind2;
        if (datas2 == null) {
            this.datas = new byte[0];
        } else {
            this.datas = datas2;
        }
    }

    public void task() {
        if (BackgroundThreadManager.getInstance().containsTask(this)) {
            this.retryCount++;
            if (this.datas.length == 0) {
                BackgroundThreadManager.getInstance().removeTask();
                return;
            }
            if (this.flag) {
                try {
                    Thread.sleep(240);
                } catch (InterruptedException e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
            if (this.isUnbind || this.isFmData) {
                BackgroundThreadManager.getInstance().setWriteUnbind(true);
            }
            ProtoBle protoBle = ProtoBle.getInstance();
            if (protoBle != null && protoBle.isConnected()) {
                protoBle.writeDataToWristBand(this.datas);
            }
        }
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag2) {
        this.flag = flag2;
    }

    public byte[] getDatas() {
        return this.datas;
    }

    public boolean isUnbind() {
        return this.isUnbind;
    }

    public void setUnbind(boolean unbind) {
        this.isUnbind = unbind;
    }

    public Context getContext() {
        return this.context;
    }
}
