package com.inuker.bluetooth.library.utils.hook.utils;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HookUtils {
    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        return MethodUtils.getAccessibleMethod(clazz, name, parameterTypes);
    }

    public static Field getField(Class<?> clazz, String name) {
        if (clazz != null) {
            return FieldUtils.getDeclaredField(clazz, name, true);
        }
        return null;
    }

    public static <T> T getValue(Field field) {
        return getValue(field, null);
    }

    public static <T> T getValue(Field field, Object object) {
        if (field != null) {
            try {
                return field.get(object);
            } catch (IllegalAccessException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        return null;
    }

    public static <T> T invoke(Method method, Object object, Object... parameters) {
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            ThrowableExtension.printStackTrace(e);
        } catch (InvocationTargetException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
        return null;
    }
}
