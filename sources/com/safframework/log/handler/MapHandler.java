package com.safframework.log.handler;

import com.alibaba.fastjson.JSON;
import com.safframework.log.L;
import com.safframework.log.LoggerPrinter;
import com.safframework.log.parser.Parser;
import com.safframework.log.utils.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0018\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003H\u0016¨\u0006\f"}, d2 = {"Lcom/safframework/log/handler/MapHandler;", "Lcom/safframework/log/handler/BaseHandler;", "Lcom/safframework/log/parser/Parser;", "", "()V", "handle", "", "obj", "", "parseString", "", "map", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: MapHandler.kt */
public final class MapHandler extends BaseHandler implements Parser<Map<?, ?>> {
    /* access modifiers changed from: protected */
    public boolean handle(@NotNull Object obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        if (!(obj instanceof Map)) {
            return false;
        }
        String s = L.getMethodNames();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {parseString((Map) obj)};
        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
        System.out.println(format);
        return true;
    }

    @NotNull
    public String parseString(@NotNull Map<?, ?> map) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(map, "map");
        Set keys = map.keySet();
        boolean isPrimitiveType = Utils.isPrimitiveType(CollectionsKt.firstOrNull((Iterable) map.values()));
        String msg = map.getClass().toString() + LoggerPrinter.INSTANCE.getBR() + "║ ";
        JSONObject jsonObject = new JSONObject();
        Iterable $receiver$iv = keys;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            if (isPrimitiveType) {
                try {
                    obj = jsonObject.put(String.valueOf(item$iv$iv), map.get(item$iv$iv));
                } catch (JSONException e) {
                    L.e("Invalid Json");
                    obj = Unit.INSTANCE;
                }
            } else {
                obj = jsonObject.put(String.valueOf(item$iv$iv), new JSONObject(JSON.toJSONString(map.get(item$iv$iv))));
            }
            destination$iv$iv.add(obj);
        }
        Collection destination$iv$iv2 = (List) destination$iv$iv;
        return msg + new Regex("\n").replace(jsonObject.toString(LoggerPrinter.INSTANCE.getJSON_INDENT()), "\n║ ");
    }
}
