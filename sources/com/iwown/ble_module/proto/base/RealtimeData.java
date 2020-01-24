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
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;

public final class RealtimeData {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_DateTime_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_DateTime_fieldAccessorTable = new FieldAccessorTable(internal_static_DateTime_descriptor, new String[]{"DateTime", "TimeZone"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_RtBattery_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_RtBattery_fieldAccessorTable = new FieldAccessorTable(internal_static_RtBattery_descriptor, new String[]{"Level", "Charging"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_RtData_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(6));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_RtData_fieldAccessorTable = new FieldAccessorTable(internal_static_RtData_descriptor, new String[]{"Time", "Battery", "Health", "Key"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_RtGNSS_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_RtGNSS_fieldAccessorTable = new FieldAccessorTable(internal_static_RtGNSS_descriptor, new String[]{"Longitude", "Latitude", "Speed", "Altitude"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_RtHealth_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_RtHealth_fieldAccessorTable = new FieldAccessorTable(internal_static_RtHealth_descriptor, new String[]{"Steps", "Distance", "Calorie"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_RtNotification_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(8));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_RtNotification_fieldAccessorTable = new FieldAccessorTable(internal_static_RtNotification_descriptor, new String[]{"RtData", "RtState", "Data"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_RtState_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(7));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_RtState_fieldAccessorTable = new FieldAccessorTable(internal_static_RtState_descriptor, new String[]{"Time", "Battery", "Health", "Mode"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_RtSubscriber_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(5));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_RtSubscriber_fieldAccessorTable = new FieldAccessorTable(internal_static_RtSubscriber_descriptor, new String[]{"Time", "Battery", "Health", "Mode"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_RtTime_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_RtTime_fieldAccessorTable = new FieldAccessorTable(internal_static_RtTime_descriptor, new String[]{"Seconds"});

    public static final class DateTime extends GeneratedMessageV3 implements DateTimeOrBuilder {
        public static final int DATE_TIME_FIELD_NUMBER = 1;
        private static final DateTime DEFAULT_INSTANCE = new DateTime();
        @Deprecated
        public static final Parser<DateTime> PARSER = new AbstractParser<DateTime>() {
            public DateTime parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DateTime(input, extensionRegistry);
            }
        };
        public static final int TIME_ZONE_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public RtTime dateTime_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int timeZone_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements DateTimeOrBuilder {
            private int bitField0_;
            private SingleFieldBuilderV3<RtTime, Builder, RtTimeOrBuilder> dateTimeBuilder_;
            private RtTime dateTime_;
            private int timeZone_;

            public static final Descriptor getDescriptor() {
                return RealtimeData.internal_static_DateTime_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return RealtimeData.internal_static_DateTime_fieldAccessorTable.ensureFieldAccessorsInitialized(DateTime.class, Builder.class);
            }

            private Builder() {
                this.dateTime_ = null;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.dateTime_ = null;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (DateTime.alwaysUseFieldBuilders) {
                    getDateTimeFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = null;
                } else {
                    this.dateTimeBuilder_.clear();
                }
                this.bitField0_ &= -2;
                this.timeZone_ = 0;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return RealtimeData.internal_static_DateTime_descriptor;
            }

            public DateTime getDefaultInstanceForType() {
                return DateTime.getDefaultInstance();
            }

            public DateTime build() {
                DateTime result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public DateTime buildPartial() {
                DateTime result = new DateTime((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                if (this.dateTimeBuilder_ == null) {
                    result.dateTime_ = this.dateTime_;
                } else {
                    result.dateTime_ = (RtTime) this.dateTimeBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.timeZone_ = this.timeZone_;
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
                if (other instanceof DateTime) {
                    return mergeFrom((DateTime) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DateTime other) {
                if (other != DateTime.getDefaultInstance()) {
                    if (other.hasDateTime()) {
                        mergeDateTime(other.getDateTime());
                    }
                    if (other.hasTimeZone()) {
                        setTimeZone(other.getTimeZone());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasDateTime() && hasTimeZone() && getDateTime().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                DateTime parsedMessage = null;
                try {
                    DateTime parsedMessage2 = (DateTime) DateTime.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    DateTime parsedMessage3 = (DateTime) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasDateTime() {
                return (this.bitField0_ & 1) == 1;
            }

            public RtTime getDateTime() {
                if (this.dateTimeBuilder_ == null) {
                    return this.dateTime_ == null ? RtTime.getDefaultInstance() : this.dateTime_;
                }
                return (RtTime) this.dateTimeBuilder_.getMessage();
            }

            public Builder setDateTime(RtTime value) {
                if (this.dateTimeBuilder_ != null) {
                    this.dateTimeBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.dateTime_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setDateTime(Builder builderForValue) {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = builderForValue.build();
                    onChanged();
                } else {
                    this.dateTimeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergeDateTime(RtTime value) {
                if (this.dateTimeBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 1 || this.dateTime_ == null || this.dateTime_ == RtTime.getDefaultInstance()) {
                        this.dateTime_ = value;
                    } else {
                        this.dateTime_ = RtTime.newBuilder(this.dateTime_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.dateTimeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearDateTime() {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTime_ = null;
                    onChanged();
                } else {
                    this.dateTimeBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public Builder getDateTimeBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (Builder) getDateTimeFieldBuilder().getBuilder();
            }

            public RtTimeOrBuilder getDateTimeOrBuilder() {
                if (this.dateTimeBuilder_ != null) {
                    return (RtTimeOrBuilder) this.dateTimeBuilder_.getMessageOrBuilder();
                }
                return this.dateTime_ == null ? RtTime.getDefaultInstance() : this.dateTime_;
            }

            private SingleFieldBuilderV3<RtTime, Builder, RtTimeOrBuilder> getDateTimeFieldBuilder() {
                if (this.dateTimeBuilder_ == null) {
                    this.dateTimeBuilder_ = new SingleFieldBuilderV3<>(getDateTime(), getParentForChildren(), isClean());
                    this.dateTime_ = null;
                }
                return this.dateTimeBuilder_;
            }

            public boolean hasTimeZone() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getTimeZone() {
                return this.timeZone_;
            }

            public Builder setTimeZone(int value) {
                this.bitField0_ |= 2;
                this.timeZone_ = value;
                onChanged();
                return this;
            }

            public Builder clearTimeZone() {
                this.bitField0_ &= -3;
                this.timeZone_ = 0;
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

        private DateTime(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private DateTime() {
            this.memoizedIsInitialized = -1;
            this.timeZone_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DateTime(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                subBuilder = this.dateTime_.toBuilder();
                            }
                            this.dateTime_ = (RtTime) input.readMessage(RtTime.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.dateTime_);
                                this.dateTime_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.timeZone_ = input.readSFixed32();
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
            return RealtimeData.internal_static_DateTime_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return RealtimeData.internal_static_DateTime_fieldAccessorTable.ensureFieldAccessorsInitialized(DateTime.class, Builder.class);
        }

        public boolean hasDateTime() {
            return (this.bitField0_ & 1) == 1;
        }

        public RtTime getDateTime() {
            return this.dateTime_ == null ? RtTime.getDefaultInstance() : this.dateTime_;
        }

        public RtTimeOrBuilder getDateTimeOrBuilder() {
            return this.dateTime_ == null ? RtTime.getDefaultInstance() : this.dateTime_;
        }

        public boolean hasTimeZone() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getTimeZone() {
            return this.timeZone_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasDateTime()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasTimeZone()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getDateTime().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getDateTime());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeSFixed32(2, this.timeZone_);
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
                size2 = 0 + CodedOutputStream.computeMessageSize(1, getDateTime());
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeSFixed32Size(2, this.timeZone_);
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
            if (!(obj instanceof DateTime)) {
                return super.equals(obj);
            }
            DateTime other = (DateTime) obj;
            boolean result3 = 1 != 0 && hasDateTime() == other.hasDateTime();
            if (hasDateTime()) {
                result3 = result3 && getDateTime().equals(other.getDateTime());
            }
            if (!result3 || hasTimeZone() != other.hasTimeZone()) {
                result = false;
            } else {
                result = true;
            }
            if (hasTimeZone()) {
                if (!result || getTimeZone() != other.getTimeZone()) {
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
            if (hasDateTime()) {
                hash = (((hash * 37) + 1) * 53) + getDateTime().hashCode();
            }
            if (hasTimeZone()) {
                hash = (((hash * 37) + 2) * 53) + getTimeZone();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static DateTime parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DateTime) PARSER.parseFrom(data);
        }

        public static DateTime parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DateTime) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DateTime parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DateTime) PARSER.parseFrom(data);
        }

        public static DateTime parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DateTime) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DateTime parseFrom(InputStream input) throws IOException {
            return (DateTime) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DateTime parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DateTime) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DateTime parseDelimitedFrom(InputStream input) throws IOException {
            return (DateTime) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DateTime parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DateTime) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DateTime parseFrom(CodedInputStream input) throws IOException {
            return (DateTime) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DateTime parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DateTime) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DateTime prototype) {
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

        public static DateTime getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DateTime> parser() {
            return PARSER;
        }

        public Parser<DateTime> getParserForType() {
            return PARSER;
        }

        public DateTime getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class RtBattery extends GeneratedMessageV3 implements RtBatteryOrBuilder {
        public static final int CHARGING_FIELD_NUMBER = 2;
        private static final RtBattery DEFAULT_INSTANCE = new RtBattery();
        public static final int LEVEL_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<RtBattery> PARSER = new AbstractParser<RtBattery>() {
            public RtBattery parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new RtBattery(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public boolean charging_;
        /* access modifiers changed from: private */
        public int level_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements RtBatteryOrBuilder {
            private int bitField0_;
            private boolean charging_;
            private int level_;

            public static final Descriptor getDescriptor() {
                return RealtimeData.internal_static_RtBattery_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return RealtimeData.internal_static_RtBattery_fieldAccessorTable.ensureFieldAccessorsInitialized(RtBattery.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (RtBattery.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.level_ = 0;
                this.bitField0_ &= -2;
                this.charging_ = false;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return RealtimeData.internal_static_RtBattery_descriptor;
            }

            public RtBattery getDefaultInstanceForType() {
                return RtBattery.getDefaultInstance();
            }

            public RtBattery build() {
                RtBattery result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public RtBattery buildPartial() {
                RtBattery result = new RtBattery((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.level_ = this.level_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.charging_ = this.charging_;
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
                if (other instanceof RtBattery) {
                    return mergeFrom((RtBattery) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RtBattery other) {
                if (other != RtBattery.getDefaultInstance()) {
                    if (other.hasLevel()) {
                        setLevel(other.getLevel());
                    }
                    if (other.hasCharging()) {
                        setCharging(other.getCharging());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasLevel() && hasCharging()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                RtBattery parsedMessage = null;
                try {
                    RtBattery parsedMessage2 = (RtBattery) RtBattery.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    RtBattery parsedMessage3 = (RtBattery) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasLevel() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getLevel() {
                return this.level_;
            }

            public Builder setLevel(int value) {
                this.bitField0_ |= 1;
                this.level_ = value;
                onChanged();
                return this;
            }

            public Builder clearLevel() {
                this.bitField0_ &= -2;
                this.level_ = 0;
                onChanged();
                return this;
            }

            public boolean hasCharging() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getCharging() {
                return this.charging_;
            }

            public Builder setCharging(boolean value) {
                this.bitField0_ |= 2;
                this.charging_ = value;
                onChanged();
                return this;
            }

            public Builder clearCharging() {
                this.bitField0_ &= -3;
                this.charging_ = false;
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

        private RtBattery(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private RtBattery() {
            this.memoizedIsInitialized = -1;
            this.level_ = 0;
            this.charging_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RtBattery(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.level_ = input.readFixed32();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.charging_ = input.readBool();
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
            return RealtimeData.internal_static_RtBattery_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return RealtimeData.internal_static_RtBattery_fieldAccessorTable.ensureFieldAccessorsInitialized(RtBattery.class, Builder.class);
        }

        public boolean hasLevel() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getLevel() {
            return this.level_;
        }

        public boolean hasCharging() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getCharging() {
            return this.charging_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasLevel()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasCharging()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.level_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.charging_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.level_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeBoolSize(2, this.charging_);
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
            if (!(obj instanceof RtBattery)) {
                return super.equals(obj);
            }
            RtBattery other = (RtBattery) obj;
            boolean result3 = 1 != 0 && hasLevel() == other.hasLevel();
            if (hasLevel()) {
                result3 = result3 && getLevel() == other.getLevel();
            }
            if (!result3 || hasCharging() != other.hasCharging()) {
                result = false;
            } else {
                result = true;
            }
            if (hasCharging()) {
                if (!result || getCharging() != other.getCharging()) {
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
            if (hasLevel()) {
                hash = (((hash * 37) + 1) * 53) + getLevel();
            }
            if (hasCharging()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashBoolean(getCharging());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static RtBattery parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RtBattery) PARSER.parseFrom(data);
        }

        public static RtBattery parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtBattery) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtBattery parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RtBattery) PARSER.parseFrom(data);
        }

        public static RtBattery parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtBattery) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtBattery parseFrom(InputStream input) throws IOException {
            return (RtBattery) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtBattery parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtBattery) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtBattery parseDelimitedFrom(InputStream input) throws IOException {
            return (RtBattery) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RtBattery parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtBattery) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtBattery parseFrom(CodedInputStream input) throws IOException {
            return (RtBattery) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtBattery parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtBattery) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RtBattery prototype) {
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

        public static RtBattery getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RtBattery> parser() {
            return PARSER;
        }

        public Parser<RtBattery> getParserForType() {
            return PARSER;
        }

        public RtBattery getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class RtData extends GeneratedMessageV3 implements RtDataOrBuilder {
        public static final int BATTERY_FIELD_NUMBER = 2;
        private static final RtData DEFAULT_INSTANCE = new RtData();
        public static final int HEALTH_FIELD_NUMBER = 3;
        public static final int KEY_FIELD_NUMBER = 4;
        @Deprecated
        public static final Parser<RtData> PARSER = new AbstractParser<RtData>() {
            public RtData parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new RtData(input, extensionRegistry);
            }
        };
        public static final int TIME_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public RtBattery battery_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public RtHealth health_;
        /* access modifiers changed from: private */
        public int key_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public RtTime time_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements RtDataOrBuilder {
            private SingleFieldBuilderV3<RtBattery, Builder, RtBatteryOrBuilder> batteryBuilder_;
            private RtBattery battery_;
            private int bitField0_;
            private SingleFieldBuilderV3<RtHealth, Builder, RtHealthOrBuilder> healthBuilder_;
            private RtHealth health_;
            private int key_;
            private SingleFieldBuilderV3<RtTime, Builder, RtTimeOrBuilder> timeBuilder_;
            private RtTime time_;

            public static final Descriptor getDescriptor() {
                return RealtimeData.internal_static_RtData_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return RealtimeData.internal_static_RtData_fieldAccessorTable.ensureFieldAccessorsInitialized(RtData.class, Builder.class);
            }

            private Builder() {
                this.time_ = null;
                this.battery_ = null;
                this.health_ = null;
                this.key_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.time_ = null;
                this.battery_ = null;
                this.health_ = null;
                this.key_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (RtData.alwaysUseFieldBuilders) {
                    getTimeFieldBuilder();
                    getBatteryFieldBuilder();
                    getHealthFieldBuilder();
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
                if (this.batteryBuilder_ == null) {
                    this.battery_ = null;
                } else {
                    this.batteryBuilder_.clear();
                }
                this.bitField0_ &= -3;
                if (this.healthBuilder_ == null) {
                    this.health_ = null;
                } else {
                    this.healthBuilder_.clear();
                }
                this.bitField0_ &= -5;
                this.key_ = 0;
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return RealtimeData.internal_static_RtData_descriptor;
            }

            public RtData getDefaultInstanceForType() {
                return RtData.getDefaultInstance();
            }

            public RtData build() {
                RtData result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public RtData buildPartial() {
                RtData result = new RtData((com.google.protobuf.GeneratedMessageV3.Builder) this);
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
                if (this.batteryBuilder_ == null) {
                    result.battery_ = this.battery_;
                } else {
                    result.battery_ = (RtBattery) this.batteryBuilder_.build();
                }
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                if (this.healthBuilder_ == null) {
                    result.health_ = this.health_;
                } else {
                    result.health_ = (RtHealth) this.healthBuilder_.build();
                }
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.key_ = this.key_;
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
                if (other instanceof RtData) {
                    return mergeFrom((RtData) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RtData other) {
                if (other != RtData.getDefaultInstance()) {
                    if (other.hasTime()) {
                        mergeTime(other.getTime());
                    }
                    if (other.hasBattery()) {
                        mergeBattery(other.getBattery());
                    }
                    if (other.hasHealth()) {
                        mergeHealth(other.getHealth());
                    }
                    if (other.hasKey()) {
                        setKey(other.getKey());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasTime() && !getTime().isInitialized()) {
                    return false;
                }
                if (hasBattery() && !getBattery().isInitialized()) {
                    return false;
                }
                if (!hasHealth() || getHealth().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                RtData parsedMessage = null;
                try {
                    RtData parsedMessage2 = (RtData) RtData.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    RtData parsedMessage3 = (RtData) e.getUnfinishedMessage();
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

            public Builder setTime(Builder builderForValue) {
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

            public Builder getTimeBuilder() {
                this.bitField0_ |= 1;
                onChanged();
                return (Builder) getTimeFieldBuilder().getBuilder();
            }

            public RtTimeOrBuilder getTimeOrBuilder() {
                if (this.timeBuilder_ != null) {
                    return (RtTimeOrBuilder) this.timeBuilder_.getMessageOrBuilder();
                }
                return this.time_ == null ? RtTime.getDefaultInstance() : this.time_;
            }

            private SingleFieldBuilderV3<RtTime, Builder, RtTimeOrBuilder> getTimeFieldBuilder() {
                if (this.timeBuilder_ == null) {
                    this.timeBuilder_ = new SingleFieldBuilderV3<>(getTime(), getParentForChildren(), isClean());
                    this.time_ = null;
                }
                return this.timeBuilder_;
            }

            public boolean hasBattery() {
                return (this.bitField0_ & 2) == 2;
            }

            public RtBattery getBattery() {
                if (this.batteryBuilder_ == null) {
                    return this.battery_ == null ? RtBattery.getDefaultInstance() : this.battery_;
                }
                return (RtBattery) this.batteryBuilder_.getMessage();
            }

            public Builder setBattery(RtBattery value) {
                if (this.batteryBuilder_ != null) {
                    this.batteryBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.battery_ = value;
                    onChanged();
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setBattery(Builder builderForValue) {
                if (this.batteryBuilder_ == null) {
                    this.battery_ = builderForValue.build();
                    onChanged();
                } else {
                    this.batteryBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeBattery(RtBattery value) {
                if (this.batteryBuilder_ == null) {
                    if ((this.bitField0_ & 2) != 2 || this.battery_ == null || this.battery_ == RtBattery.getDefaultInstance()) {
                        this.battery_ = value;
                    } else {
                        this.battery_ = RtBattery.newBuilder(this.battery_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.batteryBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearBattery() {
                if (this.batteryBuilder_ == null) {
                    this.battery_ = null;
                    onChanged();
                } else {
                    this.batteryBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public Builder getBatteryBuilder() {
                this.bitField0_ |= 2;
                onChanged();
                return (Builder) getBatteryFieldBuilder().getBuilder();
            }

            public RtBatteryOrBuilder getBatteryOrBuilder() {
                if (this.batteryBuilder_ != null) {
                    return (RtBatteryOrBuilder) this.batteryBuilder_.getMessageOrBuilder();
                }
                return this.battery_ == null ? RtBattery.getDefaultInstance() : this.battery_;
            }

            private SingleFieldBuilderV3<RtBattery, Builder, RtBatteryOrBuilder> getBatteryFieldBuilder() {
                if (this.batteryBuilder_ == null) {
                    this.batteryBuilder_ = new SingleFieldBuilderV3<>(getBattery(), getParentForChildren(), isClean());
                    this.battery_ = null;
                }
                return this.batteryBuilder_;
            }

            public boolean hasHealth() {
                return (this.bitField0_ & 4) == 4;
            }

            public RtHealth getHealth() {
                if (this.healthBuilder_ == null) {
                    return this.health_ == null ? RtHealth.getDefaultInstance() : this.health_;
                }
                return (RtHealth) this.healthBuilder_.getMessage();
            }

            public Builder setHealth(RtHealth value) {
                if (this.healthBuilder_ != null) {
                    this.healthBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.health_ = value;
                    onChanged();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setHealth(Builder builderForValue) {
                if (this.healthBuilder_ == null) {
                    this.health_ = builderForValue.build();
                    onChanged();
                } else {
                    this.healthBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeHealth(RtHealth value) {
                if (this.healthBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 4 || this.health_ == null || this.health_ == RtHealth.getDefaultInstance()) {
                        this.health_ = value;
                    } else {
                        this.health_ = RtHealth.newBuilder(this.health_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.healthBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearHealth() {
                if (this.healthBuilder_ == null) {
                    this.health_ = null;
                    onChanged();
                } else {
                    this.healthBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder getHealthBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (Builder) getHealthFieldBuilder().getBuilder();
            }

            public RtHealthOrBuilder getHealthOrBuilder() {
                if (this.healthBuilder_ != null) {
                    return (RtHealthOrBuilder) this.healthBuilder_.getMessageOrBuilder();
                }
                return this.health_ == null ? RtHealth.getDefaultInstance() : this.health_;
            }

            private SingleFieldBuilderV3<RtHealth, Builder, RtHealthOrBuilder> getHealthFieldBuilder() {
                if (this.healthBuilder_ == null) {
                    this.healthBuilder_ = new SingleFieldBuilderV3<>(getHealth(), getParentForChildren(), isClean());
                    this.health_ = null;
                }
                return this.healthBuilder_;
            }

            public boolean hasKey() {
                return (this.bitField0_ & 8) == 8;
            }

            public RtKeyEvent getKey() {
                RtKeyEvent result = RtKeyEvent.valueOf(this.key_);
                return result == null ? RtKeyEvent.RT_KEY_CAMERA : result;
            }

            public Builder setKey(RtKeyEvent value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.key_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearKey() {
                this.bitField0_ &= -9;
                this.key_ = 0;
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

        private RtData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private RtData() {
            this.memoizedIsInitialized = -1;
            this.key_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RtData(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                subBuilder = this.time_.toBuilder();
                            }
                            this.time_ = (RtTime) input.readMessage(RtTime.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.time_);
                                this.time_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            break;
                        case 18:
                            Builder subBuilder2 = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder2 = this.battery_.toBuilder();
                            }
                            this.battery_ = (RtBattery) input.readMessage(RtBattery.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.battery_);
                                this.battery_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            break;
                        case 26:
                            Builder subBuilder3 = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder3 = this.health_.toBuilder();
                            }
                            this.health_ = (RtHealth) input.readMessage(RtHealth.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom(this.health_);
                                this.health_ = subBuilder3.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            break;
                        case 32:
                            int rawValue = input.readEnum();
                            if (RtKeyEvent.valueOf(rawValue) != null) {
                                this.bitField0_ |= 8;
                                this.key_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(4, rawValue);
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
            return RealtimeData.internal_static_RtData_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return RealtimeData.internal_static_RtData_fieldAccessorTable.ensureFieldAccessorsInitialized(RtData.class, Builder.class);
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

        public boolean hasBattery() {
            return (this.bitField0_ & 2) == 2;
        }

        public RtBattery getBattery() {
            return this.battery_ == null ? RtBattery.getDefaultInstance() : this.battery_;
        }

        public RtBatteryOrBuilder getBatteryOrBuilder() {
            return this.battery_ == null ? RtBattery.getDefaultInstance() : this.battery_;
        }

        public boolean hasHealth() {
            return (this.bitField0_ & 4) == 4;
        }

        public RtHealth getHealth() {
            return this.health_ == null ? RtHealth.getDefaultInstance() : this.health_;
        }

        public RtHealthOrBuilder getHealthOrBuilder() {
            return this.health_ == null ? RtHealth.getDefaultInstance() : this.health_;
        }

        public boolean hasKey() {
            return (this.bitField0_ & 8) == 8;
        }

        public RtKeyEvent getKey() {
            RtKeyEvent result = RtKeyEvent.valueOf(this.key_);
            return result == null ? RtKeyEvent.RT_KEY_CAMERA : result;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (hasTime() && !getTime().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasBattery() && !getBattery().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasHealth() || getHealth().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, getTime());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, getBattery());
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, getHealth());
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeEnum(4, this.key_);
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
                size2 += CodedOutputStream.computeMessageSize(2, getBattery());
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeMessageSize(3, getHealth());
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeEnumSize(4, this.key_);
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
            if (!(obj instanceof RtData)) {
                return super.equals(obj);
            }
            RtData other = (RtData) obj;
            boolean result5 = 1 != 0 && hasTime() == other.hasTime();
            if (hasTime()) {
                result5 = result5 && getTime().equals(other.getTime());
            }
            if (!result5 || hasBattery() != other.hasBattery()) {
                result = false;
            } else {
                result = true;
            }
            if (hasBattery()) {
                if (!result || !getBattery().equals(other.getBattery())) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasHealth() != other.hasHealth()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasHealth()) {
                if (!result2 || !getHealth().equals(other.getHealth())) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasKey() != other.hasKey()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasKey()) {
                if (!result3 || this.key_ != other.key_) {
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
            if (hasTime()) {
                hash = (((hash * 37) + 1) * 53) + getTime().hashCode();
            }
            if (hasBattery()) {
                hash = (((hash * 37) + 2) * 53) + getBattery().hashCode();
            }
            if (hasHealth()) {
                hash = (((hash * 37) + 3) * 53) + getHealth().hashCode();
            }
            if (hasKey()) {
                hash = (((hash * 37) + 4) * 53) + this.key_;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static RtData parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RtData) PARSER.parseFrom(data);
        }

        public static RtData parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtData parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RtData) PARSER.parseFrom(data);
        }

        public static RtData parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtData) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtData parseFrom(InputStream input) throws IOException {
            return (RtData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtData parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtData parseDelimitedFrom(InputStream input) throws IOException {
            return (RtData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RtData parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtData) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtData parseFrom(CodedInputStream input) throws IOException {
            return (RtData) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtData parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtData) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RtData prototype) {
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

        public static RtData getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RtData> parser() {
            return PARSER;
        }

        public Parser<RtData> getParserForType() {
            return PARSER;
        }

        public RtData getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class RtGNSS extends GeneratedMessageV3 implements RtGNSSOrBuilder {
        public static final int ALTITUDE_FIELD_NUMBER = 4;
        private static final RtGNSS DEFAULT_INSTANCE = new RtGNSS();
        public static final int LATITUDE_FIELD_NUMBER = 2;
        public static final int LONGITUDE_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<RtGNSS> PARSER = new AbstractParser<RtGNSS>() {
            public RtGNSS parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new RtGNSS(input, extensionRegistry);
            }
        };
        public static final int SPEED_FIELD_NUMBER = 3;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public float altitude_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public float latitude_;
        /* access modifiers changed from: private */
        public float longitude_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public float speed_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements RtGNSSOrBuilder {
            private float altitude_;
            private int bitField0_;
            private float latitude_;
            private float longitude_;
            private float speed_;

            public static final Descriptor getDescriptor() {
                return RealtimeData.internal_static_RtGNSS_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return RealtimeData.internal_static_RtGNSS_fieldAccessorTable.ensureFieldAccessorsInitialized(RtGNSS.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (RtGNSS.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.longitude_ = 0.0f;
                this.bitField0_ &= -2;
                this.latitude_ = 0.0f;
                this.bitField0_ &= -3;
                this.speed_ = 0.0f;
                this.bitField0_ &= -5;
                this.altitude_ = 0.0f;
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return RealtimeData.internal_static_RtGNSS_descriptor;
            }

            public RtGNSS getDefaultInstanceForType() {
                return RtGNSS.getDefaultInstance();
            }

            public RtGNSS build() {
                RtGNSS result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public RtGNSS buildPartial() {
                RtGNSS result = new RtGNSS((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.longitude_ = this.longitude_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.latitude_ = this.latitude_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.speed_ = this.speed_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.altitude_ = this.altitude_;
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
                if (other instanceof RtGNSS) {
                    return mergeFrom((RtGNSS) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RtGNSS other) {
                if (other != RtGNSS.getDefaultInstance()) {
                    if (other.hasLongitude()) {
                        setLongitude(other.getLongitude());
                    }
                    if (other.hasLatitude()) {
                        setLatitude(other.getLatitude());
                    }
                    if (other.hasSpeed()) {
                        setSpeed(other.getSpeed());
                    }
                    if (other.hasAltitude()) {
                        setAltitude(other.getAltitude());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasLongitude() && hasLatitude() && hasSpeed() && hasAltitude()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                RtGNSS parsedMessage = null;
                try {
                    RtGNSS parsedMessage2 = (RtGNSS) RtGNSS.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    RtGNSS parsedMessage3 = (RtGNSS) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasLongitude() {
                return (this.bitField0_ & 1) == 1;
            }

            public float getLongitude() {
                return this.longitude_;
            }

            public Builder setLongitude(float value) {
                this.bitField0_ |= 1;
                this.longitude_ = value;
                onChanged();
                return this;
            }

            public Builder clearLongitude() {
                this.bitField0_ &= -2;
                this.longitude_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasLatitude() {
                return (this.bitField0_ & 2) == 2;
            }

            public float getLatitude() {
                return this.latitude_;
            }

            public Builder setLatitude(float value) {
                this.bitField0_ |= 2;
                this.latitude_ = value;
                onChanged();
                return this;
            }

            public Builder clearLatitude() {
                this.bitField0_ &= -3;
                this.latitude_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasSpeed() {
                return (this.bitField0_ & 4) == 4;
            }

            public float getSpeed() {
                return this.speed_;
            }

            public Builder setSpeed(float value) {
                this.bitField0_ |= 4;
                this.speed_ = value;
                onChanged();
                return this;
            }

            public Builder clearSpeed() {
                this.bitField0_ &= -5;
                this.speed_ = 0.0f;
                onChanged();
                return this;
            }

            public boolean hasAltitude() {
                return (this.bitField0_ & 8) == 8;
            }

            public float getAltitude() {
                return this.altitude_;
            }

            public Builder setAltitude(float value) {
                this.bitField0_ |= 8;
                this.altitude_ = value;
                onChanged();
                return this;
            }

            public Builder clearAltitude() {
                this.bitField0_ &= -9;
                this.altitude_ = 0.0f;
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

        private RtGNSS(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private RtGNSS() {
            this.memoizedIsInitialized = -1;
            this.longitude_ = 0.0f;
            this.latitude_ = 0.0f;
            this.speed_ = 0.0f;
            this.altitude_ = 0.0f;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RtGNSS(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.longitude_ = input.readFloat();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.latitude_ = input.readFloat();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
                            this.speed_ = input.readFloat();
                            break;
                        case 37:
                            this.bitField0_ |= 8;
                            this.altitude_ = input.readFloat();
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
            return RealtimeData.internal_static_RtGNSS_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return RealtimeData.internal_static_RtGNSS_fieldAccessorTable.ensureFieldAccessorsInitialized(RtGNSS.class, Builder.class);
        }

        public boolean hasLongitude() {
            return (this.bitField0_ & 1) == 1;
        }

        public float getLongitude() {
            return this.longitude_;
        }

        public boolean hasLatitude() {
            return (this.bitField0_ & 2) == 2;
        }

        public float getLatitude() {
            return this.latitude_;
        }

        public boolean hasSpeed() {
            return (this.bitField0_ & 4) == 4;
        }

        public float getSpeed() {
            return this.speed_;
        }

        public boolean hasAltitude() {
            return (this.bitField0_ & 8) == 8;
        }

        public float getAltitude() {
            return this.altitude_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasLongitude()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasLatitude()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSpeed()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasAltitude()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFloat(1, this.longitude_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFloat(2, this.latitude_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFloat(3, this.speed_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeFloat(4, this.altitude_);
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
                size2 = 0 + CodedOutputStream.computeFloatSize(1, this.longitude_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFloatSize(2, this.latitude_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFloatSize(3, this.speed_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeFloatSize(4, this.altitude_);
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
            if (!(obj instanceof RtGNSS)) {
                return super.equals(obj);
            }
            RtGNSS other = (RtGNSS) obj;
            boolean result5 = 1 != 0 && hasLongitude() == other.hasLongitude();
            if (hasLongitude()) {
                result5 = result5 && Float.floatToIntBits(getLongitude()) == Float.floatToIntBits(other.getLongitude());
            }
            if (!result5 || hasLatitude() != other.hasLatitude()) {
                result = false;
            } else {
                result = true;
            }
            if (hasLatitude()) {
                if (!result || Float.floatToIntBits(getLatitude()) != Float.floatToIntBits(other.getLatitude())) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasSpeed() != other.hasSpeed()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasSpeed()) {
                if (!result2 || Float.floatToIntBits(getSpeed()) != Float.floatToIntBits(other.getSpeed())) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasAltitude() != other.hasAltitude()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasAltitude()) {
                if (!result3 || Float.floatToIntBits(getAltitude()) != Float.floatToIntBits(other.getAltitude())) {
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
            if (hasLongitude()) {
                hash = (((hash * 37) + 1) * 53) + Float.floatToIntBits(getLongitude());
            }
            if (hasLatitude()) {
                hash = (((hash * 37) + 2) * 53) + Float.floatToIntBits(getLatitude());
            }
            if (hasSpeed()) {
                hash = (((hash * 37) + 3) * 53) + Float.floatToIntBits(getSpeed());
            }
            if (hasAltitude()) {
                hash = (((hash * 37) + 4) * 53) + Float.floatToIntBits(getAltitude());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static RtGNSS parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RtGNSS) PARSER.parseFrom(data);
        }

        public static RtGNSS parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtGNSS) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtGNSS parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RtGNSS) PARSER.parseFrom(data);
        }

        public static RtGNSS parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtGNSS) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtGNSS parseFrom(InputStream input) throws IOException {
            return (RtGNSS) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtGNSS parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtGNSS) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtGNSS parseDelimitedFrom(InputStream input) throws IOException {
            return (RtGNSS) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RtGNSS parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtGNSS) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtGNSS parseFrom(CodedInputStream input) throws IOException {
            return (RtGNSS) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtGNSS parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtGNSS) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RtGNSS prototype) {
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

        public static RtGNSS getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RtGNSS> parser() {
            return PARSER;
        }

        public Parser<RtGNSS> getParserForType() {
            return PARSER;
        }

        public RtGNSS getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class RtHealth extends GeneratedMessageV3 implements RtHealthOrBuilder {
        public static final int CALORIE_FIELD_NUMBER = 3;
        private static final RtHealth DEFAULT_INSTANCE = new RtHealth();
        public static final int DISTANCE_FIELD_NUMBER = 2;
        @Deprecated
        public static final Parser<RtHealth> PARSER = new AbstractParser<RtHealth>() {
            public RtHealth parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new RtHealth(input, extensionRegistry);
            }
        };
        public static final int STEPS_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int calorie_;
        /* access modifiers changed from: private */
        public int distance_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int steps_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements RtHealthOrBuilder {
            private int bitField0_;
            private int calorie_;
            private int distance_;
            private int steps_;

            public static final Descriptor getDescriptor() {
                return RealtimeData.internal_static_RtHealth_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return RealtimeData.internal_static_RtHealth_fieldAccessorTable.ensureFieldAccessorsInitialized(RtHealth.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (RtHealth.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.steps_ = 0;
                this.bitField0_ &= -2;
                this.distance_ = 0;
                this.bitField0_ &= -3;
                this.calorie_ = 0;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return RealtimeData.internal_static_RtHealth_descriptor;
            }

            public RtHealth getDefaultInstanceForType() {
                return RtHealth.getDefaultInstance();
            }

            public RtHealth build() {
                RtHealth result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public RtHealth buildPartial() {
                RtHealth result = new RtHealth((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.steps_ = this.steps_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.distance_ = this.distance_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
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
                if (other instanceof RtHealth) {
                    return mergeFrom((RtHealth) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RtHealth other) {
                if (other != RtHealth.getDefaultInstance()) {
                    if (other.hasSteps()) {
                        setSteps(other.getSteps());
                    }
                    if (other.hasDistance()) {
                        setDistance(other.getDistance());
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
                if (hasSteps() && hasDistance() && hasCalorie()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                RtHealth parsedMessage = null;
                try {
                    RtHealth parsedMessage2 = (RtHealth) RtHealth.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    RtHealth parsedMessage3 = (RtHealth) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSteps() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getSteps() {
                return this.steps_;
            }

            public Builder setSteps(int value) {
                this.bitField0_ |= 1;
                this.steps_ = value;
                onChanged();
                return this;
            }

            public Builder clearSteps() {
                this.bitField0_ &= -2;
                this.steps_ = 0;
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
                this.distance_ = 0;
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

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private RtHealth(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private RtHealth() {
            this.memoizedIsInitialized = -1;
            this.steps_ = 0;
            this.distance_ = 0;
            this.calorie_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RtHealth(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.steps_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.distance_ = input.readFixed32();
                            break;
                        case 29:
                            this.bitField0_ |= 4;
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
            return RealtimeData.internal_static_RtHealth_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return RealtimeData.internal_static_RtHealth_fieldAccessorTable.ensureFieldAccessorsInitialized(RtHealth.class, Builder.class);
        }

        public boolean hasSteps() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getSteps() {
            return this.steps_;
        }

        public boolean hasDistance() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getDistance() {
            return this.distance_;
        }

        public boolean hasCalorie() {
            return (this.bitField0_ & 4) == 4;
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
            if (!hasSteps()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasDistance()) {
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
                output.writeFixed32(1, this.steps_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.distance_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeFixed32(3, this.calorie_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.steps_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.distance_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeFixed32Size(3, this.calorie_);
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
            if (!(obj instanceof RtHealth)) {
                return super.equals(obj);
            }
            RtHealth other = (RtHealth) obj;
            boolean result4 = 1 != 0 && hasSteps() == other.hasSteps();
            if (hasSteps()) {
                result4 = result4 && getSteps() == other.getSteps();
            }
            if (!result4 || hasDistance() != other.hasDistance()) {
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
            if (hasSteps()) {
                hash = (((hash * 37) + 1) * 53) + getSteps();
            }
            if (hasDistance()) {
                hash = (((hash * 37) + 2) * 53) + getDistance();
            }
            if (hasCalorie()) {
                hash = (((hash * 37) + 3) * 53) + getCalorie();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static RtHealth parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RtHealth) PARSER.parseFrom(data);
        }

        public static RtHealth parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtHealth) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtHealth parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RtHealth) PARSER.parseFrom(data);
        }

        public static RtHealth parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtHealth) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtHealth parseFrom(InputStream input) throws IOException {
            return (RtHealth) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtHealth parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtHealth) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtHealth parseDelimitedFrom(InputStream input) throws IOException {
            return (RtHealth) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RtHealth parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtHealth) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtHealth parseFrom(CodedInputStream input) throws IOException {
            return (RtHealth) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtHealth parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtHealth) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RtHealth prototype) {
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

        public static RtHealth getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RtHealth> parser() {
            return PARSER;
        }

        public Parser<RtHealth> getParserForType() {
            return PARSER;
        }

        public RtHealth getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum RtKeyEvent implements ProtocolMessageEnum {
        RT_KEY_CAMERA(0);
        
        public static final int RT_KEY_CAMERA_VALUE = 0;
        private static final RtKeyEvent[] VALUES = null;
        private static final EnumLiteMap<RtKeyEvent> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<RtKeyEvent>() {
                public RtKeyEvent findValueByNumber(int number) {
                    return RtKeyEvent.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static RtKeyEvent valueOf(int value2) {
            return forNumber(value2);
        }

        public static RtKeyEvent forNumber(int value2) {
            switch (value2) {
                case 0:
                    return RT_KEY_CAMERA;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<RtKeyEvent> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) RealtimeData.getDescriptor().getEnumTypes().get(1);
        }

        public static RtKeyEvent valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private RtKeyEvent(int value2) {
            this.value = value2;
        }
    }

    public enum RtMode implements ProtocolMessageEnum {
        RT_MODE_BACK_NORMAL(0),
        RT_MODE_ENTER_CAMERA(1);
        
        public static final int RT_MODE_BACK_NORMAL_VALUE = 0;
        public static final int RT_MODE_ENTER_CAMERA_VALUE = 1;
        private static final RtMode[] VALUES = null;
        private static final EnumLiteMap<RtMode> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<RtMode>() {
                public RtMode findValueByNumber(int number) {
                    return RtMode.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static RtMode valueOf(int value2) {
            return forNumber(value2);
        }

        public static RtMode forNumber(int value2) {
            switch (value2) {
                case 0:
                    return RT_MODE_BACK_NORMAL;
                case 1:
                    return RT_MODE_ENTER_CAMERA;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<RtMode> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) RealtimeData.getDescriptor().getEnumTypes().get(0);
        }

        public static RtMode valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private RtMode(int value2) {
            this.value = value2;
        }
    }

    public static final class RtNotification extends GeneratedMessageV3 implements RtNotificationOrBuilder {
        private static final RtNotification DEFAULT_INSTANCE = new RtNotification();
        @Deprecated
        public static final Parser<RtNotification> PARSER = new AbstractParser<RtNotification>() {
            public RtNotification parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new RtNotification(input, extensionRegistry);
            }
        };
        public static final int RT_DATA_FIELD_NUMBER = 1;
        public static final int RT_STATE_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int dataCase_;
        /* access modifiers changed from: private */
        public Object data_;
        private byte memoizedIsInitialized;

        public enum DataCase implements EnumLite {
            RT_DATA(1),
            RT_STATE(2),
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
                    case 1:
                        return RT_DATA;
                    case 2:
                        return RT_STATE;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements RtNotificationOrBuilder {
            private int bitField0_;
            private int dataCase_;
            private Object data_;
            private SingleFieldBuilderV3<RtData, Builder, RtDataOrBuilder> rtDataBuilder_;
            private SingleFieldBuilderV3<RtState, Builder, RtStateOrBuilder> rtStateBuilder_;

            public static final Descriptor getDescriptor() {
                return RealtimeData.internal_static_RtNotification_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return RealtimeData.internal_static_RtNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(RtNotification.class, Builder.class);
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
                if (RtNotification.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.dataCase_ = 0;
                this.data_ = null;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return RealtimeData.internal_static_RtNotification_descriptor;
            }

            public RtNotification getDefaultInstanceForType() {
                return RtNotification.getDefaultInstance();
            }

            public RtNotification build() {
                RtNotification result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public RtNotification buildPartial() {
                RtNotification result = new RtNotification((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int i = this.bitField0_;
                if (this.dataCase_ == 1) {
                    if (this.rtDataBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.rtDataBuilder_.build();
                    }
                }
                if (this.dataCase_ == 2) {
                    if (this.rtStateBuilder_ == null) {
                        result.data_ = this.data_;
                    } else {
                        result.data_ = this.rtStateBuilder_.build();
                    }
                }
                result.bitField0_ = 0;
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
                if (other instanceof RtNotification) {
                    return mergeFrom((RtNotification) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RtNotification other) {
                if (other != RtNotification.getDefaultInstance()) {
                    switch (AnonymousClass2.$SwitchMap$com$iwown$ble_module$proto$base$RealtimeData$RtNotification$DataCase[other.getDataCase().ordinal()]) {
                        case 1:
                            mergeRtData(other.getRtData());
                            break;
                        case 2:
                            mergeRtState(other.getRtState());
                            break;
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasRtData() || getRtData().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                RtNotification parsedMessage = null;
                try {
                    RtNotification parsedMessage2 = (RtNotification) RtNotification.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    RtNotification parsedMessage3 = (RtNotification) e.getUnfinishedMessage();
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

            public boolean hasRtData() {
                return this.dataCase_ == 1;
            }

            public RtData getRtData() {
                if (this.rtDataBuilder_ == null) {
                    if (this.dataCase_ == 1) {
                        return (RtData) this.data_;
                    }
                    return RtData.getDefaultInstance();
                } else if (this.dataCase_ == 1) {
                    return (RtData) this.rtDataBuilder_.getMessage();
                } else {
                    return RtData.getDefaultInstance();
                }
            }

            public Builder setRtData(RtData value) {
                if (this.rtDataBuilder_ != null) {
                    this.rtDataBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 1;
                return this;
            }

            public Builder setRtData(Builder builderForValue) {
                if (this.rtDataBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.rtDataBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 1;
                return this;
            }

            public Builder mergeRtData(RtData value) {
                if (this.rtDataBuilder_ == null) {
                    if (this.dataCase_ != 1 || this.data_ == RtData.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = RtData.newBuilder((RtData) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 1) {
                        this.rtDataBuilder_.mergeFrom(value);
                    }
                    this.rtDataBuilder_.setMessage(value);
                }
                this.dataCase_ = 1;
                return this;
            }

            public Builder clearRtData() {
                if (this.rtDataBuilder_ != null) {
                    if (this.dataCase_ == 1) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.rtDataBuilder_.clear();
                } else if (this.dataCase_ == 1) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getRtDataBuilder() {
                return (Builder) getRtDataFieldBuilder().getBuilder();
            }

            public RtDataOrBuilder getRtDataOrBuilder() {
                if (this.dataCase_ == 1 && this.rtDataBuilder_ != null) {
                    return (RtDataOrBuilder) this.rtDataBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 1) {
                    return (RtData) this.data_;
                }
                return RtData.getDefaultInstance();
            }

            private SingleFieldBuilderV3<RtData, Builder, RtDataOrBuilder> getRtDataFieldBuilder() {
                if (this.rtDataBuilder_ == null) {
                    if (this.dataCase_ != 1) {
                        this.data_ = RtData.getDefaultInstance();
                    }
                    this.rtDataBuilder_ = new SingleFieldBuilderV3<>((RtData) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 1;
                onChanged();
                return this.rtDataBuilder_;
            }

            public boolean hasRtState() {
                return this.dataCase_ == 2;
            }

            public RtState getRtState() {
                if (this.rtStateBuilder_ == null) {
                    if (this.dataCase_ == 2) {
                        return (RtState) this.data_;
                    }
                    return RtState.getDefaultInstance();
                } else if (this.dataCase_ == 2) {
                    return (RtState) this.rtStateBuilder_.getMessage();
                } else {
                    return RtState.getDefaultInstance();
                }
            }

            public Builder setRtState(RtState value) {
                if (this.rtStateBuilder_ != null) {
                    this.rtStateBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    onChanged();
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder setRtState(Builder builderForValue) {
                if (this.rtStateBuilder_ == null) {
                    this.data_ = builderForValue.build();
                    onChanged();
                } else {
                    this.rtStateBuilder_.setMessage(builderForValue.build());
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder mergeRtState(RtState value) {
                if (this.rtStateBuilder_ == null) {
                    if (this.dataCase_ != 2 || this.data_ == RtState.getDefaultInstance()) {
                        this.data_ = value;
                    } else {
                        this.data_ = RtState.newBuilder((RtState) this.data_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    if (this.dataCase_ == 2) {
                        this.rtStateBuilder_.mergeFrom(value);
                    }
                    this.rtStateBuilder_.setMessage(value);
                }
                this.dataCase_ = 2;
                return this;
            }

            public Builder clearRtState() {
                if (this.rtStateBuilder_ != null) {
                    if (this.dataCase_ == 2) {
                        this.dataCase_ = 0;
                        this.data_ = null;
                    }
                    this.rtStateBuilder_.clear();
                } else if (this.dataCase_ == 2) {
                    this.dataCase_ = 0;
                    this.data_ = null;
                    onChanged();
                }
                return this;
            }

            public Builder getRtStateBuilder() {
                return (Builder) getRtStateFieldBuilder().getBuilder();
            }

            public RtStateOrBuilder getRtStateOrBuilder() {
                if (this.dataCase_ == 2 && this.rtStateBuilder_ != null) {
                    return (RtStateOrBuilder) this.rtStateBuilder_.getMessageOrBuilder();
                }
                if (this.dataCase_ == 2) {
                    return (RtState) this.data_;
                }
                return RtState.getDefaultInstance();
            }

            private SingleFieldBuilderV3<RtState, Builder, RtStateOrBuilder> getRtStateFieldBuilder() {
                if (this.rtStateBuilder_ == null) {
                    if (this.dataCase_ != 2) {
                        this.data_ = RtState.getDefaultInstance();
                    }
                    this.rtStateBuilder_ = new SingleFieldBuilderV3<>((RtState) this.data_, getParentForChildren(), isClean());
                    this.data_ = null;
                }
                this.dataCase_ = 2;
                onChanged();
                return this.rtStateBuilder_;
            }

            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        private RtNotification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        private RtNotification() {
            this.dataCase_ = 0;
            this.memoizedIsInitialized = -1;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RtNotification(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.dataCase_ == 1) {
                                subBuilder = ((RtData) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(RtData.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom((RtData) this.data_);
                                this.data_ = subBuilder.buildPartial();
                            }
                            this.dataCase_ = 1;
                            break;
                        case 18:
                            Builder subBuilder2 = null;
                            if (this.dataCase_ == 2) {
                                subBuilder2 = ((RtState) this.data_).toBuilder();
                            }
                            this.data_ = input.readMessage(RtState.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom((RtState) this.data_);
                                this.data_ = subBuilder2.buildPartial();
                            }
                            this.dataCase_ = 2;
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
            return RealtimeData.internal_static_RtNotification_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return RealtimeData.internal_static_RtNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(RtNotification.class, Builder.class);
        }

        public DataCase getDataCase() {
            return DataCase.forNumber(this.dataCase_);
        }

        public boolean hasRtData() {
            return this.dataCase_ == 1;
        }

        public RtData getRtData() {
            if (this.dataCase_ == 1) {
                return (RtData) this.data_;
            }
            return RtData.getDefaultInstance();
        }

        public RtDataOrBuilder getRtDataOrBuilder() {
            if (this.dataCase_ == 1) {
                return (RtData) this.data_;
            }
            return RtData.getDefaultInstance();
        }

        public boolean hasRtState() {
            return this.dataCase_ == 2;
        }

        public RtState getRtState() {
            if (this.dataCase_ == 2) {
                return (RtState) this.data_;
            }
            return RtState.getDefaultInstance();
        }

        public RtStateOrBuilder getRtStateOrBuilder() {
            if (this.dataCase_ == 2) {
                return (RtState) this.data_;
            }
            return RtState.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasRtData() || getRtData().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            }
            this.memoizedIsInitialized = 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if (this.dataCase_ == 1) {
                output.writeMessage(1, (RtData) this.data_);
            }
            if (this.dataCase_ == 2) {
                output.writeMessage(2, (RtState) this.data_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            if (this.dataCase_ == 1) {
                size2 = 0 + CodedOutputStream.computeMessageSize(1, (RtData) this.data_);
            }
            if (this.dataCase_ == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, (RtState) this.data_);
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
            if (!(obj instanceof RtNotification)) {
                return super.equals(obj);
            }
            RtNotification other = (RtNotification) obj;
            if (1 == 0 || !getDataCase().equals(other.getDataCase())) {
                result = false;
            } else {
                result = true;
            }
            if (!result) {
                return false;
            }
            switch (this.dataCase_) {
                case 1:
                    if (result && getRtData().equals(other.getRtData())) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                    break;
                case 2:
                    if (result && getRtState().equals(other.getRtState())) {
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
            switch (this.dataCase_) {
                case 1:
                    hash = (((hash * 37) + 1) * 53) + getRtData().hashCode();
                    break;
                case 2:
                    hash = (((hash * 37) + 2) * 53) + getRtState().hashCode();
                    break;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static RtNotification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RtNotification) PARSER.parseFrom(data);
        }

        public static RtNotification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtNotification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RtNotification) PARSER.parseFrom(data);
        }

        public static RtNotification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtNotification parseFrom(InputStream input) throws IOException {
            return (RtNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtNotification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtNotification parseDelimitedFrom(InputStream input) throws IOException {
            return (RtNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RtNotification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtNotification parseFrom(CodedInputStream input) throws IOException {
            return (RtNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtNotification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RtNotification prototype) {
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

        public static RtNotification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RtNotification> parser() {
            return PARSER;
        }

        public Parser<RtNotification> getParserForType() {
            return PARSER;
        }

        public RtNotification getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class RtState extends GeneratedMessageV3 implements RtStateOrBuilder {
        public static final int BATTERY_FIELD_NUMBER = 2;
        private static final RtState DEFAULT_INSTANCE = new RtState();
        public static final int HEALTH_FIELD_NUMBER = 3;
        public static final int MODE_FIELD_NUMBER = 4;
        @Deprecated
        public static final Parser<RtState> PARSER = new AbstractParser<RtState>() {
            public RtState parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new RtState(input, extensionRegistry);
            }
        };
        public static final int TIME_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int battery_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int health_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int mode_;
        /* access modifiers changed from: private */
        public int time_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements RtStateOrBuilder {
            private int battery_;
            private int bitField0_;
            private int health_;
            private int mode_;
            private int time_;

            public static final Descriptor getDescriptor() {
                return RealtimeData.internal_static_RtState_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return RealtimeData.internal_static_RtState_fieldAccessorTable.ensureFieldAccessorsInitialized(RtState.class, Builder.class);
            }

            private Builder() {
                this.time_ = 0;
                this.battery_ = 0;
                this.health_ = 0;
                this.mode_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.time_ = 0;
                this.battery_ = 0;
                this.health_ = 0;
                this.mode_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (RtState.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.time_ = 0;
                this.bitField0_ &= -2;
                this.battery_ = 0;
                this.bitField0_ &= -3;
                this.health_ = 0;
                this.bitField0_ &= -5;
                this.mode_ = 0;
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return RealtimeData.internal_static_RtState_descriptor;
            }

            public RtState getDefaultInstanceForType() {
                return RtState.getDefaultInstance();
            }

            public RtState build() {
                RtState result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public RtState buildPartial() {
                RtState result = new RtState((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.time_ = this.time_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.battery_ = this.battery_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.health_ = this.health_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.mode_ = this.mode_;
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
                if (other instanceof RtState) {
                    return mergeFrom((RtState) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RtState other) {
                if (other != RtState.getDefaultInstance()) {
                    if (other.hasTime()) {
                        setTime(other.getTime());
                    }
                    if (other.hasBattery()) {
                        setBattery(other.getBattery());
                    }
                    if (other.hasHealth()) {
                        setHealth(other.getHealth());
                    }
                    if (other.hasMode()) {
                        setMode(other.getMode());
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
                RtState parsedMessage = null;
                try {
                    RtState parsedMessage2 = (RtState) RtState.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    RtState parsedMessage3 = (RtState) e.getUnfinishedMessage();
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

            public RtSync getTime() {
                RtSync result = RtSync.valueOf(this.time_);
                return result == null ? RtSync.STOP_ALL : result;
            }

            public Builder setTime(RtSync value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.time_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearTime() {
                this.bitField0_ &= -2;
                this.time_ = 0;
                onChanged();
                return this;
            }

            public boolean hasBattery() {
                return (this.bitField0_ & 2) == 2;
            }

            public RtSync getBattery() {
                RtSync result = RtSync.valueOf(this.battery_);
                return result == null ? RtSync.STOP_ALL : result;
            }

            public Builder setBattery(RtSync value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.battery_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearBattery() {
                this.bitField0_ &= -3;
                this.battery_ = 0;
                onChanged();
                return this;
            }

            public boolean hasHealth() {
                return (this.bitField0_ & 4) == 4;
            }

            public RtSync getHealth() {
                RtSync result = RtSync.valueOf(this.health_);
                return result == null ? RtSync.STOP_ALL : result;
            }

            public Builder setHealth(RtSync value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.health_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearHealth() {
                this.bitField0_ &= -5;
                this.health_ = 0;
                onChanged();
                return this;
            }

            public boolean hasMode() {
                return (this.bitField0_ & 8) == 8;
            }

            public RtMode getMode() {
                RtMode result = RtMode.valueOf(this.mode_);
                return result == null ? RtMode.RT_MODE_BACK_NORMAL : result;
            }

            public Builder setMode(RtMode value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.mode_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearMode() {
                this.bitField0_ &= -9;
                this.mode_ = 0;
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

        private RtState(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private RtState() {
            this.memoizedIsInitialized = -1;
            this.time_ = 0;
            this.battery_ = 0;
            this.health_ = 0;
            this.mode_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RtState(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (RtSync.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.time_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 16:
                            int rawValue2 = input.readEnum();
                            if (RtSync.valueOf(rawValue2) != null) {
                                this.bitField0_ |= 2;
                                this.battery_ = rawValue2;
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue2);
                                break;
                            }
                        case 24:
                            int rawValue3 = input.readEnum();
                            if (RtSync.valueOf(rawValue3) != null) {
                                this.bitField0_ |= 4;
                                this.health_ = rawValue3;
                                break;
                            } else {
                                unknownFields.mergeVarintField(3, rawValue3);
                                break;
                            }
                        case 32:
                            int rawValue4 = input.readEnum();
                            if (RtMode.valueOf(rawValue4) != null) {
                                this.bitField0_ |= 8;
                                this.mode_ = rawValue4;
                                break;
                            } else {
                                unknownFields.mergeVarintField(4, rawValue4);
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
            return RealtimeData.internal_static_RtState_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return RealtimeData.internal_static_RtState_fieldAccessorTable.ensureFieldAccessorsInitialized(RtState.class, Builder.class);
        }

        public boolean hasTime() {
            return (this.bitField0_ & 1) == 1;
        }

        public RtSync getTime() {
            RtSync result = RtSync.valueOf(this.time_);
            return result == null ? RtSync.STOP_ALL : result;
        }

        public boolean hasBattery() {
            return (this.bitField0_ & 2) == 2;
        }

        public RtSync getBattery() {
            RtSync result = RtSync.valueOf(this.battery_);
            return result == null ? RtSync.STOP_ALL : result;
        }

        public boolean hasHealth() {
            return (this.bitField0_ & 4) == 4;
        }

        public RtSync getHealth() {
            RtSync result = RtSync.valueOf(this.health_);
            return result == null ? RtSync.STOP_ALL : result;
        }

        public boolean hasMode() {
            return (this.bitField0_ & 8) == 8;
        }

        public RtMode getMode() {
            RtMode result = RtMode.valueOf(this.mode_);
            return result == null ? RtMode.RT_MODE_BACK_NORMAL : result;
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
                output.writeEnum(1, this.time_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeEnum(2, this.battery_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeEnum(3, this.health_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeEnum(4, this.mode_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.time_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeEnumSize(2, this.battery_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeEnumSize(3, this.health_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeEnumSize(4, this.mode_);
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
            if (!(obj instanceof RtState)) {
                return super.equals(obj);
            }
            RtState other = (RtState) obj;
            boolean result5 = 1 != 0 && hasTime() == other.hasTime();
            if (hasTime()) {
                result5 = result5 && this.time_ == other.time_;
            }
            if (!result5 || hasBattery() != other.hasBattery()) {
                result = false;
            } else {
                result = true;
            }
            if (hasBattery()) {
                if (!result || this.battery_ != other.battery_) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasHealth() != other.hasHealth()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasHealth()) {
                if (!result2 || this.health_ != other.health_) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasMode() != other.hasMode()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasMode()) {
                if (!result3 || this.mode_ != other.mode_) {
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
            if (hasTime()) {
                hash = (((hash * 37) + 1) * 53) + this.time_;
            }
            if (hasBattery()) {
                hash = (((hash * 37) + 2) * 53) + this.battery_;
            }
            if (hasHealth()) {
                hash = (((hash * 37) + 3) * 53) + this.health_;
            }
            if (hasMode()) {
                hash = (((hash * 37) + 4) * 53) + this.mode_;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static RtState parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RtState) PARSER.parseFrom(data);
        }

        public static RtState parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtState) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtState parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RtState) PARSER.parseFrom(data);
        }

        public static RtState parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtState) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtState parseFrom(InputStream input) throws IOException {
            return (RtState) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtState parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtState) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtState parseDelimitedFrom(InputStream input) throws IOException {
            return (RtState) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RtState parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtState) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtState parseFrom(CodedInputStream input) throws IOException {
            return (RtState) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtState parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtState) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RtState prototype) {
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

        public static RtState getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RtState> parser() {
            return PARSER;
        }

        public Parser<RtState> getParserForType() {
            return PARSER;
        }

        public RtState getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class RtSubscriber extends GeneratedMessageV3 implements RtSubscriberOrBuilder {
        public static final int BATTERY_FIELD_NUMBER = 2;
        private static final RtSubscriber DEFAULT_INSTANCE = new RtSubscriber();
        public static final int HEALTH_FIELD_NUMBER = 3;
        public static final int MODE_FIELD_NUMBER = 4;
        @Deprecated
        public static final Parser<RtSubscriber> PARSER = new AbstractParser<RtSubscriber>() {
            public RtSubscriber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new RtSubscriber(input, extensionRegistry);
            }
        };
        public static final int TIME_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int battery_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int health_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int mode_;
        /* access modifiers changed from: private */
        public int time_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements RtSubscriberOrBuilder {
            private int battery_;
            private int bitField0_;
            private int health_;
            private int mode_;
            private int time_;

            public static final Descriptor getDescriptor() {
                return RealtimeData.internal_static_RtSubscriber_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return RealtimeData.internal_static_RtSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(RtSubscriber.class, Builder.class);
            }

            private Builder() {
                this.time_ = 0;
                this.battery_ = 0;
                this.health_ = 0;
                this.mode_ = 0;
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.time_ = 0;
                this.battery_ = 0;
                this.health_ = 0;
                this.mode_ = 0;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (RtSubscriber.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.time_ = 0;
                this.bitField0_ &= -2;
                this.battery_ = 0;
                this.bitField0_ &= -3;
                this.health_ = 0;
                this.bitField0_ &= -5;
                this.mode_ = 0;
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return RealtimeData.internal_static_RtSubscriber_descriptor;
            }

            public RtSubscriber getDefaultInstanceForType() {
                return RtSubscriber.getDefaultInstance();
            }

            public RtSubscriber build() {
                RtSubscriber result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public RtSubscriber buildPartial() {
                RtSubscriber result = new RtSubscriber((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.time_ = this.time_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.battery_ = this.battery_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.health_ = this.health_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.mode_ = this.mode_;
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
                if (other instanceof RtSubscriber) {
                    return mergeFrom((RtSubscriber) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RtSubscriber other) {
                if (other != RtSubscriber.getDefaultInstance()) {
                    if (other.hasTime()) {
                        setTime(other.getTime());
                    }
                    if (other.hasBattery()) {
                        setBattery(other.getBattery());
                    }
                    if (other.hasHealth()) {
                        setHealth(other.getHealth());
                    }
                    if (other.hasMode()) {
                        setMode(other.getMode());
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
                RtSubscriber parsedMessage = null;
                try {
                    RtSubscriber parsedMessage2 = (RtSubscriber) RtSubscriber.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    RtSubscriber parsedMessage3 = (RtSubscriber) e.getUnfinishedMessage();
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

            public RtSync getTime() {
                RtSync result = RtSync.valueOf(this.time_);
                return result == null ? RtSync.STOP_ALL : result;
            }

            public Builder setTime(RtSync value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.time_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearTime() {
                this.bitField0_ &= -2;
                this.time_ = 0;
                onChanged();
                return this;
            }

            public boolean hasBattery() {
                return (this.bitField0_ & 2) == 2;
            }

            public RtSync getBattery() {
                RtSync result = RtSync.valueOf(this.battery_);
                return result == null ? RtSync.STOP_ALL : result;
            }

            public Builder setBattery(RtSync value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.battery_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearBattery() {
                this.bitField0_ &= -3;
                this.battery_ = 0;
                onChanged();
                return this;
            }

            public boolean hasHealth() {
                return (this.bitField0_ & 4) == 4;
            }

            public RtSync getHealth() {
                RtSync result = RtSync.valueOf(this.health_);
                return result == null ? RtSync.STOP_ALL : result;
            }

            public Builder setHealth(RtSync value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.health_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearHealth() {
                this.bitField0_ &= -5;
                this.health_ = 0;
                onChanged();
                return this;
            }

            public boolean hasMode() {
                return (this.bitField0_ & 8) == 8;
            }

            public RtMode getMode() {
                RtMode result = RtMode.valueOf(this.mode_);
                return result == null ? RtMode.RT_MODE_BACK_NORMAL : result;
            }

            public Builder setMode(RtMode value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.mode_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearMode() {
                this.bitField0_ &= -9;
                this.mode_ = 0;
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

        private RtSubscriber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private RtSubscriber() {
            this.memoizedIsInitialized = -1;
            this.time_ = 0;
            this.battery_ = 0;
            this.health_ = 0;
            this.mode_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RtSubscriber(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (RtSync.valueOf(rawValue) != null) {
                                this.bitField0_ |= 1;
                                this.time_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(1, rawValue);
                                break;
                            }
                        case 16:
                            int rawValue2 = input.readEnum();
                            if (RtSync.valueOf(rawValue2) != null) {
                                this.bitField0_ |= 2;
                                this.battery_ = rawValue2;
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue2);
                                break;
                            }
                        case 24:
                            int rawValue3 = input.readEnum();
                            if (RtSync.valueOf(rawValue3) != null) {
                                this.bitField0_ |= 4;
                                this.health_ = rawValue3;
                                break;
                            } else {
                                unknownFields.mergeVarintField(3, rawValue3);
                                break;
                            }
                        case 32:
                            int rawValue4 = input.readEnum();
                            if (RtMode.valueOf(rawValue4) != null) {
                                this.bitField0_ |= 8;
                                this.mode_ = rawValue4;
                                break;
                            } else {
                                unknownFields.mergeVarintField(4, rawValue4);
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
            return RealtimeData.internal_static_RtSubscriber_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return RealtimeData.internal_static_RtSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(RtSubscriber.class, Builder.class);
        }

        public boolean hasTime() {
            return (this.bitField0_ & 1) == 1;
        }

        public RtSync getTime() {
            RtSync result = RtSync.valueOf(this.time_);
            return result == null ? RtSync.STOP_ALL : result;
        }

        public boolean hasBattery() {
            return (this.bitField0_ & 2) == 2;
        }

        public RtSync getBattery() {
            RtSync result = RtSync.valueOf(this.battery_);
            return result == null ? RtSync.STOP_ALL : result;
        }

        public boolean hasHealth() {
            return (this.bitField0_ & 4) == 4;
        }

        public RtSync getHealth() {
            RtSync result = RtSync.valueOf(this.health_);
            return result == null ? RtSync.STOP_ALL : result;
        }

        public boolean hasMode() {
            return (this.bitField0_ & 8) == 8;
        }

        public RtMode getMode() {
            RtMode result = RtMode.valueOf(this.mode_);
            return result == null ? RtMode.RT_MODE_BACK_NORMAL : result;
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
                output.writeEnum(1, this.time_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeEnum(2, this.battery_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeEnum(3, this.health_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeEnum(4, this.mode_);
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
                size2 = 0 + CodedOutputStream.computeEnumSize(1, this.time_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeEnumSize(2, this.battery_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeEnumSize(3, this.health_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeEnumSize(4, this.mode_);
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
            if (!(obj instanceof RtSubscriber)) {
                return super.equals(obj);
            }
            RtSubscriber other = (RtSubscriber) obj;
            boolean result5 = 1 != 0 && hasTime() == other.hasTime();
            if (hasTime()) {
                result5 = result5 && this.time_ == other.time_;
            }
            if (!result5 || hasBattery() != other.hasBattery()) {
                result = false;
            } else {
                result = true;
            }
            if (hasBattery()) {
                if (!result || this.battery_ != other.battery_) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasHealth() != other.hasHealth()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasHealth()) {
                if (!result2 || this.health_ != other.health_) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasMode() != other.hasMode()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasMode()) {
                if (!result3 || this.mode_ != other.mode_) {
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
            if (hasTime()) {
                hash = (((hash * 37) + 1) * 53) + this.time_;
            }
            if (hasBattery()) {
                hash = (((hash * 37) + 2) * 53) + this.battery_;
            }
            if (hasHealth()) {
                hash = (((hash * 37) + 3) * 53) + this.health_;
            }
            if (hasMode()) {
                hash = (((hash * 37) + 4) * 53) + this.mode_;
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static RtSubscriber parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RtSubscriber) PARSER.parseFrom(data);
        }

        public static RtSubscriber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtSubscriber parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RtSubscriber) PARSER.parseFrom(data);
        }

        public static RtSubscriber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtSubscriber parseFrom(InputStream input) throws IOException {
            return (RtSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtSubscriber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtSubscriber parseDelimitedFrom(InputStream input) throws IOException {
            return (RtSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RtSubscriber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtSubscriber parseFrom(CodedInputStream input) throws IOException {
            return (RtSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtSubscriber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RtSubscriber prototype) {
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

        public static RtSubscriber getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RtSubscriber> parser() {
            return PARSER;
        }

        public Parser<RtSubscriber> getParserForType() {
            return PARSER;
        }

        public RtSubscriber getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum RtSync implements ProtocolMessageEnum {
        STOP_ALL(0),
        ONLY_ONCE(1),
        ON_VALUE_CHANGE(2),
        ON_SECOND_CHANGE(3),
        ON_MINUTE_CHANGE(4),
        ON_HOUR_CHANGE(5),
        ON_DAY_CHANGE(6);
        
        public static final int ONLY_ONCE_VALUE = 1;
        public static final int ON_DAY_CHANGE_VALUE = 6;
        public static final int ON_HOUR_CHANGE_VALUE = 5;
        public static final int ON_MINUTE_CHANGE_VALUE = 4;
        public static final int ON_SECOND_CHANGE_VALUE = 3;
        public static final int ON_VALUE_CHANGE_VALUE = 2;
        public static final int STOP_ALL_VALUE = 0;
        private static final RtSync[] VALUES = null;
        private static final EnumLiteMap<RtSync> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<RtSync>() {
                public RtSync findValueByNumber(int number) {
                    return RtSync.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static RtSync valueOf(int value2) {
            return forNumber(value2);
        }

        public static RtSync forNumber(int value2) {
            switch (value2) {
                case 0:
                    return STOP_ALL;
                case 1:
                    return ONLY_ONCE;
                case 2:
                    return ON_VALUE_CHANGE;
                case 3:
                    return ON_SECOND_CHANGE;
                case 4:
                    return ON_MINUTE_CHANGE;
                case 5:
                    return ON_HOUR_CHANGE;
                case 6:
                    return ON_DAY_CHANGE;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<RtSync> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) RealtimeData.getDescriptor().getEnumTypes().get(2);
        }

        public static RtSync valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private RtSync(int value2) {
            this.value = value2;
        }
    }

    public static final class RtTime extends GeneratedMessageV3 implements RtTimeOrBuilder {
        private static final RtTime DEFAULT_INSTANCE = new RtTime();
        @Deprecated
        public static final Parser<RtTime> PARSER = new AbstractParser<RtTime>() {
            public RtTime parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new RtTime(input, extensionRegistry);
            }
        };
        public static final int SECONDS_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public int seconds_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements RtTimeOrBuilder {
            private int bitField0_;
            private int seconds_;

            public static final Descriptor getDescriptor() {
                return RealtimeData.internal_static_RtTime_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return RealtimeData.internal_static_RtTime_fieldAccessorTable.ensureFieldAccessorsInitialized(RtTime.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (RtTime.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.seconds_ = 0;
                this.bitField0_ &= -2;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return RealtimeData.internal_static_RtTime_descriptor;
            }

            public RtTime getDefaultInstanceForType() {
                return RtTime.getDefaultInstance();
            }

            public RtTime build() {
                RtTime result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public RtTime buildPartial() {
                RtTime result = new RtTime((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.seconds_ = this.seconds_;
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
                if (other instanceof RtTime) {
                    return mergeFrom((RtTime) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(RtTime other) {
                if (other != RtTime.getDefaultInstance()) {
                    if (other.hasSeconds()) {
                        setSeconds(other.getSeconds());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasSeconds()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                RtTime parsedMessage = null;
                try {
                    RtTime parsedMessage2 = (RtTime) RtTime.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    RtTime parsedMessage3 = (RtTime) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasSeconds() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getSeconds() {
                return this.seconds_;
            }

            public Builder setSeconds(int value) {
                this.bitField0_ |= 1;
                this.seconds_ = value;
                onChanged();
                return this;
            }

            public Builder clearSeconds() {
                this.bitField0_ &= -2;
                this.seconds_ = 0;
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

        private RtTime(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private RtTime() {
            this.memoizedIsInitialized = -1;
            this.seconds_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RtTime(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.seconds_ = input.readFixed32();
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
            return RealtimeData.internal_static_RtTime_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return RealtimeData.internal_static_RtTime_fieldAccessorTable.ensureFieldAccessorsInitialized(RtTime.class, Builder.class);
        }

        public boolean hasSeconds() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getSeconds() {
            return this.seconds_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasSeconds()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.seconds_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.seconds_);
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
            if (!(obj instanceof RtTime)) {
                return super.equals(obj);
            }
            RtTime other = (RtTime) obj;
            boolean result2 = 1 != 0 && hasSeconds() == other.hasSeconds();
            if (hasSeconds()) {
                result2 = result2 && getSeconds() == other.getSeconds();
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
            if (hasSeconds()) {
                hash = (((hash * 37) + 1) * 53) + getSeconds();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static RtTime parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RtTime) PARSER.parseFrom(data);
        }

        public static RtTime parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtTime) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtTime parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RtTime) PARSER.parseFrom(data);
        }

        public static RtTime parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RtTime) PARSER.parseFrom(data, extensionRegistry);
        }

        public static RtTime parseFrom(InputStream input) throws IOException {
            return (RtTime) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtTime parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtTime) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtTime parseDelimitedFrom(InputStream input) throws IOException {
            return (RtTime) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static RtTime parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtTime) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RtTime parseFrom(CodedInputStream input) throws IOException {
            return (RtTime) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static RtTime parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RtTime) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RtTime prototype) {
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

        public static RtTime getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RtTime> parser() {
            return PARSER;
        }

        public Parser<RtTime> getParserForType() {
            return PARSER;
        }

        public RtTime getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    /* renamed from: com.iwown.ble_module.proto.base.RealtimeData$2 reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$iwown$ble_module$proto$base$RealtimeData$RtNotification$DataCase = new int[DataCase.values().length];

        static {
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$RealtimeData$RtNotification$DataCase[DataCase.RT_DATA.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$RealtimeData$RtNotification$DataCase[DataCase.RT_STATE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$iwown$ble_module$proto$base$RealtimeData$RtNotification$DataCase[DataCase.DATA_NOT_SET.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public interface DateTimeOrBuilder extends MessageOrBuilder {
        RtTime getDateTime();

        RtTimeOrBuilder getDateTimeOrBuilder();

        int getTimeZone();

        boolean hasDateTime();

        boolean hasTimeZone();
    }

    public interface RtBatteryOrBuilder extends MessageOrBuilder {
        boolean getCharging();

        int getLevel();

        boolean hasCharging();

        boolean hasLevel();
    }

    public interface RtDataOrBuilder extends MessageOrBuilder {
        RtBattery getBattery();

        RtBatteryOrBuilder getBatteryOrBuilder();

        RtHealth getHealth();

        RtHealthOrBuilder getHealthOrBuilder();

        RtKeyEvent getKey();

        RtTime getTime();

        RtTimeOrBuilder getTimeOrBuilder();

        boolean hasBattery();

        boolean hasHealth();

        boolean hasKey();

        boolean hasTime();
    }

    public interface RtGNSSOrBuilder extends MessageOrBuilder {
        float getAltitude();

        float getLatitude();

        float getLongitude();

        float getSpeed();

        boolean hasAltitude();

        boolean hasLatitude();

        boolean hasLongitude();

        boolean hasSpeed();
    }

    public interface RtHealthOrBuilder extends MessageOrBuilder {
        int getCalorie();

        int getDistance();

        int getSteps();

        boolean hasCalorie();

        boolean hasDistance();

        boolean hasSteps();
    }

    public interface RtNotificationOrBuilder extends MessageOrBuilder {
        DataCase getDataCase();

        RtData getRtData();

        RtDataOrBuilder getRtDataOrBuilder();

        RtState getRtState();

        RtStateOrBuilder getRtStateOrBuilder();

        boolean hasRtData();

        boolean hasRtState();
    }

    public interface RtStateOrBuilder extends MessageOrBuilder {
        RtSync getBattery();

        RtSync getHealth();

        RtMode getMode();

        RtSync getTime();

        boolean hasBattery();

        boolean hasHealth();

        boolean hasMode();

        boolean hasTime();
    }

    public interface RtSubscriberOrBuilder extends MessageOrBuilder {
        RtSync getBattery();

        RtSync getHealth();

        RtMode getMode();

        RtSync getTime();

        boolean hasBattery();

        boolean hasHealth();

        boolean hasMode();

        boolean hasTime();
    }

    public interface RtTimeOrBuilder extends MessageOrBuilder {
        int getSeconds();

        boolean hasSeconds();
    }

    private RealtimeData() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0013realtime_data.proto\"\u0019\n\u0006RtTime\u0012\u000f\n\u0007seconds\u0018\u0001 \u0002(\u0007\"9\n\bDateTime\u0012\u001a\n\tdate_time\u0018\u0001 \u0002(\u000b2\u0007.RtTime\u0012\u0011\n\ttime_zone\u0018\u0002 \u0002(\u000f\",\n\tRtBattery\u0012\r\n\u0005level\u0018\u0001 \u0002(\u0007\u0012\u0010\n\bcharging\u0018\u0002 \u0002(\b\"<\n\bRtHealth\u0012\r\n\u0005steps\u0018\u0001 \u0002(\u0007\u0012\u0010\n\bdistance\u0018\u0002 \u0002(\u0007\u0012\u000f\n\u0007calorie\u0018\u0003 \u0002(\u0007\"N\n\u0006RtGNSS\u0012\u0011\n\tlongitude\u0018\u0001 \u0002(\u0002\u0012\u0010\n\blatitude\u0018\u0002 \u0002(\u0002\u0012\r\n\u0005speed\u0018\u0003 \u0002(\u0002\u0012\u0010\n\baltitude\u0018\u0004 \u0002(\u0002\"o\n\fRtSubscriber\u0012\u0015\n\u0004time\u0018\u0001 \u0001(\u000e2\u0007.RtSync\u0012\u0018\n\u0007battery\u0018\u0002 \u0001(\u000e2\u0007.RtSync\u0012\u0017\n\u0006health\u0018\u0003 \u0001(\u000e2\u0007.RtSync\u0012\u0015\n\u0004mode\u0018\u0004 \u0001(\u000e2", "\u0007.RtMode\"q\n\u0006RtData\u0012\u0015\n\u0004time\u0018\u0001 \u0001(\u000b2\u0007.RtTime\u0012\u001b\n\u0007battery\u0018\u0002 \u0001(\u000b2\n.RtBattery\u0012\u0019\n\u0006health\u0018\u0003 \u0001(\u000b2\t.RtHealth\u0012\u0018\n\u0003key\u0018\u0004 \u0001(\u000e2\u000b.RtKeyEvent\"j\n\u0007RtState\u0012\u0015\n\u0004time\u0018\u0001 \u0001(\u000e2\u0007.RtSync\u0012\u0018\n\u0007battery\u0018\u0002 \u0001(\u000e2\u0007.RtSync\u0012\u0017\n\u0006health\u0018\u0003 \u0001(\u000e2\u0007.RtSync\u0012\u0015\n\u0004mode\u0018\u0004 \u0001(\u000e2\u0007.RtMode\"R\n\u000eRtNotification\u0012\u001a\n\u0007rt_data\u0018\u0001 \u0001(\u000b2\u0007.RtDataH\u0000\u0012\u001c\n\brt_state\u0018\u0002 \u0001(\u000b2\b.RtStateH\u0000B\u0006\n\u0004data*;\n\u0006RtMode\u0012\u0017\n\u0013RT_MODE_BACK_NORMAL\u0010\u0000\u0012\u0018\n\u0014RT_MODE_ENTER_CAMERA\u0010\u0001*\u001f\n\nRtKeyEvent\u0012\u0011\n\rRT_KEY", "_CAMERA\u0010\u0000*\u0001\n\u0006RtSync\u0012\f\n\bSTOP_ALL\u0010\u0000\u0012\r\n\tONLY_ONCE\u0010\u0001\u0012\u0013\n\u000fON_VALUE_CHANGE\u0010\u0002\u0012\u0014\n\u0010ON_SECOND_CHANGE\u0010\u0003\u0012\u0014\n\u0010ON_MINUTE_CHANGE\u0010\u0004\u0012\u0012\n\u000eON_HOUR_CHANGE\u0010\u0005\u0012\u0011\n\rON_DAY_CHANGE\u0010\u0006"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                RealtimeData.descriptor = root;
                return null;
            }
        });
    }
}
