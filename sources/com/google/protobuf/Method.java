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

public final class Method extends GeneratedMessageV3 implements MethodOrBuilder {
    private static final Method DEFAULT_INSTANCE = new Method();
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int OPTIONS_FIELD_NUMBER = 6;
    /* access modifiers changed from: private */
    public static final Parser<Method> PARSER = new AbstractParser<Method>() {
        public Method parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Method(input, extensionRegistry);
        }
    };
    public static final int REQUEST_STREAMING_FIELD_NUMBER = 3;
    public static final int REQUEST_TYPE_URL_FIELD_NUMBER = 2;
    public static final int RESPONSE_STREAMING_FIELD_NUMBER = 5;
    public static final int RESPONSE_TYPE_URL_FIELD_NUMBER = 4;
    public static final int SYNTAX_FIELD_NUMBER = 7;
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public int bitField0_;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public volatile Object name_;
    /* access modifiers changed from: private */
    public List<Option> options_;
    /* access modifiers changed from: private */
    public boolean requestStreaming_;
    /* access modifiers changed from: private */
    public volatile Object requestTypeUrl_;
    /* access modifiers changed from: private */
    public boolean responseStreaming_;
    /* access modifiers changed from: private */
    public volatile Object responseTypeUrl_;
    /* access modifiers changed from: private */
    public int syntax_;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements MethodOrBuilder {
        private int bitField0_;
        private Object name_;
        private RepeatedFieldBuilderV3<Option, com.google.protobuf.Option.Builder, OptionOrBuilder> optionsBuilder_;
        private List<Option> options_;
        private boolean requestStreaming_;
        private Object requestTypeUrl_;
        private boolean responseStreaming_;
        private Object responseTypeUrl_;
        private int syntax_;

        public static final Descriptor getDescriptor() {
            return ApiProto.internal_static_google_protobuf_Method_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return ApiProto.internal_static_google_protobuf_Method_fieldAccessorTable.ensureFieldAccessorsInitialized(Method.class, Builder.class);
        }

        private Builder() {
            this.name_ = "";
            this.requestTypeUrl_ = "";
            this.responseTypeUrl_ = "";
            this.options_ = Collections.emptyList();
            this.syntax_ = 0;
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.requestTypeUrl_ = "";
            this.responseTypeUrl_ = "";
            this.options_ = Collections.emptyList();
            this.syntax_ = 0;
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
            this.requestTypeUrl_ = "";
            this.requestStreaming_ = false;
            this.responseTypeUrl_ = "";
            this.responseStreaming_ = false;
            if (this.optionsBuilder_ == null) {
                this.options_ = Collections.emptyList();
                this.bitField0_ &= -33;
            } else {
                this.optionsBuilder_.clear();
            }
            this.syntax_ = 0;
            return this;
        }

        public Descriptor getDescriptorForType() {
            return ApiProto.internal_static_google_protobuf_Method_descriptor;
        }

        public Method getDefaultInstanceForType() {
            return Method.getDefaultInstance();
        }

        public Method build() {
            Method result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public Method buildPartial() {
            Method result = new Method((com.google.protobuf.GeneratedMessageV3.Builder) this);
            int i = this.bitField0_;
            result.name_ = this.name_;
            result.requestTypeUrl_ = this.requestTypeUrl_;
            result.requestStreaming_ = this.requestStreaming_;
            result.responseTypeUrl_ = this.responseTypeUrl_;
            result.responseStreaming_ = this.responseStreaming_;
            if (this.optionsBuilder_ == null) {
                if ((this.bitField0_ & 32) == 32) {
                    this.options_ = Collections.unmodifiableList(this.options_);
                    this.bitField0_ &= -33;
                }
                result.options_ = this.options_;
            } else {
                result.options_ = this.optionsBuilder_.build();
            }
            result.syntax_ = this.syntax_;
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
            if (other instanceof Method) {
                return mergeFrom((Method) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Method other) {
            RepeatedFieldBuilderV3<Option, com.google.protobuf.Option.Builder, OptionOrBuilder> repeatedFieldBuilderV3 = null;
            if (other != Method.getDefaultInstance()) {
                if (!other.getName().isEmpty()) {
                    this.name_ = other.name_;
                    onChanged();
                }
                if (!other.getRequestTypeUrl().isEmpty()) {
                    this.requestTypeUrl_ = other.requestTypeUrl_;
                    onChanged();
                }
                if (other.getRequestStreaming()) {
                    setRequestStreaming(other.getRequestStreaming());
                }
                if (!other.getResponseTypeUrl().isEmpty()) {
                    this.responseTypeUrl_ = other.responseTypeUrl_;
                    onChanged();
                }
                if (other.getResponseStreaming()) {
                    setResponseStreaming(other.getResponseStreaming());
                }
                if (this.optionsBuilder_ == null) {
                    if (!other.options_.isEmpty()) {
                        if (this.options_.isEmpty()) {
                            this.options_ = other.options_;
                            this.bitField0_ &= -33;
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
                        this.bitField0_ &= -33;
                        if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                            repeatedFieldBuilderV3 = getOptionsFieldBuilder();
                        }
                        this.optionsBuilder_ = repeatedFieldBuilderV3;
                    } else {
                        this.optionsBuilder_.addAllMessages(other.options_);
                    }
                }
                if (other.syntax_ != 0) {
                    setSyntaxValue(other.getSyntaxValue());
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
            Method parsedMessage = null;
            try {
                Method parsedMessage2 = (Method) Method.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                Method parsedMessage3 = (Method) e.getUnfinishedMessage();
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
            this.name_ = Method.getDefaultInstance().getName();
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

        public String getRequestTypeUrl() {
            Object ref = this.requestTypeUrl_;
            if (ref instanceof String) {
                return (String) ref;
            }
            String s = ((ByteString) ref).toStringUtf8();
            this.requestTypeUrl_ = s;
            return s;
        }

        public ByteString getRequestTypeUrlBytes() {
            Object ref = this.requestTypeUrl_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.requestTypeUrl_ = b;
            return b;
        }

        public Builder setRequestTypeUrl(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.requestTypeUrl_ = value;
            onChanged();
            return this;
        }

        public Builder clearRequestTypeUrl() {
            this.requestTypeUrl_ = Method.getDefaultInstance().getRequestTypeUrl();
            onChanged();
            return this;
        }

        public Builder setRequestTypeUrlBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.requestTypeUrl_ = value;
            onChanged();
            return this;
        }

        public boolean getRequestStreaming() {
            return this.requestStreaming_;
        }

        public Builder setRequestStreaming(boolean value) {
            this.requestStreaming_ = value;
            onChanged();
            return this;
        }

        public Builder clearRequestStreaming() {
            this.requestStreaming_ = false;
            onChanged();
            return this;
        }

        public String getResponseTypeUrl() {
            Object ref = this.responseTypeUrl_;
            if (ref instanceof String) {
                return (String) ref;
            }
            String s = ((ByteString) ref).toStringUtf8();
            this.responseTypeUrl_ = s;
            return s;
        }

        public ByteString getResponseTypeUrlBytes() {
            Object ref = this.responseTypeUrl_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.responseTypeUrl_ = b;
            return b;
        }

        public Builder setResponseTypeUrl(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.responseTypeUrl_ = value;
            onChanged();
            return this;
        }

        public Builder clearResponseTypeUrl() {
            this.responseTypeUrl_ = Method.getDefaultInstance().getResponseTypeUrl();
            onChanged();
            return this;
        }

        public Builder setResponseTypeUrlBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.responseTypeUrl_ = value;
            onChanged();
            return this;
        }

        public boolean getResponseStreaming() {
            return this.responseStreaming_;
        }

        public Builder setResponseStreaming(boolean value) {
            this.responseStreaming_ = value;
            onChanged();
            return this;
        }

        public Builder clearResponseStreaming() {
            this.responseStreaming_ = false;
            onChanged();
            return this;
        }

        private void ensureOptionsIsMutable() {
            if ((this.bitField0_ & 32) != 32) {
                this.options_ = new ArrayList(this.options_);
                this.bitField0_ |= 32;
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
                this.bitField0_ &= -33;
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
                this.optionsBuilder_ = new RepeatedFieldBuilderV3<>(this.options_, (this.bitField0_ & 32) == 32, getParentForChildren(), isClean());
                this.options_ = null;
            }
            return this.optionsBuilder_;
        }

        public int getSyntaxValue() {
            return this.syntax_;
        }

        public Builder setSyntaxValue(int value) {
            this.syntax_ = value;
            onChanged();
            return this;
        }

        public Syntax getSyntax() {
            Syntax result = Syntax.valueOf(this.syntax_);
            return result == null ? Syntax.UNRECOGNIZED : result;
        }

        public Builder setSyntax(Syntax value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.syntax_ = value.getNumber();
            onChanged();
            return this;
        }

        public Builder clearSyntax() {
            this.syntax_ = 0;
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

    private Method(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private Method() {
        this.memoizedIsInitialized = -1;
        this.name_ = "";
        this.requestTypeUrl_ = "";
        this.requestStreaming_ = false;
        this.responseTypeUrl_ = "";
        this.responseStreaming_ = false;
        this.options_ = Collections.emptyList();
        this.syntax_ = 0;
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Method(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    case 18:
                        this.requestTypeUrl_ = input.readStringRequireUtf8();
                        break;
                    case 24:
                        this.requestStreaming_ = input.readBool();
                        break;
                    case 34:
                        this.responseTypeUrl_ = input.readStringRequireUtf8();
                        break;
                    case 40:
                        this.responseStreaming_ = input.readBool();
                        break;
                    case 50:
                        if ((mutable_bitField0_ & 32) != 32) {
                            this.options_ = new ArrayList();
                            mutable_bitField0_ |= 32;
                        }
                        this.options_.add(input.readMessage(Option.parser(), extensionRegistry));
                        break;
                    case 56:
                        this.syntax_ = input.readEnum();
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
                if ((mutable_bitField0_ & 32) == 32) {
                    this.options_ = Collections.unmodifiableList(this.options_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
                throw th;
            }
        }
        if ((mutable_bitField0_ & 32) == 32) {
            this.options_ = Collections.unmodifiableList(this.options_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
    }

    public static final Descriptor getDescriptor() {
        return ApiProto.internal_static_google_protobuf_Method_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return ApiProto.internal_static_google_protobuf_Method_fieldAccessorTable.ensureFieldAccessorsInitialized(Method.class, Builder.class);
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

    public String getRequestTypeUrl() {
        Object ref = this.requestTypeUrl_;
        if (ref instanceof String) {
            return (String) ref;
        }
        String s = ((ByteString) ref).toStringUtf8();
        this.requestTypeUrl_ = s;
        return s;
    }

    public ByteString getRequestTypeUrlBytes() {
        Object ref = this.requestTypeUrl_;
        if (!(ref instanceof String)) {
            return (ByteString) ref;
        }
        ByteString b = ByteString.copyFromUtf8((String) ref);
        this.requestTypeUrl_ = b;
        return b;
    }

    public boolean getRequestStreaming() {
        return this.requestStreaming_;
    }

    public String getResponseTypeUrl() {
        Object ref = this.responseTypeUrl_;
        if (ref instanceof String) {
            return (String) ref;
        }
        String s = ((ByteString) ref).toStringUtf8();
        this.responseTypeUrl_ = s;
        return s;
    }

    public ByteString getResponseTypeUrlBytes() {
        Object ref = this.responseTypeUrl_;
        if (!(ref instanceof String)) {
            return (ByteString) ref;
        }
        ByteString b = ByteString.copyFromUtf8((String) ref);
        this.responseTypeUrl_ = b;
        return b;
    }

    public boolean getResponseStreaming() {
        return this.responseStreaming_;
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

    public int getSyntaxValue() {
        return this.syntax_;
    }

    public Syntax getSyntax() {
        Syntax result = Syntax.valueOf(this.syntax_);
        return result == null ? Syntax.UNRECOGNIZED : result;
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
        if (!getRequestTypeUrlBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 2, this.requestTypeUrl_);
        }
        if (this.requestStreaming_) {
            output.writeBool(3, this.requestStreaming_);
        }
        if (!getResponseTypeUrlBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 4, this.responseTypeUrl_);
        }
        if (this.responseStreaming_) {
            output.writeBool(5, this.responseStreaming_);
        }
        for (int i = 0; i < this.options_.size(); i++) {
            output.writeMessage(6, (MessageLite) this.options_.get(i));
        }
        if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
            output.writeEnum(7, this.syntax_);
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
        if (!getRequestTypeUrlBytes().isEmpty()) {
            size2 += GeneratedMessageV3.computeStringSize(2, this.requestTypeUrl_);
        }
        if (this.requestStreaming_) {
            size2 += CodedOutputStream.computeBoolSize(3, this.requestStreaming_);
        }
        if (!getResponseTypeUrlBytes().isEmpty()) {
            size2 += GeneratedMessageV3.computeStringSize(4, this.responseTypeUrl_);
        }
        if (this.responseStreaming_) {
            size2 += CodedOutputStream.computeBoolSize(5, this.responseStreaming_);
        }
        for (int i = 0; i < this.options_.size(); i++) {
            size2 += CodedOutputStream.computeMessageSize(6, (MessageLite) this.options_.get(i));
        }
        if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
            size2 += CodedOutputStream.computeEnumSize(7, this.syntax_);
        }
        int size3 = size2 + this.unknownFields.getSerializedSize();
        this.memoizedSize = size3;
        return size3;
    }

    public boolean equals(Object obj) {
        boolean result;
        boolean result2;
        boolean result3;
        boolean result4;
        boolean result5;
        boolean result6;
        boolean result7;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Method)) {
            return super.equals(obj);
        }
        Method other = (Method) obj;
        if (!(1 != 0 && getName().equals(other.getName())) || !getRequestTypeUrl().equals(other.getRequestTypeUrl())) {
            result = false;
        } else {
            result = true;
        }
        if (!result || getRequestStreaming() != other.getRequestStreaming()) {
            result2 = false;
        } else {
            result2 = true;
        }
        if (!result2 || !getResponseTypeUrl().equals(other.getResponseTypeUrl())) {
            result3 = false;
        } else {
            result3 = true;
        }
        if (!result3 || getResponseStreaming() != other.getResponseStreaming()) {
            result4 = false;
        } else {
            result4 = true;
        }
        if (!result4 || !getOptionsList().equals(other.getOptionsList())) {
            result5 = false;
        } else {
            result5 = true;
        }
        if (!result5 || this.syntax_ != other.syntax_) {
            result6 = false;
        } else {
            result6 = true;
        }
        if (!result6 || !this.unknownFields.equals(other.unknownFields)) {
            result7 = false;
        } else {
            result7 = true;
        }
        return result7;
    }

    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = ((((((((((((((((((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + getName().hashCode()) * 37) + 2) * 53) + getRequestTypeUrl().hashCode()) * 37) + 3) * 53) + Internal.hashBoolean(getRequestStreaming())) * 37) + 4) * 53) + getResponseTypeUrl().hashCode()) * 37) + 5) * 53) + Internal.hashBoolean(getResponseStreaming());
        if (getOptionsCount() > 0) {
            hash = (((hash * 37) + 6) * 53) + getOptionsList().hashCode();
        }
        int hash2 = (((((hash * 37) + 7) * 53) + this.syntax_) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash2;
        return hash2;
    }

    public static Method parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Method) PARSER.parseFrom(data);
    }

    public static Method parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Method) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Method parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Method) PARSER.parseFrom(data);
    }

    public static Method parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Method) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Method parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Method) PARSER.parseFrom(data);
    }

    public static Method parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Method) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Method parseFrom(InputStream input) throws IOException {
        return (Method) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Method parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Method) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Method parseDelimitedFrom(InputStream input) throws IOException {
        return (Method) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Method parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Method) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Method parseFrom(CodedInputStream input) throws IOException {
        return (Method) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Method parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Method) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Method prototype) {
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

    public static Method getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Method> parser() {
        return PARSER;
    }

    public Parser<Method> getParserForType() {
        return PARSER;
    }

    public Method getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
