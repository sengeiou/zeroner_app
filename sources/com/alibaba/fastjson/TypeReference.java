package com.alibaba.fastjson;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TypeReference<T> {
    static ConcurrentMap<Type, Type> classTypeCache = new ConcurrentHashMap(16, 0.75f, 1);
    protected final Type type;

    protected TypeReference() {
        Type oriType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (oriType instanceof Class) {
            this.type = oriType;
            return;
        }
        Type cachedType = (Type) classTypeCache.get(oriType);
        if (cachedType == null) {
            classTypeCache.putIfAbsent(oriType, oriType);
            cachedType = (Type) classTypeCache.get(oriType);
        }
        this.type = cachedType;
    }

    protected TypeReference(Type... actualTypeArguments) {
        Class<?> thisClass = getClass();
        ParameterizedType argType = (ParameterizedType) ((ParameterizedType) thisClass.getGenericSuperclass()).getActualTypeArguments()[0];
        Type rawType = argType.getRawType();
        Type[] argTypes = argType.getActualTypeArguments();
        int actualIndex = 0;
        for (int i = 0; i < argTypes.length; i++) {
            if ((argTypes[i] instanceof TypeVariable) && actualIndex < actualTypeArguments.length) {
                int actualIndex2 = actualIndex + 1;
                argTypes[i] = actualTypeArguments[actualIndex];
                actualIndex = actualIndex2;
            }
            if (argTypes[i] instanceof GenericArrayType) {
                argTypes[i] = TypeUtils.checkPrimitiveArray((GenericArrayType) argTypes[i]);
            }
            if (argTypes[i] instanceof ParameterizedType) {
                argTypes[i] = handlerParameterizedType((ParameterizedType) argTypes[i], actualTypeArguments, actualIndex);
            }
        }
        Type key = new ParameterizedTypeImpl(argTypes, thisClass, rawType);
        Type cachedType = (Type) classTypeCache.get(key);
        if (cachedType == null) {
            classTypeCache.putIfAbsent(key, key);
            cachedType = (Type) classTypeCache.get(key);
        }
        this.type = cachedType;
    }

    private Type handlerParameterizedType(ParameterizedType type2, Type[] actualTypeArguments, int actualIndex) {
        Class<?> thisClass = getClass();
        Type rawType = type2.getRawType();
        Type[] argTypes = type2.getActualTypeArguments();
        for (int i = 0; i < argTypes.length; i++) {
            if ((argTypes[i] instanceof TypeVariable) && actualIndex < actualTypeArguments.length) {
                int actualIndex2 = actualIndex + 1;
                argTypes[i] = actualTypeArguments[actualIndex];
                actualIndex = actualIndex2;
            }
            if (argTypes[i] instanceof GenericArrayType) {
                argTypes[i] = TypeUtils.checkPrimitiveArray((GenericArrayType) argTypes[i]);
            }
            if (argTypes[i] instanceof ParameterizedType) {
                return handlerParameterizedType((ParameterizedType) argTypes[i], actualTypeArguments, actualIndex);
            }
        }
        return new ParameterizedTypeImpl(argTypes, thisClass, rawType);
    }

    public Type getType() {
        return this.type;
    }
}
