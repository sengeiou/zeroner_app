package com.safframework.log.handler;

import android.net.Uri;
import com.google.common.net.HttpHeaders;
import com.safframework.log.L;
import com.safframework.log.LoggerPrinter;
import com.safframework.log.parser.Parser;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¨\u0006\f"}, d2 = {"Lcom/safframework/log/handler/UriHandler;", "Lcom/safframework/log/handler/BaseHandler;", "Lcom/safframework/log/parser/Parser;", "Landroid/net/Uri;", "()V", "handle", "", "obj", "", "parseString", "", "uri", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: UriHandler.kt */
public final class UriHandler extends BaseHandler implements Parser<Uri> {
    /* access modifiers changed from: protected */
    public boolean handle(@NotNull Object obj) {
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        if (!(obj instanceof Uri)) {
            return false;
        }
        String s = L.getMethodNames();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {parseString((Uri) obj)};
        String format = String.format(s, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
        System.out.println(format);
        return true;
    }

    @NotNull
    public String parseString(@NotNull Uri uri) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        String msg = uri.getClass().toString() + LoggerPrinter.INSTANCE.getBR() + "║ ";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Scheme", uri.getScheme());
        jsonObject.put(HttpHeaders.HOST, uri.getHost());
        jsonObject.put("Port", uri.getPort());
        jsonObject.put("Path", uri.getPath());
        jsonObject.put("Query", uri.getQuery());
        jsonObject.put("Fragment", uri.getFragment());
        return msg + new Regex("\n").replace(jsonObject.toString(LoggerPrinter.INSTANCE.getJSON_INDENT()), "\n║ ");
    }
}
