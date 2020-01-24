package com.alibaba.json.serializer;

public interface PropertyFilter extends SerializeFilter {
    boolean apply(Object obj, String str, Object obj2);
}
