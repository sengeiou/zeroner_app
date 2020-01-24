package com.iwown.ble_module.zg_ble.data.model;

import com.iwown.ble_module.utils.JsonTool;

public class Result {
    private int dataType = -1;
    private int index = 0;
    private boolean isLastData = true;
    private boolean isRspForRead = true;
    private int result_code = -1;

    public boolean isLastData() {
        return this.isLastData;
    }

    public void setLastData(boolean lastData) {
        this.isLastData = lastData;
    }

    public boolean isRspForRead() {
        return this.isRspForRead;
    }

    public void setRspForRead(boolean rspForRead) {
        this.isRspForRead = rspForRead;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index2) {
        this.index = index2;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType2) {
        this.dataType = dataType2;
    }

    public int getResult_code() {
        return this.result_code;
    }

    public void setResult_code(int result_code2) {
        this.result_code = result_code2;
    }

    public Result initDataInfo(byte[] bytes) {
        boolean z;
        boolean z2;
        this.dataType = bytes[0];
        if ((bytes[0] & 128) == 128) {
            z = true;
        } else {
            z = false;
        }
        this.isRspForRead = z;
        this.index = bytes[2] & Byte.MAX_VALUE;
        if ((bytes[2] & 128) == 128) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.isLastData = z2;
        byte data_3 = bytes[3];
        if (data_3 == 1) {
            this.result_code = bytes[4];
        } else if (data_3 == 2) {
            if (bytes[4] == 0) {
                this.result_code = 0;
            } else {
                this.result_code = bytes[5];
            }
        } else if (data_3 > 2) {
            this.result_code = 0;
        }
        return this;
    }

    public String toString() {
        return JsonTool.toJson(this);
    }
}
