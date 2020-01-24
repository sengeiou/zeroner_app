package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Option extends GeneratedMessageV3 implements OptionOrBuilder {
    private static final Option DEFAULT_INSTANCE = new Option();
    public static final int NAME_FIELD_NUMBER = 1;
    /* access modifiers changed from: private */
    public static final Parser<Option> PARSER = new AbstractParser<Option>() {
        public Option parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Option(input, extensionRegistry);
        }
    };
    public static final int VALUE_FIELD_NUMBER = 2;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public volatile Object name_;
    /* access modifiers changed from: private */
    public Any value_;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements OptionOrBuilder {
        private Object name_;
        private SingleFieldBuilderV3<Any, com.google.protobuf.Any.Builder, AnyOrBuilder> valueBuilder_;
        private Any value_;

        public static final Descriptor getDescriptor() {
            return TypeProto.internal_static_google_protobuf_Option_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return TypeProto.internal_static_google_protobuf_Option_fieldAccessorTable.ensureFieldAccessorsInitialized(Option.class, Builder.class);
        }

        private Builder() {
            this.name_ = "";
            this.value_ = null;
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.value_ = null;
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
            }
        }

        public Builder clear() {
            super.clear();
            this.name_ = "";
            if (this.valueBuilder_ == null) {
                this.value_ = null;
            } else {
                this.value_ = null;
                this.valueBuilder_ = null;
            }
            return this;
        }

        public Descriptor getDescriptorForType() {
            return TypeProto.internal_static_google_protobuf_Option_descriptor;
        }

        public Option getDefaultInstanceForType() {
            return Option.getDefaultInstance();
        }

        public Option build() {
            Option result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public Option buildPartial() {
            Option result = new Option((com.google.protobuf.GeneratedMessageV3.Builder) this);
            result.name_ = this.name_;
            if (this.valueBuilder_ == null) {
                result.value_ = this.value_;
            } else {
                result.value_ = (Any) this.valueBuilder_.build();
            }
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
            if (other instanceof Option) {
                return mergeFrom((Option) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Option other) {
            if (other != Option.getDefaultInstance()) {
                if (!other.getName().isEmpty()) {
                    this.name_ = other.name_;
                    onChanged();
                }
                if (other.hasValue()) {
                    mergeValue(other.getValue());
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
            Option parsedMessage = null;
            try {
                Option parsedMessage2 = (Option) Option.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                Option parsedMessage3 = (Option) e.getUnfinishedMessage();
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
            this.name_ = Option.getDefaultInstance().getName();
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

        public boolean hasValue() {
            return (this.valueBuilder_ == null && this.value_ == null) ? false : true;
        }

        public Any getValue() {
            if (this.valueBuilder_ == null) {
                return this.value_ == null ? Any.getDefaultInstance() : this.value_;
            }
            return (Any) this.valueBuilder_.getMessage();
        }

        public Builder setValue(Any value) {
            if (this.valueBuilder_ != null) {
                this.valueBuilder_.setMessage(value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                this.value_ = value;
                onChanged();
            }
            return this;
        }

        public Builder setValue(com.google.protobuf.Any.Builder builderForValue) {
            if (this.valueBuilder_ == null) {
                this.value_ = builderForValue.build();
                onChanged();
            } else {
                this.valueBuilder_.setMessage(builderForValue.build());
            }
            return this;
        }

        public Builder mergeValue(Any value) {
            if (this.valueBuilder_ == null) {
                if (this.value_ != null) {
                    this.value_ = Any.newBuilder(this.value_).mergeFrom(value).buildPartial();
                } else {
                    this.value_ = value;
                }
                onChanged();
            } else {
                this.valueBuilder_.mergeFrom(value);
            }
            return this;
        }

        public Builder clearValue() {
            if (this.valueBuilder_ == null) {
                this.value_ = null;
                onChanged();
            } else {
                this.value_ = null;
                this.valueBuilder_ = null;
            }
            return this;
        }

        public com.google.protobuf.Any.Builder getValueBuilder() {
            onChanged();
            return (com.google.protobuf.Any.Builder) getValueFieldBuilder().getBuilder();
        }

        public AnyOrBuilder getValueOrBuilder() {
            if (this.valueBuilder_ != null) {
                return (AnyOrBuilder) this.valueBuilder_.getMessageOrBuilder();
            }
            return this.value_ == null ? Any.getDefaultInstance() : this.value_;
        }

        private SingleFieldBuilderV3<Any, com.google.protobuf.Any.Builder, AnyOrBuilder> getValueFieldBuilder() {
            if (this.valueBuilder_ == null) {
                this.valueBuilder_ = new SingleFieldBuilderV3<>(getValue(), getParentForChildren(), isClean());
                this.value_ = null;
            }
            return this.valueBuilder_;
        }

        public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.setUnknownFieldsProto3(unknownFields);
        }

        public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.mergeUnknownFields(unknownFields);
        }
    }

    private Option(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private Option() {
        this.memoizedIsInitialized = -1;
        this.name_ = "";
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Option(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        com.google.protobuf.Any.Builder subBuilder = null;
                        if (this.value_ != null) {
                            subBuilder = this.value_.toBuilder();
                        }
                        this.value_ = (Any) input.readMessage(Any.parser(), extensionRegistry);
                        if (subBuilder == null) {
                            break;
                        } else {
                            subBuilder.mergeFrom(this.value_);
                            this.value_ = subBuilder.buildPartial();
                            break;
                        }
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
        return TypeProto.internal_static_google_protobuf_Option_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return TypeProto.internal_static_google_protobuf_Option_fieldAccessorTable.ensureFieldAccessorsInitialized(Option.class, Builder.class);
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

    public boolean hasValue() {
        return this.value_ != null;
    }

    public Any getValue() {
        return this.value_ == null ? Any.getDefaultInstance() : this.value_;
    }

    public AnyOrBuilder getValueOrBuilder() {
        return getValue();
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
        if (this.value_ != null) {
            output.writeMessage(2, getValue());
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
        if (this.value_ != null) {
            size2 += CodedOutputStream.computeMessageSize(2, getValue());
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
        if (!(obj instanceof Option)) {
            return super.equals(obj);
        }
        Option other = (Option) obj;
        if (!(1 != 0 && getName().equals(other.getName())) || hasValue() != other.hasValue()) {
            result = false;
        } else {
            result = true;
        }
        if (hasValue()) {
            if (!result || !getValue().equals(other.getValue())) {
                result = false;
            } else {
                result = true;
            }
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
        int hash = ((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + getName().hashCode();
        if (hasValue()) {
            hash = (((hash * 37) + 2) * 53) + getValue().hashCode();
        }
        int hash2 = (hash * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash2;
        return hash2;
    }

    public static Option parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Option) PARSER.parseFrom(data);
    }

    public static Option parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Option) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Option parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Option) PARSER.parseFrom(data);
    }

    public static Option parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Option) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Option parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Option) PARSER.parseFrom(data);
    }

    public static Option parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Option) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Option parseFrom(InputStream input) throws IOException {
        return (Option) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Option parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Option) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Option parseDelimitedFrom(InputStream input) throws IOException {
        return (Option) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Option parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Option) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Option parseFrom(CodedInputStream input) throws IOException {
        return (Option) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Option parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Option) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Option prototype) {
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

    public static Option getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Option> parser() {
        return PARSER;
    }

    public Parser<Option> getParserForType() {
        return PARSER;
    }

    public Option getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
