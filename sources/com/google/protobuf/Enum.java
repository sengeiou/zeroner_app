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

public final class Enum extends GeneratedMessageV3 implements EnumOrBuilder {
    private static final Enum DEFAULT_INSTANCE = new Enum();
    public static final int ENUMVALUE_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    /* access modifiers changed from: private */
    public static final Parser<Enum> PARSER = new AbstractParser<Enum>() {
        public Enum parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Enum(input, extensionRegistry);
        }
    };
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 4;
    public static final int SYNTAX_FIELD_NUMBER = 5;
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public List<EnumValue> enumvalue_;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public volatile Object name_;
    /* access modifiers changed from: private */
    public List<Option> options_;
    /* access modifiers changed from: private */
    public SourceContext sourceContext_;
    /* access modifiers changed from: private */
    public int syntax_;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements EnumOrBuilder {
        private int bitField0_;
        private RepeatedFieldBuilderV3<EnumValue, com.google.protobuf.EnumValue.Builder, EnumValueOrBuilder> enumvalueBuilder_;
        private List<EnumValue> enumvalue_;
        private Object name_;
        private RepeatedFieldBuilderV3<Option, com.google.protobuf.Option.Builder, OptionOrBuilder> optionsBuilder_;
        private List<Option> options_;
        private SingleFieldBuilderV3<SourceContext, com.google.protobuf.SourceContext.Builder, SourceContextOrBuilder> sourceContextBuilder_;
        private SourceContext sourceContext_;
        private int syntax_;

        public static final Descriptor getDescriptor() {
            return TypeProto.internal_static_google_protobuf_Enum_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return TypeProto.internal_static_google_protobuf_Enum_fieldAccessorTable.ensureFieldAccessorsInitialized(Enum.class, Builder.class);
        }

        private Builder() {
            this.name_ = "";
            this.enumvalue_ = Collections.emptyList();
            this.options_ = Collections.emptyList();
            this.sourceContext_ = null;
            this.syntax_ = 0;
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.enumvalue_ = Collections.emptyList();
            this.options_ = Collections.emptyList();
            this.sourceContext_ = null;
            this.syntax_ = 0;
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                getEnumvalueFieldBuilder();
                getOptionsFieldBuilder();
            }
        }

        public Builder clear() {
            super.clear();
            this.name_ = "";
            if (this.enumvalueBuilder_ == null) {
                this.enumvalue_ = Collections.emptyList();
                this.bitField0_ &= -3;
            } else {
                this.enumvalueBuilder_.clear();
            }
            if (this.optionsBuilder_ == null) {
                this.options_ = Collections.emptyList();
                this.bitField0_ &= -5;
            } else {
                this.optionsBuilder_.clear();
            }
            if (this.sourceContextBuilder_ == null) {
                this.sourceContext_ = null;
            } else {
                this.sourceContext_ = null;
                this.sourceContextBuilder_ = null;
            }
            this.syntax_ = 0;
            return this;
        }

        public Descriptor getDescriptorForType() {
            return TypeProto.internal_static_google_protobuf_Enum_descriptor;
        }

        public Enum getDefaultInstanceForType() {
            return Enum.getDefaultInstance();
        }

        public Enum build() {
            Enum result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public Enum buildPartial() {
            Enum result = new Enum((com.google.protobuf.GeneratedMessageV3.Builder) this);
            int i = this.bitField0_;
            result.name_ = this.name_;
            if (this.enumvalueBuilder_ == null) {
                if ((this.bitField0_ & 2) == 2) {
                    this.enumvalue_ = Collections.unmodifiableList(this.enumvalue_);
                    this.bitField0_ &= -3;
                }
                result.enumvalue_ = this.enumvalue_;
            } else {
                result.enumvalue_ = this.enumvalueBuilder_.build();
            }
            if (this.optionsBuilder_ == null) {
                if ((this.bitField0_ & 4) == 4) {
                    this.options_ = Collections.unmodifiableList(this.options_);
                    this.bitField0_ &= -5;
                }
                result.options_ = this.options_;
            } else {
                result.options_ = this.optionsBuilder_.build();
            }
            if (this.sourceContextBuilder_ == null) {
                result.sourceContext_ = this.sourceContext_;
            } else {
                result.sourceContext_ = (SourceContext) this.sourceContextBuilder_.build();
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
            if (other instanceof Enum) {
                return mergeFrom((Enum) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Enum other) {
            RepeatedFieldBuilderV3<Option, com.google.protobuf.Option.Builder, OptionOrBuilder> repeatedFieldBuilderV3 = null;
            if (other != Enum.getDefaultInstance()) {
                if (!other.getName().isEmpty()) {
                    this.name_ = other.name_;
                    onChanged();
                }
                if (this.enumvalueBuilder_ == null) {
                    if (!other.enumvalue_.isEmpty()) {
                        if (this.enumvalue_.isEmpty()) {
                            this.enumvalue_ = other.enumvalue_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureEnumvalueIsMutable();
                            this.enumvalue_.addAll(other.enumvalue_);
                        }
                        onChanged();
                    }
                } else if (!other.enumvalue_.isEmpty()) {
                    if (this.enumvalueBuilder_.isEmpty()) {
                        this.enumvalueBuilder_.dispose();
                        this.enumvalueBuilder_ = null;
                        this.enumvalue_ = other.enumvalue_;
                        this.bitField0_ &= -3;
                        this.enumvalueBuilder_ = GeneratedMessageV3.alwaysUseFieldBuilders ? getEnumvalueFieldBuilder() : null;
                    } else {
                        this.enumvalueBuilder_.addAllMessages(other.enumvalue_);
                    }
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
                if (other.hasSourceContext()) {
                    mergeSourceContext(other.getSourceContext());
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
            Enum parsedMessage = null;
            try {
                Enum parsedMessage2 = (Enum) Enum.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                Enum parsedMessage3 = (Enum) e.getUnfinishedMessage();
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
            this.name_ = Enum.getDefaultInstance().getName();
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

        private void ensureEnumvalueIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
                this.enumvalue_ = new ArrayList(this.enumvalue_);
                this.bitField0_ |= 2;
            }
        }

        public List<EnumValue> getEnumvalueList() {
            if (this.enumvalueBuilder_ == null) {
                return Collections.unmodifiableList(this.enumvalue_);
            }
            return this.enumvalueBuilder_.getMessageList();
        }

        public int getEnumvalueCount() {
            if (this.enumvalueBuilder_ == null) {
                return this.enumvalue_.size();
            }
            return this.enumvalueBuilder_.getCount();
        }

        public EnumValue getEnumvalue(int index) {
            if (this.enumvalueBuilder_ == null) {
                return (EnumValue) this.enumvalue_.get(index);
            }
            return (EnumValue) this.enumvalueBuilder_.getMessage(index);
        }

        public Builder setEnumvalue(int index, EnumValue value) {
            if (this.enumvalueBuilder_ != null) {
                this.enumvalueBuilder_.setMessage(index, value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureEnumvalueIsMutable();
                this.enumvalue_.set(index, value);
                onChanged();
            }
            return this;
        }

        public Builder setEnumvalue(int index, com.google.protobuf.EnumValue.Builder builderForValue) {
            if (this.enumvalueBuilder_ == null) {
                ensureEnumvalueIsMutable();
                this.enumvalue_.set(index, builderForValue.build());
                onChanged();
            } else {
                this.enumvalueBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addEnumvalue(EnumValue value) {
            if (this.enumvalueBuilder_ != null) {
                this.enumvalueBuilder_.addMessage(value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureEnumvalueIsMutable();
                this.enumvalue_.add(value);
                onChanged();
            }
            return this;
        }

        public Builder addEnumvalue(int index, EnumValue value) {
            if (this.enumvalueBuilder_ != null) {
                this.enumvalueBuilder_.addMessage(index, value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureEnumvalueIsMutable();
                this.enumvalue_.add(index, value);
                onChanged();
            }
            return this;
        }

        public Builder addEnumvalue(com.google.protobuf.EnumValue.Builder builderForValue) {
            if (this.enumvalueBuilder_ == null) {
                ensureEnumvalueIsMutable();
                this.enumvalue_.add(builderForValue.build());
                onChanged();
            } else {
                this.enumvalueBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addEnumvalue(int index, com.google.protobuf.EnumValue.Builder builderForValue) {
            if (this.enumvalueBuilder_ == null) {
                ensureEnumvalueIsMutable();
                this.enumvalue_.add(index, builderForValue.build());
                onChanged();
            } else {
                this.enumvalueBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllEnumvalue(Iterable<? extends EnumValue> values) {
            if (this.enumvalueBuilder_ == null) {
                ensureEnumvalueIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.enumvalue_);
                onChanged();
            } else {
                this.enumvalueBuilder_.addAllMessages(values);
            }
            return this;
        }

        public Builder clearEnumvalue() {
            if (this.enumvalueBuilder_ == null) {
                this.enumvalue_ = Collections.emptyList();
                this.bitField0_ &= -3;
                onChanged();
            } else {
                this.enumvalueBuilder_.clear();
            }
            return this;
        }

        public Builder removeEnumvalue(int index) {
            if (this.enumvalueBuilder_ == null) {
                ensureEnumvalueIsMutable();
                this.enumvalue_.remove(index);
                onChanged();
            } else {
                this.enumvalueBuilder_.remove(index);
            }
            return this;
        }

        public com.google.protobuf.EnumValue.Builder getEnumvalueBuilder(int index) {
            return (com.google.protobuf.EnumValue.Builder) getEnumvalueFieldBuilder().getBuilder(index);
        }

        public EnumValueOrBuilder getEnumvalueOrBuilder(int index) {
            if (this.enumvalueBuilder_ == null) {
                return (EnumValueOrBuilder) this.enumvalue_.get(index);
            }
            return (EnumValueOrBuilder) this.enumvalueBuilder_.getMessageOrBuilder(index);
        }

        public List<? extends EnumValueOrBuilder> getEnumvalueOrBuilderList() {
            if (this.enumvalueBuilder_ != null) {
                return this.enumvalueBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.enumvalue_);
        }

        public com.google.protobuf.EnumValue.Builder addEnumvalueBuilder() {
            return (com.google.protobuf.EnumValue.Builder) getEnumvalueFieldBuilder().addBuilder(EnumValue.getDefaultInstance());
        }

        public com.google.protobuf.EnumValue.Builder addEnumvalueBuilder(int index) {
            return (com.google.protobuf.EnumValue.Builder) getEnumvalueFieldBuilder().addBuilder(index, EnumValue.getDefaultInstance());
        }

        public List<com.google.protobuf.EnumValue.Builder> getEnumvalueBuilderList() {
            return getEnumvalueFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<EnumValue, com.google.protobuf.EnumValue.Builder, EnumValueOrBuilder> getEnumvalueFieldBuilder() {
            if (this.enumvalueBuilder_ == null) {
                this.enumvalueBuilder_ = new RepeatedFieldBuilderV3<>(this.enumvalue_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                this.enumvalue_ = null;
            }
            return this.enumvalueBuilder_;
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

        public boolean hasSourceContext() {
            return (this.sourceContextBuilder_ == null && this.sourceContext_ == null) ? false : true;
        }

        public SourceContext getSourceContext() {
            if (this.sourceContextBuilder_ == null) {
                return this.sourceContext_ == null ? SourceContext.getDefaultInstance() : this.sourceContext_;
            }
            return (SourceContext) this.sourceContextBuilder_.getMessage();
        }

        public Builder setSourceContext(SourceContext value) {
            if (this.sourceContextBuilder_ != null) {
                this.sourceContextBuilder_.setMessage(value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                this.sourceContext_ = value;
                onChanged();
            }
            return this;
        }

        public Builder setSourceContext(com.google.protobuf.SourceContext.Builder builderForValue) {
            if (this.sourceContextBuilder_ == null) {
                this.sourceContext_ = builderForValue.build();
                onChanged();
            } else {
                this.sourceContextBuilder_.setMessage(builderForValue.build());
            }
            return this;
        }

        public Builder mergeSourceContext(SourceContext value) {
            if (this.sourceContextBuilder_ == null) {
                if (this.sourceContext_ != null) {
                    this.sourceContext_ = SourceContext.newBuilder(this.sourceContext_).mergeFrom(value).buildPartial();
                } else {
                    this.sourceContext_ = value;
                }
                onChanged();
            } else {
                this.sourceContextBuilder_.mergeFrom(value);
            }
            return this;
        }

        public Builder clearSourceContext() {
            if (this.sourceContextBuilder_ == null) {
                this.sourceContext_ = null;
                onChanged();
            } else {
                this.sourceContext_ = null;
                this.sourceContextBuilder_ = null;
            }
            return this;
        }

        public com.google.protobuf.SourceContext.Builder getSourceContextBuilder() {
            onChanged();
            return (com.google.protobuf.SourceContext.Builder) getSourceContextFieldBuilder().getBuilder();
        }

        public SourceContextOrBuilder getSourceContextOrBuilder() {
            if (this.sourceContextBuilder_ != null) {
                return (SourceContextOrBuilder) this.sourceContextBuilder_.getMessageOrBuilder();
            }
            return this.sourceContext_ == null ? SourceContext.getDefaultInstance() : this.sourceContext_;
        }

        private SingleFieldBuilderV3<SourceContext, com.google.protobuf.SourceContext.Builder, SourceContextOrBuilder> getSourceContextFieldBuilder() {
            if (this.sourceContextBuilder_ == null) {
                this.sourceContextBuilder_ = new SingleFieldBuilderV3<>(getSourceContext(), getParentForChildren(), isClean());
                this.sourceContext_ = null;
            }
            return this.sourceContextBuilder_;
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

    private Enum(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private Enum() {
        this.memoizedIsInitialized = -1;
        this.name_ = "";
        this.enumvalue_ = Collections.emptyList();
        this.options_ = Collections.emptyList();
        this.syntax_ = 0;
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Enum(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        if ((mutable_bitField0_ & 2) != 2) {
                            this.enumvalue_ = new ArrayList();
                            mutable_bitField0_ |= 2;
                        }
                        this.enumvalue_.add(input.readMessage(EnumValue.parser(), extensionRegistry));
                        break;
                    case 26:
                        if ((mutable_bitField0_ & 4) != 4) {
                            this.options_ = new ArrayList();
                            mutable_bitField0_ |= 4;
                        }
                        this.options_.add(input.readMessage(Option.parser(), extensionRegistry));
                        break;
                    case 34:
                        com.google.protobuf.SourceContext.Builder subBuilder = null;
                        if (this.sourceContext_ != null) {
                            subBuilder = this.sourceContext_.toBuilder();
                        }
                        this.sourceContext_ = (SourceContext) input.readMessage(SourceContext.parser(), extensionRegistry);
                        if (subBuilder == null) {
                            break;
                        } else {
                            subBuilder.mergeFrom(this.sourceContext_);
                            this.sourceContext_ = subBuilder.buildPartial();
                            break;
                        }
                    case 40:
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
                if ((mutable_bitField0_ & 2) == 2) {
                    this.enumvalue_ = Collections.unmodifiableList(this.enumvalue_);
                }
                if ((mutable_bitField0_ & 4) == 4) {
                    this.options_ = Collections.unmodifiableList(this.options_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
                throw th;
            }
        }
        if ((mutable_bitField0_ & 2) == 2) {
            this.enumvalue_ = Collections.unmodifiableList(this.enumvalue_);
        }
        if ((mutable_bitField0_ & 4) == 4) {
            this.options_ = Collections.unmodifiableList(this.options_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
    }

    public static final Descriptor getDescriptor() {
        return TypeProto.internal_static_google_protobuf_Enum_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return TypeProto.internal_static_google_protobuf_Enum_fieldAccessorTable.ensureFieldAccessorsInitialized(Enum.class, Builder.class);
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

    public List<EnumValue> getEnumvalueList() {
        return this.enumvalue_;
    }

    public List<? extends EnumValueOrBuilder> getEnumvalueOrBuilderList() {
        return this.enumvalue_;
    }

    public int getEnumvalueCount() {
        return this.enumvalue_.size();
    }

    public EnumValue getEnumvalue(int index) {
        return (EnumValue) this.enumvalue_.get(index);
    }

    public EnumValueOrBuilder getEnumvalueOrBuilder(int index) {
        return (EnumValueOrBuilder) this.enumvalue_.get(index);
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

    public boolean hasSourceContext() {
        return this.sourceContext_ != null;
    }

    public SourceContext getSourceContext() {
        return this.sourceContext_ == null ? SourceContext.getDefaultInstance() : this.sourceContext_;
    }

    public SourceContextOrBuilder getSourceContextOrBuilder() {
        return getSourceContext();
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
        for (int i = 0; i < this.enumvalue_.size(); i++) {
            output.writeMessage(2, (MessageLite) this.enumvalue_.get(i));
        }
        for (int i2 = 0; i2 < this.options_.size(); i2++) {
            output.writeMessage(3, (MessageLite) this.options_.get(i2));
        }
        if (this.sourceContext_ != null) {
            output.writeMessage(4, getSourceContext());
        }
        if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
            output.writeEnum(5, this.syntax_);
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
        for (int i = 0; i < this.enumvalue_.size(); i++) {
            size2 += CodedOutputStream.computeMessageSize(2, (MessageLite) this.enumvalue_.get(i));
        }
        for (int i2 = 0; i2 < this.options_.size(); i2++) {
            size2 += CodedOutputStream.computeMessageSize(3, (MessageLite) this.options_.get(i2));
        }
        if (this.sourceContext_ != null) {
            size2 += CodedOutputStream.computeMessageSize(4, getSourceContext());
        }
        if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
            size2 += CodedOutputStream.computeEnumSize(5, this.syntax_);
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
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Enum)) {
            return super.equals(obj);
        }
        Enum other = (Enum) obj;
        if (!(1 != 0 && getName().equals(other.getName())) || !getEnumvalueList().equals(other.getEnumvalueList())) {
            result = false;
        } else {
            result = true;
        }
        if (!result || !getOptionsList().equals(other.getOptionsList())) {
            result2 = false;
        } else {
            result2 = true;
        }
        if (!result2 || hasSourceContext() != other.hasSourceContext()) {
            result3 = false;
        } else {
            result3 = true;
        }
        if (hasSourceContext()) {
            if (!result3 || !getSourceContext().equals(other.getSourceContext())) {
                result3 = false;
            } else {
                result3 = true;
            }
        }
        if (!result3 || this.syntax_ != other.syntax_) {
            result4 = false;
        } else {
            result4 = true;
        }
        if (!result4 || !this.unknownFields.equals(other.unknownFields)) {
            result5 = false;
        } else {
            result5 = true;
        }
        return result5;
    }

    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = ((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + getName().hashCode();
        if (getEnumvalueCount() > 0) {
            hash = (((hash * 37) + 2) * 53) + getEnumvalueList().hashCode();
        }
        if (getOptionsCount() > 0) {
            hash = (((hash * 37) + 3) * 53) + getOptionsList().hashCode();
        }
        if (hasSourceContext()) {
            hash = (((hash * 37) + 4) * 53) + getSourceContext().hashCode();
        }
        int hash2 = (((((hash * 37) + 5) * 53) + this.syntax_) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash2;
        return hash2;
    }

    public static Enum parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Enum) PARSER.parseFrom(data);
    }

    public static Enum parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Enum) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Enum parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Enum) PARSER.parseFrom(data);
    }

    public static Enum parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Enum) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Enum parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Enum) PARSER.parseFrom(data);
    }

    public static Enum parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Enum) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Enum parseFrom(InputStream input) throws IOException {
        return (Enum) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Enum parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Enum) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Enum parseDelimitedFrom(InputStream input) throws IOException {
        return (Enum) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Enum parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Enum) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Enum parseFrom(CodedInputStream input) throws IOException {
        return (Enum) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Enum parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Enum) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Enum prototype) {
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

    public static Enum getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Enum> parser() {
        return PARSER;
    }

    public Parser<Enum> getParserForType() {
        return PARSER;
    }

    public Enum getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
