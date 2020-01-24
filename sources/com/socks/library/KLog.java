package com.socks.library;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.socks.library.klog.BaseLog;
import com.socks.library.klog.FileLog;
import com.socks.library.klog.JsonLog;
import com.socks.library.klog.XmlLog;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public final class KLog {
    public static final int A = 6;
    public static final int D = 2;
    private static final String DEFAULT_MESSAGE = "execute";
    public static final int E = 5;
    public static final int I = 3;
    private static boolean IS_SHOW_LOG = true;
    private static final int JSON = 7;
    public static final int JSON_INDENT = 4;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String NULL = "null";
    public static final String NULL_TIPS = "Log with null object";
    private static final String PARAM = "Param";
    private static final int STACK_TRACE_INDEX_4 = 4;
    private static final int STACK_TRACE_INDEX_5 = 5;
    private static final String SUFFIX = ".java";
    private static final String TAG_DEFAULT = "KLog";
    public static final int V = 1;
    public static final int W = 4;
    private static final int XML = 8;
    private static String mGlobalTag;
    private static boolean mIsGlobalTagEmpty = true;

    public static void init(boolean isShowLog) {
        IS_SHOW_LOG = isShowLog;
    }

    public static void init(boolean isShowLog, @Nullable String tag) {
        IS_SHOW_LOG = isShowLog;
        mGlobalTag = tag;
        mIsGlobalTagEmpty = TextUtils.isEmpty(mGlobalTag);
    }

    public static void v() {
        printLog(1, null, DEFAULT_MESSAGE);
    }

    public static void v(Object msg) {
        printLog(1, null, msg);
    }

    public static void v(String tag, Object... objects) {
        printLog(1, tag, objects);
    }

    public static void d() {
        printLog(2, null, DEFAULT_MESSAGE);
    }

    public static void d(Object msg) {
        printLog(2, null, msg);
    }

    public static void d(String tag, Object... objects) {
        printLog(2, tag, objects);
    }

    public static void i() {
        printLog(3, null, DEFAULT_MESSAGE);
    }

    public static void i(Object msg) {
        printLog(3, null, msg);
    }

    public static void i(String tag, Object... objects) {
        printLog(3, tag, objects);
    }

    public static void w() {
        printLog(4, null, DEFAULT_MESSAGE);
    }

    public static void w(Object msg) {
        printLog(4, null, msg);
    }

    public static void w(String tag, Object... objects) {
        printLog(4, tag, objects);
    }

    public static void e() {
        printLog(5, null, DEFAULT_MESSAGE);
    }

    public static void e(Object msg) {
        printLog(5, null, msg);
    }

    public static void e(String tag, Object... objects) {
        printLog(5, tag, objects);
    }

    public static void a() {
        printLog(6, null, DEFAULT_MESSAGE);
    }

    public static void a(Object msg) {
        printLog(6, null, msg);
    }

    public static void a(String tag, Object... objects) {
        printLog(6, tag, objects);
    }

    public static void json(String jsonFormat) {
        printLog(7, null, jsonFormat);
    }

    public static void json(String tag, String jsonFormat) {
        printLog(7, tag, jsonFormat);
    }

    public static void xml(String xml) {
        printLog(8, null, xml);
    }

    public static void xml(String tag, String xml) {
        printLog(8, tag, xml);
    }

    public static void file(File targetDirectory, Object msg) {
        printFile(null, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, Object msg) {
        printFile(tag, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, String fileName, Object msg) {
        printFile(tag, targetDirectory, fileName, msg);
    }

    public static void debug() {
        printDebug(null, DEFAULT_MESSAGE);
    }

    public static void debug(Object msg) {
        printDebug(null, msg);
    }

    public static void debug(String tag, Object... objects) {
        printDebug(tag, objects);
    }

    public static void trace() {
        printStackTrace();
    }

    private static void printStackTrace() {
        if (IS_SHOW_LOG) {
            Throwable tr = new Throwable();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ThrowableExtension.printStackTrace(tr, pw);
            pw.flush();
            String[] traceString = sw.toString().split("\\n\\t");
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for (String trace : traceString) {
                if (!trace.contains("at com.socks.library.KLog")) {
                    sb.append(trace).append("\n");
                }
            }
            String[] contents = wrapperContent(4, null, sb.toString());
            BaseLog.printDefault(2, contents[0], contents[2] + contents[1]);
        }
    }

    private static void printLog(int type, String tagStr, Object... objects) {
        if (IS_SHOW_LOG) {
            String[] contents = wrapperContent(5, tagStr, objects);
            String tag = contents[0];
            String msg = contents[1];
            String headString = contents[2];
            switch (type) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    BaseLog.printDefault(type, tag, headString + msg);
                    return;
                case 7:
                    JsonLog.printJson(tag, msg, headString);
                    return;
                case 8:
                    XmlLog.printXml(tag, msg, headString);
                    return;
                default:
                    return;
            }
        }
    }

    private static void printDebug(String tagStr, Object... objects) {
        String[] contents = wrapperContent(5, tagStr, objects);
        BaseLog.printDefault(2, contents[0], contents[2] + contents[1]);
    }

    private static void printFile(String tagStr, File targetDirectory, String fileName, Object objectMsg) {
        if (IS_SHOW_LOG) {
            String[] contents = wrapperContent(5, tagStr, objectMsg);
            FileLog.printFile(contents[0], targetDirectory, fileName, contents[2], contents[1]);
        }
    }

    private static String[] wrapperContent(int stackTraceIndex, String tagStr, Object... objects) {
        String tag;
        StackTraceElement targetElement = Thread.currentThread().getStackTrace()[stackTraceIndex];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }
        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }
        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();
        if (lineNumber < 0) {
            lineNumber = 0;
        }
        if (tagStr == null) {
            tag = className;
        } else {
            tag = tagStr;
        }
        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tag)) {
            tag = TAG_DEFAULT;
        } else if (!mIsGlobalTagEmpty) {
            tag = mGlobalTag;
        }
        return new String[]{tag, objects == null ? NULL_TIPS : getObjectsString(objects), "[ (" + className + ":" + lineNumber + ")#" + methodName + " ] "};
    }

    private static String getObjectsString(Object... objects) {
        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n");
                } else {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        }
        Object object2 = objects[0];
        return object2 == null ? NULL : object2.toString();
    }
}
