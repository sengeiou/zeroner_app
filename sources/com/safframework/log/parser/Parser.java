package com.safframework.log.parser;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/safframework/log/parser/Parser;", "T", "", "parseString", "", "t", "(Ljava/lang/Object;)Ljava/lang/String;", "saf-log_release"}, k = 1, mv = {1, 1, 8})
/* compiled from: Parser.kt */
public interface Parser<T> {
    @NotNull
    String parseString(T t);
}
