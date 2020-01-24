package com.google.protobuf;

import com.github.mikephil.charting.utils.Utils;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import com.google.protobuf.Internal.EnumLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Value extends GeneratedMessageV3 implements ValueOrBuilder {
    public static final int BOOL_VALUE_FIELD_NUMBER = 4;
    private static final Value DEFAULT_INSTANCE = new Value();
    public static final int LIST_VALUE_FIELD_NUMBER = 6;
    public static final int NULL_VALUE_FIELD_NUMBER = 1;
    public static final int NUMBER_VALUE_FIELD_NUMBER = 2;
    /* access modifiers changed from: private */
    public static final Parser<Value> PARSER = new AbstractParser<Value>() {
        public Value parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new Value(input, extensionRegistry);
        }
    };
    public static final int STRING_VALUE_FIELD_NUMBER = 3;
    public static final int STRUCT_VALUE_FIELD_NUMBER = 5;
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public int kindCase_;
    /* access modifiers changed from: private */
    public Object kind_;
    private byte memoizedIsInitialized;

    public enum KindCase implements EnumLite {
        NULL_VALUE(1),
        NUMBER_VALUE(2),
        STRING_VALUE(3),
        BOOL_VALUE(4),
        STRUCT_VALUE(5),
        LIST_VALUE(6),
        KIND_NOT_SET(0);
        
        private final int value;

        private KindCase(int value2) {
            this.value = value2;
        }

        @Deprecated
        public static KindCase valueOf(int value2) {
            return forNumber(value2);
        }

        public static KindCase forNumber(int value2) {
            switch (value2) {
                case 0:
                    return KIND_NOT_SET;
                case 1:
                    return NULL_VALUE;
                case 2:
                    return NUMBER_VALUE;
                case 3:
                    return STRING_VALUE;
                case 4:
                    return BOOL_VALUE;
                case 5:
                    return STRUCT_VALUE;
                case 6:
                    return LIST_VALUE;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements ValueOrBuilder {
        private int kindCase_;
        private Object kind_;
        private SingleFieldBuilderV3<ListValue, com.google.protobuf.ListValue.Builder, ListValueOrBuilder> listValueBuilder_;
        private SingleFieldBuilderV3<Struct, com.google.protobuf.Struct.Builder, StructOrBuilder> structValueBuilder_;

        public static final Descriptor getDescriptor() {
            return StructProto.internal_static_google_protobuf_Value_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return StructProto.internal_static_google_protobuf_Value_fieldAccessorTable.ensureFieldAccessorsInitialized(Value.class, Builder.class);
        }

        private Builder() {
            this.kindCase_ = 0;
            maybeForceBuilderInitialization();
        }

        private Builder(BuilderParent parent) {
            super(parent);
            this.kindCase_ = 0;
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3.alwaysUseFieldBuilders) {
            }
        }

        public Builder clear() {
            super.clear();
            this.kindCase_ = 0;
            this.kind_ = null;
            return this;
        }

        public Descriptor getDescriptorForType() {
            return StructProto.internal_static_google_protobuf_Value_descriptor;
        }

        public Value getDefaultInstanceForType() {
            return Value.getDefaultInstance();
        }

        public Value build() {
            Value result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        public Value buildPartial() {
            Value result = new Value((com.google.protobuf.GeneratedMessageV3.Builder) this);
            if (this.kindCase_ == 1) {
                result.kind_ = this.kind_;
            }
            if (this.kindCase_ == 2) {
                result.kind_ = this.kind_;
            }
            if (this.kindCase_ == 3) {
                result.kind_ = this.kind_;
            }
            if (this.kindCase_ == 4) {
                result.kind_ = this.kind_;
            }
            if (this.kindCase_ == 5) {
                if (this.structValueBuilder_ == null) {
                    result.kind_ = this.kind_;
                } else {
                    result.kind_ = this.structValueBuilder_.build();
                }
            }
            if (this.kindCase_ == 6) {
                if (this.listValueBuilder_ == null) {
                    result.kind_ = this.kind_;
                } else {
                    result.kind_ = this.listValueBuilder_.build();
                }
            }
            result.kindCase_ = this.kindCase_;
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
            if (other instanceof Value) {
                return mergeFrom((Value) other);
            }
            super.mergeFrom(other);
            return this;
        }

        public Builder mergeFrom(Value other) {
            if (other != Value.getDefaultInstance()) {
                switch (other.getKindCase()) {
                    case NULL_VALUE:
                        setNullValueValue(other.getNullValueValue());
                        break;
                    case NUMBER_VALUE:
                        setNumberValue(other.getNumberValue());
                        break;
                    case STRING_VALUE:
                        this.kindCase_ = 3;
                        this.kind_ = other.kind_;
                        onChanged();
                        break;
                    case BOOL_VALUE:
                        setBoolValue(other.getBoolValue());
                        break;
                    case STRUCT_VALUE:
                        mergeStructValue(other.getStructValue());
                        break;
                    case LIST_VALUE:
                        mergeListValue(other.getListValue());
                        break;
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
            Value parsedMessage = null;
            try {
                Value parsedMessage2 = (Value) Value.PARSER.parsePartialFrom(input, extensionRegistry);
                if (parsedMessage2 != null) {
                    mergeFrom(parsedMessage2);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                Value parsedMessage3 = (Value) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } catch (Throwable th) {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
                throw th;
            }
        }

        public KindCase getKindCase() {
            return KindCase.forNumber(this.kindCase_);
        }

        public Builder clearKind() {
            this.kindCase_ = 0;
            this.kind_ = null;
            onChanged();
            return this;
        }

        public int getNullValueValue() {
            if (this.kindCase_ == 1) {
                return ((Integer) this.kind_).intValue();
            }
            return 0;
        }

        public Builder setNullValueValue(int value) {
            this.kindCase_ = 1;
            this.kind_ = Integer.valueOf(value);
            onChanged();
            return this;
        }

        public NullValue getNullValue() {
            if (this.kindCase_ != 1) {
                return NullValue.NULL_VALUE;
            }
            NullValue result = NullValue.valueOf(((Integer) this.kind_).intValue());
            if (result == null) {
                return NullValue.UNRECOGNIZED;
            }
            return result;
        }

        public Builder setNullValue(NullValue value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.kindCase_ = 1;
            this.kind_ = Integer.valueOf(value.getNumber());
            onChanged();
            return this;
        }

        public Builder clearNullValue() {
            if (this.kindCase_ == 1) {
                this.kindCase_ = 0;
                this.kind_ = null;
                onChanged();
            }
            return this;
        }

        public double getNumberValue() {
            if (this.kindCase_ == 2) {
                return ((Double) this.kind_).doubleValue();
            }
            return Utils.DOUBLE_EPSILON;
        }

        public Builder setNumberValue(double value) {
            this.kindCase_ = 2;
            this.kind_ = Double.valueOf(value);
            onChanged();
            return this;
        }

        public Builder clearNumberValue() {
            if (this.kindCase_ == 2) {
                this.kindCase_ = 0;
                this.kind_ = null;
                onChanged();
            }
            return this;
        }

        public String getStringValue() {
            Object obj = "";
            if (this.kindCase_ == 3) {
                obj = this.kind_;
            }
            if (obj instanceof String) {
                return (String) obj;
            }
            String s = ((ByteString) obj).toStringUtf8();
            if (this.kindCase_ != 3) {
                return s;
            }
            this.kind_ = s;
            return s;
        }

        public ByteString getStringValueBytes() {
            Object obj = "";
            if (this.kindCase_ == 3) {
                obj = this.kind_;
            }
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString b = ByteString.copyFromUtf8((String) obj);
            if (this.kindCase_ != 3) {
                return b;
            }
            this.kind_ = b;
            return b;
        }

        public Builder setStringValue(String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.kindCase_ = 3;
            this.kind_ = value;
            onChanged();
            return this;
        }

        public Builder clearStringValue() {
            if (this.kindCase_ == 3) {
                this.kindCase_ = 0;
                this.kind_ = null;
                onChanged();
            }
            return this;
        }

        public Builder setStringValueBytes(ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            AbstractMessageLite.checkByteStringIsUtf8(value);
            this.kindCase_ = 3;
            this.kind_ = value;
            onChanged();
            return this;
        }

        public boolean getBoolValue() {
            if (this.kindCase_ == 4) {
                return ((Boolean) this.kind_).booleanValue();
            }
            return false;
        }

        public Builder setBoolValue(boolean value) {
            this.kindCase_ = 4;
            this.kind_ = Boolean.valueOf(value);
            onChanged();
            return this;
        }

        public Builder clearBoolValue() {
            if (this.kindCase_ == 4) {
                this.kindCase_ = 0;
                this.kind_ = null;
                onChanged();
            }
            return this;
        }

        public boolean hasStructValue() {
            return this.kindCase_ == 5;
        }

        public Struct getStructValue() {
            if (this.structValueBuilder_ == null) {
                if (this.kindCase_ == 5) {
                    return (Struct) this.kind_;
                }
                return Struct.getDefaultInstance();
            } else if (this.kindCase_ == 5) {
                return (Struct) this.structValueBuilder_.getMessage();
            } else {
                return Struct.getDefaultInstance();
            }
        }

        public Builder setStructValue(Struct value) {
            if (this.structValueBuilder_ != null) {
                this.structValueBuilder_.setMessage(value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                this.kind_ = value;
                onChanged();
            }
            this.kindCase_ = 5;
            return this;
        }

        public Builder setStructValue(com.google.protobuf.Struct.Builder builderForValue) {
            if (this.structValueBuilder_ == null) {
                this.kind_ = builderForValue.build();
                onChanged();
            } else {
                this.structValueBuilder_.setMessage(builderForValue.build());
            }
            this.kindCase_ = 5;
            return this;
        }

        public Builder mergeStructValue(Struct value) {
            if (this.structValueBuilder_ == null) {
                if (this.kindCase_ != 5 || this.kind_ == Struct.getDefaultInstance()) {
                    this.kind_ = value;
                } else {
                    this.kind_ = Struct.newBuilder((Struct) this.kind_).mergeFrom(value).buildPartial();
                }
                onChanged();
            } else {
                if (this.kindCase_ == 5) {
                    this.structValueBuilder_.mergeFrom(value);
                }
                this.structValueBuilder_.setMessage(value);
            }
            this.kindCase_ = 5;
            return this;
        }

        public Builder clearStructValue() {
            if (this.structValueBuilder_ != null) {
                if (this.kindCase_ == 5) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                }
                this.structValueBuilder_.clear();
            } else if (this.kindCase_ == 5) {
                this.kindCase_ = 0;
                this.kind_ = null;
                onChanged();
            }
            return this;
        }

        public com.google.protobuf.Struct.Builder getStructValueBuilder() {
            return (com.google.protobuf.Struct.Builder) getStructValueFieldBuilder().getBuilder();
        }

        public StructOrBuilder getStructValueOrBuilder() {
            if (this.kindCase_ == 5 && this.structValueBuilder_ != null) {
                return (StructOrBuilder) this.structValueBuilder_.getMessageOrBuilder();
            }
            if (this.kindCase_ == 5) {
                return (Struct) this.kind_;
            }
            return Struct.getDefaultInstance();
        }

        private SingleFieldBuilderV3<Struct, com.google.protobuf.Struct.Builder, StructOrBuilder> getStructValueFieldBuilder() {
            if (this.structValueBuilder_ == null) {
                if (this.kindCase_ != 5) {
                    this.kind_ = Struct.getDefaultInstance();
                }
                this.structValueBuilder_ = new SingleFieldBuilderV3<>((Struct) this.kind_, getParentForChildren(), isClean());
                this.kind_ = null;
            }
            this.kindCase_ = 5;
            onChanged();
            return this.structValueBuilder_;
        }

        public boolean hasListValue() {
            return this.kindCase_ == 6;
        }

        public ListValue getListValue() {
            if (this.listValueBuilder_ == null) {
                if (this.kindCase_ == 6) {
                    return (ListValue) this.kind_;
                }
                return ListValue.getDefaultInstance();
            } else if (this.kindCase_ == 6) {
                return (ListValue) this.listValueBuilder_.getMessage();
            } else {
                return ListValue.getDefaultInstance();
            }
        }

        public Builder setListValue(ListValue value) {
            if (this.listValueBuilder_ != null) {
                this.listValueBuilder_.setMessage(value);
            } else if (value == null) {
                throw new NullPointerException();
            } else {
                this.kind_ = value;
                onChanged();
            }
            this.kindCase_ = 6;
            return this;
        }

        public Builder setListValue(com.google.protobuf.ListValue.Builder builderForValue) {
            if (this.listValueBuilder_ == null) {
                this.kind_ = builderForValue.build();
                onChanged();
            } else {
                this.listValueBuilder_.setMessage(builderForValue.build());
            }
            this.kindCase_ = 6;
            return this;
        }

        public Builder mergeListValue(ListValue value) {
            if (this.listValueBuilder_ == null) {
                if (this.kindCase_ != 6 || this.kind_ == ListValue.getDefaultInstance()) {
                    this.kind_ = value;
                } else {
                    this.kind_ = ListValue.newBuilder((ListValue) this.kind_).mergeFrom(value).buildPartial();
                }
                onChanged();
            } else {
                if (this.kindCase_ == 6) {
                    this.listValueBuilder_.mergeFrom(value);
                }
                this.listValueBuilder_.setMessage(value);
            }
            this.kindCase_ = 6;
            return this;
        }

        public Builder clearListValue() {
            if (this.listValueBuilder_ != null) {
                if (this.kindCase_ == 6) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                }
                this.listValueBuilder_.clear();
            } else if (this.kindCase_ == 6) {
                this.kindCase_ = 0;
                this.kind_ = null;
                onChanged();
            }
            return this;
        }

        public com.google.protobuf.ListValue.Builder getListValueBuilder() {
            return (com.google.protobuf.ListValue.Builder) getListValueFieldBuilder().getBuilder();
        }

        public ListValueOrBuilder getListValueOrBuilder() {
            if (this.kindCase_ == 6 && this.listValueBuilder_ != null) {
                return (ListValueOrBuilder) this.listValueBuilder_.getMessageOrBuilder();
            }
            if (this.kindCase_ == 6) {
                return (ListValue) this.kind_;
            }
            return ListValue.getDefaultInstance();
        }

        private SingleFieldBuilderV3<ListValue, com.google.protobuf.ListValue.Builder, ListValueOrBuilder> getListValueFieldBuilder() {
            if (this.listValueBuilder_ == null) {
                if (this.kindCase_ != 6) {
                    this.kind_ = ListValue.getDefaultInstance();
                }
                this.listValueBuilder_ = new SingleFieldBuilderV3<>((ListValue) this.kind_, getParentForChildren(), isClean());
                this.kind_ = null;
            }
            this.kindCase_ = 6;
            onChanged();
            return this.listValueBuilder_;
        }

        public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.setUnknownFieldsProto3(unknownFields);
        }

        public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            return (Builder) super.mergeUnknownFields(unknownFields);
        }
    }

    private Value(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.kindCase_ = 0;
        this.memoizedIsInitialized = -1;
    }

    private Value() {
        this.kindCase_ = 0;
        this.memoizedIsInitialized = -1;
    }

    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private Value(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    case 8:
                        int rawValue = input.readEnum();
                        this.kindCase_ = 1;
                        this.kind_ = Integer.valueOf(rawValue);
                        break;
                    case 17:
                        this.kindCase_ = 2;
                        this.kind_ = Double.valueOf(input.readDouble());
                        break;
                    case 26:
                        String s = input.readStringRequireUtf8();
                        this.kindCase_ = 3;
                        this.kind_ = s;
                        break;
                    case 32:
                        this.kindCase_ = 4;
                        this.kind_ = Boolean.valueOf(input.readBool());
                        break;
                    case 42:
                        com.google.protobuf.Struct.Builder subBuilder = null;
                        if (this.kindCase_ == 5) {
                            subBuilder = ((Struct) this.kind_).toBuilder();
                        }
                        this.kind_ = input.readMessage(Struct.parser(), extensionRegistry);
                        if (subBuilder != null) {
                            subBuilder.mergeFrom((Struct) this.kind_);
                            this.kind_ = subBuilder.buildPartial();
                        }
                        this.kindCase_ = 5;
                        break;
                    case 50:
                        com.google.protobuf.ListValue.Builder subBuilder2 = null;
                        if (this.kindCase_ == 6) {
                            subBuilder2 = ((ListValue) this.kind_).toBuilder();
                        }
                        this.kind_ = input.readMessage(ListValue.parser(), extensionRegistry);
                        if (subBuilder2 != null) {
                            subBuilder2.mergeFrom((ListValue) this.kind_);
                            this.kind_ = subBuilder2.buildPartial();
                        }
                        this.kindCase_ = 6;
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
        return StructProto.internal_static_google_protobuf_Value_descriptor;
    }

    /* access modifiers changed from: protected */
    public FieldAccessorTable internalGetFieldAccessorTable() {
        return StructProto.internal_static_google_protobuf_Value_fieldAccessorTable.ensureFieldAccessorsInitialized(Value.class, Builder.class);
    }

    public KindCase getKindCase() {
        return KindCase.forNumber(this.kindCase_);
    }

    public int getNullValueValue() {
        if (this.kindCase_ == 1) {
            return ((Integer) this.kind_).intValue();
        }
        return 0;
    }

    public NullValue getNullValue() {
        if (this.kindCase_ != 1) {
            return NullValue.NULL_VALUE;
        }
        NullValue result = NullValue.valueOf(((Integer) this.kind_).intValue());
        if (result == null) {
            return NullValue.UNRECOGNIZED;
        }
        return result;
    }

    public double getNumberValue() {
        if (this.kindCase_ == 2) {
            return ((Double) this.kind_).doubleValue();
        }
        return Utils.DOUBLE_EPSILON;
    }

    public String getStringValue() {
        Object obj = "";
        if (this.kindCase_ == 3) {
            obj = this.kind_;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        String s = ((ByteString) obj).toStringUtf8();
        if (this.kindCase_ == 3) {
            this.kind_ = s;
        }
        return s;
    }

    public ByteString getStringValueBytes() {
        Object obj = "";
        if (this.kindCase_ == 3) {
            obj = this.kind_;
        }
        if (!(obj instanceof String)) {
            return (ByteString) obj;
        }
        ByteString b = ByteString.copyFromUtf8((String) obj);
        if (this.kindCase_ != 3) {
            return b;
        }
        this.kind_ = b;
        return b;
    }

    public boolean getBoolValue() {
        if (this.kindCase_ == 4) {
            return ((Boolean) this.kind_).booleanValue();
        }
        return false;
    }

    public boolean hasStructValue() {
        return this.kindCase_ == 5;
    }

    public Struct getStructValue() {
        if (this.kindCase_ == 5) {
            return (Struct) this.kind_;
        }
        return Struct.getDefaultInstance();
    }

    public StructOrBuilder getStructValueOrBuilder() {
        if (this.kindCase_ == 5) {
            return (Struct) this.kind_;
        }
        return Struct.getDefaultInstance();
    }

    public boolean hasListValue() {
        return this.kindCase_ == 6;
    }

    public ListValue getListValue() {
        if (this.kindCase_ == 6) {
            return (ListValue) this.kind_;
        }
        return ListValue.getDefaultInstance();
    }

    public ListValueOrBuilder getListValueOrBuilder() {
        if (this.kindCase_ == 6) {
            return (ListValue) this.kind_;
        }
        return ListValue.getDefaultInstance();
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
        if (this.kindCase_ == 1) {
            output.writeEnum(1, ((Integer) this.kind_).intValue());
        }
        if (this.kindCase_ == 2) {
            output.writeDouble(2, ((Double) this.kind_).doubleValue());
        }
        if (this.kindCase_ == 3) {
            GeneratedMessageV3.writeString(output, 3, this.kind_);
        }
        if (this.kindCase_ == 4) {
            output.writeBool(4, ((Boolean) this.kind_).booleanValue());
        }
        if (this.kindCase_ == 5) {
            output.writeMessage(5, (Struct) this.kind_);
        }
        if (this.kindCase_ == 6) {
            output.writeMessage(6, (ListValue) this.kind_);
        }
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if (this.kindCase_ == 1) {
            size2 = 0 + CodedOutputStream.computeEnumSize(1, ((Integer) this.kind_).intValue());
        }
        if (this.kindCase_ == 2) {
            size2 += CodedOutputStream.computeDoubleSize(2, ((Double) this.kind_).doubleValue());
        }
        if (this.kindCase_ == 3) {
            size2 += GeneratedMessageV3.computeStringSize(3, this.kind_);
        }
        if (this.kindCase_ == 4) {
            size2 += CodedOutputStream.computeBoolSize(4, ((Boolean) this.kind_).booleanValue());
        }
        if (this.kindCase_ == 5) {
            size2 += CodedOutputStream.computeMessageSize(5, (Struct) this.kind_);
        }
        if (this.kindCase_ == 6) {
            size2 += CodedOutputStream.computeMessageSize(6, (ListValue) this.kind_);
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
        if (!(obj instanceof Value)) {
            return super.equals(obj);
        }
        Value other = (Value) obj;
        if (1 == 0 || !getKindCase().equals(other.getKindCase())) {
            result = false;
        } else {
            result = true;
        }
        if (!result) {
            return false;
        }
        switch (this.kindCase_) {
            case 1:
                if (result && getNullValueValue() == other.getNullValueValue()) {
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
            case 2:
                if (result && Double.doubleToLongBits(getNumberValue()) == Double.doubleToLongBits(other.getNumberValue())) {
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
            case 3:
                if (result && getStringValue().equals(other.getStringValue())) {
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
                break;
            case 4:
                if (result && getBoolValue() == other.getBoolValue()) {
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
            case 5:
                if (result && getStructValue().equals(other.getStructValue())) {
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
                break;
            case 6:
                if (result && getListValue().equals(other.getListValue())) {
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
                break;
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
        switch (this.kindCase_) {
            case 1:
                hash = (((hash * 37) + 1) * 53) + getNullValueValue();
                break;
            case 2:
                hash = (((hash * 37) + 2) * 53) + Internal.hashLong(Double.doubleToLongBits(getNumberValue()));
                break;
            case 3:
                hash = (((hash * 37) + 3) * 53) + getStringValue().hashCode();
                break;
            case 4:
                hash = (((hash * 37) + 4) * 53) + Internal.hashBoolean(getBoolValue());
                break;
            case 5:
                hash = (((hash * 37) + 5) * 53) + getStructValue().hashCode();
                break;
            case 6:
                hash = (((hash * 37) + 6) * 53) + getListValue().hashCode();
                break;
        }
        int hash2 = (hash * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = hash2;
        return hash2;
    }

    public static Value parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Value) PARSER.parseFrom(data);
    }

    public static Value parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Value) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Value parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Value) PARSER.parseFrom(data);
    }

    public static Value parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Value) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Value parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Value) PARSER.parseFrom(data);
    }

    public static Value parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Value) PARSER.parseFrom(data, extensionRegistry);
    }

    public static Value parseFrom(InputStream input) throws IOException {
        return (Value) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Value parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Value) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Value parseDelimitedFrom(InputStream input) throws IOException {
        return (Value) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
    }

    public static Value parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Value) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Value parseFrom(CodedInputStream input) throws IOException {
        return (Value) GeneratedMessageV3.parseWithIOException(PARSER, input);
    }

    public static Value parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Value) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Value prototype) {
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

    public static Value getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Value> parser() {
        return PARSER;
    }

    public Parser<Value> getParserForType() {
        return PARSER;
    }

    public Value getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
