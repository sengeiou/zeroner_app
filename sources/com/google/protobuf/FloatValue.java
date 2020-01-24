package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class FloatValue extends GeneratedMessageV3 implements FloatValueOrBuilder {
    private static final FloatValue DEFAULT_INSTANCE = new FloatValue();
    /* access modifiers changed from: private */
    public static final Parser<FloatValue> PARSER = new AbstractParser<FloatValue>() {
        public FloatValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new FloatValue(input, extensionRegistry);
        }
    };
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public float value_;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FloatValueOrBuilder {
        private float value_;

        public static final Descriptor getDescriptor() {
            return WrappersProto.internal_static_google_protobuf_FloatValue_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return WrappersProto.internal_static_google_protobuf_FloatValue_fieldAccessorTable.ensureFieldAccessorsInitialized(FloatValue.class, Builder.class);
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
            this.value_ = 0.0f;
            return this;
        }

        public Descriptor getDescriptorForType() {
            return WrappersProto.internal_static_google_protobuf_FloatValue_descriptor;
        }

        public FloatValue getDefaultInstanceForType() {
            return FloatValue.getDefaultInstance();
        }

        public FloatValue build() {
            FloatValue result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public FloatValue buildPartial() {
            FloatValue result = new FloatValue((com.google.protobuf.GeneratedMessageV3.Builder) this);
            result.value_ = this.value_;
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
            if (other instanceof FloatValue) {
                return mergeFrom((FloatValue) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(FloatValue other) {
            if (other != FloatValue.getDefaultInstance()) {
                if (other.getValue() != 0.0f) {
                    setValue(other.getValue());
                }
                mergeUnknownFields(other.unknownFields);
                onChanged();
            }
            return this;
        }

        public final boolean isInitialized() {
            return true;
        }

        public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            FloatValue parsedMessage = null;
            try {
                FloatValue parsedMessage2 = (FloatValue) FloatValue.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                FloatValue parsedMessage3 = (FloatValue) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                throw th;
            }
        }

        public float getValue() {
            return this.value_;
        }

        public Builder setValue(float value) {
            this.value_ = value;
            onChanged();
            return this;
        }

        public Builder clearValue() {
            this.value_ = 0.0f;
            onChanged();
            return this;
        }

        public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.setUnknownFieldsProto3(unknownFields);
        }

        public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.mergeUnknownFields(unknownFields);
        }
    }

    private FloatValue(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private FloatValue() {
        this.memoizedIsInitialized = -1;
        this.value_ = 0.0f;
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private FloatValue(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
            throw new NullPointerException();
        }
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
        boolean done = false;
        while (!done) {
            try {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        done = true;
                        break;
                    case 13:
                        this.value_ = input.readFloat();
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
        return WrappersProto.internal_static_google_protobuf_FloatValue_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return WrappersProto.internal_static_google_protobuf_FloatValue_fieldAccessorTable.ensureFieldAccessorsInitialized(FloatValue.class, Builder.class);
    }

    public float getValue() {
        return this.value_;
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
        if (this.value_ != 0.0f) {
            output.writeFloat(1, this.value_);
        }
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if (this.value_ != 0.0f) {
            size2 = 0 + CodedOutputStream.computeFloatSize(1, this.value_);
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
        if (!(obj instanceof FloatValue)) {
            return super.equals(obj);
        }
        FloatValue other = (FloatValue) obj;
        if (1 == 0 || Float.floatToIntBits(getValue()) != Float.floatToIntBits(other.getValue())) {
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
        int hash = ((((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + Float.floatToIntBits(getValue())) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash;
        return hash;
    }

    public static FloatValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (FloatValue) PARSER.parseFrom(data);
    }

    public static FloatValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (FloatValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static FloatValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (FloatValue) PARSER.parseFrom(data);
    }

    public static FloatValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (FloatValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static FloatValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (FloatValue) PARSER.parseFrom(data);
    }

    public static FloatValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (FloatValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static FloatValue parseFrom(InputStream input) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static FloatValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static FloatValue parseDelimitedFrom(InputStream input) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static FloatValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static FloatValue parseFrom(CodedInputStream input) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static FloatValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(FloatValue prototype) {
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

    public static FloatValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static FloatValue of(float value) {
        return newBuilder().setValue(value).build();
    }

    public static Parser<FloatValue> parser() {
        return PARSER;
    }

    public Parser<FloatValue> getParserForType() {
        return PARSER;
    }

    public FloatValue getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
