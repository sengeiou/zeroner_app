package com.iwown.ble_module.zg_ble.data.model;

public class ZgAgpsData {
    private int code = 0;
    private int dataType;
    private int type;
    private int writeCode = 0;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public int getWriteCode() {
        return this.writeCode;
    }

    public void setWriteCode(int writeCode2) {
        this.writeCode = writeCode2;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType2) {
        this.dataType = dataType2;
    }
}
