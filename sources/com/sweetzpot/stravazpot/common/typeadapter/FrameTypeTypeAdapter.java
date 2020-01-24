package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.gear.model.FrameType;
import java.io.IOException;

public class FrameTypeTypeAdapter extends TypeAdapter<FrameType> {
    public void write(JsonWriter out, FrameType frameType) throws IOException {
        out.value((long) frameType.getRawValue());
    }

    public FrameType read(JsonReader in) throws IOException {
        FrameType[] values;
        if (!in.peek().equals(JsonToken.NULL)) {
            int value = in.nextInt();
            for (FrameType type : FrameType.values()) {
                if (type.getRawValue() == value) {
                    return type;
                }
            }
        }
        return null;
    }
}
