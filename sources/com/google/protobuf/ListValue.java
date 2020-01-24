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

public final class ListValue extends GeneratedMessageV3 implements ListValueOrBuilder {
    private static final ListValue DEFAULT_INSTANCE = new ListValue();
    /* access modifiers changed from: private */
    public static final Parser<ListValue> PARSER = new AbstractParser<ListValue>() {
        public ListValue parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new ListValue(input, extensionRegistry);
        }
    };
    public static final int VALUES_FIELD_NUMBER = 1;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public List<Value> values_;

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements ListValueOrBuilder {
        private int bitField0_;
        private RepeatedFieldBuilderV3<Value, com.google.protobuf.Value.Builder, ValueOrBuilder> valuesBuilder_;
        private List<Value> values_;

        public static final Descriptor getDescriptor() {
            return StructProto.internal_static_google_protobuf_ListValue_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return StructProto.internal_static_google_protobuf_ListValue_fieldAccessorTable.ensureFieldAccessorsInitialized(ListValue.class, Builder.class);
        }

        private Builder() {
            this.values_ = Collections.emptyList();
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            this.values_ = Collections.emptyList();
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                getValuesFieldBuilder();
            }
        }

        public Builder clear() {
            super.clear();
            if (this.valuesBuilder_ == null) {
                this.values_ = Collections.emptyList();
                this.bitField0_ &= -2;
            } else {
                this.valuesBuilder_.clear();
            }
            return this;
        }

        public Descriptor getDescriptorForType() {
            return StructProto.internal_static_google_protobuf_ListValue_descriptor;
        }

        public ListValue getDefaultInstanceForType() {
            return ListValue.getDefaultInstance();
        }

        public ListValue build() {
            ListValue result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public ListValue buildPartial() {
            ListValue result = new ListValue((com.google.protobuf.GeneratedMessageV3.Builder) this);
            int i = this.bitField0_;
            if (this.valuesBuilder_ == null) {
                if ((this.bitField0_ & 1) == 1) {
                    this.values_ = Collections.unmodifiableList(this.values_);
                    this.bitField0_ &= -2;
                }
                result.values_ = this.values_;
            } else {
                result.values_ = this.valuesBuilder_.build();
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
            if (other instanceof ListValue) {
                return mergeFrom((ListValue) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(ListValue other) {
            RepeatedFieldBuilderV3<Value, com.google.protobuf.Value.Builder, ValueOrBuilder> repeatedFieldBuilderV3 = null;
            if (other != ListValue.getDefaultInstance()) {
                if (this.valuesBuilder_ == null) {
                    if (!other.values_.isEmpty()) {
                        if (this.values_.isEmpty()) {
                            this.values_ = other.values_;
                            this.bitField0_ &= -2;
                        } else {
                            ensureValuesIsMutable();
                            this.values_.addAll(other.values_);
                        }
                        onChanged();
                    }
                } else if (!other.values_.isEmpty()) {
                    if (this.valuesBuilder_.isEmpty()) {
                        this.valuesBuilder_.dispose();
                        this.valuesBuilder_ = null;
                        this.values_ = other.values_;
                        this.bitField0_ &= -2;
                        if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                            repeatedFieldBuilderV3 = getValuesFieldBuilder();
                        }
                        this.valuesBuilder_ = repeatedFieldBuilderV3;
                    } else {
                        this.valuesBuilder_.addAllMessages(other.values_);
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
            ListValue parsedMessage = null;
            try {
                ListValue parsedMessage2 = (ListValue) ListValue.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                ListValue parsedMessage3 = (ListValue) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                throw th;
            }
        }

        private void ensureValuesIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.values_ = new ArrayList(this.values_);
                this.bitField0_ |= 1;
            }
        }

        public List<Value> getValuesList() {
            if (this.valuesBuilder_ == null) {
                return Collections.unmodifiableList(this.values_);
            }
            return this.valuesBuilder_.getMessageList();
        }

        public int getValuesCount() {
            if (this.valuesBuilder_ == null) {
                return this.values_.size();
            }
            return this.valuesBuilder_.getCount();
        }

        public Value getValues(int index) {
            if (this.valuesBuilder_ == null) {
                return (Value) this.values_.get(index);
            }
            return (Value) this.valuesBuilder_.getMessage(index);
        }

        public Builder setValues(int index, Value value) {
            if (this.valuesBuilder_ != null) {
                this.valuesBuilder_.setMessage(index, value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureValuesIsMutable();
                this.values_.set(index, value);
                onChanged();
            }
            return this;
        }

        public Builder setValues(int index, com.google.protobuf.Value.Builder builderForValue) {
            if (this.valuesBuilder_ == null) {
                ensureValuesIsMutable();
                this.values_.set(index, builderForValue.build());
                onChanged();
            } else {
                this.valuesBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addValues(Value value) {
            if (this.valuesBuilder_ != null) {
                this.valuesBuilder_.addMessage(value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureValuesIsMutable();
                this.values_.add(value);
                onChanged();
            }
            return this;
        }

        public Builder addValues(int index, Value value) {
            if (this.valuesBuilder_ != null) {
                this.valuesBuilder_.addMessage(index, value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                ensureValuesIsMutable();
                this.values_.add(index, value);
                onChanged();
            }
            return this;
        }

        public Builder addValues(com.google.protobuf.Value.Builder builderForValue) {
            if (this.valuesBuilder_ == null) {
                ensureValuesIsMutable();
                this.values_.add(builderForValue.build());
                onChanged();
            } else {
                this.valuesBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        public Builder addValues(int index, com.google.protobuf.Value.Builder builderForValue) {
            if (this.valuesBuilder_ == null) {
                ensureValuesIsMutable();
                this.values_.add(index, builderForValue.build());
                onChanged();
            } else {
                this.valuesBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        public Builder addAllValues(Iterable<? extends Value> values) {
            if (this.valuesBuilder_ == null) {
                ensureValuesIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.values_);
                onChanged();
            } else {
                this.valuesBuilder_.addAllMessages(values);
            }
            return this;
        }

        public Builder clearValues() {
            if (this.valuesBuilder_ == null) {
                this.values_ = Collections.emptyList();
                this.bitField0_ &= -2;
                onChanged();
            } else {
                this.valuesBuilder_.clear();
            }
            return this;
        }

        public Builder removeValues(int index) {
            if (this.valuesBuilder_ == null) {
                ensureValuesIsMutable();
                this.values_.remove(index);
                onChanged();
            } else {
                this.valuesBuilder_.remove(index);
            }
            return this;
        }

        public com.google.protobuf.Value.Builder getValuesBuilder(int index) {
            return (com.google.protobuf.Value.Builder) getValuesFieldBuilder().getBuilder(index);
        }

        public ValueOrBuilder getValuesOrBuilder(int index) {
            if (this.valuesBuilder_ == null) {
                return (ValueOrBuilder) this.values_.get(index);
            }
            return (ValueOrBuilder) this.valuesBuilder_.getMessageOrBuilder(index);
        }

        public List<? extends ValueOrBuilder> getValuesOrBuilderList() {
            if (this.valuesBuilder_ != null) {
                return this.valuesBuilder_.getMessageOrBuilderList();
            }
            return Collections.unmodifiableList(this.values_);
        }

        public com.google.protobuf.Value.Builder addValuesBuilder() {
            return (com.google.protobuf.Value.Builder) getValuesFieldBuilder().addBuilder(Value.getDefaultInstance());
        }

        public com.google.protobuf.Value.Builder addValuesBuilder(int index) {
            return (com.google.protobuf.Value.Builder) getValuesFieldBuilder().addBuilder(index, Value.getDefaultInstance());
        }

        public List<com.google.protobuf.Value.Builder> getValuesBuilderList() {
            return getValuesFieldBuilder().getBuilderList();
        }

        private RepeatedFieldBuilderV3<Value, com.google.protobuf.Value.Builder, ValueOrBuilder> getValuesFieldBuilder() {
            boolean z = true;
            if (this.valuesBuilder_ == null) {
                List<Value> list = this.values_;
                if ((this.bitField0_ & 1) != 1) {
                    z = false;
                }
                this.valuesBuilder_ = new RepeatedFieldBuilderV3<>(list, z, getParentForChildren(), isClean());
                this.values_ = null;
            }
            return this.valuesBuilder_;
        }

        public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.setUnknownFieldsProto3(unknownFields);
        }

        public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.mergeUnknownFields(unknownFields);
        }
    }

    private ListValue(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private ListValue() {
        this.memoizedIsInitialized = -1;
        this.values_ = Collections.emptyList();
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private ListValue(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        if ((mutable_bitField0_ & 1) != 1) {
                            this.values_ = new ArrayList();
                            mutable_bitField0_ |= 1;
                        }
                        this.values_.add(input.readMessage(Value.parser(), extensionRegistry));
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
                if ((mutable_bitField0_ & 1) == 1) {
                    this.values_ = Collections.unmodifiableList(this.values_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
                throw th;
            }
        }
        if ((mutable_bitField0_ & 1) == 1) {
            this.values_ = Collections.unmodifiableList(this.values_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
    }

    public static final Descriptor getDescriptor() {
        return StructProto.internal_static_google_protobuf_ListValue_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return StructProto.internal_static_google_protobuf_ListValue_fieldAccessorTable.ensureFieldAccessorsInitialized(ListValue.class, Builder.class);
    }

    public List<Value> getValuesList() {
        return this.values_;
    }

    public List<? extends ValueOrBuilder> getValuesOrBuilderList() {
        return this.values_;
    }

    public int getValuesCount() {
        return this.values_.size();
    }

    public Value getValues(int index) {
        return (Value) this.values_.get(index);
    }

    public ValueOrBuilder getValuesOrBuilder(int index) {
        return (ValueOrBuilder) this.values_.get(index);
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
        for (int i = 0; i < this.values_.size(); i++) {
            output.writeMessage(1, (MessageLite) this.values_.get(i));
        }
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        for (int i = 0; i < this.values_.size(); i++) {
            size2 += CodedOutputStream.computeMessageSize(1, (MessageLite) this.values_.get(i));
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
        if (!(obj instanceof ListValue)) {
            return super.equals(obj);
        }
        ListValue other = (ListValue) obj;
        if (1 == 0 || !getValuesList().equals(other.getValuesList())) {
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
        int hash = getDescriptor().hashCode() + 779;
        if (getValuesCount() > 0) {
            hash = (((hash * 37) + 1) * 53) + getValuesList().hashCode();
        }
        int hash2 = (hash * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash2;
        return hash2;
    }

    public static ListValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (ListValue) PARSER.parseFrom(data);
    }

    public static ListValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ListValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (ListValue) PARSER.parseFrom(data);
    }

    public static ListValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ListValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (ListValue) PARSER.parseFrom(data);
    }

    public static ListValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ListValue) PARSER.parseFrom(data, extensionRegistry);
    }

    public static ListValue parseFrom(InputStream input) throws IOException {
        return (ListValue) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ListValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ListValue) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ListValue parseDelimitedFrom(InputStream input) throws IOException {
        return (ListValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static ListValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ListValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ListValue parseFrom(CodedInputStream input) throws IOException {
        return (ListValue) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static ListValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ListValue) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ListValue prototype) {
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

    public static ListValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ListValue> parser() {
        return PARSER;
    }

    public Parser<ListValue> getParserForType() {
        return PARSER;
    }

    public ListValue getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
