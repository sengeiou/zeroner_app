package com.safframework.log.handler;

import com.safframework.log.L;
import com.safframework.log.parser.Parser;
import java.lang.ref.Reference;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u00012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0014\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016¨\u0006\f"}, d2 = {"Lcom/safframework/log/handler/ReferenceHandler;", "Lcom/safframework/log/handler/BaseHandler;", "Lcom/safframework/log/parser/Parser;", "Ljava/lang/ref/Reference;", "()V", "handle", "", "obj", "", "parseString", "", "reference", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: ReferenceHandler.kt */
public final class ReferenceHandler extends BaseHandler implements Parser<Reference<?>> {
    /* access modifiers changed from: protected */
    public boolean handle(@NotNull Object obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        if (!(obj instanceof Reference)) {
            return false;
        }
        String s = L.getMethodNames();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {parseString((Reference) obj)};
        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
        System.out.println(format);
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0076  */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String parseString(@org.jetbrains.annotations.NotNull java.lang.ref.Reference<?> r8) {
        /*
            r7 = this;
            java.lang.String r5 = "reference"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r8, r5)
            java.lang.Object r0 = r8.get()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.Class r6 = r8.getClass()
            java.lang.String r6 = r6.getCanonicalName()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = "<"
            java.lang.StringBuilder r6 = r5.append(r6)
            if (r0 == 0) goto L_0x0074
            java.lang.Class r5 = r0.getClass()
            if (r5 == 0) goto L_0x0074
            java.lang.String r5 = r5.getSimpleName()
        L_0x002e:
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.String r6 = ">"
            java.lang.StringBuilder r5 = r5.append(r6)
            com.safframework.log.LoggerPrinter r6 = com.safframework.log.LoggerPrinter.INSTANCE
            java.lang.String r6 = r6.getBR()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = "║ "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r4 = r5.toString()
            boolean r1 = com.safframework.log.utils.Utils.isPrimitiveType(r0)
            if (r1 == 0) goto L_0x0076
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r4)
            java.lang.String r6 = "{"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = "}"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r4 = r5.toString()
        L_0x0073:
            return r4
        L_0x0074:
            r5 = 0
            goto L_0x002e
        L_0x0076:
            org.json.JSONObject r2 = new org.json.JSONObject
            java.lang.String r5 = com.alibaba.fastjson.JSON.toJSONString(r0)
            r2.<init>(r5)
            com.safframework.log.LoggerPrinter r5 = com.safframework.log.LoggerPrinter.INSTANCE
            int r5 = r5.getJSON_INDENT()
            java.lang.String r3 = r2.toString(r5)
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3
            java.lang.String r5 = "\n"
            kotlin.text.Regex r6 = new kotlin.text.Regex
            r6.<init>(r5)
            java.lang.String r5 = "\n║ "
            java.lang.String r3 = r6.replace(r3, r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r4)
            java.lang.StringBuilder r5 = r5.append(r3)
            java.lang.String r4 = r5.toString()
            goto L_0x0073
        */
        throw new UnsupportedOperationException("Method not decompiled: com.safframework.log.handler.ReferenceHandler.parseString(java.lang.ref.Reference):java.lang.String");
    }
}
