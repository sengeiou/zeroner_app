package com.facebook.stetho.common;

import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.PrintWriter;
import java.io.StringWriter;

public class LogRedirector {
    private static volatile Logger sLogger;

    public interface Logger {
        boolean isLoggable(String str, int i);

        void log(int i, String str, String str2);
    }

    public static void setLogger(Logger logger) {
        Util.throwIfNull(logger);
        Util.throwIfNotNull(sLogger);
        sLogger = logger;
    }

    public static void e(String tag, String message, Throwable t) {
        e(tag, message + "\n" + formatThrowable(t));
    }

    public static void e(String tag, String message) {
        log(6, tag, message);
    }

    public static void w(String tag, String message, Throwable t) {
        w(tag, message + "\n" + formatThrowable(t));
    }

    public static void w(String tag, String message) {
        log(5, tag, message);
    }

    public static void i(String tag, String message, Throwable t) {
        i(tag, message + "\n" + formatThrowable(t));
    }

    public static void i(String tag, String message) {
        log(4, tag, message);
    }

    public static void d(String tag, String message, Throwable t) {
        d(tag, message + "\n" + formatThrowable(t));
    }

    public static void d(String tag, String message) {
        log(3, tag, message);
    }

    public static void v(String tag, String message, Throwable t) {
        v(tag, message + "\n" + formatThrowable(t));
    }

    public static void v(String tag, String message) {
        log(2, tag, message);
    }

    private static String formatThrowable(Throwable t) {
        StringWriter buf = new StringWriter();
        PrintWriter writer = new PrintWriter(buf);
        ThrowableExtension.printStackTrace(t);
        writer.flush();
        return buf.toString();
    }

    private static void log(int priority, String tag, String message) {
        Logger logger = sLogger;
        if (logger != null) {
            logger.log(priority, tag, message);
        } else {
            Log.println(priority, tag, message);
        }
    }

    public static boolean isLoggable(String tag, int priority) {
        Logger logger = sLogger;
        if (logger != null) {
            return logger.isLoggable(tag, priority);
        }
        return Log.isLoggable(tag, priority);
    }
}
