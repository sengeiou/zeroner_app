package com.safframework.log.handler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0001H$J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\u0001J\u000e\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0000R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0000X\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/safframework/log/handler/BaseHandler;", "", "()V", "nextHandler", "handle", "", "obj", "handleObject", "", "setNextHandler", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: BaseHandler.kt */
public abstract class BaseHandler {
    private BaseHandler nextHandler;

    /* access modifiers changed from: protected */
    public abstract boolean handle(@NotNull Object obj);

    public final void handleObject(@NotNull Object obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        if (!handle(obj) && this.nextHandler != null) {
            BaseHandler baseHandler = this.nextHandler;
            if (baseHandler == null) {
                Intrinsics.throwNpe();
            }
            baseHandler.handleObject(obj);
        }
    }

    public final void setNextHandler(@NotNull BaseHandler nextHandler2) {
        Intrinsics.checkParameterIsNotNull(nextHandler2, "nextHandler");
        this.nextHandler = nextHandler2;
    }
}
