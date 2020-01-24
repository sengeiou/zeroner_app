package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.authenticaton.model.Token;
import java.io.IOException;

public class TokenTypeAdapter extends TypeAdapter<Token> {
    public void write(JsonWriter out, Token token) throws IOException {
        out.value(token.toString());
    }

    public Token read(JsonReader in) throws IOException {
        if (!in.peek().equals(JsonToken.NULL)) {
            return new Token(in.nextString());
        }
        in.nextNull();
        return null;
    }
}
