package com.iwown.ble_module.zg_ble.data.model;

public class AgpsType {
    private int code;
    private int data;
    private int statusCode;

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode2) {
        this.statusCode = statusCode2;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public int getData() {
        return this.data;
    }

    public void setData(int data2) {
        this.data = data2;
    }
}
