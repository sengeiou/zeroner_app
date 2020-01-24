package com.iwown.ble_module.proto.base;

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
import com.google.protobuf.Internal;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import com.iwown.ble_module.proto.base.HisEcgData.HisDataECG;
import com.iwown.ble_module.proto.base.HisEcgData.HisDataECGOrBuilder;
import com.iwown.ble_module.proto.base.HisGnssData.HisDataGNSS;
import com.iwown.ble_module.proto.base.HisGnssData.HisDataGNSSOrBuilder;
import com.iwown.ble_module.proto.base.HisHealthData.HisDataHealth;
import com.iwown.ble_module.proto.base.HisHealthData.HisDataHealthOrBuilder;
import com.iwown.ble_module.proto.base.HisPpgData.HisDataPPG;
import com.iwown.ble_module.proto.base.HisPpgData.HisDataPPGOrBuilder;
import com.iwown.ble_module.proto.base.HisRriData.HisDataRRI;
import com.iwown.ble_module.proto.base.HisRriData.HisDataRRIOrBuilder;
import com.iwown.ble_module.proto.base.RealtimeData.RtTime;
import com.iwown.ble_module.proto.base.RealtimeData.RtTimeOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HisDataOuterClass {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisBlock_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisBlock_fieldAccessorTable = new FieldAccessorTable(internal_static_HisBlock_descriptor, new String[]{"StartSeq", "EndSeq"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisConfirm_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(8));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisConfirm_fieldAccessorTable = new FieldAccessorTable(internal_static_HisConfirm_descriptor, new String[]{"Operation", "Ret"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisData_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(7));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisData_fieldAccessorTable = new FieldAccessorTable(internal_static_HisData_descriptor, new String[]{"Seq", "Status", "Health", "Gnss", "Ecg", "Ppg", "Rri", "Data"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisITSync_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisITSync_fieldAccessorTable = new FieldAccessorTable(internal_static_HisITSync_descriptor, new String[]{"Type"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisIndexTable_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(6));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisIndexTable_fieldAccessorTable = new FieldAccessorTable(internal_static_HisIndexTable_descriptor, new String[]{"Index"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisIndex_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(5));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisIndex_fieldAccessorTable = new FieldAccessorTable(internal_static_HisIndex_descriptor, new String[]{"Time", "StartSeq", "EndSeq"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisNotification_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(9));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisNotification_fieldAccessorTable = new FieldAccessorTable(internal_static_HisNotification_descriptor, new String[]{"Type", "Confirm", "IndexTable", "HisData", "Data"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisStartSync_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisStartSync_fieldAccessorTable = new FieldAccessorTable(internal_static_HisStartSync_descriptor, new String[]{"Type", "Block"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisStopSync_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisStopSync_fieldAccessorTable = new FieldAccessorTable(internal_static_HisStopSync_descriptor, new String[]{"Type"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisSubscriber_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisSubscriber_fieldAccessorTable = new FieldAccessorTable(internal_static_HisSubscriber_descriptor, new String[]{"Operation", "ItSync", "StartSync", "StopSync", "Data"});

    public static final class HisBlock extends GeneratedMessageV3 implements HisBlockOrBuilder {
        private static final HisBlock DEFAULT_INSTANCE = new HisBlock();
        public static final int END_SEQ_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<HisBlock> PARSER = new AbstractParser<HisBlock>() {
            public HisBlock parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisBlock(input, extensionRegistry);
            }
        };
        public static final int START_SEQ_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int endSeq_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int startSeq_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisBlockOrBuilder {
            private int bitField0_;
            private int endSeq_;
            private int startSeq_;

            public static final Descriptor getDescriptor() {
                return HisDataOuterClass.internal_static_HisBlock_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisDataOuterClass.internal_static_HisBlock_fieldAccessorTable.ensureFieldAccessorsInitialized(HisBlock.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisBlock.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.startSeq_ = 0;
                this.bitField0_ &= -2;
                this.endSeq_ = 0;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisDataOuterClass.internal_static_HisBlock_descriptor;
            }

            public HisBlock getDefaultInstanceForType() {
                return HisBlock.getDefaultInstance();
            }

            public HisBlock build() {
                HisBlock result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisBlock buildPartial() {
                HisBlock result = new HisBlock((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.startSeq_ = this.startSeq_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.endSeq_ = this.endSeq_;
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
                if (other instanceof HisBlock) {
                    return mergeFrom((HisBlock) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisBlock other) {
                if (other != HisBlock.getDefaultInstance()) {
                    if (other.hasStartSeq()) {
                        setStartSeq(other.getStartSeq());
                    }
                    if (other.hasEndSeq()) {
                        setEndSeq(other.getEndSeq());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasStartSeq() && hasEndSeq()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisBlock parsedMessage = null;
                try {
                    HisBlock parsedMessage2 = (HisBlock) HisBlock.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisBlock parsedMessage3 = (HisBlock) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasStartSeq() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getStartSeq() {
                return this.startSeq_;
            }

            public Builder setStartSeq(int value) {
                this.bitField0_ |= 1;
                this.startSeq_ = value;
                onChanged();
                return this;
            }

            public Builder clearStartSeq() {
                this.bitField0_ &= -2;
                this.startSeq_ = 0;
                onChanged();
                return this;
            }

            public boolean hasEndSeq() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getEndSeq() {
                return this.endSeq_;
            }

            public Builder setEndSeq(int value) {
                this.bitField0_ |= 2;
                this.endSeq_ = value;
                onChanged();
                return this;
            }

            public Builder clearEndSeq() {
                this.bitField0_ &= -3;
                this.endSeq_ = 0;
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

        private HisBlock(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisBlock() {
            this.memoizedIsInitialized = -1;
            this.startSeq_ = 0;
            this.endSeq_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisBlock(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.startSeq_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.endSeq_ = input.readFixed32();
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
            return HisDataOuterClass.internal_static_HisBlock_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisDataOuterClass.internal_static_HisBlock_fieldAccessorTable.ensureFieldAccessorsInitialized(HisBlock.class, Builder.class);
        }

        public boolean hasStartSeq() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getStartSeq() {
            return this.startSeq_;
        }

        public boolean hasEndSeq() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getEndSeq() {
            return this.endSeq_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasStartSeq()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasEndSeq()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.startSeq_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.endSeq_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.startSeq_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.endSeq_);
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
            if (!(obj instanceof HisBlock)) {
                return super.equals(obj);
            }
            HisBlock other = (HisBlock) obj;
            boolean result3 = 1 != 0 && hasStartSeq() == other.hasStartSeq();
            if (hasStartSeq()) {
                result3 = result3 && getStartSeq() == other.getStartSeq();
            }
            if (!result3 || hasEndSeq() != other.hasEndSeq()) {
                result = false;
            } else {
                result = true;
            }
            if (hasEndSeq()) {
                if (!result || getEndSeq() != other.getEndSeq()) {
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
            if (hasStartSeq()) {
                hash = (((hash * 37) + 1) * 53) + getStartSeq();
            }
            if (hasEndSeq()) {
                hash = (((hash * 37) + 2) * 53) + getEndSeq();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisBlock parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisBlock) PARSER.parseFrom(data);
        }

        public static HisBlock parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisBlock) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisBlock parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisBlock) PARSER.parseFrom(data);
        }

        public static HisBlock parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisBlock) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisBlock parseFrom(InputStream input) throws IOException {
            return (HisBlock) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisBlock parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisBlock) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisBlock parseDelimitedFrom(InputStream input) throws IOException {
            return (HisBlock) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisBlock parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisBlock) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisBlock parseFrom(CodedInputStream input) throws IOException {
            return (HisBlock) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisBlock parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisBlock) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisBlock prototype) {
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

        public static HisBlock getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisBlock> parser() {
            return PARSER;
        }

        public Parser<HisBlock> getParserForType() {
            return PARSER;
        }

        public HisBlock getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisConfirm extends GeneratedMessageV3 implements HisConfirmOrBuilder {
        private static final HisConfirm DEFAULT_INSTANCE = new HisConfirm();
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<HisConfirm> PARSER = new AbstractParser<HisConfirm>() {
            public HisConfirm parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisConfirm(input, extensionRegistry);
            }
        };
        public static final int RET_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int operation_;
        /* access modifiers changed from: private */
        public boolean ret_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisConfirmOrBuilder {
            private int bitField0_;
            private int operation_;
            private boolean ret_;

            public static final Descriptor getDescriptor() {
                return HisDataOuterClass.internal_static_HisConfirm_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisDataOuterClass.internal_static_HisConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(HisConfirm.class, Builder.class);
            }

            private Builder() {
                this.operation_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.operation_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisConfirm.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.operation_ = 0;
                this.bitField0_ &= -2;
                this.ret_ = false;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisDataOuterClass.internal_static_HisConfirm_descriptor;
            }

            public HisConfirm getDefaultInstanceForType() {
                return HisConfirm.getDefaultInstance();
            }

            public HisConfirm build() {
                HisConfirm result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisConfirm buildPartial() {
                HisConfirm result = new HisConfirm((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.operation_ = this.operation_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.ret_ = this.ret_;
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
                if (other instanceof HisConfirm) {
                    return mergeFrom((HisConfirm) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisConfirm other) {
                if (other != HisConfirm.getDefaultInstance()) {
                    if (other.hasOperation()) {
                        setOperation(other.getOperation());
                    }
                    if (other.hasRet()) {
                        setRet(other.getRet());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasOperation() && hasRet()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisConfirm parsedMessage = null;
                try {
                    HisConfirm parsedMessage2 = (HisConfirm) HisConfirm.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisConfirm parsedMessage3 = (HisConfirm) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasOperation() {
                return (this.bitField0_ & 1) == 1;
            }

            public HisOperation getOperation() {
                HisOperation result = HisOperation.valueOf(this.operation_);
                return result == null ? HisOperation.IT_SYNC : result;
            }

            public Builder setOperation(HisOperation value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.operation_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearOperation() {
                this.bitField0_ &= -2;
                this.operation_ = 0;
                onChanged();
                return this;
            }

            public boolean hasRet() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getRet() {
                return this.ret_;
            }

            public Builder setRet(boolean value) {
                this.bitField0_ |= 2;
                this.ret_ = value;
                onChanged();
                return this;
            }

            public Builder clearRet() {
                this.bitField0_ &= -3;
                this.ret_ = false;
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

        private HisConfirm(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisConfirm() {
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
            this.ret_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisConfirm(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            int rawValue = input.readEnum();
                            if (HisOperation.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.operation_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 16:
                            this.bitField0_ |= 2;
                            this.ret_ = input.readBool();
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
            return HisDataOuterClass.internal_static_HisConfirm_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisDataOuterClass.internal_static_HisConfirm_fieldAccessorTable.ensureFieldAccessorsInitialized(HisConfirm.class, Builder.class);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public HisOperation getOperation() {
            HisOperation result = HisOperation.valueOf(this.operation_);
            return result == null ? HisOperation.IT_SYNC : result;
        }

        public boolean hasRet() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getRet() {
            return this.ret_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasOperation()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasRet()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.operation_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.ret_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.operation_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.ret_);
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
            if (!(obj instanceof HisConfirm)) {
                return super.equals(obj);
            }
            HisConfirm other = (HisConfirm) obj;
            boolean result3 = 1 != 0 && hasOperation() == other.hasOperation();
            if (hasOperation()) {
                result3 = result3 && this.operation_ == other.operation_;
            }
            if (!result3 || hasRet() != other.hasRet()) {
                result = false;
            } else {
                result = true;
            }
            if (hasRet()) {
                if (!result || getRet() != other.getRet()) {
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
            if (hasOperation()) {
                hash = (((hash * 37) + 1) * 53) + this.operation_;
            }
            if (hasRet()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getRet());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisConfirm parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisConfirm) PARSER.parseFrom(data);
        }

        public static HisConfirm parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisConfirm parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisConfirm) PARSER.parseFrom(data);
        }

        public static HisConfirm parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisConfirm) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisConfirm parseFrom(InputStream input) throws IOException {
            return (HisConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisConfirm parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisConfirm parseDelimitedFrom(InputStream input) throws IOException {
            return (HisConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisConfirm parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisConfirm) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisConfirm parseFrom(CodedInputStream input) throws IOException {
            return (HisConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisConfirm parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisConfirm) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisConfirm prototype) {
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

        public static HisConfirm getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisConfirm> parser() {
            return PARSER;
        }

        public Parser<HisConfirm> getParserForType() {
            return PARSER;
        }

        public HisConfirm getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisData extends GeneratedMessageV3 implements HisDataOrBuilder {
        private static final HisData DEFAULT_INSTANCE = new HisData();
        public static final int ECG_FIELD_NUMBER = 5;
        public static final int GNSS_FIELD_NUMBER = 4;
        public static final int HEALTH_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<HisData> PARSER = new AbstractParser<HisData>() {
            public HisData parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisData(input, extensionRegistry);
            }
        };
        public static final int PPG_FIELD_NUMBER = 6;
        public static final int RRI_FIELD_NUMBER = 7;
        public static final int SEQ_FIELD_NUMBER = 1;
        public static final int STATUS_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int dataCase_;
        /* access modifiers changed from: private */
        public Object data_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int seq_;

        public enum DataCase implements EnumLite {
            STATUS(2),
            HEALTH(3),
            GNSS(4),
            ECG(5),
            PPG(6),
            RRI(7),
            DATA_NOT_SET(0);
            
            private final int value;

            private DataCase(int value2) {
                this.value = value2;
            }

            @Deprecated
            public static DataCase valueOf(int value2) {
                return forNumber(value2);
            }

            public static DataCase forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return DATA_NOT_SET;
                    case 2:
                        return STATUS;
                    case 3:
                        return HEALTH;
                    case 4:
                        return GNSS;
                    case 5:
                        return ECG;
                    case 6:
                        return PPG;
                    case 7:
                        return RRI;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisDataOrBuilder {
            private int bitField0_;
            private int dataCase_;
            private Object data_;
            private SingleFieldBuilderV3<HisDataECG, com.iwown.ble_module.proto.base.HisEcgData.HisDataECG.Builder, HisDataECGOrBuilder> ecgBuilder_;
            private SingleFieldBuilderV3<HisDataGNSS, com.iwown.ble_module.proto.base.HisGnssData.HisDataGNSS.Builder, HisDataGNSSOrBuilder> gnssBuilder_;
            private SingleFieldBuilderV3<HisDataHealth, com.iwown.ble_module.proto.base.HisHealthData.HisDataHealth.Builder, HisDataHealthOrBuilder> healthBuilder_;
            private SingleFieldBuilderV3<HisDataPPG, com.iwown.ble_module.proto.base.HisPpgData.HisDataPPG.Builder, HisDataPPGOrBuilder> ppgBuilder_;
            private SingleFieldBuilderV3<HisDataRRI, com.iwown.ble_module.proto.base.HisRriData.HisDataRRI.Builder, HisDataRRIOrBuilder> rriBuilder_;
            private int seq_;

            public static final Descriptor getDescriptor() {
                return HisDataOuterClass.internal_static_HisData_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisDataOuterClass.internal_static_HisData_fieldAccessorTable.ensureFieldAccessorsInitialized(HisData.class, Builder.class);
            }

            private Builder() {
                this.dataCase_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.dataCase_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisData.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.seq_ = 0;
                this.bitField0_ &= -2;
                this.dataCase_ = 0;
                this.data_ = null;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisDataOuterClass.internal_static_HisData_descriptor;
            }

            public HisData getDefaultInstanceForType() {
                return HisData.getDefaultInstance();
            }

            public HisData build() {
                HisData result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisData buildPartial() {
                HisData result = new HisData((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.seq_ = this.seq_;
                if (this.dataCase_ == 2) {
                    result.data_ = this.data_;
                }
                if (this.dataCase_ == 3) {
                    if (this.healthBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.healthBuilder_.build();
                    }
                }
                if (this.dataCase_ == 4) {
                    if (this.gnssBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.gnssBuilder_.build();
                    }
                }
                if (this.dataCase_ == 5) {
                    if (this.ecgBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.ecgBuilder_.build();
                    }
                }
                if (this.dataCase_ == 6) {
                    if (this.ppgBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.ppgBuilder_.build();
                    }
                }
                if (this.dataCase_ == 7) {
                    if (this.rriBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.rriBuilder_.build();
                    }
                }
                result.bitField0_ = to_bitField0_;
                result.dataCase_ = this.dataCase_;
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
                if (other instanceof HisData) {
                    return mergeFrom((HisData) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisData other) {
                if (other != HisData.getDefaultInstance()) {
                    if (other.hasSeq()) {
                        setSeq(other.getSeq());
                    }
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisData$DataCase[other.getDataCase().ordinal()]) {
                        case 1:
                            setStatus(other.getStatus());
                            break;
                        case 2:
                            mergeHealth(other.getHealth());
                            break;
                        case 3:
                            mergeGnss(other.getGnss());
                            break;
                        case 4:
                            mergeEcg(other.getEcg());
                            break;
                        case 5:
                            mergePpg(other.getPpg());
                            break;
                        case 6:
                            mergeRri(other.getRri());
                            break;
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasSeq()) {
                    return false;
                }
                if (hasHealth() && !getHealth().isInitialized()) {
                    return false;
                }
                if (hasGnss() && !getGnss().isInitialized()) {
                    return false;
                }
                if (hasEcg() && !getEcg().isInitialized()) {
                    return false;
                }
                if (hasPpg() && !getPpg().isInitialized()) {
                    return false;
                }
                if (!hasRri() || getRri().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisData parsedMessage = null;
                try {
                    HisData parsedMessage2 = (HisData) HisData.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisData parsedMessage3 = (HisData) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public DataCase getDataCase() {
                return DataCase.forNumber(this.dataCase_);
            }

            public Builder clearData() {
                this.dataCase_ = 0;
                this.data_ = null;
                onChanged();
                return this;
            }

            public boolean hasSeq() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getSeq() {
                return this.seq_;
            }

            public Builder setSeq(int value) {
                this.bitField0_ |= 1;
                this.seq_ = value;
                onChanged();
                return this;
            }

            public Builder clearSeq() {
                this.bitField0_ &= -2;
                this.seq_ = 0;
                onChanged();
                return this;
            }

            public boolean hasStatus() {
                return this.dataCase_ == 2;
            }

            public HisDataStatus getStatus() {
                if (this.dataCase_ != 2) {
                    return HisDataStatus.STATUS_OK;
                }
                HisDataStatus result = HisDataStatus.valueOf(((Integer) this.data_).intValue());
                if (result == null) {
                    return HisDataStatus.STATUS_OK;
                }
                return result;
            }

            public Builder setStatus(HisDataStatus value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.dataCase_ = 2;
                this.data_ = Integer.valueOf(value.getNumber());
                onChanged();
                return this;
            }

            public Builder clearStatus() {
                if (this.dataCase_ == 2) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public boolean hasHealth() {
                return this.dataCase_ == 3;
            }

            public HisDataHealth getHealth() {
                if (this.healthBuilder_ == null) {
                    if (this.dataCase_ == 3) {
                        return (HisDataHealth) this.data_;
                    }
                    return HisDataHealth.getDefaultInstance();
                } else if (this.dataCase_ == 3) {
                    return (HisDataHealth) this.healthBuilder_.getMessage();
                } else {
                    return HisDataHealth.getDefaultInstance();
                }
            }

            public Builder setHealth(HisDataHealth value) {
                if (this.healthBuilder_ != null) {
                    this.healthBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder setHealth(com.iwown.ble_module.proto.base.HisHealthData.HisDataHealth.Builder builderForValue) {
                if (this.healthBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.healthBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder mergeHealth(HisDataHealth value) {
                if (this.healthBuilder_ == null) {
                    if (this.dataCase_ != 3 || this.data_ == HisDataHealth.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = HisDataHealth.newBuilder((HisDataHealth) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 3) {
                        this.healthBuilder_.mergeFrom(value);
                    }
                    this.healthBuilder_.setMessage(value);
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder clearHealth() {
                if (this.healthBuilder_ != null) {
                    if (this.dataCase_ == 3) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.healthBuilder_.clear();
                } else if (this.dataCase_ == 3) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public com.iwown.ble_module.proto.base.HisHealthData.HisDataHealth.Builder getHealthBuilder() {
                return (com.iwown.ble_module.proto.base.HisHealthData.HisDataHealth.Builder) getHealthFieldBuilder().getBuilder();
            }

            public HisDataHealthOrBuilder getHealthOrBuilder() {
                if (this.dataCase_ == 3 && this.healthBuilder_ != null) {
                    return (HisDataHealthOrBuilder) this.healthBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 3) {
                    return (HisDataHealth) this.data_;
                }
                return HisDataHealth.getDefaultInstance();
            }

            private SingleFieldBuilderV3<HisDataHealth, com.iwown.ble_module.proto.base.HisHealthData.HisDataHealth.Builder, HisDataHealthOrBuilder> getHealthFieldBuilder() {
                if (this.healthBuilder_ == null) {
                    if (this.dataCase_ != 3) {
                        this.data_ = HisDataHealth.getDefaultInstance();
                    }
                    this.healthBuilder_ = new SingleFieldBuilderV3<>((HisDataHealth) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 3;
                onChanged();
                return this.healthBuilder_;
            }

            public boolean hasGnss() {
                return this.dataCase_ == 4;
            }

            public HisDataGNSS getGnss() {
                if (this.gnssBuilder_ == null) {
                    if (this.dataCase_ == 4) {
                        return (HisDataGNSS) this.data_;
                    }
                    return HisDataGNSS.getDefaultInstance();
                } else if (this.dataCase_ == 4) {
                    return (HisDataGNSS) this.gnssBuilder_.getMessage();
                } else {
                    return HisDataGNSS.getDefaultInstance();
                }
            }

            public Builder setGnss(HisDataGNSS value) {
                if (this.gnssBuilder_ != null) {
                    this.gnssBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 4;
                return this;
            }

            public Builder setGnss(com.iwown.ble_module.proto.base.HisGnssData.HisDataGNSS.Builder builderForValue) {
                if (this.gnssBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.gnssBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 4;
                return this;
            }

            public Builder mergeGnss(HisDataGNSS value) {
                if (this.gnssBuilder_ == null) {
                    if (this.dataCase_ != 4 || this.data_ == HisDataGNSS.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = HisDataGNSS.newBuilder((HisDataGNSS) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 4) {
                        this.gnssBuilder_.mergeFrom(value);
                    }
                    this.gnssBuilder_.setMessage(value);
                }
                this.dataCase_ = 4;
                return this;
            }

            public Builder clearGnss() {
                if (this.gnssBuilder_ != null) {
                    if (this.dataCase_ == 4) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.gnssBuilder_.clear();
                } else if (this.dataCase_ == 4) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public com.iwown.ble_module.proto.base.HisGnssData.HisDataGNSS.Builder getGnssBuilder() {
                return (com.iwown.ble_module.proto.base.HisGnssData.HisDataGNSS.Builder) getGnssFieldBuilder().getBuilder();
            }

            public HisDataGNSSOrBuilder getGnssOrBuilder() {
                if (this.dataCase_ == 4 && this.gnssBuilder_ != null) {
                    return (HisDataGNSSOrBuilder) this.gnssBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 4) {
                    return (HisDataGNSS) this.data_;
                }
                return HisDataGNSS.getDefaultInstance();
            }

            private SingleFieldBuilderV3<HisDataGNSS, com.iwown.ble_module.proto.base.HisGnssData.HisDataGNSS.Builder, HisDataGNSSOrBuilder> getGnssFieldBuilder() {
                if (this.gnssBuilder_ == null) {
                    if (this.dataCase_ != 4) {
                        this.data_ = HisDataGNSS.getDefaultInstance();
                    }
                    this.gnssBuilder_ = new SingleFieldBuilderV3<>((HisDataGNSS) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 4;
                onChanged();
                return this.gnssBuilder_;
            }

            public boolean hasEcg() {
                return this.dataCase_ == 5;
            }

            public HisDataECG getEcg() {
                if (this.ecgBuilder_ == null) {
                    if (this.dataCase_ == 5) {
                        return (HisDataECG) this.data_;
                    }
                    return HisDataECG.getDefaultInstance();
                } else if (this.dataCase_ == 5) {
                    return (HisDataECG) this.ecgBuilder_.getMessage();
                } else {
                    return HisDataECG.getDefaultInstance();
                }
            }

            public Builder setEcg(HisDataECG value) {
                if (this.ecgBuilder_ != null) {
                    this.ecgBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 5;
                return this;
            }

            public Builder setEcg(com.iwown.ble_module.proto.base.HisEcgData.HisDataECG.Builder builderForValue) {
                if (this.ecgBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.ecgBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 5;
                return this;
            }

            public Builder mergeEcg(HisDataECG value) {
                if (this.ecgBuilder_ == null) {
                    if (this.dataCase_ != 5 || this.data_ == HisDataECG.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = HisDataECG.newBuilder((HisDataECG) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 5) {
                        this.ecgBuilder_.mergeFrom(value);
                    }
                    this.ecgBuilder_.setMessage(value);
                }
                this.dataCase_ = 5;
                return this;
            }

            public Builder clearEcg() {
                if (this.ecgBuilder_ != null) {
                    if (this.dataCase_ == 5) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.ecgBuilder_.clear();
                } else if (this.dataCase_ == 5) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public com.iwown.ble_module.proto.base.HisEcgData.HisDataECG.Builder getEcgBuilder() {
                return (com.iwown.ble_module.proto.base.HisEcgData.HisDataECG.Builder) getEcgFieldBuilder().getBuilder();
            }

            public HisDataECGOrBuilder getEcgOrBuilder() {
                if (this.dataCase_ == 5 && this.ecgBuilder_ != null) {
                    return (HisDataECGOrBuilder) this.ecgBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 5) {
                    return (HisDataECG) this.data_;
                }
                return HisDataECG.getDefaultInstance();
            }

            private SingleFieldBuilderV3<HisDataECG, com.iwown.ble_module.proto.base.HisEcgData.HisDataECG.Builder, HisDataECGOrBuilder> getEcgFieldBuilder() {
                if (this.ecgBuilder_ == null) {
                    if (this.dataCase_ != 5) {
                        this.data_ = HisDataECG.getDefaultInstance();
                    }
                    this.ecgBuilder_ = new SingleFieldBuilderV3<>((HisDataECG) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 5;
                onChanged();
                return this.ecgBuilder_;
            }

            public boolean hasPpg() {
                return this.dataCase_ == 6;
            }

            public HisDataPPG getPpg() {
                if (this.ppgBuilder_ == null) {
                    if (this.dataCase_ == 6) {
                        return (HisDataPPG) this.data_;
                    }
                    return HisDataPPG.getDefaultInstance();
                } else if (this.dataCase_ == 6) {
                    return (HisDataPPG) this.ppgBuilder_.getMessage();
                } else {
                    return HisDataPPG.getDefaultInstance();
                }
            }

            public Builder setPpg(HisDataPPG value) {
                if (this.ppgBuilder_ != null) {
                    this.ppgBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 6;
                return this;
            }

            public Builder setPpg(com.iwown.ble_module.proto.base.HisPpgData.HisDataPPG.Builder builderForValue) {
                if (this.ppgBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.ppgBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 6;
                return this;
            }

            public Builder mergePpg(HisDataPPG value) {
                if (this.ppgBuilder_ == null) {
                    if (this.dataCase_ != 6 || this.data_ == HisDataPPG.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = HisDataPPG.newBuilder((HisDataPPG) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 6) {
                        this.ppgBuilder_.mergeFrom(value);
                    }
                    this.ppgBuilder_.setMessage(value);
                }
                this.dataCase_ = 6;
                return this;
            }

            public Builder clearPpg() {
                if (this.ppgBuilder_ != null) {
                    if (this.dataCase_ == 6) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.ppgBuilder_.clear();
                } else if (this.dataCase_ == 6) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public com.iwown.ble_module.proto.base.HisPpgData.HisDataPPG.Builder getPpgBuilder() {
                return (com.iwown.ble_module.proto.base.HisPpgData.HisDataPPG.Builder) getPpgFieldBuilder().getBuilder();
            }

            public HisDataPPGOrBuilder getPpgOrBuilder() {
                if (this.dataCase_ == 6 && this.ppgBuilder_ != null) {
                    return (HisDataPPGOrBuilder) this.ppgBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 6) {
                    return (HisDataPPG) this.data_;
                }
                return HisDataPPG.getDefaultInstance();
            }

            private SingleFieldBuilderV3<HisDataPPG, com.iwown.ble_module.proto.base.HisPpgData.HisDataPPG.Builder, HisDataPPGOrBuilder> getPpgFieldBuilder() {
                if (this.ppgBuilder_ == null) {
                    if (this.dataCase_ != 6) {
                        this.data_ = HisDataPPG.getDefaultInstance();
                    }
                    this.ppgBuilder_ = new SingleFieldBuilderV3<>((HisDataPPG) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 6;
                onChanged();
                return this.ppgBuilder_;
            }

            public boolean hasRri() {
                return this.dataCase_ == 7;
            }

            public HisDataRRI getRri() {
                if (this.rriBuilder_ == null) {
                    if (this.dataCase_ == 7) {
                        return (HisDataRRI) this.data_;
                    }
                    return HisDataRRI.getDefaultInstance();
                } else if (this.dataCase_ == 7) {
                    return (HisDataRRI) this.rriBuilder_.getMessage();
                } else {
                    return HisDataRRI.getDefaultInstance();
                }
            }

            public Builder setRri(HisDataRRI value) {
                if (this.rriBuilder_ != null) {
                    this.rriBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 7;
                return this;
            }

            public Builder setRri(com.iwown.ble_module.proto.base.HisRriData.HisDataRRI.Builder builderForValue) {
                if (this.rriBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.rriBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 7;
                return this;
            }

            public Builder mergeRri(HisDataRRI value) {
                if (this.rriBuilder_ == null) {
                    if (this.dataCase_ != 7 || this.data_ == HisDataRRI.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = HisDataRRI.newBuilder((HisDataRRI) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 7) {
                        this.rriBuilder_.mergeFrom(value);
                    }
                    this.rriBuilder_.setMessage(value);
                }
                this.dataCase_ = 7;
                return this;
            }

            public Builder clearRri() {
                if (this.rriBuilder_ != null) {
                    if (this.dataCase_ == 7) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.rriBuilder_.clear();
                } else if (this.dataCase_ == 7) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public com.iwown.ble_module.proto.base.HisRriData.HisDataRRI.Builder getRriBuilder() {
                return (com.iwown.ble_module.proto.base.HisRriData.HisDataRRI.Builder) getRriFieldBuilder().getBuilder();
            }

            public HisDataRRIOrBuilder getRriOrBuilder() {
                if (this.dataCase_ == 7 && this.rriBuilder_ != null) {
                    return (HisDataRRIOrBuilder) this.rriBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 7) {
                    return (HisDataRRI) this.data_;
                }
                return HisDataRRI.getDefaultInstance();
            }

            private SingleFieldBuilderV3<HisDataRRI, com.iwown.ble_module.proto.base.HisRriData.HisDataRRI.Builder, HisDataRRIOrBuilder> getRriFieldBuilder() {
                if (this.rriBuilder_ == null) {
                    if (this.dataCase_ != 7) {
                        this.data_ = HisDataRRI.getDefaultInstance();
                    }
                    this.rriBuilder_ = new SingleFieldBuilderV3<>((HisDataRRI) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 7;
                onChanged();
                return this.rriBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private HisData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private HisData() {
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
            this.seq_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisData(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.seq_ = input.readFixed32();
                            break;
                        case 16:
                            int rawValue = input.readEnum();
                            if (HisDataStatus.valueOf(rawValue) != null) {
                                this.dataCase_ = 2;
                                this.data_ = Integer.valueOf(rawValue);
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue);
                                break;
                            }
                        case 26:
                            com.iwown.ble_module.proto.base.HisHealthData.HisDataHealth.Builder subBuilder = null;
                            if (this.dataCase_ == 3) {
                                subBuilder = ((HisDataHealth) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(HisDataHealth.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((HisDataHealth) this.data_);
                                this.data_ = subBuilder.buildPartial();
                            }
                            this.dataCase_ = 3;
                            break;
                        case 34:
                            com.iwown.ble_module.proto.base.HisGnssData.HisDataGNSS.Builder subBuilder2 = null;
                            if (this.dataCase_ == 4) {
                                subBuilder2 = ((HisDataGNSS) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(HisDataGNSS.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((HisDataGNSS) this.data_);
                                this.data_ = subBuilder2.buildPartial();
                            }
                            this.dataCase_ = 4;
                            break;
                        case 42:
                            com.iwown.ble_module.proto.base.HisEcgData.HisDataECG.Builder subBuilder3 = null;
                            if (this.dataCase_ == 5) {
                                subBuilder3 = ((HisDataECG) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(HisDataECG.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom((HisDataECG) this.data_);
                                this.data_ = subBuilder3.buildPartial();
                            }
                            this.dataCase_ = 5;
                            break;
                        case 50:
                            com.iwown.ble_module.proto.base.HisPpgData.HisDataPPG.Builder subBuilder4 = null;
                            if (this.dataCase_ == 6) {
                                subBuilder4 = ((HisDataPPG) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(HisDataPPG.PARSER, extensionRegistry);
                            if (subBuilder4 != null) {
                                subBuilder4.mergeFrom((HisDataPPG) this.data_);
                                this.data_ = subBuilder4.buildPartial();
                            }
                            this.dataCase_ = 6;
                            break;
                        case 58:
                            com.iwown.ble_module.proto.base.HisRriData.HisDataRRI.Builder subBuilder5 = null;
                            if (this.dataCase_ == 7) {
                                subBuilder5 = ((HisDataRRI) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(HisDataRRI.PARSER, extensionRegistry);
                            if (subBuilder5 != null) {
                                subBuilder5.mergeFrom((HisDataRRI) this.data_);
                                this.data_ = subBuilder5.buildPartial();
                            }
                            this.dataCase_ = 7;
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
            return HisDataOuterClass.internal_static_HisData_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisDataOuterClass.internal_static_HisData_fieldAccessorTable.ensureFieldAccessorsInitialized(HisData.class, Builder.class);
        }

        public DataCase getDataCase() {
            return DataCase.forNumber(this.dataCase_);
        }

        public boolean hasSeq() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getSeq() {
            return this.seq_;
        }

        public boolean hasStatus() {
            return this.dataCase_ == 2;
        }

        public HisDataStatus getStatus() {
            if (this.dataCase_ != 2) {
                return HisDataStatus.STATUS_OK;
            }
            HisDataStatus result = HisDataStatus.valueOf(((Integer) this.data_).intValue());
            if (result == null) {
                return HisDataStatus.STATUS_OK;
            }
            return result;
        }

        public boolean hasHealth() {
            return this.dataCase_ == 3;
        }

        public HisDataHealth getHealth() {
            if (this.dataCase_ == 3) {
                return (HisDataHealth) this.data_;
            }
            return HisDataHealth.getDefaultInstance();
        }

        public HisDataHealthOrBuilder getHealthOrBuilder() {
            if (this.dataCase_ == 3) {
                return (HisDataHealth) this.data_;
            }
            return HisDataHealth.getDefaultInstance();
        }

        public boolean hasGnss() {
            return this.dataCase_ == 4;
        }

        public HisDataGNSS getGnss() {
            if (this.dataCase_ == 4) {
                return (HisDataGNSS) this.data_;
            }
            return HisDataGNSS.getDefaultInstance();
        }

        public HisDataGNSSOrBuilder getGnssOrBuilder() {
            if (this.dataCase_ == 4) {
                return (HisDataGNSS) this.data_;
            }
            return HisDataGNSS.getDefaultInstance();
        }

        public boolean hasEcg() {
            return this.dataCase_ == 5;
        }

        public HisDataECG getEcg() {
            if (this.dataCase_ == 5) {
                return (HisDataECG) this.data_;
            }
            return HisDataECG.getDefaultInstance();
        }

        public HisDataECGOrBuilder getEcgOrBuilder() {
            if (this.dataCase_ == 5) {
                return (HisDataECG) this.data_;
            }
            return HisDataECG.getDefaultInstance();
        }

        public boolean hasPpg() {
            return this.dataCase_ == 6;
        }

        public HisDataPPG getPpg() {
            if (this.dataCase_ == 6) {
                return (HisDataPPG) this.data_;
            }
            return HisDataPPG.getDefaultInstance();
        }

        public HisDataPPGOrBuilder getPpgOrBuilder() {
            if (this.dataCase_ == 6) {
                return (HisDataPPG) this.data_;
            }
            return HisDataPPG.getDefaultInstance();
        }

        public boolean hasRri() {
            return this.dataCase_ == 7;
        }

        public HisDataRRI getRri() {
            if (this.dataCase_ == 7) {
                return (HisDataRRI) this.data_;
            }
            return HisDataRRI.getDefaultInstance();
        }

        public HisDataRRIOrBuilder getRriOrBuilder() {
            if (this.dataCase_ == 7) {
                return (HisDataRRI) this.data_;
            }
            return HisDataRRI.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSeq()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasHealth() && !getHealth().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasGnss() && !getGnss().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasEcg() && !getEcg().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasPpg() && !getPpg().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasRri() || getRri().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.seq_);
            }
            if (this.dataCase_ == 2) {
                output.writeEnum(2, ((Integer) this.data_).intValue());
            }
            if (this.dataCase_ == 3) {
                output.writeMessage(3, (HisDataHealth) this.data_);
            }
            if (this.dataCase_ == 4) {
                output.writeMessage(4, (HisDataGNSS) this.data_);
            }
            if (this.dataCase_ == 5) {
                output.writeMessage(5, (HisDataECG) this.data_);
            }
            if (this.dataCase_ == 6) {
                output.writeMessage(6, (HisDataPPG) this.data_);
            }
            if (this.dataCase_ == 7) {
                output.writeMessage(7, (HisDataRRI) this.data_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.seq_);
            }
            if (this.dataCase_ == 2) {
                size2 += CodedOutputStream.computeEnumSize(2, ((Integer) this.data_).intValue());
            }
            if (this.dataCase_ == 3) {
                size2 += CodedOutputStream.computeMessageSize(3, (HisDataHealth) this.data_);
            }
            if (this.dataCase_ == 4) {
                size2 += CodedOutputStream.computeMessageSize(4, (HisDataGNSS) this.data_);
            }
            if (this.dataCase_ == 5) {
                size2 += CodedOutputStream.computeMessageSize(5, (HisDataECG) this.data_);
            }
            if (this.dataCase_ == 6) {
                size2 += CodedOutputStream.computeMessageSize(6, (HisDataPPG) this.data_);
            }
            if (this.dataCase_ == 7) {
                size2 += CodedOutputStream.computeMessageSize(7, (HisDataRRI) this.data_);
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
            if (!(obj instanceof HisData)) {
                return super.equals(obj);
            }
            HisData other = (HisData) obj;
            boolean result3 = 1 != 0 && hasSeq() == other.hasSeq();
            if (hasSeq()) {
                result3 = result3 && getSeq() == other.getSeq();
            }
            if (!result3 || !getDataCase().equals(other.getDataCase())) {
                result = false;
            } else {
                result = true;
            }
            if (!result) {
                return false;
            }
            switch (this.dataCase_) {
                case 2:
                    if (result && getStatus().equals(other.getStatus())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 3:
                    if (result && getHealth().equals(other.getHealth())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 4:
                    if (result && getGnss().equals(other.getGnss())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 5:
                    if (result && getEcg().equals(other.getEcg())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 6:
                    if (result && getPpg().equals(other.getPpg())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 7:
                    if (result && getRri().equals(other.getRri())) {
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
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasSeq()) {
                hash = (((hash * 37) + 1) * 53) + getSeq();
            }
            switch (this.dataCase_) {
                case 2:
                    hash = (((hash * 37) + 2) * 53) + getStatus().getNumber();
                    break;
                case 3:
                    hash = (((hash * 37) + 3) * 53) + getHealth().hashCode();
                    break;
                case 4:
                    hash = (((hash * 37) + 4) * 53) + getGnss().hashCode();
                    break;
                case 5:
                    hash = (((hash * 37) + 5) * 53) + getEcg().hashCode();
                    break;
                case 6:
                    hash = (((hash * 37) + 6) * 53) + getPpg().hashCode();
                    break;
                case 7:
                    hash = (((hash * 37) + 7) * 53) + getRri().hashCode();
                    break;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisData parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisData) PARSER.parseFrom(data);
        }

        public static HisData parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisData parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisData) PARSER.parseFrom(data);
        }

        public static HisData parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisData parseFrom(InputStream input) throws IOException {
            return (HisData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisData parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisData parseDelimitedFrom(InputStream input) throws IOException {
            return (HisData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisData parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisData parseFrom(CodedInputStream input) throws IOException {
            return (HisData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisData parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisData prototype) {
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

        public static HisData getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisData> parser() {
            return PARSER;
        }

        public Parser<HisData> getParserForType() {
            return PARSER;
        }

        public HisData getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum HisDataStatus implements ProtocolMessageEnum {
        STATUS_OK(0),
        STATUS_INNER_ERROR(1),
        STATUS_NV_ERROR(2),
        STATUS_CRC_ERROR(3),
        STATUS_TYPE_ERROR(4),
        STATUS_NOT_SUPPORTED(5);
        
        public static final int STATUS_CRC_ERROR_VALUE = 3;
        public static final int STATUS_INNER_ERROR_VALUE = 1;
        public static final int STATUS_NOT_SUPPORTED_VALUE = 5;
        public static final int STATUS_NV_ERROR_VALUE = 2;
        public static final int STATUS_OK_VALUE = 0;
        public static final int STATUS_TYPE_ERROR_VALUE = 4;
        private static final HisDataStatus[] VALUES = null;
        private static final EnumLiteMap<HisDataStatus> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<HisDataStatus>() {
                public HisDataStatus findValueByNumber(int number) {
                    return HisDataStatus.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static HisDataStatus valueOf(int value2) {
            return forNumber(value2);
        }

        public static HisDataStatus forNumber(int value2) {
            switch (value2) {
                case 0:
                    return STATUS_OK;
                case 1:
                    return STATUS_INNER_ERROR;
                case 2:
                    return STATUS_NV_ERROR;
                case 3:
                    return STATUS_CRC_ERROR;
                case 4:
                    return STATUS_TYPE_ERROR;
                case 5:
                    return STATUS_NOT_SUPPORTED;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<HisDataStatus> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) HisDataOuterClass.getDescriptor().getEnumTypes().get(2);
        }

        public static HisDataStatus valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private HisDataStatus(int value2) {
            this.value = value2;
        }
    }

    public enum HisDataType implements ProtocolMessageEnum {
        HEALTH_DATA(0),
        GNSS_DATA(1),
        ECG_DATA(2),
        PPG_DATA(3),
        RRI_DATA(4);
        
        public static final int ECG_DATA_VALUE = 2;
        public static final int GNSS_DATA_VALUE = 1;
        public static final int HEALTH_DATA_VALUE = 0;
        public static final int PPG_DATA_VALUE = 3;
        public static final int RRI_DATA_VALUE = 4;
        private static final HisDataType[] VALUES = null;
        private static final EnumLiteMap<HisDataType> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<HisDataType>() {
                public HisDataType findValueByNumber(int number) {
                    return HisDataType.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static HisDataType valueOf(int value2) {
            return forNumber(value2);
        }

        public static HisDataType forNumber(int value2) {
            switch (value2) {
                case 0:
                    return HEALTH_DATA;
                case 1:
                    return GNSS_DATA;
                case 2:
                    return ECG_DATA;
                case 3:
                    return PPG_DATA;
                case 4:
                    return RRI_DATA;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<HisDataType> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) HisDataOuterClass.getDescriptor().getEnumTypes().get(1);
        }

        public static HisDataType valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private HisDataType(int value2) {
            this.value = value2;
        }
    }

    public static final class HisITSync extends GeneratedMessageV3 implements HisITSyncOrBuilder {
        private static final HisITSync DEFAULT_INSTANCE = new HisITSync();
        @Deprecated
        public static final Parser<HisITSync> PARSER = new AbstractParser<HisITSync>() {
            public HisITSync parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisITSync(input, extensionRegistry);
            }
        };
        public static final int TYPE_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int type_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisITSyncOrBuilder {
            private int bitField0_;
            private int type_;

            public static final Descriptor getDescriptor() {
                return HisDataOuterClass.internal_static_HisITSync_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisDataOuterClass.internal_static_HisITSync_fieldAccessorTable.ensureFieldAccessorsInitialized(HisITSync.class, Builder.class);
            }

            private Builder() {
                this.type_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.type_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisITSync.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.type_ = 0;
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisDataOuterClass.internal_static_HisITSync_descriptor;
            }

            public HisITSync getDefaultInstanceForType() {
                return HisITSync.getDefaultInstance();
            }

            public HisITSync build() {
                HisITSync result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisITSync buildPartial() {
                HisITSync result = new HisITSync((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.type_ = this.type_;
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
                if (other instanceof HisITSync) {
                    return mergeFrom((HisITSync) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisITSync other) {
                if (other != HisITSync.getDefaultInstance()) {
                    if (other.hasType()) {
                        setType(other.getType());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasType()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisITSync parsedMessage = null;
                try {
                    HisITSync parsedMessage2 = (HisITSync) HisITSync.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisITSync parsedMessage3 = (HisITSync) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasType() {
                return (this.bitField0_ & 1) == 1;
            }

            public HisDataType getType() {
                HisDataType result = HisDataType.valueOf(this.type_);
                return result == null ? HisDataType.HEALTH_DATA : result;
            }

            public Builder setType(HisDataType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.type_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearType() {
                this.bitField0_ &= -2;
                this.type_ = 0;
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

        private HisITSync(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisITSync() {
            this.memoizedIsInitialized = -1;
            this.type_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisITSync(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            int rawValue = input.readEnum();
                            if (HisDataType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.type_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
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
            return HisDataOuterClass.internal_static_HisITSync_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisDataOuterClass.internal_static_HisITSync_fieldAccessorTable.ensureFieldAccessorsInitialized(HisITSync.class, Builder.class);
        }

        public boolean hasType() {
            return (this.bitField0_ & 1) == 1;
        }

        public HisDataType getType() {
            HisDataType result = HisDataType.valueOf(this.type_);
            return result == null ? HisDataType.HEALTH_DATA : result;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.type_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.type_);
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
            if (!(obj instanceof HisITSync)) {
                return super.equals(obj);
            }
            HisITSync other = (HisITSync) obj;
            boolean result2 = 1 != 0 && hasType() == other.hasType();
            if (hasType()) {
                result2 = result2 && this.type_ == other.type_;
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
            if (hasType()) {
                hash = (((hash * 37) + 1) * 53) + this.type_;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisITSync parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisITSync) PARSER.parseFrom(data);
        }

        public static HisITSync parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisITSync) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisITSync parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisITSync) PARSER.parseFrom(data);
        }

        public static HisITSync parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisITSync) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisITSync parseFrom(InputStream input) throws IOException {
            return (HisITSync) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisITSync parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisITSync) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisITSync parseDelimitedFrom(InputStream input) throws IOException {
            return (HisITSync) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisITSync parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisITSync) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisITSync parseFrom(CodedInputStream input) throws IOException {
            return (HisITSync) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisITSync parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisITSync) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisITSync prototype) {
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

        public static HisITSync getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisITSync> parser() {
            return PARSER;
        }

        public Parser<HisITSync> getParserForType() {
            return PARSER;
        }

        public HisITSync getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisIndex extends GeneratedMessageV3 implements HisIndexOrBuilder {
        private static final HisIndex DEFAULT_INSTANCE = new HisIndex();
        public static final int END_SEQ_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<HisIndex> PARSER = new AbstractParser<HisIndex>() {
            public HisIndex parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisIndex(input, extensionRegistry);
            }
        };
        public static final int START_SEQ_FIELD_NUMBER = 2;
        public static final int TIME_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int endSeq_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int startSeq_;
        /* access modifiers changed from: private */
        public RtTime time_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisIndexOrBuilder {
            private int bitField0_;
            private int endSeq_;
            private int startSeq_;
            private SingleFieldBuilderV3<RtTime, com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder, RtTimeOrBuilder> timeBuilder_;
            private RtTime time_;

            public static final Descriptor getDescriptor() {
                return HisDataOuterClass.internal_static_HisIndex_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisDataOuterClass.internal_static_HisIndex_fieldAccessorTable.ensureFieldAccessorsInitialized(HisIndex.class, Builder.class);
            }

            private Builder() {
                this.time_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.time_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisIndex.alwaysUseFieldBuilders) {
                    getTimeFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.timeBuilder_ == null) {
                    this.time_ = null;
                } else {
                    this.timeBuilder_.clear();
                }
                this.bitField0_ &= -2;
                this.startSeq_ = 0;
                this.bitField0_ &= -3;
                this.endSeq_ = 0;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisDataOuterClass.internal_static_HisIndex_descriptor;
            }

            public HisIndex getDefaultInstanceForType() {
                return HisIndex.getDefaultInstance();
            }

            public HisIndex build() {
                HisIndex result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisIndex buildPartial() {
                HisIndex result = new HisIndex((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.timeBuilder_ == null) {
                    result.time_ = this.time_;
                } else {
                    result.time_ = (RtTime) this.timeBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.startSeq_ = this.startSeq_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.endSeq_ = this.endSeq_;
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
                if (other instanceof HisIndex) {
                    return mergeFrom((HisIndex) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisIndex other) {
                if (other != HisIndex.getDefaultInstance()) {
                    if (other.hasTime()) {
                        mergeTime(other.getTime());
                    }
                    if (other.hasStartSeq()) {
                        setStartSeq(other.getStartSeq());
                    }
                    if (other.hasEndSeq()) {
                        setEndSeq(other.getEndSeq());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasTime() && hasStartSeq() && hasEndSeq() && getTime().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisIndex parsedMessage = null;
                try {
                    HisIndex parsedMessage2 = (HisIndex) HisIndex.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisIndex parsedMessage3 = (HisIndex) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasTime() {
                return (this.bitField0_ & 1) == 1;
            }

            public RtTime getTime() {
                if (this.timeBuilder_ == null) {
                    return this.time_ == null ? RtTime.getDefaultInstance() : this.time_;
                }
                return (RtTime) this.timeBuilder_.getMessage();
            }

            public Builder setTime(RtTime value) {
                if (this.timeBuilder_ != null) {
                    this.timeBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.time_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setTime(com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder builderForValue) {
                if (this.timeBuilder_ == null) {
                    this.time_ = builderForValue.build();
                    onChanged();
                } else {
                    this.timeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeTime(RtTime value) {
                if (this.timeBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.time_ == null || this.time_ == RtTime.getDefaultInstance()) {
                        this.time_ = value;
                    } else {
                        this.time_ = RtTime.newBuilder(this.time_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.timeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearTime() {
                if (this.timeBuilder_ == null) {
                    this.time_ = null;
                    onChanged();
                } else {
                    this.timeBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder getTimeBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder) getTimeFieldBuilder().getBuilder();
            }

            public RtTimeOrBuilder getTimeOrBuilder() {
                if (this.timeBuilder_ != null) {
                    return (RtTimeOrBuilder) this.timeBuilder_.getMessageOrBuilder();
                }
                return this.time_ == null ? RtTime.getDefaultInstance() : this.time_;
            }

            private SingleFieldBuilderV3<RtTime, com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder, RtTimeOrBuilder> getTimeFieldBuilder() {
                if (this.timeBuilder_ == null) {
                    this.timeBuilder_ = new SingleFieldBuilderV3<>(getTime(), getParentForChildren(), isClean());
                    this.time_ = null;
                }
                return this.timeBuilder_;
            }

            public boolean hasStartSeq() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getStartSeq() {
                return this.startSeq_;
            }

            public Builder setStartSeq(int value) {
                this.bitField0_ |= 2;
                this.startSeq_ = value;
                onChanged();
                return this;
            }

            public Builder clearStartSeq() {
                this.bitField0_ &= -3;
                this.startSeq_ = 0;
                onChanged();
                return this;
            }

            public boolean hasEndSeq() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getEndSeq() {
                return this.endSeq_;
            }

            public Builder setEndSeq(int value) {
                this.bitField0_ |= 4;
                this.endSeq_ = value;
                onChanged();
                return this;
            }

            public Builder clearEndSeq() {
                this.bitField0_ &= -5;
                this.endSeq_ = 0;
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

        private HisIndex(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisIndex() {
            this.memoizedIsInitialized = -1;
            this.startSeq_ = 0;
            this.endSeq_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisIndex(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            com.iwown.ble_module.proto.base.RealtimeData.RtTime.Builder subBuilder = null;
                            if ((this.bitField0_ & 1) == 1) {
                                subBuilder = this.time_.toBuilder();
                            }
                            this.time_ = (RtTime) input.readMessage(RtTime.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.time_);
                                this.time_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.startSeq_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.endSeq_ = input.readFixed32();
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
            return HisDataOuterClass.internal_static_HisIndex_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisDataOuterClass.internal_static_HisIndex_fieldAccessorTable.ensureFieldAccessorsInitialized(HisIndex.class, Builder.class);
        }

        public boolean hasTime() {
            return (this.bitField0_ & 1) == 1;
        }

        public RtTime getTime() {
            return this.time_ == null ? RtTime.getDefaultInstance() : this.time_;
        }

        public RtTimeOrBuilder getTimeOrBuilder() {
            return this.time_ == null ? RtTime.getDefaultInstance() : this.time_;
        }

        public boolean hasStartSeq() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getStartSeq() {
            return this.startSeq_;
        }

        public boolean hasEndSeq() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getEndSeq() {
            return this.endSeq_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasTime()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasStartSeq()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasEndSeq()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getTime().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getTime());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.startSeq_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.endSeq_);
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, getTime());
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.startSeq_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.endSeq_);
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
            if (!(obj instanceof HisIndex)) {
                return super.equals(obj);
            }
            HisIndex other = (HisIndex) obj;
            boolean result4 = 1 != 0 && hasTime() == other.hasTime();
            if (hasTime()) {
                result4 = result4 && getTime().equals(other.getTime());
            }
            if (!result4 || hasStartSeq() != other.hasStartSeq()) {
                result = false;
            } else {
                result = true;
            }
            if (hasStartSeq()) {
                if (!result || getStartSeq() != other.getStartSeq()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasEndSeq() != other.hasEndSeq()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasEndSeq()) {
                if (!result2 || getEndSeq() != other.getEndSeq()) {
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
            if (hasTime()) {
                hash = (((hash * 37) + 1) * 53) + getTime().hashCode();
            }
            if (hasStartSeq()) {
                hash = (((hash * 37) + 2) * 53) + getStartSeq();
            }
            if (hasEndSeq()) {
                hash = (((hash * 37) + 3) * 53) + getEndSeq();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisIndex parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisIndex) PARSER.parseFrom(data);
        }

        public static HisIndex parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisIndex) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisIndex parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisIndex) PARSER.parseFrom(data);
        }

        public static HisIndex parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisIndex) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisIndex parseFrom(InputStream input) throws IOException {
            return (HisIndex) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisIndex parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisIndex) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisIndex parseDelimitedFrom(InputStream input) throws IOException {
            return (HisIndex) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisIndex parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisIndex) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisIndex parseFrom(CodedInputStream input) throws IOException {
            return (HisIndex) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisIndex parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisIndex) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisIndex prototype) {
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

        public static HisIndex getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisIndex> parser() {
            return PARSER;
        }

        public Parser<HisIndex> getParserForType() {
            return PARSER;
        }

        public HisIndex getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisIndexTable extends GeneratedMessageV3 implements HisIndexTableOrBuilder {
        private static final HisIndexTable DEFAULT_INSTANCE = new HisIndexTable();
        public static final int INDEX_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<HisIndexTable> PARSER = new AbstractParser<HisIndexTable>() {
            public HisIndexTable parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisIndexTable(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public List<HisIndex> index_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisIndexTableOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilderV3<HisIndex, Builder, HisIndexOrBuilder> indexBuilder_;
            private List<HisIndex> index_;

            public static final Descriptor getDescriptor() {
                return HisDataOuterClass.internal_static_HisIndexTable_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisDataOuterClass.internal_static_HisIndexTable_fieldAccessorTable.ensureFieldAccessorsInitialized(HisIndexTable.class, Builder.class);
            }

            private Builder() {
                this.index_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.index_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisIndexTable.alwaysUseFieldBuilders) {
                    getIndexFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.indexBuilder_ == null) {
                    this.index_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                } else {
                    this.indexBuilder_.clear();
                }
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisDataOuterClass.internal_static_HisIndexTable_descriptor;
            }

            public HisIndexTable getDefaultInstanceForType() {
                return HisIndexTable.getDefaultInstance();
            }

            public HisIndexTable build() {
                HisIndexTable result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisIndexTable buildPartial() {
                HisIndexTable result = new HisIndexTable((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int i = this.bitField0_;
                if (this.indexBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.index_ = Collections.unmodifiableList(this.index_);
                        this.bitField0_ &= -2;
                    }
                    result.index_ = this.index_;
                } else {
                    result.index_ = this.indexBuilder_.build();
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
                if (other instanceof HisIndexTable) {
                    return mergeFrom((HisIndexTable) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisIndexTable other) {
                RepeatedFieldBuilderV3<HisIndex, Builder, HisIndexOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != HisIndexTable.getDefaultInstance()) {
                    if (this.indexBuilder_ == null) {
                        if (!other.index_.isEmpty()) {
                            if (this.index_.isEmpty()) {
                                this.index_ = other.index_;
                                this.bitField0_ &= -2;
                            } else {
                                ensureIndexIsMutable();
                                this.index_.addAll(other.index_);
                            }
                            onChanged();
                        }
                    } else if (!other.index_.isEmpty()) {
                        if (this.indexBuilder_.isEmpty()) {
                            this.indexBuilder_.dispose();
                            this.indexBuilder_ = null;
                            this.index_ = other.index_;
                            this.bitField0_ &= -2;
                            if (HisIndexTable.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getIndexFieldBuilder();
                            }
                            this.indexBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.indexBuilder_.addAllMessages(other.index_);
                        }
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getIndexCount(); i++) {
                    if (!getIndex(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisIndexTable parsedMessage = null;
                try {
                    HisIndexTable parsedMessage2 = (HisIndexTable) HisIndexTable.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisIndexTable parsedMessage3 = (HisIndexTable) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            private void ensureIndexIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.index_ = new ArrayList(this.index_);
                    this.bitField0_ |= 1;
                }
            }

            public List<HisIndex> getIndexList() {
                if (this.indexBuilder_ == null) {
                    return Collections.unmodifiableList(this.index_);
                }
                return this.indexBuilder_.getMessageList();
            }

            public int getIndexCount() {
                if (this.indexBuilder_ == null) {
                    return this.index_.size();
                }
                return this.indexBuilder_.getCount();
            }

            public HisIndex getIndex(int index) {
                if (this.indexBuilder_ == null) {
                    return (HisIndex) this.index_.get(index);
                }
                return (HisIndex) this.indexBuilder_.getMessage(index);
            }

            public Builder setIndex(int index, HisIndex value) {
                if (this.indexBuilder_ != null) {
                    this.indexBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureIndexIsMutable();
                    this.index_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setIndex(int index, Builder builderForValue) {
                if (this.indexBuilder_ == null) {
                    ensureIndexIsMutable();
                    this.index_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.indexBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addIndex(HisIndex value) {
                if (this.indexBuilder_ != null) {
                    this.indexBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureIndexIsMutable();
                    this.index_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addIndex(int index, HisIndex value) {
                if (this.indexBuilder_ != null) {
                    this.indexBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureIndexIsMutable();
                    this.index_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addIndex(Builder builderForValue) {
                if (this.indexBuilder_ == null) {
                    ensureIndexIsMutable();
                    this.index_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.indexBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addIndex(int index, Builder builderForValue) {
                if (this.indexBuilder_ == null) {
                    ensureIndexIsMutable();
                    this.index_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.indexBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllIndex(Iterable<? extends HisIndex> values) {
                if (this.indexBuilder_ == null) {
                    ensureIndexIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.index_);
                    onChanged();
                } else {
                    this.indexBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearIndex() {
                if (this.indexBuilder_ == null) {
                    this.index_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    onChanged();
                } else {
                    this.indexBuilder_.clear();
                }
                return this;
            }

            public Builder removeIndex(int index) {
                if (this.indexBuilder_ == null) {
                    ensureIndexIsMutable();
                    this.index_.remove(index);
                    onChanged();
                } else {
                    this.indexBuilder_.remove(index);
                }
                return this;
            }

            public Builder getIndexBuilder(int index) {
                return (Builder) getIndexFieldBuilder().getBuilder(index);
            }

            public HisIndexOrBuilder getIndexOrBuilder(int index) {
                if (this.indexBuilder_ == null) {
                    return (HisIndexOrBuilder) this.index_.get(index);
                }
                return (HisIndexOrBuilder) this.indexBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends HisIndexOrBuilder> getIndexOrBuilderList() {
                if (this.indexBuilder_ != null) {
                    return this.indexBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.index_);
            }

            public Builder addIndexBuilder() {
                return (Builder) getIndexFieldBuilder().addBuilder(HisIndex.getDefaultInstance());
            }

            public Builder addIndexBuilder(int index) {
                return (Builder) getIndexFieldBuilder().addBuilder(index, HisIndex.getDefaultInstance());
            }

            public List<Builder> getIndexBuilderList() {
                return getIndexFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<HisIndex, Builder, HisIndexOrBuilder> getIndexFieldBuilder() {
                boolean z = true;
                if (this.indexBuilder_ == null) {
                    List<HisIndex> list = this.index_;
                    if ((this.bitField0_ & 1) != 1) {
                        z = false;
                    }
                    this.indexBuilder_ = new RepeatedFieldBuilderV3<>(list, z, getParentForChildren(), isClean());
                    this.index_ = null;
                }
                return this.indexBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private HisIndexTable(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisIndexTable() {
            this.memoizedIsInitialized = -1;
            this.index_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisIndexTable(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.index_ = new ArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            this.index_.add(input.readMessage(HisIndex.PARSER, extensionRegistry));
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
                    if ((mutable_bitField0_ & 1) == 1) {
                        this.index_ = Collections.unmodifiableList(this.index_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 1) == 1) {
                this.index_ = Collections.unmodifiableList(this.index_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return HisDataOuterClass.internal_static_HisIndexTable_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisDataOuterClass.internal_static_HisIndexTable_fieldAccessorTable.ensureFieldAccessorsInitialized(HisIndexTable.class, Builder.class);
        }

        public List<HisIndex> getIndexList() {
            return this.index_;
        }

        public List<? extends HisIndexOrBuilder> getIndexOrBuilderList() {
            return this.index_;
        }

        public int getIndexCount() {
            return this.index_.size();
        }

        public HisIndex getIndex(int index) {
            return (HisIndex) this.index_.get(index);
        }

        public HisIndexOrBuilder getIndexOrBuilder(int index) {
            return (HisIndexOrBuilder) this.index_.get(index);
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < getIndexCount(); i++) {
                if (!getIndex(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            for (int i = 0; i < this.index_.size(); i++) {
                output.writeMessage(1, (MessageLite) this.index_.get(i));
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            for (int i = 0; i < this.index_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(1, (MessageLite) this.index_.get(i));
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
            if (!(obj instanceof HisIndexTable)) {
                return super.equals(obj);
            }
            HisIndexTable other = (HisIndexTable) obj;
            if (1 == 0 || !getIndexList().equals(other.getIndexList())) {
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
            if (getIndexCount() > 0) {
                hash = (((hash * 37) + 1) * 53) + getIndexList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisIndexTable parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisIndexTable) PARSER.parseFrom(data);
        }

        public static HisIndexTable parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisIndexTable) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisIndexTable parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisIndexTable) PARSER.parseFrom(data);
        }

        public static HisIndexTable parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisIndexTable) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisIndexTable parseFrom(InputStream input) throws IOException {
            return (HisIndexTable) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisIndexTable parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisIndexTable) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisIndexTable parseDelimitedFrom(InputStream input) throws IOException {
            return (HisIndexTable) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisIndexTable parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisIndexTable) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisIndexTable parseFrom(CodedInputStream input) throws IOException {
            return (HisIndexTable) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisIndexTable parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisIndexTable) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisIndexTable prototype) {
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

        public static HisIndexTable getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisIndexTable> parser() {
            return PARSER;
        }

        public Parser<HisIndexTable> getParserForType() {
            return PARSER;
        }

        public HisIndexTable getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisNotification extends GeneratedMessageV3 implements HisNotificationOrBuilder {
        public static final int CONFIRM_FIELD_NUMBER = 2;
        private static final HisNotification DEFAULT_INSTANCE = new HisNotification();
        public static final int HIS_DATA_FIELD_NUMBER = 4;
        public static final int INDEX_TABLE_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<HisNotification> PARSER = new AbstractParser<HisNotification>() {
            public HisNotification parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisNotification(input, extensionRegistry);
            }
        };
        public static final int TYPE_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int dataCase_;
        /* access modifiers changed from: private */
        public Object data_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int type_;

        public enum DataCase implements EnumLite {
            CONFIRM(2),
            INDEX_TABLE(3),
            HIS_DATA(4),
            DATA_NOT_SET(0);
            
            private final int value;

            private DataCase(int value2) {
                this.value = value2;
            }

            @Deprecated
            public static DataCase valueOf(int value2) {
                return forNumber(value2);
            }

            public static DataCase forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return DATA_NOT_SET;
                    case 2:
                        return CONFIRM;
                    case 3:
                        return INDEX_TABLE;
                    case 4:
                        return HIS_DATA;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisNotificationOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<HisConfirm, Builder, HisConfirmOrBuilder> confirmBuilder_;
            private int dataCase_;
            private Object data_;
            private SingleFieldBuilderV3<HisData, Builder, HisDataOrBuilder> hisDataBuilder_;
            private SingleFieldBuilderV3<HisIndexTable, Builder, HisIndexTableOrBuilder> indexTableBuilder_;
            private int type_;

            public static final Descriptor getDescriptor() {
                return HisDataOuterClass.internal_static_HisNotification_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisDataOuterClass.internal_static_HisNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(HisNotification.class, Builder.class);
            }

            private Builder() {
                this.dataCase_ = 0;
                this.type_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.dataCase_ = 0;
                this.type_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisNotification.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.type_ = 0;
                this.bitField0_ &= -2;
                this.dataCase_ = 0;
                this.data_ = null;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisDataOuterClass.internal_static_HisNotification_descriptor;
            }

            public HisNotification getDefaultInstanceForType() {
                return HisNotification.getDefaultInstance();
            }

            public HisNotification build() {
                HisNotification result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisNotification buildPartial() {
                HisNotification result = new HisNotification((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.type_ = this.type_;
                if (this.dataCase_ == 2) {
                    if (this.confirmBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.confirmBuilder_.build();
                    }
                }
                if (this.dataCase_ == 3) {
                    if (this.indexTableBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.indexTableBuilder_.build();
                    }
                }
                if (this.dataCase_ == 4) {
                    if (this.hisDataBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.hisDataBuilder_.build();
                    }
                }
                result.bitField0_ = to_bitField0_;
                result.dataCase_ = this.dataCase_;
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
                if (other instanceof HisNotification) {
                    return mergeFrom((HisNotification) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisNotification other) {
                if (other != HisNotification.getDefaultInstance()) {
                    if (other.hasType()) {
                        setType(other.getType());
                    }
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisNotification$DataCase[other.getDataCase().ordinal()]) {
                        case 1:
                            mergeConfirm(other.getConfirm());
                            break;
                        case 2:
                            mergeIndexTable(other.getIndexTable());
                            break;
                        case 3:
                            mergeHisData(other.getHisData());
                            break;
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasType()) {
                    return false;
                }
                if (hasConfirm() && !getConfirm().isInitialized()) {
                    return false;
                }
                if (hasIndexTable() && !getIndexTable().isInitialized()) {
                    return false;
                }
                if (!hasHisData() || getHisData().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisNotification parsedMessage = null;
                try {
                    HisNotification parsedMessage2 = (HisNotification) HisNotification.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisNotification parsedMessage3 = (HisNotification) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public DataCase getDataCase() {
                return DataCase.forNumber(this.dataCase_);
            }

            public Builder clearData() {
                this.dataCase_ = 0;
                this.data_ = null;
                onChanged();
                return this;
            }

            public boolean hasType() {
                return (this.bitField0_ & 1) == 1;
            }

            public HisDataType getType() {
                HisDataType result = HisDataType.valueOf(this.type_);
                return result == null ? HisDataType.HEALTH_DATA : result;
            }

            public Builder setType(HisDataType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.type_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearType() {
                this.bitField0_ &= -2;
                this.type_ = 0;
                onChanged();
                return this;
            }

            public boolean hasConfirm() {
                return this.dataCase_ == 2;
            }

            public HisConfirm getConfirm() {
                if (this.confirmBuilder_ == null) {
                    if (this.dataCase_ == 2) {
                        return (HisConfirm) this.data_;
                    }
                    return HisConfirm.getDefaultInstance();
                } else if (this.dataCase_ == 2) {
                    return (HisConfirm) this.confirmBuilder_.getMessage();
                } else {
                    return HisConfirm.getDefaultInstance();
                }
            }

            public Builder setConfirm(HisConfirm value) {
                if (this.confirmBuilder_ != null) {
                    this.confirmBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder setConfirm(Builder builderForValue) {
                if (this.confirmBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.confirmBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder mergeConfirm(HisConfirm value) {
                if (this.confirmBuilder_ == null) {
                    if (this.dataCase_ != 2 || this.data_ == HisConfirm.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = HisConfirm.newBuilder((HisConfirm) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 2) {
                        this.confirmBuilder_.mergeFrom(value);
                    }
                    this.confirmBuilder_.setMessage(value);
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder clearConfirm() {
                if (this.confirmBuilder_ != null) {
                    if (this.dataCase_ == 2) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.confirmBuilder_.clear();
                } else if (this.dataCase_ == 2) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getConfirmBuilder() {
                return (Builder) getConfirmFieldBuilder().getBuilder();
            }

            public HisConfirmOrBuilder getConfirmOrBuilder() {
                if (this.dataCase_ == 2 && this.confirmBuilder_ != null) {
                    return (HisConfirmOrBuilder) this.confirmBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 2) {
                    return (HisConfirm) this.data_;
                }
                return HisConfirm.getDefaultInstance();
            }

            private SingleFieldBuilderV3<HisConfirm, Builder, HisConfirmOrBuilder> getConfirmFieldBuilder() {
                if (this.confirmBuilder_ == null) {
                    if (this.dataCase_ != 2) {
                        this.data_ = HisConfirm.getDefaultInstance();
                    }
                    this.confirmBuilder_ = new SingleFieldBuilderV3<>((HisConfirm) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 2;
                onChanged();
                return this.confirmBuilder_;
            }

            public boolean hasIndexTable() {
                return this.dataCase_ == 3;
            }

            public HisIndexTable getIndexTable() {
                if (this.indexTableBuilder_ == null) {
                    if (this.dataCase_ == 3) {
                        return (HisIndexTable) this.data_;
                    }
                    return HisIndexTable.getDefaultInstance();
                } else if (this.dataCase_ == 3) {
                    return (HisIndexTable) this.indexTableBuilder_.getMessage();
                } else {
                    return HisIndexTable.getDefaultInstance();
                }
            }

            public Builder setIndexTable(HisIndexTable value) {
                if (this.indexTableBuilder_ != null) {
                    this.indexTableBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder setIndexTable(Builder builderForValue) {
                if (this.indexTableBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.indexTableBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder mergeIndexTable(HisIndexTable value) {
                if (this.indexTableBuilder_ == null) {
                    if (this.dataCase_ != 3 || this.data_ == HisIndexTable.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = HisIndexTable.newBuilder((HisIndexTable) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 3) {
                        this.indexTableBuilder_.mergeFrom(value);
                    }
                    this.indexTableBuilder_.setMessage(value);
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder clearIndexTable() {
                if (this.indexTableBuilder_ != null) {
                    if (this.dataCase_ == 3) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.indexTableBuilder_.clear();
                } else if (this.dataCase_ == 3) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getIndexTableBuilder() {
                return (Builder) getIndexTableFieldBuilder().getBuilder();
            }

            public HisIndexTableOrBuilder getIndexTableOrBuilder() {
                if (this.dataCase_ == 3 && this.indexTableBuilder_ != null) {
                    return (HisIndexTableOrBuilder) this.indexTableBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 3) {
                    return (HisIndexTable) this.data_;
                }
                return HisIndexTable.getDefaultInstance();
            }

            private SingleFieldBuilderV3<HisIndexTable, Builder, HisIndexTableOrBuilder> getIndexTableFieldBuilder() {
                if (this.indexTableBuilder_ == null) {
                    if (this.dataCase_ != 3) {
                        this.data_ = HisIndexTable.getDefaultInstance();
                    }
                    this.indexTableBuilder_ = new SingleFieldBuilderV3<>((HisIndexTable) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 3;
                onChanged();
                return this.indexTableBuilder_;
            }

            public boolean hasHisData() {
                return this.dataCase_ == 4;
            }

            public HisData getHisData() {
                if (this.hisDataBuilder_ == null) {
                    if (this.dataCase_ == 4) {
                        return (HisData) this.data_;
                    }
                    return HisData.getDefaultInstance();
                } else if (this.dataCase_ == 4) {
                    return (HisData) this.hisDataBuilder_.getMessage();
                } else {
                    return HisData.getDefaultInstance();
                }
            }

            public Builder setHisData(HisData value) {
                if (this.hisDataBuilder_ != null) {
                    this.hisDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 4;
                return this;
            }

            public Builder setHisData(Builder builderForValue) {
                if (this.hisDataBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.hisDataBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 4;
                return this;
            }

            public Builder mergeHisData(HisData value) {
                if (this.hisDataBuilder_ == null) {
                    if (this.dataCase_ != 4 || this.data_ == HisData.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = HisData.newBuilder((HisData) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 4) {
                        this.hisDataBuilder_.mergeFrom(value);
                    }
                    this.hisDataBuilder_.setMessage(value);
                }
                this.dataCase_ = 4;
                return this;
            }

            public Builder clearHisData() {
                if (this.hisDataBuilder_ != null) {
                    if (this.dataCase_ == 4) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.hisDataBuilder_.clear();
                } else if (this.dataCase_ == 4) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getHisDataBuilder() {
                return (Builder) getHisDataFieldBuilder().getBuilder();
            }

            public HisDataOrBuilder getHisDataOrBuilder() {
                if (this.dataCase_ == 4 && this.hisDataBuilder_ != null) {
                    return (HisDataOrBuilder) this.hisDataBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 4) {
                    return (HisData) this.data_;
                }
                return HisData.getDefaultInstance();
            }

            private SingleFieldBuilderV3<HisData, Builder, HisDataOrBuilder> getHisDataFieldBuilder() {
                if (this.hisDataBuilder_ == null) {
                    if (this.dataCase_ != 4) {
                        this.data_ = HisData.getDefaultInstance();
                    }
                    this.hisDataBuilder_ = new SingleFieldBuilderV3<>((HisData) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 4;
                onChanged();
                return this.hisDataBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private HisNotification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private HisNotification() {
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
            this.type_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisNotification(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            int rawValue = input.readEnum();
                            if (HisDataType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.type_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 18:
                            Builder subBuilder = null;
                            if (this.dataCase_ == 2) {
                                subBuilder = ((HisConfirm) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(HisConfirm.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((HisConfirm) this.data_);
                                this.data_ = subBuilder.buildPartial();
                            }
                            this.dataCase_ = 2;
                            break;
                        case 26:
                            Builder subBuilder2 = null;
                            if (this.dataCase_ == 3) {
                                subBuilder2 = ((HisIndexTable) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(HisIndexTable.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((HisIndexTable) this.data_);
                                this.data_ = subBuilder2.buildPartial();
                            }
                            this.dataCase_ = 3;
                            break;
                        case 34:
                            Builder subBuilder3 = null;
                            if (this.dataCase_ == 4) {
                                subBuilder3 = ((HisData) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(HisData.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom((HisData) this.data_);
                                this.data_ = subBuilder3.buildPartial();
                            }
                            this.dataCase_ = 4;
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
            return HisDataOuterClass.internal_static_HisNotification_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisDataOuterClass.internal_static_HisNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(HisNotification.class, Builder.class);
        }

        public DataCase getDataCase() {
            return DataCase.forNumber(this.dataCase_);
        }

        public boolean hasType() {
            return (this.bitField0_ & 1) == 1;
        }

        public HisDataType getType() {
            HisDataType result = HisDataType.valueOf(this.type_);
            return result == null ? HisDataType.HEALTH_DATA : result;
        }

        public boolean hasConfirm() {
            return this.dataCase_ == 2;
        }

        public HisConfirm getConfirm() {
            if (this.dataCase_ == 2) {
                return (HisConfirm) this.data_;
            }
            return HisConfirm.getDefaultInstance();
        }

        public HisConfirmOrBuilder getConfirmOrBuilder() {
            if (this.dataCase_ == 2) {
                return (HisConfirm) this.data_;
            }
            return HisConfirm.getDefaultInstance();
        }

        public boolean hasIndexTable() {
            return this.dataCase_ == 3;
        }

        public HisIndexTable getIndexTable() {
            if (this.dataCase_ == 3) {
                return (HisIndexTable) this.data_;
            }
            return HisIndexTable.getDefaultInstance();
        }

        public HisIndexTableOrBuilder getIndexTableOrBuilder() {
            if (this.dataCase_ == 3) {
                return (HisIndexTable) this.data_;
            }
            return HisIndexTable.getDefaultInstance();
        }

        public boolean hasHisData() {
            return this.dataCase_ == 4;
        }

        public HisData getHisData() {
            if (this.dataCase_ == 4) {
                return (HisData) this.data_;
            }
            return HisData.getDefaultInstance();
        }

        public HisDataOrBuilder getHisDataOrBuilder() {
            if (this.dataCase_ == 4) {
                return (HisData) this.data_;
            }
            return HisData.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasConfirm() && !getConfirm().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasIndexTable() && !getIndexTable().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasHisData() || getHisData().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.type_);
            }
            if (this.dataCase_ == 2) {
                output.writeMessage(2, (HisConfirm) this.data_);
            }
            if (this.dataCase_ == 3) {
                output.writeMessage(3, (HisIndexTable) this.data_);
            }
            if (this.dataCase_ == 4) {
                output.writeMessage(4, (HisData) this.data_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.type_);
            }
            if (this.dataCase_ == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, (HisConfirm) this.data_);
            }
            if (this.dataCase_ == 3) {
                size2 += CodedOutputStream.computeMessageSize(3, (HisIndexTable) this.data_);
            }
            if (this.dataCase_ == 4) {
                size2 += CodedOutputStream.computeMessageSize(4, (HisData) this.data_);
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
            if (!(obj instanceof HisNotification)) {
                return super.equals(obj);
            }
            HisNotification other = (HisNotification) obj;
            boolean result3 = 1 != 0 && hasType() == other.hasType();
            if (hasType()) {
                result3 = result3 && this.type_ == other.type_;
            }
            if (!result3 || !getDataCase().equals(other.getDataCase())) {
                result = false;
            } else {
                result = true;
            }
            if (!result) {
                return false;
            }
            switch (this.dataCase_) {
                case 2:
                    if (result && getConfirm().equals(other.getConfirm())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 3:
                    if (result && getIndexTable().equals(other.getIndexTable())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 4:
                    if (result && getHisData().equals(other.getHisData())) {
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
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasType()) {
                hash = (((hash * 37) + 1) * 53) + this.type_;
            }
            switch (this.dataCase_) {
                case 2:
                    hash = (((hash * 37) + 2) * 53) + getConfirm().hashCode();
                    break;
                case 3:
                    hash = (((hash * 37) + 3) * 53) + getIndexTable().hashCode();
                    break;
                case 4:
                    hash = (((hash * 37) + 4) * 53) + getHisData().hashCode();
                    break;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisNotification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisNotification) PARSER.parseFrom(data);
        }

        public static HisNotification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisNotification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisNotification) PARSER.parseFrom(data);
        }

        public static HisNotification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisNotification parseFrom(InputStream input) throws IOException {
            return (HisNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisNotification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisNotification parseDelimitedFrom(InputStream input) throws IOException {
            return (HisNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisNotification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisNotification parseFrom(CodedInputStream input) throws IOException {
            return (HisNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisNotification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisNotification prototype) {
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

        public static HisNotification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisNotification> parser() {
            return PARSER;
        }

        public Parser<HisNotification> getParserForType() {
            return PARSER;
        }

        public HisNotification getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum HisOperation implements ProtocolMessageEnum {
        IT_SYNC(0),
        START_SYNC(1),
        STOP_SYNC(2);
        
        public static final int IT_SYNC_VALUE = 0;
        public static final int START_SYNC_VALUE = 1;
        public static final int STOP_SYNC_VALUE = 2;
        private static final HisOperation[] VALUES = null;
        private static final EnumLiteMap<HisOperation> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<HisOperation>() {
                public HisOperation findValueByNumber(int number) {
                    return HisOperation.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static HisOperation valueOf(int value2) {
            return forNumber(value2);
        }

        public static HisOperation forNumber(int value2) {
            switch (value2) {
                case 0:
                    return IT_SYNC;
                case 1:
                    return START_SYNC;
                case 2:
                    return STOP_SYNC;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<HisOperation> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) HisDataOuterClass.getDescriptor().getEnumTypes().get(0);
        }

        public static HisOperation valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private HisOperation(int value2) {
            this.value = value2;
        }
    }

    public static final class HisStartSync extends GeneratedMessageV3 implements HisStartSyncOrBuilder {
        public static final int BLOCK_FIELD_NUMBER = 2;
        private static final HisStartSync DEFAULT_INSTANCE = new HisStartSync();
        @Deprecated
        public static final Parser<HisStartSync> PARSER = new AbstractParser<HisStartSync>() {
            public HisStartSync parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisStartSync(input, extensionRegistry);
            }
        };
        public static final int TYPE_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public List<HisBlock> block_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int type_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisStartSyncOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilderV3<HisBlock, Builder, HisBlockOrBuilder> blockBuilder_;
            private List<HisBlock> block_;
            private int type_;

            public static final Descriptor getDescriptor() {
                return HisDataOuterClass.internal_static_HisStartSync_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisDataOuterClass.internal_static_HisStartSync_fieldAccessorTable.ensureFieldAccessorsInitialized(HisStartSync.class, Builder.class);
            }

            private Builder() {
                this.type_ = 0;
                this.block_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.type_ = 0;
                this.block_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisStartSync.alwaysUseFieldBuilders) {
                    getBlockFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.type_ = 0;
                this.bitField0_ &= -2;
                if (this.blockBuilder_ == null) {
                    this.block_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.blockBuilder_.clear();
                }
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisDataOuterClass.internal_static_HisStartSync_descriptor;
            }

            public HisStartSync getDefaultInstanceForType() {
                return HisStartSync.getDefaultInstance();
            }

            public HisStartSync build() {
                HisStartSync result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisStartSync buildPartial() {
                HisStartSync result = new HisStartSync((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.type_ = this.type_;
                if (this.blockBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.block_ = Collections.unmodifiableList(this.block_);
                        this.bitField0_ &= -3;
                    }
                    result.block_ = this.block_;
                } else {
                    result.block_ = this.blockBuilder_.build();
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
                if (other instanceof HisStartSync) {
                    return mergeFrom((HisStartSync) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisStartSync other) {
                RepeatedFieldBuilderV3<HisBlock, Builder, HisBlockOrBuilder> repeatedFieldBuilderV3 = null;
                if (other != HisStartSync.getDefaultInstance()) {
                    if (other.hasType()) {
                        setType(other.getType());
                    }
                    if (this.blockBuilder_ == null) {
                        if (!other.block_.isEmpty()) {
                            if (this.block_.isEmpty()) {
                                this.block_ = other.block_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureBlockIsMutable();
                                this.block_.addAll(other.block_);
                            }
                            onChanged();
                        }
                    } else if (!other.block_.isEmpty()) {
                        if (this.blockBuilder_.isEmpty()) {
                            this.blockBuilder_.dispose();
                            this.blockBuilder_ = null;
                            this.block_ = other.block_;
                            this.bitField0_ &= -3;
                            if (HisStartSync.alwaysUseFieldBuilders) {
                                repeatedFieldBuilderV3 = getBlockFieldBuilder();
                            }
                            this.blockBuilder_ = repeatedFieldBuilderV3;
                        } else {
                            this.blockBuilder_.addAllMessages(other.block_);
                        }
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasType()) {
                    return false;
                }
                for (int i = 0; i < getBlockCount(); i++) {
                    if (!getBlock(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisStartSync parsedMessage = null;
                try {
                    HisStartSync parsedMessage2 = (HisStartSync) HisStartSync.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisStartSync parsedMessage3 = (HisStartSync) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasType() {
                return (this.bitField0_ & 1) == 1;
            }

            public HisDataType getType() {
                HisDataType result = HisDataType.valueOf(this.type_);
                return result == null ? HisDataType.HEALTH_DATA : result;
            }

            public Builder setType(HisDataType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.type_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearType() {
                this.bitField0_ &= -2;
                this.type_ = 0;
                onChanged();
                return this;
            }

            private void ensureBlockIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.block_ = new ArrayList(this.block_);
                    this.bitField0_ |= 2;
                }
            }

            public List<HisBlock> getBlockList() {
                if (this.blockBuilder_ == null) {
                    return Collections.unmodifiableList(this.block_);
                }
                return this.blockBuilder_.getMessageList();
            }

            public int getBlockCount() {
                if (this.blockBuilder_ == null) {
                    return this.block_.size();
                }
                return this.blockBuilder_.getCount();
            }

            public HisBlock getBlock(int index) {
                if (this.blockBuilder_ == null) {
                    return (HisBlock) this.block_.get(index);
                }
                return (HisBlock) this.blockBuilder_.getMessage(index);
            }

            public Builder setBlock(int index, HisBlock value) {
                if (this.blockBuilder_ != null) {
                    this.blockBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureBlockIsMutable();
                    this.block_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setBlock(int index, Builder builderForValue) {
                if (this.blockBuilder_ == null) {
                    ensureBlockIsMutable();
                    this.block_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.blockBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addBlock(HisBlock value) {
                if (this.blockBuilder_ != null) {
                    this.blockBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureBlockIsMutable();
                    this.block_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addBlock(int index, HisBlock value) {
                if (this.blockBuilder_ != null) {
                    this.blockBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureBlockIsMutable();
                    this.block_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addBlock(Builder builderForValue) {
                if (this.blockBuilder_ == null) {
                    ensureBlockIsMutable();
                    this.block_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.blockBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addBlock(int index, Builder builderForValue) {
                if (this.blockBuilder_ == null) {
                    ensureBlockIsMutable();
                    this.block_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.blockBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllBlock(Iterable<? extends HisBlock> values) {
                if (this.blockBuilder_ == null) {
                    ensureBlockIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.block_);
                    onChanged();
                } else {
                    this.blockBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearBlock() {
                if (this.blockBuilder_ == null) {
                    this.block_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.blockBuilder_.clear();
                }
                return this;
            }

            public Builder removeBlock(int index) {
                if (this.blockBuilder_ == null) {
                    ensureBlockIsMutable();
                    this.block_.remove(index);
                    onChanged();
                } else {
                    this.blockBuilder_.remove(index);
                }
                return this;
            }

            public Builder getBlockBuilder(int index) {
                return (Builder) getBlockFieldBuilder().getBuilder(index);
            }

            public HisBlockOrBuilder getBlockOrBuilder(int index) {
                if (this.blockBuilder_ == null) {
                    return (HisBlockOrBuilder) this.block_.get(index);
                }
                return (HisBlockOrBuilder) this.blockBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends HisBlockOrBuilder> getBlockOrBuilderList() {
                if (this.blockBuilder_ != null) {
                    return this.blockBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.block_);
            }

            public Builder addBlockBuilder() {
                return (Builder) getBlockFieldBuilder().addBuilder(HisBlock.getDefaultInstance());
            }

            public Builder addBlockBuilder(int index) {
                return (Builder) getBlockFieldBuilder().addBuilder(index, HisBlock.getDefaultInstance());
            }

            public List<Builder> getBlockBuilderList() {
                return getBlockFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<HisBlock, Builder, HisBlockOrBuilder> getBlockFieldBuilder() {
                if (this.blockBuilder_ == null) {
                    this.blockBuilder_ = new RepeatedFieldBuilderV3<>(this.block_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.block_ = null;
                }
                return this.blockBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private HisStartSync(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisStartSync() {
            this.memoizedIsInitialized = -1;
            this.type_ = 0;
            this.block_ = Collections.emptyList();
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisStartSync(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 8:
                            int rawValue = input.readEnum();
                            if (HisDataType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.type_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 18:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.block_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.block_.add(input.readMessage(HisBlock.PARSER, extensionRegistry));
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
                        this.block_ = Collections.unmodifiableList(this.block_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.block_ = Collections.unmodifiableList(this.block_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return HisDataOuterClass.internal_static_HisStartSync_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisDataOuterClass.internal_static_HisStartSync_fieldAccessorTable.ensureFieldAccessorsInitialized(HisStartSync.class, Builder.class);
        }

        public boolean hasType() {
            return (this.bitField0_ & 1) == 1;
        }

        public HisDataType getType() {
            HisDataType result = HisDataType.valueOf(this.type_);
            return result == null ? HisDataType.HEALTH_DATA : result;
        }

        public List<HisBlock> getBlockList() {
            return this.block_;
        }

        public List<? extends HisBlockOrBuilder> getBlockOrBuilderList() {
            return this.block_;
        }

        public int getBlockCount() {
            return this.block_.size();
        }

        public HisBlock getBlock(int index) {
            return (HisBlock) this.block_.get(index);
        }

        public HisBlockOrBuilder getBlockOrBuilder(int index) {
            return (HisBlockOrBuilder) this.block_.get(index);
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < getBlockCount(); i++) {
                if (!getBlock(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.type_);
            }
            for (int i = 0; i < this.block_.size(); i++) {
                output.writeMessage(2, (MessageLite) this.block_.get(i));
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.type_);
            }
            for (int i = 0; i < this.block_.size(); i++) {
                size2 += CodedOutputStream.computeMessageSize(2, (MessageLite) this.block_.get(i));
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
            if (!(obj instanceof HisStartSync)) {
                return super.equals(obj);
            }
            HisStartSync other = (HisStartSync) obj;
            boolean result3 = 1 != 0 && hasType() == other.hasType();
            if (hasType()) {
                result3 = result3 && this.type_ == other.type_;
            }
            if (!result3 || !getBlockList().equals(other.getBlockList())) {
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
            if (hasType()) {
                hash = (((hash * 37) + 1) * 53) + this.type_;
            }
            if (getBlockCount() > 0) {
                hash = (((hash * 37) + 2) * 53) + getBlockList().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisStartSync parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisStartSync) PARSER.parseFrom(data);
        }

        public static HisStartSync parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisStartSync) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisStartSync parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisStartSync) PARSER.parseFrom(data);
        }

        public static HisStartSync parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisStartSync) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisStartSync parseFrom(InputStream input) throws IOException {
            return (HisStartSync) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisStartSync parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisStartSync) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisStartSync parseDelimitedFrom(InputStream input) throws IOException {
            return (HisStartSync) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisStartSync parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisStartSync) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisStartSync parseFrom(CodedInputStream input) throws IOException {
            return (HisStartSync) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisStartSync parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisStartSync) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisStartSync prototype) {
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

        public static HisStartSync getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisStartSync> parser() {
            return PARSER;
        }

        public Parser<HisStartSync> getParserForType() {
            return PARSER;
        }

        public HisStartSync getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisStopSync extends GeneratedMessageV3 implements HisStopSyncOrBuilder {
        private static final HisStopSync DEFAULT_INSTANCE = new HisStopSync();
        @Deprecated
        public static final Parser<HisStopSync> PARSER = new AbstractParser<HisStopSync>() {
            public HisStopSync parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisStopSync(input, extensionRegistry);
            }
        };
        public static final int TYPE_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int type_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisStopSyncOrBuilder {
            private int bitField0_;
            private int type_;

            public static final Descriptor getDescriptor() {
                return HisDataOuterClass.internal_static_HisStopSync_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisDataOuterClass.internal_static_HisStopSync_fieldAccessorTable.ensureFieldAccessorsInitialized(HisStopSync.class, Builder.class);
            }

            private Builder() {
                this.type_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.type_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisStopSync.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.type_ = 0;
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisDataOuterClass.internal_static_HisStopSync_descriptor;
            }

            public HisStopSync getDefaultInstanceForType() {
                return HisStopSync.getDefaultInstance();
            }

            public HisStopSync build() {
                HisStopSync result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisStopSync buildPartial() {
                HisStopSync result = new HisStopSync((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.type_ = this.type_;
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
                if (other instanceof HisStopSync) {
                    return mergeFrom((HisStopSync) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisStopSync other) {
                if (other != HisStopSync.getDefaultInstance()) {
                    if (other.hasType()) {
                        setType(other.getType());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasType()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisStopSync parsedMessage = null;
                try {
                    HisStopSync parsedMessage2 = (HisStopSync) HisStopSync.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisStopSync parsedMessage3 = (HisStopSync) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasType() {
                return (this.bitField0_ & 1) == 1;
            }

            public HisDataType getType() {
                HisDataType result = HisDataType.valueOf(this.type_);
                return result == null ? HisDataType.HEALTH_DATA : result;
            }

            public Builder setType(HisDataType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.type_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearType() {
                this.bitField0_ &= -2;
                this.type_ = 0;
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

        private HisStopSync(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisStopSync() {
            this.memoizedIsInitialized = -1;
            this.type_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisStopSync(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            int rawValue = input.readEnum();
                            if (HisDataType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.type_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
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
            return HisDataOuterClass.internal_static_HisStopSync_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisDataOuterClass.internal_static_HisStopSync_fieldAccessorTable.ensureFieldAccessorsInitialized(HisStopSync.class, Builder.class);
        }

        public boolean hasType() {
            return (this.bitField0_ & 1) == 1;
        }

        public HisDataType getType() {
            HisDataType result = HisDataType.valueOf(this.type_);
            return result == null ? HisDataType.HEALTH_DATA : result;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.type_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.type_);
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
            if (!(obj instanceof HisStopSync)) {
                return super.equals(obj);
            }
            HisStopSync other = (HisStopSync) obj;
            boolean result2 = 1 != 0 && hasType() == other.hasType();
            if (hasType()) {
                result2 = result2 && this.type_ == other.type_;
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
            if (hasType()) {
                hash = (((hash * 37) + 1) * 53) + this.type_;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisStopSync parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisStopSync) PARSER.parseFrom(data);
        }

        public static HisStopSync parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisStopSync) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisStopSync parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisStopSync) PARSER.parseFrom(data);
        }

        public static HisStopSync parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisStopSync) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisStopSync parseFrom(InputStream input) throws IOException {
            return (HisStopSync) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisStopSync parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisStopSync) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisStopSync parseDelimitedFrom(InputStream input) throws IOException {
            return (HisStopSync) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisStopSync parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisStopSync) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisStopSync parseFrom(CodedInputStream input) throws IOException {
            return (HisStopSync) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisStopSync parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisStopSync) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisStopSync prototype) {
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

        public static HisStopSync getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisStopSync> parser() {
            return PARSER;
        }

        public Parser<HisStopSync> getParserForType() {
            return PARSER;
        }

        public HisStopSync getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisSubscriber extends GeneratedMessageV3 implements HisSubscriberOrBuilder {
        private static final HisSubscriber DEFAULT_INSTANCE = new HisSubscriber();
        public static final int IT_SYNC_FIELD_NUMBER = 2;
        public static final int OPERATION_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<HisSubscriber> PARSER = new AbstractParser<HisSubscriber>() {
            public HisSubscriber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisSubscriber(input, extensionRegistry);
            }
        };
        public static final int START_SYNC_FIELD_NUMBER = 3;
        public static final int STOP_SYNC_FIELD_NUMBER = 4;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int dataCase_;
        /* access modifiers changed from: private */
        public Object data_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int operation_;

        public enum DataCase implements EnumLite {
            IT_SYNC(2),
            START_SYNC(3),
            STOP_SYNC(4),
            DATA_NOT_SET(0);
            
            private final int value;

            private DataCase(int value2) {
                this.value = value2;
            }

            @Deprecated
            public static DataCase valueOf(int value2) {
                return forNumber(value2);
            }

            public static DataCase forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return DATA_NOT_SET;
                    case 2:
                        return IT_SYNC;
                    case 3:
                        return START_SYNC;
                    case 4:
                        return STOP_SYNC;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisSubscriberOrBuilder {
            private int bitField0_;
            private int dataCase_;
            private Object data_;
            private SingleFieldBuilderV3<HisITSync, Builder, HisITSyncOrBuilder> itSyncBuilder_;
            private int operation_;
            private SingleFieldBuilderV3<HisStartSync, Builder, HisStartSyncOrBuilder> startSyncBuilder_;
            private SingleFieldBuilderV3<HisStopSync, Builder, HisStopSyncOrBuilder> stopSyncBuilder_;

            public static final Descriptor getDescriptor() {
                return HisDataOuterClass.internal_static_HisSubscriber_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisDataOuterClass.internal_static_HisSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(HisSubscriber.class, Builder.class);
            }

            private Builder() {
                this.dataCase_ = 0;
                this.operation_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.dataCase_ = 0;
                this.operation_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisSubscriber.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.operation_ = 0;
                this.bitField0_ &= -2;
                this.dataCase_ = 0;
                this.data_ = null;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisDataOuterClass.internal_static_HisSubscriber_descriptor;
            }

            public HisSubscriber getDefaultInstanceForType() {
                return HisSubscriber.getDefaultInstance();
            }

            public HisSubscriber build() {
                HisSubscriber result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisSubscriber buildPartial() {
                HisSubscriber result = new HisSubscriber((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.operation_ = this.operation_;
                if (this.dataCase_ == 2) {
                    if (this.itSyncBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.itSyncBuilder_.build();
                    }
                }
                if (this.dataCase_ == 3) {
                    if (this.startSyncBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.startSyncBuilder_.build();
                    }
                }
                if (this.dataCase_ == 4) {
                    if (this.stopSyncBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.stopSyncBuilder_.build();
                    }
                }
                result.bitField0_ = to_bitField0_;
                result.dataCase_ = this.dataCase_;
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
                if (other instanceof HisSubscriber) {
                    return mergeFrom((HisSubscriber) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisSubscriber other) {
                if (other != HisSubscriber.getDefaultInstance()) {
                    if (other.hasOperation()) {
                        setOperation(other.getOperation());
                    }
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisSubscriber$DataCase[other.getDataCase().ordinal()]) {
                        case 1:
                            mergeItSync(other.getItSync());
                            break;
                        case 2:
                            mergeStartSync(other.getStartSync());
                            break;
                        case 3:
                            mergeStopSync(other.getStopSync());
                            break;
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasOperation()) {
                    return false;
                }
                if (hasItSync() && !getItSync().isInitialized()) {
                    return false;
                }
                if (hasStartSync() && !getStartSync().isInitialized()) {
                    return false;
                }
                if (!hasStopSync() || getStopSync().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisSubscriber parsedMessage = null;
                try {
                    HisSubscriber parsedMessage2 = (HisSubscriber) HisSubscriber.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisSubscriber parsedMessage3 = (HisSubscriber) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public DataCase getDataCase() {
                return DataCase.forNumber(this.dataCase_);
            }

            public Builder clearData() {
                this.dataCase_ = 0;
                this.data_ = null;
                onChanged();
                return this;
            }

            public boolean hasOperation() {
                return (this.bitField0_ & 1) == 1;
            }

            public HisOperation getOperation() {
                HisOperation result = HisOperation.valueOf(this.operation_);
                return result == null ? HisOperation.IT_SYNC : result;
            }

            public Builder setOperation(HisOperation value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.operation_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearOperation() {
                this.bitField0_ &= -2;
                this.operation_ = 0;
                onChanged();
                return this;
            }

            public boolean hasItSync() {
                return this.dataCase_ == 2;
            }

            public HisITSync getItSync() {
                if (this.itSyncBuilder_ == null) {
                    if (this.dataCase_ == 2) {
                        return (HisITSync) this.data_;
                    }
                    return HisITSync.getDefaultInstance();
                } else if (this.dataCase_ == 2) {
                    return (HisITSync) this.itSyncBuilder_.getMessage();
                } else {
                    return HisITSync.getDefaultInstance();
                }
            }

            public Builder setItSync(HisITSync value) {
                if (this.itSyncBuilder_ != null) {
                    this.itSyncBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder setItSync(Builder builderForValue) {
                if (this.itSyncBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.itSyncBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder mergeItSync(HisITSync value) {
                if (this.itSyncBuilder_ == null) {
                    if (this.dataCase_ != 2 || this.data_ == HisITSync.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = HisITSync.newBuilder((HisITSync) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 2) {
                        this.itSyncBuilder_.mergeFrom(value);
                    }
                    this.itSyncBuilder_.setMessage(value);
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder clearItSync() {
                if (this.itSyncBuilder_ != null) {
                    if (this.dataCase_ == 2) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.itSyncBuilder_.clear();
                } else if (this.dataCase_ == 2) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getItSyncBuilder() {
                return (Builder) getItSyncFieldBuilder().getBuilder();
            }

            public HisITSyncOrBuilder getItSyncOrBuilder() {
                if (this.dataCase_ == 2 && this.itSyncBuilder_ != null) {
                    return (HisITSyncOrBuilder) this.itSyncBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 2) {
                    return (HisITSync) this.data_;
                }
                return HisITSync.getDefaultInstance();
            }

            private SingleFieldBuilderV3<HisITSync, Builder, HisITSyncOrBuilder> getItSyncFieldBuilder() {
                if (this.itSyncBuilder_ == null) {
                    if (this.dataCase_ != 2) {
                        this.data_ = HisITSync.getDefaultInstance();
                    }
                    this.itSyncBuilder_ = new SingleFieldBuilderV3<>((HisITSync) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 2;
                onChanged();
                return this.itSyncBuilder_;
            }

            public boolean hasStartSync() {
                return this.dataCase_ == 3;
            }

            public HisStartSync getStartSync() {
                if (this.startSyncBuilder_ == null) {
                    if (this.dataCase_ == 3) {
                        return (HisStartSync) this.data_;
                    }
                    return HisStartSync.getDefaultInstance();
                } else if (this.dataCase_ == 3) {
                    return (HisStartSync) this.startSyncBuilder_.getMessage();
                } else {
                    return HisStartSync.getDefaultInstance();
                }
            }

            public Builder setStartSync(HisStartSync value) {
                if (this.startSyncBuilder_ != null) {
                    this.startSyncBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder setStartSync(Builder builderForValue) {
                if (this.startSyncBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.startSyncBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder mergeStartSync(HisStartSync value) {
                if (this.startSyncBuilder_ == null) {
                    if (this.dataCase_ != 3 || this.data_ == HisStartSync.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = HisStartSync.newBuilder((HisStartSync) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 3) {
                        this.startSyncBuilder_.mergeFrom(value);
                    }
                    this.startSyncBuilder_.setMessage(value);
                }
                this.dataCase_ = 3;
                return this;
            }

            public Builder clearStartSync() {
                if (this.startSyncBuilder_ != null) {
                    if (this.dataCase_ == 3) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.startSyncBuilder_.clear();
                } else if (this.dataCase_ == 3) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getStartSyncBuilder() {
                return (Builder) getStartSyncFieldBuilder().getBuilder();
            }

            public HisStartSyncOrBuilder getStartSyncOrBuilder() {
                if (this.dataCase_ == 3 && this.startSyncBuilder_ != null) {
                    return (HisStartSyncOrBuilder) this.startSyncBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 3) {
                    return (HisStartSync) this.data_;
                }
                return HisStartSync.getDefaultInstance();
            }

            private SingleFieldBuilderV3<HisStartSync, Builder, HisStartSyncOrBuilder> getStartSyncFieldBuilder() {
                if (this.startSyncBuilder_ == null) {
                    if (this.dataCase_ != 3) {
                        this.data_ = HisStartSync.getDefaultInstance();
                    }
                    this.startSyncBuilder_ = new SingleFieldBuilderV3<>((HisStartSync) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 3;
                onChanged();
                return this.startSyncBuilder_;
            }

            public boolean hasStopSync() {
                return this.dataCase_ == 4;
            }

            public HisStopSync getStopSync() {
                if (this.stopSyncBuilder_ == null) {
                    if (this.dataCase_ == 4) {
                        return (HisStopSync) this.data_;
                    }
                    return HisStopSync.getDefaultInstance();
                } else if (this.dataCase_ == 4) {
                    return (HisStopSync) this.stopSyncBuilder_.getMessage();
                } else {
                    return HisStopSync.getDefaultInstance();
                }
            }

            public Builder setStopSync(HisStopSync value) {
                if (this.stopSyncBuilder_ != null) {
                    this.stopSyncBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 4;
                return this;
            }

            public Builder setStopSync(Builder builderForValue) {
                if (this.stopSyncBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.stopSyncBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 4;
                return this;
            }

            public Builder mergeStopSync(HisStopSync value) {
                if (this.stopSyncBuilder_ == null) {
                    if (this.dataCase_ != 4 || this.data_ == HisStopSync.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = HisStopSync.newBuilder((HisStopSync) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 4) {
                        this.stopSyncBuilder_.mergeFrom(value);
                    }
                    this.stopSyncBuilder_.setMessage(value);
                }
                this.dataCase_ = 4;
                return this;
            }

            public Builder clearStopSync() {
                if (this.stopSyncBuilder_ != null) {
                    if (this.dataCase_ == 4) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.stopSyncBuilder_.clear();
                } else if (this.dataCase_ == 4) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getStopSyncBuilder() {
                return (Builder) getStopSyncFieldBuilder().getBuilder();
            }

            public HisStopSyncOrBuilder getStopSyncOrBuilder() {
                if (this.dataCase_ == 4 && this.stopSyncBuilder_ != null) {
                    return (HisStopSyncOrBuilder) this.stopSyncBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 4) {
                    return (HisStopSync) this.data_;
                }
                return HisStopSync.getDefaultInstance();
            }

            private SingleFieldBuilderV3<HisStopSync, Builder, HisStopSyncOrBuilder> getStopSyncFieldBuilder() {
                if (this.stopSyncBuilder_ == null) {
                    if (this.dataCase_ != 4) {
                        this.data_ = HisStopSync.getDefaultInstance();
                    }
                    this.stopSyncBuilder_ = new SingleFieldBuilderV3<>((HisStopSync) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 4;
                onChanged();
                return this.stopSyncBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private HisSubscriber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private HisSubscriber() {
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
            this.operation_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisSubscriber(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            int rawValue = input.readEnum();
                            if (HisOperation.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.operation_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 18:
                            Builder subBuilder = null;
                            if (this.dataCase_ == 2) {
                                subBuilder = ((HisITSync) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(HisITSync.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((HisITSync) this.data_);
                                this.data_ = subBuilder.buildPartial();
                            }
                            this.dataCase_ = 2;
                            break;
                        case 26:
                            Builder subBuilder2 = null;
                            if (this.dataCase_ == 3) {
                                subBuilder2 = ((HisStartSync) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(HisStartSync.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((HisStartSync) this.data_);
                                this.data_ = subBuilder2.buildPartial();
                            }
                            this.dataCase_ = 3;
                            break;
                        case 34:
                            Builder subBuilder3 = null;
                            if (this.dataCase_ == 4) {
                                subBuilder3 = ((HisStopSync) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(HisStopSync.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom((HisStopSync) this.data_);
                                this.data_ = subBuilder3.buildPartial();
                            }
                            this.dataCase_ = 4;
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
            return HisDataOuterClass.internal_static_HisSubscriber_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisDataOuterClass.internal_static_HisSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(HisSubscriber.class, Builder.class);
        }

        public DataCase getDataCase() {
            return DataCase.forNumber(this.dataCase_);
        }

        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        public HisOperation getOperation() {
            HisOperation result = HisOperation.valueOf(this.operation_);
            return result == null ? HisOperation.IT_SYNC : result;
        }

        public boolean hasItSync() {
            return this.dataCase_ == 2;
        }

        public HisITSync getItSync() {
            if (this.dataCase_ == 2) {
                return (HisITSync) this.data_;
            }
            return HisITSync.getDefaultInstance();
        }

        public HisITSyncOrBuilder getItSyncOrBuilder() {
            if (this.dataCase_ == 2) {
                return (HisITSync) this.data_;
            }
            return HisITSync.getDefaultInstance();
        }

        public boolean hasStartSync() {
            return this.dataCase_ == 3;
        }

        public HisStartSync getStartSync() {
            if (this.dataCase_ == 3) {
                return (HisStartSync) this.data_;
            }
            return HisStartSync.getDefaultInstance();
        }

        public HisStartSyncOrBuilder getStartSyncOrBuilder() {
            if (this.dataCase_ == 3) {
                return (HisStartSync) this.data_;
            }
            return HisStartSync.getDefaultInstance();
        }

        public boolean hasStopSync() {
            return this.dataCase_ == 4;
        }

        public HisStopSync getStopSync() {
            if (this.dataCase_ == 4) {
                return (HisStopSync) this.data_;
            }
            return HisStopSync.getDefaultInstance();
        }

        public HisStopSyncOrBuilder getStopSyncOrBuilder() {
            if (this.dataCase_ == 4) {
                return (HisStopSync) this.data_;
            }
            return HisStopSync.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasOperation()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasItSync() && !getItSync().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasStartSync() && !getStartSync().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasStopSync() || getStopSync().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.operation_);
            }
            if (this.dataCase_ == 2) {
                output.writeMessage(2, (HisITSync) this.data_);
            }
            if (this.dataCase_ == 3) {
                output.writeMessage(3, (HisStartSync) this.data_);
            }
            if (this.dataCase_ == 4) {
                output.writeMessage(4, (HisStopSync) this.data_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.operation_);
            }
            if (this.dataCase_ == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, (HisITSync) this.data_);
            }
            if (this.dataCase_ == 3) {
                size2 += CodedOutputStream.computeMessageSize(3, (HisStartSync) this.data_);
            }
            if (this.dataCase_ == 4) {
                size2 += CodedOutputStream.computeMessageSize(4, (HisStopSync) this.data_);
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
            if (!(obj instanceof HisSubscriber)) {
                return super.equals(obj);
            }
            HisSubscriber other = (HisSubscriber) obj;
            boolean result3 = 1 != 0 && hasOperation() == other.hasOperation();
            if (hasOperation()) {
                result3 = result3 && this.operation_ == other.operation_;
            }
            if (!result3 || !getDataCase().equals(other.getDataCase())) {
                result = false;
            } else {
                result = true;
            }
            if (!result) {
                return false;
            }
            switch (this.dataCase_) {
                case 2:
                    if (result && getItSync().equals(other.getItSync())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 3:
                    if (result && getStartSync().equals(other.getStartSync())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 4:
                    if (result && getStopSync().equals(other.getStopSync())) {
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
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasOperation()) {
                hash = (((hash * 37) + 1) * 53) + this.operation_;
            }
            switch (this.dataCase_) {
                case 2:
                    hash = (((hash * 37) + 2) * 53) + getItSync().hashCode();
                    break;
                case 3:
                    hash = (((hash * 37) + 3) * 53) + getStartSync().hashCode();
                    break;
                case 4:
                    hash = (((hash * 37) + 4) * 53) + getStopSync().hashCode();
                    break;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisSubscriber parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisSubscriber) PARSER.parseFrom(data);
        }

        public static HisSubscriber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisSubscriber parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisSubscriber) PARSER.parseFrom(data);
        }

        public static HisSubscriber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisSubscriber parseFrom(InputStream input) throws IOException {
            return (HisSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisSubscriber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisSubscriber parseDelimitedFrom(InputStream input) throws IOException {
            return (HisSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisSubscriber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisSubscriber parseFrom(CodedInputStream input) throws IOException {
            return (HisSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisSubscriber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisSubscriber prototype) {
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

        public static HisSubscriber getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisSubscriber> parser() {
            return PARSER;
        }

        public Parser<HisSubscriber> getParserForType() {
            return PARSER;
        }

        public HisSubscriber getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    /* renamed from: com.iwown.ble_module.proto.base.HisDataOuterClass$2 reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisData$DataCase = new int[DataCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisNotification$DataCase = new int[DataCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisSubscriber$DataCase = new int[DataCase.values().length];

        static {
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisNotification$DataCase[DataCase.CONFIRM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisNotification$DataCase[DataCase.INDEX_TABLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisNotification$DataCase[DataCase.HIS_DATA.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisNotification$DataCase[DataCase.DATA_NOT_SET.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisData$DataCase[DataCase.STATUS.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisData$DataCase[DataCase.HEALTH.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisData$DataCase[DataCase.GNSS.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisData$DataCase[DataCase.ECG.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisData$DataCase[DataCase.PPG.ordinal()] = 5;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisData$DataCase[DataCase.RRI.ordinal()] = 6;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisData$DataCase[DataCase.DATA_NOT_SET.ordinal()] = 7;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisSubscriber$DataCase[DataCase.IT_SYNC.ordinal()] = 1;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisSubscriber$DataCase[DataCase.START_SYNC.ordinal()] = 2;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisSubscriber$DataCase[DataCase.STOP_SYNC.ordinal()] = 3;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$HisDataOuterClass$HisSubscriber$DataCase[DataCase.DATA_NOT_SET.ordinal()] = 4;
            } catch (NoSuchFieldError e15) {
            }
        }
    }

    public interface HisBlockOrBuilder extends MessageOrBuilder {
        int getEndSeq();

        int getStartSeq();

        boolean hasEndSeq();

        boolean hasStartSeq();
    }

    public interface HisConfirmOrBuilder extends MessageOrBuilder {
        HisOperation getOperation();

        boolean getRet();

        boolean hasOperation();

        boolean hasRet();
    }

    public interface HisDataOrBuilder extends MessageOrBuilder {
        DataCase getDataCase();

        HisDataECG getEcg();

        HisDataECGOrBuilder getEcgOrBuilder();

        HisDataGNSS getGnss();

        HisDataGNSSOrBuilder getGnssOrBuilder();

        HisDataHealth getHealth();

        HisDataHealthOrBuilder getHealthOrBuilder();

        HisDataPPG getPpg();

        HisDataPPGOrBuilder getPpgOrBuilder();

        HisDataRRI getRri();

        HisDataRRIOrBuilder getRriOrBuilder();

        int getSeq();

        HisDataStatus getStatus();

        boolean hasEcg();

        boolean hasGnss();

        boolean hasHealth();

        boolean hasPpg();

        boolean hasRri();

        boolean hasSeq();

        boolean hasStatus();
    }

    public interface HisITSyncOrBuilder extends MessageOrBuilder {
        HisDataType getType();

        boolean hasType();
    }

    public interface HisIndexOrBuilder extends MessageOrBuilder {
        int getEndSeq();

        int getStartSeq();

        RtTime getTime();

        RtTimeOrBuilder getTimeOrBuilder();

        boolean hasEndSeq();

        boolean hasStartSeq();

        boolean hasTime();
    }

    public interface HisIndexTableOrBuilder extends MessageOrBuilder {
        HisIndex getIndex(int i);

        int getIndexCount();

        List<HisIndex> getIndexList();

        HisIndexOrBuilder getIndexOrBuilder(int i);

        List<? extends HisIndexOrBuilder> getIndexOrBuilderList();
    }

    public interface HisNotificationOrBuilder extends MessageOrBuilder {
        HisConfirm getConfirm();

        HisConfirmOrBuilder getConfirmOrBuilder();

        DataCase getDataCase();

        HisData getHisData();

        HisDataOrBuilder getHisDataOrBuilder();

        HisIndexTable getIndexTable();

        HisIndexTableOrBuilder getIndexTableOrBuilder();

        HisDataType getType();

        boolean hasConfirm();

        boolean hasHisData();

        boolean hasIndexTable();

        boolean hasType();
    }

    public interface HisStartSyncOrBuilder extends MessageOrBuilder {
        HisBlock getBlock(int i);

        int getBlockCount();

        List<HisBlock> getBlockList();

        HisBlockOrBuilder getBlockOrBuilder(int i);

        List<? extends HisBlockOrBuilder> getBlockOrBuilderList();

        HisDataType getType();

        boolean hasType();
    }

    public interface HisStopSyncOrBuilder extends MessageOrBuilder {
        HisDataType getType();

        boolean hasType();
    }

    public interface HisSubscriberOrBuilder extends MessageOrBuilder {
        DataCase getDataCase();

        HisITSync getItSync();

        HisITSyncOrBuilder getItSyncOrBuilder();

        HisOperation getOperation();

        HisStartSync getStartSync();

        HisStartSyncOrBuilder getStartSyncOrBuilder();

        HisStopSync getStopSync();

        HisStopSyncOrBuilder getStopSyncOrBuilder();

        boolean hasItSync();

        boolean hasOperation();

        boolean hasStartSync();

        boolean hasStopSync();
    }

    private HisDataOuterClass() {
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
        FileDescriptor[] fileDescriptorArr = {RealtimeData.getDescriptor(), HisHealthData.getDescriptor(), HisGnssData.getDescriptor(), HisEcgData.getDescriptor(), HisPpgData.getDescriptor(), HisRriData.getDescriptor()};
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u000ehis_data.proto\u001a\u0013realtime_data.proto\u001a\u0015his_health_data.proto\u001a\u0013his_gnss_data.proto\u001a\u0012his_ecg_data.proto\u001a\u0012his_ppg_data.proto\u001a\u0012his_rri_data.proto\".\n\bHisBlock\u0012\u0011\n\tstart_seq\u0018\u0001 \u0002(\u0007\u0012\u000f\n\u0007end_seq\u0018\u0002 \u0002(\u0007\"'\n\tHisITSync\u0012\u001a\n\u0004type\u0018\u0001 \u0002(\u000e2\f.HisDataType\"D\n\fHisStartSync\u0012\u001a\n\u0004type\u0018\u0001 \u0002(\u000e2\f.HisDataType\u0012\u0018\n\u0005block\u0018\u0002 \u0003(\u000b2\t.HisBlock\")\n\u000bHisStopSync\u0012\u001a\n\u0004type\u0018\u0001 \u0002(\u000e2\f.HisDataType\" \u0001\n\rHisSubscriber\u0012 \n\toperation\u0018\u0001 \u0002(\u000e2\r.HisOperation\u0012\u001d\n\u0007i", "t_sync\u0018\u0002 \u0001(\u000b2\n.HisITSyncH\u0000\u0012#\n\nstart_sync\u0018\u0003 \u0001(\u000b2\r.HisStartSyncH\u0000\u0012!\n\tstop_sync\u0018\u0004 \u0001(\u000b2\f.HisStopSyncH\u0000B\u0006\n\u0004data\"E\n\bHisIndex\u0012\u0015\n\u0004time\u0018\u0001 \u0002(\u000b2\u0007.RtTime\u0012\u0011\n\tstart_seq\u0018\u0002 \u0002(\u0007\u0012\u000f\n\u0007end_seq\u0018\u0003 \u0002(\u0007\")\n\rHisIndexTable\u0012\u0018\n\u0005index\u0018\u0001 \u0003(\u000b2\t.HisIndex\"Ô\u0001\n\u0007HisData\u0012\u000b\n\u0003seq\u0018\u0001 \u0002(\u0007\u0012 \n\u0006status\u0018\u0002 \u0001(\u000e2\u000e.HisDataStatusH\u0000\u0012 \n\u0006health\u0018\u0003 \u0001(\u000b2\u000e.HisDataHealthH\u0000\u0012\u001c\n\u0004gnss\u0018\u0004 \u0001(\u000b2\f.HisDataGNSSH\u0000\u0012\u001a\n\u0003ecg\u0018\u0005 \u0001(\u000b2\u000b.HisDataECGH\u0000\u0012\u001a\n\u0003ppg\u0018\u0006 \u0001(\u000b2\u000b.HisDataPPGH\u0000\u0012", "\u001a\n\u0003rri\u0018\u0007 \u0001(\u000b2\u000b.HisDataRRIH\u0000B\u0006\n\u0004data\";\n\nHisConfirm\u0012 \n\toperation\u0018\u0001 \u0002(\u000e2\r.HisOperation\u0012\u000b\n\u0003ret\u0018\u0002 \u0002(\b\"\u0001\n\u000fHisNotification\u0012\u001a\n\u0004type\u0018\u0001 \u0002(\u000e2\f.HisDataType\u0012\u001e\n\u0007confirm\u0018\u0002 \u0001(\u000b2\u000b.HisConfirmH\u0000\u0012%\n\u000bindex_table\u0018\u0003 \u0001(\u000b2\u000e.HisIndexTableH\u0000\u0012\u001c\n\bhis_data\u0018\u0004 \u0001(\u000b2\b.HisDataH\u0000B\u0006\n\u0004data*:\n\fHisOperation\u0012\u000b\n\u0007IT_SYNC\u0010\u0000\u0012\u000e\n\nSTART_SYNC\u0010\u0001\u0012\r\n\tSTOP_SYNC\u0010\u0002*W\n\u000bHisDataType\u0012\u000f\n\u000bHEALTH_DATA\u0010\u0000\u0012\r\n\tGNSS_DATA\u0010\u0001\u0012\f\n\bECG_DATA\u0010\u0002\u0012\f\n\bPPG_DATA\u0010\u0003\u0012\f\n\bRRI_DATA", "\u0010\u0004*\u0001\n\rHisDataStatus\u0012\r\n\tSTATUS_OK\u0010\u0000\u0012\u0016\n\u0012STATUS_INNER_ERROR\u0010\u0001\u0012\u0013\n\u000fSTATUS_NV_ERROR\u0010\u0002\u0012\u0014\n\u0010STATUS_CRC_ERROR\u0010\u0003\u0012\u0015\n\u0011STATUS_TYPE_ERROR\u0010\u0004\u0012\u0018\n\u0014STATUS_NOT_SUPPORTED\u0010\u0005"}, fileDescriptorArr, new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                HisDataOuterClass.descriptor = root;
                return null;
            }
        });
        RealtimeData.getDescriptor();
        HisHealthData.getDescriptor();
        HisGnssData.getDescriptor();
        HisEcgData.getDescriptor();
        HisPpgData.getDescriptor();
        HisRriData.getDescriptor();
    }
}
