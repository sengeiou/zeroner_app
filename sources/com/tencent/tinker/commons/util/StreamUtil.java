package com.tencent.tinker.commons.util;

import java.io.Closeable;
import java.util.zip.ZipFile;

public final class StreamUtil {
    public static void closeQuietly(Object obj) {
        if (obj != null) {
            try {
                if (obj instanceof Closeable) {
                    ((Closeable) obj).close();
                } else if (obj instanceof AutoCloseable) {
                    ((AutoCloseable) obj).close();
                } else if (obj instanceof ZipFile) {
                    ((ZipFile) obj).close();
                }
            } catch (Throwable th) {
            }
        }
    }
}
