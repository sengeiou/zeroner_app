package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.athlete.model.AthleteType;
import java.io.IOException;

public class AthleteTypeTypeAdapter extends TypeAdapter<AthleteType> {
    public void write(JsonWriter out, AthleteType athleteType) throws IOException {
        out.value((long) athleteType.getRawValue());
    }

    public AthleteType read(JsonReader in) throws IOException {
        AthleteType[] values;
        if (!in.peek().equals(JsonToken.NULL)) {
            int value = in.nextInt();
            for (AthleteType type : AthleteType.values()) {
                if (type.getRawValue() == value) {
                    return type;
                }
            }
        }
        return null;
    }
}
