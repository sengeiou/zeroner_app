package com.iwown.ble_module.iwown.task;

import java.util.ArrayList;
import java.util.List;

public class DataBean {
    public static final int TYPE_NORMAL = 2;
    public static final int TYPE_UNBIND = 3;
    public static final int TYPE_UPDATE = 1;
    private List<byte[]> data = new ArrayList();
    private boolean flag = true;
    private boolean isFmData;
    private boolean isNeedRetry = false;
    private boolean isUnbind = false;
    private int retryCount = 0;

    public void addData(byte[] bytes) {
        this.data.add(bytes);
    }

    public List<byte[]> getData() {
        return this.data;
    }

    public void setData(List<byte[]> data2) {
        this.data = data2;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag2) {
        this.flag = flag2;
    }

    public boolean isUnbind() {
        return this.isUnbind;
    }

    public void setUnbind(boolean unbind) {
        this.isUnbind = unbind;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int retryCount2) {
        this.retryCount = retryCount2;
    }

    public boolean isNeedRetry() {
        return this.isNeedRetry;
    }

    public void setNeedRetry(boolean needRetry) {
        this.isNeedRetry = needRetry;
    }

    public boolean isFmData() {
        return this.isFmData;
    }

    public void setFmData(boolean fmData) {
        this.isFmData = fmData;
    }
}
