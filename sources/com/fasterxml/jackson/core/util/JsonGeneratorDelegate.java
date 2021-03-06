package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonGeneratorDelegate extends JsonGenerator {
    protected JsonGenerator delegate;

    public JsonGeneratorDelegate(JsonGenerator d) {
        this.delegate = d;
    }

    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }

    public JsonGenerator setCodec(ObjectCodec oc) {
        this.delegate.setCodec(oc);
        return this;
    }

    public void setSchema(FormatSchema schema) {
        this.delegate.setSchema(schema);
    }

    public FormatSchema getSchema() {
        return this.delegate.getSchema();
    }

    public boolean canUseSchema(FormatSchema schema) {
        return this.delegate.canUseSchema(schema);
    }

    public Version version() {
        return this.delegate.version();
    }

    public Object getOutputTarget() {
        return this.delegate.getOutputTarget();
    }

    public JsonGenerator setRootValueSeparator(SerializableString sep) {
        this.delegate.setRootValueSeparator(sep);
        return this;
    }

    public JsonGenerator enable(Feature f) {
        this.delegate.enable(f);
        return this;
    }

    public JsonGenerator disable(Feature f) {
        this.delegate.disable(f);
        return this;
    }

    public boolean isEnabled(Feature f) {
        return this.delegate.isEnabled(f);
    }

    public JsonGenerator setPrettyPrinter(PrettyPrinter pp) {
        this.delegate.setPrettyPrinter(pp);
        return this;
    }

    public PrettyPrinter getPrettyPrinter() {
        return this.delegate.getPrettyPrinter();
    }

    public JsonGenerator useDefaultPrettyPrinter() {
        this.delegate.useDefaultPrettyPrinter();
        return this;
    }

    public JsonGenerator setHighestNonEscapedChar(int charCode) {
        this.delegate.setHighestNonEscapedChar(charCode);
        return this;
    }

    public int getHighestEscapedChar() {
        return this.delegate.getHighestEscapedChar();
    }

    public CharacterEscapes getCharacterEscapes() {
        return this.delegate.getCharacterEscapes();
    }

    public JsonGenerator setCharacterEscapes(CharacterEscapes esc) {
        this.delegate.setCharacterEscapes(esc);
        return this;
    }

    public void writeStartArray() throws IOException, JsonGenerationException {
        this.delegate.writeStartArray();
    }

    public void writeEndArray() throws IOException, JsonGenerationException {
        this.delegate.writeEndArray();
    }

    public void writeStartObject() throws IOException, JsonGenerationException {
        this.delegate.writeStartObject();
    }

    public void writeEndObject() throws IOException, JsonGenerationException {
        this.delegate.writeEndObject();
    }

    public void writeFieldName(String name) throws IOException, JsonGenerationException {
        this.delegate.writeFieldName(name);
    }

    public void writeFieldName(SerializableString name) throws IOException, JsonGenerationException {
        this.delegate.writeFieldName(name);
    }

    public void writeString(String text) throws IOException, JsonGenerationException {
        this.delegate.writeString(text);
    }

    public void writeString(char[] text, int offset, int len) throws IOException, JsonGenerationException {
        this.delegate.writeString(text, offset, len);
    }

    public void writeString(SerializableString text) throws IOException, JsonGenerationException {
        this.delegate.writeString(text);
    }

    public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException, JsonGenerationException {
        this.delegate.writeRawUTF8String(text, offset, length);
    }

    public void writeUTF8String(byte[] text, int offset, int length) throws IOException, JsonGenerationException {
        this.delegate.writeUTF8String(text, offset, length);
    }

    public void writeRaw(String text) throws IOException, JsonGenerationException {
        this.delegate.writeRaw(text);
    }

    public void writeRaw(String text, int offset, int len) throws IOException, JsonGenerationException {
        this.delegate.writeRaw(text, offset, len);
    }

    public void writeRaw(SerializableString raw) throws IOException, JsonGenerationException {
        this.delegate.writeRaw(raw);
    }

    public void writeRaw(char[] text, int offset, int len) throws IOException, JsonGenerationException {
        this.delegate.writeRaw(text, offset, len);
    }

    public void writeRaw(char c) throws IOException, JsonGenerationException {
        this.delegate.writeRaw(c);
    }

    public void writeRawValue(String text) throws IOException, JsonGenerationException {
        this.delegate.writeRawValue(text);
    }

    public void writeRawValue(String text, int offset, int len) throws IOException, JsonGenerationException {
        this.delegate.writeRawValue(text, offset, len);
    }

    public void writeRawValue(char[] text, int offset, int len) throws IOException, JsonGenerationException {
        this.delegate.writeRawValue(text, offset, len);
    }

    public void writeBinary(Base64Variant b64variant, byte[] data, int offset, int len) throws IOException, JsonGenerationException {
        this.delegate.writeBinary(b64variant, data, offset, len);
    }

    public int writeBinary(Base64Variant b64variant, InputStream data, int dataLength) throws IOException, JsonGenerationException {
        return this.delegate.writeBinary(b64variant, data, dataLength);
    }

    public void writeNumber(int v) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(v);
    }

    public void writeNumber(long v) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(v);
    }

    public void writeNumber(BigInteger v) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(v);
    }

    public void writeNumber(double v) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(v);
    }

    public void writeNumber(float v) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(v);
    }

    public void writeNumber(BigDecimal v) throws IOException, JsonGenerationException {
        this.delegate.writeNumber(v);
    }

    public void writeNumber(String encodedValue) throws IOException, JsonGenerationException, UnsupportedOperationException {
        this.delegate.writeNumber(encodedValue);
    }

    public void writeBoolean(boolean state) throws IOException, JsonGenerationException {
        this.delegate.writeBoolean(state);
    }

    public void writeNull() throws IOException, JsonGenerationException {
        this.delegate.writeNull();
    }

    public void writeObject(Object pojo) throws IOException, JsonProcessingException {
        this.delegate.writeObject(pojo);
    }

    public void writeTree(TreeNode rootNode) throws IOException, JsonProcessingException {
        this.delegate.writeTree(rootNode);
    }

    public void copyCurrentEvent(JsonParser jp) throws IOException, JsonProcessingException {
        this.delegate.copyCurrentEvent(jp);
    }

    public void copyCurrentStructure(JsonParser jp) throws IOException, JsonProcessingException {
        this.delegate.copyCurrentStructure(jp);
    }

    public JsonStreamContext getOutputContext() {
        return this.delegate.getOutputContext();
    }

    public void flush() throws IOException {
        this.delegate.flush();
    }

    public void close() throws IOException {
        this.delegate.close();
    }

    public boolean isClosed() {
        return this.delegate.isClosed();
    }
}
