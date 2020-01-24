package com.iwown.ble_module.zg_ble.exception;

public class TimeoutException extends BleException {
    public TimeoutException() {
        super(100, "Timeout Exception Occurred!");
    }
}
