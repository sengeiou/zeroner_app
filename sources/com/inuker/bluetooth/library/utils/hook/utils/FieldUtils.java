package com.inuker.bluetooth.library.utils.hook.utils;

import com.inuker.bluetooth.library.utils.StringUtils;
import java.lang.reflect.Field;

public class FieldUtils {
    public static Field getDeclaredField(Class<?> cls, String fieldName, boolean forceAccess) {
        boolean z = true;
        if (cls == null) {
            z = false;
        }
        Validate.isTrue(z, "The class must not be null", new Object[0]);
        Validate.isTrue(StringUtils.isNotBlank(fieldName), "The field name must not be blank/empty", new Object[0]);
        try {
            Field field = cls.getDeclaredField(fieldName);
            if (MemberUtils.isAccessible(field)) {
                return field;
            }
            if (!forceAccess) {
                return null;
            }
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
