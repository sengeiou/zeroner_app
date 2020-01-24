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
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import com.iwown.ble_module.proto.base.RealtimeData.DateTime;
import com.iwown.ble_module.proto.base.RealtimeData.DateTimeOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HisPpgData {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static Descriptor internal_static_HisDataPPG_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static FieldAccessorTable internal_static_HisDataPPG_fieldAccessorTable = new FieldAccessorTable(internal_static_HisDataPPG_descriptor, new String[]{"TimeStamp", "RawData"});

    public static final class HisDataPPG extends GeneratedMessageV3 implements HisDataPPGOrBuilder {
        private static final HisDataPPG DEFAULT_INSTANCE = new HisDataPPG();
        @Deprecated
        public static final Parser<HisDataPPG> PARSER = new AbstractParser<HisDataPPG>() {
            public HisDataPPG parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisDataPPG(input, extensionRegistry);
            }
        };
        public static final int RAW_DATA_FIELD_NUMBER = 2;
        public static final int TIME_STAMP_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        private int rawDataMemoizedSerializedSize;
        /* access modifiers changed from: private */
        public List<Integer> rawData_;
        /* access modifiers changed from: private */
        public DateTime timeStamp_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisDataPPGOrBuilder {
            private int bitField0_;
            private List<Integer> rawData_;
            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> timeStampBuilder_;
            private DateTime timeStamp_;

            public static final Descriptor getDescriptor() {
                return HisPpgData.internal_static_HisDataPPG_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisPpgData.internal_static_HisDataPPG_fieldAccessorTable.ensureFieldAccessorsInitialized(HisDataPPG.class, Builder.class);
            }

            private Builder() {
                this.timeStamp_ = null;
                this.rawData_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.timeStamp_ = null;
                this.rawData_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisDataPPG.alwaysUseFieldBuilders) {
                    getTimeStampFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.timeStampBuilder_ == null) {
                    this.timeStamp_ = null;
                } else {
                    this.timeStampBuilder_.clear();
                }
                this.bitField0_ &= -2;
                this.rawData_ = Collections.emptyList();
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisPpgData.internal_static_HisDataPPG_descriptor;
            }

            public HisDataPPG getDefaultInstanceForType() {
                return HisDataPPG.getDefaultInstance();
            }

            public HisDataPPG build() {
                HisDataPPG result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisDataPPG buildPartial() {
                HisDataPPG result = new HisDataPPG((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.timeStampBuilder_ == null) {
                    result.timeStamp_ = this.timeStamp_;
                } else {
                    result.timeStamp_ = (DateTime) this.timeStampBuilder_.build();
                }
                if ((this.bitField0_ & 2) == 2) {
                    this.rawData_ = Collections.unmodifiableList(this.rawData_);
                    this.bitField0_ &= -3;
                }
                result.rawData_ = this.rawData_;
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
                if (other instanceof HisDataPPG) {
                    return mergeFrom((HisDataPPG) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisDataPPG other) {
                if (other != HisDataPPG.getDefaultInstance()) {
                    if (other.hasTimeStamp()) {
                        mergeTimeStamp(other.getTimeStamp());
                    }
                    if (!other.rawData_.isEmpty()) {
                        if (this.rawData_.isEmpty()) {
                            this.rawData_ = other.rawData_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureRawDataIsMutable();
                            this.rawData_.addAll(other.rawData_);
                        }
                        onChanged();
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasTimeStamp() && getTimeStamp().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisDataPPG parsedMessage = null;
                try {
                    HisDataPPG parsedMessage2 = (HisDataPPG) HisDataPPG.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisDataPPG parsedMessage3 = (HisDataPPG) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasTimeStamp() {
                return (this.bitField0_ & 1) == 1;
            }

            public DateTime getTimeStamp() {
                if (this.timeStampBuilder_ == null) {
                    return this.timeStamp_ == null ? DateTime.getDefaultInstance() : this.timeStamp_;
                }
                return (DateTime) this.timeStampBuilder_.getMessage();
            }

            public Builder setTimeStamp(DateTime value) {
                if (this.timeStampBuilder_ != null) {
                    this.timeStampBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.timeStamp_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setTimeStamp(com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder builderForValue) {
                if (this.timeStampBuilder_ == null) {
                    this.timeStamp_ = builderForValue.build();
                    onChanged();
                } else {
                    this.timeStampBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeTimeStamp(DateTime value) {
                if (this.timeStampBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.timeStamp_ == null || this.timeStamp_ == DateTime.getDefaultInstance()) {
                        this.timeStamp_ = value;
                    } else {
                        this.timeStamp_ = DateTime.newBuilder(this.timeStamp_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.timeStampBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearTimeStamp() {
                if (this.timeStampBuilder_ == null) {
                    this.timeStamp_ = null;
                    onChanged();
                } else {
                    this.timeStampBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder getTimeStampBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder) getTimeStampFieldBuilder().getBuilder();
            }

            public DateTimeOrBuilder getTimeStampOrBuilder() {
                if (this.timeStampBuilder_ != null) {
                    return (DateTimeOrBuilder) this.timeStampBuilder_.getMessageOrBuilder();
                }
                return this.timeStamp_ == null ? DateTime.getDefaultInstance() : this.timeStamp_;
            }

            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> getTimeStampFieldBuilder() {
                if (this.timeStampBuilder_ == null) {
                    this.timeStampBuilder_ = new SingleFieldBuilderV3<>(getTimeStamp(), getParentForChildren(), isClean());
                    this.timeStamp_ = null;
                }
                return this.timeStampBuilder_;
            }

            private void ensureRawDataIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.rawData_ = new ArrayList(this.rawData_);
                    this.bitField0_ |= 2;
                }
            }

            public List<Integer> getRawDataList() {
                return Collections.unmodifiableList(this.rawData_);
            }

            public int getRawDataCount() {
                return this.rawData_.size();
            }

            public int getRawData(int index) {
                return ((Integer) this.rawData_.get(index)).intValue();
            }

            public Builder setRawData(int index, int value) {
                ensureRawDataIsMutable();
                this.rawData_.set(index, Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addRawData(int value) {
                ensureRawDataIsMutable();
                this.rawData_.add(Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addAllRawData(Iterable<? extends Integer> values) {
                ensureRawDataIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.rawData_);
                onChanged();
                return this;
            }

            public Builder clearRawData() {
                this.rawData_ = Collections.emptyList();
                this.bitField0_ &= -3;
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

        private HisDataPPG(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.rawDataMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = -1;
        }

        private HisDataPPG() {
            this.rawDataMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = -1;
            this.rawData_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisDataPPG(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
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
                            com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder subBuilder = null;
                            if ((this.bitField0_ & 1) == 1) {
                                subBuilder = this.timeStamp_.toBuilder();
                            }
                            this.timeStamp_ = (DateTime) input.readMessage(DateTime.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.timeStamp_);
                                this.timeStamp_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            break;
                        case 18:
                            int limit = input.pushLimit(input.readRawVarint32());
                            if ((mutable_bitField0_ & 2) != 2 && input.getBytesUntilLimit() > 0) {
                                this.rawData_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.rawData_.add(Integer.valueOf(input.readSFixed32()));
                            }
                            input.popLimit(limit);
                            break;
                        case 21:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.rawData_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.rawData_.add(Integer.valueOf(input.readSFixed32()));
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
                    if ((mutable_bitField0_ & 2) == 2) {
                        this.rawData_ = Collections.unmodifiableList(this.rawData_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.rawData_ = Collections.unmodifiableList(this.rawData_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return HisPpgData.internal_static_HisDataPPG_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisPpgData.internal_static_HisDataPPG_fieldAccessorTable.ensureFieldAccessorsInitialized(HisDataPPG.class, Builder.class);
        }

        public boolean hasTimeStamp() {
            return (this.bitField0_ & 1) == 1;
        }

        public DateTime getTimeStamp() {
            return this.timeStamp_ == null ? DateTime.getDefaultInstance() : this.timeStamp_;
        }

        public DateTimeOrBuilder getTimeStampOrBuilder() {
            return this.timeStamp_ == null ? DateTime.getDefaultInstance() : this.timeStamp_;
        }

        public List<Integer> getRawDataList() {
            return this.rawData_;
        }

        public int getRawDataCount() {
            return this.rawData_.size();
        }

        public int getRawData(int index) {
            return ((Integer) this.rawData_.get(index)).intValue();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasTimeStamp()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getTimeStamp().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getTimeStamp());
            }
            if (getRawDataList().size() > 0) {
                output.writeUInt32NoTag(18);
                output.writeUInt32NoTag(this.rawDataMemoizedSerializedSize);
            }
            for (int i = 0; i < this.rawData_.size(); i++) {
                output.writeSFixed32NoTag(((Integer) this.rawData_.get(i)).intValue());
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, getTimeStamp());
            }
            int dataSize = getRawDataList().size() * 4;
            int size3 = size2 + dataSize;
            if (!getRawDataList().isEmpty()) {
                size3 = size3 + 1 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }
            this.rawDataMemoizedSerializedSize = dataSize;
            int size4 = size3 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size4;
            return size4;
        }

        public boolean equals(Object obj) {
            boolean result;
            boolean result2;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof HisDataPPG)) {
                return super.equals(obj);
            }
            HisDataPPG other = (HisDataPPG) obj;
            boolean result3 = 1 != 0 && hasTimeStamp() == other.hasTimeStamp();
            if (hasTimeStamp()) {
                result3 = result3 && getTimeStamp().equals(other.getTimeStamp());
            }
            if (!result3 || !getRawDataList().equals(other.getRawDataList())) {
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
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasTimeStamp()) {
                hash = (((hash * 37) + 1) * 53) + getTimeStamp().hashCode();
            }
            if (getRawDataCount() > 0) {
                hash = (((hash * 37) + 2) * 53) + getRawDataList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisDataPPG parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisDataPPG) PARSER.parseFrom(data);
        }

        public static HisDataPPG parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisDataPPG) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisDataPPG parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisDataPPG) PARSER.parseFrom(data);
        }

        public static HisDataPPG parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisDataPPG) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisDataPPG parseFrom(InputStream input) throws IOException {
            return (HisDataPPG) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisDataPPG parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataPPG) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisDataPPG parseDelimitedFrom(InputStream input) throws IOException {
            return (HisDataPPG) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisDataPPG parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataPPG) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisDataPPG parseFrom(CodedInputStream input) throws IOException {
            return (HisDataPPG) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisDataPPG parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataPPG) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisDataPPG prototype) {
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

        public static HisDataPPG getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisDataPPG> parser() {
            return PARSER;
        }

        public Parser<HisDataPPG> getParserForType() {
            return PARSER;
        }

        public HisDataPPG getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface HisDataPPGOrBuilder extends MessageOrBuilder {
        int getRawData(int i);

        int getRawDataCount();

        List<Integer> getRawDataList();

        DateTime getTimeStamp();

        DateTimeOrBuilder getTimeStampOrBuilder();

        boolean hasTimeStamp();
    }

    private HisPpgData() {
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
        FileDescriptor[] fileDescriptorArr = {RealtimeData.getDescriptor()};
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0012his_ppg_data.proto\u001a\u0013realtime_data.proto\"A\n\nHisDataPPG\u0012\u001d\n\ntime_stamp\u0018\u0001 \u0002(\u000b2\t.DateTime\u0012\u0014\n\braw_data\u0018\u0002 \u0003(\u000fB\u0002\u0010\u0001"}, fileDescriptorArr, new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                HisPpgData.descriptor = root;
                return null;
            }
        });
        RealtimeData.getDescriptor();
    }
}
