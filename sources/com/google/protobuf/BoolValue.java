package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class BoolValue extends GeneratedMessageV3 implements BoolValueOrBuilder {
    private static final BoolValue DEFAULT_INSTANCE = new BoolValue();
    /* access modifiers changed from: private */
    public static final Parser<BoolValue> PARSER = new AbstractParser<BoolValue>() {
        public BoolValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new BoolValue(input, extensionRegistry);
        }
    };
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public boolean value_;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements BoolValueOrBuilder {
        private boolean value_;

        public static final Descriptor getDescriptor() {
            return WrappersProto.internal_static_google_protobuf_BoolValue_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return WrappersProto.internal_static_google_protobuf_BoolValue_fieldAccessorTable.ensureFieldAccessorsInitialized(BoolValue.class, Builder.class);
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
            this.value_ = false;
            return this;
        }

        public Descriptor getDescriptorForType() {
            return WrappersProto.internal_static_google_protobuf_BoolValue_descriptor;
        }

        public BoolValue getDefaultInstanceForType() {
            return BoolValue.getDefaultInstance();
        }

        public BoolValue build() {
            BoolValue result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public BoolValue buildPartial() {
            BoolValue result = new BoolValue((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
            if (other instanceof BoolValue) {
                return mergeFrom((BoolValue) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(BoolValue other) {
            if (other != BoolValue.getDefaultInstance()) {
                if (other.getValue()) {
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
            BoolValue parsedMessage = null;
            try {
                BoolValue parsedMessage2 = (BoolValue) BoolValue.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                BoolValue parsedMessage3 = (BoolValue) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                throw th;
            }
        }

        public boolean getValue() {
            return this.value_;
        }

        public Builder setValue(boolean value) {
            this.value_ = value;
            onChanged();
            return this;
        }

        public Builder clearValue() {
            this.value_ = false;
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

    private BoolValue(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private BoolValue() {
        this.memoizedIsInitialized = -1;
        this.value_ = false;
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private BoolValue(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    case 8:
                        this.value_ = input.readBool();
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
        return WrappersProto.internal_static_google_protobuf_BoolValue_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return WrappersProto.internal_static_google_protobuf_BoolValue_fieldAccessorTable.ensureFieldAccessorsInitialized(BoolValue.class, Builder.class);
    }

    public boolean getValue() {
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
        if (this.value_) {
            output.writeBool(1, this.value_);
        }
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if (this.value_) {
            size2 = 0 + CodedOutputStream.computeBoolSize(1, this.value_);
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
        if (!(obj instanceof BoolValue)) {
            return super.equals(obj);
        }
        BoolValue other = (BoolValue) obj;
        if (1 == 0 || getValue() != other.getValue()) {
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
        int hash = ((((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + Internal.hashBoolean(getValue())) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash;
        return hash;
    }

    public static BoolValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (BoolValue) PARSER.parseFrom(data);
    }

    public static BoolValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BoolValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static BoolValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (BoolValue) PARSER.parseFrom(data);
    }

    public static BoolValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BoolValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static BoolValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (BoolValue) PARSER.parseFrom(data);
    }

    public static BoolValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BoolValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static BoolValue parseFrom(InputStream input) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static BoolValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static BoolValue parseDelimitedFrom(InputStream input) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static BoolValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static BoolValue parseFrom(CodedInputStream input) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static BoolValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(BoolValue prototype) {
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

    public static BoolValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static BoolValue of(boolean value) {
        return newBuilder().setValue(value).build();
    }

    public static Parser<BoolValue> parser() {
        return PARSER;
    }

    public Parser<BoolValue> getParserForType() {
        return PARSER;
    }

    public BoolValue getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
