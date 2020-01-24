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
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import com.iwown.ble_module.proto.base.RealtimeData.DateTime;
import com.iwown.ble_module.proto.base.RealtimeData.DateTimeOrBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HisHealthData {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisDataHealth_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(7));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisDataHealth_fieldAccessorTable = new FieldAccessorTable(internal_static_HisDataHealth_descriptor, new String[]{"TimeStamp", "SleepData", "PedoData", "HrData", "HrvData", "BpData", "AfData", "MdtData"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisHealthAf_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(5));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisHealthAf_fieldAccessorTable = new FieldAccessorTable(internal_static_HisHealthAf_descriptor, new String[]{"Af"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisHealthBp_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisHealthBp_fieldAccessorTable = new FieldAccessorTable(internal_static_HisHealthBp_descriptor, new String[]{"Sbp", "Dbp", "Time"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisHealthHr_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisHealthHr_fieldAccessorTable = new FieldAccessorTable(internal_static_HisHealthHr_descriptor, new String[]{"MinBpm", "MaxBpm", "AvgBpm"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisHealthHrv_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisHealthHrv_fieldAccessorTable = new FieldAccessorTable(internal_static_HisHealthHrv_descriptor, new String[]{"SDNN", "RMSSD", "PNN50", "MEAN", "Fatigue"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisHealthMdt_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisHealthMdt_fieldAccessorTable = new FieldAccessorTable(internal_static_HisHealthMdt_descriptor, new String[]{"SDNN", "RMSSD", "PNN50", "MEAN", "Status"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisHealthPedo_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisHealthPedo_fieldAccessorTable = new FieldAccessorTable(internal_static_HisHealthPedo_descriptor, new String[]{"Type", "State", "Calorie", "Step", "Distance"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HisHealthSleep_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(6));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HisHealthSleep_fieldAccessorTable = new FieldAccessorTable(internal_static_HisHealthSleep_descriptor, new String[]{"SleepData", "ShutDown", "Charge"});

    public static final class HisDataHealth extends GeneratedMessageV3 implements HisDataHealthOrBuilder {
        public static final int AF_DATA_FIELD_NUMBER = 7;
        public static final int BP_DATA_FIELD_NUMBER = 6;
        private static final HisDataHealth DEFAULT_INSTANCE = new HisDataHealth();
        public static final int HRV_DATA_FIELD_NUMBER = 5;
        public static final int HR_DATA_FIELD_NUMBER = 4;
        public static final int MDT_DATA_FIELD_NUMBER = 8;
        @Deprecated
        public static final Parser<HisDataHealth> PARSER = new AbstractParser<HisDataHealth>() {
            public HisDataHealth parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisDataHealth(input, extensionRegistry);
            }
        };
        public static final int PEDO_DATA_FIELD_NUMBER = 3;
        public static final int SLEEP_DATA_FIELD_NUMBER = 2;
        public static final int TIME_STAMP_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public HisHealthAf afData_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public HisHealthBp bpData_;
        /* access modifiers changed from: private */
        public HisHealthHr hrData_;
        /* access modifiers changed from: private */
        public HisHealthHrv hrvData_;
        /* access modifiers changed from: private */
        public HisHealthMdt mdtData_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public HisHealthPedo pedoData_;
        /* access modifiers changed from: private */
        public HisHealthSleep sleepData_;
        /* access modifiers changed from: private */
        public DateTime timeStamp_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisDataHealthOrBuilder {
            private SingleFieldBuilderV3<HisHealthAf, Builder, HisHealthAfOrBuilder> afDataBuilder_;
            private HisHealthAf afData_;
            private int bitField0_;
            private SingleFieldBuilderV3<HisHealthBp, Builder, HisHealthBpOrBuilder> bpDataBuilder_;
            private HisHealthBp bpData_;
            private SingleFieldBuilderV3<HisHealthHr, Builder, HisHealthHrOrBuilder> hrDataBuilder_;
            private HisHealthHr hrData_;
            private SingleFieldBuilderV3<HisHealthHrv, Builder, HisHealthHrvOrBuilder> hrvDataBuilder_;
            private HisHealthHrv hrvData_;
            private SingleFieldBuilderV3<HisHealthMdt, Builder, HisHealthMdtOrBuilder> mdtDataBuilder_;
            private HisHealthMdt mdtData_;
            private SingleFieldBuilderV3<HisHealthPedo, Builder, HisHealthPedoOrBuilder> pedoDataBuilder_;
            private HisHealthPedo pedoData_;
            private SingleFieldBuilderV3<HisHealthSleep, Builder, HisHealthSleepOrBuilder> sleepDataBuilder_;
            private HisHealthSleep sleepData_;
            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> timeStampBuilder_;
            private DateTime timeStamp_;

            public static final Descriptor getDescriptor() {
                return HisHealthData.internal_static_HisDataHealth_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisHealthData.internal_static_HisDataHealth_fieldAccessorTable.ensureFieldAccessorsInitialized(HisDataHealth.class, Builder.class);
            }

            private Builder() {
                this.timeStamp_ = null;
                this.sleepData_ = null;
                this.pedoData_ = null;
                this.hrData_ = null;
                this.hrvData_ = null;
                this.bpData_ = null;
                this.afData_ = null;
                this.mdtData_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.timeStamp_ = null;
                this.sleepData_ = null;
                this.pedoData_ = null;
                this.hrData_ = null;
                this.hrvData_ = null;
                this.bpData_ = null;
                this.afData_ = null;
                this.mdtData_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisDataHealth.alwaysUseFieldBuilders) {
                    getTimeStampFieldBuilder();
                    getSleepDataFieldBuilder();
                    getPedoDataFieldBuilder();
                    getHrDataFieldBuilder();
                    getHrvDataFieldBuilder();
                    getBpDataFieldBuilder();
                    getAfDataFieldBuilder();
                    getMdtDataFieldBuilder();
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
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = null;
                } else {
                    this.sleepDataBuilder_.clear();
                }
                this.bitField0_ &= -3;
                if (this.pedoDataBuilder_ == null) {
                    this.pedoData_ = null;
                } else {
                    this.pedoDataBuilder_.clear();
                }
                this.bitField0_ &= -5;
                if (this.hrDataBuilder_ == null) {
                    this.hrData_ = null;
                } else {
                    this.hrDataBuilder_.clear();
                }
                this.bitField0_ &= -9;
                if (this.hrvDataBuilder_ == null) {
                    this.hrvData_ = null;
                } else {
                    this.hrvDataBuilder_.clear();
                }
                this.bitField0_ &= -17;
                if (this.bpDataBuilder_ == null) {
                    this.bpData_ = null;
                } else {
                    this.bpDataBuilder_.clear();
                }
                this.bitField0_ &= -33;
                if (this.afDataBuilder_ == null) {
                    this.afData_ = null;
                } else {
                    this.afDataBuilder_.clear();
                }
                this.bitField0_ &= -65;
                if (this.mdtDataBuilder_ == null) {
                    this.mdtData_ = null;
                } else {
                    this.mdtDataBuilder_.clear();
                }
                this.bitField0_ &= -129;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisHealthData.internal_static_HisDataHealth_descriptor;
            }

            public HisDataHealth getDefaultInstanceForType() {
                return HisDataHealth.getDefaultInstance();
            }

            public HisDataHealth build() {
                HisDataHealth result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisDataHealth buildPartial() {
                HisDataHealth result = new HisDataHealth((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.timeStampBuilder_ == null) {
                    result.timeStamp_ = this.timeStamp_;
                } else {
                    result.timeStamp_ = (DateTime) this.timeStampBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.sleepDataBuilder_ == null) {
                    result.sleepData_ = this.sleepData_;
                } else {
                    result.sleepData_ = (HisHealthSleep) this.sleepDataBuilder_.build();
                }
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                if (this.pedoDataBuilder_ == null) {
                    result.pedoData_ = this.pedoData_;
                } else {
                    result.pedoData_ = (HisHealthPedo) this.pedoDataBuilder_.build();
                }
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                if (this.hrDataBuilder_ == null) {
                    result.hrData_ = this.hrData_;
                } else {
                    result.hrData_ = (HisHealthHr) this.hrDataBuilder_.build();
                }
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                if (this.hrvDataBuilder_ == null) {
                    result.hrvData_ = this.hrvData_;
                } else {
                    result.hrvData_ = (HisHealthHrv) this.hrvDataBuilder_.build();
                }
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                if (this.bpDataBuilder_ == null) {
                    result.bpData_ = this.bpData_;
                } else {
                    result.bpData_ = (HisHealthBp) this.bpDataBuilder_.build();
                }
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                if (this.afDataBuilder_ == null) {
                    result.afData_ = this.afData_;
                } else {
                    result.afData_ = (HisHealthAf) this.afDataBuilder_.build();
                }
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                if (this.mdtDataBuilder_ == null) {
                    result.mdtData_ = this.mdtData_;
                } else {
                    result.mdtData_ = (HisHealthMdt) this.mdtDataBuilder_.build();
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
                if (other instanceof HisDataHealth) {
                    return mergeFrom((HisDataHealth) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisDataHealth other) {
                if (other != HisDataHealth.getDefaultInstance()) {
                    if (other.hasTimeStamp()) {
                        mergeTimeStamp(other.getTimeStamp());
                    }
                    if (other.hasSleepData()) {
                        mergeSleepData(other.getSleepData());
                    }
                    if (other.hasPedoData()) {
                        mergePedoData(other.getPedoData());
                    }
                    if (other.hasHrData()) {
                        mergeHrData(other.getHrData());
                    }
                    if (other.hasHrvData()) {
                        mergeHrvData(other.getHrvData());
                    }
                    if (other.hasBpData()) {
                        mergeBpData(other.getBpData());
                    }
                    if (other.hasAfData()) {
                        mergeAfData(other.getAfData());
                    }
                    if (other.hasMdtData()) {
                        mergeMdtData(other.getMdtData());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasTimeStamp() || !getTimeStamp().isInitialized()) {
                    return false;
                }
                if (hasSleepData() && !getSleepData().isInitialized()) {
                    return false;
                }
                if (hasPedoData() && !getPedoData().isInitialized()) {
                    return false;
                }
                if (hasHrData() && !getHrData().isInitialized()) {
                    return false;
                }
                if (hasHrvData() && !getHrvData().isInitialized()) {
                    return false;
                }
                if (hasBpData() && !getBpData().isInitialized()) {
                    return false;
                }
                if (hasAfData() && !getAfData().isInitialized()) {
                    return false;
                }
                if (!hasMdtData() || getMdtData().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisDataHealth parsedMessage = null;
                try {
                    HisDataHealth parsedMessage2 = (HisDataHealth) HisDataHealth.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisDataHealth parsedMessage3 = (HisDataHealth) e.getUnfinishedMessage();
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

            public boolean hasSleepData() {
                return (this.bitField0_ & 2) == 2;
            }

            public HisHealthSleep getSleepData() {
                if (this.sleepDataBuilder_ == null) {
                    return this.sleepData_ == null ? HisHealthSleep.getDefaultInstance() : this.sleepData_;
                }
                return (HisHealthSleep) this.sleepDataBuilder_.getMessage();
            }

            public Builder setSleepData(HisHealthSleep value) {
                if (this.sleepDataBuilder_ != null) {
                    this.sleepDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.sleepData_ = value;
                    onChanged();
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setSleepData(Builder builderForValue) {
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = builderForValue.build();
                    onChanged();
                } else {
                    this.sleepDataBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeSleepData(HisHealthSleep value) {
                if (this.sleepDataBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.sleepData_ == null || this.sleepData_ == HisHealthSleep.getDefaultInstance()) {
                        this.sleepData_ = value;
                    } else {
                        this.sleepData_ = HisHealthSleep.newBuilder(this.sleepData_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.sleepDataBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearSleepData() {
                if (this.sleepDataBuilder_ == null) {
                    this.sleepData_ = null;
                    onChanged();
                } else {
                    this.sleepDataBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public Builder getSleepDataBuilder() {
                this.bitField0_ |= 2;
                onChanged();
                return (Builder) getSleepDataFieldBuilder().getBuilder();
            }

            public HisHealthSleepOrBuilder getSleepDataOrBuilder() {
                if (this.sleepDataBuilder_ != null) {
                    return (HisHealthSleepOrBuilder) this.sleepDataBuilder_.getMessageOrBuilder();
                }
                return this.sleepData_ == null ? HisHealthSleep.getDefaultInstance() : this.sleepData_;
            }

            private SingleFieldBuilderV3<HisHealthSleep, Builder, HisHealthSleepOrBuilder> getSleepDataFieldBuilder() {
                if (this.sleepDataBuilder_ == null) {
                    this.sleepDataBuilder_ = new SingleFieldBuilderV3<>(getSleepData(), getParentForChildren(), isClean());
                    this.sleepData_ = null;
                }
                return this.sleepDataBuilder_;
            }

            public boolean hasPedoData() {
                return (this.bitField0_ & 4) == 4;
            }

            public HisHealthPedo getPedoData() {
                if (this.pedoDataBuilder_ == null) {
                    return this.pedoData_ == null ? HisHealthPedo.getDefaultInstance() : this.pedoData_;
                }
                return (HisHealthPedo) this.pedoDataBuilder_.getMessage();
            }

            public Builder setPedoData(HisHealthPedo value) {
                if (this.pedoDataBuilder_ != null) {
                    this.pedoDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.pedoData_ = value;
                    onChanged();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setPedoData(Builder builderForValue) {
                if (this.pedoDataBuilder_ == null) {
                    this.pedoData_ = builderForValue.build();
                    onChanged();
                } else {
                    this.pedoDataBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergePedoData(HisHealthPedo value) {
                if (this.pedoDataBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 4 || this.pedoData_ == null || this.pedoData_ == HisHealthPedo.getDefaultInstance()) {
                        this.pedoData_ = value;
                    } else {
                        this.pedoData_ = HisHealthPedo.newBuilder(this.pedoData_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.pedoDataBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearPedoData() {
                if (this.pedoDataBuilder_ == null) {
                    this.pedoData_ = null;
                    onChanged();
                } else {
                    this.pedoDataBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder getPedoDataBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (Builder) getPedoDataFieldBuilder().getBuilder();
            }

            public HisHealthPedoOrBuilder getPedoDataOrBuilder() {
                if (this.pedoDataBuilder_ != null) {
                    return (HisHealthPedoOrBuilder) this.pedoDataBuilder_.getMessageOrBuilder();
                }
                return this.pedoData_ == null ? HisHealthPedo.getDefaultInstance() : this.pedoData_;
            }

            private SingleFieldBuilderV3<HisHealthPedo, Builder, HisHealthPedoOrBuilder> getPedoDataFieldBuilder() {
                if (this.pedoDataBuilder_ == null) {
                    this.pedoDataBuilder_ = new SingleFieldBuilderV3<>(getPedoData(), getParentForChildren(), isClean());
                    this.pedoData_ = null;
                }
                return this.pedoDataBuilder_;
            }

            public boolean hasHrData() {
                return (this.bitField0_ & 8) == 8;
            }

            public HisHealthHr getHrData() {
                if (this.hrDataBuilder_ == null) {
                    return this.hrData_ == null ? HisHealthHr.getDefaultInstance() : this.hrData_;
                }
                return (HisHealthHr) this.hrDataBuilder_.getMessage();
            }

            public Builder setHrData(HisHealthHr value) {
                if (this.hrDataBuilder_ != null) {
                    this.hrDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.hrData_ = value;
                    onChanged();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setHrData(Builder builderForValue) {
                if (this.hrDataBuilder_ == null) {
                    this.hrData_ = builderForValue.build();
                    onChanged();
                } else {
                    this.hrDataBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeHrData(HisHealthHr value) {
                if (this.hrDataBuilder_ == null) {
                    if ((this.bitField0_ & 8) != 8 || this.hrData_ == null || this.hrData_ == HisHealthHr.getDefaultInstance()) {
                        this.hrData_ = value;
                    } else {
                        this.hrData_ = HisHealthHr.newBuilder(this.hrData_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.hrDataBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearHrData() {
                if (this.hrDataBuilder_ == null) {
                    this.hrData_ = null;
                    onChanged();
                } else {
                    this.hrDataBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public Builder getHrDataBuilder() {
                this.bitField0_ |= 8;
                onChanged();
                return (Builder) getHrDataFieldBuilder().getBuilder();
            }

            public HisHealthHrOrBuilder getHrDataOrBuilder() {
                if (this.hrDataBuilder_ != null) {
                    return (HisHealthHrOrBuilder) this.hrDataBuilder_.getMessageOrBuilder();
                }
                return this.hrData_ == null ? HisHealthHr.getDefaultInstance() : this.hrData_;
            }

            private SingleFieldBuilderV3<HisHealthHr, Builder, HisHealthHrOrBuilder> getHrDataFieldBuilder() {
                if (this.hrDataBuilder_ == null) {
                    this.hrDataBuilder_ = new SingleFieldBuilderV3<>(getHrData(), getParentForChildren(), isClean());
                    this.hrData_ = null;
                }
                return this.hrDataBuilder_;
            }

            public boolean hasHrvData() {
                return (this.bitField0_ & 16) == 16;
            }

            public HisHealthHrv getHrvData() {
                if (this.hrvDataBuilder_ == null) {
                    return this.hrvData_ == null ? HisHealthHrv.getDefaultInstance() : this.hrvData_;
                }
                return (HisHealthHrv) this.hrvDataBuilder_.getMessage();
            }

            public Builder setHrvData(HisHealthHrv value) {
                if (this.hrvDataBuilder_ != null) {
                    this.hrvDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.hrvData_ = value;
                    onChanged();
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder setHrvData(Builder builderForValue) {
                if (this.hrvDataBuilder_ == null) {
                    this.hrvData_ = builderForValue.build();
                    onChanged();
                } else {
                    this.hrvDataBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder mergeHrvData(HisHealthHrv value) {
                if (this.hrvDataBuilder_ == null) {
                    if ((this.bitField0_ & 16) != 16 || this.hrvData_ == null || this.hrvData_ == HisHealthHrv.getDefaultInstance()) {
                        this.hrvData_ = value;
                    } else {
                        this.hrvData_ = HisHealthHrv.newBuilder(this.hrvData_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.hrvDataBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder clearHrvData() {
                if (this.hrvDataBuilder_ == null) {
                    this.hrvData_ = null;
                    onChanged();
                } else {
                    this.hrvDataBuilder_.clear();
                }
                this.bitField0_ &= -17;
                return this;
            }

            public Builder getHrvDataBuilder() {
                this.bitField0_ |= 16;
                onChanged();
                return (Builder) getHrvDataFieldBuilder().getBuilder();
            }

            public HisHealthHrvOrBuilder getHrvDataOrBuilder() {
                if (this.hrvDataBuilder_ != null) {
                    return (HisHealthHrvOrBuilder) this.hrvDataBuilder_.getMessageOrBuilder();
                }
                return this.hrvData_ == null ? HisHealthHrv.getDefaultInstance() : this.hrvData_;
            }

            private SingleFieldBuilderV3<HisHealthHrv, Builder, HisHealthHrvOrBuilder> getHrvDataFieldBuilder() {
                if (this.hrvDataBuilder_ == null) {
                    this.hrvDataBuilder_ = new SingleFieldBuilderV3<>(getHrvData(), getParentForChildren(), isClean());
                    this.hrvData_ = null;
                }
                return this.hrvDataBuilder_;
            }

            public boolean hasBpData() {
                return (this.bitField0_ & 32) == 32;
            }

            public HisHealthBp getBpData() {
                if (this.bpDataBuilder_ == null) {
                    return this.bpData_ == null ? HisHealthBp.getDefaultInstance() : this.bpData_;
                }
                return (HisHealthBp) this.bpDataBuilder_.getMessage();
            }

            public Builder setBpData(HisHealthBp value) {
                if (this.bpDataBuilder_ != null) {
                    this.bpDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.bpData_ = value;
                    onChanged();
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder setBpData(Builder builderForValue) {
                if (this.bpDataBuilder_ == null) {
                    this.bpData_ = builderForValue.build();
                    onChanged();
                } else {
                    this.bpDataBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder mergeBpData(HisHealthBp value) {
                if (this.bpDataBuilder_ == null) {
                    if ((this.bitField0_ & 32) != 32 || this.bpData_ == null || this.bpData_ == HisHealthBp.getDefaultInstance()) {
                        this.bpData_ = value;
                    } else {
                        this.bpData_ = HisHealthBp.newBuilder(this.bpData_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.bpDataBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder clearBpData() {
                if (this.bpDataBuilder_ == null) {
                    this.bpData_ = null;
                    onChanged();
                } else {
                    this.bpDataBuilder_.clear();
                }
                this.bitField0_ &= -33;
                return this;
            }

            public Builder getBpDataBuilder() {
                this.bitField0_ |= 32;
                onChanged();
                return (Builder) getBpDataFieldBuilder().getBuilder();
            }

            public HisHealthBpOrBuilder getBpDataOrBuilder() {
                if (this.bpDataBuilder_ != null) {
                    return (HisHealthBpOrBuilder) this.bpDataBuilder_.getMessageOrBuilder();
                }
                return this.bpData_ == null ? HisHealthBp.getDefaultInstance() : this.bpData_;
            }

            private SingleFieldBuilderV3<HisHealthBp, Builder, HisHealthBpOrBuilder> getBpDataFieldBuilder() {
                if (this.bpDataBuilder_ == null) {
                    this.bpDataBuilder_ = new SingleFieldBuilderV3<>(getBpData(), getParentForChildren(), isClean());
                    this.bpData_ = null;
                }
                return this.bpDataBuilder_;
            }

            public boolean hasAfData() {
                return (this.bitField0_ & 64) == 64;
            }

            public HisHealthAf getAfData() {
                if (this.afDataBuilder_ == null) {
                    return this.afData_ == null ? HisHealthAf.getDefaultInstance() : this.afData_;
                }
                return (HisHealthAf) this.afDataBuilder_.getMessage();
            }

            public Builder setAfData(HisHealthAf value) {
                if (this.afDataBuilder_ != null) {
                    this.afDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.afData_ = value;
                    onChanged();
                }
                this.bitField0_ |= 64;
                return this;
            }

            public Builder setAfData(Builder builderForValue) {
                if (this.afDataBuilder_ == null) {
                    this.afData_ = builderForValue.build();
                    onChanged();
                } else {
                    this.afDataBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 64;
                return this;
            }

            public Builder mergeAfData(HisHealthAf value) {
                if (this.afDataBuilder_ == null) {
                    if ((this.bitField0_ & 64) != 64 || this.afData_ == null || this.afData_ == HisHealthAf.getDefaultInstance()) {
                        this.afData_ = value;
                    } else {
                        this.afData_ = HisHealthAf.newBuilder(this.afData_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.afDataBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 64;
                return this;
            }

            public Builder clearAfData() {
                if (this.afDataBuilder_ == null) {
                    this.afData_ = null;
                    onChanged();
                } else {
                    this.afDataBuilder_.clear();
                }
                this.bitField0_ &= -65;
                return this;
            }

            public Builder getAfDataBuilder() {
                this.bitField0_ |= 64;
                onChanged();
                return (Builder) getAfDataFieldBuilder().getBuilder();
            }

            public HisHealthAfOrBuilder getAfDataOrBuilder() {
                if (this.afDataBuilder_ != null) {
                    return (HisHealthAfOrBuilder) this.afDataBuilder_.getMessageOrBuilder();
                }
                return this.afData_ == null ? HisHealthAf.getDefaultInstance() : this.afData_;
            }

            private SingleFieldBuilderV3<HisHealthAf, Builder, HisHealthAfOrBuilder> getAfDataFieldBuilder() {
                if (this.afDataBuilder_ == null) {
                    this.afDataBuilder_ = new SingleFieldBuilderV3<>(getAfData(), getParentForChildren(), isClean());
                    this.afData_ = null;
                }
                return this.afDataBuilder_;
            }

            public boolean hasMdtData() {
                return (this.bitField0_ & 128) == 128;
            }

            public HisHealthMdt getMdtData() {
                if (this.mdtDataBuilder_ == null) {
                    return this.mdtData_ == null ? HisHealthMdt.getDefaultInstance() : this.mdtData_;
                }
                return (HisHealthMdt) this.mdtDataBuilder_.getMessage();
            }

            public Builder setMdtData(HisHealthMdt value) {
                if (this.mdtDataBuilder_ != null) {
                    this.mdtDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.mdtData_ = value;
                    onChanged();
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder setMdtData(Builder builderForValue) {
                if (this.mdtDataBuilder_ == null) {
                    this.mdtData_ = builderForValue.build();
                    onChanged();
                } else {
                    this.mdtDataBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder mergeMdtData(HisHealthMdt value) {
                if (this.mdtDataBuilder_ == null) {
                    if ((this.bitField0_ & 128) != 128 || this.mdtData_ == null || this.mdtData_ == HisHealthMdt.getDefaultInstance()) {
                        this.mdtData_ = value;
                    } else {
                        this.mdtData_ = HisHealthMdt.newBuilder(this.mdtData_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.mdtDataBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder clearMdtData() {
                if (this.mdtDataBuilder_ == null) {
                    this.mdtData_ = null;
                    onChanged();
                } else {
                    this.mdtDataBuilder_.clear();
                }
                this.bitField0_ &= -129;
                return this;
            }

            public Builder getMdtDataBuilder() {
                this.bitField0_ |= 128;
                onChanged();
                return (Builder) getMdtDataFieldBuilder().getBuilder();
            }

            public HisHealthMdtOrBuilder getMdtDataOrBuilder() {
                if (this.mdtDataBuilder_ != null) {
                    return (HisHealthMdtOrBuilder) this.mdtDataBuilder_.getMessageOrBuilder();
                }
                return this.mdtData_ == null ? HisHealthMdt.getDefaultInstance() : this.mdtData_;
            }

            private SingleFieldBuilderV3<HisHealthMdt, Builder, HisHealthMdtOrBuilder> getMdtDataFieldBuilder() {
                if (this.mdtDataBuilder_ == null) {
                    this.mdtDataBuilder_ = new SingleFieldBuilderV3<>(getMdtData(), getParentForChildren(), isClean());
                    this.mdtData_ = null;
                }
                return this.mdtDataBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private HisDataHealth(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisDataHealth() {
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisDataHealth(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            Builder subBuilder2 = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder2 = this.sleepData_.toBuilder();
                            }
                            this.sleepData_ = (HisHealthSleep) input.readMessage(HisHealthSleep.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.sleepData_);
                                this.sleepData_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            break;
                        case 26:
                            Builder subBuilder3 = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder3 = this.pedoData_.toBuilder();
                            }
                            this.pedoData_ = (HisHealthPedo) input.readMessage(HisHealthPedo.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom(this.pedoData_);
                                this.pedoData_ = subBuilder3.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            break;
                        case 34:
                            Builder subBuilder4 = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder4 = this.hrData_.toBuilder();
                            }
                            this.hrData_ = (HisHealthHr) input.readMessage(HisHealthHr.PARSER, extensionRegistry);
                            if (subBuilder4 != null) {
                                subBuilder4.mergeFrom(this.hrData_);
                                this.hrData_ = subBuilder4.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            break;
                        case 42:
                            Builder subBuilder5 = null;
                            if ((this.bitField0_ & 16) == 16) {
                                subBuilder5 = this.hrvData_.toBuilder();
                            }
                            this.hrvData_ = (HisHealthHrv) input.readMessage(HisHealthHrv.PARSER, extensionRegistry);
                            if (subBuilder5 != null) {
                                subBuilder5.mergeFrom(this.hrvData_);
                                this.hrvData_ = subBuilder5.buildPartial();
                            }
                            this.bitField0_ |= 16;
                            break;
                        case 50:
                            Builder subBuilder6 = null;
                            if ((this.bitField0_ & 32) == 32) {
                                subBuilder6 = this.bpData_.toBuilder();
                            }
                            this.bpData_ = (HisHealthBp) input.readMessage(HisHealthBp.PARSER, extensionRegistry);
                            if (subBuilder6 != null) {
                                subBuilder6.mergeFrom(this.bpData_);
                                this.bpData_ = subBuilder6.buildPartial();
                            }
                            this.bitField0_ |= 32;
                            break;
                        case 58:
                            Builder subBuilder7 = null;
                            if ((this.bitField0_ & 64) == 64) {
                                subBuilder7 = this.afData_.toBuilder();
                            }
                            this.afData_ = (HisHealthAf) input.readMessage(HisHealthAf.PARSER, extensionRegistry);
                            if (subBuilder7 != null) {
                                subBuilder7.mergeFrom(this.afData_);
                                this.afData_ = subBuilder7.buildPartial();
                            }
                            this.bitField0_ |= 64;
                            break;
                        case 66:
                            Builder subBuilder8 = null;
                            if ((this.bitField0_ & 128) == 128) {
                                subBuilder8 = this.mdtData_.toBuilder();
                            }
                            this.mdtData_ = (HisHealthMdt) input.readMessage(HisHealthMdt.PARSER, extensionRegistry);
                            if (subBuilder8 != null) {
                                subBuilder8.mergeFrom(this.mdtData_);
                                this.mdtData_ = subBuilder8.buildPartial();
                            }
                            this.bitField0_ |= 128;
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
            return HisHealthData.internal_static_HisDataHealth_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisHealthData.internal_static_HisDataHealth_fieldAccessorTable.ensureFieldAccessorsInitialized(HisDataHealth.class, Builder.class);
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

        public boolean hasSleepData() {
            return (this.bitField0_ & 2) == 2;
        }

        public HisHealthSleep getSleepData() {
            return this.sleepData_ == null ? HisHealthSleep.getDefaultInstance() : this.sleepData_;
        }

        public HisHealthSleepOrBuilder getSleepDataOrBuilder() {
            return this.sleepData_ == null ? HisHealthSleep.getDefaultInstance() : this.sleepData_;
        }

        public boolean hasPedoData() {
            return (this.bitField0_ & 4) == 4;
        }

        public HisHealthPedo getPedoData() {
            return this.pedoData_ == null ? HisHealthPedo.getDefaultInstance() : this.pedoData_;
        }

        public HisHealthPedoOrBuilder getPedoDataOrBuilder() {
            return this.pedoData_ == null ? HisHealthPedo.getDefaultInstance() : this.pedoData_;
        }

        public boolean hasHrData() {
            return (this.bitField0_ & 8) == 8;
        }

        public HisHealthHr getHrData() {
            return this.hrData_ == null ? HisHealthHr.getDefaultInstance() : this.hrData_;
        }

        public HisHealthHrOrBuilder getHrDataOrBuilder() {
            return this.hrData_ == null ? HisHealthHr.getDefaultInstance() : this.hrData_;
        }

        public boolean hasHrvData() {
            return (this.bitField0_ & 16) == 16;
        }

        public HisHealthHrv getHrvData() {
            return this.hrvData_ == null ? HisHealthHrv.getDefaultInstance() : this.hrvData_;
        }

        public HisHealthHrvOrBuilder getHrvDataOrBuilder() {
            return this.hrvData_ == null ? HisHealthHrv.getDefaultInstance() : this.hrvData_;
        }

        public boolean hasBpData() {
            return (this.bitField0_ & 32) == 32;
        }

        public HisHealthBp getBpData() {
            return this.bpData_ == null ? HisHealthBp.getDefaultInstance() : this.bpData_;
        }

        public HisHealthBpOrBuilder getBpDataOrBuilder() {
            return this.bpData_ == null ? HisHealthBp.getDefaultInstance() : this.bpData_;
        }

        public boolean hasAfData() {
            return (this.bitField0_ & 64) == 64;
        }

        public HisHealthAf getAfData() {
            return this.afData_ == null ? HisHealthAf.getDefaultInstance() : this.afData_;
        }

        public HisHealthAfOrBuilder getAfDataOrBuilder() {
            return this.afData_ == null ? HisHealthAf.getDefaultInstance() : this.afData_;
        }

        public boolean hasMdtData() {
            return (this.bitField0_ & 128) == 128;
        }

        public HisHealthMdt getMdtData() {
            return this.mdtData_ == null ? HisHealthMdt.getDefaultInstance() : this.mdtData_;
        }

        public HisHealthMdtOrBuilder getMdtDataOrBuilder() {
            return this.mdtData_ == null ? HisHealthMdt.getDefaultInstance() : this.mdtData_;
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
            } else if (hasSleepData() && !getSleepData().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasPedoData() && !getPedoData().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasHrData() && !getHrData().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasHrvData() && !getHrvData().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasBpData() && !getBpData().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasAfData() && !getAfData().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasMdtData() || getMdtData().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getTimeStamp());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, getSleepData());
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, getPedoData());
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(4, getHrData());
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeMessage(5, getHrvData());
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeMessage(6, getBpData());
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeMessage(7, getAfData());
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeMessage(8, getMdtData());
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
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, getSleepData());
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeMessageSize(3, getPedoData());
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeMessageSize(4, getHrData());
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeMessageSize(5, getHrvData());
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeMessageSize(6, getBpData());
            }
            if ((this.bitField0_ & 64) == 64) {
                size2 += CodedOutputStream.computeMessageSize(7, getAfData());
            }
            if ((this.bitField0_ & 128) == 128) {
                size2 += CodedOutputStream.computeMessageSize(8, getMdtData());
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
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof HisDataHealth)) {
                return super.equals(obj);
            }
            HisDataHealth other = (HisDataHealth) obj;
            boolean result9 = 1 != 0 && hasTimeStamp() == other.hasTimeStamp();
            if (hasTimeStamp()) {
                result9 = result9 && getTimeStamp().equals(other.getTimeStamp());
            }
            if (!result9 || hasSleepData() != other.hasSleepData()) {
                result = false;
            } else {
                result = true;
            }
            if (hasSleepData()) {
                if (!result || !getSleepData().equals(other.getSleepData())) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasPedoData() != other.hasPedoData()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasPedoData()) {
                if (!result2 || !getPedoData().equals(other.getPedoData())) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasHrData() != other.hasHrData()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasHrData()) {
                if (!result3 || !getHrData().equals(other.getHrData())) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasHrvData() != other.hasHrvData()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasHrvData()) {
                if (!result4 || !getHrvData().equals(other.getHrvData())) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasBpData() != other.hasBpData()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasBpData()) {
                if (!result5 || !getBpData().equals(other.getBpData())) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || hasAfData() != other.hasAfData()) {
                result6 = false;
            } else {
                result6 = true;
            }
            if (hasAfData()) {
                if (!result6 || !getAfData().equals(other.getAfData())) {
                    result6 = false;
                } else {
                    result6 = true;
                }
            }
            if (!result6 || hasMdtData() != other.hasMdtData()) {
                result7 = false;
            } else {
                result7 = true;
            }
            if (hasMdtData()) {
                if (!result7 || !getMdtData().equals(other.getMdtData())) {
                    result7 = false;
                } else {
                    result7 = true;
                }
            }
            if (!result7 || !this.unknownFields.equals(other.unknownFields)) {
                result8 = false;
            } else {
                result8 = true;
            }
            return result8;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasTimeStamp()) {
                hash = (((hash * 37) + 1) * 53) + getTimeStamp().hashCode();
            }
            if (hasSleepData()) {
                hash = (((hash * 37) + 2) * 53) + getSleepData().hashCode();
            }
            if (hasPedoData()) {
                hash = (((hash * 37) + 3) * 53) + getPedoData().hashCode();
            }
            if (hasHrData()) {
                hash = (((hash * 37) + 4) * 53) + getHrData().hashCode();
            }
            if (hasHrvData()) {
                hash = (((hash * 37) + 5) * 53) + getHrvData().hashCode();
            }
            if (hasBpData()) {
                hash = (((hash * 37) + 6) * 53) + getBpData().hashCode();
            }
            if (hasAfData()) {
                hash = (((hash * 37) + 7) * 53) + getAfData().hashCode();
            }
            if (hasMdtData()) {
                hash = (((hash * 37) + 8) * 53) + getMdtData().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisDataHealth parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisDataHealth) PARSER.parseFrom(data);
        }

        public static HisDataHealth parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisDataHealth) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisDataHealth parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisDataHealth) PARSER.parseFrom(data);
        }

        public static HisDataHealth parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisDataHealth) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisDataHealth parseFrom(InputStream input) throws IOException {
            return (HisDataHealth) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisDataHealth parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataHealth) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisDataHealth parseDelimitedFrom(InputStream input) throws IOException {
            return (HisDataHealth) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisDataHealth parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataHealth) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisDataHealth parseFrom(CodedInputStream input) throws IOException {
            return (HisDataHealth) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisDataHealth parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisDataHealth) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisDataHealth prototype) {
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

        public static HisDataHealth getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisDataHealth> parser() {
            return PARSER;
        }

        public Parser<HisDataHealth> getParserForType() {
            return PARSER;
        }

        public HisDataHealth getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisHealthAf extends GeneratedMessageV3 implements HisHealthAfOrBuilder {
        public static final int AF_FIELD_NUMBER = 1;
        private static final HisHealthAf DEFAULT_INSTANCE = new HisHealthAf();
        @Deprecated
        public static final Parser<HisHealthAf> PARSER = new AbstractParser<HisHealthAf>() {
            public HisHealthAf parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisHealthAf(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int af_;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisHealthAfOrBuilder {
            private int af_;
            private int bitField0_;

            public static final Descriptor getDescriptor() {
                return HisHealthData.internal_static_HisHealthAf_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisHealthData.internal_static_HisHealthAf_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthAf.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisHealthAf.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.af_ = 0;
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisHealthData.internal_static_HisHealthAf_descriptor;
            }

            public HisHealthAf getDefaultInstanceForType() {
                return HisHealthAf.getDefaultInstance();
            }

            public HisHealthAf build() {
                HisHealthAf result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisHealthAf buildPartial() {
                HisHealthAf result = new HisHealthAf((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.af_ = this.af_;
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
                if (other instanceof HisHealthAf) {
                    return mergeFrom((HisHealthAf) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisHealthAf other) {
                if (other != HisHealthAf.getDefaultInstance()) {
                    if (other.hasAf()) {
                        setAf(other.getAf());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasAf()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisHealthAf parsedMessage = null;
                try {
                    HisHealthAf parsedMessage2 = (HisHealthAf) HisHealthAf.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisHealthAf parsedMessage3 = (HisHealthAf) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasAf() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getAf() {
                return this.af_;
            }

            public Builder setAf(int value) {
                this.bitField0_ |= 1;
                this.af_ = value;
                onChanged();
                return this;
            }

            public Builder clearAf() {
                this.bitField0_ &= -2;
                this.af_ = 0;
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

        private HisHealthAf(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisHealthAf() {
            this.memoizedIsInitialized = -1;
            this.af_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisHealthAf(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.af_ = input.readSFixed32();
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
            return HisHealthData.internal_static_HisHealthAf_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisHealthData.internal_static_HisHealthAf_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthAf.class, Builder.class);
        }

        public boolean hasAf() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getAf() {
            return this.af_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasAf()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeSFixed32(1, this.af_);
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
                size2 = 0 + CodedOutputStream.computeSFixed32Size(1, this.af_);
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
            if (!(obj instanceof HisHealthAf)) {
                return super.equals(obj);
            }
            HisHealthAf other = (HisHealthAf) obj;
            boolean result2 = 1 != 0 && hasAf() == other.hasAf();
            if (hasAf()) {
                result2 = result2 && getAf() == other.getAf();
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
            if (hasAf()) {
                hash = (((hash * 37) + 1) * 53) + getAf();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisHealthAf parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisHealthAf) PARSER.parseFrom(data);
        }

        public static HisHealthAf parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthAf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthAf parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisHealthAf) PARSER.parseFrom(data);
        }

        public static HisHealthAf parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthAf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthAf parseFrom(InputStream input) throws IOException {
            return (HisHealthAf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthAf parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthAf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthAf parseDelimitedFrom(InputStream input) throws IOException {
            return (HisHealthAf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisHealthAf parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthAf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthAf parseFrom(CodedInputStream input) throws IOException {
            return (HisHealthAf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthAf parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthAf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisHealthAf prototype) {
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

        public static HisHealthAf getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisHealthAf> parser() {
            return PARSER;
        }

        public Parser<HisHealthAf> getParserForType() {
            return PARSER;
        }

        public HisHealthAf getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisHealthBp extends GeneratedMessageV3 implements HisHealthBpOrBuilder {
        public static final int DBP_FIELD_NUMBER = 2;
        private static final HisHealthBp DEFAULT_INSTANCE = new HisHealthBp();
        @Deprecated
        public static final Parser<HisHealthBp> PARSER = new AbstractParser<HisHealthBp>() {
            public HisHealthBp parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisHealthBp(input, extensionRegistry);
            }
        };
        public static final int SBP_FIELD_NUMBER = 1;
        public static final int TIME_FIELD_NUMBER = 3;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int dbp_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int sbp_;
        /* access modifiers changed from: private */
        public int time_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisHealthBpOrBuilder {
            private int bitField0_;
            private int dbp_;
            private int sbp_;
            private int time_;

            public static final Descriptor getDescriptor() {
                return HisHealthData.internal_static_HisHealthBp_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisHealthData.internal_static_HisHealthBp_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthBp.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisHealthBp.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.sbp_ = 0;
                this.bitField0_ &= -2;
                this.dbp_ = 0;
                this.bitField0_ &= -3;
                this.time_ = 0;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisHealthData.internal_static_HisHealthBp_descriptor;
            }

            public HisHealthBp getDefaultInstanceForType() {
                return HisHealthBp.getDefaultInstance();
            }

            public HisHealthBp build() {
                HisHealthBp result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisHealthBp buildPartial() {
                HisHealthBp result = new HisHealthBp((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.sbp_ = this.sbp_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.dbp_ = this.dbp_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.time_ = this.time_;
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
                if (other instanceof HisHealthBp) {
                    return mergeFrom((HisHealthBp) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisHealthBp other) {
                if (other != HisHealthBp.getDefaultInstance()) {
                    if (other.hasSbp()) {
                        setSbp(other.getSbp());
                    }
                    if (other.hasDbp()) {
                        setDbp(other.getDbp());
                    }
                    if (other.hasTime()) {
                        setTime(other.getTime());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasSbp() && hasDbp()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisHealthBp parsedMessage = null;
                try {
                    HisHealthBp parsedMessage2 = (HisHealthBp) HisHealthBp.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisHealthBp parsedMessage3 = (HisHealthBp) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSbp() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getSbp() {
                return this.sbp_;
            }

            public Builder setSbp(int value) {
                this.bitField0_ |= 1;
                this.sbp_ = value;
                onChanged();
                return this;
            }

            public Builder clearSbp() {
                this.bitField0_ &= -2;
                this.sbp_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDbp() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getDbp() {
                return this.dbp_;
            }

            public Builder setDbp(int value) {
                this.bitField0_ |= 2;
                this.dbp_ = value;
                onChanged();
                return this;
            }

            public Builder clearDbp() {
                this.bitField0_ &= -3;
                this.dbp_ = 0;
                onChanged();
                return this;
            }

            public boolean hasTime() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getTime() {
                return this.time_;
            }

            public Builder setTime(int value) {
                this.bitField0_ |= 4;
                this.time_ = value;
                onChanged();
                return this;
            }

            public Builder clearTime() {
                this.bitField0_ &= -5;
                this.time_ = 0;
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

        private HisHealthBp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisHealthBp() {
            this.memoizedIsInitialized = -1;
            this.sbp_ = 0;
            this.dbp_ = 0;
            this.time_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisHealthBp(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.sbp_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.dbp_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.time_ = input.readFixed32();
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
            return HisHealthData.internal_static_HisHealthBp_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisHealthData.internal_static_HisHealthBp_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthBp.class, Builder.class);
        }

        public boolean hasSbp() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getSbp() {
            return this.sbp_;
        }

        public boolean hasDbp() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getDbp() {
            return this.dbp_;
        }

        public boolean hasTime() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getTime() {
            return this.time_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSbp()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDbp()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.sbp_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.dbp_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.time_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.sbp_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.dbp_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.time_);
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
            if (!(obj instanceof HisHealthBp)) {
                return super.equals(obj);
            }
            HisHealthBp other = (HisHealthBp) obj;
            boolean result4 = 1 != 0 && hasSbp() == other.hasSbp();
            if (hasSbp()) {
                result4 = result4 && getSbp() == other.getSbp();
            }
            if (!result4 || hasDbp() != other.hasDbp()) {
                result = false;
            } else {
                result = true;
            }
            if (hasDbp()) {
                if (!result || getDbp() != other.getDbp()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasTime() != other.hasTime()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasTime()) {
                if (!result2 || getTime() != other.getTime()) {
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
            if (hasSbp()) {
                hash = (((hash * 37) + 1) * 53) + getSbp();
            }
            if (hasDbp()) {
                hash = (((hash * 37) + 2) * 53) + getDbp();
            }
            if (hasTime()) {
                hash = (((hash * 37) + 3) * 53) + getTime();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisHealthBp parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisHealthBp) PARSER.parseFrom(data);
        }

        public static HisHealthBp parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthBp) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthBp parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisHealthBp) PARSER.parseFrom(data);
        }

        public static HisHealthBp parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthBp) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthBp parseFrom(InputStream input) throws IOException {
            return (HisHealthBp) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthBp parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthBp) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthBp parseDelimitedFrom(InputStream input) throws IOException {
            return (HisHealthBp) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisHealthBp parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthBp) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthBp parseFrom(CodedInputStream input) throws IOException {
            return (HisHealthBp) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthBp parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthBp) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisHealthBp prototype) {
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

        public static HisHealthBp getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisHealthBp> parser() {
            return PARSER;
        }

        public Parser<HisHealthBp> getParserForType() {
            return PARSER;
        }

        public HisHealthBp getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisHealthHr extends GeneratedMessageV3 implements HisHealthHrOrBuilder {
        public static final int AVG_BPM_FIELD_NUMBER = 3;
        private static final HisHealthHr DEFAULT_INSTANCE = new HisHealthHr();
        public static final int MAX_BPM_FIELD_NUMBER = 2;
        public static final int MIN_BPM_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<HisHealthHr> PARSER = new AbstractParser<HisHealthHr>() {
            public HisHealthHr parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisHealthHr(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int avgBpm_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int maxBpm_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int minBpm_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisHealthHrOrBuilder {
            private int avgBpm_;
            private int bitField0_;
            private int maxBpm_;
            private int minBpm_;

            public static final Descriptor getDescriptor() {
                return HisHealthData.internal_static_HisHealthHr_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisHealthData.internal_static_HisHealthHr_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthHr.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisHealthHr.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.minBpm_ = 0;
                this.bitField0_ &= -2;
                this.maxBpm_ = 0;
                this.bitField0_ &= -3;
                this.avgBpm_ = 0;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisHealthData.internal_static_HisHealthHr_descriptor;
            }

            public HisHealthHr getDefaultInstanceForType() {
                return HisHealthHr.getDefaultInstance();
            }

            public HisHealthHr build() {
                HisHealthHr result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisHealthHr buildPartial() {
                HisHealthHr result = new HisHealthHr((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.minBpm_ = this.minBpm_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.maxBpm_ = this.maxBpm_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.avgBpm_ = this.avgBpm_;
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
                if (other instanceof HisHealthHr) {
                    return mergeFrom((HisHealthHr) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisHealthHr other) {
                if (other != HisHealthHr.getDefaultInstance()) {
                    if (other.hasMinBpm()) {
                        setMinBpm(other.getMinBpm());
                    }
                    if (other.hasMaxBpm()) {
                        setMaxBpm(other.getMaxBpm());
                    }
                    if (other.hasAvgBpm()) {
                        setAvgBpm(other.getAvgBpm());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasMinBpm() && hasMaxBpm() && hasAvgBpm()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisHealthHr parsedMessage = null;
                try {
                    HisHealthHr parsedMessage2 = (HisHealthHr) HisHealthHr.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisHealthHr parsedMessage3 = (HisHealthHr) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasMinBpm() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getMinBpm() {
                return this.minBpm_;
            }

            public Builder setMinBpm(int value) {
                this.bitField0_ |= 1;
                this.minBpm_ = value;
                onChanged();
                return this;
            }

            public Builder clearMinBpm() {
                this.bitField0_ &= -2;
                this.minBpm_ = 0;
                onChanged();
                return this;
            }

            public boolean hasMaxBpm() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getMaxBpm() {
                return this.maxBpm_;
            }

            public Builder setMaxBpm(int value) {
                this.bitField0_ |= 2;
                this.maxBpm_ = value;
                onChanged();
                return this;
            }

            public Builder clearMaxBpm() {
                this.bitField0_ &= -3;
                this.maxBpm_ = 0;
                onChanged();
                return this;
            }

            public boolean hasAvgBpm() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getAvgBpm() {
                return this.avgBpm_;
            }

            public Builder setAvgBpm(int value) {
                this.bitField0_ |= 4;
                this.avgBpm_ = value;
                onChanged();
                return this;
            }

            public Builder clearAvgBpm() {
                this.bitField0_ &= -5;
                this.avgBpm_ = 0;
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

        private HisHealthHr(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisHealthHr() {
            this.memoizedIsInitialized = -1;
            this.minBpm_ = 0;
            this.maxBpm_ = 0;
            this.avgBpm_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisHealthHr(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.minBpm_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.maxBpm_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.avgBpm_ = input.readFixed32();
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
            return HisHealthData.internal_static_HisHealthHr_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisHealthData.internal_static_HisHealthHr_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthHr.class, Builder.class);
        }

        public boolean hasMinBpm() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getMinBpm() {
            return this.minBpm_;
        }

        public boolean hasMaxBpm() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getMaxBpm() {
            return this.maxBpm_;
        }

        public boolean hasAvgBpm() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getAvgBpm() {
            return this.avgBpm_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasMinBpm()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasMaxBpm()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasAvgBpm()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.minBpm_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.maxBpm_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.avgBpm_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.minBpm_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.maxBpm_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.avgBpm_);
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
            if (!(obj instanceof HisHealthHr)) {
                return super.equals(obj);
            }
            HisHealthHr other = (HisHealthHr) obj;
            boolean result4 = 1 != 0 && hasMinBpm() == other.hasMinBpm();
            if (hasMinBpm()) {
                result4 = result4 && getMinBpm() == other.getMinBpm();
            }
            if (!result4 || hasMaxBpm() != other.hasMaxBpm()) {
                result = false;
            } else {
                result = true;
            }
            if (hasMaxBpm()) {
                if (!result || getMaxBpm() != other.getMaxBpm()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasAvgBpm() != other.hasAvgBpm()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasAvgBpm()) {
                if (!result2 || getAvgBpm() != other.getAvgBpm()) {
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
            if (hasMinBpm()) {
                hash = (((hash * 37) + 1) * 53) + getMinBpm();
            }
            if (hasMaxBpm()) {
                hash = (((hash * 37) + 2) * 53) + getMaxBpm();
            }
            if (hasAvgBpm()) {
                hash = (((hash * 37) + 3) * 53) + getAvgBpm();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisHealthHr parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisHealthHr) PARSER.parseFrom(data);
        }

        public static HisHealthHr parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthHr) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthHr parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisHealthHr) PARSER.parseFrom(data);
        }

        public static HisHealthHr parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthHr) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthHr parseFrom(InputStream input) throws IOException {
            return (HisHealthHr) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthHr parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthHr) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthHr parseDelimitedFrom(InputStream input) throws IOException {
            return (HisHealthHr) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisHealthHr parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthHr) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthHr parseFrom(CodedInputStream input) throws IOException {
            return (HisHealthHr) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthHr parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthHr) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisHealthHr prototype) {
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

        public static HisHealthHr getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisHealthHr> parser() {
            return PARSER;
        }

        public Parser<HisHealthHr> getParserForType() {
            return PARSER;
        }

        public HisHealthHr getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisHealthHrv extends GeneratedMessageV3 implements HisHealthHrvOrBuilder {
        private static final HisHealthHrv DEFAULT_INSTANCE = new HisHealthHrv();
        public static final int FATIGUE_FIELD_NUMBER = 5;
        public static final int MEAN_FIELD_NUMBER = 4;
        @Deprecated
        public static final Parser<HisHealthHrv> PARSER = new AbstractParser<HisHealthHrv>() {
            public HisHealthHrv parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisHealthHrv(input, extensionRegistry);
            }
        };
        public static final int PNN50_FIELD_NUMBER = 3;
        public static final int RMSSD_FIELD_NUMBER = 2;
        public static final int SDNN_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public float fatigue_;
        /* access modifiers changed from: private */
        public float mEAN_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public float pNN50_;
        /* access modifiers changed from: private */
        public float rMSSD_;
        /* access modifiers changed from: private */
        public float sDNN_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisHealthHrvOrBuilder {
            private int bitField0_;
            private float fatigue_;
            private float mEAN_;
            private float pNN50_;
            private float rMSSD_;
            private float sDNN_;

            public static final Descriptor getDescriptor() {
                return HisHealthData.internal_static_HisHealthHrv_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisHealthData.internal_static_HisHealthHrv_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthHrv.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisHealthHrv.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.sDNN_ = 0.0f;
                this.bitField0_ &= -2;
                this.rMSSD_ = 0.0f;
                this.bitField0_ &= -3;
                this.pNN50_ = 0.0f;
                this.bitField0_ &= -5;
                this.mEAN_ = 0.0f;
                this.bitField0_ &= -9;
                this.fatigue_ = 0.0f;
                this.bitField0_ &= -17;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisHealthData.internal_static_HisHealthHrv_descriptor;
            }

            public HisHealthHrv getDefaultInstanceForType() {
                return HisHealthHrv.getDefaultInstance();
            }

            public HisHealthHrv build() {
                HisHealthHrv result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisHealthHrv buildPartial() {
                HisHealthHrv result = new HisHealthHrv((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.sDNN_ = this.sDNN_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.rMSSD_ = this.rMSSD_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.pNN50_ = this.pNN50_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.mEAN_ = this.mEAN_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.fatigue_ = this.fatigue_;
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
                if (other instanceof HisHealthHrv) {
                    return mergeFrom((HisHealthHrv) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisHealthHrv other) {
                if (other != HisHealthHrv.getDefaultInstance()) {
                    if (other.hasSDNN()) {
                        setSDNN(other.getSDNN());
                    }
                    if (other.hasRMSSD()) {
                        setRMSSD(other.getRMSSD());
                    }
                    if (other.hasPNN50()) {
                        setPNN50(other.getPNN50());
                    }
                    if (other.hasMEAN()) {
                        setMEAN(other.getMEAN());
                    }
                    if (other.hasFatigue()) {
                        setFatigue(other.getFatigue());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasSDNN() && hasRMSSD() && hasPNN50() && hasMEAN() && hasFatigue()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisHealthHrv parsedMessage = null;
                try {
                    HisHealthHrv parsedMessage2 = (HisHealthHrv) HisHealthHrv.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisHealthHrv parsedMessage3 = (HisHealthHrv) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSDNN() {
                return (this.bitField0_ & 1) == 1;
            }

            public float getSDNN() {
                return this.sDNN_;
            }

            public Builder setSDNN(float value) {
                this.bitField0_ |= 1;
                this.sDNN_ = value;
                onChanged();
                return this;
            }

            public Builder clearSDNN() {
                this.bitField0_ &= -2;
                this.sDNN_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasRMSSD() {
                return (this.bitField0_ & 2) == 2;
            }

            public float getRMSSD() {
                return this.rMSSD_;
            }

            public Builder setRMSSD(float value) {
                this.bitField0_ |= 2;
                this.rMSSD_ = value;
                onChanged();
                return this;
            }

            public Builder clearRMSSD() {
                this.bitField0_ &= -3;
                this.rMSSD_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasPNN50() {
                return (this.bitField0_ & 4) == 4;
            }

            public float getPNN50() {
                return this.pNN50_;
            }

            public Builder setPNN50(float value) {
                this.bitField0_ |= 4;
                this.pNN50_ = value;
                onChanged();
                return this;
            }

            public Builder clearPNN50() {
                this.bitField0_ &= -5;
                this.pNN50_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasMEAN() {
                return (this.bitField0_ & 8) == 8;
            }

            public float getMEAN() {
                return this.mEAN_;
            }

            public Builder setMEAN(float value) {
                this.bitField0_ |= 8;
                this.mEAN_ = value;
                onChanged();
                return this;
            }

            public Builder clearMEAN() {
                this.bitField0_ &= -9;
                this.mEAN_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasFatigue() {
                return (this.bitField0_ & 16) == 16;
            }

            public float getFatigue() {
                return this.fatigue_;
            }

            public Builder setFatigue(float value) {
                this.bitField0_ |= 16;
                this.fatigue_ = value;
                onChanged();
                return this;
            }

            public Builder clearFatigue() {
                this.bitField0_ &= -17;
                this.fatigue_ = 0.0f;
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

        private HisHealthHrv(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisHealthHrv() {
            this.memoizedIsInitialized = -1;
            this.sDNN_ = 0.0f;
            this.rMSSD_ = 0.0f;
            this.pNN50_ = 0.0f;
            this.mEAN_ = 0.0f;
            this.fatigue_ = 0.0f;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisHealthHrv(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.sDNN_ = input.readFloat();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.rMSSD_ = input.readFloat();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.pNN50_ = input.readFloat();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.mEAN_ = input.readFloat();
                            break;
                        case 45:
                            this.bitField0_ |= 16;
                            this.fatigue_ = input.readFloat();
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
            return HisHealthData.internal_static_HisHealthHrv_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisHealthData.internal_static_HisHealthHrv_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthHrv.class, Builder.class);
        }

        public boolean hasSDNN() {
            return (this.bitField0_ & 1) == 1;
        }

        public float getSDNN() {
            return this.sDNN_;
        }

        public boolean hasRMSSD() {
            return (this.bitField0_ & 2) == 2;
        }

        public float getRMSSD() {
            return this.rMSSD_;
        }

        public boolean hasPNN50() {
            return (this.bitField0_ & 4) == 4;
        }

        public float getPNN50() {
            return this.pNN50_;
        }

        public boolean hasMEAN() {
            return (this.bitField0_ & 8) == 8;
        }

        public float getMEAN() {
            return this.mEAN_;
        }

        public boolean hasFatigue() {
            return (this.bitField0_ & 16) == 16;
        }

        public float getFatigue() {
            return this.fatigue_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSDNN()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasRMSSD()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasPNN50()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasMEAN()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasFatigue()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFloat(1, this.sDNN_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFloat(2, this.rMSSD_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFloat(3, this.pNN50_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFloat(4, this.mEAN_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeFloat(5, this.fatigue_);
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
                size2 = 0 + CodedOutputStream.computeFloatSize(1, this.sDNN_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFloatSize(2, this.rMSSD_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFloatSize(3, this.pNN50_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFloatSize(4, this.mEAN_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeFloatSize(5, this.fatigue_);
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
            if (!(obj instanceof HisHealthHrv)) {
                return super.equals(obj);
            }
            HisHealthHrv other = (HisHealthHrv) obj;
            boolean result6 = 1 != 0 && hasSDNN() == other.hasSDNN();
            if (hasSDNN()) {
                result6 = result6 && Float.floatToIntBits(getSDNN()) == Float.floatToIntBits(other.getSDNN());
            }
            if (!result6 || hasRMSSD() != other.hasRMSSD()) {
                result = false;
            } else {
                result = true;
            }
            if (hasRMSSD()) {
                if (!result || Float.floatToIntBits(getRMSSD()) != Float.floatToIntBits(other.getRMSSD())) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasPNN50() != other.hasPNN50()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasPNN50()) {
                if (!result2 || Float.floatToIntBits(getPNN50()) != Float.floatToIntBits(other.getPNN50())) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasMEAN() != other.hasMEAN()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasMEAN()) {
                if (!result3 || Float.floatToIntBits(getMEAN()) != Float.floatToIntBits(other.getMEAN())) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasFatigue() != other.hasFatigue()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasFatigue()) {
                if (!result4 || Float.floatToIntBits(getFatigue()) != Float.floatToIntBits(other.getFatigue())) {
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
            if (hasSDNN()) {
                hash = (((hash * 37) + 1) * 53) + Float.floatToIntBits(getSDNN());
            }
            if (hasRMSSD()) {
                hash = (((hash * 37) + 2) * 53) + Float.floatToIntBits(getRMSSD());
            }
            if (hasPNN50()) {
                hash = (((hash * 37) + 3) * 53) + Float.floatToIntBits(getPNN50());
            }
            if (hasMEAN()) {
                hash = (((hash * 37) + 4) * 53) + Float.floatToIntBits(getMEAN());
            }
            if (hasFatigue()) {
                hash = (((hash * 37) + 5) * 53) + Float.floatToIntBits(getFatigue());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisHealthHrv parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisHealthHrv) PARSER.parseFrom(data);
        }

        public static HisHealthHrv parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthHrv) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthHrv parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisHealthHrv) PARSER.parseFrom(data);
        }

        public static HisHealthHrv parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthHrv) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthHrv parseFrom(InputStream input) throws IOException {
            return (HisHealthHrv) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthHrv parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthHrv) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthHrv parseDelimitedFrom(InputStream input) throws IOException {
            return (HisHealthHrv) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisHealthHrv parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthHrv) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthHrv parseFrom(CodedInputStream input) throws IOException {
            return (HisHealthHrv) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthHrv parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthHrv) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisHealthHrv prototype) {
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

        public static HisHealthHrv getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisHealthHrv> parser() {
            return PARSER;
        }

        public Parser<HisHealthHrv> getParserForType() {
            return PARSER;
        }

        public HisHealthHrv getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisHealthMdt extends GeneratedMessageV3 implements HisHealthMdtOrBuilder {
        private static final HisHealthMdt DEFAULT_INSTANCE = new HisHealthMdt();
        public static final int MEAN_FIELD_NUMBER = 4;
        @Deprecated
        public static final Parser<HisHealthMdt> PARSER = new AbstractParser<HisHealthMdt>() {
            public HisHealthMdt parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisHealthMdt(input, extensionRegistry);
            }
        };
        public static final int PNN50_FIELD_NUMBER = 3;
        public static final int RMSSD_FIELD_NUMBER = 2;
        public static final int SDNN_FIELD_NUMBER = 1;
        public static final int STATUS_FIELD_NUMBER = 5;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public float mEAN_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public float pNN50_;
        /* access modifiers changed from: private */
        public float rMSSD_;
        /* access modifiers changed from: private */
        public float sDNN_;
        /* access modifiers changed from: private */
        public int status_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisHealthMdtOrBuilder {
            private int bitField0_;
            private float mEAN_;
            private float pNN50_;
            private float rMSSD_;
            private float sDNN_;
            private int status_;

            public static final Descriptor getDescriptor() {
                return HisHealthData.internal_static_HisHealthMdt_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisHealthData.internal_static_HisHealthMdt_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthMdt.class, Builder.class);
            }

            private Builder() {
                this.status_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.status_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisHealthMdt.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.sDNN_ = 0.0f;
                this.bitField0_ &= -2;
                this.rMSSD_ = 0.0f;
                this.bitField0_ &= -3;
                this.pNN50_ = 0.0f;
                this.bitField0_ &= -5;
                this.mEAN_ = 0.0f;
                this.bitField0_ &= -9;
                this.status_ = 0;
                this.bitField0_ &= -17;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisHealthData.internal_static_HisHealthMdt_descriptor;
            }

            public HisHealthMdt getDefaultInstanceForType() {
                return HisHealthMdt.getDefaultInstance();
            }

            public HisHealthMdt build() {
                HisHealthMdt result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisHealthMdt buildPartial() {
                HisHealthMdt result = new HisHealthMdt((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.sDNN_ = this.sDNN_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.rMSSD_ = this.rMSSD_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.pNN50_ = this.pNN50_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.mEAN_ = this.mEAN_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.status_ = this.status_;
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
                if (other instanceof HisHealthMdt) {
                    return mergeFrom((HisHealthMdt) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisHealthMdt other) {
                if (other != HisHealthMdt.getDefaultInstance()) {
                    if (other.hasSDNN()) {
                        setSDNN(other.getSDNN());
                    }
                    if (other.hasRMSSD()) {
                        setRMSSD(other.getRMSSD());
                    }
                    if (other.hasPNN50()) {
                        setPNN50(other.getPNN50());
                    }
                    if (other.hasMEAN()) {
                        setMEAN(other.getMEAN());
                    }
                    if (other.hasStatus()) {
                        setStatus(other.getStatus());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasSDNN() && hasRMSSD() && hasPNN50() && hasMEAN() && hasStatus()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisHealthMdt parsedMessage = null;
                try {
                    HisHealthMdt parsedMessage2 = (HisHealthMdt) HisHealthMdt.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisHealthMdt parsedMessage3 = (HisHealthMdt) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSDNN() {
                return (this.bitField0_ & 1) == 1;
            }

            public float getSDNN() {
                return this.sDNN_;
            }

            public Builder setSDNN(float value) {
                this.bitField0_ |= 1;
                this.sDNN_ = value;
                onChanged();
                return this;
            }

            public Builder clearSDNN() {
                this.bitField0_ &= -2;
                this.sDNN_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasRMSSD() {
                return (this.bitField0_ & 2) == 2;
            }

            public float getRMSSD() {
                return this.rMSSD_;
            }

            public Builder setRMSSD(float value) {
                this.bitField0_ |= 2;
                this.rMSSD_ = value;
                onChanged();
                return this;
            }

            public Builder clearRMSSD() {
                this.bitField0_ &= -3;
                this.rMSSD_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasPNN50() {
                return (this.bitField0_ & 4) == 4;
            }

            public float getPNN50() {
                return this.pNN50_;
            }

            public Builder setPNN50(float value) {
                this.bitField0_ |= 4;
                this.pNN50_ = value;
                onChanged();
                return this;
            }

            public Builder clearPNN50() {
                this.bitField0_ &= -5;
                this.pNN50_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasMEAN() {
                return (this.bitField0_ & 8) == 8;
            }

            public float getMEAN() {
                return this.mEAN_;
            }

            public Builder setMEAN(float value) {
                this.bitField0_ |= 8;
                this.mEAN_ = value;
                onChanged();
                return this;
            }

            public Builder clearMEAN() {
                this.bitField0_ &= -9;
                this.mEAN_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasStatus() {
                return (this.bitField0_ & 16) == 16;
            }

            public HisMdtState getStatus() {
                HisMdtState result = HisMdtState.valueOf(this.status_);
                return result == null ? HisMdtState.PREPARE : result;
            }

            public Builder setStatus(HisMdtState value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.status_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearStatus() {
                this.bitField0_ &= -17;
                this.status_ = 0;
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

        private HisHealthMdt(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisHealthMdt() {
            this.memoizedIsInitialized = -1;
            this.sDNN_ = 0.0f;
            this.rMSSD_ = 0.0f;
            this.pNN50_ = 0.0f;
            this.mEAN_ = 0.0f;
            this.status_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisHealthMdt(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.sDNN_ = input.readFloat();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.rMSSD_ = input.readFloat();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.pNN50_ = input.readFloat();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.mEAN_ = input.readFloat();
                            break;
                        case 40:
                            int rawValue = input.readEnum();
                            if (HisMdtState.valueOf(rawValue) != null) {
                                this.bitField0_ |= 16;
                                this.status_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(5, rawValue);
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
            return HisHealthData.internal_static_HisHealthMdt_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisHealthData.internal_static_HisHealthMdt_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthMdt.class, Builder.class);
        }

        public boolean hasSDNN() {
            return (this.bitField0_ & 1) == 1;
        }

        public float getSDNN() {
            return this.sDNN_;
        }

        public boolean hasRMSSD() {
            return (this.bitField0_ & 2) == 2;
        }

        public float getRMSSD() {
            return this.rMSSD_;
        }

        public boolean hasPNN50() {
            return (this.bitField0_ & 4) == 4;
        }

        public float getPNN50() {
            return this.pNN50_;
        }

        public boolean hasMEAN() {
            return (this.bitField0_ & 8) == 8;
        }

        public float getMEAN() {
            return this.mEAN_;
        }

        public boolean hasStatus() {
            return (this.bitField0_ & 16) == 16;
        }

        public HisMdtState getStatus() {
            HisMdtState result = HisMdtState.valueOf(this.status_);
            return result == null ? HisMdtState.PREPARE : result;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSDNN()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasRMSSD()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasPNN50()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasMEAN()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasStatus()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFloat(1, this.sDNN_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFloat(2, this.rMSSD_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFloat(3, this.pNN50_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFloat(4, this.mEAN_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeEnum(5, this.status_);
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
                size2 = 0 + CodedOutputStream.computeFloatSize(1, this.sDNN_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFloatSize(2, this.rMSSD_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFloatSize(3, this.pNN50_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFloatSize(4, this.mEAN_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeEnumSize(5, this.status_);
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
            if (!(obj instanceof HisHealthMdt)) {
                return super.equals(obj);
            }
            HisHealthMdt other = (HisHealthMdt) obj;
            boolean result6 = 1 != 0 && hasSDNN() == other.hasSDNN();
            if (hasSDNN()) {
                result6 = result6 && Float.floatToIntBits(getSDNN()) == Float.floatToIntBits(other.getSDNN());
            }
            if (!result6 || hasRMSSD() != other.hasRMSSD()) {
                result = false;
            } else {
                result = true;
            }
            if (hasRMSSD()) {
                if (!result || Float.floatToIntBits(getRMSSD()) != Float.floatToIntBits(other.getRMSSD())) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasPNN50() != other.hasPNN50()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasPNN50()) {
                if (!result2 || Float.floatToIntBits(getPNN50()) != Float.floatToIntBits(other.getPNN50())) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasMEAN() != other.hasMEAN()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasMEAN()) {
                if (!result3 || Float.floatToIntBits(getMEAN()) != Float.floatToIntBits(other.getMEAN())) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasStatus() != other.hasStatus()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasStatus()) {
                if (!result4 || this.status_ != other.status_) {
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
            if (hasSDNN()) {
                hash = (((hash * 37) + 1) * 53) + Float.floatToIntBits(getSDNN());
            }
            if (hasRMSSD()) {
                hash = (((hash * 37) + 2) * 53) + Float.floatToIntBits(getRMSSD());
            }
            if (hasPNN50()) {
                hash = (((hash * 37) + 3) * 53) + Float.floatToIntBits(getPNN50());
            }
            if (hasMEAN()) {
                hash = (((hash * 37) + 4) * 53) + Float.floatToIntBits(getMEAN());
            }
            if (hasStatus()) {
                hash = (((hash * 37) + 5) * 53) + this.status_;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisHealthMdt parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisHealthMdt) PARSER.parseFrom(data);
        }

        public static HisHealthMdt parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthMdt) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthMdt parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisHealthMdt) PARSER.parseFrom(data);
        }

        public static HisHealthMdt parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthMdt) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthMdt parseFrom(InputStream input) throws IOException {
            return (HisHealthMdt) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthMdt parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthMdt) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthMdt parseDelimitedFrom(InputStream input) throws IOException {
            return (HisHealthMdt) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisHealthMdt parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthMdt) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthMdt parseFrom(CodedInputStream input) throws IOException {
            return (HisHealthMdt) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthMdt parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthMdt) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisHealthMdt prototype) {
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

        public static HisHealthMdt getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisHealthMdt> parser() {
            return PARSER;
        }

        public Parser<HisHealthMdt> getParserForType() {
            return PARSER;
        }

        public HisHealthMdt getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisHealthPedo extends GeneratedMessageV3 implements HisHealthPedoOrBuilder {
        public static final int CALORIE_FIELD_NUMBER = 3;
        private static final HisHealthPedo DEFAULT_INSTANCE = new HisHealthPedo();
        public static final int DISTANCE_FIELD_NUMBER = 5;
        @Deprecated
        public static final Parser<HisHealthPedo> PARSER = new AbstractParser<HisHealthPedo>() {
            public HisHealthPedo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisHealthPedo(input, extensionRegistry);
            }
        };
        public static final int STATE_FIELD_NUMBER = 2;
        public static final int STEP_FIELD_NUMBER = 4;
        public static final int TYPE_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int calorie_;
        /* access modifiers changed from: private */
        public int distance_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int state_;
        /* access modifiers changed from: private */
        public int step_;
        /* access modifiers changed from: private */
        public int type_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisHealthPedoOrBuilder {
            private int bitField0_;
            private int calorie_;
            private int distance_;
            private int state_;
            private int step_;
            private int type_;

            public static final Descriptor getDescriptor() {
                return HisHealthData.internal_static_HisHealthPedo_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisHealthData.internal_static_HisHealthPedo_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthPedo.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisHealthPedo.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.type_ = 0;
                this.bitField0_ &= -2;
                this.state_ = 0;
                this.bitField0_ &= -3;
                this.calorie_ = 0;
                this.bitField0_ &= -5;
                this.step_ = 0;
                this.bitField0_ &= -9;
                this.distance_ = 0;
                this.bitField0_ &= -17;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisHealthData.internal_static_HisHealthPedo_descriptor;
            }

            public HisHealthPedo getDefaultInstanceForType() {
                return HisHealthPedo.getDefaultInstance();
            }

            public HisHealthPedo build() {
                HisHealthPedo result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisHealthPedo buildPartial() {
                HisHealthPedo result = new HisHealthPedo((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.state_ = this.state_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.calorie_ = this.calorie_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.step_ = this.step_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.distance_ = this.distance_;
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
                if (other instanceof HisHealthPedo) {
                    return mergeFrom((HisHealthPedo) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisHealthPedo other) {
                if (other != HisHealthPedo.getDefaultInstance()) {
                    if (other.hasType()) {
                        setType(other.getType());
                    }
                    if (other.hasState()) {
                        setState(other.getState());
                    }
                    if (other.hasCalorie()) {
                        setCalorie(other.getCalorie());
                    }
                    if (other.hasStep()) {
                        setStep(other.getStep());
                    }
                    if (other.hasDistance()) {
                        setDistance(other.getDistance());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasType() && hasState() && hasCalorie() && hasStep() && hasDistance()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisHealthPedo parsedMessage = null;
                try {
                    HisHealthPedo parsedMessage2 = (HisHealthPedo) HisHealthPedo.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisHealthPedo parsedMessage3 = (HisHealthPedo) e.getUnfinishedMessage();
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

            public int getType() {
                return this.type_;
            }

            public Builder setType(int value) {
                this.bitField0_ |= 1;
                this.type_ = value;
                onChanged();
                return this;
            }

            public Builder clearType() {
                this.bitField0_ &= -2;
                this.type_ = 0;
                onChanged();
                return this;
            }

            public boolean hasState() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getState() {
                return this.state_;
            }

            public Builder setState(int value) {
                this.bitField0_ |= 2;
                this.state_ = value;
                onChanged();
                return this;
            }

            public Builder clearState() {
                this.bitField0_ &= -3;
                this.state_ = 0;
                onChanged();
                return this;
            }

            public boolean hasCalorie() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getCalorie() {
                return this.calorie_;
            }

            public Builder setCalorie(int value) {
                this.bitField0_ |= 4;
                this.calorie_ = value;
                onChanged();
                return this;
            }

            public Builder clearCalorie() {
                this.bitField0_ &= -5;
                this.calorie_ = 0;
                onChanged();
                return this;
            }

            public boolean hasStep() {
                return (this.bitField0_ & 8) == 8;
            }

            public int getStep() {
                return this.step_;
            }

            public Builder setStep(int value) {
                this.bitField0_ |= 8;
                this.step_ = value;
                onChanged();
                return this;
            }

            public Builder clearStep() {
                this.bitField0_ &= -9;
                this.step_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDistance() {
                return (this.bitField0_ & 16) == 16;
            }

            public int getDistance() {
                return this.distance_;
            }

            public Builder setDistance(int value) {
                this.bitField0_ |= 16;
                this.distance_ = value;
                onChanged();
                return this;
            }

            public Builder clearDistance() {
                this.bitField0_ &= -17;
                this.distance_ = 0;
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

        private HisHealthPedo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HisHealthPedo() {
            this.memoizedIsInitialized = -1;
            this.type_ = 0;
            this.state_ = 0;
            this.calorie_ = 0;
            this.step_ = 0;
            this.distance_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisHealthPedo(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.type_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.state_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.calorie_ = input.readFixed32();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.step_ = input.readFixed32();
                            break;
                        case 45:
                            this.bitField0_ |= 16;
                            this.distance_ = input.readFixed32();
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
            return HisHealthData.internal_static_HisHealthPedo_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisHealthData.internal_static_HisHealthPedo_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthPedo.class, Builder.class);
        }

        public boolean hasType() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getType() {
            return this.type_;
        }

        public boolean hasState() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getState() {
            return this.state_;
        }

        public boolean hasCalorie() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getCalorie() {
            return this.calorie_;
        }

        public boolean hasStep() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getStep() {
            return this.step_;
        }

        public boolean hasDistance() {
            return (this.bitField0_ & 16) == 16;
        }

        public int getDistance() {
            return this.distance_;
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
            } else if (!hasState()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasCalorie()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasStep()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDistance()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.type_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.state_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.calorie_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFixed32(4, this.step_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeFixed32(5, this.distance_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.type_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.state_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.calorie_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFixed32Size(4, this.step_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeFixed32Size(5, this.distance_);
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
            if (!(obj instanceof HisHealthPedo)) {
                return super.equals(obj);
            }
            HisHealthPedo other = (HisHealthPedo) obj;
            boolean result6 = 1 != 0 && hasType() == other.hasType();
            if (hasType()) {
                result6 = result6 && getType() == other.getType();
            }
            if (!result6 || hasState() != other.hasState()) {
                result = false;
            } else {
                result = true;
            }
            if (hasState()) {
                if (!result || getState() != other.getState()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasCalorie() != other.hasCalorie()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasCalorie()) {
                if (!result2 || getCalorie() != other.getCalorie()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasStep() != other.hasStep()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasStep()) {
                if (!result3 || getStep() != other.getStep()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasDistance() != other.hasDistance()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasDistance()) {
                if (!result4 || getDistance() != other.getDistance()) {
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
            if (hasType()) {
                hash = (((hash * 37) + 1) * 53) + getType();
            }
            if (hasState()) {
                hash = (((hash * 37) + 2) * 53) + getState();
            }
            if (hasCalorie()) {
                hash = (((hash * 37) + 3) * 53) + getCalorie();
            }
            if (hasStep()) {
                hash = (((hash * 37) + 4) * 53) + getStep();
            }
            if (hasDistance()) {
                hash = (((hash * 37) + 5) * 53) + getDistance();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisHealthPedo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisHealthPedo) PARSER.parseFrom(data);
        }

        public static HisHealthPedo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthPedo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthPedo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisHealthPedo) PARSER.parseFrom(data);
        }

        public static HisHealthPedo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthPedo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthPedo parseFrom(InputStream input) throws IOException {
            return (HisHealthPedo) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthPedo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthPedo) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthPedo parseDelimitedFrom(InputStream input) throws IOException {
            return (HisHealthPedo) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisHealthPedo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthPedo) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthPedo parseFrom(CodedInputStream input) throws IOException {
            return (HisHealthPedo) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthPedo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthPedo) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisHealthPedo prototype) {
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

        public static HisHealthPedo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisHealthPedo> parser() {
            return PARSER;
        }

        public Parser<HisHealthPedo> getParserForType() {
            return PARSER;
        }

        public HisHealthPedo getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HisHealthSleep extends GeneratedMessageV3 implements HisHealthSleepOrBuilder {
        public static final int CHARGE_FIELD_NUMBER = 3;
        private static final HisHealthSleep DEFAULT_INSTANCE = new HisHealthSleep();
        @Deprecated
        public static final Parser<HisHealthSleep> PARSER = new AbstractParser<HisHealthSleep>() {
            public HisHealthSleep parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HisHealthSleep(input, extensionRegistry);
            }
        };
        public static final int SHUT_DOWN_FIELD_NUMBER = 2;
        public static final int SLEEP_DATA_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public boolean charge_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public boolean shutDown_;
        private int sleepDataMemoizedSerializedSize;
        /* access modifiers changed from: private */
        public List<Integer> sleepData_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HisHealthSleepOrBuilder {
            private int bitField0_;
            private boolean charge_;
            private boolean shutDown_;
            private List<Integer> sleepData_;

            public static final Descriptor getDescriptor() {
                return HisHealthData.internal_static_HisHealthSleep_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return HisHealthData.internal_static_HisHealthSleep_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthSleep.class, Builder.class);
            }

            private Builder() {
                this.sleepData_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.sleepData_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HisHealthSleep.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.sleepData_ = Collections.emptyList();
                this.bitField0_ &= -2;
                this.shutDown_ = false;
                this.bitField0_ &= -3;
                this.charge_ = false;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return HisHealthData.internal_static_HisHealthSleep_descriptor;
            }

            public HisHealthSleep getDefaultInstanceForType() {
                return HisHealthSleep.getDefaultInstance();
            }

            public HisHealthSleep build() {
                HisHealthSleep result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HisHealthSleep buildPartial() {
                HisHealthSleep result = new HisHealthSleep((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    this.sleepData_ = Collections.unmodifiableList(this.sleepData_);
                    this.bitField0_ &= -2;
                }
                result.sleepData_ = this.sleepData_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ = 0 | 1;
                }
                result.shutDown_ = this.shutDown_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 2;
                }
                result.charge_ = this.charge_;
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
                if (other instanceof HisHealthSleep) {
                    return mergeFrom((HisHealthSleep) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HisHealthSleep other) {
                if (other != HisHealthSleep.getDefaultInstance()) {
                    if (!other.sleepData_.isEmpty()) {
                        if (this.sleepData_.isEmpty()) {
                            this.sleepData_ = other.sleepData_;
                            this.bitField0_ &= -2;
                        } else {
                            ensureSleepDataIsMutable();
                            this.sleepData_.addAll(other.sleepData_);
                        }
                        onChanged();
                    }
                    if (other.hasShutDown()) {
                        setShutDown(other.getShutDown());
                    }
                    if (other.hasCharge()) {
                        setCharge(other.getCharge());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasShutDown() && hasCharge()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HisHealthSleep parsedMessage = null;
                try {
                    HisHealthSleep parsedMessage2 = (HisHealthSleep) HisHealthSleep.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HisHealthSleep parsedMessage3 = (HisHealthSleep) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            private void ensureSleepDataIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.sleepData_ = new ArrayList(this.sleepData_);
                    this.bitField0_ |= 1;
                }
            }

            public List<Integer> getSleepDataList() {
                return Collections.unmodifiableList(this.sleepData_);
            }

            public int getSleepDataCount() {
                return this.sleepData_.size();
            }

            public int getSleepData(int index) {
                return ((Integer) this.sleepData_.get(index)).intValue();
            }

            public Builder setSleepData(int index, int value) {
                ensureSleepDataIsMutable();
                this.sleepData_.set(index, Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addSleepData(int value) {
                ensureSleepDataIsMutable();
                this.sleepData_.add(Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addAllSleepData(Iterable<? extends Integer> values) {
                ensureSleepDataIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.sleepData_);
                onChanged();
                return this;
            }

            public Builder clearSleepData() {
                this.sleepData_ = Collections.emptyList();
                this.bitField0_ &= -2;
                onChanged();
                return this;
            }

            public boolean hasShutDown() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getShutDown() {
                return this.shutDown_;
            }

            public Builder setShutDown(boolean value) {
                this.bitField0_ |= 2;
                this.shutDown_ = value;
                onChanged();
                return this;
            }

            public Builder clearShutDown() {
                this.bitField0_ &= -3;
                this.shutDown_ = false;
                onChanged();
                return this;
            }

            public boolean hasCharge() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getCharge() {
                return this.charge_;
            }

            public Builder setCharge(boolean value) {
                this.bitField0_ |= 4;
                this.charge_ = value;
                onChanged();
                return this;
            }

            public Builder clearCharge() {
                this.bitField0_ &= -5;
                this.charge_ = false;
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

        private HisHealthSleep(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.sleepDataMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = -1;
        }

        private HisHealthSleep() {
            this.sleepDataMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = -1;
            this.sleepData_ = Collections.emptyList();
            this.shutDown_ = false;
            this.charge_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HisHealthSleep(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            int limit = input.pushLimit(input.readRawVarint32());
                            if ((mutable_bitField0_ & 1) != 1 && input.getBytesUntilLimit() > 0) {
                                this.sleepData_ = new ArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.sleepData_.add(Integer.valueOf(input.readFixed32()));
                            }
                            input.popLimit(limit);
                            break;
                        case 13:
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.sleepData_ = new ArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            this.sleepData_.add(Integer.valueOf(input.readFixed32()));
                            break;
                        case 16:
                            this.bitField0_ |= 1;
                            this.shutDown_ = input.readBool();
                            break;
                        case 24:
                            this.bitField0_ |= 2;
                            this.charge_ = input.readBool();
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
                        this.sleepData_ = Collections.unmodifiableList(this.sleepData_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 1) == 1) {
                this.sleepData_ = Collections.unmodifiableList(this.sleepData_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return HisHealthData.internal_static_HisHealthSleep_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return HisHealthData.internal_static_HisHealthSleep_fieldAccessorTable.ensureFieldAccessorsInitialized(HisHealthSleep.class, Builder.class);
        }

        public List<Integer> getSleepDataList() {
            return this.sleepData_;
        }

        public int getSleepDataCount() {
            return this.sleepData_.size();
        }

        public int getSleepData(int index) {
            return ((Integer) this.sleepData_.get(index)).intValue();
        }

        public boolean hasShutDown() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getShutDown() {
            return this.shutDown_;
        }

        public boolean hasCharge() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getCharge() {
            return this.charge_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasShutDown()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasCharge()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            if (getSleepDataList().size() > 0) {
                output.writeUInt32NoTag(10);
                output.writeUInt32NoTag(this.sleepDataMemoizedSerializedSize);
            }
            for (int i = 0; i < this.sleepData_.size(); i++) {
                output.writeFixed32NoTag(((Integer) this.sleepData_.get(i)).intValue());
            }
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(2, this.shutDown_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(3, this.charge_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int dataSize = getSleepDataList().size() * 4;
            int size2 = 0 + dataSize;
            if (!getSleepDataList().isEmpty()) {
                size2 = size2 + 1 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }
            this.sleepDataMemoizedSerializedSize = dataSize;
            if ((this.bitField0_ & 1) == 1) {
                size2 += CodedOutputStream.computeBoolSize(2, this.shutDown_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(3, this.charge_);
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
            if (!(obj instanceof HisHealthSleep)) {
                return super.equals(obj);
            }
            HisHealthSleep other = (HisHealthSleep) obj;
            if (!(1 != 0 && getSleepDataList().equals(other.getSleepDataList())) || hasShutDown() != other.hasShutDown()) {
                result = false;
            } else {
                result = true;
            }
            if (hasShutDown()) {
                if (!result || getShutDown() != other.getShutDown()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasCharge() != other.hasCharge()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasCharge()) {
                if (!result2 || getCharge() != other.getCharge()) {
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
            if (getSleepDataCount() > 0) {
                hash = (((hash * 37) + 1) * 53) + getSleepDataList().hashCode();
            }
            if (hasShutDown()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getShutDown());
            }
            if (hasCharge()) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashBoolean(getCharge());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HisHealthSleep parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HisHealthSleep) PARSER.parseFrom(data);
        }

        public static HisHealthSleep parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthSleep) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthSleep parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HisHealthSleep) PARSER.parseFrom(data);
        }

        public static HisHealthSleep parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HisHealthSleep) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HisHealthSleep parseFrom(InputStream input) throws IOException {
            return (HisHealthSleep) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthSleep parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthSleep) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthSleep parseDelimitedFrom(InputStream input) throws IOException {
            return (HisHealthSleep) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HisHealthSleep parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthSleep) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HisHealthSleep parseFrom(CodedInputStream input) throws IOException {
            return (HisHealthSleep) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HisHealthSleep parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HisHealthSleep) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HisHealthSleep prototype) {
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

        public static HisHealthSleep getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HisHealthSleep> parser() {
            return PARSER;
        }

        public Parser<HisHealthSleep> getParserForType() {
            return PARSER;
        }

        public HisHealthSleep getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum HisMdtState implements ProtocolMessageEnum {
        PREPARE(0),
        ONGOING(1),
        RELAX(2);
        
        public static final int ONGOING_VALUE = 1;
        public static final int PREPARE_VALUE = 0;
        public static final int RELAX_VALUE = 2;
        private static final HisMdtState[] VALUES = null;
        private static final EnumLiteMap<HisMdtState> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<HisMdtState>() {
                public HisMdtState findValueByNumber(int number) {
                    return HisMdtState.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static HisMdtState valueOf(int value2) {
            return forNumber(value2);
        }

        public static HisMdtState forNumber(int value2) {
            switch (value2) {
                case 0:
                    return PREPARE;
                case 1:
                    return ONGOING;
                case 2:
                    return RELAX;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<HisMdtState> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) HisHealthData.getDescriptor().getEnumTypes().get(0);
        }

        public static HisMdtState valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private HisMdtState(int value2) {
            this.value = value2;
        }
    }

    public interface HisDataHealthOrBuilder extends MessageOrBuilder {
        HisHealthAf getAfData();

        HisHealthAfOrBuilder getAfDataOrBuilder();

        HisHealthBp getBpData();

        HisHealthBpOrBuilder getBpDataOrBuilder();

        HisHealthHr getHrData();

        HisHealthHrOrBuilder getHrDataOrBuilder();

        HisHealthHrv getHrvData();

        HisHealthHrvOrBuilder getHrvDataOrBuilder();

        HisHealthMdt getMdtData();

        HisHealthMdtOrBuilder getMdtDataOrBuilder();

        HisHealthPedo getPedoData();

        HisHealthPedoOrBuilder getPedoDataOrBuilder();

        HisHealthSleep getSleepData();

        HisHealthSleepOrBuilder getSleepDataOrBuilder();

        DateTime getTimeStamp();

        DateTimeOrBuilder getTimeStampOrBuilder();

        boolean hasAfData();

        boolean hasBpData();

        boolean hasHrData();

        boolean hasHrvData();

        boolean hasMdtData();

        boolean hasPedoData();

        boolean hasSleepData();

        boolean hasTimeStamp();
    }

    public interface HisHealthAfOrBuilder extends MessageOrBuilder {
        int getAf();

        boolean hasAf();
    }

    public interface HisHealthBpOrBuilder extends MessageOrBuilder {
        int getDbp();

        int getSbp();

        int getTime();

        boolean hasDbp();

        boolean hasSbp();

        boolean hasTime();
    }

    public interface HisHealthHrOrBuilder extends MessageOrBuilder {
        int getAvgBpm();

        int getMaxBpm();

        int getMinBpm();

        boolean hasAvgBpm();

        boolean hasMaxBpm();

        boolean hasMinBpm();
    }

    public interface HisHealthHrvOrBuilder extends MessageOrBuilder {
        float getFatigue();

        float getMEAN();

        float getPNN50();

        float getRMSSD();

        float getSDNN();

        boolean hasFatigue();

        boolean hasMEAN();

        boolean hasPNN50();

        boolean hasRMSSD();

        boolean hasSDNN();
    }

    public interface HisHealthMdtOrBuilder extends MessageOrBuilder {
        float getMEAN();

        float getPNN50();

        float getRMSSD();

        float getSDNN();

        HisMdtState getStatus();

        boolean hasMEAN();

        boolean hasPNN50();

        boolean hasRMSSD();

        boolean hasSDNN();

        boolean hasStatus();
    }

    public interface HisHealthPedoOrBuilder extends MessageOrBuilder {
        int getCalorie();

        int getDistance();

        int getState();

        int getStep();

        int getType();

        boolean hasCalorie();

        boolean hasDistance();

        boolean hasState();

        boolean hasStep();

        boolean hasType();
    }

    public interface HisHealthSleepOrBuilder extends MessageOrBuilder {
        boolean getCharge();

        boolean getShutDown();

        int getSleepData(int i);

        int getSleepDataCount();

        List<Integer> getSleepDataList();

        boolean hasCharge();

        boolean hasShutDown();
    }

    private HisHealthData() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0015his_health_data.proto\u001a\u0013realtime_data.proto\"]\n\rHisHealthPedo\u0012\f\n\u0004type\u0018\u0001 \u0002(\u0007\u0012\r\n\u0005state\u0018\u0002 \u0002(\u0007\u0012\u000f\n\u0007calorie\u0018\u0003 \u0002(\u0007\u0012\f\n\u0004step\u0018\u0004 \u0002(\u0007\u0012\u0010\n\bdistance\u0018\u0005 \u0002(\u0007\"@\n\u000bHisHealthHr\u0012\u000f\n\u0007min_bpm\u0018\u0001 \u0002(\u0007\u0012\u000f\n\u0007max_bpm\u0018\u0002 \u0002(\u0007\u0012\u000f\n\u0007avg_bpm\u0018\u0003 \u0002(\u0007\"Y\n\fHisHealthHrv\u0012\f\n\u0004SDNN\u0018\u0001 \u0002(\u0002\u0012\r\n\u0005RMSSD\u0018\u0002 \u0002(\u0002\u0012\r\n\u0005PNN50\u0018\u0003 \u0002(\u0002\u0012\f\n\u0004MEAN\u0018\u0004 \u0002(\u0002\u0012\u000f\n\u0007fatigue\u0018\u0005 \u0002(\u0002\"f\n\fHisHealthMdt\u0012\f\n\u0004SDNN\u0018\u0001 \u0002(\u0002\u0012\r\n\u0005RMSSD\u0018\u0002 \u0002(\u0002\u0012\r\n\u0005PNN50\u0018\u0003 \u0002(\u0002\u0012\f\n\u0004MEAN\u0018\u0004 \u0002(\u0002\u0012\u001c\n\u0006status\u0018\u0005 \u0002(\u000e2\f.HisMdtState", "\"5\n\u000bHisHealthBp\u0012\u000b\n\u0003sbp\u0018\u0001 \u0002(\u0007\u0012\u000b\n\u0003dbp\u0018\u0002 \u0002(\u0007\u0012\f\n\u0004time\u0018\u0003 \u0001(\u0007\"\u0019\n\u000bHisHealthAf\u0012\n\n\u0002af\u0018\u0001 \u0002(\u000f\"K\n\u000eHisHealthSleep\u0012\u0016\n\nsleep_data\u0018\u0001 \u0003(\u0007B\u0002\u0010\u0001\u0012\u0011\n\tshut_down\u0018\u0002 \u0002(\b\u0012\u000e\n\u0006charge\u0018\u0003 \u0002(\b\"\u0002\n\rHisDataHealth\u0012\u001d\n\ntime_stamp\u0018\u0001 \u0002(\u000b2\t.DateTime\u0012#\n\nsleep_data\u0018\u0002 \u0001(\u000b2\u000f.HisHealthSleep\u0012!\n\tpedo_data\u0018\u0003 \u0001(\u000b2\u000e.HisHealthPedo\u0012\u001d\n\u0007hr_data\u0018\u0004 \u0001(\u000b2\f.HisHealthHr\u0012\u001f\n\bhrv_data\u0018\u0005 \u0001(\u000b2\r.HisHealthHrv\u0012\u001d\n\u0007bp_data\u0018\u0006 \u0001(\u000b2\f.HisHealthBp\u0012\u001d\n\u0007af_data\u0018\u0007 \u0001(\u000b2\f.HisHe", "althAf\u0012\u001f\n\bmdt_data\u0018\b \u0001(\u000b2\r.HisHealthMdt*2\n\u000bHisMdtState\u0012\u000b\n\u0007PREPARE\u0010\u0000\u0012\u000b\n\u0007ONGOING\u0010\u0001\u0012\t\n\u0005RELAX\u0010\u0002"}, fileDescriptorArr, new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                HisHealthData.descriptor = root;
                return null;
            }
        });
        RealtimeData.getDescriptor();
    }
}
