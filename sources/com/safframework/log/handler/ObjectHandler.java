package com.safframework.log.handler;

import com.alibaba.fastjson.JSON;
import com.safframework.log.L;
import com.safframework.log.LoggerPrinter;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lcom/safframework/log/handler/ObjectHandler;", "Lcom/safframework/log/handler/BaseHandler;", "()V", "handle", "", "obj", "", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: ObjectHandler.kt */
public final class ObjectHandler extends BaseHandler {
    /* access modifiers changed from: protected */
    public boolean handle(@NotNull Object obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        String s = L.getMethodNames();
        String msg = obj.getClass().toString() + LoggerPrinter.INSTANCE.getBR() + "║ ";
        String message = new Regex("\n").replace(new JSONObject(JSON.toJSONString(obj)).toString(LoggerPrinter.INSTANCE.getJSON_INDENT()), "\n║ ");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {msg + message};
        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
        System.out.println(format);
        return true;
    }
}
