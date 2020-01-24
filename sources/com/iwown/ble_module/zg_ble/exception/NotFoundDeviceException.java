package com.iwown.ble_module.zg_ble.exception;

public class NotFoundDeviceException extends BleException {
    public NotFoundDeviceException() {
        super(103, "Not Found Device Exception Occurred!");
    }
}
