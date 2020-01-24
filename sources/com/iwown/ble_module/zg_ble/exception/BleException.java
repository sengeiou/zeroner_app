package com.iwown.ble_module.zg_ble.exception;

import java.io.Serializable;

public class BleException implements Serializable {
    public static final int ERROR_CODE_257 = 105;
    public static final int ERROR_CODE_BLUETOOTH_NOT_ENABLE = 104;
    public static final int ERROR_CODE_GATT = 101;
    public static final int ERROR_CODE_NOT_FOUND_DEVICE = 103;
    public static final int ERROR_CODE_OTHER = 102;
    public static final int ERROR_CODE_SCAN_FAILED = 105;
    public static final int ERROR_CODE_TIMEOUT = 100;
    private static final long serialVersionUID = 8004414918500865564L;
    private int code;
    private String description;

    public BleException(int code2, String description2) {
        this.code = code2;
        this.description = description2;
    }

    public int getCode() {
        return this.code;
    }

    public BleException setCode(int code2) {
        this.code = code2;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public BleException setDescription(String description2) {
        this.description = description2;
        return this;
    }

    public String toString() {
        return "BleException { code=" + this.code + ", description='" + this.description + '\'' + '}';
    }
}
