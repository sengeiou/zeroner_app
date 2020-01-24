package com.alibaba.json.parser.deserializer;

public interface ExtraProcessable {
    void processExtra(String str, Object obj);
}
