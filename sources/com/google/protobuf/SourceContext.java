package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class SourceContext extends GeneratedMessageV3 implements SourceContextOrBuilder {
    private static final SourceContext DEFAULT_INSTANCE = new SourceContext();
    public static final int FILE_NAME_FIELD_NUMBER = 1;
    /* access modifiers changed from: private */
    public static final Parser<SourceContext> PARSER = new AbstractParser<SourceContext>() {
        public SourceContext parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new SourceContext(input, extensionRegistry);
        }
    };
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public volatile Object fileName_;
    private byte memoizedIsInitialized;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements SourceContextOrBuilder {
        private Object fileName_;

        public static final Descriptor getDescriptor() {
            return SourceContextProto.internal_static_google_protobuf_SourceContext_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return SourceContextProto.internal_static_google_protobuf_SourceContext_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceContext.class, Builder.class);
        }

        private Builder() {
            this.fileName_ = "";
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            this.fileName_ = "";
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
            }
        }

        public Builder clear() {
            super.clear();
            this.fileName_ = "";
            return this;
        }

        public Descriptor getDescriptorForType() {
            return SourceContextProto.internal_static_google_protobuf_SourceContext_descriptor;
        }

        public SourceContext getDefaultInstanceForType() {
            return SourceContext.getDefaultInstance();
        }

        public SourceContext build() {
            SourceContext result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public SourceContext buildPartial() {
            SourceContext result = new SourceContext((com.google.protobuf.GeneratedMessageV3.Builder) this);
            result.fileName_ = this.fileName_;
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
            if (other instanceof SourceContext) {
                return mergeFrom((SourceContext) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(SourceContext other) {
            if (other != SourceContext.getDefaultInstance()) {
                if (!other.getFileName().isEmpty()) {
                    this.fileName_ = other.fileName_;
                    onChanged();
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
            SourceContext parsedMessage = null;
            try {
                SourceContext parsedMessage2 = (SourceContext) SourceContext.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                SourceContext parsedMessage3 = (SourceContext) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                throw th;
            }
        }

        public String getFileName() {
            Object ref = this.fileName_;
            if (ref instanceof String) {
                return (String) ref;
            }
            String s = ((ByteString) ref).toStringUtf8();
            this.fileName_ = s;
            return s;
        }

        public ByteString getFileNameBytes() {
            Object ref = this.fileName_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.fileName_ = b;
            return b;
        }

        public Builder setFileName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.fileName_ = value;
            onChanged();
            return this;
        }

        public Builder clearFileName() {
            this.fileName_ = SourceContext.getDefaultInstance().getFileName();
            onChanged();
            return this;
        }

        public Builder setFileNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.fileName_ = value;
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

    private SourceContext(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private SourceContext() {
        this.memoizedIsInitialized = -1;
        this.fileName_ = "";
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private SourceContext(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.fileName_ = input.readStringRequireUtf8();
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
        return SourceContextProto.internal_static_google_protobuf_SourceContext_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return SourceContextProto.internal_static_google_protobuf_SourceContext_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceContext.class, Builder.class);
    }

    public String getFileName() {
        Object ref = this.fileName_;
        if (ref instanceof String) {
            return (String) ref;
        }
        String s = ((ByteString) ref).toStringUtf8();
        this.fileName_ = s;
        return s;
    }

    public ByteString getFileNameBytes() {
        Object ref = this.fileName_;
        if (!(ref instanceof String)) {
            return (ByteString) ref;
        }
        ByteString b = ByteString.copyFromUtf8((String) ref);
        this.fileName_ = b;
        return b;
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
        if (!getFileNameBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 1, this.fileName_);
        }
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if (!getFileNameBytes().isEmpty()) {
            size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.fileName_);
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
        if (!(obj instanceof SourceContext)) {
            return super.equals(obj);
        }
        SourceContext other = (SourceContext) obj;
        if (1 == 0 || !getFileName().equals(other.getFileName())) {
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
        int hash = ((((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + getFileName().hashCode()) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash;
        return hash;
    }

    public static SourceContext parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (SourceContext) PARSER.parseFrom(data);
    }

    public static SourceContext parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (SourceContext) PARSER.parseFrom(data, extensionRegistry);
    }

    public static SourceContext parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (SourceContext) PARSER.parseFrom(data);
    }

    public static SourceContext parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (SourceContext) PARSER.parseFrom(data, extensionRegistry);
    }

    public static SourceContext parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (SourceContext) PARSER.parseFrom(data);
    }

    public static SourceContext parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (SourceContext) PARSER.parseFrom(data, extensionRegistry);
    }

    public static SourceContext parseFrom(InputStream input) throws IOException {
        return (SourceContext) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static SourceContext parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (SourceContext) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static SourceContext parseDelimitedFrom(InputStream input) throws IOException {
        return (SourceContext) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static SourceContext parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (SourceContext) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static SourceContext parseFrom(CodedInputStream input) throws IOException {
        return (SourceContext) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static SourceContext parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (SourceContext) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(SourceContext prototype) {
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

    public static SourceContext getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<SourceContext> parser() {
        return PARSER;
    }

    public Parser<SourceContext> getParserForType() {
        return PARSER;
    }

    public SourceContext getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
