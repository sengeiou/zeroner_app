package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EnumValue extends GeneratedMessageV3 implements EnumValueOrBuilder {
    private static final EnumValue DEFAULT_INSTANCE = new EnumValue();
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int NUMBER_FIELD_NUMBER = 2;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    /* access modifiers changed from: private */
    public static final Parser<EnumValue> PARSER = new AbstractParser<EnumValue>() {
        public EnumValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new EnumValue(input, extensionRegistry);
        }
    };
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public int bitField0_;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public volatile Object name_;
    /* access modifiers changed from: private */
    public int number_;
    /* access modifiers changed from: private */
    public List<Option> options_;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements EnumValueOrBuilder {
        private int bitField0_;
        private Object name_;
        private int number_;
        private RepeatedFieldBuilderV3<Option, com.google.protobuf.Option.Builder, OptionOrBuilder> optionsBuilder_;
        private List<Option> options_;

        public static final Descriptor getDescriptor() {
            return TypeProto.internal_static_google_protobuf_EnumValue_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return TypeProto.internal_static_google_protobuf_EnumValue_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValue.class, Builder.class);
        }

        private Builder() {
            this.name_ = "";
            this.options_ = Collections.emptyList();
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.options_ = Collections.emptyList();
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                getOptionsFieldBuilder();
            }
        }

        public Builder clear() {
            super.clear();
            this.name_ = "";
            this.number_ = 0;
            if (this.optionsBuilder_ == null) {
                this.options_ = Collections.emptyList();
                this.bitField0_ &= -5;
            } else {
                this.optionsBuilder_.clear();
            }
            return this;
        }

        public Descriptor getDescriptorForType() {
            return TypeProto.internal_static_google_protobuf_EnumValue_descriptor;
        }

        public EnumValue getDefaultInstanceForType() {
            return EnumValue.getDefaultInstance();
        }

        public EnumValue build() {
            EnumValue result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public EnumValue buildPartial() {
            EnumValue result = new EnumValue((com.google.protobuf.GeneratedMessageV3.Builder) this);
            int i = this.bitField0_;
            result.name_ = this.name_;
            result.number_ = this.number_;
            if (this.optionsBuilder_ == null) {
                if ((this.bitField0_ & 4) == 4) {
                    this.options_ = Collections.unmodifiableList(this.options_);
                    this.bitField0_ &= -5;
                }
                result.options_ = this.options_;
            } else {
                result.options_ = this.optionsBuilder_.build();
            }
            result.bitField0_ = 0;
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
            if (other instanceof EnumValue) {
                return mergeFrom((EnumValue) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(EnumValue other) {
            RepeatedFieldBuilderV3<Option, com.google.protobuf.Option.Builder, OptionOrBuilder> repeatedFieldBuilderV3 = null;
            if (other != EnumValue.getDefaultInstance()) {
                if (!other.getName().isEmpty()) {
                    this.name_ = other.name_;
                    onChanged();
                }
                if (other.getNumber() != 0) {
                    setNumber(other.getNumber());
                }
                if (this.optionsBuilder_ == null) {
                    if (!other.options_.isEmpty()) {
                        if (this.options_.isEmpty()) {
                            this.options_ = other.options_;
                            this.bitField0_ &= -5;
                        } else {
                            ensureOptionsIsMutable();
                            this.options_.addAll(other.options_);
                        }
                        onChanged();
                    }
                } else if (!other.options_.isEmpty()) {
                    if (this.optionsBuilder_.isEmpty()) {
                        this.optionsBuilder_.dispose();
                        this.optionsBuilder_ = null;
                        this.options_ = other.options_;
                        this.bitField0_ &= -5;
                        if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                            repeatedFieldBuilderV3 = getOptionsFieldBuilder();
                        }
                        this.optionsBuilder_ = repeatedFieldBuilderV3;
                    } else {
                        this.optionsBuilder_.addAllMessages(other.options_);
                    }
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
            EnumValue parsedMessage = null;
            try {
                EnumValue parsedMessage2 = (EnumValue) EnumValue.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                EnumValue parsedMessage3 = (EnumValue) e.getUnfinishedMessage();
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
            this.name_ = EnumValue.getDefaultInstance().getName();
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

        public int getNumber() {
            return this.number_;
        }

        public Builder setNumber(int value) {
            this.number_ = value;
            onChanged();
            return this;
        }

        public Builder clearNumber() {
            this.number_ = 0;
            onChanged();
            return this;
        }

        private void ensureOptionsIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
                this.options_ = new ArrayList(this.options_);
                this.bitField0_ |= 4;
            }
        }

        public List<Option> getOptionsList() {
            if (this.optionsBuilder_ == null) {
                return Collections.unmodifiableList(this.options_);
            }
            return this.optionsBuilder_.getMessageList();
        }

        public int getOptionsCount() {
            if (this.optionsBuilder_ == null) {
                return this.options_.size();
            }
            return this.optionsBuilder_.getCount();
        }

        public Option getOptions(int index) {
            if (this.optionsBuilder_ == null) {
                return (Option) this.options_.get(index);
            }
            return (Option) this.optionsBuilder_.getMessage(index);
        }

        public Builder setOptions(int index, Option value) {
            if (this.optionsBuilder_ != null) {
                this.optionsBuilder_.setMessage(index, value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureOptionsIsMutable();
                this.options_.set(index, value);
                onChanged();
            }
            return this;
        }

        public Builder setOptions(int index, com.google.protobuf.Option.Builder builderForValue) {
            if (this.optionsBuilder_ == null) {
                ensureOptionsIsMutable();
                this.options_.set(index, builderForValue.build());
                onChanged();
            } else {
                this.optionsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addOptions(Option value) {
            if (this.optionsBuilder_ != null) {
                this.optionsBuilder_.addMessage(value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureOptionsIsMutable();
                this.options_.add(value);
                onChanged();
            }
            return this;
        }

        public Builder addOptions(int index, Option value) {
            if (this.optionsBuilder_ != null) {
                this.optionsBuilder_.addMessage(index, value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureOptionsIsMutable();
                this.options_.add(index, value);
                onChanged();
            }
            return this;
        }

        public Builder addOptions(com.google.protobuf.Option.Builder builderForValue) {
            if (this.optionsBuilder_ == null) {
                ensureOptionsIsMutable();
                this.options_.add(builderForValue.build());
                onChanged();
            } else {
                this.optionsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addOptions(int index, com.google.protobuf.Option.Builder builderForValue) {
            if (this.optionsBuilder_ == null) {
                ensureOptionsIsMutable();
                this.options_.add(index, builderForValue.build());
                onChanged();
            } else {
                this.optionsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> values) {
            if (this.optionsBuilder_ == null) {
                ensureOptionsIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.options_);
                onChanged();
            } else {
                this.optionsBuilder_.addAllMessages(values);
            }
            return this;
        }

        public Builder clearOptions() {
            if (this.optionsBuilder_ == null) {
                this.options_ = Collections.emptyList();
                this.bitField0_ &= -5;
                onChanged();
            } else {
                this.optionsBuilder_.clear();
            }
            return this;
        }

        public Builder removeOptions(int index) {
            if (this.optionsBuilder_ == null) {
                ensureOptionsIsMutable();
                this.options_.remove(index);
                onChanged();
            } else {
                this.optionsBuilder_.remove(index);
            }
            return this;
        }

        public com.google.protobuf.Option.Builder getOptionsBuilder(int index) {
            return (com.google.protobuf.Option.Builder) getOptionsFieldBuilder().getBuilder(index);
        }

        public OptionOrBuilder getOptionsOrBuilder(int index) {
            if (this.optionsBuilder_ == null) {
                return (OptionOrBuilder) this.options_.get(index);
            }
            return (OptionOrBuilder) this.optionsBuilder_.getMessageOrBuilder(index);
        }

        public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
            if (this.optionsBuilder_ != null) {
                return this.optionsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.options_);
        }

        public com.google.protobuf.Option.Builder addOptionsBuilder() {
            return (com.google.protobuf.Option.Builder) getOptionsFieldBuilder().addBuilder(Option.getDefaultInstance());
        }

        public com.google.protobuf.Option.Builder addOptionsBuilder(int index) {
            return (com.google.protobuf.Option.Builder) getOptionsFieldBuilder().addBuilder(index, Option.getDefaultInstance());
        }

        public List<com.google.protobuf.Option.Builder> getOptionsBuilderList() {
            return getOptionsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Option, com.google.protobuf.Option.Builder, OptionOrBuilder> getOptionsFieldBuilder() {
            if (this.optionsBuilder_ == null) {
                this.optionsBuilder_ = new RepeatedFieldBuilderV3<>(this.options_, (this.bitField0_ & 4) == 4, getParentForChildren(), isClean());
                this.options_ = null;
            }
            return this.optionsBuilder_;
        }

        public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.setUnknownFieldsProto3(unknownFields);
        }

        public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.mergeUnknownFields(unknownFields);
        }
    }

    private EnumValue(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private EnumValue() {
        this.memoizedIsInitialized = -1;
        this.name_ = "";
        this.number_ = 0;
        this.options_ = Collections.emptyList();
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private EnumValue(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.name_ = input.readStringRequireUtf8();
                        break;
                    case 16:
                        this.number_ = input.readInt32();
                        break;
                    case 26:
                        if ((mutable_bitField0_ & 4) != 4) {
                            this.options_ = new ArrayList();
                            mutable_bitField0_ |= 4;
                        }
                        this.options_.add(input.readMessage(Option.parser(), extensionRegistry));
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
                if ((mutable_bitField0_ & 4) == 4) {
                    this.options_ = Collections.unmodifiableList(this.options_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
                throw th;
            }
        }
        if ((mutable_bitField0_ & 4) == 4) {
            this.options_ = Collections.unmodifiableList(this.options_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
    }

    public static final Descriptor getDescriptor() {
        return TypeProto.internal_static_google_protobuf_EnumValue_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return TypeProto.internal_static_google_protobuf_EnumValue_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValue.class, Builder.class);
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

    public int getNumber() {
        return this.number_;
    }

    public List<Option> getOptionsList() {
        return this.options_;
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }

    public int getOptionsCount() {
        return this.options_.size();
    }

    public Option getOptions(int index) {
        return (Option) this.options_.get(index);
    }

    public OptionOrBuilder getOptionsOrBuilder(int index) {
        return (OptionOrBuilder) this.options_.get(index);
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
        if (this.number_ != 0) {
            output.writeInt32(2, this.number_);
        }
        for (int i = 0; i < this.options_.size(); i++) {
            output.writeMessage(3, (MessageLite) this.options_.get(i));
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
        if (this.number_ != 0) {
            size2 += CodedOutputStream.computeInt32Size(2, this.number_);
        }
        for (int i = 0; i < this.options_.size(); i++) {
            size2 += CodedOutputStream.computeMessageSize(3, (MessageLite) this.options_.get(i));
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
        if (!(obj instanceof EnumValue)) {
            return super.equals(obj);
        }
        EnumValue other = (EnumValue) obj;
        if (!(1 != 0 && getName().equals(other.getName())) || getNumber() != other.getNumber()) {
            result = false;
        } else {
            result = true;
        }
        if (!result || !getOptionsList().equals(other.getOptionsList())) {
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
        int hash = ((((((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + getName().hashCode()) * 37) + 2) * 53) + getNumber();
        if (getOptionsCount() > 0) {
            hash = (((hash * 37) + 3) * 53) + getOptionsList().hashCode();
        }
        int hash2 = (hash * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash2;
        return hash2;
    }

    public static EnumValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (EnumValue) PARSER.parseFrom(data);
    }

    public static EnumValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (EnumValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static EnumValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (EnumValue) PARSER.parseFrom(data);
    }

    public static EnumValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (EnumValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static EnumValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (EnumValue) PARSER.parseFrom(data);
    }

    public static EnumValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (EnumValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static EnumValue parseFrom(InputStream input) throws IOException {
        return (EnumValue) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static EnumValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (EnumValue) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static EnumValue parseDelimitedFrom(InputStream input) throws IOException {
        return (EnumValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static EnumValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (EnumValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static EnumValue parseFrom(CodedInputStream input) throws IOException {
        return (EnumValue) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static EnumValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (EnumValue) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(EnumValue prototype) {
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

    public static EnumValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<EnumValue> parser() {
        return PARSER;
    }

    public Parser<EnumValue> getParserForType() {
        return PARSER;
    }

    public EnumValue getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
