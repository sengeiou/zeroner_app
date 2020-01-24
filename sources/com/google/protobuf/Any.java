package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Any extends GeneratedMessageV3 implements AnyOrBuilder {
    private static final Any DEFAULT_INSTANCE = new Any();
    /* access modifiers changed from: private */
    public static final Parser<Any> PARSER = new AbstractParser<Any>() {
        public Any parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Any(input, extensionRegistry);
        }
    };
    public static final int TYPE_URL_FIELD_NUMBER = 1;
    public static final int VALUE_FIELD_NUMBER = 2;
    private static final long serialVersionUID = 0;
    private volatile Message cachedUnpackValue;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public volatile Object typeUrl_;
    /* access modifiers changed from: private */
    public ByteString value_;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements AnyOrBuilder {
        private Object typeUrl_;
        private ByteString value_;

        public static final Descriptor getDescriptor() {
            return AnyProto.internal_static_google_protobuf_Any_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return AnyProto.internal_static_google_protobuf_Any_fieldAccessorTable.ensureFieldAccessorsInitialized(Any.class, Builder.class);
        }

        private Builder() {
            this.typeUrl_ = "";
            this.value_ = ByteString.EMPTY;
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            this.typeUrl_ = "";
            this.value_ = ByteString.EMPTY;
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
            }
        }

        public Builder clear() {
            super.clear();
            this.typeUrl_ = "";
            this.value_ = ByteString.EMPTY;
            return this;
        }

        public Descriptor getDescriptorForType() {
            return AnyProto.internal_static_google_protobuf_Any_descriptor;
        }

        public Any getDefaultInstanceForType() {
            return Any.getDefaultInstance();
        }

        public Any build() {
            Any result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public Any buildPartial() {
            Any result = new Any((com.google.protobuf.GeneratedMessageV3.Builder) this);
            result.typeUrl_ = this.typeUrl_;
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
            if (other instanceof Any) {
                return mergeFrom((Any) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Any other) {
            if (other != Any.getDefaultInstance()) {
                if (!other.getTypeUrl().isEmpty()) {
                    this.typeUrl_ = other.typeUrl_;
                    onChanged();
                }
                if (other.getValue() != ByteString.EMPTY) {
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
            Any parsedMessage = null;
            try {
                Any parsedMessage2 = (Any) Any.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                Any parsedMessage3 = (Any) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                throw th;
            }
        }

        public String getTypeUrl() {
            Object ref = this.typeUrl_;
            if (ref instanceof String) {
                return (String) ref;
            }
            String s = ((ByteString) ref).toStringUtf8();
            this.typeUrl_ = s;
            return s;
        }

        public ByteString getTypeUrlBytes() {
            Object ref = this.typeUrl_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.typeUrl_ = b;
            return b;
        }

        public Builder setTypeUrl(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.typeUrl_ = value;
            onChanged();
            return this;
        }

        public Builder clearTypeUrl() {
            this.typeUrl_ = Any.getDefaultInstance().getTypeUrl();
            onChanged();
            return this;
        }

        public Builder setTypeUrlBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.typeUrl_ = value;
            onChanged();
            return this;
        }

        public ByteString getValue() {
            return this.value_;
        }

        public Builder setValue(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.value_ = value;
            onChanged();
            return this;
        }

        public Builder clearValue() {
            this.value_ = Any.getDefaultInstance().getValue();
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

    private Any(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private Any() {
        this.memoizedIsInitialized = -1;
        this.typeUrl_ = "";
        this.value_ = ByteString.EMPTY;
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Any(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    case 10:
                        this.typeUrl_ = input.readStringRequireUtf8();
                        break;
                    case 18:
                        this.value_ = input.readBytes();
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
        return AnyProto.internal_static_google_protobuf_Any_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return AnyProto.internal_static_google_protobuf_Any_fieldAccessorTable.ensureFieldAccessorsInitialized(Any.class, Builder.class);
    }

    private static String getTypeUrl(String typeUrlPrefix, Descriptor descriptor) {
        if (typeUrlPrefix.endsWith("/")) {
            return typeUrlPrefix + descriptor.getFullName();
        }
        return typeUrlPrefix + "/" + descriptor.getFullName();
    }

    private static String getTypeNameFromTypeUrl(String typeUrl) {
        int pos = typeUrl.lastIndexOf(47);
        return pos == -1 ? "" : typeUrl.substring(pos + 1);
    }

    public static <T extends Message> Any pack(T message) {
        return newBuilder().setTypeUrl(getTypeUrl("type.googleapis.com", message.getDescriptorForType())).setValue(message.toByteString()).build();
    }

    public static <T extends Message> Any pack(T message, String typeUrlPrefix) {
        return newBuilder().setTypeUrl(getTypeUrl(typeUrlPrefix, message.getDescriptorForType())).setValue(message.toByteString()).build();
    }

    public <T extends Message> boolean is(Class<T> clazz) {
        return getTypeNameFromTypeUrl(getTypeUrl()).equals(((Message) Internal.getDefaultInstance(clazz)).getDescriptorForType().getFullName());
    }

    public <T extends Message> T unpack(Class<T> clazz) throws InvalidProtocolBufferException {
        if (!is(clazz)) {
            throw new InvalidProtocolBufferException("Type of the Any message does not match the given class.");
        } else if (this.cachedUnpackValue != null) {
            return this.cachedUnpackValue;
        } else {
            T result = (Message) ((Message) Internal.getDefaultInstance(clazz)).getParserForType().parseFrom(getValue());
            this.cachedUnpackValue = result;
            return result;
        }
    }

    public String getTypeUrl() {
        Object ref = this.typeUrl_;
        if (ref instanceof String) {
            return (String) ref;
        }
        String s = ((ByteString) ref).toStringUtf8();
        this.typeUrl_ = s;
        return s;
    }

    public ByteString getTypeUrlBytes() {
        Object ref = this.typeUrl_;
        if (!(ref instanceof String)) {
            return (ByteString) ref;
        }
        ByteString b = ByteString.copyFromUtf8((String) ref);
        this.typeUrl_ = b;
        return b;
    }

    public ByteString getValue() {
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
        if (!getTypeUrlBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 1, this.typeUrl_);
        }
        if (!this.value_.isEmpty()) {
            output.writeBytes(2, this.value_);
        }
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if (!getTypeUrlBytes().isEmpty()) {
            size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.typeUrl_);
        }
        if (!this.value_.isEmpty()) {
            size2 += CodedOutputStream.computeBytesSize(2, this.value_);
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
        if (!(obj instanceof Any)) {
            return super.equals(obj);
        }
        Any other = (Any) obj;
        if (1 == 0 || !getTypeUrl().equals(other.getTypeUrl())) {
            result = false;
        } else {
            result = true;
        }
        if (!result || !getValue().equals(other.getValue())) {
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
        int hash = ((((((((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + getTypeUrl().hashCode()) * 37) + 2) * 53) + getValue().hashCode()) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash;
        return hash;
    }

    public static Any parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Any) PARSER.parseFrom(data);
    }

    public static Any parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Any) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Any parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Any) PARSER.parseFrom(data);
    }

    public static Any parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Any) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Any parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Any) PARSER.parseFrom(data);
    }

    public static Any parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Any) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Any parseFrom(InputStream input) throws IOException {
        return (Any) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Any parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Any) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Any parseDelimitedFrom(InputStream input) throws IOException {
        return (Any) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Any parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Any) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Any parseFrom(CodedInputStream input) throws IOException {
        return (Any) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Any parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Any) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Any prototype) {
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

    public static Any getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Any> parser() {
        return PARSER;
    }

    public Parser<Any> getParserForType() {
        return PARSER;
    }

    public Any getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
