package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.common.model.Percentage;
import java.io.IOException;

public class PercentageTypeAdapter extends TypeAdapter<Percentage> {
    public void write(JsonWriter out, Percentage percentage) throws IOException {
        out.value((double) percentage.getValue());
    }

    public Percentage read(JsonReader in) throws IOException {
        if (!in.peek().equals(JsonToken.NULL)) {
            return Percentage.of((float) in.nextDouble());
        }
        in.nextNull();
        return null;
    }
}
