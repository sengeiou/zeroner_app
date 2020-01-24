package com.inuker.bluetooth.library.utils.hook;

import android.os.IBinder;
import android.os.IInterface;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.hook.utils.HookUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BluetoothManagerBinderProxyHandler implements InvocationHandler {
    private IBinder iBinder;
    private Object iBluetoothManager;
    private Class<?> iBluetoothManagerClaz = HookUtils.getClass("android.bluetooth.IBluetoothManager");

    BluetoothManagerBinderProxyHandler(IBinder iBinder2) {
        this.iBinder = iBinder2;
        this.iBluetoothManager = HookUtils.invoke(HookUtils.getMethod(HookUtils.getClass("android.bluetooth.IBluetoothManager$Stub"), "asInterface", IBinder.class), null, iBinder2);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        BluetoothLog.v(String.format("IBinder method: %s", new Object[]{method.getName()}));
        if (!"queryLocalInterface".equals(method.getName())) {
            return method.invoke(this.iBinder, args);
        }
        return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[]{IBinder.class, IInterface.class, this.iBluetoothManagerClaz}, new BluetoothManagerProxyHandler(this.iBluetoothManager));
    }
}
