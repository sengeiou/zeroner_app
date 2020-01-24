package com.alibaba.json.parser;

import com.alibaba.json.JSONArray;
import com.alibaba.json.parser.deserializer.FieldDeserializer;
import com.alibaba.json.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

final class ResolveFieldDeserializer extends FieldDeserializer {
    private final Collection collection;
    private final int index;
    private final Object key;
    private final List list;
    private final Map map;
    private final DefaultJSONParser parser;

    public ResolveFieldDeserializer(DefaultJSONParser parser2, List list2, int index2) {
        super(null, null, 0);
        this.parser = parser2;
        this.index = index2;
        this.list = list2;
        this.key = null;
        this.map = null;
        this.collection = null;
    }

    public ResolveFieldDeserializer(Map map2, Object index2) {
        super(null, null, 0);
        this.parser = null;
        this.index = -1;
        this.list = null;
        this.key = index2;
        this.map = map2;
        this.collection = null;
    }

    public ResolveFieldDeserializer(Collection collection2) {
        super(null, null, 0);
        this.parser = null;
        this.index = -1;
        this.list = null;
        this.key = null;
        this.map = null;
        this.collection = collection2;
    }

    public void setValue(Object object, Object value) {
        Object item;
        if (this.map != null) {
            this.map.put(this.key, value);
        } else if (this.collection != null) {
            this.collection.add(value);
        } else {
            this.list.set(this.index, value);
            if (this.list instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) this.list;
                Object array = jsonArray.getRelatedArray();
                if (array != null && Array.getLength(array) > this.index) {
                    if (jsonArray.getComponentType() != null) {
                        item = TypeUtils.cast(value, jsonArray.getComponentType(), this.parser.config);
                    } else {
                        item = value;
                    }
                    Array.set(array, this.index, item);
                }
            }
        }
    }

    public void parseField(DefaultJSONParser parser2, Object object, Type objectType, Map<String, Object> map2) {
    }
}
