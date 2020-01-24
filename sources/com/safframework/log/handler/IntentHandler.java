package com.safframework.log.handler;

import android.content.Intent;
import android.os.Bundle;
import com.alibaba.fastjson.JSON;
import com.safframework.log.L;
import com.safframework.log.LoggerPrinter;
import com.safframework.log.parser.Parser;
import com.safframework.log.utils.Utils;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u0003H\u0016¨\u0006\u000f"}, d2 = {"Lcom/safframework/log/handler/IntentHandler;", "Lcom/safframework/log/handler/BaseHandler;", "Lcom/safframework/log/parser/Parser;", "Landroid/content/Intent;", "()V", "handle", "", "obj", "", "parseBundleString", "", "extras", "Landroid/os/Bundle;", "parseString", "intent", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: IntentHandler.kt */
public final class IntentHandler extends BaseHandler implements Parser<Intent> {
    /* access modifiers changed from: protected */
    public boolean handle(@NotNull Object obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        if (!(obj instanceof Intent)) {
            return false;
        }
        String s = L.getMethodNames();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {parseString((Intent) obj)};
        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
        System.out.println(format);
        return true;
    }

    @NotNull
    public String parseString(@NotNull Intent intent) {
        Intrinsics.checkParameterIsNotNull(intent, "intent");
        String msg = intent.getClass().toString() + LoggerPrinter.INSTANCE.getBR() + "║ ";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Scheme", intent.getScheme());
        jsonObject.put("Action", intent.getAction());
        jsonObject.put("DataString", intent.getDataString());
        jsonObject.put("Type", intent.getType());
        jsonObject.put("Package", intent.getPackage());
        jsonObject.put("ComponentInfo", intent.getComponent());
        jsonObject.put("Categories", intent.getCategories());
        if (intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            Intrinsics.checkExpressionValueIsNotNull(extras, "intent.extras");
            jsonObject.put("Extras", new JSONObject(parseBundleString(extras)));
        }
        return msg + new Regex("\n").replace(jsonObject.toString(LoggerPrinter.INSTANCE.getJSON_INDENT()), "\n║ ");
    }

    private final String parseBundleString(Bundle extras) {
        JSONObject jsonObject = new JSONObject();
        for (String key : extras.keySet()) {
            if (Utils.isPrimitiveType(extras.get(key))) {
                try {
                    jsonObject.put(key.toString(), extras.get(key));
                } catch (JSONException e) {
                    L.e("Invalid Json");
                }
            } else {
                jsonObject.put(key.toString(), new JSONObject(JSON.toJSONString(extras.get(key))));
            }
        }
        String jSONObject = jsonObject.toString(LoggerPrinter.INSTANCE.getJSON_INDENT());
        Intrinsics.checkExpressionValueIsNotNull(jSONObject, "jsonObject.toString(LoggerPrinter.JSON_INDENT)");
        return jSONObject;
    }
}
