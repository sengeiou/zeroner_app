package com.alibaba.android.arouter.utils;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.android.arouter.facade.template.ILogger;

public class DefaultLogger implements ILogger {
    private static boolean isMonitorMode = false;
    private static boolean isShowLog = false;
    private static boolean isShowStackTrace = false;
    private String defaultTag = Consts.SDK_NAME;

    public void showLog(boolean showLog) {
        isShowLog = showLog;
    }

    public void showStackTrace(boolean showStackTrace) {
        isShowStackTrace = showStackTrace;
    }

    public void showMonitor(boolean showMonitor) {
        isMonitorMode = showMonitor;
    }

    public DefaultLogger() {
    }

    public DefaultLogger(String defaultTag2) {
        this.defaultTag = defaultTag2;
    }

    public void debug(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag();
            }
            Log.d(tag, message + getExtInfo(stackTraceElement));
        }
    }

    public void info(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag();
            }
            Log.i(tag, message + getExtInfo(stackTraceElement));
        }
    }

    public void warning(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag();
            }
            Log.w(tag, message + getExtInfo(stackTraceElement));
        }
    }

    public void error(String tag, String message) {
        if (isShowLog) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            if (TextUtils.isEmpty(tag)) {
                tag = getDefaultTag();
            }
            Log.e(tag, message + getExtInfo(stackTraceElement));
        }
    }

    public void monitor(String message) {
        if (isShowLog && isMonitorMode()) {
            Log.d(this.defaultTag + "::monitor", message + getExtInfo(Thread.currentThread().getStackTrace()[3]));
        }
    }

    public boolean isMonitorMode() {
        return isMonitorMode;
    }

    public String getDefaultTag() {
        return this.defaultTag;
    }

    public static String getExtInfo(StackTraceElement stackTraceElement) {
        String separator = " & ";
        StringBuilder sb = new StringBuilder("[");
        if (isShowStackTrace) {
            String threadName = Thread.currentThread().getName();
            String fileName = stackTraceElement.getFileName();
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            long threadID = Thread.currentThread().getId();
            int lineNumber = stackTraceElement.getLineNumber();
            sb.append("ThreadId=").append(threadID).append(separator);
            sb.append("ThreadName=").append(threadName).append(separator);
            sb.append("FileName=").append(fileName).append(separator);
            sb.append("ClassName=").append(className).append(separator);
            sb.append("MethodName=").append(methodName).append(separator);
            sb.append("LineNumber=").append(lineNumber);
        }
        sb.append(" ] ");
        return sb.toString();
    }
}
