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
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u00012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0014\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016¨\u0006\f"}, d2 = {"Lcom/safframework/log/handler/CollectionHandler;", "Lcom/safframework/log/handler/BaseHandler;", "Lcom/safframework/log/parser/Parser;", "", "()V", "handle", "", "obj", "", "parseString", "", "collection", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: CollectionHandler.kt */
public final class CollectionHandler extends BaseHandler implements Parser<Collection<?>> {
    /* access modifiers changed from: protected */
    public boolean handle(@NotNull Object obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        if (!(obj instanceof Collection)) {
            return false;
        }
        if (Utils.isPrimitiveType(CollectionsKt.firstOrNull((Iterable) obj))) {
            Class simpleName = ((Collection) obj).getClass();
            String msg = "%s size = %d" + LoggerPrinter.INSTANCE.getBR();
            StringBuilder sb = new StringBuilder();
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Object[] objArr = {simpleName, Integer.valueOf(((Collection) obj).size())};
            String format = String.format(msg, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
            String msg2 = sb.append(format).append("║ ").toString();
            String s = L.getMethodNames();
            StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
            Object[] objArr2 = {msg2 + obj.toString()};
            String format2 = String.format(s, Arrays.copyOf(objArr2, objArr2.length));
            Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(format, *args)");
            System.out.println(format2);
            return true;
        }
        String s2 = L.getMethodNames();
        StringCompanionObject stringCompanionObject3 = StringCompanionObject.INSTANCE;
        Object[] objArr3 = {parseString((Collection) obj)};
        String format3 = String.format(s2, Arrays.copyOf(objArr3, objArr3.length));
        Intrinsics.checkExpressionValueIsNotNull(format3, "java.lang.String.format(format, *args)");
        System.out.println(format3);
        return true;
    }

    @NotNull
    public String parseString(@NotNull Collection<?> collection) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(collection, "collection");
        JSONArray jsonArray = new JSONArray();
        Class simpleName = collection.getClass();
        String msg = "%s size = %d" + LoggerPrinter.INSTANCE.getBR();
        StringBuilder sb = new StringBuilder();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {simpleName, Integer.valueOf(collection.size())};
        String format = String.format(msg, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
        String msg2 = sb.append(format).append("║ ").toString();
        Iterable<Object> $receiver$iv = collection;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            try {
                obj = jsonArray.put(new JSONObject(JSON.toJSONString(item$iv$iv)));
            } catch (JSONException e) {
                L.e("Invalid Json");
                obj = Unit.INSTANCE;
            }
            destination$iv$iv.add(obj);
        }
        Collection destination$iv$iv2 = (List) destination$iv$iv;
        return msg2 + new Regex("\n").replace(jsonArray.toString(LoggerPrinter.INSTANCE.getJSON_INDENT()), "\n║ ");
    }
}
