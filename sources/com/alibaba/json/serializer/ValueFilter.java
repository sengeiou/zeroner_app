package com.alibaba.json.serializer;

public interface ValueFilter extends SerializeFilter {
    Object process(Object obj, String str, Object obj2);
}
