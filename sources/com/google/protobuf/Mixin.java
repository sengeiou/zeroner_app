package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Mixin extends GeneratedMessageV3 implements MixinOrBuilder {
    private static final Mixin DEFAULT_INSTANCE = new Mixin();
    public static final int NAME_FIELD_NUMBER = 1;
    /* access modifiers changed from: private */
    public static final Parser<Mixin> PARSER = new AbstractParser<Mixin>() {
        public Mixin parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Mixin(input, extensionRegistry);
        }
    };
    public static final int ROOT_FIELD_NUMBER = 2;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public volatile Object name_;
    /* access modifiers changed from: private */
    public volatile Object root_;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MixinOrBuilder {
        private Object name_;
        private Object root_;

        public static final Descriptor getDescriptor() {
            return ApiProto.internal_static_google_protobuf_Mixin_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return ApiProto.internal_static_google_protobuf_Mixin_fieldAccessorTable.ensureFieldAccessorsInitialized(Mixin.class, Builder.class);
        }

        private Builder() {
            this.name_ = "";
            this.root_ = "";
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.root_ = "";
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
            }
        }

        public Builder clear() {
            super.clear();
            this.name_ = "";
            this.root_ = "";
            return this;
        }

        public Descriptor getDescriptorForType() {
            return ApiProto.internal_static_google_protobuf_Mixin_descriptor;
        }

        public Mixin getDefaultInstanceForType() {
            return Mixin.getDefaultInstance();
        }

        public Mixin build() {
            Mixin result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public Mixin buildPartial() {
            Mixin result = new Mixin((com.google.protobuf.GeneratedMessageV3.Builder) this);
            result.name_ = this.name_;
            result.root_ = this.root_;
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
            if (other instanceof Mixin) {
                return mergeFrom((Mixin) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Mixin other) {
            if (other != Mixin.getDefaultInstance()) {
                if (!other.getName().isEmpty()) {
                    this.name_ = other.name_;
                    onChanged();
                }
                if (!other.getRoot().isEmpty()) {
                    this.root_ = other.root_;
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
            Mixin parsedMessage = null;
            try {
                Mixin parsedMessage2 = (Mixin) Mixin.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                Mixin parsedMessage3 = (Mixin) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                throw th;
            }
        }

        public String getName() {
            Object ref = this.name_;
            if (ref instanceof String) {
                return (String) ref;
            }
            String s = ((ByteString) ref).toStringUtf8();
            this.name_ = s;
            return s;
        }

        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.name_ = b;
            return b;
        }

        public Builder setName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.name_ = value;
            onChanged();
            return this;
        }

        public Builder clearName() {
            this.name_ = Mixin.getDefaultInstance().getName();
            onChanged();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.name_ = value;
            onChanged();
            return this;
        }

        public String getRoot() {
            Object ref = this.root_;
            if (ref instanceof String) {
                return (String) ref;
            }
            String s = ((ByteString) ref).toStringUtf8();
            this.root_ = s;
            return s;
        }

        public ByteString getRootBytes() {
            Object ref = this.root_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.root_ = b;
            return b;
        }

        public Builder setRoot(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.root_ = value;
            onChanged();
            return this;
        }

        public Builder clearRoot() {
            this.root_ = Mixin.getDefaultInstance().getRoot();
            onChanged();
            return this;
        }

        public Builder setRootBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.root_ = value;
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

    private Mixin(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private Mixin() {
        this.memoizedIsInitialized = -1;
        this.name_ = "";
        this.root_ = "";
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Mixin(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.name_ = input.readStringRequireUtf8();
                        break;
                    case 18:
                        this.root_ = input.readStringRequireUtf8();
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
        return ApiProto.internal_static_google_protobuf_Mixin_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return ApiProto.internal_static_google_protobuf_Mixin_fieldAccessorTable.ensureFieldAccessorsInitialized(Mixin.class, Builder.class);
    }

    public String getName() {
        Object ref = this.name_;
        if (ref instanceof String) {
            return (String) ref;
        }
        String s = ((ByteString) ref).toStringUtf8();
        this.name_ = s;
        return s;
    }

    public ByteString getNameBytes() {
        Object ref = this.name_;
        if (!(ref instanceof String)) {
            return (ByteString) ref;
        }
        ByteString b = ByteString.copyFromUtf8((String) ref);
        this.name_ = b;
        return b;
    }

    public String getRoot() {
        Object ref = this.root_;
        if (ref instanceof String) {
            return (String) ref;
        }
        String s = ((ByteString) ref).toStringUtf8();
        this.root_ = s;
        return s;
    }

    public ByteString getRootBytes() {
        Object ref = this.root_;
        if (!(ref instanceof String)) {
            return (ByteString) ref;
        }
        ByteString b = ByteString.copyFromUtf8((String) ref);
        this.root_ = b;
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
        if (!getNameBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 1, this.name_);
        }
        if (!getRootBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 2, this.root_);
        }
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if (!getNameBytes().isEmpty()) {
            size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.name_);
        }
        if (!getRootBytes().isEmpty()) {
            size2 += GeneratedMessageV3.computeStringSize(2, this.root_);
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
        if (!(obj instanceof Mixin)) {
            return super.equals(obj);
        }
        Mixin other = (Mixin) obj;
        if (1 == 0 || !getName().equals(other.getName())) {
            result = false;
        } else {
            result = true;
        }
        if (!result || !getRoot().equals(other.getRoot())) {
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
        int hash = ((((((((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + getName().hashCode()) * 37) + 2) * 53) + getRoot().hashCode()) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash;
        return hash;
    }

    public static Mixin parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Mixin) PARSER.parseFrom(data);
    }

    public static Mixin parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Mixin) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Mixin parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Mixin) PARSER.parseFrom(data);
    }

    public static Mixin parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Mixin) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Mixin parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Mixin) PARSER.parseFrom(data);
    }

    public static Mixin parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Mixin) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Mixin parseFrom(InputStream input) throws IOException {
        return (Mixin) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Mixin parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Mixin) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Mixin parseDelimitedFrom(InputStream input) throws IOException {
        return (Mixin) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Mixin parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Mixin) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Mixin parseFrom(CodedInputStream input) throws IOException {
        return (Mixin) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Mixin parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Mixin) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Mixin prototype) {
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

    public static Mixin getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Mixin> parser() {
        return PARSER;
    }

    public Parser<Mixin> getParserForType() {
        return PARSER;
    }

    public Mixin getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
