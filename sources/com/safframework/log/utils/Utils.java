package com.safframework.log.utils;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0001H\u0007¨\u0006\u0006"}, d2 = {"Lcom/safframework/log/utils/Utils;", "", "()V", "isPrimitiveType", "", "value", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: Utils.kt */
public final class Utils {
    public static final Utils INSTANCE = null;

    static {
        new Utils();
    }

    private Utils() {
        INSTANCE = this;
    }

    @JvmStatic
    public static final boolean isPrimitiveType(@Nullable Object value) {
        if (!(value instanceof Boolean) && !(value instanceof String) && !(value instanceof Integer) && !(value instanceof Float) && !(value instanceof Double)) {
            return false;
        }
        return true;
    }
}
