package com.tencent.bugly.beta.tinker;

import android.util.Log;
import com.tencent.tinker.lib.util.TinkerLog.TinkerLogImp;

/* compiled from: BUGLY */
public class TinkerLogger implements TinkerLogImp {
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_NONE = 5;
    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_WARNING = 3;
    private static final String TAG = "Tinker.TinkerLogger";
    private static int level = 0;

    public static int getLogLevel() {
        return level;
    }

    public static void setLevel(int level2) {
        level = level2;
        Log.w(TAG, "new log level: " + level2);
    }

    public void v(String s, String s1, Object... objects) {
        if (level <= 0) {
            if (objects != null) {
                s1 = String.format(s1, objects);
            }
            Log.v(s, s1);
        }
    }

    public void i(String s, String s1, Object... objects) {
        if (level <= 2) {
            if (objects != null) {
                s1 = String.format(s1, objects);
            }
            Log.i(s, s1);
        }
    }

    public void w(String s, String s1, Object... objects) {
        if (level <= 3) {
            if (objects != null) {
                s1 = String.format(s1, objects);
            }
            Log.w(s, s1);
        }
    }

    public void d(String s, String s1, Object... objects) {
        if (level <= 1) {
            if (objects != null) {
                s1 = String.format(s1, objects);
            }
            Log.d(s, s1);
        }
    }

    public void e(String s, String s1, Object... objects) {
        if (level <= 4) {
            if (objects != null) {
                s1 = String.format(s1, objects);
            }
            Log.e(s, s1);
        }
    }

    public void printErrStackTrace(String s, Throwable throwable, String s1, Object... objects) {
        String format = objects == null ? s1 : String.format(s1, objects);
        if (format == null) {
            format = "";
        }
        Log.e(s, format + "  " + Log.getStackTraceString(throwable));
    }
}
