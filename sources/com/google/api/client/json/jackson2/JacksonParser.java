package com.google.api.client.json.jackson2;

import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

final class JacksonParser extends JsonParser {
    private final JacksonFactory factory;
    private final com.fasterxml.jackson.core.JsonParser parser;

    public JacksonFactory getFactory() {
        return this.factory;
    }

    JacksonParser(JacksonFactory factory2, com.fasterxml.jackson.core.JsonParser parser2) {
        this.factory = factory2;
        this.parser = parser2;
    }

    public void close() throws IOException {
        this.parser.close();
    }

    public JsonToken nextToken() throws IOException {
        return JacksonFactory.convert(this.parser.nextToken());
    }

    public String getCurrentName() throws IOException {
        return this.parser.getCurrentName();
    }

    public JsonToken getCurrentToken() {
        return JacksonFactory.convert(this.parser.getCurrentToken());
    }

    public JsonParser skipChildren() throws IOException {
        this.parser.skipChildren();
        return this;
    }

    public String getText() throws IOException {
        return this.parser.getText();
    }

    public byte getByteValue() throws IOException {
        return this.parser.getByteValue();
    }

    public float getFloatValue() throws IOException {
        return this.parser.getFloatValue();
    }

    public int getIntValue() throws IOException {
        return this.parser.getIntValue();
    }

    public short getShortValue() throws IOException {
        return this.parser.getShortValue();
    }

    public BigInteger getBigIntegerValue() throws IOException {
        return this.parser.getBigIntegerValue();
    }

    public BigDecimal getDecimalValue() throws IOException {
        return this.parser.getDecimalValue();
    }

    public double getDoubleValue() throws IOException {
        return this.parser.getDoubleValue();
    }

    public long getLongValue() throws IOException {
        return this.parser.getLongValue();
    }
}
