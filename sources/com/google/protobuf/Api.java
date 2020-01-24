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

public final class Api extends GeneratedMessageV3 implements ApiOrBuilder {
    private static final Api DEFAULT_INSTANCE = new Api();
    public static final int METHODS_FIELD_NUMBER = 2;
    public static final int MIXINS_FIELD_NUMBER = 6;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    /* access modifiers changed from: private */
    public static final Parser<Api> PARSER = new AbstractParser<Api>() {
        public Api parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Api(input, extensionRegistry);
        }
    };
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
    public static final int SYNTAX_FIELD_NUMBER = 7;
    public static final int VERSION_FIELD_NUMBER = 4;
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public int bitField0_;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public List<Method> methods_;
    /* access modifiers changed from: private */
    public List<Mixin> mixins_;
    /* access modifiers changed from: private */
    public volatile Object name_;
    /* access modifiers changed from: private */
    public List<Option> options_;
    /* access modifiers changed from: private */
    public SourceContext sourceContext_;
    /* access modifiers changed from: private */
    public int syntax_;
    /* access modifiers changed from: private */
    public volatile Object version_;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements ApiOrBuilder {
        private int bitField0_;
        private RepeatedFieldBuilderV3<Method, com.google.protobuf.Method.Builder, MethodOrBuilder> methodsBuilder_;
        private List<Method> methods_;
        private RepeatedFieldBuilderV3<Mixin, com.google.protobuf.Mixin.Builder, MixinOrBuilder> mixinsBuilder_;
        private List<Mixin> mixins_;
        private Object name_;
        private RepeatedFieldBuilderV3<Option, com.google.protobuf.Option.Builder, OptionOrBuilder> optionsBuilder_;
        private List<Option> options_;
        private SingleFieldBuilderV3<SourceContext, com.google.protobuf.SourceContext.Builder, SourceContextOrBuilder> sourceContextBuilder_;
        private SourceContext sourceContext_;
        private int syntax_;
        private Object version_;

        public static final Descriptor getDescriptor() {
            return ApiProto.internal_static_google_protobuf_Api_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return ApiProto.internal_static_google_protobuf_Api_fieldAccessorTable.ensureFieldAccessorsInitialized(Api.class, Builder.class);
        }

        private Builder() {
            this.name_ = "";
            this.methods_ = Collections.emptyList();
            this.options_ = Collections.emptyList();
            this.version_ = "";
            this.sourceContext_ = null;
            this.mixins_ = Collections.emptyList();
            this.syntax_ = 0;
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.methods_ = Collections.emptyList();
            this.options_ = Collections.emptyList();
            this.version_ = "";
            this.sourceContext_ = null;
            this.mixins_ = Collections.emptyList();
            this.syntax_ = 0;
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                getMethodsFieldBuilder();
                getOptionsFieldBuilder();
                getMixinsFieldBuilder();
            }
        }

        public Builder clear() {
            super.clear();
            this.name_ = "";
            if (this.methodsBuilder_ == null) {
                this.methods_ = Collections.emptyList();
                this.bitField0_ &= -3;
            } else {
                this.methodsBuilder_.clear();
            }
            if (this.optionsBuilder_ == null) {
                this.options_ = Collections.emptyList();
                this.bitField0_ &= -5;
            } else {
                this.optionsBuilder_.clear();
            }
            this.version_ = "";
            if (this.sourceContextBuilder_ == null) {
                this.sourceContext_ = null;
            } else {
                this.sourceContext_ = null;
                this.sourceContextBuilder_ = null;
            }
            if (this.mixinsBuilder_ == null) {
                this.mixins_ = Collections.emptyList();
                this.bitField0_ &= -33;
            } else {
                this.mixinsBuilder_.clear();
            }
            this.syntax_ = 0;
            return this;
        }

        public Descriptor getDescriptorForType() {
            return ApiProto.internal_static_google_protobuf_Api_descriptor;
        }

        public Api getDefaultInstanceForType() {
            return Api.getDefaultInstance();
        }

        public Api build() {
            Api result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public Api buildPartial() {
            Api result = new Api((com.google.protobuf.GeneratedMessageV3.Builder) this);
            int i = this.bitField0_;
            result.name_ = this.name_;
            if (this.methodsBuilder_ == null) {
                if ((this.bitField0_ & 2) == 2) {
                    this.methods_ = Collections.unmodifiableList(this.methods_);
                    this.bitField0_ &= -3;
                }
                result.methods_ = this.methods_;
            } else {
                result.methods_ = this.methodsBuilder_.build();
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
            result.version_ = this.version_;
            if (this.sourceContextBuilder_ == null) {
                result.sourceContext_ = this.sourceContext_;
            } else {
                result.sourceContext_ = (SourceContext) this.sourceContextBuilder_.build();
            }
            if (this.mixinsBuilder_ == null) {
                if ((this.bitField0_ & 32) == 32) {
                    this.mixins_ = Collections.unmodifiableList(this.mixins_);
                    this.bitField0_ &= -33;
                }
                result.mixins_ = this.mixins_;
            } else {
                result.mixins_ = this.mixinsBuilder_.build();
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
            if (other instanceof Api) {
                return mergeFrom((Api) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Api other) {
            RepeatedFieldBuilderV3<Mixin, com.google.protobuf.Mixin.Builder, MixinOrBuilder> repeatedFieldBuilderV3 = null;
            if (other != Api.getDefaultInstance()) {
                if (!other.getName().isEmpty()) {
                    this.name_ = other.name_;
                    onChanged();
                }
                if (this.methodsBuilder_ == null) {
                    if (!other.methods_.isEmpty()) {
                        if (this.methods_.isEmpty()) {
                            this.methods_ = other.methods_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureMethodsIsMutable();
                            this.methods_.addAll(other.methods_);
                        }
                        onChanged();
                    }
                } else if (!other.methods_.isEmpty()) {
                    if (this.methodsBuilder_.isEmpty()) {
                        this.methodsBuilder_.dispose();
                        this.methodsBuilder_ = null;
                        this.methods_ = other.methods_;
                        this.bitField0_ &= -3;
                        this.methodsBuilder_ = GeneratedMessageV3.alwaysUseFieldBuilders ? getMethodsFieldBuilder() : null;
                    } else {
                        this.methodsBuilder_.addAllMessages(other.methods_);
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
                        this.optionsBuilder_ = GeneratedMessageV3.alwaysUseFieldBuilders ? getOptionsFieldBuilder() : null;
                    } else {
                        this.optionsBuilder_.addAllMessages(other.options_);
                    }
                }
                if (!other.getVersion().isEmpty()) {
                    this.version_ = other.version_;
                    onChanged();
                }
                if (other.hasSourceContext()) {
                    mergeSourceContext(other.getSourceContext());
                }
                if (this.mixinsBuilder_ == null) {
                    if (!other.mixins_.isEmpty()) {
                        if (this.mixins_.isEmpty()) {
                            this.mixins_ = other.mixins_;
                            this.bitField0_ &= -33;
                        } else {
                            ensureMixinsIsMutable();
                            this.mixins_.addAll(other.mixins_);
                        }
                        onChanged();
                    }
                } else if (!other.mixins_.isEmpty()) {
                    if (this.mixinsBuilder_.isEmpty()) {
                        this.mixinsBuilder_.dispose();
                        this.mixinsBuilder_ = null;
                        this.mixins_ = other.mixins_;
                        this.bitField0_ &= -33;
                        if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                            repeatedFieldBuilderV3 = getMixinsFieldBuilder();
                        }
                        this.mixinsBuilder_ = repeatedFieldBuilderV3;
                    } else {
                        this.mixinsBuilder_.addAllMessages(other.mixins_);
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
            Api parsedMessage = null;
            try {
                Api parsedMessage2 = (Api) Api.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                Api parsedMessage3 = (Api) e.getUnfinishedMessage();
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
            this.name_ = Api.getDefaultInstance().getName();
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

        private void ensureMethodsIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
                this.methods_ = new ArrayList(this.methods_);
                this.bitField0_ |= 2;
            }
        }

        public List<Method> getMethodsList() {
            if (this.methodsBuilder_ == null) {
                return Collections.unmodifiableList(this.methods_);
            }
            return this.methodsBuilder_.getMessageList();
        }

        public int getMethodsCount() {
            if (this.methodsBuilder_ == null) {
                return this.methods_.size();
            }
            return this.methodsBuilder_.getCount();
        }

        public Method getMethods(int index) {
            if (this.methodsBuilder_ == null) {
                return (Method) this.methods_.get(index);
            }
            return (Method) this.methodsBuilder_.getMessage(index);
        }

        public Builder setMethods(int index, Method value) {
            if (this.methodsBuilder_ != null) {
                this.methodsBuilder_.setMessage(index, value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureMethodsIsMutable();
                this.methods_.set(index, value);
                onChanged();
            }
            return this;
        }

        public Builder setMethods(int index, com.google.protobuf.Method.Builder builderForValue) {
            if (this.methodsBuilder_ == null) {
                ensureMethodsIsMutable();
                this.methods_.set(index, builderForValue.build());
                onChanged();
            } else {
                this.methodsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addMethods(Method value) {
            if (this.methodsBuilder_ != null) {
                this.methodsBuilder_.addMessage(value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureMethodsIsMutable();
                this.methods_.add(value);
                onChanged();
            }
            return this;
        }

        public Builder addMethods(int index, Method value) {
            if (this.methodsBuilder_ != null) {
                this.methodsBuilder_.addMessage(index, value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureMethodsIsMutable();
                this.methods_.add(index, value);
                onChanged();
            }
            return this;
        }

        public Builder addMethods(com.google.protobuf.Method.Builder builderForValue) {
            if (this.methodsBuilder_ == null) {
                ensureMethodsIsMutable();
                this.methods_.add(builderForValue.build());
                onChanged();
            } else {
                this.methodsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addMethods(int index, com.google.protobuf.Method.Builder builderForValue) {
            if (this.methodsBuilder_ == null) {
                ensureMethodsIsMutable();
                this.methods_.add(index, builderForValue.build());
                onChanged();
            } else {
                this.methodsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllMethods(Iterable<? extends Method> values) {
            if (this.methodsBuilder_ == null) {
                ensureMethodsIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.methods_);
                onChanged();
            } else {
                this.methodsBuilder_.addAllMessages(values);
            }
            return this;
        }

        public Builder clearMethods() {
            if (this.methodsBuilder_ == null) {
                this.methods_ = Collections.emptyList();
                this.bitField0_ &= -3;
                onChanged();
            } else {
                this.methodsBuilder_.clear();
            }
            return this;
        }

        public Builder removeMethods(int index) {
            if (this.methodsBuilder_ == null) {
                ensureMethodsIsMutable();
                this.methods_.remove(index);
                onChanged();
            } else {
                this.methodsBuilder_.remove(index);
            }
            return this;
        }

        public com.google.protobuf.Method.Builder getMethodsBuilder(int index) {
            return (com.google.protobuf.Method.Builder) getMethodsFieldBuilder().getBuilder(index);
        }

        public MethodOrBuilder getMethodsOrBuilder(int index) {
            if (this.methodsBuilder_ == null) {
                return (MethodOrBuilder) this.methods_.get(index);
            }
            return (MethodOrBuilder) this.methodsBuilder_.getMessageOrBuilder(index);
        }

        public List<? extends MethodOrBuilder> getMethodsOrBuilderList() {
            if (this.methodsBuilder_ != null) {
                return this.methodsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.methods_);
        }

        public com.google.protobuf.Method.Builder addMethodsBuilder() {
            return (com.google.protobuf.Method.Builder) getMethodsFieldBuilder().addBuilder(Method.getDefaultInstance());
        }

        public com.google.protobuf.Method.Builder addMethodsBuilder(int index) {
            return (com.google.protobuf.Method.Builder) getMethodsFieldBuilder().addBuilder(index, Method.getDefaultInstance());
        }

        public List<com.google.protobuf.Method.Builder> getMethodsBuilderList() {
            return getMethodsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Method, com.google.protobuf.Method.Builder, MethodOrBuilder> getMethodsFieldBuilder() {
            if (this.methodsBuilder_ == null) {
                this.methodsBuilder_ = new RepeatedFieldBuilderV3<>(this.methods_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                this.methods_ = null;
            }
            return this.methodsBuilder_;
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

        public String getVersion() {
            Object ref = this.version_;
            if (ref instanceof String) {
                return (String) ref;
            }
            String s = ((ByteString) ref).toStringUtf8();
            this.version_ = s;
            return s;
        }

        public ByteString getVersionBytes() {
            Object ref = this.version_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.version_ = b;
            return b;
        }

        public Builder setVersion(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.version_ = value;
            onChanged();
            return this;
        }

        public Builder clearVersion() {
            this.version_ = Api.getDefaultInstance().getVersion();
            onChanged();
            return this;
        }

        public Builder setVersionBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.version_ = value;
            onChanged();
            return this;
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

        private void ensureMixinsIsMutable() {
            if ((this.bitField0_ & 32) != 32) {
                this.mixins_ = new ArrayList(this.mixins_);
                this.bitField0_ |= 32;
            }
        }

        public List<Mixin> getMixinsList() {
            if (this.mixinsBuilder_ == null) {
                return Collections.unmodifiableList(this.mixins_);
            }
            return this.mixinsBuilder_.getMessageList();
        }

        public int getMixinsCount() {
            if (this.mixinsBuilder_ == null) {
                return this.mixins_.size();
            }
            return this.mixinsBuilder_.getCount();
        }

        public Mixin getMixins(int index) {
            if (this.mixinsBuilder_ == null) {
                return (Mixin) this.mixins_.get(index);
            }
            return (Mixin) this.mixinsBuilder_.getMessage(index);
        }

        public Builder setMixins(int index, Mixin value) {
            if (this.mixinsBuilder_ != null) {
                this.mixinsBuilder_.setMessage(index, value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureMixinsIsMutable();
                this.mixins_.set(index, value);
                onChanged();
            }
            return this;
        }

        public Builder setMixins(int index, com.google.protobuf.Mixin.Builder builderForValue) {
            if (this.mixinsBuilder_ == null) {
                ensureMixinsIsMutable();
                this.mixins_.set(index, builderForValue.build());
                onChanged();
            } else {
                this.mixinsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addMixins(Mixin value) {
            if (this.mixinsBuilder_ != null) {
                this.mixinsBuilder_.addMessage(value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureMixinsIsMutable();
                this.mixins_.add(value);
                onChanged();
            }
            return this;
        }

        public Builder addMixins(int index, Mixin value) {
            if (this.mixinsBuilder_ != null) {
                this.mixinsBuilder_.addMessage(index, value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureMixinsIsMutable();
                this.mixins_.add(index, value);
                onChanged();
            }
            return this;
        }

        public Builder addMixins(com.google.protobuf.Mixin.Builder builderForValue) {
            if (this.mixinsBuilder_ == null) {
                ensureMixinsIsMutable();
                this.mixins_.add(builderForValue.build());
                onChanged();
            } else {
                this.mixinsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addMixins(int index, com.google.protobuf.Mixin.Builder builderForValue) {
            if (this.mixinsBuilder_ == null) {
                ensureMixinsIsMutable();
                this.mixins_.add(index, builderForValue.build());
                onChanged();
            } else {
                this.mixinsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllMixins(Iterable<? extends Mixin> values) {
            if (this.mixinsBuilder_ == null) {
                ensureMixinsIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.mixins_);
                onChanged();
            } else {
                this.mixinsBuilder_.addAllMessages(values);
            }
            return this;
        }

        public Builder clearMixins() {
            if (this.mixinsBuilder_ == null) {
                this.mixins_ = Collections.emptyList();
                this.bitField0_ &= -33;
                onChanged();
            } else {
                this.mixinsBuilder_.clear();
            }
            return this;
        }

        public Builder removeMixins(int index) {
            if (this.mixinsBuilder_ == null) {
                ensureMixinsIsMutable();
                this.mixins_.remove(index);
                onChanged();
            } else {
                this.mixinsBuilder_.remove(index);
            }
            return this;
        }

        public com.google.protobuf.Mixin.Builder getMixinsBuilder(int index) {
            return (com.google.protobuf.Mixin.Builder) getMixinsFieldBuilder().getBuilder(index);
        }

        public MixinOrBuilder getMixinsOrBuilder(int index) {
            if (this.mixinsBuilder_ == null) {
                return (MixinOrBuilder) this.mixins_.get(index);
            }
            return (MixinOrBuilder) this.mixinsBuilder_.getMessageOrBuilder(index);
        }

        public List<? extends MixinOrBuilder> getMixinsOrBuilderList() {
            if (this.mixinsBuilder_ != null) {
                return this.mixinsBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.mixins_);
        }

        public com.google.protobuf.Mixin.Builder addMixinsBuilder() {
            return (com.google.protobuf.Mixin.Builder) getMixinsFieldBuilder().addBuilder(Mixin.getDefaultInstance());
        }

        public com.google.protobuf.Mixin.Builder addMixinsBuilder(int index) {
            return (com.google.protobuf.Mixin.Builder) getMixinsFieldBuilder().addBuilder(index, Mixin.getDefaultInstance());
        }

        public List<com.google.protobuf.Mixin.Builder> getMixinsBuilderList() {
            return getMixinsFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Mixin, com.google.protobuf.Mixin.Builder, MixinOrBuilder> getMixinsFieldBuilder() {
            if (this.mixinsBuilder_ == null) {
                this.mixinsBuilder_ = new RepeatedFieldBuilderV3<>(this.mixins_, (this.bitField0_ & 32) == 32, getParentForChildren(), isClean());
                this.mixins_ = null;
            }
            return this.mixinsBuilder_;
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

    private Api(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private Api() {
        this.memoizedIsInitialized = -1;
        this.name_ = "";
        this.methods_ = Collections.emptyList();
        this.options_ = Collections.emptyList();
        this.version_ = "";
        this.mixins_ = Collections.emptyList();
        this.syntax_ = 0;
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Api(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.methods_ = new ArrayList();
                            mutable_bitField0_ |= 2;
                        }
                        this.methods_.add(input.readMessage(Method.parser(), extensionRegistry));
                        break;
                    case 26:
                        if ((mutable_bitField0_ & 4) != 4) {
                            this.options_ = new ArrayList();
                            mutable_bitField0_ |= 4;
                        }
                        this.options_.add(input.readMessage(Option.parser(), extensionRegistry));
                        break;
                    case 34:
                        this.version_ = input.readStringRequireUtf8();
                        break;
                    case 42:
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
                    case 50:
                        if ((mutable_bitField0_ & 32) != 32) {
                            this.mixins_ = new ArrayList();
                            mutable_bitField0_ |= 32;
                        }
                        this.mixins_.add(input.readMessage(Mixin.parser(), extensionRegistry));
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
                if ((mutable_bitField0_ & 2) == 2) {
                    this.methods_ = Collections.unmodifiableList(this.methods_);
                }
                if ((mutable_bitField0_ & 4) == 4) {
                    this.options_ = Collections.unmodifiableList(this.options_);
                }
                if ((mutable_bitField0_ & 32) == 32) {
                    this.mixins_ = Collections.unmodifiableList(this.mixins_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
                throw th;
            }
        }
        if ((mutable_bitField0_ & 2) == 2) {
            this.methods_ = Collections.unmodifiableList(this.methods_);
        }
        if ((mutable_bitField0_ & 4) == 4) {
            this.options_ = Collections.unmodifiableList(this.options_);
        }
        if ((mutable_bitField0_ & 32) == 32) {
            this.mixins_ = Collections.unmodifiableList(this.mixins_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
    }

    public static final Descriptor getDescriptor() {
        return ApiProto.internal_static_google_protobuf_Api_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return ApiProto.internal_static_google_protobuf_Api_fieldAccessorTable.ensureFieldAccessorsInitialized(Api.class, Builder.class);
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

    public List<Method> getMethodsList() {
        return this.methods_;
    }

    public List<? extends MethodOrBuilder> getMethodsOrBuilderList() {
        return this.methods_;
    }

    public int getMethodsCount() {
        return this.methods_.size();
    }

    public Method getMethods(int index) {
        return (Method) this.methods_.get(index);
    }

    public MethodOrBuilder getMethodsOrBuilder(int index) {
        return (MethodOrBuilder) this.methods_.get(index);
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

    public String getVersion() {
        Object ref = this.version_;
        if (ref instanceof String) {
            return (String) ref;
        }
        String s = ((ByteString) ref).toStringUtf8();
        this.version_ = s;
        return s;
    }

    public ByteString getVersionBytes() {
        Object ref = this.version_;
        if (!(ref instanceof String)) {
            return (ByteString) ref;
        }
        ByteString b = ByteString.copyFromUtf8((String) ref);
        this.version_ = b;
        return b;
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

    public List<Mixin> getMixinsList() {
        return this.mixins_;
    }

    public List<? extends MixinOrBuilder> getMixinsOrBuilderList() {
        return this.mixins_;
    }

    public int getMixinsCount() {
        return this.mixins_.size();
    }

    public Mixin getMixins(int index) {
        return (Mixin) this.mixins_.get(index);
    }

    public MixinOrBuilder getMixinsOrBuilder(int index) {
        return (MixinOrBuilder) this.mixins_.get(index);
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
        for (int i = 0; i < this.methods_.size(); i++) {
            output.writeMessage(2, (MessageLite) this.methods_.get(i));
        }
        for (int i2 = 0; i2 < this.options_.size(); i2++) {
            output.writeMessage(3, (MessageLite) this.options_.get(i2));
        }
        if (!getVersionBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 4, this.version_);
        }
        if (this.sourceContext_ != null) {
            output.writeMessage(5, getSourceContext());
        }
        for (int i3 = 0; i3 < this.mixins_.size(); i3++) {
            output.writeMessage(6, (MessageLite) this.mixins_.get(i3));
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
        for (int i = 0; i < this.methods_.size(); i++) {
            size2 += CodedOutputStream.computeMessageSize(2, (MessageLite) this.methods_.get(i));
        }
        for (int i2 = 0; i2 < this.options_.size(); i2++) {
            size2 += CodedOutputStream.computeMessageSize(3, (MessageLite) this.options_.get(i2));
        }
        if (!getVersionBytes().isEmpty()) {
            size2 += GeneratedMessageV3.computeStringSize(4, this.version_);
        }
        if (this.sourceContext_ != null) {
            size2 += CodedOutputStream.computeMessageSize(5, getSourceContext());
        }
        for (int i3 = 0; i3 < this.mixins_.size(); i3++) {
            size2 += CodedOutputStream.computeMessageSize(6, (MessageLite) this.mixins_.get(i3));
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
        if (!(obj instanceof Api)) {
            return super.equals(obj);
        }
        Api other = (Api) obj;
        if (!(1 != 0 && getName().equals(other.getName())) || !getMethodsList().equals(other.getMethodsList())) {
            result = false;
        } else {
            result = true;
        }
        if (!result || !getOptionsList().equals(other.getOptionsList())) {
            result2 = false;
        } else {
            result2 = true;
        }
        if (!result2 || !getVersion().equals(other.getVersion())) {
            result3 = false;
        } else {
            result3 = true;
        }
        if (!result3 || hasSourceContext() != other.hasSourceContext()) {
            result4 = false;
        } else {
            result4 = true;
        }
        if (hasSourceContext()) {
            if (!result4 || !getSourceContext().equals(other.getSourceContext())) {
                result4 = false;
            } else {
                result4 = true;
            }
        }
        if (!result4 || !getMixinsList().equals(other.getMixinsList())) {
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
        int hash = ((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + getName().hashCode();
        if (getMethodsCount() > 0) {
            hash = (((hash * 37) + 2) * 53) + getMethodsList().hashCode();
        }
        if (getOptionsCount() > 0) {
            hash = (((hash * 37) + 3) * 53) + getOptionsList().hashCode();
        }
        int hash2 = (((hash * 37) + 4) * 53) + getVersion().hashCode();
        if (hasSourceContext()) {
            hash2 = (((hash2 * 37) + 5) * 53) + getSourceContext().hashCode();
        }
        if (getMixinsCount() > 0) {
            hash2 = (((hash2 * 37) + 6) * 53) + getMixinsList().hashCode();
        }
        int hash3 = (((((hash2 * 37) + 7) * 53) + this.syntax_) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash3;
        return hash3;
    }

    public static Api parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Api) PARSER.parseFrom(data);
    }

    public static Api parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Api) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Api parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Api) PARSER.parseFrom(data);
    }

    public static Api parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Api) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Api parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Api) PARSER.parseFrom(data);
    }

    public static Api parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Api) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Api parseFrom(InputStream input) throws IOException {
        return (Api) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Api parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Api) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Api parseDelimitedFrom(InputStream input) throws IOException {
        return (Api) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Api parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Api) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Api parseFrom(CodedInputStream input) throws IOException {
        return (Api) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Api parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Api) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Api prototype) {
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

    public static Api getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Api> parser() {
        return PARSER;
    }

    public Parser<Api> getParserForType() {
        return PARSER;
    }

    public Api getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
