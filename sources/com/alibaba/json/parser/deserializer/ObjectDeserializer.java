package com.alibaba.json.parser.deserializer;

import com.alibaba.json.parser.DefaultJSONParser;
import java.lang.reflect.Type;

public interface ObjectDeserializer {
    <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj);
}
