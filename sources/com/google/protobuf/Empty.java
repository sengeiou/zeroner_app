package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Empty extends GeneratedMessageV3 implements EmptyOrBuilder {
    private static final Empty DEFAULT_INSTANCE = new Empty();
    /* access modifiers changed from: private */
    public static final Parser<Empty> PARSER = new AbstractParser<Empty>() {
        public Empty parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Empty(input, extensionRegistry);
        }
    };
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements EmptyOrBuilder {
        public static final Descriptor getDescriptor() {
            return EmptyProto.internal_static_google_protobuf_Empty_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return EmptyProto.internal_static_google_protobuf_Empty_fieldAccessorTable.ensureFieldAccessorsInitialized(Empty.class, Builder.class);
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
            return this;
        }

        public Descriptor getDescriptorForType() {
            return EmptyProto.internal_static_google_protobuf_Empty_descriptor;
        }

        public Empty getDefaultInstanceForType() {
            return Empty.getDefaultInstance();
        }

        public Empty build() {
            Empty result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public Empty buildPartial() {
            Empty result = new Empty((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
            if (other instanceof Empty) {
                return mergeFrom((Empty) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Empty other) {
            if (other != Empty.getDefaultInstance()) {
                mergeUnknownFields(other.unknownFields);
                onChanged();
            }
            return this;
        }

        public final boolean isInitialized() {
            return true;
        }

        public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            Empty parsedMessage = null;
            try {
                Empty parsedMessage2 = (Empty) Empty.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                Empty parsedMessage3 = (Empty) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                throw th;
            }
        }

        public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.setUnknownFieldsProto3(unknownFields);
        }

        public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.mergeUnknownFields(unknownFields);
        }
    }

    private Empty(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private Empty() {
        this.memoizedIsInitialized = -1;
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Empty(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
        return EmptyProto.internal_static_google_protobuf_Empty_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return EmptyProto.internal_static_google_protobuf_Empty_fieldAccessorTable.ensureFieldAccessorsInitialized(Empty.class, Builder.class);
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
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0 + this.unknownFields.getSerializedSize();
        this.memoizedSize = size2;
        return size2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Empty)) {
            return super.equals(obj);
        }
        return 1 != 0 && this.unknownFields.equals(((Empty) obj).unknownFields);
    }

    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = ((getDescriptor().hashCode() + 779) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash;
        return hash;
    }

    public static Empty parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Empty) PARSER.parseFrom(data);
    }

    public static Empty parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Empty) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Empty parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Empty) PARSER.parseFrom(data);
    }

    public static Empty parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Empty) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Empty parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Empty) PARSER.parseFrom(data);
    }

    public static Empty parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Empty) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Empty parseFrom(InputStream input) throws IOException {
        return (Empty) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Empty parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Empty) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Empty parseDelimitedFrom(InputStream input) throws IOException {
        return (Empty) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Empty parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Empty) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Empty parseFrom(CodedInputStream input) throws IOException {
        return (Empty) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Empty parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Empty) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Empty prototype) {
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

    public static Empty getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Empty> parser() {
        return PARSER;
    }

    public Parser<Empty> getParserForType() {
        return PARSER;
    }

    public Empty getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
