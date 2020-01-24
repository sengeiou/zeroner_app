package com.iwown.lib_common.file;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.Closeable;
import java.io.IOException;

public class CloseUtils {
    private CloseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void closeIO(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }
        }
    }

    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
