package com.safframework.log;

import android.util.Log;
import com.alibaba.android.arouter.utils.Consts;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.safframework.log.handler.BaseHandler;
import com.safframework.log.handler.BundleHandler;
import com.safframework.log.handler.CollectionHandler;
import com.safframework.log.handler.IntentHandler;
import com.safframework.log.handler.MapHandler;
import com.safframework.log.handler.ObjectHandler;
import com.safframework.log.handler.ReferenceHandler;
import com.safframework.log.handler.StringHandler;
import com.safframework.log.handler.ThrowableHandler;
import com.safframework.log.handler.UriHandler;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001%B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0006H\u0007J\u0018\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0012\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004H\u0007J\u001c\u0010\u0016\u001a\u00020\u00172\b\u0010\u0019\u001a\u0004\u0018\u00010\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004H\u0007J\u001a\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0012\u0010\u001c\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004H\u0007J\u001c\u0010\u001c\u001a\u00020\u00172\b\u0010\u0019\u001a\u0004\u0018\u00010\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004H\u0007J\u001a\u0010\u001c\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\b\u0010\u001d\u001a\u00020\u0004H\u0007J\u0012\u0010\n\u001a\u00020\u00002\b\u0010\n\u001a\u0004\u0018\u00010\u0004H\u0007J\u0012\u0010\u001e\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004H\u0007J\u001c\u0010\u001e\u001a\u00020\u00172\b\u0010\u0019\u001a\u0004\u0018\u00010\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004H\u0007J\u001a\u0010\u001e\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0014\u0010\u001f\u001a\u00020\u00002\n\u0010 \u001a\u0006\u0012\u0002\b\u00030!H\u0007J\u0010\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u0004H\u0007J\u0012\u0010\"\u001a\u00020\u00172\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u0007J\u0012\u0010$\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004H\u0007J\u001c\u0010$\u001a\u00020\u00172\b\u0010\u0019\u001a\u0004\u0018\u00010\u00042\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004H\u0007J\u001a\u0010$\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001a\u001a\u00020\u001bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\u00060\bj\b\u0012\u0004\u0012\u00020\u0006`\tX\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u0002\n\u0000R$\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\r\u0010\u0002\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006&"}, d2 = {"Lcom/safframework/log/L;", "", "()V", "TAG", "", "firstHandler", "Lcom/safframework/log/handler/BaseHandler;", "handlers", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "header", "logLevel", "Lcom/safframework/log/L$LogLevel;", "logLevel$annotations", "getLogLevel", "()Lcom/safframework/log/L$LogLevel;", "setLogLevel", "(Lcom/safframework/log/L$LogLevel;)V", "addCustomerHandler", "handler", "index", "", "d", "", "msg", "tag", "tr", "", "e", "getMethodNames", "i", "init", "clazz", "Ljava/lang/Class;", "json", "obj", "w", "LogLevel", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: L.kt */
public final class L {
    public static final L INSTANCE = null;
    private static String TAG;
    private static BaseHandler firstHandler;
    private static final ArrayList<BaseHandler> handlers = null;
    private static String header;
    @NotNull
    private static LogLevel logLevel;

    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\b\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lcom/safframework/log/L$LogLevel;", "", "(Ljava/lang/String;I)V", "value", "", "getValue", "()I", "ERROR", "WARN", "INFO", "DEBUG", "saf-log_release"}, k = 1, mv = {1, 1, 8})
    /* compiled from: L.kt */
    public enum LogLevel {
        ;

        @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/safframework/log/L$LogLevel$DEBUG;", "Lcom/safframework/log/L$LogLevel;", "(Ljava/lang/String;I)V", "value", "", "getValue", "()I", "saf-log_release"}, k = 1, mv = {1, 1, 8})
        /* compiled from: L.kt */
        static final class DEBUG extends LogLevel {
            DEBUG(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1);
            }

            public int getValue() {
                return 3;
            }
        }

        @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/safframework/log/L$LogLevel$ERROR;", "Lcom/safframework/log/L$LogLevel;", "(Ljava/lang/String;I)V", "value", "", "getValue", "()I", "saf-log_release"}, k = 1, mv = {1, 1, 8})
        /* compiled from: L.kt */
        static final class ERROR extends LogLevel {
            ERROR(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1);
            }

            public int getValue() {
                return 0;
            }
        }

        @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/safframework/log/L$LogLevel$INFO;", "Lcom/safframework/log/L$LogLevel;", "(Ljava/lang/String;I)V", "value", "", "getValue", "()I", "saf-log_release"}, k = 1, mv = {1, 1, 8})
        /* compiled from: L.kt */
        static final class INFO extends LogLevel {
            INFO(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1);
            }

            public int getValue() {
                return 2;
            }
        }

        @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0001\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/safframework/log/L$LogLevel$WARN;", "Lcom/safframework/log/L$LogLevel;", "(Ljava/lang/String;I)V", "value", "", "getValue", "()I", "saf-log_release"}, k = 1, mv = {1, 1, 8})
        /* compiled from: L.kt */
        static final class WARN extends LogLevel {
            WARN(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1);
            }

            public int getValue() {
                return 1;
            }
        }

        public abstract int getValue();
    }

    @JvmStatic
    public static /* synthetic */ void logLevel$annotations() {
    }

    static {
        new L();
    }

    private L() {
        INSTANCE = this;
        TAG = "SAF_L";
        header = "";
        handlers = new ArrayList<>();
        handlers.add(new StringHandler());
        handlers.add(new CollectionHandler());
        handlers.add(new MapHandler());
        handlers.add(new BundleHandler());
        handlers.add(new IntentHandler());
        handlers.add(new UriHandler());
        handlers.add(new ThrowableHandler());
        handlers.add(new ReferenceHandler());
        handlers.add(new ObjectHandler());
        int size = handlers.size() - 1;
        if (0 <= size) {
            int i = 0;
            while (true) {
                if (i > 0) {
                    BaseHandler baseHandler = (BaseHandler) handlers.get(i - 1);
                    Object obj = handlers.get(i);
                    Intrinsics.checkExpressionValueIsNotNull(obj, "handlers[i]");
                    baseHandler.setNextHandler((BaseHandler) obj);
                }
                if (i == size) {
                    break;
                }
                i++;
            }
        }
        Object obj2 = handlers.get(0);
        Intrinsics.checkExpressionValueIsNotNull(obj2, "handlers[0]");
        firstHandler = (BaseHandler) obj2;
        logLevel = LogLevel.DEBUG;
    }

    @NotNull
    public static final LogLevel getLogLevel() {
        return logLevel;
    }

    public static final void setLogLevel(@NotNull LogLevel logLevel2) {
        Intrinsics.checkParameterIsNotNull(logLevel2, "<set-?>");
        logLevel = logLevel2;
    }

    @JvmStatic
    @NotNull
    public static final L init(@NotNull Class<?> clazz) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        String simpleName = clazz.getSimpleName();
        Intrinsics.checkExpressionValueIsNotNull(simpleName, "clazz.simpleName");
        TAG = simpleName;
        return INSTANCE;
    }

    @JvmStatic
    @NotNull
    public static final L init(@NotNull String tag) {
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        TAG = tag;
        return INSTANCE;
    }

    @JvmStatic
    @NotNull
    public static final L header(@Nullable String header2) {
        header = header2;
        return INSTANCE;
    }

    @JvmStatic
    @NotNull
    public static final L addCustomerHandler(@NotNull BaseHandler handler) {
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        return addCustomerHandler(handler, handlers.size() - 1);
    }

    @JvmStatic
    @NotNull
    public static final L addCustomerHandler(@NotNull BaseHandler handler, int index) {
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        handlers.add(index, handler);
        int i = 0;
        int size = handlers.size() - 1;
        if (0 <= size) {
            while (true) {
                if (i > 0) {
                    BaseHandler baseHandler = (BaseHandler) handlers.get(i - 1);
                    Object obj = handlers.get(i);
                    Intrinsics.checkExpressionValueIsNotNull(obj, "handlers[i]");
                    baseHandler.setNextHandler((BaseHandler) obj);
                }
                if (i == size) {
                    break;
                }
                i++;
            }
        }
        return INSTANCE;
    }

    @JvmStatic
    public static final void e(@Nullable String msg) {
        boolean z;
        if (LogLevel.ERROR.getValue() <= logLevel.getValue() && msg != null) {
            if (msg.length() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                String s = getMethodNames();
                if (StringsKt.contains$default((CharSequence) msg, (CharSequence) "\n", false, 2, (Object) null)) {
                    String str = TAG;
                    StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                    Object[] objArr = {new Regex("\n").replace((CharSequence) msg, "\n║ ")};
                    String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
                    Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
                    Log.e(str, format);
                    return;
                }
                String str2 = TAG;
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                Object[] objArr2 = {msg};
                String format2 = String.format(s, Arrays.copyOf(objArr2, objArr2.length));
                Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(format, *args)");
                Log.e(str2, format2);
            }
        }
    }

    @JvmStatic
    public static final void e(@Nullable String tag, @Nullable String msg) {
        boolean z;
        if (LogLevel.ERROR.getValue() <= logLevel.getValue() && tag != null) {
            if (tag.length() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z && msg != null) {
                if (msg.length() > 0) {
                    String s = getMethodNames();
                    if (StringsKt.contains$default((CharSequence) msg, (CharSequence) "\n", false, 2, (Object) null)) {
                        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                        Object[] objArr = {new Regex("\n").replace((CharSequence) msg, "\n║ ")};
                        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
                        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
                        Log.e(tag, format);
                        return;
                    }
                    StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                    Object[] objArr2 = {msg};
                    String format2 = String.format(s, Arrays.copyOf(objArr2, objArr2.length));
                    Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(format, *args)");
                    Log.e(tag, format2);
                }
            }
        }
    }

    @JvmStatic
    public static final void e(@Nullable String msg, @NotNull Throwable tr) {
        Intrinsics.checkParameterIsNotNull(tr, "tr");
        if (LogLevel.ERROR.getValue() <= logLevel.getValue() && msg != null) {
            if (msg.length() > 0) {
                Log.e(TAG, msg, tr);
            }
        }
    }

    @JvmStatic
    public static final void w(@Nullable String msg) {
        boolean z;
        if (LogLevel.WARN.getValue() <= logLevel.getValue() && msg != null) {
            if (msg.length() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                String s = getMethodNames();
                if (StringsKt.contains$default((CharSequence) msg, (CharSequence) "\n", false, 2, (Object) null)) {
                    String str = TAG;
                    StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                    Object[] objArr = {new Regex("\n").replace((CharSequence) msg, "\n║ ")};
                    String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
                    Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
                    Log.w(str, format);
                    return;
                }
                String str2 = TAG;
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                Object[] objArr2 = {msg};
                String format2 = String.format(s, Arrays.copyOf(objArr2, objArr2.length));
                Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(format, *args)");
                Log.w(str2, format2);
            }
        }
    }

    @JvmStatic
    public static final void w(@Nullable String tag, @Nullable String msg) {
        boolean z;
        if (LogLevel.WARN.getValue() <= logLevel.getValue() && tag != null) {
            if (tag.length() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z && msg != null) {
                if (msg.length() > 0) {
                    String s = getMethodNames();
                    if (StringsKt.contains$default((CharSequence) msg, (CharSequence) "\n", false, 2, (Object) null)) {
                        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                        Object[] objArr = {new Regex("\n").replace((CharSequence) msg, "\n║ ")};
                        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
                        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
                        Log.w(tag, format);
                        return;
                    }
                    StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                    Object[] objArr2 = {msg};
                    String format2 = String.format(s, Arrays.copyOf(objArr2, objArr2.length));
                    Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(format, *args)");
                    Log.w(tag, format2);
                }
            }
        }
    }

    @JvmStatic
    public static final void w(@Nullable String msg, @NotNull Throwable tr) {
        Intrinsics.checkParameterIsNotNull(tr, "tr");
        if (LogLevel.WARN.getValue() <= logLevel.getValue() && msg != null) {
            if (msg.length() > 0) {
                Log.w(TAG, msg, tr);
            }
        }
    }

    @JvmStatic
    public static final void i(@Nullable String msg) {
        boolean z;
        if (LogLevel.INFO.getValue() <= logLevel.getValue() && msg != null) {
            if (msg.length() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                String s = getMethodNames();
                if (StringsKt.contains$default((CharSequence) msg, (CharSequence) "\n", false, 2, (Object) null)) {
                    String str = TAG;
                    StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                    Object[] objArr = {new Regex("\n").replace((CharSequence) msg, "\n║ ")};
                    String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
                    Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
                    Log.i(str, format);
                    return;
                }
                String str2 = TAG;
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                Object[] objArr2 = {msg};
                String format2 = String.format(s, Arrays.copyOf(objArr2, objArr2.length));
                Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(format, *args)");
                Log.i(str2, format2);
            }
        }
    }

    @JvmStatic
    public static final void i(@Nullable String tag, @Nullable String msg) {
        boolean z;
        if (LogLevel.INFO.getValue() <= logLevel.getValue() && tag != null) {
            if (tag.length() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z && msg != null) {
                if (msg.length() > 0) {
                    String s = getMethodNames();
                    if (StringsKt.contains$default((CharSequence) msg, (CharSequence) "\n", false, 2, (Object) null)) {
                        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                        Object[] objArr = {new Regex("\n").replace((CharSequence) msg, "\n║ ")};
                        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
                        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
                        Log.i(tag, format);
                        return;
                    }
                    StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                    Object[] objArr2 = {msg};
                    String format2 = String.format(s, Arrays.copyOf(objArr2, objArr2.length));
                    Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(format, *args)");
                    Log.i(tag, format2);
                }
            }
        }
    }

    @JvmStatic
    public static final void i(@Nullable String msg, @NotNull Throwable tr) {
        Intrinsics.checkParameterIsNotNull(tr, "tr");
        if (LogLevel.INFO.getValue() <= logLevel.getValue() && msg != null) {
            if (msg.length() > 0) {
                Log.i(TAG, msg, tr);
            }
        }
    }

    @JvmStatic
    public static final void d(@Nullable String msg) {
        boolean z;
        if (LogLevel.DEBUG.getValue() <= logLevel.getValue() && msg != null) {
            if (msg.length() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                String s = getMethodNames();
                if (StringsKt.contains$default((CharSequence) msg, (CharSequence) "\n", false, 2, (Object) null)) {
                    String str = TAG;
                    StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                    Object[] objArr = {new Regex("\n").replace((CharSequence) msg, "\n║ ")};
                    String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
                    Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
                    Log.d(str, format);
                    return;
                }
                String str2 = TAG;
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                Object[] objArr2 = {msg};
                String format2 = String.format(s, Arrays.copyOf(objArr2, objArr2.length));
                Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(format, *args)");
                Log.d(str2, format2);
            }
        }
    }

    @JvmStatic
    public static final void d(@Nullable String tag, @Nullable String msg) {
        boolean z;
        if (LogLevel.DEBUG.getValue() <= logLevel.getValue() && tag != null) {
            if (tag.length() > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z && msg != null) {
                if (msg.length() > 0) {
                    String s = getMethodNames();
                    if (StringsKt.contains$default((CharSequence) msg, (CharSequence) "\n", false, 2, (Object) null)) {
                        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                        Object[] objArr = {new Regex("\n").replace((CharSequence) msg, "\n║ ")};
                        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
                        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
                        Log.d(tag, format);
                        return;
                    }
                    StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                    Object[] objArr2 = {msg};
                    String format2 = String.format(s, Arrays.copyOf(objArr2, objArr2.length));
                    Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(format, *args)");
                    Log.d(tag, format2);
                }
            }
        }
    }

    @JvmStatic
    public static final void d(@Nullable String msg, @NotNull Throwable tr) {
        Intrinsics.checkParameterIsNotNull(tr, "tr");
        if (LogLevel.DEBUG.getValue() <= logLevel.getValue() && msg != null) {
            if (msg.length() > 0) {
                Log.d(TAG, msg, tr);
            }
        }
    }

    @JvmStatic
    public static final void json(@Nullable Object obj) {
        if (obj == null) {
            d("object is null");
        } else {
            firstHandler.handleObject(obj);
        }
    }

    @JvmStatic
    @NotNull
    public static final String getMethodNames() {
        StackTraceElement[] sElements = Thread.currentThread().getStackTrace();
        LoggerPrinter loggerPrinter = LoggerPrinter.INSTANCE;
        Intrinsics.checkExpressionValueIsNotNull(sElements, "sElements");
        int stackOffset = loggerPrinter.getStackOffset(sElements) + 1;
        StringBuilder builder = new StringBuilder();
        builder.append(LoggerPrinter.INSTANCE.getTOP_BORDER()).append(LoggerPrinter.INSTANCE.getBR());
        if (header != null) {
            String str = header;
            if (str == null) {
                Intrinsics.throwNpe();
            }
            if (str.length() > 0) {
                builder.append("║ Header: " + header).append(LoggerPrinter.INSTANCE.getBR()).append(LoggerPrinter.INSTANCE.getMIDDLE_BORDER()).append(LoggerPrinter.INSTANCE.getBR());
            }
        }
        builder.append("║ Thread: " + Thread.currentThread().getName()).append(LoggerPrinter.INSTANCE.getBR()).append(LoggerPrinter.INSTANCE.getMIDDLE_BORDER()).append(LoggerPrinter.INSTANCE.getBR()).append("║ ").append(sElements[stackOffset].getClassName()).append(Consts.DOT).append(sElements[stackOffset].getMethodName()).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(" (").append(sElements[stackOffset].getFileName()).append(":").append(sElements[stackOffset].getLineNumber()).append(")").append(LoggerPrinter.INSTANCE.getBR()).append(LoggerPrinter.INSTANCE.getMIDDLE_BORDER()).append(LoggerPrinter.INSTANCE.getBR()).append("║ ").append("%s").append(LoggerPrinter.INSTANCE.getBR()).append(LoggerPrinter.INSTANCE.getBOTTOM_BORDER()).append(LoggerPrinter.INSTANCE.getBR());
        String sb = builder.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb, "builder.toString()");
        return sb;
    }
}
