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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DeviceConf {
    /* access modifiers changed from: private */
    public static FileDescriptor descriptor;
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_DeviceConfNotification_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_DeviceConfNotification_fieldAccessorTable = new FieldAccessorTable(internal_static_DeviceConfNotification_descriptor, new String[]{"Hash", "LanguageId", "LcdGsSwitch", "LcdGsTime", "DistanceUnit", "TemperatureUnit", "HourFormat", "DateFormat", "AutoHeartrate", "AutoSport", "HabitualHand", "NickName"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_DeviceConfSubscriber_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_DeviceConfSubscriber_fieldAccessorTable = new FieldAccessorTable(internal_static_DeviceConfSubscriber_descriptor, new String[]{"Hash", "SupportLanguageMask", "SupportLcdGestureSwitch", "SupportLcdGestureTime", "SupportDistanceUnit", "SupportTemperatureUnit", "SupportHourFormat", "SupportDateFormat", "SupportAutoHeartrate", "SupportAutoSport", "SupportHabitualHand", "SupportNickName"});
    /* access modifiers changed from: private */
    public static final Descriptor internal_static_DeviceLcdGs_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    /* access modifiers changed from: private */
    public static final FieldAccessorTable internal_static_DeviceLcdGs_fieldAccessorTable = new FieldAccessorTable(internal_static_DeviceLcdGs_descriptor, new String[]{"LcdGsStartHour", "LcdGsEndHour"});

    public static final class DeviceConfNotification extends GeneratedMessageV3 implements DeviceConfNotificationOrBuilder {
        public static final int AUTO_HEARTRATE_FIELD_NUMBER = 9;
        public static final int AUTO_SPORT_FIELD_NUMBER = 10;
        public static final int DATE_FORMAT_FIELD_NUMBER = 8;
        private static final DeviceConfNotification DEFAULT_INSTANCE = new DeviceConfNotification();
        public static final int DISTANCE_UNIT_FIELD_NUMBER = 5;
        public static final int HABITUAL_HAND_FIELD_NUMBER = 11;
        public static final int HASH_FIELD_NUMBER = 1;
        public static final int HOUR_FORMAT_FIELD_NUMBER = 7;
        public static final int LANGUAGE_ID_FIELD_NUMBER = 2;
        public static final int LCD_GS_SWITCH_FIELD_NUMBER = 3;
        public static final int LCD_GS_TIME_FIELD_NUMBER = 4;
        public static final int NICK_NAME_FIELD_NUMBER = 12;
        @Deprecated
        public static final Parser<DeviceConfNotification> PARSER = new AbstractParser<DeviceConfNotification>() {
            public DeviceConfNotification parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DeviceConfNotification(input, extensionRegistry);
            }
        };
        public static final int TEMPERATURE_UNIT_FIELD_NUMBER = 6;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public boolean autoHeartrate_;
        /* access modifiers changed from: private */
        public boolean autoSport_;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public boolean dateFormat_;
        /* access modifiers changed from: private */
        public boolean distanceUnit_;
        /* access modifiers changed from: private */
        public boolean habitualHand_;
        /* access modifiers changed from: private */
        public int hash_;
        /* access modifiers changed from: private */
        public boolean hourFormat_;
        /* access modifiers changed from: private */
        public int languageId_;
        /* access modifiers changed from: private */
        public boolean lcdGsSwitch_;
        /* access modifiers changed from: private */
        public DeviceLcdGs lcdGsTime_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public volatile Object nickName_;
        /* access modifiers changed from: private */
        public boolean temperatureUnit_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements DeviceConfNotificationOrBuilder {
            private boolean autoHeartrate_;
            private boolean autoSport_;
            private int bitField0_;
            private boolean dateFormat_;
            private boolean distanceUnit_;
            private boolean habitualHand_;
            private int hash_;
            private boolean hourFormat_;
            private int languageId_;
            private boolean lcdGsSwitch_;
            private SingleFieldBuilderV3<DeviceLcdGs, Builder, DeviceLcdGsOrBuilder> lcdGsTimeBuilder_;
            private DeviceLcdGs lcdGsTime_;
            private Object nickName_;
            private boolean temperatureUnit_;

            public static final Descriptor getDescriptor() {
                return DeviceConf.internal_static_DeviceConfNotification_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DeviceConf.internal_static_DeviceConfNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceConfNotification.class, Builder.class);
            }

            private Builder() {
                this.languageId_ = 0;
                this.lcdGsSwitch_ = true;
                this.lcdGsTime_ = null;
                this.nickName_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.languageId_ = 0;
                this.lcdGsSwitch_ = true;
                this.lcdGsTime_ = null;
                this.nickName_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (DeviceConfNotification.alwaysUseFieldBuilders) {
                    getLcdGsTimeFieldBuilder();
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.languageId_ = 0;
                this.bitField0_ &= -3;
                this.lcdGsSwitch_ = true;
                this.bitField0_ &= -5;
                if (this.lcdGsTimeBuilder_ == null) {
                    this.lcdGsTime_ = null;
                } else {
                    this.lcdGsTimeBuilder_.clear();
                }
                this.bitField0_ &= -9;
                this.distanceUnit_ = false;
                this.bitField0_ &= -17;
                this.temperatureUnit_ = false;
                this.bitField0_ &= -33;
                this.hourFormat_ = false;
                this.bitField0_ &= -65;
                this.dateFormat_ = false;
                this.bitField0_ &= -129;
                this.autoHeartrate_ = false;
                this.bitField0_ &= -257;
                this.autoSport_ = false;
                this.bitField0_ &= -513;
                this.habitualHand_ = false;
                this.bitField0_ &= -1025;
                this.nickName_ = "";
                this.bitField0_ &= -2049;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DeviceConf.internal_static_DeviceConfNotification_descriptor;
            }

            public DeviceConfNotification getDefaultInstanceForType() {
                return DeviceConfNotification.getDefaultInstance();
            }

            public DeviceConfNotification build() {
                DeviceConfNotification result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public DeviceConfNotification buildPartial() {
                DeviceConfNotification result = new DeviceConfNotification((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.languageId_ = this.languageId_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.lcdGsSwitch_ = this.lcdGsSwitch_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                if (this.lcdGsTimeBuilder_ == null) {
                    result.lcdGsTime_ = this.lcdGsTime_;
                } else {
                    result.lcdGsTime_ = (DeviceLcdGs) this.lcdGsTimeBuilder_.build();
                }
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.distanceUnit_ = this.distanceUnit_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.temperatureUnit_ = this.temperatureUnit_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.hourFormat_ = this.hourFormat_;
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                result.dateFormat_ = this.dateFormat_;
                if ((from_bitField0_ & 256) == 256) {
                    to_bitField0_ |= 256;
                }
                result.autoHeartrate_ = this.autoHeartrate_;
                if ((from_bitField0_ & 512) == 512) {
                    to_bitField0_ |= 512;
                }
                result.autoSport_ = this.autoSport_;
                if ((from_bitField0_ & 1024) == 1024) {
                    to_bitField0_ |= 1024;
                }
                result.habitualHand_ = this.habitualHand_;
                if ((from_bitField0_ & 2048) == 2048) {
                    to_bitField0_ |= 2048;
                }
                result.nickName_ = this.nickName_;
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
                if (other instanceof DeviceConfNotification) {
                    return mergeFrom((DeviceConfNotification) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeviceConfNotification other) {
                if (other != DeviceConfNotification.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (other.hasLanguageId()) {
                        setLanguageId(other.getLanguageId());
                    }
                    if (other.hasLcdGsSwitch()) {
                        setLcdGsSwitch(other.getLcdGsSwitch());
                    }
                    if (other.hasLcdGsTime()) {
                        mergeLcdGsTime(other.getLcdGsTime());
                    }
                    if (other.hasDistanceUnit()) {
                        setDistanceUnit(other.getDistanceUnit());
                    }
                    if (other.hasTemperatureUnit()) {
                        setTemperatureUnit(other.getTemperatureUnit());
                    }
                    if (other.hasHourFormat()) {
                        setHourFormat(other.getHourFormat());
                    }
                    if (other.hasDateFormat()) {
                        setDateFormat(other.getDateFormat());
                    }
                    if (other.hasAutoHeartrate()) {
                        setAutoHeartrate(other.getAutoHeartrate());
                    }
                    if (other.hasAutoSport()) {
                        setAutoSport(other.getAutoSport());
                    }
                    if (other.hasHabitualHand()) {
                        setHabitualHand(other.getHabitualHand());
                    }
                    if (other.hasNickName()) {
                        this.bitField0_ |= 2048;
                        this.nickName_ = other.nickName_;
                        onChanged();
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasHash()) {
                    return false;
                }
                if (!hasLcdGsTime() || getLcdGsTime().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                DeviceConfNotification parsedMessage = null;
                try {
                    DeviceConfNotification parsedMessage2 = (DeviceConfNotification) DeviceConfNotification.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    DeviceConfNotification parsedMessage3 = (DeviceConfNotification) e.getUnfinishedMessage();
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

            public boolean hasLanguageId() {
                return (this.bitField0_ & 2) == 2;
            }

            public DeviceLanuage getLanguageId() {
                DeviceLanuage result = DeviceLanuage.valueOf(this.languageId_);
                return result == null ? DeviceLanuage.English : result;
            }

            public Builder setLanguageId(DeviceLanuage value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.languageId_ = value.getNumber();
                onChanged();
                return this;
            }

            public Builder clearLanguageId() {
                this.bitField0_ &= -3;
                this.languageId_ = 0;
                onChanged();
                return this;
            }

            public boolean hasLcdGsSwitch() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getLcdGsSwitch() {
                return this.lcdGsSwitch_;
            }

            public Builder setLcdGsSwitch(boolean value) {
                this.bitField0_ |= 4;
                this.lcdGsSwitch_ = value;
                onChanged();
                return this;
            }

            public Builder clearLcdGsSwitch() {
                this.bitField0_ &= -5;
                this.lcdGsSwitch_ = true;
                onChanged();
                return this;
            }

            public boolean hasLcdGsTime() {
                return (this.bitField0_ & 8) == 8;
            }

            public DeviceLcdGs getLcdGsTime() {
                if (this.lcdGsTimeBuilder_ == null) {
                    return this.lcdGsTime_ == null ? DeviceLcdGs.getDefaultInstance() : this.lcdGsTime_;
                }
                return (DeviceLcdGs) this.lcdGsTimeBuilder_.getMessage();
            }

            public Builder setLcdGsTime(DeviceLcdGs value) {
                if (this.lcdGsTimeBuilder_ != null) {
                    this.lcdGsTimeBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.lcdGsTime_ = value;
                    onChanged();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setLcdGsTime(Builder builderForValue) {
                if (this.lcdGsTimeBuilder_ == null) {
                    this.lcdGsTime_ = builderForValue.build();
                    onChanged();
                } else {
                    this.lcdGsTimeBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeLcdGsTime(DeviceLcdGs value) {
                if (this.lcdGsTimeBuilder_ == null) {
                    if ((this.bitField0_ & 8) != 8 || this.lcdGsTime_ == null || this.lcdGsTime_ == DeviceLcdGs.getDefaultInstance()) {
                        this.lcdGsTime_ = value;
                    } else {
                        this.lcdGsTime_ = DeviceLcdGs.newBuilder(this.lcdGsTime_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.lcdGsTimeBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearLcdGsTime() {
                if (this.lcdGsTimeBuilder_ == null) {
                    this.lcdGsTime_ = null;
                    onChanged();
                } else {
                    this.lcdGsTimeBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public Builder getLcdGsTimeBuilder() {
                this.bitField0_ |= 8;
                onChanged();
                return (Builder) getLcdGsTimeFieldBuilder().getBuilder();
            }

            public DeviceLcdGsOrBuilder getLcdGsTimeOrBuilder() {
                if (this.lcdGsTimeBuilder_ != null) {
                    return (DeviceLcdGsOrBuilder) this.lcdGsTimeBuilder_.getMessageOrBuilder();
                }
                return this.lcdGsTime_ == null ? DeviceLcdGs.getDefaultInstance() : this.lcdGsTime_;
            }

            private SingleFieldBuilderV3<DeviceLcdGs, Builder, DeviceLcdGsOrBuilder> getLcdGsTimeFieldBuilder() {
                if (this.lcdGsTimeBuilder_ == null) {
                    this.lcdGsTimeBuilder_ = new SingleFieldBuilderV3<>(getLcdGsTime(), getParentForChildren(), isClean());
                    this.lcdGsTime_ = null;
                }
                return this.lcdGsTimeBuilder_;
            }

            public boolean hasDistanceUnit() {
                return (this.bitField0_ & 16) == 16;
            }

            public boolean getDistanceUnit() {
                return this.distanceUnit_;
            }

            public Builder setDistanceUnit(boolean value) {
                this.bitField0_ |= 16;
                this.distanceUnit_ = value;
                onChanged();
                return this;
            }

            public Builder clearDistanceUnit() {
                this.bitField0_ &= -17;
                this.distanceUnit_ = false;
                onChanged();
                return this;
            }

            public boolean hasTemperatureUnit() {
                return (this.bitField0_ & 32) == 32;
            }

            public boolean getTemperatureUnit() {
                return this.temperatureUnit_;
            }

            public Builder setTemperatureUnit(boolean value) {
                this.bitField0_ |= 32;
                this.temperatureUnit_ = value;
                onChanged();
                return this;
            }

            public Builder clearTemperatureUnit() {
                this.bitField0_ &= -33;
                this.temperatureUnit_ = false;
                onChanged();
                return this;
            }

            public boolean hasHourFormat() {
                return (this.bitField0_ & 64) == 64;
            }

            public boolean getHourFormat() {
                return this.hourFormat_;
            }

            public Builder setHourFormat(boolean value) {
                this.bitField0_ |= 64;
                this.hourFormat_ = value;
                onChanged();
                return this;
            }

            public Builder clearHourFormat() {
                this.bitField0_ &= -65;
                this.hourFormat_ = false;
                onChanged();
                return this;
            }

            public boolean hasDateFormat() {
                return (this.bitField0_ & 128) == 128;
            }

            public boolean getDateFormat() {
                return this.dateFormat_;
            }

            public Builder setDateFormat(boolean value) {
                this.bitField0_ |= 128;
                this.dateFormat_ = value;
                onChanged();
                return this;
            }

            public Builder clearDateFormat() {
                this.bitField0_ &= -129;
                this.dateFormat_ = false;
                onChanged();
                return this;
            }

            public boolean hasAutoHeartrate() {
                return (this.bitField0_ & 256) == 256;
            }

            public boolean getAutoHeartrate() {
                return this.autoHeartrate_;
            }

            public Builder setAutoHeartrate(boolean value) {
                this.bitField0_ |= 256;
                this.autoHeartrate_ = value;
                onChanged();
                return this;
            }

            public Builder clearAutoHeartrate() {
                this.bitField0_ &= -257;
                this.autoHeartrate_ = false;
                onChanged();
                return this;
            }

            public boolean hasAutoSport() {
                return (this.bitField0_ & 512) == 512;
            }

            public boolean getAutoSport() {
                return this.autoSport_;
            }

            public Builder setAutoSport(boolean value) {
                this.bitField0_ |= 512;
                this.autoSport_ = value;
                onChanged();
                return this;
            }

            public Builder clearAutoSport() {
                this.bitField0_ &= -513;
                this.autoSport_ = false;
                onChanged();
                return this;
            }

            public boolean hasHabitualHand() {
                return (this.bitField0_ & 1024) == 1024;
            }

            public boolean getHabitualHand() {
                return this.habitualHand_;
            }

            public Builder setHabitualHand(boolean value) {
                this.bitField0_ |= 1024;
                this.habitualHand_ = value;
                onChanged();
                return this;
            }

            public Builder clearHabitualHand() {
                this.bitField0_ &= -1025;
                this.habitualHand_ = false;
                onChanged();
                return this;
            }

            public boolean hasNickName() {
                return (this.bitField0_ & 2048) == 2048;
            }

            public String getNickName() {
                Object ref = this.nickName_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.nickName_ = s;
                return s;
            }

            public ByteString getNickNameBytes() {
                Object ref = this.nickName_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.nickName_ = b;
                return b;
            }

            public Builder setNickName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2048;
                this.nickName_ = value;
                onChanged();
                return this;
            }

            public Builder clearNickName() {
                this.bitField0_ &= -2049;
                this.nickName_ = DeviceConfNotification.getDefaultInstance().getNickName();
                onChanged();
                return this;
            }

            public Builder setNickNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2048;
                this.nickName_ = value;
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

        private DeviceConfNotification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private DeviceConfNotification() {
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.languageId_ = 0;
            this.lcdGsSwitch_ = true;
            this.distanceUnit_ = false;
            this.temperatureUnit_ = false;
            this.hourFormat_ = false;
            this.dateFormat_ = false;
            this.autoHeartrate_ = false;
            this.autoSport_ = false;
            this.habitualHand_ = false;
            this.nickName_ = "";
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DeviceConfNotification(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            int rawValue = input.readEnum();
                            if (DeviceLanuage.valueOf(rawValue) != null) {
                                this.bitField0_ |= 2;
                                this.languageId_ = rawValue;
                                break;
                            } else {
                                unknownFields.mergeVarintField(2, rawValue);
                                break;
                            }
                        case 24:
                            this.bitField0_ |= 4;
                            this.lcdGsSwitch_ = input.readBool();
                            break;
                        case 34:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder = this.lcdGsTime_.toBuilder();
                            }
                            this.lcdGsTime_ = (DeviceLcdGs) input.readMessage(DeviceLcdGs.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.lcdGsTime_);
                                this.lcdGsTime_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            break;
                        case 40:
                            this.bitField0_ |= 16;
                            this.distanceUnit_ = input.readBool();
                            break;
                        case 48:
                            this.bitField0_ |= 32;
                            this.temperatureUnit_ = input.readBool();
                            break;
                        case 56:
                            this.bitField0_ |= 64;
                            this.hourFormat_ = input.readBool();
                            break;
                        case 64:
                            this.bitField0_ |= 128;
                            this.dateFormat_ = input.readBool();
                            break;
                        case 72:
                            this.bitField0_ |= 256;
                            this.autoHeartrate_ = input.readBool();
                            break;
                        case 80:
                            this.bitField0_ |= 512;
                            this.autoSport_ = input.readBool();
                            break;
                        case 88:
                            this.bitField0_ |= 1024;
                            this.habitualHand_ = input.readBool();
                            break;
                        case 98:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 2048;
                            this.nickName_ = bs;
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
            return DeviceConf.internal_static_DeviceConfNotification_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DeviceConf.internal_static_DeviceConfNotification_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceConfNotification.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public boolean hasLanguageId() {
            return (this.bitField0_ & 2) == 2;
        }

        public DeviceLanuage getLanguageId() {
            DeviceLanuage result = DeviceLanuage.valueOf(this.languageId_);
            return result == null ? DeviceLanuage.English : result;
        }

        public boolean hasLcdGsSwitch() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getLcdGsSwitch() {
            return this.lcdGsSwitch_;
        }

        public boolean hasLcdGsTime() {
            return (this.bitField0_ & 8) == 8;
        }

        public DeviceLcdGs getLcdGsTime() {
            return this.lcdGsTime_ == null ? DeviceLcdGs.getDefaultInstance() : this.lcdGsTime_;
        }

        public DeviceLcdGsOrBuilder getLcdGsTimeOrBuilder() {
            return this.lcdGsTime_ == null ? DeviceLcdGs.getDefaultInstance() : this.lcdGsTime_;
        }

        public boolean hasDistanceUnit() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean getDistanceUnit() {
            return this.distanceUnit_;
        }

        public boolean hasTemperatureUnit() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean getTemperatureUnit() {
            return this.temperatureUnit_;
        }

        public boolean hasHourFormat() {
            return (this.bitField0_ & 64) == 64;
        }

        public boolean getHourFormat() {
            return this.hourFormat_;
        }

        public boolean hasDateFormat() {
            return (this.bitField0_ & 128) == 128;
        }

        public boolean getDateFormat() {
            return this.dateFormat_;
        }

        public boolean hasAutoHeartrate() {
            return (this.bitField0_ & 256) == 256;
        }

        public boolean getAutoHeartrate() {
            return this.autoHeartrate_;
        }

        public boolean hasAutoSport() {
            return (this.bitField0_ & 512) == 512;
        }

        public boolean getAutoSport() {
            return this.autoSport_;
        }

        public boolean hasHabitualHand() {
            return (this.bitField0_ & 1024) == 1024;
        }

        public boolean getHabitualHand() {
            return this.habitualHand_;
        }

        public boolean hasNickName() {
            return (this.bitField0_ & 2048) == 2048;
        }

        public String getNickName() {
            Object ref = this.nickName_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.nickName_ = s;
            }
            return s;
        }

        public ByteString getNickNameBytes() {
            Object ref = this.nickName_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.nickName_ = b;
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
            if (!hasHash()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasLcdGsTime() || getLcdGsTime().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            } else {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.hash_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeEnum(2, this.languageId_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(3, this.lcdGsSwitch_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(4, getLcdGsTime());
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBool(5, this.distanceUnit_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeBool(6, this.temperatureUnit_);
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeBool(7, this.hourFormat_);
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeBool(8, this.dateFormat_);
            }
            if ((this.bitField0_ & 256) == 256) {
                output.writeBool(9, this.autoHeartrate_);
            }
            if ((this.bitField0_ & 512) == 512) {
                output.writeBool(10, this.autoSport_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                output.writeBool(11, this.habitualHand_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                GeneratedMessageV3.writeString(output, 12, this.nickName_);
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
                size2 += CodedOutputStream.computeEnumSize(2, this.languageId_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeBoolSize(3, this.lcdGsSwitch_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeMessageSize(4, getLcdGsTime());
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeBoolSize(5, this.distanceUnit_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += CodedOutputStream.computeBoolSize(6, this.temperatureUnit_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size2 += CodedOutputStream.computeBoolSize(7, this.hourFormat_);
            }
            if ((this.bitField0_ & 128) == 128) {
                size2 += CodedOutputStream.computeBoolSize(8, this.dateFormat_);
            }
            if ((this.bitField0_ & 256) == 256) {
                size2 += CodedOutputStream.computeBoolSize(9, this.autoHeartrate_);
            }
            if ((this.bitField0_ & 512) == 512) {
                size2 += CodedOutputStream.computeBoolSize(10, this.autoSport_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                size2 += CodedOutputStream.computeBoolSize(11, this.habitualHand_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                size2 += GeneratedMessageV3.computeStringSize(12, this.nickName_);
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
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DeviceConfNotification)) {
                return super.equals(obj);
            }
            DeviceConfNotification other = (DeviceConfNotification) obj;
            boolean result13 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result13 = result13 && getHash() == other.getHash();
            }
            if (!result13 || hasLanguageId() != other.hasLanguageId()) {
                result = false;
            } else {
                result = true;
            }
            if (hasLanguageId()) {
                if (!result || this.languageId_ != other.languageId_) {
                    result = false;
                } else {
                    result = true;
                }
            }
            if (!result || hasLcdGsSwitch() != other.hasLcdGsSwitch()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasLcdGsSwitch()) {
                if (!result2 || getLcdGsSwitch() != other.getLcdGsSwitch()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasLcdGsTime() != other.hasLcdGsTime()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasLcdGsTime()) {
                if (!result3 || !getLcdGsTime().equals(other.getLcdGsTime())) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasDistanceUnit() != other.hasDistanceUnit()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasDistanceUnit()) {
                if (!result4 || getDistanceUnit() != other.getDistanceUnit()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasTemperatureUnit() != other.hasTemperatureUnit()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasTemperatureUnit()) {
                if (!result5 || getTemperatureUnit() != other.getTemperatureUnit()) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || hasHourFormat() != other.hasHourFormat()) {
                result6 = false;
            } else {
                result6 = true;
            }
            if (hasHourFormat()) {
                if (!result6 || getHourFormat() != other.getHourFormat()) {
                    result6 = false;
                } else {
                    result6 = true;
                }
            }
            if (!result6 || hasDateFormat() != other.hasDateFormat()) {
                result7 = false;
            } else {
                result7 = true;
            }
            if (hasDateFormat()) {
                if (!result7 || getDateFormat() != other.getDateFormat()) {
                    result7 = false;
                } else {
                    result7 = true;
                }
            }
            if (!result7 || hasAutoHeartrate() != other.hasAutoHeartrate()) {
                result8 = false;
            } else {
                result8 = true;
            }
            if (hasAutoHeartrate()) {
                if (!result8 || getAutoHeartrate() != other.getAutoHeartrate()) {
                    result8 = false;
                } else {
                    result8 = true;
                }
            }
            if (!result8 || hasAutoSport() != other.hasAutoSport()) {
                result9 = false;
            } else {
                result9 = true;
            }
            if (hasAutoSport()) {
                if (!result9 || getAutoSport() != other.getAutoSport()) {
                    result9 = false;
                } else {
                    result9 = true;
                }
            }
            if (!result9 || hasHabitualHand() != other.hasHabitualHand()) {
                result10 = false;
            } else {
                result10 = true;
            }
            if (hasHabitualHand()) {
                if (!result10 || getHabitualHand() != other.getHabitualHand()) {
                    result10 = false;
                } else {
                    result10 = true;
                }
            }
            if (!result10 || hasNickName() != other.hasNickName()) {
                result11 = false;
            } else {
                result11 = true;
            }
            if (hasNickName()) {
                if (!result11 || !getNickName().equals(other.getNickName())) {
                    result11 = false;
                } else {
                    result11 = true;
                }
            }
            if (!result11 || !this.unknownFields.equals(other.unknownFields)) {
                result12 = false;
            } else {
                result12 = true;
            }
            return result12;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            if (hasLanguageId()) {
                hash = (((hash * 37) + 2) * 53) + this.languageId_;
            }
            if (hasLcdGsSwitch()) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashBoolean(getLcdGsSwitch());
            }
            if (hasLcdGsTime()) {
                hash = (((hash * 37) + 4) * 53) + getLcdGsTime().hashCode();
            }
            if (hasDistanceUnit()) {
                hash = (((hash * 37) + 5) * 53) + Internal.hashBoolean(getDistanceUnit());
            }
            if (hasTemperatureUnit()) {
                hash = (((hash * 37) + 6) * 53) + Internal.hashBoolean(getTemperatureUnit());
            }
            if (hasHourFormat()) {
                hash = (((hash * 37) + 7) * 53) + Internal.hashBoolean(getHourFormat());
            }
            if (hasDateFormat()) {
                hash = (((hash * 37) + 8) * 53) + Internal.hashBoolean(getDateFormat());
            }
            if (hasAutoHeartrate()) {
                hash = (((hash * 37) + 9) * 53) + Internal.hashBoolean(getAutoHeartrate());
            }
            if (hasAutoSport()) {
                hash = (((hash * 37) + 10) * 53) + Internal.hashBoolean(getAutoSport());
            }
            if (hasHabitualHand()) {
                hash = (((hash * 37) + 11) * 53) + Internal.hashBoolean(getHabitualHand());
            }
            if (hasNickName()) {
                hash = (((hash * 37) + 12) * 53) + getNickName().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static DeviceConfNotification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceConfNotification) PARSER.parseFrom(data);
        }

        public static DeviceConfNotification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceConfNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceConfNotification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceConfNotification) PARSER.parseFrom(data);
        }

        public static DeviceConfNotification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceConfNotification) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceConfNotification parseFrom(InputStream input) throws IOException {
            return (DeviceConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceConfNotification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceConfNotification parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceConfNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeviceConfNotification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceConfNotification) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceConfNotification parseFrom(CodedInputStream input) throws IOException {
            return (DeviceConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceConfNotification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceConfNotification) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeviceConfNotification prototype) {
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

        public static DeviceConfNotification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceConfNotification> parser() {
            return PARSER;
        }

        public Parser<DeviceConfNotification> getParserForType() {
            return PARSER;
        }

        public DeviceConfNotification getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static final class DeviceConfSubscriber extends GeneratedMessageV3 implements DeviceConfSubscriberOrBuilder {
        private static final DeviceConfSubscriber DEFAULT_INSTANCE = new DeviceConfSubscriber();
        public static final int HASH_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<DeviceConfSubscriber> PARSER = new AbstractParser<DeviceConfSubscriber>() {
            public DeviceConfSubscriber parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DeviceConfSubscriber(input, extensionRegistry);
            }
        };
        public static final int SUPPORT_AUTO_HEARTRATE_FIELD_NUMBER = 9;
        public static final int SUPPORT_AUTO_SPORT_FIELD_NUMBER = 10;
        public static final int SUPPORT_DATE_FORMAT_FIELD_NUMBER = 8;
        public static final int SUPPORT_DISTANCE_UNIT_FIELD_NUMBER = 5;
        public static final int SUPPORT_HABITUAL_HAND_FIELD_NUMBER = 11;
        public static final int SUPPORT_HOUR_FORMAT_FIELD_NUMBER = 7;
        public static final int SUPPORT_LANGUAGE_MASK_FIELD_NUMBER = 2;
        public static final int SUPPORT_LCD_GESTURE_SWITCH_FIELD_NUMBER = 3;
        public static final int SUPPORT_LCD_GESTURE_TIME_FIELD_NUMBER = 4;
        public static final int SUPPORT_NICK_NAME_FIELD_NUMBER = 12;
        public static final int SUPPORT_TEMPERATURE_UNIT_FIELD_NUMBER = 6;
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int hash_;
        private byte memoizedIsInitialized;
        /* access modifiers changed from: private */
        public boolean supportAutoHeartrate_;
        /* access modifiers changed from: private */
        public boolean supportAutoSport_;
        /* access modifiers changed from: private */
        public boolean supportDateFormat_;
        /* access modifiers changed from: private */
        public boolean supportDistanceUnit_;
        /* access modifiers changed from: private */
        public boolean supportHabitualHand_;
        /* access modifiers changed from: private */
        public boolean supportHourFormat_;
        private int supportLanguageMaskMemoizedSerializedSize;
        /* access modifiers changed from: private */
        public List<Integer> supportLanguageMask_;
        /* access modifiers changed from: private */
        public boolean supportLcdGestureSwitch_;
        /* access modifiers changed from: private */
        public boolean supportLcdGestureTime_;
        /* access modifiers changed from: private */
        public boolean supportNickName_;
        /* access modifiers changed from: private */
        public boolean supportTemperatureUnit_;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements DeviceConfSubscriberOrBuilder {
            private int bitField0_;
            private int hash_;
            private boolean supportAutoHeartrate_;
            private boolean supportAutoSport_;
            private boolean supportDateFormat_;
            private boolean supportDistanceUnit_;
            private boolean supportHabitualHand_;
            private boolean supportHourFormat_;
            private List<Integer> supportLanguageMask_;
            private boolean supportLcdGestureSwitch_;
            private boolean supportLcdGestureTime_;
            private boolean supportNickName_;
            private boolean supportTemperatureUnit_;

            public static final Descriptor getDescriptor() {
                return DeviceConf.internal_static_DeviceConfSubscriber_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DeviceConf.internal_static_DeviceConfSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceConfSubscriber.class, Builder.class);
            }

            private Builder() {
                this.supportLanguageMask_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.supportLanguageMask_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (DeviceConfSubscriber.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.hash_ = 0;
                this.bitField0_ &= -2;
                this.supportLanguageMask_ = Collections.emptyList();
                this.bitField0_ &= -3;
                this.supportLcdGestureSwitch_ = false;
                this.bitField0_ &= -5;
                this.supportLcdGestureTime_ = false;
                this.bitField0_ &= -9;
                this.supportDistanceUnit_ = false;
                this.bitField0_ &= -17;
                this.supportTemperatureUnit_ = false;
                this.bitField0_ &= -33;
                this.supportHourFormat_ = false;
                this.bitField0_ &= -65;
                this.supportDateFormat_ = false;
                this.bitField0_ &= -129;
                this.supportAutoHeartrate_ = false;
                this.bitField0_ &= -257;
                this.supportAutoSport_ = false;
                this.bitField0_ &= -513;
                this.supportHabitualHand_ = false;
                this.bitField0_ &= -1025;
                this.supportNickName_ = false;
                this.bitField0_ &= -2049;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DeviceConf.internal_static_DeviceConfSubscriber_descriptor;
            }

            public DeviceConfSubscriber getDefaultInstanceForType() {
                return DeviceConfSubscriber.getDefaultInstance();
            }

            public DeviceConfSubscriber build() {
                DeviceConfSubscriber result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public DeviceConfSubscriber buildPartial() {
                DeviceConfSubscriber result = new DeviceConfSubscriber((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.hash_ = this.hash_;
                if ((this.bitField0_ & 2) == 2) {
                    this.supportLanguageMask_ = Collections.unmodifiableList(this.supportLanguageMask_);
                    this.bitField0_ &= -3;
                }
                result.supportLanguageMask_ = this.supportLanguageMask_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 2;
                }
                result.supportLcdGestureSwitch_ = this.supportLcdGestureSwitch_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 4;
                }
                result.supportLcdGestureTime_ = this.supportLcdGestureTime_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 8;
                }
                result.supportDistanceUnit_ = this.supportDistanceUnit_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 16;
                }
                result.supportTemperatureUnit_ = this.supportTemperatureUnit_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 32;
                }
                result.supportHourFormat_ = this.supportHourFormat_;
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 64;
                }
                result.supportDateFormat_ = this.supportDateFormat_;
                if ((from_bitField0_ & 256) == 256) {
                    to_bitField0_ |= 128;
                }
                result.supportAutoHeartrate_ = this.supportAutoHeartrate_;
                if ((from_bitField0_ & 512) == 512) {
                    to_bitField0_ |= 256;
                }
                result.supportAutoSport_ = this.supportAutoSport_;
                if ((from_bitField0_ & 1024) == 1024) {
                    to_bitField0_ |= 512;
                }
                result.supportHabitualHand_ = this.supportHabitualHand_;
                if ((from_bitField0_ & 2048) == 2048) {
                    to_bitField0_ |= 1024;
                }
                result.supportNickName_ = this.supportNickName_;
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
                if (other instanceof DeviceConfSubscriber) {
                    return mergeFrom((DeviceConfSubscriber) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeviceConfSubscriber other) {
                if (other != DeviceConfSubscriber.getDefaultInstance()) {
                    if (other.hasHash()) {
                        setHash(other.getHash());
                    }
                    if (!other.supportLanguageMask_.isEmpty()) {
                        if (this.supportLanguageMask_.isEmpty()) {
                            this.supportLanguageMask_ = other.supportLanguageMask_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureSupportLanguageMaskIsMutable();
                            this.supportLanguageMask_.addAll(other.supportLanguageMask_);
                        }
                        onChanged();
                    }
                    if (other.hasSupportLcdGestureSwitch()) {
                        setSupportLcdGestureSwitch(other.getSupportLcdGestureSwitch());
                    }
                    if (other.hasSupportLcdGestureTime()) {
                        setSupportLcdGestureTime(other.getSupportLcdGestureTime());
                    }
                    if (other.hasSupportDistanceUnit()) {
                        setSupportDistanceUnit(other.getSupportDistanceUnit());
                    }
                    if (other.hasSupportTemperatureUnit()) {
                        setSupportTemperatureUnit(other.getSupportTemperatureUnit());
                    }
                    if (other.hasSupportHourFormat()) {
                        setSupportHourFormat(other.getSupportHourFormat());
                    }
                    if (other.hasSupportDateFormat()) {
                        setSupportDateFormat(other.getSupportDateFormat());
                    }
                    if (other.hasSupportAutoHeartrate()) {
                        setSupportAutoHeartrate(other.getSupportAutoHeartrate());
                    }
                    if (other.hasSupportAutoSport()) {
                        setSupportAutoSport(other.getSupportAutoSport());
                    }
                    if (other.hasSupportHabitualHand()) {
                        setSupportHabitualHand(other.getSupportHabitualHand());
                    }
                    if (other.hasSupportNickName()) {
                        setSupportNickName(other.getSupportNickName());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasHash() && hasSupportLcdGestureSwitch() && hasSupportLcdGestureTime() && hasSupportDistanceUnit() && hasSupportTemperatureUnit() && hasSupportHourFormat() && hasSupportDateFormat() && hasSupportAutoHeartrate() && hasSupportAutoSport() && hasSupportHabitualHand() && hasSupportNickName()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                DeviceConfSubscriber parsedMessage = null;
                try {
                    DeviceConfSubscriber parsedMessage2 = (DeviceConfSubscriber) DeviceConfSubscriber.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    DeviceConfSubscriber parsedMessage3 = (DeviceConfSubscriber) e.getUnfinishedMessage();
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

            private void ensureSupportLanguageMaskIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.supportLanguageMask_ = new ArrayList(this.supportLanguageMask_);
                    this.bitField0_ |= 2;
                }
            }

            public List<Integer> getSupportLanguageMaskList() {
                return Collections.unmodifiableList(this.supportLanguageMask_);
            }

            public int getSupportLanguageMaskCount() {
                return this.supportLanguageMask_.size();
            }

            public int getSupportLanguageMask(int index) {
                return ((Integer) this.supportLanguageMask_.get(index)).intValue();
            }

            public Builder setSupportLanguageMask(int index, int value) {
                ensureSupportLanguageMaskIsMutable();
                this.supportLanguageMask_.set(index, Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addSupportLanguageMask(int value) {
                ensureSupportLanguageMaskIsMutable();
                this.supportLanguageMask_.add(Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addAllSupportLanguageMask(Iterable<? extends Integer> values) {
                ensureSupportLanguageMaskIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.supportLanguageMask_);
                onChanged();
                return this;
            }

            public Builder clearSupportLanguageMask() {
                this.supportLanguageMask_ = Collections.emptyList();
                this.bitField0_ &= -3;
                onChanged();
                return this;
            }

            public boolean hasSupportLcdGestureSwitch() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getSupportLcdGestureSwitch() {
                return this.supportLcdGestureSwitch_;
            }

            public Builder setSupportLcdGestureSwitch(boolean value) {
                this.bitField0_ |= 4;
                this.supportLcdGestureSwitch_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportLcdGestureSwitch() {
                this.bitField0_ &= -5;
                this.supportLcdGestureSwitch_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportLcdGestureTime() {
                return (this.bitField0_ & 8) == 8;
            }

            public boolean getSupportLcdGestureTime() {
                return this.supportLcdGestureTime_;
            }

            public Builder setSupportLcdGestureTime(boolean value) {
                this.bitField0_ |= 8;
                this.supportLcdGestureTime_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportLcdGestureTime() {
                this.bitField0_ &= -9;
                this.supportLcdGestureTime_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportDistanceUnit() {
                return (this.bitField0_ & 16) == 16;
            }

            public boolean getSupportDistanceUnit() {
                return this.supportDistanceUnit_;
            }

            public Builder setSupportDistanceUnit(boolean value) {
                this.bitField0_ |= 16;
                this.supportDistanceUnit_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportDistanceUnit() {
                this.bitField0_ &= -17;
                this.supportDistanceUnit_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportTemperatureUnit() {
                return (this.bitField0_ & 32) == 32;
            }

            public boolean getSupportTemperatureUnit() {
                return this.supportTemperatureUnit_;
            }

            public Builder setSupportTemperatureUnit(boolean value) {
                this.bitField0_ |= 32;
                this.supportTemperatureUnit_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportTemperatureUnit() {
                this.bitField0_ &= -33;
                this.supportTemperatureUnit_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportHourFormat() {
                return (this.bitField0_ & 64) == 64;
            }

            public boolean getSupportHourFormat() {
                return this.supportHourFormat_;
            }

            public Builder setSupportHourFormat(boolean value) {
                this.bitField0_ |= 64;
                this.supportHourFormat_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportHourFormat() {
                this.bitField0_ &= -65;
                this.supportHourFormat_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportDateFormat() {
                return (this.bitField0_ & 128) == 128;
            }

            public boolean getSupportDateFormat() {
                return this.supportDateFormat_;
            }

            public Builder setSupportDateFormat(boolean value) {
                this.bitField0_ |= 128;
                this.supportDateFormat_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportDateFormat() {
                this.bitField0_ &= -129;
                this.supportDateFormat_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportAutoHeartrate() {
                return (this.bitField0_ & 256) == 256;
            }

            public boolean getSupportAutoHeartrate() {
                return this.supportAutoHeartrate_;
            }

            public Builder setSupportAutoHeartrate(boolean value) {
                this.bitField0_ |= 256;
                this.supportAutoHeartrate_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportAutoHeartrate() {
                this.bitField0_ &= -257;
                this.supportAutoHeartrate_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportAutoSport() {
                return (this.bitField0_ & 512) == 512;
            }

            public boolean getSupportAutoSport() {
                return this.supportAutoSport_;
            }

            public Builder setSupportAutoSport(boolean value) {
                this.bitField0_ |= 512;
                this.supportAutoSport_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportAutoSport() {
                this.bitField0_ &= -513;
                this.supportAutoSport_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportHabitualHand() {
                return (this.bitField0_ & 1024) == 1024;
            }

            public boolean getSupportHabitualHand() {
                return this.supportHabitualHand_;
            }

            public Builder setSupportHabitualHand(boolean value) {
                this.bitField0_ |= 1024;
                this.supportHabitualHand_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportHabitualHand() {
                this.bitField0_ &= -1025;
                this.supportHabitualHand_ = false;
                onChanged();
                return this;
            }

            public boolean hasSupportNickName() {
                return (this.bitField0_ & 2048) == 2048;
            }

            public boolean getSupportNickName() {
                return this.supportNickName_;
            }

            public Builder setSupportNickName(boolean value) {
                this.bitField0_ |= 2048;
                this.supportNickName_ = value;
                onChanged();
                return this;
            }

            public Builder clearSupportNickName() {
                this.bitField0_ &= -2049;
                this.supportNickName_ = false;
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

        private DeviceConfSubscriber(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.supportLanguageMaskMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = -1;
        }

        private DeviceConfSubscriber() {
            this.supportLanguageMaskMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = -1;
            this.hash_ = 0;
            this.supportLanguageMask_ = Collections.emptyList();
            this.supportLcdGestureSwitch_ = false;
            this.supportLcdGestureTime_ = false;
            this.supportDistanceUnit_ = false;
            this.supportTemperatureUnit_ = false;
            this.supportHourFormat_ = false;
            this.supportDateFormat_ = false;
            this.supportAutoHeartrate_ = false;
            this.supportAutoSport_ = false;
            this.supportHabitualHand_ = false;
            this.supportNickName_ = false;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DeviceConfSubscriber(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 13:
                            this.bitField0_ |= 1;
                            this.hash_ = input.readFixed32();
                            break;
                        case 18:
                            int limit = input.pushLimit(input.readRawVarint32());
                            if ((mutable_bitField0_ & 2) != 2 && input.getBytesUntilLimit() > 0) {
                                this.supportLanguageMask_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.supportLanguageMask_.add(Integer.valueOf(input.readFixed32()));
                            }
                            input.popLimit(limit);
                            break;
                        case 21:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.supportLanguageMask_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.supportLanguageMask_.add(Integer.valueOf(input.readFixed32()));
                            break;
                        case 24:
                            this.bitField0_ |= 2;
                            this.supportLcdGestureSwitch_ = input.readBool();
                            break;
                        case 32:
                            this.bitField0_ |= 4;
                            this.supportLcdGestureTime_ = input.readBool();
                            break;
                        case 40:
                            this.bitField0_ |= 8;
                            this.supportDistanceUnit_ = input.readBool();
                            break;
                        case 48:
                            this.bitField0_ |= 16;
                            this.supportTemperatureUnit_ = input.readBool();
                            break;
                        case 56:
                            this.bitField0_ |= 32;
                            this.supportHourFormat_ = input.readBool();
                            break;
                        case 64:
                            this.bitField0_ |= 64;
                            this.supportDateFormat_ = input.readBool();
                            break;
                        case 72:
                            this.bitField0_ |= 128;
                            this.supportAutoHeartrate_ = input.readBool();
                            break;
                        case 80:
                            this.bitField0_ |= 256;
                            this.supportAutoSport_ = input.readBool();
                            break;
                        case 88:
                            this.bitField0_ |= 512;
                            this.supportHabitualHand_ = input.readBool();
                            break;
                        case 96:
                            this.bitField0_ |= 1024;
                            this.supportNickName_ = input.readBool();
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
                        this.supportLanguageMask_ = Collections.unmodifiableList(this.supportLanguageMask_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.supportLanguageMask_ = Collections.unmodifiableList(this.supportLanguageMask_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DeviceConf.internal_static_DeviceConfSubscriber_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DeviceConf.internal_static_DeviceConfSubscriber_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceConfSubscriber.class, Builder.class);
        }

        public boolean hasHash() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getHash() {
            return this.hash_;
        }

        public List<Integer> getSupportLanguageMaskList() {
            return this.supportLanguageMask_;
        }

        public int getSupportLanguageMaskCount() {
            return this.supportLanguageMask_.size();
        }

        public int getSupportLanguageMask(int index) {
            return ((Integer) this.supportLanguageMask_.get(index)).intValue();
        }

        public boolean hasSupportLcdGestureSwitch() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getSupportLcdGestureSwitch() {
            return this.supportLcdGestureSwitch_;
        }

        public boolean hasSupportLcdGestureTime() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getSupportLcdGestureTime() {
            return this.supportLcdGestureTime_;
        }

        public boolean hasSupportDistanceUnit() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean getSupportDistanceUnit() {
            return this.supportDistanceUnit_;
        }

        public boolean hasSupportTemperatureUnit() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean getSupportTemperatureUnit() {
            return this.supportTemperatureUnit_;
        }

        public boolean hasSupportHourFormat() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean getSupportHourFormat() {
            return this.supportHourFormat_;
        }

        public boolean hasSupportDateFormat() {
            return (this.bitField0_ & 64) == 64;
        }

        public boolean getSupportDateFormat() {
            return this.supportDateFormat_;
        }

        public boolean hasSupportAutoHeartrate() {
            return (this.bitField0_ & 128) == 128;
        }

        public boolean getSupportAutoHeartrate() {
            return this.supportAutoHeartrate_;
        }

        public boolean hasSupportAutoSport() {
            return (this.bitField0_ & 256) == 256;
        }

        public boolean getSupportAutoSport() {
            return this.supportAutoSport_;
        }

        public boolean hasSupportHabitualHand() {
            return (this.bitField0_ & 512) == 512;
        }

        public boolean getSupportHabitualHand() {
            return this.supportHabitualHand_;
        }

        public boolean hasSupportNickName() {
            return (this.bitField0_ & 1024) == 1024;
        }

        public boolean getSupportNickName() {
            return this.supportNickName_;
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
            } else if (!hasSupportLcdGestureSwitch()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportLcdGestureTime()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportDistanceUnit()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportTemperatureUnit()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportHourFormat()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportDateFormat()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportAutoHeartrate()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportAutoSport()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportHabitualHand()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasSupportNickName()) {
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
                output.writeFixed32(1, this.hash_);
            }
            if (getSupportLanguageMaskList().size() > 0) {
                output.writeUInt32NoTag(18);
                output.writeUInt32NoTag(this.supportLanguageMaskMemoizedSerializedSize);
            }
            for (int i = 0; i < this.supportLanguageMask_.size(); i++) {
                output.writeFixed32NoTag(((Integer) this.supportLanguageMask_.get(i)).intValue());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(3, this.supportLcdGestureSwitch_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(4, this.supportLcdGestureTime_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBool(5, this.supportDistanceUnit_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBool(6, this.supportTemperatureUnit_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeBool(7, this.supportHourFormat_);
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeBool(8, this.supportDateFormat_);
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeBool(9, this.supportAutoHeartrate_);
            }
            if ((this.bitField0_ & 256) == 256) {
                output.writeBool(10, this.supportAutoSport_);
            }
            if ((this.bitField0_ & 512) == 512) {
                output.writeBool(11, this.supportHabitualHand_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                output.writeBool(12, this.supportNickName_);
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
            int dataSize = getSupportLanguageMaskList().size() * 4;
            int size3 = size2 + dataSize;
            if (!getSupportLanguageMaskList().isEmpty()) {
                size3 = size3 + 1 + CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }
            this.supportLanguageMaskMemoizedSerializedSize = dataSize;
            if ((this.bitField0_ & 2) == 2) {
                size3 += CodedOutputStream.computeBoolSize(3, this.supportLcdGestureSwitch_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size3 += CodedOutputStream.computeBoolSize(4, this.supportLcdGestureTime_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size3 += CodedOutputStream.computeBoolSize(5, this.supportDistanceUnit_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size3 += CodedOutputStream.computeBoolSize(6, this.supportTemperatureUnit_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size3 += CodedOutputStream.computeBoolSize(7, this.supportHourFormat_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size3 += CodedOutputStream.computeBoolSize(8, this.supportDateFormat_);
            }
            if ((this.bitField0_ & 128) == 128) {
                size3 += CodedOutputStream.computeBoolSize(9, this.supportAutoHeartrate_);
            }
            if ((this.bitField0_ & 256) == 256) {
                size3 += CodedOutputStream.computeBoolSize(10, this.supportAutoSport_);
            }
            if ((this.bitField0_ & 512) == 512) {
                size3 += CodedOutputStream.computeBoolSize(11, this.supportHabitualHand_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                size3 += CodedOutputStream.computeBoolSize(12, this.supportNickName_);
            }
            int size4 = size3 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size4;
            return size4;
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
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DeviceConfSubscriber)) {
                return super.equals(obj);
            }
            DeviceConfSubscriber other = (DeviceConfSubscriber) obj;
            boolean result13 = 1 != 0 && hasHash() == other.hasHash();
            if (hasHash()) {
                result13 = result13 && getHash() == other.getHash();
            }
            if (!result13 || !getSupportLanguageMaskList().equals(other.getSupportLanguageMaskList())) {
                result = false;
            } else {
                result = true;
            }
            if (!result || hasSupportLcdGestureSwitch() != other.hasSupportLcdGestureSwitch()) {
                result2 = false;
            } else {
                result2 = true;
            }
            if (hasSupportLcdGestureSwitch()) {
                if (!result2 || getSupportLcdGestureSwitch() != other.getSupportLcdGestureSwitch()) {
                    result2 = false;
                } else {
                    result2 = true;
                }
            }
            if (!result2 || hasSupportLcdGestureTime() != other.hasSupportLcdGestureTime()) {
                result3 = false;
            } else {
                result3 = true;
            }
            if (hasSupportLcdGestureTime()) {
                if (!result3 || getSupportLcdGestureTime() != other.getSupportLcdGestureTime()) {
                    result3 = false;
                } else {
                    result3 = true;
                }
            }
            if (!result3 || hasSupportDistanceUnit() != other.hasSupportDistanceUnit()) {
                result4 = false;
            } else {
                result4 = true;
            }
            if (hasSupportDistanceUnit()) {
                if (!result4 || getSupportDistanceUnit() != other.getSupportDistanceUnit()) {
                    result4 = false;
                } else {
                    result4 = true;
                }
            }
            if (!result4 || hasSupportTemperatureUnit() != other.hasSupportTemperatureUnit()) {
                result5 = false;
            } else {
                result5 = true;
            }
            if (hasSupportTemperatureUnit()) {
                if (!result5 || getSupportTemperatureUnit() != other.getSupportTemperatureUnit()) {
                    result5 = false;
                } else {
                    result5 = true;
                }
            }
            if (!result5 || hasSupportHourFormat() != other.hasSupportHourFormat()) {
                result6 = false;
            } else {
                result6 = true;
            }
            if (hasSupportHourFormat()) {
                if (!result6 || getSupportHourFormat() != other.getSupportHourFormat()) {
                    result6 = false;
                } else {
                    result6 = true;
                }
            }
            if (!result6 || hasSupportDateFormat() != other.hasSupportDateFormat()) {
                result7 = false;
            } else {
                result7 = true;
            }
            if (hasSupportDateFormat()) {
                if (!result7 || getSupportDateFormat() != other.getSupportDateFormat()) {
                    result7 = false;
                } else {
                    result7 = true;
                }
            }
            if (!result7 || hasSupportAutoHeartrate() != other.hasSupportAutoHeartrate()) {
                result8 = false;
            } else {
                result8 = true;
            }
            if (hasSupportAutoHeartrate()) {
                if (!result8 || getSupportAutoHeartrate() != other.getSupportAutoHeartrate()) {
                    result8 = false;
                } else {
                    result8 = true;
                }
            }
            if (!result8 || hasSupportAutoSport() != other.hasSupportAutoSport()) {
                result9 = false;
            } else {
                result9 = true;
            }
            if (hasSupportAutoSport()) {
                if (!result9 || getSupportAutoSport() != other.getSupportAutoSport()) {
                    result9 = false;
                } else {
                    result9 = true;
                }
            }
            if (!result9 || hasSupportHabitualHand() != other.hasSupportHabitualHand()) {
                result10 = false;
            } else {
                result10 = true;
            }
            if (hasSupportHabitualHand()) {
                if (!result10 || getSupportHabitualHand() != other.getSupportHabitualHand()) {
                    result10 = false;
                } else {
                    result10 = true;
                }
            }
            if (!result10 || hasSupportNickName() != other.hasSupportNickName()) {
                result11 = false;
            } else {
                result11 = true;
            }
            if (hasSupportNickName()) {
                if (!result11 || getSupportNickName() != other.getSupportNickName()) {
                    result11 = false;
                } else {
                    result11 = true;
                }
            }
            if (!result11 || !this.unknownFields.equals(other.unknownFields)) {
                result12 = false;
            } else {
                result12 = true;
            }
            return result12;
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasHash()) {
                hash = (((hash * 37) + 1) * 53) + getHash();
            }
            if (getSupportLanguageMaskCount() > 0) {
                hash = (((hash * 37) + 2) * 53) + getSupportLanguageMaskList().hashCode();
            }
            if (hasSupportLcdGestureSwitch()) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashBoolean(getSupportLcdGestureSwitch());
            }
            if (hasSupportLcdGestureTime()) {
                hash = (((hash * 37) + 4) * 53) + Internal.hashBoolean(getSupportLcdGestureTime());
            }
            if (hasSupportDistanceUnit()) {
                hash = (((hash * 37) + 5) * 53) + Internal.hashBoolean(getSupportDistanceUnit());
            }
            if (hasSupportTemperatureUnit()) {
                hash = (((hash * 37) + 6) * 53) + Internal.hashBoolean(getSupportTemperatureUnit());
            }
            if (hasSupportHourFormat()) {
                hash = (((hash * 37) + 7) * 53) + Internal.hashBoolean(getSupportHourFormat());
            }
            if (hasSupportDateFormat()) {
                hash = (((hash * 37) + 8) * 53) + Internal.hashBoolean(getSupportDateFormat());
            }
            if (hasSupportAutoHeartrate()) {
                hash = (((hash * 37) + 9) * 53) + Internal.hashBoolean(getSupportAutoHeartrate());
            }
            if (hasSupportAutoSport()) {
                hash = (((hash * 37) + 10) * 53) + Internal.hashBoolean(getSupportAutoSport());
            }
            if (hasSupportHabitualHand()) {
                hash = (((hash * 37) + 11) * 53) + Internal.hashBoolean(getSupportHabitualHand());
            }
            if (hasSupportNickName()) {
                hash = (((hash * 37) + 12) * 53) + Internal.hashBoolean(getSupportNickName());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static DeviceConfSubscriber parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceConfSubscriber) PARSER.parseFrom(data);
        }

        public static DeviceConfSubscriber parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceConfSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceConfSubscriber parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceConfSubscriber) PARSER.parseFrom(data);
        }

        public static DeviceConfSubscriber parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceConfSubscriber) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceConfSubscriber parseFrom(InputStream input) throws IOException {
            return (DeviceConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceConfSubscriber parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceConfSubscriber parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceConfSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeviceConfSubscriber parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceConfSubscriber) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceConfSubscriber parseFrom(CodedInputStream input) throws IOException {
            return (DeviceConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceConfSubscriber parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceConfSubscriber) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeviceConfSubscriber prototype) {
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

        public static DeviceConfSubscriber getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceConfSubscriber> parser() {
            return PARSER;
        }

        public Parser<DeviceConfSubscriber> getParserForType() {
            return PARSER;
        }

        public DeviceConfSubscriber getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public enum DeviceLanuage implements ProtocolMessageEnum {
        English(0),
        Chinese(1),
        Italian(2),
        Japanese(3),
        France(4),
        German(5),
        Portuguese(6),
        Spanish(7),
        Russian(8),
        Korean(9),
        Arabic(10),
        Vietnam(11),
        Polish(12),
        Romanian(13),
        Swedish(14),
        Thai(15),
        Turkish(16),
        Denish(17),
        Ukrainian(18),
        Norwegian(19),
        Dutch(20),
        Czech(21),
        Bare(255);
        
        public static final int Arabic_VALUE = 10;
        public static final int Bare_VALUE = 255;
        public static final int Chinese_VALUE = 1;
        public static final int Czech_VALUE = 21;
        public static final int Denish_VALUE = 17;
        public static final int Dutch_VALUE = 20;
        public static final int English_VALUE = 0;
        public static final int France_VALUE = 4;
        public static final int German_VALUE = 5;
        public static final int Italian_VALUE = 2;
        public static final int Japanese_VALUE = 3;
        public static final int Korean_VALUE = 9;
        public static final int Norwegian_VALUE = 19;
        public static final int Polish_VALUE = 12;
        public static final int Portuguese_VALUE = 6;
        public static final int Romanian_VALUE = 13;
        public static final int Russian_VALUE = 8;
        public static final int Spanish_VALUE = 7;
        public static final int Swedish_VALUE = 14;
        public static final int Thai_VALUE = 15;
        public static final int Turkish_VALUE = 16;
        public static final int Ukrainian_VALUE = 18;
        private static final DeviceLanuage[] VALUES = null;
        public static final int Vietnam_VALUE = 11;
        private static final EnumLiteMap<DeviceLanuage> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<DeviceLanuage>() {
                public DeviceLanuage findValueByNumber(int number) {
                    return DeviceLanuage.forNumber(number);
                }
            };
            VALUES = values();
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static DeviceLanuage valueOf(int value2) {
            return forNumber(value2);
        }

        public static DeviceLanuage forNumber(int value2) {
            switch (value2) {
                case 0:
                    return English;
                case 1:
                    return Chinese;
                case 2:
                    return Italian;
                case 3:
                    return Japanese;
                case 4:
                    return France;
                case 5:
                    return German;
                case 6:
                    return Portuguese;
                case 7:
                    return Spanish;
                case 8:
                    return Russian;
                case 9:
                    return Korean;
                case 10:
                    return Arabic;
                case 11:
                    return Vietnam;
                case 12:
                    return Polish;
                case 13:
                    return Romanian;
                case 14:
                    return Swedish;
                case 15:
                    return Thai;
                case 16:
                    return Turkish;
                case 17:
                    return Denish;
                case 18:
                    return Ukrainian;
                case 19:
                    return Norwegian;
                case 20:
                    return Dutch;
                case 21:
                    return Czech;
                case 255:
                    return Bare;
                default:
                    return null;
            }
        }

        public static EnumLiteMap<DeviceLanuage> internalGetValueMap() {
            return internalValueMap;
        }

        public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor) getDescriptor().getValues().get(ordinal());
        }

        public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }

        public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor) DeviceConf.getDescriptor().getEnumTypes().get(0);
        }

        public static DeviceLanuage valueOf(EnumValueDescriptor desc) {
            if (desc.getType() == getDescriptor()) {
                return VALUES[desc.getIndex()];
            }
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
        }

        private DeviceLanuage(int value2) {
            this.value = value2;
        }
    }

    public static final class DeviceLcdGs extends GeneratedMessageV3 implements DeviceLcdGsOrBuilder {
        private static final DeviceLcdGs DEFAULT_INSTANCE = new DeviceLcdGs();
        public static final int LCD_GS_END_HOUR_FIELD_NUMBER = 2;
        public static final int LCD_GS_START_HOUR_FIELD_NUMBER = 1;
        @Deprecated
        public static final Parser<DeviceLcdGs> PARSER = new AbstractParser<DeviceLcdGs>() {
            public DeviceLcdGs parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DeviceLcdGs(input, extensionRegistry);
            }
        };
        private static final long serialVersionUID = 0;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int lcdGsEndHour_;
        /* access modifiers changed from: private */
        public int lcdGsStartHour_;
        private byte memoizedIsInitialized;

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements DeviceLcdGsOrBuilder {
            private int bitField0_;
            private int lcdGsEndHour_;
            private int lcdGsStartHour_;

            public static final Descriptor getDescriptor() {
                return DeviceConf.internal_static_DeviceLcdGs_descriptor;
            }

            /* access modifiers changed from: protected */
            public FieldAccessorTable internalGetFieldAccessorTable() {
                return DeviceConf.internal_static_DeviceLcdGs_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceLcdGs.class, Builder.class);
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (DeviceLcdGs.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                this.lcdGsStartHour_ = 0;
                this.bitField0_ &= -2;
                this.lcdGsEndHour_ = 0;
                this.bitField0_ &= -3;
                return this;
            }

            public Descriptor getDescriptorForType() {
                return DeviceConf.internal_static_DeviceLcdGs_descriptor;
            }

            public DeviceLcdGs getDefaultInstanceForType() {
                return DeviceLcdGs.getDefaultInstance();
            }

            public DeviceLcdGs build() {
                DeviceLcdGs result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw newUninitializedMessageException(result);
            }

            public DeviceLcdGs buildPartial() {
                DeviceLcdGs result = new DeviceLcdGs((com.google.protobuf.GeneratedMessageV3.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.lcdGsStartHour_ = this.lcdGsStartHour_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.lcdGsEndHour_ = this.lcdGsEndHour_;
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
                if (other instanceof DeviceLcdGs) {
                    return mergeFrom((DeviceLcdGs) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeviceLcdGs other) {
                if (other != DeviceLcdGs.getDefaultInstance()) {
                    if (other.hasLcdGsStartHour()) {
                        setLcdGsStartHour(other.getLcdGsStartHour());
                    }
                    if (other.hasLcdGsEndHour()) {
                        setLcdGsEndHour(other.getLcdGsEndHour());
                    }
                    mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasLcdGsStartHour() && hasLcdGsEndHour()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                DeviceLcdGs parsedMessage = null;
                try {
                    DeviceLcdGs parsedMessage2 = (DeviceLcdGs) DeviceLcdGs.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage2 != null) {
                        mergeFrom(parsedMessage2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    DeviceLcdGs parsedMessage3 = (DeviceLcdGs) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    throw th;
                }
            }

            public boolean hasLcdGsStartHour() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getLcdGsStartHour() {
                return this.lcdGsStartHour_;
            }

            public Builder setLcdGsStartHour(int value) {
                this.bitField0_ |= 1;
                this.lcdGsStartHour_ = value;
                onChanged();
                return this;
            }

            public Builder clearLcdGsStartHour() {
                this.bitField0_ &= -2;
                this.lcdGsStartHour_ = 0;
                onChanged();
                return this;
            }

            public boolean hasLcdGsEndHour() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getLcdGsEndHour() {
                return this.lcdGsEndHour_;
            }

            public Builder setLcdGsEndHour(int value) {
                this.bitField0_ |= 2;
                this.lcdGsEndHour_ = value;
                onChanged();
                return this;
            }

            public Builder clearLcdGsEndHour() {
                this.bitField0_ &= -3;
                this.lcdGsEndHour_ = 0;
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

        private DeviceLcdGs(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private DeviceLcdGs() {
            this.memoizedIsInitialized = -1;
            this.lcdGsStartHour_ = 0;
            this.lcdGsEndHour_ = 0;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DeviceLcdGs(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.lcdGsStartHour_ = input.readFixed32();
                            break;
                        case 21:
                            this.bitField0_ |= 2;
                            this.lcdGsEndHour_ = input.readFixed32();
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
            return DeviceConf.internal_static_DeviceLcdGs_descriptor;
        }

        /* access modifiers changed from: protected */
        public FieldAccessorTable internalGetFieldAccessorTable() {
            return DeviceConf.internal_static_DeviceLcdGs_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceLcdGs.class, Builder.class);
        }

        public boolean hasLcdGsStartHour() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getLcdGsStartHour() {
            return this.lcdGsStartHour_;
        }

        public boolean hasLcdGsEndHour() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getLcdGsEndHour() {
            return this.lcdGsEndHour_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasLcdGsStartHour()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasLcdGsEndHour()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeFixed32(1, this.lcdGsStartHour_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeFixed32(2, this.lcdGsEndHour_);
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
                size2 = 0 + CodedOutputStream.computeFixed32Size(1, this.lcdGsStartHour_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeFixed32Size(2, this.lcdGsEndHour_);
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
            if (!(obj instanceof DeviceLcdGs)) {
                return super.equals(obj);
            }
            DeviceLcdGs other = (DeviceLcdGs) obj;
            boolean result3 = 1 != 0 && hasLcdGsStartHour() == other.hasLcdGsStartHour();
            if (hasLcdGsStartHour()) {
                result3 = result3 && getLcdGsStartHour() == other.getLcdGsStartHour();
            }
            if (!result3 || hasLcdGsEndHour() != other.hasLcdGsEndHour()) {
                result = false;
            } else {
                result = true;
            }
            if (hasLcdGsEndHour()) {
                if (!result || getLcdGsEndHour() != other.getLcdGsEndHour()) {
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
            if (hasLcdGsStartHour()) {
                hash = (((hash * 37) + 1) * 53) + getLcdGsStartHour();
            }
            if (hasLcdGsEndHour()) {
                hash = (((hash * 37) + 2) * 53) + getLcdGsEndHour();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static DeviceLcdGs parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceLcdGs) PARSER.parseFrom(data);
        }

        public static DeviceLcdGs parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceLcdGs) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceLcdGs parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceLcdGs) PARSER.parseFrom(data);
        }

        public static DeviceLcdGs parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceLcdGs) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceLcdGs parseFrom(InputStream input) throws IOException {
            return (DeviceLcdGs) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceLcdGs parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceLcdGs) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceLcdGs parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceLcdGs) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeviceLcdGs parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceLcdGs) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceLcdGs parseFrom(CodedInputStream input) throws IOException {
            return (DeviceLcdGs) GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceLcdGs parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceLcdGs) GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeviceLcdGs prototype) {
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

        public static DeviceLcdGs getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceLcdGs> parser() {
            return PARSER;
        }

        public Parser<DeviceLcdGs> getParserForType() {
            return PARSER;
        }

        public DeviceLcdGs getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public interface DeviceConfNotificationOrBuilder extends MessageOrBuilder {
        boolean getAutoHeartrate();

        boolean getAutoSport();

        boolean getDateFormat();

        boolean getDistanceUnit();

        boolean getHabitualHand();

        int getHash();

        boolean getHourFormat();

        DeviceLanuage getLanguageId();

        boolean getLcdGsSwitch();

        DeviceLcdGs getLcdGsTime();

        DeviceLcdGsOrBuilder getLcdGsTimeOrBuilder();

        String getNickName();

        ByteString getNickNameBytes();

        boolean getTemperatureUnit();

        boolean hasAutoHeartrate();

        boolean hasAutoSport();

        boolean hasDateFormat();

        boolean hasDistanceUnit();

        boolean hasHabitualHand();

        boolean hasHash();

        boolean hasHourFormat();

        boolean hasLanguageId();

        boolean hasLcdGsSwitch();

        boolean hasLcdGsTime();

        boolean hasNickName();

        boolean hasTemperatureUnit();
    }

    public interface DeviceConfSubscriberOrBuilder extends MessageOrBuilder {
        int getHash();

        boolean getSupportAutoHeartrate();

        boolean getSupportAutoSport();

        boolean getSupportDateFormat();

        boolean getSupportDistanceUnit();

        boolean getSupportHabitualHand();

        boolean getSupportHourFormat();

        int getSupportLanguageMask(int i);

        int getSupportLanguageMaskCount();

        List<Integer> getSupportLanguageMaskList();

        boolean getSupportLcdGestureSwitch();

        boolean getSupportLcdGestureTime();

        boolean getSupportNickName();

        boolean getSupportTemperatureUnit();

        boolean hasHash();

        boolean hasSupportAutoHeartrate();

        boolean hasSupportAutoSport();

        boolean hasSupportDateFormat();

        boolean hasSupportDistanceUnit();

        boolean hasSupportHabitualHand();

        boolean hasSupportHourFormat();

        boolean hasSupportLcdGestureSwitch();

        boolean hasSupportLcdGestureTime();

        boolean hasSupportNickName();

        boolean hasSupportTemperatureUnit();
    }

    public interface DeviceLcdGsOrBuilder extends MessageOrBuilder {
        int getLcdGsEndHour();

        int getLcdGsStartHour();

        boolean hasLcdGsEndHour();

        boolean hasLcdGsStartHour();
    }

    private DeviceConf() {
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
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0011device_conf.proto\"A\n\u000bDeviceLcdGs\u0012\u0019\n\u0011lcd_gs_start_hour\u0018\u0001 \u0002(\u0007\u0012\u0017\n\u000flcd_gs_end_hour\u0018\u0002 \u0002(\u0007\"¼\u0002\n\u0016DeviceConfNotification\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012#\n\u000blanguage_id\u0018\u0002 \u0001(\u000e2\u000e.DeviceLanuage\u0012\u001b\n\rlcd_gs_switch\u0018\u0003 \u0001(\b:\u0004true\u0012!\n\u000blcd_gs_time\u0018\u0004 \u0001(\u000b2\f.DeviceLcdGs\u0012\u0015\n\rdistance_unit\u0018\u0005 \u0001(\b\u0012\u0018\n\u0010temperature_unit\u0018\u0006 \u0001(\b\u0012\u0013\n\u000bhour_format\u0018\u0007 \u0001(\b\u0012\u0013\n\u000bdate_format\u0018\b \u0001(\b\u0012\u0016\n\u000eauto_heartrate\u0018\t \u0001(\b\u0012\u0012\n\nauto_sport\u0018\n \u0001(\b\u0012\u0015\n\rhabitual_hand\u0018\u000b \u0001(\b\u0012\u0011\n\tnick_name\u0018", "\f \u0001(\t\"þ\u0002\n\u0014DeviceConfSubscriber\u0012\f\n\u0004hash\u0018\u0001 \u0002(\u0007\u0012!\n\u0015support_language_mask\u0018\u0002 \u0003(\u0007B\u0002\u0010\u0001\u0012\"\n\u001asupport_lcd_gesture_switch\u0018\u0003 \u0002(\b\u0012 \n\u0018support_lcd_gesture_time\u0018\u0004 \u0002(\b\u0012\u001d\n\u0015support_distance_unit\u0018\u0005 \u0002(\b\u0012 \n\u0018support_temperature_unit\u0018\u0006 \u0002(\b\u0012\u001b\n\u0013support_hour_format\u0018\u0007 \u0002(\b\u0012\u001b\n\u0013support_date_format\u0018\b \u0002(\b\u0012\u001e\n\u0016support_auto_heartrate\u0018\t \u0002(\b\u0012\u001a\n\u0012support_auto_sport\u0018\n \u0002(\b\u0012\u001d\n\u0015support_habitual_hand\u0018\u000b \u0002(\b\u0012\u0019\n\u0011support_nick_name\u0018\f \u0002(\b*´\u0002\n\rDevic", "eLanuage\u0012\u000b\n\u0007English\u0010\u0000\u0012\u000b\n\u0007Chinese\u0010\u0001\u0012\u000b\n\u0007Italian\u0010\u0002\u0012\f\n\bJapanese\u0010\u0003\u0012\n\n\u0006France\u0010\u0004\u0012\n\n\u0006German\u0010\u0005\u0012\u000e\n\nPortuguese\u0010\u0006\u0012\u000b\n\u0007Spanish\u0010\u0007\u0012\u000b\n\u0007Russian\u0010\b\u0012\n\n\u0006Korean\u0010\t\u0012\n\n\u0006Arabic\u0010\n\u0012\u000b\n\u0007Vietnam\u0010\u000b\u0012\n\n\u0006Polish\u0010\f\u0012\f\n\bRomanian\u0010\r\u0012\u000b\n\u0007Swedish\u0010\u000e\u0012\b\n\u0004Thai\u0010\u000f\u0012\u000b\n\u0007Turkish\u0010\u0010\u0012\n\n\u0006Denish\u0010\u0011\u0012\r\n\tUkrainian\u0010\u0012\u0012\r\n\tNorwegian\u0010\u0013\u0012\t\n\u0005Dutch\u0010\u0014\u0012\t\n\u0005Czech\u0010\u0015\u0012\t\n\u0004Bare\u0010ÿ\u0001"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                DeviceConf.descriptor = root;
                return null;
            }
        });
    }
}
