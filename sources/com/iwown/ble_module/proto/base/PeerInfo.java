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
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.IOException;
import java.io.InputStream;

public final class PeerInfo {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_AfConf_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(6));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_AfConf_fieldAccessorTable = new FieldAccessorTable(internal_static_AfConf_descriptor, new String[]{"Hash", "AutoRun", "Interval"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_BpCaliConf_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(5));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_BpCaliConf_fieldAccessorTable = new FieldAccessorTable(internal_static_BpCaliConf_descriptor, new String[]{"Hash", "SrcSbp", "SrcDbp", "DstSbp", "DstDbp", "DifSbp", "DifDbp"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_GnssConf_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_GnssConf_fieldAccessorTable = new FieldAccessorTable(internal_static_GnssConf_descriptor, new String[]{"Hash", "Altitude", "Latitude", "Longitude"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_GoalConf_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_GoalConf_fieldAccessorTable = new FieldAccessorTable(internal_static_GoalConf_descriptor, new String[]{"Hash", "Distance", "Step", "Calorie"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_HrAlarmConf_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_HrAlarmConf_fieldAccessorTable = new FieldAccessorTable(internal_static_HrAlarmConf_descriptor, new String[]{"Hash", "Enable", "ThsHigh", "ThsLow", "Timeout", "Interval"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_PeerInfoNotification_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(7));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_PeerInfoNotification_fieldAccessorTable = new FieldAccessorTable(internal_static_PeerInfoNotification_descriptor, new String[]{"PeerType", "PeerStatus", "DateTime", "GnssConf", "HrAlarmConf", "UserConf", "GoalConf", "BpcaliConf", "AfConf"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_PeerInfoSubsriber_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_PeerInfoSubsriber_fieldAccessorTable = new FieldAccessorTable(internal_static_PeerInfoSubsriber_descriptor, new String[]{"SupportPeerType", "SupportPeerStatus", "SupportDateTime", "SupportGnssConf", "SupportHrAlarmConf", "SupportUserConf", "SupportGoalConf", "HashGnssConf", "HashHrAlarmConf", "HashUserConf", "HashGoalConf", "SupportBpcaliConf", "HashBpcaliConf", "SupportAfConf", "HashAfConf"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_UserConf_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_UserConf_fieldAccessorTable = new FieldAccessorTable(internal_static_UserConf_descriptor, new String[]{"Hash", "Height", "Weight", "Gender", HttpHeaders.AGE, "CalibWalk", "CalibRun"});

    public static final class AfConf extends GeneratedMessageV3 implements AfConfOrBuilder {
        public static final int AUTO_RUN_FIELD_NUMBER = 2;
        private static final AfConf DEFAULT_INSTANCE = new AfConf();
        public static final int HASH_FIELD_NUMBER = 1;
        public static final int INTERVAL_FIELD_NUMBER = 3;
        @Deprecated
        public static final Parser<AfConf> PARSER = new AbstractParser<AfConf>() {
            public AfConf parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AfConf(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public boolean autoRun_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int hash_;
        /* access modifiers changed from: private */
        public int interval_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements AfConfOrBuilder {
            private boolean autoRun_;
            private int bitField0_;
            private int hash_;
            private int interval_;

            public static final Descriptor getDescriptor() {
                return PeerInfo.internal_static_AfConf_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PeerInfo.internal_static_AfConf_fieldAccessorTable.ensureFieldAccessorsInitialized(AfConf.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (AfConf.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.autoRun_ = false;
                this.bitField0_ &= -3;
                this.interval_ = 0;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return PeerInfo.internal_static_AfConf_descriptor;
            }

            public AfConf getDefaultInstanceForType() {
                return AfConf.getDefaultInstance();
            }

            public AfConf build() {
                AfConf result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public AfConf buildPartial() {
                AfConf result = new AfConf((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.autoRun_ = this.autoRun_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.interval_ = this.interval_;
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
                if (other instanceof AfConf) {
                    return mergeFrom((AfConf) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AfConf other) {
                if (other != AfConf.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (other.hasAutoRun()) {
                        setAutoRun(other.getAutoRun());
                    }
                    if (other.hasInterval()) {
                        setInterval(other.getInterval());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasHash() && hasAutoRun() && hasInterval()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                AfConf parsedMessage = null;
                try {
                    AfConf parsedMessage2 = (AfConf) AfConf.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    AfConf parsedMessage3 = (AfConf) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasHash() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getHash() {
                return this.hash_;
            }

            public Builder setHash(int value) {
                this.bitField0_ |= 1;
                this.hash_ = value;
                onChanged();
                return this;
            }

            public Builder clearHash() {
                this.bitField0_ &= -2;
                this.hash_ = 0;
                onChanged();
                return this;
            }

            public boolean hasAutoRun() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getAutoRun() {
                return this.autoRun_;
            }

            public Builder setAutoRun(boolean value) {
                this.bitField0_ |= 2;
                this.autoRun_ = value;
                onChanged();
                return this;
            }

            public Builder clearAutoRun() {
                this.bitField0_ &= -3;
                this.autoRun_ = false;
                onChanged();
                return this;
            }

            public boolean hasInterval() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getInterval() {
                return this.interval_;
            }

            public Builder setInterval(int value) {
                this.bitField0_ |= 4;
                this.interval_ = value;
                onChanged();
                return this;
            }

            public Builder clearInterval() {
                this.bitField0_ &= -5;
                this.interval_ = 0;
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

        private AfConf(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private AfConf() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.autoRun_ = false;
            this.interval_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AfConf(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.hash_ = input.readFixed32();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.autoRun_ = input.readBool();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.interval_ = input.readFixed32();
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
            return PeerInfo.internal_static_AfConf_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return PeerInfo.internal_static_AfConf_fieldAccessorTable.ensureFieldAccessorsInitialized(AfConf.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasAutoRun() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getAutoRun() {
            return this.autoRun_;
        }

        public boolean hasInterval() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getInterval() {
            return this.interval_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasHash()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasAutoRun()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasInterval()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.autoRun_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.interval_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.autoRun_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.interval_);
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
            if (!(obj instanceof AfConf)) {
                return super.equals(obj);
            }
            AfConf other = (AfConf) obj;
            boolean result4 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result4 = result4 && getHash() == other.getHash();
            }
            if (!result4 || hasAutoRun() != other.hasAutoRun()) {
                result = false;
            } else {
                result = true;
            }
            if (hasAutoRun()) {
                if (!result || getAutoRun() != other.getAutoRun()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasInterval() != other.hasInterval()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasInterval()) {
                if (!result2 || getInterval() != other.getInterval()) {
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
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            if (hasAutoRun()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getAutoRun());
            }
            if (hasInterval()) {
                hash = (((hash * 37) + 3) * 53) + getInterval();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static AfConf parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AfConf) PARSER.parseFrom(data);
        }

        public static AfConf parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AfConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AfConf parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AfConf) PARSER.parseFrom(data);
        }

        public static AfConf parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AfConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static AfConf parseFrom(InputStream input) throws IOException {
            return (AfConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AfConf parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AfConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AfConf parseDelimitedFrom(InputStream input) throws IOException {
            return (AfConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AfConf parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AfConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AfConf parseFrom(CodedInputStream input) throws IOException {
            return (AfConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AfConf parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AfConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AfConf prototype) {
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

        public static AfConf getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AfConf> parser() {
            return PARSER;
        }

        public Parser<AfConf> getParserForType() {
            return PARSER;
        }

        public AfConf getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class BpCaliConf extends GeneratedMessageV3 implements BpCaliConfOrBuilder {
        private static final BpCaliConf DEFAULT_INSTANCE = new BpCaliConf();
        public static final int DIF_DBP_FIELD_NUMBER = 7;
        public static final int DIF_SBP_FIELD_NUMBER = 6;
        public static final int DST_DBP_FIELD_NUMBER = 5;
        public static final int DST_SBP_FIELD_NUMBER = 4;
        public static final int HASH_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<BpCaliConf> PARSER = new AbstractParser<BpCaliConf>() {
            public BpCaliConf parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new BpCaliConf(input, extensionRegistry);
            }
        };
        public static final int SRC_DBP_FIELD_NUMBER = 3;
        public static final int SRC_SBP_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int difDbp_;
        /* access modifiers changed from: private */
        public int difSbp_;
        /* access modifiers changed from: private */
        public int dstDbp_;
        /* access modifiers changed from: private */
        public int dstSbp_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int srcDbp_;
        /* access modifiers changed from: private */
        public int srcSbp_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements BpCaliConfOrBuilder {
            private int bitField0_;
            private int difDbp_;
            private int difSbp_;
            private int dstDbp_;
            private int dstSbp_;
            private int hash_;
            private int srcDbp_;
            private int srcSbp_;

            public static final Descriptor getDescriptor() {
                return PeerInfo.internal_static_BpCaliConf_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PeerInfo.internal_static_BpCaliConf_fieldAccessorTable.ensureFieldAccessorsInitialized(BpCaliConf.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (BpCaliConf.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.srcSbp_ = 0;
                this.bitField0_ &= -3;
                this.srcDbp_ = 0;
                this.bitField0_ &= -5;
                this.dstSbp_ = 0;
                this.bitField0_ &= -9;
                this.dstDbp_ = 0;
                this.bitField0_ &= -17;
                this.difSbp_ = 0;
                this.bitField0_ &= -33;
                this.difDbp_ = 0;
                this.bitField0_ &= -65;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return PeerInfo.internal_static_BpCaliConf_descriptor;
            }

            public BpCaliConf getDefaultInstanceForType() {
                return BpCaliConf.getDefaultInstance();
            }

            public BpCaliConf build() {
                BpCaliConf result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public BpCaliConf buildPartial() {
                BpCaliConf result = new BpCaliConf((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.srcSbp_ = this.srcSbp_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.srcDbp_ = this.srcDbp_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.dstSbp_ = this.dstSbp_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.dstDbp_ = this.dstDbp_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.difSbp_ = this.difSbp_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.difDbp_ = this.difDbp_;
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
                if (other instanceof BpCaliConf) {
                    return mergeFrom((BpCaliConf) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(BpCaliConf other) {
                if (other != BpCaliConf.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (other.hasSrcSbp()) {
                        setSrcSbp(other.getSrcSbp());
                    }
                    if (other.hasSrcDbp()) {
                        setSrcDbp(other.getSrcDbp());
                    }
                    if (other.hasDstSbp()) {
                        setDstSbp(other.getDstSbp());
                    }
                    if (other.hasDstDbp()) {
                        setDstDbp(other.getDstDbp());
                    }
                    if (other.hasDifSbp()) {
                        setDifSbp(other.getDifSbp());
                    }
                    if (other.hasDifDbp()) {
                        setDifDbp(other.getDifDbp());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasHash() && hasSrcSbp() && hasSrcDbp() && hasDstSbp() && hasDstDbp() && hasDifSbp() && hasDifDbp()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                BpCaliConf parsedMessage = null;
                try {
                    BpCaliConf parsedMessage2 = (BpCaliConf) BpCaliConf.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    BpCaliConf parsedMessage3 = (BpCaliConf) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasHash() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getHash() {
                return this.hash_;
            }

            public Builder setHash(int value) {
                this.bitField0_ |= 1;
                this.hash_ = value;
                onChanged();
                return this;
            }

            public Builder clearHash() {
                this.bitField0_ &= -2;
                this.hash_ = 0;
                onChanged();
                return this;
            }

            public boolean hasSrcSbp() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getSrcSbp() {
                return this.srcSbp_;
            }

            public Builder setSrcSbp(int value) {
                this.bitField0_ |= 2;
                this.srcSbp_ = value;
                onChanged();
                return this;
            }

            public Builder clearSrcSbp() {
                this.bitField0_ &= -3;
                this.srcSbp_ = 0;
                onChanged();
                return this;
            }

            public boolean hasSrcDbp() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getSrcDbp() {
                return this.srcDbp_;
            }

            public Builder setSrcDbp(int value) {
                this.bitField0_ |= 4;
                this.srcDbp_ = value;
                onChanged();
                return this;
            }

            public Builder clearSrcDbp() {
                this.bitField0_ &= -5;
                this.srcDbp_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDstSbp() {
                return (this.bitField0_ & 8) == 8;
            }

            public int getDstSbp() {
                return this.dstSbp_;
            }

            public Builder setDstSbp(int value) {
                this.bitField0_ |= 8;
                this.dstSbp_ = value;
                onChanged();
                return this;
            }

            public Builder clearDstSbp() {
                this.bitField0_ &= -9;
                this.dstSbp_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDstDbp() {
                return (this.bitField0_ & 16) == 16;
            }

            public int getDstDbp() {
                return this.dstDbp_;
            }

            public Builder setDstDbp(int value) {
                this.bitField0_ |= 16;
                this.dstDbp_ = value;
                onChanged();
                return this;
            }

            public Builder clearDstDbp() {
                this.bitField0_ &= -17;
                this.dstDbp_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDifSbp() {
                return (this.bitField0_ & 32) == 32;
            }

            public int getDifSbp() {
                return this.difSbp_;
            }

            public Builder setDifSbp(int value) {
                this.bitField0_ |= 32;
                this.difSbp_ = value;
                onChanged();
                return this;
            }

            public Builder clearDifSbp() {
                this.bitField0_ &= -33;
                this.difSbp_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDifDbp() {
                return (this.bitField0_ & 64) == 64;
            }

            public int getDifDbp() {
                return this.difDbp_;
            }

            public Builder setDifDbp(int value) {
                this.bitField0_ |= 64;
                this.difDbp_ = value;
                onChanged();
                return this;
            }

            public Builder clearDifDbp() {
                this.bitField0_ &= -65;
                this.difDbp_ = 0;
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

        private BpCaliConf(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private BpCaliConf() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.srcSbp_ = 0;
            this.srcDbp_ = 0;
            this.dstSbp_ = 0;
            this.dstDbp_ = 0;
            this.difSbp_ = 0;
            this.difDbp_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private BpCaliConf(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.hash_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.srcSbp_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.srcDbp_ = input.readFixed32();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.dstSbp_ = input.readFixed32();
                            break;
                        case 45:
                            this.bitField0_ |= 16;
                            this.dstDbp_ = input.readFixed32();
                            break;
                        case 53:
                            this.bitField0_ |= 32;
                            this.difSbp_ = input.readSFixed32();
                            break;
                        case 61:
                            this.bitField0_ |= 64;
                            this.difDbp_ = input.readSFixed32();
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
            return PeerInfo.internal_static_BpCaliConf_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return PeerInfo.internal_static_BpCaliConf_fieldAccessorTable.ensureFieldAccessorsInitialized(BpCaliConf.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasSrcSbp() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getSrcSbp() {
            return this.srcSbp_;
        }

        public boolean hasSrcDbp() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getSrcDbp() {
            return this.srcDbp_;
        }

        public boolean hasDstSbp() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getDstSbp() {
            return this.dstSbp_;
        }

        public boolean hasDstDbp() {
            return (this.bitField0_ & 16) == 16;
        }

        public int getDstDbp() {
            return this.dstDbp_;
        }

        public boolean hasDifSbp() {
            return (this.bitField0_ & 32) == 32;
        }

        public int getDifSbp() {
            return this.difSbp_;
        }

        public boolean hasDifDbp() {
            return (this.bitField0_ & 64) == 64;
        }

        public int getDifDbp() {
            return this.difDbp_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasHash()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSrcSbp()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSrcDbp()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDstSbp()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDstDbp()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDifSbp()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDifDbp()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.srcSbp_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.srcDbp_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFixed32(4, this.dstSbp_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeFixed32(5, this.dstDbp_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeSFixed32(6, this.difSbp_);
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeSFixed32(7, this.difDbp_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.srcSbp_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.srcDbp_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFixed32Size(4, this.dstSbp_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeFixed32Size(5, this.dstDbp_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeSFixed32Size(6, this.difSbp_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size2 += CodedOutputStream.computeSFixed32Size(7, this.difDbp_);
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
            if (!(obj instanceof BpCaliConf)) {
                return super.equals(obj);
            }
            BpCaliConf other = (BpCaliConf) obj;
            boolean result8 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result8 = result8 && getHash() == other.getHash();
            }
            if (!result8 || hasSrcSbp() != other.hasSrcSbp()) {
                result = false;
            } else {
                result = true;
            }
            if (hasSrcSbp()) {
                if (!result || getSrcSbp() != other.getSrcSbp()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasSrcDbp() != other.hasSrcDbp()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasSrcDbp()) {
                if (!result2 || getSrcDbp() != other.getSrcDbp()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasDstSbp() != other.hasDstSbp()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasDstSbp()) {
                if (!result3 || getDstSbp() != other.getDstSbp()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasDstDbp() != other.hasDstDbp()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasDstDbp()) {
                if (!result4 || getDstDbp() != other.getDstDbp()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasDifSbp() != other.hasDifSbp()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasDifSbp()) {
                if (!result5 || getDifSbp() != other.getDifSbp()) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || hasDifDbp() != other.hasDifDbp()) {
                result6 = false;
            } else {
                result6 = true;
            }
            if (hasDifDbp()) {
                if (!result6 || getDifDbp() != other.getDifDbp()) {
                    result6 = false;
                } else {
                    result6 = true;
                }
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
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            if (hasSrcSbp()) {
                hash = (((hash * 37) + 2) * 53) + getSrcSbp();
            }
            if (hasSrcDbp()) {
                hash = (((hash * 37) + 3) * 53) + getSrcDbp();
            }
            if (hasDstSbp()) {
                hash = (((hash * 37) + 4) * 53) + getDstSbp();
            }
            if (hasDstDbp()) {
                hash = (((hash * 37) + 5) * 53) + getDstDbp();
            }
            if (hasDifSbp()) {
                hash = (((hash * 37) + 6) * 53) + getDifSbp();
            }
            if (hasDifDbp()) {
                hash = (((hash * 37) + 7) * 53) + getDifDbp();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static BpCaliConf parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (BpCaliConf) PARSER.parseFrom(data);
        }

        public static BpCaliConf parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BpCaliConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static BpCaliConf parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (BpCaliConf) PARSER.parseFrom(data);
        }

        public static BpCaliConf parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BpCaliConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static BpCaliConf parseFrom(InputStream input) throws IOException {
            return (BpCaliConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BpCaliConf parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BpCaliConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static BpCaliConf parseDelimitedFrom(InputStream input) throws IOException {
            return (BpCaliConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static BpCaliConf parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BpCaliConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static BpCaliConf parseFrom(CodedInputStream input) throws IOException {
            return (BpCaliConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BpCaliConf parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BpCaliConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(BpCaliConf prototype) {
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

        public static BpCaliConf getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BpCaliConf> parser() {
            return PARSER;
        }

        public Parser<BpCaliConf> getParserForType() {
            return PARSER;
        }

        public BpCaliConf getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class GnssConf extends GeneratedMessageV3 implements GnssConfOrBuilder {
        public static final int ALTITUDE_FIELD_NUMBER = 2;
        private static final GnssConf DEFAULT_INSTANCE = new GnssConf();
        public static final int HASH_FIELD_NUMBER = 1;
        public static final int LATITUDE_FIELD_NUMBER = 3;
        public static final int LONGITUDE_FIELD_NUMBER = 4;
        @Deprecated
        public static final Parser<GnssConf> PARSER = new AbstractParser<GnssConf>() {
            public GnssConf parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new GnssConf(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public float altitude_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int hash_;
        /* access modifiers changed from: private */
        public float latitude_;
        /* access modifiers changed from: private */
        public float longitude_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements GnssConfOrBuilder {
            private float altitude_;
            private int bitField0_;
            private int hash_;
            private float latitude_;
            private float longitude_;

            public static final Descriptor getDescriptor() {
                return PeerInfo.internal_static_GnssConf_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PeerInfo.internal_static_GnssConf_fieldAccessorTable.ensureFieldAccessorsInitialized(GnssConf.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GnssConf.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.altitude_ = 0.0f;
                this.bitField0_ &= -3;
                this.latitude_ = 0.0f;
                this.bitField0_ &= -5;
                this.longitude_ = 0.0f;
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return PeerInfo.internal_static_GnssConf_descriptor;
            }

            public GnssConf getDefaultInstanceForType() {
                return GnssConf.getDefaultInstance();
            }

            public GnssConf build() {
                GnssConf result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public GnssConf buildPartial() {
                GnssConf result = new GnssConf((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.altitude_ = this.altitude_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.latitude_ = this.latitude_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.longitude_ = this.longitude_;
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
                if (other instanceof GnssConf) {
                    return mergeFrom((GnssConf) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(GnssConf other) {
                if (other != GnssConf.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (other.hasAltitude()) {
                        setAltitude(other.getAltitude());
                    }
                    if (other.hasLatitude()) {
                        setLatitude(other.getLatitude());
                    }
                    if (other.hasLongitude()) {
                        setLongitude(other.getLongitude());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasHash() && hasAltitude() && hasLatitude() && hasLongitude()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                GnssConf parsedMessage = null;
                try {
                    GnssConf parsedMessage2 = (GnssConf) GnssConf.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    GnssConf parsedMessage3 = (GnssConf) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasHash() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getHash() {
                return this.hash_;
            }

            public Builder setHash(int value) {
                this.bitField0_ |= 1;
                this.hash_ = value;
                onChanged();
                return this;
            }

            public Builder clearHash() {
                this.bitField0_ &= -2;
                this.hash_ = 0;
                onChanged();
                return this;
            }

            public boolean hasAltitude() {
                return (this.bitField0_ & 2) == 2;
            }

            public float getAltitude() {
                return this.altitude_;
            }

            public Builder setAltitude(float value) {
                this.bitField0_ |= 2;
                this.altitude_ = value;
                onChanged();
                return this;
            }

            public Builder clearAltitude() {
                this.bitField0_ &= -3;
                this.altitude_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasLatitude() {
                return (this.bitField0_ & 4) == 4;
            }

            public float getLatitude() {
                return this.latitude_;
            }

            public Builder setLatitude(float value) {
                this.bitField0_ |= 4;
                this.latitude_ = value;
                onChanged();
                return this;
            }

            public Builder clearLatitude() {
                this.bitField0_ &= -5;
                this.latitude_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasLongitude() {
                return (this.bitField0_ & 8) == 8;
            }

            public float getLongitude() {
                return this.longitude_;
            }

            public Builder setLongitude(float value) {
                this.bitField0_ |= 8;
                this.longitude_ = value;
                onChanged();
                return this;
            }

            public Builder clearLongitude() {
                this.bitField0_ &= -9;
                this.longitude_ = 0.0f;
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

        private GnssConf(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private GnssConf() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.altitude_ = 0.0f;
            this.latitude_ = 0.0f;
            this.longitude_ = 0.0f;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private GnssConf(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.hash_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.altitude_ = input.readFloat();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.latitude_ = input.readFloat();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.longitude_ = input.readFloat();
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
            return PeerInfo.internal_static_GnssConf_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return PeerInfo.internal_static_GnssConf_fieldAccessorTable.ensureFieldAccessorsInitialized(GnssConf.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasAltitude() {
            return (this.bitField0_ & 2) == 2;
        }

        public float getAltitude() {
            return this.altitude_;
        }

        public boolean hasLatitude() {
            return (this.bitField0_ & 4) == 4;
        }

        public float getLatitude() {
            return this.latitude_;
        }

        public boolean hasLongitude() {
            return (this.bitField0_ & 8) == 8;
        }

        public float getLongitude() {
            return this.longitude_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasHash()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasAltitude()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasLatitude()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasLongitude()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFloat(2, this.altitude_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFloat(3, this.latitude_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFloat(4, this.longitude_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFloatSize(2, this.altitude_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFloatSize(3, this.latitude_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFloatSize(4, this.longitude_);
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
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof GnssConf)) {
                return super.equals(obj);
            }
            GnssConf other = (GnssConf) obj;
            boolean result5 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result5 = result5 && getHash() == other.getHash();
            }
            if (!result5 || hasAltitude() != other.hasAltitude()) {
                result = false;
            } else {
                result = true;
            }
            if (hasAltitude()) {
                if (!result || Float.floatToIntBits(getAltitude()) != Float.floatToIntBits(other.getAltitude())) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasLatitude() != other.hasLatitude()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasLatitude()) {
                if (!result2 || Float.floatToIntBits(getLatitude()) != Float.floatToIntBits(other.getLatitude())) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasLongitude() != other.hasLongitude()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasLongitude()) {
                if (!result3 || Float.floatToIntBits(getLongitude()) != Float.floatToIntBits(other.getLongitude())) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || !this.unknownFields.equals(other.unknownFields)) {
                result4 = false;
            } else {
                result4 = true;
            }
            return result4;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            if (hasAltitude()) {
                hash = (((hash * 37) + 2) * 53) + Float.floatToIntBits(getAltitude());
            }
            if (hasLatitude()) {
                hash = (((hash * 37) + 3) * 53) + Float.floatToIntBits(getLatitude());
            }
            if (hasLongitude()) {
                hash = (((hash * 37) + 4) * 53) + Float.floatToIntBits(getLongitude());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static GnssConf parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GnssConf) PARSER.parseFrom(data);
        }

        public static GnssConf parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GnssConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static GnssConf parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GnssConf) PARSER.parseFrom(data);
        }

        public static GnssConf parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GnssConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static GnssConf parseFrom(InputStream input) throws IOException {
            return (GnssConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GnssConf parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GnssConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static GnssConf parseDelimitedFrom(InputStream input) throws IOException {
            return (GnssConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static GnssConf parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GnssConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static GnssConf parseFrom(CodedInputStream input) throws IOException {
            return (GnssConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GnssConf parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GnssConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(GnssConf prototype) {
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

        public static GnssConf getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GnssConf> parser() {
            return PARSER;
        }

        public Parser<GnssConf> getParserForType() {
            return PARSER;
        }

        public GnssConf getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class GoalConf extends GeneratedMessageV3 implements GoalConfOrBuilder {
        public static final int CALORIE_FIELD_NUMBER = 4;
        private static final GoalConf DEFAULT_INSTANCE = new GoalConf();
        public static final int DISTANCE_FIELD_NUMBER = 2;
        public static final int HASH_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<GoalConf> PARSER = new AbstractParser<GoalConf>() {
            public GoalConf parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new GoalConf(input, extensionRegistry);
            }
        };
        public static final int STEP_FIELD_NUMBER = 3;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int calorie_;
        /* access modifiers changed from: private */
        public int distance_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int step_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements GoalConfOrBuilder {
            private int bitField0_;
            private int calorie_;
            private int distance_;
            private int hash_;
            private int step_;

            public static final Descriptor getDescriptor() {
                return PeerInfo.internal_static_GoalConf_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PeerInfo.internal_static_GoalConf_fieldAccessorTable.ensureFieldAccessorsInitialized(GoalConf.class, Builder.class);
            }

            private Builder() {
                this.distance_ = 10000;
                this.step_ = 10000;
                this.calorie_ = 400;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.distance_ = 10000;
                this.step_ = 10000;
                this.calorie_ = 400;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GoalConf.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.distance_ = 10000;
                this.bitField0_ &= -3;
                this.step_ = 10000;
                this.bitField0_ &= -5;
                this.calorie_ = 400;
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return PeerInfo.internal_static_GoalConf_descriptor;
            }

            public GoalConf getDefaultInstanceForType() {
                return GoalConf.getDefaultInstance();
            }

            public GoalConf build() {
                GoalConf result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public GoalConf buildPartial() {
                GoalConf result = new GoalConf((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.distance_ = this.distance_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.step_ = this.step_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.calorie_ = this.calorie_;
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
                if (other instanceof GoalConf) {
                    return mergeFrom((GoalConf) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(GoalConf other) {
                if (other != GoalConf.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (other.hasDistance()) {
                        setDistance(other.getDistance());
                    }
                    if (other.hasStep()) {
                        setStep(other.getStep());
                    }
                    if (other.hasCalorie()) {
                        setCalorie(other.getCalorie());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasHash() && hasDistance() && hasStep() && hasCalorie()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                GoalConf parsedMessage = null;
                try {
                    GoalConf parsedMessage2 = (GoalConf) GoalConf.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    GoalConf parsedMessage3 = (GoalConf) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasHash() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getHash() {
                return this.hash_;
            }

            public Builder setHash(int value) {
                this.bitField0_ |= 1;
                this.hash_ = value;
                onChanged();
                return this;
            }

            public Builder clearHash() {
                this.bitField0_ &= -2;
                this.hash_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDistance() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getDistance() {
                return this.distance_;
            }

            public Builder setDistance(int value) {
                this.bitField0_ |= 2;
                this.distance_ = value;
                onChanged();
                return this;
            }

            public Builder clearDistance() {
                this.bitField0_ &= -3;
                this.distance_ = 10000;
                onChanged();
                return this;
            }

            public boolean hasStep() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getStep() {
                return this.step_;
            }

            public Builder setStep(int value) {
                this.bitField0_ |= 4;
                this.step_ = value;
                onChanged();
                return this;
            }

            public Builder clearStep() {
                this.bitField0_ &= -5;
                this.step_ = 10000;
                onChanged();
                return this;
            }

            public boolean hasCalorie() {
                return (this.bitField0_ & 8) == 8;
            }

            public int getCalorie() {
                return this.calorie_;
            }

            public Builder setCalorie(int value) {
                this.bitField0_ |= 8;
                this.calorie_ = value;
                onChanged();
                return this;
            }

            public Builder clearCalorie() {
                this.bitField0_ &= -9;
                this.calorie_ = 400;
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

        private GoalConf(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private GoalConf() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.distance_ = 10000;
            this.step_ = 10000;
            this.calorie_ = 400;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private GoalConf(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.hash_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.distance_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.step_ = input.readFixed32();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.calorie_ = input.readFixed32();
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
            return PeerInfo.internal_static_GoalConf_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return PeerInfo.internal_static_GoalConf_fieldAccessorTable.ensureFieldAccessorsInitialized(GoalConf.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasDistance() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getDistance() {
            return this.distance_;
        }

        public boolean hasStep() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getStep() {
            return this.step_;
        }

        public boolean hasCalorie() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getCalorie() {
            return this.calorie_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasHash()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDistance()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasStep()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasCalorie()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.distance_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.step_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFixed32(4, this.calorie_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.distance_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.step_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFixed32Size(4, this.calorie_);
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
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof GoalConf)) {
                return super.equals(obj);
            }
            GoalConf other = (GoalConf) obj;
            boolean result5 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result5 = result5 && getHash() == other.getHash();
            }
            if (!result5 || hasDistance() != other.hasDistance()) {
                result = false;
            } else {
                result = true;
            }
            if (hasDistance()) {
                if (!result || getDistance() != other.getDistance()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasStep() != other.hasStep()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasStep()) {
                if (!result2 || getStep() != other.getStep()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasCalorie() != other.hasCalorie()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasCalorie()) {
                if (!result3 || getCalorie() != other.getCalorie()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || !this.unknownFields.equals(other.unknownFields)) {
                result4 = false;
            } else {
                result4 = true;
            }
            return result4;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            if (hasDistance()) {
                hash = (((hash * 37) + 2) * 53) + getDistance();
            }
            if (hasStep()) {
                hash = (((hash * 37) + 3) * 53) + getStep();
            }
            if (hasCalorie()) {
                hash = (((hash * 37) + 4) * 53) + getCalorie();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static GoalConf parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GoalConf) PARSER.parseFrom(data);
        }

        public static GoalConf parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GoalConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static GoalConf parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GoalConf) PARSER.parseFrom(data);
        }

        public static GoalConf parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GoalConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static GoalConf parseFrom(InputStream input) throws IOException {
            return (GoalConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GoalConf parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GoalConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static GoalConf parseDelimitedFrom(InputStream input) throws IOException {
            return (GoalConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static GoalConf parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GoalConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static GoalConf parseFrom(CodedInputStream input) throws IOException {
            return (GoalConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GoalConf parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GoalConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(GoalConf prototype) {
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

        public static GoalConf getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GoalConf> parser() {
            return PARSER;
        }

        public Parser<GoalConf> getParserForType() {
            return PARSER;
        }

        public GoalConf getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class HrAlarmConf extends GeneratedMessageV3 implements HrAlarmConfOrBuilder {
        private static final HrAlarmConf DEFAULT_INSTANCE = new HrAlarmConf();
        public static final int ENABLE_FIELD_NUMBER = 2;
        public static final int HASH_FIELD_NUMBER = 1;
        public static final int INTERVAL_FIELD_NUMBER = 6;
        @Deprecated
        public static final Parser<HrAlarmConf> PARSER = new AbstractParser<HrAlarmConf>() {
            public HrAlarmConf parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new HrAlarmConf(input, extensionRegistry);
            }
        };
        public static final int THS_HIGH_FIELD_NUMBER = 3;
        public static final int THS_LOW_FIELD_NUMBER = 4;
        public static final int TIMEOUT_FIELD_NUMBER = 5;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public boolean enable_;
        /* access modifiers changed from: private */
        public int hash_;
        /* access modifiers changed from: private */
        public int interval_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int thsHigh_;
        /* access modifiers changed from: private */
        public int thsLow_;
        /* access modifiers changed from: private */
        public int timeout_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements HrAlarmConfOrBuilder {
            private int bitField0_;
            private boolean enable_;
            private int hash_;
            private int interval_;
            private int thsHigh_;
            private int thsLow_;
            private int timeout_;

            public static final Descriptor getDescriptor() {
                return PeerInfo.internal_static_HrAlarmConf_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PeerInfo.internal_static_HrAlarmConf_fieldAccessorTable.ensureFieldAccessorsInitialized(HrAlarmConf.class, Builder.class);
            }

            private Builder() {
                this.thsHigh_ = Opcodes.AND_LONG;
                this.thsLow_ = 50;
                this.timeout_ = 30;
                this.interval_ = 2;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.thsHigh_ = Opcodes.AND_LONG;
                this.thsLow_ = 50;
                this.timeout_ = 30;
                this.interval_ = 2;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (HrAlarmConf.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.enable_ = false;
                this.bitField0_ &= -3;
                this.thsHigh_ = Opcodes.AND_LONG;
                this.bitField0_ &= -5;
                this.thsLow_ = 50;
                this.bitField0_ &= -9;
                this.timeout_ = 30;
                this.bitField0_ &= -17;
                this.interval_ = 2;
                this.bitField0_ &= -33;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return PeerInfo.internal_static_HrAlarmConf_descriptor;
            }

            public HrAlarmConf getDefaultInstanceForType() {
                return HrAlarmConf.getDefaultInstance();
            }

            public HrAlarmConf build() {
                HrAlarmConf result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public HrAlarmConf buildPartial() {
                HrAlarmConf result = new HrAlarmConf((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.enable_ = this.enable_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.thsHigh_ = this.thsHigh_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.thsLow_ = this.thsLow_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.timeout_ = this.timeout_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.interval_ = this.interval_;
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
                if (other instanceof HrAlarmConf) {
                    return mergeFrom((HrAlarmConf) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(HrAlarmConf other) {
                if (other != HrAlarmConf.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (other.hasEnable()) {
                        setEnable(other.getEnable());
                    }
                    if (other.hasThsHigh()) {
                        setThsHigh(other.getThsHigh());
                    }
                    if (other.hasThsLow()) {
                        setThsLow(other.getThsLow());
                    }
                    if (other.hasTimeout()) {
                        setTimeout(other.getTimeout());
                    }
                    if (other.hasInterval()) {
                        setInterval(other.getInterval());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasHash() && hasEnable() && hasThsHigh() && hasThsLow() && hasTimeout() && hasInterval()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                HrAlarmConf parsedMessage = null;
                try {
                    HrAlarmConf parsedMessage2 = (HrAlarmConf) HrAlarmConf.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    HrAlarmConf parsedMessage3 = (HrAlarmConf) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasHash() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getHash() {
                return this.hash_;
            }

            public Builder setHash(int value) {
                this.bitField0_ |= 1;
                this.hash_ = value;
                onChanged();
                return this;
            }

            public Builder clearHash() {
                this.bitField0_ &= -2;
                this.hash_ = 0;
                onChanged();
                return this;
            }

            public boolean hasEnable() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getEnable() {
                return this.enable_;
            }

            public Builder setEnable(boolean value) {
                this.bitField0_ |= 2;
                this.enable_ = value;
                onChanged();
                return this;
            }

            public Builder clearEnable() {
                this.bitField0_ &= -3;
                this.enable_ = false;
                onChanged();
                return this;
            }

            public boolean hasThsHigh() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getThsHigh() {
                return this.thsHigh_;
            }

            public Builder setThsHigh(int value) {
                this.bitField0_ |= 4;
                this.thsHigh_ = value;
                onChanged();
                return this;
            }

            public Builder clearThsHigh() {
                this.bitField0_ &= -5;
                this.thsHigh_ = Opcodes.AND_LONG;
                onChanged();
                return this;
            }

            public boolean hasThsLow() {
                return (this.bitField0_ & 8) == 8;
            }

            public int getThsLow() {
                return this.thsLow_;
            }

            public Builder setThsLow(int value) {
                this.bitField0_ |= 8;
                this.thsLow_ = value;
                onChanged();
                return this;
            }

            public Builder clearThsLow() {
                this.bitField0_ &= -9;
                this.thsLow_ = 50;
                onChanged();
                return this;
            }

            public boolean hasTimeout() {
                return (this.bitField0_ & 16) == 16;
            }

            public int getTimeout() {
                return this.timeout_;
            }

            public Builder setTimeout(int value) {
                this.bitField0_ |= 16;
                this.timeout_ = value;
                onChanged();
                return this;
            }

            public Builder clearTimeout() {
                this.bitField0_ &= -17;
                this.timeout_ = 30;
                onChanged();
                return this;
            }

            public boolean hasInterval() {
                return (this.bitField0_ & 32) == 32;
            }

            public int getInterval() {
                return this.interval_;
            }

            public Builder setInterval(int value) {
                this.bitField0_ |= 32;
                this.interval_ = value;
                onChanged();
                return this;
            }

            public Builder clearInterval() {
                this.bitField0_ &= -33;
                this.interval_ = 2;
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

        private HrAlarmConf(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private HrAlarmConf() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.enable_ = false;
            this.thsHigh_ = Opcodes.AND_LONG;
            this.thsLow_ = 50;
            this.timeout_ = 30;
            this.interval_ = 2;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private HrAlarmConf(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.hash_ = input.readFixed32();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.enable_ = input.readBool();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.thsHigh_ = input.readFixed32();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.thsLow_ = input.readFixed32();
                            break;
                        case 45:
                            this.bitField0_ |= 16;
                            this.timeout_ = input.readFixed32();
                            break;
                        case 53:
                            this.bitField0_ |= 32;
                            this.interval_ = input.readFixed32();
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
            return PeerInfo.internal_static_HrAlarmConf_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return PeerInfo.internal_static_HrAlarmConf_fieldAccessorTable.ensureFieldAccessorsInitialized(HrAlarmConf.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasEnable() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getEnable() {
            return this.enable_;
        }

        public boolean hasThsHigh() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getThsHigh() {
            return this.thsHigh_;
        }

        public boolean hasThsLow() {
            return (this.bitField0_ & 8) == 8;
        }

        public int getThsLow() {
            return this.thsLow_;
        }

        public boolean hasTimeout() {
            return (this.bitField0_ & 16) == 16;
        }

        public int getTimeout() {
            return this.timeout_;
        }

        public boolean hasInterval() {
            return (this.bitField0_ & 32) == 32;
        }

        public int getInterval() {
            return this.interval_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasHash()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasEnable()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasThsHigh()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasThsLow()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasTimeout()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasInterval()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.enable_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.thsHigh_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFixed32(4, this.thsLow_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeFixed32(5, this.timeout_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeFixed32(6, this.interval_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.enable_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.thsHigh_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFixed32Size(4, this.thsLow_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeFixed32Size(5, this.timeout_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeFixed32Size(6, this.interval_);
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
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof HrAlarmConf)) {
                return super.equals(obj);
            }
            HrAlarmConf other = (HrAlarmConf) obj;
            boolean result7 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result7 = result7 && getHash() == other.getHash();
            }
            if (!result7 || hasEnable() != other.hasEnable()) {
                result = false;
            } else {
                result = true;
            }
            if (hasEnable()) {
                if (!result || getEnable() != other.getEnable()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasThsHigh() != other.hasThsHigh()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasThsHigh()) {
                if (!result2 || getThsHigh() != other.getThsHigh()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasThsLow() != other.hasThsLow()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasThsLow()) {
                if (!result3 || getThsLow() != other.getThsLow()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasTimeout() != other.hasTimeout()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasTimeout()) {
                if (!result4 || getTimeout() != other.getTimeout()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasInterval() != other.hasInterval()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasInterval()) {
                if (!result5 || getInterval() != other.getInterval()) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || !this.unknownFields.equals(other.unknownFields)) {
                result6 = false;
            } else {
                result6 = true;
            }
            return result6;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            if (hasEnable()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getEnable());
            }
            if (hasThsHigh()) {
                hash = (((hash * 37) + 3) * 53) + getThsHigh();
            }
            if (hasThsLow()) {
                hash = (((hash * 37) + 4) * 53) + getThsLow();
            }
            if (hasTimeout()) {
                hash = (((hash * 37) + 5) * 53) + getTimeout();
            }
            if (hasInterval()) {
                hash = (((hash * 37) + 6) * 53) + getInterval();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static HrAlarmConf parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HrAlarmConf) PARSER.parseFrom(data);
        }

        public static HrAlarmConf parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HrAlarmConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HrAlarmConf parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HrAlarmConf) PARSER.parseFrom(data);
        }

        public static HrAlarmConf parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HrAlarmConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static HrAlarmConf parseFrom(InputStream input) throws IOException {
            return (HrAlarmConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HrAlarmConf parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HrAlarmConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static HrAlarmConf parseDelimitedFrom(InputStream input) throws IOException {
            return (HrAlarmConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static HrAlarmConf parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HrAlarmConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static HrAlarmConf parseFrom(CodedInputStream input) throws IOException {
            return (HrAlarmConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static HrAlarmConf parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HrAlarmConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(HrAlarmConf prototype) {
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

        public static HrAlarmConf getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HrAlarmConf> parser() {
            return PARSER;
        }

        public Parser<HrAlarmConf> getParserForType() {
            return PARSER;
        }

        public HrAlarmConf getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class PeerInfoNotification extends GeneratedMessageV3 implements PeerInfoNotificationOrBuilder {
        public static final int AF_CONF_FIELD_NUMBER = 9;
        public static final int BPCALI_CONF_FIELD_NUMBER = 8;
        public static final int DATE_TIME_FIELD_NUMBER = 3;
        private static final PeerInfoNotification DEFAULT_INSTANCE = new PeerInfoNotification();
        public static final int GNSS_CONF_FIELD_NUMBER = 4;
        public static final int GOAL_CONF_FIELD_NUMBER = 7;
        public static final int HR_ALARM_CONF_FIELD_NUMBER = 5;
        @Deprecated
        public static final Parser<PeerInfoNotification> PARSER = new AbstractParser<PeerInfoNotification>() {
            public PeerInfoNotification parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new PeerInfoNotification(input, extensionRegistry);
            }
        };
        public static final int PEER_STATUS_FIELD_NUMBER = 2;
        public static final int PEER_TYPE_FIELD_NUMBER = 1;
        public static final int USER_CONF_FIELD_NUMBER = 6;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public AfConf afConf_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public BpCaliConf bpcaliConf_;
        /* access modifiers changed from: private */
        public DateTime dateTime_;
        /* access modifiers changed from: private */
        public GnssConf gnssConf_;
        /* access modifiers changed from: private */
        public GoalConf goalConf_;
        /* access modifiers changed from: private */
        public HrAlarmConf hrAlarmConf_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int peerStatus_;
        /* access modifiers changed from: private */
        public int peerType_;
        /* access modifiers changed from: private */
        public UserConf userConf_;

        public enum PeerStatus implements ProtocolMessageEnum {
            APP_BACKGROUND(0),
            APP_FOREGROUND(1);
            
            public static final int APP_BACKGROUND_VALUE = 0;
            public static final int APP_FOREGROUND_VALUE = 1;
            private static final PeerStatus[] VALUES = null;
            private static final EnumLiteMap<PeerStatus> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<PeerStatus>() {
                    public PeerStatus findValueByNumber(int number) {
                        return PeerStatus.forNumber(number);
                    }
                };
                VALUES = values();
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static PeerStatus valueOf(int value2) {
                return forNumber(value2);
            }

            public static PeerStatus forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return APP_BACKGROUND;
                    case 1:
                        return APP_FOREGROUND;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<PeerStatus> internalGetValueMap() {
                return internalValueMap;
            }

            public final EnumValueDescriptor getValueDescriptor() {
                return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
            }

            public final EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final EnumDescriptor getDescriptor() {
                return (EnumDescriptor) PeerInfoNotification.getDescriptor().getEnumTypes().get(1);
            }

            public static PeerStatus valueOf(EnumValueDescriptor desc) {
                if (desc.getType() == getDescriptor()) {
                    return VALUES[desc.getIndex()];
                }
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }

            private PeerStatus(int value2) {
                this.value = value2;
            }
        }

        public enum PeerType implements ProtocolMessageEnum {
            APP_ANDROID(0),
            APP_IOS(1);
            
            public static final int APP_ANDROID_VALUE = 0;
            public static final int APP_IOS_VALUE = 1;
            private static final PeerType[] VALUES = null;
            private static final EnumLiteMap<PeerType> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<PeerType>() {
                    public PeerType findValueByNumber(int number) {
                        return PeerType.forNumber(number);
                    }
                };
                VALUES = values();
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static PeerType valueOf(int value2) {
                return forNumber(value2);
            }

            public static PeerType forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return APP_ANDROID;
                    case 1:
                        return APP_IOS;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<PeerType> internalGetValueMap() {
                return internalValueMap;
            }

            public final EnumValueDescriptor getValueDescriptor() {
                return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
            }

            public final EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final EnumDescriptor getDescriptor() {
                return (EnumDescriptor) PeerInfoNotification.getDescriptor().getEnumTypes().get(0);
            }

            public static PeerType valueOf(EnumValueDescriptor desc) {
                if (desc.getType() == getDescriptor()) {
                    return VALUES[desc.getIndex()];
                }
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }

            private PeerType(int value2) {
                this.value = value2;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements PeerInfoNotificationOrBuilder {
            private SingleFieldBuilderV3<AfConf, Builder, AfConfOrBuilder> afConfBuilder_;
            private AfConf afConf_;
            private int bitField0_;
            private SingleFieldBuilderV3<BpCaliConf, Builder, BpCaliConfOrBuilder> bpcaliConfBuilder_;
            private BpCaliConf bpcaliConf_;
            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> dateTimeBuilder_;
            private DateTime dateTime_;
            private SingleFieldBuilderV3<GnssConf, Builder, GnssConfOrBuilder> gnssConfBuilder_;
            private GnssConf gnssConf_;
            private SingleFieldBuilderV3<GoalConf, Builder, GoalConfOrBuilder> goalConfBuilder_;
            private GoalConf goalConf_;
            private SingleFieldBuilderV3<HrAlarmConf, Builder, HrAlarmConfOrBuilder> hrAlarmConfBuilder_;
            private HrAlarmConf hrAlarmConf_;
            private int peerStatus_;
            private int peerType_;
            private SingleFieldBuilderV3<UserConf, Builder, UserConfOrBuilder> userConfBuilder_;
            private UserConf userConf_;

            public static final Descriptor getDescriptor() {
                return PeerInfo.internal_static_PeerInfoNotification_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PeerInfo.internal_static_PeerInfoNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(PeerInfoNotification.class, Builder.class);
            }

            private Builder() {
                this.peerType_ = 0;
                this.peerStatus_ = 0;
                this.dateTime_ = null;
                this.gnssConf_ = null;
                this.hrAlarmConf_ = null;
                this.userConf_ = null;
                this.goalConf_ = null;
                this.bpcaliConf_ = null;
                this.afConf_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.peerType_ = 0;
                this.peerStatus_ = 0;
                this.dateTime_ = null;
                this.gnssConf_ = null;
                this.hrAlarmConf_ = null;
                this.userConf_ = null;
                this.goalConf_ = null;
                this.bpcaliConf_ = null;
                this.afConf_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (PeerInfoNotification.alwaysUseFieldBuilders) {
                    getDateTimeFieldBuilder();
                    getGnssConfFieldBuilder();
                    getHrAlarmConfFieldBuilder();
                    getUserConfFieldBuilder();
                    getGoalConfFieldBuilder();
                    getBpcaliConfFieldBuilder();
                    getAfConfFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.peerType_ = 0;
                this.bitField0_ &= -2;
                this.peerStatus_ = 0;
                this.bitField0_ &= -3;
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = null;
                } else {
                    this.dateTimeBuilder_.clear();
                }
                this.bitField0_ &= -5;
                if (this.gnssConfBuilder_ == null) {
                    this.gnssConf_ = null;
                } else {
                    this.gnssConfBuilder_.clear();
                }
                this.bitField0_ &= -9;
                if (this.hrAlarmConfBuilder_ == null) {
                    this.hrAlarmConf_ = null;
                } else {
                    this.hrAlarmConfBuilder_.clear();
                }
                this.bitField0_ &= -17;
                if (this.userConfBuilder_ == null) {
                    this.userConf_ = null;
                } else {
                    this.userConfBuilder_.clear();
                }
                this.bitField0_ &= -33;
                if (this.goalConfBuilder_ == null) {
                    this.goalConf_ = null;
                } else {
                    this.goalConfBuilder_.clear();
                }
                this.bitField0_ &= -65;
                if (this.bpcaliConfBuilder_ == null) {
                    this.bpcaliConf_ = null;
                } else {
                    this.bpcaliConfBuilder_.clear();
                }
                this.bitField0_ &= -129;
                if (this.afConfBuilder_ == null) {
                    this.afConf_ = null;
                } else {
                    this.afConfBuilder_.clear();
                }
                this.bitField0_ &= -257;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return PeerInfo.internal_static_PeerInfoNotification_descriptor;
            }

            public PeerInfoNotification getDefaultInstanceForType() {
                return PeerInfoNotification.getDefaultInstance();
            }

            public PeerInfoNotification build() {
                PeerInfoNotification result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public PeerInfoNotification buildPartial() {
                PeerInfoNotification result = new PeerInfoNotification((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.peerType_ = this.peerType_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.peerStatus_ = this.peerStatus_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                if (this.dateTimeBuilder_ == null) {
                    result.dateTime_ = this.dateTime_;
                } else {
                    result.dateTime_ = (DateTime) this.dateTimeBuilder_.build();
                }
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                if (this.gnssConfBuilder_ == null) {
                    result.gnssConf_ = this.gnssConf_;
                } else {
                    result.gnssConf_ = (GnssConf) this.gnssConfBuilder_.build();
                }
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                if (this.hrAlarmConfBuilder_ == null) {
                    result.hrAlarmConf_ = this.hrAlarmConf_;
                } else {
                    result.hrAlarmConf_ = (HrAlarmConf) this.hrAlarmConfBuilder_.build();
                }
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                if (this.userConfBuilder_ == null) {
                    result.userConf_ = this.userConf_;
                } else {
                    result.userConf_ = (UserConf) this.userConfBuilder_.build();
                }
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                if (this.goalConfBuilder_ == null) {
                    result.goalConf_ = this.goalConf_;
                } else {
                    result.goalConf_ = (GoalConf) this.goalConfBuilder_.build();
                }
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                if (this.bpcaliConfBuilder_ == null) {
                    result.bpcaliConf_ = this.bpcaliConf_;
                } else {
                    result.bpcaliConf_ = (BpCaliConf) this.bpcaliConfBuilder_.build();
                }
                if ((from_bitField0_ & 256) == 256) {
                    to_bitField0_ |= 256;
                }
                if (this.afConfBuilder_ == null) {
                    result.afConf_ = this.afConf_;
                } else {
                    result.afConf_ = (AfConf) this.afConfBuilder_.build();
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
                if (other instanceof PeerInfoNotification) {
                    return mergeFrom((PeerInfoNotification) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(PeerInfoNotification other) {
                if (other != PeerInfoNotification.getDefaultInstance()) {
                    if (other.hasPeerType()) {
                        setPeerType(other.getPeerType());
                    }
                    if (other.hasPeerStatus()) {
                        setPeerStatus(other.getPeerStatus());
                    }
                    if (other.hasDateTime()) {
                        mergeDateTime(other.getDateTime());
                    }
                    if (other.hasGnssConf()) {
                        mergeGnssConf(other.getGnssConf());
                    }
                    if (other.hasHrAlarmConf()) {
                        mergeHrAlarmConf(other.getHrAlarmConf());
                    }
                    if (other.hasUserConf()) {
                        mergeUserConf(other.getUserConf());
                    }
                    if (other.hasGoalConf()) {
                        mergeGoalConf(other.getGoalConf());
                    }
                    if (other.hasBpcaliConf()) {
                        mergeBpcaliConf(other.getBpcaliConf());
                    }
                    if (other.hasAfConf()) {
                        mergeAfConf(other.getAfConf());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasDateTime() && !getDateTime().isInitialized()) {
                    return false;
                }
                if (hasGnssConf() && !getGnssConf().isInitialized()) {
                    return false;
                }
                if (hasHrAlarmConf() && !getHrAlarmConf().isInitialized()) {
                    return false;
                }
                if (hasUserConf() && !getUserConf().isInitialized()) {
                    return false;
                }
                if (hasGoalConf() && !getGoalConf().isInitialized()) {
                    return false;
                }
                if (hasBpcaliConf() && !getBpcaliConf().isInitialized()) {
                    return false;
                }
                if (!hasAfConf() || getAfConf().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                PeerInfoNotification parsedMessage = null;
                try {
                    PeerInfoNotification parsedMessage2 = (PeerInfoNotification) PeerInfoNotification.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    PeerInfoNotification parsedMessage3 = (PeerInfoNotification) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasPeerType() {
                return (this.bitField0_ & 1) == 1;
            }

            public PeerType getPeerType() {
                PeerType result = PeerType.valueOf(this.peerType_);
                return result == null ? PeerType.APP_ANDROID : result;
            }

            public Builder setPeerType(PeerType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.peerType_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearPeerType() {
                this.bitField0_ &= -2;
                this.peerType_ = 0;
                onChanged();
                return this;
            }

            public boolean hasPeerStatus() {
                return (this.bitField0_ & 2) == 2;
            }

            public PeerStatus getPeerStatus() {
                PeerStatus result = PeerStatus.valueOf(this.peerStatus_);
                return result == null ? PeerStatus.APP_BACKGROUND : result;
            }

            public Builder setPeerStatus(PeerStatus value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.peerStatus_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearPeerStatus() {
                this.bitField0_ &= -3;
                this.peerStatus_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDateTime() {
                return (this.bitField0_ & 4) == 4;
            }

            public DateTime getDateTime() {
                if (this.dateTimeBuilder_ == null) {
                    return this.dateTime_ == null ? DateTime.getDefaultInstance() : this.dateTime_;
                }
                return (DateTime) this.dateTimeBuilder_.getMessage();
            }

            public Builder setDateTime(DateTime value) {
                if (this.dateTimeBuilder_ != null) {
                    this.dateTimeBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.dateTime_ = value;
                    onChanged();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setDateTime(com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder builderForValue) {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = builderForValue.build();
                    onChanged();
                } else {
                    this.dateTimeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeDateTime(DateTime value) {
                if (this.dateTimeBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 4 || this.dateTime_ == null || this.dateTime_ == DateTime.getDefaultInstance()) {
                        this.dateTime_ = value;
                    } else {
                        this.dateTime_ = DateTime.newBuilder(this.dateTime_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.dateTimeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearDateTime() {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = null;
                    onChanged();
                } else {
                    this.dateTimeBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder getDateTimeBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder) getDateTimeFieldBuilder().getBuilder();
            }

            public DateTimeOrBuilder getDateTimeOrBuilder() {
                if (this.dateTimeBuilder_ != null) {
                    return (DateTimeOrBuilder) this.dateTimeBuilder_.getMessageOrBuilder();
                }
                return this.dateTime_ == null ? DateTime.getDefaultInstance() : this.dateTime_;
            }

            private SingleFieldBuilderV3<DateTime, com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder, DateTimeOrBuilder> getDateTimeFieldBuilder() {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTimeBuilder_ = new SingleFieldBuilderV3<>(getDateTime(), getParentForChildren(), isClean());
                    this.dateTime_ = null;
                }
                return this.dateTimeBuilder_;
            }

            public boolean hasGnssConf() {
                return (this.bitField0_ & 8) == 8;
            }

            public GnssConf getGnssConf() {
                if (this.gnssConfBuilder_ == null) {
                    return this.gnssConf_ == null ? GnssConf.getDefaultInstance() : this.gnssConf_;
                }
                return (GnssConf) this.gnssConfBuilder_.getMessage();
            }

            public Builder setGnssConf(GnssConf value) {
                if (this.gnssConfBuilder_ != null) {
                    this.gnssConfBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.gnssConf_ = value;
                    onChanged();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setGnssConf(Builder builderForValue) {
                if (this.gnssConfBuilder_ == null) {
                    this.gnssConf_ = builderForValue.build();
                    onChanged();
                } else {
                    this.gnssConfBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeGnssConf(GnssConf value) {
                if (this.gnssConfBuilder_ == null) {
                    if ((this.bitField0_ & 8) != 8 || this.gnssConf_ == null || this.gnssConf_ == GnssConf.getDefaultInstance()) {
                        this.gnssConf_ = value;
                    } else {
                        this.gnssConf_ = GnssConf.newBuilder(this.gnssConf_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.gnssConfBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearGnssConf() {
                if (this.gnssConfBuilder_ == null) {
                    this.gnssConf_ = null;
                    onChanged();
                } else {
                    this.gnssConfBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public Builder getGnssConfBuilder() {
                this.bitField0_ |= 8;
                onChanged();
                return (Builder) getGnssConfFieldBuilder().getBuilder();
            }

            public GnssConfOrBuilder getGnssConfOrBuilder() {
                if (this.gnssConfBuilder_ != null) {
                    return (GnssConfOrBuilder) this.gnssConfBuilder_.getMessageOrBuilder();
                }
                return this.gnssConf_ == null ? GnssConf.getDefaultInstance() : this.gnssConf_;
            }

            private SingleFieldBuilderV3<GnssConf, Builder, GnssConfOrBuilder> getGnssConfFieldBuilder() {
                if (this.gnssConfBuilder_ == null) {
                    this.gnssConfBuilder_ = new SingleFieldBuilderV3<>(getGnssConf(), getParentForChildren(), isClean());
                    this.gnssConf_ = null;
                }
                return this.gnssConfBuilder_;
            }

            public boolean hasHrAlarmConf() {
                return (this.bitField0_ & 16) == 16;
            }

            public HrAlarmConf getHrAlarmConf() {
                if (this.hrAlarmConfBuilder_ == null) {
                    return this.hrAlarmConf_ == null ? HrAlarmConf.getDefaultInstance() : this.hrAlarmConf_;
                }
                return (HrAlarmConf) this.hrAlarmConfBuilder_.getMessage();
            }

            public Builder setHrAlarmConf(HrAlarmConf value) {
                if (this.hrAlarmConfBuilder_ != null) {
                    this.hrAlarmConfBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.hrAlarmConf_ = value;
                    onChanged();
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder setHrAlarmConf(Builder builderForValue) {
                if (this.hrAlarmConfBuilder_ == null) {
                    this.hrAlarmConf_ = builderForValue.build();
                    onChanged();
                } else {
                    this.hrAlarmConfBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder mergeHrAlarmConf(HrAlarmConf value) {
                if (this.hrAlarmConfBuilder_ == null) {
                    if ((this.bitField0_ & 16) != 16 || this.hrAlarmConf_ == null || this.hrAlarmConf_ == HrAlarmConf.getDefaultInstance()) {
                        this.hrAlarmConf_ = value;
                    } else {
                        this.hrAlarmConf_ = HrAlarmConf.newBuilder(this.hrAlarmConf_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.hrAlarmConfBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder clearHrAlarmConf() {
                if (this.hrAlarmConfBuilder_ == null) {
                    this.hrAlarmConf_ = null;
                    onChanged();
                } else {
                    this.hrAlarmConfBuilder_.clear();
                }
                this.bitField0_ &= -17;
                return this;
            }

            public Builder getHrAlarmConfBuilder() {
                this.bitField0_ |= 16;
                onChanged();
                return (Builder) getHrAlarmConfFieldBuilder().getBuilder();
            }

            public HrAlarmConfOrBuilder getHrAlarmConfOrBuilder() {
                if (this.hrAlarmConfBuilder_ != null) {
                    return (HrAlarmConfOrBuilder) this.hrAlarmConfBuilder_.getMessageOrBuilder();
                }
                return this.hrAlarmConf_ == null ? HrAlarmConf.getDefaultInstance() : this.hrAlarmConf_;
            }

            private SingleFieldBuilderV3<HrAlarmConf, Builder, HrAlarmConfOrBuilder> getHrAlarmConfFieldBuilder() {
                if (this.hrAlarmConfBuilder_ == null) {
                    this.hrAlarmConfBuilder_ = new SingleFieldBuilderV3<>(getHrAlarmConf(), getParentForChildren(), isClean());
                    this.hrAlarmConf_ = null;
                }
                return this.hrAlarmConfBuilder_;
            }

            public boolean hasUserConf() {
                return (this.bitField0_ & 32) == 32;
            }

            public UserConf getUserConf() {
                if (this.userConfBuilder_ == null) {
                    return this.userConf_ == null ? UserConf.getDefaultInstance() : this.userConf_;
                }
                return (UserConf) this.userConfBuilder_.getMessage();
            }

            public Builder setUserConf(UserConf value) {
                if (this.userConfBuilder_ != null) {
                    this.userConfBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.userConf_ = value;
                    onChanged();
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder setUserConf(Builder builderForValue) {
                if (this.userConfBuilder_ == null) {
                    this.userConf_ = builderForValue.build();
                    onChanged();
                } else {
                    this.userConfBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder mergeUserConf(UserConf value) {
                if (this.userConfBuilder_ == null) {
                    if ((this.bitField0_ & 32) != 32 || this.userConf_ == null || this.userConf_ == UserConf.getDefaultInstance()) {
                        this.userConf_ = value;
                    } else {
                        this.userConf_ = UserConf.newBuilder(this.userConf_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.userConfBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder clearUserConf() {
                if (this.userConfBuilder_ == null) {
                    this.userConf_ = null;
                    onChanged();
                } else {
                    this.userConfBuilder_.clear();
                }
                this.bitField0_ &= -33;
                return this;
            }

            public Builder getUserConfBuilder() {
                this.bitField0_ |= 32;
                onChanged();
                return (Builder) getUserConfFieldBuilder().getBuilder();
            }

            public UserConfOrBuilder getUserConfOrBuilder() {
                if (this.userConfBuilder_ != null) {
                    return (UserConfOrBuilder) this.userConfBuilder_.getMessageOrBuilder();
                }
                return this.userConf_ == null ? UserConf.getDefaultInstance() : this.userConf_;
            }

            private SingleFieldBuilderV3<UserConf, Builder, UserConfOrBuilder> getUserConfFieldBuilder() {
                if (this.userConfBuilder_ == null) {
                    this.userConfBuilder_ = new SingleFieldBuilderV3<>(getUserConf(), getParentForChildren(), isClean());
                    this.userConf_ = null;
                }
                return this.userConfBuilder_;
            }

            public boolean hasGoalConf() {
                return (this.bitField0_ & 64) == 64;
            }

            public GoalConf getGoalConf() {
                if (this.goalConfBuilder_ == null) {
                    return this.goalConf_ == null ? GoalConf.getDefaultInstance() : this.goalConf_;
                }
                return (GoalConf) this.goalConfBuilder_.getMessage();
            }

            public Builder setGoalConf(GoalConf value) {
                if (this.goalConfBuilder_ != null) {
                    this.goalConfBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.goalConf_ = value;
                    onChanged();
                }
                this.bitField0_ |= 64;
                return this;
            }

            public Builder setGoalConf(Builder builderForValue) {
                if (this.goalConfBuilder_ == null) {
                    this.goalConf_ = builderForValue.build();
                    onChanged();
                } else {
                    this.goalConfBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 64;
                return this;
            }

            public Builder mergeGoalConf(GoalConf value) {
                if (this.goalConfBuilder_ == null) {
                    if ((this.bitField0_ & 64) != 64 || this.goalConf_ == null || this.goalConf_ == GoalConf.getDefaultInstance()) {
                        this.goalConf_ = value;
                    } else {
                        this.goalConf_ = GoalConf.newBuilder(this.goalConf_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.goalConfBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 64;
                return this;
            }

            public Builder clearGoalConf() {
                if (this.goalConfBuilder_ == null) {
                    this.goalConf_ = null;
                    onChanged();
                } else {
                    this.goalConfBuilder_.clear();
                }
                this.bitField0_ &= -65;
                return this;
            }

            public Builder getGoalConfBuilder() {
                this.bitField0_ |= 64;
                onChanged();
                return (Builder) getGoalConfFieldBuilder().getBuilder();
            }

            public GoalConfOrBuilder getGoalConfOrBuilder() {
                if (this.goalConfBuilder_ != null) {
                    return (GoalConfOrBuilder) this.goalConfBuilder_.getMessageOrBuilder();
                }
                return this.goalConf_ == null ? GoalConf.getDefaultInstance() : this.goalConf_;
            }

            private SingleFieldBuilderV3<GoalConf, Builder, GoalConfOrBuilder> getGoalConfFieldBuilder() {
                if (this.goalConfBuilder_ == null) {
                    this.goalConfBuilder_ = new SingleFieldBuilderV3<>(getGoalConf(), getParentForChildren(), isClean());
                    this.goalConf_ = null;
                }
                return this.goalConfBuilder_;
            }

            public boolean hasBpcaliConf() {
                return (this.bitField0_ & 128) == 128;
            }

            public BpCaliConf getBpcaliConf() {
                if (this.bpcaliConfBuilder_ == null) {
                    return this.bpcaliConf_ == null ? BpCaliConf.getDefaultInstance() : this.bpcaliConf_;
                }
                return (BpCaliConf) this.bpcaliConfBuilder_.getMessage();
            }

            public Builder setBpcaliConf(BpCaliConf value) {
                if (this.bpcaliConfBuilder_ != null) {
                    this.bpcaliConfBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.bpcaliConf_ = value;
                    onChanged();
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder setBpcaliConf(Builder builderForValue) {
                if (this.bpcaliConfBuilder_ == null) {
                    this.bpcaliConf_ = builderForValue.build();
                    onChanged();
                } else {
                    this.bpcaliConfBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder mergeBpcaliConf(BpCaliConf value) {
                if (this.bpcaliConfBuilder_ == null) {
                    if ((this.bitField0_ & 128) != 128 || this.bpcaliConf_ == null || this.bpcaliConf_ == BpCaliConf.getDefaultInstance()) {
                        this.bpcaliConf_ = value;
                    } else {
                        this.bpcaliConf_ = BpCaliConf.newBuilder(this.bpcaliConf_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.bpcaliConfBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder clearBpcaliConf() {
                if (this.bpcaliConfBuilder_ == null) {
                    this.bpcaliConf_ = null;
                    onChanged();
                } else {
                    this.bpcaliConfBuilder_.clear();
                }
                this.bitField0_ &= -129;
                return this;
            }

            public Builder getBpcaliConfBuilder() {
                this.bitField0_ |= 128;
                onChanged();
                return (Builder) getBpcaliConfFieldBuilder().getBuilder();
            }

            public BpCaliConfOrBuilder getBpcaliConfOrBuilder() {
                if (this.bpcaliConfBuilder_ != null) {
                    return (BpCaliConfOrBuilder) this.bpcaliConfBuilder_.getMessageOrBuilder();
                }
                return this.bpcaliConf_ == null ? BpCaliConf.getDefaultInstance() : this.bpcaliConf_;
            }

            private SingleFieldBuilderV3<BpCaliConf, Builder, BpCaliConfOrBuilder> getBpcaliConfFieldBuilder() {
                if (this.bpcaliConfBuilder_ == null) {
                    this.bpcaliConfBuilder_ = new SingleFieldBuilderV3<>(getBpcaliConf(), getParentForChildren(), isClean());
                    this.bpcaliConf_ = null;
                }
                return this.bpcaliConfBuilder_;
            }

            public boolean hasAfConf() {
                return (this.bitField0_ & 256) == 256;
            }

            public AfConf getAfConf() {
                if (this.afConfBuilder_ == null) {
                    return this.afConf_ == null ? AfConf.getDefaultInstance() : this.afConf_;
                }
                return (AfConf) this.afConfBuilder_.getMessage();
            }

            public Builder setAfConf(AfConf value) {
                if (this.afConfBuilder_ != null) {
                    this.afConfBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.afConf_ = value;
                    onChanged();
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder setAfConf(Builder builderForValue) {
                if (this.afConfBuilder_ == null) {
                    this.afConf_ = builderForValue.build();
                    onChanged();
                } else {
                    this.afConfBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder mergeAfConf(AfConf value) {
                if (this.afConfBuilder_ == null) {
                    if ((this.bitField0_ & 256) != 256 || this.afConf_ == null || this.afConf_ == AfConf.getDefaultInstance()) {
                        this.afConf_ = value;
                    } else {
                        this.afConf_ = AfConf.newBuilder(this.afConf_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.afConfBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder clearAfConf() {
                if (this.afConfBuilder_ == null) {
                    this.afConf_ = null;
                    onChanged();
                } else {
                    this.afConfBuilder_.clear();
                }
                this.bitField0_ &= -257;
                return this;
            }

            public Builder getAfConfBuilder() {
                this.bitField0_ |= 256;
                onChanged();
                return (Builder) getAfConfFieldBuilder().getBuilder();
            }

            public AfConfOrBuilder getAfConfOrBuilder() {
                if (this.afConfBuilder_ != null) {
                    return (AfConfOrBuilder) this.afConfBuilder_.getMessageOrBuilder();
                }
                return this.afConf_ == null ? AfConf.getDefaultInstance() : this.afConf_;
            }

            private SingleFieldBuilderV3<AfConf, Builder, AfConfOrBuilder> getAfConfFieldBuilder() {
                if (this.afConfBuilder_ == null) {
                    this.afConfBuilder_ = new SingleFieldBuilderV3<>(getAfConf(), getParentForChildren(), isClean());
                    this.afConf_ = null;
                }
                return this.afConfBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private PeerInfoNotification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private PeerInfoNotification() {
            this.memoizedIsInitialized = -1;
            this.peerType_ = 0;
            this.peerStatus_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private PeerInfoNotification(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (PeerType.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.peerType_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 16:
                            int rawValue2 = input.readEnum();
                            if (PeerStatus.valueOf(rawValue2) != null) {
                                this.bitField0_ |= 2;
                                this.peerStatus_ = rawValue2;
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue2);
                                break;
                            }
                        case 26:
                            com.iwown.ble_module.proto.base.RealtimeData.DateTime.Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.dateTime_.toBuilder();
                            }
                            this.dateTime_ = (DateTime) input.readMessage(DateTime.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.dateTime_);
                                this.dateTime_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            break;
                        case 34:
                            Builder subBuilder2 = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder2 = this.gnssConf_.toBuilder();
                            }
                            this.gnssConf_ = (GnssConf) input.readMessage(GnssConf.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.gnssConf_);
                                this.gnssConf_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            break;
                        case 42:
                            Builder subBuilder3 = null;
                            if ((this.bitField0_ & 16) == 16) {
                                subBuilder3 = this.hrAlarmConf_.toBuilder();
                            }
                            this.hrAlarmConf_ = (HrAlarmConf) input.readMessage(HrAlarmConf.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom(this.hrAlarmConf_);
                                this.hrAlarmConf_ = subBuilder3.buildPartial();
                            }
                            this.bitField0_ |= 16;
                            break;
                        case 50:
                            Builder subBuilder4 = null;
                            if ((this.bitField0_ & 32) == 32) {
                                subBuilder4 = this.userConf_.toBuilder();
                            }
                            this.userConf_ = (UserConf) input.readMessage(UserConf.PARSER, extensionRegistry);
                            if (subBuilder4 != null) {
                                subBuilder4.mergeFrom(this.userConf_);
                                this.userConf_ = subBuilder4.buildPartial();
                            }
                            this.bitField0_ |= 32;
                            break;
                        case 58:
                            Builder subBuilder5 = null;
                            if ((this.bitField0_ & 64) == 64) {
                                subBuilder5 = this.goalConf_.toBuilder();
                            }
                            this.goalConf_ = (GoalConf) input.readMessage(GoalConf.PARSER, extensionRegistry);
                            if (subBuilder5 != null) {
                                subBuilder5.mergeFrom(this.goalConf_);
                                this.goalConf_ = subBuilder5.buildPartial();
                            }
                            this.bitField0_ |= 64;
                            break;
                        case 66:
                            Builder subBuilder6 = null;
                            if ((this.bitField0_ & 128) == 128) {
                                subBuilder6 = this.bpcaliConf_.toBuilder();
                            }
                            this.bpcaliConf_ = (BpCaliConf) input.readMessage(BpCaliConf.PARSER, extensionRegistry);
                            if (subBuilder6 != null) {
                                subBuilder6.mergeFrom(this.bpcaliConf_);
                                this.bpcaliConf_ = subBuilder6.buildPartial();
                            }
                            this.bitField0_ |= 128;
                            break;
                        case 74:
                            Builder subBuilder7 = null;
                            if ((this.bitField0_ & 256) == 256) {
                                subBuilder7 = this.afConf_.toBuilder();
                            }
                            this.afConf_ = (AfConf) input.readMessage(AfConf.PARSER, extensionRegistry);
                            if (subBuilder7 != null) {
                                subBuilder7.mergeFrom(this.afConf_);
                                this.afConf_ = subBuilder7.buildPartial();
                            }
                            this.bitField0_ |= 256;
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
            return PeerInfo.internal_static_PeerInfoNotification_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return PeerInfo.internal_static_PeerInfoNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(PeerInfoNotification.class, Builder.class);
        }

        public boolean hasPeerType() {
            return (this.bitField0_ & 1) == 1;
        }

        public PeerType getPeerType() {
            PeerType result = PeerType.valueOf(this.peerType_);
            return result == null ? PeerType.APP_ANDROID : result;
        }

        public boolean hasPeerStatus() {
            return (this.bitField0_ & 2) == 2;
        }

        public PeerStatus getPeerStatus() {
            PeerStatus result = PeerStatus.valueOf(this.peerStatus_);
            return result == null ? PeerStatus.APP_BACKGROUND : result;
        }

        public boolean hasDateTime() {
            return (this.bitField0_ & 4) == 4;
        }

        public DateTime getDateTime() {
            return this.dateTime_ == null ? DateTime.getDefaultInstance() : this.dateTime_;
        }

        public DateTimeOrBuilder getDateTimeOrBuilder() {
            return this.dateTime_ == null ? DateTime.getDefaultInstance() : this.dateTime_;
        }

        public boolean hasGnssConf() {
            return (this.bitField0_ & 8) == 8;
        }

        public GnssConf getGnssConf() {
            return this.gnssConf_ == null ? GnssConf.getDefaultInstance() : this.gnssConf_;
        }

        public GnssConfOrBuilder getGnssConfOrBuilder() {
            return this.gnssConf_ == null ? GnssConf.getDefaultInstance() : this.gnssConf_;
        }

        public boolean hasHrAlarmConf() {
            return (this.bitField0_ & 16) == 16;
        }

        public HrAlarmConf getHrAlarmConf() {
            return this.hrAlarmConf_ == null ? HrAlarmConf.getDefaultInstance() : this.hrAlarmConf_;
        }

        public HrAlarmConfOrBuilder getHrAlarmConfOrBuilder() {
            return this.hrAlarmConf_ == null ? HrAlarmConf.getDefaultInstance() : this.hrAlarmConf_;
        }

        public boolean hasUserConf() {
            return (this.bitField0_ & 32) == 32;
        }

        public UserConf getUserConf() {
            return this.userConf_ == null ? UserConf.getDefaultInstance() : this.userConf_;
        }

        public UserConfOrBuilder getUserConfOrBuilder() {
            return this.userConf_ == null ? UserConf.getDefaultInstance() : this.userConf_;
        }

        public boolean hasGoalConf() {
            return (this.bitField0_ & 64) == 64;
        }

        public GoalConf getGoalConf() {
            return this.goalConf_ == null ? GoalConf.getDefaultInstance() : this.goalConf_;
        }

        public GoalConfOrBuilder getGoalConfOrBuilder() {
            return this.goalConf_ == null ? GoalConf.getDefaultInstance() : this.goalConf_;
        }

        public boolean hasBpcaliConf() {
            return (this.bitField0_ & 128) == 128;
        }

        public BpCaliConf getBpcaliConf() {
            return this.bpcaliConf_ == null ? BpCaliConf.getDefaultInstance() : this.bpcaliConf_;
        }

        public BpCaliConfOrBuilder getBpcaliConfOrBuilder() {
            return this.bpcaliConf_ == null ? BpCaliConf.getDefaultInstance() : this.bpcaliConf_;
        }

        public boolean hasAfConf() {
            return (this.bitField0_ & 256) == 256;
        }

        public AfConf getAfConf() {
            return this.afConf_ == null ? AfConf.getDefaultInstance() : this.afConf_;
        }

        public AfConfOrBuilder getAfConfOrBuilder() {
            return this.afConf_ == null ? AfConf.getDefaultInstance() : this.afConf_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (hasDateTime() && !getDateTime().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasGnssConf() && !getGnssConf().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasHrAlarmConf() && !getHrAlarmConf().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasUserConf() && !getUserConf().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasGoalConf() && !getGoalConf().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasBpcaliConf() && !getBpcaliConf().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasAfConf() || getAfConf().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.peerType_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeEnum(2, this.peerStatus_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, getDateTime());
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(4, getGnssConf());
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeMessage(5, getHrAlarmConf());
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeMessage(6, getUserConf());
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeMessage(7, getGoalConf());
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeMessage(8, getBpcaliConf());
            }
            if ((this.bitField0_ & 256) == 256) {
                output.writeMessage(9, getAfConf());
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.peerType_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeEnumSize(2, this.peerStatus_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeMessageSize(3, getDateTime());
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeMessageSize(4, getGnssConf());
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeMessageSize(5, getHrAlarmConf());
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeMessageSize(6, getUserConf());
            }
            if ((this.bitField0_ & 64) == 64) {
                size2 += CodedOutputStream.computeMessageSize(7, getGoalConf());
            }
            if ((this.bitField0_ & 128) == 128) {
                size2 += CodedOutputStream.computeMessageSize(8, getBpcaliConf());
            }
            if ((this.bitField0_ & 256) == 256) {
                size2 += CodedOutputStream.computeMessageSize(9, getAfConf());
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
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PeerInfoNotification)) {
                return super.equals(obj);
            }
            PeerInfoNotification other = (PeerInfoNotification) obj;
            boolean result10 = 1 != 0 && hasPeerType() == other.hasPeerType();
            if (hasPeerType()) {
                result10 = result10 && this.peerType_ == other.peerType_;
            }
            if (!result10 || hasPeerStatus() != other.hasPeerStatus()) {
                result = false;
            } else {
                result = true;
            }
            if (hasPeerStatus()) {
                if (!result || this.peerStatus_ != other.peerStatus_) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasDateTime() != other.hasDateTime()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasDateTime()) {
                if (!result2 || !getDateTime().equals(other.getDateTime())) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasGnssConf() != other.hasGnssConf()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasGnssConf()) {
                if (!result3 || !getGnssConf().equals(other.getGnssConf())) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasHrAlarmConf() != other.hasHrAlarmConf()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasHrAlarmConf()) {
                if (!result4 || !getHrAlarmConf().equals(other.getHrAlarmConf())) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasUserConf() != other.hasUserConf()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasUserConf()) {
                if (!result5 || !getUserConf().equals(other.getUserConf())) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || hasGoalConf() != other.hasGoalConf()) {
                result6 = false;
            } else {
                result6 = true;
            }
            if (hasGoalConf()) {
                if (!result6 || !getGoalConf().equals(other.getGoalConf())) {
                    result6 = false;
                } else {
                    result6 = true;
                }
            }
            if (!result6 || hasBpcaliConf() != other.hasBpcaliConf()) {
                result7 = false;
            } else {
                result7 = true;
            }
            if (hasBpcaliConf()) {
                if (!result7 || !getBpcaliConf().equals(other.getBpcaliConf())) {
                    result7 = false;
                } else {
                    result7 = true;
                }
            }
            if (!result7 || hasAfConf() != other.hasAfConf()) {
                result8 = false;
            } else {
                result8 = true;
            }
            if (hasAfConf()) {
                if (!result8 || !getAfConf().equals(other.getAfConf())) {
                    result8 = false;
                } else {
                    result8 = true;
                }
            }
            if (!result8 || !this.unknownFields.equals(other.unknownFields)) {
                result9 = false;
            } else {
                result9 = true;
            }
            return result9;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasPeerType()) {
                hash = (((hash * 37) + 1) * 53) + this.peerType_;
            }
            if (hasPeerStatus()) {
                hash = (((hash * 37) + 2) * 53) + this.peerStatus_;
            }
            if (hasDateTime()) {
                hash = (((hash * 37) + 3) * 53) + getDateTime().hashCode();
            }
            if (hasGnssConf()) {
                hash = (((hash * 37) + 4) * 53) + getGnssConf().hashCode();
            }
            if (hasHrAlarmConf()) {
                hash = (((hash * 37) + 5) * 53) + getHrAlarmConf().hashCode();
            }
            if (hasUserConf()) {
                hash = (((hash * 37) + 6) * 53) + getUserConf().hashCode();
            }
            if (hasGoalConf()) {
                hash = (((hash * 37) + 7) * 53) + getGoalConf().hashCode();
            }
            if (hasBpcaliConf()) {
                hash = (((hash * 37) + 8) * 53) + getBpcaliConf().hashCode();
            }
            if (hasAfConf()) {
                hash = (((hash * 37) + 9) * 53) + getAfConf().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static PeerInfoNotification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PeerInfoNotification) PARSER.parseFrom(data);
        }

        public static PeerInfoNotification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PeerInfoNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static PeerInfoNotification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PeerInfoNotification) PARSER.parseFrom(data);
        }

        public static PeerInfoNotification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PeerInfoNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static PeerInfoNotification parseFrom(InputStream input) throws IOException {
            return (PeerInfoNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static PeerInfoNotification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerInfoNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static PeerInfoNotification parseDelimitedFrom(InputStream input) throws IOException {
            return (PeerInfoNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static PeerInfoNotification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerInfoNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static PeerInfoNotification parseFrom(CodedInputStream input) throws IOException {
            return (PeerInfoNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static PeerInfoNotification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerInfoNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(PeerInfoNotification prototype) {
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

        public static PeerInfoNotification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PeerInfoNotification> parser() {
            return PARSER;
        }

        public Parser<PeerInfoNotification> getParserForType() {
            return PARSER;
        }

        public PeerInfoNotification getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class PeerInfoSubsriber extends GeneratedMessageV3 implements PeerInfoSubsriberOrBuilder {
        private static final PeerInfoSubsriber DEFAULT_INSTANCE = new PeerInfoSubsriber();
        public static final int HASH_AF_CONF_FIELD_NUMBER = 16;
        public static final int HASH_BPCALI_CONF_FIELD_NUMBER = 14;
        public static final int HASH_GNSS_CONF_FIELD_NUMBER = 9;
        public static final int HASH_GOAL_CONF_FIELD_NUMBER = 12;
        public static final int HASH_HR_ALARM_CONF_FIELD_NUMBER = 10;
        public static final int HASH_USER_CONF_FIELD_NUMBER = 11;
        @Deprecated
        public static final Parser<PeerInfoSubsriber> PARSER = new AbstractParser<PeerInfoSubsriber>() {
            public PeerInfoSubsriber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new PeerInfoSubsriber(input, extensionRegistry);
            }
        };
        public static final int SUPPORT_AF_CONF_FIELD_NUMBER = 15;
        public static final int SUPPORT_BPCALI_CONF_FIELD_NUMBER = 13;
        public static final int SUPPORT_DATE_TIME_FIELD_NUMBER = 3;
        public static final int SUPPORT_GNSS_CONF_FIELD_NUMBER = 4;
        public static final int SUPPORT_GOAL_CONF_FIELD_NUMBER = 7;
        public static final int SUPPORT_HR_ALARM_CONF_FIELD_NUMBER = 5;
        public static final int SUPPORT_PEER_STATUS_FIELD_NUMBER = 2;
        public static final int SUPPORT_PEER_TYPE_FIELD_NUMBER = 1;
        public static final int SUPPORT_USER_CONF_FIELD_NUMBER = 6;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int hashAfConf_;
        /* access modifiers changed from: private */
        public boolean hashBpcaliConf_;
        /* access modifiers changed from: private */
        public int hashGnssConf_;
        /* access modifiers changed from: private */
        public int hashGoalConf_;
        /* access modifiers changed from: private */
        public int hashHrAlarmConf_;
        /* access modifiers changed from: private */
        public int hashUserConf_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public boolean supportAfConf_;
        /* access modifiers changed from: private */
        public boolean supportBpcaliConf_;
        /* access modifiers changed from: private */
        public boolean supportDateTime_;
        /* access modifiers changed from: private */
        public boolean supportGnssConf_;
        /* access modifiers changed from: private */
        public boolean supportGoalConf_;
        /* access modifiers changed from: private */
        public boolean supportHrAlarmConf_;
        /* access modifiers changed from: private */
        public boolean supportPeerStatus_;
        /* access modifiers changed from: private */
        public boolean supportPeerType_;
        /* access modifiers changed from: private */
        public boolean supportUserConf_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements PeerInfoSubsriberOrBuilder {
            private int bitField0_;
            private int hashAfConf_;
            private boolean hashBpcaliConf_;
            private int hashGnssConf_;
            private int hashGoalConf_;
            private int hashHrAlarmConf_;
            private int hashUserConf_;
            private boolean supportAfConf_;
            private boolean supportBpcaliConf_;
            private boolean supportDateTime_;
            private boolean supportGnssConf_;
            private boolean supportGoalConf_;
            private boolean supportHrAlarmConf_;
            private boolean supportPeerStatus_;
            private boolean supportPeerType_;
            private boolean supportUserConf_;

            public static final Descriptor getDescriptor() {
                return PeerInfo.internal_static_PeerInfoSubsriber_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PeerInfo.internal_static_PeerInfoSubsriber_fieldAccessorTable.ensureFieldAccessorsInitialized(PeerInfoSubsriber.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (PeerInfoSubsriber.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.supportPeerType_ = false;
                this.bitField0_ &= -2;
                this.supportPeerStatus_ = false;
                this.bitField0_ &= -3;
                this.supportDateTime_ = false;
                this.bitField0_ &= -5;
                this.supportGnssConf_ = false;
                this.bitField0_ &= -9;
                this.supportHrAlarmConf_ = false;
                this.bitField0_ &= -17;
                this.supportUserConf_ = false;
                this.bitField0_ &= -33;
                this.supportGoalConf_ = false;
                this.bitField0_ &= -65;
                this.hashGnssConf_ = 0;
                this.bitField0_ &= -129;
                this.hashHrAlarmConf_ = 0;
                this.bitField0_ &= -257;
                this.hashUserConf_ = 0;
                this.bitField0_ &= -513;
                this.hashGoalConf_ = 0;
                this.bitField0_ &= -1025;
                this.supportBpcaliConf_ = false;
                this.bitField0_ &= -2049;
                this.hashBpcaliConf_ = false;
                this.bitField0_ &= -4097;
                this.supportAfConf_ = false;
                this.bitField0_ &= -8193;
                this.hashAfConf_ = 0;
                this.bitField0_ &= -16385;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return PeerInfo.internal_static_PeerInfoSubsriber_descriptor;
            }

            public PeerInfoSubsriber getDefaultInstanceForType() {
                return PeerInfoSubsriber.getDefaultInstance();
            }

            public PeerInfoSubsriber build() {
                PeerInfoSubsriber result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public PeerInfoSubsriber buildPartial() {
                PeerInfoSubsriber result = new PeerInfoSubsriber((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.supportPeerType_ = this.supportPeerType_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.supportPeerStatus_ = this.supportPeerStatus_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.supportDateTime_ = this.supportDateTime_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.supportGnssConf_ = this.supportGnssConf_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.supportHrAlarmConf_ = this.supportHrAlarmConf_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.supportUserConf_ = this.supportUserConf_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.supportGoalConf_ = this.supportGoalConf_;
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                result.hashGnssConf_ = this.hashGnssConf_;
                if ((from_bitField0_ & 256) == 256) {
                    to_bitField0_ |= 256;
                }
                result.hashHrAlarmConf_ = this.hashHrAlarmConf_;
                if ((from_bitField0_ & 512) == 512) {
                    to_bitField0_ |= 512;
                }
                result.hashUserConf_ = this.hashUserConf_;
                if ((from_bitField0_ & 1024) == 1024) {
                    to_bitField0_ |= 1024;
                }
                result.hashGoalConf_ = this.hashGoalConf_;
                if ((from_bitField0_ & 2048) == 2048) {
                    to_bitField0_ |= 2048;
                }
                result.supportBpcaliConf_ = this.supportBpcaliConf_;
                if ((from_bitField0_ & 4096) == 4096) {
                    to_bitField0_ |= 4096;
                }
                result.hashBpcaliConf_ = this.hashBpcaliConf_;
                if ((from_bitField0_ & 8192) == 8192) {
                    to_bitField0_ |= 8192;
                }
                result.supportAfConf_ = this.supportAfConf_;
                if ((from_bitField0_ & 16384) == 16384) {
                    to_bitField0_ |= 16384;
                }
                result.hashAfConf_ = this.hashAfConf_;
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
                if (other instanceof PeerInfoSubsriber) {
                    return mergeFrom((PeerInfoSubsriber) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(PeerInfoSubsriber other) {
                if (other != PeerInfoSubsriber.getDefaultInstance()) {
                    if (other.hasSupportPeerType()) {
                        setSupportPeerType(other.getSupportPeerType());
                    }
                    if (other.hasSupportPeerStatus()) {
                        setSupportPeerStatus(other.getSupportPeerStatus());
                    }
                    if (other.hasSupportDateTime()) {
                        setSupportDateTime(other.getSupportDateTime());
                    }
                    if (other.hasSupportGnssConf()) {
                        setSupportGnssConf(other.getSupportGnssConf());
                    }
                    if (other.hasSupportHrAlarmConf()) {
                        setSupportHrAlarmConf(other.getSupportHrAlarmConf());
                    }
                    if (other.hasSupportUserConf()) {
                        setSupportUserConf(other.getSupportUserConf());
                    }
                    if (other.hasSupportGoalConf()) {
                        setSupportGoalConf(other.getSupportGoalConf());
                    }
                    if (other.hasHashGnssConf()) {
                        setHashGnssConf(other.getHashGnssConf());
                    }
                    if (other.hasHashHrAlarmConf()) {
                        setHashHrAlarmConf(other.getHashHrAlarmConf());
                    }
                    if (other.hasHashUserConf()) {
                        setHashUserConf(other.getHashUserConf());
                    }
                    if (other.hasHashGoalConf()) {
                        setHashGoalConf(other.getHashGoalConf());
                    }
                    if (other.hasSupportBpcaliConf()) {
                        setSupportBpcaliConf(other.getSupportBpcaliConf());
                    }
                    if (other.hasHashBpcaliConf()) {
                        setHashBpcaliConf(other.getHashBpcaliConf());
                    }
                    if (other.hasSupportAfConf()) {
                        setSupportAfConf(other.getSupportAfConf());
                    }
                    if (other.hasHashAfConf()) {
                        setHashAfConf(other.getHashAfConf());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasSupportPeerType() && hasSupportPeerStatus() && hasSupportDateTime() && hasSupportGnssConf() && hasSupportHrAlarmConf() && hasSupportUserConf() && hasSupportGoalConf()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                PeerInfoSubsriber parsedMessage = null;
                try {
                    PeerInfoSubsriber parsedMessage2 = (PeerInfoSubsriber) PeerInfoSubsriber.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    PeerInfoSubsriber parsedMessage3 = (PeerInfoSubsriber) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSupportPeerType() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getSupportPeerType() {
                return this.supportPeerType_;
            }

            public Builder setSupportPeerType(boolean value) {
                this.bitField0_ |= 1;
                this.supportPeerType_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportPeerType() {
                this.bitField0_ &= -2;
                this.supportPeerType_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportPeerStatus() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getSupportPeerStatus() {
                return this.supportPeerStatus_;
            }

            public Builder setSupportPeerStatus(boolean value) {
                this.bitField0_ |= 2;
                this.supportPeerStatus_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportPeerStatus() {
                this.bitField0_ &= -3;
                this.supportPeerStatus_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportDateTime() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getSupportDateTime() {
                return this.supportDateTime_;
            }

            public Builder setSupportDateTime(boolean value) {
                this.bitField0_ |= 4;
                this.supportDateTime_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportDateTime() {
                this.bitField0_ &= -5;
                this.supportDateTime_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportGnssConf() {
                return (this.bitField0_ & 8) == 8;
            }

            public boolean getSupportGnssConf() {
                return this.supportGnssConf_;
            }

            public Builder setSupportGnssConf(boolean value) {
                this.bitField0_ |= 8;
                this.supportGnssConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportGnssConf() {
                this.bitField0_ &= -9;
                this.supportGnssConf_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportHrAlarmConf() {
                return (this.bitField0_ & 16) == 16;
            }

            public boolean getSupportHrAlarmConf() {
                return this.supportHrAlarmConf_;
            }

            public Builder setSupportHrAlarmConf(boolean value) {
                this.bitField0_ |= 16;
                this.supportHrAlarmConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportHrAlarmConf() {
                this.bitField0_ &= -17;
                this.supportHrAlarmConf_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportUserConf() {
                return (this.bitField0_ & 32) == 32;
            }

            public boolean getSupportUserConf() {
                return this.supportUserConf_;
            }

            public Builder setSupportUserConf(boolean value) {
                this.bitField0_ |= 32;
                this.supportUserConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportUserConf() {
                this.bitField0_ &= -33;
                this.supportUserConf_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportGoalConf() {
                return (this.bitField0_ & 64) == 64;
            }

            public boolean getSupportGoalConf() {
                return this.supportGoalConf_;
            }

            public Builder setSupportGoalConf(boolean value) {
                this.bitField0_ |= 64;
                this.supportGoalConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportGoalConf() {
                this.bitField0_ &= -65;
                this.supportGoalConf_ = false;
                onChanged();
                return this;
            }

            public boolean hasHashGnssConf() {
                return (this.bitField0_ & 128) == 128;
            }

            public int getHashGnssConf() {
                return this.hashGnssConf_;
            }

            public Builder setHashGnssConf(int value) {
                this.bitField0_ |= 128;
                this.hashGnssConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearHashGnssConf() {
                this.bitField0_ &= -129;
                this.hashGnssConf_ = 0;
                onChanged();
                return this;
            }

            public boolean hasHashHrAlarmConf() {
                return (this.bitField0_ & 256) == 256;
            }

            public int getHashHrAlarmConf() {
                return this.hashHrAlarmConf_;
            }

            public Builder setHashHrAlarmConf(int value) {
                this.bitField0_ |= 256;
                this.hashHrAlarmConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearHashHrAlarmConf() {
                this.bitField0_ &= -257;
                this.hashHrAlarmConf_ = 0;
                onChanged();
                return this;
            }

            public boolean hasHashUserConf() {
                return (this.bitField0_ & 512) == 512;
            }

            public int getHashUserConf() {
                return this.hashUserConf_;
            }

            public Builder setHashUserConf(int value) {
                this.bitField0_ |= 512;
                this.hashUserConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearHashUserConf() {
                this.bitField0_ &= -513;
                this.hashUserConf_ = 0;
                onChanged();
                return this;
            }

            public boolean hasHashGoalConf() {
                return (this.bitField0_ & 1024) == 1024;
            }

            public int getHashGoalConf() {
                return this.hashGoalConf_;
            }

            public Builder setHashGoalConf(int value) {
                this.bitField0_ |= 1024;
                this.hashGoalConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearHashGoalConf() {
                this.bitField0_ &= -1025;
                this.hashGoalConf_ = 0;
                onChanged();
                return this;
            }

            public boolean hasSupportBpcaliConf() {
                return (this.bitField0_ & 2048) == 2048;
            }

            public boolean getSupportBpcaliConf() {
                return this.supportBpcaliConf_;
            }

            public Builder setSupportBpcaliConf(boolean value) {
                this.bitField0_ |= 2048;
                this.supportBpcaliConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportBpcaliConf() {
                this.bitField0_ &= -2049;
                this.supportBpcaliConf_ = false;
                onChanged();
                return this;
            }

            public boolean hasHashBpcaliConf() {
                return (this.bitField0_ & 4096) == 4096;
            }

            public boolean getHashBpcaliConf() {
                return this.hashBpcaliConf_;
            }

            public Builder setHashBpcaliConf(boolean value) {
                this.bitField0_ |= 4096;
                this.hashBpcaliConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearHashBpcaliConf() {
                this.bitField0_ &= -4097;
                this.hashBpcaliConf_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportAfConf() {
                return (this.bitField0_ & 8192) == 8192;
            }

            public boolean getSupportAfConf() {
                return this.supportAfConf_;
            }

            public Builder setSupportAfConf(boolean value) {
                this.bitField0_ |= 8192;
                this.supportAfConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportAfConf() {
                this.bitField0_ &= -8193;
                this.supportAfConf_ = false;
                onChanged();
                return this;
            }

            public boolean hasHashAfConf() {
                return (this.bitField0_ & 16384) == 16384;
            }

            public int getHashAfConf() {
                return this.hashAfConf_;
            }

            public Builder setHashAfConf(int value) {
                this.bitField0_ |= 16384;
                this.hashAfConf_ = value;
                onChanged();
                return this;
            }

            public Builder clearHashAfConf() {
                this.bitField0_ &= -16385;
                this.hashAfConf_ = 0;
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

        private PeerInfoSubsriber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private PeerInfoSubsriber() {
            this.memoizedIsInitialized = -1;
            this.supportPeerType_ = false;
            this.supportPeerStatus_ = false;
            this.supportDateTime_ = false;
            this.supportGnssConf_ = false;
            this.supportHrAlarmConf_ = false;
            this.supportUserConf_ = false;
            this.supportGoalConf_ = false;
            this.hashGnssConf_ = 0;
            this.hashHrAlarmConf_ = 0;
            this.hashUserConf_ = 0;
            this.hashGoalConf_ = 0;
            this.supportBpcaliConf_ = false;
            this.hashBpcaliConf_ = false;
            this.supportAfConf_ = false;
            this.hashAfConf_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private PeerInfoSubsriber(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.supportPeerType_ = input.readBool();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.supportPeerStatus_ = input.readBool();
                            break;
                        case 24:
                            this.bitField0_ |= 4;
                            this.supportDateTime_ = input.readBool();
                            break;
                        case 32:
                            this.bitField0_ |= 8;
                            this.supportGnssConf_ = input.readBool();
                            break;
                        case 40:
                            this.bitField0_ |= 16;
                            this.supportHrAlarmConf_ = input.readBool();
                            break;
                        case 48:
                            this.bitField0_ |= 32;
                            this.supportUserConf_ = input.readBool();
                            break;
                        case 56:
                            this.bitField0_ |= 64;
                            this.supportGoalConf_ = input.readBool();
                            break;
                        case 77:
                            this.bitField0_ |= 128;
                            this.hashGnssConf_ = input.readFixed32();
                            break;
                        case 85:
                            this.bitField0_ |= 256;
                            this.hashHrAlarmConf_ = input.readFixed32();
                            break;
                        case 93:
                            this.bitField0_ |= 512;
                            this.hashUserConf_ = input.readFixed32();
                            break;
                        case 101:
                            this.bitField0_ |= 1024;
                            this.hashGoalConf_ = input.readFixed32();
                            break;
                        case 104:
                            this.bitField0_ |= 2048;
                            this.supportBpcaliConf_ = input.readBool();
                            break;
                        case 112:
                            this.bitField0_ |= 4096;
                            this.hashBpcaliConf_ = input.readBool();
                            break;
                        case 120:
                            this.bitField0_ |= 8192;
                            this.supportAfConf_ = input.readBool();
                            break;
                        case Opcodes.LONG_TO_FLOAT /*133*/:
                            this.bitField0_ |= 16384;
                            this.hashAfConf_ = input.readFixed32();
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
            return PeerInfo.internal_static_PeerInfoSubsriber_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return PeerInfo.internal_static_PeerInfoSubsriber_fieldAccessorTable.ensureFieldAccessorsInitialized(PeerInfoSubsriber.class, Builder.class);
        }

        public boolean hasSupportPeerType() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getSupportPeerType() {
            return this.supportPeerType_;
        }

        public boolean hasSupportPeerStatus() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getSupportPeerStatus() {
            return this.supportPeerStatus_;
        }

        public boolean hasSupportDateTime() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getSupportDateTime() {
            return this.supportDateTime_;
        }

        public boolean hasSupportGnssConf() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean getSupportGnssConf() {
            return this.supportGnssConf_;
        }

        public boolean hasSupportHrAlarmConf() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean getSupportHrAlarmConf() {
            return this.supportHrAlarmConf_;
        }

        public boolean hasSupportUserConf() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean getSupportUserConf() {
            return this.supportUserConf_;
        }

        public boolean hasSupportGoalConf() {
            return (this.bitField0_ & 64) == 64;
        }

        public boolean getSupportGoalConf() {
            return this.supportGoalConf_;
        }

        public boolean hasHashGnssConf() {
            return (this.bitField0_ & 128) == 128;
        }

        public int getHashGnssConf() {
            return this.hashGnssConf_;
        }

        public boolean hasHashHrAlarmConf() {
            return (this.bitField0_ & 256) == 256;
        }

        public int getHashHrAlarmConf() {
            return this.hashHrAlarmConf_;
        }

        public boolean hasHashUserConf() {
            return (this.bitField0_ & 512) == 512;
        }

        public int getHashUserConf() {
            return this.hashUserConf_;
        }

        public boolean hasHashGoalConf() {
            return (this.bitField0_ & 1024) == 1024;
        }

        public int getHashGoalConf() {
            return this.hashGoalConf_;
        }

        public boolean hasSupportBpcaliConf() {
            return (this.bitField0_ & 2048) == 2048;
        }

        public boolean getSupportBpcaliConf() {
            return this.supportBpcaliConf_;
        }

        public boolean hasHashBpcaliConf() {
            return (this.bitField0_ & 4096) == 4096;
        }

        public boolean getHashBpcaliConf() {
            return this.hashBpcaliConf_;
        }

        public boolean hasSupportAfConf() {
            return (this.bitField0_ & 8192) == 8192;
        }

        public boolean getSupportAfConf() {
            return this.supportAfConf_;
        }

        public boolean hasHashAfConf() {
            return (this.bitField0_ & 16384) == 16384;
        }

        public int getHashAfConf() {
            return this.hashAfConf_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSupportPeerType()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportPeerStatus()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportDateTime()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportGnssConf()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportHrAlarmConf()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportUserConf()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportGoalConf()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(1, this.supportPeerType_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.supportPeerStatus_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(3, this.supportDateTime_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBool(4, this.supportGnssConf_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBool(5, this.supportHrAlarmConf_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeBool(6, this.supportUserConf_);
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeBool(7, this.supportGoalConf_);
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeFixed32(9, this.hashGnssConf_);
            }
            if ((this.bitField0_ & 256) == 256) {
                output.writeFixed32(10, this.hashHrAlarmConf_);
            }
            if ((this.bitField0_ & 512) == 512) {
                output.writeFixed32(11, this.hashUserConf_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                output.writeFixed32(12, this.hashGoalConf_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                output.writeBool(13, this.supportBpcaliConf_);
            }
            if ((this.bitField0_ & 4096) == 4096) {
                output.writeBool(14, this.hashBpcaliConf_);
            }
            if ((this.bitField0_ & 8192) == 8192) {
                output.writeBool(15, this.supportAfConf_);
            }
            if ((this.bitField0_ & 16384) == 16384) {
                output.writeFixed32(16, this.hashAfConf_);
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
                size2 = 0 + CodedOutputStream.computeBoolSize(1, this.supportPeerType_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.supportPeerStatus_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeBoolSize(3, this.supportDateTime_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeBoolSize(4, this.supportGnssConf_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeBoolSize(5, this.supportHrAlarmConf_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeBoolSize(6, this.supportUserConf_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size2 += CodedOutputStream.computeBoolSize(7, this.supportGoalConf_);
            }
            if ((this.bitField0_ & 128) == 128) {
                size2 += CodedOutputStream.computeFixed32Size(9, this.hashGnssConf_);
            }
            if ((this.bitField0_ & 256) == 256) {
                size2 += CodedOutputStream.computeFixed32Size(10, this.hashHrAlarmConf_);
            }
            if ((this.bitField0_ & 512) == 512) {
                size2 += CodedOutputStream.computeFixed32Size(11, this.hashUserConf_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                size2 += CodedOutputStream.computeFixed32Size(12, this.hashGoalConf_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                size2 += CodedOutputStream.computeBoolSize(13, this.supportBpcaliConf_);
            }
            if ((this.bitField0_ & 4096) == 4096) {
                size2 += CodedOutputStream.computeBoolSize(14, this.hashBpcaliConf_);
            }
            if ((this.bitField0_ & 8192) == 8192) {
                size2 += CodedOutputStream.computeBoolSize(15, this.supportAfConf_);
            }
            if ((this.bitField0_ & 16384) == 16384) {
                size2 += CodedOutputStream.computeFixed32Size(16, this.hashAfConf_);
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
            boolean result11;
            boolean result12;
            boolean result13;
            boolean result14;
            boolean result15;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PeerInfoSubsriber)) {
                return super.equals(obj);
            }
            PeerInfoSubsriber other = (PeerInfoSubsriber) obj;
            boolean result16 = 1 != 0 && hasSupportPeerType() == other.hasSupportPeerType();
            if (hasSupportPeerType()) {
                result16 = result16 && getSupportPeerType() == other.getSupportPeerType();
            }
            if (!result16 || hasSupportPeerStatus() != other.hasSupportPeerStatus()) {
                result = false;
            } else {
                result = true;
            }
            if (hasSupportPeerStatus()) {
                if (!result || getSupportPeerStatus() != other.getSupportPeerStatus()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasSupportDateTime() != other.hasSupportDateTime()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasSupportDateTime()) {
                if (!result2 || getSupportDateTime() != other.getSupportDateTime()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasSupportGnssConf() != other.hasSupportGnssConf()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasSupportGnssConf()) {
                if (!result3 || getSupportGnssConf() != other.getSupportGnssConf()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasSupportHrAlarmConf() != other.hasSupportHrAlarmConf()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasSupportHrAlarmConf()) {
                if (!result4 || getSupportHrAlarmConf() != other.getSupportHrAlarmConf()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasSupportUserConf() != other.hasSupportUserConf()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasSupportUserConf()) {
                if (!result5 || getSupportUserConf() != other.getSupportUserConf()) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || hasSupportGoalConf() != other.hasSupportGoalConf()) {
                result6 = false;
            } else {
                result6 = true;
            }
            if (hasSupportGoalConf()) {
                if (!result6 || getSupportGoalConf() != other.getSupportGoalConf()) {
                    result6 = false;
                } else {
                    result6 = true;
                }
            }
            if (!result6 || hasHashGnssConf() != other.hasHashGnssConf()) {
                result7 = false;
            } else {
                result7 = true;
            }
            if (hasHashGnssConf()) {
                if (!result7 || getHashGnssConf() != other.getHashGnssConf()) {
                    result7 = false;
                } else {
                    result7 = true;
                }
            }
            if (!result7 || hasHashHrAlarmConf() != other.hasHashHrAlarmConf()) {
                result8 = false;
            } else {
                result8 = true;
            }
            if (hasHashHrAlarmConf()) {
                if (!result8 || getHashHrAlarmConf() != other.getHashHrAlarmConf()) {
                    result8 = false;
                } else {
                    result8 = true;
                }
            }
            if (!result8 || hasHashUserConf() != other.hasHashUserConf()) {
                result9 = false;
            } else {
                result9 = true;
            }
            if (hasHashUserConf()) {
                if (!result9 || getHashUserConf() != other.getHashUserConf()) {
                    result9 = false;
                } else {
                    result9 = true;
                }
            }
            if (!result9 || hasHashGoalConf() != other.hasHashGoalConf()) {
                result10 = false;
            } else {
                result10 = true;
            }
            if (hasHashGoalConf()) {
                if (!result10 || getHashGoalConf() != other.getHashGoalConf()) {
                    result10 = false;
                } else {
                    result10 = true;
                }
            }
            if (!result10 || hasSupportBpcaliConf() != other.hasSupportBpcaliConf()) {
                result11 = false;
            } else {
                result11 = true;
            }
            if (hasSupportBpcaliConf()) {
                if (!result11 || getSupportBpcaliConf() != other.getSupportBpcaliConf()) {
                    result11 = false;
                } else {
                    result11 = true;
                }
            }
            if (!result11 || hasHashBpcaliConf() != other.hasHashBpcaliConf()) {
                result12 = false;
            } else {
                result12 = true;
            }
            if (hasHashBpcaliConf()) {
                if (!result12 || getHashBpcaliConf() != other.getHashBpcaliConf()) {
                    result12 = false;
                } else {
                    result12 = true;
                }
            }
            if (!result12 || hasSupportAfConf() != other.hasSupportAfConf()) {
                result13 = false;
            } else {
                result13 = true;
            }
            if (hasSupportAfConf()) {
                if (!result13 || getSupportAfConf() != other.getSupportAfConf()) {
                    result13 = false;
                } else {
                    result13 = true;
                }
            }
            if (!result13 || hasHashAfConf() != other.hasHashAfConf()) {
                result14 = false;
            } else {
                result14 = true;
            }
            if (hasHashAfConf()) {
                if (!result14 || getHashAfConf() != other.getHashAfConf()) {
                    result14 = false;
                } else {
                    result14 = true;
                }
            }
            if (!result14 || !this.unknownFields.equals(other.unknownFields)) {
                result15 = false;
            } else {
                result15 = true;
            }
            return result15;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasSupportPeerType()) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashBoolean(getSupportPeerType());
            }
            if (hasSupportPeerStatus()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getSupportPeerStatus());
            }
            if (hasSupportDateTime()) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashBoolean(getSupportDateTime());
            }
            if (hasSupportGnssConf()) {
                hash = (((hash * 37) + 4) * 53) + Internal.hashBoolean(getSupportGnssConf());
            }
            if (hasSupportHrAlarmConf()) {
                hash = (((hash * 37) + 5) * 53) + Internal.hashBoolean(getSupportHrAlarmConf());
            }
            if (hasSupportUserConf()) {
                hash = (((hash * 37) + 6) * 53) + Internal.hashBoolean(getSupportUserConf());
            }
            if (hasSupportGoalConf()) {
                hash = (((hash * 37) + 7) * 53) + Internal.hashBoolean(getSupportGoalConf());
            }
            if (hasHashGnssConf()) {
                hash = (((hash * 37) + 9) * 53) + getHashGnssConf();
            }
            if (hasHashHrAlarmConf()) {
                hash = (((hash * 37) + 10) * 53) + getHashHrAlarmConf();
            }
            if (hasHashUserConf()) {
                hash = (((hash * 37) + 11) * 53) + getHashUserConf();
            }
            if (hasHashGoalConf()) {
                hash = (((hash * 37) + 12) * 53) + getHashGoalConf();
            }
            if (hasSupportBpcaliConf()) {
                hash = (((hash * 37) + 13) * 53) + Internal.hashBoolean(getSupportBpcaliConf());
            }
            if (hasHashBpcaliConf()) {
                hash = (((hash * 37) + 14) * 53) + Internal.hashBoolean(getHashBpcaliConf());
            }
            if (hasSupportAfConf()) {
                hash = (((hash * 37) + 15) * 53) + Internal.hashBoolean(getSupportAfConf());
            }
            if (hasHashAfConf()) {
                hash = (((hash * 37) + 16) * 53) + getHashAfConf();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static PeerInfoSubsriber parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PeerInfoSubsriber) PARSER.parseFrom(data);
        }

        public static PeerInfoSubsriber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PeerInfoSubsriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static PeerInfoSubsriber parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PeerInfoSubsriber) PARSER.parseFrom(data);
        }

        public static PeerInfoSubsriber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PeerInfoSubsriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static PeerInfoSubsriber parseFrom(InputStream input) throws IOException {
            return (PeerInfoSubsriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static PeerInfoSubsriber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerInfoSubsriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static PeerInfoSubsriber parseDelimitedFrom(InputStream input) throws IOException {
            return (PeerInfoSubsriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static PeerInfoSubsriber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerInfoSubsriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static PeerInfoSubsriber parseFrom(CodedInputStream input) throws IOException {
            return (PeerInfoSubsriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static PeerInfoSubsriber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerInfoSubsriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(PeerInfoSubsriber prototype) {
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

        public static PeerInfoSubsriber getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PeerInfoSubsriber> parser() {
            return PARSER;
        }

        public Parser<PeerInfoSubsriber> getParserForType() {
            return PARSER;
        }

        public PeerInfoSubsriber getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class UserConf extends GeneratedMessageV3 implements UserConfOrBuilder {
        public static final int AGE_FIELD_NUMBER = 5;
        public static final int CALIB_RUN_FIELD_NUMBER = 7;
        public static final int CALIB_WALK_FIELD_NUMBER = 6;
        private static final UserConf DEFAULT_INSTANCE = new UserConf();
        public static final int GENDER_FIELD_NUMBER = 4;
        public static final int HASH_FIELD_NUMBER = 1;
        public static final int HEIGHT_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<UserConf> PARSER = new AbstractParser<UserConf>() {
            public UserConf parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new UserConf(input, extensionRegistry);
            }
        };
        public static final int WEIGHT_FIELD_NUMBER = 3;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int age_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int calibRun_;
        /* access modifiers changed from: private */
        public int calibWalk_;
        /* access modifiers changed from: private */
        public boolean gender_;
        /* access modifiers changed from: private */
        public int hash_;
        /* access modifiers changed from: private */
        public int height_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int weight_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements UserConfOrBuilder {
            private int age_;
            private int bitField0_;
            private int calibRun_;
            private int calibWalk_;
            private boolean gender_;
            private int hash_;
            private int height_;
            private int weight_;

            public static final Descriptor getDescriptor() {
                return PeerInfo.internal_static_UserConf_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return PeerInfo.internal_static_UserConf_fieldAccessorTable.ensureFieldAccessorsInitialized(UserConf.class, Builder.class);
            }

            private Builder() {
                this.height_ = 170;
                this.weight_ = 60;
                this.age_ = 20;
                this.calibWalk_ = 100;
                this.calibRun_ = 100;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.height_ = 170;
                this.weight_ = 60;
                this.age_ = 20;
                this.calibWalk_ = 100;
                this.calibRun_ = 100;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (UserConf.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.height_ = 170;
                this.bitField0_ &= -3;
                this.weight_ = 60;
                this.bitField0_ &= -5;
                this.gender_ = false;
                this.bitField0_ &= -9;
                this.age_ = 20;
                this.bitField0_ &= -17;
                this.calibWalk_ = 100;
                this.bitField0_ &= -33;
                this.calibRun_ = 100;
                this.bitField0_ &= -65;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return PeerInfo.internal_static_UserConf_descriptor;
            }

            public UserConf getDefaultInstanceForType() {
                return UserConf.getDefaultInstance();
            }

            public UserConf build() {
                UserConf result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public UserConf buildPartial() {
                UserConf result = new UserConf((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.height_ = this.height_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.weight_ = this.weight_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.gender_ = this.gender_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.age_ = this.age_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.calibWalk_ = this.calibWalk_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.calibRun_ = this.calibRun_;
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
                if (other instanceof UserConf) {
                    return mergeFrom((UserConf) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(UserConf other) {
                if (other != UserConf.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (other.hasHeight()) {
                        setHeight(other.getHeight());
                    }
                    if (other.hasWeight()) {
                        setWeight(other.getWeight());
                    }
                    if (other.hasGender()) {
                        setGender(other.getGender());
                    }
                    if (other.hasAge()) {
                        setAge(other.getAge());
                    }
                    if (other.hasCalibWalk()) {
                        setCalibWalk(other.getCalibWalk());
                    }
                    if (other.hasCalibRun()) {
                        setCalibRun(other.getCalibRun());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasHash() && hasHeight() && hasWeight() && hasGender() && hasAge() && hasCalibWalk() && hasCalibRun()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                UserConf parsedMessage = null;
                try {
                    UserConf parsedMessage2 = (UserConf) UserConf.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    UserConf parsedMessage3 = (UserConf) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasHash() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getHash() {
                return this.hash_;
            }

            public Builder setHash(int value) {
                this.bitField0_ |= 1;
                this.hash_ = value;
                onChanged();
                return this;
            }

            public Builder clearHash() {
                this.bitField0_ &= -2;
                this.hash_ = 0;
                onChanged();
                return this;
            }

            public boolean hasHeight() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getHeight() {
                return this.height_;
            }

            public Builder setHeight(int value) {
                this.bitField0_ |= 2;
                this.height_ = value;
                onChanged();
                return this;
            }

            public Builder clearHeight() {
                this.bitField0_ &= -3;
                this.height_ = 170;
                onChanged();
                return this;
            }

            public boolean hasWeight() {
                return (this.bitField0_ & 4) == 4;
            }

            public int getWeight() {
                return this.weight_;
            }

            public Builder setWeight(int value) {
                this.bitField0_ |= 4;
                this.weight_ = value;
                onChanged();
                return this;
            }

            public Builder clearWeight() {
                this.bitField0_ &= -5;
                this.weight_ = 60;
                onChanged();
                return this;
            }

            public boolean hasGender() {
                return (this.bitField0_ & 8) == 8;
            }

            public boolean getGender() {
                return this.gender_;
            }

            public Builder setGender(boolean value) {
                this.bitField0_ |= 8;
                this.gender_ = value;
                onChanged();
                return this;
            }

            public Builder clearGender() {
                this.bitField0_ &= -9;
                this.gender_ = false;
                onChanged();
                return this;
            }

            public boolean hasAge() {
                return (this.bitField0_ & 16) == 16;
            }

            public int getAge() {
                return this.age_;
            }

            public Builder setAge(int value) {
                this.bitField0_ |= 16;
                this.age_ = value;
                onChanged();
                return this;
            }

            public Builder clearAge() {
                this.bitField0_ &= -17;
                this.age_ = 20;
                onChanged();
                return this;
            }

            public boolean hasCalibWalk() {
                return (this.bitField0_ & 32) == 32;
            }

            public int getCalibWalk() {
                return this.calibWalk_;
            }

            public Builder setCalibWalk(int value) {
                this.bitField0_ |= 32;
                this.calibWalk_ = value;
                onChanged();
                return this;
            }

            public Builder clearCalibWalk() {
                this.bitField0_ &= -33;
                this.calibWalk_ = 100;
                onChanged();
                return this;
            }

            public boolean hasCalibRun() {
                return (this.bitField0_ & 64) == 64;
            }

            public int getCalibRun() {
                return this.calibRun_;
            }

            public Builder setCalibRun(int value) {
                this.bitField0_ |= 64;
                this.calibRun_ = value;
                onChanged();
                return this;
            }

            public Builder clearCalibRun() {
                this.bitField0_ &= -65;
                this.calibRun_ = 100;
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

        private UserConf(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private UserConf() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.height_ = 170;
            this.weight_ = 60;
            this.gender_ = false;
            this.age_ = 20;
            this.calibWalk_ = 100;
            this.calibRun_ = 100;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private UserConf(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.hash_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.height_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.weight_ = input.readFixed32();
                            break;
                        case 32:
                            this.bitField0_ |= 8;
                            this.gender_ = input.readBool();
                            break;
                        case 45:
                            this.bitField0_ |= 16;
                            this.age_ = input.readFixed32();
                            break;
                        case 53:
                            this.bitField0_ |= 32;
                            this.calibWalk_ = input.readFixed32();
                            break;
                        case 61:
                            this.bitField0_ |= 64;
                            this.calibRun_ = input.readFixed32();
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
            return PeerInfo.internal_static_UserConf_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return PeerInfo.internal_static_UserConf_fieldAccessorTable.ensureFieldAccessorsInitialized(UserConf.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasHeight() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getHeight() {
            return this.height_;
        }

        public boolean hasWeight() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getWeight() {
            return this.weight_;
        }

        public boolean hasGender() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean getGender() {
            return this.gender_;
        }

        public boolean hasAge() {
            return (this.bitField0_ & 16) == 16;
        }

        public int getAge() {
            return this.age_;
        }

        public boolean hasCalibWalk() {
            return (this.bitField0_ & 32) == 32;
        }

        public int getCalibWalk() {
            return this.calibWalk_;
        }

        public boolean hasCalibRun() {
            return (this.bitField0_ & 64) == 64;
        }

        public int getCalibRun() {
            return this.calibRun_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasHash()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasHeight()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasWeight()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasGender()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasAge()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasCalibWalk()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasCalibRun()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.height_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.weight_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBool(4, this.gender_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeFixed32(5, this.age_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeFixed32(6, this.calibWalk_);
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeFixed32(7, this.calibRun_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.height_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.weight_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeBoolSize(4, this.gender_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeFixed32Size(5, this.age_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeFixed32Size(6, this.calibWalk_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size2 += CodedOutputStream.computeFixed32Size(7, this.calibRun_);
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
            if (!(obj instanceof UserConf)) {
                return super.equals(obj);
            }
            UserConf other = (UserConf) obj;
            boolean result8 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result8 = result8 && getHash() == other.getHash();
            }
            if (!result8 || hasHeight() != other.hasHeight()) {
                result = false;
            } else {
                result = true;
            }
            if (hasHeight()) {
                if (!result || getHeight() != other.getHeight()) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasWeight() != other.hasWeight()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasWeight()) {
                if (!result2 || getWeight() != other.getWeight()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasGender() != other.hasGender()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasGender()) {
                if (!result3 || getGender() != other.getGender()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasAge() != other.hasAge()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasAge()) {
                if (!result4 || getAge() != other.getAge()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasCalibWalk() != other.hasCalibWalk()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasCalibWalk()) {
                if (!result5 || getCalibWalk() != other.getCalibWalk()) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || hasCalibRun() != other.hasCalibRun()) {
                result6 = false;
            } else {
                result6 = true;
            }
            if (hasCalibRun()) {
                if (!result6 || getCalibRun() != other.getCalibRun()) {
                    result6 = false;
                } else {
                    result6 = true;
                }
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
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            if (hasHeight()) {
                hash = (((hash * 37) + 2) * 53) + getHeight();
            }
            if (hasWeight()) {
                hash = (((hash * 37) + 3) * 53) + getWeight();
            }
            if (hasGender()) {
                hash = (((hash * 37) + 4) * 53) + Internal.hashBoolean(getGender());
            }
            if (hasAge()) {
                hash = (((hash * 37) + 5) * 53) + getAge();
            }
            if (hasCalibWalk()) {
                hash = (((hash * 37) + 6) * 53) + getCalibWalk();
            }
            if (hasCalibRun()) {
                hash = (((hash * 37) + 7) * 53) + getCalibRun();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static UserConf parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (UserConf) PARSER.parseFrom(data);
        }

        public static UserConf parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UserConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static UserConf parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (UserConf) PARSER.parseFrom(data);
        }

        public static UserConf parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UserConf) PARSER.parseFrom(data, extensionRegistry);
        }

        public static UserConf parseFrom(InputStream input) throws IOException {
            return (UserConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static UserConf parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UserConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static UserConf parseDelimitedFrom(InputStream input) throws IOException {
            return (UserConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static UserConf parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UserConf) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static UserConf parseFrom(CodedInputStream input) throws IOException {
            return (UserConf) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static UserConf parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UserConf) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(UserConf prototype) {
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

        public static UserConf getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<UserConf> parser() {
            return PARSER;
        }

        public Parser<UserConf> getParserForType() {
            return PARSER;
        }

        public UserConf getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface AfConfOrBuilder extends MessageOrBuilder {
        boolean getAutoRun();

        int getHash();

        int getInterval();

        boolean hasAutoRun();

        boolean hasHash();

        boolean hasInterval();
    }

    public interface BpCaliConfOrBuilder extends MessageOrBuilder {
        int getDifDbp();

        int getDifSbp();

        int getDstDbp();

        int getDstSbp();

        int getHash();

        int getSrcDbp();

        int getSrcSbp();

        boolean hasDifDbp();

        boolean hasDifSbp();

        boolean hasDstDbp();

        boolean hasDstSbp();

        boolean hasHash();

        boolean hasSrcDbp();

        boolean hasSrcSbp();
    }

    public interface GnssConfOrBuilder extends MessageOrBuilder {
        float getAltitude();

        int getHash();

        float getLatitude();

        float getLongitude();

        boolean hasAltitude();

        boolean hasHash();

        boolean hasLatitude();

        boolean hasLongitude();
    }

    public interface GoalConfOrBuilder extends MessageOrBuilder {
        int getCalorie();

        int getDistance();

        int getHash();

        int getStep();

        boolean hasCalorie();

        boolean hasDistance();

        boolean hasHash();

        boolean hasStep();
    }

    public interface HrAlarmConfOrBuilder extends MessageOrBuilder {
        boolean getEnable();

        int getHash();

        int getInterval();

        int getThsHigh();

        int getThsLow();

        int getTimeout();

        boolean hasEnable();

        boolean hasHash();

        boolean hasInterval();

        boolean hasThsHigh();

        boolean hasThsLow();

        boolean hasTimeout();
    }

    public interface PeerInfoNotificationOrBuilder extends MessageOrBuilder {
        AfConf getAfConf();

        AfConfOrBuilder getAfConfOrBuilder();

        BpCaliConf getBpcaliConf();

        BpCaliConfOrBuilder getBpcaliConfOrBuilder();

        DateTime getDateTime();

        DateTimeOrBuilder getDateTimeOrBuilder();

        GnssConf getGnssConf();

        GnssConfOrBuilder getGnssConfOrBuilder();

        GoalConf getGoalConf();

        GoalConfOrBuilder getGoalConfOrBuilder();

        HrAlarmConf getHrAlarmConf();

        HrAlarmConfOrBuilder getHrAlarmConfOrBuilder();

        PeerStatus getPeerStatus();

        PeerType getPeerType();

        UserConf getUserConf();

        UserConfOrBuilder getUserConfOrBuilder();

        boolean hasAfConf();

        boolean hasBpcaliConf();

        boolean hasDateTime();

        boolean hasGnssConf();

        boolean hasGoalConf();

        boolean hasHrAlarmConf();

        boolean hasPeerStatus();

        boolean hasPeerType();

        boolean hasUserConf();
    }

    public interface PeerInfoSubsriberOrBuilder extends MessageOrBuilder {
        int getHashAfConf();

        boolean getHashBpcaliConf();

        int getHashGnssConf();

        int getHashGoalConf();

        int getHashHrAlarmConf();

        int getHashUserConf();

        boolean getSupportAfConf();

        boolean getSupportBpcaliConf();

        boolean getSupportDateTime();

        boolean getSupportGnssConf();

        boolean getSupportGoalConf();

        boolean getSupportHrAlarmConf();

        boolean getSupportPeerStatus();

        boolean getSupportPeerType();

        boolean getSupportUserConf();

        boolean hasHashAfConf();

        boolean hasHashBpcaliConf();

        boolean hasHashGnssConf();

        boolean hasHashGoalConf();

        boolean hasHashHrAlarmConf();

        boolean hasHashUserConf();

        boolean hasSupportAfConf();

        boolean hasSupportBpcaliConf();

        boolean hasSupportDateTime();

        boolean hasSupportGnssConf();

        boolean hasSupportGoalConf();

        boolean hasSupportHrAlarmConf();

        boolean hasSupportPeerStatus();

        boolean hasSupportPeerType();

        boolean hasSupportUserConf();
    }

    public interface UserConfOrBuilder extends MessageOrBuilder {
        int getAge();

        int getCalibRun();

        int getCalibWalk();

        boolean getGender();

        int getHash();

        int getHeight();

        int getWeight();

        boolean hasAge();

        boolean hasCalibRun();

        boolean hasCalibWalk();

        boolean hasGender();

        boolean hasHash();

        boolean hasHeight();

        boolean hasWeight();
    }

    private PeerInfo() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u000fpeer_info.proto\u001a\u0013realtime_data.proto\" \u0003\n\u0011PeerInfoSubsriber\u0012\u0019\n\u0011support_peer_type\u0018\u0001 \u0002(\b\u0012\u001b\n\u0013support_peer_status\u0018\u0002 \u0002(\b\u0012\u0019\n\u0011support_date_time\u0018\u0003 \u0002(\b\u0012\u0019\n\u0011support_gnss_conf\u0018\u0004 \u0002(\b\u0012\u001d\n\u0015support_hr_alarm_conf\u0018\u0005 \u0002(\b\u0012\u0019\n\u0011support_user_conf\u0018\u0006 \u0002(\b\u0012\u0019\n\u0011support_goal_conf\u0018\u0007 \u0002(\b\u0012\u0016\n\u000ehash_gnss_conf\u0018\t \u0001(\u0007\u0012\u001a\n\u0012hash_hr_alarm_conf\u0018\n \u0001(\u0007\u0012\u0016\n\u000ehash_user_conf\u0018\u000b \u0001(\u0007\u0012\u0016\n\u000ehash_goal_conf\u0018\f \u0001(\u0007\u0012\u001b\n\u0013support_bpcali_conf\u0018\r \u0001(\b\u0012\u0018\n\u0010hash_bpcali_", "conf\u0018\u000e \u0001(\b\u0012\u0017\n\u000fsupport_af_conf\u0018\u000f \u0001(\b\u0012\u0014\n\fhash_af_conf\u0018\u0010 \u0001(\u0007\"\u0001\n\bUserConf\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u0013\n\u0006height\u0018\u0002 \u0002(\u0007:\u0003170\u0012\u0012\n\u0006weight\u0018\u0003 \u0002(\u0007:\u000260\u0012\u000e\n\u0006gender\u0018\u0004 \u0002(\b\u0012\u000f\n\u0003age\u0018\u0005 \u0002(\u0007:\u000220\u0012\u0017\n\ncalib_walk\u0018\u0006 \u0002(\u0007:\u0003100\u0012\u0016\n\tcalib_run\u0018\u0007 \u0002(\u0007:\u0003100\"\\\n\bGoalConf\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u0017\n\bdistance\u0018\u0002 \u0002(\u0007:\u000510000\u0012\u0013\n\u0004step\u0018\u0003 \u0002(\u0007:\u000510000\u0012\u0014\n\u0007calorie\u0018\u0004 \u0002(\u0007:\u0003400\"\u0001\n\u000bHrAlarmConf\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u000e\n\u0006enable\u0018\u0002 \u0002(\b\u0012\u0015\n\bths_high\u0018\u0003 \u0002(\u0007:\u0003160\u0012\u0013\n\u0007ths_low\u0018\u0004 \u0002(\u0007:\u000250\u0012\u0013\n\u0007timeo", "ut\u0018\u0005 \u0002(\u0007:\u000230\u0012\u0013\n\binterval\u0018\u0006 \u0002(\u0007:\u00012\"O\n\bGnssConf\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u0010\n\baltitude\u0018\u0002 \u0002(\u0002\u0012\u0010\n\blatitude\u0018\u0003 \u0002(\u0002\u0012\u0011\n\tlongitude\u0018\u0004 \u0002(\u0002\"\u0001\n\nBpCaliConf\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u000f\n\u0007src_sbp\u0018\u0002 \u0002(\u0007\u0012\u000f\n\u0007src_dbp\u0018\u0003 \u0002(\u0007\u0012\u000f\n\u0007dst_sbp\u0018\u0004 \u0002(\u0007\u0012\u000f\n\u0007dst_dbp\u0018\u0005 \u0002(\u0007\u0012\u000f\n\u0007dif_sbp\u0018\u0006 \u0002(\u000f\u0012\u000f\n\u0007dif_dbp\u0018\u0007 \u0002(\u000f\"=\n\u0006AfConf\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012\u0010\n\bauto_run\u0018\u0002 \u0002(\b\u0012\u0013\n\binterval\u0018\u0003 \u0002(\u0007:\u00010\"¹\u0003\n\u0014PeerInfoNotification\u00121\n\tpeer_type\u0018\u0001 \u0001(\u000e2\u001e.PeerInfoNotification.PeerType\u00125\n\u000bpeer_status\u0018", "\u0002 \u0001(\u000e2 .PeerInfoNotification.PeerStatus\u0012\u001c\n\tdate_time\u0018\u0003 \u0001(\u000b2\t.DateTime\u0012\u001c\n\tgnss_conf\u0018\u0004 \u0001(\u000b2\t.GnssConf\u0012#\n\rhr_alarm_conf\u0018\u0005 \u0001(\u000b2\f.HrAlarmConf\u0012\u001c\n\tuser_conf\u0018\u0006 \u0001(\u000b2\t.UserConf\u0012\u001c\n\tgoal_conf\u0018\u0007 \u0001(\u000b2\t.GoalConf\u0012 \n\u000bbpcali_conf\u0018\b \u0001(\u000b2\u000b.BpCaliConf\u0012\u0018\n\u0007af_conf\u0018\t \u0001(\u000b2\u0007.AfConf\"(\n\bPeerType\u0012\u000f\n\u000bAPP_ANDROID\u0010\u0000\u0012\u000b\n\u0007APP_IOS\u0010\u0001\"4\n\nPeerStatus\u0012\u0012\n\u000eAPP_BACKGROUND\u0010\u0000\u0012\u0012\n\u000eAPP_FOREGROUND\u0010\u0001"}, fileDescriptorArr, new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                PeerInfo.descriptor = root;
                return null;
            }
        });
        RealtimeData.getDescriptor();
    }
}
