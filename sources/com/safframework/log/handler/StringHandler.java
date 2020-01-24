package com.safframework.log.handler;

import com.safframework.log.L;
import com.safframework.log.LoggerPrinter;
import com.safframework.log.parser.Parser;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"Lcom/safframework/log/handler/StringHandler;", "Lcom/safframework/log/handler/BaseHandler;", "Lcom/safframework/log/parser/Parser;", "", "()V", "handle", "", "obj", "", "parseString", "json", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: StringHandler.kt */
public final class StringHandler extends BaseHandler implements Parser<String> {
    /* access modifiers changed from: protected */
    public boolean handle(@NotNull Object obj) {
        int index$iv$iv;
        boolean match$iv$iv;
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        if (!(obj instanceof String)) {
            return false;
        }
        CharSequence $receiver$iv$iv = (String) obj;
        int startIndex$iv$iv = 0;
        int endIndex$iv$iv = $receiver$iv$iv.length() - 1;
        boolean startFound$iv$iv = false;
        while (startIndex$iv$iv <= endIndex$iv$iv) {
            if (!startFound$iv$iv) {
                index$iv$iv = startIndex$iv$iv;
            } else {
                index$iv$iv = endIndex$iv$iv;
            }
            if ($receiver$iv$iv.charAt(index$iv$iv) <= ' ') {
                match$iv$iv = true;
            } else {
                match$iv$iv = false;
            }
            if (!startFound$iv$iv) {
                if (!match$iv$iv) {
                    startFound$iv$iv = true;
                } else {
                    startIndex$iv$iv++;
                }
            } else if (!match$iv$iv) {
                break;
            } else {
                endIndex$iv$iv--;
            }
        }
        String json = $receiver$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1).toString();
        String s = L.getMethodNames();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {parseString(json)};
        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
        System.out.println(format);
        return true;
    }

    @NotNull
    public String parseString(@NotNull String json) {
        String message;
        Intrinsics.checkParameterIsNotNull(json, "json");
        String message2 = "";
        try {
            if (StringsKt.startsWith$default(json, "{", false, 2, null)) {
                message = new JSONObject(json).toString(LoggerPrinter.INSTANCE.getJSON_INDENT());
                Intrinsics.checkExpressionValueIsNotNull(message, "jsonObject.toString(LoggerPrinter.JSON_INDENT)");
                try {
                    return new Regex("\n").replace((CharSequence) message, "\n║ ");
                } catch (JSONException e) {
                    String str = message;
                }
            } else if (!StringsKt.startsWith$default(json, "[", false, 2, null)) {
                return message2;
            } else {
                message = new JSONArray(json).toString(LoggerPrinter.INSTANCE.getJSON_INDENT());
                Intrinsics.checkExpressionValueIsNotNull(message, "jsonArray.toString(LoggerPrinter.JSON_INDENT)");
                return new Regex("\n").replace((CharSequence) message, "\n║ ");
            }
        } catch (JSONException e2) {
            L.e("Invalid Json: " + json);
            return "";
        }
    }
}
