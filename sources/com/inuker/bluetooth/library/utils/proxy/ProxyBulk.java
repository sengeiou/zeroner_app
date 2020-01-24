package com.inuker.bluetooth.library.utils.proxy;

import com.inuker.bluetooth.library.utils.BluetoothLog;
import java.lang.reflect.Method;

public class ProxyBulk {
    public Object[] args;
    public Method method;
    public Object object;

    public ProxyBulk(Object object2, Method method2, Object[] args2) {
        this.object = object2;
        this.method = method2;
        this.args = args2;
    }

    public Object safeInvoke() {
        Object result = null;
        try {
            return this.method.invoke(this.object, this.args);
        } catch (Throwable e) {
            BluetoothLog.e(e);
            return result;
        }
    }

    public static Object safeInvoke(Object obj) {
        return ((ProxyBulk) obj).safeInvoke();
    }
}
