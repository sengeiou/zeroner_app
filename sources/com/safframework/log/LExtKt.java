package com.safframework.log;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0004\u001a\u0012\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0004\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\u0004\u001a\u0012\u0010\b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\b\u001a\u00020\u0001*\u00020\u0004¨\u0006\t"}, d2 = {"d", "", "", "text", "", "e", "i", "json", "w", "saf-log_release"}, k = 2, mv = {1, 1, 8})
/* compiled from: LExt.kt */
public final class LExtKt {
    public static final void e(@NotNull Object $receiver, @NotNull String text) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(text, "text");
        L.e(text);
    }

    public static final void w(@NotNull Object $receiver, @NotNull String text) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(text, "text");
        L.w(text);
    }

    public static final void i(@NotNull Object $receiver, @NotNull String text) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(text, "text");
        L.d(text);
    }

    public static final void d(@NotNull Object $receiver, @NotNull String text) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(text, "text");
        L.i(text);
    }

    public static final void json(@NotNull Object $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        L.json($receiver);
    }

    public static final void e(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        L.e($receiver);
    }

    public static final void w(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        L.w($receiver);
    }

    public static final void i(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        L.i($receiver);
    }

    public static final void d(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        L.d($receiver);
    }

    public static final void json(@NotNull String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        L.json($receiver);
    }
}
