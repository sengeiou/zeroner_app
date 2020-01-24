package com.iwown.ble_module.iwown.task;

import android.content.Context;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.bluetooth.ZeronerBle;

public class ZeronerBleWriteDataTask implements ITask {
    private DataBean bean;
    private Context context;

    public ZeronerBleWriteDataTask(Context context2, DataBean bean2) {
        this.context = context2.getApplicationContext();
        this.bean = bean2;
    }

    public void task() {
        if (ZeronerBackgroundThreadManager.getInstance().containsTask(this) && this.bean != null && this.bean.getData().size() != 0) {
            this.bean.setRetryCount(this.bean.getRetryCount() + 1);
            if (this.bean.isUnbind() || this.bean.isFmData()) {
                ZeronerBackgroundThreadManager.getInstance().setWriteUnbind(true);
            }
            ZeronerBle instance = ZeronerBle.getInstance();
            for (int i = 0; i < this.bean.getData().size(); i++) {
                if (this.bean.isFlag()) {
                    try {
                        Thread.sleep(240);
                    } catch (InterruptedException e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
                try {
                    if (this.bean.getData() != null) {
                        instance.writeDataToWristBand((byte[]) this.bean.getData().get(i));
                    }
                } catch (Exception e2) {
                    ThrowableExtension.printStackTrace(e2);
                }
            }
        }
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context2) {
        this.context = context2;
    }

    public DataBean getBean() {
        return this.bean;
    }

    public void setBean(DataBean bean2) {
        this.bean = bean2;
    }
}
