package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.club.model.Terrain;
import java.io.IOException;

public class TerrainTypeAdapter extends TypeAdapter<Terrain> {
    public void write(JsonWriter out, Terrain terrain) throws IOException {
        out.value((long) terrain.getRawValue());
    }

    public Terrain read(JsonReader in) throws IOException {
        Terrain[] values;
        if (!in.peek().equals(JsonToken.NULL)) {
            int value = in.nextInt();
            for (Terrain terrain : Terrain.values()) {
                if (terrain.getRawValue() == value) {
                    return terrain;
                }
            }
        } else {
            in.nextNull();
        }
        return null;
    }
}
