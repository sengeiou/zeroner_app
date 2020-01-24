package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.common.model.Coordinates;
import java.io.IOException;

public class CoordinatesTypeAdapter extends TypeAdapter<Coordinates> {
    public void write(JsonWriter out, Coordinates coordinates) throws IOException {
        out.beginArray();
        out.value((double) coordinates.getLatitude());
        out.value((double) coordinates.getLongitude());
        out.endArray();
    }

    public Coordinates read(JsonReader in) throws IOException {
        if (!in.peek().equals(JsonToken.NULL)) {
            in.beginArray();
            float latitude = (float) in.nextDouble();
            float longitude = (float) in.nextDouble();
            in.endArray();
            return Coordinates.at(latitude, longitude);
        }
        in.nextNull();
        return null;
    }
}
