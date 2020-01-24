package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.reflect.TypeToken;

public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor constructorConstructor2) {
        this.constructorConstructor = constructorConstructor2;
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> targetType) {
        JsonAdapter annotation = (JsonAdapter) targetType.getRawType().getAnnotation(JsonAdapter.class);
        if (annotation == null) {
            return null;
        }
        return getTypeAdapter(this.constructorConstructor, gson, targetType, annotation);
    }

    /* access modifiers changed from: 0000 */
    public TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor2, Gson gson, TypeToken<?> type, JsonAdapter annotation) {
        JsonSerializer jsonSerializer;
        JsonDeserializer jsonDeserializer;
        TypeAdapter<?> typeAdapter;
        Object instance = constructorConstructor2.get(TypeToken.get(annotation.value())).construct();
        if (instance instanceof TypeAdapter) {
            typeAdapter = (TypeAdapter) instance;
        } else if (instance instanceof TypeAdapterFactory) {
            typeAdapter = ((TypeAdapterFactory) instance).create(gson, type);
        } else if ((instance instanceof JsonSerializer) || (instance instanceof JsonDeserializer)) {
            if (instance instanceof JsonSerializer) {
                jsonSerializer = (JsonSerializer) instance;
            } else {
                jsonSerializer = null;
            }
            if (instance instanceof JsonDeserializer) {
                jsonDeserializer = (JsonDeserializer) instance;
            } else {
                jsonDeserializer = null;
            }
            typeAdapter = new TreeTypeAdapter<>(jsonSerializer, jsonDeserializer, gson, type, null);
        } else {
            throw new IllegalArgumentException("Invalid attempt to bind an instance of " + instance.getClass().getName() + " as a @JsonAdapter for " + type.toString() + ". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer.");
        }
        if (typeAdapter == null || !annotation.nullSafe()) {
            return typeAdapter;
        }
        return typeAdapter.nullSafe();
    }
}
