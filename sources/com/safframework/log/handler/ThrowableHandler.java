package com.safframework.log.handler;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.safframework.log.L;
import com.safframework.log.parser.Parser;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¨\u0006\f"}, d2 = {"Lcom/safframework/log/handler/ThrowableHandler;", "Lcom/safframework/log/handler/BaseHandler;", "Lcom/safframework/log/parser/Parser;", "", "()V", "handle", "", "obj", "", "parseString", "", "throwable", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: ThrowableHandler.kt */
public final class ThrowableHandler extends BaseHandler implements Parser<Throwable> {
    /* access modifiers changed from: protected */
    public boolean handle(@NotNull Object obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        if (!(obj instanceof Throwable)) {
            return false;
        }
        String s = L.getMethodNames();
        PrintStream printStream = System.err;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {parseString((Throwable) obj)};
        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
        printStream.println(format);
        return true;
    }

    @NotNull
    public String parseString(@NotNull Throwable throwable) {
        Intrinsics.checkParameterIsNotNull(throwable, "throwable");
        StringWriter sw = new StringWriter(256);
        PrintWriter pw = new PrintWriter(sw, false);
        ThrowableExtension.printStackTrace(throwable, pw);
        pw.flush();
        String message = new Regex("\n").replace(sw.toString(), "\n║ ");
        Intrinsics.checkExpressionValueIsNotNull(message, "message");
        return message;
    }
}
