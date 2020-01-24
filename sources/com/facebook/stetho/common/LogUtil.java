package com.facebook.stetho.common;

import java.util.Locale;

public class LogUtil {
    private static final String TAG = "stetho";

    public static void e(String format, Object... args) {
        e(format(format, args));
    }

    public static void e(Throwable t, String format, Object... args) {
        e(t, format(format, args));
    }

    public static void e(String message) {
        if (isLoggable(6)) {
            LogRedirector.e(TAG, message);
        }
    }

    public static void e(Throwable t, String message) {
        if (isLoggable(6)) {
            LogRedirector.e(TAG, message, t);
        }
    }

    public static void w(String format, Object... args) {
        w(format(format, args));
    }

    public static void w(Throwable t, String format, Object... args) {
        w(t, format(format, args));
    }

    public static void w(String message) {
        if (isLoggable(5)) {
            LogRedirector.w(TAG, message);
        }
    }

    public static void w(Throwable t, String message) {
        if (isLoggable(5)) {
            LogRedirector.w(TAG, message, t);
        }
    }

    public static void i(String format, Object... args) {
        i(format(format, args));
    }

    public static void i(Throwable t, String format, Object... args) {
        i(t, format(format, args));
    }

    public static void i(String message) {
        if (isLoggable(4)) {
            LogRedirector.i(TAG, message);
        }
    }

    public static void i(Throwable t, String message) {
        if (isLoggable(4)) {
            LogRedirector.i(TAG, message, t);
        }
    }

    public static void d(String format, Object... args) {
        d(format(format, args));
    }

    public static void d(Throwable t, String format, Object... args) {
        d(t, format(format, args));
    }

    public static void d(String message) {
        if (isLoggable(3)) {
            LogRedirector.d(TAG, message);
        }
    }

    public static void d(Throwable t, String message) {
        if (isLoggable(3)) {
            LogRedirector.d(TAG, message, t);
        }
    }

    public static void v(String format, Object... args) {
        v(format(format, args));
    }

    public static void v(Throwable t, String format, Object... args) {
        v(t, format(format, args));
    }

    public static void v(String message) {
        if (isLoggable(2)) {
            LogRedirector.v(TAG, message);
        }
    }

    public static void v(Throwable t, String message) {
        if (isLoggable(2)) {
            LogRedirector.v(TAG, message, t);
        }
    }

    private static String format(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }

    public static boolean isLoggable(int priority) {
        switch (priority) {
            case 5:
            case 6:
                return true;
            default:
                return LogRedirector.isLoggable(TAG, priority);
        }
    }
}
