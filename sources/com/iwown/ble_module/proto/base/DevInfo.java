package com.iwown.ble_module.proto.base;

import com.google.common.net.HttpHeaders;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;

public final class DevInfo {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_DevInfoManu_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_DevInfoManu_fieldAccessorTable = new FieldAccessorTable(internal_static_DevInfoManu_descriptor, new String[]{HttpHeaders.DATE, "Factory"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_DeviceInfoRequest_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_DeviceInfoRequest_fieldAccessorTable = new FieldAccessorTable(internal_static_DeviceInfoRequest_descriptor, new String[]{"Reserved"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_DeviceInfoResponse_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_DeviceInfoResponse_fieldAccessorTable = new FieldAccessorTable(internal_static_DeviceInfoResponse_descriptor, new String[]{"Model", "Version", "Mac", "Fota", "Manu"});

    public enum DevInfoFota implements ProtocolMessageEnum {
        NON(0),
        CC2540(1),
        NRF51(2),
        DA1468X(3),
        MT2523(4),
        NRF52_BLE(5),
        NRF52_SERIAL(6);
        
        public static final int CC2540_VALUE = 1;
        public static final int DA1468X_VALUE = 3;
        public static final int MT2523_VALUE = 4;
        public static final int NON_VALUE = 0;
        public static final int NRF51_VALUE = 2;
        public static final int NRF52_BLE_VALUE = 5;
        public static final int NRF52_SERIAL_VALUE = 6;
        private static final DevInfoFota[] VALUES = null;
        private static final EnumLiteMap<DevInfoFota> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<DevInfoFota>() {
                public DevInfoFota findValueByNumber(int number) {
                    return DevInfoFota.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static DevInfoFota valueOf(int value2) {
            return forNumber(value2);
        }

        public static DevInfoFota forNumber(int value2) {
            switch (value2) {
                case 0:
                    return NON;
                case 1:
                    return CC2540;
                case 2:
                    return NRF51;
                case 3:
                    return DA1468X;
                case 4:
                    return MT2523;
                case 5:
                    return NRF52_BLE;
                case 6:
                    return NRF52_SERIAL;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<DevInfoFota> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) DevInfo.getDescriptor().getEnumTypes().get(0);
        }

        public static DevInfoFota valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private DevInfoFota(int value2) {
            this.value = value2;
        }
    }

    public static final class DevInfoManu extends GeneratedMessageV3 implements DevInfoManuOrBuilder {
        public static final int DATE_FIELD_NUMBER = 1;
        private static final DevInfoManu DEFAULT_INSTANCE = new DevInfoManu();
        public static final int FACTORY_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<DevInfoManu> PARSER = new AbstractParser<DevInfoManu>() {
            public DevInfoManu parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DevInfoManu(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public volatile Object date_;
        /* access modifiers changed from: private */
        public volatile Object factory_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements DevInfoManuOrBuilder {
            private int bitField0_;
            private Object date_;
            private Object factory_;

            public static final Descriptor getDescriptor() {
                return DevInfo.internal_static_DevInfoManu_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DevInfo.internal_static_DevInfoManu_fieldAccessorTable.ensureFieldAccessorsInitialized(DevInfoManu.class, Builder.class);
            }

            private Builder() {
                this.date_ = "";
                this.factory_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.date_ = "";
                this.factory_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (DevInfoManu.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.date_ = "";
                this.bitField0_ &= -2;
                this.factory_ = "";
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DevInfo.internal_static_DevInfoManu_descriptor;
            }

            public DevInfoManu getDefaultInstanceForType() {
                return DevInfoManu.getDefaultInstance();
            }

            public DevInfoManu build() {
                DevInfoManu result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public DevInfoManu buildPartial() {
                DevInfoManu result = new DevInfoManu((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.date_ = this.date_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.factory_ = this.factory_;
                result.bitField0_ = to_bitField0_;
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
                if (other instanceof DevInfoManu) {
                    return mergeFrom((DevInfoManu) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DevInfoManu other) {
                if (other != DevInfoManu.getDefaultInstance()) {
                    if (other.hasDate()) {
                        this.bitField0_ |= 1;
                        this.date_ = other.date_;
                        onChanged();
                    }
                    if (other.hasFactory()) {
                        this.bitField0_ |= 2;
                        this.factory_ = other.factory_;
                        onChanged();
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasDate() && hasFactory()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                DevInfoManu parsedMessage = null;
                try {
                    DevInfoManu parsedMessage2 = (DevInfoManu) DevInfoManu.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    DevInfoManu parsedMessage3 = (DevInfoManu) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasDate() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getDate() {
                Object ref = this.date_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.date_ = s;
                return s;
            }

            public ByteString getDateBytes() {
                Object ref = this.date_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.date_ = b;
                return b;
            }

            public Builder setDate(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.date_ = value;
                onChanged();
                return this;
            }

            public Builder clearDate() {
                this.bitField0_ &= -2;
                this.date_ = DevInfoManu.getDefaultInstance().getDate();
                onChanged();
                return this;
            }

            public Builder setDateBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.date_ = value;
                onChanged();
                return this;
            }

            public boolean hasFactory() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getFactory() {
                Object ref = this.factory_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.factory_ = s;
                return s;
            }

            public ByteString getFactoryBytes() {
                Object ref = this.factory_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.factory_ = b;
                return b;
            }

            public Builder setFactory(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.factory_ = value;
                onChanged();
                return this;
            }

            public Builder clearFactory() {
                this.bitField0_ &= -3;
                this.factory_ = DevInfoManu.getDefaultInstance().getFactory();
                onChanged();
                return this;
            }

            public Builder setFactoryBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.factory_ = value;
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private DevInfoManu(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private DevInfoManu() {
            this.memoizedIsInitialized = -1;
            this.date_ = "";
            this.factory_ = "";
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DevInfoManu(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
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
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.date_ = bs;
                            break;
                        case 18:
                            ByteString bs2 = input.readBytes();
                            this.bitField0_ |= 2;
                            this.factory_ = bs2;
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
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
            return DevInfo.internal_static_DevInfoManu_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DevInfo.internal_static_DevInfoManu_fieldAccessorTable.ensureFieldAccessorsInitialized(DevInfoManu.class, Builder.class);
        }

        public boolean hasDate() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getDate() {
            Object ref = this.date_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.date_ = s;
            }
            return s;
        }

        public ByteString getDateBytes() {
            Object ref = this.date_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.date_ = b;
            return b;
        }

        public boolean hasFactory() {
            return (this.bitField0_ & 2) == 2;
        }

        public String getFactory() {
            Object ref = this.factory_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.factory_ = s;
            }
            return s;
        }

        public ByteString getFactoryBytes() {
            Object ref = this.factory_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.factory_ = b;
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
            if (!hasDate()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasFactory()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                GeneratedMessageV3.writeString(output, 1, this.date_);
            }
            if ((this.bitField0_ & 2) == 2) {
                GeneratedMessageV3.writeString(output, 2, this.factory_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.date_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += GeneratedMessageV3.computeStringSize(2, this.factory_);
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
            if (!(obj instanceof DevInfoManu)) {
                return super.equals(obj);
            }
            DevInfoManu other = (DevInfoManu) obj;
            boolean result3 = 1 != 0 && hasDate() == other.hasDate();
            if (hasDate()) {
                result3 = result3 && getDate().equals(other.getDate());
            }
            if (!result3 || hasFactory() != other.hasFactory()) {
                result = false;
            } else {
                result = true;
            }
            if (hasFactory()) {
                if (!result || !getFactory().equals(other.getFactory())) {
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
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasDate()) {
                hash = (((hash * 37) + 1) * 53) + getDate().hashCode();
            }
            if (hasFactory()) {
                hash = (((hash * 37) + 2) * 53) + getFactory().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static DevInfoManu parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DevInfoManu) PARSER.parseFrom(data);
        }

        public static DevInfoManu parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DevInfoManu) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DevInfoManu parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DevInfoManu) PARSER.parseFrom(data);
        }

        public static DevInfoManu parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DevInfoManu) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DevInfoManu parseFrom(InputStream input) throws IOException {
            return (DevInfoManu) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DevInfoManu parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DevInfoManu) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DevInfoManu parseDelimitedFrom(InputStream input) throws IOException {
            return (DevInfoManu) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DevInfoManu parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DevInfoManu) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DevInfoManu parseFrom(CodedInputStream input) throws IOException {
            return (DevInfoManu) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DevInfoManu parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DevInfoManu) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DevInfoManu prototype) {
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

        public static DevInfoManu getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DevInfoManu> parser() {
            return PARSER;
        }

        public Parser<DevInfoManu> getParserForType() {
            return PARSER;
        }

        public DevInfoManu getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class DeviceInfoRequest extends GeneratedMessageV3 implements DeviceInfoRequestOrBuilder {
        private static final DeviceInfoRequest DEFAULT_INSTANCE = new DeviceInfoRequest();
        @Deprecated
        public static final Parser<DeviceInfoRequest> PARSER = new AbstractParser<DeviceInfoRequest>() {
            public DeviceInfoRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DeviceInfoRequest(input, extensionRegistry);
            }
        };
        public static final int RESERVED_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int reserved_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements DeviceInfoRequestOrBuilder {
            private int bitField0_;
            private int reserved_;

            public static final Descriptor getDescriptor() {
                return DevInfo.internal_static_DeviceInfoRequest_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DevInfo.internal_static_DeviceInfoRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceInfoRequest.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (DeviceInfoRequest.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.reserved_ = 0;
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DevInfo.internal_static_DeviceInfoRequest_descriptor;
            }

            public DeviceInfoRequest getDefaultInstanceForType() {
                return DeviceInfoRequest.getDefaultInstance();
            }

            public DeviceInfoRequest build() {
                DeviceInfoRequest result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public DeviceInfoRequest buildPartial() {
                DeviceInfoRequest result = new DeviceInfoRequest((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.reserved_ = this.reserved_;
                result.bitField0_ = to_bitField0_;
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
                if (other instanceof DeviceInfoRequest) {
                    return mergeFrom((DeviceInfoRequest) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeviceInfoRequest other) {
                if (other != DeviceInfoRequest.getDefaultInstance()) {
                    if (other.hasReserved()) {
                        setReserved(other.getReserved());
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
                DeviceInfoRequest parsedMessage = null;
                try {
                    DeviceInfoRequest parsedMessage2 = (DeviceInfoRequest) DeviceInfoRequest.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    DeviceInfoRequest parsedMessage3 = (DeviceInfoRequest) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasReserved() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getReserved() {
                return this.reserved_;
            }

            public Builder setReserved(int value) {
                this.bitField0_ |= 1;
                this.reserved_ = value;
                onChanged();
                return this;
            }

            public Builder clearReserved() {
                this.bitField0_ &= -2;
                this.reserved_ = 0;
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private DeviceInfoRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private DeviceInfoRequest() {
            this.memoizedIsInitialized = -1;
            this.reserved_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DeviceInfoRequest(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 13:
                            this.bitField0_ |= 1;
                            this.reserved_ = input.readFixed32();
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
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
            return DevInfo.internal_static_DeviceInfoRequest_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DevInfo.internal_static_DeviceInfoRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceInfoRequest.class, Builder.class);
        }

        public boolean hasReserved() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getReserved() {
            return this.reserved_;
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
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.reserved_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.reserved_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            boolean result;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DeviceInfoRequest)) {
                return super.equals(obj);
            }
            DeviceInfoRequest other = (DeviceInfoRequest) obj;
            boolean result2 = 1 != 0 && hasReserved() == other.hasReserved();
            if (hasReserved()) {
                result2 = result2 && getReserved() == other.getReserved();
            }
            if (!result2 || !this.unknownFields.equals(other.unknownFields)) {
                result = false;
            } else {
                result = true;
            }
            return result;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasReserved()) {
                hash = (((hash * 37) + 1) * 53) + getReserved();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static DeviceInfoRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceInfoRequest) PARSER.parseFrom(data);
        }

        public static DeviceInfoRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceInfoRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceInfoRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceInfoRequest) PARSER.parseFrom(data);
        }

        public static DeviceInfoRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceInfoRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceInfoRequest parseFrom(InputStream input) throws IOException {
            return (DeviceInfoRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceInfoRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfoRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceInfoRequest parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceInfoRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeviceInfoRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfoRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceInfoRequest parseFrom(CodedInputStream input) throws IOException {
            return (DeviceInfoRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceInfoRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfoRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeviceInfoRequest prototype) {
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

        public static DeviceInfoRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceInfoRequest> parser() {
            return PARSER;
        }

        public Parser<DeviceInfoRequest> getParserForType() {
            return PARSER;
        }

        public DeviceInfoRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class DeviceInfoResponse extends GeneratedMessageV3 implements DeviceInfoResponseOrBuilder {
        private static final DeviceInfoResponse DEFAULT_INSTANCE = new DeviceInfoResponse();
        public static final int FOTA_FIELD_NUMBER = 4;
        public static final int MAC_FIELD_NUMBER = 3;
        public static final int MANU_FIELD_NUMBER = 5;
        public static final int MODEL_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<DeviceInfoResponse> PARSER = new AbstractParser<DeviceInfoResponse>() {
            public DeviceInfoResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DeviceInfoResponse(input, extensionRegistry);
            }
        };
        public static final int VERSION_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int fota_;
        /* access modifiers changed from: private */
        public ByteString mac_;
        /* access modifiers changed from: private */
        public DevInfoManu manu_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public volatile Object model_;
        /* access modifiers changed from: private */
        public volatile Object version_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements DeviceInfoResponseOrBuilder {
            private int bitField0_;
            private int fota_;
            private ByteString mac_;
            private SingleFieldBuilderV3<DevInfoManu, Builder, DevInfoManuOrBuilder> manuBuilder_;
            private DevInfoManu manu_;
            private Object model_;
            private Object version_;

            public static final Descriptor getDescriptor() {
                return DevInfo.internal_static_DeviceInfoResponse_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DevInfo.internal_static_DeviceInfoResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceInfoResponse.class, Builder.class);
            }

            private Builder() {
                this.model_ = "";
                this.version_ = "";
                this.mac_ = ByteString.EMPTY;
                this.fota_ = 0;
                this.manu_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.model_ = "";
                this.version_ = "";
                this.mac_ = ByteString.EMPTY;
                this.fota_ = 0;
                this.manu_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (DeviceInfoResponse.alwaysUseFieldBuilders) {
                    getManuFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.model_ = "";
                this.bitField0_ &= -2;
                this.version_ = "";
                this.bitField0_ &= -3;
                this.mac_ = ByteString.EMPTY;
                this.bitField0_ &= -5;
                this.fota_ = 0;
                this.bitField0_ &= -9;
                if (this.manuBuilder_ == null) {
                    this.manu_ = null;
                } else {
                    this.manuBuilder_.clear();
                }
                this.bitField0_ &= -17;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DevInfo.internal_static_DeviceInfoResponse_descriptor;
            }

            public DeviceInfoResponse getDefaultInstanceForType() {
                return DeviceInfoResponse.getDefaultInstance();
            }

            public DeviceInfoResponse build() {
                DeviceInfoResponse result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public DeviceInfoResponse buildPartial() {
                DeviceInfoResponse result = new DeviceInfoResponse((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.model_ = this.model_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.version_ = this.version_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.mac_ = this.mac_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.fota_ = this.fota_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                if (this.manuBuilder_ == null) {
                    result.manu_ = this.manu_;
                } else {
                    result.manu_ = (DevInfoManu) this.manuBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
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
                if (other instanceof DeviceInfoResponse) {
                    return mergeFrom((DeviceInfoResponse) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeviceInfoResponse other) {
                if (other != DeviceInfoResponse.getDefaultInstance()) {
                    if (other.hasModel()) {
                        this.bitField0_ |= 1;
                        this.model_ = other.model_;
                        onChanged();
                    }
                    if (other.hasVersion()) {
                        this.bitField0_ |= 2;
                        this.version_ = other.version_;
                        onChanged();
                    }
                    if (other.hasMac()) {
                        setMac(other.getMac());
                    }
                    if (other.hasFota()) {
                        setFota(other.getFota());
                    }
                    if (other.hasManu()) {
                        mergeManu(other.getManu());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasManu() || getManu().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                DeviceInfoResponse parsedMessage = null;
                try {
                    DeviceInfoResponse parsedMessage2 = (DeviceInfoResponse) DeviceInfoResponse.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    DeviceInfoResponse parsedMessage3 = (DeviceInfoResponse) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasModel() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getModel() {
                Object ref = this.model_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.model_ = s;
                return s;
            }

            public ByteString getModelBytes() {
                Object ref = this.model_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.model_ = b;
                return b;
            }

            public Builder setModel(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.model_ = value;
                onChanged();
                return this;
            }

            public Builder clearModel() {
                this.bitField0_ &= -2;
                this.model_ = DeviceInfoResponse.getDefaultInstance().getModel();
                onChanged();
                return this;
            }

            public Builder setModelBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.model_ = value;
                onChanged();
                return this;
            }

            public boolean hasVersion() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getVersion() {
                Object ref = this.version_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
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
                this.bitField0_ |= 2;
                this.version_ = value;
                onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.bitField0_ &= -3;
                this.version_ = DeviceInfoResponse.getDefaultInstance().getVersion();
                onChanged();
                return this;
            }

            public Builder setVersionBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.version_ = value;
                onChanged();
                return this;
            }

            public boolean hasMac() {
                return (this.bitField0_ & 4) == 4;
            }

            public ByteString getMac() {
                return this.mac_;
            }

            public Builder setMac(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.mac_ = value;
                onChanged();
                return this;
            }

            public Builder clearMac() {
                this.bitField0_ &= -5;
                this.mac_ = DeviceInfoResponse.getDefaultInstance().getMac();
                onChanged();
                return this;
            }

            public boolean hasFota() {
                return (this.bitField0_ & 8) == 8;
            }

            public DevInfoFota getFota() {
                DevInfoFota result = DevInfoFota.valueOf(this.fota_);
                return result == null ? DevInfoFota.NON : result;
            }

            public Builder setFota(DevInfoFota value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.fota_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearFota() {
                this.bitField0_ &= -9;
                this.fota_ = 0;
                onChanged();
                return this;
            }

            public boolean hasManu() {
                return (this.bitField0_ & 16) == 16;
            }

            public DevInfoManu getManu() {
                if (this.manuBuilder_ == null) {
                    return this.manu_ == null ? DevInfoManu.getDefaultInstance() : this.manu_;
                }
                return (DevInfoManu) this.manuBuilder_.getMessage();
            }

            public Builder setManu(DevInfoManu value) {
                if (this.manuBuilder_ != null) {
                    this.manuBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.manu_ = value;
                    onChanged();
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder setManu(Builder builderForValue) {
                if (this.manuBuilder_ == null) {
                    this.manu_ = builderForValue.build();
                    onChanged();
                } else {
                    this.manuBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder mergeManu(DevInfoManu value) {
                if (this.manuBuilder_ == null) {
                    if ((this.bitField0_ & 16) != 16 || this.manu_ == null || this.manu_ == DevInfoManu.getDefaultInstance()) {
                        this.manu_ = value;
                    } else {
                        this.manu_ = DevInfoManu.newBuilder(this.manu_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.manuBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder clearManu() {
                if (this.manuBuilder_ == null) {
                    this.manu_ = null;
                    onChanged();
                } else {
                    this.manuBuilder_.clear();
                }
                this.bitField0_ &= -17;
                return this;
            }

            public Builder getManuBuilder() {
                this.bitField0_ |= 16;
                onChanged();
                return (Builder) getManuFieldBuilder().getBuilder();
            }

            public DevInfoManuOrBuilder getManuOrBuilder() {
                if (this.manuBuilder_ != null) {
                    return (DevInfoManuOrBuilder) this.manuBuilder_.getMessageOrBuilder();
                }
                return this.manu_ == null ? DevInfoManu.getDefaultInstance() : this.manu_;
            }

            private SingleFieldBuilderV3<DevInfoManu, Builder, DevInfoManuOrBuilder> getManuFieldBuilder() {
                if (this.manuBuilder_ == null) {
                    this.manuBuilder_ = new SingleFieldBuilderV3<>(getManu(), getParentForChildren(), isClean());
                    this.manu_ = null;
                }
                return this.manuBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private DeviceInfoResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private DeviceInfoResponse() {
            this.memoizedIsInitialized = -1;
            this.model_ = "";
            this.version_ = "";
            this.mac_ = ByteString.EMPTY;
            this.fota_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DeviceInfoResponse(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
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
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.model_ = bs;
                            break;
                        case 18:
                            ByteString bs2 = input.readBytes();
                            this.bitField0_ |= 2;
                            this.version_ = bs2;
                            break;
                        case 26:
                            this.bitField0_ |= 4;
                            this.mac_ = input.readBytes();
                            break;
                        case 32:
                            int rawValue = input.readEnum();
                            if (DevInfoFota.valueOf(rawValue) != null) {
                                this.bitField0_ |= 8;
                                this.fota_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(4, rawValue);
                                break;
                            }
                        case 42:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 16) == 16) {
                                subBuilder = this.manu_.toBuilder();
                            }
                            this.manu_ = (DevInfoManu) input.readMessage(DevInfoManu.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.manu_);
                                this.manu_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 16;
                            break;
                        default:
                            if (parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
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
            return DevInfo.internal_static_DeviceInfoResponse_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DevInfo.internal_static_DeviceInfoResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceInfoResponse.class, Builder.class);
        }

        public boolean hasModel() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getModel() {
            Object ref = this.model_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.model_ = s;
            }
            return s;
        }

        public ByteString getModelBytes() {
            Object ref = this.model_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.model_ = b;
            return b;
        }

        public boolean hasVersion() {
            return (this.bitField0_ & 2) == 2;
        }

        public String getVersion() {
            Object ref = this.version_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.version_ = s;
            }
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

        public boolean hasMac() {
            return (this.bitField0_ & 4) == 4;
        }

        public ByteString getMac() {
            return this.mac_;
        }

        public boolean hasFota() {
            return (this.bitField0_ & 8) == 8;
        }

        public DevInfoFota getFota() {
            DevInfoFota result = DevInfoFota.valueOf(this.fota_);
            return result == null ? DevInfoFota.NON : result;
        }

        public boolean hasManu() {
            return (this.bitField0_ & 16) == 16;
        }

        public DevInfoManu getManu() {
            return this.manu_ == null ? DevInfoManu.getDefaultInstance() : this.manu_;
        }

        public DevInfoManuOrBuilder getManuOrBuilder() {
            return this.manu_ == null ? DevInfoManu.getDefaultInstance() : this.manu_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasManu() || getManu().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            }
            this.memoizedIsInitialized = 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                GeneratedMessageV3.writeString(output, 1, this.model_);
            }
            if ((this.bitField0_ & 2) == 2) {
                GeneratedMessageV3.writeString(output, 2, this.version_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBytes(3, this.mac_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeEnum(4, this.fota_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeMessage(5, getManu());
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if ((this.bitField0_ & 1) == 1) {
                size2 = 0 + GeneratedMessageV3.computeStringSize(1, this.model_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += GeneratedMessageV3.computeStringSize(2, this.version_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeBytesSize(3, this.mac_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeEnumSize(4, this.fota_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeMessageSize(5, getManu());
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
            if (!(obj instanceof DeviceInfoResponse)) {
                return super.equals(obj);
            }
            DeviceInfoResponse other = (DeviceInfoResponse) obj;
            boolean result6 = 1 != 0 && hasModel() == other.hasModel();
            if (hasModel()) {
                result6 = result6 && getModel().equals(other.getModel());
            }
            if (!result6 || hasVersion() != other.hasVersion()) {
                result = false;
            } else {
                result = true;
            }
            if (hasVersion()) {
                if (!result || !getVersion().equals(other.getVersion())) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasMac() != other.hasMac()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasMac()) {
                if (!result2 || !getMac().equals(other.getMac())) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasFota() != other.hasFota()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasFota()) {
                if (!result3 || this.fota_ != other.fota_) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasManu() != other.hasManu()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasManu()) {
                if (!result4 || !getManu().equals(other.getManu())) {
                    result4 = false;
                } else {
                    result4 = true;
                }
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
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasModel()) {
                hash = (((hash * 37) + 1) * 53) + getModel().hashCode();
            }
            if (hasVersion()) {
                hash = (((hash * 37) + 2) * 53) + getVersion().hashCode();
            }
            if (hasMac()) {
                hash = (((hash * 37) + 3) * 53) + getMac().hashCode();
            }
            if (hasFota()) {
                hash = (((hash * 37) + 4) * 53) + this.fota_;
            }
            if (hasManu()) {
                hash = (((hash * 37) + 5) * 53) + getManu().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static DeviceInfoResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceInfoResponse) PARSER.parseFrom(data);
        }

        public static DeviceInfoResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceInfoResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceInfoResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceInfoResponse) PARSER.parseFrom(data);
        }

        public static DeviceInfoResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceInfoResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceInfoResponse parseFrom(InputStream input) throws IOException {
            return (DeviceInfoResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceInfoResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfoResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceInfoResponse parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceInfoResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeviceInfoResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfoResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceInfoResponse parseFrom(CodedInputStream input) throws IOException {
            return (DeviceInfoResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceInfoResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceInfoResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeviceInfoResponse prototype) {
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

        public static DeviceInfoResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceInfoResponse> parser() {
            return PARSER;
        }

        public Parser<DeviceInfoResponse> getParserForType() {
            return PARSER;
        }

        public DeviceInfoResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface DevInfoManuOrBuilder extends MessageOrBuilder {
        String getDate();

        ByteString getDateBytes();

        String getFactory();

        ByteString getFactoryBytes();

        boolean hasDate();

        boolean hasFactory();
    }

    public interface DeviceInfoRequestOrBuilder extends MessageOrBuilder {
        int getReserved();

        boolean hasReserved();
    }

    public interface DeviceInfoResponseOrBuilder extends MessageOrBuilder {
        DevInfoFota getFota();

        ByteString getMac();

        DevInfoManu getManu();

        DevInfoManuOrBuilder getManuOrBuilder();

        String getModel();

        ByteString getModelBytes();

        String getVersion();

        ByteString getVersionBytes();

        boolean hasFota();

        boolean hasMac();

        boolean hasManu();

        boolean hasModel();

        boolean hasVersion();
    }

    private DevInfo() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite) registry);
    }

    public static FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u000edev_info.proto\",\n\u000bDevInfoManu\u0012\f\n\u0004date\u0018\u0001 \u0002(\t\u0012\u000f\n\u0007factory\u0018\u0002 \u0002(\t\"y\n\u0012DeviceInfoResponse\u0012\r\n\u0005model\u0018\u0001 \u0001(\t\u0012\u000f\n\u0007version\u0018\u0002 \u0001(\t\u0012\u000b\n\u0003mac\u0018\u0003 \u0001(\f\u0012\u001a\n\u0004fota\u0018\u0004 \u0001(\u000e2\f.DevInfoFota\u0012\u001a\n\u0004manu\u0018\u0005 \u0001(\u000b2\f.DevInfoManu\"%\n\u0011DeviceInfoRequest\u0012\u0010\n\breserved\u0018\u0001 \u0001(\u0007*g\n\u000bDevInfoFota\u0012\u0007\n\u0003NON\u0010\u0000\u0012\n\n\u0006CC2540\u0010\u0001\u0012\t\n\u0005NRF51\u0010\u0002\u0012\u000b\n\u0007DA1468X\u0010\u0003\u0012\n\n\u0006MT2523\u0010\u0004\u0012\r\n\tNRF52_BLE\u0010\u0005\u0012\u0010\n\fNRF52_SERIAL\u0010\u0006"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                DevInfo.descriptor = root;
                return null;
            }
        });
    }
}
