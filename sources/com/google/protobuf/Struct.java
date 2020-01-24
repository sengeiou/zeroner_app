package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import com.google.protobuf.WireFormat.FieldType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Map.Entry;

public final class Struct extends GeneratedMessageV3 implements StructOrBuilder {
    private static final Struct DEFAULT_INSTANCE = new Struct();
    public static final int FIELDS_FIELD_NUMBER = 1;
    /* access modifiers changed from: private */
    public static final Parser<Struct> PARSER = new AbstractParser<Struct>() {
        public Struct parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Struct(input, extensionRegistry);
        }
    };
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public MapField<String, Value> fields_;
    private byte memoizedIsInitialized;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements StructOrBuilder {
        private int bitField0_;
        private MapField<String, Value> fields_;

        public static final Descriptor getDescriptor() {
            return StructProto.internal_static_google_protobuf_Struct_descriptor;
        }

        /* access modifiers changed from: protected */
        public MapField internalGetMapField(int number) {
            switch (number) {
                case 1:
                    return internalGetFields();
                default:
                    throw new RuntimeException("Invalid map field number: " + number);
            }
        }

        /* access modifiers changed from: protected */
        public MapField internalGetMutableMapField(int number) {
            switch (number) {
                case 1:
                    return internalGetMutableFields();
                default:
                    throw new RuntimeException("Invalid map field number: " + number);
            }
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return StructProto.internal_static_google_protobuf_Struct_fieldAccessorTable.ensureFieldAccessorsInitialized(Struct.class, Builder.class);
        }

        private Builder() {
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
            }
        }

        public Builder clear() {
            super.clear();
            internalGetMutableFields().clear();
            return this;
        }

        public Descriptor getDescriptorForType() {
            return StructProto.internal_static_google_protobuf_Struct_descriptor;
        }

        public Struct getDefaultInstanceForType() {
            return Struct.getDefaultInstance();
        }

        public Struct build() {
            Struct result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public Struct buildPartial() {
            Struct result = new Struct((com.google.protobuf.GeneratedMessageV3.Builder) this);
            int i = this.bitField0_;
            result.fields_ = internalGetFields();
            result.fields_.makeImmutable();
            onBuilt();
            return result;
        }

        public Builder clone() {
            return (Builder) super.clone();
        }

        public Builder setField(FieldDescriptor field, Object value) {
            return (Builder) super.setField(field, value);
        }

        public Builder clearField(FieldDescriptor field) {
            return (Builder) super.clearField(field);
        }

        public Builder clearOneof(OneofDescriptor oneof) {
            return (Builder) super.clearOneof(oneof);
        }

        public Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
            return (Builder) super.setRepeatedField(field, index, value);
        }

        public Builder addRepeatedField(FieldDescriptor field, Object value) {
            return (Builder) super.addRepeatedField(field, value);
        }

        /* Debug info: failed to restart local var, previous not found, register: 1 */
        public Builder mergeFrom(Message other) {
            if (other instanceof Struct) {
                return mergeFrom((Struct) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Struct other) {
            if (other != Struct.getDefaultInstance()) {
                internalGetMutableFields().mergeFrom(other.internalGetFields());
                mergeUnknownFields(other.unknownFields);
                onChanged();
            }
            return this;
        }

        public final boolean isInitialized() {
            return true;
        }

        public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Struct parsedMessage = null;
            try {
                Struct parsedMessage2 = (Struct) Struct.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                Struct parsedMessage3 = (Struct) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                throw th;
            }
        }

        private MapField<String, Value> internalGetFields() {
            if (this.fields_ == null) {
                return MapField.emptyMapField(FieldsDefaultEntryHolder.defaultEntry);
            }
            return this.fields_;
        }

        private MapField<String, Value> internalGetMutableFields() {
            onChanged();
            if (this.fields_ == null) {
                this.fields_ = MapField.newMapField(FieldsDefaultEntryHolder.defaultEntry);
            }
            if (!this.fields_.isMutable()) {
                this.fields_ = this.fields_.copy();
            }
            return this.fields_;
        }

        public int getFieldsCount() {
            return internalGetFields().getMap().size();
        }

        public boolean containsFields(String key) {
            if (key != null) {
                return internalGetFields().getMap().containsKey(key);
            }
            throw new NullPointerException();
        }

        @Deprecated
        public Map<String, Value> getFields() {
            return getFieldsMap();
        }

        public Map<String, Value> getFieldsMap() {
            return internalGetFields().getMap();
        }

        public Value getFieldsOrDefault(String key, Value defaultValue) {
            if (key == null) {
                throw new NullPointerException();
            }
            Map<String, Value> map = internalGetFields().getMap();
            return map.containsKey(key) ? (Value) map.get(key) : defaultValue;
        }

        public Value getFieldsOrThrow(String key) {
            if (key == null) {
                throw new NullPointerException();
            }
            Map<String, Value> map = internalGetFields().getMap();
            if (map.containsKey(key)) {
                return (Value) map.get(key);
            }
            throw new IllegalArgumentException();
        }

        public Builder clearFields() {
            internalGetMutableFields().getMutableMap().clear();
            return this;
        }

        public Builder removeFields(String key) {
            if (key == null) {
                throw new NullPointerException();
            }
            internalGetMutableFields().getMutableMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, Value> getMutableFields() {
            return internalGetMutableFields().getMutableMap();
        }

        public Builder putFields(String key, Value value) {
            if (key == null) {
                throw new NullPointerException();
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                internalGetMutableFields().getMutableMap().put(key, value);
                return this;
            }
        }

        public Builder putAllFields(Map<String, Value> values) {
            internalGetMutableFields().getMutableMap().putAll(values);
            return this;
        }

        public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.setUnknownFieldsProto3(unknownFields);
        }

        public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.mergeUnknownFields(unknownFields);
        }
    }

    private static final class FieldsDefaultEntryHolder {
        static final MapEntry<String, Value> defaultEntry = MapEntry.newDefaultInstance(StructProto.internal_static_google_protobuf_Struct_FieldsEntry_descriptor, FieldType.STRING, "", FieldType.MESSAGE, Value.getDefaultInstance());

        private FieldsDefaultEntryHolder() {
        }
    }

    private Struct(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private Struct() {
        this.memoizedIsInitialized = -1;
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Struct(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
            throw new NullPointerException();
        }
        int mutable_bitField0_ = 0;
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
        boolean done = false;
        while (!done) {
            try {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        done = true;
                        break;
                    case 10:
                        if ((mutable_bitField0_ & 1) != 1) {
                            this.fields_ = MapField.newMapField(FieldsDefaultEntryHolder.defaultEntry);
                            mutable_bitField0_ |= 1;
                        }
                        MapEntry<String, Value> fields__ = (MapEntry) input.readMessage(FieldsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
                        this.fields_.getMutableMap().put(fields__.getKey(), fields__.getValue());
                        break;
                    default:
                        if (parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) {
                            break;
                        } else {
                            done = true;
                            break;
                        }
                }
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
            } catch (Throwable th) {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
                throw th;
            }
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
    }

    public static final Descriptor getDescriptor() {
        return StructProto.internal_static_google_protobuf_Struct_descriptor;
    }

    /* access modifiers changed from: protected */
    public MapField internalGetMapField(int number) {
        switch (number) {
            case 1:
                return internalGetFields();
            default:
                throw new RuntimeException("Invalid map field number: " + number);
        }
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return StructProto.internal_static_google_protobuf_Struct_fieldAccessorTable.ensureFieldAccessorsInitialized(Struct.class, Builder.class);
    }

    /* access modifiers changed from: private */
    public MapField<String, Value> internalGetFields() {
        if (this.fields_ == null) {
            return MapField.emptyMapField(FieldsDefaultEntryHolder.defaultEntry);
        }
        return this.fields_;
    }

    public int getFieldsCount() {
        return internalGetFields().getMap().size();
    }

    public boolean containsFields(String key) {
        if (key != null) {
            return internalGetFields().getMap().containsKey(key);
        }
        throw new NullPointerException();
    }

    @Deprecated
    public Map<String, Value> getFields() {
        return getFieldsMap();
    }

    public Map<String, Value> getFieldsMap() {
        return internalGetFields().getMap();
    }

    public Value getFieldsOrDefault(String key, Value defaultValue) {
        if (key == null) {
            throw new NullPointerException();
        }
        Map<String, Value> map = internalGetFields().getMap();
        return map.containsKey(key) ? (Value) map.get(key) : defaultValue;
    }

    public Value getFieldsOrThrow(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        Map<String, Value> map = internalGetFields().getMap();
        if (map.containsKey(key)) {
            return (Value) map.get(key);
        }
        throw new IllegalArgumentException();
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        this.memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(CodedOutputStream output) throws IOException {
        GeneratedMessageV3.serializeStringMapTo(output, internalGetFields(), FieldsDefaultEntryHolder.defaultEntry, 1);
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        for (Entry<String, Value> entry : internalGetFields().getMap().entrySet()) {
            size2 += CodedOutputStream.computeMessageSize(1, FieldsDefaultEntryHolder.defaultEntry.newBuilderForType().setKey(entry.getKey()).setValue(entry.getValue()).build());
        }
        int size3 = size2 + this.unknownFields.getSerializedSize();
        this.memoizedSize = size3;
        return size3;
    }

    public boolean equals(Object obj) {
        boolean result;
        boolean result2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Struct)) {
            return super.equals(obj);
        }
        Struct other = (Struct) obj;
        if (1 == 0 || !internalGetFields().equals(other.internalGetFields())) {
            result = false;
        } else {
            result = true;
        }
        if (!result || !this.unknownFields.equals(other.unknownFields)) {
            result2 = false;
        } else {
            result2 = true;
        }
        return result2;
    }

    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = getDescriptor().hashCode() + 779;
        if (!internalGetFields().getMap().isEmpty()) {
            hash = (((hash * 37) + 1) * 53) + internalGetFields().hashCode();
        }
        int hash2 = (hash * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash2;
        return hash2;
    }

    public static Struct parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Struct) PARSER.parseFrom(data);
    }

    public static Struct parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Struct) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Struct parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Struct) PARSER.parseFrom(data);
    }

    public static Struct parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Struct) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Struct parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Struct) PARSER.parseFrom(data);
    }

    public static Struct parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Struct) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Struct parseFrom(InputStream input) throws IOException {
        return (Struct) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Struct parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Struct) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Struct parseDelimitedFrom(InputStream input) throws IOException {
        return (Struct) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Struct parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Struct) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Struct parseFrom(CodedInputStream input) throws IOException {
        return (Struct) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Struct parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Struct) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Struct prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
        if (this == DEFAULT_INSTANCE) {
            return new Builder();
        }
        return new Builder().mergeFrom(this);
    }

    /* access modifiers changed from: protected */
    public Builder newBuilderForType(BuilderParent parent) {
        return new Builder(parent);
    }

    public static Struct getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Struct> parser() {
        return PARSER;
    }

    public Parser<Struct> getParserForType() {
        return PARSER;
    }

    public Struct getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
