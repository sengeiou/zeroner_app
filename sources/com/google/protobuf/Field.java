package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import com.google.protobuf.Internal.EnumLiteMap;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Field extends GeneratedMessageV3 implements FieldOrBuilder {
    public static final int CARDINALITY_FIELD_NUMBER = 2;
    private static final Field DEFAULT_INSTANCE = new Field();
    public static final int DEFAULT_VALUE_FIELD_NUMBER = 11;
    public static final int JSON_NAME_FIELD_NUMBER = 10;
    public static final int KIND_FIELD_NUMBER = 1;
    public static final int NAME_FIELD_NUMBER = 4;
    public static final int NUMBER_FIELD_NUMBER = 3;
    public static final int ONEOF_INDEX_FIELD_NUMBER = 7;
    public static final int OPTIONS_FIELD_NUMBER = 9;
    public static final int PACKED_FIELD_NUMBER = 8;
    /* access modifiers changed from: private */
    public static final Parser<Field> PARSER = new AbstractParser<Field>() {
        public Field parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Field(input, extensionRegistry);
        }
    };
    public static final int TYPE_URL_FIELD_NUMBER = 6;
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public int cardinality_;
    /* access modifiers changed from: private */
    public volatile Object defaultValue_;
    /* access modifiers changed from: private */
    public volatile Object jsonName_;
    /* access modifiers changed from: private */
    public int kind_;
    private byte memoizedIsInitialized;
    /* access modifiers changed from: private */
    public volatile Object name_;
    /* access modifiers changed from: private */
    public int number_;
    /* access modifiers changed from: private */
    public int oneofIndex_;
    /* access modifiers changed from: private */
    public List<Option> options_;
    /* access modifiers changed from: private */
    public boolean packed_;
    /* access modifiers changed from: private */
    public volatile Object typeUrl_;

    public enum Cardinality implements ProtocolMessageEnum {
        CARDINALITY_UNKNOWN(0),
        CARDINALITY_OPTIONAL(1),
        CARDINALITY_REQUIRED(2),
        CARDINALITY_REPEATED(3),
        UNRECOGNIZED(-1);
        
        public static final int CARDINALITY_OPTIONAL_VALUE = 1;
        public static final int CARDINALITY_REPEATED_VALUE = 3;
        public static final int CARDINALITY_REQUIRED_VALUE = 2;
        public static final int CARDINALITY_UNKNOWN_VALUE = 0;
        private static final Cardinality[] VALUES = null;
        private static final EnumLiteMap<Cardinality> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<Cardinality>() {
                public Cardinality findValueByNumber(int number) {
                    return Cardinality.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Deprecated
        public static Cardinality valueOf(int value2) {
            return forNumber(value2);
        }

        public static Cardinality forNumber(int value2) {
            switch (value2) {
                case 0:
                    return CARDINALITY_UNKNOWN;
                case 1:
                    return CARDINALITY_OPTIONAL;
                case 2:
                    return CARDINALITY_REQUIRED;
                case 3:
                    return CARDINALITY_REPEATED;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<Cardinality> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) Field.getDescriptor().getEnumTypes().get(1);
        }

        public static Cardinality valueOf(EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            } else if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            } else {
                return VALUES[desc.getIndex()];
            }
        }

        private Cardinality(int value2) {
            this.value = value2;
        }
    }

    public enum Kind implements ProtocolMessageEnum {
        TYPE_UNKNOWN(0),
        TYPE_DOUBLE(1),
        TYPE_FLOAT(2),
        TYPE_INT64(3),
        TYPE_UINT64(4),
        TYPE_INT32(5),
        TYPE_FIXED64(6),
        TYPE_FIXED32(7),
        TYPE_BOOL(8),
        TYPE_STRING(9),
        TYPE_GROUP(10),
        TYPE_MESSAGE(11),
        TYPE_BYTES(12),
        TYPE_UINT32(13),
        TYPE_ENUM(14),
        TYPE_SFIXED32(15),
        TYPE_SFIXED64(16),
        TYPE_SINT32(17),
        TYPE_SINT64(18),
        UNRECOGNIZED(-1);
        
        public static final int TYPE_BOOL_VALUE = 8;
        public static final int TYPE_BYTES_VALUE = 12;
        public static final int TYPE_DOUBLE_VALUE = 1;
        public static final int TYPE_ENUM_VALUE = 14;
        public static final int TYPE_FIXED32_VALUE = 7;
        public static final int TYPE_FIXED64_VALUE = 6;
        public static final int TYPE_FLOAT_VALUE = 2;
        public static final int TYPE_GROUP_VALUE = 10;
        public static final int TYPE_INT32_VALUE = 5;
        public static final int TYPE_INT64_VALUE = 3;
        public static final int TYPE_MESSAGE_VALUE = 11;
        public static final int TYPE_SFIXED32_VALUE = 15;
        public static final int TYPE_SFIXED64_VALUE = 16;
        public static final int TYPE_SINT32_VALUE = 17;
        public static final int TYPE_SINT64_VALUE = 18;
        public static final int TYPE_STRING_VALUE = 9;
        public static final int TYPE_UINT32_VALUE = 13;
        public static final int TYPE_UINT64_VALUE = 4;
        public static final int TYPE_UNKNOWN_VALUE = 0;
        private static final Kind[] VALUES = null;
        private static final EnumLiteMap<Kind> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<Kind>() {
                public Kind findValueByNumber(int number) {
                    return Kind.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Deprecated
        public static Kind valueOf(int value2) {
            return forNumber(value2);
        }

        public static Kind forNumber(int value2) {
            switch (value2) {
                case 0:
                    return TYPE_UNKNOWN;
                case 1:
                    return TYPE_DOUBLE;
                case 2:
                    return TYPE_FLOAT;
                case 3:
                    return TYPE_INT64;
                case 4:
                    return TYPE_UINT64;
                case 5:
                    return TYPE_INT32;
                case 6:
                    return TYPE_FIXED64;
                case 7:
                    return TYPE_FIXED32;
                case 8:
                    return TYPE_BOOL;
                case 9:
                    return TYPE_STRING;
                case 10:
                    return TYPE_GROUP;
                case 11:
                    return TYPE_MESSAGE;
                case 12:
                    return TYPE_BYTES;
                case 13:
                    return TYPE_UINT32;
                case 14:
                    return TYPE_ENUM;
                case 15:
                    return TYPE_SFIXED32;
                case 16:
                    return TYPE_SFIXED64;
                case 17:
                    return TYPE_SINT32;
                case 18:
                    return TYPE_SINT64;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<Kind> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) Field.getDescriptor().getEnumTypes().get(0);
        }

        public static Kind valueOf(EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            } else if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            } else {
                return VALUES[desc.getIndex()];
            }
        }

        private Kind(int value2) {
            this.value = value2;
        }
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements FieldOrBuilder {
        private int bitField0_;
        private int cardinality_;
        private Object defaultValue_;
        private Object jsonName_;
        private int kind_;
        private Object name_;
        private int number_;
        private int oneofIndex_;
        private RepeatedFieldBuilderV3<Option, com.google.protobuf.Option.Builder, OptionOrBuilder> optionsBuilder_;
        private List<Option> options_;
        private boolean packed_;
        private Object typeUrl_;

        public static final Descriptor getDescriptor() {
            return TypeProto.internal_static_google_protobuf_Field_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return TypeProto.internal_static_google_protobuf_Field_fieldAccessorTable.ensureFieldAccessorsInitialized(Field.class, Builder.class);
        }

        private Builder() {
            this.kind_ = 0;
            this.cardinality_ = 0;
            this.name_ = "";
            this.typeUrl_ = "";
            this.options_ = Collections.emptyList();
            this.jsonName_ = "";
            this.defaultValue_ = "";
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            this.kind_ = 0;
            this.cardinality_ = 0;
            this.name_ = "";
            this.typeUrl_ = "";
            this.options_ = Collections.emptyList();
            this.jsonName_ = "";
            this.defaultValue_ = "";
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                getOptionsFieldBuilder();
            }
        }

        public Builder clear() {
            super.clear();
            this.kind_ = 0;
            this.cardinality_ = 0;
            this.number_ = 0;
            this.name_ = "";
            this.typeUrl_ = "";
            this.oneofIndex_ = 0;
            this.packed_ = false;
            if (this.optionsBuilder_ == null) {
                this.options_ = Collections.emptyList();
                this.bitField0_ &= -129;
            } else {
                this.optionsBuilder_.clear();
            }
            this.jsonName_ = "";
            this.defaultValue_ = "";
            return this;
        }

        public Descriptor getDescriptorForType() {
            return TypeProto.internal_static_google_protobuf_Field_descriptor;
        }

        public Field getDefaultInstanceForType() {
            return Field.getDefaultInstance();
        }

        public Field build() {
            Field result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public Field buildPartial() {
            Field result = new Field((com.google.protobuf.GeneratedMessageV3.Builder) this);
            int i = this.bitField0_;
            result.kind_ = this.kind_;
            result.cardinality_ = this.cardinality_;
            result.number_ = this.number_;
            result.name_ = this.name_;
            result.typeUrl_ = this.typeUrl_;
            result.oneofIndex_ = this.oneofIndex_;
            result.packed_ = this.packed_;
            if (this.optionsBuilder_ == null) {
                if ((this.bitField0_ & 128) == 128) {
                    this.options_ = Collections.unmodifiableList(this.options_);
                    this.bitField0_ &= -129;
                }
                result.options_ = this.options_;
            } else {
                result.options_ = this.optionsBuilder_.build();
            }
            result.jsonName_ = this.jsonName_;
            result.defaultValue_ = this.defaultValue_;
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
            if (other instanceof Field) {
                return mergeFrom((Field) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Field other) {
            RepeatedFieldBuilderV3<Option, com.google.protobuf.Option.Builder, OptionOrBuilder> repeatedFieldBuilderV3 = null;
            if (other != Field.getDefaultInstance()) {
                if (other.kind_ != 0) {
                    setKindValue(other.getKindValue());
                }
                if (other.cardinality_ != 0) {
                    setCardinalityValue(other.getCardinalityValue());
                }
                if (other.getNumber() != 0) {
                    setNumber(other.getNumber());
                }
                if (!other.getName().isEmpty()) {
                    this.name_ = other.name_;
                    onChanged();
                }
                if (!other.getTypeUrl().isEmpty()) {
                    this.typeUrl_ = other.typeUrl_;
                    onChanged();
                }
                if (other.getOneofIndex() != 0) {
                    setOneofIndex(other.getOneofIndex());
                }
                if (other.getPacked()) {
                    setPacked(other.getPacked());
                }
                if (this.optionsBuilder_ == null) {
                    if (!other.options_.isEmpty()) {
                        if (this.options_.isEmpty()) {
                            this.options_ = other.options_;
                            this.bitField0_ &= -129;
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
                        this.bitField0_ &= -129;
                        if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                            repeatedFieldBuilderV3 = getOptionsFieldBuilder();
                        }
                        this.optionsBuilder_ = repeatedFieldBuilderV3;
                    } else {
                        this.optionsBuilder_.addAllMessages(other.options_);
                    }
                }
                if (!other.getJsonName().isEmpty()) {
                    this.jsonName_ = other.jsonName_;
                    onChanged();
                }
                if (!other.getDefaultValue().isEmpty()) {
                    this.defaultValue_ = other.defaultValue_;
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
            Field parsedMessage = null;
            try {
                Field parsedMessage2 = (Field) Field.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                Field parsedMessage3 = (Field) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                throw th;
            }
        }

        public int getKindValue() {
            return this.kind_;
        }

        public Builder setKindValue(int value) {
            this.kind_ = value;
            onChanged();
            return this;
        }

        public Kind getKind() {
            Kind result = Kind.valueOf(this.kind_);
            return result == null ? Kind.UNRECOGNIZED : result;
        }

        public Builder setKind(Kind value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.kind_ = value.getNumber();
            onChanged();
            return this;
        }

        public Builder clearKind() {
            this.kind_ = 0;
            onChanged();
            return this;
        }

        public int getCardinalityValue() {
            return this.cardinality_;
        }

        public Builder setCardinalityValue(int value) {
            this.cardinality_ = value;
            onChanged();
            return this;
        }

        public Cardinality getCardinality() {
            Cardinality result = Cardinality.valueOf(this.cardinality_);
            return result == null ? Cardinality.UNRECOGNIZED : result;
        }

        public Builder setCardinality(Cardinality value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.cardinality_ = value.getNumber();
            onChanged();
            return this;
        }

        public Builder clearCardinality() {
            this.cardinality_ = 0;
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
            this.name_ = Field.getDefaultInstance().getName();
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
            this.typeUrl_ = Field.getDefaultInstance().getTypeUrl();
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

        public int getOneofIndex() {
            return this.oneofIndex_;
        }

        public Builder setOneofIndex(int value) {
            this.oneofIndex_ = value;
            onChanged();
            return this;
        }

        public Builder clearOneofIndex() {
            this.oneofIndex_ = 0;
            onChanged();
            return this;
        }

        public boolean getPacked() {
            return this.packed_;
        }

        public Builder setPacked(boolean value) {
            this.packed_ = value;
            onChanged();
            return this;
        }

        public Builder clearPacked() {
            this.packed_ = false;
            onChanged();
            return this;
        }

        private void ensureOptionsIsMutable() {
            if ((this.bitField0_ & 128) != 128) {
                this.options_ = new ArrayList(this.options_);
                this.bitField0_ |= 128;
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
                this.bitField0_ &= -129;
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
                this.optionsBuilder_ = new RepeatedFieldBuilderV3<>(this.options_, (this.bitField0_ & 128) == 128, getParentForChildren(), isClean());
                this.options_ = null;
            }
            return this.optionsBuilder_;
        }

        public String getJsonName() {
            Object ref = this.jsonName_;
            if (ref instanceof String) {
                return (String) ref;
            }
            String s = ((ByteString) ref).toStringUtf8();
            this.jsonName_ = s;
            return s;
        }

        public ByteString getJsonNameBytes() {
            Object ref = this.jsonName_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.jsonName_ = b;
            return b;
        }

        public Builder setJsonName(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.jsonName_ = value;
            onChanged();
            return this;
        }

        public Builder clearJsonName() {
            this.jsonName_ = Field.getDefaultInstance().getJsonName();
            onChanged();
            return this;
        }

        public Builder setJsonNameBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.jsonName_ = value;
            onChanged();
            return this;
        }

        public String getDefaultValue() {
            Object ref = this.defaultValue_;
            if (ref instanceof String) {
                return (String) ref;
            }
            String s = ((ByteString) ref).toStringUtf8();
            this.defaultValue_ = s;
            return s;
        }

        public ByteString getDefaultValueBytes() {
            Object ref = this.defaultValue_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.defaultValue_ = b;
            return b;
        }

        public Builder setDefaultValue(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.defaultValue_ = value;
            onChanged();
            return this;
        }

        public Builder clearDefaultValue() {
            this.defaultValue_ = Field.getDefaultInstance().getDefaultValue();
            onChanged();
            return this;
        }

        public Builder setDefaultValueBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.defaultValue_ = value;
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

    private Field(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
    }

    private Field() {
        this.memoizedIsInitialized = -1;
        this.kind_ = 0;
        this.cardinality_ = 0;
        this.number_ = 0;
        this.name_ = "";
        this.typeUrl_ = "";
        this.oneofIndex_ = 0;
        this.packed_ = false;
        this.options_ = Collections.emptyList();
        this.jsonName_ = "";
        this.defaultValue_ = "";
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Field(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    case 8:
                        this.kind_ = input.readEnum();
                        break;
                    case 16:
                        this.cardinality_ = input.readEnum();
                        break;
                    case 24:
                        this.number_ = input.readInt32();
                        break;
                    case 34:
                        this.name_ = input.readStringRequireUtf8();
                        break;
                    case 50:
                        this.typeUrl_ = input.readStringRequireUtf8();
                        break;
                    case 56:
                        this.oneofIndex_ = input.readInt32();
                        break;
                    case 64:
                        this.packed_ = input.readBool();
                        break;
                    case 74:
                        if ((mutable_bitField0_ & 128) != 128) {
                            this.options_ = new ArrayList();
                            mutable_bitField0_ |= 128;
                        }
                        this.options_.add(input.readMessage(Option.parser(), extensionRegistry));
                        break;
                    case 82:
                        this.jsonName_ = input.readStringRequireUtf8();
                        break;
                    case 90:
                        this.defaultValue_ = input.readStringRequireUtf8();
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
                if ((mutable_bitField0_ & 128) == 128) {
                    this.options_ = Collections.unmodifiableList(this.options_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
                throw th;
            }
        }
        if ((mutable_bitField0_ & 128) == 128) {
            this.options_ = Collections.unmodifiableList(this.options_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
    }

    public static final Descriptor getDescriptor() {
        return TypeProto.internal_static_google_protobuf_Field_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return TypeProto.internal_static_google_protobuf_Field_fieldAccessorTable.ensureFieldAccessorsInitialized(Field.class, Builder.class);
    }

    public int getKindValue() {
        return this.kind_;
    }

    public Kind getKind() {
        Kind result = Kind.valueOf(this.kind_);
        return result == null ? Kind.UNRECOGNIZED : result;
    }

    public int getCardinalityValue() {
        return this.cardinality_;
    }

    public Cardinality getCardinality() {
        Cardinality result = Cardinality.valueOf(this.cardinality_);
        return result == null ? Cardinality.UNRECOGNIZED : result;
    }

    public int getNumber() {
        return this.number_;
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

    public int getOneofIndex() {
        return this.oneofIndex_;
    }

    public boolean getPacked() {
        return this.packed_;
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

    public String getJsonName() {
        Object ref = this.jsonName_;
        if (ref instanceof String) {
            return (String) ref;
        }
        String s = ((ByteString) ref).toStringUtf8();
        this.jsonName_ = s;
        return s;
    }

    public ByteString getJsonNameBytes() {
        Object ref = this.jsonName_;
        if (!(ref instanceof String)) {
            return (ByteString) ref;
        }
        ByteString b = ByteString.copyFromUtf8((String) ref);
        this.jsonName_ = b;
        return b;
    }

    public String getDefaultValue() {
        Object ref = this.defaultValue_;
        if (ref instanceof String) {
            return (String) ref;
        }
        String s = ((ByteString) ref).toStringUtf8();
        this.defaultValue_ = s;
        return s;
    }

    public ByteString getDefaultValueBytes() {
        Object ref = this.defaultValue_;
        if (!(ref instanceof String)) {
            return (ByteString) ref;
        }
        ByteString b = ByteString.copyFromUtf8((String) ref);
        this.defaultValue_ = b;
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
        if (this.kind_ != Kind.TYPE_UNKNOWN.getNumber()) {
            output.writeEnum(1, this.kind_);
        }
        if (this.cardinality_ != Cardinality.CARDINALITY_UNKNOWN.getNumber()) {
            output.writeEnum(2, this.cardinality_);
        }
        if (this.number_ != 0) {
            output.writeInt32(3, this.number_);
        }
        if (!getNameBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 4, this.name_);
        }
        if (!getTypeUrlBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 6, this.typeUrl_);
        }
        if (this.oneofIndex_ != 0) {
            output.writeInt32(7, this.oneofIndex_);
        }
        if (this.packed_) {
            output.writeBool(8, this.packed_);
        }
        for (int i = 0; i < this.options_.size(); i++) {
            output.writeMessage(9, (MessageLite) this.options_.get(i));
        }
        if (!getJsonNameBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 10, this.jsonName_);
        }
        if (!getDefaultValueBytes().isEmpty()) {
            GeneratedMessageV3.writeString(output, 11, this.defaultValue_);
        }
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if (this.kind_ != Kind.TYPE_UNKNOWN.getNumber()) {
            size2 = 0 + CodedOutputStream.computeEnumSize(1, this.kind_);
        }
        if (this.cardinality_ != Cardinality.CARDINALITY_UNKNOWN.getNumber()) {
            size2 += CodedOutputStream.computeEnumSize(2, this.cardinality_);
        }
        if (this.number_ != 0) {
            size2 += CodedOutputStream.computeInt32Size(3, this.number_);
        }
        if (!getNameBytes().isEmpty()) {
            size2 += GeneratedMessageV3.computeStringSize(4, this.name_);
        }
        if (!getTypeUrlBytes().isEmpty()) {
            size2 += GeneratedMessageV3.computeStringSize(6, this.typeUrl_);
        }
        if (this.oneofIndex_ != 0) {
            size2 += CodedOutputStream.computeInt32Size(7, this.oneofIndex_);
        }
        if (this.packed_) {
            size2 += CodedOutputStream.computeBoolSize(8, this.packed_);
        }
        for (int i = 0; i < this.options_.size(); i++) {
            size2 += CodedOutputStream.computeMessageSize(9, (MessageLite) this.options_.get(i));
        }
        if (!getJsonNameBytes().isEmpty()) {
            size2 += GeneratedMessageV3.computeStringSize(10, this.jsonName_);
        }
        if (!getDefaultValueBytes().isEmpty()) {
            size2 += GeneratedMessageV3.computeStringSize(11, this.defaultValue_);
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
        boolean result8;
        boolean result9;
        boolean result10;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Field)) {
            return super.equals(obj);
        }
        Field other = (Field) obj;
        if (!(1 != 0 && this.kind_ == other.kind_) || this.cardinality_ != other.cardinality_) {
            result = false;
        } else {
            result = true;
        }
        if (!result || getNumber() != other.getNumber()) {
            result2 = false;
        } else {
            result2 = true;
        }
        if (!result2 || !getName().equals(other.getName())) {
            result3 = false;
        } else {
            result3 = true;
        }
        if (!result3 || !getTypeUrl().equals(other.getTypeUrl())) {
            result4 = false;
        } else {
            result4 = true;
        }
        if (!result4 || getOneofIndex() != other.getOneofIndex()) {
            result5 = false;
        } else {
            result5 = true;
        }
        if (!result5 || getPacked() != other.getPacked()) {
            result6 = false;
        } else {
            result6 = true;
        }
        if (!result6 || !getOptionsList().equals(other.getOptionsList())) {
            result7 = false;
        } else {
            result7 = true;
        }
        if (!result7 || !getJsonName().equals(other.getJsonName())) {
            result8 = false;
        } else {
            result8 = true;
        }
        if (!result8 || !getDefaultValue().equals(other.getDefaultValue())) {
            result9 = false;
        } else {
            result9 = true;
        }
        if (!result9 || !this.unknownFields.equals(other.unknownFields)) {
            result10 = false;
        } else {
            result10 = true;
        }
        return result10;
    }

    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int hash = ((((((((((((((((((((((((((((getDescriptor().hashCode() + 779) * 37) + 1) * 53) + this.kind_) * 37) + 2) * 53) + this.cardinality_) * 37) + 3) * 53) + getNumber()) * 37) + 4) * 53) + getName().hashCode()) * 37) + 6) * 53) + getTypeUrl().hashCode()) * 37) + 7) * 53) + getOneofIndex()) * 37) + 8) * 53) + Internal.hashBoolean(getPacked());
        if (getOptionsCount() > 0) {
            hash = (((hash * 37) + 9) * 53) + getOptionsList().hashCode();
        }
        int hash2 = (((((((((hash * 37) + 10) * 53) + getJsonName().hashCode()) * 37) + 11) * 53) + getDefaultValue().hashCode()) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash2;
        return hash2;
    }

    public static Field parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Field) PARSER.parseFrom(data);
    }

    public static Field parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Field) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Field parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Field) PARSER.parseFrom(data);
    }

    public static Field parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Field) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Field parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Field) PARSER.parseFrom(data);
    }

    public static Field parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Field) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Field parseFrom(InputStream input) throws IOException {
        return (Field) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Field parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Field) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Field parseDelimitedFrom(InputStream input) throws IOException {
        return (Field) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Field parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Field) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Field parseFrom(CodedInputStream input) throws IOException {
        return (Field) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Field parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Field) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Field prototype) {
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

    public static Field getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Field> parser() {
        return PARSER;
    }

    public Parser<Field> getParserForType() {
        return PARSER;
    }

    public Field getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
