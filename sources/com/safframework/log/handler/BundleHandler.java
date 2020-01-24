package com.safframework.log.handler;

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

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¨\u0006\f"}, d2 = {"Lcom/safframework/log/handler/BundleHandler;", "Lcom/safframework/log/handler/BaseHandler;", "Lcom/safframework/log/parser/Parser;", "Landroid/os/Bundle;", "()V", "handle", "", "obj", "", "parseString", "", "bundle", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: BundleHandler.kt */
public final class BundleHandler extends BaseHandler implements Parser<Bundle> {
    /* access modifiers changed from: protected */
    public boolean handle(@NotNull Object obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        if (!(obj instanceof Bundle)) {
            return false;
        }
        String s = L.getMethodNames();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {parseString((Bundle) obj)};
        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
        System.out.println(format);
        return true;
    }

    @NotNull
    public String parseString(@NotNull Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(bundle, "bundle");
        String msg = bundle.getClass().toString() + LoggerPrinter.INSTANCE.getBR() + "║ ";
        JSONObject jsonObject = new JSONObject();
        for (String key : bundle.keySet()) {
            if (Utils.isPrimitiveType(bundle.get(key))) {
                try {
                    jsonObject.put(key.toString(), bundle.get(key));
                } catch (JSONException e) {
                    L.e("Invalid Json");
                }
            } else {
                jsonObject.put(key.toString(), new JSONObject(JSON.toJSONString(bundle.get(key))));
            }
        }
        return msg + new Regex("\n").replace(jsonObject.toString(LoggerPrinter.INSTANCE.getJSON_INDENT()), "\n║ ");
    }
}
