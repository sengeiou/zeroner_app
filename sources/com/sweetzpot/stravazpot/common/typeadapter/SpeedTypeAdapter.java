package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.common.model.Speed;
import java.io.IOException;

public class SpeedTypeAdapter extends TypeAdapter<Speed> {
    public void write(JsonWriter out, Speed speed) throws IOException {
        out.value((double) speed.getMetersPerSecond());
    }

    public Speed read(JsonReader in) throws IOException {
        if (!in.peek().equals(JsonToken.NULL)) {
            return Speed.metersPerSecond((float) in.nextDouble());
        }
        in.nextNull();
        return null;
    }
}
