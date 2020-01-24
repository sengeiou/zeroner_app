package com.airbnb.lottie.parser;

import android.util.JsonReader;
import java.io.IOException;

public class IntegerParser implements ValueParser<Integer> {
    public static final IntegerParser INSTANCE = new IntegerParser();

    private IntegerParser() {
    }

    public Integer parse(JsonReader reader, float scale) throws IOException {
        return Integer.valueOf(Math.round(JsonUtils.valueFromObject(reader) * scale));
    }
}
