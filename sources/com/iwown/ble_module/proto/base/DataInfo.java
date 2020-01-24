package com.iwown.ble_module.proto.base;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;

public final class DataInfo {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_DataInfoRequest_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_DataInfoRequest_fieldAccessorTable = new FieldAccessorTable(internal_static_DataInfoRequest_descriptor, new String[]{"Reserved"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_DataInfoResponse_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_DataInfoResponse_fieldAccessorTable = new FieldAccessorTable(internal_static_DataInfoResponse_descriptor, new String[]{"RtDataInfo", "HisDataInfo"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisDataInfo_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisDataInfo_fieldAccessorTable = new FieldAccessorTable(internal_static_HisDataInfo_descriptor, new String[]{"SupportHealth", "SupportGnss", "SupportEcg", "SupportPpg", "SupportRri"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_RtDataInfo_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_RtDataInfo_fieldAccessorTable = new FieldAccessorTable(internal_static_RtDataInfo_descriptor, new String[]{"SupportTime", "SupportBattery", "SupportHealth"});

    public static final class DataInfoRequest extends GeneratedMessageV3 implements DataInfoRequestOrBuilder {
        private static final DataInfoRequest DEFAULT_INSTANCE = new DataInfoRequest();
        @Deprecated
        public static final Parser<DataInfoRequest> PARSER = new AbstractParser<DataInfoRequest>() {
            public DataInfoRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DataInfoRequest(input, extensionRegistry);
            }
        };
        public static final int RESERVED_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int reserved_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements DataInfoRequestOrBuilder {
            private int bitField0_;
            private int reserved_;

            public static final Descriptor getDescriptor() {
                return DataInfo.internal_static_DataInfoRequest_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DataInfo.internal_static_DataInfoRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(DataInfoRequest.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (DataInfoRequest.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.reserved_ = 0;
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DataInfo.internal_static_DataInfoRequest_descriptor;
            }

            public DataInfoRequest getDefaultInstanceForType() {
                return DataInfoRequest.getDefaultInstance();
            }

            public DataInfoRequest build() {
                DataInfoRequest result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public DataInfoRequest buildPartial() {
                DataInfoRequest result = new DataInfoRequest((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
                if (other instanceof DataInfoRequest) {
                    return mergeFrom((DataInfoRequest) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DataInfoRequest other) {
                if (other != DataInfoRequest.getDefaultInstance()) {
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
                DataInfoRequest parsedMessage = null;
                try {
                    DataInfoRequest parsedMessage2 = (DataInfoRequest) DataInfoRequest.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    DataInfoRequest parsedMessage3 = (DataInfoRequest) e.getUnfinishedMessage();
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

        private DataInfoRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private DataInfoRequest() {
            this.memoizedIsInitialized = -1;
            this.reserved_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DataInfoRequest(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return DataInfo.internal_static_DataInfoRequest_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DataInfo.internal_static_DataInfoRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(DataInfoRequest.class, Builder.class);
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
            if (!(obj instanceof DataInfoRequest)) {
                return super.equals(obj);
            }
            DataInfoRequest other = (DataInfoRequest) obj;
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

        public static DataInfoRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DataInfoRequest) PARSER.parseFrom(data);
        }

        public static DataInfoRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DataInfoRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DataInfoRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DataInfoRequest) PARSER.parseFrom(data);
        }

        public static DataInfoRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DataInfoRequest) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DataInfoRequest parseFrom(InputStream input) throws IOException {
            return (DataInfoRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DataInfoRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DataInfoRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DataInfoRequest parseDelimitedFrom(InputStream input) throws IOException {
            return (DataInfoRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DataInfoRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DataInfoRequest) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DataInfoRequest parseFrom(CodedInputStream input) throws IOException {
            return (DataInfoRequest) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DataInfoRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DataInfoRequest) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DataInfoRequest prototype) {
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

        public static DataInfoRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DataInfoRequest> parser() {
            return PARSER;
        }

        public Parser<DataInfoRequest> getParserForType() {
            return PARSER;
        }

        public DataInfoRequest getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class DataInfoResponse extends GeneratedMessageV3 implements DataInfoResponseOrBuilder {
        private static final DataInfoResponse DEFAULT_INSTANCE = new DataInfoResponse();
        public static final int HIS_DATA_INFO_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<DataInfoResponse> PARSER = new AbstractParser<DataInfoResponse>() {
            public DataInfoResponse parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DataInfoResponse(input, extensionRegistry);
            }
        };
        public static final int RT_DATA_INFO_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public HisDataInfo hisDataInfo_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public RtDataInfo rtDataInfo_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements DataInfoResponseOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<HisDataInfo, Builder, HisDataInfoOrBuilder> hisDataInfoBuilder_;
            private HisDataInfo hisDataInfo_;
            private SingleFieldBuilderV3<RtDataInfo, Builder, RtDataInfoOrBuilder> rtDataInfoBuilder_;
            private RtDataInfo rtDataInfo_;

            public static final Descriptor getDescriptor() {
                return DataInfo.internal_static_DataInfoResponse_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DataInfo.internal_static_DataInfoResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(DataInfoResponse.class, Builder.class);
            }

            private Builder() {
                this.rtDataInfo_ = null;
                this.hisDataInfo_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.rtDataInfo_ = null;
                this.hisDataInfo_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (DataInfoResponse.alwaysUseFieldBuilders) {
                    getRtDataInfoFieldBuilder();
                    getHisDataInfoFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.rtDataInfoBuilder_ == null) {
                    this.rtDataInfo_ = null;
                } else {
                    this.rtDataInfoBuilder_.clear();
                }
                this.bitField0_ &= -2;
                if (this.hisDataInfoBuilder_ == null) {
                    this.hisDataInfo_ = null;
                } else {
                    this.hisDataInfoBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DataInfo.internal_static_DataInfoResponse_descriptor;
            }

            public DataInfoResponse getDefaultInstanceForType() {
                return DataInfoResponse.getDefaultInstance();
            }

            public DataInfoResponse build() {
                DataInfoResponse result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public DataInfoResponse buildPartial() {
                DataInfoResponse result = new DataInfoResponse((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.rtDataInfoBuilder_ == null) {
                    result.rtDataInfo_ = this.rtDataInfo_;
                } else {
                    result.rtDataInfo_ = (RtDataInfo) this.rtDataInfoBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.hisDataInfoBuilder_ == null) {
                    result.hisDataInfo_ = this.hisDataInfo_;
                } else {
                    result.hisDataInfo_ = (HisDataInfo) this.hisDataInfoBuilder_.build();
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
                if (other instanceof DataInfoResponse) {
                    return mergeFrom((DataInfoResponse) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DataInfoResponse other) {
                if (other != DataInfoResponse.getDefaultInstance()) {
                    if (other.hasRtDataInfo()) {
                        mergeRtDataInfo(other.getRtDataInfo());
                    }
                    if (other.hasHisDataInfo()) {
                        mergeHisDataInfo(other.getHisDataInfo());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasRtDataInfo() && !getRtDataInfo().isInitialized()) {
                    return false;
                }
                if (!hasHisDataInfo() || getHisDataInfo().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                DataInfoResponse parsedMessage = null;
                try {
                    DataInfoResponse parsedMessage2 = (DataInfoResponse) DataInfoResponse.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    DataInfoResponse parsedMessage3 = (DataInfoResponse) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasRtDataInfo() {
                return (this.bitField0_ & 1) == 1;
            }

            public RtDataInfo getRtDataInfo() {
                if (this.rtDataInfoBuilder_ == null) {
                    return this.rtDataInfo_ == null ? RtDataInfo.getDefaultInstance() : this.rtDataInfo_;
                }
                return (RtDataInfo) this.rtDataInfoBuilder_.getMessage();
            }

            public Builder setRtDataInfo(RtDataInfo value) {
                if (this.rtDataInfoBuilder_ != null) {
                    this.rtDataInfoBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.rtDataInfo_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setRtDataInfo(Builder builderForValue) {
                if (this.rtDataInfoBuilder_ == null) {
                    this.rtDataInfo_ = builderForValue.build();
                    onChanged();
                } else {
                    this.rtDataInfoBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeRtDataInfo(RtDataInfo value) {
                if (this.rtDataInfoBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.rtDataInfo_ == null || this.rtDataInfo_ == RtDataInfo.getDefaultInstance()) {
                        this.rtDataInfo_ = value;
                    } else {
                        this.rtDataInfo_ = RtDataInfo.newBuilder(this.rtDataInfo_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.rtDataInfoBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearRtDataInfo() {
                if (this.rtDataInfoBuilder_ == null) {
                    this.rtDataInfo_ = null;
                    onChanged();
                } else {
                    this.rtDataInfoBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public Builder getRtDataInfoBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (Builder) getRtDataInfoFieldBuilder().getBuilder();
            }

            public RtDataInfoOrBuilder getRtDataInfoOrBuilder() {
                if (this.rtDataInfoBuilder_ != null) {
                    return (RtDataInfoOrBuilder) this.rtDataInfoBuilder_.getMessageOrBuilder();
                }
                return this.rtDataInfo_ == null ? RtDataInfo.getDefaultInstance() : this.rtDataInfo_;
            }

            private SingleFieldBuilderV3<RtDataInfo, Builder, RtDataInfoOrBuilder> getRtDataInfoFieldBuilder() {
                if (this.rtDataInfoBuilder_ == null) {
                    this.rtDataInfoBuilder_ = new SingleFieldBuilderV3<>(getRtDataInfo(), getParentForChildren(), isClean());
                    this.rtDataInfo_ = null;
                }
                return this.rtDataInfoBuilder_;
            }

            public boolean hasHisDataInfo() {
                return (this.bitField0_ & 2) == 2;
            }

            public HisDataInfo getHisDataInfo() {
                if (this.hisDataInfoBuilder_ == null) {
                    return this.hisDataInfo_ == null ? HisDataInfo.getDefaultInstance() : this.hisDataInfo_;
                }
                return (HisDataInfo) this.hisDataInfoBuilder_.getMessage();
            }

            public Builder setHisDataInfo(HisDataInfo value) {
                if (this.hisDataInfoBuilder_ != null) {
                    this.hisDataInfoBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.hisDataInfo_ = value;
                    onChanged();
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setHisDataInfo(Builder builderForValue) {
                if (this.hisDataInfoBuilder_ == null) {
                    this.hisDataInfo_ = builderForValue.build();
                    onChanged();
                } else {
                    this.hisDataInfoBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeHisDataInfo(HisDataInfo value) {
                if (this.hisDataInfoBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.hisDataInfo_ == null || this.hisDataInfo_ == HisDataInfo.getDefaultInstance()) {
                        this.hisDataInfo_ = value;
                    } else {
                        this.hisDataInfo_ = HisDataInfo.newBuilder(this.hisDataInfo_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.hisDataInfoBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearHisDataInfo() {
                if (this.hisDataInfoBuilder_ == null) {
                    this.hisDataInfo_ = null;
                    onChanged();
                } else {
                    this.hisDataInfoBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public Builder getHisDataInfoBuilder() {
                this.bitField0_ |= 2;
                onChanged();
                return (Builder) getHisDataInfoFieldBuilder().getBuilder();
            }

            public HisDataInfoOrBuilder getHisDataInfoOrBuilder() {
                if (this.hisDataInfoBuilder_ != null) {
                    return (HisDataInfoOrBuilder) this.hisDataInfoBuilder_.getMessageOrBuilder();
                }
                return this.hisDataInfo_ == null ? HisDataInfo.getDefaultInstance() : this.hisDataInfo_;
            }

            private SingleFieldBuilderV3<HisDataInfo, Builder, HisDataInfoOrBuilder> getHisDataInfoFieldBuilder() {
                if (this.hisDataInfoBuilder_ == null) {
                    this.hisDataInfoBuilder_ = new SingleFieldBuilderV3<>(getHisDataInfo(), getParentForChildren(), isClean());
                    this.hisDataInfo_ = null;
                }
                return this.hisDataInfoBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private DataInfoResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private DataInfoResponse() {
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DataInfoResponse(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 1) == 1) {
                                subBuilder = this.rtDataInfo_.toBuilder();
                            }
                            this.rtDataInfo_ = (RtDataInfo) input.readMessage(RtDataInfo.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.rtDataInfo_);
                                this.rtDataInfo_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            break;
                        case 18:
                            Builder subBuilder2 = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder2 = this.hisDataInfo_.toBuilder();
                            }
                            this.hisDataInfo_ = (HisDataInfo) input.readMessage(HisDataInfo.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.hisDataInfo_);
                                this.hisDataInfo_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 2;
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
            return DataInfo.internal_static_DataInfoResponse_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DataInfo.internal_static_DataInfoResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(DataInfoResponse.class, Builder.class);
        }

        public boolean hasRtDataInfo() {
            return (this.bitField0_ & 1) == 1;
        }

        public RtDataInfo getRtDataInfo() {
            return this.rtDataInfo_ == null ? RtDataInfo.getDefaultInstance() : this.rtDataInfo_;
        }

        public RtDataInfoOrBuilder getRtDataInfoOrBuilder() {
            return this.rtDataInfo_ == null ? RtDataInfo.getDefaultInstance() : this.rtDataInfo_;
        }

        public boolean hasHisDataInfo() {
            return (this.bitField0_ & 2) == 2;
        }

        public HisDataInfo getHisDataInfo() {
            return this.hisDataInfo_ == null ? HisDataInfo.getDefaultInstance() : this.hisDataInfo_;
        }

        public HisDataInfoOrBuilder getHisDataInfoOrBuilder() {
            return this.hisDataInfo_ == null ? HisDataInfo.getDefaultInstance() : this.hisDataInfo_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (hasRtDataInfo() && !getRtDataInfo().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasHisDataInfo() || getHisDataInfo().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getRtDataInfo());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, getHisDataInfo());
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, getRtDataInfo());
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, getHisDataInfo());
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
            if (!(obj instanceof DataInfoResponse)) {
                return super.equals(obj);
            }
            DataInfoResponse other = (DataInfoResponse) obj;
            boolean result3 = 1 != 0 && hasRtDataInfo() == other.hasRtDataInfo();
            if (hasRtDataInfo()) {
                result3 = result3 && getRtDataInfo().equals(other.getRtDataInfo());
            }
            if (!result3 || hasHisDataInfo() != other.hasHisDataInfo()) {
                result = false;
            } else {
                result = true;
            }
            if (hasHisDataInfo()) {
                if (!result || !getHisDataInfo().equals(other.getHisDataInfo())) {
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
            if (hasRtDataInfo()) {
                hash = (((hash * 37) + 1) * 53) + getRtDataInfo().hashCode();
            }
            if (hasHisDataInfo()) {
                hash = (((hash * 37) + 2) * 53) + getHisDataInfo().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static DataInfoResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DataInfoResponse) PARSER.parseFrom(data);
        }

        public static DataInfoResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DataInfoResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DataInfoResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DataInfoResponse) PARSER.parseFrom(data);
        }

        public static DataInfoResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DataInfoResponse) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DataInfoResponse parseFrom(InputStream input) throws IOException {
            return (DataInfoResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DataInfoResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DataInfoResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DataInfoResponse parseDelimitedFrom(InputStream input) throws IOException {
            return (DataInfoResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DataInfoResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DataInfoResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DataInfoResponse parseFrom(CodedInputStream input) throws IOException {
            return (DataInfoResponse) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DataInfoResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DataInfoResponse) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DataInfoResponse prototype) {
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

        public static DataInfoResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DataInfoResponse> parser() {
            return PARSER;
        }

        public Parser<DataInfoResponse> getParserForType() {
            return PARSER;
        }

        public DataInfoResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisDataInfo extends GeneratedMessageV3 implements HisDataInfoOrBuilder {
        private static final HisDataInfo DEFAULT_INSTANCE = new HisDataInfo();
        @Deprecated
        public static final Parser<HisDataInfo> PARSER = new AbstractParser<HisDataInfo>() {
            public HisDataInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisDataInfo(input, extensionRegistry);
            }
        };
        public static final int SUPPORT_ECG_FIELD_NUMBER = 3;
        public static final int SUPPORT_GNSS_FIELD_NUMBER = 2;
        public static final int SUPPORT_HEALTH_FIELD_NUMBER = 1;
        public static final int SUPPORT_PPG_FIELD_NUMBER = 4;
        public static final int SUPPORT_RRI_FIELD_NUMBER = 5;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public boolean supportEcg_;
        /* access modifiers changed from: private */
        public boolean supportGnss_;
        /* access modifiers changed from: private */
        public boolean supportHealth_;
        /* access modifiers changed from: private */
        public boolean supportPpg_;
        /* access modifiers changed from: private */
        public boolean supportRri_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisDataInfoOrBuilder {
            private int bitField0_;
            private boolean supportEcg_;
            private boolean supportGnss_;
            private boolean supportHealth_;
            private boolean supportPpg_;
            private boolean supportRri_;

            public static final Descriptor getDescriptor() {
                return DataInfo.internal_static_HisDataInfo_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DataInfo.internal_static_HisDataInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(HisDataInfo.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisDataInfo.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.supportHealth_ = false;
                this.bitField0_ &= -2;
                this.supportGnss_ = false;
                this.bitField0_ &= -3;
                this.supportEcg_ = false;
                this.bitField0_ &= -5;
                this.supportPpg_ = false;
                this.bitField0_ &= -9;
                this.supportRri_ = false;
                this.bitField0_ &= -17;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DataInfo.internal_static_HisDataInfo_descriptor;
            }

            public HisDataInfo getDefaultInstanceForType() {
                return HisDataInfo.getDefaultInstance();
            }

            public HisDataInfo build() {
                HisDataInfo result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisDataInfo buildPartial() {
                HisDataInfo result = new HisDataInfo((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.supportHealth_ = this.supportHealth_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.supportGnss_ = this.supportGnss_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.supportEcg_ = this.supportEcg_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.supportPpg_ = this.supportPpg_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.supportRri_ = this.supportRri_;
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
                if (other instanceof HisDataInfo) {
                    return mergeFrom((HisDataInfo) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisDataInfo other) {
                if (other != HisDataInfo.getDefaultInstance()) {
                    if (other.hasSupportHealth()) {
                        setSupportHealth(other.getSupportHealth());
                    }
                    if (other.hasSupportGnss()) {
                        setSupportGnss(other.getSupportGnss());
                    }
                    if (other.hasSupportEcg()) {
                        setSupportEcg(other.getSupportEcg());
                    }
                    if (other.hasSupportPpg()) {
                        setSupportPpg(other.getSupportPpg());
                    }
                    if (other.hasSupportRri()) {
                        setSupportRri(other.getSupportRri());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasSupportHealth() && hasSupportGnss() && hasSupportEcg()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisDataInfo parsedMessage = null;
                try {
                    HisDataInfo parsedMessage2 = (HisDataInfo) HisDataInfo.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisDataInfo parsedMessage3 = (HisDataInfo) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSupportHealth() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getSupportHealth() {
                return this.supportHealth_;
            }

            public Builder setSupportHealth(boolean value) {
                this.bitField0_ |= 1;
                this.supportHealth_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportHealth() {
                this.bitField0_ &= -2;
                this.supportHealth_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportGnss() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getSupportGnss() {
                return this.supportGnss_;
            }

            public Builder setSupportGnss(boolean value) {
                this.bitField0_ |= 2;
                this.supportGnss_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportGnss() {
                this.bitField0_ &= -3;
                this.supportGnss_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportEcg() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getSupportEcg() {
                return this.supportEcg_;
            }

            public Builder setSupportEcg(boolean value) {
                this.bitField0_ |= 4;
                this.supportEcg_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportEcg() {
                this.bitField0_ &= -5;
                this.supportEcg_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportPpg() {
                return (this.bitField0_ & 8) == 8;
            }

            public boolean getSupportPpg() {
                return this.supportPpg_;
            }

            public Builder setSupportPpg(boolean value) {
                this.bitField0_ |= 8;
                this.supportPpg_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportPpg() {
                this.bitField0_ &= -9;
                this.supportPpg_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportRri() {
                return (this.bitField0_ & 16) == 16;
            }

            public boolean getSupportRri() {
                return this.supportRri_;
            }

            public Builder setSupportRri(boolean value) {
                this.bitField0_ |= 16;
                this.supportRri_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportRri() {
                this.bitField0_ &= -17;
                this.supportRri_ = false;
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

        private HisDataInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisDataInfo() {
            this.memoizedIsInitialized = -1;
            this.supportHealth_ = false;
            this.supportGnss_ = false;
            this.supportEcg_ = false;
            this.supportPpg_ = false;
            this.supportRri_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisDataInfo(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 8:
                            this.bitField0_ |= 1;
                            this.supportHealth_ = input.readBool();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.supportGnss_ = input.readBool();
                            break;
                        case 24:
                            this.bitField0_ |= 4;
                            this.supportEcg_ = input.readBool();
                            break;
                        case 32:
                            this.bitField0_ |= 8;
                            this.supportPpg_ = input.readBool();
                            break;
                        case 40:
                            this.bitField0_ |= 16;
                            this.supportRri_ = input.readBool();
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
            return DataInfo.internal_static_HisDataInfo_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DataInfo.internal_static_HisDataInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(HisDataInfo.class, Builder.class);
        }

        public boolean hasSupportHealth() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getSupportHealth() {
            return this.supportHealth_;
        }

        public boolean hasSupportGnss() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getSupportGnss() {
            return this.supportGnss_;
        }

        public boolean hasSupportEcg() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getSupportEcg() {
            return this.supportEcg_;
        }

        public boolean hasSupportPpg() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean getSupportPpg() {
            return this.supportPpg_;
        }

        public boolean hasSupportRri() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean getSupportRri() {
            return this.supportRri_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSupportHealth()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportGnss()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportEcg()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(1, this.supportHealth_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.supportGnss_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(3, this.supportEcg_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBool(4, this.supportPpg_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBool(5, this.supportRri_);
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
                size2 = 0 + CodedOutputStream.computeBoolSize(1, this.supportHealth_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.supportGnss_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeBoolSize(3, this.supportEcg_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeBoolSize(4, this.supportPpg_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeBoolSize(5, this.supportRri_);
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
            if (!(obj instanceof HisDataInfo)) {
                return super.equals(obj);
            }
            HisDataInfo other = (HisDataInfo) obj;
            boolean result6 = 1 != 0 && hasSupportHealth() == other.hasSupportHealth();
            if (hasSupportHealth()) {
                result6 = result6 && getSupportHealth() == other.getSupportHealth();
            }
            if (!result6 || hasSupportGnss() != other.hasSupportGnss()) {
                result = false;
            } else {
                result = true;
            }
            if (hasSupportGnss()) {
                if (!result || getSupportGnss() != other.getSupportGnss()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasSupportEcg() != other.hasSupportEcg()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasSupportEcg()) {
                if (!result2 || getSupportEcg() != other.getSupportEcg()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasSupportPpg() != other.hasSupportPpg()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasSupportPpg()) {
                if (!result3 || getSupportPpg() != other.getSupportPpg()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasSupportRri() != other.hasSupportRri()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasSupportRri()) {
                if (!result4 || getSupportRri() != other.getSupportRri()) {
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
            if (hasSupportHealth()) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashBoolean(getSupportHealth());
            }
            if (hasSupportGnss()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getSupportGnss());
            }
            if (hasSupportEcg()) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashBoolean(getSupportEcg());
            }
            if (hasSupportPpg()) {
                hash = (((hash * 37) + 4) * 53) + Internal.hashBoolean(getSupportPpg());
            }
            if (hasSupportRri()) {
                hash = (((hash * 37) + 5) * 53) + Internal.hashBoolean(getSupportRri());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisDataInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisDataInfo) PARSER.parseFrom(data);
        }

        public static HisDataInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisDataInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisDataInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisDataInfo) PARSER.parseFrom(data);
        }

        public static HisDataInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisDataInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisDataInfo parseFrom(InputStream input) throws IOException {
            return (HisDataInfo) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisDataInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataInfo) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisDataInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (HisDataInfo) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisDataInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataInfo) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisDataInfo parseFrom(CodedInputStream input) throws IOException {
            return (HisDataInfo) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisDataInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataInfo) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisDataInfo prototype) {
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

        public static HisDataInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisDataInfo> parser() {
            return PARSER;
        }

        public Parser<HisDataInfo> getParserForType() {
            return PARSER;
        }

        public HisDataInfo getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class RtDataInfo extends GeneratedMessageV3 implements RtDataInfoOrBuilder {
        private static final RtDataInfo DEFAULT_INSTANCE = new RtDataInfo();
        @Deprecated
        public static final Parser<RtDataInfo> PARSER = new AbstractParser<RtDataInfo>() {
            public RtDataInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new RtDataInfo(input, extensionRegistry);
            }
        };
        public static final int SUPPORT_BATTERY_FIELD_NUMBER = 2;
        public static final int SUPPORT_HEALTH_FIELD_NUMBER = 3;
        public static final int SUPPORT_TIME_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public boolean supportBattery_;
        /* access modifiers changed from: private */
        public boolean supportHealth_;
        /* access modifiers changed from: private */
        public boolean supportTime_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements RtDataInfoOrBuilder {
            private int bitField0_;
            private boolean supportBattery_;
            private boolean supportHealth_;
            private boolean supportTime_;

            public static final Descriptor getDescriptor() {
                return DataInfo.internal_static_RtDataInfo_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DataInfo.internal_static_RtDataInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(RtDataInfo.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (RtDataInfo.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.supportTime_ = false;
                this.bitField0_ &= -2;
                this.supportBattery_ = false;
                this.bitField0_ &= -3;
                this.supportHealth_ = false;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DataInfo.internal_static_RtDataInfo_descriptor;
            }

            public RtDataInfo getDefaultInstanceForType() {
                return RtDataInfo.getDefaultInstance();
            }

            public RtDataInfo build() {
                RtDataInfo result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public RtDataInfo buildPartial() {
                RtDataInfo result = new RtDataInfo((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.supportTime_ = this.supportTime_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.supportBattery_ = this.supportBattery_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.supportHealth_ = this.supportHealth_;
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
                if (other instanceof RtDataInfo) {
                    return mergeFrom((RtDataInfo) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RtDataInfo other) {
                if (other != RtDataInfo.getDefaultInstance()) {
                    if (other.hasSupportTime()) {
                        setSupportTime(other.getSupportTime());
                    }
                    if (other.hasSupportBattery()) {
                        setSupportBattery(other.getSupportBattery());
                    }
                    if (other.hasSupportHealth()) {
                        setSupportHealth(other.getSupportHealth());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasSupportTime() && hasSupportBattery() && hasSupportHealth()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                RtDataInfo parsedMessage = null;
                try {
                    RtDataInfo parsedMessage2 = (RtDataInfo) RtDataInfo.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    RtDataInfo parsedMessage3 = (RtDataInfo) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSupportTime() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getSupportTime() {
                return this.supportTime_;
            }

            public Builder setSupportTime(boolean value) {
                this.bitField0_ |= 1;
                this.supportTime_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportTime() {
                this.bitField0_ &= -2;
                this.supportTime_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportBattery() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getSupportBattery() {
                return this.supportBattery_;
            }

            public Builder setSupportBattery(boolean value) {
                this.bitField0_ |= 2;
                this.supportBattery_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportBattery() {
                this.bitField0_ &= -3;
                this.supportBattery_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportHealth() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getSupportHealth() {
                return this.supportHealth_;
            }

            public Builder setSupportHealth(boolean value) {
                this.bitField0_ |= 4;
                this.supportHealth_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportHealth() {
                this.bitField0_ &= -5;
                this.supportHealth_ = false;
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

        private RtDataInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private RtDataInfo() {
            this.memoizedIsInitialized = -1;
            this.supportTime_ = false;
            this.supportBattery_ = false;
            this.supportHealth_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RtDataInfo(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 8:
                            this.bitField0_ |= 1;
                            this.supportTime_ = input.readBool();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.supportBattery_ = input.readBool();
                            break;
                        case 24:
                            this.bitField0_ |= 4;
                            this.supportHealth_ = input.readBool();
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
            return DataInfo.internal_static_RtDataInfo_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DataInfo.internal_static_RtDataInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(RtDataInfo.class, Builder.class);
        }

        public boolean hasSupportTime() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getSupportTime() {
            return this.supportTime_;
        }

        public boolean hasSupportBattery() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getSupportBattery() {
            return this.supportBattery_;
        }

        public boolean hasSupportHealth() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getSupportHealth() {
            return this.supportHealth_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSupportTime()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportBattery()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportHealth()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(1, this.supportTime_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.supportBattery_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(3, this.supportHealth_);
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
                size2 = 0 + CodedOutputStream.computeBoolSize(1, this.supportTime_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.supportBattery_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeBoolSize(3, this.supportHealth_);
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
            if (!(obj instanceof RtDataInfo)) {
                return super.equals(obj);
            }
            RtDataInfo other = (RtDataInfo) obj;
            boolean result4 = 1 != 0 && hasSupportTime() == other.hasSupportTime();
            if (hasSupportTime()) {
                result4 = result4 && getSupportTime() == other.getSupportTime();
            }
            if (!result4 || hasSupportBattery() != other.hasSupportBattery()) {
                result = false;
            } else {
                result = true;
            }
            if (hasSupportBattery()) {
                if (!result || getSupportBattery() != other.getSupportBattery()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasSupportHealth() != other.hasSupportHealth()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasSupportHealth()) {
                if (!result2 || getSupportHealth() != other.getSupportHealth()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
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
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasSupportTime()) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashBoolean(getSupportTime());
            }
            if (hasSupportBattery()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getSupportBattery());
            }
            if (hasSupportHealth()) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashBoolean(getSupportHealth());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static RtDataInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RtDataInfo) PARSER.parseFrom(data);
        }

        public static RtDataInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtDataInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtDataInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RtDataInfo) PARSER.parseFrom(data);
        }

        public static RtDataInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtDataInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtDataInfo parseFrom(InputStream input) throws IOException {
            return (RtDataInfo) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtDataInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtDataInfo) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtDataInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (RtDataInfo) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RtDataInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtDataInfo) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtDataInfo parseFrom(CodedInputStream input) throws IOException {
            return (RtDataInfo) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtDataInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtDataInfo) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RtDataInfo prototype) {
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

        public static RtDataInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RtDataInfo> parser() {
            return PARSER;
        }

        public Parser<RtDataInfo> getParserForType() {
            return PARSER;
        }

        public RtDataInfo getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface DataInfoRequestOrBuilder extends MessageOrBuilder {
        int getReserved();

        boolean hasReserved();
    }

    public interface DataInfoResponseOrBuilder extends MessageOrBuilder {
        HisDataInfo getHisDataInfo();

        HisDataInfoOrBuilder getHisDataInfoOrBuilder();

        RtDataInfo getRtDataInfo();

        RtDataInfoOrBuilder getRtDataInfoOrBuilder();

        boolean hasHisDataInfo();

        boolean hasRtDataInfo();
    }

    public interface HisDataInfoOrBuilder extends MessageOrBuilder {
        boolean getSupportEcg();

        boolean getSupportGnss();

        boolean getSupportHealth();

        boolean getSupportPpg();

        boolean getSupportRri();

        boolean hasSupportEcg();

        boolean hasSupportGnss();

        boolean hasSupportHealth();

        boolean hasSupportPpg();

        boolean hasSupportRri();
    }

    public interface RtDataInfoOrBuilder extends MessageOrBuilder {
        boolean getSupportBattery();

        boolean getSupportHealth();

        boolean getSupportTime();

        boolean hasSupportBattery();

        boolean hasSupportHealth();

        boolean hasSupportTime();
    }

    private DataInfo() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u000fdata_info.proto\"S\n\nRtDataInfo\u0012\u0014\n\fsupport_time\u0018\u0001 \u0002(\b\u0012\u0017\n\u000fsupport_battery\u0018\u0002 \u0002(\b\u0012\u0016\n\u000esupport_health\u0018\u0003 \u0002(\b\"z\n\u000bHisDataInfo\u0012\u0016\n\u000esupport_health\u0018\u0001 \u0002(\b\u0012\u0014\n\fsupport_gnss\u0018\u0002 \u0002(\b\u0012\u0013\n\u000bsupport_ecg\u0018\u0003 \u0002(\b\u0012\u0013\n\u000bsupport_ppg\u0018\u0004 \u0001(\b\u0012\u0013\n\u000bsupport_rri\u0018\u0005 \u0001(\b\"Z\n\u0010DataInfoResponse\u0012!\n\frt_data_info\u0018\u0001 \u0001(\u000b2\u000b.RtDataInfo\u0012#\n\rhis_data_info\u0018\u0002 \u0001(\u000b2\f.HisDataInfo\"#\n\u000fDataInfoRequest\u0012\u0010\n\breserved\u0018\u0001 \u0001(\u0007"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                DataInfo.descriptor = root;
                return null;
            }
        });
    }
}
