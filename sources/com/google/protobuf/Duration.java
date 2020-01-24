package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Duration extends GeneratedMessageV3 implements DurationOrBuilder {
    private static final Duration DEFAULT_INSTANCE = new Duration();
    public static final int NANOS_FIELD_NUMBER = 2;
    /* access modifiers changed from: private */
    public static final Parser<Duration> PARSER = new AbstractParser<Duration>() {
        public Duration parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Duration(input, extensionRegistry);
        }
    };
    public static final int SECONDS_FIELD_NUMBER = 1;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public int nanos_;
    /* access modifiers changed from: private */
    public long seconds_;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements DurationOrBuilder {
        private int nanos_;
        private long seconds_;

        public static final Descriptor getDescriptor() {
            return DurationProto.internal_static_google_protobuf_Duration_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DurationProto.internal_static_google_protobuf_Duration_fieldAccessorTable.ensureFieldAccessorsInitialized(Duration.class, Builder.class);
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
            this.seconds_ = 0;
            this.nanos_ = 0;
            return this;
        }

        public Descriptor getDescriptorForType() {
            return DurationProto.internal_static_google_protobuf_Duration_descriptor;
        }

        public Duration getDefaultInstanceForType() {
            return Duration.getDefaultInstance();
        }

        public Duration build() {
            Duration result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public Duration buildPartial() {
            Duration result = new Duration((com.google.protobuf.GeneratedMessageV3.Builder) this);
            result.seconds_ = this.seconds_;
            result.nanos_ = this.nanos_;
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
            if (other instanceof Duration) {
                return mergeFrom((Duration) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Duration other) {
            if (other != Duration.getDefaultInstance()) {
                if (other.getSeconds() != 0) {
                    setSeconds(other.getSeconds());
                }
                if (other.getNanos() != 0) {
                    setNanos(other.getNanos());
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
            Duration parsedMessage = null;
            try {
                Duration parsedMessage2 = (Duration) Duration.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                Duration parsedMessage3 = (Duration) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                throw th;
            }
        }

        public long getSeconds() {
            return this.seconds_;
        }

        public Builder setSeconds(long value) {
            this.seconds_ = value;
            onChanged();
            return this;
        }

        public Builder clearSeconds() {
            this.seconds_ = 0;
            onChanged();
            return this;
        }

        public int getNanos() {
            return this.nanos_;
        }

        public Builder setNanos(int value) {
            this.nanos_ = value;
            onChanged();
            return this;
        }

        public Builder clearNanos() {
            this.nanos_ = 0;
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

    private Duration(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private Duration() {
        this.memoizedIsInitialized = -1;
        this.seconds_ = 0;
        this.nanos_ = 0;
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Duration(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.seconds_ = input.readInt64();
                        break;
                    case 16:
                        this.nanos_ = input.readInt32();
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
        return DurationProto.internal_static_google_protobuf_Duration_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return DurationProto.internal_static_google_protobuf_Duration_fieldAccessorTable.ensureFieldAccessorsInitialized(Duration.class, Builder.class);
    }

    public long getSeconds() {
        return this.seconds_;
    }

    public int getNanos() {
        return this.nanos_;
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
        if (this.seconds_ != 0) {
            output.writeInt64(1, this.seconds_);
        }
        if (this.nanos_ != 0) {
            output.writeInt32(2, this.nanos_);
        }
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if (this.seconds_ != 0) {
            size2 = 0 + CodedOutputStream.computeInt64Size(1, this.seconds_);
        }
        if (this.nanos_ != 0) {
            size2 += CodedOutputStream.computeInt32Size(2, this.nanos_);
        }
        int size3 = size2 + this.unknownFields.getSerializedSize();
        this.memoizedSize = size3;
        return size3;
    }

    public boolean equals(Object obj) {
        boolean result;
        boolean result2;
        boolean result3;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Duration)) {
            return super.equals(obj);
        }
        Duration other = (Duration) obj;
        if (1 == 0 || getSeconds() != other.getSeconds()) {
            result = false;
        } else {
            result = true;
        }
        if (!result || getNanos() != other.getNanos()) {
            result2 = false;
        } else {
            result2 = true;
        }
        if (!result2 || !this.unknownFields.equals(other.unknownFields)) {
            result3 = false;
        } else {
            result3 = true;
        }
        return result3;
    }

    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = ((((((((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + Internal.hashLong(getSeconds())) * 37) + 2) * 53) + getNanos()) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash;
        return hash;
    }

    public static Duration parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Duration) PARSER.parseFrom(data);
    }

    public static Duration parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Duration) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Duration parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Duration) PARSER.parseFrom(data);
    }

    public static Duration parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Duration) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Duration parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Duration) PARSER.parseFrom(data);
    }

    public static Duration parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Duration) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Duration parseFrom(InputStream input) throws IOException {
        return (Duration) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Duration parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Duration) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Duration parseDelimitedFrom(InputStream input) throws IOException {
        return (Duration) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Duration parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Duration) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Duration parseFrom(CodedInputStream input) throws IOException {
        return (Duration) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Duration parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Duration) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Duration prototype) {
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

    public static Duration getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Duration> parser() {
        return PARSER;
    }

    public Parser<Duration> getParserForType() {
        return PARSER;
    }

    public Duration getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
