package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.common.model.Temperature;
import java.io.IOException;

public class TemperatureTypeAdapter extends TypeAdapter<Temperature> {
    public void write(JsonWriter out, Temperature speed) throws IOException {
        out.value((double) speed.getCelsiusDegrees());
    }

    public Temperature read(JsonReader in) throws IOException {
        if (!in.peek().equals(JsonToken.NULL)) {
            return Temperature.celsiusDegrees((float) in.nextDouble());
        }
        in.nextNull();
        return null;
    }
}
