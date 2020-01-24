package com.alibaba.json.serializer;

public interface NameFilter extends SerializeFilter {
    String process(Object obj, String str, Object obj2);
}
