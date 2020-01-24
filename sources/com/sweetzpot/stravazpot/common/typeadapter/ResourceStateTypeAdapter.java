package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.common.model.ResourceState;
import java.io.IOException;

public class ResourceStateTypeAdapter extends TypeAdapter<ResourceState> {
    public void write(JsonWriter out, ResourceState resourceState) throws IOException {
        out.value((long) resourceState.getRawValue());
    }

    public ResourceState read(JsonReader in) throws IOException {
        ResourceState[] values;
        if (!in.peek().equals(JsonToken.NULL)) {
            int value = in.nextInt();
            for (ResourceState resourceState : ResourceState.values()) {
                if (resourceState.getRawValue() == value) {
                    return resourceState;
                }
            }
        } else {
            in.nextNull();
        }
        return null;
    }
}
