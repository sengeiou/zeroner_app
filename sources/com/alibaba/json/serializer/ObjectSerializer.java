package com.alibaba.json.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public interface ObjectSerializer {
    void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type) throws IOException;
}
