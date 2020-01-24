package com.inuker.bluetooth.library.utils.hook;

import com.inuker.bluetooth.library.utils.BluetoothLog;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BluetoothGattProxyHandler implements InvocationHandler {
    private Object bluetoothGatt;

    BluetoothGattProxyHandler(Object bluetoothGatt2) {
        this.bluetoothGatt = bluetoothGatt2;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        BluetoothLog.v(String.format("IBluetoothGatt method: %s", new Object[]{method.getName()}));
        return method.invoke(this.bluetoothGatt, args);
    }
}
