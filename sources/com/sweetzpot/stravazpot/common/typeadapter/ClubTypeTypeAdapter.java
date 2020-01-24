package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.club.model.ClubType;
import java.io.IOException;

public class ClubTypeTypeAdapter extends TypeAdapter<ClubType> {
    public void write(JsonWriter out, ClubType type) throws IOException {
        out.value(type.toString());
    }

    public ClubType read(JsonReader in) throws IOException {
        ClubType[] values;
        if (!in.peek().equals(JsonToken.NULL)) {
            String input = in.nextString();
            for (ClubType type : ClubType.values()) {
                if (type.toString().equalsIgnoreCase(input)) {
                    return type;
                }
            }
        } else {
            in.nextNull();
        }
        return null;
    }
}
