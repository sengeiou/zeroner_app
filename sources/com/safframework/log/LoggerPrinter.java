package com.safframework.log;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0019\u0010\u001a\u001a\u00020\u000f2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c¢\u0006\u0002\u0010\u001eR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bXD¢\u0006\u0002\n\u0000R\u0019\u0010\t\u001a\n \n*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0006R\u000e\u0010\f\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bXD¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000fXD¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0006R\u000e\u0010\u0014\u001a\u00020\bXD¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000fXD¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u000e\u0010\u0019\u001a\u00020\bXD¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/safframework/log/LoggerPrinter;", "", "()V", "BOTTOM_BORDER", "", "getBOTTOM_BORDER", "()Ljava/lang/String;", "BOTTOM_LEFT_CORNER", "", "BR", "kotlin.jvm.PlatformType", "getBR", "DOUBLE_DIVIDER", "HORIZONTAL_DOUBLE_LINE", "JSON_INDENT", "", "getJSON_INDENT", "()I", "MIDDLE_BORDER", "getMIDDLE_BORDER", "MIDDLE_CORNER", "MIN_STACK_OFFSET", "SINGLE_DIVIDER", "TOP_BORDER", "getTOP_BORDER", "TOP_LEFT_CORNER", "getStackOffset", "trace", "", "Ljava/lang/StackTraceElement;", "([Ljava/lang/StackTraceElement;)I", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: LoggerPrinter.kt */
public final class LoggerPrinter {
    @NotNull
    private static final String BOTTOM_BORDER = null;
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final String BR = null;
    private static final String DOUBLE_DIVIDER = "═════════════════════════════════════════════════";
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    public static final LoggerPrinter INSTANCE = null;
    private static final int JSON_INDENT = 2;
    @NotNull
    private static final String MIDDLE_BORDER = null;
    private static final char MIDDLE_CORNER = '╟';
    private static final int MIN_STACK_OFFSET = 3;
    private static final String SINGLE_DIVIDER = "─────────────────────────────────────────────────";
    @NotNull
    private static final String TOP_BORDER = null;
    private static final char TOP_LEFT_CORNER = '╔';

    static {
        new LoggerPrinter();
    }

    private LoggerPrinter() {
        INSTANCE = this;
        MIN_STACK_OFFSET = 3;
        TOP_LEFT_CORNER = TOP_LEFT_CORNER;
        BOTTOM_LEFT_CORNER = BOTTOM_LEFT_CORNER;
        MIDDLE_CORNER = MIDDLE_CORNER;
        HORIZONTAL_DOUBLE_LINE = HORIZONTAL_DOUBLE_LINE;
        DOUBLE_DIVIDER = DOUBLE_DIVIDER;
        SINGLE_DIVIDER = SINGLE_DIVIDER;
        StringBuilder sb = new StringBuilder();
        char c = TOP_LEFT_CORNER;
        TOP_BORDER = sb.append(String.valueOf(c) + DOUBLE_DIVIDER).append(DOUBLE_DIVIDER).toString();
        StringBuilder sb2 = new StringBuilder();
        char c2 = BOTTOM_LEFT_CORNER;
        BOTTOM_BORDER = sb2.append(String.valueOf(c2) + DOUBLE_DIVIDER).append(DOUBLE_DIVIDER).toString();
        StringBuilder sb3 = new StringBuilder();
        char c3 = MIDDLE_CORNER;
        MIDDLE_BORDER = sb3.append(String.valueOf(c3) + SINGLE_DIVIDER).append(SINGLE_DIVIDER).toString();
        BR = System.getProperty("line.separator");
        JSON_INDENT = 2;
    }

    @NotNull
    public final String getTOP_BORDER() {
        return TOP_BORDER;
    }

    @NotNull
    public final String getBOTTOM_BORDER() {
        return BOTTOM_BORDER;
    }

    @NotNull
    public final String getMIDDLE_BORDER() {
        return MIDDLE_BORDER;
    }

    public final String getBR() {
        return BR;
    }

    public final int getJSON_INDENT() {
        return JSON_INDENT;
    }

    public final int getStackOffset(@NotNull StackTraceElement[] trace) {
        Intrinsics.checkParameterIsNotNull(trace, "trace");
        for (int i = MIN_STACK_OFFSET; i < ((Object[]) trace).length; i++) {
            String name = trace[i].getClassName();
            if ((!Intrinsics.areEqual((Object) name, (Object) LoggerPrinter.class.getName())) && (!Intrinsics.areEqual((Object) name, (Object) L.class.getName()))) {
                return i - 1;
            }
        }
        return -1;
    }
}
