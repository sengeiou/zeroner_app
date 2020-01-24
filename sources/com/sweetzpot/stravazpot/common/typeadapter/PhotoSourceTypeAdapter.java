package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.activity.model.PhotoSource;
import java.io.IOException;

public class PhotoSourceTypeAdapter extends TypeAdapter<PhotoSource> {
    public void write(JsonWriter out, PhotoSource photoSource) throws IOException {
        out.value((long) photoSource.getRawValue());
    }

    public PhotoSource read(JsonReader in) throws IOException {
        PhotoSource[] values;
        if (!in.peek().equals(JsonToken.NULL)) {
            int input = in.nextInt();
            for (PhotoSource photoSource : PhotoSource.values()) {
                if (photoSource.getRawValue() == input) {
                    return photoSource;
                }
            }
        } else {
            in.nextNull();
        }
        return null;
    }
}
