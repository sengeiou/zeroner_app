package com.alibaba.json.parser.deserializer;

import com.alibaba.json.JSONException;
import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.util.FieldInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public abstract class FieldDeserializer {
    public final Class<?> clazz;
    protected long[] enumNameHashCodes;
    protected Enum[] enums;
    public final FieldInfo fieldInfo;

    public abstract void parseField(DefaultJSONParser defaultJSONParser, Object obj, Type type, Map<String, Object> map);

    public FieldDeserializer(Class<?> clazz2, FieldInfo fieldInfo2, int fastMatchToken) {
        this.clazz = clazz2;
        this.fieldInfo = fieldInfo2;
        if (fieldInfo2 != null) {
            Class fieldClass = fieldInfo2.fieldClass;
            if (fieldClass.isEnum()) {
                Enum[] enums2 = (Enum[]) fieldClass.getEnumConstants();
                long[] enumNameHashCodes2 = new long[enums2.length];
                this.enumNameHashCodes = new long[enums2.length];
                for (int i = 0; i < enums2.length; i++) {
                    String name = enums2[i].name();
                    long hash = -3750763034362895579L;
                    for (int j = 0; j < name.length(); j++) {
                        hash = (hash ^ ((long) name.charAt(j))) * 1099511628211L;
                    }
                    enumNameHashCodes2[i] = hash;
                    this.enumNameHashCodes[i] = hash;
                }
                Arrays.sort(this.enumNameHashCodes);
                this.enums = new Enum[enums2.length];
                for (int i2 = 0; i2 < this.enumNameHashCodes.length; i2++) {
                    int j2 = 0;
                    while (true) {
                        if (j2 >= enumNameHashCodes2.length) {
                            break;
                        } else if (this.enumNameHashCodes[i2] == enumNameHashCodes2[j2]) {
                            this.enums[i2] = enums2[j2];
                            break;
                        } else {
                            j2++;
                        }
                    }
                }
            }
        }
    }

    public Enum getEnumByHashCode(long hashCode) {
        if (this.enums == null) {
            return null;
        }
        int enumIndex = Arrays.binarySearch(this.enumNameHashCodes, hashCode);
        if (enumIndex >= 0) {
            return this.enums[enumIndex];
        }
        return null;
    }

    public void setValue(Object object, int value) throws IllegalAccessException {
        this.fieldInfo.field.setInt(object, value);
    }

    public void setValue(Object object, long value) throws IllegalAccessException {
        this.fieldInfo.field.setLong(object, value);
    }

    public void setValue(Object object, float value) throws IllegalAccessException {
        this.fieldInfo.field.setFloat(object, value);
    }

    public void setValue(Object object, double value) throws IllegalAccessException {
        this.fieldInfo.field.setDouble(object, value);
    }

    public void setValue(Object object, Object value) {
        if (value != null || !this.fieldInfo.fieldClass.isPrimitive()) {
            Field field = this.fieldInfo.field;
            Method method = this.fieldInfo.method;
            try {
                if (this.fieldInfo.fieldAccess) {
                    if (!this.fieldInfo.getOnly) {
                        field.set(object, value);
                    } else if (Map.class.isAssignableFrom(this.fieldInfo.fieldClass)) {
                        Map map = (Map) field.get(object);
                        if (map != null) {
                            map.putAll((Map) value);
                        }
                    } else {
                        Collection collection = (Collection) field.get(object);
                        if (collection != null) {
                            collection.addAll((Collection) value);
                        }
                    }
                } else if (!this.fieldInfo.getOnly) {
                    method.invoke(object, new Object[]{value});
                } else if (Map.class.isAssignableFrom(this.fieldInfo.fieldClass)) {
                    Map map2 = (Map) method.invoke(object, new Object[0]);
                    if (map2 != null) {
                        map2.putAll((Map) value);
                    }
                } else {
                    Collection collection2 = (Collection) method.invoke(object, new Object[0]);
                    if (collection2 != null) {
                        collection2.addAll((Collection) value);
                    }
                }
            } catch (Exception e) {
                throw new JSONException("set property error, " + this.fieldInfo.name, e);
            }
        }
    }
}
